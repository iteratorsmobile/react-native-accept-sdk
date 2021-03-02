package com.iteratorsmobile.lib.accept;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;

import net.authorize.acceptsdk.AcceptSDKApiClient;
import net.authorize.acceptsdk.datamodel.merchant.ClientKeyBasedMerchantAuthentication;
import net.authorize.acceptsdk.datamodel.transaction.CardData;
import net.authorize.acceptsdk.datamodel.transaction.EncryptTransactionObject;
import net.authorize.acceptsdk.datamodel.transaction.TransactionObject;
import net.authorize.acceptsdk.datamodel.transaction.TransactionType;
import net.authorize.acceptsdk.datamodel.transaction.callbacks.EncryptTransactionCallback;
import net.authorize.acceptsdk.datamodel.transaction.response.EncryptTransactionResponse;
import net.authorize.acceptsdk.datamodel.transaction.response.ErrorTransactionResponse;

public class AcceptSdkModule extends ReactContextBaseJavaModule {

    private final ReactApplicationContext reactContext;

    public AcceptSdkModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
    }

    @Override
    public String getName() {
        return "AcceptSdk";
    }

    @ReactMethod
    public void getTokenWithRequest(ReadableMap request, boolean isProduction, final Promise promise) {
        try {
            AcceptSDKApiClient acceptSDKApiClient = new AcceptSDKApiClient
                    .Builder(
                            reactContext,
                            isProduction ? AcceptSDKApiClient.Environment.PRODUCTION : AcceptSDKApiClient.Environment.SANDBOX
                    )
                    .connectionTimeout(5000)
                    .build();
            EncryptTransactionObject transactionObject = createTransactionObject(request);
            acceptSDKApiClient.getTokenWithRequest(transactionObject, new EncryptTransactionCallback() {
                @Override
                public void onErrorReceived(ErrorTransactionResponse error) {
                    promise.reject("An error occured while obtaining token.", "");
                }
                @Override
                public void onEncryptionFinished(EncryptTransactionResponse encryptionResponse) {
                    WritableMap response = Arguments.createMap();
                    response.putString("dataDescriptor", encryptionResponse.getDataDescriptor());
                    response.putString("dataValue", encryptionResponse.getDataValue());
                    promise.resolve(response);
                }
            });
        } catch (Exception e) {
            promise.reject(e.getMessage(), "");
        }
    }

    private EncryptTransactionObject createTransactionObject(ReadableMap request) {
        CardData cardData = new CardData
                .Builder(request.getString("cardNumber"), request.getString("cardExpirationMonth"), request.getString("cardExpirationYear"))
                .cvvCode(request.getString("cardCode"))
                .build();
        ClientKeyBasedMerchantAuthentication clientKeyBasedMerchantAuthentication =
                ClientKeyBasedMerchantAuthentication
                        .createMerchantAuthentication(request.getString("loginId"), request.getString("clientKey"));
        return TransactionObject
                .createTransactionObject(TransactionType.SDK_TRANSACTION_ENCRYPTION)
                .cardData(cardData)
                .merchantAuthentication(clientKeyBasedMerchantAuthentication)
                .build();
    }
}
