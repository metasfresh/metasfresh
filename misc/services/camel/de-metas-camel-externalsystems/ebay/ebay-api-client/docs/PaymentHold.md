# PaymentHold

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**expectedReleaseDate** | **String** | The date and time that the payment being held is expected to be released to the seller. This timestamp is in ISO 8601 format, which uses the 24-hour Universal Coordinated Time (UTC) clock. This field will be returned if known by eBay. Format: [YYYY]-[MM]-[DD]T[hh]:[mm]:[ss].[sss]Z Example: 2015-08-04T19:09:02.768Z |  [optional]
**holdAmount** | [**Amount**](Amount.md) |  |  [optional]
**holdReason** | **String** | The reason that the payment is being held. A seller&#x27;s payment may be held for a number of reasons, including when the seller is new, the seller&#x27;s level is below standard, or if a return case or &#x27;Significantly not as described&#x27; case is pending against the seller. This field is always returned with the paymentHolds array. |  [optional]
**holdState** | **String** | The current stage or condition of the hold. This field is always returned with the paymentHolds array. Applicable values: HELD HELD_PENDING NOT_HELD RELEASE_CONFIRMED RELEASE_FAILED RELEASE_PENDING RELEASED |  [optional]
**releaseDate** | **String** | The date and time that the payment being held was actually released to the seller. This timestamp is in ISO 8601 format, which uses the 24-hour Universal Coordinated Time (UTC) clock. This field is not returned until the seller&#x27;s payment is actually released into the seller&#x27;s account. Format: [YYYY]-[MM]-[DD]T[hh]:[mm]:[ss].[sss]Z Example: 2015-08-04T19:09:02.768Z |  [optional]
**sellerActionsToRelease** | [**List&lt;SellerActionsToRelease&gt;**](SellerActionsToRelease.md) | A list of one or more possible actions that the seller can take to expedite the release of the payment hold. |  [optional]
