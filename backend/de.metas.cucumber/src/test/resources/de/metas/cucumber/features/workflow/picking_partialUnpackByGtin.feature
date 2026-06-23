@from:cucumber
@allure.label.epic:E0180_System_Administration
@allure.label.feature:F00183
@ghActions:run_on_executor7
Feature: Picking workflow - partial unpack a packed HU by scanning a product GTIN

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2022-08-18T13:30:13+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And metasfresh contains M_PricingSystems
      | Identifier  |
      | PS_30480    |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | OPT.C_Country.CountryCode | C_Currency.ISO_Code | SOTrx | IsTaxIncluded | PricePrecision |
      | PL_30480   | PS_30480           | DE                        | EUR                 | true  | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID |
      | PLV_30480  | PL_30480       |
    And metasfresh contains M_Products:
      | Identifier    | Name          | OPT.GTIN       |
      | product_30480 | Product30480  | 04006381333931 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID | M_Product_ID  | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | PP_30480   | PLV_30480              | product_30480 | 5.0      | PCE               | Normal                        |
    And set mobile UI picking profile
      | IsAllowPickingAnyHU | CreateShipmentPolicy |
      | Y                   | CREATE_AND_COMPLETE  |
    And metasfresh contains C_BPartners without locations:
      | Identifier     | IsVendor | IsCustomer | M_PricingSystem_ID |
      | customer_30480 | N        | Y          | PS_30480           |
    And metasfresh contains C_BPartner_Locations:
      | Identifier        | GLN              | C_BPartner_ID.Identifier | OPT.IsBillToDefault | OPT.IsShipTo |
      | custLoc_30480     | 0304806300480011 | customer_30480           | true                | true         |
    And metasfresh contains M_Inventories:
      | M_Inventory_ID.Identifier | MovementDate | M_Warehouse_ID |
      | inventory_30480           | 2022-08-17   | 540008         |
    And metasfresh contains M_InventoriesLines:
      | M_Inventory_ID.Identifier | M_InventoryLine_ID.Identifier | M_Product_ID.Identifier | QtyBook | QtyCount | UOM.X12DE355 |
      | inventory_30480           | invLine_30480                 | product_30480           | 0       | 10       | PCE          |
    And complete inventory with inventoryIdentifier 'inventory_30480'
    And after not more than 60s, there are added M_HUs for inventory
      | M_InventoryLine_ID.Identifier | M_HU_ID.Identifier |
      | invLine_30480                 | stockHU_30480      |
    And metasfresh contains C_Orders:
      | Identifier  | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered |
      | order_30480 | true    | customer_30480           | 2022-08-17  |
    And metasfresh contains C_OrderLines:
      | Identifier      | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | orderLine_30480 | order_30480           | product_30480           | 10         |
    And the order identified by order_30480 is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier     | C_OrderLine_ID.Identifier | IsToRecompute |
      | schedule_30480 | orderLine_30480           | N             |
    And metasfresh contains M_PickingSlot:
      | Identifier | PickingSlot | IsDynamic |
      | slot_30480 | 063.3       | Y         |

  @from:cucumber
  @allure.label.epic:E0180_System_Administration
  @allure.label.feature:F00183
  Scenario: Partial unpack by product GTIN and quantity, with re-pick loop repeated twice
    # Set up: start job, scan slot, pick 6 PCE into the package
    And start picking job for sales order identified by order_30480
    And scan picking slot identified by slot_30480
    And generate QR Codes for HUs
      | M_HU_ID.Identifier | HUQRCode.Identifier |
      | stockHU_30480      | stockHUQR_30480     |
    And pick lines
      | PickFromHU    | QtyPicked |
      | stockHU_30480 | 6         |

    # --- Cycle 1: partial unpick 2 PCE of product identified by its GTIN, then re-pick ---

    # Partial unpick by GTIN: remove 2 PCE of product_30480 from the package to the floor.
    # This capability does NOT exist today — the step below must FAIL with UnsupportedOperationException.
    When partial unpick from packed HU by product GTIN:
      | ProductGTIN    | QtyToUnpick |
      | 04006381333931 | 2           |

    # Assertions after partial unpick (not reached today — step above fails first)
    Then the packed HU contains product with qty:
      | M_Product_ID.Identifier | ExpectedQty |
      | product_30480           | 4           |
    And the picking job has re-pickable qty for product:
      | M_Product_ID.Identifier | ExpectedRePickableQty |
      | product_30480           | 2                     |

    # Re-pick the 2 PCE back into the package
    And pick lines
      | PickFromHU    | QtyPicked |
      | stockHU_30480 | 2         |
    Then the packed HU contains product with qty:
      | M_Product_ID.Identifier | ExpectedQty |
      | product_30480           | 6           |
    And the picking job has re-pickable qty for product:
      | M_Product_ID.Identifier | ExpectedRePickableQty |
      | product_30480           | 0                     |

    # --- Cycle 2: partial unpick 3 PCE, then re-pick ---

    When partial unpick from packed HU by product GTIN:
      | ProductGTIN    | QtyToUnpick |
      | 04006381333931 | 3           |
    Then the packed HU contains product with qty:
      | M_Product_ID.Identifier | ExpectedQty |
      | product_30480           | 3           |
    And the picking job has re-pickable qty for product:
      | M_Product_ID.Identifier | ExpectedRePickableQty |
      | product_30480           | 3                     |

    And pick lines
      | PickFromHU    | QtyPicked |
      | stockHU_30480 | 3         |
    Then the packed HU contains product with qty:
      | M_Product_ID.Identifier | ExpectedQty |
      | product_30480           | 6           |
    And the picking job has re-pickable qty for product:
      | M_Product_ID.Identifier | ExpectedRePickableQty |
      | product_30480           | 0                     |

    And complete picking job
