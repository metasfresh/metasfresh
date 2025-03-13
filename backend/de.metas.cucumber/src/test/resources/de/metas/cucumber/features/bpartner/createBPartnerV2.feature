@from:cucumber
@ghActions:run_on_executor3
Feature: create or update BPartner v2
  As a user
  I want create or update a BPartner record

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'

  @from:cucumber
  Scenario: create a BPartner record
    When a 'PUT' request with the below payload is sent to the metasfresh REST-API 'api/v2/bpartner/001' and fulfills with '201' status code
    """
{
   "requestItems":[
      {
         "bpartnerIdentifier":"ext-ALBERTA-001",
         "externalReferenceUrl":"www.ExternalReferenceURL.com",
         "bpartnerComposite":{
            "bpartner":{
               "code":"test_code1",
               "name":"test_name",
               "companyName":"test_company",
               "parentId":null,
               "phone":null,
               "language":"de",
               "url":null,
               "group":"test-group",
               "vatId":null
            },
            "locations":{
               "requestItems":[
                  {
                     "locationIdentifier":"gln-l11",
                     "location":{
                        "address1":"test_address1",
                        "address2":"test_address2",
                        "poBox":null,
                        "district":null,
                        "region":null,
                        "city":null,
                        "countryCode":"DE",
                        "gln":null,
                        "postal":null
                     }
                  },
                  {
                     "locationIdentifier":"gln-l22",
                     "location":{
                        "address1":null,
                        "address2":"test_address2",
                        "poBox":"test_poBox",
                        "district":null,
                        "region":"test_region",
                        "city":"test_city",
                        "countryCode":"DE",
                        "gln":null,
                        "postal":null
                     }
                  }
               ]
            },
            "contacts":{
               "requestItems":[
                  {
                     "contactIdentifier":"ext-ALBERTA-c11",
                     "contact":{
                        "code":"c11",
                        "name":"test_name_c11",
                        "email":"test_email",
                        "fax":"fax",
                        "invoiceEmailEnabled" : false
                     }
                  },
                  {
                     "contactIdentifier":"ext-ALBERTA-c22",
                     "contact":{
                        "code":"c22",
                        "name":"test_name_c22",
                        "email":null,
                        "fax":"test_fax",
                        "invoiceEmailEnabled" : true
                     }
                  }
               ]
            }
         }
      }
   ],
   "syncAdvise":{
      "ifNotExists":"CREATE",
      "ifExists":"UPDATE_MERGE"
   }
}
"""
    Then verify that bPartner was created for externalIdentifier
      | C_BPartner_ID.Identifier | externalIdentifier | OPT.Code   | Name      | OPT.CompanyName | OPT.ParentId | OPT.Phone | OPT.Language | OPT.Url | OPT.Group  | OPT.VatId |
      | created_bpartner         | ext-ALBERTA-001    | test_code1 | test_name | test_company    | null         | null      | de           | null    | test-group | null      |
    And verify that location was created for bpartner
      | bpartnerIdentifier | locationIdentifier | OPT.Address1  | OPT.Address2  | OPT.PoBox  | OPT.District | OPT.Region  | OPT.City  | CountryCode | OPT.Gln | OPT.Postal |
      | ext-ALBERTA-001    | gln-l11            | test_address1 | test_address2 | null       | null         | null        | null      | DE          | l11     | null       |
      | ext-ALBERTA-001    | gln-l22            | null          | test_address2 | test_poBox | null         | test_region | test_city | DE          | l22     | null       |
    And verify that contact was created for bpartner
      | bpartnerIdentifier | contactIdentifier | Name          | OPT.Email  | OPT.Fax  | Code | OPT.InvoiceEmailEnabled |
      | ext-ALBERTA-001    | ext-ALBERTA-c11   | test_name_c11 | test_email | fax      | c11  | false                   |
      | ext-ALBERTA-001    | ext-ALBERTA-c22   | test_name_c22 | null       | test_fax | c22  | true                    |
    And verify that S_ExternalReference was created
      | ExternalSystem | Type     | ExternalReference | ExternalReferenceURL         |
      | ALBERTA        | BPartner | 001               | www.ExternalReferenceURL.com |

  Scenario: Update a BPartner record
    When a 'PUT' request with the below payload is sent to the metasfresh REST-API 'api/v2/bpartner/001' and fulfills with '201' status code
    """
{
   "requestItems":[
      {
         "bpartnerIdentifier":"ext-ALBERTA-001",
         "bpartnerComposite":{
            "bpartner":{
               "code":"test_code_updated",
               "name":"test_name_updated",
               "companyName":"test_company",
               "parentId":null,
               "phone":null,
               "language":"de",
               "url":"url_updated",
               "group":"test-group",
               "vatId":null
            }
         }
      }
   ],
   "syncAdvise":{
      "ifNotExists":"CREATE",
      "ifExists":"UPDATE_MERGE"
   }
}
"""
    Then verify that bPartner was updated for externalIdentifier
      | C_BPartner_ID.Identifier | externalIdentifier | OPT.Code          | Name              | OPT.CompanyName | OPT.ParentId | OPT.Phone | OPT.Language | OPT.Url     | OPT.Group  | OPT.VatId |
      | created_bpartner         | ext-ALBERTA-001    | test_code_updated | test_name_updated | test_company    | null         | null      | de           | url_updated | test-group | null      |

  Scenario: Update a BPartner contact record
    When a 'PUT' request with the below payload is sent to the metasfresh REST-API 'api/v2/bpartner/001' and fulfills with '201' status code
    """
{
   "requestItems":[
      {
         "bpartnerIdentifier":"ext-ALBERTA-001",
         "bpartnerComposite":{
            "contacts":{
               "requestItems":[
                  {
                     "contactIdentifier":"ext-ALBERTA-c11",
                     "contact":{
                        "code":"c11",
                        "name":"test_name_c11_updated",
                        "email":"test_email_updated",
                        "fax":"fax_updated",
                        "invoiceEmailEnabled" : true
                     }
                  }
               ]
            }
         }
      }
   ],
   "syncAdvise":{
      "ifNotExists":"CREATE",
      "ifExists":"UPDATE_MERGE"
   }
}
"""
    Then verify that contact was updated for bpartner
      | bpartnerIdentifier | contactIdentifier | Name                  | OPT.Email          | OPT.Fax     | Code | OPT.InvoiceEmailEnabled |
      | ext-ALBERTA-001    | ext-ALBERTA-c11   | test_name_c11_updated | test_email_updated | fax_updated | c11  | true                    |

  Scenario: Update a BPartner contact record and Create another contact record
    When a 'PUT' request with the below payload is sent to the metasfresh REST-API 'api/v2/bpartner/001' and fulfills with '201' status code
    """
{
   "requestItems":[
      {
         "bpartnerIdentifier":"ext-ALBERTA-001",
         "bpartnerComposite":{
            "contacts":{
               "requestItems":[
                  {
                     "contactIdentifier":"ext-ALBERTA-c11",
                     "contact":{
                        "code":"c11",
                        "name":"test_name_c11_updated_again",
                        "email":"test_email_updated_again",
                        "fax":"fax_updated_again",
                        "invoiceEmailEnabled" : false
                     }
                  },
                  {
                     "contactIdentifier":"ext-ALBERTA-c33",
                     "contact":{
                        "code":"c33",
                        "name":"test_name_c33_created",
                        "email":"test_email_created",
                        "fax":"fax_created",
                        "invoiceEmailEnabled" : true
                     }
                  }
               ],
               "syncAdvise":{
      "ifNotExists":"CREATE",
      "ifExists":"DONT_UPDATE"
   }
            }
         }
      }
   ],
   "syncAdvise":{
      "ifNotExists":"CREATE",
      "ifExists":"DONT_UPDATE"
   }
}
"""
    Then verify that contact was not modified for bpartner
      | bpartnerIdentifier | contactIdentifier | Name                  | OPT.Email          | OPT.Fax     | Code | OPT.InvoiceEmailEnabled |
      | ext-ALBERTA-001    | ext-ALBERTA-c11   | test_name_c11_updated | test_email_updated | fax_updated | c11  | true                    |

    And verify that contact was created for bpartner
      | bpartnerIdentifier | contactIdentifier | Name                  | OPT.Email          | OPT.Fax     | Code | OPT.InvoiceEmailEnabled |
      | ext-ALBERTA-001    | ext-ALBERTA-c33   | test_name_c33_created | test_email_created | fax_created | c22  | true                    |

  @Id:S0285_700
  Scenario: Create BPartner Account record,
  using all supported external identifier formats:
  - external reference
  - iban
  - qr_iban
  validating:
  - bank account creation
  - external external reference creation
  -> then update account records
  -> then validate retrieval
  -> then update account record with IfNotExists = FAIL & unknown identifier

    When a 'PUT' request with the below payload is sent to the metasfresh REST-API 'api/v2/bpartner/001' and fulfills with '201' status code
    """
{
  "requestItems": [
    {
      "bpartnerIdentifier": "ext-ALBERTA-001",
      "bpartnerComposite": {
        "bpartner": {
          "code": "test_code_updated",
          "name": "test_name_updated",
          "companyName": "test_name_updated",
          "parentId": null,
          "phone": null,
          "language": "de",
          "url": "url_updated",
          "group": "test-group",
          "vatId": null,
          "urproduzent": false
        },
        "bankAccounts": {
          "requestItems": [
            {
              "iban": "DE15500105171114521777",
              "currencyCode": "EUR",
              "active": false,
              "accountName": "test-accountName_1",
              "accountStreet": "test-accountStreet_1",
              "accountZip": "test-accountZip_1",
              "accountCity": "test-accountCity_1",
              "accountCountry": "test-accountCountry_1" 
            },
            {
              "iban": "DE54500105178721351673",
              "currencyCode": "EUR",
              "active": false,
              "accountName": "test-accountName_2",
              "accountStreet": "test-accountStreet_2",
              "accountZip": "test-accountZip_2"
            },
            {
              "iban": "DE26500105174427157327",
              "currencyCode": "EUR",
              "name": "bank_account_S0285_700_3",
              "active": true,
              "accountCity": "test-accountCity_3",
              "accountCountry": "test-accountCountry_3"
            }
          ]
        }
      }
    }
  ],
  "syncAdvise": {
    "ifNotExists": "CREATE",
    "ifExists": "UPDATE_MERGE"
  }
}
"""
    Then the metasfresh REST-API responds with
    """
    {
  "responseItems": [
    {
      "responseBankAccountItems": [
        {
          "identifier": "DE15500105171114521777",
          "syncOutcome": "CREATED"
        },
        {
          "identifier": "DE54500105178721351673",
          "syncOutcome": "CREATED"
        },
        {
          "identifier": "DE26500105174427157327",
          "syncOutcome": "CREATED"
        }
      ]
    }
  ]
}
    """
    And verify that bPartner was updated for externalIdentifier
      | C_BPartner_ID.Identifier | externalIdentifier | Name              |
      | bpartner                 | ext-ALBERTA-001    | test_name_updated |
    And locate C_BP_BankAccount by IBAN:
      | C_BP_BankAccount_ID           | BankAccountExternalIdentifier  | BPartnerExternalIdentifier |
      | BPA_Via_ExternalRef_S0285_700 | ext-ALBERTA-BPACCT_S0285_700_1 | ext-ALBERTA-001            |
      | BPA_Via_IBAN_S0285_700        | iban-DE54500105178721351673    | ext-ALBERTA-001            |
      | BPA_Via_QR_IBAN_S0285_700     | qr_iban-DE91500105177122223557 | ext-ALBERTA-001            |
    And validate C_BP_BankAccount:
      | C_BP_BankAccount_ID           | C_BPartner_ID | IBAN                   | ISO_Code | IsActive | A_Name             | A_Street             | A_Zip             | A_City             | A_Country             |
      | BPA_Via_ExternalRef_S0285_700 | bpartner      | DE15500105171114521777 | EUR      | false    | test-accountName_1 | test-accountStreet_1 | test-accountZip_1 | test-accountCity_1 | test-accountCountry_1 |
      | BPA_Via_IBAN_S0285_700        | bpartner      | DE54500105178721351673 | EUR      | false    | test-accountName_2 | test-accountStreet_2 | test-accountZip_2 | null               | null                  |
      | BPA_Via_QR_IBAN_S0285_700     | bpartner      | DE26500105174427157327 | EUR      | true     | null               | null                 | null              | test-accountCity_3 | test-accountCountry_3 |

    ## update bank accounts
    When a 'PUT' request with the below payload is sent to the metasfresh REST-API 'api/v2/bpartner/001' and fulfills with '201' status code
    """
{
  "requestItems": [
    {
      "bpartnerIdentifier": "ext-ALBERTA-001",
      "bpartnerComposite": {
        "bankAccounts": {
          "requestItems": [
            {
              "iban": "DE15500105171114521777",
              "currencyCode": "EUR",
              "active": false,
              "accountName": "test-accountName_1_updated",
              "accountStreet": "test-accountStreet_1_updated",
              "accountZip": "test-accountZip_1_up",
              "accountCity": "test-accountCity_1_updated",
              "accountCountry": "test-accountCountry_1_updated" 
            },
            {
              "iban": "DE54500105178721351673",
              "currencyCode": "EUR",
              "active": false,
              "accountName": "test-accountName_2_updated",
              "accountStreet": "test-accountStreet_2_updated",
              "accountZip": "test-accountZip_2_up",
              "accountCity": "test-accountCity_2"
            },
            {
              "iban": "DE26500105174427157327",
              "currencyCode": "EUR",
              "active": true,
              "accountZip": "test-accountZip_3",
              "accountCity": "test-accountCity_3_updated",
              "accountCountry": "test-accountCountry_3_updated"
            }
          ]
        }
      }
    }
  ],
  "syncAdvise": {
    "ifNotExists": "FAIL",
    "ifExists": "UPDATE_MERGE"
  }
}
"""
    Then the metasfresh REST-API responds with
    """
    {
  "responseItems": [
    {
      "responseBankAccountItems": [
        {
          "identifier": "DE15500105171114521777",
          "syncOutcome": "UPDATED"
        },
        {
          "identifier": "DE54500105178721351673",
          "syncOutcome": "UPDATED"
        },
        {
          "identifier": "DE26500105174427157327",
          "syncOutcome": "UPDATED"
        }
      ]
    }
  ]
}
    """
    And validate C_BP_BankAccount:
      | C_BP_BankAccount_ID           | C_BPartner_ID | IBAN                   | ISO_Code | IsActive | A_Name                     | A_Street                     | A_Zip                | A_City                     | A_Country                     |
      | BPA_Via_ExternalRef_S0285_700 | bpartner      | DE15500105171114521777 | EUR      | false    | test-accountName_1_updated | test-accountStreet_1_updated | test-accountZip_1_up | test-accountCity_1_updated | test-accountCountry_1_updated |
      | BPA_Via_IBAN_S0285_700        | bpartner      | DE54500105178721351673 | EUR      | false    | test-accountName_2_updated | test-accountStreet_2_updated | test-accountZip_2_up | test-accountCity_2         | null                          |
      | BPA_Via_QR_IBAN_S0285_700     | bpartner      | DE26500105174427157327 | EUR      | true     | null                       | null                         | test-accountZip_3    | test-accountCity_3_updated | test-accountCountry_3_updated |


    When the metasfresh REST-API endpoint path 'api/v2/bpartner/ext-ALBERTA-001' receives a 'GET' request with the headers from context, expecting status='200'
    Then the metasfresh REST-API responds with
    """
  {
  "bankAccounts": [
    {
      "currencyId": 102,
      "iban": "DE15500105171114521777",
      "active": false,
      "accountName": "test-accountName_1_updated",
      "accountStreet": "test-accountStreet_1_updated",
      "accountZip": "test-accountZip_1_up",
      "accountCity": "test-accountCity_1_updated",
      "accountCountry": "test-accountCountry_1_updated" 
    },
    {
      "currencyId": 102,
      "iban": "DE54500105178721351673",
      "active": false,
      "accountName": "test-accountName_2_updated",
      "accountStreet": "test-accountStreet_2_updated",
      "accountZip": "test-accountZip_2_up",
      "accountCity": "test-accountCity_2"
    },
    {
      "currencyId": 102,
      "iban": "DE26500105174427157327",
      "active": true,
      "accountZip": "test-accountZip_3",
      "accountCity": "test-accountCity_3_updated",
      "accountCountry": "test-accountCountry_3_updated"
    }
  ]
}
    """
    When a 'PUT' request with the below payload is sent to the metasfresh REST-API 'api/v2/bpartner/001' and fulfills with '422' status code
    """
{
  "requestItems": [
    {
      "bpartnerIdentifier": "ext-ALBERTA-001",
      "bpartnerComposite": {
        "bankAccounts": {
          "requestItems": [
            {
              "iban": "DOESNT_MATTER",
              "currencyCode": "EUR"
            }
          ]
        }
      }
    }
  ],
  "syncAdvise": {
    "ifNotExists": "FAIL",
    "ifExists": "UPDATE_MERGE"
  }
}
"""
