

# LineItem

This type contains the details of each line item in an order.

## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**appliedPromotions** | [**List&lt;AppliedPromotion&gt;**](AppliedPromotion.md) | This array contains information about one or more sales promotions or discounts applied to the line item. It is always returned, but will be returned as an empty array if no special sales promotions or discounts apply to the order line item. |  [optional]
**deliveryCost** | [**DeliveryCost**](DeliveryCost.md) |  |  [optional]
**discountedLineItemCost** | [**Amount**](Amount.md) |  |  [optional]
**ebayCollectAndRemitTaxes** | [**List&lt;EbayCollectAndRemitTax&gt;**](EbayCollectAndRemitTax.md) | This container will be returned if the order line item is subject to a &#39;Collect and Remit&#39; tax that eBay will collect and remit to the proper taxing authority on the buyer&#39;s behalf. &#39;Collect and Remit&#39; tax includes US state-mandated sales tax, &#39;Goods and Services&#39; tax in Australia or New Zealand, and VAT collected for UK and EU countries. The amount of this tax is shown in the amount field, and the type of tax is shown in the taxType field. eBay will display the tax type and amount during checkout in accordance with the buyer&#39;s address, and handle collection and remittance of the tax without requiring the seller to take any action. |  [optional]
**giftDetails** | [**GiftDetails**](GiftDetails.md) |  |  [optional]
**itemLocation** | [**ItemLocation**](ItemLocation.md) |  |  [optional]
**legacyItemId** | **String** | The eBay-generated legacy listing item ID of the listing. Note that the unique identifier of a listing in REST-based APIs is called the listingId instead. |  [optional]
**legacyVariationId** | **String** | The unique identifier of a single variation within a multiple-variation listing. This field is only returned if the line item purchased was from a multiple-variation listing. |  [optional]
**lineItemCost** | [**Amount**](Amount.md) |  |  [optional]
**lineItemFulfillmentInstructions** | [**LineItemFulfillmentInstructions**](LineItemFulfillmentInstructions.md) |  |  [optional]
**lineItemFulfillmentStatus** | **String** | This enumeration value indicates the current fulfillment status of the line item. For implementation help, refer to &lt;a href&#x3D;&#39;https://developer.ebay.com/api-docs/sell/fulfillment/types/sel:LineItemFulfillmentStatusEnum&#39;&gt;eBay API documentation&lt;/a&gt; |  [optional]
**lineItemId** | **String** | This is the unique identifier of an eBay order line item. This field is created as soon as there is a commitment to buy from the seller. |  [optional]
**listingMarketplaceId** | **String** | The unique identifier of the eBay marketplace where the line item was listed. For implementation help, refer to &lt;a href&#x3D;&#39;https://developer.ebay.com/api-docs/sell/fulfillment/types/ba:MarketplaceIdEnum&#39;&gt;eBay API documentation&lt;/a&gt; |  [optional]
**properties** | [**LineItemProperties**](LineItemProperties.md) |  |  [optional]
**purchaseMarketplaceId** | **String** | The unique identifier of the eBay marketplace where the line item was listed. Often, the listingMarketplaceId and the purchaseMarketplaceId identifier are the same, but there are occasions when an item will surface on multiple eBay marketplaces. For implementation help, refer to &lt;a href&#x3D;&#39;https://developer.ebay.com/api-docs/sell/fulfillment/types/ba:MarketplaceIdEnum&#39;&gt;eBay API documentation&lt;/a&gt; |  [optional]
**quantity** | **Integer** | The number of units of the line item in the order. These are represented as a group by a single lineItemId. |  [optional]
**refunds** | [**List&lt;LineItemRefund&gt;**](LineItemRefund.md) | This array is always returned, but is returned as an empty array unless the seller has submitted a partial or full refund to the buyer for the order. If a refund has occurred, the refund amount and refund date will be shown for each refund. |  [optional]
**sku** | **String** | Seller-defined Stock-Keeping Unit (SKU). This inventory identifier must be unique within the seller&#39;s eBay inventory. SKUs are optional when listing in the legacy/Trading API system, but SKUs are required when listing items through the Inventory API model. |  [optional]
**soldFormat** | **String** | The eBay listing type of the line item. The most common listing types are AUCTION and FIXED_PRICE. For implementation help, refer to &lt;a href&#x3D;&#39;https://developer.ebay.com/api-docs/sell/fulfillment/types/sel:SoldFormatEnum&#39;&gt;eBay API documentation&lt;/a&gt; |  [optional]
**taxes** | [**List&lt;Tax&gt;**](Tax.md) | Contains a list of taxes applied to the line item, if any. This array is always returned, but will be returned as empty if no taxes are applicable to the line item. |  [optional]
**title** | **String** | The title of the listing. |  [optional]
**total** | [**Amount**](Amount.md) |  |  [optional]



