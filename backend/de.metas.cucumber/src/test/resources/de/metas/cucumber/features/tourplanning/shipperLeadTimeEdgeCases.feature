@from:cucumber
@allure.label.epic:E0159_Manufacturing_Planning
@allure.label.feature:F8005_Order_Checkup
@ghActions:run_on_executor7
Feature: Tour planning — shipper lead-time edge cases (weekend straddle, fix-flags, forward gate off)
  ## Edge cases on top of the forward + backward lead-time calculation:
  ## weekend straddle, IsFixedDatePromised / IsFixedPreparationDate, and the forward opt-in sysconfig switched off.
  ## The forward sysconfig is set per scenario; the backward PreparationDate is gated by the shipper's LeadTimeDays only.

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
  @Id:S28993_TC3
  Scenario: Weekend straddle — order Fri 2026-08-14 with lead time 2 gives DatePromised Tue and PreparationDate Mon
    Given temporarily set sys config boolean value true for sys config 'de.metas.tourplanning.LeadTimeForwardCalc.Enabled'
    And contains M_Shippers
      | Identifier | LeadTimeDays |
      | shipper    | 2            |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_PricingSystem_ID | C_BPartner_Location_ID | M_Shipper_ID |
      | so         | Y       | customer      | 2026-08-14  | ps                 | customerLocation       | shipper      |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | sol        | so         | product      | 10         |
    Then validate the created orders
      | Identifier | DateOrdered | DatePromised | PreparationDate |
      | so         | 2026-08-14  | 2026-08-18   | 2026-08-17      |

  # ##########################################################################################
  # ##########################################################################################
  # ##########################################################################################

  @from:cucumber
  @Id:S28993_TC4f
  Scenario: IsFixedDatePromised=Y — changing DateOrdered keeps DatePromised, PreparationDate derives from it
    Given temporarily set sys config boolean value true for sys config 'de.metas.tourplanning.LeadTimeForwardCalc.Enabled'
    And contains M_Shippers
      | Identifier | LeadTimeDays |
      | shipper    | 7            |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | DatePromised | M_PricingSystem_ID | C_BPartner_Location_ID | M_Shipper_ID | IsFixedDatePromised |
      | so         | Y       | customer      | 2026-08-10  | 2026-08-21Z  | ps                 | customerLocation       | shipper      | Y                   |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | sol        | so         | product      | 10         |
    And validate the created orders
      | Identifier | DateOrdered | DatePromised | PreparationDate |
      | so         | 2026-08-10  | 2026-08-21   | 2026-08-13      |

    When update order
      | C_Order_ID | DateOrdered |
      | so         | 2026-08-17  |

    Then validate the created orders
      | Identifier | DateOrdered | DatePromised | PreparationDate |
      | so         | 2026-08-17  | 2026-08-21   | 2026-08-13      |

  # ##########################################################################################
  # ##########################################################################################
  # ##########################################################################################

  @from:cucumber
  @Id:S28993_TC4g
  Scenario: IsFixedPreparationDate=Y — changing DateOrdered recomputes DatePromised but keeps the manual PreparationDate
    ## DatePromised is supplied explicitly at creation (= the forward value for DateOrdered 2026-08-10, lead 7); the
    ## create-time guard preserves a provided DatePromised, so the DateOrdered update below is what exercises the recompute.
    Given temporarily set sys config boolean value true for sys config 'de.metas.tourplanning.LeadTimeForwardCalc.Enabled'
    And contains M_Shippers
      | Identifier | LeadTimeDays |
      | shipper    | 7            |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | DatePromised | PreparationDate | M_PricingSystem_ID | C_BPartner_Location_ID | M_Shipper_ID | IsFixedPreparationDate |
      | so         | Y       | customer      | 2026-08-10  | 2026-08-19Z  | 2026-08-14Z     | ps                 | customerLocation       | shipper      | Y                      |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | sol        | so         | product      | 10         |
    And validate the created orders
      | Identifier | DateOrdered | DatePromised | PreparationDate |
      | so         | 2026-08-10  | 2026-08-19   | 2026-08-14      |

    When update order
      | C_Order_ID | DateOrdered |
      | so         | 2026-08-17  |

    Then validate the created orders
      | Identifier | DateOrdered | DatePromised | PreparationDate |
      | so         | 2026-08-17  | 2026-08-26   | 2026-08-14      |

  # ##########################################################################################
  # ##########################################################################################
  # ##########################################################################################

  @from:cucumber
  @Id:S28993_TC6
  Scenario: Forward gate off — a DateOrdered change does not overwrite DatePromised, backward PreparationDate still derives
    Given temporarily set sys config boolean value false for sys config 'de.metas.tourplanning.LeadTimeForwardCalc.Enabled'
    And contains M_Shippers
      | Identifier | LeadTimeDays |
      | shipper    | 7            |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | DatePromised | M_PricingSystem_ID | C_BPartner_Location_ID | M_Shipper_ID |
      | so         | Y       | customer      | 2026-08-10  | 2026-08-28Z  | ps                 | customerLocation       | shipper      |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | sol        | so         | product      | 10         |
    And validate the created orders
      | Identifier | DateOrdered | DatePromised | PreparationDate |
      | so         | 2026-08-10  | 2026-08-28   | 2026-08-20      |

    When update order
      | C_Order_ID | DateOrdered |
      | so         | 2026-08-17  |

    Then validate the created orders
      | Identifier | DateOrdered | DatePromised | PreparationDate |
      | so         | 2026-08-17  | 2026-08-28   | 2026-08-20      |

    When update order
      | C_Order_ID | DatePromised |
      | so         | 2026-09-04Z  |

    Then validate the created orders
      | Identifier | DateOrdered | DatePromised | PreparationDate |
      | so         | 2026-08-17  | 2026-09-04   | 2026-08-27      |

  # ##########################################################################################
  # ##########################################################################################
  # ##########################################################################################

  @from:cucumber
  @Id:S28993_TC8
  Scenario: Zero lead time — DatePromised = DateOrdered and PreparationDate never lands after DatePromised
    ## A route with LeadTimeDays 0: forward gives DatePromised = DateOrdered (workday +0); the backward step is
    ## clamped to <= 0 so PreparationDate = DatePromised, never DatePromised + 1 (invariant: PreparationDate <= DatePromised).
    Given temporarily set sys config boolean value true for sys config 'de.metas.tourplanning.LeadTimeForwardCalc.Enabled'
    And contains M_Shippers
      | Identifier | LeadTimeDays |
      | shipper    | 0            |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_PricingSystem_ID | C_BPartner_Location_ID | M_Shipper_ID |
      | so         | Y       | customer      | 2026-08-10  | ps                 | customerLocation       | shipper      |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | sol        | so         | product      | 10         |
    Then validate the created orders
      | Identifier | DateOrdered | DatePromised | PreparationDate |
      | so         | 2026-08-10  | 2026-08-10   | 2026-08-10      |


  # ##########################################################################################
  # ##########################################################################################
  # ##########################################################################################

  @from:cucumber
  @Id:S28993_TC11
  Scenario: Negative lead time — forward is clamped so DatePromised is never before DateOrdered
    ## A route mis-configured with a negative LeadTimeDays must not place DatePromised before DateOrdered:
    ## the forward step is clamped to >= 0, so DatePromised = DateOrdered, and the backward step (clamped <= 0)
    ## keeps PreparationDate = DatePromised. Negative behaves like 0 (invariant: PreparationDate <= DatePromised).
    Given temporarily set sys config boolean value true for sys config 'de.metas.tourplanning.LeadTimeForwardCalc.Enabled'
    And contains M_Shippers
      | Identifier | LeadTimeDays |
      | shipper    | -2           |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_PricingSystem_ID | C_BPartner_Location_ID | M_Shipper_ID |
      | so         | Y       | customer      | 2026-08-10  | ps                 | customerLocation       | shipper      |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | sol        | so         | product      | 10         |
    Then validate the created orders
      | Identifier | DateOrdered | DatePromised | PreparationDate |
      | so         | 2026-08-10  | 2026-08-10   | 2026-08-10      |
