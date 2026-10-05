@from:cucumber
@allure.label.epic:E0260_Pricing
@allure.label.feature:F32111_RV_Fresh_SalesPriceList
@ghActions:run_on_executor1
Feature: report.getSalesPriceSpecialAndBase resolves the special and base price along the price-list chain

  # The Customer delivery price report shows, per article, the standard base-list price and the
  # customer-specific special price. The price-list layering chain is: an assigned override list
  # that falls back (via BasePriceList_ID) to a base list.
  # - Base    = the price on the TRUE base list (BasePriceList_ID IS NULL); empty if the base list has no price.
  # - Special = the price on the nearest override list; empty if the article is priced only on the base list.

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2022-05-17T13:30:13+01:00[Europe/Berlin]

    And metasfresh contains M_PricingSystems
      | Identifier            |
      | customerPricingSystem |
    # override list (assigned, SO) -> base list (fallback, non-SO)
    # Generic (country-less) price lists so root resolution matches the customer regardless of location country.
    And metasfresh contains M_PriceLists
      | Identifier   | M_PricingSystem_ID.Identifier | C_Currency.ISO_Code | SOTrx | BasePriceList_ID.Identifier |
      | baseList     | customerPricingSystem         | EUR                 | false |                             |
      | overrideList | customerPricingSystem         | EUR                 | true  | baseList                    |
    And metasfresh contains M_PriceList_Versions
      | Identifier          | M_PriceList_ID.Identifier | ValidFrom  |
      | baseListVersion     | baseList                  | 2022-05-01 |
      | overrideListVersion | overrideList              | 2022-05-01 |
    # The bpartner's default location is named customerLocation via C_BPartner_Location_ID.
    And metasfresh contains C_BPartners:
      | Identifier | M_PricingSystem_ID.Identifier | C_BPartner_Location_ID.Identifier |
      | customer   | customerPricingSystem         | customerLocation                  |

  Scenario: article priced on both lists shows the base price and the customer special price
    Given metasfresh contains M_Products:
      | Identifier  |
      | productBoth |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | baseListVersion                   | productBoth             | 100      | PCE               | Normal                        |
      | overrideListVersion               | productBoth             | 90       | PCE               | Normal                        |
    Then report.getSalesPriceSpecialAndBase returns:
      | C_BPartner_Location_ID | M_Product_ID | Date       | SpecialPriceStd | BasePriceStd |
      | customerLocation       | productBoth  | 2022-05-17 | 90              | 100          |

  Scenario: article priced only on the base list shows the base price and no special price
    Given metasfresh contains M_Products:
      | Identifier      |
      | productBaseOnly |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | baseListVersion                   | productBaseOnly         | 200      | PCE               | Normal                        |
    Then report.getSalesPriceSpecialAndBase returns:
      | C_BPartner_Location_ID | M_Product_ID    | Date       | SpecialPriceStd | BasePriceStd |
      | customerLocation       | productBaseOnly | 2022-05-17 |                 | 200          |

  # Guards the structural semantics: an article priced only on the customer's override list is a
  # SPECIAL price (Base stays empty) -- it must not be reported as the base/standard price.
  Scenario: article priced only on the override list shows the special price and no base price
    Given metasfresh contains M_Products:
      | Identifier          |
      | productOverrideOnly |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | overrideListVersion               | productOverrideOnly     | 55       | PCE               | Normal                        |
    Then report.getSalesPriceSpecialAndBase returns:
      | C_BPartner_Location_ID | M_Product_ID        | Date       | SpecialPriceStd | BasePriceStd |
      | customerLocation       | productOverrideOnly | 2022-05-17 | 55              |              |

  # Three-level chain: assigned override -> middle override -> true base list. Base must come from the
  # true base list (90), not the middle override (80); Special is the nearest override (70).
  # All three rungs are SALES (SO) lists -- a sales-price report walks sales lists only. The unique index
  # M_PriceList_UC_C_Country (M_PricingSystem_ID, IsSOPriceList, COALESCE(C_Country_ID,0)) allows only ONE
  # sales list per pricing system per country, and country-less lists collapse to country 0, so a three-rung
  # all-sales chain needs one pricing system per rung. BasePriceList_ID may cross pricing systems; the
  # customer is assigned the pricing system holding the top (assigned) list.
  Scenario: three-level chain takes the base price from the true base list, not the middle override
    Given metasfresh contains M_PricingSystems
      | Identifier              |
      | layeredPricingSystem    |
      | middlePricingSystem     |
      | globalBasePricingSystem |
    And metasfresh contains M_PriceLists
      | Identifier   | M_PricingSystem_ID.Identifier | C_Currency.ISO_Code | SOTrx | BasePriceList_ID.Identifier |
      | trueBaseList | globalBasePricingSystem       | EUR                 | true  |                             |
      | middleList   | middlePricingSystem           | EUR                 | true  | trueBaseList                |
      | assignedList | layeredPricingSystem          | EUR                 | true  | middleList                  |
    And metasfresh contains M_PriceList_Versions
      | Identifier      | M_PriceList_ID.Identifier | ValidFrom  |
      | trueBaseVersion | trueBaseList              | 2022-05-01 |
      | middleVersion   | middleList                | 2022-05-01 |
      | assignedVersion | assignedList              | 2022-05-01 |
    And metasfresh contains C_BPartners:
      | Identifier      | M_PricingSystem_ID.Identifier | C_BPartner_Location_ID.Identifier |
      | layeredCustomer | layeredPricingSystem          | layeredCustomerLocation           |
    And metasfresh contains M_Products:
      | Identifier     |
      | layeredProduct |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | trueBaseVersion                   | layeredProduct          | 90       | PCE               | Normal                        |
      | middleVersion                     | layeredProduct          | 80       | PCE               | Normal                        |
      | assignedVersion                   | layeredProduct          | 70       | PCE               | Normal                        |
    Then report.getSalesPriceSpecialAndBase returns:
      | C_BPartner_Location_ID  | M_Product_ID   | Date       | SpecialPriceStd | BasePriceStd |
      | layeredCustomerLocation | layeredProduct | 2022-05-17 | 70              | 90           |

  # Version-by-date selection: getPriceListVersionsUpToBase picks, per list, the newest version whose
  # ValidFrom is on/before the report date (MAX(ValidFrom) WHERE ValidFrom <= p_Date). baseList (from the
  # Background) gets one version BEFORE and one AFTER the 2022-05-17 report date, around the in-range
  # baseListVersion (2022-05-01). The in-range version's price (110) must win over the older (100) and the
  # future (120) versions. The article is priced only on the base list, so Special stays empty.
  Scenario: the newest price-list version valid on the report date is used, ignoring older and future versions
    Given metasfresh contains M_PriceList_Versions
      | Identifier          | M_PriceList_ID.Identifier | ValidFrom  |
      | baseListVersionOld  | baseList                  | 2022-01-01 |
      | baseListVersionNext | baseList                  | 2022-09-01 |
    And metasfresh contains M_Products:
      | Identifier   |
      | productDated |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | baseListVersionOld                | productDated            | 100      | PCE               | Normal                        |
      | baseListVersion                   | productDated            | 110      | PCE               | Normal                        |
      | baseListVersionNext               | productDated            | 120      | PCE               | Normal                        |
    Then report.getSalesPriceSpecialAndBase returns:
      | C_BPartner_Location_ID | M_Product_ID | Date       | SpecialPriceStd | BasePriceStd |
      | customerLocation       | productDated | 2022-05-17 |                 | 110          |
