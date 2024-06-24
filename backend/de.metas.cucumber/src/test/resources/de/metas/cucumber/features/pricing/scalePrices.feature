@from:cucumber
@ghActions:run_on_executor6
Feature: scale prices

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2022-05-17T13:30:13+01:00[Europe/Berlin]

    And metasfresh contains M_PricingSystems
      | Identifier           | Name                 | Value                |
      | defaultPricingSystem | defaultPricingSystem | defaultPricingSystem |
    And metasfresh contains M_PriceLists
      | Identifier       | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name           | SOTrx | IsTaxIncluded | PricePrecision |
      | defaultPriceList | defaultPricingSystem          | DE                        | EUR                 | PriceListName1 | true  | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name     | ValidFrom  |
      | defaultPLV | defaultPriceList          | PLVName1 | 2022-05-11 |
    And metasfresh contains C_BPartners:
      | Identifier | Name         | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | bpartner_1 | BPartnerTest | Y              | defaultPricingSystem          |

  @from:cucumber
  @Id:S0144.2_130
  Scenario: scale price with 'Use scale price' set on product price
    Given metasfresh contains M_Products:
      | Identifier               | Name                     |
      | salesProduct_180520225_1 | salesProduct_180520225_1 |

    And metasfresh contains M_HU_PI:
      | M_HU_PI_ID.Identifier | Name          |
      | packingItemTU         | packingItemTU |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID.Identifier | M_HU_PI_ID.Identifier | Name             | HU_UnitType | IsCurrent |
      | packingVersionTU              | packingItemTU         | packingVersionTU | TU          | Y         |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID.Identifier | M_HU_PI_Version_ID.Identifier | Qty | ItemType |
      | huPiItemTU                 | packingVersionTU              | 0   | MI       |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID.Identifier | M_HU_PI_Item_ID.Identifier | M_Product_ID.Identifier  | Qty | ValidFrom  |
      | huPIItemProduct_180520225_1        | huPiItemTU                 | salesProduct_180520225_1 | 10  | 2022-05-10 |

    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier  | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName | OPT.UseScalePrice | OPT.M_HU_PI_Item_Product_ID.Identifier |
      | pp_1       | defaultPLV                        | salesProduct_180520225_1 | 5.00     | PCE               | Normal                        | S                 | huPIItemProduct_180520225_1            |
    And metasfresh contains M_ProductScalePrices:
      | Identifier | M_ProductPrice_ID | PriceLimit | PriceList | PriceStd | Qty |
      | ps_1       | pp_1              | 0          | 0         | 5        | 1   |
      | ps_2       | pp_1              | 0          | 0         | 4.5      | 10  |
      | ps_3       | pp_1              | 0          | 0         | 4        | 100 |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered |
      | o_1        | true    | bpartner_1               | 2022-05-17  |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier  | QtyEntered | OPT.M_HU_PI_Item_Product_ID.Identifier |
      | ol_1       | o_1                   | salesProduct_180520225_1 | 8          | huPIItemProduct_180520225_1            |
      | ol_2       | o_1                   | salesProduct_180520225_1 | 15         | huPIItemProduct_180520225_1            |
      | ol_3       | o_1                   | salesProduct_180520225_1 | 150        | huPIItemProduct_180520225_1            |

    And validate C_OrderLine:
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | OPT.DateOrdered | M_Product_ID.Identifier  | QtyOrdered | qtydelivered | qtyinvoiced | price | discount | currencyCode | processed | OPT.M_HU_PI_Item_Product_ID.Identifier |
      | ol_1                      | o_1                   | 2022-05-17      | salesProduct_180520225_1 | 8          | 0            | 0           | 5     | 0        | EUR          | false     | huPIItemProduct_180520225_1            |
      | ol_2                      | o_1                   | 2022-05-17      | salesProduct_180520225_1 | 15         | 0            | 0           | 4.5   | 0        | EUR          | false     | huPIItemProduct_180520225_1            |
      | ol_3                      | o_1                   | 2022-05-17      | salesProduct_180520225_1 | 150        | 0            | 0           | 4     | 0        | EUR          | false     | huPIItemProduct_180520225_1            |

  @from:cucumber
  Scenario: scale price with 'Don't use scale price' set on product price
    Given metasfresh contains M_Products:
      | Identifier               | Name                     |
      | salesProduct_180520225_2 | salesProduct_180520225_2 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier  | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName | OPT.UseScalePrice |
      | pp_1       | defaultPLV                        | salesProduct_180520225_2 | 6.00     | PCE               | Normal                        | N                 |
    And metasfresh contains M_ProductScalePrices:
      | Identifier | M_ProductPrice_ID | PriceLimit | PriceList | PriceStd | Qty |
      | ps_1       | pp_1              | 0          | 0         | 5        | 1   |
      | ps_2       | pp_1              | 0          | 0         | 4.5      | 10  |
      | ps_3       | pp_1              | 0          | 0         | 4        | 100 |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered |
      | o_1        | true    | bpartner_1               | 2022-05-17  |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier  | QtyEntered |
      | ol_1       | o_1                   | salesProduct_180520225_2 | 8          |
      | ol_2       | o_1                   | salesProduct_180520225_2 | 15         |
      | ol_3       | o_1                   | salesProduct_180520225_2 | 150        |

    Then validate C_OrderLine:
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | OPT.DateOrdered | M_Product_ID.Identifier  | QtyOrdered | qtydelivered | qtyinvoiced | price | discount | currencyCode | processed |
      | ol_1                      | o_1                   | 2022-05-17      | salesProduct_180520225_2 | 8          | 0            | 0           | 6     | 0        | EUR          | false     |
      | ol_2                      | o_1                   | 2022-05-17      | salesProduct_180520225_2 | 15         | 0            | 0           | 6     | 0        | EUR          | false     |
      | ol_3                      | o_1                   | 2022-05-17      | salesProduct_180520225_2 | 150        | 0            | 0           | 6     | 0        | EUR          | false     |


  @from:cucumber
  Scenario: scale price with 'Use scale price, fallback product price' set on product price
    Given metasfresh contains M_Products:
      | Identifier               | Name                     |
      | salesProduct_180520225_3 | salesProduct_180520225_3 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier  | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName | OPT.UseScalePrice |
      | pp_1       | defaultPLV                        | salesProduct_180520225_3 | 5.00     | PCE               | Normal                        | Y                 |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered |
      | o_1        | true    | bpartner_1               | 2022-05-17  |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier  | QtyEntered |
      | ol_1       | o_1                   | salesProduct_180520225_3 | 8          |
      | ol_2       | o_1                   | salesProduct_180520225_3 | 15         |
      | ol_3       | o_1                   | salesProduct_180520225_3 | 150        |

    And validate C_OrderLine:
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | OPT.DateOrdered | M_Product_ID.Identifier  | QtyOrdered | qtydelivered | qtyinvoiced | price | discount | currencyCode | processed |
      | ol_1                      | o_1                   | 2022-05-17      | salesProduct_180520225_3 | 8          | 0            | 0           | 5     | 0        | EUR          | false     |
      | ol_2                      | o_1                   | 2022-05-17      | salesProduct_180520225_3 | 15         | 0            | 0           | 5     | 0        | EUR          | false     |
      | ol_3                      | o_1                   | 2022-05-17      | salesProduct_180520225_3 | 150        | 0            | 0           | 5     | 0        | EUR          | false     |

