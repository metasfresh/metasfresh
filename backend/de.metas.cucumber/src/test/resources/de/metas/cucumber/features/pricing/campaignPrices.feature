@from:cucumber
@ghActions:run_on_executor6
Feature: campaign prices

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
      | defaultPLV | defaultPriceList          | PLVName1 | 2022-04-29 |
    And metasfresh contains C_BPartners:
      | Identifier | Name         | OPT.C_BP_Group_ID | OPT.IsCustomer | M_PricingSystem_ID.Identifier | OPT.IsAllowActionPrice |
      | bpartner_1 | BPartnerTest | 1000000           | Y              | defaultPricingSystem          | true                   |

  @from:cucumber
  @Id:S0133_150
  @Id:S0133_160
  @Id:S0133_190
  Scenario: campaign prices matching on M_PricingSystem_ID
    Given metasfresh contains M_Products:
      | Identifier     | Name           |
      | salesProduct_1 | salesProduct_1 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_1       | defaultPLV                        | salesProduct_1          | 5.00     | PCE               | Normal                        |
    And metasfresh contains C_CampaignPrices
      | Identifier | M_Product_ID.Identifier | PriceStd | OPT.M_PricingSystem_ID.Identifier | ValidFrom  | ValidTo    | C_TaxCategory_ID.InternalName | C_Country.CountryCode | C_Currency.ISO_Code | C_UOM_ID.X12DE355 |
      | cp_150_160 | salesProduct_1          | 3.00     | defaultPricingSystem              | 2022-05-01 | 2022-05-12 | Normal                        | DE                    | EUR                 | PCE               |
      | cp_190     | salesProduct_1          | 6.00     | defaultPricingSystem              | 2022-05-24 | 2022-05-31 | Normal                        | DE                    | EUR                 | PCE               |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.DatePromised     |
      | o_150      | true    | bpartner_1               | 2022-05-05  | 2022-05-05T21:00:00Z |
      | o_160      | true    | bpartner_1               | 2022-05-17  | 2022-05-17T21:00:00Z |
      | o_190      | true    | bpartner_1               | 2022-05-30  | 2022-05-30T21:00:00Z |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_150     | o_150                 | salesProduct_1          | 1          |
      | ol_160     | o_160                 | salesProduct_1          | 1          |
      | ol_190     | o_190                 | salesProduct_1          | 1          |

    And validate C_OrderLine:
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | dateordered | M_Product_ID.Identifier | QtyOrdered | qtydelivered | qtyinvoiced | price | discount | currencyCode | processed |
      | ol_150                    | o_150                 | 2022-05-05  | salesProduct_1          | 1          | 0            | 0           | 3     | 0        | EUR          | false     |
      | ol_160                    | o_160                 | 2022-05-17  | salesProduct_1          | 1          | 0            | 0           | 5     | 0        | EUR          | false     |
      | ol_190                    | o_190                 | 2022-05-30  | salesProduct_1          | 1          | 0            | 0           | 5     | 0        | EUR          | false     |

  @from:cucumber
  @Id:S0133_100
  @Id:S0133_110
  @Id:S0133_170
  @Id:S0133_200
  Scenario: campaign prices matching on C_BPartner_ID
    Given metasfresh contains M_Products:
      | Identifier     | Name           |
      | salesProduct_2 | salesProduct_2 |
    And metasfresh contains C_BPartners:
      | Identifier | Name                   | OPT.IsCustomer | M_PricingSystem_ID.Identifier | OPT.IsAllowActionPrice |
      | bpartner_2 | BPartnerTestNoCampaign | Y              | defaultPricingSystem          | false                  |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_2       | defaultPLV                        | salesProduct_2          | 5.00     | PCE               | Normal                        |
    And metasfresh contains C_CampaignPrices
      | Identifier | M_Product_ID.Identifier | PriceStd | OPT.C_BPartner_ID.Identifier | ValidFrom  | ValidTo    | C_TaxCategory_ID.InternalName | C_Country.CountryCode | C_Currency.ISO_Code | C_UOM_ID.X12DE355 |
      | cp_100     | salesProduct_2          | 3.00     | bpartner_1                   | 2022-05-01 | 2022-05-08 | Normal                        | DE                    | EUR                 | PCE               |
      | cp_170     | salesProduct_2          | 6.00     | bpartner_1                   | 2022-05-18 | 2022-05-24 | Normal                        | DE                    | EUR                 | PCE               |
      | cp_200     | salesProduct_2          | 3.00     | bpartner_2                   | 2022-05-25 | 2022-05-31 | Normal                        | DE                    | EUR                 | PCE               |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.DatePromised     |
      | o_100      | true    | bpartner_1               | 2022-05-03  | 2022-05-03T21:00:00Z |
      | o_110      | true    | bpartner_1               | 2022-05-15  | 2022-05-15T21:00:00Z |
      | o_170      | true    | bpartner_1               | 2022-05-21  | 2022-05-21T21:00:00Z |
      | o_200      | true    | bpartner_2               | 2022-05-28  | 2022-05-28T21:00:00Z |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_100     | o_100                 | salesProduct_2          | 1          |
      | ol_110     | o_110                 | salesProduct_2          | 1          |
      | ol_170     | o_170                 | salesProduct_2          | 1          |
      | ol_200     | o_200                 | salesProduct_2          | 1          |

    And validate C_OrderLine:
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | dateordered | M_Product_ID.Identifier | QtyOrdered | qtydelivered | qtyinvoiced | price | discount | currencyCode | processed |
      | ol_100                    | o_100                 | 2022-05-03  | salesProduct_2          | 1          | 0            | 0           | 3     | 0        | EUR          | false     |
      | ol_110                    | o_110                 | 2022-05-15  | salesProduct_2          | 1          | 0            | 0           | 5     | 0        | EUR          | false     |
      | ol_170                    | o_170                 | 2022-05-21  | salesProduct_2          | 1          | 0            | 0           | 5     | 0        | EUR          | false     |
      | ol_200                    | o_200                 | 2022-05-28  | salesProduct_2          | 1          | 0            | 0           | 5     | 0        | EUR          | false     |

  @from:cucumber
  @Id:S0133_120
  @Id:S0133_130
  @Id:S0133_140
  @Id:S0133_180
  Scenario: campaign prices matching on C_BP_Group_ID
    Given metasfresh contains M_Products:
      | Identifier     | Name           |
      | salesProduct_3 | salesProduct_3 |
    And metasfresh contains M_PricingSystems
      | Identifier             | Name                   | Value                  |
      | defaultPricingSystem_2 | defaultPricingSystem_2 | defaultPricingSystem_2 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_3       | defaultPLV                        | salesProduct_3          | 5.00     | PCE               | Normal                        |
    And metasfresh contains C_CampaignPrices
      | Identifier | M_Product_ID.Identifier | PriceStd | OPT.C_BP_Group_ID | OPT.M_PricingSystem_ID.Identifier | ValidFrom  | ValidTo    | C_TaxCategory_ID.InternalName | C_Country.CountryCode | C_Currency.ISO_Code | C_UOM_ID.X12DE355 |
      | cp_120     | salesProduct_3          | 3.00     | 1000000           |                                   | 2022-05-01 | 2022-05-08 | Normal                        | DE                    | EUR                 | PCE               |
      | cp_130     | salesProduct_3          | 6.00     | 1000000           |                                   | 2022-05-18 | 2022-05-24 | Normal                        | DE                    | EUR                 | PCE               |
      | cp_180     | salesProduct_3          | 3.00     | 1000000           | defaultPricingSystem_2            | 2022-05-25 | 2022-05-31 | Normal                        | DE                    | EUR                 | PCE               |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.DatePromised     | OPT.M_PricingSystem_ID |
      | o_120      | true    | bpartner_1               | 2022-05-03  | 2022-05-03T21:00:00Z |                        |
      | o_130      | true    | bpartner_1               | 2022-05-15  | 2022-06-15T21:00:00Z |                        |
      | o_140      | true    | bpartner_1               | 2022-05-21  | 2022-05-21T21:00:00Z |                        |
      | o_180      | true    | bpartner_1               | 2022-05-28  | 2022-05-28T21:00:00Z | defaultPricingSystem   |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_120     | o_120                 | salesProduct_3          | 1          |
      | ol_130     | o_130                 | salesProduct_3          | 1          |
      | ol_140     | o_140                 | salesProduct_3          | 1          |
      | ol_180     | o_180                 | salesProduct_3          | 1          |

    And validate C_OrderLine:
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | dateordered | M_Product_ID.Identifier | QtyOrdered | qtydelivered | qtyinvoiced | price | discount | currencyCode | processed |
      | ol_120                    | o_120                 | 2022-05-03  | salesProduct_3          | 1          | 0            | 0           | 3     | 0        | EUR          | false     |
      | ol_130                    | o_130                 | 2022-05-15  | salesProduct_3          | 1          | 0            | 0           | 5     | 0        | EUR          | false     |
      | ol_140                    | o_140                 | 2022-05-21  | salesProduct_3          | 1          | 0            | 0           | 5     | 0        | EUR          | false     |
      | ol_180                    | o_180                 | 2022-05-28  | salesProduct_3          | 1          | 0            | 0           | 5     | 0        | EUR          | false     |