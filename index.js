import { NativeModules } from 'react-native';

const { AcceptSdk } = NativeModules;

export default {
    getTokenWithRequest: (tokenRequest, isProduction) => AcceptSdk.getTokenWithRequest(tokenRequest, isProduction)        
}