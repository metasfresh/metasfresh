@from:cucumber
@ghActions:run_on_executor6
Feature:bpartner get using metasfresh api
  As a REST-API invoker
  I want want to be able to get bpartners updated since a certain time

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'

    And metasfresh contains AD_Org:
      | AD_Org_ID.Identifier | Name | Value |
      | 001                  | 001  | 001   |


  @from:cucumber
  Scenario: get Partner request by org as a REST-API invoker
  I want to be able to get partners updated since a timestamp for a certain org

    # set date and time to epoch-time=2461507318000, to avoid other records from interfering when calling with "since"
    Given metasfresh has date and time 2048-01-02T08:00:00+01:00[Europe/Berlin]

    And remove external reference if exists:
      | ExternalSystem    | ExternalReference | Type             |
      | WooCommerce       | bp1234            | BPartner         |
      | ProCareManagement | pbl2345           | BPartnerLocation |
      | Other             | u3456             | UserID           |

    And  metasfresh contains C_BPartners without locations:
      | Identifier | Name | AD_Org_ID.Identifier | REST.Context.C_BPartner_ID |
      | 1234       | 1234 | 001                  | BPartner_1234_ID           |

    And metasfresh contains C_BPartner_Locations:
      | Identifier | C_BPartner_ID.Identifier | AD_Org_ID.Identifier | REST.Context.C_BPartner_Location_ID |
      | 2345       | 1234                     | 001                  | BPartnerLocation_1234_ID            |

    And metasfresh contains AD_Users:
      | AD_User_ID.Identifier | Name                   | OPT.C_BPartner_ID.Identifier | AD_Org_ID.Identifier | REST.Context.AD_User_ID |
      | 3456                  | 3456_bpartner_api_test | 1234                         | 001                  | AD_User_1234_ID         |

    When the metasfresh REST-API endpoint path 'api/v2/bpartner/byOrg/001/?since=2461507318000' receives a 'GET' request

    Then the metasfresh REST-API responds with
    """
   {
  "resultTimestamp": 2461561200000
,
  "totalSize": 1,
  "pageSize": 1,
  "items": [
    { 
      "bpartner": {
        "metasfreshId": @BPartner_1234_ID@,
        "code": "1234",
        "globalId": null,
        "active": true,
        "name": "1234",
        "group": "Standard",
        "vendor": false,
        "customer": false,
        "paymentRule": "OnCredit",
        "company": true,
        "vatId": null,
        "metasfreshUrl": "http://localhost:3000/window/123/2156426",
        "changeInfo": {
          "createdMillis": 2461561200000,
          "createdBy": 100,
          "lastUpdatedMillis": 2461561200000,
          "lastUpdatedBy": 100
        }
      },
      "locations": [
        {
          "metasfreshId": @BPartnerLocation_1234_ID@,
          "name": "1234",
          "bpartnerName": null,
          "active": true,
          "postal": null,
          "city": null,
          "gln": null,
          "countryCode": "DE",
          "shipTo": false,
          "shipToDefault": false,
          "billTo": false,
          "billToDefault": false,
          "ephemeral": false,
          "visitorsAddress": false,
          "handoverLocation": false,
          "remitTo": false,
          "replicationLookupDefault": false,
          "vatId": null,
          "changeInfo": {
            "createdMillis": 2461561200000,
            "createdBy": 100,
            "lastUpdatedMillis": 2461561200000,
            "lastUpdatedBy": 100
          }
        }
      ],
      "contacts": [
        {
          "metasfreshId": @AD_User_1234_ID@,
          "metasfreshBPartnerId": @BPartner_1234_ID@,
          "active": true,
          "name": "3456_bpartner_api_test",
          "newsletter": false,
          "shipToDefault": false,
          "billToDefault": false,
          "defaultContact": false,
          "sales": false,
          "salesDefault": false,
          "purchase": false,
          "purchaseDefault": false,
          "subjectMatter": false,
          "invoiceEmailEnabled": null,
          "metasfreshLocationId": null
        }
      ]
    }
  ]
}
    """
# cleanup
    And metasfresh has current date and time

    # the bpartner from the first test should not occur here, because he isn't referenced from the externalSystem
  @from:cucumber
  Scenario: get Partner request by external system as a REST-API invoker
  I want to be able to get partners updated since a timestamp for a external system
          
    # set date and time to epoch-time=2461507318000, to avoid other records from interfering when calling with"since"
    Given metasfresh has date and time 2048-01-02T08:00:00+01:00[Europe/Berlin]

    And remove external reference if exists:
      | ExternalSystem    | ExternalReference | Type             |
      | WooCommerce       | bp1234            | BPartner         |
      | ProCareManagement | pbl2345           | BPartnerLocation |
      | Other             | u3456             | UserID           |

    And  metasfresh contains C_BPartners without locations:
      | Identifier            | Name                  | AD_Org_ID.Identifier | REST.Context.C_BPartner_ID |
      | bpartner_by_extSystem | bpartner_by_extSystem | 001                  | BPartner_by_extSystem_ID   |

    And metasfresh contains C_BPartner_Locations:
      | Identifier       | C_BPartner_ID.Identifier | AD_Org_ID.Identifier | REST.Context.C_BPartner_Location_ID |
      | bpl_by_extSystem | bpartner_by_extSystem    | 001                  | BPartnerLocation_by_extSystem_ID    |

    And metasfresh contains AD_Users:
      | AD_User_ID.Identifier | Name                    | OPT.C_BPartner_ID.Identifier | AD_Org_ID.Identifier | REST.Context.AD_User_ID |
      | contact_by_extSystem  | contact_by_extSystem | bpartner_by_extSystem        | 001                  | AD_User_by_extSystem_ID |

    And metasfresh contains S_ExternalReferences:
      | ExternalSystem.Code | ExternalReference | ExternalReferenceType.Code | RecordId.Identifier   |
      | WooCommerce         | bp1234            | BPartner                   | bpartner_by_extSystem |
      | ProCareManagement   | pbl2345           | BPartnerLocation           | bpl_by_extSystem      |
      | Other               | u3456             | UserID                     | contact_by_extSystem  |

    When the metasfresh REST-API endpoint path 'api/v2/bpartner/byExtSystem/WooCommerce/?since=2461507318000' receives a 'GET' request

    Then the metasfresh REST-API responds with
    """
   {
  "resultTimestamp": 2461561200000
,
  "totalSize": 1,
  "pageSize": 1,
  "items": [
    {
      "bpartner": {
        "metasfreshId": @BPartner_by_extSystem_ID@,
        "code": "bpartner_by_extSystem",
        "globalId": null,
        "active": true,
        "name": "bpartner_by_extSystem",
        "group": "Standard",
        "vendor": false,
        "customer": false,
        "paymentRule": "OnCredit",
        "company": true,
        "vatId": null,
        "metasfreshUrl": "http://localhost:3000/window/123/@BPartner_by_extSystem_ID@",
        "changeInfo": {
          "createdMillis": 2461561200000,
          "createdBy": 100,
          "lastUpdatedMillis": 2461561200000,
          "lastUpdatedBy": 100
        }
      },
      "locations": [
        {
          "metasfreshId": @BPartnerLocation_by_extSystem_ID@,
          "name": "bpartner_by_extSystem",
          "bpartnerName": null,
          "active": true,
          "postal": null,
          "city": null,
          "gln": null,
          "countryCode": "DE",
          "shipTo": false,
          "shipToDefault": false,
          "billTo": false,
          "billToDefault": false,
          "ephemeral": false,
          "visitorsAddress": false,
          "handoverLocation": false,
          "remitTo": false,
          "replicationLookupDefault": false,
          "vatId": null,
          "changeInfo": {
            "createdMillis": 2461561200000,
            "createdBy": 100,
            "lastUpdatedMillis": 2461561200000,
            "lastUpdatedBy": 100
          }
        }
      ],
      "contacts": [
        {
          "metasfreshId": @AD_User_by_extSystem_ID@,
          "metasfreshBPartnerId": @BPartner_by_extSystem_ID@,
          "active": true,
          "name": "contact_by_extSystem",
          "newsletter": false,
          "shipToDefault": false,
          "billToDefault": false,
          "defaultContact": false,
          "sales": false,
          "salesDefault": false,
          "purchase": false,
          "purchaseDefault": false,
          "subjectMatter": false,
          "invoiceEmailEnabled": null,
          "metasfreshLocationId": null,
          "changeInfo": {
            "createdMillis": 2461561200000,
            "createdBy": 100,
            "lastUpdatedMillis": 2461561200000,
            "lastUpdatedBy": 100
          }
        }
      ]
    }
  ]
}
    """
# cleanup
    And metasfresh has current date and time
    