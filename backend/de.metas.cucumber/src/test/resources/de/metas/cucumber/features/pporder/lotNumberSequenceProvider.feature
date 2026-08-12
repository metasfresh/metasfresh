@from:cucumber
@ghActions:run_on_executor5
@allure.label.epic:E3000_Manufacturing
@allure.label.feature:F5000_Handling_Unit
@F5000
Feature: Lot number stamped on HU during manufacturing receipt
## Two paths through lot-number assignment on production receipt:
## 1. Provider-driven: AD_Sequence with DBFunctionSequenceNoProvider → lot value comes from a PL/pgSQL function
## 2. Plain sequence (no provider): lot value is the plain incremental number (zero-regression)

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2025-04-01T13:30:13+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION

    And metasfresh contains M_Products:
      | Identifier        |
      | finishedGoodsProd |
      | rawMaterialProd   |

    And metasfresh contains M_HU_PI:
      | M_HU_PI_ID.Identifier |
      | lotNoTU_PI            |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID.Identifier | M_HU_PI_ID.Identifier | HU_UnitType | IsCurrent |
      | lotNoTU_PIVersion             | lotNoTU_PI            | TU          | Y         |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID.Identifier | M_HU_PI_Version_ID.Identifier | Qty | ItemType |
      | lotNoTU_PIItem             | lotNoTU_PIVersion             | 0   | MI       |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID.Identifier | M_HU_PI_Item_ID.Identifier | M_Product_ID.Identifier | Qty | ValidFrom  |
      | lotNoProduct_HUPI                  | lotNoTU_PIItem             | finishedGoodsProd       | 10  | 2021-01-01 |

    And load S_Resource:
      | S_Resource_ID.Identifier | S_Resource_ID |
      | testResource             | 540006        |

  @from:cucumber
  Scenario: Provider-driven lot number — DBFunctionSequenceNoProvider stamps a PL/pgSQL-derived value on the finished-goods HU
    ## The sequence has DBFunctionSequenceNoProvider as its provider.
    ## The provider calls the PL/pgSQL function and returns its result as the lot number (no counter appended).
    ## Fixed system time 2025-04-01T13:30:13+01:00 → UTC day-of-year 091 → expected lot = L091M2

    And the following PL/pgSQL function is created or replaced in the DB:
    """
    CREATE OR REPLACE FUNCTION test_lotno_provider(p_pp_order_id numeric, p_at timestamptz)
      RETURNS text LANGUAGE sql AS
    $$ SELECT 'L' || to_char(p_at AT TIME ZONE 'UTC', 'DDD') || 'M2' $$
    """

    And metasfresh contains AD_Sequence:
      | AD_Sequence_ID.Identifier | Name                      | OPT.CustomSequenceNoProvider_JavaClass_ID.Classname                   | OPT.StartNo |
      | seq_lotno_provider        | TestLotNoProviderSequence | de.metas.document.sequenceno.DBFunctionSequenceNoProvider             | 1           |

    And set sys config String value test_lotno_provider for sys config de.metas.document.seqNo.DBFunctionSequenceNoProvider.TestLotNoProviderSequence.dbFunctionName

    And metasfresh contains PP_Product_BOM
      | Identifier      | M_Product_ID.Identifier | ValidFrom  | PP_Product_BOMVersions_ID.Identifier |
      | bom_provider    | finishedGoodsProd       | 2021-01-01 | bomVersions_provider                 |
    And metasfresh contains PP_Product_BOMLines
      | Identifier          | PP_Product_BOM_ID.Identifier | M_Product_ID.Identifier | ValidFrom  | QtyBatch |
      | bomLine_provider    | bom_provider                 | rawMaterialProd         | 2021-01-01 | 10       |
    And the PP_Product_BOM identified by bom_provider is completed
    And update PP_Product_BOM:
      | PP_Product_BOM_ID.Identifier | OPT.LotNo_Sequence_ID.Identifier |
      | bom_provider                 | seq_lotno_provider               |

    And create PP_Order:
      | PP_Order_ID.Identifier  | DocBaseType | M_Product_ID.Identifier | QtyEntered | S_Resource_ID.Identifier | DateOrdered             | DatePromised            | DateStartSchedule       | completeDocument |
      | ppOrder_lotno_provider  | MOP         | finishedGoodsProd       | 10         | testResource             | 2025-04-01T23:59:00.00Z | 2025-04-01T23:59:00.00Z | 2025-04-01T23:59:00.00Z | Y                |

    And receive HUs for PP_Order with M_HU_LUTU_Configuration:
      | PP_Order_ID             | M_HU_ID.Identifier  | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID.Identifier |
      | ppOrder_lotno_provider  | hu_lotno_provider   | N               | 0     | N               | 1     | N               | 10          | lotNoProduct_HUPI                  |

    When complete planning for PP_Order:
      | PP_Order_ID.Identifier |
      | ppOrder_lotno_provider |

    Then M_HU_Attribute is validated
      | M_HU_ID             | M_Attribute_ID.Value | Value  |
      | hu_lotno_provider   | Lot-Nummer           | L091M2 |

  @from:cucumber
  Scenario: Plain incremental lot number — no provider configured, lot is the plain sequence counter (zero-regression)
    ## AD_Sequence with no CustomSequenceNoProvider → DocumentNoBuilder uses the plain counter.

    And metasfresh contains AD_Sequence:
      | AD_Sequence_ID.Identifier | Name                  | OPT.StartNo |
      | seq_lotno_plain           | TestLotNoPlainSeq     | 1           |

    And metasfresh contains PP_Product_BOM
      | Identifier   | M_Product_ID.Identifier | ValidFrom  | PP_Product_BOMVersions_ID.Identifier |
      | bom_plain    | finishedGoodsProd       | 2021-01-01 | bomVersions_plain                    |
    And metasfresh contains PP_Product_BOMLines
      | Identifier       | PP_Product_BOM_ID.Identifier | M_Product_ID.Identifier | ValidFrom  | QtyBatch |
      | bomLine_plain    | bom_plain                    | rawMaterialProd         | 2021-01-01 | 10       |
    And the PP_Product_BOM identified by bom_plain is completed
    And update PP_Product_BOM:
      | PP_Product_BOM_ID.Identifier | OPT.LotNo_Sequence_ID.Identifier |
      | bom_plain                    | seq_lotno_plain                  |

    And create PP_Order:
      | PP_Order_ID.Identifier | DocBaseType | M_Product_ID.Identifier | QtyEntered | S_Resource_ID.Identifier | DateOrdered             | DatePromised            | DateStartSchedule       | completeDocument |
      | ppOrder_lotno_plain    | MOP         | finishedGoodsProd       | 10         | testResource             | 2025-04-01T23:59:00.00Z | 2025-04-01T23:59:00.00Z | 2025-04-01T23:59:00.00Z | Y                |

    And receive HUs for PP_Order with M_HU_LUTU_Configuration:
      | PP_Order_ID          | M_HU_ID.Identifier | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID.Identifier |
      | ppOrder_lotno_plain  | hu_lotno_plain     | N               | 0     | N               | 1     | N               | 10          | lotNoProduct_HUPI                  |

    When complete planning for PP_Order:
      | PP_Order_ID.Identifier |
      | ppOrder_lotno_plain    |

    Then M_HU_Attribute is validated
      | M_HU_ID        | M_Attribute_ID.Value | Value |
      | hu_lotno_plain | Lot-Nummer           | 1     |
