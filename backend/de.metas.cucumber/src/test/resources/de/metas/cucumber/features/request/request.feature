@from:cucumber
@ghActions:run_on_executor5
Feature: R_Request upsert and retrieval via API

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And metasfresh has date and time 2025-04-01T13:30:13+01:00[Europe/Berlin]
    And metasfresh contains AD_Org:
      | AD_Org_ID.Identifier | Name | Value |
      | 002                  | 002  | org-2 |
    And metasfresh contains M_Products:
      | Identifier | Value   |
      | product    | reqProd |
    And metasfresh contains M_Products:
      | Identifier | Name        |
      | p1         | testProduct |
    And metasfresh contains M_PricingSystems
      | Identifier |
      | ps1        |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | OPT.C_Country.CountryCode | C_Currency.ISO_Code | SOTrx |
      | pl_so      | ps1                | DE                        | EUR                 | true  |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | ValidFrom  |
      | plv_so     | pl_so                     | 2022-01-20 |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_TaxCategory_ID.InternalName | C_UOM_ID.X12DE355 |
      | plv_so                 | p1           | 10.0     | Normal                        | PCE               |
    And metasfresh contains C_BPartners:
      | Identifier | IsVendor | IsCustomer | Value | M_PricingSystem_ID |
      | customer   | N        | Y          | reqBP | ps1                |
    And metasfresh contains AD_Users:
      | Identifier | AD_User_ID | Name     |
      | someUser   | 12345      | someUser |

  @from:cucumber
  Scenario: R_Request upsert
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | PreparationDate      | REST.Context |
      | o1         | true    | customer      | 2024-03-30  | 2024-03-29T21:00:00Z | orderId      |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | ol_1       | o1         | p1           | 3          |
    And the order identified by o1 is completed

    When a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/request' and fulfills with '201' status code
"""
{
  "orgCode" : "org-2",
  "requestType" : "A_CustomerComplaint",
  "summary" : "Another summary",
  "dateDelivered" : "2025-06-15",
  "priority" : "Medium",
  "confidentialityLevel" : "PartnerConfidential",
  "orderIdentifier" : "@orderId@"
}
"""

    Then validate a new request has been created:
      | AD_Org_ID | R_RequestType_ID    | Summary         | DateDelivered | Priority | ConfidentialType | C_Order_ID |
      | 002       | A_CustomerComplaint | Another summary | 2025-06-15    | 5        | C                | o1         |