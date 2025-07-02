@from:cucumber
@ghActions:run_on_executor7
Feature: create purchase simulation

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2021-04-11T08:00:00+01:00[Europe/Berlin]
    And metasfresh contains M_Products:
      | Identifier |
      | p_1        |
    And metasfresh contains M_PricingSystems
      | Identifier | Name                | Value                | OPT.Description            | OPT.IsActive |
      | ps_1       | pricing_system_name | pricing_system_value | pricing_system_description | true         |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name              | OPT.Description | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | pl_1       | ps_1                          | DE                        | EUR                 | price_list_name_1 | null            | true  | false         | 2              | true         |
      | pl_2       | ps_1                          | DE                        | EUR                 | price_list_name_2 | null            | false | false         | 2              | true         |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name               | ValidFrom  |
      | plv_1      | pl_1                      | priceListVersion_1 | 2021-04-01 |
      | plv_2      | pl_2                      | priceListVersion_2 | 2021-04-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_1       | plv_1                             | p_1                     | 10.0     | PCE               | Normal                        |
      | pp_2       | plv_2                             | p_1                     | 10.0     | PCE               | Normal                        |
    And metasfresh contains M_DiscountSchema
      | M_DiscountSchema_ID.Identifier | Name               | BreakValueType | DiscountType | ValidFrom  |
      | discountSchema_1               | DiscountSchemaName | Q              | P            | 2021-04-01 |
    And metasfresh contains C_BPartners:
      | Identifier | Name     | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier | OPT.PO_DiscountSchema_ID.Identifier |
      | customer_1 | Customer | N            | Y              | ps_1                          |                                     |
      | vendor_1   | Vendor   | Y            | N              | ps_1                          | discountSchema_1                    |
    And metasfresh contains C_BPartner_Locations:
      | Identifier          | GLN           | C_BPartner_ID.Identifier | OPT.Name     | OPT.IsShipToDefault | OPT.IsBillToDefault |
      | vendor_location_1   | 2311203300000 | vendor_1                 | vendor1Loc   | Y                   | Y                   |
      | customer_location_1 | 2311203300001 | customer_1               | customer1Loc | Y                   | Y                   |
    And metasfresh contains C_BPartner_Product
      | C_BPartner_ID.Identifier | M_Product_ID.Identifier |
      | vendor_1                 | p_1                     |
    And metasfresh contains PP_Product_Plannings
      | Identifier | M_Product_ID.Identifier | IsCreatePlan | OPT.IsPurchased |
      | ppln_1     | p_1                     | true         | true            |

  @from:cucumber
  @Id:S0171.200
  Scenario: create purchase simulation, delete order line and validate there is no purchase candidate for deleted line
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.PreparationDate  |
      | o_1        | true    | customer_1               | 2021-04-04  | 2021-04-04T00:00:00Z |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_1       | o_1                   | p_1                     | 100        |
    When create and process 'simulated demand' for:
      | C_Order_ID.Identifier | C_OrderLine_ID.Identifier |
      | o_1                   | ol_1                      |
    Then after not more than 30s, the MD_Candidate table has only the following records
      | Identifier | MD_Candidate_Type | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected        | Qty | Qty_AvailableToPromise | OPT.simulated |
      | c_1        | DEMAND            | SHIPMENT                      | p_1                     | 2021-04-04T00:00:00Z | 100 | -100                   | true          |
      | c_2        | SUPPLY            | PURCHASE                      | p_1                     | 2021-04-04T00:00:00Z | 100 | 0                      | true          |
    And after not more than 30s, C_PurchaseCandidate found for orderLine ol_1
      | Identifier |
      | pc_1       |
    And delete C_OrderLine identified by ol_1, but keep its id into identifierIds table
    And no C_PurchaseCandidate found for orderLine ol_1

  @from:cucumber
  Scenario: Create Sales Order. Create C_PurchaseCandidate. Create C_Order for it. Reactivate and reduce qty. C_PurchaseCandidate is not adjusted.
    When update existing PP_Product_Plannings
      | Identifier | IsCreatePlan |
      | ppln_1     | true         |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.PreparationDate  |
      | o_1        | true    | customer_1               | 2021-04-04  | 2021-04-04T00:00:00Z |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_1       | o_1                   | p_1                     | 100        |
    And the order identified by o_1 is completed
    And after not more than 30s, C_PurchaseCandidate found for orderLine ol_1
      | Identifier | QtyToPurchase |
      | pc_1       | 100 PCE       |

    Then after not more than 30s, the MD_Candidate table has only the following records
      | Identifier | MD_Candidate_Type | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected        | Qty | Qty_AvailableToPromise | C_Purchase_Candidate_ID |
      | c_1        | DEMAND            | SHIPMENT                      | p_1                     | 2021-04-04T00:00:00Z | 100 | -100                   |                         |
      | c_2        | SUPPLY            | PURCHASE                      | p_1                     | 2021-04-04T00:00:00Z | 100 | 0                      | pc_1                    |

    And the following C_PurchaseCandidates are enqueued for generating C_Orders
      | C_PurchaseCandidate_ID.Identifier |
      | pc_1                              |

    And after not more than 60s, C_PurchaseCandidate_Alloc are found
      | C_PurchaseCandidate_ID.Identifier | C_PurchaseCandidate_Alloc_ID.Identifier |
      | pc_1                              | pca_1                                   |

    And load C_OrderLines from C_PurchaseCandidate_Alloc
      | C_OrderLinePO_ID.Identifier | C_PurchaseCandidate_Alloc_ID.Identifier |
      | pol_1                       | pca_1                                   |

    And load C_Order from C_OrderLine
      | C_Order_ID.Identifier | C_OrderLine_ID.Identifier |
      | po_1                  | pol_1                     |

    Then validate the created orders
      | C_Order_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | dateordered | docbasetype | currencyCode | deliveryRule | deliveryViaRule | processed | docStatus |
      | po_1                  | vendor_1                 | vendor_location_1                 | 2021-04-11  | POO         | EUR          | F            | P               | true      | CO        |

    And validate C_OrderLine:
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | OPT.DateOrdered | M_Product_ID.Identifier | QtyOrdered | qtydelivered | qtyinvoiced | price | discount | currencyCode | processed |
      | pol_1                     | po_1                  | 2021-04-11      | p_1                     | 100        | 0            | 0           | 10    | 0        | EUR          | true      |

    Then after not more than 30s, the MD_Candidate table has only the following records
      | Identifier | MD_Candidate_Type | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected        | Qty | Qty_AvailableToPromise |
      | c_1        | DEMAND            | SHIPMENT                      | p_1                     | 2021-04-04T00:00:00Z | 100 | -100                   |
      | c_2        | SUPPLY            | PURCHASE                      | p_1                     | 2021-04-04T00:00:00Z | 100 | 0                      |

    And the order identified by o_1 is reactivated

    And after not more than 30s, C_PurchaseCandidate found for orderLine ol_1
      | Identifier | QtyToPurchase |
      | pc_1       | 100 PCE       |

    And after not more than 30s, the MD_Candidate table has only the following records
      | Identifier | MD_Candidate_Type | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected        | Qty | Qty_AvailableToPromise |
      | c_1        | DEMAND            | SHIPMENT                      | p_1                     | 2021-04-04T00:00:00Z | 0   | 0                      |
      | c_2        | SUPPLY            | PURCHASE                      | p_1                     | 2021-04-04T00:00:00Z | 100 | 100                    |

    And update C_OrderLine:
      | C_OrderLine_ID.Identifier | OPT.QtyEntered |
      | ol_1                      | 30             |
    And the order identified by o_1 is completed

    And after not more than 30s, the MD_Candidate table has only the following records
      | Identifier | MD_Candidate_Type | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected        | Qty | Qty_AvailableToPromise |
      | c_1        | DEMAND            | SHIPMENT                      | p_1                     | 2021-04-04T00:00:00Z | 30  | -30                    |
      | c_2        | SUPPLY            | PURCHASE                      | p_1                     | 2021-04-04T00:00:00Z | 100 | 70                     |