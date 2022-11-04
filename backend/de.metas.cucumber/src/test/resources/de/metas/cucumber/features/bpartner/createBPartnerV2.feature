@from:cucumber
Feature: create or update BPartner v2
  As a user
  I want create or update a BPartner record

  Background:
    Given the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'

  @from:cucumber
  Scenario: create a BPartner record
    And metasfresh contains M_SectionCode:
      | M_SectionCode_ID.Identifier | Value                   |
      | ALBERTA_001_sectionCode     | ALBERTA_001_sectionCode |
    And metasfresh contains C_Incoterms:
      | C_Incoterms_ID.Identifier | Value                 |
      | ALBERTA_001_Incoterms     | ALBERTA_001_Incoterms |
    ## todo mi: add payment term
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
               "code":"test_code",
               "name":"test_name",
               "companyName":"test_company",
               "parentId":null,
               "phone":null,
               "language":"de",
               "url":null,
               "group":"test-group",
               "vatId":null,
               "sectionCodeValue":"ALBERTA_001_sectionCode",
               "description":"ALBERTA BPartner Description",
               "deliveryRule":"AVAILABILITY",
               "deliveryViaRule":"SHIPPER",
               "storageWarehouse": true,
               "incotermsCustomerValue":"ALBERTA_001_Incoterms",
               "incotermsVendorValue":"ALBERTA_001_Incoterms",
               "paymentRule":"ON_CREDIT",
               "paymentRulePO":"ON_CREDIT"
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
                        "replicationLookupDefault":false
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
      | C_BPartner_ID.Identifier | externalIdentifier | OPT.Code  | Name      | OPT.CompanyName | OPT.ParentId | OPT.Phone | OPT.Language | OPT.Url | OPT.Group  | OPT.VatId | OPT.M_SectionCode_ID.Value | OPT.DeliveryRule | OPT.DeliveryViaRule | OPT.C_Incoterms_Customer_ID.Value | OPT.C_Incoterms_Vendor_ID.Value | OPT.PaymentRule | OPT.PaymentRulePO | OPT.IsStorageWarehouse |
      | created_bpartner         | ext-ALBERTA-001    | test_code | test_name | test_company    | null         | null      | de           | null    | test-group | null      | ALBERTA_001_sectionCode    | A                | S                   | ALBERTA_001_Incoterms             | ALBERTA_001_Incoterms           | P               | P                 | Y                      |
    And verify that location was created for bpartner
      | bpartnerIdentifier | locationIdentifier | OPT.Address1  | OPT.Address2  | OPT.PoBox  | OPT.District | OPT.Region  | OPT.City  | CountryCode | OPT.Gln | OPT.Postal | OPT.IsHandOverLocation | OPT.IsRemitTo | OPT.IsReplicationLookupDefault |
      | ext-ALBERTA-001    | gln-l11            | test_address1 | test_address2 | null       | null         | null        | null      | DE          | l11     | null       | true                   | false         | false                          |
      | ext-ALBERTA-001    | gln-l22            | null          | test_address2 | test_poBox | null         | test_region | test_city | DE          | l22     | null       |                        |               |                                |
    And verify that contact was created for bpartner
      | bpartnerIdentifier | contactIdentifier | Name          | OPT.Email  | OPT.Fax  | Code | OPT.InvoiceEmailEnabled |
      | ext-ALBERTA-001    | ext-ALBERTA-c11   | test_name_c11 | test_email | fax      | c11  | false                   |
      | ext-ALBERTA-001    | ext-ALBERTA-c22   | test_name_c22 | null       | test_fax | c22  | true                    |
    And verify that S_ExternalReference was created
      | ExternalSystem | Type     | ExternalReference | ExternalReferenceURL         | OPT.ExternalSystem_Config_ID | OPT.IsReadOnlyInMetasfresh |
      | ALBERTA        | BPartner | 001               | www.ExternalReferenceURL.com | 540000                       | false                      |

    And build BPartner Endpoint Path and store it in context
      | C_BPartner_ID.Identifier |
      | created_bpartner         |

    When a 'GET' request is sent to metasfresh REST-API with endpointPath from context and fulfills with '200' status code
    Then the metasfresh REST-API responds with
    """
{
   "bpartner":{
      "metasfreshId":2156434,
      "code":"test_code",
      "globalId":null,
      "active":true,
      "name":"test_name",
      "companyName":"test_company",
      "language":"de_DE",
      "group":"test-group",
      "vendor":false,
      "customer":false,
      "paymentRule":"OnCredit",
      "paymentRulePO":"OnCredit",
      "company":true,
      "vatId":null,
      "metasfreshUrl":"http://localhost:3000/window/123/2156434",
      "sectionCodeValue":"ALBERTA_001_sectionCode",
      "description":"ALBERTA BPartner Description",
      "deliveryRule":"Availability",
      "deliveryViaRule":"Shipper",
      "storageWarehouse":true,
      "incotermsCustomerName":"ALBERTA_001_Incoterms",
      "incotermsVendorName":"ALBERTA_001_Incoterms"
   },
   "locations":[
      {
         "metasfreshId":2205185,
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
         "replicationLookupDefault":false
      },
      {
         "metasfreshId":2205186,
         "name":"test_city test_name",
         "bpartnerName":null,
         "active":true,
         "address2":"test_address2",
         "postal":null,
         "poBox":"test_poBox",
         "region":"test_region",
         "city":"test_city",
         "gln":"l22",
         "countryCode":"DE",
         "shipTo":true,
         "shipToDefault":false,
         "billTo":true,
         "billToDefault":false,
         "ephemeral":false,
         "visitorsAddress":false,
         "handoverLocation":false,
         "remitTo":false,
         "replicationLookupDefault":false
      }
   ],
   "contacts":[
      {
         "metasfreshId":2188225,
         "metasfreshBPartnerId":2156434,
         "active":true,
         "name":"test_name_c33_created",
         "email":"test_email_created",
         "fax":"fax_created",
         "newsletter":false,
         "shipToDefault":false,
         "billToDefault":false,
         "defaultContact":false,
         "sales":false,
         "salesDefault":false,
         "purchase":false,
         "purchaseDefault":false,
         "subjectMatter":false,
         "invoiceEmailEnabled":true,
         "metasfreshLocationId":null
      },
      {
         "metasfreshId":2188226,
         "metasfreshBPartnerId":2156434,
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
         "invoiceEmailEnabled":true,
         "metasfreshLocationId":2205185
      },
      {
         "metasfreshId":2188224,
         "metasfreshBPartnerId":2156434,
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
         "metasfreshLocationId":null
      }
   ]
}
    """

  Scenario: Update a BPartner record
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
      | C_BPartner_ID.Identifier | externalIdentifier | OPT.Code          | Name              | OPT.CompanyName | OPT.ParentId | OPT.Phone | OPT.Language | OPT.Url     | OPT.Group  | OPT.VatId | OPT.IsStorageWarehouse |
      | created_bpartner         | ext-ALBERTA-001    | test_code_updated | test_name_updated | test_company    | null         | null      | de           | url_updated | test-group | null      | Y                      |
    And verify that S_ExternalReference was created
      | ExternalSystem | Type     | ExternalReference | ExternalReferenceURL         | OPT.ExternalSystem_Config_ID | OPT.IsReadOnlyInMetasfresh |
      | ALBERTA        | BPartner | 001               | www.ExternalReferenceURL.com | 540000                       | true                       |

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