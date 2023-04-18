@from:cucumber
Feature: Create customer invoice candidates from effort control

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has current date and time
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION

    And metasfresh contains M_Products:
      | Identifier         | Value                       | Name                        |
      | invoiceableProduct | invoiceableProduct_12102022 | invoiceableProduct_12102022 |
    And metasfresh contains M_PricingSystems
      | Identifier | Name        | Value       |
      | ps_SO      | ps_12102022 | ps_12102022 |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name           | SOTrx | IsTaxIncluded | PricePrecision |
      | pl_SO      | ps_SO                         | DE                        | EUR                 | pl_SO_12102022 | true  | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name   | ValidFrom  |
      | plv_SO     | pl_SO                     | plv_SO | 2022-09-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_SO      | plv_SO                            | invoiceableProduct      | 10.0     | PCE               | Normal                        |

    And metasfresh contains C_BPartners without locations:
      | Identifier  | Name                 | Value                | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | customer_SO | customer_SO_12102022 | customer_SO_12102022 | Y              | ps_SO                         |
    And metasfresh contains C_BPartner_Locations:
      | Identifier          | GLN           | C_BPartner_ID.Identifier | OPT.IsShipToDefault | OPT.IsBillToDefault |
      | customerLocation_SO | 1210202222221 | customer_SO              | Y                   | Y                   |
    And metasfresh contains AD_Users:
      | AD_User_ID.Identifier | Name                     | OPT.EMail                          | OPT.C_BPartner_ID.Identifier | OPT.IsBillToContact_Default |
      | customerUser_SO       | customerUser_SO_12102022 | customerUser_SO_12102022@email.com | customer_SO                  | true                        |

    And create or update C_Project:
      | C_Project_ID.Identifier | Name                 | Value                | C_Currency_ID.ISO_Code | OPT.C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | OPT.AD_User_ID.Identifier | OPT.M_Product_ID.Identifier |
      | testProject             | testProject_12102022 | testProject_12102022 | EUR                    | customer_SO                  | customerLocation_SO                   | customerUser_SO           | invoiceableProduct          |

  @from:cucumber
  @Id:S0199_100
  Scenario: Generate invoice candidate from effort control with two budget issues
    #  `Generate invoice candidate` process generates two invoice candidates for each budget issue
    #  invoke `generate invoice` process for the created invoice candidates
    #  there is one invoice generated per <C_Project_Id, C_Activity_ID> group and each issue ends up in its own invoice line based on S_Issue_ID
    Given metasfresh contains C_Activity:
      | C_Activity_ID.Identifier | Name                   | Value                  |
      | costCenter100            | costCenter100_12102022 | costCenter100_12102022 |
    And metasfresh contains S_Issue:
      | S_Issue_ID.Identifier | Value                     | OPT.Name                  | IssueType | IsEffortIssue | OPT.InvoiceableEffort | OPT.C_Activity_ID.Identifier | OPT.C_Project_ID.Identifier | OPT.ExternalIssueNo | OPT.Status |
      | budgetIssue100_1      | budgetIssue100_1_12102022 | budgetIssue100_1_12102022 | Internal  | N             | 12                    | costCenter100                | testProject                 | 1210221             | New        |
      | budgetIssue100_2      | budgetIssue100_2_12102022 | budgetIssue100_2_12102022 | Internal  | N             | 4                     | costCenter100                | testProject                 | 1210222             | New        |
    And after not more than 10s, S_EffortControl is found:
      | S_EffortControl_ID.Identifier | C_Activity_ID.Identifier | C_Project_ID.Identifier |
      | effortControl100              | costCenter100            | testProject             |
    And after not more than 10s, S_EffortControl is validated:
      | S_EffortControl_ID.Identifier | C_Activity_ID.Identifier | C_Project_ID.Identifier | OPT.InvoiceableHours | OPT.IsIssueClosed |
      | effortControl100              | costCenter100            | testProject             | 16                   | false             |

    When 'generate invoice candidate' from effort control process is invoked
      | S_EffortControl_ID.Identifier |
      | effortControl100              |
    Then after not more than 30s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | QtyToInvoice | OPT.AD_Table_ID.TableName | OPT.Record_ID.Identifier |
      | candIssue100_1                    | 12           | S_Issue                   | budgetIssue100_1         |
      | candIssue100_2                    | 4            | S_Issue                   | budgetIssue100_2         |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | QtyToInvoice | OPT.QtyOrdered | OPT.QtyDelivered | OPT.QtyInvoiced | OPT.M_Product_ID.Identifier | OPT.Bill_BPartner_ID.Identifier | OPT.Bill_Location_ID.Identifier | OPT.Bill_User_ID.Identifier | OPT.Processed | OPT.InvoiceRule | OPT.C_Activity_ID.Identifier | OPT.C_Project_ID.Identifier | OPT.Description                    |
      | candIssue100_1                    | 12           | 12             | 0                | 0               | invoiceableProduct          | customer_SO                     | customerLocation_SO             | customerUser_SO             | false         | I               | costCenter100                | testProject                 | 1210221\nbudgetIssue100_1_12102022 |
      | candIssue100_2                    | 4            | 4              | 0                | 0               | invoiceableProduct          | customer_SO                     | customerLocation_SO             | customerUser_SO             | false         | I               | costCenter100                | testProject                 | 1210222\nbudgetIssue100_2_12102022 |
    And validate S_Issue:
      | S_Issue_ID.Identifier | Value                     | OPT.Name                  | IssueType | IsEffortIssue | OPT.InvoiceableEffort | OPT.C_Activity_ID.Identifier | OPT.C_Project_ID.Identifier | OPT.ExternalIssueNo | OPT.Status | OPT.Processed |
      | budgetIssue100_1      | budgetIssue100_1_12102022 | budgetIssue100_1_12102022 | Internal  | N             | 12                    | costCenter100                | testProject                 | 1210221             | Invoiced   | true          |
      | budgetIssue100_2      | budgetIssue100_2_12102022 | budgetIssue100_2_12102022 | Internal  | N             | 4                     | costCenter100                | testProject                 | 1210222             | Invoiced   | true          |
    And after not more than 10s, S_EffortControl is validated:
      | S_EffortControl_ID.Identifier | C_Activity_ID.Identifier | C_Project_ID.Identifier | OPT.InvoiceableHours | OPT.IsIssueClosed |
      | effortControl100              | costCenter100            | testProject             | 16                   | true              |

    When process invoice candidates
      | C_Invoice_Candidate_ID.Identifier |
      | candIssue100_1,candIssue100_2     |
    Then after not more than 30s, C_Invoice are found:
      | C_Invoice_ID.Identifier | C_Invoice_Candidate_ID.Identifier |
      | invoice100              | candIssue100_1                    |
      | invoice100              | candIssue100_2                    |
    And validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | AD_User_ID.Identifier | processed | docStatus | OPT.C_DocType_ID.Name | OPT.C_Activity_ID.Identifier | OPT.C_Project_ID.Identifier |
      | invoice100              | customer_SO              | customerLocation_SO               | customerUser_SO       | true      | CO        | Ausgangsrechnung      | costCenter100                | testProject                 |
    And validate invoice lines for invoice100:
      | C_InvoiceLine_ID.Identifier | M_Product_ID.Identifier | qtyinvoiced | processed | OPT.PriceEntered | OPT.PriceActual | OPT.LineNetAmt | OPT.C_Activity_ID.Identifier | OPT.C_Project_ID.Identifier | OPT.Description                    |
      | invoiceLine100_1            | invoiceableProduct      | 12          | true      | 10               | 10              | 120            | costCenter100                | testProject                 | 1210221\nbudgetIssue100_1_12102022 |
      | invoiceLine100_2            | invoiceableProduct      | 4           | true      | 10               | 10              | 40             | costCenter100                | testProject                 | 1210222\nbudgetIssue100_2_12102022 |

  @from:cucumber
  @Id:S0199_200
  Scenario: Generate invoice candidate from effort control, reopen issue and increase invoiceable hours, then regenerate invoice candidate and validate that qtyToInvoice is updated
    Given metasfresh contains C_Activity:
      | C_Activity_ID.Identifier | Name                   | Value                  |
      | costCenter200            | costCenter200_13102022 | costCenter200_13102022 |
    And metasfresh contains S_Issue:
      | S_Issue_ID.Identifier | Value          | OPT.Name       | IssueType | IsEffortIssue | OPT.InvoiceableEffort | OPT.C_Activity_ID.Identifier | OPT.C_Project_ID.Identifier | OPT.ExternalIssueNo | OPT.Status |
      | budgetIssue200        | budgetIssue200 | budgetIssue200 | Internal  | N             | 20                    | costCenter200                | testProject                 | 1310221             | New        |
    And after not more than 10s, S_EffortControl is found:
      | S_EffortControl_ID.Identifier | C_Activity_ID.Identifier | C_Project_ID.Identifier |
      | effortControl200              | costCenter200            | testProject             |
    And after not more than 10s, S_EffortControl is validated:
      | S_EffortControl_ID.Identifier | C_Activity_ID.Identifier | C_Project_ID.Identifier | OPT.InvoiceableHours | OPT.IsIssueClosed |
      | effortControl200              | costCenter200            | testProject             | 20                   | false             |

    When 'generate invoice candidate' from effort control process is invoked
      | S_EffortControl_ID.Identifier |
      | effortControl200              |
    Then after not more than 30s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | QtyToInvoice | OPT.AD_Table_ID.TableName | OPT.Record_ID.Identifier |
      | candIssue200                      | 20           | S_Issue                   | budgetIssue200           |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | QtyToInvoice | OPT.QtyOrdered | OPT.QtyDelivered | OPT.QtyInvoiced | OPT.M_Product_ID.Identifier | OPT.Bill_BPartner_ID.Identifier | OPT.Bill_Location_ID.Identifier | OPT.Bill_User_ID.Identifier | OPT.Processed | OPT.InvoiceRule | OPT.C_Activity_ID.Identifier | OPT.C_Project_ID.Identifier | OPT.Description         |
      | candIssue200                      | 20           | 20             | 0                | 0               | invoiceableProduct          | customer_SO                     | customerLocation_SO             | customerUser_SO             | false         | I               | costCenter200                | testProject                 | 1310221\nbudgetIssue200 |
    And validate S_Issue:
      | S_Issue_ID.Identifier | Value          | OPT.Name       | IssueType | IsEffortIssue | OPT.InvoiceableEffort | OPT.C_Activity_ID.Identifier | OPT.C_Project_ID.Identifier | OPT.ExternalIssueNo | OPT.Status | OPT.Processed |
      | budgetIssue200        | budgetIssue200 | budgetIssue200 | Internal  | N             | 20                    | costCenter200                | testProject                 | 1310221             | Invoiced   | true          |
    And after not more than 10s, S_EffortControl is validated:
      | S_EffortControl_ID.Identifier | C_Activity_ID.Identifier | C_Project_ID.Identifier | OPT.InvoiceableHours | OPT.IsIssueClosed |
      | effortControl200              | costCenter200            | testProject             | 20                   | true              |
    And reopen S_Issue:
      | S_Issue_ID.Identifier | OPT.Processed |
      | budgetIssue200        | false         |
    And update S_Issue:
      | S_Issue_ID.Identifier | OPT.InvoiceableEffort |
      | budgetIssue200        | 25                    |
    And after not more than 10s, S_EffortControl is validated:
      | S_EffortControl_ID.Identifier | C_Activity_ID.Identifier | C_Project_ID.Identifier | OPT.InvoiceableHours | OPT.IsIssueClosed |
      | effortControl200              | costCenter200            | testProject             | 25                   | false             |
    And validate S_Issue:
      | S_Issue_ID.Identifier | Value          | OPT.Name       | IssueType | IsEffortIssue | OPT.InvoiceableEffort | OPT.C_Activity_ID.Identifier | OPT.C_Project_ID.Identifier | OPT.ExternalIssueNo | OPT.Status | OPT.Processed |
      | budgetIssue200        | budgetIssue200 | budgetIssue200 | Internal  | N             | 25                    | costCenter200                | testProject                 | 1310221             | Invoiced   | false         |

    When 'generate invoice candidate' from effort control process is invoked
      | S_EffortControl_ID.Identifier |
      | effortControl200              |
    Then after not more than 30s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | QtyToInvoice | OPT.AD_Table_ID.TableName | OPT.Record_ID.Identifier |
      | candIssue200                      | 25           | S_Issue                   | budgetIssue200           |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | QtyToInvoice | OPT.QtyOrdered | OPT.QtyDelivered | OPT.QtyInvoiced | OPT.M_Product_ID.Identifier | OPT.Bill_BPartner_ID.Identifier | OPT.Bill_Location_ID.Identifier | OPT.Bill_User_ID.Identifier | OPT.Processed | OPT.InvoiceRule | OPT.C_Activity_ID.Identifier | OPT.C_Project_ID.Identifier | OPT.Description         |
      | candIssue200                      | 25           | 25             | 0                | 0               | invoiceableProduct          | customer_SO                     | customerLocation_SO             | customerUser_SO             | false         | I               | costCenter200                | testProject                 | 1310221\nbudgetIssue200 |
    And validate S_Issue:
      | S_Issue_ID.Identifier | Value          | OPT.Name       | IssueType | IsEffortIssue | OPT.InvoiceableEffort | OPT.C_Activity_ID.Identifier | OPT.C_Project_ID.Identifier | OPT.ExternalIssueNo | OPT.Status | OPT.Processed |
      | budgetIssue200        | budgetIssue200 | budgetIssue200 | Internal  | N             | 25                    | costCenter200                | testProject                 | 1310221             | Invoiced   | true          |

  @from:cucumber
  @Id:S0199_300
  Scenario: Generate invoice candidate from effort control, generate invoice from candidate, reopen issue and increase invoiceable hours,
  then regenerate invoice candidate and validate that qtyToInvoice is updated and a new invoice is generated from the increased value
    And metasfresh contains C_Activity:
      | C_Activity_ID.Identifier | Name                   | Value                  |
      | costCenter300            | costCenter300_13102022 | costCenter300_13102022 |
    And metasfresh contains S_Issue:
      | S_Issue_ID.Identifier | Value          | OPT.Name       | IssueType | IsEffortIssue | OPT.InvoiceableEffort | OPT.C_Activity_ID.Identifier | OPT.C_Project_ID.Identifier | OPT.ExternalIssueNo | OPT.Status |
      | budgetIssue300        | budgetIssue300 | budgetIssue300 | Internal  | N             | 30                    | costCenter300                | testProject                 | 1310222             | New        |
    And after not more than 10s, S_EffortControl is found:
      | S_EffortControl_ID.Identifier | C_Activity_ID.Identifier | C_Project_ID.Identifier |
      | effortControl300              | costCenter300            | testProject             |
    And after not more than 10s, S_EffortControl is validated:
      | S_EffortControl_ID.Identifier | C_Activity_ID.Identifier | C_Project_ID.Identifier | OPT.InvoiceableHours | OPT.IsIssueClosed |
      | effortControl300              | costCenter300            | testProject             | 30                   | false             |

    When 'generate invoice candidate' from effort control process is invoked
      | S_EffortControl_ID.Identifier |
      | effortControl300              |
    Then after not more than 30s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | QtyToInvoice | OPT.AD_Table_ID.TableName | OPT.Record_ID.Identifier |
      | candIssue300                      | 30           | S_Issue                   | budgetIssue300           |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | QtyToInvoice | OPT.QtyOrdered | OPT.QtyDelivered | OPT.QtyInvoiced | OPT.M_Product_ID.Identifier | OPT.Bill_BPartner_ID.Identifier | OPT.Bill_Location_ID.Identifier | OPT.Bill_User_ID.Identifier | OPT.Processed | OPT.InvoiceRule | OPT.C_Activity_ID.Identifier | OPT.C_Project_ID.Identifier | OPT.Description         |
      | candIssue300                      | 30           | 30             | 0                | 0               | invoiceableProduct          | customer_SO                     | customerLocation_SO             | customerUser_SO             | false         | I               | costCenter300                | testProject                 | 1310222\nbudgetIssue300 |
    And validate S_Issue:
      | S_Issue_ID.Identifier | Value          | OPT.Name       | IssueType | IsEffortIssue | OPT.InvoiceableEffort | OPT.C_Activity_ID.Identifier | OPT.C_Project_ID.Identifier | OPT.ExternalIssueNo | OPT.Status | OPT.Processed |
      | budgetIssue300        | budgetIssue300 | budgetIssue300 | Internal  | N             | 30                    | costCenter300                | testProject                 | 1310222             | Invoiced   | true          |
    And after not more than 10s, S_EffortControl is validated:
      | S_EffortControl_ID.Identifier | C_Activity_ID.Identifier | C_Project_ID.Identifier | OPT.InvoiceableHours | OPT.IsIssueClosed |
      | effortControl300              | costCenter300            | testProject             | 30                   | true              |

    When process invoice candidates
      | C_Invoice_Candidate_ID.Identifier |
      | candIssue300                      |
    Then after not more than 30s, C_Invoice are found:
      | C_Invoice_ID.Identifier | C_Invoice_Candidate_ID.Identifier |
      | invoice300_1            | candIssue300                      |
    And validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | AD_User_ID.Identifier | processed | docStatus | OPT.C_DocType_ID.Name | OPT.C_Activity_ID.Identifier | OPT.C_Project_ID.Identifier |
      | invoice300_1            | customer_SO              | customerLocation_SO               | customerUser_SO       | true      | CO        | Ausgangsrechnung      | costCenter300                | testProject                 |
    And validate invoice lines for invoice300_1:
      | C_InvoiceLine_ID.Identifier | M_Product_ID.Identifier | qtyinvoiced | processed | OPT.PriceEntered | OPT.PriceActual | OPT.LineNetAmt | OPT.C_Activity_ID.Identifier | OPT.C_Project_ID.Identifier | OPT.Description         |
      | invoiceLine300_1            | invoiceableProduct      | 30          | true      | 10               | 10              | 300            | costCenter300                | testProject                 | 1310222\nbudgetIssue300 |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | QtyToInvoice | OPT.QtyOrdered | OPT.QtyDelivered | OPT.QtyInvoiced | OPT.M_Product_ID.Identifier | OPT.Bill_BPartner_ID.Identifier | OPT.Bill_Location_ID.Identifier | OPT.Bill_User_ID.Identifier | OPT.Processed | OPT.InvoiceRule | OPT.C_Activity_ID.Identifier | OPT.C_Project_ID.Identifier |
      | candIssue300                      | 0            | 30             | 0                | 30              | invoiceableProduct          | customer_SO                     | customerLocation_SO             | customerUser_SO             | true          | I               | costCenter300                | testProject                 |

    And reopen S_Issue:
      | S_Issue_ID.Identifier | OPT.Processed |
      | budgetIssue300        | false         |
    And update S_Issue:
      | S_Issue_ID.Identifier | OPT.InvoiceableEffort |
      | budgetIssue300        | 34                    |
    And validate S_Issue:
      | S_Issue_ID.Identifier | Value          | OPT.Name       | IssueType | IsEffortIssue | OPT.InvoiceableEffort | OPT.C_Activity_ID.Identifier | OPT.C_Project_ID.Identifier | OPT.ExternalIssueNo | OPT.Status | OPT.Processed |
      | budgetIssue300        | budgetIssue300 | budgetIssue300 | Internal  | N             | 34                    | costCenter300                | testProject                 | 1310222             | Invoiced   | false         |
    And after not more than 10s, S_EffortControl is validated:
      | S_EffortControl_ID.Identifier | C_Activity_ID.Identifier | C_Project_ID.Identifier | OPT.InvoiceableHours | OPT.IsIssueClosed |
      | effortControl300              | costCenter300            | testProject             | 34                   | false             |

    When 'generate invoice candidate' from effort control process is invoked
      | S_EffortControl_ID.Identifier |
      | effortControl300              |
    Then after not more than 30s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | QtyToInvoice | OPT.AD_Table_ID.TableName | OPT.Record_ID.Identifier |
      | candIssue300                      | 4            | S_Issue                   | budgetIssue300           |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | QtyToInvoice | OPT.QtyOrdered | OPT.QtyDelivered | OPT.QtyInvoiced | OPT.M_Product_ID.Identifier | OPT.Bill_BPartner_ID.Identifier | OPT.Bill_Location_ID.Identifier | OPT.Bill_User_ID.Identifier | OPT.Processed | OPT.InvoiceRule | OPT.C_Activity_ID.Identifier | OPT.C_Project_ID.Identifier | OPT.Description         |
      | candIssue300                      | 4            | 34             | 0                | 30              | invoiceableProduct          | customer_SO                     | customerLocation_SO             | customerUser_SO             | false         | I               | costCenter300                | testProject                 | 1310222\nbudgetIssue300 |
    And validate S_Issue:
      | S_Issue_ID.Identifier | Value          | OPT.Name       | IssueType | IsEffortIssue | OPT.InvoiceableEffort | OPT.C_Activity_ID.Identifier | OPT.C_Project_ID.Identifier | OPT.ExternalIssueNo | OPT.Status | OPT.Processed |
      | budgetIssue300        | budgetIssue300 | budgetIssue300 | Internal  | N             | 34                    | costCenter300                | testProject                 | 1310222             | Invoiced   | true          |
    And after not more than 10s, S_EffortControl is validated:
      | S_EffortControl_ID.Identifier | C_Activity_ID.Identifier | C_Project_ID.Identifier | OPT.InvoiceableHours | OPT.IsIssueClosed |
      | effortControl300              | costCenter300            | testProject             | 34                   | true              |

    When process invoice candidates
      | C_Invoice_Candidate_ID.Identifier |
      | candIssue300                      |
    Then after not more than 30s, C_Invoice are found:
      | C_Invoice_ID.Identifier   | C_Invoice_Candidate_ID.Identifier |
      | invoice300_1,invoice300_2 | candIssue300                      |
    And validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | AD_User_ID.Identifier | processed | docStatus | OPT.C_DocType_ID.Name | OPT.C_Activity_ID.Identifier | OPT.C_Project_ID.Identifier |
      | invoice300_2            | customer_SO              | customerLocation_SO               | customerUser_SO       | true      | CO        | Ausgangsrechnung      | costCenter300                | testProject                 |
    And validate invoice lines for invoice300_2:
      | C_InvoiceLine_ID.Identifier | M_Product_ID.Identifier | qtyinvoiced | processed | OPT.PriceEntered | OPT.PriceActual | OPT.LineNetAmt | OPT.C_Activity_ID.Identifier | OPT.C_Project_ID.Identifier | OPT.Description         |
      | invoiceLine300_2            | invoiceableProduct      | 4           | true      | 10               | 10              | 40             | costCenter300                | testProject                 | 1310222\nbudgetIssue300 |

  @from:cucumber
  @Id:S0199_400
  Scenario: Generate invoice candidate from effort control, generate invoice from candidate, reopen issue and decrease invoiceable hours,
  then regenerate invoice candidate and validate that qtyToInvoice is updated but a new invoice cannot be generated from candidate
    And metasfresh contains C_Activity:
      | C_Activity_ID.Identifier | Name                   | Value                  |
      | costCenter400            | costCenter400_13102022 | costCenter400_13102022 |
    And metasfresh contains S_Issue:
      | S_Issue_ID.Identifier | Value          | OPT.Name       | IssueType | IsEffortIssue | OPT.InvoiceableEffort | OPT.C_Activity_ID.Identifier | OPT.C_Project_ID.Identifier | OPT.ExternalIssueNo | OPT.Status |
      | budgetIssue400        | budgetIssue400 | budgetIssue400 | Internal  | N             | 40                    | costCenter400                | testProject                 | 1310223             | New        |
    And after not more than 10s, S_EffortControl is found:
      | S_EffortControl_ID.Identifier | C_Activity_ID.Identifier | C_Project_ID.Identifier |
      | effortControl400              | costCenter400            | testProject             |
    And after not more than 10s, S_EffortControl is validated:
      | S_EffortControl_ID.Identifier | C_Activity_ID.Identifier | C_Project_ID.Identifier | OPT.InvoiceableHours | OPT.IsIssueClosed |
      | effortControl400              | costCenter400            | testProject             | 40                   | false             |

    When 'generate invoice candidate' from effort control process is invoked
      | S_EffortControl_ID.Identifier |
      | effortControl400              |
    Then after not more than 30s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | QtyToInvoice | OPT.AD_Table_ID.TableName | OPT.Record_ID.Identifier |
      | candIssue400                      | 40           | S_Issue                   | budgetIssue400           |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | QtyToInvoice | OPT.QtyOrdered | OPT.QtyDelivered | OPT.QtyInvoiced | OPT.M_Product_ID.Identifier | OPT.Bill_BPartner_ID.Identifier | OPT.Bill_Location_ID.Identifier | OPT.Bill_User_ID.Identifier | OPT.Processed | OPT.InvoiceRule | OPT.C_Activity_ID.Identifier | OPT.C_Project_ID.Identifier | OPT.Description         |
      | candIssue400                      | 40           | 40             | 0                | 0               | invoiceableProduct          | customer_SO                     | customerLocation_SO             | customerUser_SO             | false         | I               | costCenter400                | testProject                 | 1310223\nbudgetIssue400 |
    And validate S_Issue:
      | S_Issue_ID.Identifier | Value          | OPT.Name       | IssueType | IsEffortIssue | OPT.InvoiceableEffort | OPT.C_Activity_ID.Identifier | OPT.C_Project_ID.Identifier | OPT.ExternalIssueNo | OPT.Status | OPT.Processed |
      | budgetIssue400        | budgetIssue400 | budgetIssue400 | Internal  | N             | 40                    | costCenter400                | testProject                 | 1310223             | Invoiced   | true          |
    And after not more than 10s, S_EffortControl is validated:
      | S_EffortControl_ID.Identifier | C_Activity_ID.Identifier | C_Project_ID.Identifier | OPT.InvoiceableHours | OPT.IsIssueClosed |
      | effortControl400              | costCenter400            | testProject             | 40                   | true              |

    When process invoice candidates
      | C_Invoice_Candidate_ID.Identifier |
      | candIssue400                      |
    Then after not more than 30s, C_Invoice are found:
      | C_Invoice_ID.Identifier | C_Invoice_Candidate_ID.Identifier |
      | invoice400              | candIssue400                      |
    And validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | AD_User_ID.Identifier | processed | docStatus | OPT.C_DocType_ID.Name | OPT.C_Activity_ID.Identifier | OPT.C_Project_ID.Identifier |
      | invoice400              | customer_SO              | customerLocation_SO               | customerUser_SO       | true      | CO        | Ausgangsrechnung      | costCenter400                | testProject                 |
    And validate invoice lines for invoice400:
      | C_InvoiceLine_ID.Identifier | M_Product_ID.Identifier | qtyinvoiced | processed | OPT.PriceEntered | OPT.PriceActual | OPT.LineNetAmt | OPT.C_Activity_ID.Identifier | OPT.C_Project_ID.Identifier | OPT.Description         |
      | invoiceLine400              | invoiceableProduct      | 40          | true      | 10               | 10              | 400            | costCenter400                | testProject                 | 1310223\nbudgetIssue400 |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | QtyToInvoice | OPT.QtyOrdered | OPT.QtyDelivered | OPT.QtyInvoiced | OPT.M_Product_ID.Identifier | OPT.Bill_BPartner_ID.Identifier | OPT.Bill_Location_ID.Identifier | OPT.Bill_User_ID.Identifier | OPT.Processed | OPT.InvoiceRule | OPT.C_Activity_ID.Identifier | OPT.C_Project_ID.Identifier |
      | candIssue400                      | 0            | 40             | 0                | 40              | invoiceableProduct          | customer_SO                     | customerLocation_SO             | customerUser_SO             | true          | I               | costCenter400                | testProject                 |

    And reopen S_Issue:
      | S_Issue_ID.Identifier | OPT.Processed |
      | budgetIssue400        | false         |
    And update S_Issue:
      | S_Issue_ID.Identifier | OPT.InvoiceableEffort |
      | budgetIssue400        | 22                    |
    And validate S_Issue:
      | S_Issue_ID.Identifier | Value          | OPT.Name       | IssueType | IsEffortIssue | OPT.InvoiceableEffort | OPT.C_Activity_ID.Identifier | OPT.C_Project_ID.Identifier | OPT.ExternalIssueNo | OPT.Status | OPT.Processed |
      | budgetIssue400        | budgetIssue400 | budgetIssue400 | Internal  | N             | 22                    | costCenter400                | testProject                 | 1310223             | Invoiced   | false         |
    And after not more than 10s, S_EffortControl is validated:
      | S_EffortControl_ID.Identifier | C_Activity_ID.Identifier | C_Project_ID.Identifier | OPT.InvoiceableHours | OPT.IsIssueClosed |
      | effortControl400              | costCenter400            | testProject             | 22                   | false             |

    When 'generate invoice candidate' from effort control process is invoked
      | S_EffortControl_ID.Identifier |
      | effortControl400              |
    Then after not more than 30s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | QtyToInvoice | OPT.AD_Table_ID.TableName | OPT.Record_ID.Identifier |
      | candIssue400                      | 0            | S_Issue                   | budgetIssue400           |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | QtyToInvoice | OPT.QtyOrdered | OPT.QtyDelivered | OPT.QtyInvoiced | OPT.M_Product_ID.Identifier | OPT.Bill_BPartner_ID.Identifier | OPT.Bill_Location_ID.Identifier | OPT.Bill_User_ID.Identifier | OPT.Processed | OPT.InvoiceRule | OPT.C_Activity_ID.Identifier | OPT.C_Project_ID.Identifier | OPT.Description         |
      | candIssue400                      | 0            | 22             | 0                | 40              | invoiceableProduct          | customer_SO                     | customerLocation_SO             | customerUser_SO             | true          | I               | costCenter400                | testProject                 | 1310223\nbudgetIssue400 |
    And validate S_Issue:
      | S_Issue_ID.Identifier | Value          | OPT.Name       | IssueType | IsEffortIssue | OPT.InvoiceableEffort | OPT.C_Activity_ID.Identifier | OPT.C_Project_ID.Identifier | OPT.ExternalIssueNo | OPT.Status | OPT.Processed |
      | budgetIssue400        | budgetIssue400 | budgetIssue400 | Internal  | N             | 22                    | costCenter400                | testProject                 | 1310223             | Invoiced   | true          |
    And after not more than 10s, S_EffortControl is validated:
      | S_EffortControl_ID.Identifier | C_Activity_ID.Identifier | C_Project_ID.Identifier | OPT.InvoiceableHours | OPT.IsIssueClosed |
      | effortControl400              | costCenter400            | testProject             | 22                   | true              |
    And invoice candidates are not billable
      | C_Invoice_Candidate_ID.Identifier |
      | candIssue400                      |

  @from:cucumber
  @Id:S0227_600
  Scenario: Validate that invoice candidate is not created from budget issue when there is no product configured on project
    Given create or update C_Project:
      | C_Project_ID.Identifier | Name                 | Value                | C_Currency_ID.ISO_Code | OPT.M_Product_ID.Identifier |
      | testProject             | testProject_12102022 | testProject_12102022 | EUR                    | null                        |
    And metasfresh contains C_Activity:
      | C_Activity_ID.Identifier | Name                   | Value                  |
      | costCenter500            | costCenter500_13102022 | costCenter500_13102022 |
    And metasfresh contains S_Issue:
      | S_Issue_ID.Identifier | Value                 | OPT.Name              | IssueType | IsEffortIssue | OPT.InvoiceableEffort | OPT.C_Activity_ID.Identifier | OPT.C_Project_ID.Identifier | OPT.ExternalIssueNo | OPT.Status |
      | budgetIssue500        | budgetIssue500_241022 | budgetIssue500_241022 | Internal  | N             | 50                    | costCenter500                | testProject                 | 241022              | New        |
    And after not more than 10s, S_EffortControl is found:
      | S_EffortControl_ID.Identifier | C_Activity_ID.Identifier | C_Project_ID.Identifier |
      | effortControl500              | costCenter500            | testProject             |
    And after not more than 10s, S_EffortControl is validated:
      | S_EffortControl_ID.Identifier | C_Activity_ID.Identifier | C_Project_ID.Identifier | OPT.InvoiceableHours | OPT.IsIssueClosed |
      | effortControl500              | costCenter500            | testProject             | 50                   | false             |
    When 'generate invoice candidate' from effort control process is invoked
      | S_EffortControl_ID.Identifier |
      | effortControl500              |
    And there is no C_Invoice_Candidate for:
      | TableName | Record_ID.Identifier |
      | S_Issue   | budgetIssue500       |
    And validate S_Issue:
      | S_Issue_ID.Identifier | Value                 | OPT.Name              | IssueType | IsEffortIssue | OPT.InvoiceableEffort | OPT.C_Activity_ID.Identifier | OPT.C_Project_ID.Identifier | OPT.ExternalIssueNo | OPT.Status | OPT.Processed | OPT.InvoicingErrorMsg                      | OPT.IsInvoicingError |
      | budgetIssue500        | budgetIssue500_241022 | budgetIssue500_241022 | Internal  | N             | 50                    | costCenter500                | testProject                 | 241022              | New        | false         | Missing invoiceable ProductId on C_Project | true                 |
