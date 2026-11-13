@from:cucumber
@allure.label.epic:E0159_Manufacturing_Planning
@allure.label.feature:F8005_Order_Checkup
@ghActions:run_on_executor7
Feature: Tour planning — PreparationDate when the sales order's DatePromised changes
  ## Without a tour, PreparationDate follows DatePromised (no-tour fallback).
  ## With a tour delivery day, PreparationDate stays the delivery day's DeliveryDate.
  ## Neither case depends on the shipper.

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
      | Identifier |
      | shipper    |


  # ##########################################################################################
  # ##########################################################################################
  # ##########################################################################################

  @from:cucumber
  @Id:S28993_TC5a
  Scenario: No tour — changing DatePromised moves PreparationDate to the new DatePromised
    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | DatePromised | M_PricingSystem_ID | C_BPartner_Location_ID | M_Shipper_ID |
      | so         | Y       | customer      | 2022-06-01  | 2022-06-15Z  | ps                 | customerLocation       | shipper      |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | sol        | so         | product      | 10         |
    And validate the created orders
      | Identifier | DatePromised | PreparationDate |
      | so         | 2022-06-15   | 2022-06-15      |

    When update order
      | C_Order_ID | DatePromised |
      | so         | 2022-06-20Z  |

    Then validate the created orders
      | Identifier | DatePromised | PreparationDate |
      | so         | 2022-06-20   | 2022-06-20      |


  # ##########################################################################################
  # ##########################################################################################
  # ##########################################################################################

  @from:cucumber
  @Id:S28993_TC5b
  Scenario: Tour delivery day — changing DatePromised keeps PreparationDate from the delivery day
    Given metasfresh contains M_Tours:
      | Identifier |
      | tour       |
    And metasfresh contains M_TourVersions:
      | Identifier  | M_Tour_ID | ValidFrom  |
      | tourVersion | tour      | 2022-01-01 |
    And metasfresh contains M_TourVersionLines:
      | Identifier      | M_TourVersion_ID | C_BPartner_Location_ID | IsToBeFetched | SeqNo |
      | tourVersionLine | tourVersion      | customerLocation       | false         | 10    |
    And metasfresh contains M_DeliveryDays:
      | Identifier  | C_BPartner_Location_ID | M_Tour_ID | M_TourVersion_ID | DeliveryDate         | DeliveryDateTimeMax  | IsToBeFetched |
      | deliveryDay | customerLocation       | tour      | tourVersion      | 2022-06-13T06:00:00Z | 2022-06-14T12:00:00Z | false         |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | DatePromised | M_PricingSystem_ID | C_BPartner_Location_ID | M_Shipper_ID |
      | so         | Y       | customer      | 2022-06-01  | 2022-06-15Z  | ps                 | customerLocation       | shipper      |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | sol        | so         | product      | 10         |
    And validate the created orders
      | Identifier | DatePromised | PreparationDate |
      | so         | 2022-06-15   | 2022-06-13      |

    When update order
      | C_Order_ID | DatePromised |
      | so         | 2022-06-16Z  |

    Then validate the created orders
      | Identifier | DatePromised | PreparationDate |
      | so         | 2022-06-16   | 2022-06-13      |


  # ##########################################################################################
  # ##########################################################################################
  # ##########################################################################################

  @from:cucumber
  @Id:S28993_TC10
  Scenario: No lead time — a DateOrdered change does not recompute a manually set PreparationDate (AC5a legacy unchanged)
    ## A no-lead-time order with a manually set PreparationDate: changing only DateOrdered must NOT re-run the
    ## tour/fallback recompute. The interceptor derives PreparationDate only when a date/location that drives it
    ## changes (C_BPartner_Location_ID / DatePromised / PreparationDate), not on a bare DateOrdered edit.
    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | DatePromised | PreparationDate | M_PricingSystem_ID | C_BPartner_Location_ID | M_Shipper_ID |
      | so         | Y       | customer      | 2022-06-01  | 2022-06-20Z  | 2022-06-10Z     | ps                 | customerLocation       | shipper      |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | sol        | so         | product      | 10         |
    And validate the created orders
      | Identifier | DateOrdered | DatePromised | PreparationDate |
      | so         | 2022-06-01  | 2022-06-20   | 2022-06-10      |

    When update order
      | C_Order_ID | DateOrdered |
      | so         | 2022-06-08  |

    Then validate the created orders
      | Identifier | DateOrdered | DatePromised | PreparationDate |
      | so         | 2022-06-08  | 2022-06-20   | 2022-06-10      |
