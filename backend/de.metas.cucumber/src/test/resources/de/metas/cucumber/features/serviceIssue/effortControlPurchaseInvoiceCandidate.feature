@from:cucumber
Feature: Create vendor invoice candidates from effort control

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has current date and time
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION

    And metasfresh contains M_Products:
      | Identifier         | Value                       | Name                        |
      | invoiceableProduct | invoiceableProduct_24102022 | invoiceableProduct_24102022 |
    And metasfresh contains M_PricingSystems
      | Identifier  | Name        | Value       |
      | ps_24102022 | ps_24102022 | ps_24102022 |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name           | SOTrx | IsTaxIncluded | PricePrecision |
      | pl_SO      | ps_24102022                   | DE                        | EUR                 | pl_SO_24102022 | true  | false         | 2              |
      | pl_PO      | ps_24102022                   | DE                        | EUR                 | pl_PO_24102022 | false | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name   | ValidFrom  |
      | plv_SO     | pl_SO                     | plv_SO | 2022-09-01 |
      | plv_PO     | pl_PO                     | plv_PO | 2022-09-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_SO      | plv_SO                            | invoiceableProduct      | 10.0     | PCE               | Normal                        |
      | pp_PO      | plv_PO                            | invoiceableProduct      | 5.0      | PCE               | Normal                        |

    And metasfresh contains C_BPartners without locations:
      | Identifier  | Name                 | Value                | OPT.IsCustomer | OPT.IsVendor | M_PricingSystem_ID.Identifier |
      | customer_SO | customer_SO_24102022 | customer_SO_24102022 | Y              | N            | ps_24102022                   |
      | vendor_PO   | vendor_PO_24102022   | vendor_PO_24102022   | N              | Y            | ps_24102022                   |
    And metasfresh contains C_BPartner_Locations:
      | Identifier          | GLN           | C_BPartner_ID.Identifier | OPT.IsShipToDefault | OPT.IsBillToDefault |
      | customerLocation_SO | 2410202222221 | customer_SO              | Y                   | Y                   |
      | vendorLocation_PO   | 2410202222222 | vendor_PO                | Y                   | Y                   |
    And metasfresh contains AD_Users:
      | AD_User_ID.Identifier | Name                  | OPT.EMail                       | OPT.C_BPartner_ID.Identifier | OPT.IsBillToContact_Default |
      | customerUser          | customerUser_24102022 | customerUser_24102022@email.com | customer_SO                  | true                        |
      | vendorUser            | vendorUser_24102022   | vendorUser_24102022@email.com   | vendor_PO                    | true                        |

    And create or update C_Project:
      | C_Project_ID.Identifier | Name                 | Value                | C_Currency_ID.ISO_Code | OPT.C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | OPT.AD_User_ID.Identifier | OPT.M_Product_ID.Identifier |
      | testProject             | testProject_24102022 | testProject_24102022 | EUR                    | customer_SO                  | customerLocation_SO                   | customerUser              | invoiceableProduct          |

  @from:cucumber
  @Id:S0227_100
  Scenario: Generate invoice candidate from effort control with one budget issue and two effort issues and invoice the invoice candidates
    Given metasfresh contains C_Activity:
      | C_Activity_ID.Identifier | Name                   | Value                  |
      | costCenter100            | costCenter100_24102022 | costCenter100_24102022 |
    And metasfresh contains S_Issue:
      | S_Issue_ID.Identifier | Value                   | OPT.Name                | OPT.S_Parent_Issue_ID.Identifier | IssueType | IsEffortIssue | OPT.InvoiceableEffort | OPT.IssueEffort | OPT.C_Activity_ID.Identifier | OPT.C_Project_ID.Identifier | OPT.ExternalIssueNo | OPT.Status | OPT.AD_User_ID.Identifier |
      | budgetIssue100        | budgetIssue100_24102022 | budgetIssue100_24102022 |                                  | Internal  | N             | 16                    |                 | costCenter100                | testProject                 | 2410220             | New        |                           |
      | effortIssue100        | effortIssue100_24102022 | effortIssue100_24102022 | budgetIssue100                   | Internal  | Y             | 0                     | 8:00            | costCenter100                | testProject                 | 2410221             | New        | vendorUser                |
      | effortIssue101        | effortIssue101_24102022 | effortIssue101_24102022 | effortIssue100                   | Internal  | Y             | 0                     | 2:00            | costCenter100                | testProject                 | 2410222             | New        | vendorUser                |
    And after not more than 10s, S_EffortControl is found:
      | S_EffortControl_ID.Identifier | C_Activity_ID.Identifier | C_Project_ID.Identifier |
      | effortControl100              | costCenter100            | testProject             |
    And after not more than 10s, S_EffortControl is validated:
      | S_EffortControl_ID.Identifier | C_Activity_ID.Identifier | C_Project_ID.Identifier | OPT.PendingEffortSum | OPT.EffortSum | OPT.InvoiceableHours | OPT.IsIssueClosed |
      | effortControl100              | costCenter100            | testProject             | 10:00                | 10:00         | 16                   | false             |

    When 'generate invoice candidate' from effort control process is invoked
      | S_EffortControl_ID.Identifier |
      | effortControl100              |
    Then after not more than 30s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | QtyToInvoice | OPT.AD_Table_ID.TableName | OPT.Record_ID.Identifier |
      | customerIC100                     | 16           | S_Issue                   | budgetIssue100           |
      | vendorIC100                       | 8            | S_Issue                   | effortIssue100           |
      | vendorIC101                       | 2            | S_Issue                   | effortIssue101           |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | QtyToInvoice | OPT.QtyOrdered | OPT.QtyDelivered | OPT.QtyInvoiced | OPT.M_Product_ID.Identifier | OPT.Bill_BPartner_ID.Identifier | OPT.Bill_Location_ID.Identifier | OPT.Bill_User_ID.Identifier | OPT.Processed | OPT.InvoiceRule | OPT.C_Activity_ID.Identifier | OPT.C_Project_ID.Identifier | OPT.Description                  | OPT.IsSOTrx | OPT.C_DocTypeInvoice_ID.Name |
      | customerIC100                     | 16           | 16             | 0                | 0               | invoiceableProduct          | customer_SO                     | customerLocation_SO             | customerUser                | false         | I               | costCenter100                | testProject                 | 2410220\nbudgetIssue100_24102022 | Y           | Ausgangsrechnung             |
      | vendorIC100                       | 8            | 8              | 0                | 0               | invoiceableProduct          | vendor_PO                       | vendorLocation_PO               | vendorUser                  | false         | I               | costCenter100                | testProject                 | 2410221\neffortIssue100_24102022 | N           | Internal Vendor Invoice      |
      | vendorIC101                       | 2            | 2              | 0                | 0               | invoiceableProduct          | vendor_PO                       | vendorLocation_PO               | vendorUser                  | false         | I               | costCenter100                | testProject                 | 2410222\neffortIssue101_24102022 | N           | Internal Vendor Invoice      |
    And validate S_Issue:
      | S_Issue_ID.Identifier | Value                   | OPT.Name                | OPT.S_Parent_Issue_ID.Identifier | IssueType | IsEffortIssue | OPT.InvoiceableEffort | OPT.IssueEffort | OPT.C_Activity_ID.Identifier | OPT.C_Project_ID.Identifier | OPT.ExternalIssueNo | OPT.Status | OPT.Processed | OPT.AD_User_ID.Identifier |
      | budgetIssue100        | budgetIssue100_24102022 | budgetIssue100_24102022 |                                  | Internal  | N             | 16                    |                 | costCenter100                | testProject                 | 2410220             | Invoiced   | true          |                           |
      | effortIssue100        | effortIssue100_24102022 | effortIssue100_24102022 | budgetIssue100                   | Internal  | Y             | 0                     | 8:00            | costCenter100                | testProject                 | 2410221             | Invoiced   | true          | vendorUser                |
      | effortIssue101        | effortIssue101_24102022 | effortIssue101_24102022 | effortIssue100                   | Internal  | Y             | 0                     | 2:00            | costCenter100                | testProject                 | 2410222             | Invoiced   | true          | vendorUser                |
    And after not more than 10s, S_EffortControl is validated:
      | S_EffortControl_ID.Identifier | C_Activity_ID.Identifier | C_Project_ID.Identifier | OPT.PendingEffortSum | OPT.EffortSum | OPT.InvoiceableHours | OPT.IsIssueClosed |
      | effortControl100              | costCenter100            | testProject             | 0:00                 | 10:00         | 16                   | true              |

    When process invoice candidates
      | C_Invoice_Candidate_ID.Identifier |
      | customerIC100                     |
    Then after not more than 30s, C_Invoice are found:
      | C_Invoice_ID.Identifier | C_Invoice_Candidate_ID.Identifier |
      | customerInvoice100      | customerIC100                     |
    And validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | AD_User_ID.Identifier | processed | docStatus | OPT.C_DocType_ID.Name | OPT.C_Activity_ID.Identifier | OPT.C_Project_ID.Identifier |
      | customerInvoice100      | customer_SO              | customerLocation_SO               | customerUser          | true      | CO        | Ausgangsrechnung      | costCenter100                | testProject                 |
    And validate invoice lines for customerInvoice100:
      | C_InvoiceLine_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | Processed | OPT.PriceEntered | OPT.PriceActual | OPT.LineNetAmt | OPT.C_Activity_ID.Identifier | OPT.C_Project_ID.Identifier | OPT.Description                  |
      | customerInvoiceLine100      | invoiceableProduct      | 16          | true      | 10               | 10              | 160            | costCenter100                | testProject                 | 2410220\nbudgetIssue100_24102022 |
    And after not more than 30s, Fact_Acct are found
      | TableName | Record_ID.Identifier | OPT.PostingType | OPT.C_Activity_ID.Identifier | OPT.C_Project_ID.Identifier |
      | C_Invoice | customerInvoice100   | Actual          | costCenter100                | testProject                 |

    When process invoice candidates
      | C_Invoice_Candidate_ID.Identifier |
      | vendorIC100,vendorIC101           |
    Then after not more than 30s, C_Invoice are found:
      | C_Invoice_ID.Identifier | C_Invoice_Candidate_ID.Identifier |
      | vendorInvoice100        | vendorIC100                       |
    And validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | AD_User_ID.Identifier | processed | docStatus | OPT.C_DocType_ID.Name   | OPT.C_Activity_ID.Identifier | OPT.C_Project_ID.Identifier |
      | vendorInvoice100        | vendor_PO                | vendorLocation_PO                 | vendorUser            | true      | CO        | Internal Vendor Invoice | costCenter100                | testProject                 |
    And validate invoice lines for vendorInvoice100:
      | C_InvoiceLine_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | Processed | OPT.PriceEntered | OPT.PriceActual | OPT.LineNetAmt | OPT.C_Activity_ID.Identifier | OPT.C_Project_ID.Identifier | OPT.Description                  |
      | vendorInvoiceLine100        | invoiceableProduct      | 8           | true      | 5                | 5               | 40             | costCenter100                | testProject                 | 2410221\neffortIssue100_24102022 |
      | vendorInvoiceLine101        | invoiceableProduct      | 2           | true      | 5                | 5               | 10             | costCenter100                | testProject                 | 2410222\neffortIssue101_24102022 |
    And after not more than 30s, Fact_Acct are found
      | TableName | Record_ID.Identifier | OPT.PostingType | OPT.C_Activity_ID.Identifier | OPT.C_Project_ID.Identifier |
      | C_Invoice | vendorInvoice100     | Statistical     | costCenter100                | testProject                 |

  @from:cucumber
  @Id:S0227_200
  Scenario: Generate invoice candidate from effort control, increase `IssueEffort` on effort issue and validate date `QtyToInvoice` on vendor invoice candidate is updated
    Given metasfresh contains C_Activity:
      | C_Activity_ID.Identifier | Name                   | Value                  |
      | costCenter200            | costCenter200_24102022 | costCenter200_24102022 |
    And metasfresh contains S_Issue:
      | S_Issue_ID.Identifier | Value                   | OPT.Name                | OPT.S_Parent_Issue_ID.Identifier | IssueType | IsEffortIssue | OPT.InvoiceableEffort | OPT.C_Activity_ID.Identifier | OPT.C_Project_ID.Identifier | OPT.ExternalIssueNo | OPT.Status | OPT.AD_User_ID.Identifier |
      | budgetIssue200        | budgetIssue200_24102022 | budgetIssue200_24102022 |                                  | Internal  | N             | 20                    | costCenter200                | testProject                 | 2510220             | New        |                           |
      | effortIssue200        | effortIssue200_24102022 | effortIssue200_24102022 | budgetIssue200                   | Internal  | Y             | 0                     | costCenter200                | testProject                 | 2510221             | New        | vendorUser                |
    And metasfresh contains S_TimeBooking:
      | S_TimeBooking_ID.Identifier | S_Issue_ID.Identifier | HoursAndMinutes | AD_User_Performing_ID.Identifier | BookedDate |
      | effortTimeBooking200        | effortIssue200        | 12:00           | vendorUser                       | 2022-10-15 |
    And after not more than 10s, S_EffortControl is found:
      | S_EffortControl_ID.Identifier | C_Activity_ID.Identifier | C_Project_ID.Identifier |
      | effortControl200              | costCenter200            | testProject             |
    And after not more than 10s, S_EffortControl is validated:
      | S_EffortControl_ID.Identifier | C_Activity_ID.Identifier | C_Project_ID.Identifier | OPT.PendingEffortSum | OPT.EffortSum | OPT.InvoiceableHours | OPT.IsIssueClosed |
      | effortControl200              | costCenter200            | testProject             | 12:00                | 12:00         | 20                   | false             |

    When 'generate invoice candidate' from effort control process is invoked
      | S_EffortControl_ID.Identifier |
      | effortControl200              |
    Then after not more than 30s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | QtyToInvoice | OPT.AD_Table_ID.TableName | OPT.Record_ID.Identifier |
      | customerIC200                     | 20           | S_Issue                   | budgetIssue200           |
      | vendorIC200                       | 12           | S_Issue                   | effortIssue200           |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | QtyToInvoice | OPT.QtyOrdered | OPT.QtyDelivered | OPT.QtyInvoiced | OPT.M_Product_ID.Identifier | OPT.Bill_BPartner_ID.Identifier | OPT.Bill_Location_ID.Identifier | OPT.Bill_User_ID.Identifier | OPT.Processed | OPT.InvoiceRule | OPT.C_Activity_ID.Identifier | OPT.C_Project_ID.Identifier | OPT.Description                  | OPT.IsSOTrx | OPT.C_DocTypeInvoice_ID.Name |
      | customerIC200                     | 20           | 20             | 0                | 0               | invoiceableProduct          | customer_SO                     | customerLocation_SO             | customerUser                | false         | I               | costCenter200                | testProject                 | 2510220\nbudgetIssue200_24102022 | Y           | Ausgangsrechnung             |
      | vendorIC200                       | 12           | 12             | 0                | 0               | invoiceableProduct          | vendor_PO                       | vendorLocation_PO               | vendorUser                  | false         | I               | costCenter200                | testProject                 | 2510221\neffortIssue200_24102022 | N           | Internal Vendor Invoice      |
    And validate S_Issue:
      | S_Issue_ID.Identifier | Value                   | OPT.Name                | OPT.S_Parent_Issue_ID.Identifier | IssueType | IsEffortIssue | OPT.InvoiceableEffort | OPT.IssueEffort | OPT.C_Activity_ID.Identifier | OPT.C_Project_ID.Identifier | OPT.ExternalIssueNo | OPT.Status | OPT.Processed | OPT.AD_User_ID.Identifier |
      | budgetIssue200        | budgetIssue200_24102022 | budgetIssue200_24102022 |                                  | Internal  | N             | 20                    |                 | costCenter200                | testProject                 | 2510220             | Invoiced   | true          |                           |
      | effortIssue200        | effortIssue200_24102022 | effortIssue200_24102022 | budgetIssue200                   | Internal  | Y             | 0                     | 12:00           | costCenter200                | testProject                 | 2510221             | Invoiced   | true          | vendorUser                |
    And after not more than 10s, S_EffortControl is validated:
      | S_EffortControl_ID.Identifier | C_Activity_ID.Identifier | C_Project_ID.Identifier | OPT.PendingEffortSum | OPT.EffortSum | OPT.InvoiceableHours | OPT.IsIssueClosed |
      | effortControl200              | costCenter200            | testProject             | 0:00                 | 12:00         | 20                   | true              |

    #  increase `IssueEffort` on effort issue
    When metasfresh contains S_TimeBooking:
      | S_TimeBooking_ID.Identifier | S_Issue_ID.Identifier | HoursAndMinutes | AD_User_Performing_ID.Identifier | BookedDate |
      | effortTimeBooking201        | effortIssue200        | 5:00            | vendorUser                       | 2022-10-16 |
    Then after not more than 10s, S_EffortControl is validated:
      | S_EffortControl_ID.Identifier | C_Activity_ID.Identifier | C_Project_ID.Identifier | OPT.PendingEffortSum | OPT.EffortSum | OPT.InvoiceableHours | OPT.IsIssueClosed |
      | effortControl200              | costCenter200            | testProject             | 0:00                 | 17:00         | 20                   | true              |
    And validate S_Issue:
      | S_Issue_ID.Identifier | Value                   | OPT.Name                | OPT.S_Parent_Issue_ID.Identifier | IssueType | IsEffortIssue | OPT.InvoiceableEffort | OPT.IssueEffort | OPT.C_Activity_ID.Identifier | OPT.C_Project_ID.Identifier | OPT.ExternalIssueNo | OPT.Status | OPT.Processed | OPT.AD_User_ID.Identifier |
      | budgetIssue200        | budgetIssue200_24102022 | budgetIssue200_24102022 |                                  | Internal  | N             | 20                    |                 | costCenter200                | testProject                 | 2510220             | Invoiced   | true          |                           |
      | effortIssue200        | effortIssue200_24102022 | effortIssue200_24102022 | budgetIssue200                   | Internal  | Y             | 0                     | 17:00           | costCenter200                | testProject                 | 2510221             | Invoiced   | true          | vendorUser                |
    And after not more than 30s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | QtyToInvoice | OPT.AD_Table_ID.TableName | OPT.Record_ID.Identifier |
      | customerIC200                     | 20           | S_Issue                   | budgetIssue200           |
      | vendorIC200                       | 17           | S_Issue                   | effortIssue200           |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | QtyToInvoice | OPT.QtyOrdered | OPT.QtyDelivered | OPT.QtyInvoiced | OPT.M_Product_ID.Identifier | OPT.Bill_BPartner_ID.Identifier | OPT.Bill_Location_ID.Identifier | OPT.Bill_User_ID.Identifier | OPT.Processed | OPT.InvoiceRule | OPT.C_Activity_ID.Identifier | OPT.C_Project_ID.Identifier | OPT.Description                  | OPT.IsSOTrx | OPT.C_DocTypeInvoice_ID.Name |
      | customerIC200                     | 20           | 20             | 0                | 0               | invoiceableProduct          | customer_SO                     | customerLocation_SO             | customerUser                | false         | I               | costCenter200                | testProject                 | 2510220\nbudgetIssue200_24102022 | Y           | Ausgangsrechnung             |
      | vendorIC200                       | 17           | 17             | 0                | 0               | invoiceableProduct          | vendor_PO                       | vendorLocation_PO               | vendorUser                  | false         | I               | costCenter200                | testProject                 | 2510221\neffortIssue200_24102022 | N           | Internal Vendor Invoice      |

  @from:cucumber
  @Id:S0227_300
  Scenario: Generate invoice candidate from effort control and invoice it, increase `IssueEffort` on effort issue and invoice again. validate that a new invoice is generated for the increased qty
    Given metasfresh contains C_Activity:
      | C_Activity_ID.Identifier | Name                   | Value                  |
      | costCenter300            | costCenter300_24102022 | costCenter300_24102022 |
    And metasfresh contains S_Issue:
      | S_Issue_ID.Identifier | Value                   | OPT.Name                | OPT.S_Parent_Issue_ID.Identifier | IssueType | IsEffortIssue | OPT.InvoiceableEffort | OPT.C_Activity_ID.Identifier | OPT.C_Project_ID.Identifier | OPT.ExternalIssueNo | OPT.Status | OPT.AD_User_ID.Identifier |
      | budgetIssue300        | budgetIssue300_24102022 | budgetIssue300_24102022 |                                  | Internal  | N             | 33                    | costCenter300                | testProject                 | 2510220             | New        |                           |
      | effortIssue300        | effortIssue300_24102022 | effortIssue300_24102022 | budgetIssue300                   | Internal  | Y             | 0                     | costCenter300                | testProject                 | 2510221             | New        | vendorUser                |
    And metasfresh contains S_TimeBooking:
      | S_TimeBooking_ID.Identifier | S_Issue_ID.Identifier | HoursAndMinutes | AD_User_Performing_ID.Identifier | BookedDate |
      | effortTimeBooking300        | effortIssue300        | 19:00           | vendorUser                       | 2022-10-17 |
    And after not more than 10s, S_EffortControl is found:
      | S_EffortControl_ID.Identifier | C_Activity_ID.Identifier | C_Project_ID.Identifier |
      | effortControl300              | costCenter300            | testProject             |
    And after not more than 10s, S_EffortControl is validated:
      | S_EffortControl_ID.Identifier | C_Activity_ID.Identifier | C_Project_ID.Identifier | OPT.PendingEffortSum | OPT.EffortSum | OPT.InvoiceableHours | OPT.IsIssueClosed |
      | effortControl300              | costCenter300            | testProject             | 19:00                | 19:00         | 33                   | false             |

    When 'generate invoice candidate' from effort control process is invoked
      | S_EffortControl_ID.Identifier |
      | effortControl300              |
    Then after not more than 30s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | QtyToInvoice | OPT.AD_Table_ID.TableName | OPT.Record_ID.Identifier |
      | customerIC300                     | 33           | S_Issue                   | budgetIssue300           |
      | vendorIC300                       | 19           | S_Issue                   | effortIssue300           |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | QtyToInvoice | OPT.QtyOrdered | OPT.QtyDelivered | OPT.QtyInvoiced | OPT.M_Product_ID.Identifier | OPT.Bill_BPartner_ID.Identifier | OPT.Bill_Location_ID.Identifier | OPT.Bill_User_ID.Identifier | OPT.Processed | OPT.InvoiceRule | OPT.C_Activity_ID.Identifier | OPT.C_Project_ID.Identifier | OPT.Description                  | OPT.IsSOTrx | OPT.C_DocTypeInvoice_ID.Name |
      | customerIC300                     | 33           | 33             | 0                | 0               | invoiceableProduct          | customer_SO                     | customerLocation_SO             | customerUser                | false         | I               | costCenter300                | testProject                 | 2510220\nbudgetIssue300_24102022 | Y           | Ausgangsrechnung             |
      | vendorIC300                       | 19           | 19             | 0                | 0               | invoiceableProduct          | vendor_PO                       | vendorLocation_PO               | vendorUser                  | false         | I               | costCenter300                | testProject                 | 2510221\neffortIssue300_24102022 | N           | Internal Vendor Invoice      |
    And validate S_Issue:
      | S_Issue_ID.Identifier | Value                   | OPT.Name                | OPT.S_Parent_Issue_ID.Identifier | IssueType | IsEffortIssue | OPT.InvoiceableEffort | OPT.IssueEffort | OPT.C_Activity_ID.Identifier | OPT.C_Project_ID.Identifier | OPT.ExternalIssueNo | OPT.Status | OPT.Processed | OPT.AD_User_ID.Identifier |
      | budgetIssue300        | budgetIssue300_24102022 | budgetIssue300_24102022 |                                  | Internal  | N             | 33                    |                 | costCenter300                | testProject                 | 2510220             | Invoiced   | true          |                           |
      | effortIssue300        | effortIssue300_24102022 | effortIssue300_24102022 | budgetIssue300                   | Internal  | Y             | 0                     | 19:00           | costCenter300                | testProject                 | 2510221             | Invoiced   | true          | vendorUser                |
    And after not more than 10s, S_EffortControl is validated:
      | S_EffortControl_ID.Identifier | C_Activity_ID.Identifier | C_Project_ID.Identifier | OPT.PendingEffortSum | OPT.EffortSum | OPT.InvoiceableHours | OPT.IsIssueClosed |
      | effortControl300              | costCenter300            | testProject             | 0:00                 | 19:00         | 33                   | true              |

    When process invoice candidates
      | C_Invoice_Candidate_ID.Identifier |
      | vendorIC300                       |
    Then after not more than 30s, C_Invoice are found:
      | C_Invoice_ID.Identifier | C_Invoice_Candidate_ID.Identifier |
      | vendorInvoice300        | vendorIC300                       |
    And validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | AD_User_ID.Identifier | processed | docStatus | OPT.C_DocType_ID.Name   | OPT.C_Activity_ID.Identifier | OPT.C_Project_ID.Identifier |
      | vendorInvoice300        | vendor_PO                | vendorLocation_PO                 | vendorUser            | true      | CO        | Internal Vendor Invoice | costCenter300                | testProject                 |
    And validate invoice lines for vendorInvoice300:
      | C_InvoiceLine_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | Processed | OPT.PriceEntered | OPT.PriceActual | OPT.LineNetAmt | OPT.C_Activity_ID.Identifier | OPT.C_Project_ID.Identifier | OPT.Description                  |
      | vendorInvoiceLine300        | invoiceableProduct      | 19          | true      | 5                | 5               | 95             | costCenter300                | testProject                 | 2510221\neffortIssue300_24102022 |
    And after not more than 30s, Fact_Acct are found
      | TableName | Record_ID.Identifier | OPT.PostingType | OPT.C_Activity_ID.Identifier | OPT.C_Project_ID.Identifier |
      | C_Invoice | vendorInvoice300     | Statistical     | costCenter300                | testProject                 |

    #  increase `IssueEffort` on effort issue
    When metasfresh contains S_TimeBooking:
      | S_TimeBooking_ID.Identifier | S_Issue_ID.Identifier | HoursAndMinutes | AD_User_Performing_ID.Identifier | BookedDate |
      | effortTimeBooking301        | effortIssue300        | 6:00            | vendorUser                       | 2022-10-18 |
    Then after not more than 10s, S_EffortControl is validated:
      | S_EffortControl_ID.Identifier | C_Activity_ID.Identifier | C_Project_ID.Identifier | OPT.PendingEffortSum | OPT.EffortSum | OPT.InvoiceableHours | OPT.IsIssueClosed |
      | effortControl300              | costCenter300            | testProject             | 0:00                 | 25:00         | 33                   | true              |
    And validate S_Issue:
      | S_Issue_ID.Identifier | Value                   | OPT.Name                | OPT.S_Parent_Issue_ID.Identifier | IssueType | IsEffortIssue | OPT.InvoiceableEffort | OPT.IssueEffort | OPT.C_Activity_ID.Identifier | OPT.C_Project_ID.Identifier | OPT.ExternalIssueNo | OPT.Status | OPT.Processed | OPT.AD_User_ID.Identifier |
      | budgetIssue300        | budgetIssue300_24102022 | budgetIssue300_24102022 |                                  | Internal  | N             | 33                    |                 | costCenter300                | testProject                 | 2510220             | Invoiced   | true          |                           |
      | effortIssue300        | effortIssue300_24102022 | effortIssue300_24102022 | budgetIssue300                   | Internal  | Y             | 0                     | 25:00           | costCenter300                | testProject                 | 2510221             | Invoiced   | true          | vendorUser                |
    Then after not more than 30s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | QtyToInvoice | OPT.AD_Table_ID.TableName | OPT.Record_ID.Identifier |
      | customerIC300                     | 33           | S_Issue                   | budgetIssue300           |
      | vendorIC300                       | 6            | S_Issue                   | effortIssue300           |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | QtyToInvoice | OPT.QtyOrdered | OPT.QtyDelivered | OPT.QtyInvoiced | OPT.M_Product_ID.Identifier | OPT.Bill_BPartner_ID.Identifier | OPT.Bill_Location_ID.Identifier | OPT.Bill_User_ID.Identifier | OPT.Processed | OPT.InvoiceRule | OPT.C_Activity_ID.Identifier | OPT.C_Project_ID.Identifier | OPT.Description                  | OPT.IsSOTrx | OPT.C_DocTypeInvoice_ID.Name |
      | customerIC300                     | 33           | 33             | 0                | 0               | invoiceableProduct          | customer_SO                     | customerLocation_SO             | customerUser                | false         | I               | costCenter300                | testProject                 | 2510220\nbudgetIssue300_24102022 | Y           | Ausgangsrechnung             |
      | vendorIC300                       | 6            | 25             | 0                | 19              | invoiceableProduct          | vendor_PO                       | vendorLocation_PO               | vendorUser                  | false         | I               | costCenter300                | testProject                 | 2510221\neffortIssue300_24102022 | N           | Internal Vendor Invoice      |

    When process invoice candidates
      | C_Invoice_Candidate_ID.Identifier |
      | vendorIC300                       |
    Then after not more than 30s, C_Invoice are found:
      | C_Invoice_ID.Identifier           | C_Invoice_Candidate_ID.Identifier |
      | vendorInvoice300,vendorInvoice301 | vendorIC300                       |
    And validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | AD_User_ID.Identifier | processed | docStatus | OPT.C_DocType_ID.Name   | OPT.C_Activity_ID.Identifier | OPT.C_Project_ID.Identifier |
      | vendorInvoice301        | vendor_PO                | vendorLocation_PO                 | vendorUser            | true      | CO        | Internal Vendor Invoice | costCenter300                | testProject                 |
    And validate invoice lines for vendorInvoice301:
      | C_InvoiceLine_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | Processed | OPT.PriceEntered | OPT.PriceActual | OPT.LineNetAmt | OPT.C_Activity_ID.Identifier | OPT.C_Project_ID.Identifier | OPT.Description                  |
      | vendorInvoiceLine301        | invoiceableProduct      | 6           | true      | 5                | 5               | 30             | costCenter300                | testProject                 | 2510221\neffortIssue300_24102022 |
    And after not more than 30s, Fact_Acct are found
      | TableName | Record_ID.Identifier | OPT.PostingType | OPT.C_Activity_ID.Identifier | OPT.C_Project_ID.Identifier |
      | C_Invoice | vendorInvoice300     | Statistical     | costCenter300                | testProject                 |

  @from:cucumber
  @Id:S0227_400
  Scenario: Generate invoice candidate from effort control and invoice it, decrease `IssueEffort` on effort issue and invoice again. validate that an invoice cannot be generated
    Given metasfresh contains C_Activity:
      | C_Activity_ID.Identifier | Name                   | Value                  |
      | costCenter400            | costCenter400_24102022 | costCenter400_24102022 |
    And metasfresh contains S_Issue:
      | S_Issue_ID.Identifier | Value                   | OPT.Name                | OPT.S_Parent_Issue_ID.Identifier | IssueType | IsEffortIssue | OPT.InvoiceableEffort | OPT.C_Activity_ID.Identifier | OPT.C_Project_ID.Identifier | OPT.ExternalIssueNo | OPT.Status | OPT.AD_User_ID.Identifier |
      | budgetIssue400        | budgetIssue400_24102022 | budgetIssue400_24102022 |                                  | Internal  | N             | 42                    | costCenter400                | testProject                 | 2510220             | New        |                           |
      | effortIssue400        | effortIssue400_24102022 | effortIssue400_24102022 | budgetIssue400                   | Internal  | Y             | 0                     | costCenter400                | testProject                 | 2510221             | New        | vendorUser                |
    And metasfresh contains S_TimeBooking:
      | S_TimeBooking_ID.Identifier | S_Issue_ID.Identifier | HoursAndMinutes | AD_User_Performing_ID.Identifier | BookedDate |
      | effortTimeBooking400        | effortIssue400        | 22:00           | vendorUser                       | 2022-10-17 |
    And after not more than 10s, S_EffortControl is found:
      | S_EffortControl_ID.Identifier | C_Activity_ID.Identifier | C_Project_ID.Identifier |
      | effortControl400              | costCenter400            | testProject             |
    And after not more than 10s, S_EffortControl is validated:
      | S_EffortControl_ID.Identifier | C_Activity_ID.Identifier | C_Project_ID.Identifier | OPT.PendingEffortSum | OPT.EffortSum | OPT.InvoiceableHours | OPT.IsIssueClosed |
      | effortControl400              | costCenter400            | testProject             | 22:00                | 22:00         | 42                   | false             |

    When 'generate invoice candidate' from effort control process is invoked
      | S_EffortControl_ID.Identifier |
      | effortControl400              |
    Then after not more than 30s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | QtyToInvoice | OPT.AD_Table_ID.TableName | OPT.Record_ID.Identifier |
      | customerIC400                     | 42           | S_Issue                   | budgetIssue400           |
      | vendorIC400                       | 22           | S_Issue                   | effortIssue400           |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | QtyToInvoice | OPT.QtyOrdered | OPT.QtyDelivered | OPT.QtyInvoiced | OPT.M_Product_ID.Identifier | OPT.Bill_BPartner_ID.Identifier | OPT.Bill_Location_ID.Identifier | OPT.Bill_User_ID.Identifier | OPT.Processed | OPT.InvoiceRule | OPT.C_Activity_ID.Identifier | OPT.C_Project_ID.Identifier | OPT.Description                  | OPT.IsSOTrx | OPT.C_DocTypeInvoice_ID.Name |
      | customerIC400                     | 42           | 42             | 0                | 0               | invoiceableProduct          | customer_SO                     | customerLocation_SO             | customerUser                | false         | I               | costCenter400                | testProject                 | 2510220\nbudgetIssue400_24102022 | Y           | Ausgangsrechnung             |
      | vendorIC400                       | 22           | 22             | 0                | 0               | invoiceableProduct          | vendor_PO                       | vendorLocation_PO               | vendorUser                  | false         | I               | costCenter400                | testProject                 | 2510221\neffortIssue400_24102022 | N           | Internal Vendor Invoice      |
    And validate S_Issue:
      | S_Issue_ID.Identifier | Value                   | OPT.Name                | OPT.S_Parent_Issue_ID.Identifier | IssueType | IsEffortIssue | OPT.InvoiceableEffort | OPT.IssueEffort | OPT.C_Activity_ID.Identifier | OPT.C_Project_ID.Identifier | OPT.ExternalIssueNo | OPT.Status | OPT.Processed | OPT.AD_User_ID.Identifier |
      | budgetIssue400        | budgetIssue400_24102022 | budgetIssue400_24102022 |                                  | Internal  | N             | 42                    |                 | costCenter400                | testProject                 | 2510220             | Invoiced   | true          |                           |
      | effortIssue400        | effortIssue400_24102022 | effortIssue400_24102022 | budgetIssue400                   | Internal  | Y             | 0                     | 22:00           | costCenter400                | testProject                 | 2510221             | Invoiced   | true          | vendorUser                |
    And after not more than 10s, S_EffortControl is validated:
      | S_EffortControl_ID.Identifier | C_Activity_ID.Identifier | C_Project_ID.Identifier | OPT.PendingEffortSum | OPT.EffortSum | OPT.InvoiceableHours | OPT.IsIssueClosed |
      | effortControl400              | costCenter400            | testProject             | 0:00                 | 22:00         | 42                   | true              |

    When process invoice candidates
      | C_Invoice_Candidate_ID.Identifier |
      | vendorIC400                       |
    Then after not more than 30s, C_Invoice are found:
      | C_Invoice_ID.Identifier | C_Invoice_Candidate_ID.Identifier |
      | vendorInvoice400        | vendorIC400                       |
    And validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | AD_User_ID.Identifier | processed | docStatus | OPT.C_DocType_ID.Name   | OPT.C_Activity_ID.Identifier | OPT.C_Project_ID.Identifier |
      | vendorInvoice400        | vendor_PO                | vendorLocation_PO                 | vendorUser            | true      | CO        | Internal Vendor Invoice | costCenter400                | testProject                 |
    And validate invoice lines for vendorInvoice400:
      | C_InvoiceLine_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | Processed | OPT.PriceEntered | OPT.PriceActual | OPT.LineNetAmt | OPT.C_Activity_ID.Identifier | OPT.C_Project_ID.Identifier | OPT.Description                  |
      | vendorInvoiceLine400        | invoiceableProduct      | 22          | true      | 5                | 5               | 110            | costCenter400                | testProject                 | 2510221\neffortIssue400_24102022 |
    And after not more than 30s, Fact_Acct are found
      | TableName | Record_ID.Identifier | OPT.PostingType | OPT.C_Activity_ID.Identifier | OPT.C_Project_ID.Identifier |
      | C_Invoice | vendorInvoice400     | Statistical     | costCenter400                | testProject                 |

    #  decrease `IssueEffort` on effort issue
    When metasfresh contains S_TimeBooking:
      | S_TimeBooking_ID.Identifier | S_Issue_ID.Identifier | HoursAndMinutes | AD_User_Performing_ID.Identifier | BookedDate |
      | effortTimeBooking400        | effortIssue400        | 15:00           | vendorUser                       | 2022-10-17 |
    Then after not more than 10s, S_EffortControl is validated:
      | S_EffortControl_ID.Identifier | C_Activity_ID.Identifier | C_Project_ID.Identifier | OPT.PendingEffortSum | OPT.EffortSum | OPT.InvoiceableHours | OPT.IsIssueClosed |
      | effortControl400              | costCenter400            | testProject             | 0:00                 | 15:00         | 42                   | true              |
    And validate S_Issue:
      | S_Issue_ID.Identifier | Value                   | OPT.Name                | OPT.S_Parent_Issue_ID.Identifier | IssueType | IsEffortIssue | OPT.InvoiceableEffort | OPT.IssueEffort | OPT.C_Activity_ID.Identifier | OPT.C_Project_ID.Identifier | OPT.ExternalIssueNo | OPT.Status | OPT.Processed | OPT.AD_User_ID.Identifier |
      | budgetIssue400        | budgetIssue400_24102022 | budgetIssue400_24102022 |                                  | Internal  | N             | 42                    |                 | costCenter400                | testProject                 | 2510220             | Invoiced   | true          |                           |
      | effortIssue400        | effortIssue400_24102022 | effortIssue400_24102022 | budgetIssue400                   | Internal  | Y             | 0                     | 15:00           | costCenter400                | testProject                 | 2510221             | Invoiced   | true          | vendorUser                |
    Then after not more than 30s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | QtyToInvoice | OPT.AD_Table_ID.TableName | OPT.Record_ID.Identifier |
      | customerIC400                     | 42           | S_Issue                   | budgetIssue400           |
      | vendorIC400                       | 0            | S_Issue                   | effortIssue400           |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | QtyToInvoice | OPT.QtyOrdered | OPT.QtyDelivered | OPT.QtyInvoiced | OPT.M_Product_ID.Identifier | OPT.Bill_BPartner_ID.Identifier | OPT.Bill_Location_ID.Identifier | OPT.Bill_User_ID.Identifier | OPT.Processed | OPT.InvoiceRule | OPT.C_Activity_ID.Identifier | OPT.C_Project_ID.Identifier | OPT.Description                  | OPT.IsSOTrx | OPT.C_DocTypeInvoice_ID.Name |
      | customerIC400                     | 42           | 42             | 0                | 0               | invoiceableProduct          | customer_SO                     | customerLocation_SO             | customerUser                | false         | I               | costCenter400                | testProject                 | 2510220\nbudgetIssue400_24102022 | Y           | Ausgangsrechnung             |
      | vendorIC400                       | 0            | 15             | 0                | 22              | invoiceableProduct          | vendor_PO                       | vendorLocation_PO               | vendorUser                  | true          | I               | costCenter400                | testProject                 | 2510221\neffortIssue400_24102022 | N           | Internal Vendor Invoice      |
    And invoice candidates are not billable
      | C_Invoice_Candidate_ID.Identifier |
      | vendorIC400                       |

  @from:cucumber
  @Id:S0227_500
  Scenario: Validate that vendor invoice candidate is not created when effort issue has no assignee
    Given metasfresh contains C_Activity:
      | C_Activity_ID.Identifier | Name                   | Value                  |
      | costCenter500            | costCenter500_24102022 | costCenter500_24102022 |
    And metasfresh contains S_Issue:
      | S_Issue_ID.Identifier | Value                   | OPT.Name                | OPT.S_Parent_Issue_ID.Identifier | IssueType | IsEffortIssue | OPT.InvoiceableEffort | OPT.C_Activity_ID.Identifier | OPT.C_Project_ID.Identifier | OPT.ExternalIssueNo | OPT.Status | OPT.AD_User_ID.Identifier |
      | budgetIssue500        | budgetIssue500_24102022 | budgetIssue500_24102022 |                                  | Internal  | N             | 16                    | costCenter500                | testProject                 | 2510220             | New        |                           |
      | effortIssue500        | effortIssue500_24102022 | effortIssue500_24102022 | budgetIssue500                   | Internal  | Y             | 0                     | costCenter500                | testProject                 | 2510221             | New        | null                      |
    And metasfresh contains S_TimeBooking:
      | S_TimeBooking_ID.Identifier | S_Issue_ID.Identifier | HoursAndMinutes | AD_User_Performing_ID.Identifier | BookedDate |
      | effortTimeBooking500        | effortIssue500        | 8:00            | vendorUser                       | 2022-10-20 |
    And after not more than 10s, S_EffortControl is found:
      | S_EffortControl_ID.Identifier | C_Activity_ID.Identifier | C_Project_ID.Identifier |
      | effortControl500              | costCenter500            | testProject             |
    And after not more than 10s, S_EffortControl is validated:
      | S_EffortControl_ID.Identifier | C_Activity_ID.Identifier | C_Project_ID.Identifier | OPT.PendingEffortSum | OPT.EffortSum | OPT.InvoiceableHours | OPT.IsIssueClosed |
      | effortControl500              | costCenter500            | testProject             | 8:00                 | 8:00          | 16                   | false             |
    When 'generate invoice candidate' from effort control process is invoked
      | S_EffortControl_ID.Identifier |
      | effortControl500              |
    Then after not more than 30s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | QtyToInvoice | OPT.AD_Table_ID.TableName | OPT.Record_ID.Identifier |
      | customerIC500                     | 16           | S_Issue                   | budgetIssue500           |
    And there is no C_Invoice_Candidate for:
      | TableName | Record_ID.Identifier |
      | S_Issue   | effortIssue500       |
    And validate S_Issue:
      | S_Issue_ID.Identifier | Value                   | OPT.Name                | OPT.S_Parent_Issue_ID.Identifier | IssueType | IsEffortIssue | OPT.InvoiceableEffort | OPT.IssueEffort | OPT.C_Activity_ID.Identifier | OPT.C_Project_ID.Identifier | OPT.ExternalIssueNo | OPT.Status | OPT.Processed | OPT.InvoicingErrorMsg         | OPT.IsInvoicingError |
      | budgetIssue500        | budgetIssue500_24102022 | budgetIssue500_24102022 |                                  | Internal  | N             | 16                    |                 | costCenter500                | testProject                 | 2510220             | Invoiced   | true          |                               | false                |
      | effortIssue500        | effortIssue500_24102022 | effortIssue500_24102022 | budgetIssue500                   | Internal  | Y             | 0                     | 8:00            | costCenter500                | testProject                 | 2510221             | New        | false         | Missing assignee from S_Issue | true                 |
