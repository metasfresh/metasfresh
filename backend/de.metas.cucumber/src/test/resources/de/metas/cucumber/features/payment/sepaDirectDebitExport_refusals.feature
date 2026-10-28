@from:cucumber
@allure.label.epic:E0130_Payment
@allure.label.feature:F00982_Payment
@allure.label.feature:F00977_SEPA_Payment_Export
@allure.label.feature:F00978_SEPA_Direct_Debit_Lastschrift
@ghActions:run_on_executor4
Feature: SEPA direct debit export refusals (mandate check, creditor identifier)
## The export of a direct debit pay selection is refused as a whole when a line has no valid mandate (missing, expired,
## one-off already used, one-off on several lines) or when the org bank account has no SEPA creditor identifier.
## A refused export creates no SEPA_Export and does not touch any mandate.
##
## Expected texts are the German AD_Message texts (cucumber runs in de_DE); dates in the messages are dd.MM.yyyy.
## The partner is asserted by the prefix of its (run-unique) Value; the 36 months rule is checked against the last use
## (or, never used, the signature date) of the mandate, the reference date is the one printed in the message.
##
## Data notes:
## - Every scenario runs in its own organization (created in the Background) with its own org BP, which is the creditor; no seed data is changed.
## - Debtor BPs get a run-unique Value (@Date@); mandates belong to the scenario's own organization, so a re-run does not collide.
## - Cells are trimmed by Cucumber.

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
  @Id:S32708_TC5
  Scenario: TC5 debtor bank account without a mandate: export refused, no SEPA_Export
    Given metasfresh contains C_BPartners:
      | Identifier | Name           | Value         | IsCustomer | M_PricingSystem_ID.Identifier | OPT.PaymentRule |
      | debtor     | SEPA Debtor T5 | dd_tc5_@Date@ | Y          | pricingSystem                 | D               |
    And metasfresh contains C_Bank:
      | C_Bank_ID.Identifier | Name           | RoutingNo | SwiftCode   |
      | debtorBank           | SEPA Debtor T5 | 20000000  | TESTDEB2XXX |
    And metasfresh contains C_BP_BankAccount
      | Identifier | C_BPartner_ID | C_Currency_ID | IBAN                   | C_Bank_ID.Identifier |
      | debtorAcct | debtor        | EUR           | DE89370400440532013000 | debtorBank           |
    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | IsSOTrx | DateInvoiced | C_Currency_ID | C_PaymentTerm_ID | OPT.PaymentRule | AD_Org_ID |
      | invoice    | debtor        | Y       | 2026-10-28   | EUR           | pt_sepa          | D               | sellerOrg |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced | Price  | C_Tax_ID |
      | invoiceL1  | invoice      | product      | 1 PCE       | 100.00 | zeroTax  |
    And the invoice identified by invoice is completed
    And metasfresh contains Pay Selection
      | Identifier   | C_BP_BankAccount_ID | PaySelectionTrxType | PayDate    | OPT.AD_Org_ID |
      | paySelection | orgAcct             | DD                  | 2026-11-02 | sellerOrg     |
    And "Create from..." is invoked for pay selection paySelection, using following parameters:
      | MatchRequirement | C_BPartner_ID | PayDate    |
      | CDD              | debtor        | 2026-11-02 |
    And the pay selection identified by paySelection is completed
    And the Pay selection identified by paySelection has exactly the following lines
      | C_Invoice_ID | OpenAmt | PayAmt | C_BP_BankAccount_ID |
      | invoice      | 100.00  | 100.00 | debtorAcct          |

    When the SEPA export process is run for pay selection "paySelection"

    Then the SEPA export process fails with error code "DD_MANDATE_CHECK_FAILED" and a message containing:
      | SEPA-Lastschrift-Export nicht erstellt  |
      | dd_tc5_                                 |
      | DE89370400440532013000                  |
      | kein aktives Lastschriftmandat          |
      | unterschriebene Mandat des Zahlers      |
      | Zeile aus der Zahlungsauswahl entfernen |
    And the pay selection "paySelection" has no SEPA_Export

  @from:cucumber
  @Id:S32708_TC18
  Scenario: TC18 org bank account without SEPA creditor identifier: export refused, no SEPA_Export
    Given metasfresh contains C_BP_BankAccount
      | Identifier   | C_BPartner_ID | C_Currency_ID | IBAN                   | C_Bank_ID.Identifier |
      | orgAcctNoCid | creditorBP    | EUR           | DE02100100100006820101 | creditorBank         |
    And metasfresh contains C_BPartners:
      | Identifier | Name            | Value          | IsCustomer | M_PricingSystem_ID.Identifier | OPT.PaymentRule |
      | debtor     | SEPA Debtor T18 | dd_tc18_@Date@ | Y          | pricingSystem                 | D               |
    And metasfresh contains C_Bank:
      | C_Bank_ID.Identifier | Name            | RoutingNo | SwiftCode   |
      | debtorBank           | SEPA Debtor T18 | 20000000  | TESTDEB2XXX |
    And metasfresh contains C_BP_BankAccount
      | Identifier | C_BPartner_ID | C_Currency_ID | IBAN                   | C_Bank_ID.Identifier |
      | debtorAcct | debtor        | EUR           | DE89370400440532013000 | debtorBank           |
    And metasfresh contains C_BP_BankAccount_DirectDebitMandate:
      | C_BP_BankAccount_DirectDebitMandate_ID | C_BP_BankAccount_ID | MandateType | MandateReference | MandateDate | IsRecurring | DateLastUsed | AD_Org_ID |
      | mandate                                | debtorAcct          | CORE        | MREF-TC18-001    | 2024-01-10  | Y           | 2026-01-15   | sellerOrg |
    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | IsSOTrx | DateInvoiced | C_Currency_ID | C_PaymentTerm_ID | OPT.PaymentRule | AD_Org_ID |
      | invoice    | debtor        | Y       | 2026-10-28   | EUR           | pt_sepa          | D               | sellerOrg |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced | Price  | C_Tax_ID |
      | invoiceL1  | invoice      | product      | 1 PCE       | 100.00 | zeroTax  |
    And the invoice identified by invoice is completed
    And metasfresh contains Pay Selection
      | Identifier   | C_BP_BankAccount_ID | PaySelectionTrxType | PayDate    | OPT.AD_Org_ID |
      | paySelection | orgAcctNoCid        | DD                  | 2026-11-02 | sellerOrg     |
    And "Create from..." is invoked for pay selection paySelection, using following parameters:
      | MatchRequirement | C_BPartner_ID | PayDate    |
      | CDD              | debtor        | 2026-11-02 |
    And the pay selection identified by paySelection is completed

    When the SEPA export process is run for pay selection "paySelection"

    Then the SEPA export process fails with a message containing:
      | SEPA Gläubiger-Identifikationsnummer |
    And the pay selection "paySelection" has no SEPA_Export

  @from:cucumber
  @Id:S32708_TC19_refused
  Scenario Outline: TC19 mandate older than 36 months before the collection date: refused (last use <DateLastUsed>, signed <MandateDate>, collection <PayDate>)
    Given metasfresh contains C_BPartners:
      | Identifier | Name            | Value          | IsCustomer | M_PricingSystem_ID.Identifier | OPT.PaymentRule |
      | debtor     | SEPA Debtor T19 | dd_tc19_@Date@ | Y          | pricingSystem                 | D               |
    And metasfresh contains C_Bank:
      | C_Bank_ID.Identifier | Name            | RoutingNo | SwiftCode   |
      | debtorBank           | SEPA Debtor T19 | 20000000  | TESTDEB2XXX |
    And metasfresh contains C_BP_BankAccount
      | Identifier | C_BPartner_ID | C_Currency_ID | IBAN                   | C_Bank_ID.Identifier |
      | debtorAcct | debtor        | EUR           | DE89370400440532013000 | debtorBank           |
    And metasfresh contains C_BP_BankAccount_DirectDebitMandate:
      | C_BP_BankAccount_DirectDebitMandate_ID | C_BP_BankAccount_ID | MandateType | MandateReference | MandateDate   | IsRecurring | DateLastUsed   | AD_Org_ID |
      | mandate                                | debtorAcct          | CORE        | MREF-TC19-001    | <MandateDate> | Y           | <DateLastUsed> | sellerOrg |
    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | IsSOTrx | DateInvoiced | C_Currency_ID | C_PaymentTerm_ID | OPT.PaymentRule | AD_Org_ID |
      | invoice    | debtor        | Y       | 2026-10-28   | EUR           | pt_sepa          | D               | sellerOrg |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced | Price  | C_Tax_ID |
      | invoiceL1  | invoice      | product      | 1 PCE       | 100.00 | zeroTax  |
    And the invoice identified by invoice is completed
    And metasfresh contains Pay Selection
      | Identifier   | C_BP_BankAccount_ID | PaySelectionTrxType | PayDate   | OPT.AD_Org_ID |
      | paySelection | orgAcct             | DD                  | <PayDate> | sellerOrg     |
    And "Create from..." is invoked for pay selection paySelection, using following parameters:
      | MatchRequirement | C_BPartner_ID | PayDate   |
      | CDD              | debtor        | <PayDate> |
    And the pay selection identified by paySelection is completed

    When the SEPA export process is run for pay selection "paySelection"

    Then the SEPA export process fails with error code "DD_MANDATE_CHECK_FAILED" and a message containing:
      | dd_tc19_                                |
      | MREF-TC19-001                           |
      | <RefDate>                               |
      | abgelaufen                              |
      | Letzte Nutzung                          |
      | korrigieren                             |
      | alte Mandat deaktivieren                |
      | Zeile aus der Zahlungsauswahl entfernen |
    And the pay selection "paySelection" has no SEPA_Export

    Examples:
      | DateLastUsed     | MandateDate | PayDate    | RefDate    |
      | 2023-08-31 23:59 | 2013-01-01  | 2026-09-01 | 31.08.2023 |
      | 2024-02-29       | 2013-01-01  | 2027-03-01 | 29.02.2024 |
      | -                | 2023-09-30  | 2026-10-31 | 30.09.2023 |

  @from:cucumber
  @Id:S32708_TC19_exported
  Scenario Outline: TC19 mandate exactly 36 months before the collection date: still valid, exported (last use <DateLastUsed>, collection <PayDate>)
    Given metasfresh contains C_BPartners:
      | Identifier | Name            | Value          | IsCustomer | M_PricingSystem_ID.Identifier | OPT.PaymentRule |
      | debtor     | SEPA Debtor T19 | dd_tc19_@Date@ | Y          | pricingSystem                 | D               |
    And metasfresh contains C_Bank:
      | C_Bank_ID.Identifier | Name            | RoutingNo | SwiftCode   |
      | debtorBank           | SEPA Debtor T19 | 20000000  | TESTDEB2XXX |
    And metasfresh contains C_BP_BankAccount
      | Identifier | C_BPartner_ID | C_Currency_ID | IBAN                   | C_Bank_ID.Identifier |
      | debtorAcct | debtor        | EUR           | DE89370400440532013000 | debtorBank           |
    And metasfresh contains C_BP_BankAccount_DirectDebitMandate:
      | C_BP_BankAccount_DirectDebitMandate_ID | C_BP_BankAccount_ID | MandateType | MandateReference | MandateDate | IsRecurring | DateLastUsed   | AD_Org_ID |
      | mandate                                | debtorAcct          | CORE        | MREF-TC19-002    | 2013-01-01  | Y           | <DateLastUsed> | sellerOrg |
    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | IsSOTrx | DateInvoiced | C_Currency_ID | C_PaymentTerm_ID | OPT.PaymentRule | AD_Org_ID |
      | invoice    | debtor        | Y       | 2026-10-28   | EUR           | pt_sepa          | D               | sellerOrg |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced | Price  | C_Tax_ID |
      | invoiceL1  | invoice      | product      | 1 PCE       | 100.00 | zeroTax  |
    And the invoice identified by invoice is completed
    And metasfresh contains Pay Selection
      | Identifier   | C_BP_BankAccount_ID | PaySelectionTrxType | PayDate   | OPT.AD_Org_ID |
      | paySelection | orgAcct             | DD                  | <PayDate> | sellerOrg     |
    And "Create from..." is invoked for pay selection paySelection, using following parameters:
      | MatchRequirement | C_BPartner_ID | PayDate   |
      | CDD              | debtor        | <PayDate> |
    And the pay selection identified by paySelection is completed

    When the SEPA export process is run for pay selection "paySelection"

    Then the SEPA export result is a single XML file

    Examples:
      | DateLastUsed     | PayDate    |
      | 2023-08-31 00:00 | 2026-08-31 |
      | 2024-02-29       | 2027-02-28 |

  @from:cucumber
  @Id:S32708_TC21a
  Scenario: TC21a valid B2B line and a CORE line without mandate: whole export refused, B2B mandate DateLastUsed unchanged
    Given metasfresh contains C_BPartners:
      | Identifier | Name             | Value             | IsCustomer | M_PricingSystem_ID.Identifier | OPT.PaymentRule |
      | b2bDebtor  | SEPA Debtor B2B  | dd_tc21a_b_@Date@ | Y          | pricingSystem                 | D               |
      | coreDebtor | SEPA Debtor CORE | dd_tc21a_c_@Date@ | Y          | pricingSystem                 | D               |
    And metasfresh contains C_Bank:
      | C_Bank_ID.Identifier | Name            | RoutingNo | SwiftCode   |
      | debtorBank           | SEPA Debtor T21 | 20000000  | TESTDEB2XXX |
    And metasfresh contains C_BP_BankAccount
      | Identifier | C_BPartner_ID | C_Currency_ID | IBAN                   | C_Bank_ID.Identifier |
      | b2bAcct    | b2bDebtor     | EUR           | DE89370400440532013000 | debtorBank           |
      | coreAcct   | coreDebtor    | EUR           | DE02120300000000202051 | debtorBank           |
    And metasfresh contains C_BP_BankAccount_DirectDebitMandate:
      | C_BP_BankAccount_DirectDebitMandate_ID | C_BP_BankAccount_ID | MandateType | MandateReference | MandateDate | IsRecurring | DateLastUsed | AD_Org_ID |
      | b2bMandate                             | b2bAcct             | B2B         | MREF-TC21A-B2B   | 2013-09-23  | Y           | 2025-09-04   | sellerOrg |
    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | IsSOTrx | DateInvoiced | C_Currency_ID | C_PaymentTerm_ID | OPT.PaymentRule | AD_Org_ID |
      | b2bInv     | b2bDebtor     | Y       | 2026-10-28   | EUR           | pt_sepa          | D               | sellerOrg |
      | coreInv    | coreDebtor    | Y       | 2026-10-28   | EUR           | pt_sepa          | D               | sellerOrg |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced | Price  | C_Tax_ID |
      | b2bInvL1   | b2bInv       | product      | 1 PCE       | 100.00 | zeroTax  |
      | coreInvL1  | coreInv      | product      | 1 PCE       | 50.00  | zeroTax  |
    And the invoice identified by b2bInv is completed
    And the invoice identified by coreInv is completed
    And metasfresh contains Pay Selection
      | Identifier   | C_BP_BankAccount_ID | PaySelectionTrxType | PayDate    | OPT.AD_Org_ID |
      | paySelection | orgAcct             | DD                  | 2026-11-02 | sellerOrg     |
    And "Create from..." is invoked for pay selection paySelection, using following parameters:
      | MatchRequirement | C_BPartner_ID | PayDate    |
      | CDD              | b2bDebtor     | 2026-11-02 |
    And "Create from..." is invoked for pay selection paySelection, using following parameters:
      | MatchRequirement | C_BPartner_ID | PayDate    |
      | CDD              | coreDebtor    | 2026-11-02 |
    And the pay selection identified by paySelection is completed
    And the Pay selection identified by paySelection has exactly the following lines
      | C_Invoice_ID | OpenAmt | PayAmt | C_BP_BankAccount_ID |
      | b2bInv       | 100.00  | 100.00 | b2bAcct             |
      | coreInv      | 50.00   | 50.00  | coreAcct            |

    When the SEPA export process is run for pay selection "paySelection"

    Then the SEPA export process fails with error code "DD_MANDATE_CHECK_FAILED" and a message containing:
      | dd_tc21a_c_                    |
      | DE02120300000000202051         |
      | kein aktives Lastschriftmandat |
    And the pay selection "paySelection" has no SEPA_Export
    And validate C_BP_BankAccount_DirectDebitMandate:
      | C_BP_BankAccount_DirectDebitMandate_ID | DateLastUsed |
      | b2bMandate                             | 2025-09-04   |

  @from:cucumber
  @Id:S32708_TC21b
  Scenario: TC21b valid B2B line and a CORE line with an expired mandate: whole export refused, B2B mandate DateLastUsed unchanged
    Given metasfresh contains C_BPartners:
      | Identifier | Name             | Value             | IsCustomer | M_PricingSystem_ID.Identifier | OPT.PaymentRule |
      | b2bDebtor  | SEPA Debtor B2B  | dd_tc21b_b_@Date@ | Y          | pricingSystem                 | D               |
      | coreDebtor | SEPA Debtor CORE | dd_tc21b_c_@Date@ | Y          | pricingSystem                 | D               |
    And metasfresh contains C_Bank:
      | C_Bank_ID.Identifier | Name            | RoutingNo | SwiftCode   |
      | debtorBank           | SEPA Debtor T21 | 20000000  | TESTDEB2XXX |
    And metasfresh contains C_BP_BankAccount
      | Identifier | C_BPartner_ID | C_Currency_ID | IBAN                   | C_Bank_ID.Identifier |
      | b2bAcct    | b2bDebtor     | EUR           | DE89370400440532013000 | debtorBank           |
      | coreAcct   | coreDebtor    | EUR           | DE02120300000000202051 | debtorBank           |
    And metasfresh contains C_BP_BankAccount_DirectDebitMandate:
      | C_BP_BankAccount_DirectDebitMandate_ID | C_BP_BankAccount_ID | MandateType | MandateReference | MandateDate | IsRecurring | DateLastUsed | AD_Org_ID |
      | b2bMandate                             | b2bAcct             | B2B         | MREF-TC21B-B2B   | 2013-09-23  | Y           | 2025-09-04   | sellerOrg |
      | coreMandate                            | coreAcct            | CORE        | MREF-TC21B-CORE  | 2014-03-11  | Y           | 2020-01-01   | sellerOrg |
    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | IsSOTrx | DateInvoiced | C_Currency_ID | C_PaymentTerm_ID | OPT.PaymentRule | AD_Org_ID |
      | b2bInv     | b2bDebtor     | Y       | 2026-10-28   | EUR           | pt_sepa          | D               | sellerOrg |
      | coreInv    | coreDebtor    | Y       | 2026-10-28   | EUR           | pt_sepa          | D               | sellerOrg |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced | Price  | C_Tax_ID |
      | b2bInvL1   | b2bInv       | product      | 1 PCE       | 100.00 | zeroTax  |
      | coreInvL1  | coreInv      | product      | 1 PCE       | 50.00  | zeroTax  |
    And the invoice identified by b2bInv is completed
    And the invoice identified by coreInv is completed
    And metasfresh contains Pay Selection
      | Identifier   | C_BP_BankAccount_ID | PaySelectionTrxType | PayDate    | OPT.AD_Org_ID |
      | paySelection | orgAcct             | DD                  | 2026-11-02 | sellerOrg     |
    And "Create from..." is invoked for pay selection paySelection, using following parameters:
      | MatchRequirement | C_BPartner_ID | PayDate    |
      | CDD              | b2bDebtor     | 2026-11-02 |
    And "Create from..." is invoked for pay selection paySelection, using following parameters:
      | MatchRequirement | C_BPartner_ID | PayDate    |
      | CDD              | coreDebtor    | 2026-11-02 |
    And the pay selection identified by paySelection is completed
    And the Pay selection identified by paySelection has exactly the following lines
      | C_Invoice_ID | OpenAmt | PayAmt | C_BP_BankAccount_ID |
      | b2bInv       | 100.00  | 100.00 | b2bAcct             |
      | coreInv      | 50.00   | 50.00  | coreAcct            |

    When the SEPA export process is run for pay selection "paySelection"

    Then the SEPA export process fails with error code "DD_MANDATE_CHECK_FAILED" and a message containing:
      | dd_tc21b_c_     |
      | MREF-TC21B-CORE |
      | 01.01.2020      |
      | abgelaufen      |
    And the pay selection "paySelection" has no SEPA_Export
    And validate C_BP_BankAccount_DirectDebitMandate:
      | C_BP_BankAccount_DirectDebitMandate_ID | DateLastUsed |
      | b2bMandate                             | 2025-09-04   |

  @from:cucumber
  @Id:S32708_TC23
  Scenario: TC23 three debtors with three different problems: one failure message names all three partners with their reasons
    Given metasfresh contains C_BPartners:
      | Identifier    | Name          | Value                  | IsCustomer | M_PricingSystem_ID.Identifier | OPT.PaymentRule |
      | oneOffDebtor  | SEPA Debtor 1 | dd_tc23_oneoff_@Date@  | Y          | pricingSystem                 | D               |
      | missingDebtor | SEPA Debtor 2 | dd_tc23_missing_@Date@ | Y          | pricingSystem                 | D               |
      | expiredDebtor | SEPA Debtor 3 | dd_tc23_expired_@Date@ | Y          | pricingSystem                 | D               |
    And metasfresh contains C_Bank:
      | C_Bank_ID.Identifier | Name            | RoutingNo | SwiftCode   |
      | debtorBank           | SEPA Debtor T23 | 20000000  | TESTDEB2XXX |
    And metasfresh contains C_BP_BankAccount
      | Identifier  | C_BPartner_ID | C_Currency_ID | IBAN                   | C_Bank_ID.Identifier |
      | oneOffAcct  | oneOffDebtor  | EUR           | DE89370400440532013000 | debtorBank           |
      | missingAcct | missingDebtor | EUR           | DE02120300000000202051 | debtorBank           |
      | expiredAcct | expiredDebtor | EUR           | DE02100100100006820101 | debtorBank           |
    And metasfresh contains C_BP_BankAccount_DirectDebitMandate:
      | C_BP_BankAccount_DirectDebitMandate_ID | C_BP_BankAccount_ID | MandateType | MandateReference | MandateDate | IsRecurring | DateLastUsed | AD_Org_ID |
      | oneOffMandate                          | oneOffAcct          | CORE        | MREF-TC23-ONEOFF | 2024-01-10  | N           | 2026-01-15   | sellerOrg |
      | expiredMandate                         | expiredAcct         | CORE        | MREF-TC23-EXPIRD | 2014-03-11  | Y           | 2020-01-01   | sellerOrg |
    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | IsSOTrx | DateInvoiced | C_Currency_ID | C_PaymentTerm_ID | OPT.PaymentRule | AD_Org_ID |
      | oneOffInv  | oneOffDebtor  | Y       | 2026-10-28   | EUR           | pt_sepa          | D               | sellerOrg |
      | missingInv | missingDebtor | Y       | 2026-10-28   | EUR           | pt_sepa          | D               | sellerOrg |
      | expiredInv | expiredDebtor | Y       | 2026-10-28   | EUR           | pt_sepa          | D               | sellerOrg |
    And metasfresh contains C_InvoiceLines
      | Identifier   | C_Invoice_ID | M_Product_ID | QtyInvoiced | Price | C_Tax_ID |
      | oneOffInvL1  | oneOffInv    | product      | 1 PCE       | 10.00 | zeroTax  |
      | missingInvL1 | missingInv   | product      | 1 PCE       | 20.00 | zeroTax  |
      | expiredInvL1 | expiredInv   | product      | 1 PCE       | 30.00 | zeroTax  |
    And the invoice identified by oneOffInv is completed
    And the invoice identified by missingInv is completed
    And the invoice identified by expiredInv is completed
    And metasfresh contains Pay Selection
      | Identifier   | C_BP_BankAccount_ID | PaySelectionTrxType | PayDate    | OPT.AD_Org_ID |
      | paySelection | orgAcct             | DD                  | 2026-11-02 | sellerOrg     |
    And "Create from..." is invoked for pay selection paySelection, using following parameters:
      | MatchRequirement | C_BPartner_ID | PayDate    |
      | CDD              | oneOffDebtor  | 2026-11-02 |
    And "Create from..." is invoked for pay selection paySelection, using following parameters:
      | MatchRequirement | C_BPartner_ID | PayDate    |
      | CDD              | missingDebtor | 2026-11-02 |
    And "Create from..." is invoked for pay selection paySelection, using following parameters:
      | MatchRequirement | C_BPartner_ID | PayDate    |
      | CDD              | expiredDebtor | 2026-11-02 |
    And the pay selection identified by paySelection is completed

    When the SEPA export process is run for pay selection "paySelection"

    Then the SEPA export process fails with error code "DD_MANDATE_CHECK_FAILED" and a message containing:
      | dd_tc23_oneoff_                    |
      | MREF-TC23-ONEOFF                   |
      | einmaliges Mandat, bereits genutzt |
      | dd_tc23_missing_                   |
      | DE02120300000000202051             |
      | kein aktives Lastschriftmandat     |
      | dd_tc23_expired_                   |
      | MREF-TC23-EXPIRD                   |
      | abgelaufen                         |
    And the pay selection "paySelection" has no SEPA_Export

  @from:cucumber
  @Id:S32708_OneOffTwoLines
  Scenario: one unused one-off mandate on two lines of one pay selection: export refused, no SEPA_Export
    Given metasfresh contains C_BPartners:
      | Identifier | Name            | Value             | IsCustomer | M_PricingSystem_ID.Identifier | OPT.PaymentRule |
      | debtor     | SEPA Debtor 1x2 | dd_oneoff2_@Date@ | Y          | pricingSystem                 | D               |
    And metasfresh contains C_Bank:
      | C_Bank_ID.Identifier | Name            | RoutingNo | SwiftCode   |
      | debtorBank           | SEPA Debtor 1x2 | 20000000  | TESTDEB2XXX |
    And metasfresh contains C_BP_BankAccount
      | Identifier | C_BPartner_ID | C_Currency_ID | IBAN                   | C_Bank_ID.Identifier |
      | debtorAcct | debtor        | EUR           | DE89370400440532013000 | debtorBank           |
    And metasfresh contains C_BP_BankAccount_DirectDebitMandate:
      | C_BP_BankAccount_DirectDebitMandate_ID | C_BP_BankAccount_ID | MandateType | MandateReference | MandateDate | IsRecurring | DateLastUsed | AD_Org_ID |
      | mandate                                | debtorAcct          | CORE        | MREF-ONEOFF2-001 | 2026-10-01  | N           | -            | sellerOrg |
    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | IsSOTrx | DateInvoiced | C_Currency_ID | C_PaymentTerm_ID | OPT.PaymentRule | AD_Org_ID |
      | invoice1   | debtor        | Y       | 2026-10-28   | EUR           | pt_sepa          | D               | sellerOrg |
      | invoice2   | debtor        | Y       | 2026-10-28   | EUR           | pt_sepa          | D               | sellerOrg |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced | Price | C_Tax_ID |
      | invoiceL1  | invoice1     | product      | 1 PCE       | 10.00 | zeroTax  |
      | invoiceL2  | invoice2     | product      | 1 PCE       | 20.00 | zeroTax  |
    And the invoice identified by invoice1 is completed
    And the invoice identified by invoice2 is completed
    And metasfresh contains Pay Selection
      | Identifier   | C_BP_BankAccount_ID | PaySelectionTrxType | PayDate    | OPT.AD_Org_ID |
      | paySelection | orgAcct             | DD                  | 2026-11-02 | sellerOrg     |
    And "Create from..." is invoked for pay selection paySelection, using following parameters:
      | MatchRequirement | C_BPartner_ID | PayDate    |
      | CDD              | debtor        | 2026-11-02 |
    And the pay selection identified by paySelection is completed

    When the SEPA export process is run for pay selection "paySelection"

    Then the SEPA export process fails with error code "DD_MANDATE_CHECK_FAILED" and a message containing:
      | dd_oneoff2_                                           |
      | MREF-ONEOFF2-001                                      |
      | einmaliges Mandat auf 2 Zeilen                        |
      | zusätzlichen Zeilen aus der Zahlungsauswahl entfernen |
    And the pay selection "paySelection" has no SEPA_Export
