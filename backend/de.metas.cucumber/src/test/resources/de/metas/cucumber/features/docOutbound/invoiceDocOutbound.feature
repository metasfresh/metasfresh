@from:cucumber
@allure.label.epic:E0280_Document_and_Email_Management
@allure.label.feature:F00580_Outbound_Emails
@ghActions:run_on_executor2
Feature: Invoice doc outbound log - bill-to location email fallback
  A bill partner that is invoice-email-enabled at partner level and has a bill-to
  location email, but no bill contact, must still get its invoice emailable:
  C_Doc_Outbound_Log.CurrentEMailAddress must carry the bill-to location email.

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2022-02-01T13:30:13+01:00[Europe/Berlin]
    And set sys config boolean value false for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And set sys config boolean value false for sys config AUTO_SHIP_AND_INVOICE
    And set sys config boolean value true for sys config de.metas.report.jasper.IsMockReportService

  @Id:S30521_TC1
  Scenario: Invoice for an invoice-email-enabled partner with only a bill-to location email carries the location email
    Given metasfresh contains M_Products:
      | Identifier | Name         |
      | product    | test_product |
    And metasfresh contains M_PricingSystems
      | Identifier | Name           | Value          |
      | ps         | pricing_system | pricing_system |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name       | SOTrx | IsTaxIncluded | PricePrecision |
      | pl         | ps                            | DE                        | EUR                 | price_list | true  | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name | ValidFrom  |
      | plv        | pl                        | plv  | 2022-01-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp         | plv                               | product                 | 10.0     | PCE               | Normal                        |

    # Partner is invoice-email-enabled at PARTNER level; bill-to location has an email; NO bill contact (AD_User)
    And metasfresh contains C_BPartners:
      | Identifier | OPT.IsCustomer | M_PricingSystem_ID.Identifier | OPT.IsInvoiceEmailEnabled |
      | bpartner   | Y              | ps                            | Y                         |
    And metasfresh contains C_BPartner_Locations:
      | Identifier | GLN           | C_BPartner_ID.Identifier | OPT.IsShipToDefault | OPT.IsBillToDefault | OPT.EMail            |
      | bpLocation | 1111123456789 | bpartner                 | Y                   | Y                   | billto@location.test |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered |
      | order      | true    | bpartner                 | 2022-01-15  |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | orderLine  | order                 | product                 | 10         |
    And the order identified by order is completed

    And after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice |
      | ic                                | orderLine                 | 0            |
    And update invoice candidates
      | C_Invoice_Candidate_ID.Identifier | OPT.InvoiceRule_Override |
      | ic                                | I                       |
    And after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice |
      | ic                                | orderLine                 | 10           |
    When process invoice candidates and wait 60s for C_Invoice_Candidate to be processed
      | C_Invoice_Candidate_ID.Identifier | OPT.IsCompleteInvoices |
      | ic                                | true                   |
    And after not more than 60s, C_Invoice are found:
      | C_Invoice_Candidate_ID.Identifier | C_Invoice_ID.Identifier |
      | ic                                | invoice                 |

    Then after not more than 60s validate C_Doc_Outbound_Log:
      | C_Doc_Outbound_Log_ID.Identifier | Record_ID.Identifier | AD_Table.Name | OPT.CurrentEMailAddress | OPT.C_BPartner_ID.Identifier |
      | invoiceOutboundLog               | invoice              | C_Invoice     | billto@location.test    | bpartner                     |

  Scenario: reset global sys config
    Given set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And set sys config boolean value false for sys config de.metas.report.jasper.IsMockReportService
