@from:cucumber
@ghActions:run_on_executor7
@allure.label.epic:E0100_Sales
@allure.label.feature:F00150
Feature: reversed shipment

  ## F00150: Sales Shipment

  Background:
    Given infrastructure and metasfresh are running
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And AD_Scheduler for classname 'de.metas.material.cockpit.stock.process.MD_Stock_Update_From_M_HUs' is disabled

  @from:cucumber
  @Id:S29561_10
  Scenario: we can create and complete a shipment, then reserve/correct it and check the hu's status
    Given metasfresh has date and time 2021-12-16T13:30:13+01:00[Europe/Berlin]
    And metasfresh contains M_Products:
      | Identifier | Name       |
      | p_1        | hu_product |
    And metasfresh contains M_PricingSystems
      | Identifier | Name                   | Value                   | OPT.Description               | OPT.IsActive |
      | ps_1       | hu_pricing_system_name | hu_pricing_system_value | hu_pricing_system_description | true         |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name               | OPT.Description | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | pl_1       | ps_1                          | DE                        | EUR                 | hu_price_list_name | null            | true  | false         | 2              | true         |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name              | ValidFrom  |
      | plv_1      | pl_1                      | hu_salesOrder-PLV | 2021-04-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_1       | plv_1                             | p_1                     | 10.0     | PCE               | Normal                        |
    And metasfresh contains C_BPartners:
      | Identifier    | Name           | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier | OPT.InvoiceRule |
      | endcustomer_1 | hu_endcustomer | N            | Y              | ps_1                          | D               |
    And metasfresh contains C_BPartner_Locations:
      | Identifier | GLN           | C_BPartner_ID.Identifier | OPT.IsShipToDefault | OPT.IsBillToDefault |
      | l_1        | 0123456789011 | endcustomer_1            | Y                   | Y                   |
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
    And after not more than 60 seconds metasfresh has MD_Stock data
      | M_Product_ID.Identifier | QtyOnHand |
      | p_1                     | 10        |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.POReference |
      | o_1        | true    | endcustomer_1            | 2021-04-17  | po_ref_mock     |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_1       | o_1                   | p_1                     | 10         |
    When the order identified by o_1 is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_s_1      | ol_1                      | N             |
    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | s_s_1                            | D            | true                | false       |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier | OPT.DocStatus |
      | s_s_1                            | s_1                   | CO            |
    And M_HU are validated:
      | M_HU_ID.Identifier | HUStatus | IsActive |
      | hu_1               | E        | N        |
    And after not more than 60 seconds metasfresh has MD_Stock data
      | M_Product_ID.Identifier | QtyOnHand |
      | p_1                     | 0         |
    # === Reverse and re-ship ===
    And perform shipment document action
      | M_InOut_ID.Identifier | DocAction |
      | s_1                   | RC        |
    Then M_HU are validated:
      | M_HU_ID.Identifier | HUStatus | IsActive |
      | hu_1               | A        | Y        |
    And after not more than 60 seconds metasfresh has MD_Stock data
      | M_Product_ID.Identifier | QtyOnHand |
      | p_1                     | 10        |
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_s_1      | ol_1                      | N             |
    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | s_s_1                            | D            | true                | false       |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier | OPT.DocStatus |
      | s_s_1                            | s_2                   | CO            |
    And M_HU are validated:
      | M_HU_ID.Identifier | HUStatus | IsActive |
      | hu_1               | E        | N        |
    And after not more than 60 seconds metasfresh has MD_Stock data
      | M_Product_ID.Identifier | QtyOnHand |
      | p_1                     | 0         |

  @from:cucumber
  @Id:S29561_20
  Scenario: aggregate-HU shipment can be reversed and re-shipped without duplicate QtyPicked rows
  ## me03#29561: when a shipment whose allocation is an aggregate HU (one VHU representing N TUs)
  ## is reversed, the HU-trx snapshot replay emits N trx lines through the same VHU. Without
  ## consolidation in ShipmentScheduleHUTrxListener, that produces N identical
  ## M_ShipmentSchedule_QtyPicked rows that collide on the M_ShipmentSchedule_QtyPicked_UI partial
  ## unique index when the next shipment is generated. This scenario fails on the second
  ## 'generate shipments' step without the fix.
    Given metasfresh has date and time 2024-06-03T13:30:13+02:00[Europe/Berlin]
    And metasfresh contains M_Products:
      | Identifier | Name           |
      | p_agg      | hu_product_agg |
    # Set up a self-contained DE→DE 0% tax. The customer-preloaded sp80 DB only
    # has CH-domestic and DE→CH taxes for active categories with InternalName, so we
    # can't rely on a built-in tax for our DE-customer scenario.
    And metasfresh contains C_TaxCategory
      | Identifier  | Name           | InternalName |
      | tcAggS29561 | tc_agg_S29561  | aggS29561    |
    And metasfresh contains C_Tax
      | Identifier | Name        | C_TaxCategory_ID | Rate | C_Country_ID.CountryCode | To_Country_ID.CountryCode | ValidFrom  |
      | taxAgg     | tax_agg_de  | tcAggS29561      | 0    | DE                       | DE                        | 2024-01-01 |
    And metasfresh contains M_PricingSystems
      | Identifier | Name                       | Value                       | OPT.IsActive |
      | ps_agg     | hu_pricing_system_name_agg | hu_pricing_system_value_agg | true         |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name                   | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | pl_agg     | ps_agg                        | DE                        | EUR                 | hu_price_list_name_agg | true  | false         | 2              | true         |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name                  | ValidFrom  |
      | plv_agg    | pl_agg                    | hu_salesOrder-PLV-agg | 2024-01-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_agg     | plv_agg                           | p_agg                   | 10.0     | PCE               | aggS29561                     |
    And metasfresh contains C_BPartners:
      | Identifier      | Name               | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier | OPT.InvoiceRule |
      | endcustomer_agg | hu_endcustomer_agg | N            | Y              | ps_agg                        | D               |
    And metasfresh contains C_BPartner_Locations:
      | Identifier | GLN           | C_BPartner_ID.Identifier | OPT.IsShipToDefault | OPT.IsBillToDefault |
      | l_agg      | 0123456789012 | endcustomer_agg          | Y                   | Y                   |
    # HU Packing Instruction hierarchy: LU holds up to 10 TU, each TU holds 2 PCE
    And metasfresh contains M_HU_PI:
      | M_HU_PI_ID |
      | pi_LU_agg  |
      | pi_TU_agg  |
      | pi_VHU_agg |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID | M_HU_PI_ID | HU_UnitType | IsCurrent |
      | piv_LU_agg         | pi_LU_agg  | LU          | Y         |
      | piv_TU_agg         | pi_TU_agg  | TU          | Y         |
      | piv_VHU_agg        | pi_VHU_agg | V           | Y         |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID | M_HU_PI_Version_ID | Qty | ItemType | Included_HU_PI_ID |
      | pii_LU_agg      | piv_LU_agg         | 10  | HU       | pi_TU_agg         |
      | pii_TU_agg      | piv_TU_agg         | 0   | PM       |                   |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID | M_HU_PI_Item_ID | M_Product_ID.Identifier | Qty | ValidFrom  |
      | pip_agg                 | pii_TU_agg      | p_agg                   | 2   | 2024-01-01 |
    # Order 12 PCE → 6 TUs of 2 PCE each → on-the-fly picking creates an aggregate HU
    # (1 VHU record representing 6 logical TUs). The reverse replays 6 trx lines through that VHU.
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.DateAcct | OPT.POReference | M_Warehouse_ID |
      | o_agg      | true    | endcustomer_agg          | 2024-06-03  | 2024-06-03   | po_ref_agg      | 540008         |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | M_HU_PI_Item_Product_ID.Identifier |
      | ol_agg     | o_agg                 | p_agg                   | 12         | pip_agg                            |
    When the order identified by o_agg is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_s_agg    | ol_agg                    | N             |
    # === First shipment: aggregate HU created on-the-fly ===
    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | s_s_agg                          | D            | true                | false       |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier | OPT.DocStatus |
      | s_s_agg                          | s_agg_1               | CO            |
    # === Reverse shipment ===
    And perform shipment document action
      | M_InOut_ID.Identifier | DocAction |
      | s_agg_1               | RC        |
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_s_agg    | ol_agg                    | N             |
    # === Re-ship: this step fails without the fix ===
    # The reversal replayed N=6 trx lines through the aggregate VHU. Without
    # ShipmentScheduleHUTrxListener consolidating them, there are N duplicate
    # QtyPicked rows, and 'generate shipments' fails with a partial unique
    # index violation on M_ShipmentSchedule_QtyPicked_UI when binding M_InOutLine_ID.
    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | s_s_agg                          | D            | true                | false       |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier | OPT.DocStatus |
      | s_s_agg                          | s_agg_2               | CO            |