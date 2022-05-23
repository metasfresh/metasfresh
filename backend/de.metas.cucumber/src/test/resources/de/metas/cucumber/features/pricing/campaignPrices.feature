@from:cucumber
Feature: campaign prices

  Background:
    Given the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
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
      | Identifier | Name         | OPT.IsCustomer | M_PricingSystem_ID.Identifier | OPT.IsAllowActionPrice |
      | bpartner_1 | BPartnerTest | Y              | defaultPricingSystem          | true                   |

  @from:cucumber
  @Id:S0133_150
  @Id:S0133_160
  @Id:S0133_190
  Scenario: campaign prices matching on M_PricingSystem_ID
    Given metasfresh contains M_Products:
      | Identifier       | Name             |
      | salesProduct_150 | salesProduct_150 |
      | salesProduct_160 | salesProduct_160 |
      | salesProduct_190 | salesProduct_190 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_150     | defaultPLV                        | salesProduct_150        | 5.00     | PCE               | Normal                        |
      | pp_160     | defaultPLV                        | salesProduct_160        | 5.00     | PCE               | Normal                        |
      | pp_190     | defaultPLV                        | salesProduct_190        | 5.00     | PCE               | Normal                        |
    And metasfresh contains C_CampaignPrices
      | Identifier | M_Product_ID.Identifier | PriceStd | OPT.M_PricingSystem_ID.Identifier | ValidFrom  | ValidTo    | C_TaxCategory_ID.InternalName | C_Country.CountryCode | C_Currency.ISO_Code | C_UOM_ID.X12DE355 |
      | cp_150     | salesProduct_150        | 3.00     | defaultPricingSystem              | 2022-05-01 | 2022-05-31 | Normal                        | DE                    | EUR                 | PCE               |
      | cp_160     | salesProduct_160        | 3.00     | defaultPricingSystem              | 2022-05-01 | 2022-05-31 | Normal                        | DE                    | EUR                 | PCE               |
      | cp_190     | salesProduct_190        | 6.00     | defaultPricingSystem              | 2022-05-01 | 2022-05-31 | Normal                        | DE                    | EUR                 | PCE               |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.DatePromised     |
      | o_150_190  | true    | bpartner_1               | 2022-05-17  | 2022-05-17T21:00:00Z |
      | o_160      | true    | bpartner_1               | 2022-06-05  | 2022-06-05T21:00:00Z |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_150     | o_150_190             | salesProduct_150        | 1          |
      | ol_160     | o_160                 | salesProduct_160        | 1          |
      | ol_190     | o_150_190             | salesProduct_190        | 1          |

    And validate C_OrderLine:
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | dateordered | M_Product_ID.Identifier | qtyordered | qtydelivered | qtyinvoiced | price | discount | currencyCode | processed |
      | ol_150                    | o_150_190             | 2022-05-17  | salesProduct_150        | 1          | 0            | 0           | 3     | 0        | EUR          | false     |
      | ol_160                    | o_160                 | 2022-06-05  | salesProduct_160        | 1          | 0            | 0           | 5     | 0        | EUR          | false     |
      | ol_190                    | o_150_190             | 2022-05-17  | salesProduct_190        | 1          | 0            | 0           | 5     | 0        | EUR          | false     |

  @from:cucumber
  @Id:S0133_100
  @Id:S0133_110
  @Id:S0133_170
  @Id:S0133_200
  Scenario: campaign prices matching on C_BPartner_ID
    Given metasfresh contains M_Products:
      | Identifier       | Name             |
      | salesProduct_100 | salesProduct_100 |
      | salesProduct_110 | salesProduct_110 |
      | salesProduct_170 | salesProduct_170 |
      | salesProduct_200 | salesProduct_200 |
    And metasfresh contains C_BPartners:
      | Identifier | Name                   | OPT.IsCustomer | M_PricingSystem_ID.Identifier | OPT.IsAllowActionPrice |
      | bpartner_2 | BPartnerTestNoCampaign | Y              | defaultPricingSystem          | false                  |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_100     | defaultPLV                        | salesProduct_100        | 5.00     | PCE               | Normal                        |
      | pp_110     | defaultPLV                        | salesProduct_110        | 5.00     | PCE               | Normal                        |
      | pp_170     | defaultPLV                        | salesProduct_170        | 5.00     | PCE               | Normal                        |
      | pp_200     | defaultPLV                        | salesProduct_200        | 5.00     | PCE               | Normal                        |
    And metasfresh contains C_CampaignPrices
      | Identifier | M_Product_ID.Identifier | PriceStd | OPT.C_BPartner_ID.Identifier | ValidFrom  | ValidTo    | C_TaxCategory_ID.InternalName | C_Country.CountryCode | C_Currency.ISO_Code | C_UOM_ID.X12DE355 |
      | cp_100     | salesProduct_100        | 3.00     | bpartner_1                   | 2022-05-01 | 2022-05-31 | Normal                        | DE                    | EUR                 | PCE               |
      | cp_110     | salesProduct_110        | 3.00     | bpartner_1                   | 2022-05-01 | 2022-05-31 | Normal                        | DE                    | EUR                 | PCE               |
      | cp_170     | salesProduct_170        | 6.00     | bpartner_1                   | 2022-05-01 | 2022-05-31 | Normal                        | DE                    | EUR                 | PCE               |
      | cp_200     | salesProduct_200        | 3.00     | bpartner_2                   | 2022-05-01 | 2022-05-31 | Normal                        | DE                    | EUR                 | PCE               |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.DatePromised     |
      | o_100_170  | true    | bpartner_1               | 2022-05-17  | 2022-05-17T21:00:00Z |
      | o_110      | true    | bpartner_1               | 2022-06-05  | 2022-06-05T21:00:00Z |
      | o_200      | true    | bpartner_2               | 2022-05-17  | 2022-05-17T21:00:00Z |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_100     | o_100_170             | salesProduct_100        | 1          |
      | ol_110     | o_110                 | salesProduct_110        | 1          |
      | ol_170     | o_100_170             | salesProduct_170        | 1          |
      | ol_200     | o_200                 | salesProduct_200        | 1          |

    And validate C_OrderLine:
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | dateordered | M_Product_ID.Identifier | qtyordered | qtydelivered | qtyinvoiced | price | discount | currencyCode | processed |
      | ol_100                    | o_100_170             | 2022-05-17  | salesProduct_100        | 1          | 0            | 0           | 3     | 0        | EUR          | false     |
      | ol_110                    | o_110                 | 2022-06-05  | salesProduct_110        | 1          | 0            | 0           | 5     | 0        | EUR          | false     |
      | ol_170                    | o_100_170             | 2022-05-17  | salesProduct_170        | 1          | 0            | 0           | 5     | 0        | EUR          | false     |
      | ol_200                    | o_200                 | 2022-05-17  | salesProduct_200        | 1          | 0            | 0           | 5     | 0        | EUR          | false     |

  @from:cucumber
  @Id:S0133_120
  @Id:S0133_130
  @Id:S0133_140
  @Id:S0133_180
  Scenario: campaign prices matching on C_BP_Group_ID
    Given metasfresh contains M_Products:
      | Identifier       | Name             |
      | salesProduct_120 | salesProduct_120 |
      | salesProduct_130 | salesProduct_130 |
      | salesProduct_140 | salesProduct_140 |
      | salesProduct_180 | salesProduct_180 |
    And metasfresh contains M_PricingSystems
      | Identifier             | Name                   | Value                  |
      | defaultPricingSystem_2 | defaultPricingSystem_2 | defaultPricingSystem_2 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_120     | defaultPLV                        | salesProduct_120        | 5.00     | PCE               | Normal                        |
      | pp_130     | defaultPLV                        | salesProduct_130        | 5.00     | PCE               | Normal                        |
      | pp_140     | defaultPLV                        | salesProduct_140        | 5.00     | PCE               | Normal                        |
      | pp_180     | defaultPLV                        | salesProduct_180        | 5.00     | PCE               | Normal                        |
    And metasfresh contains C_CampaignPrices
      | Identifier | M_Product_ID.Identifier | PriceStd | OPT.C_BPartner_ID.Identifier | OPT.M_PricingSystem_ID.Identifier | ValidFrom  | ValidTo    | C_TaxCategory_ID.InternalName | C_Country.CountryCode | C_Currency.ISO_Code | C_UOM_ID.X12DE355 |
      | cp_120     | salesProduct_120        | 3.00     | bpartner_1                   |                                   | 2022-05-01 | 2022-05-31 | Normal                        | DE                    | EUR                 | PCE               |
      | cp_130     | salesProduct_130        | 3.00     | bpartner_1                   |                                   | 2022-05-01 | 2022-05-31 | Normal                        | DE                    | EUR                 | PCE               |
      | cp_140     | salesProduct_140        | 6.00     | bpartner_1                   |                                   | 2022-05-01 | 2022-05-31 | Normal                        | DE                    | EUR                 | PCE               |
      | cp_180     | salesProduct_180        | 3.00     | bpartner_1                   | defaultPricingSystem_2            | 2022-05-01 | 2022-05-31 | Normal                        | DE                    | EUR                 | PCE               |
    And metasfresh contains C_Orders:
      | Identifier    | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.DatePromised     | OPT.M_PricingSystem_ID |
      | o_120_140_180 | true    | bpartner_1               | 2022-05-17  | 2022-05-17T21:00:00Z | defaultPricingSystem   |
      | o_130         | true    | bpartner_1               | 2022-06-05  | 2022-06-05T21:00:00Z | defaultPricingSystem   |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_120     | o_120_140_180         | salesProduct_120        | 1          |
      | ol_130     | o_130                 | salesProduct_130        | 1          |
      | ol_140     | o_120_140_180         | salesProduct_140        | 1          |
      | ol_180     | o_120_140_180         | salesProduct_180        | 1          |

    And validate C_OrderLine:
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | dateordered | M_Product_ID.Identifier | qtyordered | qtydelivered | qtyinvoiced | price | discount | currencyCode | processed |
      | ol_120                    | o_120_140_180         | 2022-05-17  | salesProduct_120        | 1          | 0            | 0           | 3     | 0        | EUR          | false     |
      | ol_130                    | o_130                 | 2022-06-05  | salesProduct_130        | 1          | 0            | 0           | 5     | 0        | EUR          | false     |
      | ol_140                    | o_120_140_180         | 2022-05-17  | salesProduct_140        | 1          | 0            | 0           | 5     | 0        | EUR          | false     |
      | ol_180                    | o_120_140_180         | 2022-05-17  | salesProduct_180        | 1          | 0            | 0           | 5     | 0        | EUR          | false     |