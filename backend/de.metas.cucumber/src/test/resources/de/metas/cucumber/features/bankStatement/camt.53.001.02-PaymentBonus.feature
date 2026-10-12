@from:cucumber
@allure.label.epic:E0170_Contract_Management
@allure.label.feature:F00970_Flatrate_Contract
@ghActions:run_on_executor3
Feature: Payment bonus on the payment that is created from a bank statement line
## - The payment created for a bank statement line books Skonto and the payment bonus: the one the paid amount matches.
## - A statement line that references the invoice but pays 'open - bonus' books the bonus, so the invoice is paid completely.
## - Each scenario has its own customer (Background), but the invoice has a fixed document number, because the camt text references it:
##   a run that fails before the invoice is paid leaves an unpaid invoice with that number, which makes the next import ambiguous until that invoice is renamed.

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
      | Identifier     | OPT.IsCustomer | OPT.IsVendor | M_PricingSystem_ID.Identifier | OPT.InvoiceRule |
      | customerBP     | Y              | N            | deductionPS                   | I               |
      | serviceCompany | N              | Y            | deductionPS                   |                 |
      # a customer without a refund contract
      | plainBP        | Y              | N            | deductionPS                   | I               |
    # the organization's EUR account that the statement is imported for: found by its IBAN (an account of its own would be a second active EUR account for the features that run after this one)
    # the statement import needs a bank with an import format on it; as in the other camt features, a new C_Bank is created and put on the shared account
    And metasfresh contains organization bank accounts
      | Identifier      | C_Currency_ID | IBAN                   |
      | org_EUR_account | EUR           | DE89370400440532013000 |
    # the statement import needs a bank (with an import format) on the account
    And load C_DataImport:
      | C_DataImport_ID.Identifier | OPT.C_DataImport_ID |
      | di_camt53                  | 540009              |
    And metasfresh contains C_Bank:
      | C_Bank_ID.Identifier | Name                 | RoutingNo | SwiftCode   | C_DataImport_ID.Identifier |
      | b_paymentBonus       | bank_paymentBonus_bs | 2234568   | AAAAAAAA82A | di_camt53                  |
    And update C_BP_BankAccount:
      | C_BP_BankAccount_ID.Identifier | OPT.C_Currency.ISO_Code | OPT.C_Bank_ID.Identifier |
      | org_EUR_account                | EUR                     | b_paymentBonus           |

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
  @Id:bsPaymentBonus_TC1
  Scenario: Bank statement — a line that pays the invoice minus the payment bonus books the bonus
    # the sales invoice: 10 x 10.00 goods (7 %) + 5 x 10.00 crates (19 %), GrandTotal = 166.50, no Skonto
    # the bonus: 2.6 % of 100.00 goods = 2.60 + 7 % VAT = 2.78; the customer pays 166.50 - 2.78 = 163.72
    # the line references the invoice by its document number; the amount is not matched against the open amount
    Given metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | C_DocTypeTarget_ID.Name | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code | OPT.DocumentNo   |
      | invoice    | customerBP    | Ausgangsrechnung        | 2026-07-15   | Spot                     | true    | EUR                 | bsPaymentBonus_1 |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced |
      | goodsLine  | invoice      | goodsProduct | 10 PCE      |
      | crateLine  | invoice      | crateProduct | 5 PCE       |
    And the invoice identified by invoice is completed

    And bank statement is imported with identifiers bs_1, not matching invoice amounts
    """
    <?xml version="1.0" encoding="utf-8"?>
<Document xmlns="urn:iso:std:iso:20022:tech:xsd:camt.053.001.02">
	<BkToCstmrStmt>
		<GrpHdr>
			<MsgId>MsgIdBSPB0001</MsgId>
			<CreDtTm>2026-07-20T19:45:37.000Z</CreDtTm>
			<MsgPgntn>
				<PgNb>1</PgNb>
				<LastPgInd>true</LastPgInd>
			</MsgPgntn>
		</GrpHdr>
		<Stmt>
			<Id>StmtIdBSPB0001</Id>
			<ElctrncSeqNb>00001</ElctrncSeqNb>
			<CreDtTm>2026-07-20T19:45:37.000Z</CreDtTm>
			<Acct>
				<Id>
					<IBAN>DE89370400440532013000</IBAN>
				</Id>
				<Ccy>EUR</Ccy>
				<Svcr>
					<FinInstnId>
						<BIC>AAAAAAAA82A</BIC>
					</FinInstnId>
				</Svcr>
			</Acct>
			<Bal>
				<Tp>
					<CdOrPrtry>
						<Cd>PRCD</Cd>
					</CdOrPrtry>
				</Tp>
				<Amt Ccy="EUR">1000.00</Amt>
				<CdtDbtInd>CRDT</CdtDbtInd>
				<Dt>
					<Dt>2026-07-19</Dt>
				</Dt>
			</Bal>
			<Bal>
				<Tp>
					<CdOrPrtry>
						<Cd>CLBD</Cd>
					</CdOrPrtry>
				</Tp>
				<Amt Ccy="EUR">1163.72</Amt>
				<CdtDbtInd>CRDT</CdtDbtInd>
				<Dt>
					<Dt>2026-07-20</Dt>
				</Dt>
			</Bal>
			<Ntry>
				<NtryRef>00001</NtryRef>
				<Amt Ccy="EUR">163.72</Amt>
				<CdtDbtInd>CRDT</CdtDbtInd>
				<Sts>BOOK</Sts>
				<BookgDt>
					<Dt>2026-07-20</Dt>
				</BookgDt>
				<ValDt>
					<Dt>2026-07-20</Dt>
				</ValDt>
				<AcctSvcrRef>AAA-BSPB0001</AcctSvcrRef>
				<BkTxCd>
					<Prtry>
						<Cd>TRF</Cd>
						<Issr>SWIFT</Issr>
					</Prtry>
				</BkTxCd>
				<NtryDtls>
					<Btch>
						<NbOfTxs>1</NbOfTxs>
						<TtlAmt Ccy="EUR">163.72</TtlAmt>
						<CdtDbtInd>CRDT</CdtDbtInd>
					</Btch>
					<TxDtls>
						<Refs>
							<AcctSvcrRef>AAA-BSPB0001</AcctSvcrRef>
							<PmtInfId>PmtInfBSPB0001</PmtInfId>
						</Refs>
						<Amt Ccy="EUR">163.72</Amt>
						<CdtDbtInd>CRDT</CdtDbtInd>
						<AmtDtls>
							<InstdAmt>
								<Amt Ccy="EUR">163.72</Amt>
							</InstdAmt>
							<TxAmt>
								<Amt Ccy="EUR">163.72</Amt>
							</TxAmt>
						</AmtDtls>
						<RltdPties>
							<Dbtr>
								<Nm>Customer Test</Nm>
								<PstlAdr>
									<StrtNm>Street</StrtNm>
									<BldgNb>152</BldgNb>
									<PstCd>0000</PstCd>
									<TwnNm>TestCity</TwnNm>
									<Ctry>DE</Ctry>
								</PstlAdr>
							</Dbtr>
							<DbtrAcct>
								<Id>
									<IBAN>CH4189144993834976921</IBAN>
								</Id>
							</DbtrAcct>
							<Cdtr>
								<Nm>Test CO</Nm>
								<PstlAdr>
									<AdrLine>Street 123</AdrLine>
									<AdrLine>0000 CityTest</AdrLine>
								</PstlAdr>
							</Cdtr>
						</RltdPties>
						<RmtInf>
							<Ustrd>bsPaymentBonus_1</Ustrd>
						</RmtInf>
					</TxDtls>
				</NtryDtls>
			</Ntry>
		</Stmt>
	</BkToCstmrStmt>
</Document>
    """
    And load C_BankStatementLine
      | C_BankStatementLine_ID.Identifier | C_BankStatement_ID.Identifier | Line |
      | bsl_1                             | bs_1                          | 10   |
    And validate C_BankStatementLine
      | C_BankStatementLine_ID.Identifier | OPT.TrxAmt | OPT.StmtAmt | OPT.C_Invoice_ID.Identifier | OPT.C_BPartner_ID.Identifier | OPT.Processed |
      | bsl_1                             | 163.72     | 163.72      | invoice                     | customerBP                   | false         |
    And the C_BankStatement identified by bs_1 is completed
    And after not more than 60s, C_Payment is found
      | C_Payment_ID.Identifier | OPT.C_BankStatement_ID.Identifier | OPT.C_BankStatementLine_ID.Identifier |
      | payment                 | bs_1                              | bsl_1                                 |
    And validate payments
      | C_Payment_ID | PayAmt | PaymentBonusAmt | PaymentBonus_CreditMemo_ID | IsAllocated |
      | payment      | 163.72 | 2.78            | bonusCreditMemo            | Y           |
    And validate created invoices
      | C_Invoice_ID | IsPaid |
      | invoice      | true   |

  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F00970_Flatrate_Contract
  @Id:bsPaymentBonus_TC1b
  Scenario: Bank statement — a line that pays the full invoice amount books no payment bonus
    # the customer pays the full 166.50: the matcher never grants a bonus to a full payment
    Given metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | C_DocTypeTarget_ID.Name | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code | OPT.DocumentNo   |
      | invoice    | customerBP    | Ausgangsrechnung        | 2026-07-15   | Spot                     | true    | EUR                 | bsPaymentBonus_2 |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced |
      | goodsLine  | invoice      | goodsProduct | 10 PCE      |
      | crateLine  | invoice      | crateProduct | 5 PCE       |
    And the invoice identified by invoice is completed

    And bank statement is imported with identifiers bs_1, not matching invoice amounts
    """
    <?xml version="1.0" encoding="utf-8"?>
<Document xmlns="urn:iso:std:iso:20022:tech:xsd:camt.053.001.02">
	<BkToCstmrStmt>
		<GrpHdr>
			<MsgId>MsgIdBSPB0002</MsgId>
			<CreDtTm>2026-07-20T19:45:37.000Z</CreDtTm>
			<MsgPgntn>
				<PgNb>1</PgNb>
				<LastPgInd>true</LastPgInd>
			</MsgPgntn>
		</GrpHdr>
		<Stmt>
			<Id>StmtIdBSPB0002</Id>
			<ElctrncSeqNb>00001</ElctrncSeqNb>
			<CreDtTm>2026-07-20T19:45:37.000Z</CreDtTm>
			<Acct>
				<Id>
					<IBAN>DE89370400440532013000</IBAN>
				</Id>
				<Ccy>EUR</Ccy>
				<Svcr>
					<FinInstnId>
						<BIC>AAAAAAAA82A</BIC>
					</FinInstnId>
				</Svcr>
			</Acct>
			<Bal>
				<Tp>
					<CdOrPrtry>
						<Cd>PRCD</Cd>
					</CdOrPrtry>
				</Tp>
				<Amt Ccy="EUR">1000.00</Amt>
				<CdtDbtInd>CRDT</CdtDbtInd>
				<Dt>
					<Dt>2026-07-19</Dt>
				</Dt>
			</Bal>
			<Bal>
				<Tp>
					<CdOrPrtry>
						<Cd>CLBD</Cd>
					</CdOrPrtry>
				</Tp>
				<Amt Ccy="EUR">1166.50</Amt>
				<CdtDbtInd>CRDT</CdtDbtInd>
				<Dt>
					<Dt>2026-07-20</Dt>
				</Dt>
			</Bal>
			<Ntry>
				<NtryRef>00001</NtryRef>
				<Amt Ccy="EUR">166.50</Amt>
				<CdtDbtInd>CRDT</CdtDbtInd>
				<Sts>BOOK</Sts>
				<BookgDt>
					<Dt>2026-07-20</Dt>
				</BookgDt>
				<ValDt>
					<Dt>2026-07-20</Dt>
				</ValDt>
				<AcctSvcrRef>AAA-BSPB0002</AcctSvcrRef>
				<BkTxCd>
					<Prtry>
						<Cd>TRF</Cd>
						<Issr>SWIFT</Issr>
					</Prtry>
				</BkTxCd>
				<NtryDtls>
					<Btch>
						<NbOfTxs>1</NbOfTxs>
						<TtlAmt Ccy="EUR">166.50</TtlAmt>
						<CdtDbtInd>CRDT</CdtDbtInd>
					</Btch>
					<TxDtls>
						<Refs>
							<AcctSvcrRef>AAA-BSPB0002</AcctSvcrRef>
							<PmtInfId>PmtInfBSPB0002</PmtInfId>
						</Refs>
						<Amt Ccy="EUR">166.50</Amt>
						<CdtDbtInd>CRDT</CdtDbtInd>
						<AmtDtls>
							<InstdAmt>
								<Amt Ccy="EUR">166.50</Amt>
							</InstdAmt>
							<TxAmt>
								<Amt Ccy="EUR">166.50</Amt>
							</TxAmt>
						</AmtDtls>
						<RltdPties>
							<Dbtr>
								<Nm>Customer Test</Nm>
								<PstlAdr>
									<StrtNm>Street</StrtNm>
									<BldgNb>152</BldgNb>
									<PstCd>0000</PstCd>
									<TwnNm>TestCity</TwnNm>
									<Ctry>DE</Ctry>
								</PstlAdr>
							</Dbtr>
							<DbtrAcct>
								<Id>
									<IBAN>CH4189144993834976921</IBAN>
								</Id>
							</DbtrAcct>
							<Cdtr>
								<Nm>Test CO</Nm>
								<PstlAdr>
									<AdrLine>Street 123</AdrLine>
									<AdrLine>0000 CityTest</AdrLine>
								</PstlAdr>
							</Cdtr>
						</RltdPties>
						<RmtInf>
							<Ustrd>bsPaymentBonus_2</Ustrd>
						</RmtInf>
					</TxDtls>
				</NtryDtls>
			</Ntry>
		</Stmt>
	</BkToCstmrStmt>
</Document>
    """
    And load C_BankStatementLine
      | C_BankStatementLine_ID.Identifier | C_BankStatement_ID.Identifier | Line |
      | bsl_1                             | bs_1                          | 10   |
    And validate C_BankStatementLine
      | C_BankStatementLine_ID.Identifier | OPT.TrxAmt | OPT.StmtAmt | OPT.C_Invoice_ID.Identifier | OPT.C_BPartner_ID.Identifier | OPT.Processed |
      | bsl_1                             | 166.50     | 166.50      | invoice                     | customerBP                   | false         |
    And the C_BankStatement identified by bs_1 is completed
    And after not more than 60s, C_Payment is found
      | C_Payment_ID.Identifier | OPT.C_BankStatement_ID.Identifier | OPT.C_BankStatementLine_ID.Identifier |
      | payment                 | bs_1                              | bsl_1                                 |
    And validate payments
      | C_Payment_ID | PayAmt | PaymentBonusAmt | PaymentBonus_CreditMemo_ID | IsAllocated |
      | payment      | 166.50 | 0               | null                       | Y           |
    And there is no payment bonus credit memo for invoice invoice
    And validate created invoices
      | C_Invoice_ID | IsPaid |
      | invoice      | true   |
