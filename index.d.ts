export interface TokenRequest {
    loginId: string;
    clientKey: string;
    cardNumber: string;
    cardExpirationYear: string;
    cardExpirationMonth: string;
    cardCode: string;
}

export default {
    getTokenWithRequest: (tokenRequest: TokenRequest, isProduction: boolean): Promise<any> => {}
}
