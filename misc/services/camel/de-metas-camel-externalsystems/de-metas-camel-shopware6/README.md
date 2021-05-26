## Current data mapping
****
Values computed in Metasfresh 
* `Order` - computed from the following parameters set on window `541116 - ExternalSystem_Config_Shopware6` 
  * `JsonExternalSystemRequest.parameters.JSONPathConstantBPartnerID`
  * `JsonExternalSystemRequest.parameters.JSONPathConstantSalesRepID`
  * `JsonExternalSystemRequest.parameters.ClientId`  
  * `JsonExternalSystemRequest.parameters.ClientSecret`  
  * `JsonExternalSystemRequest.parameters.BasePath`  
  * `JsonExternalSystemRequest.parameters.UpdatedAfterOverride`  
  * `JsonExternalSystemRequest.parameters.UpdatedAfter`  
* `JsonExternalSystemShopware6ConfigMappings` - computed from mappings set on window `541116 - ExternalSystem_Config_Shopware6` send on `JsonExternalSystemRequest.parameters.ConfigMappings`


**Shopware6 => metasfresh BPartners**

we need to invoke the endpoint `api/v2-pre/bpartner`

1. BPartner - all columns are from `C_BPartner`

Shopware | metasfresh-column | mandatory in mf | metasfresh-json | note |
---- | ---- | ---- | ---- | ---- |
Order.OrderCustomer.FirstName + " " + Order.OrderCustomer.LastName | `Name` | N | JsonRequestBPartner.Name | |
Order.OrderCustomer.company | `CompanyName` | N | JsonRequestBPartner.companyName | |
Order.OrderCustomer.customerNumber | `Value` | N | JsonRequestBPartner.code | Code of the bPartner in question "ext-Shopware6-{{customerNumber}}" |
---- | `isCustomer` | N | JsonRequestBPartner.customer | always true |
JsonExternalSystemShopware6ConfigMappings.mappings.bPartnerSyncAdvice | ---- | Y | JsonRequestBPartner.syncAdvise | |
JsonExternalSystemRequest.orgCode | `ad_org_id` | N | JsonRequestComposite.orgCode | orgId computed from orgCode |
Order.effectiveCustomerId | `C_BPartner_ID` | Y | JsonRequestBPartnerUpsertItem.bpartnerIdentifier | computed from Order.OrderCustomer.CustomerId or Order.customBPartnerId |
--- | ---- | N | JsonRequestBPartnerUpsert.syncAdvise | default value CREATE_OR_MERGE |

2. Contact - all columns are from `AD_User`

Shopware | metasfresh-column | mandatory in mf | metasfresh-json | note |
---- | ---- | ---- | ---- | ---- |
Order.firstName | `firstName` | N | JsonRequestContact.firstName | |
Order.lastName | `lastName` | N | JsonRequestContact.lastName | |
Order.email | `email` | N | JsonRequestContact.email | |
JsonExternalSystemShopware6ConfigMappings.mappings.bPartnerSyncAdvice | ---- | N | JsonRequestContact.syncAdvise | |
JsonExternalSystemShopware6ConfigMappings.mappings.bPartnerSyncAdvice | ---- | N | JsonRequestContactUpsert.syncAdvise | |
Order.effectiveCustomerId | `AD_User_ID` | N | JsonRequestContactUpsertItem.contactIdentifier | computed from Order.OrderCustomer.CustomerId or Order.customBPartnerId|

3. BPartnerLocation

* `JsonOrderAddressAndCustomId`
    * computed on Metasfresh based on `Order.OrderCustomer.Id` and `JsonExternalSystemRequest.parameters.JSONPathConstantBPartnerID`
    * `JsonOrderAddress` mapping for a delivery Address from the last order with `Order.OrderCustomer.Id` for a specific bpartner
    * `customId` for a specific bpartner location where an order was delivered

Shopware | metasfresh-column | mandatory in mf | metasfresh-json | note |
---- | ---- | ---- | ---- | ---- |
JsonOrderAddress.countryId |` c_location.c_country_id` | Y | JsonRequestLocation.countryCode | return country isoCode for countryId |
JsonOrderAddress.street | `c_location.address1` | N | JsonRequestLocation.address1 | |
JsonOrderAddress.additionalAddressLine1 | `c_location.address2` | N | JsonRequestLocation.address2 | |
JsonOrderAddress.city |` c_location.c_city_id` | N | JsonRequestLocation.city | |
JsonOrderAddress.zipcode | `c_location.postal` | N | JsonRequestLocation.postal | |
--- | `C_BPartner_Location.isshipto` | N | JsonRequestLocation.shipTo | true when Order.billingAddressId is not equal to shippingAddress.JsonOrderAddress.id |
--- | `C_BPartner_Location.isbillto` | N | JsonRequestLocation.billTo | false when Order.billingAddressId is not equal to shippingAddress.JsonOrderAddress.id |
Order.OrderCustomer.CustomerId | `C_BPartner_Location.gln` | Y | JsonRequestLocationUpsertItem.locationIdentifier | "ext-Shopware6-{{customerNumber}}-{{suffix}}" where suffix is JsonOrderAddressAndCustomId.customId or computed|
JsonExternalSystemShopware6ConfigMappings.mappings.bPartnerLocationSyncAdvice | ---- | Y | JsonRequestLocationUpsert.syncAdvise | |

***
**Shopware6 => metasfresh orderCandidate**

we need to invoke the endpoint `api/v2-pre/orders/sales/candidates/bulk`

OLCandRequestProcessor - JsonOLCandCreateBulkRequest

1. OrderCandidate - all columns are from `C_OLCand`

Shopware | metasfresh-column | mandatory in mf | metasfresh-json | note |
---- | ---- | ---- | ---- | ---- |
JsonExternalSystemRequest.orgCode | `ad_org_id` | Y | JsonOLCandCreateRequest.orgCode | |
Order.OrderCustomer.currencyId | `c_currency_id` | Y | JsonOLCandCreateRequest.currencyCode | returns currency isoCode  by currencyId, shall come from pricingSystem/priceList|
Order.OrderCustomer.id | `externalHeaderId` | Y | JsonOLCandCreateRequest.externalHeaderId | |
Order.OrderCustomer.orderNumber |  `poreference` | Y | JsonOLCandCreateRequest.poReference | |
---- |  `C_BPartner_ID` | Y | JsonOLCandCreateRequest.bpartner | bpartner details computed from shippingBPartnerLocationExternalId based on address customId for a bpartner location |
---- |  `Bill_BPartner_ID` | Y | JsonOLCandCreateRequest.billBPartner | bpartner details computed from billingBPLocationExternalId based on address customId for a bpartner location |
Order.OrderCustomer.orderDate |  `DateOrdered` | N | JsonOLCandCreateRequest.dateOrdered | |
---- |  `datePromised` | Y | JsonOLCandCreateRequest.dateRequired | computed based on `Order.OrderCustomer.Id` last delivered date to a bpartner location |
Order.OrderCustomer.updatedAt |  `dateCandidate` | N | JsonOLCandCreateRequest.dateCandidate | if Order.OrderCustomer.updatedAt is null it will be populated from Order.OrderCustomer.createdAt |
---- |  `ad_inputdatasource_id` | Y | JsonOLCandCreateRequest.dataSource | default value `int-Shopware`, identifier for AD_InputDataSource record|
---- |  `isManualPrice` | N | JsonOLCandCreateRequest.isManualPrice | default value `true` |
---- |  `isImportedWithIssues` | N | JsonOLCandCreateRequest.isImportedWithIssues | default value `true` |
---- |  `Discount` | N | JsonOLCandCreateRequest.discount | default value `0` |
---- |  `DeliveryViaRule` | N | JsonOLCandCreateRequest.deliveryViaRule | default value `S` |
---- |  `DeliveryRule` | N | JsonOLCandCreateRequest.deliveryRule | default value `A` |
---- |  `importWarningMessage` | N | JsonOLCandCreateRequest.importWarningMessage | set based on isMultipleShippingAddresses, if there are multiple shipping addresses for`Order.OrderCustomer.Id` for a bpartner location, if true the default value is set `MULTIPLE_SHIPPING_ADDRESSES_WARN_MESSAGE`, otherwise is `null` |
---- |  `m_shipper_id` | N | JsonOLCandCreateRequest.shipper | shippingMethodId computed from delivery address for `Order.OrderCustomer.Id` for a bpartner location |
Order.salesRepId | ---- | N | JsonOLCandCreateRequest.salesPartner | based on `JsonExternalSystemRequest.parameters.JSONPathConstantSalesRepID` it provides a JsonSalesPartner with salesPartnerCode from Order.salesRepId |
JsonExternalSystemShopware6ConfigMappings.mappings.docTypeOrder |  `c_doctypeorder_id` | Y | JsonOLCandCreateRequest.orderDocType | sets the JsonOrderDocType from docTypeOrder code |
JsonExternalSystemShopware6ConfigMappings.mappings.paymentRule |  `paymentrule` | Y | JsonOLCandCreateRequest.paymentRule | sets the JSONPaymentRule from paymentRule code |
JsonExternalSystemShopware6ConfigMappings.mappings.paymentTermValue |  `c_paymentterm_id` | N | JsonOLCandCreateRequest.paymentTerm | is paymentTermValue is defined then value "val-{{paymentTermValue}} is set" |

2. JsonOrderLine - all columns are from `C_OLCand`

* `JsonOrderLine` - computed on metasfresh, represents each line from an order identified by `Order.OrderCustomer.Id` for a shopwareClient identified by `JsonExternalSystemRequest.parameters.ClientId`, `JsonExternalSystemRequest.parameters.ClientSecret`, `JsonExternalSystemRequest.parameters.BasePath`

Shopware | metasfresh-column | mandatory in mf | metasfresh-json | note |
---- | ---- | ---- | ---- | ---- |
JsonOrderLine.id |  `externalLineId` | Y | JsonOLCandCreateRequest.externalLineId |  |
JsonOrderLine.productId |  `M_Product_ID` | Y | JsonOLCandCreateRequest.productIdentifier | "ext-Shopware6-{{productId}}" |
JsonOrderLine.unitPrice |  `m_productprice_id` | N | JsonOLCandCreateRequest.price | |
JsonOrderLine.quantity |  `qtyentered` | Y | JsonOLCandCreateRequest.qty | |
JsonOrderLine.description |  `description` | N | JsonOLCandCreateRequest.description | |
JsonOrderLine.position |  `line` | N | JsonOLCandCreateRequest.line | |
JsonOrderLine.JsonOrderLinePayload.isBundle |  `compensationgroupkey`,  `isgroupcompensationline` | N | JsonOLCandCreateRequest.orderLineGroup | it is set only if JsonOrderLine.JsonOrderLinePayload is defined and JsonOrderLine.JsonOrderLinePayload.isBundle is true|

3. JsonTax - all columns are from `C_OLCand`

* TaxProductIdProvider 
    * maps the VAT rate for a product, reduced and normal VAT rates
    * computed from variables set on window `541116 - ExternalSystem_Config_Shopware6`
    * parameters: `FreightCost_Reduced_VAT_Rates`, `M_FreightCost_ReducedVAT_Product_ID`, `FreightCost_NormalVAT_Rates`, `M_FreightCost_NormalVAT_Product_ID`

* `JsonTax` 
  * represents the `calculatedTaxes` computed from shipping cost from delivery Address from the last order with `Order.OrderCustomer.Id` for a specific bpartner

Shopware | metasfresh-column | mandatory in mf | metasfresh-json | note |
---- | ---- | ---- | ---- | ---- |
JsonTax.taxRate |  `externalLineId` | Y | JsonOLCandCreateRequest.externalLineId | "TaxRate_-{{taxRate}}" |
---- |  `line` | N | JsonOLCandCreateRequest.line | default `null` |
---- | ---- | N | JsonOLCandCreateRequest.orderLineGroup | default `null` |
---- |  `description` | N | JsonOLCandCreateRequest.description | default `null` |
ImportOrdersRouteContext.taxProductIdProvider |  `M_Product_ID` | N | JsonOLCandCreateRequest.productIdentifier | get productId by the given VAT rate|
JsonTax.price |  `m_productprice_id` | N | JsonOLCandCreateRequest.price | |
---- | `qtyentered` | Y | JsonOLCandCreateRequest.qty | default value `1` |

***
**Shopware6 => metasfresh payment**

we need to invoke the endpoint `api/v2-pre/orders/sales/payment`

PaymentRequestProcessor

* `JsonOrderTransaction`
    * computed by metasfresh, represents the transaction status done for an `Order.OrderCustomer.Id` for the shopwareClient
    
1. OrderPayment -- all columns are from `C_Payment`, except for `orderIdentifier`

Shopware | metasfresh-column | mandatory in mf | metasfresh-json | note |
---- | ---- | ---- | ---- | ---- |
JsonExternalSystemRequest.orgCode |  `ad_org_id` | Y | JsonOrderPaymentCreateRequest.orgCode | |
JsonOrderTransaction.id |  `externalid` | Y | JsonOrderPaymentCreateRequest.externalPaymentId | transaction id that has ben done for `Order.OrderCustomer.Id` |
Order.effectiveCustomerId |  `c_bpartner_id` | Y | JsonOrderPaymentCreateRequest.bpartnerIdentifier | |
JsonOrderTransaction.totalPrice |  `payamt` | Y | JsonOrderPaymentCreateRequest.amount | |
Order.OrderCustomer.currencyId |  `c_currency_id` | Y | JsonOrderPaymentCreateRequest.currencyCode | returns currency isoCode  by currencyId, from CurrencyInfoProvider|
Order.OrderCustomer.id | `C_Order_ID.c_payment_id` | Y | JsonOrderPaymentCreateRequest.orderIdentifier | |
JsonOrderTransaction.createdAt |  `datetrx` | N | JsonOrderPaymentCreateRequest.transactionDate | |
