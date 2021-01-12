
# react-native-accept-sdk

## Getting started

`$ npm install @iteratorsmobile/react-native-accept-sdk --save`

or 

`$ yarn add @iteratorsmobile/react-native-accept-sdk`

### Mostly automatic installation

`$ react-native link @iteratorsmobile/react-native-accept-sdk`

## Usage

```typescript
import AcceptSdk from '@iteratorsmobile/react-native-accept-sdk';
```
```typescript
AcceptSdk.getTokenWithRequest(tokenRequest: TokenRequest, isProduction: boolean)
```
```typescript
interface TokenRequest {
	loginId: string;
	clientKey: string;
	cardNumber: string;
	cardExpirationYear: string;
	cardExpirationMonth: string;
	cardCode: string;	
}
```