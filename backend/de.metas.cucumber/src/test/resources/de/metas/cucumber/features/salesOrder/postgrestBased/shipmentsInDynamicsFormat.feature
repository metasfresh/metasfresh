@from:cucumber
@allure.label.epic:E0100_Sales
@allure.label.feature:F00100_Sales_Order
@F00100
@ghActions:run_on_executor3
Feature: Export Shipments in specific format via postgREST
## F00100: Sales Order

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2022-03-31T13:30:13+01:00[Europe/Berlin]

    And destroy existing M_HUs

    And metasfresh contains M_Products:
      | Identifier | X12DE355 | REST.Context.Value |
      | product    | PCE      | productValue       |

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
      | PLV                    | product      | 6.0      | PCE               | Nominal              | Normal                        |


    And metasfresh contains M_HU_PI:
      | M_HU_PI_ID.Identifier | Name            |
      | huPackingVirtualPI    | No Packing Item |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID.Identifier | M_HU_PI_ID.Identifier | Name            | HU_UnitType | IsCurrent |
      | packingVersionCU              | huPackingVirtualPI    | No Packing Item | V           | Y         |

    And metasfresh contains M_Inventories:
      | M_Inventory_ID.Identifier | MovementDate | M_Warehouse_ID |
      | inventory                 | 2024-03-20   | 540008         |
    And metasfresh contains M_InventoriesLines:
      | M_Inventory_ID.Identifier | M_InventoryLine_ID.Identifier | M_Product_ID.Identifier | QtyBook | QtyCount | UOM.X12DE355 |
      | inventory                 | line1                         | product                 | 0       | 5        | PCE          |
      | inventory                 | line2                         | product                 | 0       | 5        | PCE          |
    And complete inventory with inventoryIdentifier 'inventory'
    And after not more than 60s, there are added M_HUs for inventory
      | M_InventoryLine_ID | M_HU_ID     |
      | line1              | createdCU_1 |
      | line2              | createdCU_2 |

  @from:cucumber
@allure.label.epic:E0100_Sales
@allure.label.feature:F00100_Sales_Order
@F00100
  Scenario: create a shipment and export it to JSON via M_InOut_ID. The used BPartner does not have an external reference for Dynamics365 External System
    Given M_HU_Attribute is changed
      | M_HU_ID.Identifier | M_Attribute_ID.Value | Value          |
      | createdCU_1        | SerialNo             | 11111111111111 |
      | createdCU_2        | SerialNo             | 22222222222222 |
    And metasfresh contains C_BPartners:
      | Identifier | IsCustomer | M_PricingSystem_ID | REST.Context | REST.Context.Value | REST.Context.Name |
      | bpartner_1 | Y          | PS                 | partnerId    | partnerValue       | partnerName       |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | POReference |
      | order_1    | true    | bpartner_1    | 2022-03-28  | poReference |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered | REST.Context |
      | ol_1       | order_1    | product      | 10         | orderLineId  |

    And the order identified by order_1 is completed

    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute |
      | s_s_2      | ol_1           | N             |

    And validate M_HUs are available to pick for shipmentSchedule identified by s_s_2
      | M_HU_ID.Identifier |
      | createdCU_1        |
      | createdCU_2        |

    When create M_PickingCandidate for M_HU
      | M_HU_ID.Identifier | M_ShipmentSchedule_ID.Identifier | QtyPicked | Status | PickStatus | ApprovalStatus |
      | createdCU_1        | s_s_2                            | 5         | IP     | P          | ?              |
      | createdCU_2        | s_s_2                            | 5         | IP     | P          | ?              |

    Then validate M_HUs:
      | M_HU_ID.Identifier | M_HU_PI_Version_ID.Identifier | HUStatus |
      | createdCU_1        | packingVersionCU              | A        |
      | createdCU_2        | packingVersionCU              | A        |

    And process picking
      | M_HU_ID.Identifier | M_ShipmentSchedule_ID.Identifier |
      | createdCU_1        | s_s_2                            |
      | createdCU_2        | s_s_2                            |

    And validate M_HUs:
      | M_HU_ID.Identifier | M_HU_PI_Version_ID.Identifier | HUStatus |
      | createdCU_1        | packingVersionCU              | S        |
      | createdCU_2        | packingVersionCU              | S        |

    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | s_s_2                            | P            | true                | false       |

    And after not more than 30s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID | REST.Context.DocumentNo | REST.Context |
      | s_s_2                 | shipment   | documentNo              | inOutId      |


    And the following API_Audit_Config records are created:
      | Identifier | SeqNo | OPT.Method | OPT.PathPrefix   | IsForceProcessedAsync | IsSynchronousAuditLoggingEnabled | IsWrapApiResponse |
      | c_1        | 10    | GET        | api/v2/processes | N                     | Y                                | N                 |
    And add HTTP headers
      | Key          | Value                          |
      | Content-Type | application/json;charset=UTF-8 |
      | accept       | application/json;charset=UTF-8 |

    When a 'POST' request with the below payload and headers from context is sent to the metasfresh REST-API 'api/v2/processes/Shipment_Dynamics_JSON/invoke' and fulfills with '200' status code
    """
{
  "processParameters": [
    {
      "name": "M_InOut_ID",
      "value": "@inOutId@"
    }
  ]
}
    """

    Then the metasfresh REST-API responds with
    """
{
  "orgCode": "001",
  "deliveryNoteNumber": "@documentNo@",
  "poReference": "poReference",
  "dateShip": "2022-03-31",
  "partnerIdentifier": "@partnerId@",
  "partnerValue": "@partnerValue@",
  "partnerName": "@partnerName@",
  "lines": [
    {
      "qty": 10,
      "uom": "PCE",
      "poLineId": @orderLineId@,
      "shippingItems": [
        {
          "serialNumber": "22222222222222"
        },
        {
          "serialNumber": "11111111111111"
        }
      ],
      "productIdentifier": "@productValue@"
    }
  ]
}
    """

  @from:cucumber
@allure.label.epic:E0100_Sales
@allure.label.feature:F00100_Sales_Order
@F00100
  Scenario: create a shipment and export it to JSON via M_InOut_ID. The used BPartner has an external reference for Dynamics365 External System
    Given M_HU_Attribute is changed
      | M_HU_ID.Identifier | M_Attribute_ID.Value | Value          |
      | createdCU_1        | SerialNo             | 33333333333333 |
      | createdCU_2        | SerialNo             | 44444444444444 |
    And metasfresh contains C_BPartners:
      | Identifier | IsCustomer | M_PricingSystem_ID | REST.Context | REST.Context.Value | REST.Context.Name |
      | bpartner_1 | Y          | PS                 | partnerId    | partnerValue       | partnerName       |

    When metasfresh contains External System
      | Name        | Value       |
      | Dynamics365 | Dynamics365 |
    And metasfresh contains S_ExternalReference:
      | S_ExternalReference_ID.Identifier | ExternalSystem | Type     | ExternalReference | C_BPartner_ID |
      | bpartner_ref                      | Dynamics365    | BPartner | DT100             | bpartner_1    |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | POReference |
      | order_1    | true    | bpartner_1    | 2022-03-28  | poReference |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered | REST.Context |
      | ol_1       | order_1    | product      | 10         | orderLineId  |

    And the order identified by order_1 is completed

    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute |
      | s_s_2      | ol_1           | N             |

    And validate M_HUs are available to pick for shipmentSchedule identified by s_s_2
      | M_HU_ID.Identifier |
      | createdCU_1        |
      | createdCU_2        |

    When create M_PickingCandidate for M_HU
      | M_HU_ID.Identifier | M_ShipmentSchedule_ID.Identifier | QtyPicked | Status | PickStatus | ApprovalStatus |
      | createdCU_1        | s_s_2                            | 5         | IP     | P          | ?              |
      | createdCU_2        | s_s_2                            | 5         | IP     | P          | ?              |

    Then validate M_HUs:
      | M_HU_ID.Identifier | M_HU_PI_Version_ID.Identifier | HUStatus |
      | createdCU_1        | packingVersionCU              | A        |
      | createdCU_2        | packingVersionCU              | A        |

    And process picking
      | M_HU_ID.Identifier | M_ShipmentSchedule_ID.Identifier |
      | createdCU_1        | s_s_2                            |
      | createdCU_2        | s_s_2                            |

    And validate M_HUs:
      | M_HU_ID.Identifier | M_HU_PI_Version_ID.Identifier | HUStatus |
      | createdCU_1        | packingVersionCU              | S        |
      | createdCU_2        | packingVersionCU              | S        |

    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | s_s_2                            | P            | true                | false       |

    And after not more than 30s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID | REST.Context.DocumentNo | REST.Context |
      | s_s_2                 | shipment   | documentNo              | inOutId      |


    And the following API_Audit_Config records are created:
      | Identifier | SeqNo | OPT.Method | OPT.PathPrefix   | IsForceProcessedAsync | IsSynchronousAuditLoggingEnabled | IsWrapApiResponse |
      | c_1        | 10    | GET        | api/v2/processes | N                     | Y                                | N                 |
    And add HTTP headers
      | Key          | Value                          |
      | Content-Type | application/json;charset=UTF-8 |
      | accept       | application/json;charset=UTF-8 |

    When a 'POST' request with the below payload and headers from context is sent to the metasfresh REST-API 'api/v2/processes/Shipment_Dynamics_JSON/invoke' and fulfills with '200' status code
    """
{
  "processParameters": [
    {
      "name": "M_InOut_ID",
      "value": "@inOutId@"
    }
  ]
}
    """

    Then the metasfresh REST-API responds with
    """
{
  "orgCode": "001",
  "deliveryNoteNumber": "@documentNo@",
  "poReference": "poReference",
  "dateShip": "2022-03-31",
  "partnerIdentifier": "DT100",
  "partnerValue": "@partnerValue@",
  "partnerName": "@partnerName@",
  "lines": [
    {
      "qty": 10,
      "uom": "PCE",
      "poLineId": @orderLineId@,
      "shippingItems": [
        {
          "serialNumber": "33333333333333"
        },
        {
          "serialNumber": "44444444444444"
        }
      ],
      "productIdentifier": "@productValue@"
    }
  ]
}
    """
