@from:cucumber
@allure.label.epic:E0159_Manufacturing_Planning
@allure.label.feature:F8005_Order_Checkup
@ghActions:run_on_executor7
Feature: Tour planning — PreparationDate derived backward from the shipper's lead time
  ## A shipper with a LeadTimeDays value (field presence = opt-in) makes the order's
  ## PreparationDate = workday(DatePromised, -LeadTimeDays + 1), Mon-Fri business days.
  ## IsFixedPreparationDate='Y' keeps a manually set PreparationDate. No lead time = existing behaviour.

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2022-06-01T13:30:13+02:00[Europe/Berlin]

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
    And contains M_Shippers
      | Identifier | LeadTimeDays |
      | shipper    | 7            |
    And contains M_Shippers
      | Identifier |
      | noLeadTime |




  # ##########################################################################################
  # ##########################################################################################
  # ##########################################################################################

  @from:cucumber
  @Id:S28993_TC4a
  Scenario: Lead time 7 — PreparationDate is 6 working days before DatePromised
    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | DatePromised | M_PricingSystem_ID | C_BPartner_Location_ID | M_Shipper_ID | IsFixedPreparationDate |
      | so         | Y       | customer      | 2022-06-01  | 2026-08-19Z  | ps                 | customerLocation       | shipper      | N                      |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | sol        | so         | product      | 10         |
    Then validate the created orders
      | Identifier | DatePromised | PreparationDate |
      | so         | 2026-08-19   | 2026-08-11      |

  # ##########################################################################################
  # ##########################################################################################
  # ##########################################################################################

  @from:cucumber
  @Id:S28993_TC4b
  Scenario: Lead time 7 with IsFixedPreparationDate=Y — a manually set PreparationDate is kept
    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | DatePromised | PreparationDate | M_PricingSystem_ID | C_BPartner_Location_ID | M_Shipper_ID | IsFixedPreparationDate |
      | so         | Y       | customer      | 2022-06-01  | 2026-08-19Z  | 2026-08-14Z     | ps                 | customerLocation       | shipper      | Y                      |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | sol        | so         | product      | 10         |
    Then validate the created orders
      | Identifier | DatePromised | PreparationDate |
      | so         | 2026-08-19   | 2026-08-14      |

  # ##########################################################################################
  # ##########################################################################################
  # ##########################################################################################

  @from:cucumber
  @Id:S28993_TC4c
  Scenario: Lead time 7 and no C_BPartner_Location_ID — PreparationDate is still derived
    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | DatePromised | M_PricingSystem_ID | M_Shipper_ID | IsFixedPreparationDate |
      | so         | Y       | customer      | 2022-06-01  | 2026-08-19Z  | ps                 | shipper      | N                      |
    Then validate the created orders
      | Identifier | DatePromised | PreparationDate |
      | so         | 2026-08-19   | 2026-08-11      |

  # ##########################################################################################
  # ##########################################################################################
  # ##########################################################################################

  @from:cucumber
  @Id:S28993_TC4d
  Scenario: Shipper without lead time — the existing no-tour fallback applies (PreparationDate = DatePromised)
    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | DatePromised | M_PricingSystem_ID | C_BPartner_Location_ID | M_Shipper_ID |
      | so         | Y       | customer      | 2022-06-01  | 2026-08-19Z  | ps                 | customerLocation       | noLeadTime   |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | sol        | so         | product      | 10         |
    Then validate the created orders
      | Identifier | DatePromised | PreparationDate |
      | so         | 2026-08-19   | 2026-08-19      |

  # ##########################################################################################
  # ##########################################################################################
  # ##########################################################################################

  @from:cucumber
  @Id:S28993_TC4e
  Scenario: Purchase order on a lead-time shipper — the lead time does not apply (sales orders only)
    Given metasfresh contains M_PricingSystems
      | Identifier |
      | ps_po      |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | C_Currency.ISO_Code | Name  | SOTrx | IsTaxIncluded | PricePrecision |
      | pl_po      | ps_po              | EUR                 | pl_po | false | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID |
      | plv_po     | pl_po          |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_po      | plv_po                 | product      | 5.00     | PCE               | Normal                        |
    And metasfresh contains C_BPartners without locations:
      | Identifier | IsVendor | IsCustomer | PO_PricingSystem_ID |
      | vendor     | Y        | N          | ps_po               |
    And metasfresh contains C_BPartner_Locations:
      | Identifier     | C_BPartner_ID | IsShipToDefault | IsBillToDefault |
      | vendorLocation | vendor        | Y               | Y               |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | DatePromised | DocBaseType | M_PricingSystem_ID | C_BPartner_Location_ID | M_Shipper_ID |
      | po         | N       | vendor        | 2022-06-01  | 2026-08-19Z  | POO         | ps_po              | vendorLocation         | shipper      |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | pol        | po         | product      | 10         |
    Then validate the created orders
      | Identifier | DatePromised | PreparationDate |
      | po         | 2026-08-19   | 2026-08-19      |
