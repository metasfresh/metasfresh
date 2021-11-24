@from:cucumber
@topic:materialdispo
Feature: material-dispo updates on StockEstimateEvent events
  As a user
  I want material dispo to be updated properly if a StockEstimateEvent is processed or unprocessed
  So that the ATP is always correct

  Background:
    Given the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh initially has no MD_Candidate data
    And metasfresh initially has no MD_Candidate_StockChange_detail data
    And no product with value 'product_value1234' exists
    And metasfresh contains M_Product with M_Product_ID '1234'

  @from:cucumber
  @topic:materialdispo
  Scenario: StockEstimateCreatedEvent without stock or ATP
    When metasfresh receives a StockEstimateCreatedEvent
      | M_Product_ID | Fresh_QtyOnHand_ID | Fresh_QtyOnHand_Line_ID | DateDoc                 | Qty |
      | 1234         | 2                  | 22                      | 2021-06-23T00:00:00.00Z | 10  |
    Then metasfresh has this MD_Candidate data
      | Identifier | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected           | Qty | Qty_AvailableToPromise |
      | c_2        | INVENTORY_UP      | STOCK_CHANGE              | 1234                    | 2021-06-23T00:00:00.00Z | 10  | 10                     |
    And metasfresh has this MD_Candidate_StockChange_Detail data
      | MD_Candidate_ID.Identifier | Fresh_QtyOnHand_ID | Fresh_QtyOnHand_Line_ID | IsReverted |
      | c_2                        | 2                  | 22                      | N          |

  @from:cucumber
  @topic:materialdispo
  Scenario: StockEstimateCreatedEvent with stock
    And metasfresh initially has this MD_Candidate data
      | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected           | Qty | Qty_AvailableToPromise |
      | INVENTORY_UP      |                           | 1234                    | 2020-12-12T00:00:00.00Z | 100 | 100                    |
    When metasfresh receives a StockEstimateCreatedEvent
      | M_Product_ID | Fresh_QtyOnHand_ID | Fresh_QtyOnHand_Line_ID | DateDoc                 | Qty |
      | 1234         | 2                  | 22                      | 2021-06-23T00:00:00.00Z | 90  |
    Then metasfresh has this MD_Candidate data
      | Identifier | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected           | Qty | Qty_AvailableToPromise |
      | c_1        | INVENTORY_UP      |                           | 1234                    | 2020-12-12T00:00:00.00Z | 100 | 100                    |
      | c_2        | INVENTORY_DOWN    | STOCK_CHANGE              | 1234                    | 2021-06-23T00:00:00.00Z | -10 | 90                     |
    And metasfresh has this MD_Candidate_StockChange_Detail data
      | MD_Candidate_ID.Identifier | Fresh_QtyOnHand_ID | Fresh_QtyOnHand_Line_ID | IsReverted |
      | c_2                        | 2                  | 22                      | N          |

  @from:cucumber
  @topic:materialdispo
  Scenario: StockEstimateDeletedEvents without related data are ignored
    And metasfresh initially has this MD_Candidate data
      | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected           | Qty | Qty_AvailableToPromise |
      | INVENTORY_UP      |                           | 1234                    | 2020-12-12T10:00:00.00Z | 100 | 100                    |
    When metasfresh receives a StockEstimateDeletedEvent
      | M_Product_ID | Fresh_QtyOnHand_ID | Fresh_QtyOnHand_Line_ID | DateDoc                 | Qty |
      | 1234         | 1                  | 11                      | 2021-06-23T23:59:00.00Z | 10  |
    Then metasfresh has this MD_Candidate data
      | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected           | Qty | Qty_AvailableToPromise |
      | INVENTORY_UP      |                           | 1234                    | 2020-12-12T10:00:00.00Z | 100 | 100                    |

  @from:cucumber
  @topic:materialdispo
  Scenario: StockEstimateDeletedEvents with related data are processed
    And metasfresh receives a StockEstimateCreatedEvent
      | M_Product_ID | Fresh_QtyOnHand_ID | Fresh_QtyOnHand_Line_ID | DateDoc                 | Qty |
      | 1234         | 1                  | 11                      | 2021-06-23T00:00:00.00Z | 90  |
    And metasfresh has this MD_Candidate data
      | Identifier | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected           | Qty | Qty_AvailableToPromise |
      | c_1        | INVENTORY_UP      | STOCK_CHANGE              | 1234                    | 2021-06-23T00:00:00.00Z | 90  | 90                     |
    And metasfresh has this MD_Candidate_StockChange_Detail data
      | MD_Candidate_ID.Identifier | Fresh_QtyOnHand_ID | Fresh_QtyOnHand_Line_ID | IsReverted |
      | c_1                        | 1                  | 11                      | N          |
    When metasfresh receives a StockEstimateDeletedEvent
      | M_Product_ID | Fresh_QtyOnHand_ID | Fresh_QtyOnHand_Line_ID | DateDoc                 | Qty |
      | 1234         | 1                  | 11                      | 2021-06-23T00:00:00.00Z | 90  |
    Then metasfresh has no MD_Candidate for identifier "c_1"
    And metasfresh has this MD_Candidate_StockChange_Detail data
      | MD_Candidate_ID.Identifier | Fresh_QtyOnHand_ID | Fresh_QtyOnHand_Line_ID | IsReverted |
      | c_1                        | 1                  | 11                      | Y          |

  @from:cucumber
  @topic:materialdispo
  Scenario: StockEstimateEvent flow with INVENTORY_UP and positive ATP
    And metasfresh initially has this MD_Candidate data
      | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected           | Qty | Qty_AvailableToPromise |
      | INVENTORY_UP      |                           | 1234                    | 2021-06-23T00:00:00.00Z | 100 | 100                    |
      | INVENTORY_UP      |                           | 1234                    | 2021-06-25T00:00:00.00Z | 40  | 140                    |
    And metasfresh receives a StockEstimateCreatedEvent
      | M_Product_ID | Fresh_QtyOnHand_ID | Fresh_QtyOnHand_Line_ID | DateDoc                 | Qty |
      | 1234         | 1                  | 11                      | 2021-06-24T00:00:00.00Z | 160 |
    And metasfresh has this MD_Candidate data
      | Identifier | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected           | Qty | Qty_AvailableToPromise |
      | c_1        | INVENTORY_UP      |                           | 1234                    | 2021-06-23T00:00:00.00Z | 100 | 100                    |
      | c_3        | INVENTORY_UP      | STOCK_CHANGE              | 1234                    | 2021-06-24T00:00:00.00Z | 60  | 160                    |
      | c_2        | INVENTORY_UP      |                           | 1234                    | 2021-06-25T00:00:00.00Z | 40  | 200                    |
    And metasfresh has this MD_Candidate_StockChange_Detail data
      | MD_Candidate_ID.Identifier | Fresh_QtyOnHand_ID | Fresh_QtyOnHand_Line_ID | IsReverted |
      | c_3                        | 1                  | 11                      | N          |
    When metasfresh receives a StockEstimateDeletedEvent
      | M_Product_ID | Fresh_QtyOnHand_ID | Fresh_QtyOnHand_Line_ID | DateDoc                 | Qty |
      | 1234         | 1                  | 11                      | 2021-06-24T00:00:00.00Z | 160 |
    Then metasfresh has this MD_Candidate data
      | Identifier | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected           | Qty | Qty_AvailableToPromise |
      | c_1        | INVENTORY_UP      |                           | 1234                    | 2021-06-23T00:00:00.00Z | 100 | 100                    |
      | c_2        | INVENTORY_UP      |                           | 1234                    | 2021-06-25T00:00:00.00Z | 40  | 140                    |
    And metasfresh has this MD_Candidate_StockChange_Detail data
      | MD_Candidate_ID.Identifier | Fresh_QtyOnHand_ID | Fresh_QtyOnHand_Line_ID | IsReverted |
      | c_3                        | 1                  | 11                      | Y          |

  @from:cucumber
  @topic:materialdispo
  Scenario: Stock estimate event with INVENTORY_DOWN and positive ATP
    And metasfresh initially has this MD_Candidate data
      | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected           | Qty | Qty_AvailableToPromise |
      | INVENTORY_UP      |                           | 1234                    | 2021-06-23T00:00:00.00Z | 100 | 100                    |
      | INVENTORY_DOWN    |                           | 1234                    | 2021-06-25T00:00:00.00Z | -60 | 40                     |
    When metasfresh receives a StockEstimateCreatedEvent
      | M_Product_ID | Fresh_QtyOnHand_ID | Fresh_QtyOnHand_Line_ID | DateDoc                 | Qty |
      | 1234         | 1                  | 11                      | 2021-06-24T00:00:00.00Z | 85  |
    Then metasfresh has this MD_Candidate data
      | Identifier | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected           | Qty | Qty_AvailableToPromise |
      | c_1        | INVENTORY_UP      |                           | 1234                    | 2021-06-23T00:00:00.00Z | 100 | 100                    |
      | c_3        | INVENTORY_DOWN    | STOCK_CHANGE              | 1234                    | 2021-06-24T00:00:00.00Z | -15 | 85                     |
      | c_2        | INVENTORY_DOWN    |                           | 1234                    | 2021-06-25T00:00:00.00Z | -60 | 25                     |
    And metasfresh has this MD_Candidate_StockChange_Detail data
      | MD_Candidate_ID.Identifier | Fresh_QtyOnHand_ID | Fresh_QtyOnHand_Line_ID | IsReverted |
      | c_3                        | 1                  | 11                      | N          |
    When metasfresh receives a StockEstimateDeletedEvent
      | M_Product_ID | Fresh_QtyOnHand_ID | Fresh_QtyOnHand_Line_ID | DateDoc                 | Qty |
      | 1234         | 1                  | 11                      | 2021-06-24T00:00:00.00Z | 85  |
    Then metasfresh has this MD_Candidate data
      | Identifier | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected           | Qty | Qty_AvailableToPromise |
      | c_1        | INVENTORY_UP      |                           | 1234                    | 2021-06-23T00:00:00.00Z | 100 | 100                    |
      | c_2        | INVENTORY_DOWN    |                           | 1234                    | 2021-06-25T00:00:00.00Z | -60 | 40                     |
    And metasfresh has this MD_Candidate_StockChange_Detail data
      | MD_Candidate_ID.Identifier | Fresh_QtyOnHand_ID | Fresh_QtyOnHand_Line_ID | IsReverted |
      | c_3                        | 1                  | 11                      | Y          |