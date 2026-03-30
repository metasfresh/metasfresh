@from:cucumber
@allure.label.epic:E0155_Material_Disposition
@allure.label.feature:F_QtyReservation
@ghActions:run_on_executor5
Feature: Qty Reservation — over-reservation prevention via QtyDemand_QtySupply_V
## Validates that the cockpit view correctly tracks available TUs and that
## attempting to reserve more TUs than available is rejected.
## The over-reservation prevention logic lives in the SE203_MaterialCockpit_V view
## (se203-specific extension of QtyDemand_QtySupply_V).

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And AD_Scheduler for classname 'de.metas.material.cockpit.stock.process.MD_Stock_Update_From_M_HUs' is disabled
    And metasfresh has date and time 2026-03-20T13:30:13+01:00[Europe/Berlin]
    And metasfresh contains M_PricingSystems
      | Identifier |
      | ps         |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | C_Currency.ISO_Code | SOTrx |
      | pl         | ps                 | EUR                 | true  |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID |
      | plv        | pl             |
    And metasfresh contains M_Warehouse:
      | Identifier |
      | warehouse  |
    And metasfresh contains M_Products:
      | Identifier | IsStocked |
      | product    | true      |
    And metasfresh contains M_HU_PI:
      | M_HU_PI_ID |
      | huPI       |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID | M_HU_PI_ID | HU_UnitType |
      | huPIV              | huPI       | TU          |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID | M_HU_PI_Version_ID | ItemType |
      | huPIItem        | huPIV              | MI       |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID | M_HU_PI_Item_ID | M_Product_ID | Qty    |
      | huPIP_10PCE             | huPIItem        | product      | 10 PCE |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID.X12DE355 |
      | plv                    | product      | 10.00    | PCE               |
    And metasfresh contains C_BPartners:
      | Identifier | IsCustomer | M_PricingSystem_ID | DeliveryRule |
      | bp         | true       | ps                 | A            |

  @from:cucumber
  @Id:S_QtyRes_210
  Scenario: Cannot reserve more TU than available in QtyDemand_QtySupply_V
  ## Given 2 TUs of 10 PCE each (= 20 PCE on-hand stock)
  ## And a sales order for 20 PCE
  ## When we query QtyDemand_QtySupply_V we see 2 TU available
  ## And we reserve all 2 TU — succeeds
  ## Then QtyDemand_QtySupply_V shows 0 TU available for the on-hand supply type
  ## And attempting to reserve 1 more TU fails

    # Create 2 TUs with 10 PCE each via completed inventories
    And metasfresh contains single line completed inventories
      | M_Inventory_ID | M_Warehouse_ID | MovementDate | M_Product_ID | QtyBook | QtyCount | M_HU_PI_Item_Product_ID | M_HU_ID |
      | inv_1          | warehouse      | 2026-03-20   | product      | 0 PCE   | 10 PCE   | huPIP_10PCE             | hu_1    |
    And metasfresh contains single line completed inventories
      | M_Inventory_ID | M_Warehouse_ID | MovementDate | M_Product_ID | QtyBook | QtyCount | M_HU_PI_Item_Product_ID | M_HU_ID |
      | inv_2          | warehouse      | 2026-03-20   | product      | 0 PCE   | 10 PCE   | huPIP_10PCE             | hu_2    |
    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes
    And after not more than 60 seconds metasfresh has MD_Stock data
      | M_Product_ID.Identifier | QtyOnHand |
      | product                 | 20        |

    # Create sales order for 20 PCE
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID | DeliveryRule |
      | order      | true    | bp            | 2026-03-20  | warehouse      | F            |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered | M_HU_PI_Item_Product_ID |
      | orderLine  | order      | product      | 20         | huPIP_10PCE             |
    And the order identified by order is completed

    # Query the cockpit view — expect 2 TUs available (on-hand, status=Available)
    When QtyDemand_QtySupply_V is queried for product "product" and warehouse "warehouse":
      | Identifier | SupplyType | AvailabilityStatus |
      | row_avail  | OH         | A                  |

    # Reserve all 2 TU — should succeed
    And make qty reservation from QtyDemand_QtySupply_V row "row_avail" for order line "orderLine" with QtyTU 2

    # Validate: after reserving 2 TU, available TU should be 0
    Then validate QtyDemand_QtySupply_V:
      | Identifier | M_Product_ID | M_Warehouse_ID | SupplyType | AvailabilityStatus | QtyTU |
      | row_zero   | product      | warehouse      | OH         | A                  | 0     |

    # Verify the view no longer shows any available rows for reservation (over-reservation prevention)
    And QtyDemand_QtySupply_V row "row_avail" is no longer available for reservation
