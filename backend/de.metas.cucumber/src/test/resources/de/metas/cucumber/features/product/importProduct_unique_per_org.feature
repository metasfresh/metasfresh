@from:cucumber
@ghActions:run_on_executor5
Feature: Product import - uniqueness checks are evaluated per organization
  The "not unique" checks of the product import compare the staging rows of one client.
  Rows that carry the same search key but belong to different organizations are different products
  and must not be flagged; rows of the same organization still are.

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2022-05-17T13:30:13+01:00[Europe/Berlin]
    And all I_Product staging records are deleted
    And metasfresh contains AD_Org:
      | AD_Org_ID.Identifier | Name         |
      | org_1                | ImportOrg 1  |
      | org_2                | ImportOrg 2  |

  Scenario: Same Value and UPC in different orgs are not flagged as not unique
    Given metasfresh contains I_Product:
      | Identifier | Value         | UPC           | AD_Org_ID.Identifier |
      | iProd_1    | ORG_UNIQ_P1   | 4012345678901 | org_1                |
      | iProd_2    | ORG_UNIQ_P1   | 4012345678901 | org_2                |
    When the ImportProduct process is invoked
    Then validate I_Product:
      | Identifier | I_ErrorMsg_NotContains |
      | iProd_1    | Value not unique       |
      | iProd_1    | UPC not unique         |
      | iProd_2    | Value not unique       |
      | iProd_2    | UPC not unique         |

  Scenario: Same Value and UPC in the same org are flagged as not unique
    Given metasfresh contains I_Product:
      | Identifier | Value         | UPC           | AD_Org_ID.Identifier |
      | iProd_1    | ORG_UNIQ_P2   | 4012345678902 | org_1                |
      | iProd_2    | ORG_UNIQ_P2   | 4012345678902 | org_1                |
    When the ImportProduct process is invoked
    Then validate I_Product:
      | Identifier | I_ErrorMsg_Contains |
      | iProd_1    | Value not unique    |
      | iProd_1    | UPC not unique      |
      | iProd_2    | Value not unique    |
      | iProd_2    | UPC not unique      |

  Scenario: Same VendorProductNo in different orgs is not flagged, in the same org it is
    Given metasfresh contains C_BPartners:
      | Identifier | Value             |
      | bp_vendor  | OrgUniqVendorBP   |
    And metasfresh contains I_Product:
      | Identifier | Value         | BPartner_Value  | AD_Org_ID.Identifier |
      | iProd_1    | ORG_UNIQ_P3   | OrgUniqVendorBP | org_1                |
      | iProd_2    | ORG_UNIQ_P3   | OrgUniqVendorBP | org_2                |
      | iProd_3    | ORG_UNIQ_P4   | OrgUniqVendorBP | org_1                |
      | iProd_4    | ORG_UNIQ_P4   | OrgUniqVendorBP | org_1                |
    When the ImportProduct process is invoked
    Then validate I_Product:
      | Identifier | I_ErrorMsg_NotContains     |
      | iProd_1    | VendorProductNo not unique |
      | iProd_2    | VendorProductNo not unique |
    And validate I_Product:
      | Identifier | I_ErrorMsg_Contains        |
      | iProd_3    | VendorProductNo not unique |
      | iProd_4    | VendorProductNo not unique |
