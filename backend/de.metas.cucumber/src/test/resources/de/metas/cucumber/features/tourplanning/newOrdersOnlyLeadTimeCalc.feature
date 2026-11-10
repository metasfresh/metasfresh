@from:cucumber
@allure.label.epic:E0159_Manufacturing_Planning
@allure.label.feature:F8005_Order_Checkup
@ghActions:run_on_executor7
Feature: Tour planning — lead-time calc applies to new / edited orders only (enabling is non-retroactive)
  ## Enabling the feature — a delivery route (Lieferweg) gaining a lead time plus the forward sysconfig
  ## switched on — must NOT recompute orders that already exist. Only a subsequent create or edit of an
  ## order triggers the forward/backward calculation.

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2026-08-03T13:30:13+02:00[Europe/Berlin]

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
  @Id:S28993_TC7a
  Scenario: Enabling the feature does not recompute an order that already existed
    Given temporarily set sys config boolean value false for sys config 'de.metas.tourplanning.LeadTimeForwardCalc.Enabled'
    And contains M_Shippers
      | Identifier |
      | shipper    |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | DatePromised | M_PricingSystem_ID | C_BPartner_Location_ID | M_Shipper_ID |
      | so         | Y       | customer      | 2026-08-10  | 2026-08-24Z  | ps                 | customerLocation       | shipper      |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | sol        | so         | product      | 10         |
    And validate the created orders
      | Identifier | DateOrdered | DatePromised | PreparationDate |
      | so         | 2026-08-10  | 2026-08-24   | 2026-08-24      |

    # Simulate the enable: the route gains a lead time and the forward calc is switched on.
    When update M_Shipper:
      | Identifier | LeadTimeDays |
      | shipper    | 7            |
    And temporarily set sys config boolean value true for sys config 'de.metas.tourplanning.LeadTimeForwardCalc.Enabled'

    # The existing order was never re-saved, so its dates stay exactly as they were.
    Then validate the created orders
      | Identifier | DateOrdered | DatePromised | PreparationDate |
      | so         | 2026-08-10  | 2026-08-24   | 2026-08-24      |

  # ##########################################################################################
  # ##########################################################################################
  # ##########################################################################################

  @from:cucumber
  @Id:S28993_TC7b
  Scenario: After enabling, changing an existing order's route triggers the calc
    Given temporarily set sys config boolean value true for sys config 'de.metas.tourplanning.LeadTimeForwardCalc.Enabled'
    And contains M_Shippers
      | Identifier    | LeadTimeDays |
      | shipperNoLead |              |
      | shipperLead   | 7            |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | DatePromised | M_PricingSystem_ID | C_BPartner_Location_ID | M_Shipper_ID  |
      | so         | Y       | customer      | 2026-08-10  | 2026-08-24Z  | ps                 | customerLocation       | shipperNoLead |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | sol        | so         | product      | 10         |
    And validate the created orders
      | Identifier | DateOrdered | DatePromised | PreparationDate |
      | so         | 2026-08-10  | 2026-08-24   | 2026-08-24      |

    # The order's route changes to a lead-time route -> forward + backward calc now applies.
    When update order
      | C_Order_ID | M_Shipper_ID |
      | so         | shipperLead  |
    Then validate the created orders
      | Identifier | DateOrdered | DatePromised | PreparationDate |
      | so         | 2026-08-10  | 2026-08-19   | 2026-08-11      |
