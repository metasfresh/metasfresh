@from:cucumber
@topic:shipmentScheduleExport
Feature: Shipment schedule updating
  Verifies that M_ShipmentSchedule is properly updated on various cases

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2023-06-01T13:30:13+01:00[Europe/Berlin]
    And deactivate all M_ShipmentSchedule records
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    # we don't care about the export, and don't want it to interject in any way
    And set sys config int value 999999 for sys config de.metas.inoutcandidate.M_ShipmentSchedule.canBeExportedAfterSeconds

    # set up masterdata
    And metasfresh contains M_Products:
<<<<<<< HEAD
      | Identifier    | Name          | OPT.Description   |
      | product_S0271 | product_S0271 | dummy description |
    And metasfresh contains M_PricingSystems
      | Identifier | Name                 | Value                |
      | ps_S0271   | pricing_system_S0271 | pricing_system_S0271 |
    And metasfresh contains M_PriceLists
      | Identifier  | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name                | SOTrx | IsTaxIncluded | PricePrecision |
      | pl_so_S0271 | ps_S0271                      | DE                        | EUR                 | price_list_so_S0271 | true  | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier   | M_PriceList_ID.Identifier | Name         | ValidFrom  |
      | plv_so_S0271 | pl_so_S0271               | plv_so_S0271 | 2022-02-01 |
    And metasfresh contains M_ProductPrices
      | Identifier       | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_TaxCategory_ID.InternalName | C_UOM_ID.X12DE355 |
      | pp_product_S0271 | plv_so_S0271                      | product_S0271           | 10.0     | Normal                        | PCE               |

    # with this bpartner, we will create the orders with DeliveryRule=Availability
    And metasfresh contains C_BPartners:
      | Identifier        | Name              | M_PricingSystem_ID.Identifier | OPT.IsCustomer | OPT.CompanyName       | OPT.AD_Language | OPT.DeliveryRule |
      | customer_so_S0271 | customer_so_S0271 | ps_S0271                      | Y              | customer_so_S0271_cmp | de_DE           | A                |
    And metasfresh contains C_Location:
      | C_Location_ID.Identifier | CountryCode | OPT.Address1 | OPT.Postal | OPT.City |
      | location_S0271           | DE          | addr ship 10 | 123        | shipCity |
    And metasfresh contains C_BPartner_Locations:
      | Identifier           | GLN                 | C_BPartner_ID.Identifier | OPT.C_Location_ID.Identifier | OPT.IsShipTo | OPT.IsBillTo | OPT.EMail         | OPT.BPartnerName | OPT.Phone |
      | shipBPLocation_S0271 | ship_location_S0271 | customer_so_S0271        | location_S0271               | true         | true         | ship@location.com | shipBPName       | 321       |
=======
      | Identifier   | Name         | OPT.Description   |
      | product_0601 | product_0601 | dummy description |
    And metasfresh contains M_PricingSystems
      | Identifier | Name                | Value               |
      | ps_0601    | pricing_system_0601 | pricing_system_0601 |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name               | SOTrx | IsTaxIncluded | PricePrecision |
      | pl_so_0601 | ps_0601                       | DE                        | EUR                 | price_list_so_0601 | true  | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier  | M_PriceList_ID.Identifier | Name        | ValidFrom  |
      | plv_so_0601 | pl_so_0601                | plv_so_0601 | 2022-02-01 |
    And metasfresh contains M_ProductPrices
      | Identifier      | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_TaxCategory_ID.InternalName | C_UOM_ID.X12DE355 |
      | pp_product_0601 | plv_so_0601                       | product_0601            | 10.0     | Normal                        | PCE               |

    # with this bpartner, we will create the orders with DeliveryRule=Availability
    And metasfresh contains C_BPartners:
      | Identifier       | Name             | M_PricingSystem_ID.Identifier | OPT.IsCustomer | OPT.CompanyName      | OPT.AD_Language | OPT.DeliveryRule |
      | customer_so_0601 | customer_so_0601 | ps_0601                       | Y              | customer_so_0601_cmp | de_DE           | A                |
    And metasfresh contains C_Location:
      | C_Location_ID.Identifier | CountryCode | OPT.Address1 | OPT.Postal | OPT.City |
      | location_0601            | DE          | addr ship 10 | 123        | shipCity |
    And metasfresh contains C_BPartner_Locations:
      | Identifier          | GLN                 | C_BPartner_ID.Identifier | OPT.C_Location_ID.Identifier | OPT.IsShipTo | OPT.IsBillTo | OPT.EMail         | OPT.BPartnerName | OPT.Phone |
      | shipBPLocation_0601 | ship_location_S0271 | customer_so_0601         | location_0601                | true         | true         | ship@location.com | shipBPName       | 321       |
>>>>>>> a22e5b8f990 (Update affected M_ShipmentSchedules after stock change (#15461))


  @Id:S0271_010
  Scenario: Create Inventory

<<<<<<< HEAD
    Given metasfresh contains M_Inventories:
      | Identifier | M_Warehouse_ID | MovementDate |
      | i_1_S0271  | 540008         | 2021-04-16   |
    And metasfresh contains M_InventoriesLines:
      | Identifier | M_Inventory_ID.Identifier | M_Product_ID.Identifier | UOM.X12DE355 | QtyCount | QtyBook |
      | il_1_S0271 | i_1_S0271                 | product_S0271           | PCE          | 5        | 0       |
    And the inventory identified by i_1_S0271 is completed

    And after not more than 60s, there are added M_HUs for inventory
      | M_InventoryLine_ID.Identifier | M_HU_ID.Identifier |
      | il_1_S0271                    | hu_S0271           |
    
    # create the orders (with DeliveryRule=Availability and verify the initial state of their shipment schedules
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered |
      | o_1_S0271  | true    | customer_so_S0271        | 2023-05-31  |
      | o_2_S0271  | true    | customer_so_S0271        | 2023-06-01  |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_1_S0271 | o_1_S0271             | product_S0271           | 10         |
      | ol_2_S0271 | o_2_S0271             | product_S0271           | 10         |
    And the order identified by o_1_S0271 is completed
    And the order identified by o_2_S0271 is completed

    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier       | C_OrderLine_ID.Identifier | IsToRecompute |
      | schedule_1_S0271 | ol_1_S0271                | N             |
      | schedule_2_S0271 | ol_2_S0271                | N             |
    # initially expect the first sched to get our stock of 5 and the 2nd sched to get nothing
    And after not more than 60s, validate shipment schedules:
      | M_ShipmentSchedule_ID.Identifier | OPT.QtyToDeliver | OPT.QtyDelivered | OPT.QtyOrdered | OPT.QtyOnHand | OPT.Processed | OPT.DeliveryRule |
      | schedule_1_S0271                 | 5                | 0                | 10             | 5             | false         | A                |
      | schedule_2_S0271                 | 0                | 0                | 10             | 0             | false         | A                |
=======
    And metasfresh contains M_Inventories:
      | Identifier | M_Warehouse_ID | MovementDate |
      | i_1_0601   | 540008         | 2021-04-16   |
    And metasfresh contains M_InventoriesLines:
      | Identifier | M_Inventory_ID.Identifier | M_Product_ID.Identifier | UOM.X12DE355 | QtyCount | QtyBook |
      | il_1_0601  | i_1_0601                  | product_0601            | PCE          | 5        | 0       |
    When the inventory identified by i_1_0601 is completed

    # create the orders (with DeliveryRule=Availability and verify the initial state of their shipment schedules
    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered |
      | o_1_0601   | true    | customer_so_0601         | 2023-05-31  |
      | o_2_0601   | true    | customer_so_0601         | 2023-06-01  |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_1_0601  | o_1_0601              | product_0601            | 10         |
      | ol_2_0601  | o_2_0601              | product_0601            | 10         |
    And the order identified by o_1_0601 is completed
    And the order identified by o_2_0601 is completed

    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier      | C_OrderLine_ID.Identifier | IsToRecompute |
      | schedule_1_0601 | ol_1_0601                 | N             |
      | schedule_2_0601 | ol_2_0601                 | N             |
    # initially expect the first sched to get our stock of 5 and the 2nd sched to get nothing
    And after not more than 60s, validate shipment schedules:
      | M_ShipmentSchedule_ID.Identifier | OPT.QtyToDeliver | OPT.QtyDelivered | OPT.QtyOrdered | OPT.QtyOnHand | OPT.Processed | OPT.DeliveryRule |
      | schedule_1_0601                  | 5                | 0                | 10             | 5             | false         | A                |
      | schedule_2_0601                  | 0                | 0                | 10             | 0             | false         | A                |
>>>>>>> a22e5b8f990 (Update affected M_ShipmentSchedules after stock change (#15461))

    # Prepare the inventory document
    And metasfresh contains M_Inventories:
      | Identifier | M_Warehouse_ID | MovementDate |
<<<<<<< HEAD
      | i_2_S0271  | 540008         | 2021-04-16   |
    And metasfresh contains M_InventoriesLines:
      | Identifier | M_Inventory_ID.Identifier | M_Product_ID.Identifier | UOM.X12DE355 | QtyCount | QtyBook |
      | il_2_S0271 | i_2_S0271                 | product_S0271           | PCE          | 11       | 0       |

    # now change the stock from 5 to 16
    When the inventory identified by i_2_S0271 is completed

    Then after not more than 60s, validate shipment schedules:
      | M_ShipmentSchedule_ID.Identifier | OPT.QtyToDeliver | OPT.QtyDelivered | OPT.QtyOrdered | OPT.QtyOnHand | OPT.Processed |
      | schedule_1_S0271                 | 10               | 0                | 10             | 16            | false         |
      | schedule_2_S0271                 | 6                | 0                | 10             | 6             | false         |

    # now change the stock from 16 to 11
    When M_HU are disposed:
      | M_HU_ID.Identifier | MovementDate         |
      | hu_S0271           | 2023-05-30T21:00:00Z |

    Then after not more than 60s, validate shipment schedules:
      | M_ShipmentSchedule_ID.Identifier | OPT.QtyToDeliver | OPT.QtyDelivered | OPT.QtyOrdered | OPT.QtyOnHand | OPT.Processed |
      | schedule_1_S0271                 | 10               | 0                | 10             | 11            | false         |
      | schedule_2_S0271                 | 1                | 0                | 10             | 1             | false         |
=======
      | i_2_0601   | 540008         | 2021-04-16   |
    And metasfresh contains M_InventoriesLines:
      | Identifier | M_Inventory_ID.Identifier | M_Product_ID.Identifier | UOM.X12DE355 | QtyCount | QtyBook |
      | il_2_0601  | i_2_0601                  | product_0601            | PCE          | 11       | 0       |

    # now change the stock from 5 to 16
    When the inventory identified by i_2_0601 is completed

    Then after not more than 60s, validate shipment schedules:
      | M_ShipmentSchedule_ID.Identifier | OPT.QtyToDeliver | OPT.QtyDelivered | OPT.QtyOrdered | OPT.QtyOnHand | OPT.Processed |
      | schedule_1_0601                  | 10               | 0                | 10             | 16            | false         |
      | schedule_2_0601                  | 6                | 0                | 10             | 6             | false         |
>>>>>>> a22e5b8f990 (Update affected M_ShipmentSchedules after stock change (#15461))
