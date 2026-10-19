@from:cucumber
@topic:sqlFunction
@ghActions:run_on_executor7
Feature: SQL function result lines
  A core report SQL function is called with an invoice ID and its text result is checked line by line.

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2022-02-25T13:30:13+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION

    And metasfresh contains M_Products:
      | Identifier | Name                 |
      | product_1  | sql_function_product |
    And metasfresh contains M_PricingSystems
      | Identifier | Name                | Value               |
      | ps_1       | sql_function_ps     | sql_function_ps     |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name              | SOTrx | IsTaxIncluded | PricePrecision |
      | pl_so      | ps_1                          | DE                        | EUR                 | sql_function_pl   | true  | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name   | ValidFrom  |
      | plv_so     | pl_so                     | plv_so | 2022-01-20 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_TaxCategory_ID.InternalName | C_UOM_ID.X12DE355 |
      | pp_1       | plv_so                            | product_1               | 10.0     | Normal                        | PCE               |
    And metasfresh contains C_BPartners without locations:
      | Identifier  | Name                | M_PricingSystem_ID.Identifier | OPT.IsCustomer | OPT.CompanyName | OPT.InvoiceRule | C_PaymentTerm_ID.Value |
      | customer_1  | sql_function_bp     | ps_1                          | Y              | sql_function_bp | I               | 1000002                |
    And metasfresh contains C_Location:
      | C_Location_ID.Identifier | CountryCode | OPT.Address1   | OPT.Postal | OPT.City |
      | location_1               | DE          | Bahnhofstrasse | 8000       | Zuerich  |
    And metasfresh contains C_BPartner_Locations:
      | Identifier   | GLN           | C_BPartner_ID.Identifier | OPT.C_Location_ID.Identifier | OPT.IsShipTo | OPT.IsBillTo |
      | bpLocation_1 | 1234567891155 | customer_1               | location_1                   | true         | true         |


  # ##############################################################################################################
  # ##############################################################################################################
  # ##############################################################################################################
  # ##############################################################################################################
  Scenario: QR code function result of a completed invoice is checked line by line
    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.C_BPartner_Location_ID.Identifier | OPT.DeliveryRule | OPT.DeliveryViaRule | OPT.InvoiceRule | C_PaymentTerm_ID.Value |
      | order_1    | true    | customer_1               | 2022-02-02  | bpLocation_1                          | F                | S                   | I               | 1000002                |
    And metasfresh contains C_OrderLines:
      | Identifier  | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | orderLine_1 | order_1               | product_1               | 1          |
    And the order identified by order_1 is completed
    And after not more than 60s, C_Invoice_Candidates are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyToInvoice |
      | invoiceCand_1                     | orderLine_1               | 1                |
    And process invoice candidates
      | C_Invoice_Candidate_ID.Identifier |
      | invoiceCand_1                     |
    And after not more than 60s, C_Invoice are found:
      | C_Invoice_ID.Identifier | C_Invoice_Candidate_ID.Identifier |
      | invoice_1               | invoiceCand_1                     |

    Then the SQL function de_metas_endcustomer_fresh_reports.Docs_Sales_Invoice_QR_Code called with the ID of invoice_1 returns these lines:
      | LineNo | Value |
      | 1      | SPC   |
      | 2      | 0200  |
      | 3      | 1     |
      | 20     | EUR   |
