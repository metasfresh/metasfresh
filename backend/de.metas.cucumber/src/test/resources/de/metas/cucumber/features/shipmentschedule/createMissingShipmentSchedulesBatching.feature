@from:cucumber
@allure.label.epic:E0100_Sales
@allure.label.feature:F00130_Shipment_Schedule
@ghActions:run_on_executor7
Feature: create missing shipment schedules in bounded batches
## F00130: Shipment Schedule (Lieferdisposition)
# A large backlog of missing shipment schedules is created in bounded batches per
# workpackage run, re-enqueueing a follow-up workpackage for the remaining work,
# instead of creating everything in one unbounded run.

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2024-01-15T13:30:13+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And set sys config int value 2 for sys config de.metas.inoutcandidate.async.CreateMissingShipmentSchedulesWorkpackageProcessor.MaxToProcess
    And metasfresh contains M_PricingSystems
      | Identifier |
      | ps_1       |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | SOTrx |
      | pl_1       | ps_1                          | DE                        | EUR                 | true  |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier |
      | plv_1      | pl_1                      |
    And metasfresh contains M_Products:
      | Identifier |
      | product_1  |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_1       | plv_1                             | product_1               | 10.0     | PCE               | Normal                        |
    And metasfresh contains C_BPartners:
      | Identifier | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | bpartner_1 | N            | Y              | ps_1                          |

  @from:cucumber
  @allure.label.epic:E0100_Sales
  @allure.label.feature:F00130_Shipment_Schedule
  @Id:S31050_TC1
  Scenario: one workpackage run creates a bounded batch and re-enqueues the rest
  _Given a sales order with 5 order lines is completed, enqueueing one CreateMissingShipmentSchedules workpackage
  _And the MaxToProcess sysconfig for that processor is 2
  _When the next CreateMissingShipmentSchedules workpackage is processed
  _Then exactly 2 shipment schedules exist for the order, and a follow-up workpackage is re-enqueued for the rest
  _When the two remaining follow-up workpackages are processed
  _Then all 5 shipment schedules exist for the order, and no CreateMissingShipmentSchedules workpackage is pending

    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered |
      | order_1    | true    | bpartner_1               | 2024-01-15  |
    And metasfresh contains C_OrderLines:
      | Identifier  | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | orderLine_1 | order_1               | product_1               | 10         |
      | orderLine_2 | order_1               | product_1               | 10         |
      | orderLine_3 | order_1               | product_1               | 10         |
      | orderLine_4 | order_1               | product_1               | 10         |
      | orderLine_5 | order_1               | product_1               | 10         |

    When the order identified by order_1 is completed

    Then there is 1 pending "CreateMissingShipmentSchedules" workpackage

    When the next CreateMissingShipmentSchedules workpackage is processed

    Then after not more than 30s, M_ShipmentSchedules are found:
      | Identifier         | C_OrderLine_ID.Identifier |
      | shipmentSchedule_1 | orderLine_1               |
      | shipmentSchedule_2 | orderLine_2               |
    And there is 1 pending "CreateMissingShipmentSchedules" workpackage

    When the next CreateMissingShipmentSchedules workpackage is processed
    And the next CreateMissingShipmentSchedules workpackage is processed

    Then after not more than 30s, M_ShipmentSchedules are found:
      | Identifier         | C_OrderLine_ID.Identifier |
      | shipmentSchedule_1 | orderLine_1               |
      | shipmentSchedule_2 | orderLine_2               |
      | shipmentSchedule_3 | orderLine_3               |
      | shipmentSchedule_4 | orderLine_4               |
      | shipmentSchedule_5 | orderLine_5               |
    And there are 0 pending "CreateMissingShipmentSchedules" workpackages
