@from:cucumber
@ghActions:run_on_executor5
Feature: reversed shipment clears HU C_BPartner_ID

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2021-12-16T13:30:13+01:00[Europe/Berlin]

  @from:cucumber
  Scenario: reversing a shipment clears C_BPartner_ID on HU so it can be shipped to a different customer
    Given metasfresh contains M_Products:
      | Identifier | Name                    |
      | p_1        | hu_bpartner_clr_product |
    And metasfresh contains M_PricingSystems
      | Identifier | Name                        | Value                        | OPT.Description                    | OPT.IsActive |
      | ps_1       | hu_bpartner_clr_pricing_sys | hu_bpartner_clr_pricing_sys  | hu_bpartner_clr_pricing_sys_descr  | true         |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name                        | OPT.Description | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | pl_1       | ps_1                          | DE                        | EUR                 | hu_bpartner_clr_price_list  | null            | true  | false         | 2              | true         |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name                   | ValidFrom  |
      | plv_1      | pl_1                      | hu_bpartner_clr_PLV    | 2021-04-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_1       | plv_1                             | p_1                     | 10.0     | PCE               | Normal                        |

    # Two customers that will ship from the same HU
    And metasfresh contains C_BPartners:
      | Identifier     | Name              | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier | OPT.InvoiceRule |
      | endcustomer_A  | hu_bpc_customer_A | N            | Y              | ps_1                          | D               |
      | endcustomer_B  | hu_bpc_customer_B | N            | Y              | ps_1                          | D               |
    And metasfresh contains C_BPartner_Locations:
      | Identifier | GLN           | C_BPartner_ID.Identifier | OPT.IsShipToDefault | OPT.IsBillToDefault |
      | loc_A      | 0123456789011 | endcustomer_A            | Y                   | Y                   |
      | loc_B      | 0123456789012 | endcustomer_B            | Y                   | Y                   |

    # Create stock via inventory
    And metasfresh contains M_Inventories:
      | M_Inventory_ID.Identifier | MovementDate | M_Warehouse_ID |
      | inv_1                     | 2021-04-01   | 540008         |
    And metasfresh contains M_InventoriesLines:
      | M_Inventory_ID.Identifier | M_InventoryLine_ID.Identifier | M_Product_ID.Identifier | QtyBook | QtyCount | UOM.X12DE355 |
      | inv_1                     | inv_l_1                       | p_1                     | 0       | 10       | PCE          |
    When the inventory identified by inv_1 is completed
    And after not more than 60s, there are added M_HUs for inventory
      | M_InventoryLine_ID.Identifier | M_HU_ID.Identifier |
      | inv_l_1                       | hu_1               |

    # Verify initial state: HU active, no BPartner, storage=10
    And M_HU_Storage are validated
      | Identifier | M_HU_ID.Identifier | M_Product_ID.Identifier | Qty |
      | hu_s_1     | hu_1               | p_1                     | 10  |
    And M_HU are validated:
      | M_HU_ID.Identifier | HUStatus | IsActive | C_BPartner_ID | C_BPartner_Location_ID |
      | hu_1               | A        | Y        | null          | null                   |

    # Create order for Customer A and generate shipment
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.POReference          |
      | o_A        | true    | endcustomer_A            | 2021-04-17  | po_ref_bpartner_clr_A    |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_A       | o_A                   | p_1                     | 10         |
    When the order identified by o_A is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_s_A      | ol_A                      | N             |
    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | s_s_A                            | D            | true                | false       |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier | OPT.DocStatus |
      | s_s_A                            | ship_A                | CO            |

    # After shipping: HU is shipped (E) and belongs to Customer A
    And M_HU are validated:
      | M_HU_ID.Identifier | HUStatus | IsActive | C_BPartner_ID | C_BPartner_Location_ID |
      | hu_1               | E        | N        | endcustomer_A | loc_A                  |

    # Reverse the shipment
    And perform shipment document action
      | M_InOut_ID.Identifier | DocAction |
      | ship_A                | RC        |

    # After reversal: HU is active again, BPartner+Location cleared, storage restored
    And M_HU are validated:
      | M_HU_ID.Identifier | HUStatus | IsActive | C_BPartner_ID | C_BPartner_Location_ID |
      | hu_1               | A        | Y        | null          | null                   |
    And M_HU_Storage are validated
      | Identifier | M_HU_ID.Identifier | M_Product_ID.Identifier | Qty |
      | hu_s_1     | hu_1               | p_1                     | 10  |

    # Now create order for Customer B and generate shipment from the same HU
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.POReference          |
      | o_B        | true    | endcustomer_B            | 2021-04-18  | po_ref_bpartner_clr_B    |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_B       | o_B                   | p_1                     | 10         |
    When the order identified by o_B is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_s_B      | ol_B                      | N             |
    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | s_s_B                            | D            | true                | false       |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier | OPT.DocStatus |
      | s_s_B                            | ship_B                | CO            |

    # After shipping to Customer B: HU is shipped and belongs to Customer B
    Then M_HU are validated:
      | M_HU_ID.Identifier | HUStatus | IsActive | C_BPartner_ID | C_BPartner_Location_ID |
      | hu_1               | E        | N        | endcustomer_B | loc_B                  |

  @from:cucumber
  Scenario: double reversal triple shipment proves fix is idempotent across multiple cycles
    Given metasfresh contains M_Products:
      | Identifier | Name                 |
      | p_1        | hu_bpc_dbl_product   |
    And metasfresh contains M_PricingSystems
      | Identifier | Name           | Value          | OPT.Description        | OPT.IsActive |
      | ps_1       | hu_bpc_dbl_ps  | hu_bpc_dbl_ps  | hu_bpc_dbl_ps_descr    | true         |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name           | OPT.Description | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | pl_1       | ps_1                          | DE                        | EUR                 | hu_bpc_dbl_pl  | null            | true  | false         | 2              | true         |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name            | ValidFrom  |
      | plv_1      | pl_1                      | hu_bpc_dbl_plv  | 2021-04-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_1       | plv_1                             | p_1                     | 10.0     | PCE               | Normal                        |

    # Three customers
    And metasfresh contains C_BPartners:
      | Identifier     | Name                | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier | OPT.InvoiceRule |
      | endcustomer_A  | hu_bpc_dbl_cust_A   | N            | Y              | ps_1                          | D               |
      | endcustomer_B  | hu_bpc_dbl_cust_B   | N            | Y              | ps_1                          | D               |
      | endcustomer_C  | hu_bpc_dbl_cust_C   | N            | Y              | ps_1                          | D               |
    And metasfresh contains C_BPartner_Locations:
      | Identifier | GLN           | C_BPartner_ID.Identifier | OPT.IsShipToDefault | OPT.IsBillToDefault |
      | loc_A      | 0123456789021 | endcustomer_A            | Y                   | Y                   |
      | loc_B      | 0123456789022 | endcustomer_B            | Y                   | Y                   |
      | loc_C      | 0123456789023 | endcustomer_C            | Y                   | Y                   |

    # Create stock via inventory (10 PCE)
    And metasfresh contains M_Inventories:
      | M_Inventory_ID.Identifier | MovementDate | M_Warehouse_ID |
      | inv_1                     | 2021-04-01   | 540008         |
    And metasfresh contains M_InventoriesLines:
      | M_Inventory_ID.Identifier | M_InventoryLine_ID.Identifier | M_Product_ID.Identifier | QtyBook | QtyCount | UOM.X12DE355 |
      | inv_1                     | inv_l_1                       | p_1                     | 0       | 10       | PCE          |
    When the inventory identified by inv_1 is completed
    And after not more than 60s, there are added M_HUs for inventory
      | M_InventoryLine_ID.Identifier | M_HU_ID.Identifier |
      | inv_l_1                       | hu_1               |

    # Verify initial state
    And M_HU are validated:
      | M_HU_ID.Identifier | HUStatus | IsActive | C_BPartner_ID | C_BPartner_Location_ID |
      | hu_1               | A        | Y        | null          | null                   |

    # === Cycle 1: Ship to Customer A ===
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.POReference      |
      | o_A        | true    | endcustomer_A            | 2021-04-17  | po_ref_dbl_A         |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_A       | o_A                   | p_1                     | 10         |
    When the order identified by o_A is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_s_A      | ol_A                      | N             |
    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | s_s_A                            | D            | true                | false       |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier | OPT.DocStatus |
      | s_s_A                            | ship_A                | CO            |

    And M_HU are validated:
      | M_HU_ID.Identifier | HUStatus | IsActive | C_BPartner_ID | C_BPartner_Location_ID |
      | hu_1               | E        | N        | endcustomer_A | loc_A                  |

    # === Reverse shipment to A ===
    And perform shipment document action
      | M_InOut_ID.Identifier | DocAction |
      | ship_A                | RC        |

    And M_HU are validated:
      | M_HU_ID.Identifier | HUStatus | IsActive | C_BPartner_ID | C_BPartner_Location_ID |
      | hu_1               | A        | Y        | null          | null                   |
    And M_HU_Storage are validated
      | Identifier | M_HU_ID.Identifier | M_Product_ID.Identifier | Qty |
      | hu_s_1     | hu_1               | p_1                     | 10  |

    # === Cycle 2: Ship to Customer B ===
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.POReference      |
      | o_B        | true    | endcustomer_B            | 2021-04-18  | po_ref_dbl_B         |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_B       | o_B                   | p_1                     | 10         |
    When the order identified by o_B is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_s_B      | ol_B                      | N             |
    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | s_s_B                            | D            | true                | false       |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier | OPT.DocStatus |
      | s_s_B                            | ship_B                | CO            |

    And M_HU are validated:
      | M_HU_ID.Identifier | HUStatus | IsActive | C_BPartner_ID | C_BPartner_Location_ID |
      | hu_1               | E        | N        | endcustomer_B | loc_B                  |

    # === Reverse shipment to B ===
    And perform shipment document action
      | M_InOut_ID.Identifier | DocAction |
      | ship_B                | RC        |

    And M_HU are validated:
      | M_HU_ID.Identifier | HUStatus | IsActive | C_BPartner_ID | C_BPartner_Location_ID |
      | hu_1               | A        | Y        | null          | null                   |
    And M_HU_Storage are validated
      | Identifier | M_HU_ID.Identifier | M_Product_ID.Identifier | Qty |
      | hu_s_1     | hu_1               | p_1                     | 10  |

    # === Cycle 3: Ship to Customer C ===
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.POReference      |
      | o_C        | true    | endcustomer_C            | 2021-04-19  | po_ref_dbl_C         |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_C       | o_C                   | p_1                     | 10         |
    When the order identified by o_C is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_s_C      | ol_C                      | N             |
    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | s_s_C                            | D            | true                | false       |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier | OPT.DocStatus |
      | s_s_C                            | ship_C                | CO            |

    Then M_HU are validated:
      | M_HU_ID.Identifier | HUStatus | IsActive | C_BPartner_ID | C_BPartner_Location_ID |
      | hu_1               | E        | N        | endcustomer_C | loc_C                  |

  @from:cucumber
  Scenario: LU/TU/VHU hierarchy: on-the-fly picking splits VHU from hierarchy, reversal restores the split VHU
    # On-the-fly picking from an LU/TU/VHU hierarchy splits a new VHU out of the hierarchy.
    # The original hierarchy gets depleted (status D). The split VHU gets shipped (status E).
    # This test validates that reversal properly clears BPartner on the split VHU,
    # and that it can then be re-shipped to a different customer.
    Given set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION

    # Product and pricing
    Given metasfresh contains M_Products:
      | Identifier | Name                    |
      | p_1        | hu_bpc_hier_product     |
    And metasfresh contains M_PricingSystems
      | Identifier | Name            | Value           | OPT.IsActive |
      | ps_1       | hu_bpc_hier_ps  | hu_bpc_hier_ps  | true         |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name            | OPT.Description | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | pl_1       | ps_1                          | DE                        | EUR                 | hu_bpc_hier_pl  | null            | true  | false         | 2              | true         |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name             | ValidFrom  |
      | plv_1      | pl_1                      | hu_bpc_hier_plv  | 2021-04-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_1       | plv_1                             | p_1                     | 10.0     | PCE               | Normal                        |

    # Two customers
    And metasfresh contains C_BPartners:
      | Identifier     | Name                | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier | OPT.InvoiceRule |
      | endcustomer_A  | hu_bpc_hier_cust_A  | N            | Y              | ps_1                          | D               |
      | endcustomer_B  | hu_bpc_hier_cust_B  | N            | Y              | ps_1                          | D               |
    And metasfresh contains C_BPartner_Locations:
      | Identifier | GLN           | C_BPartner_ID.Identifier | OPT.IsShipToDefault | OPT.IsBillToDefault |
      | loc_A      | 0123456789031 | endcustomer_A            | Y                   | Y                   |
      | loc_B      | 0123456789032 | endcustomer_B            | Y                   | Y                   |

    # Packing Instruction hierarchy: LU (holds 10 TUs) -> TU (holds 10 PCE) -> Virtual
    And metasfresh contains M_HU_PI:
      | M_HU_PI_ID.Identifier | Name            |
      | huPackingLU           | huPackingLU     |
      | huPackingTU           | huPackingTU     |
      | huPackingVirtualPI    | No Packing Item |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID.Identifier | M_HU_PI_ID.Identifier | Name             | HU_UnitType | IsCurrent |
      | packingVersionLU              | huPackingLU           | packingVersionLU | LU          | Y         |
      | packingVersionTU              | huPackingTU           | packingVersionTU | TU          | Y         |
      | packingVersionCU              | huPackingVirtualPI    | No Packing Item  | V           | Y         |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID.Identifier | M_HU_PI_Version_ID.Identifier | Qty | ItemType | OPT.Included_HU_PI_ID.Identifier |
      | huPiItemLU                 | packingVersionLU              | 10  | HU       | huPackingTU                      |
      | huPiItemTU                 | packingVersionTU              | 0   | MI       |                                  |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID.Identifier | M_HU_PI_Item_ID.Identifier | M_Product_ID.Identifier | Qty | ValidFrom  |
      | huProductTU                        | huPiItemTU                 | p_1                     | 10  | 2021-01-01 |

    # Create stock via inventory (10 PCE -> VHU)
    And metasfresh contains M_Inventories:
      | M_Inventory_ID.Identifier | MovementDate | M_Warehouse_ID |
      | inv_1                     | 2021-04-01   | 540008         |
    And metasfresh contains M_InventoriesLines:
      | M_Inventory_ID.Identifier | M_InventoryLine_ID.Identifier | M_Product_ID.Identifier | QtyBook | QtyCount | UOM.X12DE355 |
      | inv_1                     | inv_l_1                       | p_1                     | 0       | 10       | PCE          |
    When the inventory identified by inv_1 is completed
    And after not more than 60s, there are added M_HUs for inventory
      | M_InventoryLine_ID.Identifier | M_HU_ID.Identifier |
      | inv_l_1                       | createdCU          |

    # Build hierarchy: CU -> TU -> LU
    And transform CU to new TUs
      | sourceCU.Identifier | cuQty | M_HU_PI_Item_Product_ID.Identifier | OPT.resultedNewTUs.Identifier | OPT.resultedNewCUs.Identifier |
      | createdCU           | 10    | huProductTU                        | createdTU                     | newCreatedCU                  |
    And transform TU to new LUs
      | sourceTU.Identifier | tuQty | M_HU_PI_Item_ID.Identifier | resultedNewLUs.Identifier |
      | createdTU           | 1     | huPiItemLU                 | createdLU                 |

    # Validate LU/TU/VHU hierarchy: all Active, no BPartner, storage=10 at each level
    And M_HU are validated:
      | M_HU_ID.Identifier | HUStatus | IsActive | C_BPartner_ID | C_BPartner_Location_ID |
      | createdLU          | A        | Y        | null          | null                   |
      | createdTU          | A        | Y        | null          | null                   |
      | newCreatedCU       | A        | Y        | null          | null                   |

    # Ship to Customer A (on-the-fly picking splits a VHU from the LU hierarchy)
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.POReference      |
      | o_A        | true    | endcustomer_A            | 2021-04-17  | po_ref_hier_A        |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_A       | o_A                   | p_1                     | 10         |
    When the order identified by o_A is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_s_A      | ol_A                      | N             |
    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | s_s_A                            | D            | true                | false       |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier | OPT.DocStatus |
      | s_s_A                            | ship_A                | CO            |

    # On-the-fly picking extracts newCreatedCU from the LU/TU hierarchy.
    # The VHU (newCreatedCU) is shipped with Customer A's BPartner.
    And M_HU are validated:
      | M_HU_ID.Identifier | HUStatus | IsActive | C_BPartner_ID | C_BPartner_Location_ID |
      | newCreatedCU       | E        | N        | endcustomer_A | loc_A                  |

    # The original LU/TU hierarchy is depleted (stock was extracted by on-the-fly picking)
    And M_HU are validated:
      | M_HU_ID.Identifier | HUStatus |
      | createdLU          | D        |
      | createdTU          | D        |

    # Reverse the shipment
    And perform shipment document action
      | M_InOut_ID.Identifier | DocAction |
      | ship_A                | RC        |

    # After reversal: VHU is restored, BPartner+Location cleared
    And M_HU are validated:
      | M_HU_ID.Identifier | HUStatus | IsActive | C_BPartner_ID | C_BPartner_Location_ID |
      | newCreatedCU       | A        | Y        | null          | null                   |
    And M_HU_Storage are validated
      | Identifier | M_HU_ID.Identifier | M_Product_ID.Identifier | Qty |
      | hu_s_cu    | newCreatedCU       | p_1                     | 10  |

    # Ship the restored VHU to Customer B
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.POReference      |
      | o_B        | true    | endcustomer_B            | 2021-04-18  | po_ref_hier_B        |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_B       | o_B                   | p_1                     | 10         |
    When the order identified by o_B is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_s_B      | ol_B                      | N             |
    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | s_s_B                            | D            | true                | false       |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier | OPT.DocStatus |
      | s_s_B                            | ship_B                | CO            |

    # VHU is now shipped to Customer B
    Then M_HU are validated:
      | M_HU_ID.Identifier | HUStatus | IsActive | C_BPartner_ID | C_BPartner_Location_ID |
      | newCreatedCU       | E        | N        | endcustomer_B | loc_B                  |

  @from:cucumber
  Scenario: partial shipment reversal clears BPartner on the split VHU
    # On-the-fly picking from a 10 PCE HU for a 5 PCE order splits a new 5 PCE VHU.
    # Reversing that partial shipment should clear BPartner on the split VHU,
    # allowing it to be re-shipped to a different customer.
    Given metasfresh contains M_Products:
      | Identifier | Name                    |
      | p_1        | hu_bpc_partial_product  |
    And metasfresh contains M_PricingSystems
      | Identifier | Name               | Value              | OPT.IsActive |
      | ps_1       | hu_bpc_partial_ps  | hu_bpc_partial_ps  | true         |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name               | OPT.Description | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | pl_1       | ps_1                          | DE                        | EUR                 | hu_bpc_partial_pl  | null            | true  | false         | 2              | true         |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name                | ValidFrom  |
      | plv_1      | pl_1                      | hu_bpc_partial_plv  | 2021-04-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_1       | plv_1                             | p_1                     | 10.0     | PCE               | Normal                        |

    # Two customers
    And metasfresh contains C_BPartners:
      | Identifier     | Name                    | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier | OPT.InvoiceRule |
      | endcustomer_A  | hu_bpc_partial_cust_A   | N            | Y              | ps_1                          | D               |
      | endcustomer_B  | hu_bpc_partial_cust_B   | N            | Y              | ps_1                          | D               |
    And metasfresh contains C_BPartner_Locations:
      | Identifier | GLN           | C_BPartner_ID.Identifier | OPT.IsShipToDefault | OPT.IsBillToDefault |
      | loc_A      | 0123456789041 | endcustomer_A            | Y                   | Y                   |
      | loc_B      | 0123456789042 | endcustomer_B            | Y                   | Y                   |

    # Create stock: 10 PCE in one VHU
    And metasfresh contains M_Inventories:
      | M_Inventory_ID.Identifier | MovementDate | M_Warehouse_ID |
      | inv_1                     | 2021-04-01   | 540008         |
    And metasfresh contains M_InventoriesLines:
      | M_Inventory_ID.Identifier | M_InventoryLine_ID.Identifier | M_Product_ID.Identifier | QtyBook | QtyCount | UOM.X12DE355 |
      | inv_1                     | inv_l_1                       | p_1                     | 0       | 10       | PCE          |
    When the inventory identified by inv_1 is completed
    And after not more than 60s, there are added M_HUs for inventory
      | M_InventoryLine_ID.Identifier | M_HU_ID.Identifier |
      | inv_l_1                       | hu_1               |

    And M_HU_Storage are validated
      | Identifier | M_HU_ID.Identifier | M_Product_ID.Identifier | Qty |
      | hu_s_1     | hu_1               | p_1                     | 10  |
    And M_HU are validated:
      | M_HU_ID.Identifier | HUStatus | IsActive | C_BPartner_ID | C_BPartner_Location_ID |
      | hu_1               | A        | Y        | null          | null                   |

    # Order only 5 PCE for Customer A (partial: less than full HU qty)
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.POReference          |
      | o_A        | true    | endcustomer_A            | 2021-04-17  | po_ref_partial_A         |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_A       | o_A                   | p_1                     | 5          |
    When the order identified by o_A is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_s_A      | ol_A                      | N             |
    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | s_s_A                            | D            | true                | false       |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier | OPT.DocStatus |
      | s_s_A                            | ship_A                | CO            |

    # On-the-fly picking splits a new VHU with 5 PCE from the source HU.
    # Locate the split VHU via QtyPicked records.
    And M_ShipmentSchedule_QtyPicked records for M_ShipmentSchedule s_s_A can be located in specified order
      | M_ShipmentSchedule_QtyPicked_ID.Identifier |
      | qp_A                                       |
    And load M_HU as splitVHU_A from M_ShipmentSchedule_QtyPicked identified by qp_A

    # The split VHU is shipped with Customer A's BPartner
    And M_HU are validated:
      | M_HU_ID.Identifier | HUStatus | IsActive | C_BPartner_ID | C_BPartner_Location_ID |
      | splitVHU_A         | E        | N        | endcustomer_A | loc_A                  |
    And M_HU_Storage are validated
      | Identifier | M_HU_ID.Identifier | M_Product_ID.Identifier | Qty |
      | hu_s_split | splitVHU_A         | p_1                     | 5   |

    # Reverse the partial shipment
    And perform shipment document action
      | M_InOut_ID.Identifier | DocAction |
      | ship_A                | RC        |

    # After reversal: split VHU restored, BPartner cleared
    And M_HU are validated:
      | M_HU_ID.Identifier | HUStatus | IsActive | C_BPartner_ID | C_BPartner_Location_ID |
      | splitVHU_A         | A        | Y        | null          | null                   |

    # Ship 5 PCE to Customer B — on-the-fly picking can pick the restored split VHU
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.POReference          |
      | o_B        | true    | endcustomer_B            | 2021-04-18  | po_ref_partial_B         |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_B       | o_B                   | p_1                     | 5          |
    When the order identified by o_B is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_s_B      | ol_B                      | N             |
    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | s_s_B                            | D            | true                | false       |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier | OPT.DocStatus |
      | s_s_B                            | ship_B                | CO            |

    # The same split VHU (restored after reversal) is re-picked for Customer B
    Then M_HU are validated:
      | M_HU_ID.Identifier | HUStatus | IsActive | C_BPartner_ID | C_BPartner_Location_ID |
      | splitVHU_A         | E        | N        | endcustomer_B | loc_B                  |

  @from:cucumber
  Scenario: picking cancellation clears BPartner on HU (Bug 2 fix)
    # When an HU is picked for a shipment schedule, BPartner is set on the HU.
    # Cancelling/unprocessing the picking should clear BPartner, allowing re-pick for another customer.
    # This tests the fix in HUShipmentScheduleBL.deleteByTopLevelHUsAndShipmentScheduleId().
    Given metasfresh contains M_Products:
      | Identifier | Name                    |
      | p_1        | hu_bpc_unpick_product   |
    And metasfresh contains M_PricingSystems
      | Identifier | Name               | Value              | OPT.IsActive |
      | ps_1       | hu_bpc_unpick_ps   | hu_bpc_unpick_ps   | true         |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name               | OPT.Description | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | pl_1       | ps_1                          | DE                        | EUR                 | hu_bpc_unpick_pl   | null            | true  | false         | 2              | true         |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name                | ValidFrom  |
      | plv_1      | pl_1                      | hu_bpc_unpick_plv   | 2021-04-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_1       | plv_1                             | p_1                     | 10.0     | PCE               | Normal                        |

    # Two customers
    And metasfresh contains C_BPartners:
      | Identifier     | Name                    | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier | OPT.InvoiceRule |
      | endcustomer_A  | hu_bpc_unpick_cust_A    | N            | Y              | ps_1                          | D               |
      | endcustomer_B  | hu_bpc_unpick_cust_B    | N            | Y              | ps_1                          | D               |
    And metasfresh contains C_BPartner_Locations:
      | Identifier | GLN           | C_BPartner_ID.Identifier | OPT.IsShipToDefault | OPT.IsBillToDefault |
      | loc_A      | 0123456789051 | endcustomer_A            | Y                   | Y                   |
      | loc_B      | 0123456789052 | endcustomer_B            | Y                   | Y                   |

    # Create stock: 10 PCE
    And metasfresh contains M_Inventories:
      | M_Inventory_ID.Identifier | MovementDate | M_Warehouse_ID |
      | inv_1                     | 2021-04-01   | 540008         |
    And metasfresh contains M_InventoriesLines:
      | M_Inventory_ID.Identifier | M_InventoryLine_ID.Identifier | M_Product_ID.Identifier | QtyBook | QtyCount | UOM.X12DE355 |
      | inv_1                     | inv_l_1                       | p_1                     | 0       | 10       | PCE          |
    When the inventory identified by inv_1 is completed
    And after not more than 60s, there are added M_HUs for inventory
      | M_InventoryLine_ID.Identifier | M_HU_ID.Identifier |
      | inv_l_1                       | hu_1               |

    And M_HU are validated:
      | M_HU_ID.Identifier | HUStatus | IsActive | C_BPartner_ID | C_BPartner_Location_ID |
      | hu_1               | A        | Y        | null          | null                   |

    # Create order for Customer A
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.POReference          |
      | o_A        | true    | endcustomer_A            | 2021-04-17  | po_ref_unpick_A          |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_A       | o_A                   | p_1                     | 10         |
    When the order identified by o_A is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_s_A      | ol_A                      | N             |

    # Pick the HU for Customer A's shipment schedule (sets BPartner on HU)
    And the following qty is picked
      | M_ShipmentSchedule_ID.Identifier | M_HU_ID.Identifier | QtyPicked |
      | s_s_A                            | hu_1               | 10        |

    # After picking: HU should have BPartner from shipment schedule
    And M_HU are validated:
      | M_HU_ID.Identifier | HUStatus | IsActive | C_BPartner_ID | C_BPartner_Location_ID |
      | hu_1               | S        | Y        | endcustomer_A | loc_A                  |

    # Cancel/unprocess picking — Bug 2 fix should clear BPartner
    And unprocess picking candidates for shipment schedule
      | M_ShipmentSchedule_ID.Identifier |
      | s_s_A                            |

    # After unprocessing: BPartner should be cleared
    And M_HU are validated:
      | M_HU_ID.Identifier | HUStatus | IsActive | C_BPartner_ID | C_BPartner_Location_ID |
      | hu_1               | A        | Y        | null          | null                   |

    # Create order for Customer B
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.POReference          |
      | o_B        | true    | endcustomer_B            | 2021-04-18  | po_ref_unpick_B          |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_B       | o_B                   | p_1                     | 10         |
    When the order identified by o_B is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_s_B      | ol_B                      | N             |

    # Pick the same HU for Customer B's shipment schedule
    And the following qty is picked
      | M_ShipmentSchedule_ID.Identifier | M_HU_ID.Identifier | QtyPicked |
      | s_s_B                            | hu_1               | 10        |

    # After re-picking: HU should now belong to Customer B
    Then M_HU are validated:
      | M_HU_ID.Identifier | HUStatus | IsActive | C_BPartner_ID | C_BPartner_Location_ID |
      | hu_1               | S        | Y        | endcustomer_B | loc_B                  |
