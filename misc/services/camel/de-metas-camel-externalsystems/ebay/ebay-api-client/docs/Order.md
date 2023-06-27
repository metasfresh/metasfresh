

# Order

This type contains the details of an order, including information about the buyer, order history, shipping fulfillments, line items, costs, payments, and order fulfillment status.

## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**buyer** | [**Buyer**](Buyer.md) |  |  [optional]
**buyerCheckoutNotes** | **String** | This field contains any comments that the buyer left for the seller about the order during checkout process. This field is only returned if a buyer left comments at checkout time. |  [optional]
**cancelStatus** | [**CancelStatus**](CancelStatus.md) |  |  [optional]
**creationDate** | **String** | The date and time that the order was created. This timestamp is in ISO 8601 format, which uses the 24-hour Universal Coordinated Time (UTC) clock. Format: [YYYY]-[MM]-[DD]T[hh]:[mm]:[ss].[sss]Z Example: 2015-08-04T19:09:02.768Z |  [optional]
**ebayCollectAndRemitTax** | **Boolean** | This field is only returned if true, and indicates that eBay will collect tax (US state-mandated sales tax, &#39;Goods and Services&#39; tax in Australia or New Zealand, and VAT collected for UK and EU countries) for at least one line item in the order, and remit the tax to the taxing authority of the buyer&#39;s residence. If this field is returned, the seller should search for one or more ebayCollectAndRemitTaxes containers at the line item level to get more information about the type of tax and the amount. |  [optional]
**fulfillmentHrefs** | **List&lt;String&gt;** | This array contains a list of one or more getShippingFulfillment call URIs that can be used to retrieve shipping fulfillments that have been set up for the order. |  [optional]
**fulfillmentStartInstructions** | [**List&lt;FulfillmentStartInstruction&gt;**](FulfillmentStartInstruction.md) | This container consists of a set of specifications for fulfilling the order, including the type of fulfillment, shipping carrier and service, shipping address, and estimated delivery window. These instructions are derived from the buyer&#39;s and seller&#39;s eBay account preferences, the listing parameters, and the buyer&#39;s checkout selections. The seller can use them as a starting point for packaging, addressing, and shipping the order. Note: Although this container is presented as an array, it currently returns only one set of fulfillment specifications. Additional array members will be supported in future functionality. |  [optional]
**lastModifiedDate** | **String** | The date and time that the order was last modified. This timestamp is in ISO 8601 format, which uses the 24-hour Universal Coordinated Time (UTC) clock. Format: [YYYY]-[MM]-[DD]T[hh]:[mm]:[ss].[sss]Z Example: 2015-08-04T19:09:02.768Z |  [optional]
**legacyOrderId** | **String** | The unique identifier of the order in legacy format, as traditionally used by the Trading API (and other legacy APIs). Both the orderId field and this field are always returned. Note: In June 2019, Order IDs in REST APIs transitioned to a new format. For the Trading and other legacy APIs, by using version control/compatibility level, users have the option of using the older legacy order ID format, or they can migrate to the new order ID format, which is the same order ID format being used by REST APIs. Although users of the Trading API (and other legacy APIs) can now transition to the new order ID format, this legacyOrderId field will still return order IDs in the old format to distinguish between the old and new order IDs. |  [optional]
**lineItems** | [**List&lt;LineItem&gt;**](LineItem.md) | This array contains the details for all line items that comprise the order. |  [optional]
**orderFulfillmentStatus** | **String** | The degree to which fulfillment of the order is complete. See the OrderFulfillmentStatus type definition for more information about each possible fulfillment state. For implementation help, refer to &lt;a href&#x3D;&#39;https://developer.ebay.com/api-docs/sell/fulfillment/types/sel:OrderFulfillmentStatus&#39;&gt;eBay API documentation&lt;/a&gt; |  [optional]
**orderId** | **String** | The unique identifier of the order. Both the legacyOrderId field (traditionally used by Trading and other legacy APIS) and this field are always returned. Note: In June 2019, Order IDs in REST APIs transitioned to a new format. For the Trading and other legacy APIs, by using version control/compatibility level, users have the option of using the older legacy order ID format, or they can migrate to the new order ID format, which is the same order ID format being used by REST APIs. The new format is a non-parsable string, globally unique across all eBay marketplaces, and consistent for both single line item and multiple line item orders. These order identifiers are automatically generated after buyer payment, and unlike in the past, instead of just being known and exposed to the seller, these unique order identifiers will also be known and used/referenced by the buyer and eBay customer support. |  [optional]
**orderPaymentStatus** | **String** | The enumeration value returned in this field indicates the current payment status of an order, or in case of a refund request, the current status of the refund. See the OrderPaymentStatusEnum type definition for more information about each possible payment/refund state. For implementation help, refer to &lt;a href&#x3D;&#39;https://developer.ebay.com/api-docs/sell/fulfillment/types/sel:OrderPaymentStatusEnum&#39;&gt;eBay API documentation&lt;/a&gt; |  [optional]
**paymentSummary** | [**PaymentSummary**](PaymentSummary.md) |  |  [optional]
**pricingSummary** | [**PricingSummary**](PricingSummary.md) |  |  [optional]
**program** | [**Program**](Program.md) |  |  [optional]
**salesRecordReference** | **String** | An eBay-generated identifier that is used to identify and manage orders through the Selling Manager and Selling Manager Pro tools. This order identifier can also be found on the Orders grid page and in the Sales Record pages in Seller Hub. A salesRecordReference number is only generated and returned at the order level, and not at the order line item level. In cases where the seller does not have a Selling Manager or Selling Manager Pro subscription nor access to Seller Hub, this field may not be returned. |  [optional]
**sellerId** | **String** | The unique eBay user ID of the seller who sold the order. |  [optional]
**totalFeeBasisAmount** | [**Amount**](Amount.md) |  |  [optional]
**totalMarketplaceFee** | [**Amount**](Amount.md) |  |  [optional]



