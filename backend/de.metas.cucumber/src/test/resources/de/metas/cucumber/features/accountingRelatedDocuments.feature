@from:cucumber
@ghActions:run_on_executor2
Feature: accounting related documents

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2023-12-18T13:30:13+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And set sys config boolean value false for sys config AUTO_SHIP_AND_INVOICE

  @from:cucumber
  @Id:S0250_100
  Scenario: Make sure after posting an invoice we also get the accounting related documents
    And metasfresh contains M_Products:
      | Identifier | Name            |
      | product1   | acctRelatedDocs |
    And metasfresh contains M_PricingSystems
      | Identifier | Name            | Value           |
      | ps_1       | acctRelatedDocs | acctRelatedDocs |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name            | SOTrx | IsTaxIncluded | PricePrecision |
      | pl_1       | ps_1                          | CH                        | CHF                 | acctRelatedDocs | false | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name            | ValidFrom  |
      | plv_1      | pl_1                      | acctRelatedDocs | 2021-01-01 |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | plv_1                             | product1                | 10.0     | PCE               | TaxFree                       |
    And metasfresh contains C_BPartners:
      | Identifier | Name            | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | bp         | acctRelatedDocs | Y            | N              | ps_1                          |
    And metasfresh contains C_BPartner_Locations:
      | Identifier | GLN           | C_BPartner_ID.Identifier | OPT.IsShipToDefault | OPT.IsBillToDefault | CountryCode |
      | bpl        | 0123456789011 | bp                       | Y                   | Y                   | CH          |
    And metasfresh contains C_Orders:
      | Identifier | DocBaseType | C_BPartner_ID.Identifier | DateOrdered | OPT.M_PricingSystem_ID.Identifier | InvoiceRule |
      | o_1        | POO         | bp                       | 2023-12-17  | ps_1                              | I           |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_1       | o_1                   | product1                | 10         |
    When the order identified by o_1 is completed
    And after not more than 30s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice |
      | ic1                               | ol_1                      | 10           |
    And process invoice candidates
      | C_Invoice_Candidate_ID.Identifier |
      | ic1                               |
    Then after not more than 30s, C_Invoice are found:
      | C_Invoice_ID.Identifier | C_Invoice_Candidate_ID.Identifier |
      | invoice_1               | ic1                               |
    And after not more than 30s, the invoice document with identifier invoice_1 has the following accounting records:
      | OPT.AccountConceptualName | DR  | CR  |
      | P_Expense_Acct            | 100 | 0   |
      | V_Liability_Acct          | 0   | 100 |
    And after not more than 1s, the invoice document with identifier invoice_1 has the following related documents:
      | InternalName | Id        |
      | Fact_Acct    | Fact_Acct |
