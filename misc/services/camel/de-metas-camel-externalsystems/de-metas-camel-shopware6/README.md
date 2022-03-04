## Current data mapping

****

## Values pulled from Shopware6

* `Order` - pulled via the search endpoint `api/v3/search/order/`

## Values computed in metasfresh

computed from the following parameters set on window `541116 - ExternalSystem_Config_Shopware6`

* `JsonExternalSystemRequest.parameters.ClientId`
* `JsonExternalSystemRequest.parameters.ClientSecret`
* `JsonExternalSystemRequest.parameters.BasePath`
* `JsonExternalSystemRequest.parameters.JSONPathConstantBPartnerLocationID`
* `JsonExternalSystemRequest.parameters.JSONPathEmail`
* `JsonExternalSystemRequest.parameters.JSONPathConstantSalesRepID`
* `JsonExternalSystemRequest.parameters.UpdatedAfterOverride`
* `JsonExternalSystemRequest.parameters.UpdatedAfter`
* `JsonExternalSystemRequest.parameters.M_FreightCost_NormalVAT_Product_ID`
* `JsonExternalSystemRequest.parameters.FreightCost_NormalVAT_Rates`
* `JsonExternalSystemRequest.parameters.M_FreightCost_ReducedVAT_Product_ID`
* `JsonExternalSystemRequest.parameters.FreightCost_Reduced_VAT_Rates`
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

## Shopware6 => metasfresh BPartners

we need to invoke the endpoint `api/v2/bpartner`

#### 1. BPartner - all `metasfresh-column` values refer to `C_BPartner` columns

Shopware | metasfresh-column | mandatory in mf | metasfresh-json | note |
---- | ---- | ---- | ---- | ---- |
Order.OrderCustomer.FirstName + " " + Order.OrderCustomer.LastName | `Name` | N | JsonRequestBPartner.Name | |
Order.OrderCustomer.company | `CompanyName` | N | JsonRequestBPartner.companyName | |
Order.OrderCustomer.customerNumber | `Value` | N | JsonRequestBPartner.code | Code of the bPartner in question "ext-Shopware6-{{customerNumber}}" |
---- | `isCustomer` | N | JsonRequestBPartner.customer | always true |
JsonExternalSystemShopware6ConfigMappings.mappings.bPartnerSyncAdvice | ---- | Y | JsonRequestBPartner.syncAdvise | |
JsonExternalSystemRequest.orgCode | `ad_org_id` | N | JsonRequestComposite.orgCode | orgId computed from orgCode |
Order.effectiveCustomerId | `C_BPartner_ID` | Y | JsonRequestBPartnerUpsertItem.bpartnerIdentifier | computed from Order.OrderCustomer.CustomerId or Order.customBPartnerId |
--- | ---- | N | JsonRequestBPartnerUpsert.syncAdvise | JsonExternalSystemShopware6ConfigMappings.bpartnerSyncAdvice if available, else CREATE_OR_MERGE |

---

#### 2. Contact - all `metasfresh-column` values refer to `AD_User` columns

Shopware | metasfresh-column | mandatory in mf | metasfresh-json | note |
---- | ---- | ---- | ---- | ---- |
Order.firstName | `firstName` | N | JsonRequestContact.firstName | |
Order.lastName | `lastName` | N | JsonRequestContact.lastName | |
Order.email | `email` | N | JsonRequestContact.email | |
---- | `isbilltocontact_default` | Y | JsonRequestContact.billToDefault | always true | 
---- | `isshiptocontact_default` | Y | JsonRequestContact.shipToDefault | always true | 
JsonExternalSystemShopware6ConfigMappings.mappings.invoiceEmailEnabled | `IsInvoiceEmailEnabled` | N | JsonRequestContact.invoiceEmailEnabled | |
JsonExternalSystemShopware6ConfigMappings.mappings.bPartnerLocationSyncAdvice | ---- | N | JsonRequestContactUpsert.syncAdvise | |
Order.effectiveCustomerId | `AD_User_ID` | Y | JsonRequestContactUpsertItem.contactIdentifier | computed from Order.OrderCustomer.CustomerId or Order.customBPartnerId|

---

#### 3. BPartnerLocation

3.1. For *delivery*, the information is pulled via the deliveries endpoint `api/v3/order/{{Order.id}}/deliveries`

* if more than one delivery is returned, the address is pulled from the last one, and a warning message is sent in metas
* note: path to shipping address: `data/shippingOrderAddress`

3.2. For *billing*, the information is pulled from the endpoint: `api/v3/order-address/{{Order.billingAddressId}}` using the `Order.billingAddressId`

3.3 `OrderAddressDetails` - computed for both delivery and shipping address and mapped to metas POJOs

* `JsonOrderAddress` mapping for a shopware address (billing or shipping)
* `customId` custom identifier of the shopware address
* `customEmail` custom email of the shopware address

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
JsonOrderAddress.countryId |` c_location.c_country_id` | Y | JsonRequestLocation.countryCode | return country isoCode for countryId |
JsonOrderAddress.street | `c_location.address1` | N | JsonRequestLocation.address1 | |
JsonOrderAddress.additionalAddressLine1 | `c_location.address2` | N | JsonRequestLocation.address2 | |
JsonOrderAddress.additionalAddressLine2 | `c_location.address3` | N | JsonRequestLocation.address3 | |
JsonOrderAddress.city |` c_location.c_city_id` | N | JsonRequestLocation.city | |
JsonOrderAddress.zipcode | `c_location.postal` | N | JsonRequestLocation.postal | |
--- | `C_BPartner_Location.isshipto` | N | JsonRequestLocation.shipTo | true when Order.billingAddressId is not equal to shippingAddress.JsonOrderAddress.id |
--- | `C_BPartner_Location.isbillto` | N | JsonRequestLocation.billTo | false when Order.billingAddressId is not equal to shippingAddress.JsonOrderAddress.id |
BPartnerName | `C_BPartner_Location.BPartnerName` | N | JsonRequestLocation.bpartnerName | see 3.5 |
JsonOrderAddress.phoneNumber | `C_BPartner_Location.phone` | N | JsonRequestLocation.phone | |
OrderAddressDetails.customEmail | `C_BPartner_Location.email` | N | JsonRequestLocation.email | |
Order.OrderCustomer.CustomerId | `C_BPartner_Location.C_BPartner_Location_ID` | Y | JsonRequestLocationUpsertItem.locationIdentifier | "ext-Shopware6-{{customerNumber}}-{{suffix}}" where suffix is OrderAddressDetails.customId or computed|
JsonExternalSystemShopware6ConfigMappings.mappings.bPartnerLocationSyncAdvice | ---- | Y | JsonRequestLocationUpsert.syncAdvise | |


## **Shopware6 => metasfresh orderCandidate**

we need to invoke the endpoint `api/v2/orders/sales/candidates/bulk`

#### 1. OrderCandidate - all `metasfresh-column` values refer to `C_OLCand` columns

Shopware | metasfresh-column | mandatory in mf | metasfresh-json | note |
---- | ---- | ---- | ---- | ---- |
JsonExternalSystemRequest.orgCode | `ad_org_id` | Y | JsonOLCandCreateRequest.orgCode | |
Order.OrderCustomer.currencyId | `c_currency_id` | Y | JsonOLCandCreateRequest.currencyCode | returns currency isoCode  by currencyId, shall come from pricingSystem/priceList|
Order.OrderCustomer.id | `externalHeaderId` | Y | JsonOLCandCreateRequest.externalHeaderId | |
Order.OrderCustomer.orderNumber |  `poreference` | Y | JsonOLCandCreateRequest.poReference | |
Order.OrderCustomer.email |  `email` | Y | JsonOLCandCreateRequest.email | |
---- |  `C_BPartner_ID` | Y | JsonOLCandCreateRequest.bpartner | bpartner details computed from shippingBPartnerLocationExternalId based on address customId for a bpartner location |
---- |  `Bill_BPartner_ID` | Y | JsonOLCandCreateRequest.billBPartner | bpartner details computed from billingBPLocationExternalId based on address customId from `JsonExternalSystemRequest.parameters.JSONPathConstantBPartnerLocationID` |
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
Order.salesRepId | `C_BPartner_SalesRep_ID` | N | JsonOLCandCreateRequest.salesPartner | based on `JsonExternalSystemRequest.parameters.JSONPathConstantSalesRepID` it provides a JsonSalesPartner with salesPartnerCode from Order.salesRepId |
JsonExternalSystemShopware6ConfigMappings.mappings.docTypeOrder |  `c_doctypeorder_id` | Y | JsonOLCandCreateRequest.orderDocType | sets the JsonOrderDocType from docTypeOrder code |
JsonExternalSystemShopware6ConfigMappings.mappings.paymentRule |  `paymentrule` | Y | JsonOLCandCreateRequest.paymentRule | sets the JSONPaymentRule from paymentRule code |
JsonExternalSystemShopware6ConfigMappings.mappings.paymentTermValue |  `c_paymentterm_id` | N | JsonOLCandCreateRequest.paymentTerm | is paymentTermValue is defined then value "val-{{paymentTermValue}} is set" |

#### 2. JsonOrderLine

* represents a shopware order line, pulled via `api/v3/order/{Order.Id}/line-items`
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


## **Shopware6 => metasfresh payment**

we need to invoke the endpoint `api/v2/orders/sales/payment`

PaymentRequestProcessor

#### 1. `JsonOrderTransaction` - OrderPayment

* pulled via endpoint: `api/v3/order/{{Order.Id}}/transactions`
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
