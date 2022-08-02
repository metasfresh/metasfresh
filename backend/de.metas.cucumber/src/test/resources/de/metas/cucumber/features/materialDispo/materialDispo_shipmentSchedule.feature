@from:cucumber
@topic:materialdispo
Feature: material-dispo updates on shipment-schedule events
  As a user
  I want material dispo to be updated properly if shipment-schedules are created
  So that the ATP is always correct

  Background: Initial Data
    Given metasfresh initially has no MD_Candidate data
    And metasfresh contains M_Products:
      | Identifier | Name            |
      | p_1        | salesProduct_12 |
    And metasfresh contains M_PricingSystems
      | Identifier | Name                | Value                | OPT.Description            | OPT.IsActive |
      | ps_1       | pricing_system_name | pricing_system_value | pricing_system_description | true         |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name            | OPT.Description | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | pl_1       | ps_1                          | DE                        | EUR                 | price_list_name | null            | true  | false         | 2              | true         |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name           | ValidFrom  |
      | plv_1      | pl_1                      | salesOrder-PLV | 2020-04-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_1       | plv_1                             | p_1                     | 10.0     | PCE               | Normal                        |
    And metasfresh contains C_BPartners:
      | Identifier    | Name        | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | endcustomer_1 | Endcustomer | N            | Y              | ps_1                          |

  @from:cucumber
  @topic:materialdispo
  Scenario: shipment-schedule with no quantity in stock
    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered |
      | o_1        | true    | endcustomer_1            | 2021-12-01  |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered   |
      | ol_1       | o_1                   | p_1                     | 10           |
    When the order identified by o_1 is completed
    Then after not more than 30s, metasfresh has this MD_Candidate data
      | Identifier | MD_Candidate_Type     | OPT.MD_Candidate_BusinessCase  | M_Product_ID.Identifier  | DateProjected           | Qty      | Qty_AvailableToPromise   |
      | c_1        | DEMAND                | SHIPMENT                   | p_1                      | 2021-12-12T10:00:00.00Z | -10      | -10                      |
      | c_2        | SUPPLY                |                            | p_1                      | 2021-12-12T10:00:00.00Z | 10       | 0                        |
    And metasfresh generates this MD_Candidate_Demand_Detail data
      | Identifier | MD_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | PlannedQty |
      | cdd_1      | c_1                        | ol_1                      | 10         |

  @from:cucumber
  @topic:materialdispo
  Scenario: shipment-schedule with quantity in stock
    Given metasfresh initially has this MD_Candidate data
      | Identifier | MD_Candidate_Type         | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier  | DateProjected           | Qty      | Qty_AvailableToPromise |
      | c_1        | INVENTORY_UP              |                           | p_1                      | 2021-04-01T10:00:00.00Z | 100      | 100                    |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered |
      | o_1        | true    | endcustomer_1            | 2021-04-08  |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered   |
      | ol_1       | o_1                   | p_1                     | 20           |
    When the order identified by o_1 is completed
    Then after not more than 30s, metasfresh has this MD_Candidate data
      | Identifier | MD_Candidate_Type         | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier  | DateProjected           | Qty      | Qty_AvailableToPromise |
      | c_1        | INVENTORY_UP              |                           | p_1                      | 2021-04-01T10:00:00.00Z | 100      | 100                    |
      | c_2        | DEMAND                    | SHIPMENT                  | p_1                      | 2022-12-08T10:00:00.00Z | -20      | 80                     |
    And metasfresh generates this MD_Candidate_Demand_Detail data
      | Identifier | MD_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | PlannedQty |
      | cdd_1      | c_2                        | ol_1                      | 20         |