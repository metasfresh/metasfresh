@from:cucumber
Feature: import bank statement in camt53 import format

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2020-02-28T13:30:13+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config de.metas.payment.esr.Enabled

    And metasfresh contains M_Products:
      | Identifier | Name                            |
      | p_1        | bankStatementProduct_12102022_1 |
    And metasfresh contains M_PricingSystems
      | Identifier | Name                           | Value                           | OPT.Description                       | OPT.IsActive |
      | ps_1       | pricing_system_name_12102022_1 | pricing_system_value_12102022_1 | pricing_system_description_12102022_1 | true         |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name                         | OPT.Description | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | pl_S0203_1 | ps_1                          | DE                        | CHF                 | price_list_sales_name_S0203  | null            | true  | false         | 2              | true         |
      | pl_S0203_2 | ps_1                          | DE                        | CHF                 | price_list_vendor_name_S0203 | null            | false | false         | 2              | true         |
    And metasfresh contains M_PriceList_Versions
      | Identifier  | M_PriceList_ID.Identifier | Name                             | ValidFrom  |
      | plv_S0203_1 | pl_S0203_1                | trackedProduct-PLV_Sales_S0203   | 2021-04-01 |
      | plv_S0203_2 | pl_S0203_2                | trackedProduct-PLV__vendor_S0203 | 2021-04-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_1       | plv_S0203_1                       | p_1                     | 10.0     | PCE               | Normal                        |
      | pp_2       | plv_S0203_2                       | p_1                     | 10.0     | PCE               | Normal                        |

    And metasfresh contains C_BPartners without locations:
      | Identifier | Name                     | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | bpartner_1 | EndcustomerPP_12102022_1 | Y            | Y              | ps_1                          |

    And metasfresh contains C_BPartner_Locations:
      | Identifier | GLN           | C_BPartner_ID.Identifier | OPT.IsBillToDefault | OPT.IsShipTo |
      | l_1        | 0203111111111 | bpartner_1               | true                | true         |

  @from:cucumber
  @Id:S0203_100
  Scenario: Import bank statement, identify org-account by IBAN and link one statement-line to a payment for a sales invoice that is matched via documentNo
    Given set sys config boolean value true for sys config de.metas.payment.esr.Enabled
    And load C_DataImport:
      | C_DataImport_ID.Identifier | OPT.C_DataImport_ID |
      | di_1_S0203_100             | 540009              |
    And metasfresh contains C_Bank:
      | C_Bank_ID.Identifier | Name       | RoutingNo | SwiftCode   | C_DataImport_ID.Identifier |
      | b_1_S0203_100        | bank_S0203 | 2234567   | AAAAAAAA82A | di_1_S0203_100             |
	# change the bankaccount of the AD_Org bpartner ("metasfresh") to be an ESR-Account
    And load C_BP_BankAccount:
      | C_BP_BankAccount_ID.Identifier | OPT.C_BP_BankAccount_ID |
      | bpb_1_S0203                    | 2000257                 |

    And update C_BP_BankAccount:
      | C_BP_BankAccount_ID.Identifier | OPT.C_Currency.ISO_Code | OPT.IsEsrAccount | OPT.AccountNo | OPT.ESR_RenderedAccountNo | OPT.IBAN              |
      | bpb_1_S0203                    | CHF                     | Y                | 1234567890    | 123456789                 | CH3908704016075473007 |

    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID.Identifier | C_DocTypeTarget_ID.Name | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code | OPT.DocumentNo |
      | inv_1      | bpartner_1               | Ausgangsrechnung        | 2022-05-09   | Spot                     | true    | CHF                 | 511003_bksts   |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | C_UOM_ID.X12DE355 |
      | invl_1     | inv_1                   | p_1                     | 10          | PCE               |
    And the invoice identified by inv_1 is completed
    And bank statement is imported with identifiers bs_1, matching invoice amounts
    """
    <?xml version="1.0" encoding="utf-8"?>
<Document xmlns="urn:iso:std:iso:20022:tech:xsd:camt.053.001.02">
	<BkToCstmrStmt>
		<GrpHdr>
			<MsgId>MsgId11111111111111</MsgId>
			<CreDtTm>2022-05-11T19:45:37.000Z</CreDtTm>
			<MsgPgntn>
				<PgNb>1</PgNb>
				<LastPgInd>true</LastPgInd>
			</MsgPgntn>
		</GrpHdr>
		<Stmt>
			<Id>StmtId11111111111111</Id>
			<ElctrncSeqNb>00001</ElctrncSeqNb>
			<CreDtTm>2022-05-11T19:45:37.000Z</CreDtTm>
			<Acct>
				<Id>
					<IBAN>CH3908704016075473007</IBAN>
				</Id>
				<Ccy>CHF</Ccy>
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
				<Amt Ccy="EUR">41891.00</Amt>
				<CdtDbtInd>CRDT</CdtDbtInd>
				<Dt>
					<Dt>2022-05-11</Dt>
				</Dt>
			</Bal>
			<Bal>
				<Tp>
					<CdOrPrtry>
						<Cd>CLBD</Cd>
					</CdOrPrtry>
				</Tp>
				<Amt Ccy="EUR">>42010.00</Amt>
				<CdtDbtInd>CRDT</CdtDbtInd>
				<Dt>
					<Dt>2022-05-11</Dt>
				</Dt>
			</Bal>
			<Ntry>
				<NtryRef>00001</NtryRef>
				<Amt Ccy="CHF">119</Amt>
				<CdtDbtInd>CRDT</CdtDbtInd>
				<Sts>BOOK</Sts>
				<BookgDt>
					<Dt>2022-05-10</Dt>
				</BookgDt>
				<ValDt>
					<Dt>2022-05-10</Dt>
				</ValDt>
				<AcctSvcrRef>AAA-1111111</AcctSvcrRef>
				<BkTxCd>
					<Prtry>
						<Cd>TRF</Cd>
						<Issr>SWIFT</Issr>
					</Prtry>
				</BkTxCd>
				<NtryDtls>
					<Btch>
						<NbOfTxs>1</NbOfTxs>
						<TtlAmt Ccy="CHF">119</TtlAmt>
						<CdtDbtInd>CRDT</CdtDbtInd>
					</Btch>
					<TxDtls>
						<Refs>
							<AcctSvcrRef>AAA-1111111</AcctSvcrRef>
							<PmtInfId>AAAAAA1111:11:11AAAA2022</PmtInfId>
						</Refs>
						<Amt Ccy="CHF">119</Amt>
						<CdtDbtInd>CRDT</CdtDbtInd>
						<AmtDtls>
							<InstdAmt>
								<Amt Ccy="CHF">119</Amt>
							</InstdAmt>
							<TxAmt>
								<Amt Ccy="CHF">119</Amt>
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
									<Ctry>CH</Ctry>
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
							<Ustrd>511003_bksts</Ustrd>
						</RmtInf>
					</TxDtls>
				</NtryDtls>
			</Ntry>
		</Stmt>
	</BkToCstmrStmt>
</Document>
    """
    And validate C_BankStatement
      | C_BankStatement_ID.Identifier | OPT.BeginningBalance | OPT.EndingBalance | OPT.StatementDifference | OPT.Processed | OPT.C_BP_BankAccount_ID.Identifier | OPT.StatementDate | OPT.IsReconciled |
      | bs_1                          | 41891                | 42010.00          | 119                     | false         | bpb_1_S0203                        | 2022-05-11        | false            |
    And load C_BankStatementLine
      | C_BankStatementLine_ID.Identifier | C_BankStatement_ID.Identifier | Line |
      | bsl_1                             | bs_1                          | 10   |
    And validate C_BankStatementLine
      | C_BankStatementLine_ID.Identifier | OPT.ValutaDate | OPT.DateAcct | OPT.C_Currency_ID.ISO_Code | OPT.TrxAmt | OPT.StmtAmt | OPT.StatementLineDate | OPT.C_BPartner_ID.Identifier | OPT.C_Invoice_ID.Identifier | OPT.Processed | OPT.IsReconciled |
      | bsl_1                             | 2022-05-10     | 2022-05-10   | CHF                        | 119        | 119         | 2022-05-10            | bpartner_1                   | inv_1                       | false         | false            |
    And the C_BankStatement identified by bs_1 is completed
    And after not more than 60s, C_Payment is found
      | C_Payment_ID.Identifier | OPT.C_BankStatement_ID.Identifier | OPT.C_BankStatementLine_ID.Identifier |
      | p_1                     | bs_1                              | bsl_1                                 |
    And validate payments
      | C_Payment_ID.Identifier | C_Payment_ID.IsAllocated | OPT.OpenAmt | OPT.PayAmt | OPT.C_Invoice_ID.Identifier | OPT.DateTrx | OPT.C_BPartner_ID.Identifier | OPT.C_BP_BankAccount_ID.Identifier |
      | p_1                     | true                     | 0.00        | 119        | inv_1                       | 2022-05-10  | bpartner_1                   | bpb_1_S0203                        |
    And validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | paymentTerm   | processed | docStatus | OPT.IsPaid |
      | inv_1                   | bpartner_1               | l_1                               | 30 Tage netto | true      | CO        | true       |
    And set sys config boolean value false for sys config de.metas.payment.esr.Enabled


  @from:cucumber
  @Id:S0203_200
  Scenario: Import one statement, identify org-account by IBAN and link two invoices one of which is matched via ESR-Reference
    Given set sys config boolean value true for sys config de.metas.payment.esr.Enabled
    And load C_DataImport:
      | C_DataImport_ID.Identifier | OPT.C_DataImport_ID |
      | di_1_S0203_100             | 540009              |
    And metasfresh contains C_Bank:
      | C_Bank_ID.Identifier | Name       | RoutingNo | SwiftCode   | C_DataImport_ID.Identifier |
      | b_1_S0203_100        | bank_S0203 | 2234567   | AAAAAAAA82A | di_1_S0203_100             |
	# change the bankaccount of the AD_Org bpartner ("metasfresh") to be an ESR-Account
    And load C_BP_BankAccount:
      | C_BP_BankAccount_ID.Identifier | OPT.C_BP_BankAccount_ID |
      | bpb_1_S0203                    | 2000257                 |

    And update C_BP_BankAccount:
      | C_BP_BankAccount_ID.Identifier | OPT.C_Currency.ISO_Code | OPT.IsEsrAccount | OPT.AccountNo | OPT.ESR_RenderedAccountNo | OPT.IBAN              |
      | bpb_1_S0203                    | CHF                     | Y                | 1234567890    | 123456789                 | CH3908704016075473007 |

    And metasfresh contains C_Invoice:
      | Identifier  | C_BPartner_ID.Identifier | C_DocTypeTarget_ID.Name | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code | OPT.DocumentNo |
      | inv_S0203_1 | bpartner_1               | Ausgangsrechnung        | 2022-05-09   | Spot                     | true    | CHF                 | 511003_bksts_2 |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | C_UOM_ID.X12DE355 |
      | invl_1     | inv_S0203_1             | p_1                     | 10          | PCE               |
    And the invoice identified by inv_S0203_1 is completed

    And load C_ReferenceNo:
      | C_ReferenceNo_ID.Identifier        | Record_ID.Identifier | C_ReferenceNo_Type_ID.Identifier |
      | ReferenceNo_metasfresh_S0203_200_1 | inv_S0203_1          | 540006                           |
    And update C_ReferenceNo:
      | C_ReferenceNo_ID.Identifier        | Record_ID.Identifier | C_ReferenceNo_Type_ID.Identifier | OPT.ReferenceNo                |
      | ReferenceNo_metasfresh_S0203_200_1 | inv_S0203_1          | 540006                           | ReferenceNo_metasfresh_S0203_1 |

    And bank statement is imported with identifiers bs_S0203_1, matching invoice amounts
    """
    <?xml version="1.0" encoding="utf-8"?>
<Document xmlns="urn:iso:std:iso:20022:tech:xsd:camt.053.001.02">
	<BkToCstmrStmt>
		<GrpHdr>
			<MsgId>MsgId11111111111111</MsgId>
			<CreDtTm>2022-05-11T19:45:37.000Z</CreDtTm>
			<MsgPgntn>
				<PgNb>1</PgNb>
				<LastPgInd>true</LastPgInd>
			</MsgPgntn>
		</GrpHdr>
		<Stmt>
			<Id>StmtId11111111111111</Id>
			<ElctrncSeqNb>00001</ElctrncSeqNb>
			<CreDtTm>2022-05-11T19:45:37.000Z</CreDtTm>
			<Acct>
				<Id>
					<IBAN>CH3908704016075473007</IBAN>
				</Id>
				<Ccy>CHF</Ccy>
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
				<Amt Ccy="EUR">41891.00</Amt>
				<CdtDbtInd>CRDT</CdtDbtInd>
				<Dt>
					<Dt>2022-05-11</Dt>
				</Dt>
			</Bal>
			<Bal>
				<Tp>
					<CdOrPrtry>
						<Cd>CLBD</Cd>
					</CdOrPrtry>
				</Tp>
				<Amt Ccy="EUR">>42010.00</Amt>
				<CdtDbtInd>CRDT</CdtDbtInd>
				<Dt>
					<Dt>2022-05-11</Dt>
				</Dt>
			</Bal>
			<Ntry>
				<NtryRef>00001</NtryRef>
				<Amt Ccy="CHF">119</Amt>
				<CdtDbtInd>CRDT</CdtDbtInd>
				<Sts>BOOK</Sts>
				<BookgDt>
					<Dt>2022-05-10</Dt>
				</BookgDt>
				<ValDt>
					<Dt>2022-05-10</Dt>
				</ValDt>
				<AcctSvcrRef>AAA-1111111</AcctSvcrRef>
				<BkTxCd>
					<Prtry>
						<Cd>TRF</Cd>
						<Issr>SWIFT</Issr>
					</Prtry>
				</BkTxCd>
				<NtryDtls>
					<Btch>
						<NbOfTxs>1</NbOfTxs>
						<TtlAmt Ccy="CHF">119</TtlAmt>
						<CdtDbtInd>CRDT</CdtDbtInd>
					</Btch>
					<TxDtls>
						<Refs>
							<AcctSvcrRef>AAA-1111111</AcctSvcrRef>
							<PmtInfId>AAAAAA1111:11:11AAAA2022</PmtInfId>
						</Refs>
						<Amt Ccy="CHF">119</Amt>
						<CdtDbtInd>CRDT</CdtDbtInd>
						<AmtDtls>
							<InstdAmt>
								<Amt Ccy="CHF">119</Amt>
							</InstdAmt>
							<TxAmt>
								<Amt Ccy="CHF">119</Amt>
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
									<Ctry>CH</Ctry>
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
							<Ustrd>ReferenceNo_metasfresh_S0203_1</Ustrd>
						</RmtInf>
					</TxDtls>
				</NtryDtls>
			</Ntry>
		</Stmt>
	</BkToCstmrStmt>
</Document>
    """
    And validate C_BankStatement
      | C_BankStatement_ID.Identifier | OPT.BeginningBalance | OPT.EndingBalance | OPT.StatementDifference | OPT.Processed | OPT.C_BP_BankAccount_ID.Identifier | OPT.StatementDate | OPT.IsReconciled |
      | bs_S0203_1                    | 41891                | 42010.00          | 119                     | false         | bpb_1_S0203                        | 2022-05-11        | false            |
    And load C_BankStatementLine
      | C_BankStatementLine_ID.Identifier | C_BankStatement_ID.Identifier | Line |
      | bsl_S0203_1                       | bs_S0203_1                    | 10   |
    And validate C_BankStatementLine
      | C_BankStatementLine_ID.Identifier | OPT.ValutaDate | OPT.DateAcct | OPT.C_Currency_ID.ISO_Code | OPT.TrxAmt | OPT.StmtAmt | OPT.StatementLineDate | OPT.C_BPartner_ID.Identifier | OPT.C_Invoice_ID.Identifier | OPT.Processed | OPT.IsReconciled |
      | bsl_S0203_1                       | 2022-05-10     | 2022-05-10   | CHF                        | 119        | 119         | 2022-05-10            | bpartner_1                   | inv_S0203_1                 | false         | false            |
    And the C_BankStatement identified by bs_S0203_1 is completed
    And after not more than 60s, C_Payment is found
      | C_Payment_ID.Identifier | OPT.C_BankStatement_ID.Identifier | OPT.C_BankStatementLine_ID.Identifier |
      | p_S0203_1               | bs_S0203_1                        | bsl_S0203_1                           |
    And validate payments
      | C_Payment_ID.Identifier | C_Payment_ID.IsAllocated | OPT.OpenAmt | OPT.PayAmt | OPT.C_Invoice_ID.Identifier | OPT.DateTrx | OPT.C_BPartner_ID.Identifier | OPT.C_BP_BankAccount_ID.Identifier |
      | p_S0203_1               | true                     | 0.00        | 119        | inv_S0203_1                 | 2022-05-10  | bpartner_1                   | bpb_1_S0203                        |
    And validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | paymentTerm   | processed | docStatus | OPT.IsPaid |
      | inv_S0203_1             | bpartner_1               | l_1                               | 30 Tage netto | true      | CO        | true       |
    And set sys config boolean value false for sys config de.metas.payment.esr.Enabled

   @from:cucumber
  Scenario: Import one statement, identify org-account by IBAN and link two invoices, but in 2 different bank statements; so the expectation is to have created 2 bank statements
    Given set sys config boolean value true for sys config de.metas.payment.esr.Enabled
    And load C_DataImport:
      | C_DataImport_ID.Identifier | OPT.C_DataImport_ID |
      | di_1_S0203_100             | 540009              |
    And metasfresh contains C_Bank:
      | C_Bank_ID.Identifier | Name       | RoutingNo | SwiftCode   | C_DataImport_ID.Identifier |
      | b_1_S0203_100        | bank_S0203 | 2234567   | AAAAAAAA82A | di_1_S0203_100             |
	# change the bankaccount of the AD_Org bpartner ("metasfresh") to be an ESR-Account
    And load C_BP_BankAccount:
      | C_BP_BankAccount_ID.Identifier | OPT.C_BP_BankAccount_ID |
      | bpb_1_S0203                    | 2000257                 |

    And update C_BP_BankAccount:
      | C_BP_BankAccount_ID.Identifier | OPT.C_Currency.ISO_Code | OPT.IsEsrAccount | OPT.AccountNo | OPT.ESR_RenderedAccountNo | OPT.IBAN              |
      | bpb_1_S0203                    | CHF                     | Y                | 1234567890    | 123456789                 | CH3908704016075473007 |

    And metasfresh contains C_Invoice:
      | Identifier  | C_BPartner_ID.Identifier | C_DocTypeTarget_ID.Name | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code | OPT.DocumentNo |
      | inv_S0203_3 | bpartner_1               | Ausgangsrechnung        | 2022-05-09   | Spot                     | true    | CHF                 | 511003_bksts_3 |
      | inv_S0203_4 | bpartner_1               | Ausgangsrechnung        | 2022-05-09   | Spot                     | true    | CHF                 | 511003_bksts_4 |

    And metasfresh contains C_InvoiceLines
      | Identifier     | C_Invoice_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | C_UOM_ID.X12DE355 |
      | invl_S0203_3_1 | inv_S0203_3             | p_1                     | 10          | PCE               |
      | invl_S0203_4_1 | inv_S0203_4             | p_1                     | 10          | PCE               |

    And the invoice identified by inv_S0203_3 is completed
    And the invoice identified by inv_S0203_4 is completed

    When bank statement is imported with identifiers bs_S0203_300_1, bs_S0203_300_2, matching invoice amounts
    """
    <?xml version="1.0" encoding="utf-8"?>
<Document xmlns="urn:iso:std:iso:20022:tech:xsd:camt.053.001.02">
	<BkToCstmrStmt>
		<GrpHdr>
			<MsgId>MsgId11111111111111</MsgId>
			<CreDtTm>2022-05-11T19:45:37.000Z</CreDtTm>
			<MsgPgntn>
				<PgNb>1</PgNb>
				<LastPgInd>true</LastPgInd>
			</MsgPgntn>
		</GrpHdr>
		<Stmt>
			<Id>StmtId11111111111111</Id>
			<ElctrncSeqNb>00001</ElctrncSeqNb>
			<CreDtTm>2022-05-11T19:45:37.000Z</CreDtTm>
			<Acct>
				<Id>
					<IBAN>CH3908704016075473007</IBAN>
				</Id>
				<Ccy>CHF</Ccy>
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
				<Amt Ccy="EUR">41891.00</Amt>
				<CdtDbtInd>CRDT</CdtDbtInd>
				<Dt>
					<Dt>2022-05-11</Dt>
				</Dt>
			</Bal>
			<Bal>
				<Tp>
					<CdOrPrtry>
						<Cd>CLBD</Cd>
					</CdOrPrtry>
				</Tp>
				<Amt Ccy="EUR">>42010.00</Amt>
				<CdtDbtInd>CRDT</CdtDbtInd>
				<Dt>
					<Dt>2022-05-11</Dt>
				</Dt>
			</Bal>
			<Ntry>
				<NtryRef>00001</NtryRef>
				<Amt Ccy="CHF">119</Amt>
				<CdtDbtInd>CRDT</CdtDbtInd>
				<Sts>BOOK</Sts>
				<BookgDt>
					<Dt>2022-05-10</Dt>
				</BookgDt>
				<ValDt>
					<Dt>2022-05-10</Dt>
				</ValDt>
				<AcctSvcrRef>AAA-1111111</AcctSvcrRef>
				<BkTxCd>
					<Prtry>
						<Cd>TRF</Cd>
						<Issr>SWIFT</Issr>
					</Prtry>
				</BkTxCd>
				<NtryDtls>
					<Btch>
						<NbOfTxs>1</NbOfTxs>
						<TtlAmt Ccy="CHF">119</TtlAmt>
						<CdtDbtInd>CRDT</CdtDbtInd>
					</Btch>
					<TxDtls>
						<Refs>
							<AcctSvcrRef>AAA-1111111</AcctSvcrRef>
							<PmtInfId>AAAAAA1111:11:11AAAA2022</PmtInfId>
						</Refs>
						<Amt Ccy="CHF">119</Amt>
						<CdtDbtInd>CRDT</CdtDbtInd>
						<AmtDtls>
							<InstdAmt>
								<Amt Ccy="CHF">119</Amt>
							</InstdAmt>
							<TxAmt>
								<Amt Ccy="CHF">119</Amt>
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
									<Ctry>CH</Ctry>
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
							<Ustrd>511003_bksts_3</Ustrd>
						</RmtInf>
					</TxDtls>
				</NtryDtls>
			</Ntry>
		</Stmt>

			<Stmt>
			<Id>StmtId11111111111111</Id>
			<ElctrncSeqNb>00001</ElctrncSeqNb>
			<CreDtTm>2022-05-11T19:45:37.000Z</CreDtTm>
			<Acct>
				<Id>
					<IBAN>CH3908704016075473007</IBAN>
				</Id>
				<Ccy>CHF</Ccy>
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
				<Amt Ccy="EUR">41891.00</Amt>
				<CdtDbtInd>CRDT</CdtDbtInd>
				<Dt>
					<Dt>2022-05-11</Dt>
				</Dt>
			</Bal>
			<Bal>
				<Tp>
					<CdOrPrtry>
						<Cd>CLBD</Cd>
					</CdOrPrtry>
				</Tp>
				<Amt Ccy="EUR">>42010.00</Amt>
				<CdtDbtInd>CRDT</CdtDbtInd>
				<Dt>
					<Dt>2022-05-11</Dt>
				</Dt>
			</Bal>
			<Ntry>
				<NtryRef>00001</NtryRef>
				<Amt Ccy="CHF">119</Amt>
				<CdtDbtInd>CRDT</CdtDbtInd>
				<Sts>BOOK</Sts>
				<BookgDt>
					<Dt>2022-05-10</Dt>
				</BookgDt>
				<ValDt>
					<Dt>2022-05-10</Dt>
				</ValDt>
				<AcctSvcrRef>AAA-1111111</AcctSvcrRef>
				<BkTxCd>
					<Prtry>
						<Cd>TRF</Cd>
						<Issr>SWIFT</Issr>
					</Prtry>
				</BkTxCd>
				<NtryDtls>
					<Btch>
						<NbOfTxs>1</NbOfTxs>
						<TtlAmt Ccy="CHF">119</TtlAmt>
						<CdtDbtInd>CRDT</CdtDbtInd>
					</Btch>
					<TxDtls>
						<Refs>
							<AcctSvcrRef>AAA-1111111</AcctSvcrRef>
							<PmtInfId>AAAAAA1111:11:11AAAA2022</PmtInfId>
						</Refs>
						<Amt Ccy="CHF">119</Amt>
						<CdtDbtInd>CRDT</CdtDbtInd>
						<AmtDtls>
							<InstdAmt>
								<Amt Ccy="CHF">119</Amt>
							</InstdAmt>
							<TxAmt>
								<Amt Ccy="CHF">119</Amt>
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
									<Ctry>CH</Ctry>
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
							<Ustrd>511003_bksts_4</Ustrd>
						</RmtInf>
					</TxDtls>
				</NtryDtls>
			</Ntry>
		</Stmt>
	</BkToCstmrStmt>
</Document>
    """
    And validate C_BankStatement
      | C_BankStatement_ID.Identifier | OPT.BeginningBalance | OPT.EndingBalance | OPT.StatementDifference | OPT.Processed | OPT.C_BP_BankAccount_ID.Identifier | OPT.StatementDate | OPT.IsReconciled |
      | bs_S0203_300_1                | 41891                | 42010.00          | 119                     | false         | bpb_1_S0203                        | 2022-05-11        | false            |
    And load C_BankStatementLine
      | C_BankStatementLine_ID.Identifier | C_BankStatement_ID.Identifier | Line |
      | bsl_S0203_300_1                   | bs_S0203_300_1                | 10   |
    And validate C_BankStatementLine
      | C_BankStatementLine_ID.Identifier | OPT.ValutaDate | OPT.DateAcct | OPT.C_Currency_ID.ISO_Code | OPT.TrxAmt | OPT.StmtAmt | OPT.StatementLineDate | OPT.C_BPartner_ID.Identifier | OPT.C_Invoice_ID.Identifier | OPT.Processed | OPT.IsReconciled |
      | bsl_S0203_300_1                   | 2022-05-10     | 2022-05-10   | CHF                        | 119        | 119         | 2022-05-10            | bpartner_1                   | inv_S0203_3                 | false         | false            |
    And the C_BankStatement identified by bs_S0203_300_1 is completed

    And validate C_BankStatement
      | C_BankStatement_ID.Identifier | OPT.BeginningBalance | OPT.EndingBalance | OPT.StatementDifference | OPT.Processed | OPT.C_BP_BankAccount_ID.Identifier | OPT.StatementDate | OPT.IsReconciled |
      | bs_S0203_300_2                | 41891                | 42010.00          | 119                     | false         | bpb_1_S0203                        | 2022-05-11        | false            |
    And load C_BankStatementLine
      | C_BankStatementLine_ID.Identifier | C_BankStatement_ID.Identifier | Line |
      | bsl_S0203_300_2                   | bs_S0203_300_2                | 10   |
    And validate C_BankStatementLine
      | C_BankStatementLine_ID.Identifier | OPT.ValutaDate | OPT.DateAcct | OPT.C_Currency_ID.ISO_Code | OPT.TrxAmt | OPT.StmtAmt | OPT.StatementLineDate | OPT.C_BPartner_ID.Identifier | OPT.C_Invoice_ID.Identifier | OPT.Processed | OPT.IsReconciled |
      | bsl_S0203_300_2                   | 2022-05-10     | 2022-05-10   | CHF                        | 119        | 119         | 2022-05-10            | bpartner_1                   | inv_S0203_4                 | false         | false            |
    And the C_BankStatement identified by bs_S0203_300_2 is completed

    And after not more than 60s, C_Payment is found
      | C_Payment_ID.Identifier | OPT.C_BankStatement_ID.Identifier | OPT.C_BankStatementLine_ID.Identifier |
      | p_S0203_3               | bs_S0203_300_1                    | bsl_S0203_300_1                       |
      | p_S0203_4               | bs_S0203_300_2                    | bsl_S0203_300_2                       |
    And validate payments
      | C_Payment_ID.Identifier | C_Payment_ID.IsAllocated | OPT.OpenAmt | OPT.PayAmt | OPT.C_Invoice_ID.Identifier | OPT.DateTrx | OPT.C_BPartner_ID.Identifier | OPT.C_BP_BankAccount_ID.Identifier |
      | p_S0203_3               | true                     | 0.00        | 119        | inv_S0203_3                 | 2022-05-10  | bpartner_1                   | bpb_1_S0203                        |
      | p_S0203_4               | true                     | 0.00        | 119        | inv_S0203_4                 | 2022-05-10  | bpartner_1                   | bpb_1_S0203                        |
    And validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | paymentTerm   | processed | docStatus | OPT.IsPaid |
      | inv_S0203_3             | bpartner_1               | l_1                               | 30 Tage netto | true      | CO        | true       |
      | inv_S0203_4             | bpartner_1               | l_1                               | 30 Tage netto | true      | CO        | true       |
    And set sys config boolean value false for sys config de.metas.payment.esr.Enabled

