@Id:S0285
@from:cucumber
@ghActions:run_on_executor3
Feature: create or update BPartner v2
  As a user
  I want create or update a BPartner record

  # IMPORTANT: the different scenarios assume depend on each other, so you can't run just one of them (pls fix when time)
  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'

  @Id:S0285_100
  @S0405
  @from:cucumber
  Scenario: create a BPartner record
    And create M_SectionCode:
      | M_SectionCode_ID.Identifier | M_SectionCode_ID | Value                   |
      | ALBERTA_001_sectionCode     | 1234567          | ALBERTA_001_sectionCode |
    And load C_Incoterms by id:
      | C_Incoterms_ID.Identifier | C_Incoterms_ID |
      | Incoterms_Customer_101122 | 1000000        |
      | Incoterms_Vendor_101122   | 1000001        |
    And validate C_Incoterms:
      | C_Incoterms_ID.Identifier | Value | Name                  |
      | Incoterms_Customer_101122 | DAF   | DAF - frei Grenze     |
      | Incoterms_Vendor_101122   | DDU   | DDU - frei unverzollt |
    And load C_PaymentTerm by id:
      | C_PaymentTerm_ID.Identifier | C_PaymentTerm_ID |
      | PaymentTerm_101122          | 1000009          |
      | PaymentTerm_PO_101122       | 1000013          |
    And validate C_PaymentTerm:
      | C_PaymentTerm_ID.Identifier | Value       | Name        |
      | PaymentTerm_101122          | 10 Tage 1 % | 10 Tage 1 % |
      | PaymentTerm_PO_101122       | 10 Tage 4%  | 10 Tage 4%  |
    And metasfresh contains C_BPartners:
      | Identifier          | Name                          |
      | sectionGroupPartner | sectionGroupPartnerIdentifier |
    And metasfresh contains S_ExternalReference:
      | S_ExternalReference_ID.Identifier | ExternalSystem | ExternalReference | Type     | OPT.C_BPartner_ID.Identifier |
      | sectionGroupPartner               | ALBERTA        | bp2212            | BPartner | sectionGroupPartner          |

    When a 'PUT' request with the below payload is sent to the metasfresh REST-API 'api/v2/bpartner/001' and fulfills with '201' status code
    """

{
   "requestItems":[
      {
         "bpartnerIdentifier":"ext-ALBERTA-001",
         "externalReferenceUrl":"www.ExternalReferenceURL.com",
         "externalSystemConfigId": 540000,
         "isReadOnlyInMetasfresh": false,
         "bpartnerComposite":{
            "bpartner":{
               "code":"test_code_211122",
               "name":"test_name",
               "companyName":"test_company",
               "parentId":null,
               "phone":null,
               "language":"de",
               "url":null,
               "group":"test-group",
               "priceListId": 2008396,
               "vatId": "vatId_BPartner001",
               "sectionCodeValue":"ALBERTA_001_sectionCode",
               "description":"ALBERTA BPartner Description",
               "deliveryRule":"Availability",
               "deliveryViaRule":"Shipper",
               "storageWarehouse": true,
               "customerPaymentTermIdentifier": "val-10 Tage 1 %",
               "vendorPaymentTermIdentifier": "val-10 Tage 4%",
               "incotermsCustomerValue":"DAF",
               "incotermsVendorValue":"DDU",
               "paymentRule":"OnCredit",
               "paymentRulePO":"Cash",
               "sectionGroupPartnerIdentifier":"ext-ALBERTA-bp2212",
               "prospect":true,
               "urproduzent":true
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
                        "postal":null,
                        "handoverLocation":true,
                        "remitTo":false,
                        "replicationLookupDefault":false,
                        "vatId": null,
                        "sapPaymentMethod":"PAY"
                     }
                  },
                  {
                     "locationIdentifier":"ext-ALBERTA-l22",
                     "externalSystemConfigId": 540000,
                     "isReadOnlyInMetasfresh": true,
                     "location":{
                        "address1":null,
                        "address2":"test_address2",
                        "poBox":"test_poBox",
                        "district":null,
                        "region":"test_region",
                        "city":"test_city",
                        "countryCode":"DE",
                        "gln":null,
                        "postal":null,
                        "vatId": "vatId_Location_l22"
                     }
                  },
                  {
                     "locationIdentifier":"ext-Other-l33",
                     "isReadOnlyInMetasfresh": false,
                     "location":{
                        "name":"test_location_name",
                        "bpartnerName":"test_partner_name",
                        "address1":"test_address1",
                        "poBox":null,
                        "district":null,
                        "region":"test_region",
                        "city":"test_city",
                        "countryCode":"DE",
                        "gln":null,
                        "postal":null,
                        "vatId":null
                     }
                  },
                     {
                     "locationIdentifier":"ext-Other-l44",
                     "isReadOnlyInMetasfresh": false,
                     "location":{
                        "name":"test_location_name2",
                        "bpartnerName":"test_partner_name2",
                        "address1":"test_address1",
                        "poBox":null,
                        "district":null,
                        "region":"test_region",
                        "city":"test_city",
                        "countryCode":"DE",
                        "gln":null,
                        "postal":null,
                        "vatId":null
                     }
                  }
               ]
            },
            "contacts":{
               "requestItems":[
                  {
                     "contactIdentifier":"ext-ALBERTA-c11",
                     "externalSystemConfigId": 540000,
                     "isReadOnlyInMetasfresh": true,
                     "contact":{
                        "code":"c11",
                        "name":"test_name_c11",
                        "email":"test_email",
                        "fax":"fax",
                        "invoiceEmailEnabled" : false,
                        "greeting": {
                          "greetingInfo": {
                            "greeting": "test_greeting_261023",
                            "letterSalutation": "test_salutation_261023",
                            "name": "test_name_261023"
                          },
                          "identifier": "ext-ALBERTA-greetingTest261023"
                        }
                     }
                  },
                  {
                     "contactIdentifier":"ext-ALBERTA-c22",
                     "externalSystemConfigId": 540000,
                     "isReadOnlyInMetasfresh": true,
                     "contact":{
                        "code":"c22",
                        "name":"test_name_c22",
                        "email":null,
                        "fax":"test_fax",
                        "invoiceEmailEnabled" : true
                     }
                  }
               ]
            },
            "creditLimits": {
              "requestItems": [
                {
                  "type": "Insurance",
                  "orgCode": "001",
                  "creditLimitMetasfreshId": null,
                  "dateFrom": "2022-10-31",
                  "amount": {
                    "amount": "20.5",
                    "currencyCode": "EUR"
                  },
                  "active": true,
                  "processed": false,
                  "approvedBy": 100
                },
                {
                  "type": "Insurance",
                  "orgCode": "001",
                  "creditLimitMetasfreshId": null,
                  "dateFrom": "2022-10-30",
                  "amount": {
                    "amount": "10",
                    "currencyCode": "CHF"
                  },
                  "active": false,
                  "processed": true
                }
              ],
              "syncAdvise": {
                "ifNotExists": "CREATE",
                "ifExists": "UPDATE_MERGE"
              }
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
      | C_BPartner_ID.Identifier | externalIdentifier | OPT.Code         | Name      | OPT.CompanyName | OPT.ParentId | OPT.Phone | OPT.Language | OPT.Url | OPT.Group  | OPT.VatId         | OPT.M_SectionCode_ID.Identifier | OPT.DeliveryRule | OPT.DeliveryViaRule | OPT.C_Incoterms_Customer_ID.Identifier | OPT.C_Incoterms_Vendor_ID.Identifier | OPT.PaymentRule | OPT.PaymentRulePO | OPT.IsStorageWarehouse | OPT.C_PaymentTerm_ID.Identifier | OPT.PO_PaymentTerm_ID.Identifier | OPT.Section_Group_Partner_ID.Identifier | OPT.IsProspect | OPT.Fresh_Urproduzent |
      | created_bpartner         | ext-ALBERTA-001    | test_code_211122 | test_name | test_company    | null         | null      | de           | null    | test-group | vatId_BPartner001 | ALBERTA_001_sectionCode         | A                | S                   | Incoterms_Customer_101122              | Incoterms_Vendor_101122              | P               | B                 | Y                      | PaymentTerm_101122              | PaymentTerm_PO_101122            | sectionGroupPartner                     | true           | true                  |
    And verify that location was created for bpartner
      | bpartnerIdentifier | locationIdentifier | OPT.Address1  | OPT.Address2  | OPT.PoBox  | OPT.District | OPT.Region  | OPT.City  | CountryCode | OPT.Gln | OPT.Postal | OPT.IsHandOverLocation | OPT.IsRemitTo | OPT.IsReplicationLookupDefault | OPT.VATaxId        | OPT.SAP_PaymentMethod | OPT.Name                | OPT.BPartnerName   |
      | ext-ALBERTA-001    | gln-l11            | test_address1 | test_address2 | null       | null         | null        | null      | DE          | l11     | null       | true                   | false         | false                          | null               | PAY                   | test_address1 test_name | null               |
      | ext-ALBERTA-001    | ext-ALBERTA-l22    | null          | test_address2 | test_poBox | null         | test_region | test_city | DE          | null    | null       |                        |               |                                | vatId_Location_l22 |                       | test_city test_name     | null               |
      | ext-ALBERTA-001    | ext-Other-l33      | test_address1 | null          | null       | null         | test_region | test_city | DE          | null    | null       |                        |               |                                | null               |                       | test_location_name      | test_partner_name  |
      | ext-ALBERTA-001    | ext-Other-l44      | test_address1 | null          | null       | null         | test_region | test_city | DE          | null    | null       |                        |               |                                | null               |                       | test_location_name2     | test_partner_name2 |
    And verify that contact was created for bpartner
      | bpartnerIdentifier | contactIdentifier | Name          | OPT.Email  | OPT.Fax  | Code | OPT.InvoiceEmailEnabled |
      | ext-ALBERTA-001    | ext-ALBERTA-c11   | test_name_c11 | test_email | fax      | c11  | false                   |
      | ext-ALBERTA-001    | ext-ALBERTA-c22   | test_name_c22 | null       | test_fax | c22  | true                    |
    And verify that credit limit was created for bpartner: created_bpartner
      | Amount | IsActive | C_CreditLimit_Type.Name | OPT.DateFrom | Processed | OPT.ApprovedBy_ID |
      | 23.17  | true     | Insurance               | 2022-10-31   | false     | 100               |
      | 10     | false    | Insurance               | 2022-10-30   | true      | null              |
    And verify that S_ExternalReference was created
      | ExternalSystem | Type             | ExternalReference  | OPT.ExternalReferenceURL     | OPT.ExternalSystem_Config_ID | OPT.IsReadOnlyInMetasfresh |
      | ALBERTA        | BPartner         | 001                | www.ExternalReferenceURL.com | 540000                       | false                      |
      | ALBERTA        | BPartnerLocation | l22                | null                         | 540000                       | true                       |
      | ALBERTA        | UserID           | c11                | null                         | 540000                       | true                       |
      | ALBERTA        | UserID           | c22                | null                         | 540000                       | true                       |
      | ALBERTA        | Greeting         | greetingTest261023 | null                         |                              |                            |
    And validate C_BPartner_Stats
      | C_BPartner_ID.Identifier | OPT.SOCreditStatus | OPT.SO_CreditUsed |
      | created_bpartner         | W                  | 0                 |

    And build BPartner Endpoint Path and store it in context
      | C_BPartner_ID.Identifier |
      | created_bpartner         |

    When a 'GET' request is sent to metasfresh REST-API with endpointPath from context and fulfills with '200' status code
    Then the metasfresh REST-API responds with
    """
{
   "bpartner":{
      "code":"test_code_211122",
      "globalId":null,
      "active":true,
      "name":"test_name",
      "companyName":"test_company",
      "language":"de_DE",
      "group":"test-group",
      "vendor":false,
      "customer":false,
      "paymentRule":"OnCredit",
      "paymentRulePO":"Cash",
      "company":true,
      "vatId": "vatId_BPartner001",
      "sectionCodeId":1234567,
      "description":"ALBERTA BPartner Description",
      "deliveryRule":"Availability",
      "deliveryViaRule":"Shipper",
      "storageWarehouse":true,
      "incotermsCustomerId":1000000,
      "incotermsVendorId":1000001,
      "customerPaymentTermId":1000009,
      "vendorPaymentTermId":1000013,
      "urproduzent":true
   },
   "locations":[
      {
         "name":"test_address1 test_name",
         "bpartnerName":null,
         "active":true,
         "address1":"test_address1",
         "address2":"test_address2",
         "postal":null,
         "city":null,
         "gln":"l11",
         "countryCode":"DE",
         "shipTo":true,
         "shipToDefault":false,
         "billTo":true,
         "billToDefault":false,
         "ephemeral":false,
         "visitorsAddress":false,
         "handoverLocation":true,
         "remitTo":false,
         "replicationLookupDefault":false,
         "vatId": null
      },
      {
         "name":"test_city test_name",
         "bpartnerName":null,
         "active":true,
         "address2":"test_address2",
         "postal":null,
         "poBox":"test_poBox",
         "region":"test_region",
         "city":"test_city",
         "gln":null,
         "countryCode":"DE",
         "shipTo":true,
         "shipToDefault":false,
         "billTo":true,
         "billToDefault":false,
         "ephemeral":false,
         "visitorsAddress":false,
         "handoverLocation":false,
         "remitTo":false,
         "replicationLookupDefault":false,
         "vatId": "vatId_Location_l22"
      },
      {
         "name":"test_location_name",
         "bpartnerName":"test_partner_name",
         "active":true,
         "address1":"test_address1",
         "postal":null,
         "region":"test_region",
         "city":"test_city",
         "gln":null,
         "countryCode":"DE",
         "shipTo":true,
         "shipToDefault":false,
         "billTo":true,
         "billToDefault":false,
         "ephemeral":false,
         "visitorsAddress":false,
         "handoverLocation":false,
         "remitTo":false,
         "replicationLookupDefault":false,
         "vatId":null
      },
      {
         "name":"test_location_name2",
         "bpartnerName":"test_partner_name2",
         "active":true,
         "address1":"test_address1",
         "postal":null,
         "region":"test_region",
         "city":"test_city",
         "gln":null,
         "countryCode":"DE",
         "shipTo":true,
         "shipToDefault":false,
         "billTo":true,
         "billToDefault":false,
         "ephemeral":false,
         "visitorsAddress":false,
         "handoverLocation":false,
         "remitTo":false,
         "replicationLookupDefault":false,
         "vatId":null
      }
   ],
   "contacts":[
      {
         "active":true,
         "name":"test_name_c11",
         "email":"test_email",
         "fax":"fax",
         "newsletter":false,
         "shipToDefault":false,
         "billToDefault":false,
         "defaultContact":false,
         "sales":false,
         "salesDefault":false,
         "purchase":false,
         "purchaseDefault":false,
         "subjectMatter":false,
         "invoiceEmailEnabled":false,
         "greeting": {
           "name": "test_name_261023",
           "greeting": "test_greeting_261023",
           "letterSalutation": "test_salutation_261023"
         }
      },
      {
         "active":true,
         "name":"test_name_c22",
         "fax":"test_fax",
         "newsletter":false,
         "shipToDefault":false,
         "billToDefault":false,
         "defaultContact":false,
         "sales":false,
         "salesDefault":false,
         "purchase":false,
         "purchaseDefault":false,
         "subjectMatter":false,
         "invoiceEmailEnabled":true
      }
   ]
}
    """

  @Id:S0285_200
  @S0405
  Scenario: Update a BPartner record - DEPENDS ON PREDECESSOR
    When a 'PUT' request with the below payload is sent to the metasfresh REST-API 'api/v2/bpartner/001' and fulfills with '201' status code
    """
{
   "requestItems":[
      {
         "bpartnerIdentifier":"ext-ALBERTA-001",
         "externalReferenceUrl":"www.ExternalReferenceURL.com",
         "externalSystemConfigId": 540000,
         "isReadOnlyInMetasfresh": true,
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
               "vatId":null,
               "urproduzent":false,
               "storageWarehouse":true
            },
            "locations":{
               "requestItems":[
                  {
                     "locationIdentifier":"ext-ALBERTA-l22",
                     "isReadOnlyInMetasfresh": false,
                     "location":{
                        "address1":null,
                        "address2":"test_address2",
                        "poBox":"test_poBox",
                        "district":null,
                        "region":"test_region",
                        "city":"test_city",
                        "countryCode":"DE",
                        "gln":null,
                        "postal":null,
                        "vatId": null
                     }
                  },
                  {
                     "locationIdentifier":"ext-Other-l33",
                     "isReadOnlyInMetasfresh": false,
                     "location":{
                        "name":null,
                        "bpartnerName":null,
                        "address1":"test_address1",
                        "poBox":null,
                        "district":null,
                        "region":"test_region",
                        "city":"test_city",
                        "countryCode":"DE",
                        "gln":null,
                        "postal":null,
                        "vatId":null
                     }
                  },
                  {
                     "locationIdentifier":"ext-Other-l44",
                     "isReadOnlyInMetasfresh": false,
                     "location":{
                       "active":true,
                       "address1":"test_address1",
                       "postal":null,
                       "region":"test_region",
                       "city":"test_city",
                       "gln":null,
                       "countryCode":"DE",
                       "shipTo":true,
                       "shipToDefault":false,
                       "billTo":true,
                       "billToDefault":false,
                       "ephemeral":false,
                       "visitorsAddress":false,
                       "handoverLocation":false,
                       "remitTo":false,
                       "replicationLookupDefault":false,
                       "vatId":null
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
    Then verify that bPartner was updated for externalIdentifier
      | C_BPartner_ID.Identifier | externalIdentifier | OPT.Code          | Name              | OPT.CompanyName | OPT.ParentId | OPT.Phone | OPT.Language | OPT.Url     | OPT.Group  | OPT.VatId | OPT.IsStorageWarehouse | OPT.Fresh_Urproduzent |
      | created_bpartner         | ext-ALBERTA-001    | test_code_updated | test_name_updated | test_company    | null         | null      | de           | url_updated | test-group | null      | Y                      | false                 |
    And verify that location was updated for bpartner
      | bpartnerIdentifier | locationIdentifier | OPT.Address1  | OPT.Address2  | OPT.PoBox  | OPT.District | OPT.Region  | OPT.City  | CountryCode | OPT.Gln | OPT.Postal | OPT.VATaxId | OPT.Name                                  |
      | ext-ALBERTA-001    | ext-ALBERTA-l22    | null          | test_address2 | test_poBox | null         | test_region | test_city | DE          | null    | null       | null        | test_city test_name               |
      | ext-ALBERTA-001    | ext-Other-l33      | test_address1 | null          | null       | null         | test_region | test_city | DE          | null    | null       | null        | test_city test_address1 test_name_updated |
      | ext-ALBERTA-001    | ext-Other-l44      | test_address1 | null          | null       | null         | test_region | test_city | DE          | null    | null       | null        | test_location_name2                       |
    And verify that S_ExternalReference was created
      | ExternalSystem | Type             | ExternalReference | OPT.ExternalReferenceURL     | OPT.ExternalSystem_Config_ID | OPT.IsReadOnlyInMetasfresh |
      | ALBERTA        | BPartner         | 001               | www.ExternalReferenceURL.com | 540000                       | true                       |
      | ALBERTA        | BPartnerLocation | l22               | null                         | 540000                       | false                      |
      | ALBERTA        | UserID           | c11               | null                         | 540000                       | true                       |

  @Id:S0285_300
  Scenario: Update a BPartner contact record - DEPENDS ON PREDECESSOR
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
                     "isReadOnlyInMetasfresh": false,
                     "contact":{
                        "code":"c11",
                        "name":"test_name_c11_updated",
                        "email":"test_email_updated",
                        "fax":"fax_updated",
                        "invoiceEmailEnabled" : true,
                        "greeting": {
                          "greetingInfo": {
                            "greeting": "test_greeting_261023_updated",
                            "letterSalutation": "test_salutation_261023_updated",
                            "name": "test_name_261023_updated"
                          },
                          "identifier": "ext-ALBERTA-greetingCPTest1"
                        }
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
    And verify that S_ExternalReference was created
      | ExternalSystem | Type   | ExternalReference | OPT.IsReadOnlyInMetasfresh |
      | ALBERTA        | UserID | c11               | false                      |
    And the metasfresh REST-API endpoint path 'api/v2/bpartner/ext-ALBERTA-001' receives a 'GET' request
    Then the metasfresh REST-API responds with
"""
{
  "contacts": [
    {
      "greeting": {
        "name": "test_name_261023_updated",
        "greeting": "test_greeting_261023_updated",
        "letterSalutation": "test_salutation_261023_updated"
      }
    },
    {}
  ]
}
"""

  @Id:S0285_400
  @S0405
  Scenario: Update a BPartner contact record and Create another contact record - DEPENDS ON PREDECESSOR
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
                     "isReadOnlyInMetasfresh": true,
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
    And verify that S_ExternalReference was created
      | ExternalSystem | Type   | ExternalReference | OPT.IsReadOnlyInMetasfresh |
      | ALBERTA        | UserID | c11               | false                      |
      | ALBERTA        | UserID | c33               | false                      |

  @Id:S0285_500
  @from:cucumber
  Scenario: create BPartner with external reference type of code - orgCode set in path - DEPENDS ON PREDECESSOR

    Given load AD_Org:
      | AD_Org_ID.Identifier | Value |
      | providedOrg          | 001   |

    When a 'PUT' request with the below payload is sent to the metasfresh REST-API 'api/v2/bpartner/001' and fulfills with '201' status code
    """
{
   "requestItems":[
      {
         "bpartnerIdentifier":"ext-ALBERTA-bPartner1",
         "bpartnerComposite":{
            "bpartner":{
               "code":"ext-ALBERTA-BPartnerTestCode1",
               "name":"BPartnerTestName1",
               "companyName":"BPartnerTestCompany1",
               "language":"de",
               "group":"test-group"
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
    Then set context properties:
      | Key         | Value  |
      | #AD_Role_ID | 540024 |
    And verify that S_ExternalReference was created
      | ExternalSystem | Type          | ExternalReference | OPT.AD_Org_ID.Identifier |
      | ALBERTA        | BPartnerValue | BPartnerTestCode1 | providedOrg              |
      | ALBERTA        | BPartner      | bPartner1         | providedOrg              |
    And verify that bPartner was created for externalIdentifier
      | C_BPartner_ID.Identifier | externalIdentifier    | Name              | OPT.CompanyName      | OPT.AD_Org_ID.Identifier |
      | bpartner                 | ext-ALBERTA-bPartner1 | BPartnerTestName1 | BPartnerTestCompany1 | providedOrg              |

  @Id:S0285_600
  @from:cucumber
  Scenario: process CreateBPartner requests given - DEPENDS ON PREDECESSOR:
  _no orgCode in path
  _different orgCode set for each request item

    Given metasfresh contains AD_Org:
      | AD_Org_ID.Identifier | Name                   | Value    |
      | bPartner2_orgCode    | BPartner2 Organization | orgCode2 |
      | bPartner3_orgCode    | BPartner3 Organization | orgCode3 |

    When a 'PUT' request with the below payload is sent to the metasfresh REST-API 'api/v2/bpartner/' and fulfills with '201' status code
    """
{
   "requestItems":[
      {
         "bpartnerIdentifier":"ext-ALBERTA-bPartner2",
         "bpartnerComposite":{
            "orgCode": "orgCode2",
            "bpartner":{
               "code":"ext-ALBERTA-BPartnerTestCode2",
               "name":"BPartnerTestName2",
               "companyName":"BPartnerTestCompany2",
               "group":"test-group"
            }
         }
      },
      {
         "bpartnerIdentifier":"ext-ALBERTA-bPartner3",
         "bpartnerComposite":{
            "orgCode": "orgCode3",
            "bpartner":{
               "code":"ext-ALBERTA-BPartnerTestCode3",
               "name":"BPartnerTestName3",
               "companyName":"BPartnerTestCompany3",
               "language":"de",
               "group":"test-group"
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
    Then set context properties:
      | Key         | Value  |
      | #AD_Role_ID | 540024 |
    And verify that S_ExternalReference was created
      | ExternalSystem | Type          | ExternalReference | OPT.AD_Org_ID.Identifier |
      | ALBERTA        | BPartnerValue | BPartnerTestCode2 | bPartner2_orgCode        |
      | ALBERTA        | BPartner      | bPartner2         | bPartner2_orgCode        |
      | ALBERTA        | BPartnerValue | BPartnerTestCode3 | bPartner3_orgCode        |
      | ALBERTA        | BPartner      | bPartner3         | bPartner3_orgCode        |
    And verify that bPartner was created for externalIdentifier
      | C_BPartner_ID.Identifier | externalIdentifier    | Name              | OPT.CompanyName      | OPT.AD_Org_ID.Identifier |
      | bpartner2                | ext-ALBERTA-bPartner2 | BPartnerTestName2 | BPartnerTestCompany2 | bPartner2_orgCode        |
      | bpartner3                | ext-ALBERTA-bPartner3 | BPartnerTestName3 | BPartnerTestCompany3 | bPartner3_orgCode        |

  @Id:S0285_700
  Scenario: Create BPartner Account record, using all supported external identifier formats - DEPENDS ON PREDECESSOR?:
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
          "companyName": "test_company",
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
              "identifier": "ext-ALBERTA-BPACCT_S0285_700_1",
              "iban": "DE15500105171114521777",
              "qrIban": "DE34500105173193385568",
              "currencyCode": "EUR",
              "name": "bank_account_S0285_700_1",
              "isDefault": false,
              "active": false
            },
            {
              "identifier": "iban-DE54500105178721351673",
              "iban": "DE54500105178721351673",
              "qrIban": "DE96500105176155493434",
              "currencyCode": "EUR",
              "name": "bank_account_S0285_700_2",
              "isDefault": false,
              "active": false
            },
            {
              "identifier": "qr_iban-DE91500105177122223557",
              "iban": "DE26500105174427157327",
              "qrIban": "DE91500105177122223557",
              "currencyCode": "EUR",
              "name": "bank_account_S0285_700_3",
              "isDefault": true,
              "active": true
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
          "identifier": "ext-ALBERTA-BPACCT_S0285_700_1",
          "syncOutcome": "CREATED"
        },
        {
          "identifier": "iban-DE54500105178721351673",
          "syncOutcome": "CREATED"
        },
        {
          "identifier": "qr_iban-DE91500105177122223557",
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
    And locate C_BP_BankAccount by ExternalIdentifier:
      | C_BP_BankAccount_ID.Identifier | BankAccountExternalIdentifier  | BPartnerExternalIdentifier |
      | BPA_Via_ExternalRef_S0285_700  | ext-ALBERTA-BPACCT_S0285_700_1 | ext-ALBERTA-001            |
      | BPA_Via_IBAN_S0285_700         | iban-DE54500105178721351673    | ext-ALBERTA-001            |
      | BPA_Via_QR_IBAN_S0285_700      | qr_iban-DE91500105177122223557 | ext-ALBERTA-001            |
    And validate C_BP_BankAccount:
      | C_BP_BankAccount_ID.Identifier | C_BPartner_ID.Identifier | OPT.Name                 | OPT.IBAN               | OPT.QR_IBAN            | OPT.ISO_Code | OPT.IsActive | OPT.IsDefault |
      | BPA_Via_ExternalRef_S0285_700  | bpartner                 | bank_account_S0285_700_1 | DE15500105171114521777 | DE34500105173193385568 | EUR          | false        | false         |
      | BPA_Via_IBAN_S0285_700         | bpartner                 | bank_account_S0285_700_2 | DE54500105178721351673 | DE96500105176155493434 | EUR          | false        | false         |
      | BPA_Via_QR_IBAN_S0285_700      | bpartner                 | bank_account_S0285_700_3 | DE26500105174427157327 | DE91500105177122223557 | EUR          | true         | true          |
    And verify that S_ExternalReference was created
      | ExternalSystem | Type        | ExternalReference  |
      | ALBERTA        | BankAccount | BPACCT_S0285_700_1 |

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
              "identifier": "ext-ALBERTA-BPACCT_S0285_700_1",
              "iban": "DE15500105171114521777",
              "qrIban": "DE34500105173193385568",
              "currencyCode": "EUR",
              "name": "bank_account_S0285_700_1_updated",
              "isDefault": false,
              "active": false
            },
            {
              "identifier": "iban-DE54500105178721351673",
              "iban": "DE54500105178721351673",
              "qrIban": "DE96500105176155493434",
              "currencyCode": "EUR",
              "name": "bank_account_S0285_700_2_updated",
              "isDefault": false,
              "active": false
            },
            {
              "identifier": "qr_iban-DE91500105177122223557",
              "iban": "DE26500105174427157327",
              "qrIban": "DE91500105177122223557",
              "currencyCode": "EUR",
              "name": "bank_account_S0285_700_3_updated",
              "isDefault": true,
              "active": true
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
          "identifier": "ext-ALBERTA-BPACCT_S0285_700_1",
          "syncOutcome": "UPDATED"
        },
        {
          "identifier": "iban-DE54500105178721351673",
          "syncOutcome": "UPDATED"
        },
        {
          "identifier": "qr_iban-DE91500105177122223557",
          "syncOutcome": "UPDATED"
        }
      ]
    }
  ]
}
    """
    And validate C_BP_BankAccount:
      | C_BP_BankAccount_ID.Identifier | C_BPartner_ID.Identifier | OPT.Name                         | OPT.IBAN               | OPT.QR_IBAN            | OPT.ISO_Code | OPT.IsActive | OPT.IsDefault |
      | BPA_Via_ExternalRef_S0285_700  | bpartner                 | bank_account_S0285_700_1_updated | DE15500105171114521777 | DE34500105173193385568 | EUR          | false        | false         |
      | BPA_Via_IBAN_S0285_700         | bpartner                 | bank_account_S0285_700_2_updated | DE54500105178721351673 | DE96500105176155493434 | EUR          | false        | false         |
      | BPA_Via_QR_IBAN_S0285_700      | bpartner                 | bank_account_S0285_700_3_updated | DE26500105174427157327 | DE91500105177122223557 | EUR          | true         | true          |


    When the metasfresh REST-API endpoint path 'api/v2/bpartner/ext-ALBERTA-001' receives a 'GET' request with the headers from context, expecting status='200'
    Then the metasfresh REST-API responds with
    """
  {
  "bankAccounts": [
    {
      "currencyId": 102,
      "iban": "DE15500105171114521777",
      "swiftCode": null,
      "qrIban": "DE34500105173193385568",
      "name": "bank_account_S0285_700_1_updated",
      "active": false,
      "default": false
    },
    {
      "currencyId": 102,
      "iban": "DE54500105178721351673",
      "swiftCode": null,
      "qrIban": "DE96500105176155493434",
      "name": "bank_account_S0285_700_2_updated",
      "active": false,
      "default": false
    },
    {
      "currencyId": 102,
      "iban": "DE26500105174427157327",
      "swiftCode": null,
      "qrIban": "DE91500105177122223557",
      "name": "bank_account_S0285_700_3_updated",
      "active": true,
      "default": true
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
              "identifier": "ext-ALBERTA-BPACCT_S0285_700_MISSING",
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

  Scenario: Update a BPartner contact record with a missing greeting, expecting error - DEPENDS ON PREDECESSOR
    When a 'PUT' request with the below payload is sent to the metasfresh REST-API 'api/v2/bpartner/001' and fulfills with '422' status code
    """

{
  "requestItems": [
    {
      "bpartnerIdentifier": "ext-ALBERTA-001",
      "bpartnerComposite": {
        "contacts": {
          "requestItems": [
            {
              "contactIdentifier": "ext-ALBERTA-c11",
              "contact": {
                "code": "c11",
                "name": "test_name_c11_updated",
                "email": "test_email_updated",
                "fax": "fax_updated",
                "invoiceEmailEnabled": true,
                "greeting": {
                  "greetingInfo": {
                    "greeting": "test_greeting_261023_updated",
                    "letterSalutation": "test_salutation_261023_updated",
                    "name": "test_name_261023_updated"
                  },
                  "identifier": "ext-ALBERTA-greetingCPTest1_missing"
                }
              }
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