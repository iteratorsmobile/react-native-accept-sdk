#import "AcceptSdk.h"
@import AuthorizeNetAccept;

@implementation AcceptSdk

RCT_EXPORT_MODULE()

RCT_EXPORT_METHOD(getTokenWithRequest: (NSDictionary *)tokenRequest isProduction:(BOOL)isProduction resolver:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject)
{    
    AcceptSDKHandler *handler = [[AcceptSDKHandler alloc] initWithEnvironment: isProduction ? AcceptSDKEnvironmentENV_LIVE : AcceptSDKEnvironmentENV_TEST];
    AcceptSDKRequest *request = [[AcceptSDKRequest alloc] init];

    request.merchantAuthentication.name = [tokenRequest valueForKey: @"loginId"];
    request.merchantAuthentication.clientKey = [tokenRequest valueForKey: @"clientKey"];
    request.securePaymentContainerRequest.webCheckOutDataType.token.cardNumber = [tokenRequest valueForKey: @"cardNumber"];
    request.securePaymentContainerRequest.webCheckOutDataType.token.expirationMonth = [tokenRequest valueForKey: @"cardExpirationMonth"];
    request.securePaymentContainerRequest.webCheckOutDataType.token.expirationYear = [tokenRequest valueForKey: @"cardExpirationYear"];
    request.securePaymentContainerRequest.webCheckOutDataType.token.cardCode = [tokenRequest valueForKey: @"cardCode"];

    [handler getTokenWithRequest:request successHandler:^(AcceptSDKTokenResponse * _Nonnull token) {
        NSDictionary *responseDictionary = @{@"dataDescriptor": token.getOpaqueData.getDataDescriptor, @"dataValue": token.getOpaqueData.getDataValue};
        resolve(responseDictionary);
    } failureHandler:^(AcceptSDKErrorResponse * _Nonnull error) {
        reject(
           @"ERROR_OBTAINING_TOKEN",
           [[[[error getMessages] getMessages] firstObject] getText],
           nil
        );
    }];
}

@end
