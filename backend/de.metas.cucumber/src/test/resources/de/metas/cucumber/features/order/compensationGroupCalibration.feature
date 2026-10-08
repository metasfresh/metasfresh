@from:cucumber
@allure.label.epic:E0100_Sales
@allure.label.feature:F00127_BundleSinglePrice
@ghActions:run_on_executor5
Feature: Compensation group calibration

  Components of a compensation group created from a schema scale with the first matching calibration rule.
  - The factor is a 100-based percent: 80 = 80 %, 100 = unchanged, 0 = Qty 0.
  - Qty = (template Qty x menu Qty, rounded half-up to the UOM precision) x factor %, rounded up (away from zero) to the UOM precision.
  - A positive factor never rounds a non-zero base to 0; a factor of 0 creates the component line with Qty 0.
  - The menu line (a product that carries a schema) is never calibrated.
  - Purchase orders are never calibrated.

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2026-10-07T08:00:00+02:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And metasfresh contains C_UOMs:
      | Identifier | X12DE355 | Name  | UOMSymbol | StdPrecision | CostingPrecision |
      | uom_pce    | PCE      | Stück | Stk       | 0            | 4                |
      | uom_grm    | GRM      | Gramm | GRM       | 2            | 0                |
      | uom_ltr    | LTR      | Litre | LTR       | 2            | 0                |
    And validate C_UOM:
      | X12DE355 | StdPrecision |
      | PCE      | 0            |
      | GRM      | 2            |
      | LTR      | 2            |
    And metasfresh contains M_PricingSystems
      | Identifier |
      | ps         |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name       | SOTrx | IsTaxIncluded | PricePrecision |
      | pl_sales   | ps                 | DE                        | EUR                 | pl_calib_s | true  | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID | Name         | ValidFrom  |
      | plv_sales  | pl_sales       | plv_calib_s  | 2026-01-01 |
    And metasfresh contains M_Products:
      | Identifier | X12DE355 | IsStocked |
      | reis       | GRM      | false     |
      | kaese      | GRM      | false     |
      | gram       | GRM      | false     |
      | fisch      | PCE      | false     |
      | pce_a      | PCE      | false     |
      | pce_b      | PCE      | false     |
      | milch      | LTR      | false     |
      | kraft      | LTR      | false     |
      | tomate     | LTR      | false     |
      | discount   | PCE      | false     |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_TaxCategory_ID.InternalName | C_UOM_ID.X12DE355 |
      | pp_reis    | plv_sales              | reis         | 1        | Normal                        | GRM               |
      | pp_kaese   | plv_sales              | kaese        | 1        | Normal                        | GRM               |
      | pp_gram    | plv_sales              | gram         | 1        | Normal                        | GRM               |
      | pp_fisch   | plv_sales              | fisch        | 5        | Normal                        | PCE               |
      | pp_pce_a   | plv_sales              | pce_a        | 1        | Normal                        | PCE               |
      | pp_pce_b   | plv_sales              | pce_b        | 1        | Normal                        | PCE               |
      | pp_milch   | plv_sales              | milch        | 1        | Normal                        | LTR               |
      | pp_kraft   | plv_sales              | kraft        | 1        | Normal                        | LTR               |
      | pp_tomate  | plv_sales              | tomate       | 1        | Normal                        | LTR               |
      | pp_disc    | plv_sales              | discount     | 1        | Normal                        | PCE               |
    And metasfresh contains C_BP_Groups:
      | Identifier |
      | grp_a      |
      | grp_b      |
    And metasfresh contains C_BPartners:
      | Identifier | IsCustomer | C_BP_Group_ID | M_PricingSystem_ID |
      | cust_x     | Y          | grp_a         | ps                 |
      | cust_y     | Y          | grp_a         | ps                 |
      | cust_z     | Y          | grp_b         | ps                 |


  # ##########################################################################################
  # One base menu serves three customers (per-age-group quantities, group rule vs customer rule)
  @from:cucumber
  @Id:S26881_TC3
  Scenario: One base menu is calibrated per customer and per customer group
    Given metasfresh contains C_CompensationGroup_Schema:
      | Identifier  | Name        |
      | schema_base | CalibBase   |
    And metasfresh contains C_CompensationGroup_Schema_TemplateLine:
      | Identifier | C_CompensationGroup_Schema_ID | M_Product_ID | Qty  | C_UOM_ID | SeqNo |
      | tl_kraft   | schema_base                   | kraft        | 0.15 | LTR      | 10    |
      | tl_tomate  | schema_base                   | tomate       | 0.14 | LTR      | 20    |
      | tl_kaese   | schema_base                   | kaese        | 25   | GRM      | 30    |
    And metasfresh contains C_CompensationGroup_CalibrationRule:
      | Identifier | SeqNo | C_BPartner_ID | M_Product_ID | GroupCompensationCalibrationFactor |
      | r10        | 10    | cust_x        | kraft        | 40                                 |
      | r20        | 20    | cust_x        | tomate       | 64.3                               |
      | r30        | 30    | cust_x        | kaese        | 80                                 |
    And metasfresh contains C_CompensationGroup_CalibrationRule:
      | Identifier | SeqNo | C_BP_Group_ID | M_Product_ID | GroupCompensationCalibrationFactor |
      | r40        | 40    | grp_a         | kraft        | 66.7                               |
      | r50        | 50    | grp_a         | tomate       | 71.4                               |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered |
      | order_x    | true    | cust_x        | 2026-10-07  |
      | order_y    | true    | cust_y        | 2026-10-07  |
      | order_z    | true    | cust_z        | 2026-10-07  |

    # customer without any matching rule: the base quantities
    When create compensation group from schema template:
      | C_Order_ID | C_CompensationGroup_Schema_ID | Qty | Calibrated | IdentifyLinesBy |
      | order_z    | schema_base                   | 1   | Y          | Product         |
    Then validate C_OrderLine:
      | C_OrderLine_ID  | M_Product_ID | OPT.QtyEntered | OPT.GroupCompensationCalibrationFactor | OPT.GroupCompensationQtyEnteredUncalibrated | OPT.C_CompensationGroup_CalibrationRule_ID |
      | schema_ol_kraft | kraft        | 0.15           | 100                                    | 0.15                                        | null                                       |
      | schema_ol_tomate | tomate      | 0.14           | 100                                    | 0.14                                        | null                                       |
      | schema_ol_kaese | kaese        | 25             | 100                                    | 25                                          | null                                       |

    # customer in the group: the group rules
    When create compensation group from schema template:
      | C_Order_ID | C_CompensationGroup_Schema_ID | Qty | Calibrated | IdentifyLinesBy |
      | order_y    | schema_base                   | 1   | Y          | Product         |
    Then validate C_OrderLine:
      | C_OrderLine_ID  | M_Product_ID | OPT.QtyEntered | OPT.GroupCompensationCalibrationFactor | OPT.GroupCompensationQtyEnteredUncalibrated | OPT.C_CompensationGroup_CalibrationRule_ID |
      | schema_ol_kraft | kraft        | 0.11           | 66.7                                   | 0.15                                        | r40                                        |
      | schema_ol_tomate | tomate      | 0.10           | 71.4                                   | 0.14                                        | r50                                        |
      | schema_ol_kaese | kaese        | 25             | 100                                    | 25                                          | null                                       |

    # customer with own rules: customer rules come before the group rules
    When create compensation group from schema template:
      | C_Order_ID | C_CompensationGroup_Schema_ID | Qty | Calibrated | IdentifyLinesBy |
      | order_x    | schema_base                   | 1   | Y          | Product         |
    Then validate C_OrderLine:
      | C_OrderLine_ID  | M_Product_ID | OPT.QtyEntered | OPT.GroupCompensationCalibrationFactor | OPT.GroupCompensationQtyEnteredUncalibrated | OPT.C_CompensationGroup_CalibrationRule_ID |
      | schema_ol_kraft | kraft        | 0.06           | 40                                     | 0.15                                        | r10                                        |
      | schema_ol_tomate | tomate      | 0.10           | 64.3                                   | 0.14                                        | r20                                        |
      | schema_ol_kaese | kaese        | 20             | 80                                     | 25                                          | r30                                        |


  # ##########################################################################################
  # First match by SeqNo: customer rule above group rule, also on a quotation
  @from:cucumber
  @Id:S26881_TC4_SeqNo
  Scenario: The first matching rule by SeqNo wins, on sales orders and quotations
    Given metasfresh contains C_CompensationGroup_Schema:
      | Identifier | Name       |
      | schema_1   | CalibSeqNo |
    And metasfresh contains C_CompensationGroup_Schema_TemplateLine:
      | Identifier | C_CompensationGroup_Schema_ID | M_Product_ID | Qty | C_UOM_ID | SeqNo |
      | tl_reis    | schema_1                      | reis         | 200 | GRM      | 10    |
    And metasfresh contains C_CompensationGroup_CalibrationRule:
      | Identifier | SeqNo | C_BPartner_ID | GroupCompensationCalibrationFactor |
      | r10        | 10    | cust_x        | 70                                 |
    And metasfresh contains C_CompensationGroup_CalibrationRule:
      | Identifier | SeqNo | C_BP_Group_ID | GroupCompensationCalibrationFactor |
      | r20        | 20    | grp_a         | 50                                 |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | DocBaseType | DocSubType |
      | order_x    | true    | cust_x        | 2026-10-07  | SOO         | SO         |
      | order_y    | true    | cust_y        | 2026-10-07  | SOO         | SO         |
      | quote_x    | true    | cust_x        | 2026-10-07  | SOO         | ON         |

    When create compensation group from schema template:
      | C_Order_ID | C_CompensationGroup_Schema_ID | Qty | Calibrated | IdentifyLinesBy |
      | order_x    | schema_1                      | 1   | Y          | Product         |
    Then validate C_OrderLine:
      | C_OrderLine_ID | M_Product_ID | OPT.QtyEntered | OPT.GroupCompensationCalibrationFactor | OPT.GroupCompensationQtyEnteredUncalibrated | OPT.C_CompensationGroup_CalibrationRule_ID |
      | schema_ol_reis | reis         | 140            | 70                                     | 200                                         | r10                                        |
    When create compensation group from schema template:
      | C_Order_ID | C_CompensationGroup_Schema_ID | Qty | Calibrated | IdentifyLinesBy |
      | order_y    | schema_1                      | 1   | Y          | Product         |
    Then validate C_OrderLine:
      | C_OrderLine_ID | M_Product_ID | OPT.QtyEntered | OPT.GroupCompensationCalibrationFactor | OPT.GroupCompensationQtyEnteredUncalibrated | OPT.C_CompensationGroup_CalibrationRule_ID |
      | schema_ol_reis | reis         | 100            | 50                                     | 200                                         | r20                                        |
    When create compensation group from schema template:
      | C_Order_ID | C_CompensationGroup_Schema_ID | Qty | Calibrated | IdentifyLinesBy |
      | quote_x    | schema_1                      | 1   | Y          | Product         |
    Then validate C_OrderLine:
      | C_OrderLine_ID | M_Product_ID | OPT.QtyEntered | OPT.GroupCompensationCalibrationFactor | OPT.GroupCompensationQtyEnteredUncalibrated | OPT.C_CompensationGroup_CalibrationRule_ID |
      | schema_ol_reis | reis         | 140            | 70                                     | 200                                         | r10                                        |


  # ##########################################################################################
  # Equal SeqNo: the rule with the lower ID wins
  @from:cucumber
  @Id:S26881_TC4_Tie
  Scenario: Rules with equal SeqNo: the lower ID wins
    Given metasfresh contains C_CompensationGroup_Schema:
      | Identifier | Name     |
      | schema_1   | CalibTie |
    And metasfresh contains C_CompensationGroup_Schema_TemplateLine:
      | Identifier | C_CompensationGroup_Schema_ID | M_Product_ID | Qty | C_UOM_ID | SeqNo |
      | tl_reis    | schema_1                      | reis         | 200 | GRM      | 10    |
    And metasfresh contains C_CompensationGroup_CalibrationRule:
      | Identifier  | SeqNo | C_BPartner_ID | M_Product_ID | GroupCompensationCalibrationFactor |
      | rule_first  | 30    | cust_x        | reis         | 90                                 |
      | rule_second | 30    | cust_x        | reis         | 40                                 |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered |
      | order_x    | true    | cust_x        | 2026-10-07  |

    When create compensation group from schema template:
      | C_Order_ID | C_CompensationGroup_Schema_ID | Qty | Calibrated | IdentifyLinesBy |
      | order_x    | schema_1                      | 1   | Y          | Product         |
    Then validate C_OrderLine:
      | C_OrderLine_ID | M_Product_ID | OPT.QtyEntered | OPT.GroupCompensationCalibrationFactor | OPT.GroupCompensationQtyEnteredUncalibrated | OPT.C_CompensationGroup_CalibrationRule_ID |
      | schema_ol_reis | reis         | 180            | 90                                     | 200                                         | rule_first                                 |


  # ##########################################################################################
  # A rule with customer AND group matches only when both fit
  @from:cucumber
  @Id:S26881_TC4_PartnerAndGroup
  Scenario: A rule naming a customer and a group of another customer group does not match
    Given metasfresh contains C_CompensationGroup_Schema:
      | Identifier | Name          |
      | schema_1   | CalibMismatch |
    And metasfresh contains C_CompensationGroup_Schema_TemplateLine:
      | Identifier | C_CompensationGroup_Schema_ID | M_Product_ID | Qty | C_UOM_ID | SeqNo |
      | tl_reis    | schema_1                      | reis         | 200 | GRM      | 10    |
    And metasfresh contains C_CompensationGroup_CalibrationRule:
      | Identifier    | SeqNo | C_BPartner_ID | C_BP_Group_ID | GroupCompensationCalibrationFactor |
      | rule_mismatch | 10    | cust_x        | grp_b         | 50                                 |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered |
      | order_x    | true    | cust_x        | 2026-10-07  |

    When create compensation group from schema template:
      | C_Order_ID | C_CompensationGroup_Schema_ID | Qty | Calibrated | IdentifyLinesBy |
      | order_x    | schema_1                      | 1   | Y          | Product         |
    Then validate C_OrderLine:
      | C_OrderLine_ID | M_Product_ID | OPT.QtyEntered | OPT.GroupCompensationCalibrationFactor | OPT.GroupCompensationQtyEnteredUncalibrated | OPT.C_CompensationGroup_CalibrationRule_ID |
      | schema_ol_reis | reis         | 200            | 100                                    | 200                                         | null                                       |


  # ##########################################################################################
  # Rules of the order's organisation and of organisation * apply, rules of another organisation do not
  @from:cucumber
  @Id:S26881_TC4_Org
  Scenario: Only rules of the order's organisation or of organisation * apply
    Given load AD_Org:
      | Value | AD_Org_ID.Identifier |
      | 001   | org_a                |
      | 0     | org_any              |
    And metasfresh contains AD_Org:
      | Value        | Name         | AD_Org_ID.Identifier |
      | CALIB_ORG_B  | CalibOrgB    | org_b                |
    And metasfresh contains C_CompensationGroup_Schema:
      | Identifier | Name     |
      | schema_1   | CalibOrg |
    And metasfresh contains C_CompensationGroup_Schema_TemplateLine:
      | Identifier | C_CompensationGroup_Schema_ID | M_Product_ID | Qty | C_UOM_ID | SeqNo |
      | tl_reis    | schema_1                      | reis         | 200 | GRM      | 10    |
    And metasfresh contains C_CompensationGroup_CalibrationRule:
      | Identifier | SeqNo | AD_Org_ID | C_BPartner_ID | GroupCompensationCalibrationFactor |
      | rule_org_a | 10    | org_a     | cust_x        | 50                                 |
      | rule_any   | 20    | org_any   | cust_x        | 80                                 |
    And metasfresh contains M_Warehouse:
      | Identifier |
      | wh         |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | OPT.AD_Org_ID | OPT.M_Warehouse_ID |
      | order_a    | true    | cust_x        | 2026-10-07  | org_a         | wh                 |
      | order_b    | true    | cust_x        | 2026-10-07  | org_b         | wh                 |

    When create compensation group from schema template:
      | C_Order_ID | C_CompensationGroup_Schema_ID | Qty | Calibrated | IdentifyLinesBy |
      | order_a    | schema_1                      | 1   | Y          | Product         |
    Then validate C_OrderLine:
      | C_OrderLine_ID | M_Product_ID | OPT.QtyEntered | OPT.GroupCompensationCalibrationFactor | OPT.C_CompensationGroup_CalibrationRule_ID |
      | schema_ol_reis | reis         | 100            | 50                                     | rule_org_a                                 |
    When create compensation group from schema template:
      | C_Order_ID | C_CompensationGroup_Schema_ID | Qty | Calibrated | IdentifyLinesBy |
      | order_b    | schema_1                      | 1   | Y          | Product         |
    Then validate C_OrderLine:
      | C_OrderLine_ID | M_Product_ID | OPT.QtyEntered | OPT.GroupCompensationCalibrationFactor | OPT.C_CompensationGroup_CalibrationRule_ID |
      | schema_ol_reis | reis         | 160            | 80                                     | rule_any                                   |


  # ##########################################################################################
  # A general rule above a specific one hides it (intended)
  @from:cucumber
  @Id:S26881_TC5
  Scenario: A general rule with a lower SeqNo hides a more specific rule
    Given metasfresh contains C_CompensationGroup_Schema:
      | Identifier | Name         |
      | schema_1   | CalibGeneral |
    And metasfresh contains C_CompensationGroup_Schema_TemplateLine:
      | Identifier | C_CompensationGroup_Schema_ID | M_Product_ID | Qty | C_UOM_ID | SeqNo |
      | tl_reis    | schema_1                      | reis         | 200 | GRM      | 10    |
    And metasfresh contains C_CompensationGroup_CalibrationRule:
      | Identifier   | SeqNo | C_BP_Group_ID | GroupCompensationCalibrationFactor |
      | rule_general | 10    | grp_a         | 80                                 |
    And metasfresh contains C_CompensationGroup_CalibrationRule:
      | Identifier   | SeqNo | C_BP_Group_ID | M_Product_ID | GroupCompensationCalibrationFactor |
      | rule_special | 20    | grp_a         | reis         | 50                                 |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered |
      | order_x    | true    | cust_x        | 2026-10-07  |

    When create compensation group from schema template:
      | C_Order_ID | C_CompensationGroup_Schema_ID | Qty | Calibrated | IdentifyLinesBy |
      | order_x    | schema_1                      | 1   | Y          | Product         |
    Then validate C_OrderLine:
      | C_OrderLine_ID | M_Product_ID | OPT.QtyEntered | OPT.GroupCompensationCalibrationFactor | OPT.GroupCompensationQtyEnteredUncalibrated | OPT.C_CompensationGroup_CalibrationRule_ID |
      | schema_ol_reis | reis         | 160            | 80                                     | 200                                         | rule_general                               |


  # ##########################################################################################
  # Category rule matches the exact category only, not its child categories
  @from:cucumber
  @Id:S26881_TC6
  Scenario: A product category rule matches the exact category and not child categories
    Given metasfresh contains M_Product_Category:
      | Identifier | Name           | Value          |
      | cat_side   | CalibSide      | CALIB_SIDE     |
    And metasfresh contains M_Product_Category:
      | Identifier | Name           | Value          | OPT.M_Product_Category_Parent_ID |
      | cat_child  | CalibSideChild | CALIB_SIDECHLD | cat_side                         |
    And metasfresh contains M_Product_Category:
      | Identifier | Name           | Value          |
      | cat_other  | CalibOther     | CALIB_OTHER    |
    And metasfresh contains M_Products:
      | Identifier | X12DE355 | IsStocked | M_Product_Category_ID |
      | in_side    | GRM      | false     | cat_side              |
      | in_other   | GRM      | false     | cat_other             |
      | in_child   | GRM      | false     | cat_child             |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_TaxCategory_ID.InternalName | C_UOM_ID.X12DE355 |
      | pp_side    | plv_sales              | in_side      | 1        | Normal                        | GRM               |
      | pp_other   | plv_sales              | in_other     | 1        | Normal                        | GRM               |
      | pp_child   | plv_sales              | in_child     | 1        | Normal                        | GRM               |
    And metasfresh contains C_CompensationGroup_Schema:
      | Identifier | Name     |
      | schema_1   | CalibCat |
    And metasfresh contains C_CompensationGroup_Schema_TemplateLine:
      | Identifier | C_CompensationGroup_Schema_ID | M_Product_ID | Qty | C_UOM_ID | SeqNo |
      | tl_side    | schema_1                      | in_side      | 100 | GRM      | 10    |
      | tl_other   | schema_1                      | in_other     | 100 | GRM      | 20    |
      | tl_child   | schema_1                      | in_child     | 100 | GRM      | 30    |
    And metasfresh contains C_CompensationGroup_CalibrationRule:
      | Identifier | SeqNo | C_BP_Group_ID | M_Product_Category_ID | GroupCompensationCalibrationFactor |
      | rule_cat   | 10    | grp_a         | cat_side              | 50                                 |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered |
      | order_x    | true    | cust_x        | 2026-10-07  |

    When create compensation group from schema template:
      | C_Order_ID | C_CompensationGroup_Schema_ID | Qty | Calibrated | IdentifyLinesBy |
      | order_x    | schema_1                      | 1   | Y          | Product         |
    Then validate C_OrderLine:
      | C_OrderLine_ID    | M_Product_ID | OPT.QtyEntered | OPT.GroupCompensationCalibrationFactor | OPT.C_CompensationGroup_CalibrationRule_ID |
      | schema_ol_in_side | in_side      | 50             | 50                                     | rule_cat                                   |
      | schema_ol_in_other| in_other     | 100            | 100                                    | null                                       |
      | schema_ol_in_child| in_child     | 100            | 100                                    | null                                       |


  # ##########################################################################################
  # Schema rule: the same component calibrates in one schema and not in another
  @from:cucumber
  @Id:S26881_TC7
  Scenario: A schema rule calibrates the component of that schema only
    Given metasfresh contains C_CompensationGroup_Schema:
      | Identifier | Name       |
      | schema_1   | CalibSchm1 |
      | schema_2   | CalibSchm2 |
    And metasfresh contains C_CompensationGroup_Schema_TemplateLine:
      | Identifier | C_CompensationGroup_Schema_ID | M_Product_ID | Qty | C_UOM_ID | SeqNo |
      | tl_reis_1  | schema_1                      | reis         | 200 | GRM      | 10    |
      | tl_reis_2  | schema_2                      | reis         | 200 | GRM      | 10    |
    And metasfresh contains C_CompensationGroup_CalibrationRule:
      | Identifier  | SeqNo | C_BP_Group_ID | C_CompensationGroup_Schema_ID | GroupCompensationCalibrationFactor |
      | rule_schema | 10    | grp_a         | schema_1                      | 80                                 |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered |
      | order_x    | true    | cust_x        | 2026-10-07  |

    When create compensation group from schema template:
      | C_Order_ID | C_CompensationGroup_Schema_ID | Qty | Calibrated | IdentifyLinesBy |
      | order_x    | schema_1                      | 1   | Y          | Product         |
    Then validate C_OrderLine:
      | C_OrderLine_ID | M_Product_ID | OPT.QtyEntered | OPT.GroupCompensationCalibrationFactor | OPT.C_CompensationGroup_CalibrationRule_ID |
      | schema_ol_reis | reis         | 160            | 80                                     | rule_schema                                |
    When create compensation group from schema template:
      | C_Order_ID | C_CompensationGroup_Schema_ID | Qty | Calibrated | IdentifyLinesBy |
      | order_x    | schema_2                      | 1   | Y          | Product         |
    Then validate C_OrderLine:
      | C_OrderLine_ID | M_Product_ID | OPT.QtyEntered | OPT.GroupCompensationCalibrationFactor | OPT.C_CompensationGroup_CalibrationRule_ID |
      | schema_ol_reis | reis         | 200            | 100                                    | null                                       |


  # ##########################################################################################
  # No matching rule: quantities exactly as without calibration
  @from:cucumber
  @Id:S26881_TC8
  Scenario: Without a matching rule quantities stay as they are and the factor is 100
    Given metasfresh contains C_CompensationGroup_Schema:
      | Identifier | Name       |
      | schema_1   | CalibNoHit |
    And metasfresh contains C_CompensationGroup_Schema_TemplateLine:
      | Identifier | C_CompensationGroup_Schema_ID | M_Product_ID | Qty   | C_UOM_ID | SeqNo |
      | tl_milch   | schema_1                      | milch        | 0.125 | LTR      | 10    |
      | tl_kraft   | schema_1                      | kraft        | 0.004 | LTR      | 20    |
    And metasfresh contains C_CompensationGroup_CalibrationRule:
      | Identifier  | SeqNo | C_BPartner_ID | GroupCompensationCalibrationFactor |
      | rule_other  | 10    | cust_y        | 50                                 |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered |
      | order_x    | true    | cust_x        | 2026-10-07  |

    When create compensation group from schema template:
      | C_Order_ID | C_CompensationGroup_Schema_ID | Qty | Calibrated | IdentifyLinesBy |
      | order_x    | schema_1                      | 1   | Y          | Product         |
    Then validate C_OrderLine:
      | C_OrderLine_ID  | M_Product_ID | OPT.QtyEntered | OPT.GroupCompensationCalibrationFactor | OPT.GroupCompensationQtyEnteredUncalibrated | OPT.C_CompensationGroup_CalibrationRule_ID |
      | schema_ol_milch | milch        | 0.13           | 100                                    | 0.13                                        | null                                       |
      | schema_ol_kraft | kraft        | 0              | 100                                    | 0                                           | null                                       |


  # ##########################################################################################
  # Calibrated quantities round up per UOM precision; a positive factor never turns a non-zero base into Qty 0
  @from:cucumber
  @Id:S26881_TC10
  Scenario: Calibrated quantities round up to the UOM precision and a positive factor never turns a non-zero base into 0
    Given metasfresh contains C_CompensationGroup_Schema:
      | Identifier | Name       |
      | schema_1   | CalibRound |
    And metasfresh contains C_CompensationGroup_Schema_TemplateLine:
      | Identifier | C_CompensationGroup_Schema_ID | M_Product_ID | Qty  | C_UOM_ID | SeqNo |
      | tl_pce_a   | schema_1                      | pce_a        | 3    | PCE      | 10    |
      | tl_pce_b   | schema_1                      | pce_b        | 4    | PCE      | 20    |
      | tl_fisch   | schema_1                      | fisch        | 1    | PCE      | 30    |
      | tl_kraft   | schema_1                      | kraft        | 0.15 | LTR      | 40    |
      | tl_gram    | schema_1                      | gram         | 0.01 | GRM      | 50    |
    And metasfresh contains C_CompensationGroup_CalibrationRule:
      | Identifier | SeqNo | C_BPartner_ID | M_Product_ID | GroupCompensationCalibrationFactor |
      | r_pce_a    | 10    | cust_x        | pce_a        | 50                                 |
      | r_pce_b    | 20    | cust_x        | pce_b        | 30                                 |
      | r_fisch    | 30    | cust_x        | fisch        | 40                                 |
      | r_kraft    | 40    | cust_x        | kraft        | 66.7                               |
      | r_gram     | 50    | cust_x        | gram         | 40                                 |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered |
      | order_1    | true    | cust_x        | 2026-10-07  |
      | order_10   | true    | cust_x        | 2026-10-07  |

    When create compensation group from schema template:
      | C_Order_ID | C_CompensationGroup_Schema_ID | Qty | Calibrated | IdentifyLinesBy |
      | order_1    | schema_1                      | 1   | Y          | Product         |
    Then validate C_OrderLine:
      | C_OrderLine_ID  | M_Product_ID | OPT.QtyEntered | OPT.GroupCompensationCalibrationFactor | OPT.GroupCompensationQtyEnteredUncalibrated |
      | schema_ol_pce_a | pce_a        | 2              | 50                                     | 3                                           |
      | schema_ol_pce_b | pce_b        | 2              | 30                                     | 4                                           |
      | schema_ol_fisch | fisch        | 1              | 40                                     | 1                                           |
      | schema_ol_kraft | kraft        | 0.11           | 66.7                                   | 0.15                                        |
      | schema_ol_gram  | gram         | 0.01           | 40                                     | 0.01                                        |
    When create compensation group from schema template:
      | C_Order_ID | C_CompensationGroup_Schema_ID | Qty | Calibrated | IdentifyLinesBy |
      | order_10   | schema_1                      | 10  | Y          | Product         |
    Then validate C_OrderLine:
      | C_OrderLine_ID  | M_Product_ID | OPT.QtyEntered | OPT.GroupCompensationCalibrationFactor | OPT.GroupCompensationQtyEnteredUncalibrated |
      | schema_ol_fisch | fisch        | 4              | 40                                     | 10                                          |


  # ##########################################################################################
  # A template quantity that already rounds to 0 stays 0 although the factor is positive
  @from:cucumber
  @Id:S26881_TC10_TemplateQtyRoundsToZero
  Scenario: A template quantity that rounds to 0 stays 0 with a positive factor
    Given metasfresh contains C_CompensationGroup_Schema:
      | Identifier | Name       |
      | schema_1   | CalibZeroQ |
    And metasfresh contains C_CompensationGroup_Schema_TemplateLine:
      | Identifier | C_CompensationGroup_Schema_ID | M_Product_ID | Qty   | C_UOM_ID | SeqNo |
      | tl_kraft   | schema_1                      | kraft        | 0.004 | LTR      | 10    |
    And metasfresh contains C_CompensationGroup_CalibrationRule:
      | Identifier | SeqNo | C_BPartner_ID | GroupCompensationCalibrationFactor |
      | rule_half  | 10    | cust_x        | 50                                 |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered |
      | order_x    | true    | cust_x        | 2026-10-07  |

    When create compensation group from schema template:
      | C_Order_ID | C_CompensationGroup_Schema_ID | Qty | Calibrated | IdentifyLinesBy |
      | order_x    | schema_1                      | 1   | Y          | Product         |
    Then validate C_OrderLine:
      | C_OrderLine_ID  | M_Product_ID | OPT.QtyEntered | OPT.GroupCompensationCalibrationFactor | OPT.GroupCompensationQtyEnteredUncalibrated | OPT.C_CompensationGroup_CalibrationRule_ID |
      | schema_ol_kraft | kraft        | 0              | 50                                     | 0                                           | rule_half                                  |


  # ##########################################################################################
  # Fractional menu quantity
  @from:cucumber
  @Id:S26881_FractionalMenuQty
  Scenario: A fractional menu quantity is multiplied before rounding
    # the base (template Qty x menu Qty) is rounded half-up to the UOM precision before the factor applies: 1 PCE x 2.5 = 2.5 -> 3 (half-up); 3 x 50 % = 1.5 -> 2 (up)
    Given metasfresh contains C_CompensationGroup_Schema:
      | Identifier | Name      |
      | schema_1   | CalibFrac |
    And metasfresh contains C_CompensationGroup_Schema_TemplateLine:
      | Identifier | C_CompensationGroup_Schema_ID | M_Product_ID | Qty | C_UOM_ID | SeqNo |
      | tl_reis    | schema_1                      | reis         | 200 | GRM      | 10    |
      | tl_pce_a   | schema_1                      | pce_a        | 1   | PCE      | 20    |
    And metasfresh contains C_CompensationGroup_CalibrationRule:
      | Identifier | SeqNo | C_BPartner_ID | GroupCompensationCalibrationFactor |
      | rule_half  | 10    | cust_x        | 50                                 |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered |
      | order_x    | true    | cust_x        | 2026-10-07  |

    When create compensation group from schema template:
      | C_Order_ID | C_CompensationGroup_Schema_ID | Qty | Calibrated | IdentifyLinesBy |
      | order_x    | schema_1                      | 2.5 | Y          | Product         |
    Then validate C_OrderLine:
      | C_OrderLine_ID  | M_Product_ID | OPT.QtyEntered | OPT.GroupCompensationCalibrationFactor | OPT.GroupCompensationQtyEnteredUncalibrated | OPT.C_CompensationGroup_CalibrationRule_ID |
      | schema_ol_reis  | reis         | 250            | 50                                     | 500                                         | rule_half                                  |
      | schema_ol_pce_a | pce_a        | 2              | 50                                     | 3                                           | rule_half                                  |


  # ##########################################################################################
  # The same menu entered twice in one order
  @from:cucumber
  @Id:S26881_SameMenuTwice
  Scenario: The same menu entered twice in one order is calibrated both times
    Given metasfresh contains C_CompensationGroup_Schema:
      | Identifier | Name       |
      | schema_1   | CalibTwice |
    And metasfresh contains C_CompensationGroup_Schema_TemplateLine:
      | Identifier | C_CompensationGroup_Schema_ID | M_Product_ID | Qty | C_UOM_ID | SeqNo |
      | tl_reis    | schema_1                      | reis         | 200 | GRM      | 10    |
    And metasfresh contains C_CompensationGroup_CalibrationRule:
      | Identifier | SeqNo | C_BPartner_ID | GroupCompensationCalibrationFactor |
      | rule_half  | 10    | cust_x        | 50                                 |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered |
      | order_x    | true    | cust_x        | 2026-10-07  |

    When create compensation group from schema template:
      | C_Order_ID | C_CompensationGroup_Schema_ID | Qty | Calibrated | IdentifyLinesBy |
      | order_x    | schema_1                      | 1   | Y          | Product         |
    Then validate C_OrderLine:
      | C_OrderLine_ID | M_Product_ID | OPT.QtyEntered | OPT.GroupCompensationCalibrationFactor | OPT.C_CompensationGroup_CalibrationRule_ID |
      | schema_ol_reis | reis         | 100            | 50                                     | rule_half                                  |
    When create compensation group from schema template:
      | C_Order_ID | C_CompensationGroup_Schema_ID | Qty | Calibrated | IdentifyLinesBy |
      | order_x    | schema_1                      | 3   | Y          | Product         |
    Then validate C_OrderLine:
      | C_OrderLine_ID | M_Product_ID | OPT.QtyEntered | OPT.GroupCompensationCalibrationFactor | OPT.C_CompensationGroup_CalibrationRule_ID |
      | schema_ol_reis | reis         | 300            | 50                                     | rule_half                                  |


  # ##########################################################################################
  # A rule created after a first order applies to the next order only
  @from:cucumber
  @Id:S26881_RuleCreatedAfterFirstOrder
  Scenario: A rule created after a first order applies to the next order and not to the first
    Given metasfresh contains C_CompensationGroup_Schema:
      | Identifier | Name        |
      | schema_1   | CalibLater  |
    And metasfresh contains C_CompensationGroup_Schema_TemplateLine:
      | Identifier | C_CompensationGroup_Schema_ID | M_Product_ID | Qty | C_UOM_ID | SeqNo |
      | tl_reis    | schema_1                      | reis         | 200 | GRM      | 10    |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered |
      | order_1    | true    | cust_x        | 2026-10-07  |
      | order_2    | true    | cust_x        | 2026-10-07  |
    When create compensation group from schema template:
      | C_Order_ID | C_CompensationGroup_Schema_ID | Qty | Calibrated | IdentifyLinesBy |
      | order_1    | schema_1                      | 1   | Y          | Product         |
    Then validate C_OrderLine:
      | C_OrderLine_ID | M_Product_ID | OPT.QtyEntered | OPT.GroupCompensationCalibrationFactor | OPT.C_CompensationGroup_CalibrationRule_ID |
      | schema_ol_reis | reis         | 200            | 100                                    | null                                       |

    When metasfresh contains C_CompensationGroup_CalibrationRule:
      | Identifier | SeqNo | C_BPartner_ID | GroupCompensationCalibrationFactor |
      | rule_later | 10    | cust_x        | 50                                 |
    And create compensation group from schema template:
      | C_Order_ID | C_CompensationGroup_Schema_ID | Qty | Calibrated | IdentifyLinesBy |
      | order_2    | schema_1                      | 1   | Y          | Product         |
    Then validate C_OrderLine:
      | C_OrderLine_ID | M_Product_ID | OPT.QtyEntered | OPT.GroupCompensationCalibrationFactor | OPT.C_CompensationGroup_CalibrationRule_ID |
      | schema_ol_reis | reis         | 100            | 50                                     | rule_later                                 |


  # ##########################################################################################
  # A deactivated rule is not matched
  @from:cucumber
  @Id:S26881_TC15_DeactivatedRule
  Scenario: A deactivated rule is not matched
    Given metasfresh contains C_CompensationGroup_Schema:
      | Identifier | Name        |
      | schema_1   | CalibDeact  |
    And metasfresh contains C_CompensationGroup_Schema_TemplateLine:
      | Identifier | C_CompensationGroup_Schema_ID | M_Product_ID | Qty | C_UOM_ID | SeqNo |
      | tl_reis    | schema_1                      | reis         | 200 | GRM      | 10    |
    And metasfresh contains C_CompensationGroup_CalibrationRule:
      | Identifier | SeqNo | C_BPartner_ID | GroupCompensationCalibrationFactor |
      | rule_off   | 10    | cust_x        | 70                                 |
    And deactivate C_CompensationGroup_CalibrationRule:
      | Identifier |
      | rule_off   |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered |
      | order_x    | true    | cust_x        | 2026-10-07  |

    When create compensation group from schema template:
      | C_Order_ID | C_CompensationGroup_Schema_ID | Qty | Calibrated | IdentifyLinesBy |
      | order_x    | schema_1                      | 1   | Y          | Product         |
    Then validate C_OrderLine:
      | C_OrderLine_ID | M_Product_ID | OPT.QtyEntered | OPT.GroupCompensationCalibrationFactor | OPT.GroupCompensationQtyEnteredUncalibrated | OPT.C_CompensationGroup_CalibrationRule_ID |
      | schema_ol_reis | reis         | 200            | 100                                    | 200                                         | null                                       |


  # ##########################################################################################
  # A rule deactivated after use stays on the line that already refers to it; new orders do not match it
  @from:cucumber
  @Id:S26881_TC15_DeactivatedRuleStaysOnLine
  Scenario: A rule deactivated after use stays on its existing line and is not matched by a new order
    Given metasfresh contains C_CompensationGroup_Schema:
      | Identifier | Name           |
      | schema_1   | CalibDeactUsed |
    And metasfresh contains C_CompensationGroup_Schema_TemplateLine:
      | Identifier | C_CompensationGroup_Schema_ID | M_Product_ID | Qty | C_UOM_ID | SeqNo |
      | tl_reis    | schema_1                      | reis         | 200 | GRM      | 10    |
    And metasfresh contains C_CompensationGroup_CalibrationRule:
      | Identifier | SeqNo | C_BPartner_ID | GroupCompensationCalibrationFactor |
      | rule_10    | 10    | cust_x        | 70                                 |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered |
      | order_1    | true    | cust_x        | 2026-10-07  |
      | order_2    | true    | cust_x        | 2026-10-07  |
    And create compensation group from schema template:
      | C_Order_ID | C_CompensationGroup_Schema_ID | Qty | Calibrated | IdentifyLinesBy |
      | order_1    | schema_1                      | 1   | Y          | Product         |
    And validate C_OrderLine:
      | C_OrderLine_ID | M_Product_ID | OPT.QtyEntered | OPT.GroupCompensationCalibrationFactor | OPT.GroupCompensationQtyEnteredUncalibrated | OPT.C_CompensationGroup_CalibrationRule_ID |
      | schema_ol_reis | reis         | 140            | 70                                     | 200                                         | rule_10                                    |

    When deactivate C_CompensationGroup_CalibrationRule:
      | Identifier |
      | rule_10    |

    # the existing line keeps the rule and the factor
    Then validate C_OrderLine:
      | C_OrderLine_ID | M_Product_ID | OPT.QtyEntered | OPT.GroupCompensationCalibrationFactor | OPT.GroupCompensationQtyEnteredUncalibrated | OPT.C_CompensationGroup_CalibrationRule_ID |
      | schema_ol_reis | reis         | 140            | 70                                     | 200                                         | rule_10                                    |

    # a new order of the same customer does not match the deactivated rule
    When create compensation group from schema template:
      | C_Order_ID | C_CompensationGroup_Schema_ID | Qty | Calibrated | IdentifyLinesBy |
      | order_2    | schema_1                      | 1   | Y          | Product         |
    Then validate C_OrderLine:
      | C_OrderLine_ID | M_Product_ID | OPT.QtyEntered | OPT.GroupCompensationCalibrationFactor | OPT.GroupCompensationQtyEnteredUncalibrated | OPT.C_CompensationGroup_CalibrationRule_ID |
      | schema_ol_reis | reis         | 200            | 100                                    | 200                                         | null                                       |


  # ##########################################################################################
  # The percentage discount follows the calibrated quantities and a Qty edit of a calibrated line
  @from:cucumber
  @Id:S26881_TC11
  Scenario: The percentage discount follows the calibration and a Qty edit, the stored calibration stays
    Given metasfresh contains C_CompensationGroup_Schema:
      | Identifier | Name         |
      | schema_1   | CalibPercent |
    And metasfresh contains C_CompensationGroup_Schema_TemplateLine:
      | Identifier | C_CompensationGroup_Schema_ID | M_Product_ID | Qty | C_UOM_ID | SeqNo |
      | tl_fisch   | schema_1                      | fisch        | 1   | PCE      | 10    |
      | tl_reis    | schema_1                      | reis         | 200 | GRM      | 20    |
    And metasfresh contains C_CompensationGroup_SchemaLine:
      | Identifier | C_CompensationGroup_Schema_ID | M_Product_ID | OPT.CompleteOrderDiscount | OPT.SeqNo |
      | sl_disc    | schema_1                      | discount     | 10                        | 30        |
    And metasfresh contains C_CompensationGroup_CalibrationRule:
      | Identifier | SeqNo | C_BPartner_ID | M_Product_ID | GroupCompensationCalibrationFactor |
      | rule_10    | 10    | cust_x        | reis         | 50                                 |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered |
      | order_x    | true    | cust_x        | 2026-10-07  |

    When create compensation group from schema template:
      | C_Order_ID | C_CompensationGroup_Schema_ID | Qty | Calibrated | IdentifyLinesBy |
      | order_x    | schema_1                      | 10  | Y          | Product         |
    Then validate C_OrderLine:
      | C_OrderLine_ID | M_Product_ID | OPT.QtyEntered | OPT.GroupCompensationCalibrationFactor | OPT.GroupCompensationQtyEnteredUncalibrated | OPT.C_CompensationGroup_CalibrationRule_ID |
      | schema_ol_reis | reis         | 1000           | 50                                     | 2000                                        | rule_10                                    |
      | schema_ol_fisch | fisch       | 10             | 100                                    | 10                                          | null                                       |
    # 10 % of the net of the regular lines: 10 PCE x 5 EUR + 1000 GRM x 1 EUR = 1050 EUR
    And validate C_OrderLine:
      | C_OrderLine_ID       | M_Product_ID | OPT.IsGroupCompensationLine | OPT.price |
      | schema_comp_discount | discount     | true                        | -105      |

    # the clerk adjusts the quantity of the calibrated line
    When update C_OrderLine:
      | C_OrderLine_ID.Identifier | OPT.QtyEntered |
      | schema_ol_reis            | 1200           |
    # read the lines fresh: the discount line is rewritten by the group, not by the edit
    And load C_OrderLines from C_Order:
      | C_Order_ID | C_OrderLine_ID       | M_Product_ID |
      | order_x    | schema_ol_reis       | reis         |
      | order_x    | schema_ol_fisch      | fisch        |
      | order_x    | schema_comp_discount | discount     |
    # 10 % of 10 PCE x 5 EUR + 1200 GRM x 1 EUR = 1250 EUR; factor, rule and uncalibrated quantity stay
    Then validate C_OrderLine:
      | C_OrderLine_ID  | M_Product_ID | OPT.QtyEntered | OPT.GroupCompensationCalibrationFactor | OPT.GroupCompensationQtyEnteredUncalibrated | OPT.C_CompensationGroup_CalibrationRule_ID |
      | schema_ol_reis  | reis         | 1200           | 50                                     | 2000                                        | rule_10                                    |
      | schema_ol_fisch | fisch        | 10             | 100                                    | 10                                          | null                                       |
    And validate C_OrderLine:
      | C_OrderLine_ID       | M_Product_ID | OPT.IsGroupCompensationLine | OPT.price |
      | schema_comp_discount | discount     | true                        | -125      |


  # ##########################################################################################
  # Factor 0 keeps the component line with Qty 0; the percentage discount follows the net, also after the clerk types a Qty
  @from:cucumber
  @Id:S26881_TC14_FactorZero
  Scenario: A component with factor 0 gets a line with Qty 0, the discount follows the net and the order can be completed
    Given metasfresh contains C_CompensationGroup_Schema:
      | Identifier | Name       |
      | schema_1   | CalibZero  |
    And metasfresh contains C_CompensationGroup_Schema_TemplateLine:
      | Identifier | C_CompensationGroup_Schema_ID | M_Product_ID | Qty | C_UOM_ID | SeqNo |
      | tl_fisch   | schema_1                      | fisch        | 1   | PCE      | 10    |
      | tl_reis    | schema_1                      | reis         | 200 | GRM      | 20    |
    And metasfresh contains C_CompensationGroup_SchemaLine:
      | Identifier | C_CompensationGroup_Schema_ID | M_Product_ID | OPT.CompleteOrderDiscount | OPT.SeqNo |
      | sl_disc    | schema_1                      | discount     | 10                        | 30        |
    And metasfresh contains C_CompensationGroup_CalibrationRule:
      | Identifier | SeqNo | C_BPartner_ID | M_Product_ID | GroupCompensationCalibrationFactor |
      | rule_zero  | 10    | cust_x        | fisch        | 0                                  |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered |
      | order_x    | true    | cust_x        | 2026-10-07  |

    When create compensation group from schema template:
      | C_Order_ID | C_CompensationGroup_Schema_ID | Qty | Calibrated | IdentifyLinesBy |
      | order_x    | schema_1                      | 1   | Y          | Product         |
    Then validate C_OrderLine:
      | C_OrderLine_ID  | M_Product_ID | OPT.QtyEntered | OPT.GroupCompensationCalibrationFactor | OPT.GroupCompensationQtyEnteredUncalibrated | OPT.C_CompensationGroup_CalibrationRule_ID |
      | schema_ol_fisch | fisch        | 0              | 0                                      | 1                                           | rule_zero                                  |
      | schema_ol_reis  | reis         | 200            | 100                                    | 200                                         | null                                       |
    # 10 % of the net of the regular lines: 0 PCE x 5 EUR + 200 GRM x 1 EUR = 200 EUR
    And validate C_OrderLine:
      | C_OrderLine_ID       | M_Product_ID | OPT.IsGroupCompensationLine | OPT.price |
      | schema_comp_discount | discount     | true                        | -20       |

    # the clerk types a quantity on the line with Qty 0
    When update C_OrderLine:
      | C_OrderLine_ID.Identifier | OPT.QtyEntered |
      | schema_ol_fisch           | 1              |
    # read the lines fresh: the discount line is rewritten by the group, not by the edit
    And load C_OrderLines from C_Order:
      | C_Order_ID | C_OrderLine_ID       | M_Product_ID |
      | order_x    | schema_ol_fisch      | fisch        |
      | order_x    | schema_ol_reis       | reis         |
      | order_x    | schema_comp_discount | discount     |
    # 10 % of 1 PCE x 5 EUR + 200 GRM x 1 EUR = 205 EUR; factor, rule and uncalibrated quantity stay
    Then validate C_OrderLine:
      | C_OrderLine_ID  | M_Product_ID | OPT.QtyEntered | OPT.GroupCompensationCalibrationFactor | OPT.GroupCompensationQtyEnteredUncalibrated | OPT.C_CompensationGroup_CalibrationRule_ID |
      | schema_ol_fisch | fisch        | 1              | 0                                      | 1                                           | rule_zero                                  |
    And validate C_OrderLine:
      | C_OrderLine_ID       | M_Product_ID | OPT.IsGroupCompensationLine | OPT.price |
      | schema_comp_discount | discount     | true                        | -20.5     |

    # a line with Qty 0 does not block completing the order
    When update C_OrderLine:
      | C_OrderLine_ID.Identifier | OPT.QtyEntered |
      | schema_ol_fisch           | 0              |
    And the order identified by order_x is completed
    Then validate C_OrderLine:
      | C_OrderLine_ID  | M_Product_ID | OPT.QtyEntered | OPT.GroupCompensationCalibrationFactor | OPT.C_CompensationGroup_CalibrationRule_ID |
      | schema_ol_fisch | fisch        | 0              | 0                                      | rule_zero                                  |


  # ##########################################################################################
  # Every component at factor 0 next to a menu line: all lines are created, the components with Qty 0
  @from:cucumber
  @Id:S26881_TC14_AllZeroWithMenuLine
  Scenario: All components at factor 0 keep their lines with Qty 0 next to the menu line
    Given metasfresh contains C_CompensationGroup_Schema:
      | Identifier | Name         |
      | schema_1   | CalibAllMenu |
    And metasfresh contains M_Products:
      | Identifier | X12DE355 | IsStocked | C_CompensationGroup_Schema_ID |
      | menu_1     | PCE      | false     | schema_1                      |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_TaxCategory_ID.InternalName | C_UOM_ID.X12DE355 |
      | pp_menu_1  | plv_sales              | menu_1       | 10       | Normal                        | PCE               |
    And metasfresh contains C_CompensationGroup_Schema_TemplateLine:
      | Identifier | C_CompensationGroup_Schema_ID | M_Product_ID | Qty | C_UOM_ID | SeqNo |
      | tl_menu    | schema_1                      | menu_1       | 1   | PCE      | 10    |
      | tl_fisch   | schema_1                      | fisch        | 1   | PCE      | 20    |
      | tl_reis    | schema_1                      | reis         | 200 | GRM      | 30    |
    And metasfresh contains C_CompensationGroup_CalibrationRule:
      | Identifier | SeqNo | C_BPartner_ID | M_Product_ID | GroupCompensationCalibrationFactor |
      | rule_fisch | 10    | cust_x        | fisch        | 0                                  |
      | rule_reis  | 20    | cust_x        | reis         | 0                                  |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered |
      | order_x    | true    | cust_x        | 2026-10-07  |

    When create compensation group from schema template:
      | C_Order_ID | C_CompensationGroup_Schema_ID | Qty | Calibrated | IdentifyLinesBy |
      | order_x    | schema_1                      | 3   | Y          | Product         |
    Then validate C_OrderLine:
      | C_OrderLine_ID   | M_Product_ID | OPT.QtyEntered | OPT.GroupCompensationCalibrationFactor | OPT.GroupCompensationQtyEnteredUncalibrated | OPT.C_CompensationGroup_CalibrationRule_ID |
      | schema_ol_menu_1 | menu_1       | 3              | null                                   | null                                        | null                                       |
      | schema_ol_fisch  | fisch        | 0              | 0                                      | 3                                           | rule_fisch                                 |
      | schema_ol_reis   | reis         | 0              | 0                                      | 600                                         | rule_reis                                  |


  # ##########################################################################################
  # Every component at factor 0 and no menu line: all component lines are created with Qty 0, no error
  @from:cucumber
  @Id:S26881_TC14_AllZeroWithoutMenuLine
  Scenario: All components at factor 0 without a menu line are created with Qty 0
    Given metasfresh contains C_CompensationGroup_Schema:
      | Identifier | Name          |
      | schema_1   | CalibAllNoMnu |
    And metasfresh contains C_CompensationGroup_Schema_TemplateLine:
      | Identifier | C_CompensationGroup_Schema_ID | M_Product_ID | Qty | C_UOM_ID | SeqNo |
      | tl_fisch   | schema_1                      | fisch        | 1   | PCE      | 10    |
      | tl_reis    | schema_1                      | reis         | 200 | GRM      | 20    |
    And metasfresh contains C_CompensationGroup_CalibrationRule:
      | Identifier | SeqNo | C_BPartner_ID | GroupCompensationCalibrationFactor |
      | rule_zero  | 10    | cust_x        | 0                                  |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered |
      | order_x    | true    | cust_x        | 2026-10-07  |

    When create compensation group from schema template:
      | C_Order_ID | C_CompensationGroup_Schema_ID | Qty | Calibrated | IdentifyLinesBy |
      | order_x    | schema_1                      | 1   | Y          | Product         |
    Then validate C_OrderLine:
      | C_OrderLine_ID  | M_Product_ID | OPT.QtyEntered | OPT.GroupCompensationCalibrationFactor | OPT.GroupCompensationQtyEnteredUncalibrated | OPT.C_CompensationGroup_CalibrationRule_ID |
      | schema_ol_fisch | fisch        | 0              | 0                                      | 1                                           | rule_zero                                  |
      | schema_ol_reis  | reis         | 0              | 0                                      | 200                                         | rule_zero                                  |


  # ##########################################################################################
  # The menu line carries the schema of the group: never calibrated, even if a rule matches it
  @from:cucumber
  @Id:S26881_MenuLineOwnSchema
  Scenario: The menu line of the own schema is never calibrated
    Given metasfresh contains C_CompensationGroup_Schema:
      | Identifier | Name         |
      | schema_1   | CalibMenuOwn |
    And metasfresh contains M_Products:
      | Identifier | X12DE355 | IsStocked | C_CompensationGroup_Schema_ID |
      | menu_1     | PCE      | false     | schema_1                      |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_TaxCategory_ID.InternalName | C_UOM_ID.X12DE355 |
      | pp_menu_1  | plv_sales              | menu_1       | 10       | Normal                        | PCE               |
    And metasfresh contains C_CompensationGroup_Schema_TemplateLine:
      | Identifier | C_CompensationGroup_Schema_ID | M_Product_ID | Qty | C_UOM_ID | SeqNo |
      | tl_menu    | schema_1                      | menu_1       | 1   | PCE      | 10    |
      | tl_milch   | schema_1                      | milch        | 0.25| LTR      | 20    |
    And metasfresh contains C_CompensationGroup_CalibrationRule:
      | Identifier | SeqNo | C_BPartner_ID | GroupCompensationCalibrationFactor |
      | rule_cust  | 10    | cust_x        | 80                                 |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered |
      | order_x    | true    | cust_x        | 2026-10-07  |

    When create compensation group from schema template:
      | C_Order_ID | C_CompensationGroup_Schema_ID | Qty  | Calibrated | IdentifyLinesBy |
      | order_x    | schema_1                      | 1500 | Y          | Product         |
    Then validate C_OrderLine:
      | C_OrderLine_ID   | M_Product_ID | OPT.QtyEntered | OPT.GroupCompensationCalibrationFactor | OPT.GroupCompensationQtyEnteredUncalibrated | OPT.C_CompensationGroup_CalibrationRule_ID |
      | schema_ol_menu_1 | menu_1       | 1500           | null                                   | null                                        | null                                       |


  # ##########################################################################################
  # The menu line carries another schema, matched by a customer + product rule: still not calibrated
  @from:cucumber
  @Id:S26881_MenuLineOtherSchema
  Scenario: A menu line carrying another schema is not calibrated even if a customer and product rule matches it
    Given metasfresh contains C_CompensationGroup_Schema:
      | Identifier | Name         |
      | schema_1   | CalibMenuOne |
      | schema_2   | CalibMenuTwo |
    And metasfresh contains M_Products:
      | Identifier | X12DE355 | IsStocked | C_CompensationGroup_Schema_ID |
      | menu_2     | PCE      | false     | schema_2                      |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_TaxCategory_ID.InternalName | C_UOM_ID.X12DE355 |
      | pp_menu_2  | plv_sales              | menu_2       | 10       | Normal                        | PCE               |
    And metasfresh contains C_CompensationGroup_Schema_TemplateLine:
      | Identifier | C_CompensationGroup_Schema_ID | M_Product_ID | Qty | C_UOM_ID | SeqNo |
      | tl_menu_2  | schema_1                      | menu_2       | 2   | PCE      | 10    |
    And metasfresh contains C_CompensationGroup_CalibrationRule:
      | Identifier | SeqNo | C_BPartner_ID | M_Product_ID | GroupCompensationCalibrationFactor |
      | rule_menu  | 10    | cust_x        | menu_2       | 50                                 |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered |
      | order_x    | true    | cust_x        | 2026-10-07  |

    When create compensation group from schema template:
      | C_Order_ID | C_CompensationGroup_Schema_ID | Qty | Calibrated | IdentifyLinesBy |
      | order_x    | schema_1                      | 3   | Y          | Product         |
    Then validate C_OrderLine:
      | C_OrderLine_ID   | M_Product_ID | OPT.QtyEntered | OPT.GroupCompensationCalibrationFactor | OPT.GroupCompensationQtyEnteredUncalibrated | OPT.C_CompensationGroup_CalibrationRule_ID |
      | schema_ol_menu_2 | menu_2       | 6              | null                                   | null                                        | null                                       |


  # ##########################################################################################
  # Purchase orders are never calibrated, even for a partner that is customer and vendor
  @from:cucumber
  @Id:S26881_PurchaseOrderUncalibrated
  Scenario: A purchase order is not calibrated although a customer rule matches the partner
    Given metasfresh contains M_PricingSystems
      | Identifier |
      | ps_po      |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name       | SOTrx | IsTaxIncluded | PricePrecision |
      | pl_po      | ps_po              | DE                        | EUR                 | pl_calib_p | false | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID | Name        | ValidFrom  |
      | plv_po     | pl_po          | plv_calib_p | 2026-01-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_TaxCategory_ID.InternalName | C_UOM_ID.X12DE355 |
      | pp_po_reis | plv_po                 | reis         | 1        | Normal                        | GRM               |
    And metasfresh contains C_BPartners:
      | Identifier | IsCustomer | IsVendor | M_PricingSystem_ID | PO_PricingSystem_ID |
      | both       | Y          | Y        | ps                 | ps_po               |
    And metasfresh contains C_CompensationGroup_Schema:
      | Identifier | Name      |
      | schema_1   | CalibPO   |
    And metasfresh contains C_CompensationGroup_Schema_TemplateLine:
      | Identifier | C_CompensationGroup_Schema_ID | M_Product_ID | Qty | C_UOM_ID | SeqNo |
      | tl_reis    | schema_1                      | reis         | 200 | GRM      | 10    |
    And metasfresh contains C_CompensationGroup_CalibrationRule:
      | Identifier | SeqNo | C_BPartner_ID | GroupCompensationCalibrationFactor |
      | rule_cust  | 10    | both          | 50                                 |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_PricingSystem_ID |
      | po_1       | false   | both          | 2026-10-07  | ps_po              |

    When create compensation group from schema template:
      | C_Order_ID | C_CompensationGroup_Schema_ID | Qty | Calibrated | IdentifyLinesBy |
      | po_1       | schema_1                      | 1   | Y          | Product         |
    Then validate C_OrderLine:
      | C_OrderLine_ID | M_Product_ID | OPT.QtyEntered | OPT.GroupCompensationCalibrationFactor | OPT.GroupCompensationQtyEnteredUncalibrated | OPT.C_CompensationGroup_CalibrationRule_ID |
      | schema_ol_reis | reis         | 200            | null                                   | null                                        | null                                       |


  # ##########################################################################################
  # Existing order lines keep their calibration when the rule or the order's customer changes
  @from:cucumber
  @Id:S26881_TC13_ExistingLinesUnchanged
  Scenario: A changed rule factor and a changed customer leave the lines of a draft order as they are
    Given metasfresh contains C_CompensationGroup_Schema:
      | Identifier | Name          |
      | schema_1   | CalibExisting |
    And metasfresh contains C_CompensationGroup_Schema_TemplateLine:
      | Identifier | C_CompensationGroup_Schema_ID | M_Product_ID | Qty | C_UOM_ID | SeqNo |
      | tl_reis    | schema_1                      | reis         | 200 | GRM      | 10    |
    And metasfresh contains C_CompensationGroup_CalibrationRule:
      | Identifier | SeqNo | C_BPartner_ID | GroupCompensationCalibrationFactor |
      | r10        | 10    | cust_x        | 50                                 |
      | r20        | 20    | cust_y        | 80                                 |
    And metasfresh contains C_BPartner_Locations:
      | Identifier | GLN           | C_BPartner_ID | OPT.IsShipToDefault | OPT.IsBillToDefault |
      | location_y | 0130456809028 | cust_y        | Y                   | Y                   |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered |
      | order_x    | true    | cust_x        | 2026-10-07  |
    And create compensation group from schema template:
      | C_Order_ID | C_CompensationGroup_Schema_ID | Qty | Calibrated | IdentifyLinesBy |
      | order_x    | schema_1                      | 1   | Y          | Product         |
    And validate C_OrderLine:
      | C_OrderLine_ID | M_Product_ID | OPT.QtyEntered | OPT.GroupCompensationCalibrationFactor | OPT.GroupCompensationQtyEnteredUncalibrated | OPT.C_CompensationGroup_CalibrationRule_ID |
      | schema_ol_reis | reis         | 100            | 50                                     | 200                                         | r10                                        |

    # the rule's factor is changed
    When update C_CompensationGroup_CalibrationRule:
      | Identifier | GroupCompensationCalibrationFactor |
      | r10        | 90                                 |
    And load C_OrderLines from C_Order:
      | C_Order_ID | C_OrderLine_ID | M_Product_ID |
      | order_x    | schema_ol_reis | reis         |
    Then validate C_OrderLine:
      | C_OrderLine_ID | M_Product_ID | OPT.QtyEntered | OPT.GroupCompensationCalibrationFactor | OPT.GroupCompensationQtyEnteredUncalibrated | OPT.C_CompensationGroup_CalibrationRule_ID |
      | schema_ol_reis | reis         | 100            | 50                                     | 200                                         | r10                                        |

    # the order's customer is changed to one with another rule, together with its location
    When update order
      | C_Order_ID | C_BPartner_ID | C_BPartner_Location_ID |
      | order_x    | cust_y        | location_y             |
    And load C_OrderLines from C_Order:
      | C_Order_ID | C_OrderLine_ID | M_Product_ID |
      | order_x    | schema_ol_reis | reis         |
    Then validate C_OrderLine:
      | C_OrderLine_ID | M_Product_ID | OPT.QtyEntered | OPT.GroupCompensationCalibrationFactor | OPT.GroupCompensationQtyEnteredUncalibrated | OPT.C_CompensationGroup_CalibrationRule_ID |
      | schema_ol_reis | reis         | 100            | 50                                     | 200                                         | r10                                        |


  # ##########################################################################################
  # A copied order keeps the lines' quantity, factor and rule, without a new match
  @from:cucumber
  @Id:S26881_TC13_CopiedOrder
  Scenario: A copied order keeps the calibration of the original lines although the rule has changed
    Given metasfresh contains C_CompensationGroup_Schema:
      | Identifier | Name      |
      | schema_1   | CalibCopy |
    And metasfresh contains C_CompensationGroup_Schema_TemplateLine:
      | Identifier | C_CompensationGroup_Schema_ID | M_Product_ID | Qty | C_UOM_ID | SeqNo |
      | tl_reis    | schema_1                      | reis         | 200 | GRM      | 10    |
    And metasfresh contains C_CompensationGroup_CalibrationRule:
      | Identifier | SeqNo | C_BPartner_ID | GroupCompensationCalibrationFactor |
      | r10        | 10    | cust_x        | 50                                 |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered |
      | order_x    | true    | cust_x        | 2026-10-07  |
    And create compensation group from schema template:
      | C_Order_ID | C_CompensationGroup_Schema_ID | Qty | Calibrated | IdentifyLinesBy |
      | order_x    | schema_1                      | 1   | Y          | Product         |
    And update C_CompensationGroup_CalibrationRule:
      | Identifier | GroupCompensationCalibrationFactor |
      | r10        | 90                                 |

    When C_Order is cloned
      | C_Order_ID.Identifier | ClonedOrder.C_Order_ID.Identifier |
      | order_x               | order_copy                        |
    And load C_OrderLines from C_Order:
      | C_Order_ID | C_OrderLine_ID | M_Product_ID |
      | order_copy | copy_ol_reis   | reis         |
    # the copy takes over 100 / 50 / 200 / r10, not the 180 a new match with the changed rule would give
    Then validate C_OrderLine:
      | C_OrderLine_ID | M_Product_ID | OPT.QtyEntered | OPT.GroupCompensationCalibrationFactor | OPT.GroupCompensationQtyEnteredUncalibrated | OPT.C_CompensationGroup_CalibrationRule_ID |
      | copy_ol_reis   | reis         | 100            | 50                                     | 200                                         | r10                                        |


  # ##########################################################################################
  # A sales order created from a calibrated quotation keeps the lines' quantity, factor and rule
  @from:cucumber
  @Id:S26881_TC13_SalesOrderFromQuotation
  Scenario: A sales order created from a calibrated quotation keeps the calibration of the quotation lines
    Given metasfresh contains C_CompensationGroup_Schema:
      | Identifier | Name       |
      | schema_1   | CalibQuote |
    And metasfresh contains C_CompensationGroup_Schema_TemplateLine:
      | Identifier | C_CompensationGroup_Schema_ID | M_Product_ID | Qty | C_UOM_ID | SeqNo |
      | tl_reis    | schema_1                      | reis         | 200 | GRM      | 10    |
    And metasfresh contains C_CompensationGroup_CalibrationRule:
      | Identifier | SeqNo | C_BPartner_ID | GroupCompensationCalibrationFactor |
      | r10        | 10    | cust_x        | 50                                 |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | DocBaseType | DocSubType |
      | quote_x    | true    | cust_x        | 2026-10-07  | SOO         | ON         |
    And create compensation group from schema template:
      | C_Order_ID | C_CompensationGroup_Schema_ID | Qty | Calibrated | IdentifyLinesBy |
      | quote_x    | schema_1                      | 1   | Y          | Product         |
    And the order identified by quote_x is completed
    And update C_CompensationGroup_CalibrationRule:
      | Identifier | GroupCompensationCalibrationFactor |
      | r10        | 90                                 |

    When sales order is created from proposal:
      | C_Order_ID | SalesOrder.C_Order_ID |
      | quote_x    | order_x               |
    And load C_OrderLines from C_Order:
      | C_Order_ID | C_OrderLine_ID | M_Product_ID |
      | order_x    | order_ol_reis  | reis         |
    # 100 / 50 / 200 / r10 as quoted, not the 180 a new match with the changed rule would give
    Then validate C_OrderLine:
      | C_OrderLine_ID | M_Product_ID | OPT.QtyEntered | OPT.GroupCompensationCalibrationFactor | OPT.GroupCompensationQtyEnteredUncalibrated | OPT.C_CompensationGroup_CalibrationRule_ID |
      | order_ol_reis  | reis         | 100            | 50                                     | 200                                         | r10                                        |


  # ##########################################################################################
  # The action grouping existing lines by product category is not calibrated
  @from:cucumber
  @Id:S26881_TC13_MultiGroupsProcess
  Scenario: Grouping order lines by the schema of their product category adds uncalibrated template lines
    Given metasfresh contains C_CompensationGroup_Schema:
      | Identifier | Name          |
      | schema_1   | CalibMultiGrp |
    And metasfresh contains C_CompensationGroup_Schema_TemplateLine:
      | Identifier | C_CompensationGroup_Schema_ID | M_Product_ID | Qty | C_UOM_ID | SeqNo |
      | tl_reis    | schema_1                      | reis         | 200 | GRM      | 10    |
    And metasfresh contains M_Product_Category:
      | Identifier | Name         | Value         |
      | cat_menu   | CalibMenuCat | CalibMenuCat1 |
    And update M_Product_Category:
      | M_Product_Category_ID | OPT.C_CompensationGroup_Schema_ID |
      | cat_menu              | schema_1                          |
    And metasfresh contains M_Products:
      | Identifier | X12DE355 | IsStocked | OPT.M_Product_Category_ID.Identifier |
      | menu_a     | PCE      | false     | cat_menu                             |
      | menu_b     | PCE      | false     | cat_menu                             |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_TaxCategory_ID.InternalName | C_UOM_ID.X12DE355 |
      | pp_menu_a  | plv_sales              | menu_a       | 1        | Normal                        | PCE               |
      | pp_menu_b  | plv_sales              | menu_b       | 1        | Normal                        | PCE               |
    And metasfresh contains C_CompensationGroup_CalibrationRule:
      | Identifier | SeqNo | C_BPartner_ID | GroupCompensationCalibrationFactor |
      | r10        | 10    | cust_x        | 50                                 |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered |
      | order_x    | true    | cust_x        | 2026-10-07  |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | ol_menu_a  | order_x    | menu_a       | 1          |
      | ol_menu_b  | order_x    | menu_b       | 1          |

    When the AD_Process with value 'C_Order_CreateCompensationMultiGroups' is run on the record identified by 'order_x' with the selected included records 'ol_menu_a,ol_menu_b'
    And load C_OrderLines from C_Order:
      | C_Order_ID | C_OrderLine_ID | M_Product_ID |
      | order_x    | ol_reis        | reis         |
    # a rule matches the customer, yet the template line has today's quantity and no calibration
    Then validate C_OrderLine:
      | C_OrderLine_ID | M_Product_ID | OPT.QtyEntered | OPT.GroupCompensationCalibrationFactor | OPT.GroupCompensationQtyEnteredUncalibrated | OPT.C_CompensationGroup_CalibrationRule_ID |
      | ol_reis        | reis         | 200            | null                                   | null                                        | null                                       |
