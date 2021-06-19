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
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_1       | o_1                   | p_1                     | 10         |
    When the order identified by o_1 is completed
    Then after not more than 10s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_ol_1     | ol_1                      | N             |

  Scenario: we can generate a purchase order from a sales order
    Given metasfresh has date and time 2021-04-16T13:30:13+01:00[Europe/Berlin]
    And metasfresh contains M_Products:
      | Identifier | M_Product_ID | Name           |
      | p_2        | 2            | salesProduct_2 |
    And metasfresh contains M_PricingSystems
      | M_PricingSystem_ID | Name                  | Value                  | OPT.Description            | OPT.IsActive |
      | 30                 | pricing_system_name_2 | pricing_system_value_2 | pricing_system_description | true         |
    And metasfresh contains M_PriceLists
      | M_PriceList_ID | M_PricingSystem_ID | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name              | OPT.Description | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | 30             | 30                 | DE                        | EUR                 | price_list_name_2 | null            | true  | false         | 2              | true         |
      | 40             | 30                 | DE                        | EUR                 | price_list_name_3 | null            | false | false         | 2              | true         |
    And metasfresh contains M_PriceList_Versions
      | M_PriceList_Version_ID | M_PriceList_ID | Name                | ValidFrom  |
      | 30                     | 30             | salesOrder-PLV_2    | 2021-04-01 |
      | 40                     | 40             | purchaseOrder-PLV_2 | 2021-04-01 |
    And metasfresh contains M_ProductPrices
      | M_ProductPrice_ID | M_PriceList_Version_ID | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | 30                | 30                     | p_2                     | 10.0     | PCE               | Normal                        |
      | 40                | 40                     | p_2                     | 10.0     | PCE               | Normal                        |
    And metasfresh contains C_BPartners:
      | Identifier    | Name          | OPT.IsVendor | OPT.IsCustomer | OPT.M_PricingSystem_ID |
      | endcustomer_2 | Endcustomer_2 | N            | Y              | 30                     |
      | vendor_1      | vendor_1      | Y            | Y              | 30                     |
    And metasfresh contains C_BPartner_Products:
      | C_BPartner_ID.Identifier | M_Product_ID.Identifier |
      | vendor_1                 | p_2                     |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered |
      | o_2        | true    | endcustomer_2            | 2021-04-17  |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_2       | o_2                   | p_2                     | 10         |
    And the order identified by o_2 is completed
    When generate PO from SO is invoked with parameters:
      | C_BPartner_ID.Identifier | C_Order_ID.Identifier | PurchaseType |
      | vendor_1                 | o_2                   | Mediated     |
    Then the order is created:
      | Link_Order_ID.Identifier | IsSOTrx | DocBaseType | DocSubType |
      | o_2                      | false   | POO         | MED        |
    And the purchase order linked to order 'o_2' has lines:
    | QtyOrdered | LineNetAmt | M_Product_ID.Identifier |
    | 10         | 100        | p_2                     |
    And the sales order identified by 'o_2' is closed