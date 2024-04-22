@from:cucumber
@ghActions:run_on_executor5
Feature: Cleared HU can be picked on the fly and manually picked

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2022-03-31T13:30:13+01:00[Europe/Berlin]

    And destroy existing M_HUs

    And metasfresh contains M_Products:
      | Identifier | Value     | Name      |
      | huProduct  | huProduct | huProduct |

    And load M_Warehouse:
      | M_Warehouse_ID.Identifier | Value        |
      | warehouseStd              | StdWarehouse |
    And load M_Locator:
      | M_Locator_ID.Identifier | M_Warehouse_ID.Identifier | Value      |
      | locatorHauptlager       | warehouseStd              | Hauptlager |

    And metasfresh contains M_PricingSystems
      | Identifier   | Name            | Value            | OPT.IsActive |
      | pricingSys_1 | pricingSys_Name | pricingSys_Value | true         |
    And metasfresh contains M_PriceLists
      | Identifier  | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name              | OPT.Description | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | priceList_1 | pricingSys_1                  | DE                        | EUR                 | d_price_list_name | null            | true  | false         | 2              | true         |
    And metasfresh contains M_PriceList_Versions
      | Identifier         | M_PriceList_ID.Identifier | Name           | ValidFrom  |
      | priceListVersion_1 | priceList_1               | salesOrder-PLV | 2022-02-01 |
    And metasfresh contains M_ProductPrices
      | Identifier      | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | productPrices_1 | priceListVersion_1                | huProduct               | 10.0     | PCE               | Normal                        |

    And metasfresh contains M_HU_PI:
      | M_HU_PI_ID.Identifier | Name            |
      | huPackingVirtualPI    | No Packing Item |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID.Identifier | M_HU_PI_ID.Identifier | Name            | HU_UnitType | IsCurrent |
      | packingVersionCU              | huPackingVirtualPI    | No Packing Item | V           | Y         |

    And metasfresh initially has M_Inventory data
      | M_Inventory_ID.Identifier | MovementDate | DocumentNo     |
      | huProduct_inventory       | 2022-03-20   | inventoryDocNo |
    And metasfresh initially has M_InventoryLine data
      | M_Inventory_ID.Identifier | M_InventoryLine_ID.Identifier | M_Product_ID.Identifier | QtyBook | QtyCount |
      | huProduct_inventory       | huProduct_inventoryLine       | huProduct               | 0       | 10       |
    And complete inventory with inventoryIdentifier 'huProduct_inventory'
    And after not more than 60s, there are added M_HUs for inventory
      | M_InventoryLine_ID.Identifier | M_HU_ID.Identifier |
      | huProduct_inventoryLine       | createdCU          |

    And update HU clearance status
      | M_HU_ID.Identifier | ClearanceStatus | OPT.ClearanceNote |
      | createdCU          | Cleared         | Cleared HU        |


  Scenario: Cleared HUs can be picked on the fly
    Given metasfresh contains C_BPartners:
      | Identifier | Name                                  | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | bpartner_1 | BPartnerTest_PickedOnTheFlyClearedHUs | N            | Y              | pricingSys_1                  |
    And metasfresh contains C_BPartner_Locations:
      | Identifier | GLN           | C_BPartner_ID.Identifier |
      | location_1 | 0442283371291 | bpartner_1               |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.C_PaymentTerm_ID | deliveryRule |
      | order_1    | true    | bpartner_1               | 2022-03-28  | 1000012              | F            |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_1       | order_1               | huProduct               | 10         |

    And the order identified by order_1 is completed

    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_s_1      | ol_1                      | N             |

    When 'generate shipments' process is invoked
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | s_s_1                            | D            | true                | false       |

    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier | OPT.DocStatus |
      | s_s_1                            | shipment_1            | CO            |

    And validate M_ShipmentSchedule_QtyPicked:
      | M_ShipmentSchedule_ID.Identifier | OPT.VHU_ID.Identifier | IsAnonymousHuPickedOnTheFly |
      | s_s_1                            | createdCU             | Y                           |

    And validate M_HUs:
      | M_HU_ID.Identifier | M_HU_PI_Version_ID.Identifier | HUStatus | OPT.M_Locator_ID.Identifier | OPT.ClearanceStatus | OPT.ClearanceNote |
      | createdCU          | packingVersionCU              | E        | locatorHauptlager           | C                   | Cleared HU        |


  Scenario: Cleared HUs can be manually picked
    Given metasfresh contains C_BPartners:
      | Identifier | Name                                  | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | bpartner_1 | BPartnerTest_ManuallyPickedClearedHUs | N            | Y              | pricingSys_1                  |
    And metasfresh contains C_BPartner_Locations:
      | Identifier | GLN           | C_BPartner_ID.Identifier |
      | location_1 | 0120087122881 | bpartner_1               |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.C_PaymentTerm_ID | deliveryRule |
      | order_1    | true    | bpartner_1               | 2022-03-28  | 1000012              | F            |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_1       | order_1               | huProduct               | 10         |

    And the order identified by order_1 is completed

    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_s_2      | ol_1                      | N             |

    And validate M_HUs are available to pick for shipmentSchedule identified by s_s_2
      | M_HU_ID.Identifier |
      | createdCU          |

    When create M_PickingCandidate for M_HU
      | M_HU_ID.Identifier | M_ShipmentSchedule_ID.Identifier | QtyPicked | Status | PickStatus | ApprovalStatus |
      | createdCU          | s_s_2                            | 10        | IP     | P          | ?              |

    Then validate M_HUs:
      | M_HU_ID.Identifier | M_HU_PI_Version_ID.Identifier | HUStatus | OPT.M_Locator_ID.Identifier | OPT.ClearanceStatus | OPT.ClearanceNote |
      | createdCU          | packingVersionCU              | A        | locatorHauptlager           | C                   | Cleared HU        |

    And process picking
      | M_HU_ID.Identifier | M_ShipmentSchedule_ID.Identifier |
      | createdCU          | s_s_2                            |

    And validate M_HUs:
      | M_HU_ID.Identifier | M_HU_PI_Version_ID.Identifier | HUStatus | OPT.M_Locator_ID.Identifier | OPT.ClearanceStatus | OPT.ClearanceNote |
      | createdCU          | packingVersionCU              | S        | locatorHauptlager           | C                   | Cleared HU        |
