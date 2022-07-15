## API 명세

---------

### 1. 계약
#### 계약 정보 목록 조회

##### Request
``` http
GET /api/v1/contracts
```
 
##### Response

``` json
[
  {
    "contractId": 0,
    "contractMonths": 0,
    "contractNumber": "string",
    "insureEndDate": "2022-07-15",
    "insureStartDate": "2022-07-15",
    "productId": 0,
    "productName": "string",
    "status": "EXPIRY"
  }
]
```

| 이름             |   자료형   | 설명                                            | 필수
|----------------|:-------:|-----------------------------------------------|:---:
| contractId       |  Long   | 계약아이디                                         | O
| contractMonths      | Integer | 계약기간(월 단위)                                    | O
| contractNumber  | String  | 계약번호(계약마다 고유한 값을 가지는 참고용 정보)                  | O
| insureEndDate  | String  | 계약종료일자 (yyyy-MM-dd)                           | O
| insureStartDate  | String  | 계약시작일자 (yyyy-MM-dd)                           | O
| productId  |  Long   | 상품아이디                                         | O
| productName  | String  | 상품명                                           | O
| status  | String  | NORMAL(정상계약), WITHDRAW(청약철회), EXPIRY(기간만료) 지원 | O


#### 신규 계약 생성

##### Request
``` http
POST /api/v1/contracts
```

``` json
{
  "collateralIds": [
    0
  ],
  "contractMonths": 0,
  "productId": 0
}
```

| 이름             |   자료형   | 설명                  | 필수
|----------------|:-------:|---------------------|:---:
| collateralIds       | Long[]  | 가입담보목록(최소 1개 이상 필수) | O
| contractMonths      | Integer | 계약기간(월 단위)          | O
| productId  |  Long   | 상품번호                | O

성공 시 HTTP 상태코드 201이 반환됩니다.


#### 계약 정보 조회

##### Request
``` http
GET /api/v1/contracts/{contractId}
```

##### Response

``` json
{
  "collaterals": [
    {
      "collateralId": 0,
      "collateralName": "string",
      "premium": 0
    }
  ],
  "contractId": 0,
  "contractMonths": 0,
  "contractNumber": "string",
  "insureEndDate": "2022-07-15",
  "insureStartDate": "2022-07-15",
  "productId": 0,
  "productName": "string",
  "status": "EXPIRY",
  "totalPremium": 0
}
```

| 이름             |         자료형         | 설명                                           | 필수
|----------------|:-------------------:|----------------------------------------------|:---:
| contractId       |        Long         | 계약아이디                                        | O
| contractMonths      |       Integer       | 계약기간(월 단위)                                   | O
| contractNumber  |       String        | 계약번호(계약마다 고유한 값을 가지는 참고용 정보)                 | O
| insureEndDate  |       String        | 계약종료일자 (yyyy-MM-dd)                          | O
| insureStartDate  |       String        | 계약시작일자 (yyyy-MM-dd)                          | O
| productId  |        Long         | 상품아이디                                        | O
| productName  |       String        | 상품명                                          | O
| status  |       String        | NORMAL(정상계약), WITHDRAW(청약철회), EXPIRY(기간만료) 지원 | O
| totalPremium  |     BigDecimal      | 총 보험료                                        | O
| collaterals  | ContractCollateral[] | 가입 담보 목록 JSON 객체                             | O

#### ContractCollateral

| 이름             |  자료형   | 설명              | 필수
|----------------|:------:|-----------------|:---:
| collateralId       |  Long  | 담보아이디           | O
| collateralName      | String | 담보명             | O
| premium  | BigDecimal | 해당 담보 가입에 대한 보험료 | O

#### 계약 정보 수정

상품은 변경이 불가하며, 수정한 시간을 기준으로 담보의 가입금액과 기준금액으로 다시 보험료를 계산합니다.

##### Request
``` http
PUT /api/v1/contracts/{contractId}
```

``` json
{
  "collateralIds": [
    0
  ],
  "contractMonths": 0,
  "status": "EXPIRY"
}
```

| 이름             |   자료형   | 설명                  | 필수
|----------------|:-------:|---------------------|:---:
| collateralIds       | Long[]  | 가입담보목록(최소 1개 이상 필수) | O
| contractMonths      | Integer | 계약기간(월 단위)          | O
| status  | String  | NORMAL(정상계약), WITHDRAW(청약철회), EXPIRY(기간만료) 지원                | O

성공 시 HTTP 상태코드 204가 반환됩니다.

#### 계약 시 예상 총 보험료 계산

##### Request
``` http
GET /api/v1/contracts/calculate
```
| 이름             |  자료형   | 설명        | 필수
|----------------|:------:|-----------|:---:
| collateralIds       | Long[] | 담보 아이디 목록 | O
| contractMonths      | String | 계약기간      | O
| productId  |  Long  | 가입 상품 아이디 | O

##### Response

``` json
{
  "collaterals": [
    {
      "collateralId": 0,
      "collateralName": "string",
      "premium": 0
    }
  ],
  "contractMonths": 0,
  "productId": 0,
  "productName": "string",
  "totalPremium": 0
}
```

| 이름             |         자료형         | 설명                                           | 필수
|----------------|:-------------------:|----------------------------------------------|:---:
| contractMonths      |       Integer       | 계약기간(월 단위)                                   | O
| productId  |        Long         | 상품아이디                                        | O
| productName  |       String        | 상품명                                          | O
| totalPremium  |     BigDecimal      | 총 보험료                                        | O
| collaterals  | ContractCollateral[] | 가입 담보 목록 JSON 객체                             | O

#### ContractCollateral

| 이름             |  자료형   | 설명              | 필수
|----------------|:------:|-----------------|:---:
| collateralId       |  Long  | 담보아이디           | O
| collateralName      | String | 담보명             | O
| premium  | BigDecimal | 해당 담보 가입에 대한 보험료 | O

### 2. 담보
#### 담보 정보 목록 조회

##### Request
``` http
GET /api/v1/collaterals
```

##### Response

``` json
[
  {
    "collateralId": 0,
    "baseAmount": 0,
    "insureAmount": 0,
    "name": "string"
  }
]
```

| 이름             |   자료형   | 설명   | 필수
|----------------|:-------:|------|:---:
| collateralId       |  Long   | 담보아이디 | O
| baseAmount      | Integer | 기준금액 | O
| insureAmount  | Integer  | 가입금액 | O
| name  | String  | 담보명칭 | O


#### 신규 담보 생성

##### Request
``` http
POST /api/v1/collaterals
```

``` json
{
  "baseAmount": 0,
  "insureAmount": 0,
  "name": "string"
}
```

| 이름             |   자료형   | 설명                  | 필수
|----------------|:-------:|---------------------|:---:
| baseAmount      | Integer | 기준금액 | O
| insureAmount  | Integer  | 가입금액 | O
| name  | String  | 담보명칭 | O

성공 시 HTTP 상태코드 201이 반환됩니다.

#### 담보 정보 조회

##### Request
``` http
GET /api/v1/collaterals/{collateralId}
```

##### Response

``` json
{
  "baseAmount": 0,
  "collateralId": 0,
  "insureAmount": 0,
  "name": "string"
}
```

| 이름             |   자료형   | 설명   | 필수
|----------------|:-------:|------|:---:
| collateralId       |  Long   | 담보아이디 | O
| baseAmount      | Integer | 기준금액 | O
| insureAmount  | Integer  | 가입금액 | O
| name  | String  | 담보명칭 | O

#### 담보 정보 수정

##### Request
``` http
PUT /api/v1/collaterals/{collateralId}
```

``` json
{
  "baseAmount": 0,
  "insureAmount": 0,
  "name": "string"
}
```

| 이름             |   자료형   | 설명                  | 필수
|----------------|:-------:|---------------------|:---:
| baseAmount      | Integer | 기준금액 | O
| insureAmount  | Integer  | 가입금액 | O
| name  | String  | 담보명칭 | O

성공 시 HTTP 상태코드 204가 반환됩니다.

### 3. 상품
#### 상품 정보 목록 조회

##### Request
``` http
GET /api/v1/products
```

##### Response

``` json
[
  {
    "createdDate": "2022-07-15T02:37:53.497Z",
    "maxContractMonths": 0,
    "minContractMonths": 0,
    "name": "string",
    "productId": 0,
    "validityEndDate": "2022-07-15T02:37:53.497Z",
    "validityStartDate": "2022-07-15T02:37:53.497Z"
  }
]
```

| 이름             |   자료형   | 설명                                | 필수
|----------------|:-------:|-----------------------------------|:---:
| productId       |  Long   | 담보아이디                             | O
| maxContractMonths      | Integer | 최대가입개월수                           | O
| minContractMonths  | Integer  | 최소가입개월수                           | O
| name  | String  | 상품명칭                              | O
| validityEndDate  | String  | 상품유효기간 종료일시 (yyyy-MM-dd'T'HH:mm:ss'Z')                      | O
| validityStartDate  | String  | 상품유효기간 시작일시 (yyyy-MM-dd'T'HH:mm:ss'Z')                      | O
| createdDate  | String  | 상품등록일시 (yyyy-MM-dd'T'HH:mm:ss'Z') | O


#### 신규 상품 생성

##### Request
``` http
POST /api/v1/products
```

``` json
{
  "collateralIds": [
    0
  ],
  "maxContractMonths": 0,
  "minContractMonths": 0,
  "name": "string",
  "validityEndDate": "2022-07-15T02:40:14.032Z",
  "validityStartDate": "2022-07-15T02:40:14.032Z"
}
```

| 이름             |   자료형   | 설명                                    | 필수
|----------------|:-------:|---------------------------------------|:---:
| maxContractMonths      | Integer | 최대가입개월수                               | O
| minContractMonths  | Integer | 최소가입개월수                               | O
| name  | String  | 상품명칭                                  | O
| validityEndDate  | String  | 상품유효기간 종료일시 (yyyy-MM-dd'T'HH:mm:ss'Z') | O
| validityStartDate  | String  | 상품유효기간 시작일시 (yyyy-MM-dd'T'HH:mm:ss'Z') | O
| collateralIds  | Long[]  | 해당 상품에 포함되어있는 담보의 목록                  | O


성공 시 HTTP 상태코드 201이 반환됩니다.

#### 상품 정보 조회

##### Request
``` http
GET /api/v1/products/{productId}
```

##### Response

``` json
{
  "collaterals": [
    {
      "baseAmount": 0,
      "collateralId": 0,
      "insureAmount": 0,
      "name": "string"
    }
  ],
  "createdDate": "2022-07-15T02:41:58.111Z",
  "maxContractMonths": 0,
  "minContractMonths": 0,
  "name": "string",
  "productId": 0,
  "validityEndDate": "2022-07-15T02:41:58.111Z",
  "validityStartDate": "2022-07-15T02:41:58.111Z"
}
```

| 이름             |     자료형      | 설명                                    | 필수
|----------------|:------------:|---------------------------------------|:---:
| productId       |     Long     | 담보아이디                                 | O
| maxContractMonths      |   Integer    | 최대가입개월수                               | O
| minContractMonths  |   Integer    | 최소가입개월수                               | O
| name  |    String    | 상품명칭                                  | O
| validityEndDate  |    String    | 상품유효기간 종료일시 (yyyy-MM-dd'T'HH:mm:ss'Z') | O
| validityStartDate  |    String    | 상품유효기간 시작일시 (yyyy-MM-dd'T'HH:mm:ss'Z') | O
| createdDate  |    String    | 상품등록일시 (yyyy-MM-dd'T'HH:mm:ss'Z')     | O
| collaterals  | Collateral[] | 해당 상품에 포함되어있는 담보의 목록 | O

#### Collateral

| 이름             |  자료형   | 설명              | 필수
|----------------|:------:|-----------------|:---:
| collateralId       |  Long   | 담보아이디 | O
| baseAmount      | Integer | 기준금액 | O
| insureAmount  | Integer  | 가입금액 | O
| name  | String  | 담보명칭 | O


#### 담보 정보 수정

상품은 담보의 삭제가 불가합니다. 기존 담보를 삭제해야 할 경우 새로 등록하여야 하며, 담보를 추가만 가능합니다.

##### Request
``` http
PUT /api/v1/collaterals/{collateralId}
```

``` json
{
  "addCollateralIds": [
    0
  ],
  "maxContractMonths": 0,
  "minContractMonths": 0,
  "name": "string",
  "validityEndDate": "2022-07-15T02:44:59.047Z",
  "validityStartDate": "2022-07-15T02:44:59.047Z"
}

```

| 이름             |   자료형   | 설명                                     | 필수
|----------------|:-------:|----------------------------------------|:---:
| maxContractMonths      | Integer | 최대가입개월수                                | O
| minContractMonths  | Integer | 최소가입개월수                                | O
| name  | String  | 상품명칭                                   | O
| validityEndDate  | String  | 상품유효기간 종료일시 (yyyy-MM-dd'T'HH:mm:ss'Z') | O
| validityStartDate  | String  | 상품유효기간 시작일시 (yyyy-MM-dd'T'HH:mm:ss'Z') | O
| addCollateralIds  | Long[]  | **신규**로 추가할 담보의 목록                     | O

성공 시 HTTP 상태코드 204가 반환됩니다.