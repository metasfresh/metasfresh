@from:cucumber
@topic:materialdispo
Feature: material-dispo updates on StockEstimateEvent events
  As a user
  I want material dispo to be updated properly if a StockEstimateEvent is processed or unprocessed
  So that the ATP is always correct

  Background:
    Given the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh initially has no MD_Candidate_StockChange_detail data

    And metasfresh contains M_PricingSystems
      | Identifier | Name                         | Value                         | OPT.Description                     | OPT.IsActive |
      | ps_1       | ps_materialDispo_name_160922 | ps_materialDispo_value_160922 | ps_materialDispo_description_160922 | true         |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name                                    | OPT.Description | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | pl_1       | ps_1                          | DE                        | EUR                 | pl_materialDispo_price_list_name_160922 | null            | true  | false         | 2              | true         |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name                     | ValidFrom  |
      | plv_1      | pl_1                      | materialDispo-PLV_160922 | 2021-04-01 |

  @from:cucumber
  @topic:materialdispo
  Scenario: StockEstimateCreatedEvent without stock or ATP
    Given metasfresh contains M_Products:
      | Identifier | Name                     |
      | p_1        | p_create_No_Stock_160922 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_1       | plv_1                             | p_1                     | 10.0     | PCE               | Normal                        |
    When metasfresh receives a StockEstimateCreatedEvent
      | M_Product_ID | Fresh_QtyOnHand_ID | Fresh_QtyOnHand_Line_ID | DateDoc                 | Qty |
      | p_1          | 2                  | 22                      | 2021-06-23T00:00:00.00Z | 10  |
    Then after not more than 30s, MD_Candidates are found
      | Identifier | MD_Candidate_Type | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected           | Qty | Qty_AvailableToPromise |
      | c_2        | INVENTORY_UP      | STOCK_CHANGE                  | p_1                     | 2021-06-23T00:00:00.00Z | 10  | 10                     |
    And metasfresh has this MD_Candidate_StockChange_Detail data
      | MD_Candidate_ID.Identifier | Fresh_QtyOnHand_ID | Fresh_QtyOnHand_Line_ID | IsReverted |
      | c_2                        | 2                  | 22                      | N          |

  @from:cucumber
  @topic:materialdispo
  Scenario: StockEstimateCreatedEvent with stock
    Given metasfresh contains M_Products:
      | Identifier | Name                       |
      | p_1        | p_create_With_Stock_160922 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_1       | plv_1                             | p_1                     | 10.0     | PCE               | Normal                        |
    And metasfresh initially has this MD_Candidate data
      | MD_Candidate_Type | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected           | Qty | Qty_AvailableToPromise |
      | INVENTORY_UP      |                               | p_1                     | 2020-12-12T00:00:00.00Z | 100 | 100                    |
    When metasfresh receives a StockEstimateCreatedEvent
      | M_Product_ID | Fresh_QtyOnHand_ID | Fresh_QtyOnHand_Line_ID | DateDoc                 | Qty |
      | p_1          | 2                  | 22                      | 2021-06-23T00:00:00.00Z | 90  |
    Then after not more than 30s, MD_Candidates are found
      | Identifier | MD_Candidate_Type | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected           | Qty | Qty_AvailableToPromise |
      | c_1        | INVENTORY_UP      |                               | p_1                     | 2020-12-12T00:00:00.00Z | 100 | 100                    |
      | c_2        | INVENTORY_DOWN    | STOCK_CHANGE                  | p_1                     | 2021-06-23T00:00:00.00Z | -10 | 90                     |
    And metasfresh has this MD_Candidate_StockChange_Detail data
      | MD_Candidate_ID.Identifier | Fresh_QtyOnHand_ID | Fresh_QtyOnHand_Line_ID | IsReverted |
      | c_2                        | 2                  | 22                      | N          |

  @from:cucumber
  @topic:materialdispo
  Scenario: StockEstimateDeletedEvents without related data are ignored
    Given metasfresh contains M_Products:
      | Identifier | Name                    |
      | p_1        | p_delete_No_Data_160922 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_1       | plv_1                             | p_1                     | 10.0     | PCE               | Normal                        |
    And metasfresh initially has this MD_Candidate data
      | MD_Candidate_Type | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected           | Qty | Qty_AvailableToPromise |
      | INVENTORY_UP      |                               | p_1                     | 2020-12-12T10:00:00.00Z | 100 | 100                    |
    When metasfresh receives a StockEstimateDeletedEvent
      | M_Product_ID | Fresh_QtyOnHand_ID | Fresh_QtyOnHand_Line_ID | DateDoc                 | Qty |
      | p_1          | 1                  | 11                      | 2021-06-23T23:59:00.00Z | 10  |
    Then after not more than 30s, MD_Candidates are found
      | MD_Candidate_Type | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected           | Qty | Qty_AvailableToPromise |
      | INVENTORY_UP      |                               | p_1                     | 2020-12-12T10:00:00.00Z | 100 | 100                    |

  @from:cucumber
  @topic:materialdispo
  Scenario: StockEstimateDeletedEvents with related data are processed
    Given metasfresh contains M_Products:
      | Identifier | Name                      |
      | p_1        | p_delete_With_Data_160922 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_1       | plv_1                             | p_1                     | 10.0     | PCE               | Normal                        |
    And metasfresh receives a StockEstimateCreatedEvent
      | M_Product_ID | Fresh_QtyOnHand_ID | Fresh_QtyOnHand_Line_ID | DateDoc                 | Qty |
      | p_1          | 1                  | 11                      | 2021-06-23T00:00:00.00Z | 90  |
    And after not more than 30s, MD_Candidates are found
      | Identifier | MD_Candidate_Type | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected           | Qty | Qty_AvailableToPromise |
      | c_1        | INVENTORY_UP      | STOCK_CHANGE                  | p_1                     | 2021-06-23T00:00:00.00Z | 90  | 90                     |
    And metasfresh has this MD_Candidate_StockChange_Detail data
      | MD_Candidate_ID.Identifier | Fresh_QtyOnHand_ID | Fresh_QtyOnHand_Line_ID | IsReverted |
      | c_1                        | 1                  | 11                      | N          |
    When metasfresh receives a StockEstimateDeletedEvent
      | M_Product_ID | Fresh_QtyOnHand_ID | Fresh_QtyOnHand_Line_ID | DateDoc                 | Qty |
      | p_1          | 1                  | 11                      | 2021-06-23T00:00:00.00Z | 90  |
    Then metasfresh has no MD_Candidate for identifier "c_1"
    And metasfresh has this MD_Candidate_StockChange_Detail data
      | MD_Candidate_ID.Identifier | Fresh_QtyOnHand_ID | Fresh_QtyOnHand_Line_ID | IsReverted |
      | c_1                        | 1                  | 11                      | Y          |

  @from:cucumber
  @topic:materialdispo
  Scenario: StockEstimateEvent flow with INVENTORY_UP and positive ATP
    Given metasfresh contains M_Products:
      | Identifier | Name                 |
      | p_1        | p_estimate_Up_160922 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_1       | plv_1                             | p_1                     | 10.0     | PCE               | Normal                        |
    And metasfresh initially has this MD_Candidate data
      | MD_Candidate_Type | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected           | Qty | Qty_AvailableToPromise |
      | INVENTORY_UP      |                               | p_1                     | 2021-06-23T00:00:00.00Z | 100 | 100                    |
      | INVENTORY_UP      |                               | p_1                     | 2021-06-25T00:00:00.00Z | 40  | 140                    |
    And metasfresh receives a StockEstimateCreatedEvent
      | M_Product_ID | Fresh_QtyOnHand_ID | Fresh_QtyOnHand_Line_ID | DateDoc                 | Qty |
      | p_1          | 1                  | 11                      | 2021-06-24T00:00:00.00Z | 160 |
    And after not more than 30s, MD_Candidates are found
      | Identifier | MD_Candidate_Type | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected           | Qty | Qty_AvailableToPromise |
      | c_1        | INVENTORY_UP      |                               | p_1                     | 2021-06-23T00:00:00.00Z | 100 | 100                    |
      | c_3        | INVENTORY_UP      | STOCK_CHANGE                  | p_1                     | 2021-06-24T00:00:00.00Z | 60  | 160                    |
      | c_2        | INVENTORY_UP      |                               | p_1                     | 2021-06-25T00:00:00.00Z | 40  | 200                    |
    And metasfresh has this MD_Candidate_StockChange_Detail data
      | MD_Candidate_ID.Identifier | Fresh_QtyOnHand_ID | Fresh_QtyOnHand_Line_ID | IsReverted |
      | c_3                        | 1                  | 11                      | N          |
    When metasfresh receives a StockEstimateDeletedEvent
      | M_Product_ID | Fresh_QtyOnHand_ID | Fresh_QtyOnHand_Line_ID | DateDoc                 | Qty |
      | p_1          | 1                  | 11                      | 2021-06-24T00:00:00.00Z | 160 |
    Then after not more than 30s, MD_Candidates are found
      | Identifier | MD_Candidate_Type | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected           | Qty | Qty_AvailableToPromise |
      | c_1        | INVENTORY_UP      |                               | p_1                     | 2021-06-23T00:00:00.00Z | 100 | 100                    |
      | c_2        | INVENTORY_UP      |                               | p_1                     | 2021-06-25T00:00:00.00Z | 40  | 140                    |
    And metasfresh has this MD_Candidate_StockChange_Detail data
      | MD_Candidate_ID.Identifier | Fresh_QtyOnHand_ID | Fresh_QtyOnHand_Line_ID | IsReverted |
      | c_3                        | 1                  | 11                      | Y          |

  @from:cucumber
  @topic:materialdispo
  Scenario: Stock estimate event with INVENTORY_DOWN and positive ATP
    Given metasfresh contains M_Products:
      | Identifier | Name                   |
      | p_1        | p_estimate_Down_160922 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_1       | plv_1                             | p_1                     | 10.0     | PCE               | Normal                        |
    And metasfresh initially has this MD_Candidate data
      | MD_Candidate_Type | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected           | Qty | Qty_AvailableToPromise |
      | INVENTORY_UP      |                               | p_1                     | 2021-06-23T00:00:00.00Z | 100 | 100                    |
      | INVENTORY_DOWN    |                               | p_1                     | 2021-06-25T00:00:00.00Z | -60 | 40                     |
    When metasfresh receives a StockEstimateCreatedEvent
      | M_Product_ID | Fresh_QtyOnHand_ID | Fresh_QtyOnHand_Line_ID | DateDoc                 | Qty |
      | p_1          | 1                  | 11                      | 2021-06-24T00:00:00.00Z | 85  |
    Then after not more than 30s, MD_Candidates are found
      | Identifier | MD_Candidate_Type | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected           | Qty | Qty_AvailableToPromise |
      | c_1        | INVENTORY_UP      |                               | p_1                     | 2021-06-23T00:00:00.00Z | 100 | 100                    |
      | c_3        | INVENTORY_DOWN    | STOCK_CHANGE                  | p_1                     | 2021-06-24T00:00:00.00Z | -15 | 85                     |
      | c_2        | INVENTORY_DOWN    |                               | p_1                     | 2021-06-25T00:00:00.00Z | -60 | 25                     |
    And metasfresh has this MD_Candidate_StockChange_Detail data
      | MD_Candidate_ID.Identifier | Fresh_QtyOnHand_ID | Fresh_QtyOnHand_Line_ID | IsReverted |
      | c_3                        | 1                  | 11                      | N          |
    When metasfresh receives a StockEstimateDeletedEvent
      | M_Product_ID | Fresh_QtyOnHand_ID | Fresh_QtyOnHand_Line_ID | DateDoc                 | Qty |
      | p_1          | 1                  | 11                      | 2021-06-24T00:00:00.00Z | 85  |
    Then after not more than 30s, MD_Candidates are found
      | Identifier | MD_Candidate_Type | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected           | Qty | Qty_AvailableToPromise |
      | c_1        | INVENTORY_UP      |                               | p_1                     | 2021-06-23T00:00:00.00Z | 100 | 100                    |
      | c_2        | INVENTORY_DOWN    |                               | p_1                     | 2021-06-25T00:00:00.00Z | -60 | 40                     |
    And metasfresh has this MD_Candidate_StockChange_Detail data
      | MD_Candidate_ID.Identifier | Fresh_QtyOnHand_ID | Fresh_QtyOnHand_Line_ID | IsReverted |
      | c_3                        | 1                  | 11                      | Y          |