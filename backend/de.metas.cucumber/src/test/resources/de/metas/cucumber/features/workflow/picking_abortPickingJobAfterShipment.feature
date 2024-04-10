@from:cucumber
@ghActions:run_on_executor7
Feature: Picking workflow - abort not started picking jobs after shipment

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2022-08-18T13:30:13+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And metasfresh contains M_PricingSystems
      | Identifier                   | Name                             | Value                             | OPT.Description                         | OPT.IsActive |
      | picking_pricing_system_17813 | picking_pricing_system_17813Name | picking_pricing_system_17813Value | picking_pricing_system_17813Description | true         |
    And metasfresh contains M_PriceLists
      | Identifier             | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name                    | OPT.Description | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | picking_picelist_17813 | picking_pricing_system_17813  | DE                        | EUR                 | picking_pricelist_17813 | null            | true  | false         | 2              | true         |
    And metasfresh contains M_PriceList_Versions
      | Identifier                     | M_PriceList_ID.Identifier | Name                               | ValidFrom  |
      | picking_pricelistversion_17813 | picking_picelist_17813    | picking_pricelistversion_17813Name | 2021-04-01 |
    And metasfresh contains M_Products:
      | Identifier            | Name                  |
      | picking_product_17813 | picking_product_17813 |
    And metasfresh contains M_ProductPrices
      | Identifier           | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | workflowProductPrice | picking_pricelistversion_17813    | picking_product_17813   | 5.0      | PCE               | Normal                        |

    And set mobile UI picking profile
      | IsAllowPickingAnyHU | CreateShipmentPolicy |
      | Y                   | CREATE_AND_COMPLETE  |
    And metasfresh contains C_BPartners without locations:
      | Identifier            | Name                  | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | pickingCustomer_17813 | pickingCustomer_17813 | N            | Y              | picking_pricing_system_17813  |
    And metasfresh contains C_BPartner_Locations:
      | Identifier                    | GLN               | C_BPartner_ID.Identifier | OPT.IsBillToDefault | OPT.IsShipTo |
      | pickingCustomer_17813Location | picking_GLN_17813 | pickingCustomer_17813    | true                | true         |

    And metasfresh contains C_Orders:
      | Identifier       | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered |
      | salesOrder_17813 | true    | pickingCustomer_17813    | 2021-04-15  |
    And metasfresh contains C_OrderLines:
      | Identifier           | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | salesOrder_17813Line | salesOrder_17813      | picking_product_17813   | 10         |

    And the order identified by salesOrder_17813 is completed

    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier              | C_OrderLine_ID.Identifier | IsToRecompute |
      | pickingShipmentSchedule | salesOrder_17813Line      | N             |


  @from:cucumber
  Scenario: Abort not started picking jobs after shipment
    Given start picking job for sales order identified by salesOrder_17813
    And metasfresh contains M_PickingSlot:
      | Identifier | PickingSlot | IsDynamic |
      | 180.0      | 180.0       | Y         |
    And scan picking slot identified by 180.0
    And validate M_PickingSlot:
      | M_PickingSlot_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier |
      | 180.0                       | pickingCustomer_17813    | pickingCustomer_17813Location     |
    When 'generate shipments' process is invoked
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | pickingShipmentSchedule          | D            | true                | false       |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | pickingShipmentSchedule          | s_1                   |
    And validate single M_ShipmentSchedule_QtyPicked record created for shipment schedule
      | M_ShipmentSchedule_ID.Identifier | QtyPicked | Processed | IsAnonymousHuPickedOnTheFly | OPT.VHU_ID.Identifier |
      | pickingShipmentSchedule          | 10        | true      | false                       | null                  |
    Then after not more than 15s, validate M_PickingSlot:
      | M_PickingSlot_ID.Identifier | IsAllocated |
      | 180.0                       | N           |

