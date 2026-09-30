@from:cucumber
@allure.label.epic:E0260_Pricing
@allure.label.feature:F32060_Discounts
@ghActions:run_on_executor2
Feature: Discount schema validity window (ValidTo)
  ## A discount schema's break lines apply only while an order line's price date
  ## (DatePromised) falls within [ValidFrom, ValidTo] inclusive. When ValidTo is
  ## left empty, the schema stays valid indefinitely from ValidFrom onward.

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2026-09-10T13:30:13+02:00[Europe/Berlin]
    And metasfresh contains M_Products:
      | Identifier | Name                   |
      | product1   | discountValidToProduct |
    And metasfresh contains M_PricingSystems
      | Identifier | Name                | Value                | OPT.Description            | OPT.IsActive |
      | ps1        | pricing_system_name | pricing_system_value | pricing_system_description | true         |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name            | OPT.Description | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | pl1        | ps1                           | DE                        | EUR                 | price_list_name | null            | true  | false         | 2              | true         |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name     | ValidFrom  |
      | plv1       | pl1                       | plv_name | 2026-01-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp1        | plv1                              | product1                | 10.0     | PCE               | Normal                        |

  @from:cucumber
  @Id:S28738_TC1
  Scenario: order delivered within the discount schema's validity window gets the discount
    Given metasfresh contains M_DiscountSchemas:
      | Identifier | DiscountType | Name                | ValidFrom  | ValidTo    |
      | schema1    | B            | validToWithinWindow | 2026-09-07 | 2026-09-11 |
    And metasfresh contains M_DiscountSchemaBreaks:
      | Identifier | M_DiscountSchema_ID | M_Product_ID | Base_PricingSystem_ID | SeqNo | BreakValue | BreakDiscount |
      | break1     | schema1             | product1     | ps1                   | 10    | 1          | 10            |
    And metasfresh contains C_BPartners:
      | Identifier | Name             | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier | M_DiscountSchema_ID.Identifier |
      | customer1  | ValidToCustomer1 | N            | Y              | ps1                           | schema1                        |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered |
      | order1     | true    | customer1                | 2026-09-10  |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | DatePromised |
      | orderLine1 | order1                | product1                | 1          | 2026-09-11   |
    And validate C_OrderLine:
      | C_OrderLine_ID | discount |
      | orderLine1     | 10       |

  @from:cucumber
  @Id:S28738_TC2
  Scenario: order delivered after the discount schema's ValidTo does not get the discount
    Given metasfresh contains M_DiscountSchemas:
      | Identifier | DiscountType | Name               | ValidFrom  | ValidTo    |
      | schema2    | B            | validToAfterWindow | 2026-09-07 | 2026-09-11 |
    And metasfresh contains M_DiscountSchemaBreaks:
      | Identifier | M_DiscountSchema_ID | M_Product_ID | Base_PricingSystem_ID | SeqNo | BreakValue | BreakDiscount |
      | break2     | schema2             | product1     | ps1                   | 10    | 1          | 10            |
    And metasfresh contains C_BPartners:
      | Identifier | Name             | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier | M_DiscountSchema_ID.Identifier |
      | customer2  | ValidToCustomer2 | N            | Y              | ps1                           | schema2                        |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered |
      | order2     | true    | customer2                | 2026-09-10  |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | DatePromised |
      | orderLine2 | order2                | product1                | 1          | 2026-09-14   |
    And validate C_OrderLine:
      | C_OrderLine_ID | discount |
      | orderLine2     | 0        |

  @from:cucumber
  @Id:S28738_TC3
  Scenario: discount schema without a ValidTo still applies far in the future (regression)
    Given metasfresh contains M_DiscountSchemas:
      | Identifier | DiscountType | Name             | ValidFrom  |
      | schema3    | B            | validToLeftEmpty | 2026-01-01 |
    And metasfresh contains M_DiscountSchemaBreaks:
      | Identifier | M_DiscountSchema_ID | M_Product_ID | Base_PricingSystem_ID | SeqNo | BreakValue | BreakDiscount |
      | break3     | schema3             | product1     | ps1                   | 10    | 1          | 15            |
    And metasfresh contains C_BPartners:
      | Identifier | Name             | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier | M_DiscountSchema_ID.Identifier |
      | customer3  | ValidToCustomer3 | N            | Y              | ps1                           | schema3                        |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered |
      | order3     | true    | customer3                | 2026-09-10  |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | DatePromised |
      | orderLine3 | order3                | product1                | 1          | 2099-01-01   |
    And validate C_OrderLine:
      | C_OrderLine_ID | discount |
      | orderLine3     | 15       |
