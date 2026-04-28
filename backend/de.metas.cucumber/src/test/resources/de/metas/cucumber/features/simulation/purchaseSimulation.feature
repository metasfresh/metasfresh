@from:cucumber
@allure.label.epic:E0100_Sales
@allure.label.feature:F00106
@ghActions:run_on_executor1
Feature: create purchase simulation
## F00106: Simulation

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2021-04-11T08:00:00+01:00[Europe/Berlin]
    And metasfresh contains M_Products:
      | Identifier |
      | p_1        |
    And metasfresh contains M_PricingSystems
      | Identifier |
      | ps_1       |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | C_Country_ID | C_Currency_ID | SOTrx |
      | pl_1       | ps_1               | DE           | EUR           | true  |
      | pl_2       | ps_1               | DE           | EUR           | false |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID |
      | plv_1      | pl_1           |
      | plv_2      | pl_2           |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID |
      | plv_1                  | p_1          | 10.0     | PCE      |
      | plv_2                  | p_1          | 10.0     | PCE      |
    And metasfresh contains M_DiscountSchema
      | M_DiscountSchema_ID.Identifier | Name               | BreakValueType | DiscountType | ValidFrom  |
      | discountSchema_1               | DiscountSchemaName | Q              | P            | 2021-04-01 |
    And metasfresh contains C_BPartners without locations:
      | Identifier | IsVendor | IsCustomer | M_PricingSystem_ID | PO_DiscountSchema_ID |
      | customer_1 | N        | Y          | ps_1               |                      |
      | vendor_1   | Y        | N          | ps_1               | discountSchema_1     |
    And metasfresh contains C_BPartner_Locations:
      | Identifier          | C_BPartner_ID | IsShipToDefault | IsBillToDefault |
      | vendor_location_1   | vendor_1      | Y               | Y               |
      | customer_location_1 | customer_1    | Y               | Y               |
    And metasfresh contains C_BPartner_Product
      | C_BPartner_ID | M_Product_ID |
      | vendor_1      | p_1          |
    And metasfresh contains PP_Product_Plannings
      | Identifier | M_Product_ID | IsCreatePlan | IsPurchased |
      | ppln_1     | p_1          | true         | true        |

  @from:cucumber
@allure.label.epic:E0100_Sales
@allure.label.feature:F00106
  @Id:S0171.200
  Scenario: create purchase simulation, delete order line and validate there is no purchase candidate for deleted line
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | PreparationDate      |
      | o_1        | true    | customer_1    | 2021-04-04  | 2021-04-04T00:00:00Z |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | ol_1       | o_1        | p_1          | 100        |
    When create and process 'simulated demand' for:
      | C_Order_ID | C_OrderLine_ID |
      | o_1        | ol_1           |
    Then after not more than 30s, the MD_Candidate table has only the following records
      | Identifier | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID | DateProjected        | Qty | Qty_AvailableToPromise | simulated |
      | c_1        | DEMAND            | SHIPMENT                  | p_1          | 2021-04-04T00:00:00Z | 100 | -100                   | true      |
      | c_2        | SUPPLY            | PURCHASE                  | p_1          | 2021-04-04T00:00:00Z | 100 | 0                      | true      |
    And after not more than 30s, C_PurchaseCandidate found for orderLine ol_1
      | Identifier |
      | pc_1       |
    And delete C_OrderLine identified by ol_1, but keep its id into identifierIds table
    And no C_PurchaseCandidate found for orderLine ol_1

  @from:cucumber
@allure.label.epic:E0100_Sales
@allure.label.feature:F00106
  Scenario: Create Sales Order. Create C_PurchaseCandidate. Create C_Order for it. Reactivate and reduce qty. C_PurchaseCandidate is not adjusted.
    When update existing PP_Product_Plannings
      | Identifier | IsCreatePlan | OPT.IsDocComplete |
      | ppln_1     | true         | true              |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | PreparationDate      |
      | o_1        | true    | customer_1    | 2021-04-04  | 2021-04-04T00:00:00Z |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | ol_1       | o_1        | p_1          | 100        |
    And the order identified by o_1 is completed
    And after not more than 30s, C_PurchaseCandidate found for orderLine ol_1
      | Identifier | QtyToPurchase |
      | pc_1       | 100 PCE       |

    Then after not more than 30s, the MD_Candidate table has only the following records
      | Identifier | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID | DateProjected        | Qty | Qty_AvailableToPromise | C_Purchase_Candidate_ID |
      | c_1        | DEMAND            | SHIPMENT                  | p_1          | 2021-04-04T00:00:00Z | 100 | -100                   |                         |
      | c_2        | SUPPLY            | PURCHASE                  | p_1          | 2021-04-04T00:00:00Z | 100 | 0                      | pc_1                    |

    And after not more than 60s, C_PurchaseCandidate_Alloc are found
      | C_PurchaseCandidate_ID | C_PurchaseCandidate_Alloc_ID |
      | pc_1                   | pca_1                        |

    And load C_OrderLines from C_PurchaseCandidate_Alloc
      | C_OrderLinePO_ID | C_PurchaseCandidate_Alloc_ID |
      | pol_1            | pca_1                        |

    And load C_Order from C_OrderLine
      | C_Order_ID | C_OrderLine_ID |
      | po_1       | pol_1          |

    Then validate the created orders
      | C_Order_ID | C_BPartner_ID | C_BPartner_Location_ID | DateOrdered | DocBaseType | currencyCode | DeliveryRule | DeliveryViaRule | processed | DocStatus |
      | po_1       | vendor_1      | vendor_location_1      | 2021-04-04  | POO         | EUR          | F            | P               | true      | CO        |

    And validate C_OrderLine:
      | C_OrderLine_ID | C_Order_ID | DateOrdered | M_Product_ID | QtyOrdered | qtydelivered | qtyinvoiced | price | discount | currencyCode | processed |
      | pol_1          | po_1       | 2021-04-04  | p_1          | 100        | 0            | 0           | 10    | 0        | EUR          | true      |

    Then after not more than 30s, the MD_Candidate table has only the following records
      | Identifier | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID | DateProjected        | Qty | Qty_AvailableToPromise |
      | c_1        | DEMAND            | SHIPMENT                  | p_1          | 2021-04-04T00:00:00Z | 100 | -100                   |
      | c_2        | SUPPLY            | PURCHASE                  | p_1          | 2021-04-04T00:00:00Z | 100 | 0                      |

    And validate that there are no M_ShipmentSchedule_Recompute records after no more than 60 seconds for order 'o_1'

    And the order identified by o_1 is reactivated

    And after not more than 30s, C_PurchaseCandidate found for orderLine ol_1
      | Identifier | QtyToPurchase |
      | pc_1       | 100 PCE       |

    And after not more than 30s, the MD_Candidate table has only the following records
      | Identifier | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID | DateProjected        | Qty | Qty_AvailableToPromise |
      | c_1        | DEMAND            | SHIPMENT                  | p_1          | 2021-04-04T00:00:00Z | 0   | 0                      |
      | c_2        | SUPPLY            | PURCHASE                  | p_1          | 2021-04-04T00:00:00Z | 100 | 100                    |

    And update C_OrderLine:
      | C_OrderLine_ID.Identifier | OPT.QtyEntered |
      | ol_1                      | 30             |

    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes

    And the order identified by o_1 is completed

    And validate that there are no M_ShipmentSchedule_Recompute records after no more than 60 seconds for order 'o_1'

    And after not more than 30s, the MD_Candidate table has only the following records
      | Identifier | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID | DateProjected        | Qty | Qty_AvailableToPromise |
      | c_1        | DEMAND            | SHIPMENT                  | p_1          | 2021-04-04T00:00:00Z | 30  | -30                    |
      | c_2        | SUPPLY            | PURCHASE                  | p_1          | 2021-04-04T00:00:00Z | 100 | 70                     |
