@from:cucumber
@allure.label.epic:E0340_Invoicing
@allure.label.feature:F00700_Invoicing
@F00700
@ghActions:run_on_executor4
Feature: Invoice override-due-date from Rechnungsdisposition
## F00700: Invoice

  Processing invoice candidates via "Auswahl Fakturieren" with the OverrideDueDate parameter:
  the override is applied only when the invoice candidate's payment term has
  IsAllowOverrideDueDate=Y; it is ignored otherwise.

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And set sys config boolean value true for sys config de.metas.report.jasper.IsMockReportService
    And metasfresh has date and time 2026-03-01T13:30:13+01:00[Europe/Berlin]
    And metasfresh contains M_Products:
      | Identifier |
      | p_1        |
    And metasfresh contains M_PricingSystems
      | Identifier | Name  | Value | IsActive |
      | ps_1       | ps_1  | ps_1  | true     |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | C_Country.CountryCode | C_Currency.ISO_Code | Name        | SOTrx | IsTaxIncluded | PricePrecision |
      | pl_sales   | ps_1                          | DE                    | EUR                 | sales-PL    | true  | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name  | ValidFrom  |
      | plv_sales  | pl_sales                  | plv   | 2026-01-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_1       | plv_sales                         | p_1                     | 10.0     | PCE               | Normal                        |
    And metasfresh contains C_BPartners:
      | Identifier | Name     | IsCustomer | M_PricingSystem_ID.Identifier |
      | customer   | customer | Y          | ps_1                          |
    And metasfresh contains C_BPartner_Locations:
      | Identifier   | GLN           | C_BPartner_ID.Identifier | IsShipToDefault | IsBillToDefault |
      | customerLoc  | 1234567890123 | customer                 | Y               | Y               |
    And metasfresh contains C_PaymentTerm
      | Identifier  | NetDays | IsAllowOverrideDueDate |
      | pt_allow    | 30      | Y                      |
      | pt_disallow | 30      | N                      |

  @from:cucumber
@allure.label.epic:E0340_Invoicing
@allure.label.feature:F00700_Invoicing
@F00700
  @Id:S0209_300
  Scenario: OverrideDueDate is applied only when the payment term permits override
    # One order on each payment term (flag Y and flag N). Both are completed, shipped, and invoice candidates created.
    And metasfresh contains C_Orders:
      | Identifier  | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | C_PaymentTerm_ID.Identifier |
      | o_allow     | true    | customer                 | 2026-03-01  | pt_allow                    |
      | o_disallow  | true    | customer                 | 2026-03-01  | pt_disallow                 |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_allow   | o_allow               | p_1                     | 1          |
      | ol_disallow| o_disallow            | p_1                     | 1          |
    And the order identified by o_allow is completed
    And the order identified by o_disallow is completed
    And after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice |
      | ic_allow                          | ol_allow                  | 0            |
      | ic_disallow                       | ol_disallow               | 0            |
    And update invoice candidates
      | C_Invoice_Candidate_ID.Identifier | OPT.InvoiceRule_Override |
      | ic_allow                          | I                        |
      | ic_disallow                       | I                        |
    And after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice |
      | ic_allow                          | ol_allow                  | 1            |
      | ic_disallow                       | ol_disallow               | 1            |
    # Enqueue both candidates together with OverrideDueDate=2026-12-31; run the real invoicing process.
    When process invoice candidates together and wait 600s for C_Invoice_Candidate to be processed
      | C_Invoice_Candidate_ID.Identifier | OPT.DateInvoiced | OPT.OverrideDueDate |
      | ic_allow                          | 2026-03-01       | 2026-12-31          |
      | ic_disallow                       | 2026-03-01       | 2026-12-31          |
    Then after not more than 60s, C_Invoice are found:
      | C_Invoice_Candidate_ID.Identifier | C_Invoice_ID.Identifier |
      | ic_allow                          | inv_allow               |
      | ic_disallow                       | inv_disallow            |
    # Payment term allows override: invoice picks up the requested date.
    And validate created invoices
      | Identifier | DocStatus | DueDate    |
      | inv_allow  | CO        | 2026-12-31 |
    # Payment term disallows override: invoice keeps the rule-based date (DateInvoiced + NetDays = 2026-03-01 + 30).
    And validate created invoices
      | Identifier   | DocStatus | DueDate    |
      | inv_disallow | CO        | 2026-03-31 |
