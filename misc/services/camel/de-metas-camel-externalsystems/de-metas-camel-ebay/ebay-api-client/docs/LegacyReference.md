

# LegacyReference

Type defining the legacyReference container. This container is needed if the seller is issuing a refund for an individual order line item, and wishes to use an item ID and transaction ID to identify the order line item.

## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**legacyItemId** | **String** | The unique identifier of a listing in legacy/Trading API format. Note: Both legacyItemId and legacyTransactionId are needed to identify an order line item. |  [optional]
**legacyTransactionId** | **String** | The unique identifier of a sale/transaction in legacy/Trading API format. A &#39;transaction ID&#39; is created once a buyer purchases a &#39;Buy It Now&#39; item or if an auction listing ends with a winning bidder. Note: Both legacyItemId and legacyTransactionId are needed to identify an order line item. |  [optional]



