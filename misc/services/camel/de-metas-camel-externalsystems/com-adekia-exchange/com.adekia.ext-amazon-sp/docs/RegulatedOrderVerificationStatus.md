# RegulatedOrderVerificationStatus

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**status** | [**StatusEnum**](#StatusEnum) | The verification status of the order. | 
**requiresMerchantAction** | **Boolean** | Whether the regulated information provided in the order requires a review by the merchant. | 
**validRejectionReasons** | [**List&lt;RejectionReason&gt;**](RejectionReason.md) | A list of valid rejection reasons that may be used to reject the order&#x27;s regulated information. | 
**rejectionReason** | [**RejectionReason**](RejectionReason.md) |  |  [optional]
**reviewDate** | **String** | The date the order was reviewed. In ISO 8601 date time format. |  [optional]
**externalReviewerId** | **String** | The identifier for the order&#x27;s regulated information reviewer. |  [optional]

<a name="StatusEnum"></a>
## Enum: StatusEnum
Name | Value
---- | -----
PENDING | &quot;Pending&quot;
APPROVED | &quot;Approved&quot;
REJECTED | &quot;Rejected&quot;
EXPIRED | &quot;Expired&quot;
CANCELLED | &quot;Cancelled&quot;
