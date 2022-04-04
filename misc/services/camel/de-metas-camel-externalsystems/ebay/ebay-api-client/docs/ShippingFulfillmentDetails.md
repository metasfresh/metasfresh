

# ShippingFulfillmentDetails

This type contains the details for creating a fulfillment for an order.

## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**lineItems** | [**List&lt;LineItemReference&gt;**](LineItemReference.md) | This array contains a list of or more line items and the quantity that will be shipped in the same package. |  [optional]
**shippedDate** | **String** | This is the actual date and time that the fulfillment package was shipped. This timestamp is in ISO 8601 format, which uses the 24-hour Universal Coordinated Time (UTC) clock. The seller should use the actual date/time that the package was shipped, but if this field is omitted, it will default to the current date/time. Format: [YYYY]-[MM]-[DD]T[hh]:[mm]:[ss].[sss]Z Example: 2015-08-04T19:09:02.768Z Default: The current date and time. |  [optional]
**shippingCarrierCode** | **String** | The unique identifier of the shipping carrier being used to ship the line item(s). Technically, the shippingCarrierCode and trackingNumber fields are optional, but generally these fields will be provided if the shipping carrier and tracking number are known. Note: Use the Trading API&#39;s GeteBayDetails call to retrieve the latest shipping carrier enumeration values. When making the GeteBayDetails call, include the DetailName field in the request payload and set its value to ShippingCarrierDetails. Each valid shipping carrier enumeration value is returned in a ShippingCarrierDetails.ShippingCarrier field in the response payload. |  [optional]
**trackingNumber** | **String** | The tracking number provided by the shipping carrier for this fulfillment. The seller should be careful that this tracking number is accurate since the buyer will use this tracking number to track shipment, and eBay has no way to verify the accuracy of this number. This field and the shippingCarrierCode field are mutually dependent. If you include one, you must also include the other. Note: If you include trackingNumber (and shippingCarrierCode) in the request, the resulting fulfillment&#39;s ID (returned in the HTTP location code) is the tracking number. If you do not include shipment tracking information, the resulting fulfillment ID will default to an arbitrary number such as 999. |  [optional]



