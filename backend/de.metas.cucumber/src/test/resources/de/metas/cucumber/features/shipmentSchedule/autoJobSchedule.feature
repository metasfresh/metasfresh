@from:cucumber
@allure.label.epic:E0100_Sales
@allure.label.feature:F00105_Sales_Order_Document
@F00105
@ghActions:run_on_executor4
Feature: Auto picking job schedule
## F00105: Shipment Schedule

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2023-06-01T13:30:13+01:00[Europe/Berlin]
    And deactivate all M_ShipmentSchedule records
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And set sys config boolean value false for sys config de.metas.handlingunits.picking.job_schedule.RequireCarrierProductSet
    # we don't care about the export, and don't want it to interject in any way
    And set sys config int value 999999 for sys config de.metas.inoutcandidate.M_ShipmentSchedule.canBeExportedAfterSeconds

    When metasfresh contains External System
      | Name                      | Value               |
      | JobSchedule Test System 1 | JobSchedule_System1 |
      | JobSchedule Test System 2 | JobSchedule_System2 |
      | JobSchedule Test System 3 | JobSchedule_System3 |
    And metasfresh contains M_Product_Categories:
      | Identifier |
      | pg         |
      | pg2        |
      | pg3        |
    And metasfresh contains M_Products:
      | Identifier | M_Product_Category_ID |
      | product    | pg                    |
      | product2   | pg2                   |
      | product3   | pg3                   |
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
    And after not more than 60s, validate shipment schedules:
      | M_ShipmentSchedule_ID.Identifier | IsScheduledForPicking | QtyScheduledForPicking |
      | sched1                           | Y                     | 10                     |

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
    And after not more than 60s, validate shipment schedules:
      | M_ShipmentSchedule_ID | IsScheduledForPicking | QtyScheduledForPicking |
      | sched2                | Y                     | 10                     |
      | sched3                | Y                     | 1                      |

  Scenario: auto job schedule - Warehouse
    Given deactivate all C_Workplace records
    And metasfresh contains C_Workplaces
      | Identifier | SeqNo | M_Warehouse_ID | MaxPickingJobs |
      | workplace1 | 10    | wh             | 10             |
      | workplace2 | 20    | wh2            | 10             |
    When simple completed order with one line
      | C_Order_ID | C_BPartner_ID | DateOrdered | IsSOTrx | M_Warehouse_ID | InvoiceRule | C_OrderLine_ID | M_Product_ID | QtyEntered |
      | so4        | customer      | 2025-04-01  | true    | wh2            | I           | so4_l1         | product      | 10         |
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute |
      | sched4     | so4_l1         | N             |
    And AD_Scheduler for classname 'de.metas.handlingunits.picking.process.M_ShipmentSchedule_Traffic_Management_assign' is ran once
    And after not more than 60s, picking job schedules are found:
      | M_ShipmentSchedule_ID | C_Workplace_ID | QtyToPick |
      | sched4                | workplace2     | 10        |
    And after not more than 60s, validate shipment schedules:
      | M_ShipmentSchedule_ID | IsScheduledForPicking | QtyScheduledForPicking |
      | sched4                | Y                     | 10                     |

  Scenario: auto job schedule - Product
    Given deactivate all C_Workplace records
    And metasfresh contains C_Workplaces
      | Identifier | SeqNo | M_Warehouse_ID | MaxPickingJobs | M_Product_ID      |
      | workplace1 | 10    | wh             | 10             | product, product3 |
      | workplace2 | 20    | wh             | 10             | product2          |
    When simple completed order with one line
      | C_Order_ID | C_BPartner_ID | DateOrdered | IsSOTrx | M_Warehouse_ID | InvoiceRule | C_OrderLine_ID | M_Product_ID | QtyEntered |
      | so5        | customer      | 2025-04-01  | true    | wh             | I           | so5_l1         | product2     | 10         |
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute |
      | sched5     | so5_l1         | N             |
    And AD_Scheduler for classname 'de.metas.handlingunits.picking.process.M_ShipmentSchedule_Traffic_Management_assign' is ran once
    And after not more than 60s, picking job schedules are found:
      | M_ShipmentSchedule_ID | C_Workplace_ID | QtyToPick |
      | sched5                | workplace2     | 10        |
    And after not more than 60s, validate shipment schedules:
      | M_ShipmentSchedule_ID | IsScheduledForPicking | QtyScheduledForPicking |
      | sched5                | Y                     | 10                     |

  Scenario: auto job schedule - Product Category
    Given deactivate all C_Workplace records
    And metasfresh contains C_Workplaces
      | Identifier | SeqNo | M_Warehouse_ID | MaxPickingJobs | M_Product_Category_ID |
      | workplace1 | 10    | wh             | 10             | pg, pg3               |
      | workplace2 | 20    | wh             | 10             | pg2                   |
    When simple completed order with one line
      | C_Order_ID | C_BPartner_ID | DateOrdered | IsSOTrx | M_Warehouse_ID | InvoiceRule | C_OrderLine_ID | M_Product_ID | QtyEntered |
      | so6        | customer      | 2025-04-01  | true    | wh             | I           | so6_l1         | product2     | 10         |
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute |
      | sched6     | so6_l1         | N             |
    And AD_Scheduler for classname 'de.metas.handlingunits.picking.process.M_ShipmentSchedule_Traffic_Management_assign' is ran once
    And after not more than 60s, picking job schedules are found:
      | M_ShipmentSchedule_ID | C_Workplace_ID | QtyToPick |
      | sched6                | workplace2     | 10        |
    And after not more than 60s, validate shipment schedules:
      | M_ShipmentSchedule_ID | IsScheduledForPicking | QtyScheduledForPicking |
      | sched6                | Y                     | 10                     |

  Scenario: auto job schedule - External System
    Given deactivate all C_Workplace records
    And metasfresh contains C_Workplaces
      | Identifier | SeqNo | M_Warehouse_ID | MaxPickingJobs | ExternalSystem.Value                     |
      | workplace1 | 10    | wh             | 10             | JobSchedule_System1, JobSchedule_System3 |
      | workplace2 | 20    | wh             | 10             | JobSchedule_System2                      |
    When simple completed order with one line
      | C_Order_ID | C_BPartner_ID | DateOrdered | IsSOTrx | M_Warehouse_ID | InvoiceRule | C_OrderLine_ID | M_Product_ID | QtyEntered | ExternalSystem.Value |
      | so7        | customer      | 2025-04-01  | true    | wh             | I           | so7_l1         | product      | 10         | JobSchedule_System2  |
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute |
      | sched7     | so7_l1         | N             |
    And AD_Scheduler for classname 'de.metas.handlingunits.picking.process.M_ShipmentSchedule_Traffic_Management_assign' is ran once
    And after not more than 60s, picking job schedules are found:
      | M_ShipmentSchedule_ID | C_Workplace_ID | QtyToPick |
      | sched7                | workplace2     | 10        |
    And after not more than 60s, validate shipment schedules:
      | M_ShipmentSchedule_ID | IsScheduledForPicking | QtyScheduledForPicking |
      | sched7                | Y                     | 10                     |