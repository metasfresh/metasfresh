# OrderItem

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**ASIN** | **String** | The Amazon Standard Identification Number (ASIN) of the item. | 
**sellerSKU** | **String** | The seller stock keeping unit (SKU) of the item. |  [optional]
**orderItemId** | **String** | An Amazon-defined order item identifier. | 
**title** | **String** | The name of the item. |  [optional]
**quantityOrdered** | **Integer** | The number of items in the order.  | 
**quantityShipped** | **Integer** | The number of items shipped. |  [optional]
**productInfo** | [**ProductInfoDetail**](ProductInfoDetail.md) |  |  [optional]
**pointsGranted** | [**PointsGrantedDetail**](PointsGrantedDetail.md) |  |  [optional]
**itemPrice** | [**Money**](Money.md) |  |  [optional]
**shippingPrice** | [**Money**](Money.md) |  |  [optional]
**itemTax** | [**Money**](Money.md) |  |  [optional]
**shippingTax** | [**Money**](Money.md) |  |  [optional]
**shippingDiscount** | [**Money**](Money.md) |  |  [optional]
**shippingDiscountTax** | [**Money**](Money.md) |  |  [optional]
**promotionDiscount** | [**Money**](Money.md) |  |  [optional]
**promotionDiscountTax** | [**Money**](Money.md) |  |  [optional]
**promotionIds** | [**PromotionIdList**](PromotionIdList.md) |  |  [optional]
**coDFee** | [**Money**](Money.md) |  |  [optional]
**coDFeeDiscount** | [**Money**](Money.md) |  |  [optional]
**isGift** | **Boolean** | When true, the item is a gift. |  [optional]
**conditionNote** | **String** | The condition of the item as described by the seller. |  [optional]
**conditionId** | **String** | The condition of the item.  Possible values: New, Used, Collectible, Refurbished, Preorder, Club. |  [optional]
**conditionSubtypeId** | **String** | The subcondition of the item.  Possible values: New, Mint, Very Good, Good, Acceptable, Poor, Club, OEM, Warranty, Refurbished Warranty, Refurbished, Open Box, Any, Other. |  [optional]
**scheduledDeliveryStartDate** | **String** | The start date of the scheduled delivery window in the time zone of the order destination. In ISO 8601 date time format. |  [optional]
**scheduledDeliveryEndDate** | **String** | The end date of the scheduled delivery window in the time zone of the order destination. In ISO 8601 date time format. |  [optional]
**priceDesignation** | **String** | Indicates that the selling price is a special price that is available only for Amazon Business orders. For more information about the Amazon Business Seller Program, see the [Amazon Business website](https://www.amazon.com/b2b/info/amazon-business).   Possible values: BusinessPrice - A special price that is available only for Amazon Business orders. |  [optional]
**taxCollection** | [**TaxCollection**](TaxCollection.md) |  |  [optional]
**serialNumberRequired** | **Boolean** | When true, the product type for this item has a serial number.  Returned only for Amazon Easy Ship orders. |  [optional]
**isTransparency** | **Boolean** | When true, transparency codes are required. |  [optional]
**iossNumber** | **String** | The IOSS number for the marketplace. Sellers shipping to the European Union (EU) from outside of the EU must provide this IOSS number to their carrier when Amazon has collected the VAT on the sale. |  [optional]
**storeChainStoreId** | **String** | The store chain store identifier. Linked to a specific store in a store chain. |  [optional]
**deemedResellerCategory** | [**DeemedResellerCategoryEnum**](#DeemedResellerCategoryEnum) | The category of deemed reseller. This applies to selling partners that are not based in the EU and is used to help them meet the VAT Deemed Reseller tax laws in the EU and UK. |  [optional]
**buyerInfo** | [**ItemBuyerInfo**](ItemBuyerInfo.md) |  |  [optional]
**buyerRequestedCancel** | [**BuyerRequestedCancel**](BuyerRequestedCancel.md) |  |  [optional]

<a name="DeemedResellerCategoryEnum"></a>
## Enum: DeemedResellerCategoryEnum
Name | Value
---- | -----
IOSS | &quot;IOSS&quot;
UOSS | &quot;UOSS&quot;
