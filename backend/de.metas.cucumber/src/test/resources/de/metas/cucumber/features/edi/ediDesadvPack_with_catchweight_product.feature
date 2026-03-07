@from:cucumber
@ghActions:run_on_executor4
@allure.label.epic:E0292_EDI
@allure.label.feature:F00350_EDI
@F00350
Feature: DESADV - mobileUI Picking - Pick catch weight products

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2024-03-26T13:30:13+01:00[Europe/Berlin]

    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And set sys config boolean value false for sys config de.metas.handlingunits.HUConstants.Fresh_QuickShipment
    And set sys config boolean value true for sys config de.metas.handlingunits.shipmentschedule.api.ShipmentScheduleWithHUService.PackCUsToTU

    And metasfresh contains M_PickingSlot:
      | Identifier | PickingSlot | IsDynamic |
      | 200.0      | 200.0       | Y         |

    And metasfresh contains M_Products:
      | Identifier | X12DE355 | WeightNet | WeightGross | GTIN           |
      | product    | PCE      | 1.9 KGM   | 1.95 KGM    | 97311876341811 |
    And metasfresh contains C_UOM_Conversions
      | M_Product_ID.Identifier | FROM_C_UOM_ID.X12DE355 | TO_C_UOM_ID.X12DE355 | MultiplyRate | OPT.IsCatchUOMForProduct |
      | product                 | PCE                    | KGM                  | 1.9          | Y                        |

    And metasfresh contains M_HU_PI:
      | M_HU_PI_ID |
      | TU         |
      | LU         |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID.Identifier | M_HU_PI_ID.Identifier | HU_UnitType | IsCurrent |
      | TU                            | TU                    | TU          | Y         |
      | LU                            | LU                    | LU          | Y         |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID.Identifier | M_HU_PI_Version_ID.Identifier | Qty | ItemType | OPT.Included_HU_PI_ID.Identifier |
      | TU                         | TU                            | 0   | MI       |                                  |
      | LU                         | LU                            | 10  | HU       | TU                               |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID.Identifier | M_HU_PI_Item_ID.Identifier | M_Product_ID.Identifier | Qty | ValidFrom  | IsDefaultForProduct |
      | TUx1                               | TU                         | product                 | 1   | 2000-01-01 | true                |


    And metasfresh contains M_PricingSystems
      | Identifier |
      | PS         |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | C_Country.CountryCode | C_Currency.ISO_Code | SOTrx |
      | PL         | PS                 | DE                    | EUR                 | true  |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID |
      | PLV        | PL             |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID.X12DE355 | InvoicableQtyBasedOn | C_TaxCategory_ID.InternalName |
      | PLV                    | product      | 5.0      | KGM               | CatchWeight          | Normal                        |

    And set mobile UI picking profile
      | IsAllowPickingAnyHU | CreateShipmentPolicy  | IsAllowCompletingPartialPickingJob | IsCatchWeightTUPickingEnabled |
      | Y                   | CREATE_COMPLETE_CLOSE | Y                                  | Y                             |

    And metasfresh contains C_BPartners without locations:
      | Identifier | Name     | IsVendor | IsCustomer | M_PricingSystem_ID.Identifier |
      | customer   | customer | N        | Y          | PS                            |
    And the following c_bpartner is changed
      | Identifier | IsEdiDesadvRecipient | EdiDesadvRecipientGLN |
      | customer   | true                 | Dummy_GLN             |
    And metasfresh contains C_BPartner_Locations:
      | Identifier       | GLN       | C_BPartner_ID.Identifier | OPT.IsBillToDefault | OPT.IsShipTo |
      | customerLocation | Dummy_GLN | customer                 | true                | true         |

    And metasfresh contains M_Inventories:
      | M_Inventory_ID.Identifier | MovementDate | M_Warehouse_ID |
      | inventory                 | 2024-03-20   | 540008         |
    And metasfresh contains M_InventoriesLines:
      | M_Inventory_ID.Identifier | M_InventoryLine_ID.Identifier | M_Product_ID.Identifier | QtyBook | QtyCount | UOM.X12DE355 |
      | inventory                 | line1                         | product                 | 0       | 1000     | PCE          |
    And complete inventory with inventoryIdentifier 'inventory'
    And after not more than 60s, there are added M_HUs for inventory
      | M_InventoryLine_ID | M_HU_ID    |
      | line1              | invCU      |
    And transform CU to new LU
      | sourceCU    | newLU                | TU_PI_ID | QtyCUsPerTU | QtyTUsPerLU |
      | invCU       | pickFromAggregatedLU | TU       | 1           | 10          |
    And M_HU are validated:
      | M_HU_ID              | HUStatus |
      | pickFromAggregatedLU | A        |












# ######################################################################################################################
# ######################################################################################################################
# ######################################################################################################################
# ######################################################################################################################
  @from:cucumber
  Scenario: Pick catch weight products
    When metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | POReference   |
      | salesOrder | true    | customer                 | 2024-03-26  | po_ref_@Date@ |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_HU_PI_Item_Product_ID.Identifier | OPT.C_UOM_ID.X12DE355 | OPT.QtyItemCapacity |
      | line1      | salesOrder            | product                 | 2          | TUx1                                   | COLI                  | 4                   |
    And the order identified by salesOrder is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier       | C_OrderLine_ID.Identifier | IsToRecompute |
      | shipmentSchedule | line1                     | N             |

    And start picking job for sales order identified by salesOrder
    And scan picking slot identified by 200.0
    And set picking target as new LU identified by LU
    And pick lines
      | PickingLine.byProduct | PickFromHU           | QtyPicked | CatchWeight |
      | product               | pickFromAggregatedLU | 2         | 3.153       |
    And expect current picking target
      | Existing_LU |
      | lu          |
    And validate M_ShipmentSchedule_QtyPicked records for M_ShipmentSchedule identified by shipmentSchedule
      | QtyDeliveredCatch | Catch_UOM_ID | QtyPicked | QtyTU | QtyLU | M_LU_HU_ID | Processed |
      | 3.153             | KGM          | 2         | 2     | 1     | lu         | N         |
    And complete picking job
    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier | OPT.DocStatus |
      | shipmentSchedule                 | shipment              | CO            |
    Then after not more than 30s, EDI_Desadv_Pack records are found:
      | EDI_Desadv_Pack_ID | M_HU_ID  | IsManual_IPA_SSCC18 |
      | p_1                | lu       | true                |

    And after not more than 30s, the EDI_Desadv_Pack_Item has only the following records:
      | EDI_Desadv_Pack_Item_ID | EDI_Desadv_Pack_ID | MovementQty | QtyCUsPerTU | QtyCUsPerTU_InInvoiceUOM | QtyCUsPerLU | QtyCUsPerLU_InInvoiceUOM | QtyTU | M_InOut_ID |
      | pi_1                    | p_1                | 2           | 1           | 1.577                    | 2           | 3.153                    | 2     | shipment   |

    And the shipment identified by shipment is reversed

    Then after not more than 30s, there are no records in EDI_Desadv_Pack_Item

    And after not more than 30s, there are no records in EDI_Desadv_Pack




