# UpdateVerificationStatusRequestBody

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**status** | [**StatusEnum**](#StatusEnum) | The new verification status of the order. | 
**externalReviewerId** | **String** | The identifier for the order&#x27;s regulated information reviewer. | 
**rejectionReasonId** | **String** | The unique identifier for the rejection reason used for rejecting the order&#x27;s regulated information. Only required if the new status is rejected. |  [optional]

<a name="StatusEnum"></a>
## Enum: StatusEnum
Name | Value
---- | -----
APPROVED | &quot;Approved&quot;
REJECTED | &quot;Rejected&quot;
