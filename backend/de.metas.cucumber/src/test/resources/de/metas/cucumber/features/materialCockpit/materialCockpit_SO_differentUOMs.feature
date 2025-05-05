@from:cucumber
@ghActions:run_on_executor6
Feature: sales order interaction with material cockpit - Order Line's UOM is different than Product's UOM

  Background: Initial Data
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2021-04-16T13:30:13+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION

  @flaky # https://github.com/metasfresh/metasfresh/actions/runs/7569823176/job/20614750783
  @Id:S0278_100
  @from:cucumber
  Scenario: SO with qty = 10, no ASI
    Given metasfresh contains M_Products:
      | Identifier | Name                    | OPT.X12DE355 |
      | p_1        | salesProduct_22062023_2 | KGM          |
    And metasfresh contains C_UOM_Conversions
      | M_Product_ID.Identifier | FROM_C_UOM_ID.X12DE355 | TO_C_UOM_ID.X12DE355 | MultiplyRate |
      | p_1                     | KGM                    | PCE                  | 4            |
    And metasfresh contains M_PricingSystems
      | Identifier | Name                           | Value                           | OPT.Description                       | OPT.IsActive |
      | ps_1       | pricing_system_name_22062023_2 | pricing_system_value_22062023_2 | pricing_system_description_22062023_2 | true         |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name                       | OPT.Description | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | pl_1       | ps_1                          | DE                        | EUR                 | price_list_name_22062023_2 | null            | true  | false         | 2              | true         |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name                      | ValidFrom  |
      | plv_1      | pl_1                      | salesOrder-PLV_22062023_2 | 2020-04-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_1       | plv_1                             | p_1                     | 10.0     | PCE               | Normal                        |
    And metasfresh contains C_BPartners:
      | Identifier    | Name                  | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | endBPartner_1 | EndPartner_22062023_2 | Y            | Y              | ps_1                          |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.DatePromised     |
      | o_1        | true    | endBPartner_1            | 2021-12-01  | 2021-04-16T00:00:00Z |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.C_UOM_ID.X12DE355 |
      | ol_1       | o_1                   | p_1                     | 10         | PCE                   |
    When the order identified by o_1 is completed

    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes

    Then after not more than 90s, metasfresh has this MD_Cockpit data
      | Identifier | M_Product_ID.Identifier | DateGeneral | OPT.AttributesKey.Identifier | OPT.QtyDemand_SalesOrder_AtDate | OPT.QtyDemandSum_AtDate | OPT.QtyStockCurrent_AtDate | OPT.QtyExpectedSurplus_AtDate | OPT.QtyInventoryCount_AtDate |
      | cp_1       | p_1                     | 2021-04-16  |                              | 2.5                             | 2.5                     | 0                          | -2.5                          | 0                            |

    And inactivate C_UOM_Conversion:
      | M_Product_ID.Identifier | FROM_C_UOM_ID.X12DE355 | TO_C_UOM_ID.X12DE355 |
      | p_1                     | KGM                    | PCE                  |

    And update M_Product and expect exception to be thrown:
      | M_Product_ID.Identifier | OPT.X12DE355 | ExceptionMessage                                                                                                 |
      | p_1                     | PCE          | Die Maßeinheit kann nicht mehr geändert werden, sobald das Produkt verwendet wird (Bestellungen, Rechnungen,...) |
