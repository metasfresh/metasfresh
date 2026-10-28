@from:cucumber
@allure.label.epic:E0130_Payment
@allure.label.feature:F00982_Payment
@allure.label.feature:F00977_SEPA_Payment_Export
@allure.label.feature:F00978_SEPA_Direct_Debit_Lastschrift
@ghActions:run_on_executor4
Feature: SEPA direct debit export (pain.008.001.08), CORE and B2B
## The export writes pain.008.001.08, one file per scheme (CORE / B2B), taking the mandate data
## (reference, signature date, sequence type) from the debtor's C_BP_BankAccount_DirectDebitMandate.
##
## Expected values are derived from the requirements (schema, D7 and D9 text-conversion rules), not from the writer's output.
##
## Data notes:
## - Every scenario runs in its own organization (created in the Background) with its own org BP, which is the creditor; no seed data is changed.
## - The creditor address is the org BP's default bill-to location (the Background creates it; some scenarios re-point it).
## - Debtor BPs get a run-unique Value (@Date@) so that each scenario has its own bank account, which the pay selection line then points to.
## - Cells are trimmed by Cucumber; <SPACE> stands for one space in an expected value.

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And metasfresh has date and time 2026-10-28T10:00:00+01:00[Europe/Berlin]

    # --- every scenario runs in its own organization ---
    And metasfresh contains AD_Org:
      | AD_Org_ID.Identifier | Name                   |
      | sellerOrg            | SEPA direct debit test |

    # --- invoice master data: zero tax, so that the invoice amount = the price ---
    And metasfresh contains C_TaxCategory
      | Identifier  |
      | taxCategory |
    And metasfresh contains C_Tax
      | Identifier | C_TaxCategory_ID | Rate | C_Country_ID.CountryCode | To_Country_ID.CountryCode | AD_Org_ID |
      | zeroTax    | taxCategory      | 0    | DE                       | DE                        | sellerOrg |
    And metasfresh contains C_PaymentTerm
      | Identifier |
      | pt_sepa    |
    And metasfresh contains M_Products:
      | Identifier |
      | product    |
    And metasfresh contains M_PricingSystems
      | Identifier    | Name       | Value      | IsActive |
      | pricingSystem | ps_sepa_dd | ps_sepa_dd | true     |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | C_Country.CountryCode | C_Currency.ISO_Code | Name       | SOTrx | IsTaxIncluded | PricePrecision |
      | pl_sales   | pricingSystem                 | DE                    | EUR                 | sepa-dd-PL | true  | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name        | ValidFrom  |
      | plv_sales  | pl_sales                  | sepa-dd-plv | 2026-01-01 |
    And metasfresh contains M_ProductPrices
      | Identifier   | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID |
      | productPrice | plv_sales                         | product                 | 10.0     | PCE               | taxCategory      |

    # --- creditor: the org BP of that organization (name, address), org bank account with BIC and creditor id ---
    And metasfresh contains C_BPartners without locations:
      | Identifier | Name                    | Value           | IsCustomer | AD_OrgBP_ID.Identifier |
      | creditorBP | SEPA Test Creditor GmbH | creditor_@Date@ | N          | sellerOrg              |
    And metasfresh contains C_Location:
      | C_Location_ID.Identifier | CountryCode | OPT.Address1    | OPT.Postal | OPT.City   |
      | creditorLoc              | DE          | Musterstrasse 1 | 53111      | Testhausen |
    And metasfresh contains C_BPartner_Locations:
      | Identifier  | GLN           | C_BPartner_ID.Identifier | OPT.C_Location_ID.Identifier | OPT.IsBillTo | OPT.IsBillToDefault |
      | creditorBPL | 4000000000129 | creditorBP               | creditorLoc                  | Y            | Y                   |
    # the org info is needed for the invoice tax lookup (country of the org)
    And metasfresh contains AD_OrgInfo:
      | AD_OrgInfo_ID.Identifier | AD_Org_ID.Identifier | Org_BPartner_ID.Identifier | OrgBP_Location_ID.Identifier |
      | creditorOrgInfo          | sellerOrg            | creditorBP                 | creditorBPL                  |
    And metasfresh contains C_Bank:
      | C_Bank_ID.Identifier | Name                    | RoutingNo | SwiftCode   |
      | creditorBank         | SEPA Test Creditor Bank | 10000000  | TESTDEC0XXX |
    And metasfresh contains C_BP_BankAccount
      | Identifier | C_BPartner_ID | C_Currency_ID | IBAN                   | C_Bank_ID.Identifier | SEPA_CreditorIdentifier |
      | orgAcct    | creditorBP    | EUR           | DE91100000000123456789 | creditorBank         | DE98ZZZ09999999999      |

  @from:cucumber
  @Id:S32708_TC1
  Scenario: TC1 B2B recurring mandate: one B2B file with 2 transactions, mandate DateLastUsed set to the collection date
    Given metasfresh contains C_BPartners:
      | Identifier | Name            | Value      | IsCustomer | M_PricingSystem_ID.Identifier | OPT.PaymentRule |
      | b2bDebtor  | SEPA Debtor B2B | b2b_@Date@ | Y          | pricingSystem                 | D               |
    And metasfresh contains C_Bank:
      | C_Bank_ID.Identifier | Name                 | RoutingNo | SwiftCode   |
      | b2bBank              | SEPA Debtor Bank B2B | 20000000  | TESTDEB2XXX |
    And metasfresh contains C_BP_BankAccount
      | Identifier | C_BPartner_ID | C_Currency_ID | IBAN                   | C_Bank_ID.Identifier |
      | b2bAcct    | b2bDebtor     | EUR           | DE89370400440532013000 | b2bBank              |
    And metasfresh contains C_BP_BankAccount_DirectDebitMandate:
      | C_BP_BankAccount_DirectDebitMandate_ID | C_BP_BankAccount_ID | MandateType | MandateReference | MandateDate | IsRecurring | DateLastUsed | AD_Org_ID |
      | b2bMandate                             | b2bAcct             | B2B         | MREF-B2B-001     | 2013-09-23  | Y           | 2025-09-04   | sellerOrg |
    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | IsSOTrx | DateInvoiced | C_Currency_ID | C_PaymentTerm_ID | OPT.PaymentRule | OPT.Description | AD_Org_ID |
      | invoice1   | b2bDebtor     | Y       | 2026-10-28   | EUR           | pt_sepa          | D               | Invoice B2B 1   | sellerOrg |
      | invoice2   | b2bDebtor     | Y       | 2026-10-28   | EUR           | pt_sepa          | D               | Invoice B2B 2   | sellerOrg |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced | Price  | C_Tax_ID |
      | invoiceL1  | invoice1     | product      | 1 PCE       | 100.00 | zeroTax  |
      | invoiceL2  | invoice2     | product      | 1 PCE       | 50.00  | zeroTax  |
    And the invoice identified by invoice1 is completed
    And the invoice identified by invoice2 is completed
    And metasfresh contains Pay Selection
      | Identifier   | C_BP_BankAccount_ID | PaySelectionTrxType | PayDate    | OPT.AD_Org_ID |
      | paySelection | orgAcct             | DD                  | 2026-11-02 | sellerOrg     |
    And "Create from..." is invoked for pay selection paySelection, using following parameters:
      | MatchRequirement | C_BPartner_ID | PayDate    |
      | CDD              | b2bDebtor     | 2026-11-02 |
    # no check of the pay selection lines here: the line order of two invoices of one partner is not defined; the file assertions below prove both invoices were selected
    And the pay selection identified by paySelection is completed

    When the SEPA export process is run for pay selection "paySelection"

    Then the SEPA export result is a single XML file
    And the pay selection "paySelection" has SEPA_Export records:
      | SEPA_Protocol   | NumberOfLines |
      | pain.008.001.08 | 2             |
    And the SEPA direct debit file for scheme B2B has header:
      | Namespace                                      | NbOfTxs | CtrlSum | Cdtr.Nm                 | CdtrSchmeId        | ReqdColltnDt |
      | urn:iso:std:iso:20022:tech:xsd:pain.008.001.08 | 2       | 150.00  | SEPA Test Creditor GmbH | DE98ZZZ09999999999 | 2026-11-02   |
    And the SEPA direct debit file for scheme B2B contains exactly the transactions:
      | Dbtr.Nm         | DbtrAcct.IBAN          | InstdAmt | MndtId       | DtOfSgntr  | SeqTp | DbtrAgt     | Ustrd         |
      | SEPA Debtor B2B | DE89370400440532013000 | 100.00   | MREF-B2B-001 | 2013-09-23 | RCUR  | TESTDEB2XXX | Invoice B2B 1 |
      | SEPA Debtor B2B | DE89370400440532013000 | 50.00    | MREF-B2B-001 | 2013-09-23 | RCUR  | TESTDEB2XXX | Invoice B2B 2 |
    And validate C_BP_BankAccount_DirectDebitMandate:
      | C_BP_BankAccount_DirectDebitMandate_ID | DateLastUsed |
      | b2bMandate                             | 2026-11-02   |

  @from:cucumber
  @Id:S32708_TC2
  Scenario: TC2 CORE recurring mandate: one CORE file with RCUR
    Given metasfresh contains C_BPartners:
      | Identifier | Name             | Value       | IsCustomer | M_PricingSystem_ID.Identifier | OPT.PaymentRule |
      | coreDebtor | SEPA Debtor CORE | core_@Date@ | Y          | pricingSystem                 | D               |
    And metasfresh contains C_Bank:
      | C_Bank_ID.Identifier | Name                  | RoutingNo | SwiftCode   |
      | coreBank             | SEPA Debtor Bank CORE | 30000000  | TESTDEC1XXX |
    And metasfresh contains C_BP_BankAccount
      | Identifier | C_BPartner_ID | C_Currency_ID | IBAN                   | C_Bank_ID.Identifier |
      | coreAcct   | coreDebtor    | EUR           | DE02120300000000202051 | coreBank             |
    And metasfresh contains C_BP_BankAccount_DirectDebitMandate:
      | C_BP_BankAccount_DirectDebitMandate_ID | C_BP_BankAccount_ID | MandateType | MandateReference | MandateDate | IsRecurring | DateLastUsed | AD_Org_ID |
      | coreMandate                            | coreAcct            | CORE        | MREF-CORE-001    | 2014-03-11  | Y           | 2025-09-04   | sellerOrg |
    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | IsSOTrx | DateInvoiced | C_Currency_ID | C_PaymentTerm_ID | OPT.PaymentRule | OPT.Description | AD_Org_ID |
      | invoice    | coreDebtor    | Y       | 2026-10-28   | EUR           | pt_sepa          | D               | Invoice CORE 1  | sellerOrg |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced | Price | C_Tax_ID |
      | invoiceL1  | invoice      | product      | 1 PCE       | 80.00 | zeroTax  |
    And the invoice identified by invoice is completed
    And metasfresh contains Pay Selection
      | Identifier   | C_BP_BankAccount_ID | PaySelectionTrxType | PayDate    | OPT.AD_Org_ID |
      | paySelection | orgAcct             | DD                  | 2026-11-02 | sellerOrg     |
    And "Create from..." is invoked for pay selection paySelection, using following parameters:
      | MatchRequirement | C_BPartner_ID | PayDate    |
      | CDD              | coreDebtor    | 2026-11-02 |
    And the pay selection identified by paySelection is completed
    And the Pay selection identified by paySelection has exactly the following lines
      | C_Invoice_ID | OpenAmt | PayAmt | C_BP_BankAccount_ID |
      | invoice      | 80.00   | 80.00  | coreAcct            |

    When the SEPA export process is run for pay selection "paySelection"

    Then the SEPA export result is a single XML file
    And the pay selection "paySelection" has SEPA_Export records:
      | SEPA_Protocol   | NumberOfLines |
      | pain.008.001.08 | 1             |
    And the SEPA direct debit file for scheme CORE has header:
      | Namespace                                      | NbOfTxs | CtrlSum | Cdtr.Nm                 | CdtrSchmeId        | ReqdColltnDt |
      | urn:iso:std:iso:20022:tech:xsd:pain.008.001.08 | 1       | 80.00   | SEPA Test Creditor GmbH | DE98ZZZ09999999999 | 2026-11-02   |
    And the SEPA direct debit file for scheme CORE contains exactly the transactions:
      | Dbtr.Nm          | DbtrAcct.IBAN          | InstdAmt | MndtId        | DtOfSgntr  | SeqTp | DbtrAgt     | Ustrd          |
      | SEPA Debtor CORE | DE02120300000000202051 | 80.00    | MREF-CORE-001 | 2014-03-11 | RCUR  | TESTDEC1XXX | Invoice CORE 1 |

  @from:cucumber
  @Id:S32708_TC3
  Scenario: TC3 CORE mandate that is not recurring and was never used: sequence type OOFF
    Given metasfresh contains C_BPartners:
      | Identifier | Name             | Value       | IsCustomer | M_PricingSystem_ID.Identifier | OPT.PaymentRule |
      | coreDebtor | SEPA Debtor CORE | core_@Date@ | Y          | pricingSystem                 | D               |
    And metasfresh contains C_Bank:
      | C_Bank_ID.Identifier | Name                  | RoutingNo | SwiftCode   |
      | coreBank             | SEPA Debtor Bank CORE | 30000000  | TESTDEC1XXX |
    And metasfresh contains C_BP_BankAccount
      | Identifier | C_BPartner_ID | C_Currency_ID | IBAN                   | C_Bank_ID.Identifier |
      | coreAcct   | coreDebtor    | EUR           | DE02120300000000202051 | coreBank             |
    And metasfresh contains C_BP_BankAccount_DirectDebitMandate:
      | C_BP_BankAccount_DirectDebitMandate_ID | C_BP_BankAccount_ID | MandateType | MandateReference | MandateDate | IsRecurring | DateLastUsed | AD_Org_ID |
      | coreMandate                            | coreAcct            | CORE        | MREF-TC3-001     | 2026-10-01  | N           | -            | sellerOrg |
    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | IsSOTrx | DateInvoiced | C_Currency_ID | C_PaymentTerm_ID | OPT.PaymentRule | OPT.Description | AD_Org_ID |
      | invoice    | coreDebtor    | Y       | 2026-10-28   | EUR           | pt_sepa          | D               | Invoice OOFF 1  | sellerOrg |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced | Price | C_Tax_ID |
      | invoiceL1  | invoice      | product      | 1 PCE       | 30.00 | zeroTax  |
    And the invoice identified by invoice is completed
    And metasfresh contains Pay Selection
      | Identifier   | C_BP_BankAccount_ID | PaySelectionTrxType | PayDate    | OPT.AD_Org_ID |
      | paySelection | orgAcct             | DD                  | 2026-11-02 | sellerOrg     |
    And "Create from..." is invoked for pay selection paySelection, using following parameters:
      | MatchRequirement | C_BPartner_ID | PayDate    |
      | CDD              | coreDebtor    | 2026-11-02 |
    And the pay selection identified by paySelection is completed
    And the Pay selection identified by paySelection has exactly the following lines
      | C_Invoice_ID | OpenAmt | PayAmt | C_BP_BankAccount_ID |
      | invoice      | 30.00   | 30.00  | coreAcct            |

    When the SEPA export process is run for pay selection "paySelection"

    Then the SEPA export result is a single XML file
    And the SEPA direct debit file for scheme CORE has header:
      | Namespace                                      | NbOfTxs | CtrlSum | Cdtr.Nm                 | CdtrSchmeId        | ReqdColltnDt |
      | urn:iso:std:iso:20022:tech:xsd:pain.008.001.08 | 1       | 30.00   | SEPA Test Creditor GmbH | DE98ZZZ09999999999 | 2026-11-02   |
    And the SEPA direct debit file for scheme CORE contains exactly the transactions:
      | Dbtr.Nm          | DbtrAcct.IBAN          | InstdAmt | MndtId       | DtOfSgntr  | SeqTp | DbtrAgt     | Ustrd          |
      | SEPA Debtor CORE | DE02120300000000202051 | 30.00    | MREF-TC3-001 | 2026-10-01 | OOFF  | TESTDEC1XXX | Invoice OOFF 1 |

  @from:cucumber
  @Id:S32708_TC4
  Scenario: TC4 only the org-* mandate is active: its reference is exported, the inactive mandate of the export org is ignored
    Given metasfresh contains C_BPartners:
      | Identifier | Name            | Value      | IsCustomer | M_PricingSystem_ID.Identifier | OPT.PaymentRule |
      | b2bDebtor  | SEPA Debtor B2B | b2b_@Date@ | Y          | pricingSystem                 | D               |
    And metasfresh contains C_Bank:
      | C_Bank_ID.Identifier | Name                 | RoutingNo | SwiftCode   |
      | b2bBank              | SEPA Debtor Bank B2B | 20000000  | TESTDEB2XXX |
    And metasfresh contains C_BP_BankAccount
      | Identifier | C_BPartner_ID | C_Currency_ID | IBAN                   | C_Bank_ID.Identifier |
      | b2bAcct    | b2bDebtor     | EUR           | DE89370400440532013000 | b2bBank              |
    And metasfresh contains C_BP_BankAccount_DirectDebitMandate:
      | C_BP_BankAccount_DirectDebitMandate_ID | C_BP_BankAccount_ID | MandateType | MandateReference  | MandateDate | IsRecurring | DateLastUsed | AD_Org_ID | IsActive |
      | orgStarMandate                         | b2bAcct             | B2B         | MREF-TC4-ORGSTAR  | 2020-05-04  | Y           | 2025-09-04   | 0         | Y        |
      | inactiveMandate                        | b2bAcct             | B2B         | MREF-TC4-INACTIVE | 2019-02-03  | Y           | 2025-09-04   | sellerOrg | N        |
    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | IsSOTrx | DateInvoiced | C_Currency_ID | C_PaymentTerm_ID | OPT.PaymentRule | OPT.Description | AD_Org_ID |
      | invoice    | b2bDebtor     | Y       | 2026-10-28   | EUR           | pt_sepa          | D               | Invoice B2B 1   | sellerOrg |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced | Price  | C_Tax_ID |
      | invoiceL1  | invoice      | product      | 1 PCE       | 100.00 | zeroTax  |
    And the invoice identified by invoice is completed
    And metasfresh contains Pay Selection
      | Identifier   | C_BP_BankAccount_ID | PaySelectionTrxType | PayDate    | OPT.AD_Org_ID |
      | paySelection | orgAcct             | DD                  | 2026-11-02 | sellerOrg     |
    And "Create from..." is invoked for pay selection paySelection, using following parameters:
      | MatchRequirement | C_BPartner_ID | PayDate    |
      | CDD              | b2bDebtor     | 2026-11-02 |
    And the pay selection identified by paySelection is completed
    And the Pay selection identified by paySelection has exactly the following lines
      | C_Invoice_ID | OpenAmt | PayAmt | C_BP_BankAccount_ID |
      | invoice      | 100.00  | 100.00 | b2bAcct             |

    When the SEPA export process is run for pay selection "paySelection"

    Then the SEPA export result is a single XML file
    And the SEPA direct debit file for scheme B2B contains exactly the transactions:
      | Dbtr.Nm         | DbtrAcct.IBAN          | InstdAmt | MndtId           | DtOfSgntr  | SeqTp |
      | SEPA Debtor B2B | DE89370400440532013000 | 100.00   | MREF-TC4-ORGSTAR | 2020-05-04 | RCUR  |

  @from:cucumber
  @Id:S32708_TC6
  Scenario: TC6 one pay selection with a B2B and a CORE debtor: ZIP with one file per scheme, each with its own totals
    Given metasfresh contains C_BPartners:
      | Identifier | Name             | Value       | IsCustomer | M_PricingSystem_ID.Identifier | OPT.PaymentRule |
      | b2bDebtor  | SEPA Debtor B2B  | b2b_@Date@  | Y          | pricingSystem                 | D               |
      | coreDebtor | SEPA Debtor CORE | core_@Date@ | Y          | pricingSystem                 | D               |
    And metasfresh contains C_Bank:
      | C_Bank_ID.Identifier | Name                  | RoutingNo | SwiftCode   |
      | b2bBank              | SEPA Debtor Bank B2B  | 20000000  | TESTDEB2XXX |
      | coreBank             | SEPA Debtor Bank CORE | 30000000  | TESTDEC1XXX |
    And metasfresh contains C_BP_BankAccount
      | Identifier | C_BPartner_ID | C_Currency_ID | IBAN                   | C_Bank_ID.Identifier |
      | b2bAcct    | b2bDebtor     | EUR           | DE89370400440532013000 | b2bBank              |
      | coreAcct   | coreDebtor    | EUR           | DE02120300000000202051 | coreBank             |
    And metasfresh contains C_BP_BankAccount_DirectDebitMandate:
      | C_BP_BankAccount_DirectDebitMandate_ID | C_BP_BankAccount_ID | MandateType | MandateReference | MandateDate | IsRecurring | DateLastUsed | AD_Org_ID |
      | b2bMandate                             | b2bAcct             | B2B         | MREF-B2B-006     | 2013-09-23  | Y           | 2025-09-04   | sellerOrg |
      | coreMandate                            | coreAcct            | CORE        | MREF-CORE-006    | 2014-03-11  | Y           | 2025-09-04   | sellerOrg |
    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | IsSOTrx | DateInvoiced | C_Currency_ID | C_PaymentTerm_ID | OPT.PaymentRule | OPT.Description | AD_Org_ID |
      | b2bInv1    | b2bDebtor     | Y       | 2026-10-28   | EUR           | pt_sepa          | D               | Invoice B2B 1   | sellerOrg |
      | coreInv1   | coreDebtor    | Y       | 2026-10-28   | EUR           | pt_sepa          | D               | Invoice CORE 1  | sellerOrg |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced | Price  | C_Tax_ID |
      | b2bInvL1   | b2bInv1      | product      | 1 PCE       | 100.00 | zeroTax  |
      | coreInvL1  | coreInv1     | product      | 1 PCE       | 50.00  | zeroTax  |
    And the invoice identified by b2bInv1 is completed
    And the invoice identified by coreInv1 is completed
    And metasfresh contains Pay Selection
      | Identifier   | C_BP_BankAccount_ID | PaySelectionTrxType | PayDate    | OPT.AD_Org_ID |
      | paySelection | orgAcct             | DD                  | 2026-11-02 | sellerOrg     |
    # one "Create from..." per debtor, so that invoices of other tests on the same DB are not picked up
    And "Create from..." is invoked for pay selection paySelection, using following parameters:
      | MatchRequirement | C_BPartner_ID | PayDate    |
      | CDD              | b2bDebtor     | 2026-11-02 |
    And "Create from..." is invoked for pay selection paySelection, using following parameters:
      | MatchRequirement | C_BPartner_ID | PayDate    |
      | CDD              | coreDebtor    | 2026-11-02 |
    And the pay selection identified by paySelection is completed
    And the Pay selection identified by paySelection has exactly the following lines
      | C_Invoice_ID | OpenAmt | PayAmt | C_BP_BankAccount_ID |
      | b2bInv1      | 100.00  | 100.00 | b2bAcct             |
      | coreInv1     | 50.00   | 50.00  | coreAcct            |

    When the SEPA export process is run for pay selection "paySelection"

    Then the SEPA export result is a ZIP with 2 XML files
    And the pay selection "paySelection" has SEPA_Export records:
      | SEPA_Protocol   | NumberOfLines |
      | pain.008.001.08 | 1             |
      | pain.008.001.08 | 1             |
    And the SEPA direct debit file for scheme B2B has header:
      | Namespace                                      | NbOfTxs | CtrlSum | Cdtr.Nm                 | CdtrSchmeId        | ReqdColltnDt |
      | urn:iso:std:iso:20022:tech:xsd:pain.008.001.08 | 1       | 100.00  | SEPA Test Creditor GmbH | DE98ZZZ09999999999 | 2026-11-02   |
    And the SEPA direct debit file for scheme B2B contains exactly the transactions:
      | Dbtr.Nm         | DbtrAcct.IBAN          | InstdAmt | MndtId       | DtOfSgntr  | SeqTp | DbtrAgt     | Ustrd         |
      | SEPA Debtor B2B | DE89370400440532013000 | 100.00   | MREF-B2B-006 | 2013-09-23 | RCUR  | TESTDEB2XXX | Invoice B2B 1 |
    And the SEPA direct debit file for scheme CORE has header:
      | Namespace                                      | NbOfTxs | CtrlSum | Cdtr.Nm                 | CdtrSchmeId        | ReqdColltnDt |
      | urn:iso:std:iso:20022:tech:xsd:pain.008.001.08 | 1       | 50.00   | SEPA Test Creditor GmbH | DE98ZZZ09999999999 | 2026-11-02   |
    And the SEPA direct debit file for scheme CORE contains exactly the transactions:
      | Dbtr.Nm          | DbtrAcct.IBAN          | InstdAmt | MndtId        | DtOfSgntr  | SeqTp | DbtrAgt     | Ustrd          |
      | SEPA Debtor CORE | DE02120300000000202051 | 50.00    | MREF-CORE-006 | 2014-03-11 | RCUR  | TESTDEC1XXX | Invoice CORE 1 |

  # TC14: the creditor address comes from the org BP's location; the debtor bank account's holder data (A_Name, A_City) never appears as creditor.
  # The creditor BP location (GLN 4000000000129) is re-pointed to the scenario's C_Location.
  @from:cucumber
  @Id:S32708_TC14a
  Scenario: TC14a org BP location with a city: Cdtr.PstlAdr has town and country of the org BP, not the debtor account holder's city
    Given metasfresh contains C_BPartners:
      | Identifier | Name            | Value      | IsCustomer | M_PricingSystem_ID.Identifier | OPT.PaymentRule |
      | b2bDebtor  | SEPA Debtor B2B | b2b_@Date@ | Y          | pricingSystem                 | D               |
    And metasfresh contains C_Bank:
      | C_Bank_ID.Identifier | Name                 | RoutingNo | SwiftCode   |
      | b2bBank              | SEPA Debtor Bank B2B | 20000000  | TESTDEB2XXX |
    And metasfresh contains C_BP_BankAccount
      | Identifier | C_BPartner_ID | C_Currency_ID | IBAN                   | C_Bank_ID.Identifier | OPT.A_Name          | OPT.A_City  |
      | b2bAcct    | b2bDebtor     | EUR           | DE89370400440532013000 | b2bBank              | Account Holder Name | Holder City |
    And metasfresh contains C_BP_BankAccount_DirectDebitMandate:
      | C_BP_BankAccount_DirectDebitMandate_ID | C_BP_BankAccount_ID | MandateType | MandateReference | MandateDate | IsRecurring | DateLastUsed | AD_Org_ID |
      | b2bMandate                             | b2bAcct             | B2B         | MREF-TC14A-001   | 2013-09-23  | Y           | 2025-09-04   | sellerOrg |
    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | IsSOTrx | DateInvoiced | C_Currency_ID | C_PaymentTerm_ID | OPT.PaymentRule | OPT.Description | AD_Org_ID |
      | invoice    | b2bDebtor     | Y       | 2026-10-28   | EUR           | pt_sepa          | D               | Invoice B2B 1   | sellerOrg |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced | Price  | C_Tax_ID |
      | invoiceL1  | invoice      | product      | 1 PCE       | 100.00 | zeroTax  |
    And the invoice identified by invoice is completed
    And metasfresh contains Pay Selection
      | Identifier   | C_BP_BankAccount_ID | PaySelectionTrxType | PayDate    | OPT.AD_Org_ID |
      | paySelection | orgAcct             | DD                  | 2026-11-02 | sellerOrg     |
    And "Create from..." is invoked for pay selection paySelection, using following parameters:
      | MatchRequirement | C_BPartner_ID | PayDate    |
      | CDD              | b2bDebtor     | 2026-11-02 |
    And the pay selection identified by paySelection is completed
    And the Pay selection identified by paySelection has exactly the following lines
      | C_Invoice_ID | OpenAmt | PayAmt | C_BP_BankAccount_ID |
      | invoice      | 100.00  | 100.00 | b2bAcct             |

    When the SEPA export process is run for pay selection "paySelection"

    Then the SEPA export result is a single XML file
    And the SEPA direct debit file for scheme B2B has header:
      | Namespace                                      | NbOfTxs | CtrlSum | Cdtr.Nm                 | CdtrSchmeId        | ReqdColltnDt | Cdtr.PstlAdr.TwnNm | Cdtr.PstlAdr.Ctry |
      | urn:iso:std:iso:20022:tech:xsd:pain.008.001.08 | 1       | 100.00  | SEPA Test Creditor GmbH | DE98ZZZ09999999999 | 2026-11-02   | Testhausen         | DE                |

  @from:cucumber
  @Id:S32708_TC14b
  Scenario: TC14b org BP location without a city: no Cdtr.PstlAdr at all
    Given metasfresh contains C_BPartners:
      | Identifier | Name            | Value      | IsCustomer | M_PricingSystem_ID.Identifier | OPT.PaymentRule |
      | b2bDebtor  | SEPA Debtor B2B | b2b_@Date@ | Y          | pricingSystem                 | D               |
    And metasfresh contains C_Location:
      | C_Location_ID.Identifier | CountryCode | OPT.Address1    | OPT.Postal |
      | noCityLoc                | DE          | Musterstrasse 2 | 53111      |
    And metasfresh contains C_BPartner_Locations:
      | Identifier  | GLN           | C_BPartner_ID.Identifier | OPT.C_Location_ID.Identifier | OPT.IsBillTo | OPT.IsBillToDefault |
      | creditorBPL | 4000000000129 | creditorBP               | noCityLoc                    | Y            | Y                   |
    And metasfresh contains C_Bank:
      | C_Bank_ID.Identifier | Name                 | RoutingNo | SwiftCode   |
      | b2bBank              | SEPA Debtor Bank B2B | 20000000  | TESTDEB2XXX |
    And metasfresh contains C_BP_BankAccount
      | Identifier | C_BPartner_ID | C_Currency_ID | IBAN                   | C_Bank_ID.Identifier | OPT.A_Name          | OPT.A_City  |
      | b2bAcct    | b2bDebtor     | EUR           | DE89370400440532013000 | b2bBank              | Account Holder Name | Holder City |
    And metasfresh contains C_BP_BankAccount_DirectDebitMandate:
      | C_BP_BankAccount_DirectDebitMandate_ID | C_BP_BankAccount_ID | MandateType | MandateReference | MandateDate | IsRecurring | DateLastUsed | AD_Org_ID |
      | b2bMandate                             | b2bAcct             | B2B         | MREF-TC14B-001   | 2013-09-23  | Y           | 2025-09-04   | sellerOrg |
    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | IsSOTrx | DateInvoiced | C_Currency_ID | C_PaymentTerm_ID | OPT.PaymentRule | OPT.Description | AD_Org_ID |
      | invoice    | b2bDebtor     | Y       | 2026-10-28   | EUR           | pt_sepa          | D               | Invoice B2B 1   | sellerOrg |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced | Price  | C_Tax_ID |
      | invoiceL1  | invoice      | product      | 1 PCE       | 100.00 | zeroTax  |
    And the invoice identified by invoice is completed
    And metasfresh contains Pay Selection
      | Identifier   | C_BP_BankAccount_ID | PaySelectionTrxType | PayDate    | OPT.AD_Org_ID |
      | paySelection | orgAcct             | DD                  | 2026-11-02 | sellerOrg     |
    And "Create from..." is invoked for pay selection paySelection, using following parameters:
      | MatchRequirement | C_BPartner_ID | PayDate    |
      | CDD              | b2bDebtor     | 2026-11-02 |
    And the pay selection identified by paySelection is completed
    And the Pay selection identified by paySelection has exactly the following lines
      | C_Invoice_ID | OpenAmt | PayAmt | C_BP_BankAccount_ID |
      | invoice      | 100.00  | 100.00 | b2bAcct             |

    When the SEPA export process is run for pay selection "paySelection"

    Then the SEPA export result is a single XML file
    And the SEPA direct debit file for scheme B2B has header:
      | Namespace                                      | NbOfTxs | CtrlSum | Cdtr.Nm                 | CdtrSchmeId        | ReqdColltnDt | Cdtr.PstlAdr |
      | urn:iso:std:iso:20022:tech:xsd:pain.008.001.08 | 1       | 100.00  | SEPA Test Creditor GmbH | DE98ZZZ09999999999 | 2026-11-02   | -            |

  @from:cucumber
  @Id:S32708_TC14c
  Scenario: TC14c org BP location whose city is only disallowed characters (empty after conversion): no Cdtr.PstlAdr, the export still succeeds
    Given metasfresh contains C_BPartners:
      | Identifier | Name            | Value      | IsCustomer | M_PricingSystem_ID.Identifier | OPT.PaymentRule |
      | b2bDebtor  | SEPA Debtor B2B | b2b_@Date@ | Y          | pricingSystem                 | D               |
    And metasfresh contains C_Location:
      | C_Location_ID.Identifier | CountryCode | OPT.Address1    | OPT.Postal | OPT.City |
      | euroCityLoc              | DE          | Musterstrasse 3 | 53111      | €€       |
    And metasfresh contains C_BPartner_Locations:
      | Identifier  | GLN           | C_BPartner_ID.Identifier | OPT.C_Location_ID.Identifier | OPT.IsBillTo | OPT.IsBillToDefault |
      | creditorBPL | 4000000000129 | creditorBP               | euroCityLoc                  | Y            | Y                   |
    And metasfresh contains C_Bank:
      | C_Bank_ID.Identifier | Name                 | RoutingNo | SwiftCode   |
      | b2bBank              | SEPA Debtor Bank B2B | 20000000  | TESTDEB2XXX |
    And metasfresh contains C_BP_BankAccount
      | Identifier | C_BPartner_ID | C_Currency_ID | IBAN                   | C_Bank_ID.Identifier | OPT.A_Name          | OPT.A_City  |
      | b2bAcct    | b2bDebtor     | EUR           | DE89370400440532013000 | b2bBank              | Account Holder Name | Holder City |
    And metasfresh contains C_BP_BankAccount_DirectDebitMandate:
      | C_BP_BankAccount_DirectDebitMandate_ID | C_BP_BankAccount_ID | MandateType | MandateReference | MandateDate | IsRecurring | DateLastUsed | AD_Org_ID |
      | b2bMandate                             | b2bAcct             | B2B         | MREF-TC14C-001   | 2013-09-23  | Y           | 2025-09-04   | sellerOrg |
    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | IsSOTrx | DateInvoiced | C_Currency_ID | C_PaymentTerm_ID | OPT.PaymentRule | OPT.Description | AD_Org_ID |
      | invoice    | b2bDebtor     | Y       | 2026-10-28   | EUR           | pt_sepa          | D               | Invoice B2B 1   | sellerOrg |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced | Price  | C_Tax_ID |
      | invoiceL1  | invoice      | product      | 1 PCE       | 100.00 | zeroTax  |
    And the invoice identified by invoice is completed
    And metasfresh contains Pay Selection
      | Identifier   | C_BP_BankAccount_ID | PaySelectionTrxType | PayDate    | OPT.AD_Org_ID |
      | paySelection | orgAcct             | DD                  | 2026-11-02 | sellerOrg     |
    And "Create from..." is invoked for pay selection paySelection, using following parameters:
      | MatchRequirement | C_BPartner_ID | PayDate    |
      | CDD              | b2bDebtor     | 2026-11-02 |
    And the pay selection identified by paySelection is completed
    And the Pay selection identified by paySelection has exactly the following lines
      | C_Invoice_ID | OpenAmt | PayAmt | C_BP_BankAccount_ID |
      | invoice      | 100.00  | 100.00 | b2bAcct             |

    When the SEPA export process is run for pay selection "paySelection"

    Then the SEPA export result is a single XML file
    And the SEPA direct debit file for scheme B2B has header:
      | Namespace                                      | NbOfTxs | CtrlSum | Cdtr.Nm                 | CdtrSchmeId        | ReqdColltnDt | Cdtr.PstlAdr |
      | urn:iso:std:iso:20022:tech:xsd:pain.008.001.08 | 1       | 100.00  | SEPA Test Creditor GmbH | DE98ZZZ09999999999 | 2026-11-02   | -            |

  @from:cucumber
  @Id:S32708_TC15
  Scenario: TC15 debtor bank without BIC: DbtrAgt is NOTPROVIDED
    Given metasfresh contains C_BPartners:
      | Identifier | Name            | Value      | IsCustomer | M_PricingSystem_ID.Identifier | OPT.PaymentRule |
      | b2bDebtor  | SEPA Debtor B2B | b2b_@Date@ | Y          | pricingSystem                 | D               |
    And metasfresh contains C_Bank:
      | C_Bank_ID.Identifier | Name                         | RoutingNo |
      | noBicBank            | SEPA Debtor Bank without BIC | 40000000  |
    And metasfresh contains C_BP_BankAccount
      | Identifier | C_BPartner_ID | C_Currency_ID | IBAN                   | C_Bank_ID.Identifier |
      | b2bAcct    | b2bDebtor     | EUR           | DE89370400440532013000 | noBicBank            |
    And metasfresh contains C_BP_BankAccount_DirectDebitMandate:
      | C_BP_BankAccount_DirectDebitMandate_ID | C_BP_BankAccount_ID | MandateType | MandateReference | MandateDate | IsRecurring | DateLastUsed | AD_Org_ID |
      | b2bMandate                             | b2bAcct             | B2B         | MREF-TC15-001    | 2013-09-23  | Y           | 2025-09-04   | sellerOrg |
    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | IsSOTrx | DateInvoiced | C_Currency_ID | C_PaymentTerm_ID | OPT.PaymentRule | OPT.Description | AD_Org_ID |
      | invoice    | b2bDebtor     | Y       | 2026-10-28   | EUR           | pt_sepa          | D               | Invoice B2B 1   | sellerOrg |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced | Price  | C_Tax_ID |
      | invoiceL1  | invoice      | product      | 1 PCE       | 100.00 | zeroTax  |
    And the invoice identified by invoice is completed
    And metasfresh contains Pay Selection
      | Identifier   | C_BP_BankAccount_ID | PaySelectionTrxType | PayDate    | OPT.AD_Org_ID |
      | paySelection | orgAcct             | DD                  | 2026-11-02 | sellerOrg     |
    And "Create from..." is invoked for pay selection paySelection, using following parameters:
      | MatchRequirement | C_BPartner_ID | PayDate    |
      | CDD              | b2bDebtor     | 2026-11-02 |
    And the pay selection identified by paySelection is completed
    And the Pay selection identified by paySelection has exactly the following lines
      | C_Invoice_ID | OpenAmt | PayAmt | C_BP_BankAccount_ID |
      | invoice      | 100.00  | 100.00 | b2bAcct             |

    When the SEPA export process is run for pay selection "paySelection"

    Then the SEPA export result is a single XML file
    And the SEPA direct debit file for scheme B2B contains exactly the transactions:
      | Dbtr.Nm         | DbtrAcct.IBAN          | InstdAmt | MndtId        | DtOfSgntr  | SeqTp | DbtrAgt     |
      | SEPA Debtor B2B | DE89370400440532013000 | 100.00   | MREF-TC15-001 | 2013-09-23 | RCUR  | NOTPROVIDED |

  # TC16: text conversion (D9). Expected values are derived from the rules, never from the writer's output.
  #   MndtId:  Ä -> AE, & -> space, ö -> oe                 "MREF-Ä&ö/001" -> "MREF-AE oe/001"
  #   Dbtr.Nm: at most 70 chars; € -> space; umlauts, ß and & are kept; the rest over 70 chars is cut
  #            input    = "Müller & Söhne € Großhandel " (28 chars) + 60 x X = 88 chars
  #            expected = "Müller & Söhne   Großhandel " (28 chars; the € became a space, so 3 spaces between Söhne and Großhandel) + 42 x X = 70 chars
  #   Ustrd:   at most 140 chars; „ and “ -> space
  #            input    = "Rechnung „Nr. 1“ " (17 chars) + 140 x Y = 157 chars
  #            expected = "Rechnung  Nr. 1  " (17 chars; 2 spaces before Nr., 2 after 1) + 123 x Y = 140 chars
  #   second debtor: Dbtr.Nm "€ € €" (only disallowed characters) -> a single space; the export succeeds
  @from:cucumber
  @Id:S32708_TC16
  Scenario: TC16 text conversion: mandate reference, debtor name and remittance info are converted and cut according to the D9 rules; a name of only disallowed characters becomes one space
    Given metasfresh contains C_BPartners:
      | Identifier  | Name                                                                                     | Value        | IsCustomer | M_PricingSystem_ID.Identifier | OPT.PaymentRule |
      | longDebtor  | Müller & Söhne € Großhandel XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX | long_@Date@  | Y          | pricingSystem                 | D               |
      | emptyDebtor | € € €                                                                                    | euros_@Date@ | Y          | pricingSystem                 | D               |
    And metasfresh contains C_Bank:
      | C_Bank_ID.Identifier | Name                  | RoutingNo | SwiftCode   |
      | coreBank             | SEPA Debtor Bank CORE | 30000000  | TESTDEC1XXX |
    And metasfresh contains C_BP_BankAccount
      | Identifier | C_BPartner_ID | C_Currency_ID | IBAN                   | C_Bank_ID.Identifier |
      | longAcct   | longDebtor    | EUR           | DE89370400440532013000 | coreBank             |
      | emptyAcct  | emptyDebtor   | EUR           | DE02120300000000202051 | coreBank             |
    And metasfresh contains C_BP_BankAccount_DirectDebitMandate:
      | C_BP_BankAccount_DirectDebitMandate_ID | C_BP_BankAccount_ID | MandateType | MandateReference | MandateDate | IsRecurring | DateLastUsed | AD_Org_ID |
      | longMandate                            | longAcct            | CORE        | MREF-Ä&ö/001     | 2014-03-11  | Y           | 2025-09-04   | sellerOrg |
      | emptyMandate                           | emptyAcct           | CORE        | MREF-TC16-002    | 2014-03-11  | Y           | 2025-09-04   | sellerOrg |
    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | IsSOTrx | DateInvoiced | C_Currency_ID | C_PaymentTerm_ID | OPT.PaymentRule | OPT.Description                                                                                                                                               | AD_Org_ID |
      | longInv    | longDebtor    | Y       | 2026-10-28   | EUR           | pt_sepa          | D               | Rechnung „Nr. 1“ YYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYY | sellerOrg |
      | emptyInv   | emptyDebtor   | Y       | 2026-10-28   | EUR           | pt_sepa          | D               | Invoice for disallowed characters                                                                                                                             | sellerOrg |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced | Price  | C_Tax_ID |
      | longInvL   | longInv      | product      | 1 PCE       | 100.00 | zeroTax  |
      | emptyInvL  | emptyInv     | product      | 1 PCE       | 20.00  | zeroTax  |
    And the invoice identified by longInv is completed
    And the invoice identified by emptyInv is completed
    And metasfresh contains Pay Selection
      | Identifier   | C_BP_BankAccount_ID | PaySelectionTrxType | PayDate    | OPT.AD_Org_ID |
      | paySelection | orgAcct             | DD                  | 2026-11-02 | sellerOrg     |
    And "Create from..." is invoked for pay selection paySelection, using following parameters:
      | MatchRequirement | C_BPartner_ID | PayDate    |
      | CDD              | longDebtor    | 2026-11-02 |
    And "Create from..." is invoked for pay selection paySelection, using following parameters:
      | MatchRequirement | C_BPartner_ID | PayDate    |
      | CDD              | emptyDebtor   | 2026-11-02 |
    And the pay selection identified by paySelection is completed
    And the Pay selection identified by paySelection has exactly the following lines
      | C_Invoice_ID | OpenAmt | PayAmt | C_BP_BankAccount_ID |
      | longInv      | 100.00  | 100.00 | longAcct            |
      | emptyInv     | 20.00   | 20.00  | emptyAcct           |

    When the SEPA export process is run for pay selection "paySelection"

    Then the SEPA export result is a single XML file
    And the SEPA direct debit file for scheme CORE contains exactly the transactions:
      | Dbtr.Nm                                                                | DbtrAcct.IBAN          | InstdAmt | MndtId         | DtOfSgntr  | SeqTp | Ustrd                                                                                                                                        |
      | Müller & Söhne   Großhandel XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX | DE89370400440532013000 | 100.00   | MREF-AE oe/001 | 2014-03-11 | RCUR  | Rechnung  Nr. 1  YYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYY |
      | <SPACE>                                                                | DE02120300000000202051 | 20.00    | MREF-TC16-002  | 2014-03-11 | RCUR  | Invoice for disallowed characters                                                                                                            |

  @from:cucumber
  @Id:S32708_TC17
  Scenario: TC17 invoice without description: no Ustrd
    Given metasfresh contains C_BPartners:
      | Identifier | Name            | Value      | IsCustomer | M_PricingSystem_ID.Identifier | OPT.PaymentRule |
      | b2bDebtor  | SEPA Debtor B2B | b2b_@Date@ | Y          | pricingSystem                 | D               |
    And metasfresh contains C_Bank:
      | C_Bank_ID.Identifier | Name                 | RoutingNo | SwiftCode   |
      | b2bBank              | SEPA Debtor Bank B2B | 20000000  | TESTDEB2XXX |
    And metasfresh contains C_BP_BankAccount
      | Identifier | C_BPartner_ID | C_Currency_ID | IBAN                   | C_Bank_ID.Identifier |
      | b2bAcct    | b2bDebtor     | EUR           | DE89370400440532013000 | b2bBank              |
    And metasfresh contains C_BP_BankAccount_DirectDebitMandate:
      | C_BP_BankAccount_DirectDebitMandate_ID | C_BP_BankAccount_ID | MandateType | MandateReference | MandateDate | IsRecurring | DateLastUsed | AD_Org_ID |
      | b2bMandate                             | b2bAcct             | B2B         | MREF-TC17-001    | 2013-09-23  | Y           | 2025-09-04   | sellerOrg |
    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | IsSOTrx | DateInvoiced | C_Currency_ID | C_PaymentTerm_ID | OPT.PaymentRule | AD_Org_ID |
      | invoice    | b2bDebtor     | Y       | 2026-10-28   | EUR           | pt_sepa          | D               | sellerOrg |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced | Price  | C_Tax_ID |
      | invoiceL1  | invoice      | product      | 1 PCE       | 100.00 | zeroTax  |
    And the invoice identified by invoice is completed
    And metasfresh contains Pay Selection
      | Identifier   | C_BP_BankAccount_ID | PaySelectionTrxType | PayDate    | OPT.AD_Org_ID |
      | paySelection | orgAcct             | DD                  | 2026-11-02 | sellerOrg     |
    And "Create from..." is invoked for pay selection paySelection, using following parameters:
      | MatchRequirement | C_BPartner_ID | PayDate    |
      | CDD              | b2bDebtor     | 2026-11-02 |
    And the pay selection identified by paySelection is completed
    And the Pay selection identified by paySelection has exactly the following lines
      | C_Invoice_ID | OpenAmt | PayAmt | C_BP_BankAccount_ID |
      | invoice      | 100.00  | 100.00 | b2bAcct             |

    When the SEPA export process is run for pay selection "paySelection"

    Then the SEPA export result is a single XML file
    And the SEPA direct debit file for scheme B2B contains exactly the transactions:
      | Dbtr.Nm         | DbtrAcct.IBAN          | InstdAmt | MndtId        | DtOfSgntr  | SeqTp | Ustrd |
      | SEPA Debtor B2B | DE89370400440532013000 | 100.00   | MREF-TC17-001 | 2013-09-23 | RCUR  | -     |

  @from:cucumber
  @Id:S32708_TC20
  Scenario: TC20 mandate last used after the collection date: still exported with its own reference, DateLastUsed is not moved back
    Given metasfresh contains C_BPartners:
      | Identifier | Name            | Value      | IsCustomer | M_PricingSystem_ID.Identifier | OPT.PaymentRule |
      | b2bDebtor  | SEPA Debtor B2B | b2b_@Date@ | Y          | pricingSystem                 | D               |
    And metasfresh contains C_Bank:
      | C_Bank_ID.Identifier | Name                 | RoutingNo | SwiftCode   |
      | b2bBank              | SEPA Debtor Bank B2B | 20000000  | TESTDEB2XXX |
    And metasfresh contains C_BP_BankAccount
      | Identifier | C_BPartner_ID | C_Currency_ID | IBAN                   | C_Bank_ID.Identifier |
      | b2bAcct    | b2bDebtor     | EUR           | DE89370400440532013000 | b2bBank              |
    And metasfresh contains C_BP_BankAccount_DirectDebitMandate:
      | C_BP_BankAccount_DirectDebitMandate_ID | C_BP_BankAccount_ID | MandateType | MandateReference | MandateDate | IsRecurring | DateLastUsed | AD_Org_ID |
      | b2bMandate                             | b2bAcct             | B2B         | MREF-LATER-001   | 2013-09-23  | Y           | 2026-12-01   | sellerOrg |
    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | IsSOTrx | DateInvoiced | C_Currency_ID | C_PaymentTerm_ID | OPT.PaymentRule | OPT.Description | AD_Org_ID |
      | invoice    | b2bDebtor     | Y       | 2026-10-28   | EUR           | pt_sepa          | D               | Invoice B2B 1   | sellerOrg |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced | Price  | C_Tax_ID |
      | invoiceL1  | invoice      | product      | 1 PCE       | 100.00 | zeroTax  |
    And the invoice identified by invoice is completed
    And metasfresh contains Pay Selection
      | Identifier   | C_BP_BankAccount_ID | PaySelectionTrxType | PayDate    | OPT.AD_Org_ID |
      | paySelection | orgAcct             | DD                  | 2026-11-02 | sellerOrg     |
    And "Create from..." is invoked for pay selection paySelection, using following parameters:
      | MatchRequirement | C_BPartner_ID | PayDate    |
      | CDD              | b2bDebtor     | 2026-11-02 |
    And the pay selection identified by paySelection is completed
    And the Pay selection identified by paySelection has exactly the following lines
      | C_Invoice_ID | OpenAmt | PayAmt | C_BP_BankAccount_ID |
      | invoice      | 100.00  | 100.00 | b2bAcct             |

    When the SEPA export process is run for pay selection "paySelection"

    Then the SEPA export result is a single XML file
    And the SEPA direct debit file for scheme B2B contains exactly the transactions:
      | Dbtr.Nm         | DbtrAcct.IBAN          | InstdAmt | MndtId         | DtOfSgntr  | SeqTp | Ustrd         |
      | SEPA Debtor B2B | DE89370400440532013000 | 100.00   | MREF-LATER-001 | 2013-09-23 | RCUR  | Invoice B2B 1 |
    And validate C_BP_BankAccount_DirectDebitMandate:
      | C_BP_BankAccount_DirectDebitMandate_ID | DateLastUsed |
      | b2bMandate                             | 2026-12-01   |
