@from:cucumber
@allure.label.epic:E0360_Transport_Extralogistik
@allure.label.feature:F29080_Transport_Order
@ghActions:run_on_executor7
Feature: shipment to transportation order

  ## F29080: Transport Order — adding a shipment (with or without shipped HUs) to a Transport Order,
  ## and clearing the link when the Transport Order's package line is removed (delete or void).

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2023-06-01T10:00:00+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And AD_Scheduler for classname 'de.metas.material.cockpit.stock.process.MD_Stock_Update_From_M_HUs' is disabled
    And load M_Warehouse:
      | M_Warehouse_ID.Identifier | Value        |
      | wh_std                    | StdWarehouse |
    And load M_Shipper:
      | Identifier | Name |
      | shipper_1  | Dhl  |
    And metasfresh contains C_BPartners:
      | Identifier | Name             | OPT.IsCustomer |
      | cust_1     | to_shipment_cust | Y              |
    And metasfresh contains C_BPartner_Locations:
      | Identifier | GLN           | C_BPartner_ID.Identifier | OPT.IsShipToDefault | OPT.IsBillToDefault |
      | loc_1      | 0123456789031 | cust_1                   | Y                   | Y                   |
    And metasfresh contains M_Products:
      | Identifier | Name             |
      | p_noHu     | to_noPickProduct |

  @from:cucumber
  Scenario: TC1 - adding a shipment with no shipped HUs to a Transport Order creates one package line
    Given metasfresh contains Transport Order
      | Identifier | M_Shipper_ID | Shipper_BPartner_ID | Shipper_Location_ID | TransportDirection |
      | to_1       | shipper_1    | cust_1              | loc_1               | Outgoing           |
    And metasfresh contains M_InOut:
      | M_InOut_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | IsSOTrx | DeliveryRule | DeliveryViaRule | FreightCostRule | M_Warehouse_ID.Identifier | MovementDate | MovementType | PriorityRule | OPT.DocBaseType | OPT.DocSubType |
      | ship_1                | cust_1                   | loc_1                             | true    | F            | S               | I               | wh_std                    | 2023-06-01   | C-           | 5            | MMS             | MS             |
    And metasfresh contains M_InOutLine without HU:
      | M_InOut_ID | M_Product_ID | MovementQty |
      | ship_1     | p_noHu       | 1           |
    When the shipment identified by ship_1 is completed
    And M_ShipperTransportation_AddShipments is invoked for shipment ship_1 and transportation order: to_1
    Then metasfresh contains exactly 1 M_ShippingPackages for transportation order: to_1
    And validate M_ShipperTransportation_ID for shipment ship_1 is set

  @from:cucumber
  Scenario: TC2 - adding a shipment with shipped HUs still creates one package per HU (regression guard)
    Given metasfresh contains M_Products:
      | Identifier | Name         |
      | p_hu       | to_huProduct |
    And metasfresh contains M_PricingSystems
      | Identifier | Name     | Value    | OPT.IsActive |
      | ps_2       | to_hu_ps | to_hu_ps | true         |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name     | SOTrx | IsTaxIncluded | PricePrecision |
      | pl_2       | ps_2                          | DE                        | EUR                 | to_hu_pl | true  | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name      | ValidFrom  |
      | plv_2      | pl_2                      | to_hu_plv | 2023-05-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_2       | plv_2                             | p_hu                    | 10.0     | PCE               | Normal                        |
    And metasfresh contains C_BPartners:
      | Identifier | Name       | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | cust_2     | to_hu_cust | Y              | ps_2                          |
    And metasfresh contains C_BPartner_Locations:
      | Identifier | GLN           | C_BPartner_ID.Identifier | OPT.IsShipToDefault | OPT.IsBillToDefault |
      | loc_2      | 0123456789032 | cust_2                   | Y                   | Y                   |
    And metasfresh contains M_Inventories:
      | M_Inventory_ID.Identifier | MovementDate | M_Warehouse_ID |
      | inv_2                     | 2023-05-02   | 540008         |
    And metasfresh contains M_InventoriesLines:
      | M_Inventory_ID.Identifier | M_InventoryLine_ID.Identifier | M_Product_ID.Identifier | QtyBook | QtyCount | UOM.X12DE355 |
      | inv_2                     | inv_l_2                       | p_hu                    | 0       | 5        | PCE          |
    And the inventory identified by inv_2 is completed
    And after not more than 60s, there are added M_HUs for inventory
      | M_InventoryLine_ID.Identifier | M_HU_ID.Identifier |
      | inv_l_2                       | hu_2               |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.POReference |
      | o_2        | true    | cust_2                   | 2023-06-01  | to_hu_po        |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_2       | o_2                   | p_hu                    | 5          |
    And metasfresh contains Transport Order
      | Identifier | M_Shipper_ID | Shipper_BPartner_ID | Shipper_Location_ID | TransportDirection |
      | to_2       | shipper_1    | cust_2              | loc_2               | Outgoing           |
    When the order identified by o_2 is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_s_2      | ol_2                      | N             |
    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | s_s_2                            | D            | true                | false       |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier | OPT.DocStatus |
      | s_s_2                            | ship_2                | CO            |
    And M_ShipperTransportation_AddShipments is invoked for shipment ship_2 and transportation order: to_2
    Then metasfresh contains exactly 1 M_ShippingPackages for transportation order: to_2
    And validate M_ShipperTransportation_ID for shipment ship_2 is set

  @from:cucumber
  Scenario: TC3a - deleting a Transport Order's package line directly unlinks the shipment
    Given metasfresh contains Transport Order
      | Identifier | M_Shipper_ID | Shipper_BPartner_ID | Shipper_Location_ID | TransportDirection |
      | to_3a      | shipper_1    | cust_1              | loc_1               | Outgoing           |
    And metasfresh contains M_InOut:
      | M_InOut_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | IsSOTrx | DeliveryRule | DeliveryViaRule | FreightCostRule | M_Warehouse_ID.Identifier | MovementDate | MovementType | PriorityRule | OPT.DocBaseType | OPT.DocSubType |
      | ship_3a               | cust_1                   | loc_1                             | true    | F            | S               | I               | wh_std                    | 2023-06-01   | C-           | 5            | MMS             | MS             |
    And metasfresh contains M_InOutLine without HU:
      | M_InOut_ID | M_Product_ID | MovementQty |
      | ship_3a    | p_noHu       | 1           |
    When the shipment identified by ship_3a is completed
    And M_ShipperTransportation_AddShipments is invoked for shipment ship_3a and transportation order: to_3a
    Then metasfresh contains exactly 1 M_ShippingPackages for transportation order: to_3a
    And validate M_ShipperTransportation_ID for shipment ship_3a is set

    When delete the M_ShippingPackage for shipment ship_3a and transportation order: to_3a
    Then validate M_ShipperTransportation_ID for shipment ship_3a is null

    # the shipment is a candidate for the add-flow again
    When M_ShipperTransportation_AddShipments is invoked for shipment ship_3a and transportation order: to_3a
    Then metasfresh contains exactly 1 M_ShippingPackages for transportation order: to_3a
    And validate M_ShipperTransportation_ID for shipment ship_3a is set

  @from:cucumber
  Scenario: TC3b - deleting the Transport Order itself cascades to its package line and unlinks the shipment
    Given metasfresh contains Transport Order
      | Identifier | M_Shipper_ID | Shipper_BPartner_ID | Shipper_Location_ID | TransportDirection |
      | to_3b      | shipper_1    | cust_1              | loc_1               | Outgoing           |
    And metasfresh contains M_InOut:
      | M_InOut_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | IsSOTrx | DeliveryRule | DeliveryViaRule | FreightCostRule | M_Warehouse_ID.Identifier | MovementDate | MovementType | PriorityRule | OPT.DocBaseType | OPT.DocSubType |
      | ship_3b               | cust_1                   | loc_1                             | true    | F            | S               | I               | wh_std                    | 2023-06-01   | C-           | 5            | MMS             | MS             |
    And metasfresh contains M_InOutLine without HU:
      | M_InOut_ID | M_Product_ID | MovementQty |
      | ship_3b    | p_noHu       | 1           |
    When the shipment identified by ship_3b is completed
    And M_ShipperTransportation_AddShipments is invoked for shipment ship_3b and transportation order: to_3b
    Then metasfresh contains exactly 1 M_ShippingPackages for transportation order: to_3b
    And validate M_ShipperTransportation_ID for shipment ship_3b is set

    When delete Transport Order identified by to_3b
    Then validate M_ShipperTransportation_ID for shipment ship_3b is null

  @from:cucumber
  Scenario: TC4 - voiding a Transport Order unlinks its shipments
    Given metasfresh contains Transport Order
      | Identifier | M_Shipper_ID | Shipper_BPartner_ID | Shipper_Location_ID | TransportDirection |
      | to_4       | shipper_1    | cust_1              | loc_1               | Outgoing           |
    And metasfresh contains M_InOut:
      | M_InOut_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | IsSOTrx | DeliveryRule | DeliveryViaRule | FreightCostRule | M_Warehouse_ID.Identifier | MovementDate | MovementType | PriorityRule | OPT.DocBaseType | OPT.DocSubType |
      | ship_4                | cust_1                   | loc_1                             | true    | F            | S               | I               | wh_std                    | 2023-06-01   | C-           | 5            | MMS             | MS             |
    And metasfresh contains M_InOutLine without HU:
      | M_InOut_ID | M_Product_ID | MovementQty |
      | ship_4     | p_noHu       | 1           |
    When the shipment identified by ship_4 is completed
    And M_ShipperTransportation_AddShipments is invoked for shipment ship_4 and transportation order: to_4
    Then metasfresh contains exactly 1 M_ShippingPackages for transportation order: to_4
    And validate M_ShipperTransportation_ID for shipment ship_4 is set

    # to_4 is voided while still Drafted (NOT completed first) - MMShipperTransportation.voidIt()'s
    # line-deactivation branch only runs for Drafted/Invalid/InProgress/Approved/NotApproved, not Completed.
    When the M_ShipperTransportation identified by to_4 is voided
    Then validate M_ShipperTransportation_ID for shipment ship_4 is null
