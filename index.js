import { NativeModules } from 'react-native';

const { AcceptSdk } = NativeModules;

export default {
    getTokenWithRequest: async (tokenRequest, isProduction) => {
        try { 
            const tokenResponse = await AcceptSdk.getTokenWithRequest(tokenRequest, isProduction);
            return tokenResponse;
        } catch (error) {
            return error;
        }
    }
}