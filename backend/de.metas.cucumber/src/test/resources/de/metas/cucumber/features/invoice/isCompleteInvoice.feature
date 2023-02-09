@from:cucumber
Feature: completeInvoice option when processing invoice candidates

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And set sys config boolean value true for sys config de.metas.report.jasper.IsMockReportService
    And metasfresh has date and time 2021-12-21T13:30:13+01:00[Europe/Berlin]

  @from:cucumber
  @Id:S0209_100
  Scenario: process IC with completeInvoices=N should result in Invoice docstatus=IP and IC should be invoiceable again after voiding Invoice
    Given metasfresh contains M_Products:
      | Identifier | Name              |
      | p_ci_1     | salesProduct_ci_1 |
    And metasfresh contains M_PricingSystems
      | Identifier | Name                           | Value                           | OPT.Description                       | OPT.IsActive |
      | ps_ci_1    | pricing_system_name_04011022_1 | pricing_system_value_04011022_1 | pricing_system_description_04011022_1 | true         |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name                       | OPT.Description | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | pl_ci_1    | ps_ci_1                       | DE                        | EUR                 | price_list_name_04112022_1 | null            | true  | false         | 2              | true         |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name                      | ValidFrom  |
      | plv_ci_1   | pl_ci_1                   | salesOrder-PLV_04011022_1 | 2021-04-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_ci_1    | plv_ci_1                          | p_ci_1                  | 10.0     | PCE               | Normal                        |
    And metasfresh contains C_BPartners:
      | Identifier       | Name                   | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | endcustomer_ci_1 | Endcustomer_04011022_1 | N            | Y              | ps_ci_1                       |
    And metasfresh contains C_BPartner_Locations:
      | Identifier | GLN           | C_BPartner_ID.Identifier | OPT.IsShipToDefault | OPT.IsBillToDefault |
      | l_ci_1     | 0123456789011 | endcustomer_ci_1         | Y                   | Y                   |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.POReference  | OPT.C_PaymentTerm_ID |
      | o_ci_1     | true    | endcustomer_ci_1         | 2021-04-17  | po_ref_mock_ci_1 | 1000012              |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_ci_1    | o_ci_1                | p_ci_1                  | 10         |
    And the order identified by o_ci_1 is completed
    And after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice |
      | ic_ci_1                           | ol_ci_1                   | 0            |
    And update invoice candidates
      | C_Invoice_Candidate_ID.Identifier | OPT.InvoiceRule_Override |
      | ic_ci_1                           | I                        |
    And after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice |
      | ic_ci_1                           | ol_ci_1                   | 10           |
    When process invoice candidates and wait 600s for C_Invoice_Candidate to be processed
      | C_Invoice_Candidate_ID.Identifier | OPT.IsCompleteInvoices |
      | ic_ci_1                           | false                  |
    Then after not more than 60s, C_Invoice are found:
      | C_Invoice_Candidate_ID.Identifier | C_Invoice_ID.Identifier | OPT.DocStatus |
      | ic_ci_1                           | invoice_ci_1            | IP            |
    And validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | OPT.POReference  | paymentTerm | processed | docStatus |
      | invoice_ci_1            | endcustomer_ci_1         | l_ci_1                            | po_ref_mock_ci_1 | 1000002     | false     | IP        |
    And the invoice identified by invoice_ci_1 is voided
    And validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | OPT.POReference  | paymentTerm | processed | docStatus |
      | invoice_ci_1            | endcustomer_ci_1         | l_ci_1                            | po_ref_mock_ci_1 | 1000002     | true      | VO        |
    And process invoice candidates and wait 60s for C_Invoice_Candidate to be processed
      | C_Invoice_Candidate_ID.Identifier | OPT.IsCompleteInvoices |
      | ic_ci_1                           | false                  |
    And after not more than 60s, C_Invoice are found:
      | C_Invoice_Candidate_ID.Identifier | C_Invoice_ID.Identifier | OPT.DocStatus |
      | ic_ci_1                           | invoice_ci_3            | IP            |
    And validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | OPT.POReference  | paymentTerm | processed | docStatus |
      | invoice_ci_3            | endcustomer_ci_1         | l_ci_1                            | po_ref_mock_ci_1 | 1000002     | false     | IP        |

  @from:cucumber
  @Id:S0209_110
  Scenario: process IC with completeInvoice=Y should result in processed IC
    Given metasfresh contains M_Products:
      | Identifier | Name              |
      | p_ci_2     | salesProduct_ci_2 |
    And metasfresh contains M_PricingSystems
      | Identifier | Name                           | Value                           | OPT.Description                       | OPT.IsActive |
      | ps_ci_2    | pricing_system_name_04011022_2 | pricing_system_value_04011022_2 | pricing_system_description_04011022_2 | true         |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name                       | OPT.Description | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | pl_ci_2    | ps_ci_2                       | DE                        | EUR                 | price_list_name_04011022_2 | null            | true  | false         | 2              | true         |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name                      | ValidFrom  |
      | plv_ci_2   | pl_ci_2                   | salesOrder-PLV_04011022_2 | 2021-04-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_ci_2    | plv_ci_2                          | p_ci_2                  | 10.0     | PCE               | Normal                        |
    And metasfresh contains C_BPartners:
      | Identifier       | Name                   | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | endcustomer_ci_2 | Endcustomer_04011022_2 | N            | Y              | ps_ci_2                       |
    And metasfresh contains C_BPartner_Locations:
      | Identifier | GLN           | C_BPartner_ID.Identifier | OPT.IsShipToDefault | OPT.IsBillToDefault |
      | l_ci_2     | 0123456789011 | endcustomer_ci_2         | Y                   | Y                   |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.POReference  | OPT.C_PaymentTerm_ID |
      | o_ci_2     | true    | endcustomer_ci_2         | 2021-04-17  | po_ref_mock_ci_2 | 1000012              |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_ci_2    | o_ci_2                | p_ci_2                  | 10         |
    And the order identified by o_ci_2 is completed
    And after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice |
      | ic_ci_2                           | ol_ci_2                   | 0            |
    And update invoice candidates
      | C_Invoice_Candidate_ID.Identifier | OPT.InvoiceRule_Override |
      | ic_ci_2                           | I                        |
    And after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice |
      | ic_ci_2                           | ol_ci_2                   | 10           |
    When process invoice candidates and wait 60s for C_Invoice_Candidate to be processed
      | C_Invoice_Candidate_ID.Identifier | OPT.IsCompleteInvoices |
      | ic_ci_2                           | true                   |
    And after not more than 60s, C_Invoice are found:
      | C_Invoice_Candidate_ID.Identifier | C_Invoice_ID.Identifier |
      | ic_ci_2                           | invoice_ci_2            |
    Then validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | OPT.POReference  | paymentTerm | processed | docStatus |
      | invoice_ci_2            | endcustomer_ci_2         | l_ci_2                            | po_ref_mock_ci_2 | 1000002     | true      | CO        |
