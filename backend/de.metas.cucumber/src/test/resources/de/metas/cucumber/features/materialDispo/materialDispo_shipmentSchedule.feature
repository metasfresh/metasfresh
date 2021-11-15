@from:cucumber
@topic:materialdispo
Feature: material-dispo updates on shipment-schedule events
  As a user
  I want material dispo to be updated properly if shipment-schedules are created
  So that the ATP is always correct

  @from:cucumber
  @topic:materialdispo
  @md_candidate_without_available_quantities
  Scenario: no avalaible quantities for shipment
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
      | plv_1      | pl_1                      | salesOrder-PLV | 2021-04-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_1       | plv_1                             | p_1                     | 10.0     | PCE               | Normal                        |
    And metasfresh contains C_BPartners:
      | Identifier    | Name        | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | endcustomer_1 | Endcustomer | N            | Y              | ps_1                          |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered |
      | o_1        | true    | endcustomer_1            | 2021-11-01  |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered   |
      | ol_1       | o_1                   | p_1                     | 1000         |
    And the order identified by o_1 is completed
    Then metasfresh has this MD_Candidate data
      | Identifier | Type   | BusinessCase | M_Product_ID.Identifier | Time                    | DisplayQty | ATP   |
      | c_1        | DEMAND | SHIPMENT     | p_1                     | 2021-11-01T23:59:00.00Z | 1000       | 1000  |
      | c_2        | SUPPLY |              | p_1                     | 2021-11-01T23:59:00.00Z | 1000       | 0     |
#    And metasfresh has this MD_Candidate_Demand_Detail data
#      | MD_Candidate_ID.Identifier | M_ShipmentSchedule_ID |
#      | c_1                        | 123                   |

  @from:cucumber
  @topic:materialdispo
  Scenario: shipment-schedule with stock
    Given metasfresh initially has this MD_Candidate data
      | Type         | BusinessCase | M_Product_ID | Time                    | DisplayQty | ATP |
      | INVENTORY_UP |              | 2005577      | 2020-12-12T10:00:00.00Z | 100        | 100 |
    When metasfresh receives a ShipmentScheduleCreatedEvent
      | M_Product_ID | M_ShipmentSchedule_ID | PreparationDate         | Qty |
      | 2005577      | 123                   | 2020-12-12T23:59:00.00Z | 10  |
    Then metasfresh has this MD_Candidate data
      | Identifier | Type         | BusinessCase | M_Product_ID | Time                    | DisplayQty | ATP |
      | c_1        | INVENTORY_UP |              | 2005577      | 2020-12-12T10:00:00.00Z | 100        | 100 |
      | c_2        | DEMAND       | SHIPMENT     | 2005577      | 2020-12-12T23:59:00.00Z | -10        | 90  |
    And metasfresh has this MD_Candidate_Demand_Detail data
      | MD_Candidate_ID.Identifier | M_ShipmentSchedule_ID |
      | c_2                        | 123                   |

  