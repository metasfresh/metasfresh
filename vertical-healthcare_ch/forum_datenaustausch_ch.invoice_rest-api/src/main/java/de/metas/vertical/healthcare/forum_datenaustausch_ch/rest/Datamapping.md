
| XML | metasfresh C_OLCand column | masterdata column | notes |
| --- | -------------------------- | ----------------- | ----- |
| request/payload/invoice/requestId | | externalId | |
| request/payload/invoice/requestDate | | dateInvoiced | |
| request/payload/body/tiersGarant/insurance/eanParty | C_BPartner_ID | C_BPartner.Value | `"EAN-"eanParty`; lookup via `C_BPartner.Value`; if no C_BPartner record is found, then a new `C_BPartner` etc is created on the fly |
| request/payload/body/tiersGarant/insurance/company/companyname | C_BPartner_ID | C_BPartner.Name | will be written to `C_BPartner.Name` (both newly created and existing C_BPartner records) |
| request/payload/body/tiersGarant/insurance/person/givenname | AD_User_ID | AD_User.Name and AD_User.ExternalId |  lookup via ExternalId; `givenname` and `familyname` are concatenated |
| request/payload/body/tiersGarant/insurance/person/familyname | AD_User_ID | AD_User.Name and AD_User.ExternalId | lookup via ExternalId; `givenname` and `familyname` are concatenated |
