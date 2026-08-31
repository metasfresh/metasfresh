@from:cucumber
@allure.label.epic:E0340_Invoicing
@allure.label.feature:F00701_Sales_Invoice_Candidates
@allure.label.epic:E0225_Accounting
@allure.label.feature:F01010.3_Match_Invoice
@F00701
@ghActions:run_on_executor5
Feature: A failing "Create Invoices" run reports back to the user who started it
## F00701: Invoice Candidates

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2021-12-21T13:30:13+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And set sys config boolean value true for sys config de.metas.report.jasper.IsMockReportService

  @Id:S0163_100
  @from:cucumber
@allure.label.epic:E0340_Invoicing
@allure.label.feature:F00701_Sales_Invoice_Candidates
@allure.label.epic:E0225_Accounting
@allure.label.feature:F01010.3_Match_Invoice
@F00701
  Scenario: A "Create Invoices" run that fails for the selected candidate notifies nobody
    Given metasfresh contains M_Products:
      | Identifier | Name              |
      | p_ie_1     | salesProduct_ie_1 |
    And metasfresh contains M_PricingSystems
      | Identifier | Name                   | Value                   | OPT.IsActive |
      | ps_ie_1    | ie_pricing_system_name | ie_pricing_system_value | true         |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name               | OPT.Description | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | pl_ie_1    | ps_ie_1                       | DE                        | EUR                 | ie_price_list_name | null            | true  | false         | 2              | true         |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name              | ValidFrom  |
      | plv_ie_1   | pl_ie_1                   | ie_salesOrder-PLV | 2021-04-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_ie_1    | plv_ie_1                          | p_ie_1                  | 10.0     | PCE               | Normal                        |
    And metasfresh contains C_BPartners:
      | Identifier       | Name           | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | endcustomer_ie_1 | ie_Endcustomer | N            | Y              | ps_ie_1                       |
    And metasfresh contains C_BPartner_Locations:
      | Identifier | GLN           | C_BPartner_ID.Identifier | OPT.IsShipToDefault | OPT.IsBillToDefault |
      | l_ie_1     | ie_bPLocation | endcustomer_ie_1         | Y                   | Y                   |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered |
      | o_ie_1     | true    | endcustomer_ie_1         | 2021-04-17  |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_ie_1    | o_ie_1                | p_ie_1                  | 10         |
    And the order identified by o_ie_1 is completed
    And after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice |
      | ic_ie_1                           | ol_ie_1                   | 0            |

    # InvoiceRule=Immediate makes the candidate invoiceable without any delivery, so the run below
    # really does try to invoice it instead of skipping it for "nothing delivered yet".
    And update invoice candidates
      | C_Invoice_Candidate_ID.Identifier | OPT.InvoiceRule_Override |
      | ic_ie_1                           | I                        |
    And after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice |
      | ic_ie_1                           | ol_ie_1                   | 10           |

    # The customer is put on credit stop after the order was completed. Completing the generated
    # invoice then throws @BPartnerCreditStop@ (MInvoice.prepareIt), the invoice transaction is rolled
    # back and InvoiceCandBLCreateInvoices marks the candidate as failed. This is a genuine FAILURE of
    # the "Create Invoices" run - not a candidate that was quietly skipped.
    And upsert C_BPartner_Stats
      | C_BPartner_ID.Identifier | SOCreditStatus.Code |
      | endcustomer_ie_1         | S                   |

    And load AD_User:
      | AD_User_ID.Identifier | Login      |
      | user_metasfresh       | metasfresh |
    And load AD_Message:
      | Identifier        | Value                |
      | msgInvoicingError | Event_InvoicingError |

    # Not the "wait for the candidate to be processed" variant: a failing candidate is never
    # processed, so that variant would only time out.
    When process invoice candidates and verify C_Invoice_Candidate is not processed after 30s
      | C_Invoice_Candidate_ID.Identifier |
      | ic_ie_1                           |

    # Evidence that the run FAILED for this candidate rather than skipping it.
    # IsInvoicingError - not IsError - because the "update invalid invoice candidates" work package that
    # follows the failed run clears IsError/ErrorMsg again (InvoiceCandBL.resetError), while
    # IsInvoicingError/InvoicingErrorMsg keep the record of the failed invoicing run.
    Then validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | IsInvoicingError | OPT.Processed |
      | ic_ie_1                           | true             | false         |

    # ...and yet the user who started the run is told nothing: no notification is produced,
    # so no AD_Note exists. This is the defect.
    And after not more than 60s, validate AD_Note:
      | Identifier | AD_Message_ID.Identifier | OPT.AD_User_ID.Identifier |
      | note_1     | msgInvoicingError        | user_metasfresh           |
