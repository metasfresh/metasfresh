@from:cucumber
Feature: import bank statement in camt53 import format

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2020-02-28T13:30:13+01:00[Europe/Berlin]

    And metasfresh contains M_Products:
      | Identifier | Name                            |
      | p_1        | bankStatementProduct_12102022_1 |
    And metasfresh contains M_PricingSystems
      | Identifier | Name                           | Value                           | OPT.Description                       | OPT.IsActive |
      | ps_1       | pricing_system_name_12102022_1 | pricing_system_value_12102022_1 | pricing_system_description_12102022_1 | true         |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name                       | OPT.Description | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | pl_1       | ps_1                          | DE                        | EUR                 | price_list_name_29032022_2 | null            | false | false         | 2              | true         |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name                          | ValidFrom  |
      | plv_1      | pl_1                      | trackedProduct-PLV_12102022_1 | 2021-04-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_1       | plv_1                             | p_1                     | 10.0     | PCE               | Normal                        |

    And metasfresh contains C_BPartners without locations:
      | Identifier | Name                     | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | bpartner_1 | EndcustomerPP_12102022_1 | Y            | Y              | ps_1                          |

    And metasfresh contains C_BPartner_Locations:
      | Identifier | GLN           | C_BPartner_ID.Identifier | OPT.IsBillToDefault | OPT.IsShipTo |
      | l_1        | 0203111111111 | bpartner_1               | true                | true         |

    And load C_DataImport:
      | C_DataImport_ID.Identifier | OPT.C_DataImport_ID |
      | di_1                       | 540009              |
    And metasfresh contains C_Bank:
      | C_Bank_ID.Identifier | Name            | RoutingNo | SwiftCode   | C_DataImport_ID.Identifier |
      | b_1                  | bank_12102022_1 | 2234567   | AAAAAAAA82A | di_1                       |
    And metasfresh contains C_BP_BankAccount
      | Identifier | C_BPartner_ID.Identifier | C_Currency.ISO_Code | OPT.AccountNo | OPT.C_Bank_ID.Identifier | OPT.IBAN               |
      | bpb_1      | bpartner_1               | EUR                 | 2234567       | b_1                      | GB33BUKB20201555555555 |

  @from:cucumber
  @Id:S0203_100
  Scenario: Import bank statement in camt53 import format, identify account by Id and link it to a payment for an invoice
    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID.Identifier | C_DocTypeTarget_ID.Name | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code | OPT.DocumentNo |
      | inv_1      | bpartner_1               | Eingangsrechnung        | 2022-05-11   | Spot                     | false   | EUR                 | 511003         |
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
					<Othr>
						<Id>2234567</Id>
					</Othr>
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
							<Ustrd>511003</Ustrd>
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
    And validate C_BankStatement
      | C_BankStatement_ID.Identifier | OPT.BeginningBalance | OPT.EndingBalance | OPT.StatementDifference | OPT.Processed | OPT.C_BP_BankAccount_ID.Identifier | OPT.StatementDate | OPT.IsReconciled |
      | bs_1                          | 41891                | 83544             | 41653                   | false         | bpb_1                              | 2022-05-11        | false            |
    And load C_BankStatementLine
      | C_BankStatementLine_ID.Identifier | C_BankStatement_ID.Identifier | Line |
      | bsl_1                             | bs_1                          | 10   |
      | bsl_2                             | bs_1                          | 20   |
    And validate C_BankStatementLine
      | C_BankStatementLine_ID.Identifier | OPT.ValutaDate | OPT.DateAcct | OPT.C_Currency_ID.ISO_Code | OPT.TrxAmt | OPT.StmtAmt | OPT.StatementLineDate | OPT.C_BPartner_ID.Identifier | OPT.C_Invoice_ID.Identifier | OPT.Processed | OPT.IsReconciled |
      | bsl_1                             | 2022-05-11     | 2022-05-11   | EUR                        | -119       | -119        | 2022-05-11            | bpartner_1                   | inv_1                       | false         | false            |
      | bsl_2                             | 2022-09-07     | 2022-09-07   | EUR                        | 41772      | 41772       | 2022-09-07            |                              |                             | false         | false            |
    And the C_BankStatement identified by bs_1 is completed
    And after not more than 60s, C_Payment is found
      | C_Payment_ID.Identifier | OPT.C_BankStatement_ID.Identifier | OPT.C_BankStatementLine_ID.Identifier |
      | p_1                     | bs_1                              | bsl_1                                 |
    And validate payments
      | C_Payment_ID.Identifier | C_Payment_ID.IsAllocated | OPT.OpenAmt | OPT.PayAmt | OPT.C_Invoice_ID.Identifier | OPT.DateTrx | OPT.C_BPartner_ID.Identifier | OPT.C_BP_BankAccount_ID.Identifier |
      | p_1                     | true                     | 0.00        | 119        | inv_1                       | 2022-05-11  | bpartner_1                   | bpb_1                              |
    And validate C_BankStatement
      | C_BankStatement_ID.Identifier | OPT.BeginningBalance | OPT.EndingBalance | OPT.StatementDifference | OPT.Processed | OPT.C_BP_BankAccount_ID.Identifier | OPT.StatementDate | OPT.IsReconciled |
      | bs_1                          | 41891                | 83544             | 41653                   | true          | bpb_1                              | 2022-05-11        | false            |
    And validate C_BankStatementLine
      | C_BankStatementLine_ID.Identifier | OPT.ValutaDate | OPT.DateAcct | OPT.C_Currency_ID.ISO_Code | OPT.TrxAmt | OPT.StmtAmt | OPT.StatementLineDate | OPT.C_BPartner_ID.Identifier | OPT.C_Invoice_ID.Identifier | OPT.Processed | OPT.IsReconciled |
      | bsl_1                             | 2022-05-11     | 2022-05-11   | EUR                        | -119       | -119        | 2022-05-11            | bpartner_1                   | inv_1                       | true          | true             |
      | bsl_2                             | 2022-09-07     | 2022-09-07   | EUR                        | 41772      | 41772       | 2022-09-07            |                              |                             | true          | false            |
    And validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | paymentTerm   | processed | docStatus | OPT.IsPaid |
      | inv_1                   | bpartner_1               | l_1                               | 30 Tage netto | true      | CO        | true       |

  @from:cucumber
  @Id:S0203_200
  Scenario: Import bank statement in camt53 import format, identify account by IBAN and link it to a payment for an invoice
    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID.Identifier | C_DocTypeTarget_ID.Name | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code | OPT.DocumentNo |
      | inv_1      | bpartner_1               | Eingangsrechnung        | 2022-09-12   | Spot                     | false   | EUR                 | 511021         |
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
			<MsgId>MsgId11111111261022</MsgId>
			<CreDtTm>2022-09-12T19:45:37.000Z</CreDtTm>
			<MsgPgntn>
				<PgNb>1</PgNb>
				<LastPgInd>true</LastPgInd>
			</MsgPgntn>
		</GrpHdr>
		<Stmt>
			<Id>StmtId11111111261022</Id>
			<ElctrncSeqNb>00001</ElctrncSeqNb>
			<CreDtTm>2022-09-12T19:45:37.000Z</CreDtTm>
			<Acct>
				<Id>
					<IBAN>GB33BUKB20201555555555</IBAN>
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
				<Amt Ccy="EUR">41891.00</Amt>
				<CdtDbtInd>CRDT</CdtDbtInd>
				<Dt>
					<Dt>2022-09-12</Dt>
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
					<Dt>2022-09-12</Dt>
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
					<Dt>2022-09-12</Dt>
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
					<Dt>2022-09-12</Dt>
				</Dt>
			</Bal>
			<Ntry>
				<NtryRef>00001</NtryRef>
				<Amt Ccy="EUR">119</Amt>
				<CdtDbtInd>DBT</CdtDbtInd>
				<Sts>BOOK</Sts>
				<BookgDt>
					<Dt>2022-09-12</Dt>
				</BookgDt>
				<ValDt>
					<Dt>2022-09-12</Dt>
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
							<Ustrd>511021</Ustrd>
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
					<Dt>2022-09-20</Dt>
				</BookgDt>
				<ValDt>
					<Dt>2022-09-20</Dt>
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
    And validate C_BankStatement
      | C_BankStatement_ID.Identifier | OPT.BeginningBalance | OPT.EndingBalance | OPT.StatementDifference | OPT.Processed | OPT.C_BP_BankAccount_ID.Identifier | OPT.StatementDate | OPT.IsReconciled |
      | bs_1                          | 41891                | 83544             | 41653                   | false         | bpb_1                              | 2022-09-12        | false            |