

# PaymentDisputeActivity

This type is used by each recorded activity on a payment dispute, from creation to resolution.

## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**activityDate** | **String** | The timestamp in this field shows the date/time of the payment dispute activity. The timestamps returned here use the ISO-8601 24-hour date and time format, and the time zone used is Universal Coordinated Time (UTC), also known as Greenwich Mean Time (GMT), or Zulu. The ISO-8601 format looks like this: yyyy-MM-ddThh:mm.ss.sssZ. An example would be 2019-08-04T19:09:02.768Z. |  [optional]
**activityType** | **String** | This enumeration value indicates the type of activity that occured on the payment dispute. For example, a value of DISPUTE_OPENED is returned when a payment disute is first created, a value indicating the seller&#39;s decision on the dispute, such as SELLER_CONTEST, is returned when seller makes a decision to accept or contest dispute, and a value of DISPUTE_CLOSED is returned when a payment disute is resolved. See ActivityEnum for an explanation of each of the values that may be returned here. For implementation help, refer to &lt;a href&#x3D;&#39;https://developer.ebay.com/api-docs/sell/fulfillment/types/api:ActivityEnum&#39;&gt;eBay API documentation&lt;/a&gt; |  [optional]
**actor** | **String** | This enumeration value indicates the actor that performed the action. Possible values include the BUYER, SELLER, CS_AGENT (eBay customer service), or SYSTEM. For implementation help, refer to &lt;a href&#x3D;&#39;https://developer.ebay.com/api-docs/sell/fulfillment/types/api:ActorEnum&#39;&gt;eBay API documentation&lt;/a&gt; |  [optional]



