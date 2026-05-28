@from:cucumber
@allure.label.epic:E0100_Sales
@allure.label.feature:F00127_BundleSinglePrice
@ghActions:run_on_executor5
Feature: Compensation group component auto-pricing (F00127.1)

  When a C_CompensationGroup_Schema_TemplateLine has IsWithoutCharge='Y', the
  component order line created from that template line must automatically receive
  IsWithoutCharge='Y' and a Reason of "B". Lines
  without the flag keep their pricelist price unchanged.

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2026-05-27T08:00:00+02:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And metasfresh contains M_PricingSystems
      | Identifier | Name          | Value         |
      | ps_bsp     | ps_bspF00127  | ps_bspF00127  |

  @from:cucumber
  @Id:S0469_010
  @Id:S0469_010
  Scenario: Flag OFF — component keeps pricelist price (IsWithoutCharge=N)
    Given metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name             | SOTrx | IsTaxIncluded | PricePrecision |
      | pl_bsp     | ps_bsp             | DE                        | EUR                 | pl_bspF00127_N   | true  | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID | Name          | ValidFrom  |
      | plv_bsp    | pl_bsp         | plv_bspF00127 | 2026-01-01 |
    And metasfresh contains M_Products:
      | Identifier   | Name                     | IsStocked |
      | bundleN      | BundleSinglePrice_N_Main | false     |
      | componentN   | BundleSinglePrice_N_Comp | false     |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_TaxCategory_ID.InternalName | C_UOM_ID.X12DE355 |
      | pp_n_main  | plv_bsp                | bundleN      | 100.00   | Normal                        | PCE               |
      | pp_n_comp  | plv_bsp                | componentN   | 15.00    | Normal                        | PCE               |
    And metasfresh contains C_BPartners:
      | Identifier | Name          | IsCustomer | M_PricingSystem_ID |
      | bp_n       | BP_BspFlagN   | Y          | ps_bsp             |
    And metasfresh contains C_CompensationGroup_Schema:
      | Identifier | Name        |
      | schema_n   | BspSchema_N |
    And metasfresh contains C_CompensationGroup_Schema_TemplateLine:
      | Identifier | C_CompensationGroup_Schema_ID | M_Product_ID | Qty | C_UOM_ID | SeqNo | OPT.IsWithoutCharge |
      | tl_n       | schema_n                      | componentN   | 1   | PCE      | 10    | N                   |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered |
      | order_n    | true    | bp_n          | 2026-05-27  |
    When create compensation group from schema template:
      | C_Order_ID | C_CompensationGroup_Schema_ID | Qty |
      | order_n    | schema_n                      | 1   |
    Then validate C_OrderLine:
      | C_OrderLine_ID | M_Product_ID | OPT.IsWithoutCharge | OPT.Reason |
      | schema_ol_1    | componentN   | false               | -          |

  @from:cucumber
  @Id:S0469_020
  @Id:S0469_020
  Scenario: Flag ON — component auto-zeroed with reason (IsWithoutCharge=Y)
    Given metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name             | SOTrx | IsTaxIncluded | PricePrecision |
      | pl_bsp     | ps_bsp             | AT                        | EUR                 | pl_bspF00127_Y   | true  | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID | Name          | ValidFrom  |
      | plv_bsp    | pl_bsp         | plv_bspF00127 | 2026-01-01 |
    And metasfresh contains M_Products:
      | Identifier  | Name                     | IsStocked |
      | bundleY     | BundleSinglePrice_Y_Main | false     |
      | componentY  | BundleSinglePrice_Y_Comp | false     |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_TaxCategory_ID.InternalName | C_UOM_ID.X12DE355 |
      | pp_y_main  | plv_bsp                | bundleY      | 100.00   | Normal                        | PCE               |
      | pp_y_comp  | plv_bsp                | componentY   | 15.00    | Normal                        | PCE               |
    And metasfresh contains C_BPartners:
      | Identifier | Name         | IsCustomer | M_PricingSystem_ID |
      | bp_y       | BP_BspFlagY  | Y          | ps_bsp             |
    And metasfresh contains C_CompensationGroup_Schema:
      | Identifier | Name        |
      | schema_y   | BspSchema_Y |
    And metasfresh contains C_CompensationGroup_Schema_TemplateLine:
      | Identifier | C_CompensationGroup_Schema_ID | M_Product_ID | Qty | C_UOM_ID | SeqNo | OPT.IsWithoutCharge |
      | tl_y       | schema_y                      | componentY   | 1   | PCE      | 10    | Y                   |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered |
      | order_y    | true    | bp_y          | 2026-05-27  |
    When create compensation group from schema template:
      | C_Order_ID | C_CompensationGroup_Schema_ID | Qty |
      | order_y    | schema_y                      | 1   |
    Then validate C_OrderLine:
      | C_OrderLine_ID | M_Product_ID | OPT.IsWithoutCharge | OPT.Reason                     |
      | schema_ol_1    | componentY   | true                | B  |
    And validate C_OrderLine:
      | C_OrderLine_ID | M_Product_ID | OPT.price |
      | schema_ol_1    | componentY   | 0         |

  @from:cucumber
  @Id:S0469_030
  @Id:S0469_030
  Scenario: Mixed bundle — two template lines, one Y and one N, result mirrors independently
    Given metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name             | SOTrx | IsTaxIncluded | PricePrecision |
      | pl_bsp     | ps_bsp             | CH                        | EUR                 | pl_bspF00127_M   | true  | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID | Name          | ValidFrom  |
      | plv_bsp    | pl_bsp         | plv_bspF00127 | 2026-01-01 |
    And metasfresh contains M_Products:
      | Identifier | Name                     | IsStocked |
      | bundleM    | BundleSinglePrice_M_Main | false     |
      | compFree   | BundleSinglePrice_M_Free | false     |
      | compPaid   | BundleSinglePrice_M_Paid | false     |
    And metasfresh contains M_ProductPrices
      | Identifier   | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_TaxCategory_ID.InternalName | C_UOM_ID.X12DE355 |
      | pp_m_main    | plv_bsp                | bundleM      | 200.00   | Normal                        | PCE               |
      | pp_m_free    | plv_bsp                | compFree     | 25.00    | Normal                        | PCE               |
      | pp_m_paid    | plv_bsp                | compPaid     | 30.00    | Normal                        | PCE               |
    And metasfresh contains C_BPartners:
      | Identifier | Name        | IsCustomer | M_PricingSystem_ID |
      | bp_m       | BP_BspMixed | Y          | ps_bsp             |
    And metasfresh contains C_CompensationGroup_Schema:
      | Identifier | Name        |
      | schema_m   | BspSchema_M |
    And metasfresh contains C_CompensationGroup_Schema_TemplateLine:
      | Identifier | C_CompensationGroup_Schema_ID | M_Product_ID | Qty | C_UOM_ID | SeqNo | OPT.IsWithoutCharge |
      | tl_m_free  | schema_m                      | compFree     | 1   | PCE      | 10    | Y                   |
      | tl_m_paid  | schema_m                      | compPaid     | 1   | PCE      | 20    | N                   |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered |
      | order_m    | true    | bp_m          | 2026-05-27  |
    When create compensation group from schema template:
      | C_Order_ID | C_CompensationGroup_Schema_ID | Qty |
      | order_m    | schema_m                      | 1   |
    Then validate C_OrderLine:
      | C_OrderLine_ID | M_Product_ID | OPT.IsWithoutCharge | OPT.Reason                     |
      | schema_ol_1    | compFree     | true                | B  |
    And validate C_OrderLine:
      | C_OrderLine_ID | M_Product_ID | OPT.price |
      | schema_ol_1    | compFree     | 0         |
    And validate C_OrderLine:
      | C_OrderLine_ID | M_Product_ID | OPT.IsWithoutCharge | OPT.Reason |
      | schema_ol_2    | compPaid     | false               | -          |

  @from:cucumber
  @Id:S0469_040
  @Id:S0469_040
  Scenario: End-to-end OL to IC to IL — IsWithoutCharge and Reason propagate through invoicing
    Given metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name             | SOTrx | IsTaxIncluded | PricePrecision |
      | pl_bsp     | ps_bsp             | IT                        | EUR                 | pl_bspF00127_E2E | true  | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID | Name          | ValidFrom  |
      | plv_bsp    | pl_bsp         | plv_bspF00127 | 2026-01-01 |
    And metasfresh contains M_Products:
      | Identifier  | Name                      | IsStocked |
      | bundleE2E   | BundleSinglePrice_E2E     | false     |
      | compE2E     | BundleSinglePrice_E2EComp | false     |
    And metasfresh contains M_ProductPrices
      | Identifier    | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_TaxCategory_ID.InternalName | C_UOM_ID.X12DE355 |
      | pp_e2e_main   | plv_bsp                | bundleE2E    | 100.00   | Normal                        | PCE               |
      | pp_e2e_comp   | plv_bsp                | compE2E      | 20.00    | Normal                        | PCE               |
    And metasfresh contains C_BPartners:
      | Identifier | Name       | IsCustomer | M_PricingSystem_ID |
      | bp_e2e     | BP_BspE2E  | Y          | ps_bsp             |
    And metasfresh contains C_CompensationGroup_Schema:
      | Identifier  | Name          |
      | schema_e2e  | BspSchema_E2E |
    And metasfresh contains C_CompensationGroup_Schema_TemplateLine:
      | Identifier | C_CompensationGroup_Schema_ID | M_Product_ID | Qty | C_UOM_ID | SeqNo | OPT.IsWithoutCharge |
      | tl_e2e     | schema_e2e                    | compE2E      | 1   | PCE      | 10    | Y                   |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered |
      | order_e2e  | true    | bp_e2e        | 2026-05-27  |
    When create compensation group from schema template:
      | C_Order_ID | C_CompensationGroup_Schema_ID | Qty |
      | order_e2e  | schema_e2e                    | 1   |
    Then validate C_OrderLine:
      | C_OrderLine_ID | M_Product_ID | OPT.IsWithoutCharge | OPT.Reason                     |
      | schema_ol_1    | compE2E      | true                | B  |
    # Complete the order to trigger IC creation
    And the order identified by order_e2e is completed
    # Wait for invoice candidate
    And after not more than 60s locate up2date invoice candidates by order line:
      | C_OrderLine_ID | C_Invoice_Candidate_ID |
      | schema_ol_1    | ic_e2e                 |
    Then validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID | OPT.IsWithoutCharge | OPT.Reason                     |
      | ic_e2e                 | true                | B  |
    # Generate invoice: set InvoiceRule_Override=I (Immediate), then process
    And update invoice candidates
      | C_Invoice_Candidate_ID | OPT.InvoiceRule_Override |
      | ic_e2e                 | I                        |
    And process invoice candidates and wait 60s for C_Invoice_Candidate to be processed
      | C_Invoice_Candidate_ID |
      | ic_e2e                 |
    Then after not more than 60s, C_Invoice are found:
      | C_Invoice_ID.Identifier | C_Invoice_Candidate_ID.Identifier |
      | inv_e2e                 | ic_e2e                            |
    And validate created invoice lines
      | C_Invoice_ID.Identifier | M_Product_ID | QtyInvoiced | OPT.IsWithoutCharge | OPT.Reason                     |
      | inv_e2e                 | compE2E      | 1           | true                | B  |

  @from:cucumber
  @Id:S0469_050
  @Id:S0469_050
  Scenario: Guard rail — a manually added order line (not via template) is not auto-flagged
    # This scenario proves that IsWithoutCharge auto-flagging only happens when
    # OrderGroupRepository creates a line from a TemplateLine that has IsWithoutCharge=Y.
    # A line added directly to the order (not through the template mechanism) keeps
    # IsWithoutCharge=N regardless of any other lines in the order.
    Given metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name             | SOTrx | IsTaxIncluded | PricePrecision |
      | pl_bsp     | ps_bsp             | FR                        | EUR                 | pl_bspF00127_G   | true  | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID | Name          | ValidFrom  |
      | plv_bsp    | pl_bsp         | plv_bspF00127 | 2026-01-01 |
    And metasfresh contains M_Products:
      | Identifier | Name                      | IsStocked |
      | bundleG    | BundleSinglePrice_G_Main  | false     |
      | compG      | BundleSinglePrice_G_Comp  | false     |
      | extraG     | BundleSinglePrice_G_Extra | false     |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_TaxCategory_ID.InternalName | C_UOM_ID.X12DE355 |
      | pp_g_main  | plv_bsp                | bundleG      | 100.00   | Normal                        | PCE               |
      | pp_g_comp  | plv_bsp                | compG        | 15.00    | Normal                        | PCE               |
      | pp_g_extra | plv_bsp                | extraG       | 12.00    | Normal                        | PCE               |
    And metasfresh contains C_BPartners:
      | Identifier | Name        | IsCustomer | M_PricingSystem_ID |
      | bp_g       | BP_BspGuard | Y          | ps_bsp             |
    And metasfresh contains C_CompensationGroup_Schema:
      | Identifier | Name        |
      | schema_g   | BspSchema_G |
    And metasfresh contains C_CompensationGroup_Schema_TemplateLine:
      | Identifier | C_CompensationGroup_Schema_ID | M_Product_ID | Qty | C_UOM_ID | SeqNo | OPT.IsWithoutCharge |
      | tl_g       | schema_g                      | compG        | 1   | PCE      | 10    | Y                   |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered |
      | order_g    | true    | bp_g          | 2026-05-27  |
    # Create the bundle via template — component line gets auto-flagged
    When create compensation group from schema template:
      | C_Order_ID | C_CompensationGroup_Schema_ID | Qty |
      | order_g    | schema_g                      | 1   |
    Then validate C_OrderLine:
      | C_OrderLine_ID | M_Product_ID | OPT.IsWithoutCharge | OPT.Reason                     |
      | schema_ol_1    | compG        | true                | B  |
    # Now add an extra line to the SAME order directly (not via template)
    Given metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | ol_g_extra | order_g    | extraG       | 1          |
    # The manually added line must NOT be auto-flagged — it has no template context
    Then validate C_OrderLine:
      | C_OrderLine_ID | M_Product_ID | OPT.IsWithoutCharge | OPT.Reason |
      | ol_g_extra     | extraG       | false               | -          |
