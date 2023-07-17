

# FulfillmentStartInstruction

This type contains a set of specifications for processing a fulfillment of an order, including the type of fulfillment, shipping carrier and service, addressing details, and estimated delivery window. These instructions are derived from the buyer's and seller's eBay account preferences, the listing parameters, and the buyer's checkout selections. The seller can use them as a starting point for packaging, addressing, and shipping the order.

## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**ebaySupportedFulfillment** | **Boolean** | This field is only returned if its value is true and indicates that the fulfillment will be shipped via eBay&#39;s Global Shipping Program. For more information, see the Global Shipping Program help topic. |  [optional]
**finalDestinationAddress** | [**Address**](Address.md) |  |  [optional]
**fulfillmentInstructionsType** | **String** | The enumeration value returned in this field indicates the method of fulfillment that will be used to deliver this set of line items (this package) to the buyer. This field will have a value of SHIP_TO if the ebaySupportedFulfillment field is returned with a value of true. See the FulfillmentInstructionsType definition for more information about different fulfillment types. For implementation help, refer to &lt;a href&#x3D;&#39;https://developer.ebay.com/api-docs/sell/fulfillment/types/sel:FulfillmentInstructionsType&#39;&gt;eBay API documentation&lt;/a&gt; |  [optional]
**maxEstimatedDeliveryDate** | **String** | This is the estimated latest date that the fulfillment will be completed. This timestamp is in ISO 8601 format, which uses the 24-hour Universal Coordinated Time (UTC) clock. This field is not returned ifthe value of the fulfillmentInstructionsType field is DIGITAL or PREPARE_FOR_PICKUP. Format: [YYYY]-[MM]-[DD]T[hh]:[mm]:[ss].[sss]Z Example: 2015-08-04T19:09:02.768Z |  [optional]
**minEstimatedDeliveryDate** | **String** | This is the estimated earliest date that the fulfillment will be completed. This timestamp is in ISO 8601 format, which uses the 24-hour Universal Coordinated Time (UTC) clock. This field is not returned if the value of the fulfillmentInstructionsType field is DIGITAL or PREPARE_FOR_PICKUP. Format: [YYYY]-[MM]-[DD]T[hh]:[mm]:[ss].[sss]Z Example: 2015-08-04T19:09:02.768Z |  [optional]
**pickupStep** | [**PickupStep**](PickupStep.md) |  |  [optional]
**shippingStep** | [**ShippingStep**](ShippingStep.md) |  |  [optional]



