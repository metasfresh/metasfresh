@from:cucumber
@allure.label.epic:E0220_Financial
@allure.label.feature:F01100_Statistik_nach_Mengen_Gesamt
@F01100
Feature: import bank statement in camt.53.001.08 format - parity with the reference implementation, and real-bank samples
## F01100: Bank Statement
## Expected values are the ones RECORDED on the reference implementation for the same file and bank
## setting; they are never fitted to a run on the branch under test. Sample A is synthetic: party names, entry references and
## QRR references are substituted (QRR references regenerated with a valid check digit); amounts, dates and structure unchanged. Each file is imported twice: once with the bank setting
## "Import As Summary Line" set, once not set. Per line: amount, imported partner name, reference, and the matching outcome
## (invoice, partner, payment, "Abgeglichen").
## Allowed-difference trace (AC10): none so far. A line that matches less than on the reference implementation is a failure.
## Known reference behaviour that is recorded as is: in sample A the three QRR entries get NO invoice / partner / payment
## although invoices with the same reference exist; only the entry with the invoice number in its free text is matched.
## Invoice document numbers are unique per DB: the sample A "not set" run uses the suffix 2 on all four (B21_INV_A2 ... G170312, also in the file text) and the three QRR references carry one changed digit with a recomputed check digit, because document numbers and reference numbers are unique per DB and the "set" run has already created the originals.

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2020-02-28T13:30:13+01:00[Europe/Berlin]

    And metasfresh contains M_Products:
      | Identifier | Name        |
      | p_B21      | B21_Product |
    And metasfresh contains M_PricingSystems
      | Identifier | Name        | Value        | OPT.Description | OPT.IsActive |
      | ps_B21     | ps_name_B21 | ps_value_B21 | ps_desc_B21     | true         |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name              | OPT.Description | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | pl_SO_B21  | ps_B21                        | DE                        | CHF                 | price_list_SO_B21 | null            | true  | false         | 2              | true         |
      | pl_PO_B21  | ps_B21                        | DE                        | CHF                 | price_list_PO_B21 | null            | false | false         | 2              | true         |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name       | ValidFrom  |
      | plv_SO_B21 | pl_SO_B21                 | plv_SO_B21 | 2021-04-01 |
      | plv_PO_B21 | pl_PO_B21                 | plv_PO_B21 | 2021-04-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_SO_B21  | plv_SO_B21                        | p_B21                   | 0.01     | PCE               | Normal                        |
      | pp_PO_B21  | plv_PO_B21                        | p_B21                   | 0.01     | PCE               | Normal                        |

    And metasfresh contains C_BPartners without locations:
      | Identifier | Name         | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | bp_B21     | B21_Customer | Y            | Y              | ps_B21                        |

    And metasfresh contains C_BPartner_Locations:
      | Identifier | GLN           | C_BPartner_ID.Identifier | OPT.IsBillToDefault | OPT.IsShipTo |
      | l_B21      | 0203111111999 | bp_B21                   | true                | true         |

    And set sys config boolean value false for sys config de.metas.payment.esr.Enabled

  @from:cucumber
@allure.label.epic:E0220_Financial
@allure.label.feature:F01100_Statistik_nach_Mengen_Gesamt
@F01100
  @Id:S0337_880
  Scenario: camt.53.001.08 parity - synthetic sample A, Import As Summary Line set

    Given set sys config boolean value true for sys config de.metas.payment.esr.Enabled
    And load C_DataImport:
      | C_DataImport_ID.Identifier | OPT.C_DataImport_ID |
      | di_sampleA_on              | 540009              |
    And metasfresh contains C_Bank:
      | C_Bank_ID.Identifier | Name            | RoutingNo | SwiftCode   | C_DataImport_ID.Identifier | OPT.IsImportAsSingleSummaryLine |
      | b_sampleA_on         | bank_sampleA_on | 2234567   | AAAAAAAA82A | di_sampleA_on              | true                            |
    And load C_BP_BankAccount:
      | C_BP_BankAccount_ID.Identifier | OPT.C_BP_BankAccount_ID |
      | bpb_sampleA_on                 | 2000257                 |
    And update C_BP_BankAccount:
      | C_BP_BankAccount_ID.Identifier | OPT.C_Currency.ISO_Code | OPT.IsEsrAccount | OPT.AccountNo | OPT.ESR_RenderedAccountNo | OPT.IBAN              | OPT.C_Bank_ID.Identifier |
      | bpb_sampleA_on                 | CHF                     | Y                | 1234567890    | 123456789                 | CH9300762011623852957 | b_sampleA_on             |
    And metasfresh contains C_Invoice:
      | Identifier       | C_BPartner_ID.Identifier | C_DocTypeTarget_ID.Name | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code | OPT.DocumentNo |
      | inv_A_sampleA_on | bp_B21                   | Ausgangsrechnung        | 2022-05-11   | Spot                     | true    | CHF                 | B21_INV_A      |
      | inv_B_sampleA_on | bp_B21                   | Ausgangsrechnung        | 2022-05-11   | Spot                     | true    | CHF                 | B21_INV_B      |
      | inv_C_sampleA_on | bp_B21                   | Ausgangsrechnung        | 2022-05-11   | Spot                     | true    | CHF                 | B21_INV_C      |
      | inv_D_sampleA_on | bp_B21                   | Ausgangsrechnung        | 2022-05-11   | Spot                     | true    | CHF                 | G170311        |
    And metasfresh contains C_InvoiceLines
      | Identifier        | C_Invoice_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | C_UOM_ID.X12DE355 |
      | invl_A_sampleA_on | inv_A_sampleA_on        | p_B21                   | 272521      | PCE               |
      | invl_B_sampleA_on | inv_B_sampleA_on        | p_B21                   | 6697185     | PCE               |
      | invl_C_sampleA_on | inv_C_sampleA_on        | p_B21                   | 100000      | PCE               |
      | invl_D_sampleA_on | inv_D_sampleA_on        | p_B21                   | 25210       | PCE               |
    And the invoice identified by inv_A_sampleA_on is completed
    And the invoice identified by inv_B_sampleA_on is completed
    And the invoice identified by inv_C_sampleA_on is completed
    And the invoice identified by inv_D_sampleA_on is completed
    And load C_ReferenceNo:
      | C_ReferenceNo_ID.Identifier | Record_ID.Identifier | C_ReferenceNo_Type_ID.Identifier |
      | refno_A_sampleA_on          | inv_A_sampleA_on     | 540006                           |
      | refno_B_sampleA_on          | inv_B_sampleA_on     | 540006                           |
      | refno_C_sampleA_on          | inv_C_sampleA_on     | 540006                           |
    And update C_ReferenceNo:
      | C_ReferenceNo_ID.Identifier | Record_ID.Identifier | C_ReferenceNo_Type_ID.Identifier | OPT.ReferenceNo             |
      | refno_A_sampleA_on          | inv_A_sampleA_on     | 540006                           | 000000000000000000000000136 |
      | refno_B_sampleA_on          | inv_B_sampleA_on     | 540006                           | 000000000000000000000000110 |
      | refno_C_sampleA_on          | inv_C_sampleA_on     | 540006                           | 000000000000000000000000121 |
    When bank statement is imported with identifiers bs_sampleA_on, matching invoice amounts
    """
    <?xml version="1.0" encoding="UTF-8"?>
    <Document xmlns="urn:iso:std:iso:20022:tech:xsd:camt.053.001.08">
    	<BkToCstmrStmt>
    		<GrpHdr>
    			<MsgId>TEST-20261016183441</MsgId>
    			<CreDtTm>2026-10-16T18:34:41</CreDtTm>
    		</GrpHdr>
    		<Stmt>
    			<Id>TESTSTMT-20261016183441</Id>
    			<CreDtTm>2026-10-16T18:34:41</CreDtTm>
    			<Acct>
    				<Id><IBAN>CH9300762011623852957</IBAN></Id>
    				<Ccy>CHF</Ccy>
    			</Acct>
    			<Bal>
    				<Tp><CdOrPrtry><Cd>OPBD</Cd></CdOrPrtry></Tp>
    				<Amt Ccy="CHF">1000.00</Amt>
    				<CdtDbtInd>CRDT</CdtDbtInd>
    				<Dt><Dt>2026-10-16</Dt></Dt>
    			</Bal>
    			<Bal>
    				<Tp><CdOrPrtry><Cd>CLBD</Cd></CdOrPrtry></Tp>
    				<Amt Ccy="CHF">85810.75</Amt>
    				<CdtDbtInd>CRDT</CdtDbtInd>
    				<Dt><Dt>2026-10-16</Dt></Dt>
    			</Bal>
    			<Ntry>
    				<NtryRef>000000001</NtryRef>
    				<Amt Ccy="CHF">3243.00</Amt>
    				<CdtDbtInd>CRDT</CdtDbtInd>
    				<Sts><Cd>BOOK</Cd></Sts>
    				<BookgDt><Dt>2026-10-16</Dt></BookgDt>
    				<ValDt><Dt>2026-10-16</Dt></ValDt>
    				<AcctSvcrRef>TEST-20261016183441-1</AcctSvcrRef>
    				<BkTxCd><Prtry><Cd>NTRF</Cd></Prtry></BkTxCd>
    				<NtryDtls>
    					<TxDtls>
    						<Refs><AcctSvcrRef>TEST-20261016183441-1-TX</AcctSvcrRef></Refs>
    						<RltdPties><Dbtr><Pty><Nm>Muster Debitor 1 AG</Nm></Pty></Dbtr></RltdPties>
    						<RmtInf><Strd><CdtrRefInf><Tp><CdOrPrtry><Prtry>QRR</Prtry></CdOrPrtry></Tp><Ref>000000000000000000000000136</Ref></CdtrRefInf></Strd></RmtInf>
    					</TxDtls>
    				</NtryDtls>
    				<AddtlNtryInf>QRR Gutschrift</AddtlNtryInf>
    			</Ntry>
    			<Ntry>
    				<NtryRef>000000002</NtryRef>
    				<Amt Ccy="CHF">79696.50</Amt>
    				<CdtDbtInd>CRDT</CdtDbtInd>
    				<Sts><Cd>BOOK</Cd></Sts>
    				<BookgDt><Dt>2026-10-16</Dt></BookgDt>
    				<ValDt><Dt>2026-10-16</Dt></ValDt>
    				<AcctSvcrRef>TEST-20261016183441-2</AcctSvcrRef>
    				<BkTxCd><Prtry><Cd>NTRF</Cd></Prtry></BkTxCd>
    				<NtryDtls>
    					<TxDtls>
    						<Refs><AcctSvcrRef>TEST-20261016183441-2-TX</AcctSvcrRef></Refs>
    						<RltdPties><Dbtr><Pty><Nm>Muster Debitor 2 AG</Nm></Pty></Dbtr></RltdPties>
    						<RmtInf><Strd><CdtrRefInf><Tp><CdOrPrtry><Prtry>QRR</Prtry></CdOrPrtry></Tp><Ref>000000000000000000000000110</Ref></CdtrRefInf></Strd></RmtInf>
    					</TxDtls>
    				</NtryDtls>
    				<AddtlNtryInf>QRR Gutschrift</AddtlNtryInf>
    			</Ntry>
    			<Ntry>
    				<NtryRef>000000003</NtryRef>
    				<Amt Ccy="CHF">1621.50</Amt>
    				<CdtDbtInd>CRDT</CdtDbtInd>
    				<Sts><Cd>BOOK</Cd></Sts>
    				<BookgDt><Dt>2026-10-16</Dt></BookgDt>
    				<ValDt><Dt>2026-10-16</Dt></ValDt>
    				<AcctSvcrRef>TEST-20261016183441-3</AcctSvcrRef>
    				<BkTxCd><Prtry><Cd>NTRF</Cd></Prtry></BkTxCd>
    				<NtryDtls>
    					<TxDtls>
    						<Refs><AcctSvcrRef>TEST-20261016183441-3-TX</AcctSvcrRef></Refs>
    						<RltdPties><Dbtr><Pty><Nm>Muster Debitor 3 AG</Nm></Pty></Dbtr></RltdPties>
    						<RmtInf><Strd><CdtrRefInf><Tp><CdOrPrtry><Prtry>QRR</Prtry></CdOrPrtry></Tp><Ref>000000000000000000000000121</Ref></CdtrRefInf></Strd></RmtInf>
    					</TxDtls>
    				</NtryDtls>
    				<AddtlNtryInf>QRR Gutschrift</AddtlNtryInf>
    			</Ntry>
    			<Ntry>
    				<NtryRef>000000004</NtryRef>
    				<Amt Ccy="CHF">300.00</Amt>
    				<CdtDbtInd>CRDT</CdtDbtInd>
    				<Sts><Cd>BOOK</Cd></Sts>
    				<BookgDt><Dt>2026-10-16</Dt></BookgDt>
    				<ValDt><Dt>2026-10-16</Dt></ValDt>
    				<AcctSvcrRef>TEST-20261016183441-4</AcctSvcrRef>
    				<BkTxCd><Prtry><Cd>NTRF</Cd></Prtry></BkTxCd>
    				<NtryDtls>
    					<TxDtls>
    						<Refs><AcctSvcrRef>TEST-20261016183441-4-TX</AcctSvcrRef></Refs>
    						<RltdPties><Dbtr><Pty><Nm>Muster Hans</Nm></Pty></Dbtr></RltdPties>
    						<RmtInf><Ustrd>Rechnung G170311</Ustrd></RmtInf>
    					</TxDtls>
    				</NtryDtls>
    				<AddtlNtryInf>Gutschrift</AddtlNtryInf>
    			</Ntry>
    			<Ntry>
    				<NtryRef>000000005</NtryRef>
    				<Amt Ccy="CHF">50.25</Amt>
    				<CdtDbtInd>DBIT</CdtDbtInd>
    				<Sts><Cd>BOOK</Cd></Sts>
    				<BookgDt><Dt>2026-10-16</Dt></BookgDt>
    				<ValDt><Dt>2026-10-16</Dt></ValDt>
    				<AcctSvcrRef>TEST-20261016183441-5</AcctSvcrRef>
    				<BkTxCd><Prtry><Cd>NTRF</Cd></Prtry></BkTxCd>
    				<NtryDtls>
    					<TxDtls>
    						<Refs><AcctSvcrRef>TEST-20261016183441-5-TX</AcctSvcrRef></Refs>
    						<RltdPties><Cdtr><Pty><Nm>Muster Debitor 4 AG</Nm></Pty></Cdtr></RltdPties>
    						<RmtInf><Ustrd>Testzahlung ohne Rechnung</Ustrd></RmtInf>
    					</TxDtls>
    				</NtryDtls>
    				<AddtlNtryInf>Zahlung</AddtlNtryInf>
    			</Ntry>
    		</Stmt>
    	</BkToCstmrStmt>
    </Document>
    """
    And the C_BankStatement identified by bs_sampleA_on is completed
    Then validate imported C_BankStatement
      | C_BankStatement_ID.Identifier | LineCount | BeginningBalance | EndingBalance | StatementDifference | DocStatus | IsReconciled | StatementDate |
      | bs_sampleA_on                 | 6         | 1000.00          | 85810.75      | 84810.75            | CO        | false        | 2026-10-16    |
    And validate imported C_BankStatementLine
      | C_BankStatement_ID.Identifier | Line | TrxAmt | StmtAmt | StatementLineDate | ImportedBillPartnerName                                   | ReferenceNo           | C_Invoice.DocumentNo | C_BPartner.Name | C_Payment.PayAmt | IsReconciled |
      | bs_sampleA_on                 | 10   | 84561  | 84561   | 2026-10-16        | Muster Debitor 1 AGMuster Debitor 2 AGMuster Debitor 3 AG | <none>                | <none>               | <none>          | <none>           | false        |
      | bs_sampleA_on                 | 20   | 0      | 0       | 2026-10-16        | Muster Debitor 1 AG                                       | TEST-20261016183441-1 | <none>               | <none>          | <none>           | false        |
      | bs_sampleA_on                 | 30   | 0      | 0       | 2026-10-16        | Muster Debitor 2 AG                                       | TEST-20261016183441-2 | <none>               | <none>          | <none>           | false        |
      | bs_sampleA_on                 | 40   | 0      | 0       | 2026-10-16        | Muster Debitor 3 AG                                       | TEST-20261016183441-3 | <none>               | <none>          | <none>           | false        |
      | bs_sampleA_on                 | 50   | 300    | 300     | 2026-10-16        | Muster Hans                                               | TEST-20261016183441-4 | G170311              | B21_Customer    | 300              | true         |
      | bs_sampleA_on                 | 60   | -50.25 | -50.25  | 2026-10-16        | Muster Debitor 4 AG                                       | TEST-20261016183441-5 | <none>               | <none>          | <none>           | false        |

  @from:cucumber
@allure.label.epic:E0220_Financial
@allure.label.feature:F01100_Statistik_nach_Mengen_Gesamt
@F01100
  @Id:S0337_890
  Scenario: camt.53.001.08 parity - synthetic sample A, Import As Summary Line not set

    Given set sys config boolean value true for sys config de.metas.payment.esr.Enabled
    And load C_DataImport:
      | C_DataImport_ID.Identifier | OPT.C_DataImport_ID |
      | di_sampleA_off             | 540009              |
    And metasfresh contains C_Bank:
      | C_Bank_ID.Identifier | Name             | RoutingNo | SwiftCode   | C_DataImport_ID.Identifier | OPT.IsImportAsSingleSummaryLine |
      | b_sampleA_off        | bank_sampleA_off | 2234567   | AAAAAAAA82A | di_sampleA_off             | false                           |
    And load C_BP_BankAccount:
      | C_BP_BankAccount_ID.Identifier | OPT.C_BP_BankAccount_ID |
      | bpb_sampleA_off                | 2000257                 |
    And update C_BP_BankAccount:
      | C_BP_BankAccount_ID.Identifier | OPT.C_Currency.ISO_Code | OPT.IsEsrAccount | OPT.AccountNo | OPT.ESR_RenderedAccountNo | OPT.IBAN              | OPT.C_Bank_ID.Identifier |
      | bpb_sampleA_off                | CHF                     | Y                | 1234567890    | 123456789                 | CH9300762011623852957 | b_sampleA_off            |
    And metasfresh contains C_Invoice:
      | Identifier        | C_BPartner_ID.Identifier | C_DocTypeTarget_ID.Name | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code | OPT.DocumentNo |
      | inv_A_sampleA_off | bp_B21                   | Ausgangsrechnung        | 2022-05-11   | Spot                     | true    | CHF                 | B21_INV_A2     |
      | inv_B_sampleA_off | bp_B21                   | Ausgangsrechnung        | 2022-05-11   | Spot                     | true    | CHF                 | B21_INV_B2     |
      | inv_C_sampleA_off | bp_B21                   | Ausgangsrechnung        | 2022-05-11   | Spot                     | true    | CHF                 | B21_INV_C2     |
      | inv_D_sampleA_off | bp_B21                   | Ausgangsrechnung        | 2022-05-11   | Spot                     | true    | CHF                 | G170312        |
    And metasfresh contains C_InvoiceLines
      | Identifier         | C_Invoice_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | C_UOM_ID.X12DE355 |
      | invl_A_sampleA_off | inv_A_sampleA_off       | p_B21                   | 272521      | PCE               |
      | invl_B_sampleA_off | inv_B_sampleA_off       | p_B21                   | 6697185     | PCE               |
      | invl_C_sampleA_off | inv_C_sampleA_off       | p_B21                   | 100000      | PCE               |
      | invl_D_sampleA_off | inv_D_sampleA_off       | p_B21                   | 25210       | PCE               |
    And the invoice identified by inv_A_sampleA_off is completed
    And the invoice identified by inv_B_sampleA_off is completed
    And the invoice identified by inv_C_sampleA_off is completed
    And the invoice identified by inv_D_sampleA_off is completed
    And load C_ReferenceNo:
      | C_ReferenceNo_ID.Identifier | Record_ID.Identifier | C_ReferenceNo_Type_ID.Identifier |
      | refno_A_sampleA_off         | inv_A_sampleA_off    | 540006                           |
      | refno_B_sampleA_off         | inv_B_sampleA_off    | 540006                           |
      | refno_C_sampleA_off         | inv_C_sampleA_off    | 540006                           |
    And update C_ReferenceNo:
      | C_ReferenceNo_ID.Identifier | Record_ID.Identifier | C_ReferenceNo_Type_ID.Identifier | OPT.ReferenceNo             |
      | refno_A_sampleA_off         | inv_A_sampleA_off    | 540006                           | 000000000000100000000000135 |
      | refno_B_sampleA_off         | inv_B_sampleA_off    | 540006                           | 000000000000100000000000119 |
      | refno_C_sampleA_off         | inv_C_sampleA_off    | 540006                           | 000000000000100000000000127 |
    When bank statement is imported with identifiers bs_sampleA_off, matching invoice amounts
    """
    <?xml version="1.0" encoding="UTF-8"?>
    <Document xmlns="urn:iso:std:iso:20022:tech:xsd:camt.053.001.08">
    	<BkToCstmrStmt>
    		<GrpHdr>
    			<MsgId>TEST-20261016183441</MsgId>
    			<CreDtTm>2026-10-16T18:34:41</CreDtTm>
    		</GrpHdr>
    		<Stmt>
    			<Id>TESTSTMT-20261016183441</Id>
    			<CreDtTm>2026-10-16T18:34:41</CreDtTm>
    			<Acct>
    				<Id><IBAN>CH9300762011623852957</IBAN></Id>
    				<Ccy>CHF</Ccy>
    			</Acct>
    			<Bal>
    				<Tp><CdOrPrtry><Cd>OPBD</Cd></CdOrPrtry></Tp>
    				<Amt Ccy="CHF">1000.00</Amt>
    				<CdtDbtInd>CRDT</CdtDbtInd>
    				<Dt><Dt>2026-10-16</Dt></Dt>
    			</Bal>
    			<Bal>
    				<Tp><CdOrPrtry><Cd>CLBD</Cd></CdOrPrtry></Tp>
    				<Amt Ccy="CHF">85810.75</Amt>
    				<CdtDbtInd>CRDT</CdtDbtInd>
    				<Dt><Dt>2026-10-16</Dt></Dt>
    			</Bal>
    			<Ntry>
    				<NtryRef>000000001</NtryRef>
    				<Amt Ccy="CHF">3243.00</Amt>
    				<CdtDbtInd>CRDT</CdtDbtInd>
    				<Sts><Cd>BOOK</Cd></Sts>
    				<BookgDt><Dt>2026-10-16</Dt></BookgDt>
    				<ValDt><Dt>2026-10-16</Dt></ValDt>
    				<AcctSvcrRef>TEST-20261016183441-1</AcctSvcrRef>
    				<BkTxCd><Prtry><Cd>NTRF</Cd></Prtry></BkTxCd>
    				<NtryDtls>
    					<TxDtls>
    						<Refs><AcctSvcrRef>TEST-20261016183441-1-TX</AcctSvcrRef></Refs>
    						<RltdPties><Dbtr><Pty><Nm>Muster Debitor 1 AG</Nm></Pty></Dbtr></RltdPties>
    						<RmtInf><Strd><CdtrRefInf><Tp><CdOrPrtry><Prtry>QRR</Prtry></CdOrPrtry></Tp><Ref>000000000000100000000000135</Ref></CdtrRefInf></Strd></RmtInf>
    					</TxDtls>
    				</NtryDtls>
    				<AddtlNtryInf>QRR Gutschrift</AddtlNtryInf>
    			</Ntry>
    			<Ntry>
    				<NtryRef>000000002</NtryRef>
    				<Amt Ccy="CHF">79696.50</Amt>
    				<CdtDbtInd>CRDT</CdtDbtInd>
    				<Sts><Cd>BOOK</Cd></Sts>
    				<BookgDt><Dt>2026-10-16</Dt></BookgDt>
    				<ValDt><Dt>2026-10-16</Dt></ValDt>
    				<AcctSvcrRef>TEST-20261016183441-2</AcctSvcrRef>
    				<BkTxCd><Prtry><Cd>NTRF</Cd></Prtry></BkTxCd>
    				<NtryDtls>
    					<TxDtls>
    						<Refs><AcctSvcrRef>TEST-20261016183441-2-TX</AcctSvcrRef></Refs>
    						<RltdPties><Dbtr><Pty><Nm>Muster Debitor 2 AG</Nm></Pty></Dbtr></RltdPties>
    						<RmtInf><Strd><CdtrRefInf><Tp><CdOrPrtry><Prtry>QRR</Prtry></CdOrPrtry></Tp><Ref>000000000000100000000000119</Ref></CdtrRefInf></Strd></RmtInf>
    					</TxDtls>
    				</NtryDtls>
    				<AddtlNtryInf>QRR Gutschrift</AddtlNtryInf>
    			</Ntry>
    			<Ntry>
    				<NtryRef>000000003</NtryRef>
    				<Amt Ccy="CHF">1621.50</Amt>
    				<CdtDbtInd>CRDT</CdtDbtInd>
    				<Sts><Cd>BOOK</Cd></Sts>
    				<BookgDt><Dt>2026-10-16</Dt></BookgDt>
    				<ValDt><Dt>2026-10-16</Dt></ValDt>
    				<AcctSvcrRef>TEST-20261016183441-3</AcctSvcrRef>
    				<BkTxCd><Prtry><Cd>NTRF</Cd></Prtry></BkTxCd>
    				<NtryDtls>
    					<TxDtls>
    						<Refs><AcctSvcrRef>TEST-20261016183441-3-TX</AcctSvcrRef></Refs>
    						<RltdPties><Dbtr><Pty><Nm>Muster Debitor 3 AG</Nm></Pty></Dbtr></RltdPties>
    						<RmtInf><Strd><CdtrRefInf><Tp><CdOrPrtry><Prtry>QRR</Prtry></CdOrPrtry></Tp><Ref>000000000000100000000000127</Ref></CdtrRefInf></Strd></RmtInf>
    					</TxDtls>
    				</NtryDtls>
    				<AddtlNtryInf>QRR Gutschrift</AddtlNtryInf>
    			</Ntry>
    			<Ntry>
    				<NtryRef>000000004</NtryRef>
    				<Amt Ccy="CHF">300.00</Amt>
    				<CdtDbtInd>CRDT</CdtDbtInd>
    				<Sts><Cd>BOOK</Cd></Sts>
    				<BookgDt><Dt>2026-10-16</Dt></BookgDt>
    				<ValDt><Dt>2026-10-16</Dt></ValDt>
    				<AcctSvcrRef>TEST-20261016183441-4</AcctSvcrRef>
    				<BkTxCd><Prtry><Cd>NTRF</Cd></Prtry></BkTxCd>
    				<NtryDtls>
    					<TxDtls>
    						<Refs><AcctSvcrRef>TEST-20261016183441-4-TX</AcctSvcrRef></Refs>
    						<RltdPties><Dbtr><Pty><Nm>Muster Hans</Nm></Pty></Dbtr></RltdPties>
    						<RmtInf><Ustrd>Rechnung G170312</Ustrd></RmtInf>
    					</TxDtls>
    				</NtryDtls>
    				<AddtlNtryInf>Gutschrift</AddtlNtryInf>
    			</Ntry>
    			<Ntry>
    				<NtryRef>000000005</NtryRef>
    				<Amt Ccy="CHF">50.25</Amt>
    				<CdtDbtInd>DBIT</CdtDbtInd>
    				<Sts><Cd>BOOK</Cd></Sts>
    				<BookgDt><Dt>2026-10-16</Dt></BookgDt>
    				<ValDt><Dt>2026-10-16</Dt></ValDt>
    				<AcctSvcrRef>TEST-20261016183441-5</AcctSvcrRef>
    				<BkTxCd><Prtry><Cd>NTRF</Cd></Prtry></BkTxCd>
    				<NtryDtls>
    					<TxDtls>
    						<Refs><AcctSvcrRef>TEST-20261016183441-5-TX</AcctSvcrRef></Refs>
    						<RltdPties><Cdtr><Pty><Nm>Muster Debitor 4 AG</Nm></Pty></Cdtr></RltdPties>
    						<RmtInf><Ustrd>Testzahlung ohne Rechnung</Ustrd></RmtInf>
    					</TxDtls>
    				</NtryDtls>
    				<AddtlNtryInf>Zahlung</AddtlNtryInf>
    			</Ntry>
    		</Stmt>
    	</BkToCstmrStmt>
    </Document>
    """
    And the C_BankStatement identified by bs_sampleA_off is completed
    Then validate imported C_BankStatement
      | C_BankStatement_ID.Identifier | LineCount | BeginningBalance | EndingBalance | StatementDifference | DocStatus | IsReconciled | StatementDate |
      | bs_sampleA_off                | 5         | 1000.00          | 85810.75      | 84810.75            | CO        | false        | 2026-10-16    |
    And validate imported C_BankStatementLine
      | C_BankStatement_ID.Identifier | Line | TrxAmt  | StmtAmt | StatementLineDate | ImportedBillPartnerName | ReferenceNo           | C_Invoice.DocumentNo | C_BPartner.Name | C_Payment.PayAmt | IsReconciled |
      | bs_sampleA_off                | 10   | 3243    | 3243    | 2026-10-16        | Muster Debitor 1 AG     | TEST-20261016183441-1 | <none>               | <none>          | <none>           | false        |
      | bs_sampleA_off                | 20   | 79696.5 | 79696.5 | 2026-10-16        | Muster Debitor 2 AG     | TEST-20261016183441-2 | <none>               | <none>          | <none>           | false        |
      | bs_sampleA_off                | 30   | 1621.5  | 1621.5  | 2026-10-16        | Muster Debitor 3 AG     | TEST-20261016183441-3 | <none>               | <none>          | <none>           | false        |
      | bs_sampleA_off                | 40   | 300     | 300     | 2026-10-16        | Muster Hans             | TEST-20261016183441-4 | G170312              | B21_Customer    | 300              | true         |
      | bs_sampleA_off                | 50   | -50.25  | -50.25  | 2026-10-16        | Muster Debitor 4 AG     | TEST-20261016183441-5 | <none>               | <none>          | <none>           | false        |

  @from:cucumber
@allure.label.epic:E0220_Financial
@allure.label.feature:F01100_Statistik_nach_Mengen_Gesamt
@F01100
  @Id:S0337_900
  Scenario: camt.53.001.08 parity - PostFinance sample 2020-11-25, Import As Summary Line set

    Given set sys config boolean value true for sys config de.metas.payment.esr.Enabled
    And load C_DataImport:
      | C_DataImport_ID.Identifier | OPT.C_DataImport_ID |
      | di_pf2020_on               | 540009              |
    And metasfresh contains C_Bank:
      | C_Bank_ID.Identifier | Name           | RoutingNo | SwiftCode   | C_DataImport_ID.Identifier | OPT.IsImportAsSingleSummaryLine |
      | b_pf2020_on          | bank_pf2020_on | 2234567   | AAAAAAAA82A | di_pf2020_on               | true                            |
    And load C_BP_BankAccount:
      | C_BP_BankAccount_ID.Identifier | OPT.C_BP_BankAccount_ID |
      | bpb_pf2020_on                  | 2000257                 |
    And update C_BP_BankAccount:
      | C_BP_BankAccount_ID.Identifier | OPT.C_Currency.ISO_Code | OPT.IsEsrAccount | OPT.AccountNo | OPT.ESR_RenderedAccountNo | OPT.IBAN              | OPT.C_Bank_ID.Identifier |
      | bpb_pf2020_on                  | CHF                     | Y                | 1234567890    | 123456789                 | CH2909000000250094239 | b_pf2020_on              |
    When bank statement is imported with identifiers bs_pf2020_on, matching invoice amounts
    """
    <?xml version="1.0" encoding="UTF-8"?>
    <Document xmlns="urn:iso:std:iso:20022:tech:xsd:camt.053.001.08" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="urn:iso:std:iso:20022:tech:xsd:camt.053.001.08 file:///C:/Users/burkhalterl/Downloads/camt.053.001.08.xsd">
    	<BkToCstmrStmt>
    		<GrpHdr>
    			<MsgId>20201124375204229003967</MsgId>
    			<CreDtTm>2020-11-25T02:02:16</CreDtTm>
    			<MsgPgntn>
    				<PgNb>1</PgNb>
    				<LastPgInd>true</LastPgInd>
    			</MsgPgntn>
    			<AddtlInf>SPS/2.0/PROD</AddtlInf>
    		</GrpHdr>
    		<Stmt>
    			<Id>20201124375204229003973</Id>
    			<ElctrncSeqNb>118</ElctrncSeqNb>
    			<CreDtTm>2020-11-25T02:02:16</CreDtTm>
    			<FrToDt>
    				<FrDtTm>2020-06-24T00:00:00</FrDtTm>
    				<ToDtTm>2020-06-24T23:59:59</ToDtTm>
    			</FrToDt>
    			<Acct>
    				<Id>
    					<IBAN>CH2909000000250094239</IBAN>
    				</Id>
    				<Ccy>CHF</Ccy>
    				<Ownr>
    					<Nm>Robert Schneider SA Grands magasins Biel/Bienne</Nm>
    				</Ownr>
    			</Acct>
    			<Bal>
    				<Tp>
    					<CdOrPrtry>
    						<Cd>OPBD</Cd>
    					</CdOrPrtry>
    				</Tp>
    				<Amt Ccy="CHF">211993.19</Amt>
    				<CdtDbtInd>CRDT</CdtDbtInd>
    				<Dt>
    					<Dt>2020-11-24</Dt>
    				</Dt>
    			</Bal>
    			<Bal>
    				<Tp>
    					<CdOrPrtry>
    						<Cd>CLBD</Cd>
    					</CdOrPrtry>
    				</Tp>
    				<Amt Ccy="CHF">215112.21</Amt>
    				<CdtDbtInd>CRDT</CdtDbtInd>
    				<Dt>
    					<Dt>2020-11-24</Dt>
    				</Dt>
    			</Bal>
    			<Bal>
    				<Tp>
    					<CdOrPrtry>
    						<Cd>CLAV</Cd>
    					</CdOrPrtry>
    				</Tp>
    				<Amt Ccy="CHF">215112.21</Amt>
    				<CdtDbtInd>CRDT</CdtDbtInd>
    				<Dt>
    					<Dt>2020-11-24</Dt>
    				</Dt>
    			</Bal>
    			<Bal>
    				<Tp>
    					<CdOrPrtry>
    						<Cd>FWAV</Cd>
    					</CdOrPrtry>
    				</Tp>
    				<Amt Ccy="CHF">216649.21</Amt>
    				<CdtDbtInd>CRDT</CdtDbtInd>
    				<Dt>
    					<Dt>2020-11-25</Dt>
    				</Dt>
    			</Bal>
    			<Ntry>
    				<NtryRef>41100000000872849</NtryRef>
    				<Amt Ccy="CHF">4.50</Amt>
    				<CdtDbtInd>CRDT</CdtDbtInd>
    				<RvslInd>false</RvslInd>
    				<Sts>
    					<Cd>BOOK</Cd>
    				</Sts>
    				<BookgDt>
    					<Dt>2020-11-24</Dt>
    				</BookgDt>
    				<ValDt>
    					<Dt>2020-11-24</Dt>
    				</ValDt>
    				<AcctSvcrRef>0758103031480900</AcctSvcrRef>
    				<BkTxCd>
    					<Domn>
    						<Cd>PMNT</Cd>
    						<Fmly>
    							<Cd>IDDT</Cd>
    							<SubFmlyCd>PMDD</SubFmlyCd>
    						</Fmly>
    					</Domn>
    				</BkTxCd>
    				<NtryDtls>
    					<TxDtls>
    						<Refs>
    							<AcctSvcrRef>0758103031480900</AcctSvcrRef>
    						</Refs>
    						<Amt Ccy="CHF">4.50</Amt>
    						<CdtDbtInd>CRDT</CdtDbtInd>
    					</TxDtls>
    				</NtryDtls>
    				<AddtlNtryInf>GUTSCHRIFT CH-DD-BASISLASTSCHRIFT ID-NR. DES ZAHLUNGSEMPFÄNGERS: 41100000000872849 REFERENZ-NR: PmtInfId-CHDD-1</AddtlNtryInf>
    			</Ntry>
    			<Ntry>
    				<Amt Ccy="CHF">77.70</Amt>
    				<CdtDbtInd>DBIT</CdtDbtInd>
    				<RvslInd>false</RvslInd>
    				<Sts>
    					<Cd>BOOK</Cd>
    				</Sts>
    				<BookgDt>
    					<Dt>2020-11-24</Dt>
    				</BookgDt>
    				<ValDt>
    					<Dt>2020-11-24</Dt>
    				</ValDt>
    				<AcctSvcrRef>0758103031480977</AcctSvcrRef>
    				<BkTxCd>
    					<Domn>
    						<Cd>PMNT</Cd>
    						<Fmly>
    							<Cd>ICDT</Cd>
    							<SubFmlyCd>AUTT</SubFmlyCd>
    						</Fmly>
    					</Domn>
    				</BkTxCd>
    				<NtryDtls>
    					<TxDtls>
    						<Refs>
    							<AcctSvcrRef>0758103031480977</AcctSvcrRef>
    						</Refs>
    						<Amt Ccy="CHF">77.70</Amt>
    						<CdtDbtInd>CRDT</CdtDbtInd>
    					</TxDtls>
    				</NtryDtls>
    				<AddtlNtryInf>GUTSCHRIFT CH-DD-BASISLASTSCHRIFT ID-NR. DES ZAHLUNGSEMPFÄNGERS: 41100000000872849 REFERENZ-NR: PmtInfId-CHDD-1</AddtlNtryInf>
    			</Ntry>
    			<Ntry>
    				<Amt Ccy="CHF">1500.00</Amt>
    				<CdtDbtInd>CRDT</CdtDbtInd>
    				<RvslInd>false</RvslInd>
    				<Sts>
    					<Cd>BOOK</Cd>
    				</Sts>
    				<BookgDt>
    					<Dt>2020-11-24</Dt>
    				</BookgDt>
    				<ValDt>
    					<Dt>2020-11-24</Dt>
    				</ValDt>
    				<AcctSvcrRef>07582000303F0C7U</AcctSvcrRef>
    				<BkTxCd>
    					<Domn>
    						<Cd>PMNT</Cd>
    						<Fmly>
    							<Cd>RCDT</Cd>
    							<SubFmlyCd>AUTT</SubFmlyCd>
    						</Fmly>
    					</Domn>
    				</BkTxCd>
    				<NtryDtls>
    					<TxDtls>
    						<Refs>
    							<AcctSvcrRef>07582000303F0C7U</AcctSvcrRef>
    						</Refs>
    						<Amt Ccy="CHF">1500.00</Amt>
    						<CdtDbtInd>CRDT</CdtDbtInd>
    					</TxDtls>
    				</NtryDtls>
    				<AddtlNtryInf>SAMMELGUTSCHRIFT FÜR KONTO: CH2909000000250094239 VERARBEITUNG VOM 23.11.2020 PAKET ID: 9999999999999999</AddtlNtryInf>
    			</Ntry>
    			<Ntry>
    				<NtryRef>CH2909000000250094239</NtryRef>
    				<Amt Ccy="CHF">1537.00</Amt>
    				<CdtDbtInd>CRDT</CdtDbtInd>
    				<RvslInd>false</RvslInd>
    				<Sts>
    					<Cd>BOOK</Cd>
    				</Sts>
    				<BookgDt>
    					<Dt>2020-11-24</Dt>
    				</BookgDt>
    				<ValDt>
    					<Dt>2020-11-25</Dt>
    				</ValDt>
    				<AcctSvcrRef>07582000303KBMSU</AcctSvcrRef>
    				<BkTxCd>
    					<Domn>
    						<Cd>PMNT</Cd>
    						<Fmly>
    							<Cd>RCDT</Cd>
    							<SubFmlyCd>VCOM</SubFmlyCd>
    						</Fmly>
    					</Domn>
    				</BkTxCd>
    				<NtryDtls>
    					<TxDtls>
    						<Refs>
    							<AcctSvcrRef>07582000303KBMSU</AcctSvcrRef>
    						</Refs>
    						<Amt Ccy="CHF">1537.00</Amt>
    						<CdtDbtInd>CRDT</CdtDbtInd>
    					</TxDtls>
    				</NtryDtls>
    				<AddtlNtryInf>SAMMELGUTSCHRIFT FÜR KONTO: CH2909000000250094239 VERARBEITUNG VOM 24.11.2020 PAKET ID: 9999999999999998</AddtlNtryInf>
    			</Ntry>
    			<Ntry>
    				<NtryRef>CH7730000001250094239</NtryRef>
    				<Amt Ccy="CHF">1692.22</Amt>
    				<CdtDbtInd>CRDT</CdtDbtInd>
    				<RvslInd>false</RvslInd>
    				<Sts>
    					<Cd>BOOK</Cd>
    				</Sts>
    				<BookgDt>
    					<Dt>2020-11-24</Dt>
    				</BookgDt>
    				<ValDt>
    					<Dt>2020-11-24</Dt>
    				</ValDt>
    				<AcctSvcrRef>07582000303KBMSU</AcctSvcrRef>
    				<BkTxCd>
    					<Domn>
    						<Cd>PMNT</Cd>
    						<Fmly>
    							<Cd>RCDT</Cd>
    							<SubFmlyCd>VCOM</SubFmlyCd>
    						</Fmly>
    					</Domn>
    				</BkTxCd>
    				<NtryDtls>
    					<TxDtls>
    						<Refs>
    							<AcctSvcrRef>07582000303KBMSU</AcctSvcrRef>
    						</Refs>
    						<Amt Ccy="CHF">1692.22</Amt>
    						<CdtDbtInd>CRDT</CdtDbtInd>
    					</TxDtls>
    				</NtryDtls>
    				<AddtlNtryInf>SAMMELGUTSCHRIFT FÜR KONTO: CH7730000001250094239 VERARBEITUNG VOM 24.11.2020 PAKET ID: 201124CH000009TB</AddtlNtryInf>
    			</Ntry>
    		</Stmt>
    	</BkToCstmrStmt>
    </Document>
    """
    And the C_BankStatement identified by bs_pf2020_on is completed
    Then validate imported C_BankStatement
      | C_BankStatement_ID.Identifier | LineCount | BeginningBalance | EndingBalance | StatementDifference | DocStatus | IsReconciled | StatementDate |
      | bs_pf2020_on                  | 6         | 211993.19        | 216649.21     | 4656.02             | CO        | false        | 2020-11-25    |
    And validate imported C_BankStatementLine
      | C_BankStatement_ID.Identifier | Line | TrxAmt  | StmtAmt | StatementLineDate | ImportedBillPartnerName | ReferenceNo      | C_Invoice.DocumentNo | C_BPartner.Name | C_Payment.PayAmt | IsReconciled |
      | bs_pf2020_on                  | 10   | 0       | 0       | 2020-11-25        | <none>                  | <none>           | <none>               | <none>          | <none>           | false        |
      | bs_pf2020_on                  | 20   | 4.5     | 4.5     | 2020-11-24        | <none>                  | 0758103031480900 | <none>               | <none>          | <none>           | false        |
      | bs_pf2020_on                  | 30   | -77.7   | -77.7   | 2020-11-24        | <none>                  | 0758103031480977 | <none>               | <none>          | <none>           | false        |
      | bs_pf2020_on                  | 40   | 1500    | 1500    | 2020-11-24        | <none>                  | 07582000303F0C7U | <none>               | <none>          | <none>           | false        |
      | bs_pf2020_on                  | 50   | 1537    | 1537    | 2020-11-25        | <none>                  | 07582000303KBMSU | <none>               | <none>          | <none>           | false        |
      | bs_pf2020_on                  | 60   | 1692.22 | 1692.22 | 2020-11-24        | <none>                  | 07582000303KBMSU | <none>               | <none>          | <none>           | false        |

  @from:cucumber
@allure.label.epic:E0220_Financial
@allure.label.feature:F01100_Statistik_nach_Mengen_Gesamt
@F01100
  @Id:S0337_910
  Scenario: camt.53.001.08 parity - PostFinance sample 2020-11-25, Import As Summary Line not set

    Given set sys config boolean value true for sys config de.metas.payment.esr.Enabled
    And load C_DataImport:
      | C_DataImport_ID.Identifier | OPT.C_DataImport_ID |
      | di_pf2020_off              | 540009              |
    And metasfresh contains C_Bank:
      | C_Bank_ID.Identifier | Name            | RoutingNo | SwiftCode   | C_DataImport_ID.Identifier | OPT.IsImportAsSingleSummaryLine |
      | b_pf2020_off         | bank_pf2020_off | 2234567   | AAAAAAAA82A | di_pf2020_off              | false                           |
    And load C_BP_BankAccount:
      | C_BP_BankAccount_ID.Identifier | OPT.C_BP_BankAccount_ID |
      | bpb_pf2020_off                 | 2000257                 |
    And update C_BP_BankAccount:
      | C_BP_BankAccount_ID.Identifier | OPT.C_Currency.ISO_Code | OPT.IsEsrAccount | OPT.AccountNo | OPT.ESR_RenderedAccountNo | OPT.IBAN              | OPT.C_Bank_ID.Identifier |
      | bpb_pf2020_off                 | CHF                     | Y                | 1234567890    | 123456789                 | CH2909000000250094239 | b_pf2020_off             |
    When bank statement is imported with identifiers bs_pf2020_off, matching invoice amounts
    """
    <?xml version="1.0" encoding="UTF-8"?>
    <Document xmlns="urn:iso:std:iso:20022:tech:xsd:camt.053.001.08" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="urn:iso:std:iso:20022:tech:xsd:camt.053.001.08 file:///C:/Users/burkhalterl/Downloads/camt.053.001.08.xsd">
    	<BkToCstmrStmt>
    		<GrpHdr>
    			<MsgId>20201124375204229003967</MsgId>
    			<CreDtTm>2020-11-25T02:02:16</CreDtTm>
    			<MsgPgntn>
    				<PgNb>1</PgNb>
    				<LastPgInd>true</LastPgInd>
    			</MsgPgntn>
    			<AddtlInf>SPS/2.0/PROD</AddtlInf>
    		</GrpHdr>
    		<Stmt>
    			<Id>20201124375204229003973</Id>
    			<ElctrncSeqNb>118</ElctrncSeqNb>
    			<CreDtTm>2020-11-25T02:02:16</CreDtTm>
    			<FrToDt>
    				<FrDtTm>2020-06-24T00:00:00</FrDtTm>
    				<ToDtTm>2020-06-24T23:59:59</ToDtTm>
    			</FrToDt>
    			<Acct>
    				<Id>
    					<IBAN>CH2909000000250094239</IBAN>
    				</Id>
    				<Ccy>CHF</Ccy>
    				<Ownr>
    					<Nm>Robert Schneider SA Grands magasins Biel/Bienne</Nm>
    				</Ownr>
    			</Acct>
    			<Bal>
    				<Tp>
    					<CdOrPrtry>
    						<Cd>OPBD</Cd>
    					</CdOrPrtry>
    				</Tp>
    				<Amt Ccy="CHF">211993.19</Amt>
    				<CdtDbtInd>CRDT</CdtDbtInd>
    				<Dt>
    					<Dt>2020-11-24</Dt>
    				</Dt>
    			</Bal>
    			<Bal>
    				<Tp>
    					<CdOrPrtry>
    						<Cd>CLBD</Cd>
    					</CdOrPrtry>
    				</Tp>
    				<Amt Ccy="CHF">215112.21</Amt>
    				<CdtDbtInd>CRDT</CdtDbtInd>
    				<Dt>
    					<Dt>2020-11-24</Dt>
    				</Dt>
    			</Bal>
    			<Bal>
    				<Tp>
    					<CdOrPrtry>
    						<Cd>CLAV</Cd>
    					</CdOrPrtry>
    				</Tp>
    				<Amt Ccy="CHF">215112.21</Amt>
    				<CdtDbtInd>CRDT</CdtDbtInd>
    				<Dt>
    					<Dt>2020-11-24</Dt>
    				</Dt>
    			</Bal>
    			<Bal>
    				<Tp>
    					<CdOrPrtry>
    						<Cd>FWAV</Cd>
    					</CdOrPrtry>
    				</Tp>
    				<Amt Ccy="CHF">216649.21</Amt>
    				<CdtDbtInd>CRDT</CdtDbtInd>
    				<Dt>
    					<Dt>2020-11-25</Dt>
    				</Dt>
    			</Bal>
    			<Ntry>
    				<NtryRef>41100000000872849</NtryRef>
    				<Amt Ccy="CHF">4.50</Amt>
    				<CdtDbtInd>CRDT</CdtDbtInd>
    				<RvslInd>false</RvslInd>
    				<Sts>
    					<Cd>BOOK</Cd>
    				</Sts>
    				<BookgDt>
    					<Dt>2020-11-24</Dt>
    				</BookgDt>
    				<ValDt>
    					<Dt>2020-11-24</Dt>
    				</ValDt>
    				<AcctSvcrRef>0758103031480900</AcctSvcrRef>
    				<BkTxCd>
    					<Domn>
    						<Cd>PMNT</Cd>
    						<Fmly>
    							<Cd>IDDT</Cd>
    							<SubFmlyCd>PMDD</SubFmlyCd>
    						</Fmly>
    					</Domn>
    				</BkTxCd>
    				<NtryDtls>
    					<TxDtls>
    						<Refs>
    							<AcctSvcrRef>0758103031480900</AcctSvcrRef>
    						</Refs>
    						<Amt Ccy="CHF">4.50</Amt>
    						<CdtDbtInd>CRDT</CdtDbtInd>
    					</TxDtls>
    				</NtryDtls>
    				<AddtlNtryInf>GUTSCHRIFT CH-DD-BASISLASTSCHRIFT ID-NR. DES ZAHLUNGSEMPFÄNGERS: 41100000000872849 REFERENZ-NR: PmtInfId-CHDD-1</AddtlNtryInf>
    			</Ntry>
    			<Ntry>
    				<Amt Ccy="CHF">77.70</Amt>
    				<CdtDbtInd>DBIT</CdtDbtInd>
    				<RvslInd>false</RvslInd>
    				<Sts>
    					<Cd>BOOK</Cd>
    				</Sts>
    				<BookgDt>
    					<Dt>2020-11-24</Dt>
    				</BookgDt>
    				<ValDt>
    					<Dt>2020-11-24</Dt>
    				</ValDt>
    				<AcctSvcrRef>0758103031480977</AcctSvcrRef>
    				<BkTxCd>
    					<Domn>
    						<Cd>PMNT</Cd>
    						<Fmly>
    							<Cd>ICDT</Cd>
    							<SubFmlyCd>AUTT</SubFmlyCd>
    						</Fmly>
    					</Domn>
    				</BkTxCd>
    				<NtryDtls>
    					<TxDtls>
    						<Refs>
    							<AcctSvcrRef>0758103031480977</AcctSvcrRef>
    						</Refs>
    						<Amt Ccy="CHF">77.70</Amt>
    						<CdtDbtInd>CRDT</CdtDbtInd>
    					</TxDtls>
    				</NtryDtls>
    				<AddtlNtryInf>GUTSCHRIFT CH-DD-BASISLASTSCHRIFT ID-NR. DES ZAHLUNGSEMPFÄNGERS: 41100000000872849 REFERENZ-NR: PmtInfId-CHDD-1</AddtlNtryInf>
    			</Ntry>
    			<Ntry>
    				<Amt Ccy="CHF">1500.00</Amt>
    				<CdtDbtInd>CRDT</CdtDbtInd>
    				<RvslInd>false</RvslInd>
    				<Sts>
    					<Cd>BOOK</Cd>
    				</Sts>
    				<BookgDt>
    					<Dt>2020-11-24</Dt>
    				</BookgDt>
    				<ValDt>
    					<Dt>2020-11-24</Dt>
    				</ValDt>
    				<AcctSvcrRef>07582000303F0C7U</AcctSvcrRef>
    				<BkTxCd>
    					<Domn>
    						<Cd>PMNT</Cd>
    						<Fmly>
    							<Cd>RCDT</Cd>
    							<SubFmlyCd>AUTT</SubFmlyCd>
    						</Fmly>
    					</Domn>
    				</BkTxCd>
    				<NtryDtls>
    					<TxDtls>
    						<Refs>
    							<AcctSvcrRef>07582000303F0C7U</AcctSvcrRef>
    						</Refs>
    						<Amt Ccy="CHF">1500.00</Amt>
    						<CdtDbtInd>CRDT</CdtDbtInd>
    					</TxDtls>
    				</NtryDtls>
    				<AddtlNtryInf>SAMMELGUTSCHRIFT FÜR KONTO: CH2909000000250094239 VERARBEITUNG VOM 23.11.2020 PAKET ID: 9999999999999999</AddtlNtryInf>
    			</Ntry>
    			<Ntry>
    				<NtryRef>CH2909000000250094239</NtryRef>
    				<Amt Ccy="CHF">1537.00</Amt>
    				<CdtDbtInd>CRDT</CdtDbtInd>
    				<RvslInd>false</RvslInd>
    				<Sts>
    					<Cd>BOOK</Cd>
    				</Sts>
    				<BookgDt>
    					<Dt>2020-11-24</Dt>
    				</BookgDt>
    				<ValDt>
    					<Dt>2020-11-25</Dt>
    				</ValDt>
    				<AcctSvcrRef>07582000303KBMSU</AcctSvcrRef>
    				<BkTxCd>
    					<Domn>
    						<Cd>PMNT</Cd>
    						<Fmly>
    							<Cd>RCDT</Cd>
    							<SubFmlyCd>VCOM</SubFmlyCd>
    						</Fmly>
    					</Domn>
    				</BkTxCd>
    				<NtryDtls>
    					<TxDtls>
    						<Refs>
    							<AcctSvcrRef>07582000303KBMSU</AcctSvcrRef>
    						</Refs>
    						<Amt Ccy="CHF">1537.00</Amt>
    						<CdtDbtInd>CRDT</CdtDbtInd>
    					</TxDtls>
    				</NtryDtls>
    				<AddtlNtryInf>SAMMELGUTSCHRIFT FÜR KONTO: CH2909000000250094239 VERARBEITUNG VOM 24.11.2020 PAKET ID: 9999999999999998</AddtlNtryInf>
    			</Ntry>
    			<Ntry>
    				<NtryRef>CH7730000001250094239</NtryRef>
    				<Amt Ccy="CHF">1692.22</Amt>
    				<CdtDbtInd>CRDT</CdtDbtInd>
    				<RvslInd>false</RvslInd>
    				<Sts>
    					<Cd>BOOK</Cd>
    				</Sts>
    				<BookgDt>
    					<Dt>2020-11-24</Dt>
    				</BookgDt>
    				<ValDt>
    					<Dt>2020-11-24</Dt>
    				</ValDt>
    				<AcctSvcrRef>07582000303KBMSU</AcctSvcrRef>
    				<BkTxCd>
    					<Domn>
    						<Cd>PMNT</Cd>
    						<Fmly>
    							<Cd>RCDT</Cd>
    							<SubFmlyCd>VCOM</SubFmlyCd>
    						</Fmly>
    					</Domn>
    				</BkTxCd>
    				<NtryDtls>
    					<TxDtls>
    						<Refs>
    							<AcctSvcrRef>07582000303KBMSU</AcctSvcrRef>
    						</Refs>
    						<Amt Ccy="CHF">1692.22</Amt>
    						<CdtDbtInd>CRDT</CdtDbtInd>
    					</TxDtls>
    				</NtryDtls>
    				<AddtlNtryInf>SAMMELGUTSCHRIFT FÜR KONTO: CH7730000001250094239 VERARBEITUNG VOM 24.11.2020 PAKET ID: 201124CH000009TB</AddtlNtryInf>
    			</Ntry>
    		</Stmt>
    	</BkToCstmrStmt>
    </Document>
    """
    And the C_BankStatement identified by bs_pf2020_off is completed
    Then validate imported C_BankStatement
      | C_BankStatement_ID.Identifier | LineCount | BeginningBalance | EndingBalance | StatementDifference | DocStatus | IsReconciled | StatementDate |
      | bs_pf2020_off                 | 5         | 211993.19        | 216649.21     | 4656.02             | CO        | false        | 2020-11-25    |
    And validate imported C_BankStatementLine
      | C_BankStatement_ID.Identifier | Line | TrxAmt  | StmtAmt | StatementLineDate | ImportedBillPartnerName | ReferenceNo      | C_Invoice.DocumentNo | C_BPartner.Name | C_Payment.PayAmt | IsReconciled |
      | bs_pf2020_off                 | 10   | 4.5     | 4.5     | 2020-11-24        | <none>                  | 0758103031480900 | <none>               | <none>          | <none>           | false        |
      | bs_pf2020_off                 | 20   | -77.7   | -77.7   | 2020-11-24        | <none>                  | 0758103031480977 | <none>               | <none>          | <none>           | false        |
      | bs_pf2020_off                 | 30   | 1500    | 1500    | 2020-11-24        | <none>                  | 07582000303F0C7U | <none>               | <none>          | <none>           | false        |
      | bs_pf2020_off                 | 40   | 1537    | 1537    | 2020-11-25        | <none>                  | 07582000303KBMSU | <none>               | <none>          | <none>           | false        |
      | bs_pf2020_off                 | 50   | 1692.22 | 1692.22 | 2020-11-24        | <none>                  | 07582000303KBMSU | <none>               | <none>          | <none>           | false        |

  @from:cucumber
@allure.label.epic:E0220_Financial
@allure.label.feature:F01100_Statistik_nach_Mengen_Gesamt
@F01100
  @Id:S0337_920
  Scenario: camt.53.001.08 parity - PostFinance sample 2024-01-25, Import As Summary Line set

    Given set sys config boolean value true for sys config de.metas.payment.esr.Enabled
    And load C_DataImport:
      | C_DataImport_ID.Identifier | OPT.C_DataImport_ID |
      | di_pf2024_on               | 540009              |
    And metasfresh contains C_Bank:
      | C_Bank_ID.Identifier | Name           | RoutingNo | SwiftCode   | C_DataImport_ID.Identifier | OPT.IsImportAsSingleSummaryLine |
      | b_pf2024_on          | bank_pf2024_on | 2234567   | AAAAAAAA82A | di_pf2024_on               | true                            |
    And load C_BP_BankAccount:
      | C_BP_BankAccount_ID.Identifier | OPT.C_BP_BankAccount_ID |
      | bpb_pf2024_on                  | 2000257                 |
    And update C_BP_BankAccount:
      | C_BP_BankAccount_ID.Identifier | OPT.C_Currency.ISO_Code | OPT.IsEsrAccount | OPT.AccountNo | OPT.ESR_RenderedAccountNo | OPT.IBAN              | OPT.C_Bank_ID.Identifier |
      | bpb_pf2024_on                  | CHF                     | Y                | 1234567890    | 123456789                 | CH2909000000250094239 | b_pf2024_on              |
    When bank statement is imported with identifiers bs_pf2024_on, matching invoice amounts
    """
    <?xml version="1.0" encoding="UTF-8"?>
    <Document xmlns="urn:iso:std:iso:20022:tech:xsd:camt.053.001.08" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="urn:iso:std:iso:20022:tech:xsd:camt.053.001.08 camt.053.001.08.xsd">
    	<BkToCstmrStmt>
    		<GrpHdr>
    			<MsgId>20240124375204229003967</MsgId>
    			<CreDtTm>2024-01-25T02:02:16</CreDtTm>
    			<MsgPgntn>
    				<PgNb>1</PgNb>
    				<LastPgInd>true</LastPgInd>
    			</MsgPgntn>
    			<AddtlInf>SPS/2.0/PROD</AddtlInf>
    		</GrpHdr>
    		<Stmt>
    			<Id>20240124375204229003973</Id>
    			<ElctrncSeqNb>16</ElctrncSeqNb>
    			<CreDtTm>2024-01-25T02:02:16</CreDtTm>
    			<FrToDt>
    				<FrDtTm>2024-01-24T00:00:00</FrDtTm>
    				<ToDtTm>2024-01-24T23:59:59</ToDtTm>
    			</FrToDt>
    			<Acct>
    				<Id>
    					<IBAN>CH2909000000250094239</IBAN>
    				</Id>
    				<Ccy>CHF</Ccy>
    				<Ownr>
    					<Nm>Robert Schneider SA Grands magasins Biel/Bienne</Nm>
    				</Ownr>
    			</Acct>
    			<Bal>
    				<Tp>
    					<CdOrPrtry>
    						<Cd>OPBD</Cd>
    					</CdOrPrtry>
    				</Tp>
    				<Amt Ccy="CHF">11993.19</Amt>
    				<CdtDbtInd>CRDT</CdtDbtInd>
    				<Dt>
    					<Dt>2024-01-24</Dt>
    				</Dt>
    			</Bal>
    			<Bal>
    				<Tp>
    					<CdOrPrtry>
    						<Cd>CLBD</Cd>
    					</CdOrPrtry>
    				</Tp>
    				<Amt Ccy="CHF">14185.91</Amt>
    				<CdtDbtInd>CRDT</CdtDbtInd>
    				<Dt>
    					<Dt>2024-01-24</Dt>
    				</Dt>
    			</Bal>
    			<Bal>
    				<Tp>
    					<CdOrPrtry>
    						<Cd>CLAV</Cd>
    					</CdOrPrtry>
    				</Tp>
    				<Amt Ccy="CHF">14185.91</Amt>
    				<CdtDbtInd>CRDT</CdtDbtInd>
    				<Dt>
    					<Dt>2024-01-24</Dt>
    				</Dt>
    			</Bal>
    			<Ntry>
    				<NtryRef>CH7730000001250094239</NtryRef>
    				<Amt Ccy="CHF">500.50</Amt>
    				<CdtDbtInd>CRDT</CdtDbtInd>
    				<RvslInd>false</RvslInd>
    				<Sts>
    					<Cd>BOOK</Cd>
    				</Sts>
    				<BookgDt>
    					<Dt>2024-01-24</Dt>
    				</BookgDt>
    				<ValDt>
    					<Dt>2024-01-24</Dt>
    				</ValDt>
    				<AcctSvcrRef>1000000000000000</AcctSvcrRef>
    				<BkTxCd>
    					<Domn>
    						<Cd>PMNT</Cd>
    						<Fmly>
    							<Cd>RRCT</Cd>
    							<SubFmlyCd>VCOM</SubFmlyCd>
    						</Fmly>
    					</Domn>
    				</BkTxCd>
    				<NtryDtls>
    					<TxDtls>
    						<Refs>
    							<AcctSvcrRef>1000000000000000</AcctSvcrRef>
    						</Refs>
    						<Amt Ccy="CHF">500.50</Amt>
    						<CdtDbtInd>CRDT</CdtDbtInd>
    					</TxDtls>
    				</NtryDtls>
    				<AddtlNtryInf>GUTSCHRIFT INSTANT-ZAHLUNG AUFTRAGGEBER: MARIA BERNASCONI PLACE DE LA GARE 12 2502 BIEL/BIENNE ZAHLUNGSKONTO: CH7730000001250094239 REFERENZEN: 000000000000000000000000105 NOTPROVIDED 1006265-25BBB3B1A CH202312080001Y</AddtlNtryInf>
    			</Ntry>
    			<Ntry>
    				<NtryRef>CH7730000001250094239</NtryRef>
    				<Amt Ccy="CHF">1692.22</Amt>
    				<CdtDbtInd>CRDT</CdtDbtInd>
    				<RvslInd>false</RvslInd>
    				<Sts>
    					<Cd>BOOK</Cd>
    				</Sts>
    				<BookgDt>
    					<Dt>2024-01-24</Dt>
    				</BookgDt>
    				<ValDt>
    					<Dt>2024-01-24</Dt>
    				</ValDt>
    				<AcctSvcrRef>3000000000000000</AcctSvcrRef>
    				<BkTxCd>
    					<Domn>
    						<Cd>PMNT</Cd>
    						<Fmly>
    							<Cd>RCDT</Cd>
    							<SubFmlyCd>VCOM</SubFmlyCd>
    						</Fmly>
    					</Domn>
    				</BkTxCd>
    				<NtryDtls>
    					<TxDtls>
    						<Refs>
    							<AcctSvcrRef>3000000000000000</AcctSvcrRef>
    						</Refs>
    						<Amt Ccy="CHF">1692.22</Amt>
    						<CdtDbtInd>CRDT</CdtDbtInd>
    					</TxDtls>
    				</NtryDtls>
    				<AddtlNtryInf>SAMMELGUTSCHRIFT FÜR KONTO: CH7730000001250094239 VERARBEITUNG VOM 24.01.2024 PAKET ID: 240124CH000009TB</AddtlNtryInf>
    			</Ntry>
    		</Stmt>
    	</BkToCstmrStmt>
    </Document>
    """
    And the C_BankStatement identified by bs_pf2024_on is completed
    Then validate imported C_BankStatement
      | C_BankStatement_ID.Identifier | LineCount | BeginningBalance | EndingBalance | StatementDifference | DocStatus | IsReconciled | StatementDate |
      | bs_pf2024_on                  | 3         | 11993.19         | 14185.91      | 2192.72             | CO        | false        | 2024-01-25    |
    And validate imported C_BankStatementLine
      | C_BankStatement_ID.Identifier | Line | TrxAmt  | StmtAmt | StatementLineDate | ImportedBillPartnerName | ReferenceNo      | C_Invoice.DocumentNo | C_BPartner.Name | C_Payment.PayAmt | IsReconciled |
      | bs_pf2024_on                  | 10   | 0       | 0       | 2024-01-25        | <none>                  | <none>           | <none>               | <none>          | <none>           | false        |
      | bs_pf2024_on                  | 20   | 500.5   | 500.5   | 2024-01-24        | <none>                  | 1000000000000000 | <none>               | <none>          | <none>           | false        |
      | bs_pf2024_on                  | 30   | 1692.22 | 1692.22 | 2024-01-24        | <none>                  | 3000000000000000 | <none>               | <none>          | <none>           | false        |

  @from:cucumber
@allure.label.epic:E0220_Financial
@allure.label.feature:F01100_Statistik_nach_Mengen_Gesamt
@F01100
  @Id:S0337_930
  Scenario: camt.53.001.08 parity - PostFinance sample 2024-01-25, Import As Summary Line not set

    Given set sys config boolean value true for sys config de.metas.payment.esr.Enabled
    And load C_DataImport:
      | C_DataImport_ID.Identifier | OPT.C_DataImport_ID |
      | di_pf2024_off              | 540009              |
    And metasfresh contains C_Bank:
      | C_Bank_ID.Identifier | Name            | RoutingNo | SwiftCode   | C_DataImport_ID.Identifier | OPT.IsImportAsSingleSummaryLine |
      | b_pf2024_off         | bank_pf2024_off | 2234567   | AAAAAAAA82A | di_pf2024_off              | false                           |
    And load C_BP_BankAccount:
      | C_BP_BankAccount_ID.Identifier | OPT.C_BP_BankAccount_ID |
      | bpb_pf2024_off                 | 2000257                 |
    And update C_BP_BankAccount:
      | C_BP_BankAccount_ID.Identifier | OPT.C_Currency.ISO_Code | OPT.IsEsrAccount | OPT.AccountNo | OPT.ESR_RenderedAccountNo | OPT.IBAN              | OPT.C_Bank_ID.Identifier |
      | bpb_pf2024_off                 | CHF                     | Y                | 1234567890    | 123456789                 | CH2909000000250094239 | b_pf2024_off             |
    When bank statement is imported with identifiers bs_pf2024_off, matching invoice amounts
    """
    <?xml version="1.0" encoding="UTF-8"?>
    <Document xmlns="urn:iso:std:iso:20022:tech:xsd:camt.053.001.08" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="urn:iso:std:iso:20022:tech:xsd:camt.053.001.08 camt.053.001.08.xsd">
    	<BkToCstmrStmt>
    		<GrpHdr>
    			<MsgId>20240124375204229003967</MsgId>
    			<CreDtTm>2024-01-25T02:02:16</CreDtTm>
    			<MsgPgntn>
    				<PgNb>1</PgNb>
    				<LastPgInd>true</LastPgInd>
    			</MsgPgntn>
    			<AddtlInf>SPS/2.0/PROD</AddtlInf>
    		</GrpHdr>
    		<Stmt>
    			<Id>20240124375204229003973</Id>
    			<ElctrncSeqNb>16</ElctrncSeqNb>
    			<CreDtTm>2024-01-25T02:02:16</CreDtTm>
    			<FrToDt>
    				<FrDtTm>2024-01-24T00:00:00</FrDtTm>
    				<ToDtTm>2024-01-24T23:59:59</ToDtTm>
    			</FrToDt>
    			<Acct>
    				<Id>
    					<IBAN>CH2909000000250094239</IBAN>
    				</Id>
    				<Ccy>CHF</Ccy>
    				<Ownr>
    					<Nm>Robert Schneider SA Grands magasins Biel/Bienne</Nm>
    				</Ownr>
    			</Acct>
    			<Bal>
    				<Tp>
    					<CdOrPrtry>
    						<Cd>OPBD</Cd>
    					</CdOrPrtry>
    				</Tp>
    				<Amt Ccy="CHF">11993.19</Amt>
    				<CdtDbtInd>CRDT</CdtDbtInd>
    				<Dt>
    					<Dt>2024-01-24</Dt>
    				</Dt>
    			</Bal>
    			<Bal>
    				<Tp>
    					<CdOrPrtry>
    						<Cd>CLBD</Cd>
    					</CdOrPrtry>
    				</Tp>
    				<Amt Ccy="CHF">14185.91</Amt>
    				<CdtDbtInd>CRDT</CdtDbtInd>
    				<Dt>
    					<Dt>2024-01-24</Dt>
    				</Dt>
    			</Bal>
    			<Bal>
    				<Tp>
    					<CdOrPrtry>
    						<Cd>CLAV</Cd>
    					</CdOrPrtry>
    				</Tp>
    				<Amt Ccy="CHF">14185.91</Amt>
    				<CdtDbtInd>CRDT</CdtDbtInd>
    				<Dt>
    					<Dt>2024-01-24</Dt>
    				</Dt>
    			</Bal>
    			<Ntry>
    				<NtryRef>CH7730000001250094239</NtryRef>
    				<Amt Ccy="CHF">500.50</Amt>
    				<CdtDbtInd>CRDT</CdtDbtInd>
    				<RvslInd>false</RvslInd>
    				<Sts>
    					<Cd>BOOK</Cd>
    				</Sts>
    				<BookgDt>
    					<Dt>2024-01-24</Dt>
    				</BookgDt>
    				<ValDt>
    					<Dt>2024-01-24</Dt>
    				</ValDt>
    				<AcctSvcrRef>1000000000000000</AcctSvcrRef>
    				<BkTxCd>
    					<Domn>
    						<Cd>PMNT</Cd>
    						<Fmly>
    							<Cd>RRCT</Cd>
    							<SubFmlyCd>VCOM</SubFmlyCd>
    						</Fmly>
    					</Domn>
    				</BkTxCd>
    				<NtryDtls>
    					<TxDtls>
    						<Refs>
    							<AcctSvcrRef>1000000000000000</AcctSvcrRef>
    						</Refs>
    						<Amt Ccy="CHF">500.50</Amt>
    						<CdtDbtInd>CRDT</CdtDbtInd>
    					</TxDtls>
    				</NtryDtls>
    				<AddtlNtryInf>GUTSCHRIFT INSTANT-ZAHLUNG AUFTRAGGEBER: MARIA BERNASCONI PLACE DE LA GARE 12 2502 BIEL/BIENNE ZAHLUNGSKONTO: CH7730000001250094239 REFERENZEN: 000000000000000000000000105 NOTPROVIDED 1006265-25BBB3B1A CH202312080001Y</AddtlNtryInf>
    			</Ntry>
    			<Ntry>
    				<NtryRef>CH7730000001250094239</NtryRef>
    				<Amt Ccy="CHF">1692.22</Amt>
    				<CdtDbtInd>CRDT</CdtDbtInd>
    				<RvslInd>false</RvslInd>
    				<Sts>
    					<Cd>BOOK</Cd>
    				</Sts>
    				<BookgDt>
    					<Dt>2024-01-24</Dt>
    				</BookgDt>
    				<ValDt>
    					<Dt>2024-01-24</Dt>
    				</ValDt>
    				<AcctSvcrRef>3000000000000000</AcctSvcrRef>
    				<BkTxCd>
    					<Domn>
    						<Cd>PMNT</Cd>
    						<Fmly>
    							<Cd>RCDT</Cd>
    							<SubFmlyCd>VCOM</SubFmlyCd>
    						</Fmly>
    					</Domn>
    				</BkTxCd>
    				<NtryDtls>
    					<TxDtls>
    						<Refs>
    							<AcctSvcrRef>3000000000000000</AcctSvcrRef>
    						</Refs>
    						<Amt Ccy="CHF">1692.22</Amt>
    						<CdtDbtInd>CRDT</CdtDbtInd>
    					</TxDtls>
    				</NtryDtls>
    				<AddtlNtryInf>SAMMELGUTSCHRIFT FÜR KONTO: CH7730000001250094239 VERARBEITUNG VOM 24.01.2024 PAKET ID: 240124CH000009TB</AddtlNtryInf>
    			</Ntry>
    		</Stmt>
    	</BkToCstmrStmt>
    </Document>
    """
    And the C_BankStatement identified by bs_pf2024_off is completed
    Then validate imported C_BankStatement
      | C_BankStatement_ID.Identifier | LineCount | BeginningBalance | EndingBalance | StatementDifference | DocStatus | IsReconciled | StatementDate |
      | bs_pf2024_off                 | 2         | 11993.19         | 14185.91      | 2192.72             | CO        | false        | 2024-01-25    |
    And validate imported C_BankStatementLine
      | C_BankStatement_ID.Identifier | Line | TrxAmt  | StmtAmt | StatementLineDate | ImportedBillPartnerName | ReferenceNo      | C_Invoice.DocumentNo | C_BPartner.Name | C_Payment.PayAmt | IsReconciled |
      | bs_pf2024_off                 | 10   | 500.5   | 500.5   | 2024-01-24        | <none>                  | 1000000000000000 | <none>               | <none>          | <none>           | false        |
      | bs_pf2024_off                 | 20   | 1692.22 | 1692.22 | 2024-01-24        | <none>                  | 3000000000000000 | <none>               | <none>          | <none>           | false        |
