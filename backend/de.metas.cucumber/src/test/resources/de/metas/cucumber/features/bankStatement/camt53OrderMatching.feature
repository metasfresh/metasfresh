@from:cucumber
Feature: camt.53 bank statement import - waiting prepay orders matched by their document number
  Order matching is the fallback of invoice matching.
  A line gets an order only when it is incoming money and the text names exactly one waiting prepay order
  of the same currency and the full amount.

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2026-11-03T13:30:13+01:00[Europe/Berlin]

    And metasfresh contains M_Products:
      | Identifier |
      | p_OM       |
    And metasfresh contains M_PricingSystems
      | Identifier | OPT.IsActive |
      | ps_OM      | true         |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name             | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | pl_SO_OM   | ps_OM                         | DE                        | EUR                 | price_list_SO_OM | true  | true          | 2              | true         |
    And metasfresh contains M_PriceList_Versions
      | Identifier  | M_PriceList_ID.Identifier | Name        | ValidFrom  |
      | plv_SO_OM   | pl_SO_OM                  | plv_SO_OM   | 2026-01-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_SO_OM   | plv_SO_OM                         | p_OM                    | 123.52   | PCE               | Normal                        |

    And metasfresh contains C_BPartners without locations:
      | Identifier | OPT.IsCustomer | M_PricingSystem_ID.Identifier | OPT.InvoiceRule |
      | bp_OM      | Y              | ps_OM                         | I               |
      | bp_OM2     | Y              | ps_OM                         | I               |
    And metasfresh contains C_BPartner_Locations:
      | Identifier | GLN           | C_BPartner_ID.Identifier | OPT.IsBillToDefault | OPT.IsShipTo |
      | l_OM       | 0203111110001 | bp_OM                    | true                | true         |
      | l_OM2      | 0203111110002 | bp_OM2                   | true                | true         |

    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And set sys config boolean value true for sys config de.metas.payment.esr.Enabled
    And load C_DataImport:
      | C_DataImport_ID.Identifier | OPT.C_DataImport_ID |
      | di_OM                      | 540009              |
    And metasfresh contains C_Bank:
      | C_Bank_ID.Identifier | Name         | RoutingNo | SwiftCode   | C_DataImport_ID.Identifier |
      | b_OM                 | bank_OM_Test | 2234567   | AAAAAAAA82A | di_OM                      |
    And load C_BP_BankAccount:
      | C_BP_BankAccount_ID.Identifier | OPT.C_BP_BankAccount_ID |
      | bpb_OM                         | 2000257                 |
    And update C_BP_BankAccount:
      | C_BP_BankAccount_ID.Identifier | OPT.C_Currency.ISO_Code | OPT.IsEsrAccount | OPT.AccountNo | OPT.ESR_RenderedAccountNo | OPT.IBAN              | OPT.C_Bank_ID.Identifier |
      | bpb_OM                         | EUR                     | Y                | 1234567890    | 123456789                 | CH3908704016075473007 | b_OM                     |


  # ################################################################################
  # ################################################################################
  # ################################################################################

  @from:cucumber
  @Id:S32606_OM1
  Scenario: Credit line naming a waiting prepay order: order and partner on the line; completing the statement creates the payment with the order and releases the order

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | DocBaseType | DocSubType | DocumentNo |
      | o_1        | true    | bp_OM         | 2026-11-03  | SOO         | PR         | OM01-0021  |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | l_o_1      | o_1        | p_OM         | 1          |
    And the order identified by o_1 is completed and waits for payment
    And validate the created orders
      | Identifier | DocStatus | GrandTotal |
      | o_1        | WP        | 123.52     |
    When bank statement is imported with identifiers bs_1, matching invoice amounts
    """
<?xml version="1.0" encoding="utf-8"?>
<Document xmlns="urn:iso:std:iso:20022:tech:xsd:camt.053.001.08">
	<BkToCstmrStmt>
		<GrpHdr>
			<MsgId>MsgIdOM01</MsgId>
			<CreDtTm>2026-11-03T19:45:37.000Z</CreDtTm>
			<MsgPgntn><PgNb>1</PgNb><LastPgInd>true</LastPgInd></MsgPgntn>
		</GrpHdr>
		<Stmt>
			<Id>StmtIdOM01</Id>
			<ElctrncSeqNb>00001</ElctrncSeqNb>
			<CreDtTm>2026-11-03T19:45:37.000Z</CreDtTm>
			<FrToDt><FrDtTm>2026-11-02T00:00:00</FrDtTm><ToDtTm>2026-11-03T23:59:59</ToDtTm></FrToDt>
			<Acct>
				<Id><IBAN>CH3908704016075473007</IBAN></Id>
				<Ccy>EUR</Ccy>
				<Ownr><Nm>Test CO</Nm></Ownr>
				<Svcr><FinInstnId><BICFI>AAAAAAAA82A</BICFI><Nm>Test Bank</Nm></FinInstnId></Svcr>
			</Acct>
			<Bal>
				<Tp><CdOrPrtry><Cd>OPBD</Cd></CdOrPrtry></Tp>
				<Amt Ccy="EUR">1000.00</Amt>
				<CdtDbtInd>CRDT</CdtDbtInd>
				<Dt><Dt>2026-11-02</Dt></Dt>
			</Bal>
			<Bal>
				<Tp><CdOrPrtry><Cd>CLBD</Cd></CdOrPrtry></Tp>
				<Amt Ccy="EUR">1123.52</Amt>
				<CdtDbtInd>CRDT</CdtDbtInd>
				<Dt><Dt>2026-11-03</Dt></Dt>
			</Bal>
			<Ntry>
				<NtryRef>REF-OM01</NtryRef>
				<Amt Ccy="EUR">123.52</Amt>
				<CdtDbtInd>CRDT</CdtDbtInd>
				<Sts><Cd>BOOK</Cd></Sts>
				<BookgDt><Dt>2026-11-03</Dt></BookgDt>
				<ValDt><Dt>2026-11-03</Dt></ValDt>
				<AcctSvcrRef>REF-OM01</AcctSvcrRef>
				<BkTxCd><Prtry><Cd>TRF</Cd><Issr>SWIFT</Issr></Prtry></BkTxCd>
				<NtryDtls>
					<Btch>
						<NbOfTxs>1</NbOfTxs>
						<TtlAmt Ccy="EUR">123.52</TtlAmt>
						<CdtDbtInd>CRDT</CdtDbtInd>
					</Btch>
					<TxDtls>
						<Refs><AcctSvcrRef>REF-OM01-1</AcctSvcrRef></Refs>
						<Amt Ccy="EUR">123.52</Amt>
						<CdtDbtInd>CRDT</CdtDbtInd>
						<AmtDtls><TxAmt><Amt Ccy="EUR">123.52</Amt></TxAmt></AmtDtls>
						<RltdPties>
							<Dbtr><Pty><Nm>Customer Test</Nm></Pty></Dbtr>
							<DbtrAcct><Id><IBAN>CH4189144993834976921</IBAN></Id></DbtrAcct>
							<Cdtr><Pty><Nm>Test CO</Nm></Pty></Cdtr>
						</RltdPties>
						<RmtInf>
							<Ustrd>Vorauskasse OM01-0021 Danke</Ustrd>
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
      | C_BankStatementLine_ID.Identifier | OPT.TrxAmt | OPT.C_Order_ID.Identifier | OPT.C_BPartner_ID.Identifier |
      | bsl_1                             | 123.52     | o_1                       | bp_OM                        |
    And the C_BankStatement identified by bs_1 is completed
    And after not more than 60s, C_Payment is found
      | C_Payment_ID.Identifier | OPT.C_BankStatement_ID.Identifier | OPT.C_BankStatementLine_ID.Identifier |
      | p_1                     | bs_1                              | bsl_1                                 |
    And validate payments
      | C_Payment_ID.Identifier | PayAmt | IsReceipt | C_BPartner_ID.Identifier | C_Order_ID.Identifier |
      | p_1                     | 123.52 | true      | bp_OM                    | o_1                   |
    And validate the created orders
      | Identifier | DocStatus | GrandTotal |
      | o_1        | CO        | 123.52     |


  # ################################################################################
  # ################################################################################
  # ################################################################################

  @from:cucumber
  @Id:S32606_OM2
  Scenario: Two waiting orders named in the text, both totalling the line amount: no order is set

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | DocBaseType | DocSubType | DocumentNo |
      | o_2a       | true    | bp_OM         | 2026-11-03  | SOO         | PR         | OM02-0001  |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | l_o_2a     | o_2a       | p_OM         | 1          |
    And the order identified by o_2a is completed and waits for payment
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | DocBaseType | DocSubType | DocumentNo |
      | o_2b       | true    | bp_OM         | 2026-11-03  | SOO         | PR         | OM02-0002  |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | l_o_2b     | o_2b       | p_OM         | 1          |
    And the order identified by o_2b is completed and waits for payment
    When bank statement is imported with identifiers bs_2, matching invoice amounts
    """
<?xml version="1.0" encoding="utf-8"?>
<Document xmlns="urn:iso:std:iso:20022:tech:xsd:camt.053.001.08">
	<BkToCstmrStmt>
		<GrpHdr>
			<MsgId>MsgIdOM02</MsgId>
			<CreDtTm>2026-11-03T19:45:37.000Z</CreDtTm>
			<MsgPgntn><PgNb>1</PgNb><LastPgInd>true</LastPgInd></MsgPgntn>
		</GrpHdr>
		<Stmt>
			<Id>StmtIdOM02</Id>
			<ElctrncSeqNb>00001</ElctrncSeqNb>
			<CreDtTm>2026-11-03T19:45:37.000Z</CreDtTm>
			<FrToDt><FrDtTm>2026-11-02T00:00:00</FrDtTm><ToDtTm>2026-11-03T23:59:59</ToDtTm></FrToDt>
			<Acct>
				<Id><IBAN>CH3908704016075473007</IBAN></Id>
				<Ccy>EUR</Ccy>
				<Ownr><Nm>Test CO</Nm></Ownr>
				<Svcr><FinInstnId><BICFI>AAAAAAAA82A</BICFI><Nm>Test Bank</Nm></FinInstnId></Svcr>
			</Acct>
			<Bal>
				<Tp><CdOrPrtry><Cd>OPBD</Cd></CdOrPrtry></Tp>
				<Amt Ccy="EUR">1000.00</Amt>
				<CdtDbtInd>CRDT</CdtDbtInd>
				<Dt><Dt>2026-11-02</Dt></Dt>
			</Bal>
			<Bal>
				<Tp><CdOrPrtry><Cd>CLBD</Cd></CdOrPrtry></Tp>
				<Amt Ccy="EUR">1123.52</Amt>
				<CdtDbtInd>CRDT</CdtDbtInd>
				<Dt><Dt>2026-11-03</Dt></Dt>
			</Bal>
			<Ntry>
				<NtryRef>REF-OM02</NtryRef>
				<Amt Ccy="EUR">123.52</Amt>
				<CdtDbtInd>CRDT</CdtDbtInd>
				<Sts><Cd>BOOK</Cd></Sts>
				<BookgDt><Dt>2026-11-03</Dt></BookgDt>
				<ValDt><Dt>2026-11-03</Dt></ValDt>
				<AcctSvcrRef>REF-OM02</AcctSvcrRef>
				<BkTxCd><Prtry><Cd>TRF</Cd><Issr>SWIFT</Issr></Prtry></BkTxCd>
				<NtryDtls>
					<Btch>
						<NbOfTxs>1</NbOfTxs>
						<TtlAmt Ccy="EUR">123.52</TtlAmt>
						<CdtDbtInd>CRDT</CdtDbtInd>
					</Btch>
					<TxDtls>
						<Refs><AcctSvcrRef>REF-OM02-1</AcctSvcrRef></Refs>
						<Amt Ccy="EUR">123.52</Amt>
						<CdtDbtInd>CRDT</CdtDbtInd>
						<AmtDtls><TxAmt><Amt Ccy="EUR">123.52</Amt></TxAmt></AmtDtls>
						<RltdPties>
							<Dbtr><Pty><Nm>Customer Test</Nm></Pty></Dbtr>
							<DbtrAcct><Id><IBAN>CH4189144993834976921</IBAN></Id></DbtrAcct>
							<Cdtr><Pty><Nm>Test CO</Nm></Pty></Cdtr>
						</RltdPties>
						<RmtInf>
							<Ustrd>Vorauskasse OM02-0001 und OM02-0002</Ustrd>
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
      | bsl_2                             | bs_2                          | 10   |
    And validate C_BankStatementLine
      | C_BankStatementLine_ID.Identifier | OPT.C_Order_ID.Identifier | OPT.C_BPartner_ID.Identifier |
      | bsl_2                             | null                      | null                         |
    And validate the created orders
      | Identifier | DocStatus | GrandTotal |
      | o_2a       | WP        | 123.52     |
    And validate the created orders
      | Identifier | DocStatus | GrandTotal |
      | o_2b       | WP        | 123.52     |


  # ################################################################################
  # ################################################################################
  # ################################################################################

  @from:cucumber
  @Id:S32606_OM3
  Scenario: Text names an unpaid invoice and a waiting order: the invoice is matched, no order is set

    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID.Identifier | C_DocTypeTarget_ID.Name | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code | OPT.DocumentNo |
      | inv_3      | bp_OM                    | Ausgangsrechnung        | 2026-11-03   | Spot                     | true    | EUR                 | OM03-INV       |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | C_UOM_ID.X12DE355 |
      | il_inv_3   | inv_3                   | p_OM                    | 1           | PCE               |
    And the invoice identified by inv_3 is completed
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | DocBaseType | DocSubType | DocumentNo |
      | o_3        | true    | bp_OM         | 2026-11-03  | SOO         | PR         | OM03-0003  |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | l_o_3      | o_3        | p_OM         | 1          |
    And the order identified by o_3 is completed and waits for payment
    When bank statement is imported with identifiers bs_3, not matching invoice amounts
    """
<?xml version="1.0" encoding="utf-8"?>
<Document xmlns="urn:iso:std:iso:20022:tech:xsd:camt.053.001.08">
	<BkToCstmrStmt>
		<GrpHdr>
			<MsgId>MsgIdOM03</MsgId>
			<CreDtTm>2026-11-03T19:45:37.000Z</CreDtTm>
			<MsgPgntn><PgNb>1</PgNb><LastPgInd>true</LastPgInd></MsgPgntn>
		</GrpHdr>
		<Stmt>
			<Id>StmtIdOM03</Id>
			<ElctrncSeqNb>00001</ElctrncSeqNb>
			<CreDtTm>2026-11-03T19:45:37.000Z</CreDtTm>
			<FrToDt><FrDtTm>2026-11-02T00:00:00</FrDtTm><ToDtTm>2026-11-03T23:59:59</ToDtTm></FrToDt>
			<Acct>
				<Id><IBAN>CH3908704016075473007</IBAN></Id>
				<Ccy>EUR</Ccy>
				<Ownr><Nm>Test CO</Nm></Ownr>
				<Svcr><FinInstnId><BICFI>AAAAAAAA82A</BICFI><Nm>Test Bank</Nm></FinInstnId></Svcr>
			</Acct>
			<Bal>
				<Tp><CdOrPrtry><Cd>OPBD</Cd></CdOrPrtry></Tp>
				<Amt Ccy="EUR">1000.00</Amt>
				<CdtDbtInd>CRDT</CdtDbtInd>
				<Dt><Dt>2026-11-02</Dt></Dt>
			</Bal>
			<Bal>
				<Tp><CdOrPrtry><Cd>CLBD</Cd></CdOrPrtry></Tp>
				<Amt Ccy="EUR">1123.52</Amt>
				<CdtDbtInd>CRDT</CdtDbtInd>
				<Dt><Dt>2026-11-03</Dt></Dt>
			</Bal>
			<Ntry>
				<NtryRef>REF-OM03</NtryRef>
				<Amt Ccy="EUR">123.52</Amt>
				<CdtDbtInd>CRDT</CdtDbtInd>
				<Sts><Cd>BOOK</Cd></Sts>
				<BookgDt><Dt>2026-11-03</Dt></BookgDt>
				<ValDt><Dt>2026-11-03</Dt></ValDt>
				<AcctSvcrRef>REF-OM03</AcctSvcrRef>
				<BkTxCd><Prtry><Cd>TRF</Cd><Issr>SWIFT</Issr></Prtry></BkTxCd>
				<NtryDtls>
					<Btch>
						<NbOfTxs>1</NbOfTxs>
						<TtlAmt Ccy="EUR">123.52</TtlAmt>
						<CdtDbtInd>CRDT</CdtDbtInd>
					</Btch>
					<TxDtls>
						<Refs><AcctSvcrRef>REF-OM03-1</AcctSvcrRef></Refs>
						<Amt Ccy="EUR">123.52</Amt>
						<CdtDbtInd>CRDT</CdtDbtInd>
						<AmtDtls><TxAmt><Amt Ccy="EUR">123.52</Amt></TxAmt></AmtDtls>
						<RltdPties>
							<Dbtr><Pty><Nm>Customer Test</Nm></Pty></Dbtr>
							<DbtrAcct><Id><IBAN>CH4189144993834976921</IBAN></Id></DbtrAcct>
							<Cdtr><Pty><Nm>Test CO</Nm></Pty></Cdtr>
						</RltdPties>
						<RmtInf>
							<Ustrd>Zahlung OM03-INV und OM03-0003</Ustrd>
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
      | bsl_3                             | bs_3                          | 10   |
    And validate C_BankStatementLine
      | C_BankStatementLine_ID.Identifier | OPT.C_Order_ID.Identifier | OPT.C_Invoice_ID.Identifier |
      | bsl_3                             | null                      | inv_3                       |
    And validate the created orders
      | Identifier | DocStatus | GrandTotal |
      | o_3        | WP        | 123.52     |


  # ################################################################################
  # ################################################################################
  # ################################################################################

  @from:cucumber
  @Id:S32606_OM4
  Scenario: Text names a completed standard order and a prepay order already released by a hand payment: no order, no partner, the payment stays unlinked

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | DocBaseType | DocSubType | DocumentNo |
      | o_4a       | true    | bp_OM         | 2026-11-03  | SOO         | SO         | OM04-STD   |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | l_o_4a     | o_4a       | p_OM         | 1          |
    And the order identified by o_4a is completed
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | DocBaseType | DocSubType | DocumentNo |
      | o_4b       | true    | bp_OM         | 2026-11-03  | SOO         | PR         | OM04-PRE   |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | l_o_4b     | o_4b       | p_OM         | 1          |
    And the order identified by o_4b is completed and waits for payment
    And metasfresh contains C_Payment
      | Identifier | C_BPartner_ID | PayAmt     | IsReceipt | C_Order_ID |
      | p_4        | bp_OM         | 123.52 EUR | true      | o_4b       |
    And the payment identified by p_4 is completed
    And validate the created orders
      | Identifier | DocStatus | GrandTotal |
      | o_4b       | CO        | 123.52     |
    When bank statement is imported with identifiers bs_4, not matching invoice amounts
    """
<?xml version="1.0" encoding="utf-8"?>
<Document xmlns="urn:iso:std:iso:20022:tech:xsd:camt.053.001.08">
	<BkToCstmrStmt>
		<GrpHdr>
			<MsgId>MsgIdOM04</MsgId>
			<CreDtTm>2026-11-03T19:45:37.000Z</CreDtTm>
			<MsgPgntn><PgNb>1</PgNb><LastPgInd>true</LastPgInd></MsgPgntn>
		</GrpHdr>
		<Stmt>
			<Id>StmtIdOM04</Id>
			<ElctrncSeqNb>00001</ElctrncSeqNb>
			<CreDtTm>2026-11-03T19:45:37.000Z</CreDtTm>
			<FrToDt><FrDtTm>2026-11-02T00:00:00</FrDtTm><ToDtTm>2026-11-03T23:59:59</ToDtTm></FrToDt>
			<Acct>
				<Id><IBAN>CH3908704016075473007</IBAN></Id>
				<Ccy>EUR</Ccy>
				<Ownr><Nm>Test CO</Nm></Ownr>
				<Svcr><FinInstnId><BICFI>AAAAAAAA82A</BICFI><Nm>Test Bank</Nm></FinInstnId></Svcr>
			</Acct>
			<Bal>
				<Tp><CdOrPrtry><Cd>OPBD</Cd></CdOrPrtry></Tp>
				<Amt Ccy="EUR">1000.00</Amt>
				<CdtDbtInd>CRDT</CdtDbtInd>
				<Dt><Dt>2026-11-02</Dt></Dt>
			</Bal>
			<Bal>
				<Tp><CdOrPrtry><Cd>CLBD</Cd></CdOrPrtry></Tp>
				<Amt Ccy="EUR">1123.52</Amt>
				<CdtDbtInd>CRDT</CdtDbtInd>
				<Dt><Dt>2026-11-03</Dt></Dt>
			</Bal>
			<Ntry>
				<NtryRef>REF-OM04</NtryRef>
				<Amt Ccy="EUR">123.52</Amt>
				<CdtDbtInd>CRDT</CdtDbtInd>
				<Sts><Cd>BOOK</Cd></Sts>
				<BookgDt><Dt>2026-11-03</Dt></BookgDt>
				<ValDt><Dt>2026-11-03</Dt></ValDt>
				<AcctSvcrRef>REF-OM04</AcctSvcrRef>
				<BkTxCd><Prtry><Cd>TRF</Cd><Issr>SWIFT</Issr></Prtry></BkTxCd>
				<NtryDtls>
					<Btch>
						<NbOfTxs>1</NbOfTxs>
						<TtlAmt Ccy="EUR">123.52</TtlAmt>
						<CdtDbtInd>CRDT</CdtDbtInd>
					</Btch>
					<TxDtls>
						<Refs><AcctSvcrRef>REF-OM04-1</AcctSvcrRef></Refs>
						<Amt Ccy="EUR">123.52</Amt>
						<CdtDbtInd>CRDT</CdtDbtInd>
						<AmtDtls><TxAmt><Amt Ccy="EUR">123.52</Amt></TxAmt></AmtDtls>
						<RltdPties>
							<Dbtr><Pty><Nm>Customer Test</Nm></Pty></Dbtr>
							<DbtrAcct><Id><IBAN>CH4189144993834976921</IBAN></Id></DbtrAcct>
							<Cdtr><Pty><Nm>Test CO</Nm></Pty></Cdtr>
						</RltdPties>
						<RmtInf>
							<Ustrd>Zahlung OM04-STD und OM04-PRE</Ustrd>
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
      | bsl_4                             | bs_4                          | 10   |
    And validate C_BankStatementLine
      | C_BankStatementLine_ID.Identifier | OPT.C_Order_ID.Identifier | OPT.C_BPartner_ID.Identifier |
      | bsl_4                             | null                      | null                         |


  # ################################################################################
  # ################################################################################
  # ################################################################################

  @from:cucumber
  @Id:S32606_OM5
  Scenario: A completed same-partner same-amount payment without order is not linked to a line with an order: a new payment with the order is created

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | DocBaseType | DocSubType | DocumentNo |
      | o_5        | true    | bp_OM         | 2026-11-03  | SOO         | PR         | OM05-0005  |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | l_o_5      | o_5        | p_OM         | 1          |
    And the order identified by o_5 is completed and waits for payment
    And metasfresh contains C_Payment
      | Identifier | C_BPartner_ID | PayAmt     | IsReceipt |
      | p_5hand    | bp_OM         | 123.52 EUR | true      |
    And the payment identified by p_5hand is completed
    When bank statement is imported with identifiers bs_5, matching invoice amounts
    """
<?xml version="1.0" encoding="utf-8"?>
<Document xmlns="urn:iso:std:iso:20022:tech:xsd:camt.053.001.08">
	<BkToCstmrStmt>
		<GrpHdr>
			<MsgId>MsgIdOM05</MsgId>
			<CreDtTm>2026-11-03T19:45:37.000Z</CreDtTm>
			<MsgPgntn><PgNb>1</PgNb><LastPgInd>true</LastPgInd></MsgPgntn>
		</GrpHdr>
		<Stmt>
			<Id>StmtIdOM05</Id>
			<ElctrncSeqNb>00001</ElctrncSeqNb>
			<CreDtTm>2026-11-03T19:45:37.000Z</CreDtTm>
			<FrToDt><FrDtTm>2026-11-02T00:00:00</FrDtTm><ToDtTm>2026-11-03T23:59:59</ToDtTm></FrToDt>
			<Acct>
				<Id><IBAN>CH3908704016075473007</IBAN></Id>
				<Ccy>EUR</Ccy>
				<Ownr><Nm>Test CO</Nm></Ownr>
				<Svcr><FinInstnId><BICFI>AAAAAAAA82A</BICFI><Nm>Test Bank</Nm></FinInstnId></Svcr>
			</Acct>
			<Bal>
				<Tp><CdOrPrtry><Cd>OPBD</Cd></CdOrPrtry></Tp>
				<Amt Ccy="EUR">1000.00</Amt>
				<CdtDbtInd>CRDT</CdtDbtInd>
				<Dt><Dt>2026-11-02</Dt></Dt>
			</Bal>
			<Bal>
				<Tp><CdOrPrtry><Cd>CLBD</Cd></CdOrPrtry></Tp>
				<Amt Ccy="EUR">1123.52</Amt>
				<CdtDbtInd>CRDT</CdtDbtInd>
				<Dt><Dt>2026-11-03</Dt></Dt>
			</Bal>
			<Ntry>
				<NtryRef>REF-OM05</NtryRef>
				<Amt Ccy="EUR">123.52</Amt>
				<CdtDbtInd>CRDT</CdtDbtInd>
				<Sts><Cd>BOOK</Cd></Sts>
				<BookgDt><Dt>2026-11-03</Dt></BookgDt>
				<ValDt><Dt>2026-11-03</Dt></ValDt>
				<AcctSvcrRef>REF-OM05</AcctSvcrRef>
				<BkTxCd><Prtry><Cd>TRF</Cd><Issr>SWIFT</Issr></Prtry></BkTxCd>
				<NtryDtls>
					<Btch>
						<NbOfTxs>1</NbOfTxs>
						<TtlAmt Ccy="EUR">123.52</TtlAmt>
						<CdtDbtInd>CRDT</CdtDbtInd>
					</Btch>
					<TxDtls>
						<Refs><AcctSvcrRef>REF-OM05-1</AcctSvcrRef></Refs>
						<Amt Ccy="EUR">123.52</Amt>
						<CdtDbtInd>CRDT</CdtDbtInd>
						<AmtDtls><TxAmt><Amt Ccy="EUR">123.52</Amt></TxAmt></AmtDtls>
						<RltdPties>
							<Dbtr><Pty><Nm>Customer Test</Nm></Pty></Dbtr>
							<DbtrAcct><Id><IBAN>CH4189144993834976921</IBAN></Id></DbtrAcct>
							<Cdtr><Pty><Nm>Test CO</Nm></Pty></Cdtr>
						</RltdPties>
						<RmtInf>
							<Ustrd>Vorauskasse OM05-0005 Danke</Ustrd>
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
      | bsl_5                             | bs_5                          | 10   |
    And validate C_BankStatementLine
      | C_BankStatementLine_ID.Identifier | OPT.C_Order_ID.Identifier | OPT.C_BPartner_ID.Identifier |
      | bsl_5                             | o_5                       | bp_OM                        |
    And the C_BankStatement identified by bs_5 is completed
    And validate payments
      | C_Payment_ID.Identifier | C_BankStatementLine_ID.Identifier |
      | p_5hand                 | null                              |
    And after not more than 60s, C_Payment is found
      | C_Payment_ID.Identifier | OPT.C_BankStatement_ID.Identifier | OPT.C_BankStatementLine_ID.Identifier |
      | p_5new                  | bs_5                              | bsl_5                                 |
    And validate payments
      | C_Payment_ID.Identifier | PayAmt | C_Order_ID.Identifier |
      | p_5new                  | 123.52 | o_5                   |
    And validate C_BankStatementLine
      | C_BankStatementLine_ID.Identifier | OPT.C_Payment_ID.Identifier | OPT.Processed | OPT.IsReconciled |
      | bsl_5                             | p_5new                      | true          | true             |
    And exactly 1 payment carries the order identified by o_5
    And validate the created orders
      | Identifier | DocStatus | GrandTotal |
      | o_5        | CO        | 123.52     |


  # ################################################################################
  # ################################################################################
  # ################################################################################

  @from:cucumber
  @Id:S32606_OM5b
  Scenario: A completed same-partner same-amount payment that carries another order is not linked to a line with an order

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | DocBaseType | DocSubType | DocumentNo |
      | o_5b       | true    | bp_OM         | 2026-11-03  | SOO         | PR         | OM05B-0001 |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | l_o_5b     | o_5b       | p_OM         | 1          |
    And the order identified by o_5b is completed and waits for payment
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | DocBaseType | DocSubType | DocumentNo |
      | o_5b_other | true    | bp_OM         | 2026-11-03  | SOO         | PR         | OM05B-0002 |
    And metasfresh contains C_OrderLines:
      | Identifier   | C_Order_ID | M_Product_ID | QtyEntered |
      | l_o_5b_other | o_5b_other | p_OM         | 1          |
    And the order identified by o_5b_other is completed and waits for payment
    And metasfresh contains C_Payment
      | Identifier | C_BPartner_ID | PayAmt     | IsReceipt | C_Order_ID |
      | p_5b_other | bp_OM         | 123.52 EUR | true      | o_5b_other |
    And the payment identified by p_5b_other is completed
    And metasfresh contains C_BankStatement:
      | Identifier | C_BP_BankAccount_ID | StatementDate |
      | bs_5b      | bpb_OM              | 2026-11-03    |
    And metasfresh contains C_BankStatementLine:
      | Identifier | C_BankStatement_ID | Amount     | C_BPartner_ID | C_Order_ID |
      | bsl_5b     | bs_5b              | 123.52 EUR | bp_OM         | o_5b       |
    And the C_BankStatement identified by bs_5b is completed
    And validate payments
      | C_Payment_ID.Identifier | C_BankStatementLine_ID.Identifier |
      | p_5b_other              | null                              |
    And after not more than 60s, C_Payment is found
      | C_Payment_ID.Identifier | OPT.C_BankStatement_ID.Identifier | OPT.C_BankStatementLine_ID.Identifier |
      | p_5b_new                | bs_5b                             | bsl_5b                                |
    And validate payments
      | C_Payment_ID.Identifier | PayAmt | C_Order_ID.Identifier |
      | p_5b_new                | 123.52 | o_5b                  |
    And validate the created orders
      | Identifier | DocStatus | GrandTotal |
      | o_5b       | CO        | 123.52     |


  # ################################################################################
  # ################################################################################
  # ################################################################################

  @from:cucumber
  @Id:S32606_OM6a
  Scenario: Prepay order is not matched on a line with the wrong amount (partial amount, import not matching amounts): only full prepayments are matched

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | DocBaseType | DocSubType | DocumentNo |
      | o_6a       | true    | bp_OM         | 2026-11-03  | SOO         | PR         | OM06a-0006 |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | l_o_6a     | o_6a       | p_OM         | 1          |
    And the order identified by o_6a is completed and waits for payment
    When bank statement is imported with identifiers bs_6a, not matching invoice amounts
    """
<?xml version="1.0" encoding="utf-8"?>
<Document xmlns="urn:iso:std:iso:20022:tech:xsd:camt.053.001.08">
	<BkToCstmrStmt>
		<GrpHdr>
			<MsgId>MsgIdOM06a</MsgId>
			<CreDtTm>2026-11-03T19:45:37.000Z</CreDtTm>
			<MsgPgntn><PgNb>1</PgNb><LastPgInd>true</LastPgInd></MsgPgntn>
		</GrpHdr>
		<Stmt>
			<Id>StmtIdOM06a</Id>
			<ElctrncSeqNb>00001</ElctrncSeqNb>
			<CreDtTm>2026-11-03T19:45:37.000Z</CreDtTm>
			<FrToDt><FrDtTm>2026-11-02T00:00:00</FrDtTm><ToDtTm>2026-11-03T23:59:59</ToDtTm></FrToDt>
			<Acct>
				<Id><IBAN>CH3908704016075473007</IBAN></Id>
				<Ccy>EUR</Ccy>
				<Ownr><Nm>Test CO</Nm></Ownr>
				<Svcr><FinInstnId><BICFI>AAAAAAAA82A</BICFI><Nm>Test Bank</Nm></FinInstnId></Svcr>
			</Acct>
			<Bal>
				<Tp><CdOrPrtry><Cd>OPBD</Cd></CdOrPrtry></Tp>
				<Amt Ccy="EUR">1000.00</Amt>
				<CdtDbtInd>CRDT</CdtDbtInd>
				<Dt><Dt>2026-11-02</Dt></Dt>
			</Bal>
			<Bal>
				<Tp><CdOrPrtry><Cd>CLBD</Cd></CdOrPrtry></Tp>
				<Amt Ccy="EUR">1100.00</Amt>
				<CdtDbtInd>CRDT</CdtDbtInd>
				<Dt><Dt>2026-11-03</Dt></Dt>
			</Bal>
			<Ntry>
				<NtryRef>REF-OM06a</NtryRef>
				<Amt Ccy="EUR">100.00</Amt>
				<CdtDbtInd>CRDT</CdtDbtInd>
				<Sts><Cd>BOOK</Cd></Sts>
				<BookgDt><Dt>2026-11-03</Dt></BookgDt>
				<ValDt><Dt>2026-11-03</Dt></ValDt>
				<AcctSvcrRef>REF-OM06a</AcctSvcrRef>
				<BkTxCd><Prtry><Cd>TRF</Cd><Issr>SWIFT</Issr></Prtry></BkTxCd>
				<NtryDtls>
					<Btch>
						<NbOfTxs>1</NbOfTxs>
						<TtlAmt Ccy="EUR">100.00</TtlAmt>
						<CdtDbtInd>CRDT</CdtDbtInd>
					</Btch>
					<TxDtls>
						<Refs><AcctSvcrRef>REF-OM06a-1</AcctSvcrRef></Refs>
						<Amt Ccy="EUR">100.00</Amt>
						<CdtDbtInd>CRDT</CdtDbtInd>
						<AmtDtls><TxAmt><Amt Ccy="EUR">100.00</Amt></TxAmt></AmtDtls>
						<RltdPties>
							<Dbtr><Pty><Nm>Customer Test</Nm></Pty></Dbtr>
							<DbtrAcct><Id><IBAN>CH4189144993834976921</IBAN></Id></DbtrAcct>
							<Cdtr><Pty><Nm>Test CO</Nm></Pty></Cdtr>
						</RltdPties>
						<RmtInf>
							<Ustrd>Vorauskasse OM06a-0006 Danke</Ustrd>
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
      | bsl_6a                            | bs_6a                         | 10   |
    And validate C_BankStatementLine
      | C_BankStatementLine_ID.Identifier | OPT.TrxAmt | OPT.C_Order_ID.Identifier | OPT.C_BPartner_ID.Identifier |
      | bsl_6a                            | 100.00     | null                      | null                         |
    And validate the created orders
      | Identifier | DocStatus | GrandTotal |
      | o_6a       | WP        | 123.52     |


  # ################################################################################
  # ################################################################################
  # ################################################################################

  @from:cucumber
  @Id:S32606_OM6b
  Scenario: Prepay order is not matched on a line with the wrong amount (partial amount, import matching amounts): only full prepayments are matched

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | DocBaseType | DocSubType | DocumentNo |
      | o_6b       | true    | bp_OM         | 2026-11-03  | SOO         | PR         | OM06b-0006 |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | l_o_6b     | o_6b       | p_OM         | 1          |
    And the order identified by o_6b is completed and waits for payment
    When bank statement is imported with identifiers bs_6b, matching invoice amounts
    """
<?xml version="1.0" encoding="utf-8"?>
<Document xmlns="urn:iso:std:iso:20022:tech:xsd:camt.053.001.08">
	<BkToCstmrStmt>
		<GrpHdr>
			<MsgId>MsgIdOM06b</MsgId>
			<CreDtTm>2026-11-03T19:45:37.000Z</CreDtTm>
			<MsgPgntn><PgNb>1</PgNb><LastPgInd>true</LastPgInd></MsgPgntn>
		</GrpHdr>
		<Stmt>
			<Id>StmtIdOM06b</Id>
			<ElctrncSeqNb>00001</ElctrncSeqNb>
			<CreDtTm>2026-11-03T19:45:37.000Z</CreDtTm>
			<FrToDt><FrDtTm>2026-11-02T00:00:00</FrDtTm><ToDtTm>2026-11-03T23:59:59</ToDtTm></FrToDt>
			<Acct>
				<Id><IBAN>CH3908704016075473007</IBAN></Id>
				<Ccy>EUR</Ccy>
				<Ownr><Nm>Test CO</Nm></Ownr>
				<Svcr><FinInstnId><BICFI>AAAAAAAA82A</BICFI><Nm>Test Bank</Nm></FinInstnId></Svcr>
			</Acct>
			<Bal>
				<Tp><CdOrPrtry><Cd>OPBD</Cd></CdOrPrtry></Tp>
				<Amt Ccy="EUR">1000.00</Amt>
				<CdtDbtInd>CRDT</CdtDbtInd>
				<Dt><Dt>2026-11-02</Dt></Dt>
			</Bal>
			<Bal>
				<Tp><CdOrPrtry><Cd>CLBD</Cd></CdOrPrtry></Tp>
				<Amt Ccy="EUR">1100.00</Amt>
				<CdtDbtInd>CRDT</CdtDbtInd>
				<Dt><Dt>2026-11-03</Dt></Dt>
			</Bal>
			<Ntry>
				<NtryRef>REF-OM06b</NtryRef>
				<Amt Ccy="EUR">100.00</Amt>
				<CdtDbtInd>CRDT</CdtDbtInd>
				<Sts><Cd>BOOK</Cd></Sts>
				<BookgDt><Dt>2026-11-03</Dt></BookgDt>
				<ValDt><Dt>2026-11-03</Dt></ValDt>
				<AcctSvcrRef>REF-OM06b</AcctSvcrRef>
				<BkTxCd><Prtry><Cd>TRF</Cd><Issr>SWIFT</Issr></Prtry></BkTxCd>
				<NtryDtls>
					<Btch>
						<NbOfTxs>1</NbOfTxs>
						<TtlAmt Ccy="EUR">100.00</TtlAmt>
						<CdtDbtInd>CRDT</CdtDbtInd>
					</Btch>
					<TxDtls>
						<Refs><AcctSvcrRef>REF-OM06b-1</AcctSvcrRef></Refs>
						<Amt Ccy="EUR">100.00</Amt>
						<CdtDbtInd>CRDT</CdtDbtInd>
						<AmtDtls><TxAmt><Amt Ccy="EUR">100.00</Amt></TxAmt></AmtDtls>
						<RltdPties>
							<Dbtr><Pty><Nm>Customer Test</Nm></Pty></Dbtr>
							<DbtrAcct><Id><IBAN>CH4189144993834976921</IBAN></Id></DbtrAcct>
							<Cdtr><Pty><Nm>Test CO</Nm></Pty></Cdtr>
						</RltdPties>
						<RmtInf>
							<Ustrd>Vorauskasse OM06b-0006 Danke</Ustrd>
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
      | bsl_6b                            | bs_6b                         | 10   |
    And validate C_BankStatementLine
      | C_BankStatementLine_ID.Identifier | OPT.TrxAmt | OPT.C_Order_ID.Identifier | OPT.C_BPartner_ID.Identifier |
      | bsl_6b                            | 100.00     | null                      | null                         |
    And validate the created orders
      | Identifier | DocStatus | GrandTotal |
      | o_6b       | WP        | 123.52     |


  # ################################################################################
  # ################################################################################
  # ################################################################################

  @from:cucumber
  @Id:S32606_OM6c
  Scenario: Prepay order is not matched on a line with the wrong amount (over-payment, import not matching amounts): only full prepayments are matched

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | DocBaseType | DocSubType | DocumentNo |
      | o_6c       | true    | bp_OM         | 2026-11-03  | SOO         | PR         | OM06c-0006 |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | l_o_6c     | o_6c       | p_OM         | 1          |
    And the order identified by o_6c is completed and waits for payment
    When bank statement is imported with identifiers bs_6c, not matching invoice amounts
    """
<?xml version="1.0" encoding="utf-8"?>
<Document xmlns="urn:iso:std:iso:20022:tech:xsd:camt.053.001.08">
	<BkToCstmrStmt>
		<GrpHdr>
			<MsgId>MsgIdOM06c</MsgId>
			<CreDtTm>2026-11-03T19:45:37.000Z</CreDtTm>
			<MsgPgntn><PgNb>1</PgNb><LastPgInd>true</LastPgInd></MsgPgntn>
		</GrpHdr>
		<Stmt>
			<Id>StmtIdOM06c</Id>
			<ElctrncSeqNb>00001</ElctrncSeqNb>
			<CreDtTm>2026-11-03T19:45:37.000Z</CreDtTm>
			<FrToDt><FrDtTm>2026-11-02T00:00:00</FrDtTm><ToDtTm>2026-11-03T23:59:59</ToDtTm></FrToDt>
			<Acct>
				<Id><IBAN>CH3908704016075473007</IBAN></Id>
				<Ccy>EUR</Ccy>
				<Ownr><Nm>Test CO</Nm></Ownr>
				<Svcr><FinInstnId><BICFI>AAAAAAAA82A</BICFI><Nm>Test Bank</Nm></FinInstnId></Svcr>
			</Acct>
			<Bal>
				<Tp><CdOrPrtry><Cd>OPBD</Cd></CdOrPrtry></Tp>
				<Amt Ccy="EUR">1000.00</Amt>
				<CdtDbtInd>CRDT</CdtDbtInd>
				<Dt><Dt>2026-11-02</Dt></Dt>
			</Bal>
			<Bal>
				<Tp><CdOrPrtry><Cd>CLBD</Cd></CdOrPrtry></Tp>
				<Amt Ccy="EUR">1150.00</Amt>
				<CdtDbtInd>CRDT</CdtDbtInd>
				<Dt><Dt>2026-11-03</Dt></Dt>
			</Bal>
			<Ntry>
				<NtryRef>REF-OM06c</NtryRef>
				<Amt Ccy="EUR">150.00</Amt>
				<CdtDbtInd>CRDT</CdtDbtInd>
				<Sts><Cd>BOOK</Cd></Sts>
				<BookgDt><Dt>2026-11-03</Dt></BookgDt>
				<ValDt><Dt>2026-11-03</Dt></ValDt>
				<AcctSvcrRef>REF-OM06c</AcctSvcrRef>
				<BkTxCd><Prtry><Cd>TRF</Cd><Issr>SWIFT</Issr></Prtry></BkTxCd>
				<NtryDtls>
					<Btch>
						<NbOfTxs>1</NbOfTxs>
						<TtlAmt Ccy="EUR">150.00</TtlAmt>
						<CdtDbtInd>CRDT</CdtDbtInd>
					</Btch>
					<TxDtls>
						<Refs><AcctSvcrRef>REF-OM06c-1</AcctSvcrRef></Refs>
						<Amt Ccy="EUR">150.00</Amt>
						<CdtDbtInd>CRDT</CdtDbtInd>
						<AmtDtls><TxAmt><Amt Ccy="EUR">150.00</Amt></TxAmt></AmtDtls>
						<RltdPties>
							<Dbtr><Pty><Nm>Customer Test</Nm></Pty></Dbtr>
							<DbtrAcct><Id><IBAN>CH4189144993834976921</IBAN></Id></DbtrAcct>
							<Cdtr><Pty><Nm>Test CO</Nm></Pty></Cdtr>
						</RltdPties>
						<RmtInf>
							<Ustrd>Vorauskasse OM06c-0006 Danke</Ustrd>
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
      | bsl_6c                            | bs_6c                         | 10   |
    And validate C_BankStatementLine
      | C_BankStatementLine_ID.Identifier | OPT.TrxAmt | OPT.C_Order_ID.Identifier | OPT.C_BPartner_ID.Identifier |
      | bsl_6c                            | 150.00     | null                      | null                         |
    And validate the created orders
      | Identifier | DocStatus | GrandTotal |
      | o_6c       | WP        | 123.52     |


  # ################################################################################
  # ################################################################################
  # ################################################################################

  @from:cucumber
  @Id:S32606_OM7
  Scenario: Order released by the bank-file payment and invoiced afterwards: the payment is allocated to the invoice

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | DocBaseType | DocSubType | DocumentNo |
      | o_7        | true    | bp_OM         | 2026-11-03  | SOO         | PR         | OM07-0007  |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | l_o_7      | o_7        | p_OM         | 1          |
    And the order identified by o_7 is completed and waits for payment
    When bank statement is imported with identifiers bs_7, matching invoice amounts
    """
<?xml version="1.0" encoding="utf-8"?>
<Document xmlns="urn:iso:std:iso:20022:tech:xsd:camt.053.001.08">
	<BkToCstmrStmt>
		<GrpHdr>
			<MsgId>MsgIdOM07</MsgId>
			<CreDtTm>2026-11-03T19:45:37.000Z</CreDtTm>
			<MsgPgntn><PgNb>1</PgNb><LastPgInd>true</LastPgInd></MsgPgntn>
		</GrpHdr>
		<Stmt>
			<Id>StmtIdOM07</Id>
			<ElctrncSeqNb>00001</ElctrncSeqNb>
			<CreDtTm>2026-11-03T19:45:37.000Z</CreDtTm>
			<FrToDt><FrDtTm>2026-11-02T00:00:00</FrDtTm><ToDtTm>2026-11-03T23:59:59</ToDtTm></FrToDt>
			<Acct>
				<Id><IBAN>CH3908704016075473007</IBAN></Id>
				<Ccy>EUR</Ccy>
				<Ownr><Nm>Test CO</Nm></Ownr>
				<Svcr><FinInstnId><BICFI>AAAAAAAA82A</BICFI><Nm>Test Bank</Nm></FinInstnId></Svcr>
			</Acct>
			<Bal>
				<Tp><CdOrPrtry><Cd>OPBD</Cd></CdOrPrtry></Tp>
				<Amt Ccy="EUR">1000.00</Amt>
				<CdtDbtInd>CRDT</CdtDbtInd>
				<Dt><Dt>2026-11-02</Dt></Dt>
			</Bal>
			<Bal>
				<Tp><CdOrPrtry><Cd>CLBD</Cd></CdOrPrtry></Tp>
				<Amt Ccy="EUR">1123.52</Amt>
				<CdtDbtInd>CRDT</CdtDbtInd>
				<Dt><Dt>2026-11-03</Dt></Dt>
			</Bal>
			<Ntry>
				<NtryRef>REF-OM07</NtryRef>
				<Amt Ccy="EUR">123.52</Amt>
				<CdtDbtInd>CRDT</CdtDbtInd>
				<Sts><Cd>BOOK</Cd></Sts>
				<BookgDt><Dt>2026-11-03</Dt></BookgDt>
				<ValDt><Dt>2026-11-03</Dt></ValDt>
				<AcctSvcrRef>REF-OM07</AcctSvcrRef>
				<BkTxCd><Prtry><Cd>TRF</Cd><Issr>SWIFT</Issr></Prtry></BkTxCd>
				<NtryDtls>
					<Btch>
						<NbOfTxs>1</NbOfTxs>
						<TtlAmt Ccy="EUR">123.52</TtlAmt>
						<CdtDbtInd>CRDT</CdtDbtInd>
					</Btch>
					<TxDtls>
						<Refs><AcctSvcrRef>REF-OM07-1</AcctSvcrRef></Refs>
						<Amt Ccy="EUR">123.52</Amt>
						<CdtDbtInd>CRDT</CdtDbtInd>
						<AmtDtls><TxAmt><Amt Ccy="EUR">123.52</Amt></TxAmt></AmtDtls>
						<RltdPties>
							<Dbtr><Pty><Nm>Customer Test</Nm></Pty></Dbtr>
							<DbtrAcct><Id><IBAN>CH4189144993834976921</IBAN></Id></DbtrAcct>
							<Cdtr><Pty><Nm>Test CO</Nm></Pty></Cdtr>
						</RltdPties>
						<RmtInf>
							<Ustrd>Vorauskasse OM07-0007 Danke</Ustrd>
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
      | bsl_7                             | bs_7                          | 10   |
    And validate C_BankStatementLine
      | C_BankStatementLine_ID.Identifier | OPT.C_Order_ID.Identifier | OPT.C_BPartner_ID.Identifier |
      | bsl_7                             | o_7                       | bp_OM                        |
    And the C_BankStatement identified by bs_7 is completed
    And after not more than 60s, C_Payment is found
      | C_Payment_ID.Identifier | OPT.C_BankStatement_ID.Identifier | OPT.C_BankStatementLine_ID.Identifier |
      | p_7                     | bs_7                              | bsl_7                                 |
    And validate the created orders
      | Identifier | DocStatus | GrandTotal |
      | o_7        | CO        | 123.52     |
    And after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier |
      | ic_7                              | l_o_7                     |
    When process invoice candidates and wait 60s for C_Invoice_Candidate to be processed
      | C_Invoice_Candidate_ID.Identifier |
      | ic_7                              |
    Then after not more than 60s, C_Invoice are found:
      | C_Invoice_Candidate_ID.Identifier | C_Invoice_ID.Identifier |
      | ic_7                              | inv_7                   |
    And validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | DocStatus | OPT.IsPaid |
      | inv_7                   | bp_OM                    | CO        | true       |


  # ################################################################################
  # ################################################################################
  # ################################################################################

  @from:cucumber
  @Id:S32606_OM8c
  Scenario: Order picked by hand on a bank statement line: completing the statement creates the payment with the order and releases the order

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | DocBaseType | DocSubType | DocumentNo |
      | o_8c       | true    | bp_OM         | 2026-11-03  | SOO         | PR         | OM08C-0008 |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | l_o_8c     | o_8c       | p_OM         | 1          |
    And the order identified by o_8c is completed and waits for payment
    And metasfresh contains C_BankStatement:
      | Identifier | C_BP_BankAccount_ID | StatementDate |
      | bs_8c      | bpb_OM              | 2026-11-03    |
    And metasfresh contains C_BankStatementLine:
      | Identifier | C_BankStatement_ID | Amount     | C_BPartner_ID | C_Order_ID |
      | bsl_8c     | bs_8c              | 123.52 EUR | bp_OM         | o_8c       |
    And the C_BankStatement identified by bs_8c is completed
    And after not more than 60s, C_Payment is found
      | C_Payment_ID.Identifier | OPT.C_BankStatement_ID.Identifier | OPT.C_BankStatementLine_ID.Identifier |
      | p_8c                    | bs_8c                             | bsl_8c                                |
    And validate payments
      | C_Payment_ID.Identifier | PayAmt | IsReceipt | C_Order_ID.Identifier |
      | p_8c                    | 123.52 | true      | o_8c                  |
    And validate the created orders
      | Identifier | DocStatus | GrandTotal |
      | o_8c       | CO        | 123.52     |


  # ################################################################################
  # ################################################################################
  # ################################################################################

  @from:cucumber
  @Id:S32606_OM10
  Scenario: Debit line naming a waiting order with the same absolute amount: no order is set

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | DocBaseType | DocSubType | DocumentNo |
      | o_10       | true    | bp_OM         | 2026-11-03  | SOO         | PR         | OM10-0010  |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | l_o_10     | o_10       | p_OM         | 1          |
    And the order identified by o_10 is completed and waits for payment
    When bank statement is imported with identifiers bs_10, not matching invoice amounts
    """
<?xml version="1.0" encoding="utf-8"?>
<Document xmlns="urn:iso:std:iso:20022:tech:xsd:camt.053.001.08">
	<BkToCstmrStmt>
		<GrpHdr>
			<MsgId>MsgIdOM10</MsgId>
			<CreDtTm>2026-11-03T19:45:37.000Z</CreDtTm>
			<MsgPgntn><PgNb>1</PgNb><LastPgInd>true</LastPgInd></MsgPgntn>
		</GrpHdr>
		<Stmt>
			<Id>StmtIdOM10</Id>
			<ElctrncSeqNb>00001</ElctrncSeqNb>
			<CreDtTm>2026-11-03T19:45:37.000Z</CreDtTm>
			<FrToDt><FrDtTm>2026-11-02T00:00:00</FrDtTm><ToDtTm>2026-11-03T23:59:59</ToDtTm></FrToDt>
			<Acct>
				<Id><IBAN>CH3908704016075473007</IBAN></Id>
				<Ccy>EUR</Ccy>
				<Ownr><Nm>Test CO</Nm></Ownr>
				<Svcr><FinInstnId><BICFI>AAAAAAAA82A</BICFI><Nm>Test Bank</Nm></FinInstnId></Svcr>
			</Acct>
			<Bal>
				<Tp><CdOrPrtry><Cd>OPBD</Cd></CdOrPrtry></Tp>
				<Amt Ccy="EUR">1000.00</Amt>
				<CdtDbtInd>CRDT</CdtDbtInd>
				<Dt><Dt>2026-11-02</Dt></Dt>
			</Bal>
			<Bal>
				<Tp><CdOrPrtry><Cd>CLBD</Cd></CdOrPrtry></Tp>
				<Amt Ccy="EUR">876.48</Amt>
				<CdtDbtInd>CRDT</CdtDbtInd>
				<Dt><Dt>2026-11-03</Dt></Dt>
			</Bal>
			<Ntry>
				<NtryRef>REF-OM10</NtryRef>
				<Amt Ccy="EUR">123.52</Amt>
				<CdtDbtInd>DBIT</CdtDbtInd>
				<Sts><Cd>BOOK</Cd></Sts>
				<BookgDt><Dt>2026-11-03</Dt></BookgDt>
				<ValDt><Dt>2026-11-03</Dt></ValDt>
				<AcctSvcrRef>REF-OM10</AcctSvcrRef>
				<BkTxCd><Prtry><Cd>TRF</Cd><Issr>SWIFT</Issr></Prtry></BkTxCd>
				<NtryDtls>
					<Btch>
						<NbOfTxs>1</NbOfTxs>
						<TtlAmt Ccy="EUR">123.52</TtlAmt>
						<CdtDbtInd>DBIT</CdtDbtInd>
					</Btch>
					<TxDtls>
						<Refs><AcctSvcrRef>REF-OM10-1</AcctSvcrRef></Refs>
						<Amt Ccy="EUR">123.52</Amt>
						<CdtDbtInd>DBIT</CdtDbtInd>
						<AmtDtls><TxAmt><Amt Ccy="EUR">123.52</Amt></TxAmt></AmtDtls>
						<RltdPties>
							<Dbtr><Pty><Nm>Customer Test</Nm></Pty></Dbtr>
							<DbtrAcct><Id><IBAN>CH4189144993834976921</IBAN></Id></DbtrAcct>
							<Cdtr><Pty><Nm>Test CO</Nm></Pty></Cdtr>
						</RltdPties>
						<RmtInf>
							<Ustrd>Vorauskasse OM10-0010 Danke</Ustrd>
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
      | bsl_10                            | bs_10                         | 10   |
    And validate C_BankStatementLine
      | C_BankStatementLine_ID.Identifier | OPT.TrxAmt | OPT.C_Order_ID.Identifier | OPT.C_BPartner_ID.Identifier |
      | bsl_10                            | -123.52    | null                      | null                         |
    And validate the created orders
      | Identifier | DocStatus | GrandTotal |
      | o_10       | WP        | 123.52     |


  # ################################################################################
  # ################################################################################
  # ################################################################################

  @from:cucumber
  @Id:S32606_OM11
  Scenario: Debit line with a hand-picked order: the payment is outgoing and carries no order

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | DocBaseType | DocSubType | DocumentNo |
      | o_11       | true    | bp_OM         | 2026-11-03  | SOO         | PR         | OM11-0011  |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | l_o_11     | o_11       | p_OM         | 1          |
    And the order identified by o_11 is completed and waits for payment
    And metasfresh contains C_BankStatement:
      | Identifier | C_BP_BankAccount_ID | StatementDate |
      | bs_11      | bpb_OM              | 2026-11-03    |
    And metasfresh contains C_BankStatementLine:
      | Identifier | C_BankStatement_ID | Amount      | C_BPartner_ID | C_Order_ID |
      | bsl_11     | bs_11              | -123.52 EUR | bp_OM         | o_11       |
    And the C_BankStatement identified by bs_11 is completed
    And after not more than 60s, C_Payment is found
      | C_Payment_ID.Identifier | OPT.C_BankStatement_ID.Identifier | OPT.C_BankStatementLine_ID.Identifier |
      | p_11                    | bs_11                             | bsl_11                                |
    And validate payments
      | C_Payment_ID.Identifier | IsReceipt | C_Order_ID.Identifier |
      | p_11                    | false     | null                  |
    And validate the created orders
      | Identifier | DocStatus | GrandTotal |
      | o_11       | WP        | 123.52     |


  # ################################################################################
  # ################################################################################
  # ################################################################################

  @from:cucumber
  @Id:S32606_OM12
  Scenario: 0.00 line with a hand-picked order: the payment carries no order

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | DocBaseType | DocSubType | DocumentNo |
      | o_12       | true    | bp_OM         | 2026-11-03  | SOO         | PR         | OM12-0012  |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | l_o_12     | o_12       | p_OM         | 1          |
    And the order identified by o_12 is completed and waits for payment
    And metasfresh contains C_BankStatement:
      | Identifier | C_BP_BankAccount_ID | StatementDate |
      | bs_12      | bpb_OM              | 2026-11-03    |
    And metasfresh contains C_BankStatementLine:
      | Identifier | C_BankStatement_ID | Amount   | C_BPartner_ID | C_Order_ID |
      | bsl_12     | bs_12              | 0.00 EUR | bp_OM         | o_12       |
    And the C_BankStatement identified by bs_12 is completed
    And after not more than 60s, C_Payment is found
      | C_Payment_ID.Identifier | OPT.C_BankStatement_ID.Identifier | OPT.C_BankStatementLine_ID.Identifier |
      | p_12                    | bs_12                             | bsl_12                                |
    And validate payments
      | C_Payment_ID.Identifier | C_Order_ID.Identifier |
      | p_12                    | null                  |
    And validate the created orders
      | Identifier | DocStatus | GrandTotal |
      | o_12       | WP        | 123.52     |


  # ################################################################################
  # ################################################################################
  # ################################################################################

  @from:cucumber
  @Id:S32606_OM13a
  Scenario: Batch entry: the 0.00 detail lines get no order, even when the text of the first transaction names one

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | DocBaseType | DocSubType | DocumentNo |
      | o_13a      | true    | bp_OM         | 2026-11-03  | SOO         | PR         | OM13-0013  |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | l_o_13a    | o_13a      | p_OM         | 1          |
    And the order identified by o_13a is completed and waits for payment
    When bank statement is imported with identifiers bs_13a, not matching invoice amounts
    """
<?xml version="1.0" encoding="utf-8"?>
<Document xmlns="urn:iso:std:iso:20022:tech:xsd:camt.053.001.08">
	<BkToCstmrStmt>
		<GrpHdr>
			<MsgId>MsgIdOM13</MsgId>
			<CreDtTm>2026-11-03T19:45:37.000Z</CreDtTm>
			<MsgPgntn><PgNb>1</PgNb><LastPgInd>true</LastPgInd></MsgPgntn>
		</GrpHdr>
		<Stmt>
			<Id>StmtIdOM13</Id>
			<ElctrncSeqNb>00001</ElctrncSeqNb>
			<CreDtTm>2026-11-03T19:45:37.000Z</CreDtTm>
			<FrToDt><FrDtTm>2026-11-02T00:00:00</FrDtTm><ToDtTm>2026-11-03T23:59:59</ToDtTm></FrToDt>
			<Acct>
				<Id><IBAN>CH3908704016075473007</IBAN></Id>
				<Ccy>EUR</Ccy>
				<Ownr><Nm>Test CO</Nm></Ownr>
				<Svcr><FinInstnId><BICFI>AAAAAAAA82A</BICFI><Nm>Test Bank</Nm></FinInstnId></Svcr>
			</Acct>
			<Bal>
				<Tp><CdOrPrtry><Cd>OPBD</Cd></CdOrPrtry></Tp>
				<Amt Ccy="EUR">1000.00</Amt>
				<CdtDbtInd>CRDT</CdtDbtInd>
				<Dt><Dt>2026-11-02</Dt></Dt>
			</Bal>
			<Bal>
				<Tp><CdOrPrtry><Cd>CLBD</Cd></CdOrPrtry></Tp>
				<Amt Ccy="EUR">1123.52</Amt>
				<CdtDbtInd>CRDT</CdtDbtInd>
				<Dt><Dt>2026-11-03</Dt></Dt>
			</Bal>
			<Ntry>
				<NtryRef>REF-OM13</NtryRef>
				<Amt Ccy="EUR">123.52</Amt>
				<CdtDbtInd>CRDT</CdtDbtInd>
				<Sts><Cd>BOOK</Cd></Sts>
				<BookgDt><Dt>2026-11-03</Dt></BookgDt>
				<ValDt><Dt>2026-11-03</Dt></ValDt>
				<AcctSvcrRef>REF-OM13</AcctSvcrRef>
				<BkTxCd><Prtry><Cd>TRF</Cd><Issr>SWIFT</Issr></Prtry></BkTxCd>
				<NtryDtls>
					<Btch>
						<NbOfTxs>2</NbOfTxs>
						<TtlAmt Ccy="EUR">123.52</TtlAmt>
						<CdtDbtInd>CRDT</CdtDbtInd>
					</Btch>
					<TxDtls>
						<Refs><AcctSvcrRef>REF-OM13-1</AcctSvcrRef></Refs>
						<Amt Ccy="EUR">61.76</Amt>
						<CdtDbtInd>CRDT</CdtDbtInd>
						<AmtDtls><TxAmt><Amt Ccy="EUR">61.76</Amt></TxAmt></AmtDtls>
						<RltdPties>
							<Dbtr><Pty><Nm>Customer Test</Nm></Pty></Dbtr>
							<DbtrAcct><Id><IBAN>CH4189144993834976921</IBAN></Id></DbtrAcct>
							<Cdtr><Pty><Nm>Test CO</Nm></Pty></Cdtr>
						</RltdPties>
						<RmtInf>
							<Ustrd>Vorauskasse OM13-0013</Ustrd>
						</RmtInf>
					</TxDtls>
					<TxDtls>
						<Refs><AcctSvcrRef>REF-OM13-2</AcctSvcrRef></Refs>
						<Amt Ccy="EUR">61.76</Amt>
						<CdtDbtInd>CRDT</CdtDbtInd>
						<AmtDtls><TxAmt><Amt Ccy="EUR">61.76</Amt></TxAmt></AmtDtls>
						<RltdPties>
							<Dbtr><Pty><Nm>Customer Test</Nm></Pty></Dbtr>
							<DbtrAcct><Id><IBAN>CH4189144993834976921</IBAN></Id></DbtrAcct>
							<Cdtr><Pty><Nm>Test CO</Nm></Pty></Cdtr>
						</RltdPties>
						<RmtInf>
							<Ustrd>Sammelzahlung zweite Position</Ustrd>
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
      | bsl_13a_d1                        | bs_13a                        | 20   |
    And load C_BankStatementLine
      | C_BankStatementLine_ID.Identifier | C_BankStatement_ID.Identifier | Line |
      | bsl_13a_d2                        | bs_13a                        | 30   |
    And validate C_BankStatementLine
      | C_BankStatementLine_ID.Identifier | OPT.TrxAmt | OPT.C_Order_ID.Identifier | OPT.C_BPartner_ID.Identifier |
      | bsl_13a_d1                        | 0          | null                      | null                         |
    And validate C_BankStatementLine
      | C_BankStatementLine_ID.Identifier | OPT.TrxAmt | OPT.C_Order_ID.Identifier | OPT.C_BPartner_ID.Identifier |
      | bsl_13a_d2                        | 0          | null                      | null                         |


  # ################################################################################
  # ################################################################################
  # ################################################################################

  @from:cucumber
  @Id:S32606_OM13b
  Scenario: Batch entry: the money line (batch total) carries the order; completing creates one payment with the order for the batch total

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | DocBaseType | DocSubType | DocumentNo |
      | o_13b      | true    | bp_OM         | 2026-11-03  | SOO         | PR         | OM13B-0013 |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | l_o_13b    | o_13b      | p_OM         | 1          |
    And the order identified by o_13b is completed and waits for payment
    When bank statement is imported with identifiers bs_13b, not matching invoice amounts
    """
<?xml version="1.0" encoding="utf-8"?>
<Document xmlns="urn:iso:std:iso:20022:tech:xsd:camt.053.001.08">
	<BkToCstmrStmt>
		<GrpHdr>
			<MsgId>MsgIdOM13B</MsgId>
			<CreDtTm>2026-11-03T19:45:37.000Z</CreDtTm>
			<MsgPgntn><PgNb>1</PgNb><LastPgInd>true</LastPgInd></MsgPgntn>
		</GrpHdr>
		<Stmt>
			<Id>StmtIdOM13B</Id>
			<ElctrncSeqNb>00001</ElctrncSeqNb>
			<CreDtTm>2026-11-03T19:45:37.000Z</CreDtTm>
			<FrToDt><FrDtTm>2026-11-02T00:00:00</FrDtTm><ToDtTm>2026-11-03T23:59:59</ToDtTm></FrToDt>
			<Acct>
				<Id><IBAN>CH3908704016075473007</IBAN></Id>
				<Ccy>EUR</Ccy>
				<Ownr><Nm>Test CO</Nm></Ownr>
				<Svcr><FinInstnId><BICFI>AAAAAAAA82A</BICFI><Nm>Test Bank</Nm></FinInstnId></Svcr>
			</Acct>
			<Bal>
				<Tp><CdOrPrtry><Cd>OPBD</Cd></CdOrPrtry></Tp>
				<Amt Ccy="EUR">1000.00</Amt>
				<CdtDbtInd>CRDT</CdtDbtInd>
				<Dt><Dt>2026-11-02</Dt></Dt>
			</Bal>
			<Bal>
				<Tp><CdOrPrtry><Cd>CLBD</Cd></CdOrPrtry></Tp>
				<Amt Ccy="EUR">1123.52</Amt>
				<CdtDbtInd>CRDT</CdtDbtInd>
				<Dt><Dt>2026-11-03</Dt></Dt>
			</Bal>
			<Ntry>
				<NtryRef>REF-OM13</NtryRef>
				<Amt Ccy="EUR">123.52</Amt>
				<CdtDbtInd>CRDT</CdtDbtInd>
				<Sts><Cd>BOOK</Cd></Sts>
				<BookgDt><Dt>2026-11-03</Dt></BookgDt>
				<ValDt><Dt>2026-11-03</Dt></ValDt>
				<AcctSvcrRef>REF-OM13</AcctSvcrRef>
				<BkTxCd><Prtry><Cd>TRF</Cd><Issr>SWIFT</Issr></Prtry></BkTxCd>
				<NtryDtls>
					<Btch>
						<NbOfTxs>2</NbOfTxs>
						<TtlAmt Ccy="EUR">123.52</TtlAmt>
						<CdtDbtInd>CRDT</CdtDbtInd>
					</Btch>
					<TxDtls>
						<Refs><AcctSvcrRef>REF-OM13-1</AcctSvcrRef></Refs>
						<Amt Ccy="EUR">61.76</Amt>
						<CdtDbtInd>CRDT</CdtDbtInd>
						<AmtDtls><TxAmt><Amt Ccy="EUR">61.76</Amt></TxAmt></AmtDtls>
						<RltdPties>
							<Dbtr><Pty><Nm>Customer Test</Nm></Pty></Dbtr>
							<DbtrAcct><Id><IBAN>CH4189144993834976921</IBAN></Id></DbtrAcct>
							<Cdtr><Pty><Nm>Test CO</Nm></Pty></Cdtr>
						</RltdPties>
						<RmtInf>
							<Ustrd>Vorauskasse OM13B-0013</Ustrd>
						</RmtInf>
					</TxDtls>
					<TxDtls>
						<Refs><AcctSvcrRef>REF-OM13-2</AcctSvcrRef></Refs>
						<Amt Ccy="EUR">61.76</Amt>
						<CdtDbtInd>CRDT</CdtDbtInd>
						<AmtDtls><TxAmt><Amt Ccy="EUR">61.76</Amt></TxAmt></AmtDtls>
						<RltdPties>
							<Dbtr><Pty><Nm>Customer Test</Nm></Pty></Dbtr>
							<DbtrAcct><Id><IBAN>CH4189144993834976921</IBAN></Id></DbtrAcct>
							<Cdtr><Pty><Nm>Test CO</Nm></Pty></Cdtr>
						</RltdPties>
						<RmtInf>
							<Ustrd>Sammelzahlung zweite Position</Ustrd>
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
      | bsl_13b                           | bs_13b                        | 10   |
    And validate C_BankStatementLine
      | C_BankStatementLine_ID.Identifier | OPT.TrxAmt | OPT.C_Order_ID.Identifier | OPT.C_BPartner_ID.Identifier |
      | bsl_13b                           | 123.52     | o_13b                     | bp_OM                        |
    And load C_BankStatementLine
      | C_BankStatementLine_ID.Identifier | C_BankStatement_ID.Identifier | Line |
      | bsl_13b_d1                        | bs_13b                        | 20   |
    And validate C_BankStatementLine
      | C_BankStatementLine_ID.Identifier | OPT.C_Order_ID.Identifier |
      | bsl_13b_d1                        | null                      |
    And the C_BankStatement identified by bs_13b is completed
    And after not more than 60s, C_Payment is found
      | C_Payment_ID.Identifier | OPT.C_BankStatement_ID.Identifier | OPT.C_BankStatementLine_ID.Identifier |
      | p_13b                   | bs_13b                            | bsl_13b                               |
    And validate payments
      | C_Payment_ID.Identifier | PayAmt | C_Order_ID.Identifier |
      | p_13b                   | 123.52 | o_13b                 |
    And validate the created orders
      | Identifier | DocStatus | GrandTotal |
      | o_13b      | CO        | 123.52     |


  # ################################################################################
  # ################################################################################
  # ################################################################################

  @from:cucumber
  @Id:S32606_OM17a
  Scenario: Payment-reservation order (standard order waiting for payment) and prepay order with the same total: only the prepay order is set

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | DocBaseType | DocSubType | DocumentNo |
      | o_17a      | true    | bp_OM         | 2026-11-03  | SOO         | PR         | OM17-PRE   |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | l_o_17a    | o_17a      | p_OM         | 1          |
    And the order identified by o_17a is completed and waits for payment
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | DocBaseType | DocSubType | DocumentNo | OPT.PaymentRule |
      | o_17b      | true    | bp_OM         | 2026-11-03  | SOO         | SO         | OM17-PAY   | L               |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | l_o_17b    | o_17b      | p_OM         | 1          |
    And the order identified by o_17b is completed
    And the completed order identified by o_17b is put into waiting for payment as a payment reservation would
    When bank statement is imported with identifiers bs_17a, matching invoice amounts
    """
<?xml version="1.0" encoding="utf-8"?>
<Document xmlns="urn:iso:std:iso:20022:tech:xsd:camt.053.001.08">
	<BkToCstmrStmt>
		<GrpHdr>
			<MsgId>MsgIdOM17A</MsgId>
			<CreDtTm>2026-11-03T19:45:37.000Z</CreDtTm>
			<MsgPgntn><PgNb>1</PgNb><LastPgInd>true</LastPgInd></MsgPgntn>
		</GrpHdr>
		<Stmt>
			<Id>StmtIdOM17A</Id>
			<ElctrncSeqNb>00001</ElctrncSeqNb>
			<CreDtTm>2026-11-03T19:45:37.000Z</CreDtTm>
			<FrToDt><FrDtTm>2026-11-02T00:00:00</FrDtTm><ToDtTm>2026-11-03T23:59:59</ToDtTm></FrToDt>
			<Acct>
				<Id><IBAN>CH3908704016075473007</IBAN></Id>
				<Ccy>EUR</Ccy>
				<Ownr><Nm>Test CO</Nm></Ownr>
				<Svcr><FinInstnId><BICFI>AAAAAAAA82A</BICFI><Nm>Test Bank</Nm></FinInstnId></Svcr>
			</Acct>
			<Bal>
				<Tp><CdOrPrtry><Cd>OPBD</Cd></CdOrPrtry></Tp>
				<Amt Ccy="EUR">1000.00</Amt>
				<CdtDbtInd>CRDT</CdtDbtInd>
				<Dt><Dt>2026-11-02</Dt></Dt>
			</Bal>
			<Bal>
				<Tp><CdOrPrtry><Cd>CLBD</Cd></CdOrPrtry></Tp>
				<Amt Ccy="EUR">1123.52</Amt>
				<CdtDbtInd>CRDT</CdtDbtInd>
				<Dt><Dt>2026-11-03</Dt></Dt>
			</Bal>
			<Ntry>
				<NtryRef>REF-OM17A</NtryRef>
				<Amt Ccy="EUR">123.52</Amt>
				<CdtDbtInd>CRDT</CdtDbtInd>
				<Sts><Cd>BOOK</Cd></Sts>
				<BookgDt><Dt>2026-11-03</Dt></BookgDt>
				<ValDt><Dt>2026-11-03</Dt></ValDt>
				<AcctSvcrRef>REF-OM17A</AcctSvcrRef>
				<BkTxCd><Prtry><Cd>TRF</Cd><Issr>SWIFT</Issr></Prtry></BkTxCd>
				<NtryDtls>
					<Btch>
						<NbOfTxs>1</NbOfTxs>
						<TtlAmt Ccy="EUR">123.52</TtlAmt>
						<CdtDbtInd>CRDT</CdtDbtInd>
					</Btch>
					<TxDtls>
						<Refs><AcctSvcrRef>REF-OM17A-1</AcctSvcrRef></Refs>
						<Amt Ccy="EUR">123.52</Amt>
						<CdtDbtInd>CRDT</CdtDbtInd>
						<AmtDtls><TxAmt><Amt Ccy="EUR">123.52</Amt></TxAmt></AmtDtls>
						<RltdPties>
							<Dbtr><Pty><Nm>Customer Test</Nm></Pty></Dbtr>
							<DbtrAcct><Id><IBAN>CH4189144993834976921</IBAN></Id></DbtrAcct>
							<Cdtr><Pty><Nm>Test CO</Nm></Pty></Cdtr>
						</RltdPties>
						<RmtInf>
							<Ustrd>Zahlung OM17-PRE und OM17-PAY</Ustrd>
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
      | bsl_17a                           | bs_17a                        | 10   |
    And validate C_BankStatementLine
      | C_BankStatementLine_ID.Identifier | OPT.C_Order_ID.Identifier | OPT.C_BPartner_ID.Identifier |
      | bsl_17a                           | o_17a                     | bp_OM                        |
    And validate the created orders
      | Identifier | DocStatus | GrandTotal |
      | o_17b      | WP        | 123.52     |


  # ################################################################################
  # ################################################################################
  # ################################################################################

  @from:cucumber
  @Id:S32606_OM17b
  Scenario: Payment-reservation order with the same total, only its number in the text: no order is set

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | DocBaseType | DocSubType | DocumentNo | OPT.PaymentRule |
      | o_17c      | true    | bp_OM         | 2026-11-03  | SOO         | SO         | OM17-ONLY  | L               |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | l_o_17c    | o_17c      | p_OM         | 1          |
    And the order identified by o_17c is completed
    And the completed order identified by o_17c is put into waiting for payment as a payment reservation would
    When bank statement is imported with identifiers bs_17b, matching invoice amounts
    """
<?xml version="1.0" encoding="utf-8"?>
<Document xmlns="urn:iso:std:iso:20022:tech:xsd:camt.053.001.08">
	<BkToCstmrStmt>
		<GrpHdr>
			<MsgId>MsgIdOM17B</MsgId>
			<CreDtTm>2026-11-03T19:45:37.000Z</CreDtTm>
			<MsgPgntn><PgNb>1</PgNb><LastPgInd>true</LastPgInd></MsgPgntn>
		</GrpHdr>
		<Stmt>
			<Id>StmtIdOM17B</Id>
			<ElctrncSeqNb>00001</ElctrncSeqNb>
			<CreDtTm>2026-11-03T19:45:37.000Z</CreDtTm>
			<FrToDt><FrDtTm>2026-11-02T00:00:00</FrDtTm><ToDtTm>2026-11-03T23:59:59</ToDtTm></FrToDt>
			<Acct>
				<Id><IBAN>CH3908704016075473007</IBAN></Id>
				<Ccy>EUR</Ccy>
				<Ownr><Nm>Test CO</Nm></Ownr>
				<Svcr><FinInstnId><BICFI>AAAAAAAA82A</BICFI><Nm>Test Bank</Nm></FinInstnId></Svcr>
			</Acct>
			<Bal>
				<Tp><CdOrPrtry><Cd>OPBD</Cd></CdOrPrtry></Tp>
				<Amt Ccy="EUR">1000.00</Amt>
				<CdtDbtInd>CRDT</CdtDbtInd>
				<Dt><Dt>2026-11-02</Dt></Dt>
			</Bal>
			<Bal>
				<Tp><CdOrPrtry><Cd>CLBD</Cd></CdOrPrtry></Tp>
				<Amt Ccy="EUR">1123.52</Amt>
				<CdtDbtInd>CRDT</CdtDbtInd>
				<Dt><Dt>2026-11-03</Dt></Dt>
			</Bal>
			<Ntry>
				<NtryRef>REF-OM17B</NtryRef>
				<Amt Ccy="EUR">123.52</Amt>
				<CdtDbtInd>CRDT</CdtDbtInd>
				<Sts><Cd>BOOK</Cd></Sts>
				<BookgDt><Dt>2026-11-03</Dt></BookgDt>
				<ValDt><Dt>2026-11-03</Dt></ValDt>
				<AcctSvcrRef>REF-OM17B</AcctSvcrRef>
				<BkTxCd><Prtry><Cd>TRF</Cd><Issr>SWIFT</Issr></Prtry></BkTxCd>
				<NtryDtls>
					<Btch>
						<NbOfTxs>1</NbOfTxs>
						<TtlAmt Ccy="EUR">123.52</TtlAmt>
						<CdtDbtInd>CRDT</CdtDbtInd>
					</Btch>
					<TxDtls>
						<Refs><AcctSvcrRef>REF-OM17B-1</AcctSvcrRef></Refs>
						<Amt Ccy="EUR">123.52</Amt>
						<CdtDbtInd>CRDT</CdtDbtInd>
						<AmtDtls><TxAmt><Amt Ccy="EUR">123.52</Amt></TxAmt></AmtDtls>
						<RltdPties>
							<Dbtr><Pty><Nm>Customer Test</Nm></Pty></Dbtr>
							<DbtrAcct><Id><IBAN>CH4189144993834976921</IBAN></Id></DbtrAcct>
							<Cdtr><Pty><Nm>Test CO</Nm></Pty></Cdtr>
						</RltdPties>
						<RmtInf>
							<Ustrd>Zahlung OM17-ONLY</Ustrd>
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
      | bsl_17b                           | bs_17b                        | 10   |
    And validate C_BankStatementLine
      | C_BankStatementLine_ID.Identifier | OPT.C_Order_ID.Identifier | OPT.C_BPartner_ID.Identifier |
      | bsl_17b                           | null                      | null                         |
    And validate the created orders
      | Identifier | DocStatus | GrandTotal |
      | o_17c      | WP        | 123.52     |


  # ################################################################################
  # ################################################################################
  # ################################################################################

  @from:cucumber
  @Id:S32606_OM20a
  Scenario: Entry in another currency than the order (CHF vs EUR), import matching amounts: no order is set

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | DocBaseType | DocSubType | DocumentNo |
      | o_20a      | true    | bp_OM         | 2026-11-03  | SOO         | PR         | OM20a-0020 |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | l_o_20a    | o_20a      | p_OM         | 1          |
    And the order identified by o_20a is completed and waits for payment
    When bank statement is imported with identifiers bs_20a, matching invoice amounts
    """
<?xml version="1.0" encoding="utf-8"?>
<Document xmlns="urn:iso:std:iso:20022:tech:xsd:camt.053.001.08">
	<BkToCstmrStmt>
		<GrpHdr>
			<MsgId>MsgIdOM20a</MsgId>
			<CreDtTm>2026-11-03T19:45:37.000Z</CreDtTm>
			<MsgPgntn><PgNb>1</PgNb><LastPgInd>true</LastPgInd></MsgPgntn>
		</GrpHdr>
		<Stmt>
			<Id>StmtIdOM20a</Id>
			<ElctrncSeqNb>00001</ElctrncSeqNb>
			<CreDtTm>2026-11-03T19:45:37.000Z</CreDtTm>
			<FrToDt><FrDtTm>2026-11-02T00:00:00</FrDtTm><ToDtTm>2026-11-03T23:59:59</ToDtTm></FrToDt>
			<Acct>
				<Id><IBAN>CH3908704016075473007</IBAN></Id>
				<Ccy>EUR</Ccy>
				<Ownr><Nm>Test CO</Nm></Ownr>
				<Svcr><FinInstnId><BICFI>AAAAAAAA82A</BICFI><Nm>Test Bank</Nm></FinInstnId></Svcr>
			</Acct>
			<Bal>
				<Tp><CdOrPrtry><Cd>OPBD</Cd></CdOrPrtry></Tp>
				<Amt Ccy="EUR">1000.00</Amt>
				<CdtDbtInd>CRDT</CdtDbtInd>
				<Dt><Dt>2026-11-02</Dt></Dt>
			</Bal>
			<Bal>
				<Tp><CdOrPrtry><Cd>CLBD</Cd></CdOrPrtry></Tp>
				<Amt Ccy="EUR">1123.52</Amt>
				<CdtDbtInd>CRDT</CdtDbtInd>
				<Dt><Dt>2026-11-03</Dt></Dt>
			</Bal>
			<Ntry>
				<NtryRef>REF-OM20a</NtryRef>
				<Amt Ccy="CHF">123.52</Amt>
				<CdtDbtInd>CRDT</CdtDbtInd>
				<Sts><Cd>BOOK</Cd></Sts>
				<BookgDt><Dt>2026-11-03</Dt></BookgDt>
				<ValDt><Dt>2026-11-03</Dt></ValDt>
				<AcctSvcrRef>REF-OM20a</AcctSvcrRef>
				<BkTxCd><Prtry><Cd>TRF</Cd><Issr>SWIFT</Issr></Prtry></BkTxCd>
				<NtryDtls>
					<Btch>
						<NbOfTxs>1</NbOfTxs>
						<TtlAmt Ccy="CHF">123.52</TtlAmt>
						<CdtDbtInd>CRDT</CdtDbtInd>
					</Btch>
					<TxDtls>
						<Refs><AcctSvcrRef>REF-OM20a-1</AcctSvcrRef></Refs>
						<Amt Ccy="CHF">123.52</Amt>
						<CdtDbtInd>CRDT</CdtDbtInd>
						<AmtDtls><TxAmt><Amt Ccy="CHF">123.52</Amt></TxAmt></AmtDtls>
						<RltdPties>
							<Dbtr><Pty><Nm>Customer Test</Nm></Pty></Dbtr>
							<DbtrAcct><Id><IBAN>CH4189144993834976921</IBAN></Id></DbtrAcct>
							<Cdtr><Pty><Nm>Test CO</Nm></Pty></Cdtr>
						</RltdPties>
						<RmtInf>
							<Ustrd>Vorauskasse OM20a-0020 Danke</Ustrd>
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
      | bsl_20a                           | bs_20a                        | 10   |
    And validate C_BankStatementLine
      | C_BankStatementLine_ID.Identifier | OPT.C_Currency_ID.ISO_Code | OPT.C_Order_ID.Identifier | OPT.C_BPartner_ID.Identifier |
      | bsl_20a                           | CHF                        | null                      | null                         |
    And validate the created orders
      | Identifier | DocStatus | GrandTotal |
      | o_20a      | WP        | 123.52     |


  # ################################################################################
  # ################################################################################
  # ################################################################################

  @from:cucumber
  @Id:S32606_OM20b
  Scenario: Entry in another currency than the order (CHF vs EUR), import not matching amounts: no order is set

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | DocBaseType | DocSubType | DocumentNo |
      | o_20b      | true    | bp_OM         | 2026-11-03  | SOO         | PR         | OM20b-0020 |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | l_o_20b    | o_20b      | p_OM         | 1          |
    And the order identified by o_20b is completed and waits for payment
    When bank statement is imported with identifiers bs_20b, not matching invoice amounts
    """
<?xml version="1.0" encoding="utf-8"?>
<Document xmlns="urn:iso:std:iso:20022:tech:xsd:camt.053.001.08">
	<BkToCstmrStmt>
		<GrpHdr>
			<MsgId>MsgIdOM20b</MsgId>
			<CreDtTm>2026-11-03T19:45:37.000Z</CreDtTm>
			<MsgPgntn><PgNb>1</PgNb><LastPgInd>true</LastPgInd></MsgPgntn>
		</GrpHdr>
		<Stmt>
			<Id>StmtIdOM20b</Id>
			<ElctrncSeqNb>00001</ElctrncSeqNb>
			<CreDtTm>2026-11-03T19:45:37.000Z</CreDtTm>
			<FrToDt><FrDtTm>2026-11-02T00:00:00</FrDtTm><ToDtTm>2026-11-03T23:59:59</ToDtTm></FrToDt>
			<Acct>
				<Id><IBAN>CH3908704016075473007</IBAN></Id>
				<Ccy>EUR</Ccy>
				<Ownr><Nm>Test CO</Nm></Ownr>
				<Svcr><FinInstnId><BICFI>AAAAAAAA82A</BICFI><Nm>Test Bank</Nm></FinInstnId></Svcr>
			</Acct>
			<Bal>
				<Tp><CdOrPrtry><Cd>OPBD</Cd></CdOrPrtry></Tp>
				<Amt Ccy="EUR">1000.00</Amt>
				<CdtDbtInd>CRDT</CdtDbtInd>
				<Dt><Dt>2026-11-02</Dt></Dt>
			</Bal>
			<Bal>
				<Tp><CdOrPrtry><Cd>CLBD</Cd></CdOrPrtry></Tp>
				<Amt Ccy="EUR">1123.52</Amt>
				<CdtDbtInd>CRDT</CdtDbtInd>
				<Dt><Dt>2026-11-03</Dt></Dt>
			</Bal>
			<Ntry>
				<NtryRef>REF-OM20b</NtryRef>
				<Amt Ccy="CHF">123.52</Amt>
				<CdtDbtInd>CRDT</CdtDbtInd>
				<Sts><Cd>BOOK</Cd></Sts>
				<BookgDt><Dt>2026-11-03</Dt></BookgDt>
				<ValDt><Dt>2026-11-03</Dt></ValDt>
				<AcctSvcrRef>REF-OM20b</AcctSvcrRef>
				<BkTxCd><Prtry><Cd>TRF</Cd><Issr>SWIFT</Issr></Prtry></BkTxCd>
				<NtryDtls>
					<Btch>
						<NbOfTxs>1</NbOfTxs>
						<TtlAmt Ccy="CHF">123.52</TtlAmt>
						<CdtDbtInd>CRDT</CdtDbtInd>
					</Btch>
					<TxDtls>
						<Refs><AcctSvcrRef>REF-OM20b-1</AcctSvcrRef></Refs>
						<Amt Ccy="CHF">123.52</Amt>
						<CdtDbtInd>CRDT</CdtDbtInd>
						<AmtDtls><TxAmt><Amt Ccy="CHF">123.52</Amt></TxAmt></AmtDtls>
						<RltdPties>
							<Dbtr><Pty><Nm>Customer Test</Nm></Pty></Dbtr>
							<DbtrAcct><Id><IBAN>CH4189144993834976921</IBAN></Id></DbtrAcct>
							<Cdtr><Pty><Nm>Test CO</Nm></Pty></Cdtr>
						</RltdPties>
						<RmtInf>
							<Ustrd>Vorauskasse OM20b-0020 Danke</Ustrd>
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
      | bsl_20b                           | bs_20b                        | 10   |
    And validate C_BankStatementLine
      | C_BankStatementLine_ID.Identifier | OPT.C_Currency_ID.ISO_Code | OPT.C_Order_ID.Identifier | OPT.C_BPartner_ID.Identifier |
      | bsl_20b                           | CHF                        | null                      | null                         |
    And validate the created orders
      | Identifier | DocStatus | GrandTotal |
      | o_20b      | WP        | 123.52     |


  # ################################################################################
  # ################################################################################
  # ################################################################################

  @from:cucumber
  @Id:S32606_OM21
  Scenario: Three waiting orders named in the text, only the last one totals the line amount: exactly that order is set

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | DocBaseType | DocSubType | DocumentNo |
      | o_21a      | true    | bp_OM         | 2026-11-03  | SOO         | PR         | OM21-A     |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | l_o_21a    | o_21a      | p_OM         | 2          |
    And the order identified by o_21a is completed and waits for payment
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | DocBaseType | DocSubType | DocumentNo |
      | o_21b      | true    | bp_OM         | 2026-11-03  | SOO         | PR         | OM21-B     |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | l_o_21b    | o_21b      | p_OM         | 3          |
    And the order identified by o_21b is completed and waits for payment
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | DocBaseType | DocSubType | DocumentNo |
      | o_21c      | true    | bp_OM         | 2026-11-03  | SOO         | PR         | OM21-C     |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | l_o_21c    | o_21c      | p_OM         | 1          |
    And the order identified by o_21c is completed and waits for payment
    When bank statement is imported with identifiers bs_21, matching invoice amounts
    """
<?xml version="1.0" encoding="utf-8"?>
<Document xmlns="urn:iso:std:iso:20022:tech:xsd:camt.053.001.08">
	<BkToCstmrStmt>
		<GrpHdr>
			<MsgId>MsgIdOM21</MsgId>
			<CreDtTm>2026-11-03T19:45:37.000Z</CreDtTm>
			<MsgPgntn><PgNb>1</PgNb><LastPgInd>true</LastPgInd></MsgPgntn>
		</GrpHdr>
		<Stmt>
			<Id>StmtIdOM21</Id>
			<ElctrncSeqNb>00001</ElctrncSeqNb>
			<CreDtTm>2026-11-03T19:45:37.000Z</CreDtTm>
			<FrToDt><FrDtTm>2026-11-02T00:00:00</FrDtTm><ToDtTm>2026-11-03T23:59:59</ToDtTm></FrToDt>
			<Acct>
				<Id><IBAN>CH3908704016075473007</IBAN></Id>
				<Ccy>EUR</Ccy>
				<Ownr><Nm>Test CO</Nm></Ownr>
				<Svcr><FinInstnId><BICFI>AAAAAAAA82A</BICFI><Nm>Test Bank</Nm></FinInstnId></Svcr>
			</Acct>
			<Bal>
				<Tp><CdOrPrtry><Cd>OPBD</Cd></CdOrPrtry></Tp>
				<Amt Ccy="EUR">1000.00</Amt>
				<CdtDbtInd>CRDT</CdtDbtInd>
				<Dt><Dt>2026-11-02</Dt></Dt>
			</Bal>
			<Bal>
				<Tp><CdOrPrtry><Cd>CLBD</Cd></CdOrPrtry></Tp>
				<Amt Ccy="EUR">1123.52</Amt>
				<CdtDbtInd>CRDT</CdtDbtInd>
				<Dt><Dt>2026-11-03</Dt></Dt>
			</Bal>
			<Ntry>
				<NtryRef>REF-OM21</NtryRef>
				<Amt Ccy="EUR">123.52</Amt>
				<CdtDbtInd>CRDT</CdtDbtInd>
				<Sts><Cd>BOOK</Cd></Sts>
				<BookgDt><Dt>2026-11-03</Dt></BookgDt>
				<ValDt><Dt>2026-11-03</Dt></ValDt>
				<AcctSvcrRef>REF-OM21</AcctSvcrRef>
				<BkTxCd><Prtry><Cd>TRF</Cd><Issr>SWIFT</Issr></Prtry></BkTxCd>
				<NtryDtls>
					<Btch>
						<NbOfTxs>1</NbOfTxs>
						<TtlAmt Ccy="EUR">123.52</TtlAmt>
						<CdtDbtInd>CRDT</CdtDbtInd>
					</Btch>
					<TxDtls>
						<Refs><AcctSvcrRef>REF-OM21-1</AcctSvcrRef></Refs>
						<Amt Ccy="EUR">123.52</Amt>
						<CdtDbtInd>CRDT</CdtDbtInd>
						<AmtDtls><TxAmt><Amt Ccy="EUR">123.52</Amt></TxAmt></AmtDtls>
						<RltdPties>
							<Dbtr><Pty><Nm>Customer Test</Nm></Pty></Dbtr>
							<DbtrAcct><Id><IBAN>CH4189144993834976921</IBAN></Id></DbtrAcct>
							<Cdtr><Pty><Nm>Test CO</Nm></Pty></Cdtr>
						</RltdPties>
						<RmtInf>
							<Ustrd>Zahlung OM21-A OM21-B OM21-C</Ustrd>
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
      | bsl_21                            | bs_21                         | 10   |
    And validate C_BankStatementLine
      | C_BankStatementLine_ID.Identifier | OPT.C_Order_ID.Identifier | OPT.C_BPartner_ID.Identifier |
      | bsl_21                            | o_21c                     | bp_OM                        |


  # ################################################################################
  # ################################################################################
  # ################################################################################

  @from:cucumber
  @Id:S32606_OM22
  Scenario: Partner of a line with an order is changed to another partner: completing the statement fails

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | DocBaseType | DocSubType | DocumentNo |
      | o_22       | true    | bp_OM         | 2026-11-03  | SOO         | PR         | OM22-0022  |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | l_o_22     | o_22       | p_OM         | 1          |
    And the order identified by o_22 is completed and waits for payment
    When bank statement is imported with identifiers bs_22, matching invoice amounts
    """
<?xml version="1.0" encoding="utf-8"?>
<Document xmlns="urn:iso:std:iso:20022:tech:xsd:camt.053.001.08">
	<BkToCstmrStmt>
		<GrpHdr>
			<MsgId>MsgIdOM22</MsgId>
			<CreDtTm>2026-11-03T19:45:37.000Z</CreDtTm>
			<MsgPgntn><PgNb>1</PgNb><LastPgInd>true</LastPgInd></MsgPgntn>
		</GrpHdr>
		<Stmt>
			<Id>StmtIdOM22</Id>
			<ElctrncSeqNb>00001</ElctrncSeqNb>
			<CreDtTm>2026-11-03T19:45:37.000Z</CreDtTm>
			<FrToDt><FrDtTm>2026-11-02T00:00:00</FrDtTm><ToDtTm>2026-11-03T23:59:59</ToDtTm></FrToDt>
			<Acct>
				<Id><IBAN>CH3908704016075473007</IBAN></Id>
				<Ccy>EUR</Ccy>
				<Ownr><Nm>Test CO</Nm></Ownr>
				<Svcr><FinInstnId><BICFI>AAAAAAAA82A</BICFI><Nm>Test Bank</Nm></FinInstnId></Svcr>
			</Acct>
			<Bal>
				<Tp><CdOrPrtry><Cd>OPBD</Cd></CdOrPrtry></Tp>
				<Amt Ccy="EUR">1000.00</Amt>
				<CdtDbtInd>CRDT</CdtDbtInd>
				<Dt><Dt>2026-11-02</Dt></Dt>
			</Bal>
			<Bal>
				<Tp><CdOrPrtry><Cd>CLBD</Cd></CdOrPrtry></Tp>
				<Amt Ccy="EUR">1123.52</Amt>
				<CdtDbtInd>CRDT</CdtDbtInd>
				<Dt><Dt>2026-11-03</Dt></Dt>
			</Bal>
			<Ntry>
				<NtryRef>REF-OM22</NtryRef>
				<Amt Ccy="EUR">123.52</Amt>
				<CdtDbtInd>CRDT</CdtDbtInd>
				<Sts><Cd>BOOK</Cd></Sts>
				<BookgDt><Dt>2026-11-03</Dt></BookgDt>
				<ValDt><Dt>2026-11-03</Dt></ValDt>
				<AcctSvcrRef>REF-OM22</AcctSvcrRef>
				<BkTxCd><Prtry><Cd>TRF</Cd><Issr>SWIFT</Issr></Prtry></BkTxCd>
				<NtryDtls>
					<Btch>
						<NbOfTxs>1</NbOfTxs>
						<TtlAmt Ccy="EUR">123.52</TtlAmt>
						<CdtDbtInd>CRDT</CdtDbtInd>
					</Btch>
					<TxDtls>
						<Refs><AcctSvcrRef>REF-OM22-1</AcctSvcrRef></Refs>
						<Amt Ccy="EUR">123.52</Amt>
						<CdtDbtInd>CRDT</CdtDbtInd>
						<AmtDtls><TxAmt><Amt Ccy="EUR">123.52</Amt></TxAmt></AmtDtls>
						<RltdPties>
							<Dbtr><Pty><Nm>Customer Test</Nm></Pty></Dbtr>
							<DbtrAcct><Id><IBAN>CH4189144993834976921</IBAN></Id></DbtrAcct>
							<Cdtr><Pty><Nm>Test CO</Nm></Pty></Cdtr>
						</RltdPties>
						<RmtInf>
							<Ustrd>Vorauskasse OM22-0022 Danke</Ustrd>
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
      | bsl_22                            | bs_22                         | 10   |
    And the C_BPartner_ID of the bank statement line identified by bsl_22 is changed to bp_OM2
    And the C_BankStatement identified by bs_22 is completed expecting error code PAYMENT_BPARTNERMISMATCH_ORDER
    And validate the created orders
      | Identifier | DocStatus | GrandTotal |
      | o_22       | WP        | 123.52     |


  # ################################################################################
  # ################################################################################
  # ################################################################################

  @from:cucumber
  @Id:S32606_OM23
  Scenario: Inactive prepay order named in the text: no order is set

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | DocBaseType | DocSubType | DocumentNo |
      | o_23       | true    | bp_OM         | 2026-11-03  | SOO         | PR         | OM23-0023  |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | l_o_23     | o_23       | p_OM         | 1          |
    And the order identified by o_23 is completed and waits for payment
    And the order identified by o_23 is deactivated
    When bank statement is imported with identifiers bs_23, matching invoice amounts
    """
<?xml version="1.0" encoding="utf-8"?>
<Document xmlns="urn:iso:std:iso:20022:tech:xsd:camt.053.001.08">
	<BkToCstmrStmt>
		<GrpHdr>
			<MsgId>MsgIdOM23</MsgId>
			<CreDtTm>2026-11-03T19:45:37.000Z</CreDtTm>
			<MsgPgntn><PgNb>1</PgNb><LastPgInd>true</LastPgInd></MsgPgntn>
		</GrpHdr>
		<Stmt>
			<Id>StmtIdOM23</Id>
			<ElctrncSeqNb>00001</ElctrncSeqNb>
			<CreDtTm>2026-11-03T19:45:37.000Z</CreDtTm>
			<FrToDt><FrDtTm>2026-11-02T00:00:00</FrDtTm><ToDtTm>2026-11-03T23:59:59</ToDtTm></FrToDt>
			<Acct>
				<Id><IBAN>CH3908704016075473007</IBAN></Id>
				<Ccy>EUR</Ccy>
				<Ownr><Nm>Test CO</Nm></Ownr>
				<Svcr><FinInstnId><BICFI>AAAAAAAA82A</BICFI><Nm>Test Bank</Nm></FinInstnId></Svcr>
			</Acct>
			<Bal>
				<Tp><CdOrPrtry><Cd>OPBD</Cd></CdOrPrtry></Tp>
				<Amt Ccy="EUR">1000.00</Amt>
				<CdtDbtInd>CRDT</CdtDbtInd>
				<Dt><Dt>2026-11-02</Dt></Dt>
			</Bal>
			<Bal>
				<Tp><CdOrPrtry><Cd>CLBD</Cd></CdOrPrtry></Tp>
				<Amt Ccy="EUR">1123.52</Amt>
				<CdtDbtInd>CRDT</CdtDbtInd>
				<Dt><Dt>2026-11-03</Dt></Dt>
			</Bal>
			<Ntry>
				<NtryRef>REF-OM23</NtryRef>
				<Amt Ccy="EUR">123.52</Amt>
				<CdtDbtInd>CRDT</CdtDbtInd>
				<Sts><Cd>BOOK</Cd></Sts>
				<BookgDt><Dt>2026-11-03</Dt></BookgDt>
				<ValDt><Dt>2026-11-03</Dt></ValDt>
				<AcctSvcrRef>REF-OM23</AcctSvcrRef>
				<BkTxCd><Prtry><Cd>TRF</Cd><Issr>SWIFT</Issr></Prtry></BkTxCd>
				<NtryDtls>
					<Btch>
						<NbOfTxs>1</NbOfTxs>
						<TtlAmt Ccy="EUR">123.52</TtlAmt>
						<CdtDbtInd>CRDT</CdtDbtInd>
					</Btch>
					<TxDtls>
						<Refs><AcctSvcrRef>REF-OM23-1</AcctSvcrRef></Refs>
						<Amt Ccy="EUR">123.52</Amt>
						<CdtDbtInd>CRDT</CdtDbtInd>
						<AmtDtls><TxAmt><Amt Ccy="EUR">123.52</Amt></TxAmt></AmtDtls>
						<RltdPties>
							<Dbtr><Pty><Nm>Customer Test</Nm></Pty></Dbtr>
							<DbtrAcct><Id><IBAN>CH4189144993834976921</IBAN></Id></DbtrAcct>
							<Cdtr><Pty><Nm>Test CO</Nm></Pty></Cdtr>
						</RltdPties>
						<RmtInf>
							<Ustrd>Vorauskasse OM23-0023 Danke</Ustrd>
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
      | bsl_23                            | bs_23                         | 10   |
    And validate C_BankStatementLine
      | C_BankStatementLine_ID.Identifier | OPT.C_Order_ID.Identifier | OPT.C_BPartner_ID.Identifier |
      | bsl_23                            | null                      | null                         |


  # ################################################################################
  # ################################################################################
  # ################################################################################

  @from:cucumber
  @Id:S32606_OM24
  Scenario: Hand-picked order with a partial amount: completing the statement fails, the order stays waiting

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | DocBaseType | DocSubType | DocumentNo |
      | o_24       | true    | bp_OM         | 2026-11-03  | SOO         | PR         | OM24-0024  |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | l_o_24     | o_24       | p_OM         | 1          |
    And the order identified by o_24 is completed and waits for payment
    And metasfresh contains C_BankStatement:
      | Identifier | C_BP_BankAccount_ID | StatementDate |
      | bs_24      | bpb_OM              | 2026-11-03    |
    And metasfresh contains C_BankStatementLine:
      | Identifier | C_BankStatement_ID | Amount     | C_BPartner_ID | C_Order_ID |
      | bsl_24     | bs_24              | 100.00 EUR | bp_OM         | o_24       |
    And the C_BankStatement identified by bs_24 is completed expecting error code BANKSTATEMENT_ORDER_AMOUNT_MISMATCH
    And exactly 0 payments carry the order identified by o_24
    And validate the created orders
      | Identifier | DocStatus | GrandTotal |
      | o_24       | WP        | 123.52     |


  # ################################################################################
  # ################################################################################
  # ################################################################################

  @from:cucumber
  @Id:S32606_OM25
  Scenario: Hand-picked order with another currency than the order: completing the statement fails, the order stays waiting

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | DocBaseType | DocSubType | DocumentNo |
      | o_25       | true    | bp_OM         | 2026-11-03  | SOO         | PR         | OM25-0025  |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | l_o_25     | o_25       | p_OM         | 1          |
    And the order identified by o_25 is completed and waits for payment
    And metasfresh contains C_BankStatement:
      | Identifier | C_BP_BankAccount_ID | StatementDate |
      | bs_25      | bpb_OM              | 2026-11-03    |
    And metasfresh contains C_BankStatementLine:
      | Identifier | C_BankStatement_ID | Amount     | C_BPartner_ID | C_Order_ID |
      | bsl_25     | bs_25              | 123.52 CHF | bp_OM         | o_25       |
    And the C_BankStatement identified by bs_25 is completed expecting error code BANKSTATEMENT_ORDER_AMOUNT_MISMATCH
    And exactly 0 payments carry the order identified by o_25
    And validate the created orders
      | Identifier | DocStatus | GrandTotal |
      | o_25       | WP        | 123.52     |


  # ################################################################################
  # ################################################################################
  # ################################################################################

  @from:cucumber
  @Id:S32606_OM26
  Scenario: Same order named in two imported files before either is completed: only the first line gets the order

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | DocBaseType | DocSubType | DocumentNo |
      | o_26       | true    | bp_OM         | 2026-11-03  | SOO         | PR         | OM26-0026  |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | l_o_26     | o_26       | p_OM         | 1          |
    And the order identified by o_26 is completed and waits for payment
    When bank statement is imported with identifiers bs_26a, matching invoice amounts
    """
<?xml version="1.0" encoding="utf-8"?>
<Document xmlns="urn:iso:std:iso:20022:tech:xsd:camt.053.001.08">
	<BkToCstmrStmt>
		<GrpHdr>
			<MsgId>MsgIdOM26A</MsgId>
			<CreDtTm>2026-11-03T19:45:37.000Z</CreDtTm>
			<MsgPgntn><PgNb>1</PgNb><LastPgInd>true</LastPgInd></MsgPgntn>
		</GrpHdr>
		<Stmt>
			<Id>StmtIdOM26A</Id>
			<ElctrncSeqNb>00001</ElctrncSeqNb>
			<CreDtTm>2026-11-03T19:45:37.000Z</CreDtTm>
			<FrToDt><FrDtTm>2026-11-02T00:00:00</FrDtTm><ToDtTm>2026-11-03T23:59:59</ToDtTm></FrToDt>
			<Acct>
				<Id><IBAN>CH3908704016075473007</IBAN></Id>
				<Ccy>EUR</Ccy>
				<Ownr><Nm>Test CO</Nm></Ownr>
				<Svcr><FinInstnId><BICFI>AAAAAAAA82A</BICFI><Nm>Test Bank</Nm></FinInstnId></Svcr>
			</Acct>
			<Bal>
				<Tp><CdOrPrtry><Cd>OPBD</Cd></CdOrPrtry></Tp>
				<Amt Ccy="EUR">1000.00</Amt>
				<CdtDbtInd>CRDT</CdtDbtInd>
				<Dt><Dt>2026-11-02</Dt></Dt>
			</Bal>
			<Bal>
				<Tp><CdOrPrtry><Cd>CLBD</Cd></CdOrPrtry></Tp>
				<Amt Ccy="EUR">1123.52</Amt>
				<CdtDbtInd>CRDT</CdtDbtInd>
				<Dt><Dt>2026-11-03</Dt></Dt>
			</Bal>
			<Ntry>
				<NtryRef>REF-OM26A</NtryRef>
				<Amt Ccy="EUR">123.52</Amt>
				<CdtDbtInd>CRDT</CdtDbtInd>
				<Sts><Cd>BOOK</Cd></Sts>
				<BookgDt><Dt>2026-11-03</Dt></BookgDt>
				<ValDt><Dt>2026-11-03</Dt></ValDt>
				<AcctSvcrRef>REF-OM26A</AcctSvcrRef>
				<BkTxCd><Prtry><Cd>TRF</Cd><Issr>SWIFT</Issr></Prtry></BkTxCd>
				<NtryDtls>
					<Btch>
						<NbOfTxs>1</NbOfTxs>
						<TtlAmt Ccy="EUR">123.52</TtlAmt>
						<CdtDbtInd>CRDT</CdtDbtInd>
					</Btch>
					<TxDtls>
						<Refs><AcctSvcrRef>REF-OM26A-1</AcctSvcrRef></Refs>
						<Amt Ccy="EUR">123.52</Amt>
						<CdtDbtInd>CRDT</CdtDbtInd>
						<AmtDtls><TxAmt><Amt Ccy="EUR">123.52</Amt></TxAmt></AmtDtls>
						<RltdPties>
							<Dbtr><Pty><Nm>Customer Test</Nm></Pty></Dbtr>
							<DbtrAcct><Id><IBAN>CH4189144993834976921</IBAN></Id></DbtrAcct>
							<Cdtr><Pty><Nm>Test CO</Nm></Pty></Cdtr>
						</RltdPties>
						<RmtInf>
							<Ustrd>Vorauskasse OM26-0026 Danke</Ustrd>
						</RmtInf>
					</TxDtls>
				</NtryDtls>
			</Ntry>
		</Stmt>
	</BkToCstmrStmt>
</Document>
    """
    When bank statement is imported with identifiers bs_26b, matching invoice amounts
    """
<?xml version="1.0" encoding="utf-8"?>
<Document xmlns="urn:iso:std:iso:20022:tech:xsd:camt.053.001.08">
	<BkToCstmrStmt>
		<GrpHdr>
			<MsgId>MsgIdOM26B</MsgId>
			<CreDtTm>2026-11-03T19:45:37.000Z</CreDtTm>
			<MsgPgntn><PgNb>1</PgNb><LastPgInd>true</LastPgInd></MsgPgntn>
		</GrpHdr>
		<Stmt>
			<Id>StmtIdOM26B</Id>
			<ElctrncSeqNb>00001</ElctrncSeqNb>
			<CreDtTm>2026-11-03T19:45:37.000Z</CreDtTm>
			<FrToDt><FrDtTm>2026-11-02T00:00:00</FrDtTm><ToDtTm>2026-11-03T23:59:59</ToDtTm></FrToDt>
			<Acct>
				<Id><IBAN>CH3908704016075473007</IBAN></Id>
				<Ccy>EUR</Ccy>
				<Ownr><Nm>Test CO</Nm></Ownr>
				<Svcr><FinInstnId><BICFI>AAAAAAAA82A</BICFI><Nm>Test Bank</Nm></FinInstnId></Svcr>
			</Acct>
			<Bal>
				<Tp><CdOrPrtry><Cd>OPBD</Cd></CdOrPrtry></Tp>
				<Amt Ccy="EUR">1000.00</Amt>
				<CdtDbtInd>CRDT</CdtDbtInd>
				<Dt><Dt>2026-11-02</Dt></Dt>
			</Bal>
			<Bal>
				<Tp><CdOrPrtry><Cd>CLBD</Cd></CdOrPrtry></Tp>
				<Amt Ccy="EUR">1123.52</Amt>
				<CdtDbtInd>CRDT</CdtDbtInd>
				<Dt><Dt>2026-11-03</Dt></Dt>
			</Bal>
			<Ntry>
				<NtryRef>REF-OM26B</NtryRef>
				<Amt Ccy="EUR">123.52</Amt>
				<CdtDbtInd>CRDT</CdtDbtInd>
				<Sts><Cd>BOOK</Cd></Sts>
				<BookgDt><Dt>2026-11-03</Dt></BookgDt>
				<ValDt><Dt>2026-11-03</Dt></ValDt>
				<AcctSvcrRef>REF-OM26B</AcctSvcrRef>
				<BkTxCd><Prtry><Cd>TRF</Cd><Issr>SWIFT</Issr></Prtry></BkTxCd>
				<NtryDtls>
					<Btch>
						<NbOfTxs>1</NbOfTxs>
						<TtlAmt Ccy="EUR">123.52</TtlAmt>
						<CdtDbtInd>CRDT</CdtDbtInd>
					</Btch>
					<TxDtls>
						<Refs><AcctSvcrRef>REF-OM26B-1</AcctSvcrRef></Refs>
						<Amt Ccy="EUR">123.52</Amt>
						<CdtDbtInd>CRDT</CdtDbtInd>
						<AmtDtls><TxAmt><Amt Ccy="EUR">123.52</Amt></TxAmt></AmtDtls>
						<RltdPties>
							<Dbtr><Pty><Nm>Customer Test</Nm></Pty></Dbtr>
							<DbtrAcct><Id><IBAN>CH4189144993834976921</IBAN></Id></DbtrAcct>
							<Cdtr><Pty><Nm>Test CO</Nm></Pty></Cdtr>
						</RltdPties>
						<RmtInf>
							<Ustrd>Vorauskasse OM26-0026 Danke</Ustrd>
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
      | bsl_26a                           | bs_26a                        | 10   |
    And load C_BankStatementLine
      | C_BankStatementLine_ID.Identifier | C_BankStatement_ID.Identifier | Line |
      | bsl_26b                           | bs_26b                        | 10   |
    And validate C_BankStatementLine
      | C_BankStatementLine_ID.Identifier | OPT.C_Order_ID.Identifier | OPT.C_BPartner_ID.Identifier |
      | bsl_26a                           | o_26                      | bp_OM                        |
    And validate C_BankStatementLine
      | C_BankStatementLine_ID.Identifier | OPT.C_Order_ID.Identifier | OPT.C_BPartner_ID.Identifier |
      | bsl_26b                           | null                      | null                         |


  # ################################################################################
  # ################################################################################
  # ################################################################################

  @from:cucumber
  @Id:S32606_OM27
  Scenario: Same order named by two entries of one file: only the first entry's line gets the order

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | DocBaseType | DocSubType | DocumentNo |
      | o_27       | true    | bp_OM         | 2026-11-03  | SOO         | PR         | OM27-0027  |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | l_o_27     | o_27       | p_OM         | 1          |
    And the order identified by o_27 is completed and waits for payment
    When bank statement is imported with identifiers bs_27, matching invoice amounts
    """
<?xml version="1.0" encoding="utf-8"?>
<Document xmlns="urn:iso:std:iso:20022:tech:xsd:camt.053.001.08">
	<BkToCstmrStmt>
		<GrpHdr>
			<MsgId>MsgIdOM27</MsgId>
			<CreDtTm>2026-11-03T19:45:37.000Z</CreDtTm>
			<MsgPgntn><PgNb>1</PgNb><LastPgInd>true</LastPgInd></MsgPgntn>
		</GrpHdr>
		<Stmt>
			<Id>StmtIdOM27</Id>
			<ElctrncSeqNb>00001</ElctrncSeqNb>
			<CreDtTm>2026-11-03T19:45:37.000Z</CreDtTm>
			<FrToDt><FrDtTm>2026-11-02T00:00:00</FrDtTm><ToDtTm>2026-11-03T23:59:59</ToDtTm></FrToDt>
			<Acct>
				<Id><IBAN>CH3908704016075473007</IBAN></Id>
				<Ccy>EUR</Ccy>
				<Ownr><Nm>Test CO</Nm></Ownr>
				<Svcr><FinInstnId><BICFI>AAAAAAAA82A</BICFI><Nm>Test Bank</Nm></FinInstnId></Svcr>
			</Acct>
			<Bal>
				<Tp><CdOrPrtry><Cd>OPBD</Cd></CdOrPrtry></Tp>
				<Amt Ccy="EUR">1000.00</Amt>
				<CdtDbtInd>CRDT</CdtDbtInd>
				<Dt><Dt>2026-11-02</Dt></Dt>
			</Bal>
			<Bal>
				<Tp><CdOrPrtry><Cd>CLBD</Cd></CdOrPrtry></Tp>
				<Amt Ccy="EUR">1247.04</Amt>
				<CdtDbtInd>CRDT</CdtDbtInd>
				<Dt><Dt>2026-11-03</Dt></Dt>
			</Bal>
			<Ntry>
				<NtryRef>REF-OM27-1</NtryRef>
				<Amt Ccy="EUR">123.52</Amt>
				<CdtDbtInd>CRDT</CdtDbtInd>
				<Sts><Cd>BOOK</Cd></Sts>
				<BookgDt><Dt>2026-11-03</Dt></BookgDt>
				<ValDt><Dt>2026-11-03</Dt></ValDt>
				<AcctSvcrRef>REF-OM27-1</AcctSvcrRef>
				<BkTxCd><Prtry><Cd>TRF</Cd><Issr>SWIFT</Issr></Prtry></BkTxCd>
				<NtryDtls>
					<Btch>
						<NbOfTxs>1</NbOfTxs>
						<TtlAmt Ccy="EUR">123.52</TtlAmt>
						<CdtDbtInd>CRDT</CdtDbtInd>
					</Btch>
					<TxDtls>
						<Refs><AcctSvcrRef>REF-OM27-1-1</AcctSvcrRef></Refs>
						<Amt Ccy="EUR">123.52</Amt>
						<CdtDbtInd>CRDT</CdtDbtInd>
						<AmtDtls><TxAmt><Amt Ccy="EUR">123.52</Amt></TxAmt></AmtDtls>
						<RltdPties>
							<Dbtr><Pty><Nm>Customer Test</Nm></Pty></Dbtr>
							<DbtrAcct><Id><IBAN>CH4189144993834976921</IBAN></Id></DbtrAcct>
							<Cdtr><Pty><Nm>Test CO</Nm></Pty></Cdtr>
						</RltdPties>
						<RmtInf>
							<Ustrd>Vorauskasse OM27-0027 Danke</Ustrd>
						</RmtInf>
					</TxDtls>
				</NtryDtls>
			</Ntry>
			<Ntry>
				<NtryRef>REF-OM27-2</NtryRef>
				<Amt Ccy="EUR">123.52</Amt>
				<CdtDbtInd>CRDT</CdtDbtInd>
				<Sts><Cd>BOOK</Cd></Sts>
				<BookgDt><Dt>2026-11-03</Dt></BookgDt>
				<ValDt><Dt>2026-11-03</Dt></ValDt>
				<AcctSvcrRef>REF-OM27-2</AcctSvcrRef>
				<BkTxCd><Prtry><Cd>TRF</Cd><Issr>SWIFT</Issr></Prtry></BkTxCd>
				<NtryDtls>
					<Btch>
						<NbOfTxs>1</NbOfTxs>
						<TtlAmt Ccy="EUR">123.52</TtlAmt>
						<CdtDbtInd>CRDT</CdtDbtInd>
					</Btch>
					<TxDtls>
						<Refs><AcctSvcrRef>REF-OM27-2-1</AcctSvcrRef></Refs>
						<Amt Ccy="EUR">123.52</Amt>
						<CdtDbtInd>CRDT</CdtDbtInd>
						<AmtDtls><TxAmt><Amt Ccy="EUR">123.52</Amt></TxAmt></AmtDtls>
						<RltdPties>
							<Dbtr><Pty><Nm>Customer Test</Nm></Pty></Dbtr>
							<DbtrAcct><Id><IBAN>CH4189144993834976921</IBAN></Id></DbtrAcct>
							<Cdtr><Pty><Nm>Test CO</Nm></Pty></Cdtr>
						</RltdPties>
						<RmtInf>
							<Ustrd>Vorauskasse OM27-0027 Danke</Ustrd>
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
      | bsl_27a                           | bs_27                         | 10   |
    And load C_BankStatementLine
      | C_BankStatementLine_ID.Identifier | C_BankStatement_ID.Identifier | Line |
      | bsl_27b                           | bs_27                         | 20   |
    And validate C_BankStatementLine
      | C_BankStatementLine_ID.Identifier | OPT.C_Order_ID.Identifier | OPT.C_BPartner_ID.Identifier |
      | bsl_27a                           | o_27                      | bp_OM                        |
    And validate C_BankStatementLine
      | C_BankStatementLine_ID.Identifier | OPT.C_Order_ID.Identifier | OPT.C_BPartner_ID.Identifier |
      | bsl_27b                           | null                      | null                         |


  # ################################################################################
  # ################################################################################
  # ################################################################################

  @from:cucumber
  @Id:S32606_OM28
  Scenario: Prepay order already carried by a draft payment: no order is set

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | DocBaseType | DocSubType | DocumentNo |
      | o_28       | true    | bp_OM         | 2026-11-03  | SOO         | PR         | OM28-0028  |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | l_o_28     | o_28       | p_OM         | 1          |
    And the order identified by o_28 is completed and waits for payment
    And metasfresh contains C_Payment
      | Identifier | C_BPartner_ID | PayAmt     | IsReceipt | C_Order_ID |
      | p_28       | bp_OM         | 123.52 EUR | true      | o_28       |
    When bank statement is imported with identifiers bs_28, matching invoice amounts
    """
<?xml version="1.0" encoding="utf-8"?>
<Document xmlns="urn:iso:std:iso:20022:tech:xsd:camt.053.001.08">
	<BkToCstmrStmt>
		<GrpHdr>
			<MsgId>MsgIdOM28</MsgId>
			<CreDtTm>2026-11-03T19:45:37.000Z</CreDtTm>
			<MsgPgntn><PgNb>1</PgNb><LastPgInd>true</LastPgInd></MsgPgntn>
		</GrpHdr>
		<Stmt>
			<Id>StmtIdOM28</Id>
			<ElctrncSeqNb>00001</ElctrncSeqNb>
			<CreDtTm>2026-11-03T19:45:37.000Z</CreDtTm>
			<FrToDt><FrDtTm>2026-11-02T00:00:00</FrDtTm><ToDtTm>2026-11-03T23:59:59</ToDtTm></FrToDt>
			<Acct>
				<Id><IBAN>CH3908704016075473007</IBAN></Id>
				<Ccy>EUR</Ccy>
				<Ownr><Nm>Test CO</Nm></Ownr>
				<Svcr><FinInstnId><BICFI>AAAAAAAA82A</BICFI><Nm>Test Bank</Nm></FinInstnId></Svcr>
			</Acct>
			<Bal>
				<Tp><CdOrPrtry><Cd>OPBD</Cd></CdOrPrtry></Tp>
				<Amt Ccy="EUR">1000.00</Amt>
				<CdtDbtInd>CRDT</CdtDbtInd>
				<Dt><Dt>2026-11-02</Dt></Dt>
			</Bal>
			<Bal>
				<Tp><CdOrPrtry><Cd>CLBD</Cd></CdOrPrtry></Tp>
				<Amt Ccy="EUR">1123.52</Amt>
				<CdtDbtInd>CRDT</CdtDbtInd>
				<Dt><Dt>2026-11-03</Dt></Dt>
			</Bal>
			<Ntry>
				<NtryRef>REF-OM28</NtryRef>
				<Amt Ccy="EUR">123.52</Amt>
				<CdtDbtInd>CRDT</CdtDbtInd>
				<Sts><Cd>BOOK</Cd></Sts>
				<BookgDt><Dt>2026-11-03</Dt></BookgDt>
				<ValDt><Dt>2026-11-03</Dt></ValDt>
				<AcctSvcrRef>REF-OM28</AcctSvcrRef>
				<BkTxCd><Prtry><Cd>TRF</Cd><Issr>SWIFT</Issr></Prtry></BkTxCd>
				<NtryDtls>
					<Btch>
						<NbOfTxs>1</NbOfTxs>
						<TtlAmt Ccy="EUR">123.52</TtlAmt>
						<CdtDbtInd>CRDT</CdtDbtInd>
					</Btch>
					<TxDtls>
						<Refs><AcctSvcrRef>REF-OM28-1</AcctSvcrRef></Refs>
						<Amt Ccy="EUR">123.52</Amt>
						<CdtDbtInd>CRDT</CdtDbtInd>
						<AmtDtls><TxAmt><Amt Ccy="EUR">123.52</Amt></TxAmt></AmtDtls>
						<RltdPties>
							<Dbtr><Pty><Nm>Customer Test</Nm></Pty></Dbtr>
							<DbtrAcct><Id><IBAN>CH4189144993834976921</IBAN></Id></DbtrAcct>
							<Cdtr><Pty><Nm>Test CO</Nm></Pty></Cdtr>
						</RltdPties>
						<RmtInf>
							<Ustrd>Vorauskasse OM28-0028 Danke</Ustrd>
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
      | bsl_28                            | bs_28                         | 10   |
    And validate C_BankStatementLine
      | C_BankStatementLine_ID.Identifier | OPT.C_Order_ID.Identifier | OPT.C_BPartner_ID.Identifier |
      | bsl_28                            | null                      | null                         |
    And exactly 1 payment carries the order identified by o_28


  # ################################################################################
  # ################################################################################
  # ################################################################################

  @from:cucumber
  @Id:S32606_OM31a
  Scenario: Order released by a hand payment of another amount after the line was matched: completing the statement fails, no second payment

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | DocBaseType | DocSubType | DocumentNo |
      | o_31a      | true    | bp_OM         | 2026-11-03  | SOO         | PR         | OM31A-0031 |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | l_o_31a    | o_31a      | p_OM         | 1          |
    And the order identified by o_31a is completed and waits for payment
    When bank statement is imported with identifiers bs_31a, matching invoice amounts
    """
<?xml version="1.0" encoding="utf-8"?>
<Document xmlns="urn:iso:std:iso:20022:tech:xsd:camt.053.001.08">
	<BkToCstmrStmt>
		<GrpHdr>
			<MsgId>MsgIdOM31A</MsgId>
			<CreDtTm>2026-11-03T19:45:37.000Z</CreDtTm>
			<MsgPgntn><PgNb>1</PgNb><LastPgInd>true</LastPgInd></MsgPgntn>
		</GrpHdr>
		<Stmt>
			<Id>StmtIdOM31A</Id>
			<ElctrncSeqNb>00001</ElctrncSeqNb>
			<CreDtTm>2026-11-03T19:45:37.000Z</CreDtTm>
			<FrToDt><FrDtTm>2026-11-02T00:00:00</FrDtTm><ToDtTm>2026-11-03T23:59:59</ToDtTm></FrToDt>
			<Acct>
				<Id><IBAN>CH3908704016075473007</IBAN></Id>
				<Ccy>EUR</Ccy>
				<Ownr><Nm>Test CO</Nm></Ownr>
				<Svcr><FinInstnId><BICFI>AAAAAAAA82A</BICFI><Nm>Test Bank</Nm></FinInstnId></Svcr>
			</Acct>
			<Bal>
				<Tp><CdOrPrtry><Cd>OPBD</Cd></CdOrPrtry></Tp>
				<Amt Ccy="EUR">1000.00</Amt>
				<CdtDbtInd>CRDT</CdtDbtInd>
				<Dt><Dt>2026-11-02</Dt></Dt>
			</Bal>
			<Bal>
				<Tp><CdOrPrtry><Cd>CLBD</Cd></CdOrPrtry></Tp>
				<Amt Ccy="EUR">1123.52</Amt>
				<CdtDbtInd>CRDT</CdtDbtInd>
				<Dt><Dt>2026-11-03</Dt></Dt>
			</Bal>
			<Ntry>
				<NtryRef>REF-OM31A</NtryRef>
				<Amt Ccy="EUR">123.52</Amt>
				<CdtDbtInd>CRDT</CdtDbtInd>
				<Sts><Cd>BOOK</Cd></Sts>
				<BookgDt><Dt>2026-11-03</Dt></BookgDt>
				<ValDt><Dt>2026-11-03</Dt></ValDt>
				<AcctSvcrRef>REF-OM31A</AcctSvcrRef>
				<BkTxCd><Prtry><Cd>TRF</Cd><Issr>SWIFT</Issr></Prtry></BkTxCd>
				<NtryDtls>
					<Btch>
						<NbOfTxs>1</NbOfTxs>
						<TtlAmt Ccy="EUR">123.52</TtlAmt>
						<CdtDbtInd>CRDT</CdtDbtInd>
					</Btch>
					<TxDtls>
						<Refs><AcctSvcrRef>REF-OM31A-1</AcctSvcrRef></Refs>
						<Amt Ccy="EUR">123.52</Amt>
						<CdtDbtInd>CRDT</CdtDbtInd>
						<AmtDtls><TxAmt><Amt Ccy="EUR">123.52</Amt></TxAmt></AmtDtls>
						<RltdPties>
							<Dbtr><Pty><Nm>Customer Test</Nm></Pty></Dbtr>
							<DbtrAcct><Id><IBAN>CH4189144993834976921</IBAN></Id></DbtrAcct>
							<Cdtr><Pty><Nm>Test CO</Nm></Pty></Cdtr>
						</RltdPties>
						<RmtInf>
							<Ustrd>Vorauskasse OM31A-0031 Danke</Ustrd>
						</RmtInf>
					</TxDtls>
				</NtryDtls>
			</Ntry>
		</Stmt>
	</BkToCstmrStmt>
</Document>
    """
    And metasfresh contains C_Payment
      | Identifier | C_BPartner_ID | PayAmt     | IsReceipt | C_Order_ID |
      | p_31a      | bp_OM         | 100.00 EUR | true      | o_31a      |
    And the payment identified by p_31a is completed
    And the C_BankStatement identified by bs_31a is completed expecting error code BANKSTATEMENT_ORDER_ALREADY_PAID
    And exactly 1 payment carries the order identified by o_31a
    And validate the created orders
      | Identifier | DocStatus | GrandTotal |
      | o_31a      | CO        | 123.52     |


  # ################################################################################
  # ################################################################################
  # ################################################################################

  @from:cucumber
  @Id:S32606_OM31b
  Scenario: Hand payment for the order saved as a draft after the line was matched: completing the statement fails, no second payment

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | DocBaseType | DocSubType | DocumentNo |
      | o_31b      | true    | bp_OM         | 2026-11-03  | SOO         | PR         | OM31B-0031 |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | l_o_31b    | o_31b      | p_OM         | 1          |
    And the order identified by o_31b is completed and waits for payment
    When bank statement is imported with identifiers bs_31b, matching invoice amounts
    """
<?xml version="1.0" encoding="utf-8"?>
<Document xmlns="urn:iso:std:iso:20022:tech:xsd:camt.053.001.08">
	<BkToCstmrStmt>
		<GrpHdr>
			<MsgId>MsgIdOM31B</MsgId>
			<CreDtTm>2026-11-03T19:45:37.000Z</CreDtTm>
			<MsgPgntn><PgNb>1</PgNb><LastPgInd>true</LastPgInd></MsgPgntn>
		</GrpHdr>
		<Stmt>
			<Id>StmtIdOM31B</Id>
			<ElctrncSeqNb>00001</ElctrncSeqNb>
			<CreDtTm>2026-11-03T19:45:37.000Z</CreDtTm>
			<FrToDt><FrDtTm>2026-11-02T00:00:00</FrDtTm><ToDtTm>2026-11-03T23:59:59</ToDtTm></FrToDt>
			<Acct>
				<Id><IBAN>CH3908704016075473007</IBAN></Id>
				<Ccy>EUR</Ccy>
				<Ownr><Nm>Test CO</Nm></Ownr>
				<Svcr><FinInstnId><BICFI>AAAAAAAA82A</BICFI><Nm>Test Bank</Nm></FinInstnId></Svcr>
			</Acct>
			<Bal>
				<Tp><CdOrPrtry><Cd>OPBD</Cd></CdOrPrtry></Tp>
				<Amt Ccy="EUR">1000.00</Amt>
				<CdtDbtInd>CRDT</CdtDbtInd>
				<Dt><Dt>2026-11-02</Dt></Dt>
			</Bal>
			<Bal>
				<Tp><CdOrPrtry><Cd>CLBD</Cd></CdOrPrtry></Tp>
				<Amt Ccy="EUR">1123.52</Amt>
				<CdtDbtInd>CRDT</CdtDbtInd>
				<Dt><Dt>2026-11-03</Dt></Dt>
			</Bal>
			<Ntry>
				<NtryRef>REF-OM31B</NtryRef>
				<Amt Ccy="EUR">123.52</Amt>
				<CdtDbtInd>CRDT</CdtDbtInd>
				<Sts><Cd>BOOK</Cd></Sts>
				<BookgDt><Dt>2026-11-03</Dt></BookgDt>
				<ValDt><Dt>2026-11-03</Dt></ValDt>
				<AcctSvcrRef>REF-OM31B</AcctSvcrRef>
				<BkTxCd><Prtry><Cd>TRF</Cd><Issr>SWIFT</Issr></Prtry></BkTxCd>
				<NtryDtls>
					<Btch>
						<NbOfTxs>1</NbOfTxs>
						<TtlAmt Ccy="EUR">123.52</TtlAmt>
						<CdtDbtInd>CRDT</CdtDbtInd>
					</Btch>
					<TxDtls>
						<Refs><AcctSvcrRef>REF-OM31B-1</AcctSvcrRef></Refs>
						<Amt Ccy="EUR">123.52</Amt>
						<CdtDbtInd>CRDT</CdtDbtInd>
						<AmtDtls><TxAmt><Amt Ccy="EUR">123.52</Amt></TxAmt></AmtDtls>
						<RltdPties>
							<Dbtr><Pty><Nm>Customer Test</Nm></Pty></Dbtr>
							<DbtrAcct><Id><IBAN>CH4189144993834976921</IBAN></Id></DbtrAcct>
							<Cdtr><Pty><Nm>Test CO</Nm></Pty></Cdtr>
						</RltdPties>
						<RmtInf>
							<Ustrd>Vorauskasse OM31B-0031 Danke</Ustrd>
						</RmtInf>
					</TxDtls>
				</NtryDtls>
			</Ntry>
		</Stmt>
	</BkToCstmrStmt>
</Document>
    """
    And metasfresh contains C_Payment
      | Identifier | C_BPartner_ID | PayAmt     | IsReceipt | C_Order_ID |
      | p_31b      | bp_OM         | 100.00 EUR | true      | o_31b      |
    And the C_BankStatement identified by bs_31b is completed expecting error code BANKSTATEMENT_ORDER_ALREADY_PAID
    And exactly 1 payment carries the order identified by o_31b
    And validate the created orders
      | Identifier | DocStatus | GrandTotal |
      | o_31b      | WP        | 123.52     |


  # ################################################################################
  # ################################################################################
  # ################################################################################

  @from:cucumber
  @Id:S32606_OM31c
  Scenario: Hand payment of the full amount for the order completed after the line was matched: the line is linked to it, no new payment

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | DocBaseType | DocSubType | DocumentNo |
      | o_31c      | true    | bp_OM         | 2026-11-03  | SOO         | PR         | OM31C-0031 |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | l_o_31c    | o_31c      | p_OM         | 1          |
    And the order identified by o_31c is completed and waits for payment
    When bank statement is imported with identifiers bs_31c, matching invoice amounts
    """
<?xml version="1.0" encoding="utf-8"?>
<Document xmlns="urn:iso:std:iso:20022:tech:xsd:camt.053.001.08">
	<BkToCstmrStmt>
		<GrpHdr>
			<MsgId>MsgIdOM31C</MsgId>
			<CreDtTm>2026-11-03T19:45:37.000Z</CreDtTm>
			<MsgPgntn><PgNb>1</PgNb><LastPgInd>true</LastPgInd></MsgPgntn>
		</GrpHdr>
		<Stmt>
			<Id>StmtIdOM31C</Id>
			<ElctrncSeqNb>00001</ElctrncSeqNb>
			<CreDtTm>2026-11-03T19:45:37.000Z</CreDtTm>
			<FrToDt><FrDtTm>2026-11-02T00:00:00</FrDtTm><ToDtTm>2026-11-03T23:59:59</ToDtTm></FrToDt>
			<Acct>
				<Id><IBAN>CH3908704016075473007</IBAN></Id>
				<Ccy>EUR</Ccy>
				<Ownr><Nm>Test CO</Nm></Ownr>
				<Svcr><FinInstnId><BICFI>AAAAAAAA82A</BICFI><Nm>Test Bank</Nm></FinInstnId></Svcr>
			</Acct>
			<Bal>
				<Tp><CdOrPrtry><Cd>OPBD</Cd></CdOrPrtry></Tp>
				<Amt Ccy="EUR">1000.00</Amt>
				<CdtDbtInd>CRDT</CdtDbtInd>
				<Dt><Dt>2026-11-02</Dt></Dt>
			</Bal>
			<Bal>
				<Tp><CdOrPrtry><Cd>CLBD</Cd></CdOrPrtry></Tp>
				<Amt Ccy="EUR">1123.52</Amt>
				<CdtDbtInd>CRDT</CdtDbtInd>
				<Dt><Dt>2026-11-03</Dt></Dt>
			</Bal>
			<Ntry>
				<NtryRef>REF-OM31C</NtryRef>
				<Amt Ccy="EUR">123.52</Amt>
				<CdtDbtInd>CRDT</CdtDbtInd>
				<Sts><Cd>BOOK</Cd></Sts>
				<BookgDt><Dt>2026-11-03</Dt></BookgDt>
				<ValDt><Dt>2026-11-03</Dt></ValDt>
				<AcctSvcrRef>REF-OM31C</AcctSvcrRef>
				<BkTxCd><Prtry><Cd>TRF</Cd><Issr>SWIFT</Issr></Prtry></BkTxCd>
				<NtryDtls>
					<Btch>
						<NbOfTxs>1</NbOfTxs>
						<TtlAmt Ccy="EUR">123.52</TtlAmt>
						<CdtDbtInd>CRDT</CdtDbtInd>
					</Btch>
					<TxDtls>
						<Refs><AcctSvcrRef>REF-OM31C-1</AcctSvcrRef></Refs>
						<Amt Ccy="EUR">123.52</Amt>
						<CdtDbtInd>CRDT</CdtDbtInd>
						<AmtDtls><TxAmt><Amt Ccy="EUR">123.52</Amt></TxAmt></AmtDtls>
						<RltdPties>
							<Dbtr><Pty><Nm>Customer Test</Nm></Pty></Dbtr>
							<DbtrAcct><Id><IBAN>CH4189144993834976921</IBAN></Id></DbtrAcct>
							<Cdtr><Pty><Nm>Test CO</Nm></Pty></Cdtr>
						</RltdPties>
						<RmtInf>
							<Ustrd>Vorauskasse OM31C-0031 Danke</Ustrd>
						</RmtInf>
					</TxDtls>
				</NtryDtls>
			</Ntry>
		</Stmt>
	</BkToCstmrStmt>
</Document>
    """
    And metasfresh contains C_Payment
      | Identifier | C_BPartner_ID | PayAmt     | IsReceipt | C_Order_ID |
      | p_31c      | bp_OM         | 123.52 EUR | true      | o_31c      |
    And the payment identified by p_31c is completed
    And the C_BankStatement identified by bs_31c is completed
    And load C_BankStatementLine
      | C_BankStatementLine_ID.Identifier | C_BankStatement_ID.Identifier | Line |
      | bsl_31c                           | bs_31c                        | 10   |
    And validate C_BankStatementLine
      | C_BankStatementLine_ID.Identifier | OPT.C_Order_ID.Identifier | OPT.C_BPartner_ID.Identifier | OPT.C_Payment_ID.Identifier | OPT.Processed | OPT.IsReconciled |
      | bsl_31c                           | o_31c                     | bp_OM                        | p_31c                       | true          | true             |
    And exactly 1 payment carries the order identified by o_31c
    And validate the created orders
      | Identifier | DocStatus | GrandTotal |
      | o_31c      | CO        | 123.52     |


  # ################################################################################
  # ################################################################################
  # ################################################################################

  @from:cucumber
  @Id:S32606_OM32a
  Scenario: Debit line with a hand-picked completed order: the payment carries no order, no error

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | DocBaseType | DocSubType | DocumentNo |
      | o_32a      | true    | bp_OM         | 2026-11-03  | SOO         | SO         | OM32A-STD  |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | l_o_32a    | o_32a      | p_OM         | 1          |
    And the order identified by o_32a is completed
    And metasfresh contains C_BankStatement:
      | Identifier | C_BP_BankAccount_ID | StatementDate |
      | bs_32a     | bpb_OM              | 2026-11-03    |
    And metasfresh contains C_BankStatementLine:
      | Identifier | C_BankStatement_ID | Amount      | C_BPartner_ID | C_Order_ID |
      | bsl_32a    | bs_32a             | -123.52 EUR | bp_OM         | o_32a      |
    And the C_BankStatement identified by bs_32a is completed
    And after not more than 60s, C_Payment is found
      | C_Payment_ID.Identifier | OPT.C_BankStatement_ID.Identifier | OPT.C_BankStatementLine_ID.Identifier |
      | p_32a                   | bs_32a                            | bsl_32a                               |
    And validate payments
      | C_Payment_ID.Identifier | IsReceipt | C_Order_ID.Identifier |
      | p_32a                   | false     | null                  |


  # ################################################################################
  # ################################################################################
  # ################################################################################

  @from:cucumber
  @Id:S32606_OM32b
  Scenario: Line with an invoice and a hand-picked completed order: the payment carries the invoice, not the order, no error

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | DocBaseType | DocSubType | DocumentNo |
      | o_32b      | true    | bp_OM         | 2026-11-03  | SOO         | SO         | OM32B-STD  |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | l_o_32b    | o_32b      | p_OM         | 1          |
    And the order identified by o_32b is completed
    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID.Identifier | C_DocTypeTarget_ID.Name | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code | OPT.DocumentNo |
      | inv_32b    | bp_OM                    | Ausgangsrechnung        | 2026-11-03   | Spot                     | true    | EUR                 | OM32B-INV      |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | C_UOM_ID.X12DE355 |
      | il_inv_32b | inv_32b                 | p_OM                    | 1           | PCE               |
    And the invoice identified by inv_32b is completed
    And metasfresh contains C_BankStatement:
      | Identifier | C_BP_BankAccount_ID | StatementDate |
      | bs_32b     | bpb_OM              | 2026-11-03    |
    And metasfresh contains C_BankStatementLine:
      | Identifier | C_BankStatement_ID | Amount     | C_BPartner_ID | C_Invoice_ID | C_Order_ID |
      | bsl_32b    | bs_32b             | 123.52 EUR | bp_OM         | inv_32b      | o_32b      |
    And the C_BankStatement identified by bs_32b is completed
    And after not more than 60s, C_Payment is found
      | C_Payment_ID.Identifier | OPT.C_BankStatement_ID.Identifier | OPT.C_BankStatementLine_ID.Identifier |
      | p_32b                   | bs_32b                            | bsl_32b                               |
    And validate payments
      | C_Payment_ID.Identifier | IsReceipt | C_Order_ID.Identifier |
      | p_32b                   | true      | null                  |
