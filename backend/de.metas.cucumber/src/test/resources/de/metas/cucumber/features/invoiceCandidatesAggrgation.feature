@from:cucumber
Feature: invoice generation and invoice candidates aggregation

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2021-04-16T13:30:13+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION

  @from:cucumber
  Scenario: case 10
  - C_AggregationItem for SalesRep_ID is inactive
  - two sales orders with the same salesRep_IDs => one invoice with the respective SalesRep_ID
    Given load C_AggregationItem
      | C_AggregationItem_ID.Identifier | OPT.C_AggregationItem_ID |
      | a_1                             | 540071                   |
    And update C_AggregationItem
      | C_AggregationItem_ID.Identifier | IsActive |
      | a_1                             | false    |
    And metasfresh contains M_Products:
      | Identifier | Name                 |
      | p_1        | salesProduct_15100_1 |
      | p_2        | salesProduct_15100_2 |
    And metasfresh contains M_PricingSystems
      | Identifier | Name                      | Value                      | OPT.Description            | OPT.IsActive |
      | ps_1       | pricing_system_name_15100 | pricing_system_value_15100 | pricing_system_description | true         |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name                  | OPT.Description | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | pl_1       | ps_1                          | DE                        | EUR                 | price_list_name_15100 | null            | true  | false         | 2              | true         |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name                 | ValidFrom  |
      | plv_1      | pl_1                      | salesOrder-PLV_15100 | 2021-04-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_1       | plv_1                             | p_1                     | 10.0     | PCE               | Normal                        |
      | pp_2       | plv_1                             | p_2                     | 5.0      | PCE               | Normal                        |
    And metasfresh contains C_BPartners without locations:
      | Identifier    | Name                | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier | OPT.InvoiceRule |
      | endcustomer_1 | Endcustomer_15100_1 | N            | Y              | ps_1                          | D               |
    And metasfresh contains C_BPartner_Locations:
      | Identifier | GLN           | C_BPartner_ID.Identifier | OPT.IsShipToDefault | OPT.IsBillToDefault |
      | l_1        | 4112345000009 | endcustomer_1            | true                | true                |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.POReference   | OPT.C_PaymentTerm_ID | OPT.SalesRep_ID |
      | o_1        | true    | endcustomer_1            | 2021-04-17  | po_ref_mock_15100 | 1000012              | 100             |
      | o_2        | true    | endcustomer_1            | 2021-04-17  | po_ref_mock_15100 | 1000012              | 100             |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_1       | o_1                   | p_1                     | 10         |
      | ol_2       | o_2                   | p_2                     | 5          |
    When the order identified by o_1 is completed
    And after not more than 30s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_s_1      | ol_1                      | N             |
    And 'generate shipments' process is invoked
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | s_s_1                            | D            | true                | false       |
    And after not more than 30s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | s_s_1                            | s_1                   |

    When the order identified by o_2 is completed
    And after not more than 30s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_s_2      | ol_2                      | N             |
    And 'generate shipments' process is invoked
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | s_s_2                            | D            | true                | false       |

    And after not more than 30s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | s_s_2                            | s_2                   |
    Then enqueue candidate for invoicing and after not more than 30s, the invoice is found
      | C_Order_ID.Identifier | C_Invoice_ID.Identifier |
      | o_1,o_2               | invoice_1               |
    And validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | OPT.POReference   | paymentTerm | processed | docStatus | OPT.SalesRep_ID |
      | invoice_1               | endcustomer_1            | l_1                               | po_ref_mock_15100 | 1000002     | true      | CO        | 100             |
    And validate created invoice lines
      | C_InvoiceLine_ID.Identifier | C_Invoice_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | Processed |
      | il_1                        | invoice_1               | p_1                     | 10          | true      |
      | il_2                        | invoice_1               | p_2                     | 5           | true      |

  @from:cucumber
  Scenario: case 20
  - C_AggregationItem for SalesRep_ID is inactive
  - two sales orders, one order with salesRep_ID set and the second one with no salesRep_ID => one invoice with no SalesRep_ID
    Given load C_AggregationItem
      | C_AggregationItem_ID.Identifier | OPT.C_AggregationItem_ID |
      | a_1                             | 540071                   |
    And update C_AggregationItem
      | C_AggregationItem_ID.Identifier | IsActive |
      | a_1                             | false    |
    And metasfresh contains M_Products:
      | Identifier | Name                 |
      | p_1        | salesProduct_15100_1 |
      | p_2        | salesProduct_15100_2 |
    And metasfresh contains M_PricingSystems
      | Identifier | Name                      | Value                      | OPT.Description            | OPT.IsActive |
      | ps_1       | pricing_system_name_15100 | pricing_system_value_15100 | pricing_system_description | true         |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name                  | OPT.Description | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | pl_1       | ps_1                          | DE                        | EUR                 | price_list_name_15100 | null            | true  | false         | 2              | true         |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name                 | ValidFrom  |
      | plv_1      | pl_1                      | salesOrder-PLV_15100 | 2021-04-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_1       | plv_1                             | p_1                     | 10.0     | PCE               | Normal                        |
      | pp_2       | plv_1                             | p_2                     | 5.0      | PCE               | Normal                        |
    And metasfresh contains C_BPartners without locations:
      | Identifier    | Name                | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier | OPT.InvoiceRule |
      | endcustomer_1 | Endcustomer_15100_2 | N            | Y              | ps_1                          | D               |
    And metasfresh contains C_BPartner_Locations:
      | Identifier | GLN           | C_BPartner_ID.Identifier | OPT.IsShipToDefault | OPT.IsBillToDefault |
      | l_1        | 4212345000009 | endcustomer_1            | true                | true                |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.POReference   | OPT.C_PaymentTerm_ID | OPT.SalesRep_ID |
      | o_1        | true    | endcustomer_1            | 2021-04-17  | po_ref_mock_15100 | 1000012              | 100             |
      | o_2        | true    | endcustomer_1            | 2021-04-17  | po_ref_mock_15100 | 1000012              |                 |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_1       | o_1                   | p_1                     | 10         |
      | ol_2       | o_2                   | p_2                     | 5          |
    When the order identified by o_1 is completed
    And after not more than 30s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_s_1      | ol_1                      | N             |
    And 'generate shipments' process is invoked
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | s_s_1                            | D            | true                | false       |
    And after not more than 30s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | s_s_1                            | s_1                   |

    When the order identified by o_2 is completed
    And after not more than 30s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_s_2      | ol_2                      | N             |
    And 'generate shipments' process is invoked
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | s_s_2                            | D            | true                | false       |

    And after not more than 30s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | s_s_2                            | s_2                   |
    Then enqueue candidate for invoicing and after not more than 30s, the invoice is found
      | C_Order_ID.Identifier | C_Invoice_ID.Identifier |
      | o_1,o_2               | invoice_1               |
    And validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | OPT.POReference   | paymentTerm | processed | docStatus | OPT.SalesRep_ID |
      | invoice_1               | endcustomer_1            | l_1                               | po_ref_mock_15100 | 1000002     | true      | CO        | null            |
    And validate created invoice lines
      | C_InvoiceLine_ID.Identifier | C_Invoice_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | Processed |
      | il_1                        | invoice_1               | p_1                     | 10          | true      |
      | il_2                        | invoice_1               | p_2                     | 5           | true      |

  @from:cucumber
  Scenario: case 30
  - C_AggregationItem for SalesRep_ID is inactive
  - two sales orders with two different salesRep_ID => one invoice with SalesRep_ID is null
    Given load C_AggregationItem
      | C_AggregationItem_ID.Identifier | OPT.C_AggregationItem_ID |
      | a_1                             | 540071                   |
    And update C_AggregationItem
      | C_AggregationItem_ID.Identifier | IsActive |
      | a_1                             | false    |
    And metasfresh contains M_Products:
      | Identifier | Name                 |
      | p_1        | salesProduct_15100_1 |
      | p_2        | salesProduct_15100_2 |
    And metasfresh contains M_PricingSystems
      | Identifier | Name                      | Value                      | OPT.Description            | OPT.IsActive |
      | ps_1       | pricing_system_name_15100 | pricing_system_value_15100 | pricing_system_description | true         |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name                  | OPT.Description | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | pl_1       | ps_1                          | DE                        | EUR                 | price_list_name_15100 | null            | true  | false         | 2              | true         |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name                 | ValidFrom  |
      | plv_1      | pl_1                      | salesOrder-PLV_15100 | 2021-04-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_1       | plv_1                             | p_1                     | 10.0     | PCE               | Normal                        |
      | pp_2       | plv_1                             | p_2                     | 5.0      | PCE               | Normal                        |
    And metasfresh contains C_BPartners without locations:
      | Identifier    | Name                | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier | OPT.InvoiceRule |
      | endcustomer_1 | Endcustomer_15100_3 | N            | Y              | ps_1                          | D               |
    And metasfresh contains C_BPartner_Locations:
      | Identifier | GLN           | C_BPartner_ID.Identifier | OPT.IsShipToDefault | OPT.IsBillToDefault |
      | l_1        | 4312345000009 | endcustomer_1            | true                | true                |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.POReference   | OPT.C_PaymentTerm_ID | OPT.SalesRep_ID |
      | o_1        | true    | endcustomer_1            | 2021-04-17  | po_ref_mock_15100 | 1000012              | 100             |
      | o_2        | true    | endcustomer_1            | 2021-04-17  | po_ref_mock_15100 | 1000012              | 99              |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_1       | o_1                   | p_1                     | 10         |
      | ol_2       | o_2                   | p_2                     | 5          |
    When the order identified by o_1 is completed
    And after not more than 30s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_s_1      | ol_1                      | N             |
    And 'generate shipments' process is invoked
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | s_s_1                            | D            | true                | false       |
    And after not more than 30s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | s_s_1                            | s_1                   |

    When the order identified by o_2 is completed
    And after not more than 30s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_s_2      | ol_2                      | N             |
    And 'generate shipments' process is invoked
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | s_s_2                            | D            | true                | false       |

    And after not more than 30s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | s_s_2                            | s_2                   |
    Then enqueue candidate for invoicing and after not more than 30s, the invoice is found
      | C_Order_ID.Identifier | C_Invoice_ID.Identifier |
      | o_1,o_2               | invoice_1               |
    And validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | OPT.POReference   | paymentTerm | processed | docStatus | OPT.SalesRep_ID |
      | invoice_1               | endcustomer_1            | l_1                               | po_ref_mock_15100 | 1000002     | true      | CO        | null            |
    And validate created invoice lines
      | C_InvoiceLine_ID.Identifier | C_Invoice_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | Processed |
      | il_1                        | invoice_1               | p_1                     | 10          | true      |
      | il_2                        | invoice_1               | p_2                     | 5           | true      |

  @from:cucumber
  Scenario: case 40
  - C_AggregationItem for SalesRep_ID is active
  - two sales orders with two different salesRep_ID => two invoices with their respective SalesRep_ID
    Given load C_AggregationItem
      | C_AggregationItem_ID.Identifier | OPT.C_AggregationItem_ID |
      | a_1                             | 540071                   |
    And update C_AggregationItem
      | C_AggregationItem_ID.Identifier | IsActive |
      | a_1                             | true     |
    And metasfresh contains M_Products:
      | Identifier | Name                 |
      | p_1        | salesProduct_15100_1 |
      | p_2        | salesProduct_15100_2 |
    And metasfresh contains M_PricingSystems
      | Identifier | Name                      | Value                      | OPT.Description            | OPT.IsActive |
      | ps_1       | pricing_system_name_15100 | pricing_system_value_15100 | pricing_system_description | true         |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name                  | OPT.Description | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | pl_1       | ps_1                          | DE                        | EUR                 | price_list_name_15100 | null            | true  | false         | 2              | true         |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name                 | ValidFrom  |
      | plv_1      | pl_1                      | salesOrder-PLV_15100 | 2021-04-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_1       | plv_1                             | p_1                     | 10.0     | PCE               | Normal                        |
      | pp_2       | plv_1                             | p_2                     | 5.0      | PCE               | Normal                        |
    And metasfresh contains C_BPartners without locations:
      | Identifier    | Name                | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier | OPT.InvoiceRule |
      | endcustomer_1 | Endcustomer_15100_4 | N            | Y              | ps_1                          | D               |
    And metasfresh contains C_BPartner_Locations:
      | Identifier | GLN           | C_BPartner_ID.Identifier | OPT.IsShipToDefault | OPT.IsBillToDefault |
      | l_1        | 4412345000009 | endcustomer_1            | true                | true                |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.POReference   | OPT.C_PaymentTerm_ID | OPT.SalesRep_ID |
      | o_1        | true    | endcustomer_1            | 2021-04-17  | po_ref_mock_15100 | 1000012              | 100             |
      | o_2        | true    | endcustomer_1            | 2021-04-17  | po_ref_mock_15100 | 1000012              | 99              |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_1       | o_1                   | p_1                     | 10         |
      | ol_2       | o_2                   | p_2                     | 5          |
    When the order identified by o_1 is completed
    And after not more than 30s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_s_1      | ol_1                      | N             |
    And 'generate shipments' process is invoked
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | s_s_1                            | D            | true                | false       |
    And after not more than 30s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | s_s_1                            | s_1                   |

    When the order identified by o_2 is completed
    And after not more than 30s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_s_2      | ol_2                      | N             |
    And 'generate shipments' process is invoked
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | s_s_2                            | D            | true                | false       |

    And after not more than 30s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | s_s_2                            | s_2                   |
    Then enqueue candidate for invoicing and after not more than 30s, the invoice is found
      | C_Order_ID.Identifier | C_Invoice_ID.Identifier |
      | o_1,o_2               | invoice_1,invoice_2     |
    And validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | OPT.POReference   | paymentTerm | processed | docStatus | OPT.SalesRep_ID |
      | invoice_1               | endcustomer_1            | l_1                               | po_ref_mock_15100 | 1000002     | true      | CO        | 100             |
      | invoice_2               | endcustomer_1            | l_1                               | po_ref_mock_15100 | 1000002     | true      | CO        | 99              |
    And validate created invoice lines
      | C_InvoiceLine_ID.Identifier | C_Invoice_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | Processed |
      | il_1                        | invoice_1               | p_1                     | 10          | true      |
      | il_2                        | invoice_2               | p_2                     | 5           | true      |
    And update C_AggregationItem
      | C_AggregationItem_ID.Identifier | IsActive |
      | a_1                             | false    |

  @from:cucumber
  Scenario: case 50
  - C_AggregationItem for SalesRep_ID is active
  - two sales orders with same salesRep_ID => one invoice with the respective SalesRep_ID
    Given load C_AggregationItem
      | C_AggregationItem_ID.Identifier | OPT.C_AggregationItem_ID |
      | a_1                             | 540071                   |
    And update C_AggregationItem
      | C_AggregationItem_ID.Identifier | IsActive |
      | a_1                             | true     |
    And metasfresh contains M_Products:
      | Identifier | Name                 |
      | p_1        | salesProduct_15100_1 |
      | p_2        | salesProduct_15100_2 |
    And metasfresh contains M_PricingSystems
      | Identifier | Name                      | Value                      | OPT.Description            | OPT.IsActive |
      | ps_1       | pricing_system_name_15100 | pricing_system_value_15100 | pricing_system_description | true         |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name                  | OPT.Description | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | pl_1       | ps_1                          | DE                        | EUR                 | price_list_name_15100 | null            | true  | false         | 2              | true         |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name                 | ValidFrom  |
      | plv_1      | pl_1                      | salesOrder-PLV_15100 | 2021-04-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_1       | plv_1                             | p_1                     | 10.0     | PCE               | Normal                        |
      | pp_2       | plv_1                             | p_2                     | 5.0      | PCE               | Normal                        |
    And metasfresh contains C_BPartners without locations:
      | Identifier    | Name                | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier | OPT.InvoiceRule |
      | endcustomer_1 | Endcustomer_15100_5 | N            | Y              | ps_1                          | D               |
    And metasfresh contains C_BPartner_Locations:
      | Identifier | GLN           | C_BPartner_ID.Identifier | OPT.IsShipToDefault | OPT.IsBillToDefault |
      | l_1        | 4512345000009 | endcustomer_1            | true                | true                |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.POReference   | OPT.C_PaymentTerm_ID | OPT.SalesRep_ID |
      | o_1        | true    | endcustomer_1            | 2021-04-17  | po_ref_mock_15100 | 1000012              | 100             |
      | o_2        | true    | endcustomer_1            | 2021-04-17  | po_ref_mock_15100 | 1000012              | 100             |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_1       | o_1                   | p_1                     | 10         |
      | ol_2       | o_2                   | p_2                     | 5          |
    When the order identified by o_1 is completed
    And after not more than 30s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_s_1      | ol_1                      | N             |
    And 'generate shipments' process is invoked
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | s_s_1                            | D            | true                | false       |
    And after not more than 30s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | s_s_1                            | s_1                   |

    When the order identified by o_2 is completed
    And after not more than 30s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_s_2      | ol_2                      | N             |
    And 'generate shipments' process is invoked
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | s_s_2                            | D            | true                | false       |

    And after not more than 30s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | s_s_2                            | s_2                   |
    Then enqueue candidate for invoicing and after not more than 30s, the invoice is found
      | C_Order_ID.Identifier | C_Invoice_ID.Identifier |
      | o_1,o_2               | invoice_1               |
    And validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | OPT.POReference   | paymentTerm | processed | docStatus | OPT.SalesRep_ID |
      | invoice_1               | endcustomer_1            | l_1                               | po_ref_mock_15100 | 1000002     | true      | CO        | 100             |
    And validate created invoice lines
      | C_InvoiceLine_ID.Identifier | C_Invoice_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | Processed |
      | il_1                        | invoice_1               | p_1                     | 10          | true      |
      | il_2                        | invoice_1               | p_2                     | 5           | true      |
    And update C_AggregationItem
      | C_AggregationItem_ID.Identifier | IsActive |
      | a_1                             | false    |
