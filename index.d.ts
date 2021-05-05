declare namespace AcceptSdk {
    interface TokenRequest {
        loginId: string;
        clientKey: string;
        cardNumber: string;
        cardExpirationYear: string;
        cardExpirationMonth: string;
        cardCode: string;
    }

    interface TokenResponse {
        dataDescriptor: string;
        dataValue: string;
    }

    const getTokenWithRequest: (tokenRequest: TokenRequest, isProduction: boolean) => Promise<TokenResponse>;
}

export = AcceptSdk