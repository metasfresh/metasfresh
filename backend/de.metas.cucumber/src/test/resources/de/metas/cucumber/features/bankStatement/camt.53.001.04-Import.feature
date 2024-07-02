@from:cucumber
Feature: import bank statement in camt.53.001.04 import format

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2020-02-28T13:30:13+01:00[Europe/Berlin]

    And metasfresh contains M_Products:
      | Identifier | Name                         |
      | p_1_S0337  | bankStatementProduct_S0337_1 |
    And metasfresh contains M_PricingSystems
      | Identifier | Name                           | Value                           | OPT.Description                       | OPT.IsActive |
      | ps_1_S0337 | pricing_system_name_12102022_1 | pricing_system_value_12102022_1 | pricing_system_description_12102022_1 | true         |
    And metasfresh contains M_PriceLists
      | Identifier    | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name                | OPT.Description | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | pl_1_SO_S0337 | ps_1_S0337                    | DE                        | CHF                 | price_list_SO_S0337 | null            | true  | false         | 2              | true         |
      | pl_1_PO_S0337 | ps_1_S0337                    | DE                        | CHF                 | price_list_PO_S0337 | null            | false | false         | 2              | true         |
    And metasfresh contains M_PriceList_Versions
      | Identifier     | M_PriceList_ID.Identifier | Name                          | ValidFrom  |
      | plv_1_SO_S0337 | pl_1_SO_S0337             | trackedProduct-plv_1_SO_S0337 | 2021-04-01 |
      | plv_1_PO_S0337 | pl_1_PO_S0337             | trackedProduct-plv_1_PO_S0337 | 2021-04-01 |
    And metasfresh contains M_ProductPrices
      | Identifier    | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_1_SO_S0337 | plv_1_SO_S0337                    | p_1_S0337               | 10.0     | PCE               | Normal                        |
      | pp_1_PO_S0337 | plv_1_PO_S0337                    | p_1_S0337               | 10.0     | PCE               | Normal                        |

    And metasfresh contains C_BPartners without locations:
      | Identifier       | Name                     | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | bpartner_1_S0337 | EndcustomerPP_12102022_1 | Y            | Y              | ps_1_S0337                    |

    And metasfresh contains C_BPartner_Locations:
      | Identifier | GLN           | C_BPartner_ID.Identifier | OPT.IsBillToDefault | OPT.IsShipTo |
      | l_1_S0337  | 0203111111111 | bpartner_1_S0337         | true                | true         |

    And set sys config boolean value false for sys config de.metas.payment.esr.Enabled

  @from:cucumber
  @Id:S0337_100
  Scenario: Import bank statement, identify org-account by IBAN and link one statement-line to a payment for a sales invoice that is matched via documentNo

    Given set sys config boolean value true for sys config de.metas.payment.esr.Enabled
    And load C_DataImport:
      | C_DataImport_ID.Identifier | OPT.C_DataImport_ID |
      | di_1_S0337_100             | 540009              |
    And metasfresh contains C_Bank:
      | C_Bank_ID.Identifier | Name       | RoutingNo | SwiftCode   | C_DataImport_ID.Identifier |
      | b_1_S0337_100        | bank_S0337 | 2234567   | AAAAAAAA82A | di_1_S0337_100             |
	# change the bankaccount of the AD_Org bpartner ("metasfresh") to be an ESR-Account
    And load C_BP_BankAccount:
      | C_BP_BankAccount_ID.Identifier | OPT.C_BP_BankAccount_ID |
      | bpb_1_S0337                    | 2000257                 |

    And update C_BP_BankAccount:
      | C_BP_BankAccount_ID.Identifier | OPT.C_Currency.ISO_Code | OPT.IsEsrAccount | OPT.AccountNo | OPT.ESR_RenderedAccountNo | OPT.IBAN              |
      | bpb_1_S0337                    | CHF                     | Y                | 1234567890    | 123456789                 | CH3908704016075473007 |

    And metasfresh contains C_Invoice:
      | Identifier      | C_BPartner_ID.Identifier | C_DocTypeTarget_ID.Name | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code | OPT.DocumentNo   |
      | inv_1_S0337_100 | bpartner_1_S0337         | Ausgangsrechnung        | 2022-05-11   | Spot                     | true    | EUR                 | 511004_S0337_100 |
    And metasfresh contains C_InvoiceLines
      | Identifier       | C_Invoice_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | C_UOM_ID.X12DE355 |
      | invl_1_S0337_100 | inv_1_S0337_100         | p_1_S0337               | 10          | PCE               |
    And the invoice identified by inv_1_S0337_100 is completed
    When bank statement is imported with identifiers bs_1_S0337_100, matching invoice amounts
    """
<?xml version="1.0" encoding="utf-8"?>
<Document xmlns="urn:iso:std:iso:20022:tech:xsd:camt.053.001.04">
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
			<FrToDt>
				<FrDtTm>2022-05-08T00:00:00</FrDtTm>
				<ToDtTm>2022-05-10T23:59:59</ToDtTm>
			</FrToDt>
			<Acct>
				<Id>
					<IBAN>CH3908704016075473007</IBAN>
				</Id>
				<Ccy>CHF</Ccy>
				<Ownr>
					<Nm>Test CO</Nm>
					<PstlAdr>
						<AdrLine>Street 123</AdrLine>
						<AdrLine>0000 CityTest</AdrLine>
					</PstlAdr>
				</Ownr>
				<Svcr>
					<FinInstnId>
						<BICFI>AAAAAAAA82A</BICFI>
						<Nm>Test Bank</Nm>
					</FinInstnId>
				</Svcr>
			</Acct>
			<Bal>
				<Tp>
					<CdOrPrtry>
						<Cd>OPBD</Cd>
					</CdOrPrtry>
				</Tp>
				<Amt Ccy="CHF">41891.00</Amt>
				<CdtDbtInd>CRDT</CdtDbtInd>
				<Dt>
					<Dt>2022-05-08</Dt>
				</Dt>
			</Bal>
			<Bal>
				<Tp>
					<CdOrPrtry>
						<Cd>CLBD</Cd>
					</CdOrPrtry>
				</Tp>
				<Amt Ccy="CHF">>42010.00</Amt>
				<CdtDbtInd>CRDT</CdtDbtInd>
				<Dt>
					<Dt>2022-05-10</Dt>
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
							<Ustrd>511004_S0337_100</Ustrd>
						</RmtInf>
					</TxDtls>
				</NtryDtls>
			</Ntry>
		</Stmt>
	</BkToCstmrStmt>
</Document>
    """
    Then validate C_BankStatement
      | C_BankStatement_ID.Identifier | OPT.BeginningBalance | OPT.EndingBalance | OPT.StatementDifference | OPT.Processed | OPT.C_BP_BankAccount_ID.Identifier | OPT.StatementDate | OPT.IsReconciled |
      | bs_1_S0337_100                | 41891                | 42010             | 119                     | false         | bpb_1_S0337                        | 2022-05-11        | false            |
    And load C_BankStatementLine
      | C_BankStatementLine_ID.Identifier | C_BankStatement_ID.Identifier | Line |
      | bsl_1_S0337_100                   | bs_1_S0337_100                | 10   |
    And validate C_BankStatementLine
      | C_BankStatementLine_ID.Identifier | OPT.ValutaDate | OPT.DateAcct | OPT.C_Currency_ID.ISO_Code | OPT.TrxAmt | OPT.StmtAmt | OPT.StatementLineDate | OPT.C_BPartner_ID.Identifier | OPT.C_Invoice_ID.Identifier | OPT.Processed | OPT.IsReconciled |
      | bsl_1_S0337_100                   | 2022-05-10     | 2022-05-10   | CHF                        | 119        | 119         | 2022-05-10            | bpartner_1_S0337             | inv_1_S0337_100             | false         | false            |
    And the C_BankStatement identified by bs_1_S0337_100 is completed
    And after not more than 60s, C_Payment is found
      | C_Payment_ID.Identifier | OPT.C_BankStatement_ID.Identifier | OPT.C_BankStatementLine_ID.Identifier |
      | p_1_S0337               | bs_1_S0337_100                    | bsl_1_S0337_100                       |
    And validate payments
      | C_Payment_ID.Identifier | C_Payment_ID.IsAllocated | OPT.OpenAmt | OPT.PayAmt | OPT.C_Invoice_ID.Identifier | OPT.DateTrx | OPT.C_BPartner_ID.Identifier | OPT.C_BP_BankAccount_ID.Identifier |
      | p_1_S0337               | true                     | 0.00        | 119        | inv_1_S0337_100             | 2022-05-10  | bpartner_1_S0337             | bpb_1_S0337                        |
    And validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | paymentTerm   | processed | docStatus | OPT.IsPaid |
      | inv_1_S0337_100         | bpartner_1_S0337         | l_1_S0337                         | 30 Tage netto | true      | CO        | true       |

  @from:cucumber
  @Id:S0337_200
  Scenario: Import one statement, identify org-account by IBAN and link two invoices one of which is matched via ESR-Reference

    Given set sys config boolean value true for sys config de.metas.payment.esr.Enabled
	# change the bankaccount of the AD_Org bpartner ("metasfresh") to be an ESR-Account
    And load C_DataImport:
      | C_DataImport_ID.Identifier | OPT.C_DataImport_ID |
      | di_1_S0337_200             | 540009              |
    And metasfresh contains C_Bank:
      | C_Bank_ID.Identifier | Name           | RoutingNo | SwiftCode   | C_DataImport_ID.Identifier |
      | b_1_S0337_200        | bank_S0337_200 | 2234567   | AAAAAAAA82A | di_1_S0337_200             |
    And load C_BP_BankAccount:
      | C_BP_BankAccount_ID.Identifier       | OPT.C_BP_BankAccount_ID |
      | bp_bank_account_metasfresh_S0337_200 | 2000257                 |

    And update C_BP_BankAccount:
      | C_BP_BankAccount_ID.Identifier       | OPT.C_Currency.ISO_Code | OPT.IsEsrAccount | OPT.AccountNo | OPT.ESR_RenderedAccountNo | OPT.IBAN              |
      | bp_bank_account_metasfresh_S0337_200 | CHF                     | Y                | 1234567890    | 123456789                 | CH3908704016075473007 |

	  # create 2 invoices; we expect the sales invoice to end up with the ESR-reference 123456700102156434010001795
    Given metasfresh contains C_Invoice:
      | Identifier      | C_BPartner_ID.Identifier | C_DocTypeTarget_ID.Name | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code | OPT.DocumentNo        |
      | inv_1_S0337_200 | bpartner_1_S0337         | Ausgangsrechnung        | 2022-05-12   | Spot                     | true    | CHF                 | 511004_1_SO_S0337_200 |
      | inv_2_S0337_200 | bpartner_1_S0337         | Ausgangsrechnung        | 2022-05-12   | Spot                     | true    | CHF                 | 511004_2_SO_S0337_200 |

    And metasfresh contains C_InvoiceLines
      | Identifier       | C_Invoice_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | C_UOM_ID.X12DE355 |
      | invl_1_S0337_200 | inv_1_S0337_200         | p_1_S0337               | 10          | PCE               |
      | invl_2_S0337_200 | inv_2_S0337_200         | p_1_S0337               | 9           | PCE               |
    And the invoice identified by inv_1_S0337_200 is completed
    And the invoice identified by inv_2_S0337_200 is completed

    And load C_ReferenceNo:
      | C_ReferenceNo_ID.Identifier        | Record_ID.Identifier | C_ReferenceNo_Type_ID.Identifier |
      | ReferenceNo_metasfresh_S0337_200_1 | inv_1_S0337_200      | 540006                           |
      | ReferenceNo_metasfresh_S0337_200_2 | inv_2_S0337_200      | 540006                           |

    And update C_ReferenceNo:
      | C_ReferenceNo_ID.Identifier        | Record_ID.Identifier | C_ReferenceNo_Type_ID.Identifier | OPT.ReferenceNo                    |
      | ReferenceNo_metasfresh_S0337_200_1 | inv_1_S0337_200      | 540006                           | ReferenceNo_metasfresh_S0337_200_1 |
      | ReferenceNo_metasfresh_S0337_200_2 | inv_2_S0337_200      | 540006                           | ReferenceNo_metasfresh_S0337_200_2 |

    When bank statement is imported with identifiers bs_1_S0337_200, matching invoice amounts
    """
<?xml version="1.0" encoding="UTF-8"?>
<Document xmlns="urn:iso:std:iso:20022:tech:xsd:camt.053.001.04">
	<BkToCstmrStmt>
		<GrpHdr>
			<MsgId>2023102706963268</MsgId>
			<CreDtTm>2023-10-27T15:15:37</CreDtTm>
			<MsgPgntn>
				<PgNb>1</PgNb>
				<LastPgInd>true</LastPgInd>
			</MsgPgntn>
		</GrpHdr>
		<Stmt>
			<Id>2023102706963268</Id>
			<ElctrncSeqNb>39</ElctrncSeqNb>
			<CreDtTm>2023-10-27T15:15:37</CreDtTm>
			<FrToDt>
				<FrDtTm>2023-10-24T00:00:00</FrDtTm>
				<ToDtTm>2023-10-26T23:59:59</ToDtTm>
			</FrToDt>
			<CpyDplctInd>DUPL</CpyDplctInd>
			<Acct>
				<Id>
					<IBAN>CH3908704016075473007</IBAN>
				</Id>
				<Ccy>CHF</Ccy>
				<Ownr>
					<Nm>Our-Name</Nm>
					<PstlAdr>
						<AdrLine>Our-AdrLine 1</AdrLine>
						<AdrLine>Our AdrLine 2</AdrLine>
					</PstlAdr>
				</Ownr>
				<Svcr>
					<FinInstnId>
						<BICFI>AAAAAAAA82A</BICFI>
						<Nm>Our Bank</Nm>
					</FinInstnId>
				</Svcr>
			</Acct>
			<Bal>
				<!-- opening balance -->
				<Tp>
					<CdOrPrtry>
						<Cd>OPBD</Cd>
					</CdOrPrtry>
				</Tp>
				<Amt Ccy="CHF">1000.07</Amt>
				<CdtDbtInd>CRDT</CdtDbtInd>
				<Dt>
					<Dt>2023-10-23</Dt>
				</Dt>
			</Bal>
			<Bal>
				<!-- closing balance -->
				<Tp>
					<CdOrPrtry>
						<Cd>CLBD</Cd>
					</CdOrPrtry>
				</Tp>
				<Amt Ccy="CHF">869.17</Amt>
				<CdtDbtInd>CRDT</CdtDbtInd>
				<Dt>
					<Dt>2023-10-26</Dt>
				</Dt>
			</Bal>
			<Ntry>
				<!-- plus 107.1 CHF -->
				<Amt Ccy="CHF">107.1</Amt>
				<CdtDbtInd>CRDT</CdtDbtInd>
				<RvslInd>false</RvslInd>
				<Sts>BOOK</Sts>
				<BookgDt>
					<Dt>2023-10-24</Dt>
				</BookgDt>
				<ValDt>
					<Dt>2023-10-24</Dt>
				</ValDt>
				<BkTxCd>
					<Domn>
						<Cd>PMNT</Cd>
						<Fmly>
							<Cd>RCDT</Cd>
							<SubFmlyCd>OTHR</SubFmlyCd>
						</Fmly>
					</Domn>
				</BkTxCd>
				<NtryDtls>
					<Btch>
						<NbOfTxs>1</NbOfTxs>
						<TtlAmt Ccy="CHF">107.1</TtlAmt>
						<CdtDbtInd>CRDT</CdtDbtInd>
					</Btch>
					<TxDtls>
						<Refs>
							<AcctSvcrRef>AcctSvcrRef/1/1</AcctSvcrRef>
							<EndToEndId>EndToEndId/1/1</EndToEndId>
							<TxId>TxId/1/1</TxId>
						</Refs>
						<Amt Ccy="CHF">107.1</Amt>
						<CdtDbtInd>CRDT</CdtDbtInd>
						<AmtDtls>
							<InstdAmt>
								<Amt Ccy="CHF">107.1</Amt>
							</InstdAmt>
							<TxAmt>
								<Amt Ccy="CHF">107.1</Amt>
							</TxAmt>
						</AmtDtls>
						<RmtInf>
							<Ustrd>ESR-Referenz ReferenceNo_metasfresh_S0337_200_2</Ustrd>
						</RmtInf>
						<AddtlTxInf>Incoming payment</AddtlTxInf>
					</TxDtls>
				</NtryDtls>
				<AddtlNtryInf>Incoming payment</AddtlNtryInf>
			</Ntry>
			<Ntry>
				<!-- plus 119 CHF -->
				<Amt Ccy="CHF">119</Amt>
				<CdtDbtInd>CRDT</CdtDbtInd>
				<RvslInd>false</RvslInd>
				<Sts>BOOK</Sts>
				<BookgDt>
					<Dt>2023-10-26</Dt>
				</BookgDt>
				<ValDt>
					<Dt>2023-10-26</Dt>
				</ValDt>
				<NtryDtls>
					<Btch>
						<NbOfTxs>1</NbOfTxs>
						<TtlAmt Ccy="CHF">119</TtlAmt>
						<CdtDbtInd>CRDT</CdtDbtInd>
					</Btch>
					<TxDtls>
						<Refs>
							<MsgId>MsgId/2</MsgId>
							<AcctSvcrRef>AcctSvcrRef/2/1</AcctSvcrRef>
							<PmtInfId>PmtInfId/2/1</PmtInfId>
							<InstrId>InstrId/2/1</InstrId>
							<EndToEndId>EndToEndId/2/1</EndToEndId>
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
						<RmtInf>
							<Ustrd>511004_1_SO_S0337_200</Ustrd>
						</RmtInf>
						<AddtlTxInf>Incoming payment</AddtlTxInf>
					</TxDtls>
				</NtryDtls>
				<AddtlNtryInf>Incoming payment</AddtlNtryInf>
			</Ntry>
		</Stmt>
	</BkToCstmrStmt>
</Document>
    """

    Then validate C_BankStatement
      | C_BankStatement_ID.Identifier | OPT.BeginningBalance | OPT.EndingBalance | OPT.StatementDifference | OPT.Processed | OPT.C_BP_BankAccount_ID.Identifier   | OPT.StatementDate | OPT.IsReconciled |
      | bs_1_S0337_200                | 1000.07              | 1226.17           | 226.1                   | false         | bp_bank_account_metasfresh_S0337_200 | 2023-10-27        | false            |
    And load C_BankStatementLine
      | C_BankStatementLine_ID.Identifier | C_BankStatement_ID.Identifier | Line |
      | bsl_1_S0337_200                   | bs_1_S0337_200                | 10   |
      | bsl_2_S0337_200                   | bs_1_S0337_200                | 20   |
    And validate C_BankStatementLine
      | C_BankStatementLine_ID.Identifier | OPT.ValutaDate | OPT.DateAcct | OPT.C_Currency_ID.ISO_Code | OPT.TrxAmt | OPT.C_BPartner_ID.Identifier | OPT.C_Invoice_ID.Identifier |
      | bsl_1_S0337_200                   | 2023-10-24     | 2023-10-24   | CHF                        | 107.1      | bpartner_1_S0337             | inv_2_S0337_200             |
      | bsl_2_S0337_200                   | 2023-10-26     | 2023-10-26   | CHF                        | 119        | bpartner_1_S0337             | inv_1_S0337_200             |

    And the C_BankStatement identified by bs_1_S0337_200 is completed

    And after not more than 60s, C_Payment is found
      | C_Payment_ID.Identifier | OPT.C_BankStatement_ID.Identifier | OPT.C_BankStatementLine_ID.Identifier |
      | p_2_1_S0337             | bs_1_S0337_200                    | bsl_2_S0337_200                       |
      | p_2_2_S0337             | bs_1_S0337_200                    | bsl_1_S0337_200                       |
    And validate payments
      | C_Payment_ID.Identifier | C_Payment_ID.IsAllocated | OPT.OpenAmt | OPT.PayAmt | OPT.C_Invoice_ID.Identifier | OPT.DateTrx | OPT.C_BPartner_ID.Identifier | OPT.C_BP_BankAccount_ID.Identifier   |
      | p_2_1_S0337             | true                     | 0.00        | 119        | inv_1_S0337_200             | 2023-10-26  | bpartner_1_S0337             | bp_bank_account_metasfresh_S0337_200 |
      | p_2_2_S0337             | true                     | 0.00        | 107.1      | inv_2_S0337_200             | 2023-10-24  | bpartner_1_S0337             | bp_bank_account_metasfresh_S0337_200 |
    And validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | paymentTerm   | processed | docStatus | OPT.IsPaid |
      | inv_1_S0337_200         | bpartner_1_S0337         | l_1_S0337                         | 30 Tage netto | true      | CO        | true       |
      | inv_2_S0337_200         | bpartner_1_S0337         | l_1_S0337                         | 30 Tage netto | true      | CO        | true       |

  @from:cucumber
  @Id:S0337_300
  Scenario: Import one statement, identify org-account by IBAN and link two invoices, but in 2 different bank statements; so the expectation is to have created 2 bank statements

    Given set sys config boolean value true for sys config de.metas.payment.esr.Enabled
	# change the bankaccount of the AD_Org bpartner ("metasfresh") to be an ESR-Account
    And load C_DataImport:
      | C_DataImport_ID.Identifier | OPT.C_DataImport_ID |
      | di_1_S0337_300             | 540009              |
    And metasfresh contains C_Bank:
      | C_Bank_ID.Identifier | Name           | RoutingNo | SwiftCode   | C_DataImport_ID.Identifier |
      | b_1_S0337_300        | bank_S0337_300 | 2234567   | AAAAAAAA82A | di_1_S0337_300             |
    And load C_BP_BankAccount:
      | C_BP_BankAccount_ID.Identifier       | OPT.C_BP_BankAccount_ID |
      | bp_bank_account_metasfresh_S0337_300 | 2000257                 |

    And update C_BP_BankAccount:
      | C_BP_BankAccount_ID.Identifier       | OPT.C_Currency.ISO_Code | OPT.IsEsrAccount | OPT.AccountNo | OPT.ESR_RenderedAccountNo | OPT.IBAN              |
      | bp_bank_account_metasfresh_S0337_300 | CHF                     | Y                | 1234567890    | 123456789                 | CH3908704016075473007 |

	  # create 2 invoices; we expect the sales invoice to end up with the ESR-reference ReferenceNo_metasfresh_S0337_300_2
    Given metasfresh contains C_Invoice:
      | Identifier      | C_BPartner_ID.Identifier | C_DocTypeTarget_ID.Name | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code | OPT.DocumentNo        |
      | inv_1_S0337_300 | bpartner_1_S0337         | Ausgangsrechnung        | 2022-05-12   | Spot                     | true    | CHF                 | 511004_1_SO_S0337_300 |
      | inv_2_S0337_300 | bpartner_1_S0337         | Ausgangsrechnung        | 2022-05-12   | Spot                     | true    | CHF                 | 511004_2_SO_S0337_300 |

    And metasfresh contains C_InvoiceLines
      | Identifier       | C_Invoice_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | C_UOM_ID.X12DE355 |
      | invl_1_S0337_300 | inv_1_S0337_300         | p_1_S0337               | 10          | PCE               |
      | invl_2_S0337_300 | inv_2_S0337_300         | p_1_S0337               | 9           | PCE               |
    And the invoice identified by inv_1_S0337_300 is completed
    And the invoice identified by inv_2_S0337_300 is completed

    And load C_ReferenceNo:
      | C_ReferenceNo_ID.Identifier        | Record_ID.Identifier | C_ReferenceNo_Type_ID.Identifier |
      | ReferenceNo_metasfresh_S0337_300_1 | inv_1_S0337_300      | 540006                           |
      | ReferenceNo_metasfresh_S0337_300_2 | inv_2_S0337_300      | 540006                           |

    And update C_ReferenceNo:
      | C_ReferenceNo_ID.Identifier        | Record_ID.Identifier | C_ReferenceNo_Type_ID.Identifier | OPT.ReferenceNo                    |
      | ReferenceNo_metasfresh_S0337_300_1 | inv_1_S0337_300      | 540006                           | ReferenceNo_metasfresh_S0337_300_1 |
      | ReferenceNo_metasfresh_S0337_300_2 | inv_2_S0337_300      | 540006                           | ReferenceNo_metasfresh_S0337_300_2 |

    When bank statement is imported with identifiers bs_1_S0337_300_1, bs_1_S0337_300_2, matching invoice amounts
    """
<?xml version="1.0" encoding="UTF-8"?>
<Document xmlns="urn:iso:std:iso:20022:tech:xsd:camt.053.001.04">
	<BkToCstmrStmt>
		<GrpHdr>
			<MsgId>2023102706963268</MsgId>
			<CreDtTm>2023-10-27T15:15:37</CreDtTm>
			<MsgPgntn>
				<PgNb>1</PgNb>
				<LastPgInd>true</LastPgInd>
			</MsgPgntn>
		</GrpHdr>
		<Stmt>
			<Id>2023102706963268</Id>
			<ElctrncSeqNb>39</ElctrncSeqNb>
			<CreDtTm>2023-10-27T15:15:37</CreDtTm>
			<FrToDt>
				<FrDtTm>2023-10-24T00:00:00</FrDtTm>
				<ToDtTm>2023-10-26T23:59:59</ToDtTm>
			</FrToDt>
			<CpyDplctInd>DUPL</CpyDplctInd>
			<Acct>
				<Id>
					<IBAN>CH3908704016075473007</IBAN>
				</Id>
				<Ccy>CHF</Ccy>
				<Ownr>
					<Nm>Our-Name</Nm>
					<PstlAdr>
						<AdrLine>Our-AdrLine 1</AdrLine>
						<AdrLine>Our AdrLine 2</AdrLine>
					</PstlAdr>
				</Ownr>
				<Svcr>
					<FinInstnId>
						<BICFI>AAAAAAAA82A</BICFI>
						<Nm>Our Bank</Nm>
					</FinInstnId>
				</Svcr>
			</Acct>
			<Bal>
				<!-- opening balance -->
				<Tp>
					<CdOrPrtry>
						<Cd>OPBD</Cd>
					</CdOrPrtry>
				</Tp>
				<Amt Ccy="CHF">1000.07</Amt>
				<CdtDbtInd>CRDT</CdtDbtInd>
				<Dt>
					<Dt>2023-10-23</Dt>
				</Dt>
			</Bal>
			<Bal>
				<!-- closing balance -->
				<Tp>
					<CdOrPrtry>
						<Cd>CLBD</Cd>
					</CdOrPrtry>
				</Tp>
				<Amt Ccy="CHF">1107.17</Amt>
				<CdtDbtInd>CRDT</CdtDbtInd>
				<Dt>
					<Dt>2023-10-26</Dt>
				</Dt>
			</Bal>
			<Ntry>
				<!-- plus 107.1 CHF -->
				<Amt Ccy="CHF">107.1</Amt>
				<CdtDbtInd>CRDT</CdtDbtInd>
				<RvslInd>false</RvslInd>
				<Sts>BOOK</Sts>
				<BookgDt>
					<Dt>2023-10-24</Dt>
				</BookgDt>
				<ValDt>
					<Dt>2023-10-24</Dt>
				</ValDt>
				<BkTxCd>
					<Domn>
						<Cd>PMNT</Cd>
						<Fmly>
							<Cd>RCDT</Cd>
							<SubFmlyCd>OTHR</SubFmlyCd>
						</Fmly>
					</Domn>
				</BkTxCd>
				<NtryDtls>
					<Btch>
						<NbOfTxs>1</NbOfTxs>
						<TtlAmt Ccy="CHF">107.1</TtlAmt>
						<CdtDbtInd>CRDT</CdtDbtInd>
					</Btch>
					<TxDtls>
						<Refs>
							<AcctSvcrRef>AcctSvcrRef/1/1</AcctSvcrRef>
							<EndToEndId>EndToEndId/1/1</EndToEndId>
							<TxId>TxId/1/1</TxId>
						</Refs>
						<Amt Ccy="CHF">107.1</Amt>
						<CdtDbtInd>CRDT</CdtDbtInd>
						<AmtDtls>
							<InstdAmt>
								<Amt Ccy="CHF">107.1</Amt>
							</InstdAmt>
							<TxAmt>
								<Amt Ccy="CHF">107.1</Amt>
							</TxAmt>
						</AmtDtls>
						<RmtInf>
							<Ustrd>ESR-Referenz ReferenceNo_metasfresh_S0337_300_2</Ustrd>
						</RmtInf>
						<AddtlTxInf>Incoming payment</AddtlTxInf>
					</TxDtls>
				</NtryDtls>
				<AddtlNtryInf>Incoming payment</AddtlNtryInf>
			</Ntry>
		</Stmt>

		<Stmt>
			<Id>2023102706963268</Id>
			<ElctrncSeqNb>39</ElctrncSeqNb>
			<CreDtTm>2023-10-27T15:15:37</CreDtTm>
			<FrToDt>
				<FrDtTm>2023-10-24T00:00:00</FrDtTm>
				<ToDtTm>2023-10-26T23:59:59</ToDtTm>
			</FrToDt>
			<CpyDplctInd>DUPL</CpyDplctInd>
			<Acct>
				<Id>
					<IBAN>CH3908704016075473007</IBAN>
				</Id>
				<Ccy>CHF</Ccy>
				<Ownr>
					<Nm>Our-Name</Nm>
					<PstlAdr>
						<AdrLine>Our-AdrLine 1</AdrLine>
						<AdrLine>Our AdrLine 2</AdrLine>
					</PstlAdr>
				</Ownr>
				<Svcr>
					<FinInstnId>
						<BICFI>AAAAAAAA82A</BICFI>
						<Nm>Our Bank</Nm>
					</FinInstnId>
				</Svcr>
			</Acct>
			<Bal>
				<!-- opening balance -->
				<Tp>
					<CdOrPrtry>
						<Cd>OPBD</Cd>
					</CdOrPrtry>
				</Tp>
				<Amt Ccy="CHF">1000.07</Amt>
				<CdtDbtInd>CRDT</CdtDbtInd>
				<Dt>
					<Dt>2023-10-23</Dt>
				</Dt>
			</Bal>
			<Bal>
				<!-- closing balance -->
				<Tp>
					<CdOrPrtry>
						<Cd>CLBD</Cd>
					</CdOrPrtry>
				</Tp>
				<Amt Ccy="CHF">1119.07</Amt>
				<CdtDbtInd>CRDT</CdtDbtInd>
				<Dt>
					<Dt>2023-10-26</Dt>
				</Dt>
			</Bal>
			<Ntry>
				<!-- plus 119 CHF -->
				<Amt Ccy="CHF">119</Amt>
				<CdtDbtInd>CRDT</CdtDbtInd>
				<RvslInd>false</RvslInd>
				<Sts>BOOK</Sts>
				<BookgDt>
					<Dt>2023-10-26</Dt>
				</BookgDt>
				<ValDt>
					<Dt>2023-10-26</Dt>
				</ValDt>
				<NtryDtls>
					<Btch>
						<NbOfTxs>1</NbOfTxs>
						<TtlAmt Ccy="CHF">119</TtlAmt>
						<CdtDbtInd>CRDT</CdtDbtInd>
					</Btch>
					<TxDtls>
						<Refs>
							<MsgId>MsgId/2</MsgId>
							<AcctSvcrRef>AcctSvcrRef/2/1</AcctSvcrRef>
							<PmtInfId>PmtInfId/2/1</PmtInfId>
							<InstrId>InstrId/2/1</InstrId>
							<EndToEndId>EndToEndId/2/1</EndToEndId>
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
						<RmtInf>
							<Ustrd>511004_1_SO_S0337_300</Ustrd>
						</RmtInf>
						<AddtlTxInf>Incoming payment</AddtlTxInf>
					</TxDtls>
				</NtryDtls>
				<AddtlNtryInf>Incoming payment</AddtlNtryInf>
			</Ntry>
		</Stmt>
	</BkToCstmrStmt>
</Document>
    """

    Then validate C_BankStatement
      | C_BankStatement_ID.Identifier | OPT.BeginningBalance | OPT.EndingBalance | OPT.StatementDifference | OPT.Processed | OPT.C_BP_BankAccount_ID.Identifier   | OPT.StatementDate | OPT.IsReconciled |
      | bs_1_S0337_300_1              | 1000.07              | 1107.17           | 107.1                   | false         | bp_bank_account_metasfresh_S0337_300 | 2023-10-27        | false            |
    And load C_BankStatementLine
      | C_BankStatementLine_ID.Identifier | C_BankStatement_ID.Identifier | Line |
      | bsl_1_S0337_300_1                 | bs_1_S0337_300_1              | 10   |
    And validate C_BankStatementLine
      | C_BankStatementLine_ID.Identifier | OPT.ValutaDate | OPT.DateAcct | OPT.C_Currency_ID.ISO_Code | OPT.TrxAmt | OPT.C_BPartner_ID.Identifier | OPT.C_Invoice_ID.Identifier |
      | bsl_1_S0337_300_1                 | 2023-10-24     | 2023-10-24   | CHF                        | 107.1      | bpartner_1_S0337             | inv_2_S0337_300             |

    And the C_BankStatement identified by bs_1_S0337_300_1 is completed

    Then validate C_BankStatement
      | C_BankStatement_ID.Identifier | OPT.BeginningBalance | OPT.EndingBalance | OPT.StatementDifference | OPT.Processed | OPT.C_BP_BankAccount_ID.Identifier   | OPT.StatementDate | OPT.IsReconciled |
      | bs_1_S0337_300_2              | 1000.07              | 1119.07           | 119                     | false         | bp_bank_account_metasfresh_S0337_300 | 2023-10-27        | false            |
    And load C_BankStatementLine
      | C_BankStatementLine_ID.Identifier | C_BankStatement_ID.Identifier | Line |
      | bsl_1_S0337_300_2                 | bs_1_S0337_300_2              | 10   |
    And validate C_BankStatementLine
      | C_BankStatementLine_ID.Identifier | OPT.ValutaDate | OPT.DateAcct | OPT.C_Currency_ID.ISO_Code | OPT.TrxAmt | OPT.C_BPartner_ID.Identifier | OPT.C_Invoice_ID.Identifier |
      | bsl_1_S0337_300_2                 | 2023-10-26     | 2023-10-26   | CHF                        | 119        | bpartner_1_S0337             | inv_1_S0337_300             |

    And the C_BankStatement identified by bs_1_S0337_300_2 is completed

    And after not more than 60s, C_Payment is found
      | C_Payment_ID.Identifier | OPT.C_BankStatement_ID.Identifier | OPT.C_BankStatementLine_ID.Identifier |
      | p_3_1_S0337             | bs_1_S0337_300_1                  | bsl_1_S0337_300_1                     |
      | p_3_2_S0337             | bs_1_S0337_300_2                  | bsl_1_S0337_300_2                     |
    And validate payments
      | C_Payment_ID.Identifier | C_Payment_ID.IsAllocated | OPT.OpenAmt | OPT.PayAmt | OPT.C_Invoice_ID.Identifier | OPT.DateTrx | OPT.C_BPartner_ID.Identifier | OPT.C_BP_BankAccount_ID.Identifier   |
      | p_3_1_S0337             | true                     | 0.00        | 107.1      | inv_2_S0337_300             | 2023-10-24  | bpartner_1_S0337             | bp_bank_account_metasfresh_S0337_300 |
      | p_3_2_S0337             | true                     | 0.00        | 119        | inv_1_S0337_300             | 2023-10-26  | bpartner_1_S0337             | bp_bank_account_metasfresh_S0337_300 |
    And validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | paymentTerm   | processed | docStatus | OPT.IsPaid |
      | inv_1_S0337_300         | bpartner_1_S0337         | l_1_S0337                         | 30 Tage netto | true      | CO        | true       |
      | inv_2_S0337_300         | bpartner_1_S0337         | l_1_S0337                         | 30 Tage netto | true      | CO        | true       |

    And set sys config boolean value false for sys config de.metas.payment.esr.Enabled

  @Id:S0337_400
  Scenario: Import one statement, identify org-account by IBAN, and import one summary line, for 3 DEBIT transactions, in the same NTry, with one transaction on EUR

    Given set sys config boolean value true for sys config de.metas.payment.esr.Enabled
	# change the bankaccount of the AD_Org bpartner ("metasfresh") to be an ESR-Account
    And load C_DataImport:
      | C_DataImport_ID.Identifier | OPT.C_DataImport_ID |
      | di_1_S0337_400             | 540009              |
    And metasfresh contains C_Bank:
      | C_Bank_ID.Identifier | Name           | RoutingNo | SwiftCode   | C_DataImport_ID.Identifier |
      | b_1_S0337_400        | bank_S0337_400 | 2234567   | AAAAAAAA82A | di_1_S0337_400             |
    And load C_BP_BankAccount:
      | C_BP_BankAccount_ID.Identifier       | OPT.C_BP_BankAccount_ID |
      | bp_bank_account_metasfresh_S0337_400 | 2000257                 |

    And update C_BP_BankAccount:
      | C_BP_BankAccount_ID.Identifier       | OPT.C_Currency.ISO_Code | OPT.IsEsrAccount | OPT.AccountNo | OPT.ESR_RenderedAccountNo | OPT.IBAN              |
      | bp_bank_account_metasfresh_S0337_400 | CHF                     | Y                | 1234567890    | 123456789                 | CH3908704016075473007 |

	  # create 1 invoice; we expect the sales invoice to end up with the Document no 511004_S0337_400
    Given metasfresh contains C_Invoice:
      | Identifier      | C_BPartner_ID.Identifier | C_DocTypeTarget_ID.Name | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code | OPT.DocumentNo   |
      | inv_1_S0337_400 | bpartner_1_S0337         | Ausgangsrechnung        | 2023-10-12   | Spot                     | true    | CHF                 | 511004_S0337_400 |

    And metasfresh contains C_InvoiceLines
      | Identifier       | C_Invoice_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | C_UOM_ID.X12DE355 |
      | invl_1_S0337_400 | inv_1_S0337_400         | p_1_S0337               | 10          | PCE               |
    And the invoice identified by inv_1_S0337_400 is completed

    When bank statement is imported with identifiers bs_1_S0337_400_1, matching invoice amounts
    """
<?xml version="1.0" encoding="utf-8"?>
<Document xmlns="urn:iso:std:iso:20022:tech:xsd:camt.053.001.04">
	<BkToCstmrStmt>
		<GrpHdr>
			<MsgId>MsgId11111111111111</MsgId>
			<CreDtTm>2023-10-26T19:45:37.000Z</CreDtTm>
			<MsgPgntn>
				<PgNb>1</PgNb>
				<LastPgInd>true</LastPgInd>
			</MsgPgntn>
		</GrpHdr>
		<Stmt>
			<Id>StmtId11111111111111</Id>
			<ElctrncSeqNb>00001</ElctrncSeqNb>
			<CreDtTm>2023-10-26T19:45:37.000Z</CreDtTm>
			<FrToDt>
				<FrDtTm>2023-10-23T00:00:00</FrDtTm>
				<ToDtTm>2023-10-27T23:59:59</ToDtTm>
			</FrToDt>
			<Acct>
				<Id>
					<IBAN>CH3908704016075473007</IBAN>
				</Id>
				<Ccy>CHF</Ccy>
				<Ownr>
					<Nm>Test CO</Nm>
					<PstlAdr>
						<AdrLine>Street 123</AdrLine>
						<AdrLine>0000 CityTest</AdrLine>
					</PstlAdr>
				</Ownr>
				<Svcr>
					<FinInstnId>
						<BICFI>AAAAAAAA82A</BICFI>
						<Nm>Test Bank</Nm>
					</FinInstnId>
				</Svcr>
			</Acct>
			<Bal>
				<Tp>
					<CdOrPrtry>
						<Cd>OPBD</Cd>
					</CdOrPrtry>
				</Tp>
				<Amt Ccy="CHF">50000.50</Amt>
				<CdtDbtInd>CRDT</CdtDbtInd>
				<Dt>
					<Dt>2023-10-23</Dt>
				</Dt>
			</Bal>
			<Bal>
				<Tp>
					<CdOrPrtry>
						<Cd>CLBD</Cd>
					</CdOrPrtry>
				</Tp>
				<Amt Ccy="CHF">>28152.55</Amt>
				<CdtDbtInd>CRDT</CdtDbtInd>
				<Dt>
					<Dt>2023-10-27</Dt>
				</Dt>
			</Bal>
			<Ntry>
				<!-- plus 107.7 CHF -->
				<NtryRef>00001</NtryRef>
				<Amt Ccy="CHF">107.7</Amt>
				<CdtDbtInd>CRDT</CdtDbtInd>
				<Sts>BOOK</Sts>
				<BookgDt>
					<Dt>2023-10-24</Dt>
				</BookgDt>
				<ValDt>
					<Dt>2023-10-24</Dt>
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
						<TtlAmt Ccy="CHF">107.7</TtlAmt>
						<CdtDbtInd>CRDT</CdtDbtInd>
					</Btch>
					<TxDtls>
						<Refs>
							<AcctSvcrRef>AAA-1111111</AcctSvcrRef>
							<PmtInfId>AAAAAA1111:11:11AAAA2022</PmtInfId>
						</Refs>
						<Amt Ccy="CHF">107.7</Amt>
						<CdtDbtInd>CRDT</CdtDbtInd>
						<AmtDtls>
							<InstdAmt>
								<Amt Ccy="CHF">107.7</Amt>
							</InstdAmt>
							<TxAmt>
								<Amt Ccy="CHF">107.7</Amt>
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
							<Ustrd>511004_S0337_400</Ustrd>
						</RmtInf>
					</TxDtls>
				</NtryDtls>
			</Ntry>
			<Ntry>
				<!-- minus 20002.50 CHF -->
				<Amt Ccy="CHF">20002.50</Amt>
				<CdtDbtInd>DBIT</CdtDbtInd>
				<RvslInd>false</RvslInd>
				<Sts>BOOK</Sts>
				<BookgDt>
					<Dt>2023-10-26</Dt>
				</BookgDt>
				<ValDt>
					<Dt>2023-10-26</Dt>
				</ValDt>
				<BkTxCd>
					<Domn>
						<Cd>PMNT</Cd>
						<Fmly>
							<Cd>ICDT</Cd>
							<SubFmlyCd>OTHR</SubFmlyCd>
						</Fmly>
					</Domn>
				</BkTxCd>
				<NtryDtls>
					<Btch>
						<NbOfTxs>2</NbOfTxs>
						<TtlAmt Ccy="CHF">20002.50</TtlAmt>
						<CdtDbtInd>DBIT</CdtDbtInd>
					</Btch>
					<TxDtls>
						<Refs>
							<MsgId>MsgId/2</MsgId>
							<AcctSvcrRef>AcctSvcrRef/2/1</AcctSvcrRef>
							<PmtInfId>PmtInfId/2/1</PmtInfId>
							<InstrId>InstrId/2/1</InstrId>
							<EndToEndId>EndToEndId/2/1</EndToEndId>
						</Refs>
						<Amt Ccy="CHF">10001.20</Amt>
						<CdtDbtInd>DBIT</CdtDbtInd>
						<AmtDtls>
							<InstdAmt>
								<Amt Ccy="CHF">10001.20</Amt>
							</InstdAmt>
							<TxAmt>
								<Amt Ccy="CHF">10001.20</Amt>
							</TxAmt>
						</AmtDtls>
						<RmtInf>
							<Ustrd>Finance 2023</Ustrd>
						</RmtInf>
						<AddtlTxInf>Vergütung</AddtlTxInf>
					</TxDtls>
					<TxDtls>
						<Refs>
							<MsgId>MsgId/2</MsgId>
							<AcctSvcrRef>AcctSvcrRef/2/2</AcctSvcrRef>
							<PmtInfId>PmtInfId/2/2</PmtInfId>
							<InstrId>InstrId/2/2</InstrId>
							<EndToEndId>EndToEndId/2/2</EndToEndId>
						</Refs>
						<Amt Ccy="CHF">10001.30</Amt>
						<CdtDbtInd>DBIT</CdtDbtInd>
						<AmtDtls>
							<InstdAmt>
								<Amt Ccy="CHF">10001.30</Amt>
							</InstdAmt>
							<TxAmt>
								<Amt Ccy="CHF">10001.30</Amt>
							</TxAmt>
						</AmtDtls>
						<RmtInf>
							<Ustrd>Finance 2023</Ustrd>
						</RmtInf>
						<AddtlTxInf>Vergütung</AddtlTxInf>
					</TxDtls>
				</NtryDtls>
				<AddtlNtryInf>Vergütung</AddtlNtryInf>
			</Ntry>

			<Ntry>
				<Amt Ccy="CHF">1953.15</Amt>
				<CdtDbtInd>DBIT</CdtDbtInd>
				<RvslInd>false</RvslInd>
				<Sts>BOOK</Sts>
				<BookgDt>
					<Dt>2023-10-25</Dt>
				</BookgDt>
				<ValDt>
					<Dt>2023-10-25</Dt>
				</ValDt>
				<BkTxCd>
					<Domn>
						<Cd>PMNT</Cd>
						<Fmly>
							<Cd>ICDT</Cd>
							<SubFmlyCd>OTHR</SubFmlyCd>
						</Fmly>
					</Domn>
				</BkTxCd>
				<NtryDtls>
					<Btch>
						<NbOfTxs>1</NbOfTxs>
						<TtlAmt Ccy="CHF">1953.15</TtlAmt>
						<CdtDbtInd>DBIT</CdtDbtInd>
					</Btch>
					<TxDtls>
					<Refs>
							<AcctSvcrRef>BBB-1111111</AcctSvcrRef>
							<PmtInfId>BBBBBB1111:11:11ABBB2023</PmtInfId>
						</Refs>
						<Amt Ccy="CHF">1953.15</Amt>
						<CdtDbtInd>DBIT</CdtDbtInd>
						<AmtDtls>
							<InstdAmt>
								<Amt Ccy="EUR">2028</Amt>
							</InstdAmt>
							<TxAmt>
								<Amt Ccy="EUR">2028</Amt>
								<CcyXchg>
									<SrcCcy>CHF</SrcCcy>
									<TrgtCcy>EUR</TrgtCcy>
									<XchgRate>.9631</XchgRate>
								</CcyXchg>
							</TxAmt>
						</AmtDtls>
						<RltdPties>
							<Dbtr>
								<Nm>Test CO</Nm>
								<PstlAdr>
									<AdrLine>Street 123</AdrLine>
									<AdrLine>0000 CityTest</AdrLine>
								</PstlAdr>
							</Dbtr>
							<Cdtr>
								<Nm>aTest GmbH</Nm>
								<PstlAdr>
									<AdrLine>Street 4</AdrLine>
									<AdrLine>00000 City</AdrLine>
								</PstlAdr>
							</Cdtr>
							<CdtrAcct>
								<Id>
									<IBAN>DE78500105174126332716</IBAN>
								</Id>
							</CdtrAcct>
						</RltdPties>
						<RltdAgts>
							<CdtrAgt>
								<FinInstnId>
									<BICFI>BEVODEBBXXX</BICFI>
								</FinInstnId>
							</CdtrAgt>
						</RltdAgts>
						<RmtInf>
							<Ustrd>Some outgoing Payment</Ustrd>
						</RmtInf>
						<AddtlTxInf>Vergütung</AddtlTxInf>
					</TxDtls>
				</NtryDtls>
				<AddtlNtryInf>Vergütung</AddtlNtryInf>
			</Ntry>

		</Stmt>
	</BkToCstmrStmt>
</Document>
    """

    Then validate C_BankStatement
      | C_BankStatement_ID.Identifier | OPT.BeginningBalance | OPT.EndingBalance | OPT.StatementDifference | OPT.Processed | OPT.C_BP_BankAccount_ID.Identifier   | OPT.StatementDate | OPT.IsReconciled |
      | bs_1_S0337_400_1              | 50000.50             | 28152.55          | -21847.95               | false         | bp_bank_account_metasfresh_S0337_400 | 2023-10-26        | false            |
    And load C_BankStatementLine
      | C_BankStatementLine_ID.Identifier | C_BankStatement_ID.Identifier | Line |
      | bsl_1_S0337_400_1                 | bs_1_S0337_400_1              | 10   |
      | bsl_1_S0337_400_2                 | bs_1_S0337_400_1              | 20   |
      | bsl_1_S0337_400_3                 | bs_1_S0337_400_1              | 30   |
      | bsl_1_S0337_400_4                 | bs_1_S0337_400_1              | 40   |
      | bsl_1_S0337_400_5                 | bs_1_S0337_400_1              | 50   |
    And validate C_BankStatementLine
      | C_BankStatementLine_ID.Identifier | OPT.ValutaDate | OPT.DateAcct | OPT.C_Currency_ID.ISO_Code | OPT.TrxAmt | OPT.C_BPartner_ID.Identifier | OPT.C_Invoice_ID.Identifier |
      | bsl_1_S0337_400_1                 | 2023-10-24     | 2023-10-24   | CHF                        | 107.7      |                              |                             |
      | bsl_1_S0337_400_2                 | 2023-10-26     | 2023-10-26   | CHF                        | -20002.50  |                              |                             |
      | bsl_1_S0337_400_3                 | 2023-10-26     | 2023-10-26   | CHF                        | 0          |                              |                             |
      | bsl_1_S0337_400_4                 | 2023-10-26     | 2023-10-26   | CHF                        | 0          |                              |                             |
      | bsl_1_S0337_400_5                 | 2023-10-25     | 2023-10-25   | CHF                        | -1953.15   |                              |                             |

  @from:cucumber
  @Id:S0337_500
  Scenario: Import one statement, identify org-account by IBAN and import as one summary line

    Given set sys config boolean value true for sys config de.metas.payment.esr.Enabled
	# change the bankaccount of the AD_Org bpartner ("metasfresh") to be an ESR-Account
    And load C_DataImport:
      | C_DataImport_ID.Identifier | OPT.C_DataImport_ID |
      | di_1_S0337_500             | 540009              |
    And metasfresh contains C_Bank:
      | C_Bank_ID.Identifier | Name           | RoutingNo | SwiftCode   | C_DataImport_ID.Identifier | OPT.IsImportAsSingleSummaryLine |
	  | di_1_S0337_500       | bank_S0337_500 | 2234567   | AAAAAAAA82A | di_1_S0337_500             | Y                               |
    And load C_BP_BankAccount:
      | C_BP_BankAccount_ID.Identifier       | OPT.C_BP_BankAccount_ID | OPT.C_Bank_ID  |
      | bp_bank_account_metasfresh_S0337_500 | 2000257                 | di_1_S0337_500 |

    And update C_BP_BankAccount:
		| C_BP_BankAccount_ID.Identifier       | OPT.C_Currency.ISO_Code | OPT.IsEsrAccount | OPT.AccountNo | OPT.ESR_RenderedAccountNo | OPT.IBAN              | OPT.C_Bank_ID.Identifier |
		| bp_bank_account_metasfresh_S0337_500 | CHF                     | Y                | 1234567890    | 123456789                 | CH3908704016075473007 | di_1_S0337_500           |

	  # create 2 invoices; we expect the sales invoice to end up with the ESR-reference 123456700102156434010001795
    Given metasfresh contains C_Invoice:
      | Identifier      | C_BPartner_ID.Identifier | C_DocTypeTarget_ID.Name | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code | OPT.DocumentNo        |
      | inv_1_S0337_500 | bpartner_1_S0337         | Ausgangsrechnung        | 2022-05-12   | Spot                     | true    | CHF                 | 511004_1_SO_S0337_500 |
      | inv_2_S0337_500 | bpartner_1_S0337         | Ausgangsrechnung        | 2022-05-12   | Spot                     | true    | CHF                 | 511004_2_SO_S0337_500 |

    And metasfresh contains C_InvoiceLines
      | Identifier       | C_Invoice_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | C_UOM_ID.X12DE355 |
      | invl_1_S0337_500 | inv_1_S0337_500         | p_1_S0337               | 10          | PCE               |
      | invl_2_S0337_500 | inv_2_S0337_500         | p_1_S0337               | 9           | PCE               |
    And the invoice identified by inv_1_S0337_500 is completed
    And the invoice identified by inv_2_S0337_500 is completed

    And load C_ReferenceNo:
      | C_ReferenceNo_ID.Identifier        | Record_ID.Identifier | C_ReferenceNo_Type_ID.Identifier |
      | ReferenceNo_metasfresh_S0337_500_1 | inv_1_S0337_500      | 540006                           |
      | ReferenceNo_metasfresh_S0337_500_2 | inv_2_S0337_500      | 540006                           |

    And update C_ReferenceNo:
      | C_ReferenceNo_ID.Identifier        | Record_ID.Identifier | C_ReferenceNo_Type_ID.Identifier | OPT.ReferenceNo                    |
      | ReferenceNo_metasfresh_S0337_500_1 | inv_1_S0337_500      | 540006                           | ReferenceNo_metasfresh_S0337_500_1 |
      | ReferenceNo_metasfresh_S0337_500_2 | inv_2_S0337_500      | 540006                           | ReferenceNo_metasfresh_S0337_500_2 |

    When bank statement is imported with identifiers bs_1_S0337_500, matching invoice amounts
    """
<?xml version="1.0" encoding="UTF-8"?>
<Document xmlns="urn:iso:std:iso:20022:tech:xsd:camt.053.001.04">
	<BkToCstmrStmt>
		<GrpHdr>
			<MsgId>2023102706963268</MsgId>
			<CreDtTm>2023-10-27T15:15:37</CreDtTm>
			<MsgPgntn>
				<PgNb>1</PgNb>
				<LastPgInd>true</LastPgInd>
			</MsgPgntn>
		</GrpHdr>
		<Stmt>
			<Id>2023102706963268</Id>
			<ElctrncSeqNb>39</ElctrncSeqNb>
			<CreDtTm>2023-10-27T15:15:37</CreDtTm>
			<FrToDt>
				<FrDtTm>2023-10-24T00:00:00</FrDtTm>
				<ToDtTm>2023-10-26T23:59:59</ToDtTm>
			</FrToDt>
			<CpyDplctInd>DUPL</CpyDplctInd>
			<Acct>
				<Id>
					<IBAN>CH3908704016075473007</IBAN>
				</Id>
				<Ccy>CHF</Ccy>
				<Ownr>
					<Nm>Our-Name</Nm>
					<PstlAdr>
						<AdrLine>Our-AdrLine 1</AdrLine>
						<AdrLine>Our AdrLine 2</AdrLine>
					</PstlAdr>
				</Ownr>
				<Svcr>
					<FinInstnId>
						<BICFI>AAAAAAAA82A</BICFI>
						<Nm>Our Bank</Nm>
					</FinInstnId>
				</Svcr>
			</Acct>
			<Bal>
				<!-- opening balance -->
				<Tp>
					<CdOrPrtry>
						<Cd>OPBD</Cd>
					</CdOrPrtry>
				</Tp>
				<Amt Ccy="CHF">1000.07</Amt>
				<CdtDbtInd>CRDT</CdtDbtInd>
				<Dt>
					<Dt>2023-10-23</Dt>
				</Dt>
			</Bal>
			<Bal>
				<!-- closing balance -->
				<Tp>
					<CdOrPrtry>
						<Cd>CLBD</Cd>
					</CdOrPrtry>
				</Tp>
				<Amt Ccy="CHF">869.17</Amt>
				<CdtDbtInd>CRDT</CdtDbtInd>
				<Dt>
					<Dt>2023-10-26</Dt>
				</Dt>
			</Bal>
			<Ntry>
				<!-- plus 107.1 CHF -->
				<Amt Ccy="CHF">107.1</Amt>
				<CdtDbtInd>CRDT</CdtDbtInd>
				<RvslInd>false</RvslInd>
				<Sts>BOOK</Sts>
				<BookgDt>
					<Dt>2023-10-24</Dt>
				</BookgDt>
				<ValDt>
					<Dt>2023-10-24</Dt>
				</ValDt>
				<BkTxCd>
					<Domn>
						<Cd>PMNT</Cd>
						<Fmly>
							<Cd>RCDT</Cd>
							<SubFmlyCd>OTHR</SubFmlyCd>
						</Fmly>
					</Domn>
				</BkTxCd>
				<NtryDtls>
					<Btch>
						<NbOfTxs>1</NbOfTxs>
						<TtlAmt Ccy="CHF">107.1</TtlAmt>
						<CdtDbtInd>CRDT</CdtDbtInd>
					</Btch>
					<TxDtls>
						<Refs>
							<AcctSvcrRef>AcctSvcrRef/1/1</AcctSvcrRef>
							<EndToEndId>EndToEndId/1/1</EndToEndId>
							<TxId>TxId/1/1</TxId>
						</Refs>
						<Amt Ccy="CHF">107.1</Amt>
						<CdtDbtInd>CRDT</CdtDbtInd>
						<AmtDtls>
							<InstdAmt>
								<Amt Ccy="CHF">107.1</Amt>
							</InstdAmt>
							<TxAmt>
								<Amt Ccy="CHF">107.1</Amt>
							</TxAmt>
						</AmtDtls>
						<RmtInf>
							<Ustrd>ESR-Referenz ReferenceNo_metasfresh_S0337_500_2</Ustrd>
						</RmtInf>
						<AddtlTxInf>Incoming payment</AddtlTxInf>
					</TxDtls>
				</NtryDtls>
				<AddtlNtryInf>Incoming payment</AddtlNtryInf>
			</Ntry>
			<Ntry>
				<!-- plus 119 CHF -->
				<Amt Ccy="CHF">119</Amt>
				<CdtDbtInd>CRDT</CdtDbtInd>
				<RvslInd>false</RvslInd>
				<Sts>BOOK</Sts>
				<BookgDt>
					<Dt>2023-10-26</Dt>
				</BookgDt>
				<ValDt>
					<Dt>2023-10-26</Dt>
				</ValDt>
				<NtryDtls>
					<Btch>
						<NbOfTxs>1</NbOfTxs>
						<TtlAmt Ccy="CHF">119</TtlAmt>
						<CdtDbtInd>CRDT</CdtDbtInd>
					</Btch>
					<TxDtls>
						<Refs>
							<MsgId>MsgId/2</MsgId>
							<AcctSvcrRef>AcctSvcrRef/2/1</AcctSvcrRef>
							<PmtInfId>PmtInfId/2/1</PmtInfId>
							<InstrId>InstrId/2/1</InstrId>
							<EndToEndId>EndToEndId/2/1</EndToEndId>
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
						<RmtInf>
							<Ustrd>511004_1_SO_S0337_500</Ustrd>
						</RmtInf>
						<AddtlTxInf>Incoming payment</AddtlTxInf>
					</TxDtls>
				</NtryDtls>
				<AddtlNtryInf>Incoming payment</AddtlNtryInf>
			</Ntry>
		</Stmt>
	</BkToCstmrStmt>
</Document>
    """

    Then validate C_BankStatement
      | C_BankStatement_ID.Identifier | OPT.BeginningBalance | OPT.EndingBalance | OPT.StatementDifference | OPT.Processed | OPT.C_BP_BankAccount_ID.Identifier   | OPT.StatementDate | OPT.IsReconciled |
      | bs_1_S0337_500                | 1000.07              | 1226.17           | 226.1                   | false         | bp_bank_account_metasfresh_S0337_500 | 2023-10-27        | false            |
    And load C_BankStatementLine
      | C_BankStatementLine_ID.Identifier | C_BankStatement_ID.Identifier | Line |
      | bsl_1_S0337_500                   | bs_1_S0337_500                | 10   |
      | bsl_2_S0337_500                   | bs_1_S0337_500                | 20   |
      | bsl_3_S0337_500                   | bs_1_S0337_500                | 30   |
    And validate C_BankStatementLine
      | C_BankStatementLine_ID.Identifier | OPT.ValutaDate | OPT.DateAcct | OPT.C_Currency_ID.ISO_Code | OPT.TrxAmt | OPT.C_BPartner_ID.Identifier | OPT.C_Invoice_ID.Identifier |
      | bsl_1_S0337_500                   | 2023-10-27     | 2023-10-27   | CHF                        | 226.1      |                              |                             |
	  | bsl_2_S0337_500                   | 2023-10-24     | 2023-10-24   | CHF                        | 0          | bpartner_1_S0337             | inv_2_S0337_500             |
	  | bsl_3_S0337_500                   | 2023-10-26     | 2023-10-26   | CHF                        | 0          | bpartner_1_S0337             | inv_1_S0337_500             |

    And the C_BankStatement identified by bs_1_S0337_500 is completed

    And set sys config boolean value false for sys config de.metas.payment.esr.Enabled

