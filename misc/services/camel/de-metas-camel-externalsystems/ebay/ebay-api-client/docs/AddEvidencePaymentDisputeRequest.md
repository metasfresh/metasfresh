

# AddEvidencePaymentDisputeRequest

This type is used by the request payload of the addEvidence method. The addEvidence method is used to create a new evidence set against a payment dispute with one or more evidence files.

## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**evidenceType** | **String** | This field is used to indicate the type of evidence being provided through one or more evidence files. All evidence files (if more than one) should be associated with the evidence type passed in this field. See the EvidenceTypeEnum type for the supported evidence types. For implementation help, refer to &lt;a href&#x3D;&#39;https://developer.ebay.com/api-docs/sell/fulfillment/types/api:EvidenceTypeEnum&#39;&gt;eBay API documentation&lt;/a&gt; |  [optional]
**files** | [**List&lt;FileEvidence&gt;**](FileEvidence.md) | This array is used to specify one or more evidence files that will become part of a new evidence set associated with a payment dispute. At least one evidence file must be specified in the files array. The unique identifier of an evidence file is returned in the response payload of the uploadEvidence method. |  [optional]
**lineItems** | [**List&lt;OrderLineItems&gt;**](OrderLineItems.md) | This required array identifies the order line item(s) for which the evidence file(s) will be applicable. Both the itemId and lineItemID fields are needed to identify each order line item, and both of these values are returned under the evidenceRequests.lineItems array in the getPaymentDispute response. |  [optional]



