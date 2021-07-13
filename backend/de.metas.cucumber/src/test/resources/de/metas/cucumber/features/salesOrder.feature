Feature: sales order

  Scenario: we can create and complete a sales order
    Given metasfresh has date and time 2021-04-16T13:30:13+01:00[Europe/Berlin]
    And metasfresh contains M_Products:
      | Identifier | M_Product_ID | Name         |
      | p_1        | 1            | salesProduct |
    And metasfresh contains M_PricingSystems
      | M_PricingSystem_ID | Name                | Value                | OPT.Description            | OPT.IsActive |
      | 20                 | pricing_system_name | pricing_system_value | pricing_system_description | true         |
    And metasfresh contains M_PriceLists
      | M_PriceList_ID | M_PricingSystem_ID | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name            | OPT.Description | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | 20             | 20                 | DE                        | EUR                 | price_list_name | null            | true  | false         | 2              | true         |
    And metasfresh contains M_PriceList_Versions
      | M_PriceList_Version_ID | M_PriceList_ID | Name           | ValidFrom  |
      | 20                     | 20             | salesOrder-PLV | 2021-04-01 |
    And metasfresh contains M_ProductPrices
      | M_ProductPrice_ID | M_PriceList_Version_ID | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | 20                | 20                     | p_1                     | 10.0     | PCE               | Normal                        |
    And metasfresh contains C_BPartners:
      | Identifier    | Name        | OPT.IsVendor | OPT.IsCustomer | OPT.M_PricingSystem_ID |
      | endcustomer_1 | Endcustomer | N            | Y              | 20                     |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered |
      | o_1        | true    | endcustomer_1            | 2021-04-17  |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyOrdered |
      | ol_1       | o_1                   | p_1                     | 10         |
    When the order identified by o_1 is completed