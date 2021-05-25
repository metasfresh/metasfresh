

# AddEvidencePaymentDisputeResponse

This type is used by the response payload of the addEvidence method. Its only field is an unique identifier of an evidence set.

## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**evidenceId** | **String** | The value returned in this field is the unique identifier of the newly-created evidence set. Upon a successful call, this value is automatically genererated. This new evidence set for the payment dispute includes the evidence file(s) that were passed in to the fileId array in the request payload. The evidenceId value will be needed if the seller wishes to add to the evidence set by using the updateEvidence method, or if they want to retrieve a specific evidence file within the evidence set by using the fetchEvidenceContent method. |  [optional]



