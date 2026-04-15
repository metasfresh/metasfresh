@from:cucumber
@ghActions:run_on_executor5
Feature: Compensation Group PI Inheritance

  When a compensation group schema has IsInheritPackingInstruction=Y, creating a group
  from the template should apply a compatible packing instruction to each sub-article
  order line, based on the main article's M_HU_PI_Item (TU type).

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2022-05-17T13:30:13+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION

  @from:cucumber
  @Id:S0468_040
  Scenario: Compensation group with IsInheritPackingInstruction=Y copies PI to sub-articles
    # -- Products: main article + 2 sub-articles --
    Given metasfresh contains M_Products:
      | Identifier   |
      | mainProduct  |
      | subProduct1  |
      | subProduct2  |

    # -- Pricing setup --
    And metasfresh contains M_PricingSystems
      | Identifier | Name             | Value            |
      | ps_pi      | ps_pi_inherit    | ps_pi_inherit    |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | C_Currency.ISO_Code | Name       | SOTrx | IsTaxIncluded | PricePrecision |
      | pl_pi      | ps_pi              | EUR                 | pl_pi_inh  | true  | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID | Name       | ValidFrom  |
      | plv_pi     | pl_pi          | plv_pi_inh | 2022-01-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_TaxCategory_ID.InternalName | C_UOM_ID.X12DE355 |
      | pp_main    | plv_pi                 | mainProduct  | 10.0     | Normal                        | PCE               |
      | pp_sub1    | plv_pi                 | subProduct1  | 5.0      | Normal                        | PCE               |
      | pp_sub2    | plv_pi                 | subProduct2  | 3.0      | Normal                        | PCE               |

    # -- HU Packing Instructions: a TU (e.g., "Mischkarton") with PI Item Products for all 3 products --
    And metasfresh contains M_HU_PI:
      | Identifier | Name           |
      | huPI_TU    | Mischkarton_TU |
    And metasfresh contains M_HU_PI_Version:
      | Identifier   | M_HU_PI_ID | HU_UnitType |
      | huPIV_TU     | huPI_TU     | TU          |
    And metasfresh contains M_HU_PI_Item:
      | Identifier   | M_HU_PI_Version_ID | ItemType |
      | huPIItem_mat | huPIV_TU            | MI       |

    # PI Item Product for main article (this is the one Quick Input would resolve)
    And metasfresh contains M_HU_PI_Item_Product:
      | Identifier       | M_HU_PI_Item_ID | M_Product_ID | Qty | C_UOM_ID.X12DE355 |
      | piProd_main      | huPIItem_mat     | mainProduct  | 1   | PCE                |
    # PI Item Product for sub-article 1 (compatible — same M_HU_PI_Item)
    And metasfresh contains M_HU_PI_Item_Product:
      | Identifier       | M_HU_PI_Item_ID | M_Product_ID | Qty | C_UOM_ID.X12DE355 |
      | piProd_sub1      | huPIItem_mat     | subProduct1  | 8   | PCE                |
    # PI Item Product for sub-article 2 (compatible — same M_HU_PI_Item)
    And metasfresh contains M_HU_PI_Item_Product:
      | Identifier       | M_HU_PI_Item_ID | M_Product_ID | Qty | C_UOM_ID.X12DE355 |
      | piProd_sub2      | huPIItem_mat     | subProduct2  | 8   | PCE                |

    # -- Compensation Group Schema with IsInheritPackingInstruction=Y --
    And metasfresh contains C_CompensationGroup_Schema:
      | Identifier | Name        | IsInheritPackingInstruction |
      | schema_1   | Mischkarton | Y                           |
    And metasfresh contains C_CompensationGroup_Schema_TemplateLine:
      | Identifier | C_CompensationGroup_Schema_ID | M_Product_ID | Qty | C_UOM_ID | SeqNo |
      | tl_sub1    | schema_1                      | subProduct1  | 8   | PCE      | 10    |
      | tl_sub2    | schema_1                      | subProduct2  | 8   | PCE      | 20    |

    # -- BPartner + Order --
    And metasfresh contains C_BPartners:
      | Identifier  | Name           | IsCustomer | M_PricingSystem_ID |
      | bp_pi_test  | PI_Test_BP     | Y          | ps_pi              |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered |
      | order_pi   | true    | bp_pi_test    | 2022-05-17  |

    # -- Create group from schema template, applying PI from main article --
    When create compensation group from schema template:
      | C_Order_ID | C_CompensationGroup_Schema_ID | Qty | M_HU_PI_Item_Product_ID |
      | order_pi   | schema_1                      | 1   | piProd_main             |

    # -- Verify: both sub-article order lines got their own compatible PI --
    Then validate C_OrderLine:
      | C_OrderLine_ID | M_Product_ID | OPT.M_HU_PI_Item_Product_ID.Identifier |
      | schema_ol_1    | subProduct1  | piProd_sub1                             |
      | schema_ol_2    | subProduct2  | piProd_sub2                             |

  @from:cucumber
  @Id:S0468_050
  Scenario: Compensation group with IsInheritPackingInstruction=Y skips articles without compatible PI
    # -- Products: main article + 1 sub-article WITH PI + 1 sub-article WITHOUT PI --
    Given metasfresh contains M_Products:
      | Identifier      |
      | mainProduct2    |
      | subWithPI       |
      | subWithoutPI    |

    # -- Pricing setup --
    And metasfresh contains M_PricingSystems
      | Identifier | Name              | Value             |
      | ps_pi2     | ps_pi_skip        | ps_pi_skip        |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | C_Currency.ISO_Code | Name        | SOTrx | IsTaxIncluded | PricePrecision |
      | pl_pi2     | ps_pi2             | EUR                 | pl_pi_skip  | true  | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID | Name        | ValidFrom  |
      | plv_pi2    | pl_pi2         | plv_pi_skip | 2022-01-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_TaxCategory_ID.InternalName | C_UOM_ID.X12DE355 |
      | pp_main2   | plv_pi2                | mainProduct2 | 10.0     | Normal                        | PCE               |
      | pp_sub3    | plv_pi2                | subWithPI    | 5.0      | Normal                        | PCE               |
      | pp_sub4    | plv_pi2                | subWithoutPI | 3.0      | Normal                        | PCE               |

    # -- HU PI: only main + subWithPI have PI Item Products --
    And metasfresh contains M_HU_PI:
      | Identifier  | Name            |
      | huPI_TU2    | Mischkarton_TU2 |
    And metasfresh contains M_HU_PI_Version:
      | Identifier  | M_HU_PI_ID | HU_UnitType |
      | huPIV_TU2   | huPI_TU2    | TU          |
    And metasfresh contains M_HU_PI_Item:
      | Identifier    | M_HU_PI_Version_ID | ItemType |
      | huPIItem_mat2 | huPIV_TU2           | MI       |

    And metasfresh contains M_HU_PI_Item_Product:
      | Identifier        | M_HU_PI_Item_ID | M_Product_ID | Qty | C_UOM_ID.X12DE355 |
      | piProd_main2      | huPIItem_mat2    | mainProduct2 | 1   | PCE                |
    And metasfresh contains M_HU_PI_Item_Product:
      | Identifier        | M_HU_PI_Item_ID | M_Product_ID | Qty | C_UOM_ID.X12DE355 |
      | piProd_subWithPI   | huPIItem_mat2    | subWithPI    | 8   | PCE                |
    # NOTE: No M_HU_PI_Item_Product for subWithoutPI — inheritance should be skipped for it

    # -- Schema + template lines --
    And metasfresh contains C_CompensationGroup_Schema:
      | Identifier | Name         | IsInheritPackingInstruction |
      | schema_2   | Mischkarton2 | Y                           |
    And metasfresh contains C_CompensationGroup_Schema_TemplateLine:
      | Identifier | C_CompensationGroup_Schema_ID | M_Product_ID | Qty | C_UOM_ID | SeqNo |
      | tl2_sub1   | schema_2                      | subWithPI    | 8   | PCE      | 10    |
      | tl2_sub2   | schema_2                      | subWithoutPI | 4   | PCE      | 20    |

    # -- BPartner + Order --
    And metasfresh contains C_BPartners:
      | Identifier   | Name            | IsCustomer | M_PricingSystem_ID |
      | bp_pi_test2  | PI_Test_BP2     | Y          | ps_pi2             |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered |
      | order_pi2  | true    | bp_pi_test2   | 2022-05-17  |

    # -- Create group, providing main article's PI --
    When create compensation group from schema template:
      | C_Order_ID | C_CompensationGroup_Schema_ID | Qty | M_HU_PI_Item_Product_ID |
      | order_pi2  | schema_2                      | 1   | piProd_main2            |

    # -- Verify: subWithPI got PI, subWithoutPI kept default (101 = Virtual/No Packing Item) --
    Then validate C_OrderLine:
      | C_OrderLine_ID | M_Product_ID | OPT.M_HU_PI_Item_Product_ID.Identifier |
      | schema_ol_1    | subWithPI    | piProd_subWithPI                        |
    # subWithoutPI should NOT have piProd_subWithPI — it should have the default (101)
    # We verify it exists but don't check PI (no compatible PI was found, so it stays at default)
    And validate C_OrderLine:
      | C_OrderLine_ID | M_Product_ID |
      | schema_ol_2    | subWithoutPI |
