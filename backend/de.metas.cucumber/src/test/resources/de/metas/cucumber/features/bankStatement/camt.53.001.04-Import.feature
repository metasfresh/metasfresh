@ignore
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

    And load C_DataImport:
      | C_DataImport_ID.Identifier | OPT.C_DataImport_ID |
      | di_1_S0337                 | 540009              |
    And metasfresh contains C_Bank:
      | C_Bank_ID.Identifier | Name       | RoutingNo | SwiftCode   | C_DataImport_ID.Identifier |
      | b_1_S0337            | bank_S0337 | 2234567   | AAAAAAAA82A | di_1_S0337                 |
## Thanks to https://ssl.ibanrechner.de/sample_accounts.html for providing the IBAN
#    And metasfresh contains C_BP_BankAccount
#      | Identifier  | C_BPartner_ID.Identifier | C_Currency.ISO_Code | OPT.AccountNo | OPT.C_Bank_ID.Identifier |
#      | bpb_1_S0337 | bpartner_1_S0337         | EUR                 | 2234567       | b_1_S0337                |
    And set sys config boolean value false for sys config de.metas.payment.esr.Enabled

  @from:cucumber
  @Id:S0337_100
  Scenario: Import bank statement, identify org-account by IBAN and link one statement-line to a payment for a purchase invoice that is matched via documentNo

    And metasfresh contains C_Invoice:
      | Identifier      | C_BPartner_ID.Identifier | C_DocTypeTarget_ID.Name | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code | OPT.DocumentNo   |
      | inv_1_S0337_100 | bpartner_1_S0337         | Eingangsrechnung        | 2022-05-11   | Spot                     | false   | EUR                 | 511004_S0337_100 |
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
			<MsgId>MsgId11111111111112</MsgId>
			<CreDtTm>2022-05-11T19:45:37.000Z</CreDtTm>
			<MsgPgntn>
				<PgNb>1</PgNb>
				<LastPgInd>true</LastPgInd>
			</MsgPgntn>
		</GrpHdr>
		<Stmt>
			<Id>StmtId11111111111112</Id>
			<ElctrncSeqNb>00001</ElctrncSeqNb>
			<CreDtTm>2022-05-11T19:45:37.000Z</CreDtTm>
			<Acct>
				<Id>
					<IBAN>CH3908704016075473007</IBAN>
				</Id>
				<Ccy>EUR</Ccy>
				<Svcr>
					<FinInstnId>
						<BICFI>AAAAAAAA82A</BICFI>
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
				<Amt Ccy="EUR">0.00</Amt>
				<CdtDbtInd>CRDT</CdtDbtInd>
				<Dt>
					<Dt>2022-05-11</Dt>
				</Dt>
			</Bal>
			<Bal>
				<Tp>
					<CdOrPrtry>
						<Cd>CLAV</Cd>
					</CdOrPrtry>
				</Tp>
				<Amt Ccy="EUR">0.00</Amt>
				<CdtDbtInd>CRDT</CdtDbtInd>
				<Dt>
					<Dt>2022-05-11</Dt>
				</Dt>
			</Bal>
			<Bal>
				<Tp>
					<CdOrPrtry>
						<Cd>FWAV</Cd>
					</CdOrPrtry>
				</Tp>
				<Amt Ccy="EUR">0.00</Amt>
				<CdtDbtInd>CRDT</CdtDbtInd>
				<Dt>
					<Dt>2022-05-11</Dt>
				</Dt>
			</Bal>
			<Ntry>
				<NtryRef>00001</NtryRef>
				<Amt Ccy="EUR">119</Amt>
				<CdtDbtInd>DBT</CdtDbtInd>
				<Sts>BOOK</Sts>
				<BookgDt>
					<Dt>2022-05-11</Dt>
				</BookgDt>
				<ValDt>
					<Dt>2022-05-11</Dt>
				</ValDt>
				<AcctSvcrRef>AAA-1111111</AcctSvcrRef>
				<BkTxCd>
					<Prtry>
						<Cd>TRF</Cd>
						<Issr>SWIFT</Issr>
					</Prtry>
				</BkTxCd>
				<NtryDtls>
					<TxDtls>
						<Refs>
							<AcctSvcrRef>AAA-1111111</AcctSvcrRef>
							<PmtInfId>AAAAAA1111:11:11AAAA2022</PmtInfId>
						</Refs>
						<AmtDtls>
							<TxAmt>
								<Amt Ccy="EUR">119</Amt>
							</TxAmt>
						</AmtDtls>
						<BkTxCd>
							<Domn>
								<Cd>PMNT</Cd>
								<Fmly>
									<Cd>ICDT</Cd>
									<SubFmlyCd>ESCT</SubFmlyCd>
								</Fmly>
							</Domn>
							<Prtry>
								<Cd>NTRF+191</Cd>
							</Prtry>
						</BkTxCd>
						<RmtInf>
							<Ustrd>RechnungsNr 511004_S0337_100</Ustrd>
						</RmtInf>
					</TxDtls>
				</NtryDtls>
			</Ntry>
			<Ntry>
				<NtryRef>00002</NtryRef>
				<Amt Ccy="EUR">41772.00</Amt>
				<CdtDbtInd>CRDT</CdtDbtInd>
				<Sts>BOOK</Sts>
				<BookgDt>
					<Dt>2022-09-07</Dt>
				</BookgDt>
				<ValDt>
					<Dt>2022-09-07</Dt>
				</ValDt>
				<AcctSvcrRef>RMS-11111</AcctSvcrRef>
				<BkTxCd>
					<Prtry>
						<Cd>TRF</Cd>
						<Issr>SWIFT</Issr>
					</Prtry>
				</BkTxCd>
				<NtryDtls>
					<TxDtls>
						<Refs>
							<AcctSvcrRef>RMS-11111</AcctSvcrRef>
							<InstrId>NONREF</InstrId>
						</Refs>
						<AddtlTxInf>OUTWARD REMITTANCE</AddtlTxInf>
					</TxDtls>
				</NtryDtls>
				<AddtlNtryInf>AddtlNtryInf</AddtlNtryInf>
			</Ntry>
		</Stmt>
	</BkToCstmrStmt>
</Document>
    """
    Then validate C_BankStatement
      | C_BankStatement_ID.Identifier | OPT.BeginningBalance | OPT.EndingBalance | OPT.StatementDifference | OPT.Processed | OPT.C_BP_BankAccount_ID.Identifier | OPT.StatementDate | OPT.IsReconciled |
      | bs_1_S0337_100                | 41891                | 83544             | 41653                   | false         | bpb_1_S0337                        | 2022-05-11        | false            |
    And load C_BankStatementLine
      | C_BankStatementLine_ID.Identifier | C_BankStatement_ID.Identifier | Line |
      | bsl_1_S0337_100                   | bs_1_S0337_100                | 10   |
      | bsl_2_S0337_100                   | bs_1_S0337_100                | 20   |
    And validate C_BankStatementLine
      | C_BankStatementLine_ID.Identifier | OPT.ValutaDate | OPT.DateAcct | OPT.C_Currency_ID.ISO_Code | OPT.TrxAmt | OPT.StmtAmt | OPT.StatementLineDate | OPT.C_BPartner_ID.Identifier | OPT.C_Invoice_ID.Identifier | OPT.Processed | OPT.IsReconciled |
      | bsl_1_S0337_100                   | 2022-05-11     | 2022-05-11   | EUR                        | -119       | -119        | 2022-05-11            | bpartner_1_S0337             | inv_1_S0337_100             | false         | false            |
      | bsl_2_S0337_100                   | 2022-09-07     | 2022-09-07   | EUR                        | 41772      | 41772       | 2022-09-07            |                              |                             | false         | false            |
    And the C_BankStatement identified by bs_1_S0337_100 is completed
    And after not more than 60s, C_Payment is found
      | C_Payment_ID.Identifier | OPT.C_BankStatement_ID.Identifier | OPT.C_BankStatementLine_ID.Identifier |
      | p_1_S0337               | bs_1_S0337_100                    | bsl_1_S0337_100                       |
    And validate payments
      | C_Payment_ID.Identifier | C_Payment_ID.IsAllocated | OPT.OpenAmt | OPT.PayAmt | OPT.C_Invoice_ID.Identifier | OPT.DateTrx | OPT.C_BPartner_ID.Identifier | OPT.C_BP_BankAccount_ID.Identifier |
      | p_1_S0337               | true                     | 0.00        | 119        | inv_1_S0337_100             | 2022-05-11  | bpartner_1_S0337             | bpb_1_S0337                        |
    And validate C_BankStatement
      | C_BankStatement_ID.Identifier | OPT.BeginningBalance | OPT.EndingBalance | OPT.StatementDifference | OPT.Processed | OPT.C_BP_BankAccount_ID.Identifier | OPT.StatementDate | OPT.IsReconciled |
      | bs_1_S0337_100                | 41891                | 83544             | 41653                   | true          | bpb_1_S0337                        | 2022-05-11        | false            |
    And validate C_BankStatementLine
      | C_BankStatementLine_ID.Identifier | OPT.ValutaDate | OPT.DateAcct | OPT.C_Currency_ID.ISO_Code | OPT.TrxAmt | OPT.StmtAmt | OPT.StatementLineDate | OPT.C_BPartner_ID.Identifier | OPT.C_Invoice_ID.Identifier | OPT.Processed | OPT.IsReconciled |
      | bsl_1_S0337_100                   | 2022-05-11     | 2022-05-11   | EUR                        | -119       | -119        | 2022-05-11            | bpartner_1_S0337             | inv_1_S0337_100             | true          | true             |
      | bsl_2_S0337_100                   | 2022-09-07     | 2022-09-07   | EUR                        | 41772      | 41772       | 2022-09-07            |                              |                             | true          | false            |
    And validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | paymentTerm   | processed | docStatus | OPT.IsPaid |
      | inv_1_S0337_100         | bpartner_1_S0337         | l_1_S0337                         | 30 Tage netto | true      | CO        | true       |

  @from:cucumber
  @Id:S0337_200
  Scenario: Import one statement, identify org-account by IBAN and link to three invoices one of which is matched via ESR-Reference

    Given set sys config boolean value true for sys config de.metas.payment.esr.Enabled
	# change the bankaccount of the AD_Org bpartner ("metasfresh") to be an ESR-Account
    And load C_BP_BankAccount:
      | C_BP_BankAccount_ID.Identifier       | OPT.C_BP_BankAccount_ID |
      | bp_bank_account_metasfresh_S0337_200 | 2000257                 |

    And update C_BP_BankAccount:
      | C_BP_BankAccount_ID.Identifier       | OPT.C_Currency.ISO_Code | OPT.IsEsrAccount | OPT.AccountNo | OPT.ESR_RenderedAccountNo | OPT.IBAN              |
      | bp_bank_account_metasfresh_S0337_200 | CHF                     | Y                | 1234567890    | 123456789                 | CH3908704016075473007 |

	  # create 3 invoices; we expect the sales invoice to end up with the ESR-reference 123456700102156426010000023
    Given metasfresh contains C_Invoice:
      | Identifier      | C_BPartner_ID.Identifier | C_DocTypeTarget_ID.Name | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code | OPT.DocumentNo        |
      | inv_1_S0337_200 | bpartner_1_S0337         | Eingangsrechnung        | 2022-05-12   | Spot                     | false   | EUR                 | 511004_1_PO_S0337_200 |
      | inv_2_S0337_200 | bpartner_1_S0337         | Eingangsrechnung        | 2022-05-12   | Spot                     | false   | EUR                 | 511004_2_PO_S0337_200 |
      | inv_3_S0337_200 | bpartner_1_S0337         | Ausgangsrechnung        | 2022-05-12   | Spot                     | true    | EUR                 | 511004_3_SO_S0337_200 |
    And metasfresh contains C_InvoiceLines
      | Identifier       | C_Invoice_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | C_UOM_ID.X12DE355 |
      | invl_1_S0337_200 | inv_1_S0337_200         | p_1_S0337               | 10          | PCE               |
      | invl_2_S0337_200 | inv_2_S0337_200         | p_1_S0337               | 9           | PCE               |
      | invl_3_S0337_200 | inv_3_S0337_200         | p_1_S0337               | 8           | PCE               |
    And the invoice identified by inv_1_S0337_200 is completed
    And the invoice identified by inv_2_S0337_200 is completed
    And the invoice identified by inv_3_S0337_200 is completed

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
				<!-- plus 10001.1 CHF -->
				<Amt Ccy="CHF">95.2</Amt>
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
						<TtlAmt Ccy="CHF">95.2</TtlAmt>
						<CdtDbtInd>CRDT</CdtDbtInd>
					</Btch>
					<TxDtls>
						<Refs>
							<AcctSvcrRef>AcctSvcrRef/1/1</AcctSvcrRef>
							<EndToEndId>EndToEndId/1/1</EndToEndId>
							<TxId>TxId/1/1</TxId>
						</Refs>
						<Amt Ccy="CHF">80</Amt>
						<CdtDbtInd>CRDT</CdtDbtInd>
						<AmtDtls>
							<InstdAmt>
								<Amt Ccy="CHF">95.2</Amt>
							</InstdAmt>
							<TxAmt>
								<Amt Ccy="CHF">95.2</Amt>
							</TxAmt>
						</AmtDtls>
						<RmtInf>
							<Ustrd>ESR-Referenz 123456700102156426010000023</Ustrd>
						</RmtInf>
						<AddtlTxInf>Zahlungseingang</AddtlTxInf>
					</TxDtls>
				</NtryDtls>
				<AddtlNtryInf>Zahlungseingang</AddtlNtryInf>
			</Ntry>
			<Ntry>
				<!-- minus 226.1 CHF -->
				<Amt Ccy="CHF">226.1</Amt>
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
						<TtlAmt Ccy="CHF">226.1</TtlAmt>
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
						<Amt Ccy="CHF">119</Amt>
						<CdtDbtInd>DBIT</CdtDbtInd>
						<AmtDtls>
							<InstdAmt>
								<Amt Ccy="CHF">119</Amt>
							</InstdAmt>
							<TxAmt>
								<Amt Ccy="CHF">119</Amt>
							</TxAmt>
						</AmtDtls>
						<RmtInf>
							<Ustrd>Rechnungsnummer 511004_1_PO_S0337_200</Ustrd>
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
						<Amt Ccy="CHF">107.1</Amt>
						<CdtDbtInd>DBIT</CdtDbtInd>
						<AmtDtls>
							<InstdAmt>
								<Amt Ccy="CHF">107.1</Amt>
							</InstdAmt>
							<TxAmt>
								<Amt Ccy="CHF">107.1</Amt>
							</TxAmt>
						</AmtDtls>
						<RmtInf>
							<Ustrd>Rechnungsnummer 511004_2_PO_S0337_200</Ustrd>
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
      | bs_1_S0337_200                | 1000.07              | 890.07            | 110                     | false         | bp_bank_account_metasfresh_S0337_200 | 2023-10-27        | false            |
    And load C_BankStatementLine
      | C_BankStatementLine_ID.Identifier | C_BankStatement_ID.Identifier | Line |
      | bsl_1_S0337_200                   | bs_1_S0337_200                | 10   |
      | bsl_2_S0337_200                   | bs_1_S0337_200                | 20   |
      | bsl_3_S0337_200                   | bs_1_S0337_200                | 30   |
    And validate C_BankStatementLine
      | C_BankStatementLine_ID.Identifier | OPT.ValutaDate | OPT.DateAcct | OPT.C_Currency_ID.ISO_Code | OPT.TrxAmt | OPT.StmtAmt | OPT.StatementLineDate | OPT.C_BPartner_ID.Identifier | OPT.C_Invoice_ID.Identifier | OPT.Processed | OPT.IsReconciled |
	  | bsl_1_S0337_200                   | 2023-10-24     | 2023-10-24   | EUR                        | 95.2       | 95.2        | 2023-10-24            | bpartner_1_S0337             | inv_3_S0337_200             | false         | false            |
	  | bsl_2_S0337_200                   | 2023-10-26     | 2023-10-26   | EUR                        | -119       | -119        | 2023-10-26            | bpartner_1_S0337             | inv_1_S0337_200             | false         | false            |
	  | bsl_3_S0337_200                   | 2023-10-26     | 2023-10-26   | EUR                        | -107.1     | -107.1      | 2022-09-07            | bpartner_1_S0337             | inv_2_S0337_200             | false         | false            |
	  And the C_BankStatement identified by bs_1_S0337_200 is completed

    And set sys config boolean value false for sys config de.metas.payment.esr.Enabled