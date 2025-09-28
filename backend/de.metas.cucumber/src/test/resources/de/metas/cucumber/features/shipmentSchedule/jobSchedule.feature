@from:cucumber
Feature: Picking job schedule

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2023-06-01T13:30:13+01:00[Europe/Berlin]
    And deactivate all M_ShipmentSchedule records
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    # we don't care about the export, and don't want it to interject in any way
    And set sys config int value 999999 for sys config de.metas.inoutcandidate.M_ShipmentSchedule.canBeExportedAfterSeconds

    # set up masterdata
    And metasfresh contains M_Products:
      | Identifier |
      | product    |
    And metasfresh contains M_PricingSystems
      | Identifier |
      | ps_S0271   |
    And metasfresh contains M_PriceLists
      | Identifier  | M_PricingSystem_ID | C_Country.CountryCode | C_Currency.ISO_Code | SOTrx | IsTaxIncluded |
      | pl_so_S0271 | ps_S0271           | DE                    | EUR                 | true  | false         |
    And metasfresh contains M_PriceList_Versions
      | Identifier   | M_PriceList_ID |
      | plv_so_S0271 | pl_so_S0271    |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_TaxCategory_ID | C_UOM_ID.X12DE355 |
      | plv_so_S0271           | product      | 10.0     | Normal           | PCE               |

    And metasfresh contains C_BPartners:
      | Identifier | M_PricingSystem_ID | IsCustomer | CompanyName  | AD_Language | DeliveryRule |
      | customer   | ps_S0271           | Y          | customer_cmp | de_DE       | F            |

    And load M_Warehouse:
      | M_Warehouse_ID | Value        |
      | warehouseStd   | StdWarehouse |

    And metasfresh contains C_Workplaces
      | Identifier | M_Warehouse_ID |
      | workplace1 | warehouseStd   |
      | workplace2 | warehouseStd   |


  @Id:S0271_010
  Scenario: Schedule & check
    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered |
      | so1        | true    | customer                 | 2023-05-31  |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | so1_l1     | so1                   | product                 | 10         |
    And the order identified by so1 is completed

    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | sched1     | so1_l1                    | N             |
    And after not more than 60s, validate shipment schedules:
      | M_ShipmentSchedule_ID.Identifier | OPT.QtyToDeliver | OPT.QtyDelivered | OPT.QtyOrdered | OPT.QtyOnHand | OPT.Processed | OPT.DeliveryRule | IsScheduledForPicking | QtyScheduledForPicking |
      | sched1                           | 10               | 0                | 10             | 0             | false         | F                | N                     | 0                      |

    And create or update picking job schedules
      | M_ShipmentSchedule_ID | C_Workplace_ID | QtyToPick |
      | sched1                | workplace1     | 3         |
    And after not more than 60s, validate shipment schedules:
      | M_ShipmentSchedule_ID.Identifier | OPT.QtyToDeliver | OPT.QtyDelivered | OPT.QtyOrdered | OPT.QtyOnHand | OPT.Processed | OPT.DeliveryRule | IsScheduledForPicking | QtyScheduledForPicking |
      | sched1                           | 10               | 0                | 10             | 0             | false         | F                | Y                     | 3                      |
