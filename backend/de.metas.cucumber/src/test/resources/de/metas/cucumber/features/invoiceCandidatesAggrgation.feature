@from:cucumber
Feature: invoice generation and invoice candidates aggregation

  Background:
    Given the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION


  @from:cucumber
  @ordersWithMultipleSalesRep
  Scenario: sales orders with multiple salesRep_IDs, one invoice with SalesRep_ID = NULL
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
      | Identifier | GLN          | C_BPartner_ID.Identifier |
      | l_1        | 4012345000009| endcustomer_1            |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.POReference | OPT.C_PaymentTerm_ID | salesRep_ID|
      | o_3        | true    | endcustomer_1            | 2021-04-17  | po_ref_mock     | 1000012              | 1000010    |
      | o_4        | true    | endcustomer_1            | 2021-04-17  | po_ref_mock     | 1000012              | 1000020    |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_3       | o_3                   | p_1                     | 10         |
      | ol_4       | o_4                   | p_1                     | 10         |

    When the order identified by o_3 is completed
    And after not more than 30s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_s_3      | ol_3                      | N             |
    And generate shipments process is invoked
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | s_s_3                            | D            | true                | false       |
    And after not more than 30s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | s_s_3                            | s_3                   |
    When the order identified by o_4 is completed
    And after not more than 30s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_s_4      | ol_4                      | N             |
    And generate shipments process is invoked
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | s_s_4                            | D            | true                | false       |
    And after not more than 30s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | s_s_4                            | s_4                   |

    Then enqueue invoice candidates for invoicing and after not more than 30s, the invoice is found
      | C_Order_ID.Identifier | C_Invoice_ID.Identifier |
      | o_3                   | invoice_2               |
      | o_4                   | invoice_2               |
    And validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | poReference | paymentTerm | processed | docStatus |
      | invoice_2               | endcustomer_1            | l_1                               | po_ref_mock | 1000002     | true      | CO        |
    And validate created invoice lines
      | C_Invoice_ID.Identifier | M_Product_ID.Identifier | qtyinvoiced | processed |
      | invoice_2               | p_1                     | 10          | true      |
      | invoice_2               | p_1                     | 10          | true      |
    And validate invoice salesRep_ID
      | C_Invoice_ID.Identifier | salesRep_ID |
      | invoice_2               | 0           |