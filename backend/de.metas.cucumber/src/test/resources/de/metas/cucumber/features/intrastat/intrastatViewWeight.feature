@from:cucumber
@ghActions:run_on_executor7
Feature: Intrastat view M_InOut_V computes weight per commodity group

  gh#28336: The M_InOut_V view must report weight per line/commodity group,
  NOT the total shipment weight. Before the fix, the view used
  Docs_Sales_InOut_Sum_Weight (which sums all lines of the entire shipment),
  causing every commodity group to show the full shipment weight.

  This test ships two products with different commodity numbers in a single
  shipment and verifies that each row in the view shows the correct per-product
  weight — not the total.

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And set sys config boolean value false for sys config AUTO_SHIP_AND_INVOICE

  @from:cucumber
  @Id:S0_gh28336
  Scenario: Weight in M_InOut_V is per commodity group, not total shipment weight
    Given metasfresh has date and time 2026-01-15T13:30:13+01:00[Europe/Berlin]

    # --- Products ---
    And metasfresh contains M_Products:
      | Identifier | Name                     |
      | p_intra_1  | Intrastat Test Product A |
      | p_intra_2  | Intrastat Test Product B |

    And M_Product weight is set:
      | M_Product_ID.Identifier | Weight |
      | p_intra_1               | 2.5    |
      | p_intra_2               | 3.0    |

    And M_CommodityNumber is set for products:
      | M_Product_ID.Identifier | CommodityNumberValue |
      | p_intra_1               | 85171200             |
      | p_intra_2               | 73181500             |

    And M_CustomsTariff is set for products:
      | M_Product_ID.Identifier | CustomsTariffValue |
      | p_intra_1               | 8517120000         |
      | p_intra_2               | 7318150000         |

    # --- Pricing ---
    And metasfresh contains M_PricingSystems
      | Identifier  | Name                     | Value                     |
      | ps_intra    | intra_pricing_system     | intra_pricing_system_val  |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name              | SOTrx | IsTaxIncluded | PricePrecision |
      | pl_intra   | ps_intra                      | FR                        | EUR                 | intra_price_list  | true  | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name         | ValidFrom  |
      | plv_intra  | pl_intra                  | intra_PLV    | 2025-01-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_1       | plv_intra                         | p_intra_1               | 100.0    | PCE               | Normal                        |
      | pp_2       | plv_intra                         | p_intra_2               | 200.0    | PCE               | Normal                        |

    # --- Customer in a foreign country (FR) — must differ from org country (AT or DE) for cross-border filter ---
    And metasfresh contains C_BPartners:
      | Identifier     | Name                   | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier | OPT.InvoiceRule | OPT.VATaxID      |
      | bp_intra       | Intrastat Test Partner | N            | Y              | ps_intra                      | D               | FR123456789       |
    And metasfresh contains C_BPartner_Locations:
      | Identifier | GLN           | C_BPartner_ID.Identifier | OPT.IsShipToDefault | OPT.IsBillToDefault | C_Country_ID |
      | bpl_intra  | 9012345678901 | bp_intra                 | Y                   | Y                   | FR           |

    # --- Sales order with 2 lines ---
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered |
      | o_intra    | true    | bp_intra                 | 2026-01-15  |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_1       | o_intra               | p_intra_1               | 10         |
      | ol_2       | o_intra               | p_intra_2               | 5          |
    When the order identified by o_intra is completed
    And wait until de.metas.async rabbitMQ queue is empty or throw exception after 5 minutes

    # --- Generate shipment (batch mode: both products merge into one M_InOut) ---
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | ss_1       | ol_1                      | N             |
      | ss_2       | ol_2                      | N             |
    And 'generate shipments' process is invoked with QuantityType=D, IsCompleteShipments=true and IsShipToday=false
      | M_ShipmentSchedule_ID.Identifier |
      | ss_1                             |
      | ss_2                             |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | ss_1                             | ship_intra            |

    # --- Generate invoice ---
    And after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice |
      | ic_1                              | ol_1                      | 10           |
      | ic_2                              | ol_2                      | 5            |
    When process invoice candidates and wait 60s for C_Invoice_Candidate to be processed
      | C_Invoice_Candidate_ID.Identifier |
      | ic_1                              |
      | ic_2                              |

    # --- Query and validate the Intrastat view ---
    # Weight must be PER PRODUCT, not the total shipment weight:
    #   Product A: 10 PCE * 2.5 kg/PCE = 25 kg
    #   Product B:  5 PCE * 3.0 kg/PCE = 15 kg
    # Before the fix, both would show 40 kg (total shipment weight).
    When M_InOut_V is queried for shipment "ship_intra"
    Then M_InOut_V result contains:
      | M_Product_ID.Identifier | weight | movementqty |
      | p_intra_1               | 25     | 10          |
      | p_intra_2               | 15     | 5           |
