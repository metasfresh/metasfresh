@from:cucumber
@allure.label.epic:E0110_Sales
@allure.label.feature:F00200_Sales_Order
@ghActions:run_on_executor7
Feature: Customer Return from Shipment
## Tests that completing a customer return creates Active HUs,
## reversing destroys those HUs, and reactivating leaves them unchanged.

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2022-04-16T13:30:13+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION

    And load M_Warehouse:
      | Identifier   | Value        |
      | warehouseStd | StdWarehouse |

    And metasfresh contains M_Products:
      | Identifier |
      | product_CR |

    And metasfresh contains M_PricingSystems
      | Identifier |
      | ps_CR      |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | OPT.C_Country.CountryCode | C_Currency.ISO_Code | SOTrx |
      | pl_CR      | ps_CR              | DE                        | EUR                 | true  |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID | ValidFrom  |
      | plv_CR     | pl_CR          | 2022-03-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_CR      | plv_CR                 | product_CR   | 10.0     | PCE               | Normal                        |

    And metasfresh contains C_BPartners:
      | Identifier  | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID |
      | customer_CR | N            | Y              | ps_CR              |


  @from:cucumber
  @allure.label.epic:E0110_Sales
  @allure.label.feature:F00200_Sales_Order
  Scenario: Complete customer return and verify M_HUs are Active
    # Step 1: Create Sales Order
    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | OPT.POReference | OPT.M_PricingSystem_ID | OPT.C_BPartner_Location_ID | OPT.DeliveryRule | OPT.DeliveryViaRule |
      | order_CR   | Y       | customer_CR   | 2022-06-10  | so_ref_CR       | ps_CR                  | customer_CR                | F                | S                   |
    And metasfresh contains C_OrderLines:
      | Identifier   | C_Order_ID | M_Product_ID | QtyEntered |
      | orderLine_CR | order_CR   | product_CR   | 10         |

    When the order identified by order_CR is completed

    # Step 2: Wait for shipment schedule, generate and complete shipment
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier  | C_OrderLine_ID | IsToRecompute |
      | schedule_CR | orderLine_CR   | N             |

    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID | QuantityType | IsCompleteShipments | IsShipToday |
      | schedule_CR           | D            | true                | false       |

    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID  |
      | schedule_CR           | shipment_CR |

    # Step 3: Generate customer return from shipment and complete it
    And generate customer return from shipment
      | M_InOut_ID  | CustomerReturn_ID |
      | shipment_CR | customerReturn_CR |

    And the return inOut identified by customerReturn_CR is completed

    # Step 4: Verify HUs created by CustomerReturnHUsCreateCommand are Active
    And load HUs assigned to M_InOut
      | M_InOut_ID        | M_HU_ID   |
      | customerReturn_CR | return_hu |

    And M_HU are validated:
      | M_HU_ID   | HUStatus | IsActive |
      | return_hu | A        | Y        |


  @from:cucumber
  @allure.label.epic:E0110_Sales
  @allure.label.feature:F00200_Sales_Order
  Scenario: Reversing a completed customer return destroys its M_HUs
    # Step 1: Create Sales Order
    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | OPT.POReference | OPT.M_PricingSystem_ID | OPT.C_BPartner_Location_ID | OPT.DeliveryRule | OPT.DeliveryViaRule |
      | order_CR2  | Y       | customer_CR   | 2022-06-10  | so_ref_CR2      | ps_CR                  | customer_CR                | F                | S                   |
    And metasfresh contains C_OrderLines:
      | Identifier    | C_Order_ID | M_Product_ID | QtyEntered |
      | orderLine_CR2 | order_CR2  | product_CR   | 10         |

    When the order identified by order_CR2 is completed

    # Step 2: Shipment
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier   | C_OrderLine_ID | IsToRecompute |
      | schedule_CR2 | orderLine_CR2  | N             |

    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID | QuantityType | IsCompleteShipments | IsShipToday |
      | schedule_CR2          | D            | true                | false       |

    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID   |
      | schedule_CR2          | shipment_CR2 |

    # Step 3: Generate and complete customer return
    And generate customer return from shipment
      | M_InOut_ID   | CustomerReturn_ID  |
      | shipment_CR2 | customerReturn_CR2 |

    And the return inOut identified by customerReturn_CR2 is completed

    # After completion: HUs are Active
    And load HUs assigned to M_InOut
      | M_InOut_ID         | M_HU_ID    |
      | customerReturn_CR2 | return_hu2 |

    And M_HU are validated:
      | M_HU_ID    | HUStatus | IsActive |
      | return_hu2 | A        | Y        |

    # Step 4: Reverse the customer return
    # destroyHandlingUnitsForReversedInboundMovements (TIMING_AFTER_REVERSECORRECT)
    # calls huInOutBL.destroyHandlingUnitsIfReversedInboundTransaction — HUs are Destroyed
    And the return inOut identified by customerReturn_CR2 is reversed

    # Step 5: HUs are Destroyed
    And M_HU are validated:
      | M_HU_ID    | HUStatus | IsActive |
      | return_hu2 | D        | N        |


  @from:cucumber
  @allure.label.epic:E0110_Sales
  @allure.label.feature:F00200_Sales_Order
  Scenario: Reactivating a completed customer return leaves M_HUs unchanged
    # Step 1: Create Sales Order
    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | OPT.POReference | OPT.M_PricingSystem_ID | OPT.C_BPartner_Location_ID | OPT.DeliveryRule | OPT.DeliveryViaRule |
      | order_CR3  | Y       | customer_CR   | 2022-06-10  | so_ref_CR3      | ps_CR                  | customer_CR                | F                | S                   |
    And metasfresh contains C_OrderLines:
      | Identifier    | C_Order_ID | M_Product_ID | QtyEntered |
      | orderLine_CR3 | order_CR3  | product_CR   | 10         |

    When the order identified by order_CR3 is completed

    # Step 2: Shipment
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier   | C_OrderLine_ID | IsToRecompute |
      | schedule_CR3 | orderLine_CR3  | N             |

    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID | QuantityType | IsCompleteShipments | IsShipToday |
      | schedule_CR3          | D            | true                | false       |

    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID   |
      | schedule_CR3          | shipment_CR3 |

    # Step 3: Generate and complete customer return
    And generate customer return from shipment
      | M_InOut_ID   | CustomerReturn_ID  |
      | shipment_CR3 | customerReturn_CR3 |

    And the return inOut identified by customerReturn_CR3 is completed

    # After completion: HUs are Active
    And load HUs assigned to M_InOut
      | M_InOut_ID         | M_HU_ID    |
      | customerReturn_CR3 | return_hu3 |

    And M_HU are validated:
      | M_HU_ID    | HUStatus | IsActive |
      | return_hu3 | A        | Y        |

    # Step 4: Reactivate the customer return
    And the return inOut identified by customerReturn_CR3 is reactivated

    # Step 5: HUs are Destroyed
    And M_HU are validated:
      | M_HU_ID    | HUStatus | IsActive |
      | return_hu3 | D        | N        |
