@from:cucumber
@allure.label.epic:E0180_System_Administration
@allure.label.feature:F00183
@ghActions:run_on_executor7
Feature: Picking workflow - partial unpack counter-row net qty lockdown

  # This feature verifies that after a partial unpick of qty N from a packed qty P,
  # the generated shipment carries qty P-N with NO negative shipment line.

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2022-08-18T13:30:13+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And metasfresh contains M_PricingSystems
      | Identifier     |
      | PS_30480_crNQ  |
    And metasfresh contains M_PriceLists
      | Identifier    | M_PricingSystem_ID | OPT.C_Country.CountryCode | C_Currency.ISO_Code | SOTrx | IsTaxIncluded | PricePrecision |
      | PL_30480_crNQ | PS_30480_crNQ      | DE                        | EUR                 | true  | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier     | M_PriceList_ID |
      | PLV_30480_crNQ | PL_30480_crNQ  |
    And metasfresh contains M_Products:
      | Identifier         | Name               | OPT.GTIN       |
      | product_30480_crNQ | Product30480_crNQ  | 04006381334019 |
    And metasfresh contains M_ProductPrices
      | Identifier         | M_PriceList_Version_ID | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | PP_30480_crNQ      | PLV_30480_crNQ         | product_30480_crNQ      | 5.0      | PCE               | Normal                        |
    And set mobile UI picking profile
      | IsAllowPickingAnyHU | CreateShipmentPolicy |
      | Y                   | CREATE_AND_COMPLETE  |
    And metasfresh contains C_BPartners without locations:
      | Identifier          | IsVendor | IsCustomer | M_PricingSystem_ID |
      | customer_30480_crNQ | N        | Y          | PS_30480_crNQ      |
    And metasfresh contains C_BPartner_Locations:
      | Identifier              | GLN              | C_BPartner_ID.Identifier | OPT.IsBillToDefault | OPT.IsShipTo |
      | custLoc_30480_crNQ      | 0304806300480099 | customer_30480_crNQ      | true                | true         |
    And metasfresh contains M_Inventories:
      | M_Inventory_ID.Identifier | MovementDate | M_Warehouse_ID |
      | inventory_30480_crNQ      | 2022-08-17   | 540008         |
    And metasfresh contains M_InventoriesLines:
      | M_Inventory_ID.Identifier | M_InventoryLine_ID.Identifier | M_Product_ID.Identifier | QtyBook | QtyCount | UOM.X12DE355 |
      | inventory_30480_crNQ      | invLine_30480_crNQ            | product_30480_crNQ      | 0       | 10       | PCE          |
    And complete inventory with inventoryIdentifier 'inventory_30480_crNQ'
    And after not more than 60s, there are added M_HUs for inventory
      | M_InventoryLine_ID.Identifier | M_HU_ID.Identifier  |
      | invLine_30480_crNQ            | stockHU_30480_crNQ  |
    And metasfresh contains C_Orders:
      | Identifier         | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered |
      | order_30480_crNQ   | true    | customer_30480_crNQ      | 2022-08-17  |
    And metasfresh contains C_OrderLines:
      | Identifier           | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | orderLine_30480_crNQ | order_30480_crNQ      | product_30480_crNQ      | 6          |
    And the order identified by order_30480_crNQ is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier          | C_OrderLine_ID.Identifier    | IsToRecompute |
      | schedule_30480_crNQ | orderLine_30480_crNQ         | N             |
    And metasfresh contains M_PickingSlot:
      | Identifier          | PickingSlot | IsDynamic |
      | slot_30480_crNQ     | 063.9       | Y         |

  @from:cucumber
  @allure.label.epic:E0180_System_Administration
  @allure.label.feature:F00183
  @Id:S30480_TC2
  Scenario: Partial unpack 2 of 6 — shipment must carry qty=4 with no negative line
    # Set up: start picking job, scan slot, pick 6 PCE into the package
    And start picking job for sales order identified by order_30480_crNQ
    And scan picking slot identified by slot_30480_crNQ
    And generate QR Codes for HUs
      | M_HU_ID.Identifier | HUQRCode.Identifier      |
      | stockHU_30480_crNQ | stockHUQR_30480_crNQ     |
    And pick lines
      | PickFromHU         | QtyPicked |
      | stockHU_30480_crNQ | 6         |

    # Partial unpick 2 PCE of product identified by its GTIN.
    When partial unpick from packed HU by product GTIN:
      | ProductGTIN    | QtyToUnpick |
      | 04006381334019 | 2           |

    # Assert packed qty after partial unpick
    Then the packed HU contains product with qty:
      | M_Product_ID.Identifier | ExpectedQty |
      | product_30480_crNQ      | 4           |

    # Complete the picking job — triggers CREATE_AND_COMPLETE shipment policy
    And complete picking job

    # Wait for the shipment to be generated
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | schedule_30480_crNQ               | shipment_30480_crNQ   |

    # Net-qty lockdown: shipment line must carry qty=4 (P - N = 6 - 2)
    Then validate the created shipment lines
      | M_InOutLine_ID.Identifier  | M_InOut_ID.Identifier | M_Product_ID.Identifier | movementqty | processed |
      | shipmentLine_30480_crNQ    | shipment_30480_crNQ   | product_30480_crNQ      | 4           | true      |

    # No negative offset line — guards against a spurious counter-row in the shipment
    And the shipment identified by shipment_30480_crNQ has no negative movement lines
