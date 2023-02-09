

# Payment

This type is used to provide details about the seller payments for an order.

## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**amount** | [**Amount**](Amount.md) |  |  [optional]
**paymentDate** | **String** | The date and time that the payment was received by the seller. This field will not be returned if buyer has yet to pay for the order. This timestamp is in ISO 8601 format, which uses the 24-hour Universal Coordinated Time (UTC) clock. Format: [YYYY]-[MM]-[DD]T[hh]:[mm]:[ss].[sss]Z Example: 2015-08-04T19:09:02.768Z |  [optional]
**paymentHolds** | [**List&lt;PaymentHold&gt;**](PaymentHold.md) | This container is only returned if eBay is temporarily holding the seller&#39;s funds for the order. If a payment hold has been placed on the order, this container includes the reason for the payment hold, the expected release date of the funds into the seller&#39;s account, the current state of the hold, and as soon as the payment hold has been released, the actual release date. |  [optional]
**paymentMethod** | **String** | The payment method used to pay for the order. See the PaymentMethodTypeEnum type for more information on the payment methods. For implementation help, refer to &lt;a href&#x3D;&#39;https://developer.ebay.com/api-docs/sell/fulfillment/types/sel:PaymentMethodTypeEnum&#39;&gt;eBay API documentation&lt;/a&gt; |  [optional]
**paymentReferenceId** | **String** | This field is only returned if payment has been made by the buyer, and the paymentMethod is PAYPAL or ESCROW. This field contains the PayPal-generated transaction identifier in case of payment made via PAYPAL. |  [optional]
**paymentStatus** | **String** | The enumeration value returned in this field indicates the status of the payment for the order. See the PaymentStatusEnum type definition for more information on the possible payment states. For implementation help, refer to &lt;a href&#x3D;&#39;https://developer.ebay.com/api-docs/sell/fulfillment/types/sel:PaymentStatusEnum&#39;&gt;eBay API documentation&lt;/a&gt; |  [optional]



