## Values computed in metasfresh when pulling data from Shopware6

computed from the following parameters set on window `541116 - ExternalSystem_Config_Shopware6`

* `JsonExternalSystemRequest.parameters.ClientId`
* `JsonExternalSystemRequest.parameters.ClientSecret`
* `JsonExternalSystemRequest.parameters.BasePath`
* `JsonExternalSystemRequest.parameters.JSONPathConstantBPartnerLocationID`
  * when importing Shopware documents that contain an `address`, the system will look under the `address` object at the given JSON path to identify the value for the Shopware's address identifier as `shopwareLocationIdentifier`
  * currently, there are two documents pulled from shopware that contain `addresses`:
    * `orders`
      * `shippingAddress` - pulled from `api/order/{{Order.id}}/deliveries`
        * in this case, the `shopwareLocationIdentifier` info is located at path `data/shippingOrderAddress/customFields/originalAddressId` 
      * `billingAddress` - pulled from `api/order-address/{{Order.billingAddressId}}`      
        * in this case, the `shopwareLocationIdentifier` info is located at path `data/customFields/originalAddressId`
    * `customers`
      * `defaultShippingAddress` - pulled from `/api/customer-address/{{JsonCustomer.defaultShippingAddressId}}`
      * `defaultBillingAddress` - pulled from `/api/customer-address/{{JsonCustomer.defaultBillingAddressId}}`
        * for this scenario, currently there is no `shopwareLocationIdentifier` provided
  * even though the `address` data is located at different paths, the system is able to use the same `JSONPathConstantBPartnerLocationID` for locating the `shopwareLocationIdentifier` as it searches within the Address info. See example:
    * Given `shopwareLocationIdentifier` is located at `data/shippingOrderAddress/customFields/originalAddressId` on `GET` `api/order/{{Order.id}}/deliveries` response
    * And `shopwareLocationIdentifier` is located at `data/customFields/originalAddressId` on `GET` `api/order-address/{{Order.billingAddressId}}` response
    * Then a valid `JSONPathConstantBPartnerLocationID` would be `customFields/originalAddressId`
* `JsonExternalSystemRequest.parameters.JSONPathEmail`
  * when importing Shopware documents that contains an `address`, the system will look under the `address` object at the given JSON path to identify the value for the Shopware's address email as `shopwareLocationEmail`
  * currently, there are two documents pulled from shopware that contain `addresses`:
    * `orders`
      * `shippingAddress` - pulled from `api/order/{{Order.id}}/deliveries`
        * in this case, the `shopwareLocationEmail` info is located at path `data/shippingOrderAddress/customFields/deliveryNotificationEmailAddress`
      * `billingAddress` - pulled from `api/order-address/{{Order.billingAddressId}}`
        * in this case, the `shopwareLocationEmail` info is located at path `data/customFields/deliveryNotificationEmailAddress`
    * `customers`
      * `defaultShippingAddress` - pulled from `/api/customer-address/{{JsonCustomer.defaultShippingAddressId}}`
      * `defaultBillingAddress` - pulled from `/api/customer-address/{{JsonCustomer.defaultBillingAddressId}}`
        * for this scenario, currently there is no `shopwareLocationEmail` provided
  * even though, the `address` data is located at different paths, the system is able to use the same `JSONPathEmail` for locating the `shopwareLocationEmail` as it searches within the Address info. see example:
    * Given `shopwareLocationEmail` is located at `data/shippingOrderAddress/customFields/deliveryNotificationEmailAddress` on `GET` `api/order/{{Order.id}}/deliveries` response
    * And `shopwareLocationEmail` is located at `data/customFields/deliveryNotificationEmailAddress` on `GET` `api/order-address/{{Order.billingAddressId}}` response
    * Then a valid `JSONPathEmail` would be `customFields/deliveryNotificationEmailAddress`
* `JsonExternalSystemRequest.parameters.JSONPathSalesRepID` 
  * when importing Shopware documents that at the given JSON path contains a `salesPartnerCode` for a given `C_BPartner` from metasfresh
  * currently, there one document pulled from shopware that contain `salesPartnerCode`:
    * `orders`
      * pulled from `api/search/order/`
      * in this case, the `salesPartnerCode` info is located at path `data/customFields/mykey_partner`
  * see example:
    * Given `salesPartnerCode` is located at `data/customFields/mykey_partner` on `POST` `api/search/order/` response
    * Then a valid `JSONPathSalesRepID` would be `customFields/mykey_partner`
* `JsonExternalSystemRequest.parameters.UpdatedAfterOverride`
* `JsonExternalSystemRequest.parameters.UpdatedAfter`
* `JsonExternalSystemRequest.parameters.M_FreightCost_NormalVAT_Product_ID`
* `JsonExternalSystemRequest.parameters.NormalVAT_Rates`
* `JsonExternalSystemRequest.parameters.M_FreightCost_ReducedVAT_Product_ID`
* `JsonExternalSystemRequest.parameters.Reduced_VAT_Rates`
* `JsonExternalSystemRequest.parameters.TargetPriceListId`
* `JsonExternalSystemRequest.parameters.PriceList_IsTaxIncluded`
* `JsonExternalSystemRequest.parameters.PriceListCurrencyCode`
* `JsonExternalSystemRequest.parameters.JSONPathMetasfreshID`
  * when importing Shopware documents that contains a `customer`, the system will look under the `customer` object at the given JSON path to identify the value for metasfresh ID (C_BPartner_ID)
  * currently, there are two documents pulled from shopware that contain customers:
    * `orders`
      * pulled from `api/search/order/`
      * in this case, the `customer` info is located at path `data/orderCustomer`
    * `customers`
      * pulled from `api/search/customer`
      * in this case, the `customer` info is located directly at path `data`
  * even though, the customer data is located at different paths, the system is able to use the same `JSONPathMetasfreshID` for locating the `metasfreshId` as it searches within the customer info. see example:
    * Given `metasfreshId` is located at `data/orderCustomer/customFields/metasfreshId` on `POST` `api/search/order/` response
    * And `metasfreshId` is located at `data/customFields/metasfreshId` on `POST` `api/search/customer` response
    * Then a valid `JSONPathMetasfreshID` would be `customFields/metasfreshId`
  
* `JsonExternalSystemRequest.parameters.JSONPathShopwareID`
  * when importing Shopware documents that contains a `customer`, the system will look under the `customer` object at the given JSON path to identify the Shopware's identifier
  * currently, there are two documents pulled from shopware that contain customers:
    * `orders`
      * pulled from `api/search/order/`
      * in this case, the `customer` info is located at path `data/orderCustomer`
    * `customers`
      * pulled from `api/search/customer`
      * in this case, the `customer` info is located directly at path `data`
  * even though, the customer data is located at different paths, the system is able to use the same `JSONPathShopwareID` for locating the `shopwareId` as it searches within the customer info. see example:
    * Given `shopwareId` is located at `data/orderCustomer/customFields/someCustomId` on `POST` `api/search/order/` response
    * And `shopwareId` is located at `data/customFields/someCustomId` on `POST` `api/search/customer` response
    * Then a valid `JSONPathShopwareID` would be `customFields/someCustomId`

* `JsonExternalSystemRequest.parameters.JsonExternalSystemShopware6ConfigMappings`
    * `JsonExternalSystemShopware6ConfigMappings.seqNo`
    * `JsonExternalSystemShopware6ConfigMappings.docTypeOrder`
    * `JsonExternalSystemShopware6ConfigMappings.paymentRule`
    * `JsonExternalSystemShopware6ConfigMappings.bpartnerSyncAdvice`
    * `JsonExternalSystemShopware6ConfigMappings.bpartnerLocationSyncAdvice`
    * `JsonExternalSystemShopware6ConfigMappings.invoiceEmailEnabled`
    * `JsonExternalSystemShopware6ConfigMappings.paymentTerm`
    * `JsonExternalSystemShopware6ConfigMappings.sw6CustomerGroup`
    * `JsonExternalSystemShopware6ConfigMappings.sw6PaymentMethod`
    * `JsonExternalSystemShopware6ConfigMappings.description`
    * `JsonExternalSystemShopware6ConfigMappings.bpartnerIdJSONPath`
    * `JsonExternalSystemShopware6ConfigMappings.bpartnerLookup`

## Workflows implemented to pull values from Shopware6

* `Order` - pulled via the search endpoint `api/search/order/`
* `Product` - pulled via the search endpoint `api/search/product`
* `Customer` - pulled via the search endpoint `api/search/customer`

# Current data mapping

---

## `Order` workflow - pulled via the search endpoint `api/search/order/`

### Shopware6 => metasfresh BPartners

we need to invoke the endpoint `api/v2/bpartner`

#### 1. BPartner - all `metasfresh-column` values refer to `C_BPartner` columns

1.1. All the data from `Order.OrderCustomer` is mapped to `JsonOrderCustomer`

1.1. `JsonCustomerGroup` - the information is pulled via `api/customer/{{JsonOrderCustomer.customerId}}/group`
1.2. `computedCustomerId` - computed from `JsonExternalSystemRequest.parameters.JSONPathMetasfreshID`, if not present it will look for `JsonExternalSystemRequest.parameters.JSONPathShopwareID`, if not present it will return `JsonOrderCustomer.customerId`

Shopware | metasfresh-column | mandatory in mf | metasfresh-json | note |
---- | ---- | ---- | ---- | ---- |
JsonOrderCustomer.FirstName + " " + JsonOrderCustomer.LastName | `Name` | N | JsonRequestBPartner.Name | |
JsonOrderCustomer.company | `CompanyName` | N | JsonRequestBPartner.companyName | |
JsonOrderCustomer.customerNumber | `Value` | N | JsonRequestBPartner.code | Code of the bPartner in question "ext-Shopware6-{{customerNumber}}" |
---- | `isCustomer` | N | JsonRequestBPartner.customer | always true |
JsonExternalSystemShopware6ConfigMappings.mappings.bPartnerSyncAdvice | ---- | Y | JsonRequestBPartner.syncAdvise | |
JsonExternalSystemRequest.orgCode | `ad_org_id` | N | JsonRequestComposite.orgCode | orgId computed from orgCode |
JsonOrderCustomer.customerId | `C_BPartner_ID` | Y | JsonRequestBPartnerUpsertItem.bpartnerIdentifier | `computedCustomerId` |
---- | ---- | N | JsonRequestBPartnerUpsert.syncAdvise | JsonExternalSystemShopware6ConfigMappings.bpartnerSyncAdvice if available, else CREATE_OR_MERGE |
JsonExternalSystemRequest.parameters.TargetPriceListId | `M_PricingSystem_ID` | N | JsonRequestBPartner.priceListId | `M_PricingSystem_ID` is computed from the given `JsonRequestBPartner.priceListId` as `M_PriceList.M_PricingSystem_ID` |
---- | `C_BP_Group_ID` | N | JsonRequestBPartner.group | JsonCustomerGroup.name (see 1.1). The system looks for an existing `C_BP_Group` having `C_BP_Group.Name` the same as `JsonRequestBPartner.group` otherwise a new `C_BP_Group` is created. Then the `C_BP_Group_ID` is set on `C_BPartner`|
JsonOrderCustomer.vatIds | `VATaxID` | N | JsonRequestBPartner.vatId | |

---

#### 2. Contact - all `metasfresh-column` values refer to `AD_User` columns

Note that an order-contact is not send to metasfresh in every case (depends on config)

| Shopware                                                                      | metasfresh-column         | mandatory in mf | metasfresh-json                                | note                                                                   |
|-------------------------------------------------------------------------------|---------------------------|-----------------|------------------------------------------------|------------------------------------------------------------------------|
| JsonOrderCustomer.firstName                                                               | `firstName`               | N               | JsonRequestContact.firstName                   |                                                                        |
| JsonOrderCustomer.lastName                                                                | `lastName`                | N               | JsonRequestContact.lastName                    |                                                                        |
| JsonOrderCustomer.email                                                                   | `email`                   | N               | JsonRequestContact.email                       |                                                                        |
| ----                                                                          | `isbilltocontact_default` | Y               | JsonRequestContact.billToDefault               | always true                                                            | 
| ----                                                                          | `isshiptocontact_default` | Y               | JsonRequestContact.shipToDefault               | always true                                                            | 
| JsonExternalSystemShopware6ConfigMappings.mappings.invoiceEmailEnabled        | `IsInvoiceEmailEnabled`   | N               | JsonRequestContact.invoiceEmailEnabled         |                                                                        |
| JsonExternalSystemShopware6ConfigMappings.mappings.bPartnerLocationSyncAdvice | ----                      | N               | JsonRequestContactUpsert.syncAdvise            |                                                                        |
| JsonOrderCustomer.customerId                                                | `AD_User_ID`              | Y               | JsonRequestContactUpsertItem.contactIdentifier | computed from `JsonExternalSystemRequest.parameters.JSONPathShopwareID` or else from `JsonOrderCustomer.CustomerId`|

---

#### 3. BPartnerLocation

3.1. For *delivery*, the information is pulled via the deliveries endpoint `api/order/{{Order.id}}/deliveries`

* if more than one delivery is returned, the address is pulled from the last one, and a warning message is sent in metas
* note: path to shipping address: `data/shippingOrderAddress`

3.2. For *billing*, the information is pulled from the endpoint: `api/order-address/{{Order.billingAddressId}}` using the `Order.billingAddressId`

3.3 `AddressDetail` - computed for both delivery and shipping address and mapped to metas POJOs

* `JsonAddress` mapping for a shopware address (billing or shipping)
* `customShopwareId` shopware identifier found at given path `JsonExternalSystemRequest.parameters.JSONPathConstantBPartnerLocationID`
* `customMetasfreshId` metasfresh id found at given path `/customFields/metasfreshLocationId`
* `customEmail` custom email of the shopware address computed from `JsonExternalSystemRequest.parameters.JSONPathEmail`

3.4 `JsonSalutation` - the information is pulled via the salutation endpoint `api/v3/salutation`

3.5 `BPartnerName` - computed in the following way:

```
JsonOrderAddress.company 
JsonOrderAddress.department
JsonSalutation.JsonSalutationItem.displayName JsonOrderAddress.title JsonOrderAddress.firstName JsonOrderAddress.lastName
```

where JsonSalutation.JsonSalutationItem is the record with JsonSalutationItem.id = JsonOrderAddress.salutationId pulled via the salutation endpoint (see 3.4)

Shopware | metasfresh-column | mandatory in mf | metasfresh-json | note |
---- | ---- | ---- | ---- | ---- |
JsonAddress.countryId |` c_location.c_country_id` | Y | JsonRequestLocation.countryCode | return country isoCode for countryId |
JsonAddress.street | `c_location.address1` | N | JsonRequestLocation.address1 | |
JsonAddress.additionalAddressLine1 | `c_location.address2` | N | JsonRequestLocation.address2 | |
JsonAddress.additionalAddressLine2 | `c_location.address3` | N | JsonRequestLocation.address3 | |
JsonAddress.city |` c_location.c_city_id` | N | JsonRequestLocation.city | |
JsonAddress.zipcode | `c_location.postal` | N | JsonRequestLocation.postal | |
--- | `C_BPartner_Location.isshipto` | N | JsonRequestLocation.shipTo | true when Order.billingAddressId is not equal to shippingAddress.JsonAddress.id |
--- | `C_BPartner_Location.isbillto` | N | JsonRequestLocation.billTo | false when Order.billingAddressId is not equal to shippingAddress.JsonAddress.id |
BPartnerName | `C_BPartner_Location.BPartnerName` | N | JsonRequestLocation.bpartnerName | see 3.5 |
JsonAddress.phoneNumber | `C_BPartner_Location.phone` | N | JsonRequestLocation.phone | |
AddressDetail.customEmail | `C_BPartner_Location.email` | N | JsonRequestLocation.email | `customEmail` |
--- | `C_BPartner_Location.C_BPartner_Location_ID` | Y | JsonRequestLocationUpsertItem.locationIdentifier | `ext-Shopware6-{{locationIdentifier}}-{{suffix}}` where `suffix` can be [`-billTo`, `-shipTo`]; `locationIdentifier` is computed from `customMetasfreshId` if not present `customShopwareId` is returned, otherwise `computedCustomerId` |
JsonExternalSystemShopware6ConfigMappings.mappings.bPartnerLocationSyncAdvice | ---- | Y | JsonRequestLocationUpsert.syncAdvise | |

### Shopware6 => metasfresh orderCandidate

we need to invoke the endpoint `api/v2/orders/sales/candidates/bulk`

#### 1. OrderCandidate - all `metasfresh-column` values refer to `C_OLCand` columns

1.1 `PaymentMethodType` - used to check if order is ready for import, otherwise the order is skipped. 
Based on `PaymentMethodType`, order transaction must be:
* `debit_payment` - `SEPA` -> `open` or in `progress` (`debit-payments` are automatically set to "inProgress" in the shop)
* `pre_payment` - `Vorkasse` -> `open`
* `invoice_payment` - `Rechnung` -> `open`
* `pay_pal_payment_handler` - `PayPal` -> `paid`
* `a_c_d_c_handler` - `Kredit- oder Debitkarte` -> `open` or in `progress`
* the following types will always result in skipping the order
  * `pay_pal_pui_payment_handler` - `Rechnungskauf Paypal`
  * `cash_payment` - `Nachnahme`

Shopware | metasfresh-column        | mandatory in mf | metasfresh-json | note |
---- |--------------------------| ---- | ---- | ---- |
JsonExternalSystemRequest.orgCode | `ad_org_id`              | Y | JsonOLCandCreateRequest.orgCode | |
Order.OrderCustomer.currencyId | `c_currency_id`          | Y | JsonOLCandCreateRequest.currencyCode | returns currency isoCode  by currencyId, shall come from pricingSystem/priceList|
Order.OrderCustomer.id | `externalHeaderId`       | Y | JsonOLCandCreateRequest.externalHeaderId | |
Order.OrderCustomer.orderNumber | `poreference`            | Y | JsonOLCandCreateRequest.poReference | |
JsonAddress.email | `email`                  | Y | JsonOLCandCreateRequest.email | |
JsonAddress.phoneNumber | `phone`                  | N | JsonRequestLocation.phone | |
---- | `C_BPartner_ID`          | Y | JsonOLCandCreateRequest.bpartner | bpartner details computed from shippingBPartnerLocationExternalId based on address customId for a bpartner location |
---- | `Bill_BPartner_ID`       | Y | JsonOLCandCreateRequest.billBPartner | bpartner details computed from billingBPLocationExternalId based on address customId from `JsonExternalSystemRequest.parameters.JSONPathConstantBPartnerLocationID` |
Order.OrderCustomer.orderDate | `DateOrdered`            | N | JsonOLCandCreateRequest.dateOrdered | |
---- | `datePromised`           | Y | JsonOLCandCreateRequest.dateRequired | computed based on `Order.OrderCustomer.Id` last delivered date to a bpartner location |
Order.OrderCustomer.updatedAt | `dateCandidate`          | N | JsonOLCandCreateRequest.dateCandidate | if Order.OrderCustomer.updatedAt is null it will be populated from Order.OrderCustomer.createdAt |
---- | `ad_inputdatasource_id`  | Y | JsonOLCandCreateRequest.dataSource | default value `int-Shopware`, identifier for AD_InputDataSource record|
---- | `isManualPrice`          | N | JsonOLCandCreateRequest.isManualPrice | default value `true` |
---- | `isImportedWithIssues`   | N | JsonOLCandCreateRequest.isImportedWithIssues | default value `true` |
---- | `Discount`               | N | JsonOLCandCreateRequest.discount | default value `0` |
---- | `DeliveryViaRule`        | N | JsonOLCandCreateRequest.deliveryViaRule | default value `S` |
---- | `DeliveryRule`           | N | JsonOLCandCreateRequest.deliveryRule | default value `A` |
---- | `importWarningMessage`   | N | JsonOLCandCreateRequest.importWarningMessage | set based on isMultipleShippingAddresses, if there are multiple shipping addresses for`Order.OrderCustomer.Id` for a bpartner location, if true the default value is set `MULTIPLE_SHIPPING_ADDRESSES_WARN_MESSAGE`, otherwise is `null` |
---- | `m_shipper_id`           | N | JsonOLCandCreateRequest.shipper | shippingMethodId computed from delivery address for `Order.OrderCustomer.Id` for a bpartner location |
Order.salesRepId | `C_BPartner_SalesRep_ID` | N | JsonOLCandCreateRequest.salesPartner | based on `JsonExternalSystemRequest.parameters.JSONPathConstantSalesRepID` it provides a JsonSalesPartner with salesPartnerCode from Order.salesRepId |
JsonExternalSystemShopware6ConfigMappings.mappings.docTypeOrder | `c_doctypeorder_id`      | Y | JsonOLCandCreateRequest.orderDocType | sets the JsonOrderDocType from docTypeOrder code |
JsonExternalSystemShopware6ConfigMappings.mappings.paymentRule | `paymentrule`            | Y | JsonOLCandCreateRequest.paymentRule | sets the JSONPaymentRule from paymentRule code |
JsonExternalSystemShopware6ConfigMappings.mappings.paymentTermValue | `c_paymentterm_id`       | N | JsonOLCandCreateRequest.paymentTerm | is paymentTermValue is defined then value "val-{{paymentTermValue}} is set" |

#### 2. JsonOrderLine

* represents a shopware order line, pulled via `api/order/{Order.Id}/line-items`
* all `metasfresh-column` values refer to `C_OLCand` columns

Shopware | metasfresh-column | mandatory in mf | metasfresh-json | note |
---- | ---- | ---- | ---- | ---- |
JsonOrderLine.id |  `externalLineId` | Y | JsonOLCandCreateRequest.externalLineId |  |
JsonOrderLine.productId |  `M_Product_ID` | Y | JsonOLCandCreateRequest.productIdentifier | "ext-Shopware6-{{productId}}" |
JsonOrderLine.unitPrice |  `m_productprice_id` | N | JsonOLCandCreateRequest.price | ..if the JsonOrderLine is a bundle-main-item, then use const `ZERO` as price |
JsonOrderLine.quantity |  `qtyentered` | Y | JsonOLCandCreateRequest.qty | |
JsonOrderLine.description |  `description` | N | JsonOLCandCreateRequest.description | |
JsonOrderLine.position |  `line` | N | JsonOLCandCreateRequest.line | |
JsonOrderLine.JsonOrderLinePayload.isBundle |  `compensationgroupkey`,  `isgroupcompensationline` | N | JsonOLCandCreateRequest.orderLineGroup | it is set only if JsonOrderLine.JsonOrderLinePayload is defined and JsonOrderLine.JsonOrderLinePayload.isBundle is true|

#### 3. JsonTax

    * shopware resource found at path: `delivery/shippingCosts/calculatedTaxes`
    * all `metasfresh-column` values refer to `C_OLCand` columns

* TaxProductIdProvider
    * maps the VAT rate for a product, reduced and normal VAT rates
    * computed from variables set on window `541116 - ExternalSystem_Config_Shopware6`
    * parameters: `FreightCost_Reduced_VAT_Rates`, `M_FreightCost_ReducedVAT_Product_ID`, `FreightCost_NormalVAT_Rates`, `M_FreightCost_NormalVAT_Product_ID`

Shopware | metasfresh-column | mandatory in mf | metasfresh-json | note |
---- | ---- | ---- | ---- | ---- |
JsonTax.taxRate |  `externalLineId` | Y | JsonOLCandCreateRequest.externalLineId | "TaxRate_-{{taxRate}}" |
---- |  `line` | N | JsonOLCandCreateRequest.line | default `null` |
---- | ---- | N | JsonOLCandCreateRequest.orderLineGroup | default `null` |
---- |  `description` | N | JsonOLCandCreateRequest.description | default `null` |
---- |  `M_Product_ID` | N | JsonOLCandCreateRequest.productIdentifier | Identifier of the product in question and the given VAT rate|
JsonTax.price |  `m_productprice_id` | N | JsonOLCandCreateRequest.price | |
---- | `qtyentered` | Y | JsonOLCandCreateRequest.qty | default value `1` |

### Shopware6 => metasfresh payment

we need to invoke the endpoint `api/v2/orders/sales/payment`

PaymentRequestProcessor

#### 1. `JsonOrderTransaction` - OrderPayment

* pulled via endpoint: `api/order/{{Order.Id}}/transactions`
* all `metasfresh-column` values refer to `C_Payment` columns except from `C_Order_ID.c_payment_id`

Shopware | metasfresh-column | mandatory in mf | metasfresh-json | note |
---- | ---- | ---- | ---- | ---- |
JsonExternalSystemRequest.orgCode |  `ad_org_id` | Y | JsonOrderPaymentCreateRequest.orgCode | |
JsonOrderTransaction.id |  `externalid` | Y | JsonOrderPaymentCreateRequest.externalPaymentId | transaction id that has ben done for `Order.OrderCustomer.Id` |
Order.effectiveCustomerId |  `c_bpartner_id` | Y | JsonOrderPaymentCreateRequest.bpartnerIdentifier | |
JsonOrderTransaction.totalPrice |  `payamt` | Y | JsonOrderPaymentCreateRequest.amount | |
Order.OrderCustomer.currencyId |  `c_currency_id` | Y | JsonOrderPaymentCreateRequest.currencyCode | returns currency isoCode  by currencyId, from CurrencyInfoProvider|
Order.OrderCustomer.id | `C_Order_ID.c_payment_id` | Y | JsonOrderPaymentCreateRequest.orderIdentifier | |
JsonOrderTransaction.createdAt |  `datetrx` | N | JsonOrderPaymentCreateRequest.transactionDate | |

---

## `Product` workflow - pulled via the search endpoint `api/search/product`

### Shopware6 => metasfresh product

we need to invoke the endpoint `api/v2/products`

1. Product - all `metasfresh-column` values refer to `M_Product` columns

Shopware | metasfresh-column | mandatory in mf | metasfresh-json | note |
---- | ---- | ---- | ---- | ---- |
JsonProduct.productNumber | `value` | Y | JsonRequestProduct.code | |
JsonProduct.ean | `upc` | N | JsonRequestProduct.ean | |
JsonProduct.name | `name` | Y | JsonRequestProduct.name | |
JsonProduct.unitId | `c_uom_id` | Y | JsonRequestProduct.uomCode | set based on UOMMappings param, default UOM is PCE, if no JsonUOMMappings.JsonUOMMapping.JsonUOM.code is found for the shopware code |
---- | `producttype` | Y | JsonRequestProduct.Type.ITEM | |
--- | ---- | N | JsonRequestProductUpsert.syncAdvise | default value CREATE_OR_MERGE |

2. Price - all `metasfresh-column` values refer to `M_ProductPrice` columns

we need to invoke the endpoint '/api/v2/prices'

Shopware | metasfresh-column | mandatory in mf | metasfresh-json | note |
---- | ---- | ---- | ---- | ---- |
JsonProduct.id | `m_product_id` | Y | JsonRequestProductPrice.productIdentifier | "ext-Shopware6-{{productId}}" |
JsonExternalSystemRequest.orgCode | `ad_org_id` | Y | JsonRequestProductPrice.orgCode | |
JsonProduct.tax.taxRate | `c_taxcategory_id` | Y | JsonRequestProductPrice.taxCategory | set based on NormalVAT_Rates and Reduced_VAT_Rates params |
---- | `m_pricelist_version_id` | Y | UpsertProductPriceList.priceListIdentifier | set based on JsonExternalSystemRequest.parameters.TargetPriceListId, always set to the newest active price list version |
---- | `pricestd` | Y | JsonRequestProductPrice.priceStd | 0 if: <br /> - JsonExternalSystemRequest.parameters<br />.PriceListCurrencyCode is not equal to JsonCurrency.isoCode of JsonProduct.JsonPrice.currencyId, <br /><br /> - JsonProduct.JsonPrice is null or empty, <br /><br /> - JsonExternalSystemRequest.parameters.<br />PriceList_IsTaxIncluded is true and JsonProduct.JsonPrice.gross is null, <br /><br /> - JsonExternalSystemRequest.parameters.<br />PriceList_IsTaxIncluded is false and JsonProduct.JsonPrice.net is null|
JsonProduct.price.net | `pricestd` | Y | JsonRequestProductPrice.priceStd | if JsonExternalSystemRequest.parameters.PriceList_IsTaxIncluded is false |
JsonProduct.price.gross | `pricestd` | Y | JsonRequestProductPrice.priceStd | if JsonExternalSystemRequest.parameters.PriceList_IsTaxIncluded is true |
JsonProduct.id | ---- | Y | JsonRequestProductPriceUpsertItem.productPriceIdentifier | "ext-Shopware6-{{productId}}" |
--- | ---- | N | JsonRequestProductPriceUpsert.syncAdvise | default value CREATE_OR_MERGE |`

## `Customer` workflow - pulled via the search endpoint `api/search/customer`

#### Shopware6 => metasfresh BPartners

we need to invoke the endpoint `api/v2/bpartner`

#### 1. BPartner - all `metasfresh-column` values refer to `C_BPartner` columns

1.1. `JsonCustomerGroup` - the information is pulled via `api/customer-group/{{JsonCustomer.groupId}}`

1.2. `computedId` - computed from `JsonExternalSystemRequest.parameters.JSONPathMetasfreshID`, if not present it will look for `JsonExternalSystemRequest.parameters.JSONPathShopwareID`, if not present it will return `JsonCustomer.id`

Shopware | metasfresh-column | mandatory in mf | metasfresh-json | note |
---- | ---- | ---- | ---- | ---- |
JsonCustomer.FirstName + " " + JsonCustomer.LastName | `Name` | N | JsonRequestBPartner.Name | |
JsonCustomer.company | `CompanyName` | N | JsonRequestBPartner.companyName | |
JsonCustomer.customerNumber | `Value` | N | JsonRequestBPartner.code | Code of the bPartner in question "ext-Shopware6-{{customerNumber}}" |
---- | `isCustomer` | N | JsonRequestBPartner.customer | `true` |
JsonExternalSystemShopware6ConfigMappings.mappings.bPartnerSyncAdvice | ---- | Y | JsonRequestBPartner.syncAdvise | |
JsonExternalSystemRequest.orgCode | `ad_org_id` | N | JsonRequestComposite.orgCode | orgId computed from orgCode |
JsonCustomer.id | `C_BPartner_ID` | Y | JsonRequestBPartnerUpsertItem.bpartnerIdentifier | `computedId` |
---- | ---- | N | JsonRequestBPartnerUpsert.syncAdvise | JsonExternalSystemShopware6ConfigMappings.bpartnerSyncAdvice if available, else `CREATE_OR_MERGE` |
JsonExternalSystemRequest.parameters.TargetPriceListId | `M_PricingSystem_ID` | N | JsonRequestBPartner.priceListId | `M_PricingSystem_ID` is computed from the given `JsonRequestBPartner.priceListId` as `M_PriceList.M_PricingSystem_ID` |
---- | `C_BP_Group_ID` | N | JsonRequestBPartner.group | JsonCustomerGroup.name (see 1.1). The system looks for an existing `C_BP_Group` having `C_BP_Group.Name` the same as `JsonRequestBPartner.group` otherwise a new `C_BP_Group` is created. Then the `C_BP_Group_ID` is set on `C_BPartner`|
JsonCustomer.vatIds | `VATaxID` | N | JsonRequestBPartner.vatId | |

#### 2. Contact - all `metasfresh-column` values refer to `AD_User` columns

| Shopware | metasfresh-column | mandatory in mf | metasfresh-json | note |
| ---- | ---- | ---- | ---- | ---- |
| JsonCustomer.firstName | `firstName` | N | JsonRequestContact.firstName | |
| JsonCustomer.lastName  | `lastName` | N | JsonRequestContact.lastName | |
| JsonCustomer.email | `email` | N | JsonRequestContact.email | |
| ---- | `IsBillToContact_Default` | Y | JsonRequestContact.billToDefault | always true | 
| ---- | `IsShipToContact_Default` | Y | JsonRequestContact.shipToDefault | always true | 
| JsonExternalSystemShopware6ConfigMappings.mappings.invoiceEmailEnabled | `IsInvoiceEmailEnabled` | N | JsonRequestContact.invoiceEmailEnabled | |
| JsonExternalSystemShopware6ConfigMappings.mappings.bPartnerLocationSyncAdvice | ---- | N | JsonRequestContactUpsert.syncAdvise | |
| JsonCustomer.id | `AD_User_ID` | Y | JsonRequestContactUpsertItem.contactIdentifier | computed from `JsonExternalSystemRequest.parameters.JSONPathShopwareID` order else `JsonCustomer.id` |

#### 3. BPartnerLocation

3.1. *shipping* address details for customer are pulled from endpoint `/api/customer-address/{{JsonCustomer.defaultShippingAddressId}}` and mapped to `AddressDetail`

3.2. *billing* address details for customer are pulled from endpoint `/api/customer-address/{{JsonCustomer.defaultBillingAddressId}}` and mapped to `AddressDetail`

Shopware | metasfresh-column | mandatory in mf | metasfresh-json | note |
---- | ---- | ---- | ---- | ---- |
AddressDetail.countryId |` c_location.c_country_id` | Y | JsonRequestLocation.countryCode | return country isoCode for countryId |
AddressDetail.street | `c_location.address1` | N | JsonRequestLocation.address1 | |
AddressDetail.additionalAddressLine1 | `c_location.address2` | N | JsonRequestLocation.address2 | |
AddressDetail.additionalAddressLine2 | `c_location.address3` | N | JsonRequestLocation.address3 | |
AddressDetail.city |` c_location.c_city_id` | N | JsonRequestLocation.city | |
AddressDetail.zipcode | `c_location.postal` | N | JsonRequestLocation.postal | |
--- | `C_BPartner_Location.isshipto` | N | JsonRequestLocation.shipTo | `true` when `JsonCustomer.defaultBillingAddressId` is equal to `JsonCustomer.defaultShippingAddressId` |
--- | `C_BPartner_Location.isbillto` | N | JsonRequestLocation.billTo | `false` when `JsonCustomer.defaultBillingAddressId` is not equal to `JsonCustomer.defaultShippingAddressId` |
--- | `C_BPartner_Location.IsShipToDefault` | N | JsonRequestLocation.shipToDefault | `true` when `JsonCustomer.defaultBillingAddressId` is equal to `JsonCustomer.defaultShippingAddressId` |
--- | `C_BPartner_Location.IsBillToDefault` | N | JsonRequestLocation.billToDefault | `false` when `JsonCustomer.defaultBillingAddressId` is not equal to `JsonCustomer.defaultShippingAddressId` |
BPartnerName | `C_BPartner_Location.BPartnerName` | N | JsonRequestLocation.bpartnerName | String concatenation of `JsonCustomer.company` + `JsonCustomer.firstName` + `JsonCustomer.lastName` |
AddressDetail.phoneNumber | `C_BPartner_Location.phone` | N | JsonRequestLocation.phone | |
AddressDetail.customEmail | `C_BPartner_Location.email` | N | JsonRequestLocation.email | |
--- | `C_BPartner_Location.C_BPartner_Location_ID` | Y | JsonRequestLocationUpsertItem.locationIdentifier | `ext-Shopware6-{{locationIdentifier}}-{{suffix}}` where `suffix` can be [`-billTo`, `-shipTo`]; `locationIdentifier = computedId`
JsonExternalSystemShopware6ConfigMappings.mappings.bPartnerLocationSyncAdvice | ---- | Y | JsonRequestLocationUpsert.syncAdvise | |

## Workflows for pushing data to Shopware6

* `Stock` - pushed via the endpoint `api/product/{id}`

Pushed if the following parameter is set on window `541116 - ExternalSystem_Config_Shopware6`

* `IsSyncAvailableForSalesToShopware6`
  * If checked, the current planned quantity available for sales is automatically sent to Shopware6.

Client can set the percentage that is subtracted from the actual available for sales before it is transferred to Shopware,
by setting the following parameter on the same window, by default it is 0:

* `PercentageOfAvailableForSalesToSync`

Shopware | metasfresh-column |
---- | ---- |
stock | *availableForSales|

* `availableForSales = (MD_Available_For_Sales.QtyOnHandStock - MD_Available_For_Sales.QtyToBeShipped) * 
(100 - ExternalSystem_Config_Shopware6.PercentageOfAvailableForSalesToSync) / 100`
  * `MD_Available_For_Sales` counts for all records found for the exported product within the config's organisation
