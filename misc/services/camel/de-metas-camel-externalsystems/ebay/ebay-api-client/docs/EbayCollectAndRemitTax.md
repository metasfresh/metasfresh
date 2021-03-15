# EbayCollectAndRemitTax

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**amount** | [**Amount**](Amount.md) |  |  [optional]
**taxType** | **String** | The type of tax and fees that eBay will collect and remit to the taxing or fee authority. See the TaxTypeEnum type definition for more information about each tax or fee type. For implementation help, refer to &lt;a href&#x3D;&#x27;https://developer.ebay.com/api-docs/sell/fulfillment/types/sel:TaxTypeEnum&#x27;&gt;eBay API documentation&lt;/a&gt; |  [optional]
**collectionMethod** | **String** | This field indicates the collection method used to collect the &#x27;Collect and Remit&#x27; tax for the order. This field is always returned for orders subject to &#x27;Collect and Remit&#x27; tax, and its value is always NET. Note: Although the collectionMethod field is returned for all orders subject to &#x27;Collect and Remit&#x27; tax, the collectionMethod field and the CollectionMethodEnum type are not currently of any practical use, although this field may have use in the future. If and when the logic of this field is changed, this note will be updated and a note will also be added to the Release Notes. For implementation help, refer to &lt;a href&#x3D;&#x27;https://developer.ebay.com/api-docs/sell/fulfillment/types/sel:CollectionMethodEnum&#x27;&gt;eBay API documentation&lt;/a&gt; |  [optional]
