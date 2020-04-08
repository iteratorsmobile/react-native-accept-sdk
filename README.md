
# react-native-accept-sdk

## Getting started

add to package.json:
```
"react-native-accept-sdk": "git://github.com/iteratorsmobile/react-native-accept-sdk"
```

### Mostly automatic installation

`$ react-native link react-native-accept-sdk`

## Usage

```typescript
import  AcceptSdk  from  'react-native-accept-sdk';
AcceptSdk.getTokenWithRequest(tokenRequest: TokenRequest, isProduction: boolean)
```
```typescript
interface  TokenRequest {
	loginId: string;
	clientKey: string;
	cardNumber: string;
	cardExpirationYear: string;
	cardExpirationMonth: string;
	cardCode: string;	
}
```