@from:cucumber
@ghActions:run_on_executor2
Feature: invoice with aggregation attributes

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And set sys config boolean value false for sys config AUTO_SHIP_AND_INVOICE

  @from:cucumber
  Scenario: we can invoice a sales order with:
  - different product price UOM and product UOM
  - Aggregation Item Attribute = Per each shipment/receipt
    Given metasfresh has date and time 2021-04-16T13:30:13+01:00[Europe/Berlin]

    And metasfresh contains C_Aggregations:
      | Identifier | Name          | TableName           | EntityType                | OPT.AggregationUsageLevel |
      | a_1        | test_09032024 | C_Invoice_Candidate | de.metas.invoicecandidate | H                         |
    And load C_Aggregation:
      | C_Aggregation_ID.Identifier | OPT.C_Aggregation_ID |
      | a_2                         | 1000000              |
    And load C_Aggregation_Attributes:
      | Identifier | C_Aggregation_Attribute_ID |
      | aa_1       | 540001                     |
    And metasfresh contains C_AggregationItems:
      | Identifier | C_Aggregation_ID.Identifier | EntityType                | Type | OPT.Included_Aggregation_ID.Identifier | OPT.C_Aggregation_Attribute_ID.Identifier |
      | i_1        | a_1                         | de.metas.invoicecandidate | INC  | a_2                                    | null                                      |
      | i_2        | a_1                         | de.metas.invoicecandidate | ATR  | null                                   | aa_1                                      |

    And metasfresh contains M_Products:
      | Identifier | Name                    |
      | p_1        | d_salesProduct_09032024 |
    And metasfresh contains C_UOM_Conversions
      | M_Product_ID.Identifier | FROM_C_UOM_ID.X12DE355 | TO_C_UOM_ID.X12DE355 | MultiplyRate |
      | p_1                     | PCE                    | KGM                  | 0.25         |

    And metasfresh contains M_PricingSystems
      | Identifier | Name                           | Value                           | OPT.Description                       | OPT.IsActive |
      | ps_1       | d_pricing_system_name_09032024 | d_pricing_system_value_09032024 | d_pricing_system_description_09032024 | true         |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name                       | OPT.Description | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | pl_1       | ps_1                          | DE                        | EUR                 | d_price_list_name_09032024 | null            | true  | false         | 2              | true         |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name                      | ValidFrom  |
      | plv_1      | pl_1                      | d_salesOrder-PLV_09032024 | 2021-04-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_1       | plv_1                             | p_1                     | 10.0     | KGM               | Normal                        |
    And metasfresh contains C_BPartners:
      | Identifier    | Name                 | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier | OPT.InvoiceRule |
      | endcustomer_1 | Endcustomer_09032024 | N            | Y              | ps_1                          | D               |
    And metasfresh contains C_BPartner_Locations:
      | Identifier | GLN           | C_BPartner_ID.Identifier | OPT.IsShipToDefault | OPT.IsBillToDefault |
      | l_1        | 0123456189111 | endcustomer_1            | Y                   | Y                   |
    And update C_BPartner:
      | Identifier    | OPT.SO_Invoice_Aggregation_ID.Identifier |
      | endcustomer_1 | a_1                                      |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.POReference | OPT.C_PaymentTerm_ID |
      | o_1        | true    | endcustomer_1            | 2021-04-17  | po_ref_mock     | 1000012              |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_1       | o_1                   | p_1                     | 10         |
    When the order identified by o_1 is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_s_1      | ol_1                      | N             |
    And  'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | s_s_1                            | D            | true                | false       |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | s_s_1                            | s_1                   |
    And after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice |
      | ic_1                              | ol_1                      | 10           |
    When process invoice candidates and wait 60s for C_Invoice_Candidate to be processed
      | C_Invoice_Candidate_ID.Identifier |
      | ic_1                              |
    Then after not more than 60s, C_Invoice are found:
      | C_Invoice_Candidate_ID.Identifier | C_Invoice_ID.Identifier |
      | ic_1                              | invoice_1               |
    And validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | OPT.POReference | paymentTerm | processed | docStatus |
      | invoice_1               | endcustomer_1            | l_1                               | po_ref_mock     | 1000002     | true      | CO        |
    And validate created invoice lines
      | C_InvoiceLine_ID.Identifier | C_Invoice_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | Processed | OPT.QtyEntered | OPT.C_UOM_ID.X12DE355 | OPT.Price_UOM_ID.Identifier |
      | invoiceLine_1               | invoice_1               | p_1                     | 10          | true      | 2.5            | KGM                   | KGM                         |
