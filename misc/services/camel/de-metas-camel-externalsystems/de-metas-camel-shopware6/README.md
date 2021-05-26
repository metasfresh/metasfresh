## Current data mapping
****

**Shopware6 => metasfresh BPartners**

we need to invoke the endpoint `api/v2-pre/bpartner`

1. BPartner - all columns are from `C_BPartner`

Shopware | metasfresh-column | mandatory in mf | metasfresh-json | note |
---- | ---- | ---- | ---- | ---- |
Order.OrderCustomer.FirstName + " " + Order.OrderCustomer.LastName | `Name` | N | JsonRequestBPartner.Name | |
Order.OrderCustomer.company | `CompanyName` | N | JsonRequestBPartner.companyName | |
Order.OrderCustomer.customerNumber | `Value` | N | JsonRequestBPartner.code | Code of the bPartner in question "ext-Shopware6-{{customerNumber}}" |
---- | `isCustomer` | N | JsonRequestBPartner.customer | always true |
JsonExternalSystemShopware6ConfigMapping.bPartnerSyncAdvice | ---- | N | JsonRequestBPartner.syncAdvise | |
ImportOrdersRouteContext.orgCode | `ad_org_id` | N | JsonRequestComposite.orgCode | orgCode -> orgId |
Order.effectiveCustomerId | `C_BPartner_ID` || JsonRequestBPartnerUpsertItem.bpartnerIdentifier | value from Order.OrderCustomer.CustomerId or Order.customBPartnerId |
--- | ---- | N | JsonRequestBPartnerUpsert.syncAdvise | default value CREATE_OR_MERGE |

2. Contact - all columns are from `AD_User`

Shopware | metasfresh-column | mandatory in mf | metasfresh-json | note |
---- | ---- | ---- | ---- | ---- |
Order.firstName | `firstName` | N | JsonRequestContact.firstName | |
Order.lastName | `lastName` | N | JsonRequestContact.lastName | |
Order.email | `email` | N | JsonRequestContact.email | |
JsonExternalSystemShopware6ConfigMapping.bPartnerSyncAdvice | ---- | N | JsonRequestContact.syncAdvise | |
JsonExternalSystemShopware6ConfigMapping.bPartnerSyncAdvice | ---- | N | JsonRequestContactUpsert.syncAdvise | |
Order.effectiveCustomerId | `AD_User_ID` | N | JsonRequestContactUpsertItem.contactIdentifier | value from Order.OrderCustomer.CustomerId or Order.customBPartnerId|

3. BPartnerLocation

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
JsonExternalSystemShopware6ConfigMapping.bPartnerLocationSyncAdvice | ---- | N | JsonRequestLocationUpsert.syncAdvise | |

***
**Shopware6 => metasfresh orderCandidate**

we need to invoke the endpoint `api/v2-pre/orders/sales/candidates/bulk`

OLCandRequestProcessor - JsonOLCandCreateBulkRequest

1. OrderCandidate - all columns are from `C_OLCand`

Shopware | metasfresh-column | mandatory in mf | metasfresh-json | note |
---- | ---- | ---- | ---- | ---- |
ImportOrdersRouteContext.orgCode | `ad_org_id` | Y | JsonOLCandCreateRequest.orgCode | |
Order.OrderCustomer.currencyId | `c_currency_id` | Y | JsonOLCandCreateRequest.currencyCode | returns currency isoCode  by currencyId, shall come from pricingSystem/priceList|
Order.OrderCustomer.id | `externalHeaderId` | Y | JsonOLCandCreateRequest.externalHeaderId | |
Order.OrderCustomer.orderNumber |  `poreference` | Y | JsonOLCandCreateRequest.poReference | |
ImportOrdersRouteContext.shippingBPLocationExternalId |  `C_BPartner_ID`,  `C_BPartner_Location_ID`,  `AD_User_ID` | N | JsonOLCandCreateRequest.bpartner | bpartner details from shipping shippingBPLocationExternalId |
ImportOrdersRouteContext.billingBPLocationExternalId |  `Bill_BPartner_ID` | N | JsonOLCandCreateRequest.billBPartner | bpartner details base on billingBPLocationExternalId |
Order.OrderCustomer.orderDate |  `DateOrdered` | N | JsonOLCandCreateRequest.dateOrdered |  |
ImportOrdersRouteContext.dateRequired |  `datePromised` | Y | JsonOLCandCreateRequest.dateRequired |  |
Order.OrderCustomer.updatedAt |  `dateCandidate` | N | JsonOLCandCreateRequest.dateCandidate | if Order.OrderCustomer.updatedAt is null it will be populated from Order.OrderCustomer.createdAt |
---- |  `ad_inputdatasource_id` | Y | JsonOLCandCreateRequest.dataSource | default value `int-Shopware`, identifier for AD_InputDataSource record|
---- |  `isManualPrice` | N | JsonOLCandCreateRequest.isManualPrice | default value `true` |
---- |  `isImportedWithIssues` | N | JsonOLCandCreateRequest.isImportedWithIssues | default value `true` |
---- |  `Discount` | N | JsonOLCandCreateRequest.discount | default value `0` |
---- |  `DeliveryViaRule` | N | JsonOLCandCreateRequest.deliveryViaRule | default value `S` |
---- |  `DeliveryRule` | N | JsonOLCandCreateRequest.deliveryRule | default value `A` |
ImportOrdersRouteContext.isMultipleShippingAddresses |  `importWarningMessage` | N | JsonOLCandCreateRequest.importWarningMessage | if isMultipleShippingAddresses is set then default value is set `MULTIPLE_SHIPPING_ADDRESSES_WARN_MESSAGE`, otherwise is `null` |
ImportOrdersRouteContext.shippingMethodId |  `m_shipper_id` | N | JsonOLCandCreateRequest.shipper | external identifier for "ext-Shopware6-{{shippingMethodId}}" |
Order.salesRepId | ---- | N | JsonOLCandCreateRequest.salesPartner | JsonSalesPartner with salesPartnerCode from Order.salesRepId |
JsonExternalSystemShopware6ConfigMapping.docTypeOrder |  `c_doctypeorder_id` | Y | JsonOLCandCreateRequest.orderDocType | sets the JsonOrderDocType from docTypeOrder code |
JsonExternalSystemShopware6ConfigMapping.paymentRule |  `paymentrule` | Y | JsonOLCandCreateRequest.paymentRule | sets the JSONPaymentRule from paymentRule code |
JsonExternalSystemShopware6ConfigMapping.paymentTermValue |  `c_paymentterm_id` | N | JsonOLCandCreateRequest.paymentTerm | is paymentTermValue is defined then value "val-{{paymentTermValue}} is set" |

2. JsonOrderLine - all columns are from `C_OLCand`

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

1. OrderPayment -- all columns are from `C_Payment`, except for `orderIdentifier`

Shopware | metasfresh-column | mandatory in mf | metasfresh-json | note |
---- | ---- | ---- | ---- | ---- |
ImportOrdersRouteContext.orgCode |  `ad_org_id` | Y | JsonOrderPaymentCreateRequest.orgCode | |
JsonOrderTransaction.id |  `externalid` | Y | JsonOrderPaymentCreateRequest.externalPaymentId | |
Order.effectiveCustomerId |  `c_bpartner_id` | Y | JsonOrderPaymentCreateRequest.bpartnerIdentifier | |
JsonOrderTransaction.totalPrice |  `payamt` | Y | JsonOrderPaymentCreateRequest.amount | |
Order.OrderCustomer.currencyId |  `c_currency_id` | Y | JsonOrderPaymentCreateRequest.currencyCode | returns currency isoCode  by currencyId, from CurrencyInfoProvider|
Order.OrderCustomer.id | `C_Order_ID.c_payment_id` | Y | JsonOrderPaymentCreateRequest.orderIdentifier | |
JsonOrderTransaction.createdAt |  `datetrx` | N | JsonOrderPaymentCreateRequest.transactionDate | |
