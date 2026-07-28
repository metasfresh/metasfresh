@from:cucumber
@allure.label.epic:E0140_Purchasing
@allure.label.feature:F00600_Purchase_Order
@ghActions:run_on_executor7
Feature: Purchase order — PreparationDate from transport days
  ## PreparationDate = DatePromised − max(DeliveryTime_Promised or PO_TransportDays across all order lines).
  ## Product-level DeliveryTime_Promised overrides vendor-level PO_TransportDays.
  ## With multiple lines, the maximum transport days across all products wins.

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2022-06-01T13:30:13+02:00[Europe/Berlin]

    And metasfresh contains M_Products:
      | Identifier |
      | productA   |
      | productB   |
      | productC   |
      | productD   |

    And metasfresh contains M_PricingSystems
      | Identifier |
      | ps         |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | C_Currency.ISO_Code | SOTrx | IsTaxIncluded | PricePrecision |
      | pl         | ps                 | EUR                 | false | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID |
      | plv        | pl             |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_A       | plv                    | productA     | 10.00    | PCE               | Normal                        |
      | pp_B       | plv                    | productB     | 10.00    | PCE               | Normal                        |
      | pp_C       | plv                    | productC     | 10.00    | PCE               | Normal                        |
      | pp_D       | plv                    | productD     | 10.00    | PCE               | Normal                        |

    And metasfresh contains C_BPartners without locations:
      | Identifier | IsVendor | M_PricingSystem_ID | PO_TransportDays |
      | vendor     | Y        | ps                 | 5                |
    And metasfresh contains C_BPartner_Locations:
      | Identifier     | C_BPartner_ID | IsShipToDefault | IsBillToDefault |
      | vendorLocation | vendor        | Y               | Y               |

  @from:cucumber
  Scenario: PreparationDate uses vendor-level PO_TransportDays when no product-specific entry exists
    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | DatePromised | M_PricingSystem_ID | C_BPartner_Location_ID |
      | order_1    | N       | vendor        | 2022-06-01  | 2022-06-15Z  | ps                 | vendorLocation         |
    And metasfresh contains C_OrderLines:
      | Identifier  | C_Order_ID | M_Product_ID | QtyEntered |
      | orderLine_1 | order_1    | productA     | 10         |

    Then validate the created orders
      | Identifier | PreparationDate |
      | order_1    | 2022-06-10      |

  @from:cucumber
  Scenario: Product-specific DeliveryTime_Promised overrides vendor-level PO_TransportDays
    Given metasfresh contains C_BPartner_Product
      | C_BPartner_ID | M_Product_ID | DeliveryTime_Promised |
      | vendor        | productB     | 3                     |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | DatePromised | M_PricingSystem_ID | C_BPartner_Location_ID |
      | order_2    | N       | vendor        | 2022-06-01  | 2022-06-15Z  | ps                 | vendorLocation         |
    And metasfresh contains C_OrderLines:
      | Identifier  | C_Order_ID | M_Product_ID | QtyEntered |
      | orderLine_2 | order_2    | productB     | 10         |

    Then validate the created orders
      | Identifier | PreparationDate |
      | order_2    | 2022-06-12      |

  @from:cucumber
  Scenario: With multiple lines, PreparationDate uses the maximum transport days across all products
    Given metasfresh contains C_BPartner_Product
      | C_BPartner_ID | M_Product_ID | DeliveryTime_Promised |
      | vendor        | productC     | 3                     |
      | vendor        | productD     | 7                     |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | DatePromised | M_PricingSystem_ID | C_BPartner_Location_ID |
      | order_3    | N       | vendor        | 2022-06-01  | 2022-06-15Z  | ps                 | vendorLocation         |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | orderLineC | order_3    | productC     | 10         |
      | orderLineD | order_3    | productD     | 10         |

    Then validate the created orders
      | Identifier | PreparationDate |
      | order_3    | 2022-06-08      |
