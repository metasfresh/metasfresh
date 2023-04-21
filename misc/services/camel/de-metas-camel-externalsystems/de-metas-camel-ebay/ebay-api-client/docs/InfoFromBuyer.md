

# InfoFromBuyer

This container is returned if the buyer is returning one or more line items in an order that is associated with the payment dispute, and that buyer has provided return shipping tracking information and/or a note about the return.

## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**note** | **String** | This field shows any note that was left by the buyer for in regards to the dispute. |  [optional]
**returnShipmentTracking** | [**List&lt;TrackingInfo&gt;**](TrackingInfo.md) | This array shows shipment tracking information for one or more shipping packages being returned to the buyer after a payment dispute. |  [optional]



