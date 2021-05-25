

# TaxIdentifier

This type is used by the taxIdentifier container that is returned in getOrder. The taxIdentifier container consists of taxpayer identification information for buyers from Italy, Spain, or Guatemala. It is currently only returned for orders occurring on the eBay Italy or eBay Spain marketplaces. Note: Currently, the taxIdentifier container is only returned in getOrder and not in getOrders. So, if a seller wanted to view a buyer's tax information for a particular order returned in getOrders, that seller would need to use the orderId value for that particular order, and then run a getOrder call against that order ID.

## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**taxpayerId** | **String** | This value is the unique tax ID associated with the buyer. The type of tax identification is shown in the taxIdentifierType field. |  [optional]
**taxIdentifierType** | **String** | This enumeration value indicates the type of tax identification being used for the buyer. The different tax types are defined in the TaxIdentifierTypeEnum type. For implementation help, refer to &lt;a href&#x3D;&#39;https://developer.ebay.com/api-docs/sell/fulfillment/types/sel:TaxIdentifierTypeEnum&#39;&gt;eBay API documentation&lt;/a&gt; |  [optional]
**issuingCountry** | **String** | This two-letter code indicates the country that issued the buyer&#39;s tax ID. The country that the two-letter code represents can be found in the CountryCodeEnum type, or in the ISO 3166 standard. For implementation help, refer to &lt;a href&#x3D;&#39;https://developer.ebay.com/api-docs/sell/fulfillment/types/ba:CountryCodeEnum&#39;&gt;eBay API documentation&lt;/a&gt; |  [optional]



