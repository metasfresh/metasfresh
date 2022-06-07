

# RefundItem

This type is used if the seller is issuing a refund for one or more individual order line items in a multiple line item order. Otherwise, the seller just uses the orderLevelRefundAmount container to specify the amount of the refund for the entire order.

## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**refundAmount** | [**SimpleAmount**](SimpleAmount.md) |  |  [optional]
**lineItemId** | **String** | The unique identifier of an order line item. This identifier is created once a buyer purchases a &#39;Buy It Now&#39; item or if an auction listing ends with a winning bidder. Either this field or the legacyReference container is needed to identify an individual order line item that will receive a refund. Note: The lineItemId field is used to identify an order line item in REST API format, and the legacyReference container is used to identify an order line item in Trading/legacy API format. Both legacy and REST API identifiers are returned in getOrder (Fulfillment API) and GetOrders (Trading API). |  [optional]
**legacyReference** | [**LegacyReference**](LegacyReference.md) |  |  [optional]



