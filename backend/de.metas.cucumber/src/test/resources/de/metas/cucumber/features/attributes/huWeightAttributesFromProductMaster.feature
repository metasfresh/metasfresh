@from:cucumber
@allure.label.epic:E2300_Attributes
@allure.label.feature:F67043_Weight_Calculation
@ghActions:run_on_executor7
Feature: HU weight attributes are derived from product master Net + Gross

  When a product has both a NetWeight and a GrossWeight on its master record, the
  HU transaction listener writes the NetWeight × qty into the HU's WeightNet attribute
  and the per-CU packaging delta ((GrossWeight - NetWeight) × qty) into WeightTare.
  Total WeightGross = WeightNet + WeightTare stays mathematically unchanged.

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2026-04-29T13:30:13+02:00[Europe/Berlin]

    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION

    And metasfresh contains M_Products:
      | Identifier | X12DE355 | WeightNet | WeightGross |
      | product_1  | PCE      | 0.200 KGM | 0.205 KGM   |

    And metasfresh contains M_HU_PI:
      | M_HU_PI_ID |
      | TU         |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID | M_HU_PI_ID | HU_UnitType | IsCurrent |
      | TU                 | TU         | TU          | Y         |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID | M_HU_PI_Version_ID | Qty | ItemType |
      | TU              | TU                 | 0   | MI       |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID | M_HU_PI_Item_ID | M_Product_ID | Qty | ValidFrom  |
      | TUx100_product_1        | TU              | product_1    | 100 | 2000-01-01 |


  Scenario: Receiving 20 PCE writes WeightNet=4.000 kg, WeightTare delta=0.100 kg, WeightGross=4.100 kg
    Given metasfresh contains M_Inventories:
      | M_Inventory_ID | MovementDate | M_Warehouse_ID |
      | inventory      | 2026-04-29   | 540008         |
    And metasfresh contains M_InventoriesLines:
      | M_Inventory_ID | M_InventoryLine_ID | M_Product_ID | QtyBook | QtyCount | UOM.X12DE355 |
      | inventory      | line1              | product_1    | 0       | 20       | PCE          |
    And complete inventory with inventoryIdentifier 'inventory'
    And after not more than 60s, there are added M_HUs for inventory
      | M_InventoryLine_ID | M_HU_ID |
      | line1              | hu_1    |

    Then M_HU_Attribute is validated
      | M_HU_ID | M_Attribute_ID.Value | ValueNumber |
      | hu_1    | WeightNet            | 4.000       |
      | hu_1    | WeightTare           | 0.100       |
      | hu_1    | WeightGross          | 4.100       |




  Scenario: Receiving 9 TUs of 50 CUs onto an LU palette via WEBUI_M_ReceiptSchedule_ReceiveHUs_UsingConfig keeps WeightTare correct from planning HU through completed material receipt
    # Small product (40g net / 41g gross) packed 50 per Karton, 9 Kartons on a Pallet.
    # TU PI has no PM item (carton not modelled on TU level here), only the LU PI has a PM item (Pallet).
    # retrievePackingMaterials(pipTU) finds no sibling PM on tuPiVer, so order completion does not
    # auto-generate packing-material C_OrderLines.
    #
    # The HU listener writes WeightNet+WeightTare on each VHU during the trx; BOTU propagation rolls
    # the totals up to the LU. The scenario asserts at TWO stages so a regression in either path is
    # caught:
    #   1) immediately after `create M_HU_LUTU_Configuration ... generate M_HUs` (HUStatus=P)
    #   2) after `create material receipt` (HUStatus=A — posts M_InOut and fires HU-to-HU transfer pairs)
    Given metasfresh contains M_Products:
      | Identifier   | X12DE355 | WeightNet | WeightGross |
      | productSmall | PCE      | 0.040 KGM | 0.041 KGM   |
      | packagingLU  | PCE      |           |             |

    # Per-product PCE↔KGM conversion — without it productBL.getGrossWeight(productId, PCE) returns
    # empty in WeightTareDeltaTransferStrategy and the strategy short-circuits before reaching the
    # source-tare guard.
    And metasfresh contains C_UOM_Conversions
      | M_Product_ID | FROM_C_UOM_ID.X12DE355 | TO_C_UOM_ID.X12DE355 | MultiplyRate |
      | productSmall | PCE                    | KGM                  | 0.041        |

    And metasfresh contains M_HU_PackingMaterial:
      | M_HU_PackingMaterial_ID | M_Product_ID |
      | huPackingLU             | packagingLU  |

    And metasfresh contains M_HU_PI:
      | M_HU_PI_ID |
      | tuPi       |
      | luPi       |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID | M_HU_PI_ID | HU_UnitType | IsCurrent |
      | tuPiVer            | tuPi       | TU          | Y         |
      | luPiVer            | luPi       | LU          | Y         |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID | M_HU_PI_Version_ID | Qty | ItemType | M_HU_PackingMaterial_ID | Included_HU_PI_ID |
      | tuMiItem        | tuPiVer            | 0   | MI       |                         |                   |
      | luHuItem        | luPiVer            | 100 | HU       |                         | tuPi              |
      | luPmItem        | luPiVer            | 1   | PM       | huPackingLU             |                   |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID | M_HU_PI_Item_ID | M_Product_ID | Qty | ValidFrom  |
      | pipTU                   | tuMiItem        | productSmall | 50  | 2000-01-01 |

    And load M_Warehouse:
      | M_Warehouse_ID | Value        |
      | warehouseStd   | StdWarehouse |
    And metasfresh contains M_PricingSystems
      | Identifier |
      | ps         |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | C_Country_ID | C_Currency_ID | SOTrx |
      | pl         | ps                 | DE           | EUR           | false |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID |
      | plv        | pl             |
    And metasfresh contains M_ProductPrices
      | Identifier  | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID |
      | pp          | plv                    | productSmall | 1.0      | PCE      |
      | ppPackingLU | plv                    | packagingLU  | 0.0      | PCE      |
    And metasfresh contains C_BPartners without locations:
      | Identifier | IsVendor | IsCustomer | M_PricingSystem_ID |
      | vendor     | Y        | N          | ps                 |
    And metasfresh contains C_BPartner_Locations:
      | Identifier | C_BPartner_ID | C_Country_ID | IsShipToDefault | IsBillToDefault |
      | vendorLoc  | vendor        | DE           | Y               | Y               |

    When metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | C_PaymentTerm_ID | DocBaseType | M_PricingSystem_ID | DatePromised        | M_Warehouse_ID |
      | po         | N       | vendor        | 2026-04-29  | 1000012          | POO         | ps                 | 2026-04-30T15:00:00 | warehouseStd   |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered | Price | M_HU_PI_Item_Product_ID |
      | poLine     | po         | productSmall | 450        | 1.0   | pipTU                   |
    And the order identified by po is completed
    And after not more than 60s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID | C_Order_ID | C_OrderLine_ID | C_BPartner_ID | C_BPartner_Location_ID | M_Product_ID | QtyOrdered | M_Warehouse_ID |
      | rs                   | po         | poLine         | vendor        | vendorLoc              | productSmall | 450        | warehouseStd   |

    # Generate the planning HUs (HUStatus=P) — same code path as WEBUI_M_ReceiptSchedule_ReceiveHUs_UsingConfig
    And create M_HU_LUTU_Configuration for M_ReceiptSchedule and generate M_HUs
      | M_HU_LUTU_Configuration_ID | M_HU_ID | M_ReceiptSchedule_ID | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID | M_LU_HU_PI_ID |
      | luTuConfig                 | lu      | rs                   | N               | 1     | N               | 9     | N               | 50          | pipTU                   | luPi          |

    # Stage 1 — planning state (HUStatus=P): listener has written net+tare during VHU fill.
    Then M_HU_Attribute is validated
      | M_HU_ID | M_Attribute_ID.Value | ValueNumber |
      | lu      | WeightNet            | 18.000      |
      | lu      | WeightTare           | 0.450       |
      | lu      | WeightGross          | 18.450      |

    # Complete the material receipt → posts M_InOut and transitions HUStatus from P to A via HU-to-HU
    # transfer pairs. Both pairs of transfer trx_lines carry vhu_item_id on both sides, so the listener's
    # counterpart-VHU guard skips them. WeightTareDeltaTransferStrategy is also called by HULoader.transferAttributes
    # but skips via its source-tare guard because source tare is zero. Tare stays at 0.450.
    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes
    And create material receipt
      | M_HU_ID | M_ReceiptSchedule_ID | M_InOut_ID |
      | lu      | rs                   | receipt    |

    # Stage 2 — post-receipt state (HUStatus=A): values must remain unchanged.
    Then M_HU_Attribute is validated
      | M_HU_ID | M_Attribute_ID.Value | ValueNumber |
      | lu      | WeightNet            | 18.000      |
      | lu      | WeightTare           | 0.450       |
      | lu      | WeightGross          | 18.450      |


  Scenario: Receiving 9 TUs of 50 CUs onto an LU palette with Template PI 100 Weight* UseInASI=Y keeps WeightTare correct
    # Same structural shape as the previous scenario (own test-created tuPi/luPi), but with Template PI 100's
    # WeightGross/WeightNet/WeightTare attributes flipped to UseInASI=Y so that
    # ASIWithPackingItemTemplateAttributeStorage iterates them during transferAttributes. With this flip,
    # WeightTareDeltaTransferStrategy is invoked on the receipt-schedule's ASI source during planning-HU
    # generation; its isTransferable source-tare guard short-circuits on signum(sourceTare) <= 0, so the
    # listener writes the per-CU packaging delta alone and the aggregate VHU's WeightTare lands at 0.450.
    #
    # M_HU_PI_Attribute_StepDef's @After hook captures the pre-update UseInASI value when the flip is
    # performed and restores it afterwards regardless of scenario pass/fail, so the override never leaks
    # into subsequent scenarios.
    Given metasfresh contains M_Products:
      | Identifier   | X12DE355 | WeightNet | WeightGross |
      | productSmall | PCE      | 0.040 KGM | 0.041 KGM   |
      | packagingLU  | PCE      |           |             |

    And metasfresh contains C_UOM_Conversions
      | M_Product_ID | FROM_C_UOM_ID.X12DE355 | TO_C_UOM_ID.X12DE355 | MultiplyRate |
      | productSmall | PCE                    | KGM                  | 0.041        |

    And metasfresh contains M_HU_PackingMaterial:
      | M_HU_PackingMaterial_ID | M_Product_ID |
      | huPackingLU             | packagingLU  |

    And metasfresh contains M_HU_PI:
      | M_HU_PI_ID |
      | tuPi       |
      | luPi       |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID | M_HU_PI_ID | HU_UnitType | IsCurrent |
      | tuPiVer            | tuPi       | TU          | Y         |
      | luPiVer            | luPi       | LU          | Y         |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID | M_HU_PI_Version_ID | Qty | ItemType | M_HU_PackingMaterial_ID | Included_HU_PI_ID |
      | tuMiItem        | tuPiVer            | 0   | MI       |                         |                   |
      | luHuItem        | luPiVer            | 100 | HU       |                         | tuPi              |
      | luPmItem        | luPiVer            | 1   | PM       | huPackingLU             |                   |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID | M_HU_PI_Item_ID | M_Product_ID | Qty | ValidFrom  |
      | pipTU                   | tuMiItem        | productSmall | 50  | 2000-01-01 |

    # Flip Template PI 100 Weight* to UseInASI=Y so the strategy iterates them on the receipt-schedule's ASI.
    # The pre-update value is captured here and restored automatically by M_HU_PI_Attribute_StepDef's @After hook.
    And update M_HU_PI_Attribute UseInASI:
      | M_HU_PI_Version_ID | M_Attribute.Value | UseInASI |
      | 100                | WeightTare        | Y        |
      | 100                | WeightNet         | Y        |
      | 100                | WeightGross       | Y        |

    And load M_Warehouse:
      | M_Warehouse_ID | Value        |
      | warehouseStd   | StdWarehouse |
    And metasfresh contains M_PricingSystems
      | Identifier |
      | ps         |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | C_Country_ID | C_Currency_ID | SOTrx |
      | pl         | ps                 | DE           | EUR           | false |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID |
      | plv        | pl             |
    And metasfresh contains M_ProductPrices
      | Identifier  | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID |
      | pp          | plv                    | productSmall | 1.0      | PCE      |
      | ppPackingLU | plv                    | packagingLU  | 0.0      | PCE      |
    And metasfresh contains C_BPartners without locations:
      | Identifier | IsVendor | IsCustomer | M_PricingSystem_ID |
      | vendor     | Y        | N          | ps                 |
    And metasfresh contains C_BPartner_Locations:
      | Identifier | C_BPartner_ID | C_Country_ID | IsShipToDefault | IsBillToDefault |
      | vendorLoc  | vendor        | DE           | Y               | Y               |

    When metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | C_PaymentTerm_ID | DocBaseType | M_PricingSystem_ID | DatePromised        | M_Warehouse_ID |
      | po         | N       | vendor        | 2026-04-29  | 1000012          | POO         | ps                 | 2026-04-30T15:00:00 | warehouseStd   |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered | Price | M_HU_PI_Item_Product_ID |
      | poLine     | po         | productSmall | 450        | 1.0   | pipTU                   |
    And the order identified by po is completed
    And after not more than 60s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID | C_Order_ID | C_OrderLine_ID | C_BPartner_ID | C_BPartner_Location_ID | M_Product_ID | QtyOrdered | M_Warehouse_ID |
      | rs                   | po         | poLine         | vendor        | vendorLoc              | productSmall | 450        | warehouseStd   |

    And create M_HU_LUTU_Configuration for M_ReceiptSchedule and generate M_HUs
      | M_HU_LUTU_Configuration_ID | M_HU_ID | M_ReceiptSchedule_ID | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID | M_LU_HU_PI_ID |
      | luTuConfig                 | lu      | rs                   | N               | 1     | N               | 9     | N               | 50          | pipTU                   | luPi          |

    # Stage 1 — planning state (HUStatus=P): listener has written net+tare during VHU fill.
    Then M_HU_Attribute is validated
      | M_HU_ID | M_Attribute_ID.Value | ValueNumber |
      | lu      | WeightNet            | 18.000      |
      | lu      | WeightTare           | 0.450       |
      | lu      | WeightGross          | 18.450      |

    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes
    And create material receipt
      | M_HU_ID | M_ReceiptSchedule_ID | M_InOut_ID |
      | lu      | rs                   | receipt    |

    # Stage 2 — post-receipt state (HUStatus=A): values must remain unchanged.
    Then M_HU_Attribute is validated
      | M_HU_ID | M_Attribute_ID.Value | ValueNumber |
      | lu      | WeightNet            | 18.000      |
      | lu      | WeightTare           | 0.450       |
      | lu      | WeightGross          | 18.450      |
