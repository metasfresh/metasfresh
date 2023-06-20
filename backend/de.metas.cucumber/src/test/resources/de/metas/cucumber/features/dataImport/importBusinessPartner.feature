@from:cucumber
Feature: Import Business Partner via DataImportRestController

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'

  @from:cucumber
  Scenario: Import business partner with location and bp bank account - happy flow

    Given add HTTP header
      | Key          | Value      |
      | Content-Type | text/plain |
      | Accept       | */*        |
    And load AD_ImpFormat:
      | AD_ImpFormat_ID.Identifier | Name                      |
      | importFormat               | Geschäftspartner Standard |
    And metasfresh contains C_DataImport:
      | C_DataImport_ID.Identifier | InternalName       | AD_ImpFormat_ID.Identifier |
      | bpDataImportConfig         | bpDataImportConfig | importFormat               |
    And store business partner DataImport string requestBody in context
      | OPT.BPValue   | OPT.Companyname | OPT.TaxID            | OPT.Firstname   | OPT.Address1 | OPT.City   | OPT.CountryCode | OPT.Postal | OPT.BankDetails   | OPT.IBAN                   | OPT.QR_IBAN                |
      | bpImportValue | Import Company  | CHE-108.019.127 MWST | firstnameImport | addr1Import  | cityImport | DE              | 1101       | bankDetailsImport | CH87 0483 5056 8223 8100 0 | CH81 3100 0056 8223 8100 0 |

    When the metasfresh REST-API endpoint path 'api/v2/import/text?dataImportConfig=bpDataImportConfig&runSynchronous=true' receives a 'POST' request with the payload from context and responds with '200' status code

    Then I_I_BPartner is found:
      | I_BPartner_ID.Identifier | C_DataImport_ID.Identifier | OPT.BPValue   | OPT.Companyname | OPT.BankDetails   | OPT.IBAN                   | OPT.QR_IBAN                |
      | bpImported               | bpDataImportConfig         | bpImportValue | Import Company  | bankDetailsImport | CH87 0483 5056 8223 8100 0 | CH81 3100 0056 8223 8100 0 |

    And validate I_I_BPartner record:
      | I_BPartner_ID.Identifier | I_IsImported | Processed | C_DataImport_ID.Identifier | OPT.BPValue   | OPT.Companyname | OPT.TaxID            | OPT.Firstname   | OPT.Address1 | OPT.City   | OPT.CountryCode | OPT.Postal | OPT.BankDetails   | OPT.IBAN                   | OPT.QR_IBAN                | OPT.C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | OPT.C_BP_BankAccount_ID.Identifier |
      | bpImported               | Y            | Y         | bpDataImportConfig         | bpImportValue | Import Company  | CHE-108.019.127 MWST | firstnameImport | addr1Import  | cityImport | DE              | 1101       | bankDetailsImport | CH87 0483 5056 8223 8100 0 | CH81 3100 0056 8223 8100 0 | importedBPartner             | importedBPLocation                    | importedBPBankAccount              |

    And validate C_BPartner:
      | C_BPartner_ID.Identifier | Value         | OPT.CompanyName | OPT.VATaxID          | OPT.Firstname   |
      | importedBPartner         | bpImportValue | Import Company  | CHE-108.019.127 MWST | firstnameImport |
    And validate C_BPartner_Location:
      | C_BPartner_Location_ID.Identifier | C_BPartner_ID.Identifier | OPT.Address                  | OPT.Name                              |
      | importedBPLocation                | importedBPartner         | addr1Import\n1101 cityImport | cityImport addr1Import Import Company |
    And validate C_BP_BankAccount:
      | C_BP_BankAccount_ID.Identifier | C_BPartner_ID.Identifier | OPT.Description   | OPT.IBAN                   | OPT.QR_IBAN                |
      | importedBPBankAccount          | importedBPartner         | bankDetailsImport | CH87 0483 5056 8223 8100 0 | CH81 3100 0056 8223 8100 0 |
