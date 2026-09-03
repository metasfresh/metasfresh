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
  Scenario: A "Create Invoices" run that fails for the selected candidate notifies the user who started it
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

    # InvoiceRule=Immediate makes the candidate invoiceable without a delivery, so the run really tries to invoice it.
    And update invoice candidates
      | C_Invoice_Candidate_ID.Identifier | OPT.InvoiceRule_Override |
      | ic_ie_1                           | I                        |
    And after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice |
      | ic_ie_1                           | ol_ie_1                   | 10           |

    # Credit stop makes completing the generated invoice throw @BPartnerCreditStop@, so the run genuinely fails.
    And upsert C_BPartner_Stats
      | C_BPartner_ID.Identifier | SOCreditStatus.Code |
      | endcustomer_ie_1         | S                   |

    And load AD_User:
      | AD_User_ID.Identifier | Login      |
      | user_metasfresh       | metasfresh |
    And load AD_Message:
      | Identifier        | Value                |
      | msgInvoicingError | Event_InvoicingError |

    # The note is looked up with firstOnly(), so drop any Event_InvoicingError note this DB already
    # carries (e.g. from an earlier local run of this feature); otherwise the lookup finds two rows.
    And AD_Note table is reset

    # A failing candidate is never processed, so the "wait until processed" variant would only time out.
    When process invoice candidates and verify C_Invoice_Candidate is not processed after 30s
      | C_Invoice_Candidate_ID.Identifier |
      | ic_ie_1                           |

    # IsInvoicingError and not IsError: the "update invalid invoice candidates" run that follows clears IsError again.
    Then validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | IsInvoicingError | OPT.Processed |
      | ic_ie_1                           | true             | false         |

    And after not more than 60s, validate AD_Note:
      | Identifier | AD_Message_ID.Identifier | OPT.AD_User_ID.Identifier |
      | note_1     | msgInvoicingError        | user_metasfresh           |

  @Id:S0163_200
  @from:cucumber
@allure.label.epic:E0340_Invoicing
@allure.label.feature:F00701_Sales_Invoice_Candidates
@allure.label.epic:E0225_Accounting
@allure.label.feature:F01010.3_Match_Invoice
@F00701
  Scenario: A "Create Invoices" run over a mixed selection still invoices the good candidate and reports the failed one
    Given metasfresh contains M_Products:
      | Identifier | Name              |
      | p_mx_1     | salesProduct_mx_1 |
    And metasfresh contains M_PricingSystems
      | Identifier | Name                   | Value                   | OPT.IsActive |
      | ps_mx_1    | mx_pricing_system_name | mx_pricing_system_value | true         |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name               | OPT.Description | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | pl_mx_1    | ps_mx_1                       | DE                        | EUR                 | mx_price_list_name | null            | true  | false         | 2              | true         |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name              | ValidFrom  |
      | plv_mx_1   | pl_mx_1                   | mx_salesOrder-PLV | 2021-04-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_mx_1    | plv_mx_1                          | p_mx_1                  | 10.0     | PCE               | Normal                        |

    And metasfresh contains C_BPartners:
      | Identifier       | Name               | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | customer_mx_good | mx_GoodCustomer    | N            | Y              | ps_mx_1                       |
      | customer_mx_bad  | mx_BlockedCustomer | N            | Y              | ps_mx_1                       |
    And metasfresh contains C_BPartner_Locations:
      | Identifier | GLN             | C_BPartner_ID.Identifier | OPT.IsShipToDefault | OPT.IsBillToDefault |
      | l_mx_good  | mx_bPLocation_g | customer_mx_good         | Y                   | Y                   |
      | l_mx_bad   | mx_bPLocation_b | customer_mx_bad          | Y                   | Y                   |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered |
      | o_mx_good  | true    | customer_mx_good         | 2021-04-17  |
      | o_mx_bad   | true    | customer_mx_bad          | 2021-04-17  |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_mx_good | o_mx_good             | p_mx_1                  | 10         |
      | ol_mx_bad  | o_mx_bad              | p_mx_1                  | 10         |
    And the order identified by o_mx_good is completed
    And the order identified by o_mx_bad is completed
    And after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice |
      | ic_mx_good                        | ol_mx_good                | 0            |
      | ic_mx_bad                         | ol_mx_bad                 | 0            |

    # InvoiceRule=Immediate makes both candidates invoiceable without a delivery, so the run really tries to invoice them.
    And update invoice candidates
      | C_Invoice_Candidate_ID.Identifier | OPT.InvoiceRule_Override |
      | ic_mx_good                        | I                        |
      | ic_mx_bad                         | I                        |
    And after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice |
      | ic_mx_good                        | ol_mx_good                | 10           |
      | ic_mx_bad                         | ol_mx_bad                 | 10           |

    # The two candidates have different bill partners, so they aggregate into two invoice headers with
    # separate transactions: the good one commits, the credit-stopped one rolls back and is marked failed.
    And upsert C_BPartner_Stats
      | C_BPartner_ID.Identifier | SOCreditStatus.Code |
      | customer_mx_bad          | S                   |

    And load AD_User:
      | AD_User_ID.Identifier | Login      |
      | user_metasfresh       | metasfresh |
    And load AD_Message:
      | Identifier        | Value                |
      | msgInvoicingError | Event_InvoicingError |

    # The note is looked up with firstOnly(), so drop the preceding scenario's Event_InvoicingError note first.
    And AD_Note table is reset

    When process invoice candidates
      | C_Invoice_Candidate_ID.Identifier |
      | ic_mx_good,ic_mx_bad              |

    Then after not more than 60s, C_Invoice are found:
      | C_Invoice_ID.Identifier | C_Invoice_Candidate_ID.Identifier |
      | invoice_mx_good         | ic_mx_good                        |
    And validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | processed | DocStatus |
      | invoice_mx_good         | customer_mx_good         | l_mx_good                         | true      | CO        |

    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | IsInvoicingError | OPT.Processed |
      | ic_mx_good                        | false            | true          |
      | ic_mx_bad                         | true             | false         |

    And after not more than 60s, validate AD_Note:
      | Identifier | AD_Message_ID.Identifier | OPT.AD_User_ID.Identifier |
      | note_1     | msgInvoicingError        | user_metasfresh           |

  @Id:S0163_300
  @from:cucumber
@allure.label.epic:E0340_Invoicing
@allure.label.feature:F00701_Sales_Invoice_Candidates
@allure.label.epic:E0225_Accounting
@allure.label.feature:F01010.3_Match_Invoice
@F00701
  Scenario: A "Create Invoices" run reports the candidate the enqueuer silently skipped
    # The defect this covers: the enqueuer drops a candidate with QtyOrdered <> 0 and
    # QtyToInvoice = 0 (InvoiceCandidateEnqueuer.isEligibleForEnqueueing, "task 04372" branch).
    # The skip reason goes to Loggables only, InvoiceCandidateEnqueueResult carries just the
    # enqueued count, and InvoiceGenerate_No_Candidates_Selected fires only when NOTHING is
    # enqueued -- so in a mixed selection the good candidate is invoiced and the dropped one is
    # never mentioned anywhere the user looks.
    #
    # NOT the credit-stop mechanism of the scenario above: that candidate IS enqueued and then
    # fails inside InvoiceCandBLCreateInvoices, which is the only place Event_InvoicingError is
    # raised from. A skipped candidate never reaches that code, which is why the shipped
    # scenario went green against a different mechanism and left this path uncovered.
    Given metasfresh contains M_Products:
      | Identifier | Name              |
      | p_sk_1     | salesProduct_sk_1 |
    And metasfresh contains M_PricingSystems
      | Identifier | Name                   | Value                   | OPT.IsActive |
      | ps_sk_1    | sk_pricing_system_name | sk_pricing_system_value | true         |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name               | OPT.Description | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | pl_sk_1    | ps_sk_1                       | DE                        | EUR                 | sk_price_list_name | null            | true  | false         | 2              | true         |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name              | ValidFrom  |
      | plv_sk_1   | pl_sk_1                   | sk_salesOrder-PLV | 2021-04-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_sk_1    | plv_sk_1                          | p_sk_1                  | 10.0     | PCE               | Normal                        |

    # Different bill partners on purpose: same partner aggregates into ONE invoice header, so a
    # single selection could not show "one invoiced, one dropped" side by side.
    And metasfresh contains C_BPartners:
      | Identifier         | Name               | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | customer_sk_good   | sk_GoodCustomer    | N            | Y              | ps_sk_1                       |
      | customer_sk_skip   | sk_SkippedCustomer | N            | Y              | ps_sk_1                       |
    And metasfresh contains C_BPartner_Locations:
      | Identifier  | GLN              | C_BPartner_ID.Identifier | OPT.IsShipToDefault | OPT.IsBillToDefault |
      | l_sk_good   | sk_bPLocation_g  | customer_sk_good         | Y                   | Y                   |
      | l_sk_skip   | sk_bPLocation_s  | customer_sk_skip         | Y                   | Y                   |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered |
      | o_sk_good  | true    | customer_sk_good         | 2021-04-17  |
      | o_sk_skip  | true    | customer_sk_skip         | 2021-04-17  |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_sk_good | o_sk_good             | p_sk_1                  | 10         |
      | ol_sk_skip | o_sk_skip             | p_sk_1                  | 10         |
    And the order identified by o_sk_good is completed
    And the order identified by o_sk_skip is completed
    And after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice |
      | ic_sk_good                        | ol_sk_good                | 0            |
      | ic_sk_skip                        | ol_sk_skip                | 0            |

    # InvoiceRule=Immediate makes both invoiceable without a delivery, so the run really tries.
    # DateToInvoice_Override in the past clears the date gate, which isSkipCandidateFromInvoicing
    # evaluates BEFORE the quantity branch -- without it the date reason would mask the one under test.
    And update invoice candidates
      | C_Invoice_Candidate_ID.Identifier | OPT.InvoiceRule_Override | OPT.DateToInvoice_Override |
      | ic_sk_good                        | I                        | 2021-04-18                 |
      | ic_sk_skip                        | I                        | 2021-04-18                 |
    And after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice |
      | ic_sk_good                        | ol_sk_good                | 10           |
      | ic_sk_skip                        | ol_sk_skip                | 10           |

    # QtyToInvoice_Override = 0 with QtyOrdered still 10 is exactly the enqueuer's skip condition.
    And update invoice candidates
      | C_Invoice_Candidate_ID.Identifier | OPT.QtyToInvoice_Override |
      | ic_sk_skip                        | 0                         |
    And after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice |
      | ic_sk_skip                        | ol_sk_skip                | 0            |

    # The REAL process, not the InvoiceService shortcut: InvoiceService discards the enqueue result, so the
    # summary the user actually reads cannot be observed through it.
    When run the invoicing process for invoice candidates:
      | C_Invoice_Candidate_ID.Identifier |
      | ic_sk_good,ic_sk_skip             |

    # THE ASSERTION UNDER TEST. Before the fix the summary reported only what was enqueued; the dropped
    # candidate went to Loggables (the process log) and the user was told nothing about it.
    # "1 von 2" = one of the two selected candidates was not invoiced; the reason names the candidate.
    Then the invoicing run summary contains:
      | 1 von 2 |

    # The skipped candidate stays open and is NOT flagged as an error: being skipped is not a failure.
    # (That the GOOD candidate still gets invoiced in a mixed selection is covered by the scenario above,
    #  which drives the async batch; the queue processor is off here, so this scenario asserts the report.)
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | IsInvoicingError | OPT.Processed |
      | ic_sk_skip                        | false            | false         |
