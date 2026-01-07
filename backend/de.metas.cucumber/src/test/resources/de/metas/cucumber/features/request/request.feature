@from:cucumber
@allure.label.epic:E0270_CRM
@allure.label.feature:F00271_Karottenabrechnung_mit_Lager
@F00271
@ghActions:run_on_executor5
Feature: R_Request upsert and retrieval via API
## F00271: Request

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
@allure.label.epic:E0270_CRM
@allure.label.feature:F00271_Karottenabrechnung_mit_Lager
@F00271
  Scenario: R_Request upsert
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | PreparationDate      | REST.Context |
      | o1         | true    | customer      | 2024-03-30  | 2024-03-29T21:00:00Z | C_Order_ID   |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | ol_1       | o1         | p1           | 3          |
    And the order identified by o1 is completed

    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes

    When a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/request' and fulfills with '201' status code
"""
{
  "orgCode" : "org-2",
  "requestType" : "A_CustomerComplaint",
  "summary" : "Another summary",
  "dateDelivered" : "2025-06-15",
  "dateTrx" : "2025-06-15",
  "priority" : "Medium",
  "confidentialityLevel" : "PartnerConfidential",
  "orderId" : @C_Order_ID@
}
"""

    Then validate a new request has been created:
      | Identifier | AD_Org_ID | R_RequestType_ID    | Summary         | DateDelivered | Priority | ConfidentialType | C_Order_ID | REST.Context |
      | req1       | 002       | A_CustomerComplaint | Another summary | 2025-06-15    | 5        | C                | o1         | R_Request_ID |

    And invoke 'GET' 'api/request/@R_Request_ID@' with response code '200'

    And the returned request body of type 'de.metas.rest_api.request.JsonRRequest' is:
"""
{
  "id" : @R_Request_ID@,
  "orgCode" : "org-2",
  "requestType" : "A_CustomerComplaint",
  "bpartnerId" : 0,
  "userId" : 0,
  "priority" : "5",
  "summary" : "Another summary",
  "confidentialityLevel" : "C",
  "vendorId" : 0,
  "salesRepId" : 0,
  "dateDelivered" : "2025-06-15",
  "dateTrx" : "2025-06-15",
  "projectId" : 0,
  "productId" : 0,
  "orderId" : @C_Order_ID@,
  "inOutId" : 0,
  "invoiceId" : 0,
  "paymentId" : 0,
  "statusName" : "offen"
}
"""
