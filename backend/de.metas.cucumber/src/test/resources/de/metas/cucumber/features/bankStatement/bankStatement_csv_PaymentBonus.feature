@from:cucumber
@allure.label.epic:E0170_Contract_Management
@allure.label.feature:F00970_Flatrate_Contract
@ghActions:run_on_executor3
Feature: Payment bonus on the payment that is created from a CSV bank statement line
## - The CSV bank statement import creates a bank statement line for each CSV line; the payment bonus paths build on it.
## - Each scenario has its own customer (Background), but the invoice has a fixed document number, because the CSV references it:
##   a run that fails before the invoice is paid leaves an unpaid invoice with that number, which makes the next import ambiguous until that invoice is renamed.
## - The CSV Name column is concatenated into SQL by BankStatementImportTableSqlUpdater, so it must not contain an apostrophe.
## - The organization's EUR account is the one shared with the camt features (found by its IBAN): a second active EUR account would break payment_bonus_on_payment.feature.

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And documents are accounted immediately

    And metasfresh contains C_TaxCategory
      | Identifier   |
      | lowTaxCateg  |
      | highTaxCateg |
    And metasfresh contains C_Tax
      | Identifier | C_TaxCategory_ID | Rate | C_Country_ID.CountryCode | To_Country_ID.CountryCode |
      | lowTax     | lowTaxCateg      | 7    | DE                       | DE                        |
      | highTax    | highTaxCateg     | 19   | DE                       | DE                        |

    And metasfresh contains M_Product_Categories:
      | Identifier    |
      | goodsCategory |
      | packCategory  |
      | bonusCategory |
    And metasfresh contains M_Product_Category:
      | Identifier       | Name                | Value             | OPT.M_Product_Category_Parent_ID.Identifier |
      | subGoodsCategory | deduction sub-goods | deductionSubGoods | goodsCategory                               |

    # the bonus products post on their own revenue account
    # (applied before the products exist: a product copies its category's accounts when it is created)
    And metasfresh contains C_ElementValues:
      | Identifier       | Value |
      | bonusRevenueAcct | 4750  |
    And metasfresh contains M_Product_Category_Acct overrides:
      | M_Product_Category_ID | OPT.P_Revenue_Acct |
      | bonusCategory         | bonusRevenueAcct   |

    And metasfresh contains M_Products:
      | Identifier     | OPT.M_Product_Category_ID.Identifier | OPT.IsStocked |
      | goodsProduct   | subGoodsCategory                     | false         |
      | crateProduct   | packCategory                         | false         |
      | bonusWare      | bonusCategory                        | false         |
      | bonusPack      | bonusCategory                        | false         |
      | serviceProduct | bonusCategory                        | false         |

    And metasfresh contains M_PricingSystems
      | Identifier  |
      | deductionPS |
    And metasfresh contains M_PriceLists
      | Identifier  | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name        | SOTrx | IsTaxIncluded | PricePrecision |
      | deductionPL | deductionPS                   | DE                        | EUR                 | deductionPL | true  | false         | 2              |
      | servicePL   | deductionPS                   | DE                        | EUR                 | servicePL   | false | true          | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier   | M_PriceList_ID.Identifier | Name         | ValidFrom  |
      | deductionPLV | deductionPL               | deductionPLV | 2026-01-01 |
      | servicePLV   | servicePL                 | servicePLV   | 2026-01-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID |
      | pp_goods   | deductionPLV                      | goodsProduct            | 10       | PCE               | lowTaxCateg      |
      | pp_crate   | deductionPLV                      | crateProduct            | 10       | PCE               | highTaxCateg     |
      | pp_bWare   | deductionPLV                      | bonusWare               | 1        | PCE               | lowTaxCateg      |
      | pp_bPack   | deductionPLV                      | bonusPack               | 1        | PCE               | highTaxCateg     |
      | pp_service | servicePLV                        | serviceProduct          | 0        | PCE               | highTaxCateg     |

    And metasfresh contains C_BPartners:
      | Identifier     | OPT.IsCustomer | OPT.IsVendor | M_PricingSystem_ID.Identifier | OPT.InvoiceRule | REST.Context.Name |
      | customerBP     | Y              | N            | deductionPS                   | I               | customerName      |
      | serviceCompany | N              | Y            | deductionPS                   |                 |                   |
      # a customer without a refund contract
      | plainBP        | Y              | N            | deductionPS                   | I               |                   |
    # the organization's EUR account that the statement is imported for: found by its IBAN
    And metasfresh contains organization bank accounts
      | Identifier      | C_Currency_ID | IBAN                   |
      | org_EUR_account | EUR           | DE89370400440532013000 |

    # the import format of the CSV bank statement
    And AD_ImpFormat "bankStatementCsv" for table "I_BankStatement" with columns:
      | ColumnName         | DataType | OPT.DataFormat |
      | StatementDate      | D        | yyyy-MM-dd     |
      | StatementLineDate  | D        | yyyy-MM-dd     |
      | ValutaDate         | D        | yyyy-MM-dd     |
      | StmtAmt            | N        |                |
      | TrxAmt             | N        |                |
      | IBAN               | S        |                |
      | IBAN_To            | S        |                |
      | Bill_BPartner_Name | S        |                |
      | C_Invoice_ID       | N        |                |
      | InvoiceDocumentNo  | S        |                |
      | Memo               | S        |                |
      | ISO_Code           | S        |                |
      | Name               | S        |                |
    And C_DataImport config "bankStatementCsv" using AD_ImpFormat "bankStatementCsv"

    And metasfresh contains C_InvoiceSchedules:
      | Identifier      | InvoiceDay | InvoiceDistance |
      | monthlySchedule | 31         | 1               |

    And metasfresh contains C_PaymentTerm
      | Identifier | NetDays | Discount | DiscountDays |
      | skonto2    | 30      | 2        | 10           |

    # the refund contract of the customer: 2.6 % of the goods, deducted at payment
    And metasfresh contains C_Flatrate_Conditions:
      | Identifier     | Type_Conditions |
      | conditionsWare | Refund          |
    And metasfresh contains C_Flatrate_RefundConfigs:
      | Identifier | C_Flatrate_Conditions_ID | C_InvoiceSchedule_ID | RefundPercent | M_Product_Category_ID | Bonus_Product_ID | IsDeductedAtPayment |
      | configWare | conditionsWare           | monthlySchedule      | 2.6           | goodsCategory         | bonusWare        | Y                   |
    And metasfresh contains C_Flatrate_Terms:
      | Identifier | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | StartDate  | EndDate    |
      | termWare   | conditionsWare                      | customerBP                  | 2026-07-01 | 2026-12-31 |


  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F00970_Flatrate_Contract
  @Id:bsCsvPaymentBonus_TC0
  Scenario: CSV bank statement import — a line that pays the full invoice amount is imported, with the invoice referenced by its document number or by its ID
    # the sales invoice: 10 x 10.00 goods (7 %) + 5 x 10.00 crates (19 %), GrandTotal = 166.50
    # the statement name is the customer's (new per run), so that every run creates its own statement and not a new line in the statement of the last run
    # the CSV columns: StatementDate, StatementLineDate, ValutaDate, StmtAmt, TrxAmt, IBAN, IBAN_To, Bill_BPartner_Name, C_Invoice_ID, InvoiceDocumentNo, Memo, ISO_Code, Name
    Given metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | C_DocTypeTarget_ID.Name | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code | OPT.DocumentNo      | REST.Context |
      | invoice    | customerBP    | Ausgangsrechnung        | 2026-07-15   | Spot                     | true    | EUR                 | bsCsvPaymentBonus_1 | invoice      |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced |
      | goodsLine  | invoice      | goodsProduct | 10 PCE      |
      | crateLine  | invoice      | crateProduct | 5 PCE       |
    And the invoice identified by invoice is completed

    # the invoice is referenced by its document number
    When the following CSV with REST context variables is imported via data import config "bankStatementCsv":
    """
    2026-07-20,2026-07-20,2026-07-20,166.50,166.50,DE89370400440532013000,CH4189144993834976921,@customerName@,,bsCsvPaymentBonus_1,,EUR,@customerName@
    """
    And load C_BankStatement created by the import for bank account "org_EUR_account"
      | C_BankStatement_ID.Identifier | Name           |
      | bs_1                          | @customerName@ |
    And load C_BankStatementLine
      | C_BankStatementLine_ID.Identifier | C_BankStatement_ID.Identifier | Line |
      | bsl_1                             | bs_1                          | 10   |
    Then validate C_BankStatementLine
      | C_BankStatementLine_ID.Identifier | OPT.TrxAmt | OPT.StmtAmt | OPT.C_BPartner_ID.Identifier | OPT.Processed |
      | bsl_1                             | 166.50     | 166.50      | customerBP                   | false         |

    # the invoice is referenced by its ID (the placeholder is replaced by the ID of the invoice); another statement date, so that the line goes to its own statement
    When the following CSV with REST context variables is imported via data import config "bankStatementCsv":
    """
    2026-07-21,2026-07-21,2026-07-21,166.50,166.50,DE89370400440532013000,CH4189144993834976921,@customerName@,@invoice@,,,EUR,@customerName@
    """
    And load C_BankStatement created by the import for bank account "org_EUR_account"
      | C_BankStatement_ID.Identifier | Name           |
      | bs_2                          | @customerName@ |
    And load C_BankStatementLine
      | C_BankStatementLine_ID.Identifier | C_BankStatement_ID.Identifier | Line |
      | bsl_2                             | bs_2                          | 10   |
    Then validate C_BankStatementLine
      | C_BankStatementLine_ID.Identifier | OPT.TrxAmt | OPT.StmtAmt | OPT.C_BPartner_ID.Identifier | OPT.Processed |
      | bsl_2                             | 166.50     | 166.50      | customerBP                   | false         |
    # cleanup: the invoice stays unpaid, which would make the next import of this fixed document number ambiguous
    And the invoice identified by invoice is reversed
