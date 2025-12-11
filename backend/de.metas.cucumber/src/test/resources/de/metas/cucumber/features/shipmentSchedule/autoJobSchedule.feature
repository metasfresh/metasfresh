@from:cucumber
@ghActions:run_on_executor4
Feature: Auto picking job schedule

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2023-06-01T13:30:13+01:00[Europe/Berlin]
    And deactivate all M_ShipmentSchedule records
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And set sys config boolean value false for sys config de.metas.handlingunits.picking.job_schedule.RequireCarrierProductSet
    # we don't care about the export, and don't want it to interject in any way
    And set sys config int value 999999 for sys config de.metas.inoutcandidate.M_ShipmentSchedule.canBeExportedAfterSeconds

    And metasfresh contains M_Product_Categories:
      | Identifier |
      | pg         |
      | pg2        |
    And metasfresh contains M_Products:
      | Identifier | M_Product_Category_ID |
      | product    | pg                    |
      | product2   | pg2                   |
    And metasfresh contains M_PricingSystems
      | Identifier |
      | ps         |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | C_Country.CountryCode | C_Currency.ISO_Code | SOTrx | IsTaxIncluded |
      | pl         | ps                 | DE                    | EUR                 | true  | false         |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID |
      | plv        | pl             |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_TaxCategory_ID | C_UOM_ID.X12DE355 |
      | plv                    | product      | 10.0     | Normal           | PCE               |
      | plv                    | product2     | 10.0     | Normal           | PCE               |
    And metasfresh contains C_BPartners:
      | Identifier | M_PricingSystem_ID | IsCustomer |
      | customer   | ps                 | Y          |
    And metasfresh contains M_Warehouse:
      | M_Warehouse_ID |
      | wh             |
      | wh2            |


  Scenario: auto job schedule - seqNo
    Given deactivate all C_Workplace records
    And metasfresh contains C_Workplaces
      | Identifier | SeqNo | M_Warehouse_ID | MaxPickingJobs |
      | workplace1 | 30    | wh             | 10             |
      | workplace2 | 20    | wh             | 10             |
      | workplace3 | 10    | wh             | 0              |
    When simple completed order with one line
      | C_Order_ID | C_BPartner_ID | DateOrdered | IsSOTrx | M_Warehouse_ID | InvoiceRule | C_OrderLine_ID | M_Product_ID | QtyEntered |
      | so1        | customer      | 2025-04-01  | true    | wh             | I           | so1_l1         | product      | 10         |
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute |
      | sched1     | so1_l1         | N             |
    And AD_Scheduler for classname 'de.metas.handlingunits.picking.process.M_ShipmentSchedule_Traffic_Management_assign' is ran once
    And after not more than 60s, picking job schedules are found:
      | M_ShipmentSchedule_ID | C_Workplace_ID | QtyToPick |
      | sched1                | workplace2     | 10        |

  Scenario: auto job schedule - OrderPickingType
    Given deactivate all C_Workplace records
    And metasfresh contains C_Workplaces
      | Identifier | SeqNo | M_Warehouse_ID | MaxPickingJobs | OrderPickingType |
      | workplace1 | 10    | wh             | 10             | MP               |
      | workplace2 | 20    | wh             | 10             | SG               |
    When simple completed order with one line
      | C_Order_ID | C_BPartner_ID | DateOrdered | IsSOTrx | M_Warehouse_ID | InvoiceRule | C_OrderLine_ID | M_Product_ID | QtyEntered |
      | so2        | customer      | 2025-04-01  | true    | wh             | I           | so2_l1         | product      | 10         |
    When simple completed order with one line
      | C_Order_ID | C_BPartner_ID | DateOrdered | IsSOTrx | M_Warehouse_ID | InvoiceRule | C_OrderLine_ID | M_Product_ID | QtyEntered |
      | so3        | customer      | 2025-04-01  | true    | wh             | I           | so3_l1         | product      | 1          |
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute |
      | sched2     | so2_l1         | N             |
      | sched3     | so3_l1         | N             |
    And AD_Scheduler for classname 'de.metas.handlingunits.picking.process.M_ShipmentSchedule_Traffic_Management_assign' is ran once
    And after not more than 60s, picking job schedules are found:
      | M_ShipmentSchedule_ID | C_Workplace_ID | QtyToPick |
      | sched2                | workplace1     | 10        |
      | sched3                | workplace2     | 1         |



