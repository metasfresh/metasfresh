@from:cucumber
Feature: invoice rule after delivery

  Background:
    Given the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION

  @from:cucumber
  Scenario: we can invoice a sales order with invoice rule after delivery
    Given metasfresh has date and time 2021-04-16T13:30:13+01:00[Europe/Berlin]
    And metasfresh contains M_Products:
      | Identifier | Name            |
      | p_1        | salesProduct_70 |
    And metasfresh contains M_PricingSystems
      | Identifier | Name                   | Value                   | OPT.Description            | OPT.IsActive |
      | ps_1       | pricing_system_name_70 | pricing_system_value_70 | pricing_system_description | true         |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name               | OPT.Description | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | pl_1       | ps_1                          | DE                        | EUR                 | price_list_name_70 | null            | true  | false         | 2              | true         |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name              | ValidFrom  |
      | plv_1      | pl_1                      | salesOrder-PLV_70 | 2021-04-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_1       | plv_1                             | p_1                     | 10.0     | PCE               | Normal                        |
    And metasfresh contains C_BPartners:
      | Identifier    | Name         | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier | OPT.InvoiceRule |
      | endcustomer_1 | Endcustomer2 | N            | Y              | ps_1                          | D               |
    And metasfresh contains C_BPartner_Locations:
      | Identifier | GLN           | C_BPartner_ID.Identifier |
      | l_1        | 0123456789011 | endcustomer_1            |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.POReference | OPT.C_PaymentTerm_ID |
      | o_1        | true    | endcustomer_1            | 2021-04-17  | po_ref_mock     | 1000012              |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_1       | o_1                   | p_1                     | 10         |
    When the order identified by o_1 is completed
    And after not more than 30s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_s_1      | ol_1                      | N             |
    And generate shipments process is invoked
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | s_s_1                            | D            | true                | false       |
    And after not more than 30s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | s_s_1                            | s_1                   |
    Then enqueue candidate for invoicing and after not more than 30s, the invoice is found
      | C_Order_ID.Identifier | C_Invoice_ID.Identifier |
      | o_1                   | invoice_1               |
    And validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | poReference | paymentTerm | processed | docStatus |
      | invoice_1               | endcustomer_1            | l_1                               | po_ref_mock | 1000002     | true      | CO        |
    And validate created invoice lines
      | C_Invoice_ID.Identifier | M_Product_ID.Identifier | qtyinvoiced | processed |
      | invoice_1               | p_1                     | 10          | true      |

  @from:cucumber
  Scenario: we can invoice a sales order with invoice rule after pick
    Given metasfresh has date and time 2021-04-16T13:30:13+01:00[Europe/Berlin]
    And metasfresh contains M_Products:
      | Identifier | Name            |
      | p_2        | salesProduct_71 |
    And metasfresh contains C_UOM_Conversions
      | M_Product_ID.Identifier | FROM_C_UOM_ID.X12DE355 | TO_C_UOM_ID.X12DE355 | MultiplyRate |
      | p_2                     | PCE                    | KGM                  | 0.25         |
    And metasfresh contains M_PricingSystems
      | Identifier | Name                   | Value                   | OPT.Description            | OPT.IsActive |
      | ps_2       | pricing_system_name_71 | pricing_system_value_71 | pricing_system_description | true         |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name               | OPT.Description | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | pl_2       | ps_2                          | DE                        | EUR                 | price_list_name_71 | null            | true  | false         | 2              | true         |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name              | ValidFrom  |
      | plv_2      | pl_2                      | salesOrder-PLV_71 | 2021-04-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_2       | plv_2                             | p_2                     | 10.0     | KGM               | Normal                        |
    And metasfresh contains C_BPartners:
      | Identifier    | Name         | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier | OPT.InvoiceRule |
      | endcustomer_2 | Endcustomer3 | N            | Y              | ps_2                          | P               |
    And metasfresh contains C_BPartner_Locations:
      | Identifier | GLN           | C_BPartner_ID.Identifier |
      | l_2        | 0123456789012 | endcustomer_2            |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.POReference | OPT.C_PaymentTerm_ID |
      | o_2        | true    | endcustomer_2            | 2021-04-15  | po_ref_mock     | 1000012              |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_2       | o_2                   | p_2                     | 12         |
    And the order identified by o_2 is completed
    And after not more than 30s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_s_2      | ol_2                      | N             |
    And the following virtual inventory is created
      | M_HU_ID.Identifier | QtyToBeAdded | M_ShipmentSchedule_ID.Identifier | M_Product_ID.Identifier |
      | hu_1               | 12           | s_s_2                            | p_2                     |
    And the following qty is picked
      | M_ShipmentSchedule_ID.Identifier | M_Product_ID.Identifier | QtyPicked | M_HU_ID.Identifier |
      | s_s_2                            | p_2                     | 6         | hu_1               |
    Then enqueue candidate for invoicing and after not more than 30s, the invoice is found
      | C_Order_ID.Identifier | C_Invoice_ID.Identifier |
      | o_2                   | invoice_2               |
    And validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | poReference | paymentTerm | processed | docStatus |
      | invoice_2               | endcustomer_2            | l_2                               | po_ref_mock | 1000002     | true      | CO        |
    And validate created invoice lines
      | C_Invoice_ID.Identifier | M_Product_ID.Identifier | qtyinvoiced | processed |
      | invoice_2               | p_2                     | 6           | true      |