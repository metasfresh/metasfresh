@from:cucumber
Feature: sales order

  Background:
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'

    And metasfresh has date and time 2021-04-16T13:30:13+01:00[Europe/Berlin]

  @from:cucumber
  Scenario: we can create and complete a sales order
    Given metasfresh has date and time 2021-04-16T13:30:13+01:00[Europe/Berlin]
    And metasfresh contains M_Products:
      | Identifier | Name           |
      | p_1        | salesProduct_12 |
    And metasfresh contains M_PricingSystems
      | Identifier | Name                | Value                | OPT.Description            | OPT.IsActive |
      | ps_1       | pricing_system_name | pricing_system_value | pricing_system_description | true         |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name            | OPT.Description | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | pl_1       | ps_1                          | DE                        | EUR                 | price_list_name | null            | true  | false         | 2              | true         |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name           | ValidFrom  |
      | plv_1      | pl_1                      | salesOrder-PLV | 2021-04-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_1       | plv_1                             | p_1                     | 10.0     | PCE               | Normal                        |
    And metasfresh contains C_BPartners:
      | Identifier    | Name        | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | endcustomer_1 | Endcustomer | N            | Y              | ps_1                          |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered |
      | o_1        | true    | endcustomer_1            | 2021-04-17  |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_1       | o_1                   | p_1                     | 10         |
    When the order identified by o_1 is completed
    Then after not more than 30s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_ol_1     | ol_1                      | N             |

  @from:cucumber
  Scenario: we can generate a purchase order from a sales order
    And metasfresh contains M_Products:
      | Identifier | Name           |
      | p_2        | salesProduct_72 |
    And metasfresh contains M_PricingSystems
      | Identifier | Name                  | Value                  | OPT.Description            | OPT.IsActive |
      | ps_2       | pricing_system_name_72 | pricing_system_value_72 | pricing_system_description | true         |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name              | OPT.Description | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | pl_2       | ps_2                          | DE                        | EUR                 | price_list_name_72 | null            | true  | false         | 2              | true         |
      | pl_3       | ps_2                          | DE                        | EUR                 | price_list_name_73 | null            | false | false         | 2              | true         |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name                | ValidFrom  |
      | plv_2      | pl_2                      | salesOrder-PLV_72    | 2021-04-01 |
      | plv_3      | pl_3                      | purchaseOrder-PLV_72 | 2021-04-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_2       | plv_2                             | p_2                     | 10.0     | PCE               | Normal                        |
      | pp_3       | plv_3                             | p_2                     | 10.0     | PCE               | Normal                        |
    And metasfresh contains C_BPartners:
      | Identifier    | Name          | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | endcustomer_2 | Endcustomer_72 | N            | Y              | ps_2                          |
      | vendor_2      | vendor_72      | Y            | Y              | ps_2                          |
    And metasfresh contains C_BPartner_Products:
      | C_BPartner_ID.Identifier | M_Product_ID.Identifier |
      | vendor_2                 | p_2                     |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered |
      | o_2        | true    | endcustomer_2            | 2021-04-17  |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_2       | o_2                   | p_2                     | 10         |
    And the order identified by o_2 is completed
    And after not more than 30s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_ol_2     | ol_2                      | N             |
    When generate PO from SO is invoked with parameters:
      | C_BPartner_ID.Identifier | C_Order_ID.Identifier | PurchaseType |
      | vendor_2                 | o_2                   | Mediated     |
    Then the order is created:
      | Link_Order_ID.Identifier | IsSOTrx | DocBaseType | DocSubType |
      | o_2                      | false   | POO         | MED        |
    And the mediated purchase order linked to order 'o_2' has lines:
      | QtyOrdered | LineNetAmt | M_Product_ID.Identifier |
      | 10         | 100        | p_2                     |
    And the sales order identified by 'o_2' is closed
    And the shipment schedule identified by s_ol_2 is processed after not more than 10 seconds