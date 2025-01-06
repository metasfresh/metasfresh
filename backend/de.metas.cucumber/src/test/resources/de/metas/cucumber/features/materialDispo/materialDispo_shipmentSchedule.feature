@from:cucumber
@topic:materialdispo
@ghActions:run_on_executor6
Feature: material-dispo updates on shipment-schedule events
  As a user
  I want material dispo to be updated properly if shipment-schedules are created
  So that the ATP is always correct

  Background: Initial Data
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2022-09-19T08:00:00+01:00[Europe/Berlin]
    And metasfresh contains M_Products:
      | Identifier |
      | p_1        |
    And metasfresh contains M_PricingSystems
      | Identifier |
      | ps_1       |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | C_Country_ID | C_Currency_ID | SOTrx | IsTaxIncluded | PricePrecision |
      | pl_1       | ps_1               | DE           | EUR           | true  | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID |
      | plv_1      | pl_1           |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID | C_TaxCategory_ID |
      | pp_1       | plv_1                  | p_1          | 10.0     | PCE      | Normal           |
    And metasfresh contains C_BPartners:
      | Identifier    | IsCustomer | M_PricingSystem_ID |
      | endcustomer_1 | Y          | ps_1               |



















# #########################################################################################################################################################
# #########################################################################################################################################################
# #########################################################################################################################################################
# #########################################################################################################################################################
# #########################################################################################################################################################
  @from:cucumber
  @topic:materialdispo
  Scenario: shipment-schedule with no quantity in stock
    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | PreparationDate         |
      | o_1        | true    | endcustomer_1 | 2022-09-19  | 2022-09-18T21:00:00.00Z |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | ol_1       | o_1        | p_1          | 10         |
    When the order identified by o_1 is completed
    Then after not more than 60s, the MD_Candidate table has only the following records
      | Identifier | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID | DateProjected           | Qty | ATP |
      | c_1        | DEMAND            | SHIPMENT                  | p_1          | 2022-09-18T21:00:00.00Z | -10 | -10 |
    And metasfresh generates this MD_Candidate_Demand_Detail data
      | Identifier | MD_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | PlannedQty |
      | cdd_1      | c_1                        | ol_1                      | 10         |















# #########################################################################################################################################################
# #########################################################################################################################################################
# #########################################################################################################################################################
# #########################################################################################################################################################
# #########################################################################################################################################################
  @from:cucumber
  @topic:materialdispo
  Scenario: shipment-schedule with quantity in stock
    Given metasfresh initially has this MD_Candidate data
      | Identifier | MD_Candidate_Type | OPT.MD_Candidate_BusinessCase | M_Product_ID | DateProjected           | Qty | ATP |
      | c_1        | INVENTORY_UP      |                               | p_1          | 2022-09-18T10:00:00.00Z | 100 | 100 |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | PreparationDate         |
      | o_1        | true    | endcustomer_1 | 2021-04-08  | 2022-09-18T21:00:00.00Z |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | ol_1       | o_1        | p_1          | 20         |
    When the order identified by o_1 is completed
    Then after not more than 60s, the MD_Candidate table has only the following records
      | Identifier | MD_Candidate_Type | OPT.MD_Candidate_BusinessCase | M_Product_ID | DateProjected           | Qty | ATP |
      | c_1        | INVENTORY_UP      |                               | p_1          | 2022-09-18T10:00:00.00Z | 100 | 100 |
      | c_2        | DEMAND            | SHIPMENT                      | p_1          | 2022-09-18T21:00:00.00Z | -20 | 80  |
    And metasfresh generates this MD_Candidate_Demand_Detail data
      | Identifier | MD_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | PlannedQty |
      | cdd_1      | c_2                        | ol_1                      | 20         |




