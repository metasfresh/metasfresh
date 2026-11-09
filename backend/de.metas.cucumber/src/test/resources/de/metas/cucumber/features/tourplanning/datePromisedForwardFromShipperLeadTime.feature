@from:cucumber
@allure.label.epic:E0159_Manufacturing_Planning
@allure.label.feature:F8005_Order_Checkup
@ghActions:run_on_executor7
Feature: Tour planning — DatePromised derived forward from the shipper's lead time (on save)
  ## With the forward opt-in sysconfig de.metas.tourplanning.LeadTimeForwardCalc.Enabled = Y, a sales order whose shipper has
  ## a LeadTimeDays value gets DatePromised = workday(DateOrdered, +LeadTimeDays) (Mon-Fri business days) when saved.
  ## PreparationDate then derives backward from that DatePromised: workday(DatePromised, -LeadTimeDays + 1).

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2026-08-03T13:30:13+02:00[Europe/Berlin]
    And temporarily set sys config boolean value true for sys config 'de.metas.tourplanning.LeadTimeForwardCalc.Enabled'

    And metasfresh contains M_Products:
      | Identifier |
      | product    |
    And metasfresh contains M_PricingSystems
      | Identifier |
      | ps         |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | C_Currency.ISO_Code | Name  | SOTrx | IsTaxIncluded | PricePrecision |
      | pl_so      | ps                 | EUR                 | pl_so | true  | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID |
      | plv_so     | pl_so          |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_so      | plv_so                 | product      | 10.00    | PCE               | Normal                        |
    And metasfresh contains C_BPartners without locations:
      | Identifier | IsVendor | IsCustomer | M_PricingSystem_ID |
      | customer   | N        | Y          | ps                 |
    And metasfresh contains C_BPartner_Locations:
      | Identifier       | C_BPartner_ID | IsShipToDefault | IsBillToDefault |
      | customerLocation | customer      | Y               | Y               |

  # ##########################################################################################
  # ##########################################################################################
  # ##########################################################################################

  @from:cucumber
  @Id:S28993_TC1
  Scenario Outline: Reference case — order date Mon 2026-08-10, route <route> with lead time <leadDays> days
    Given contains M_Shippers
      | Identifier | LeadTimeDays |
      | shipper    | <leadDays>   |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_PricingSystem_ID | C_BPartner_Location_ID | M_Shipper_ID |
      | so         | Y       | customer      | 2026-08-10  | ps                 | customerLocation       | shipper      |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | sol        | so         | product      | 10         |
    Then validate the created orders
      | Identifier | DateOrdered | DatePromised   | PreparationDate   |
      | so         | 2026-08-10  | <datePromised> | <preparationDate> |

    Examples:
      | route                | leadDays | datePromised | preparationDate |
      | Abholung             | 1        | 2026-08-11   | 2026-08-11      |
      | Abholung FRUEH       | 2        | 2026-08-12   | 2026-08-11      |
      | Frei Bremerhaven     | 2        | 2026-08-12   | 2026-08-11      |
      | per Spedition Nagel  | 2        | 2026-08-12   | 2026-08-11      |
      | Dachser              | 2        | 2026-08-12   | 2026-08-11      |
      | Wiltfang             | 2        | 2026-08-12   | 2026-08-11      |
      | Transthermos         | 7        | 2026-08-19   | 2026-08-11      |
      | 48 Std Nagel         | 3        | 2026-08-13   | 2026-08-11      |
      | 72 Std Nagel         | 4        | 2026-08-14   | 2026-08-11      |

  # ##########################################################################################
  # ##########################################################################################
  # ##########################################################################################

  @from:cucumber
  @Id:S28993_TC5c
  Scenario: Same save changes DateOrdered and PreparationDate — the forward DatePromised is still computed
    ## Simulates the UI callout having set PreparationDate in the same save: the interceptor's
    ## "PreparationDate was set explicitly" guard must not skip the forward DatePromised.
    Given contains M_Shippers
      | Identifier | LeadTimeDays |
      | shipper    | 7            |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_PricingSystem_ID | C_BPartner_Location_ID | M_Shipper_ID |
      | so         | Y       | customer      | 2026-08-10  | ps                 | customerLocation       | shipper      |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | sol        | so         | product      | 10         |
    And validate the created orders
      | Identifier | DatePromised | PreparationDate |
      | so         | 2026-08-19   | 2026-08-11      |

    When update order
      | C_Order_ID | DateOrdered | PreparationDate |
      | so         | 2026-08-17  | 2026-08-18Z     |

    Then validate the created orders
      | Identifier | DateOrdered | DatePromised | PreparationDate |
      | so         | 2026-08-17  | 2026-08-26   | 2026-08-18      |

  # ##########################################################################################
  # ##########################################################################################
  # ##########################################################################################

  @from:cucumber
  @Id:S28993_TC5d
  Scenario: Manual DatePromised edit sticks — DatePromised is not recomputed, PreparationDate derives from it
    Given contains M_Shippers
      | Identifier | LeadTimeDays |
      | shipper    | 7            |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_PricingSystem_ID | C_BPartner_Location_ID | M_Shipper_ID |
      | so         | Y       | customer      | 2026-08-10  | ps                 | customerLocation       | shipper      |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | sol        | so         | product      | 10         |

    When update order
      | C_Order_ID | DatePromised |
      | so         | 2026-08-28Z  |

    Then validate the created orders
      | Identifier | DateOrdered | DatePromised | PreparationDate |
      | so         | 2026-08-10  | 2026-08-28   | 2026-08-20      |

  # ##########################################################################################
  # ##########################################################################################
  # ##########################################################################################

  @from:cucumber
  @Id:S28993_TC5e
  Scenario: IsFixedDatePromised=Y — a DateOrdered change does not move DatePromised
    Given contains M_Shippers
      | Identifier | LeadTimeDays |
      | shipper    | 7            |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | DatePromised | M_PricingSystem_ID | C_BPartner_Location_ID | M_Shipper_ID | IsFixedDatePromised |
      | so         | Y       | customer      | 2026-08-10  | 2026-08-21Z  | ps                 | customerLocation       | shipper      | Y                   |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | sol        | so         | product      | 10         |
    Then validate the created orders
      | Identifier | DateOrdered | DatePromised | PreparationDate |
      | so         | 2026-08-10  | 2026-08-21   | 2026-08-13      |
