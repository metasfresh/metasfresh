# OrderBuyerInfo

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**amazonOrderId** | **String** | An Amazon-defined order identifier, in 3-7-7 format. | 
**buyerEmail** | **String** | The anonymized email address of the buyer. |  [optional]
**buyerName** | **String** | The name of the buyer. |  [optional]
**buyerCounty** | **String** | The county of the buyer. |  [optional]
**buyerTaxInfo** | [**BuyerTaxInfo**](BuyerTaxInfo.md) |  |  [optional]
**purchaseOrderNumber** | **String** | The purchase order (PO) number entered by the buyer at checkout. Returned only for orders where the buyer entered a PO number at checkout. |  [optional]
