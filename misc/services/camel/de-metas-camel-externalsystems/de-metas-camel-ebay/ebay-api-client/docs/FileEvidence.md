

# FileEvidence

This type is used to store the unique identifier of an evidence file. Evidence files are used by seller to contest a payment dispute.

## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**fileId** | **String** | If an uploadEvidenceFile call is successful, a unique identifier of this evidence file will be returned in the uploadEvidenceFile response payload. This unique fileId value is then used to either add this evidence file to a new evidence set using the addEvidence method, or to add this file to an existing evidence set using the updateEvidence method. Note that if an evidence set already exists for a payment dispute, the getPaymentDispute method will return both the evidenceId (unique identifier of evidence set) value, and the fileId (unique identifier of a file within that evidence set) value(s). |  [optional]



