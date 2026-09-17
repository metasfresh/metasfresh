@from:cucumber
@allure.label.epic:E0140_Purchasing
@allure.label.feature:F00600_Purchase_Order
@ghActions:run_on_executor7
Feature: Tour planning — PreparationDate from delivery day or fallback
  ## When a delivery day is configured for the bpartner location, PreparationDate = M_DeliveryDay.DeliveryDate.
  ## When no delivery day matches, OrderDeliveryDayBL falls back to DatePromised (SO)
  ## or DatePromised − PO_TransportDays (PO).

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
      | pl_po      | ps                 | EUR                 | pl_po | false | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID |
      | plv_so     | pl_so          |
      | plv_po     | pl_po          |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_so      | plv_so                 | product      | 10.00    | PCE               | Normal                        |
      | pp_po      | plv_po                 | product      | 10.00    | PCE               | Normal                        |
    And metasfresh contains C_BPartners without locations:
      | Identifier | IsVendor | IsCustomer | M_PricingSystem_ID |
      | customer   | N        | Y          | ps                 |
    And metasfresh contains C_BPartner_Locations:
      | Identifier       | C_BPartner_ID | IsShipToDefault | IsBillToDefault |
      | customerLocation | customer      | Y               | Y               |
    And metasfresh contains C_BPartners without locations:
      | Identifier | IsVendor | IsCustomer | M_PricingSystem_ID | PO_TransportDays |
      | vendor     | Y        | N          | ps                 | 5                |
    And metasfresh contains C_BPartner_Locations:
      | Identifier     | C_BPartner_ID | IsShipToDefault | IsBillToDefault |
      | vendorLocation | vendor        | Y               | Y               |

  @from:cucumber
  Scenario: SO PreparationDate equals DatePromised when no delivery day is configured
    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | DatePromised | M_PricingSystem_ID | C_BPartner_Location_ID |
      | so         | Y       | customer      | 2022-06-01  | 2022-06-15Z  | ps                 | customerLocation       |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | sol        | so         | product      | 10         |

    Then validate the created orders
      | Identifier | PreparationDate |
      | so         | 2022-06-15      |

  @from:cucumber
  Scenario: PO PreparationDate equals DatePromised minus vendor PO_TransportDays when no delivery day is configured
    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | DatePromised | M_PricingSystem_ID | C_BPartner_Location_ID |
      | po         | N       | vendor        | 2022-06-01  | 2022-06-15Z  | ps                 | vendorLocation         |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | pol        | po         | product      | 10         |

    Then validate the created orders
      | Identifier | PreparationDate |
      | po         | 2022-06-10      |

  @from:cucumber
  Scenario: SO PreparationDate equals the matching delivery day DeliveryDate when a delivery day is configured
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
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | DatePromised | M_PricingSystem_ID | C_BPartner_Location_ID |
      | so         | Y       | customer      | 2022-06-01  | 2022-06-15Z  | ps                 | customerLocation       |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | sol        | so         | product      | 10         |

    Then validate the created orders
      | Identifier | PreparationDate |
      | so         | 2022-06-13      |

  @from:cucumber
  Scenario: PO PreparationDate equals the matching delivery day DeliveryDate when a delivery day is configured
    Given metasfresh contains M_Tours:
      | Identifier |
      | tour       |
    And metasfresh contains M_TourVersions:
      | Identifier  | M_Tour_ID | ValidFrom  |
      | tourVersion | tour      | 2022-01-01 |
    And metasfresh contains M_TourVersionLines:
      | Identifier      | M_TourVersion_ID | C_BPartner_Location_ID | IsToBeFetched | SeqNo |
      | tourVersionLine | tourVersion      | vendorLocation         | true          | 10    |
    And metasfresh contains M_DeliveryDays:
      | Identifier  | C_BPartner_Location_ID | M_Tour_ID | M_TourVersion_ID | DeliveryDate         | DeliveryDateTimeMax  | IsToBeFetched |
      | deliveryDay | vendorLocation         | tour      | tourVersion      | 2022-06-11T06:00:00Z | 2022-06-14T12:00:00Z | true          |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | DatePromised | M_PricingSystem_ID | C_BPartner_Location_ID |
      | po         | N       | vendor        | 2022-06-01  | 2022-06-15Z  | ps                 | vendorLocation         |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | pol        | po         | product      | 10         |

    Then validate the created orders
      | Identifier | PreparationDate |
      | po         | 2022-06-11      |
