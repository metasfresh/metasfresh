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
## Expected values are derived from the requirements (the pain.008.001.08 GBIC_5 schema and the text conversion to the basic SEPA character set and maximum lengths), not from the writer's output.
##
## Data notes:
## - Every scenario runs in its own organization (created in the Background) with its own org BP, which is the creditor; no seed data is changed.
## - The creditor address is the org BP's default bill-to location (the Background creates it; the creditor-address scenario re-points it).
## - Debtor BPs get a run-unique Value (@Date@) so that each scenario has its own bank account, which the pay selection line then points to.
## - The debtor address is the bill-to address captured on the invoice. A debtor created with "C_BPartners:" gets a default German location without town;
##   the format requires a town in an address, so such a debtor is written without Dbtr/PstlAdr (the debtor-side scenario has debtors with a full address).
## - Each export scenario compares the whole file with the expected one; ${ignore} marks the values that differ from run to run:
##   MsgId (the SEPA_Export DocumentNo) and PmtInfId / EndToEndId, which are derived from it.

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
      | Identifier | M_PricingSystem_ID | C_Country.CountryCode | C_Currency.ISO_Code | Name       | SOTrx | IsTaxIncluded | PricePrecision |
      | pl_sales   | pricingSystem      | DE                    | EUR                 | sepa-dd-PL | true  | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID | Name        | ValidFrom  |
      | plv_sales  | pl_sales       | sepa-dd-plv | 2026-01-01 |
    And metasfresh contains M_ProductPrices
      | Identifier   | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID |
      | productPrice | plv_sales              | product      | 10.0     | PCE               | taxCategory      |

    # --- creditor: the org BP of that organization (name, address), org bank account with BIC and creditor id ---
    And metasfresh contains C_BPartners without locations:
      | Identifier | Name                    | Value           | IsCustomer | AD_OrgBP_ID |
      | creditorBP | SEPA Test Creditor GmbH | creditor_@Date@ | N          | sellerOrg   |
    And metasfresh contains C_Location:
      | C_Location_ID.Identifier | CountryCode | OPT.Address1    | OPT.Postal | OPT.City   |
      | creditorLoc              | DE          | Musterstrasse 1 | 53111      | Testhausen |
    And metasfresh contains C_BPartner_Locations:
      | Identifier  | GLN           | C_BPartner_ID | OPT.C_Location_ID.Identifier | OPT.IsBillTo | OPT.IsBillToDefault |
      | creditorBPL | 4000000000129 | creditorBP    | creditorLoc                  | Y            | Y                   |
    # the org info is needed for the invoice tax lookup (country of the org)
    And metasfresh contains AD_OrgInfo:
      | AD_OrgInfo_ID.Identifier | AD_Org_ID.Identifier | Org_BPartner_ID.Identifier | OrgBP_Location_ID.Identifier |
      | creditorOrgInfo          | sellerOrg            | creditorBP                 | creditorBPL                  |
    And metasfresh contains C_Bank:
      | C_Bank_ID    | Name                    | RoutingNo | SwiftCode   |
      | creditorBank | SEPA Test Creditor Bank | 10000000  | TESTDEC0XXX |
    And metasfresh contains C_BP_BankAccount
      | Identifier | C_BPartner_ID | C_Currency_ID | IBAN                   | C_Bank_ID    | SEPA_CreditorIdentifier |
      | orgAcct    | creditorBP    | EUR           | DE91100000000123456789 | creditorBank | DE98ZZZ09999999999      |

  @from:cucumber
  @Id:S32708_TC1
  Scenario: schemes and sequence types: one ZIP with a B2B and a CORE file
    - one pay selection with debtors of both schemes: the export writes a ZIP with one file per scheme, each with its own totals
    - B2B: a recurring mandate with 2 invoices -> 2 transactions with RCUR; the mandate's DateLastUsed is set to the collection date
    - CORE: a recurring mandate -> RCUR; a mandate that is not recurring and was never used -> OOFF
    Given metasfresh contains C_BPartners:
      | Identifier   | Name                  | Value         | IsCustomer | M_PricingSystem_ID | PaymentRule |
      | b2bDebtor    | Debtor B2B Recurring  | b2b_@Date@    | Y          | pricingSystem      | D           |
      | coreDebtor   | Debtor CORE Recurring | core_@Date@   | Y          | pricingSystem      | D           |
      | oneOffDebtor | Debtor CORE OneOff    | oneoff_@Date@ | Y          | pricingSystem      | D           |
    And metasfresh contains C_Bank:
      | C_Bank_ID | Name                  | RoutingNo | SwiftCode   |
      | b2bBank   | SEPA Debtor Bank B2B  | 20000000  | TESTDEB2XXX |
      | coreBank  | SEPA Debtor Bank CORE | 30000000  | TESTDEC1XXX |
    And metasfresh contains C_BP_BankAccount
      | Identifier | C_BPartner_ID | C_Currency_ID | IBAN                   | C_Bank_ID |
      | b2bAcct    | b2bDebtor     | EUR           | DE46200000001000000001 | b2bBank   |
      | coreAcct   | coreDebtor    | EUR           | DE60300000001000000002 | coreBank  |
      | oneOffAcct | oneOffDebtor  | EUR           | DE33300000001000000003 | coreBank  |
    And metasfresh contains C_BP_BankAccount_DirectDebitMandate:
      | C_BP_BankAccount_DirectDebitMandate_ID | C_BP_BankAccount_ID | MandateType | MandateReference | MandateDate | IsRecurring | DateLastUsed | AD_Org_ID |
      | b2bMandate                             | b2bAcct             | B2B         | MREF-B2B-001     | 2013-09-23  | Y           | 2025-09-04   | sellerOrg |
      | coreMandate                            | coreAcct            | CORE        | MREF-CORE-001    | 2014-03-11  | Y           | 2025-09-04   | sellerOrg |
    # never used: no DateLastUsed
    And metasfresh contains C_BP_BankAccount_DirectDebitMandate:
      | C_BP_BankAccount_DirectDebitMandate_ID | C_BP_BankAccount_ID | MandateType | MandateReference | MandateDate | IsRecurring | AD_Org_ID |
      | oneOffMandate                          | oneOffAcct          | CORE        | MREF-OOFF-001    | 2026-10-01  | N           | sellerOrg |
    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | IsSOTrx | DateInvoiced | C_Currency_ID | C_PaymentTerm_ID | PaymentRule | AD_Org_ID |
      | b2bInv1    | b2bDebtor     | Y       | 2026-10-28   | EUR           | pt_sepa          | D           | sellerOrg |
      | b2bInv2    | b2bDebtor     | Y       | 2026-10-28   | EUR           | pt_sepa          | D           | sellerOrg |
      | coreInv    | coreDebtor    | Y       | 2026-10-28   | EUR           | pt_sepa          | D           | sellerOrg |
      | oneOffInv  | oneOffDebtor  | Y       | 2026-10-28   | EUR           | pt_sepa          | D           | sellerOrg |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced | Price  | C_Tax_ID |
      | b2bInvL1   | b2bInv1      | product      | 1 PCE       | 100.00 | zeroTax  |
      | b2bInvL2   | b2bInv2      | product      | 1 PCE       | 50.00  | zeroTax  |
      | coreInvL   | coreInv      | product      | 1 PCE       | 80.00  | zeroTax  |
      | oneOffInvL | oneOffInv    | product      | 1 PCE       | 30.00  | zeroTax  |
    And update C_Invoice:
      | Identifier | Description    |
      | b2bInv1    | Invoice B2B 1  |
      | b2bInv2    | Invoice B2B 2  |
      | coreInv    | Invoice CORE 1 |
      | oneOffInv  | Invoice OOFF 1 |
    And the invoice identified by b2bInv1 is completed
    And the invoice identified by b2bInv2 is completed
    And the invoice identified by coreInv is completed
    And the invoice identified by oneOffInv is completed
    And metasfresh contains Pay Selection
      | Identifier          | C_BP_BankAccount_ID | PaySelectionTrxType | PayDate    | AD_Org_ID |
      | schemesPaySelection | orgAcct             | DD                  | 2026-11-02 | sellerOrg |
    # one "Create from..." per debtor, so that invoices of other tests on the same DB are not picked up
    And "Create from..." is invoked for pay selection schemesPaySelection, using following parameters:
      | MatchRequirement | C_BPartner_ID | PayDate    |
      | CDD              | b2bDebtor     | 2026-11-02 |
    And "Create from..." is invoked for pay selection schemesPaySelection, using following parameters:
      | MatchRequirement | C_BPartner_ID | PayDate    |
      | CDD              | coreDebtor    | 2026-11-02 |
    And "Create from..." is invoked for pay selection schemesPaySelection, using following parameters:
      | MatchRequirement | C_BPartner_ID | PayDate    |
      | CDD              | oneOffDebtor  | 2026-11-02 |
    And the pay selection identified by schemesPaySelection is completed
    And the Pay selection identified by schemesPaySelection has exactly the following lines
      | C_Invoice_ID | OpenAmt | PayAmt | C_BP_BankAccount_ID |
      | b2bInv1      | 100.00  | 100.00 | b2bAcct             |
      | b2bInv2      | 50.00   | 50.00  | b2bAcct             |
      | coreInv      | 80.00   | 80.00  | coreAcct            |
      | oneOffInv    | 30.00   | 30.00  | oneOffAcct          |

    When the SEPA export process is run for pay selection "schemesPaySelection"

    Then the SEPA export result is a ZIP with 2 XML files
    And the pay selection "schemesPaySelection" has SEPA_Export records:
      | SEPA_Protocol   | NumberOfLines |
      | pain.008.001.08 | 2             |
      | pain.008.001.08 | 2             |
    And the SEPA direct debit file for scheme B2B is:
      """
      <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
      <Document xsi:schemaLocation="urn:iso:std:iso:20022:tech:xsd:pain.008.001.08 pain.008.001.08.xsd" xmlns="urn:iso:std:iso:20022:tech:xsd:pain.008.001.08" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
          <CstmrDrctDbtInitn>
              <GrpHdr>
                  <MsgId>${ignore}</MsgId>
                  <CreDtTm>2026-10-28T10:00:00.000+01:00</CreDtTm>
                  <NbOfTxs>2</NbOfTxs>
                  <CtrlSum>150.00</CtrlSum>
                  <InitgPty>
                      <Nm>SEPA Test Creditor GmbH</Nm>
                  </InitgPty>
              </GrpHdr>
              <PmtInf>
                  <PmtInfId>${ignore}</PmtInfId>
                  <PmtMtd>DD</PmtMtd>
                  <NbOfTxs>1</NbOfTxs>
                  <CtrlSum>100.00</CtrlSum>
                  <PmtTpInf>
                      <SvcLvl>
                          <Cd>SEPA</Cd>
                      </SvcLvl>
                      <LclInstrm>
                          <Cd>B2B</Cd>
                      </LclInstrm>
                      <SeqTp>RCUR</SeqTp>
                  </PmtTpInf>
                  <ReqdColltnDt>2026-11-02</ReqdColltnDt>
                  <Cdtr>
                      <Nm>SEPA Test Creditor GmbH</Nm>
                      <PstlAdr>
                          <StrtNm>Musterstrasse 1</StrtNm>
                          <PstCd>53111</PstCd>
                          <TwnNm>Testhausen</TwnNm>
                          <Ctry>DE</Ctry>
                      </PstlAdr>
                  </Cdtr>
                  <CdtrAcct>
                      <Id>
                          <IBAN>DE91100000000123456789</IBAN>
                      </Id>
                  </CdtrAcct>
                  <CdtrAgt>
                      <FinInstnId>
                          <BICFI>TESTDEC0XXX</BICFI>
                      </FinInstnId>
                  </CdtrAgt>
                  <ChrgBr>SLEV</ChrgBr>
                  <CdtrSchmeId>
                      <Id>
                          <PrvtId>
                              <Othr>
                                  <Id>DE98ZZZ09999999999</Id>
                                  <SchmeNm>
                                      <Prtry>SEPA</Prtry>
                                  </SchmeNm>
                              </Othr>
                          </PrvtId>
                      </Id>
                  </CdtrSchmeId>
                  <DrctDbtTxInf>
                      <PmtId>
                          <EndToEndId>${ignore}</EndToEndId>
                      </PmtId>
                      <InstdAmt Ccy="EUR">100.00</InstdAmt>
                      <DrctDbtTx>
                          <MndtRltdInf>
                              <MndtId>MREF-B2B-001</MndtId>
                              <DtOfSgntr>2013-09-23</DtOfSgntr>
                              <AmdmntInd>false</AmdmntInd>
                          </MndtRltdInf>
                      </DrctDbtTx>
                      <DbtrAgt>
                          <FinInstnId>
                              <BICFI>TESTDEB2XXX</BICFI>
                          </FinInstnId>
                      </DbtrAgt>
                      <Dbtr>
                          <Nm>Debtor B2B Recurring</Nm>
                      </Dbtr>
                      <DbtrAcct>
                          <Id>
                              <IBAN>DE46200000001000000001</IBAN>
                          </Id>
                      </DbtrAcct>
                      <RmtInf>
                          <Ustrd>Invoice B2B 1</Ustrd>
                      </RmtInf>
                  </DrctDbtTxInf>
              </PmtInf>
              <PmtInf>
                  <PmtInfId>${ignore}</PmtInfId>
                  <PmtMtd>DD</PmtMtd>
                  <NbOfTxs>1</NbOfTxs>
                  <CtrlSum>50.00</CtrlSum>
                  <PmtTpInf>
                      <SvcLvl>
                          <Cd>SEPA</Cd>
                      </SvcLvl>
                      <LclInstrm>
                          <Cd>B2B</Cd>
                      </LclInstrm>
                      <SeqTp>RCUR</SeqTp>
                  </PmtTpInf>
                  <ReqdColltnDt>2026-11-02</ReqdColltnDt>
                  <Cdtr>
                      <Nm>SEPA Test Creditor GmbH</Nm>
                      <PstlAdr>
                          <StrtNm>Musterstrasse 1</StrtNm>
                          <PstCd>53111</PstCd>
                          <TwnNm>Testhausen</TwnNm>
                          <Ctry>DE</Ctry>
                      </PstlAdr>
                  </Cdtr>
                  <CdtrAcct>
                      <Id>
                          <IBAN>DE91100000000123456789</IBAN>
                      </Id>
                  </CdtrAcct>
                  <CdtrAgt>
                      <FinInstnId>
                          <BICFI>TESTDEC0XXX</BICFI>
                      </FinInstnId>
                  </CdtrAgt>
                  <ChrgBr>SLEV</ChrgBr>
                  <CdtrSchmeId>
                      <Id>
                          <PrvtId>
                              <Othr>
                                  <Id>DE98ZZZ09999999999</Id>
                                  <SchmeNm>
                                      <Prtry>SEPA</Prtry>
                                  </SchmeNm>
                              </Othr>
                          </PrvtId>
                      </Id>
                  </CdtrSchmeId>
                  <DrctDbtTxInf>
                      <PmtId>
                          <EndToEndId>${ignore}</EndToEndId>
                      </PmtId>
                      <InstdAmt Ccy="EUR">50.00</InstdAmt>
                      <DrctDbtTx>
                          <MndtRltdInf>
                              <MndtId>MREF-B2B-001</MndtId>
                              <DtOfSgntr>2013-09-23</DtOfSgntr>
                              <AmdmntInd>false</AmdmntInd>
                          </MndtRltdInf>
                      </DrctDbtTx>
                      <DbtrAgt>
                          <FinInstnId>
                              <BICFI>TESTDEB2XXX</BICFI>
                          </FinInstnId>
                      </DbtrAgt>
                      <Dbtr>
                          <Nm>Debtor B2B Recurring</Nm>
                      </Dbtr>
                      <DbtrAcct>
                          <Id>
                              <IBAN>DE46200000001000000001</IBAN>
                          </Id>
                      </DbtrAcct>
                      <RmtInf>
                          <Ustrd>Invoice B2B 2</Ustrd>
                      </RmtInf>
                  </DrctDbtTxInf>
              </PmtInf>
          </CstmrDrctDbtInitn>
      </Document>
      """
    And the SEPA direct debit file for scheme CORE is:
      """
      <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
      <Document xsi:schemaLocation="urn:iso:std:iso:20022:tech:xsd:pain.008.001.08 pain.008.001.08.xsd" xmlns="urn:iso:std:iso:20022:tech:xsd:pain.008.001.08" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
          <CstmrDrctDbtInitn>
              <GrpHdr>
                  <MsgId>${ignore}</MsgId>
                  <CreDtTm>2026-10-28T10:00:00.000+01:00</CreDtTm>
                  <NbOfTxs>2</NbOfTxs>
                  <CtrlSum>110.00</CtrlSum>
                  <InitgPty>
                      <Nm>SEPA Test Creditor GmbH</Nm>
                  </InitgPty>
              </GrpHdr>
              <PmtInf>
                  <PmtInfId>${ignore}</PmtInfId>
                  <PmtMtd>DD</PmtMtd>
                  <NbOfTxs>1</NbOfTxs>
                  <CtrlSum>80.00</CtrlSum>
                  <PmtTpInf>
                      <SvcLvl>
                          <Cd>SEPA</Cd>
                      </SvcLvl>
                      <LclInstrm>
                          <Cd>CORE</Cd>
                      </LclInstrm>
                      <SeqTp>RCUR</SeqTp>
                  </PmtTpInf>
                  <ReqdColltnDt>2026-11-02</ReqdColltnDt>
                  <Cdtr>
                      <Nm>SEPA Test Creditor GmbH</Nm>
                      <PstlAdr>
                          <StrtNm>Musterstrasse 1</StrtNm>
                          <PstCd>53111</PstCd>
                          <TwnNm>Testhausen</TwnNm>
                          <Ctry>DE</Ctry>
                      </PstlAdr>
                  </Cdtr>
                  <CdtrAcct>
                      <Id>
                          <IBAN>DE91100000000123456789</IBAN>
                      </Id>
                  </CdtrAcct>
                  <CdtrAgt>
                      <FinInstnId>
                          <BICFI>TESTDEC0XXX</BICFI>
                      </FinInstnId>
                  </CdtrAgt>
                  <ChrgBr>SLEV</ChrgBr>
                  <CdtrSchmeId>
                      <Id>
                          <PrvtId>
                              <Othr>
                                  <Id>DE98ZZZ09999999999</Id>
                                  <SchmeNm>
                                      <Prtry>SEPA</Prtry>
                                  </SchmeNm>
                              </Othr>
                          </PrvtId>
                      </Id>
                  </CdtrSchmeId>
                  <DrctDbtTxInf>
                      <PmtId>
                          <EndToEndId>${ignore}</EndToEndId>
                      </PmtId>
                      <InstdAmt Ccy="EUR">80.00</InstdAmt>
                      <DrctDbtTx>
                          <MndtRltdInf>
                              <MndtId>MREF-CORE-001</MndtId>
                              <DtOfSgntr>2014-03-11</DtOfSgntr>
                              <AmdmntInd>false</AmdmntInd>
                          </MndtRltdInf>
                      </DrctDbtTx>
                      <DbtrAgt>
                          <FinInstnId>
                              <BICFI>TESTDEC1XXX</BICFI>
                          </FinInstnId>
                      </DbtrAgt>
                      <Dbtr>
                          <Nm>Debtor CORE Recurring</Nm>
                      </Dbtr>
                      <DbtrAcct>
                          <Id>
                              <IBAN>DE60300000001000000002</IBAN>
                          </Id>
                      </DbtrAcct>
                      <RmtInf>
                          <Ustrd>Invoice CORE 1</Ustrd>
                      </RmtInf>
                  </DrctDbtTxInf>
              </PmtInf>
              <PmtInf>
                  <PmtInfId>${ignore}</PmtInfId>
                  <PmtMtd>DD</PmtMtd>
                  <NbOfTxs>1</NbOfTxs>
                  <CtrlSum>30.00</CtrlSum>
                  <PmtTpInf>
                      <SvcLvl>
                          <Cd>SEPA</Cd>
                      </SvcLvl>
                      <LclInstrm>
                          <Cd>CORE</Cd>
                      </LclInstrm>
                      <!-- the mandate is not recurring and was never used -->
                      <SeqTp>OOFF</SeqTp>
                  </PmtTpInf>
                  <ReqdColltnDt>2026-11-02</ReqdColltnDt>
                  <Cdtr>
                      <Nm>SEPA Test Creditor GmbH</Nm>
                      <PstlAdr>
                          <StrtNm>Musterstrasse 1</StrtNm>
                          <PstCd>53111</PstCd>
                          <TwnNm>Testhausen</TwnNm>
                          <Ctry>DE</Ctry>
                      </PstlAdr>
                  </Cdtr>
                  <CdtrAcct>
                      <Id>
                          <IBAN>DE91100000000123456789</IBAN>
                      </Id>
                  </CdtrAcct>
                  <CdtrAgt>
                      <FinInstnId>
                          <BICFI>TESTDEC0XXX</BICFI>
                      </FinInstnId>
                  </CdtrAgt>
                  <ChrgBr>SLEV</ChrgBr>
                  <CdtrSchmeId>
                      <Id>
                          <PrvtId>
                              <Othr>
                                  <Id>DE98ZZZ09999999999</Id>
                                  <SchmeNm>
                                      <Prtry>SEPA</Prtry>
                                  </SchmeNm>
                              </Othr>
                          </PrvtId>
                      </Id>
                  </CdtrSchmeId>
                  <DrctDbtTxInf>
                      <PmtId>
                          <EndToEndId>${ignore}</EndToEndId>
                      </PmtId>
                      <InstdAmt Ccy="EUR">30.00</InstdAmt>
                      <DrctDbtTx>
                          <MndtRltdInf>
                              <MndtId>MREF-OOFF-001</MndtId>
                              <DtOfSgntr>2026-10-01</DtOfSgntr>
                              <AmdmntInd>false</AmdmntInd>
                          </MndtRltdInf>
                      </DrctDbtTx>
                      <DbtrAgt>
                          <FinInstnId>
                              <BICFI>TESTDEC1XXX</BICFI>
                          </FinInstnId>
                      </DbtrAgt>
                      <Dbtr>
                          <Nm>Debtor CORE OneOff</Nm>
                      </Dbtr>
                      <DbtrAcct>
                          <Id>
                              <IBAN>DE33300000001000000003</IBAN>
                          </Id>
                      </DbtrAcct>
                      <RmtInf>
                          <Ustrd>Invoice OOFF 1</Ustrd>
                      </RmtInf>
                  </DrctDbtTxInf>
              </PmtInf>
          </CstmrDrctDbtInitn>
      </Document>
      """
    And validate C_BP_BankAccount_DirectDebitMandate:
      | C_BP_BankAccount_DirectDebitMandate_ID | DateLastUsed |
      | b2bMandate                             | 2026-11-02   |

  @from:cucumber
  @Id:S32708_TC15
  Scenario: debtor-side cases in one B2B file
    One debtor per case, each named after its case (with spaces: an underscore is not in the basic SEPA character set and would become a space):
    - Debtor NoBIC: the debtor bank has no BIC -> DbtrAgt is NOTPROVIDED
      - a full German address -> Dbtr/PstlAdr, converted like the texts below ("Hauptstraße 5" -> "Hauptstrasse 5", "Köln" -> "Koeln")
      - an active mandate of another org on the same bank account -> ignored, the export org's mandate is exported
    - Debtor NoDescription: the invoice has no description -> no RmtInf/Ustrd
    - Debtor OrgStarMandate: only the org-* mandate is active -> its reference is exported, the inactive mandate of the export org is ignored
    - Debtor MandateUsedLater: the mandate was last used after the collection date -> still exported with its own reference, DateLastUsed is not moved back
      - a Swiss debtor (CH IBAN, outside the EEA) whose address has a town -> Dbtr/PstlAdr with town and country
    - text conversion to the basic SEPA character set, cut to the maximum length (expected values derived from the rules, never from the writer's output):
      - MndtId: not converted, exported as it is; a reference outside the SEPA character set is refused (see the refusals feature); "MREF-(A/B)+1.2" uses every allowed special character kind
      - Dbtr.Nm: at most 70 chars; umlauts and ß are transliterated, & and € -> space; the rest over 70 chars is cut
        input    = "Bäcker & Söhne € Großhandel " (28 chars) + 60 x X = 88 chars
        expected = "Baecker   Soehne   Grosshandel " (31 chars; & and € became spaces, so 3 spaces each between the words) + 39 x X = 70 chars
      - Ustrd: at most 140 chars; „ and “ -> space
        input    = "Rechnung „Nr. 1“ " (17 chars) + 140 x Y = 157 chars
        expected = "Rechnung  Nr. 1  " (17 chars; 2 spaces before Nr., 2 after 1) + 123 x Y = 140 chars
    Given load AD_Org:
      | AD_Org_ID.Identifier | Value |
      | orgStar              | 0     |
    # an org other than the export org: its mandates must not be used
    And metasfresh contains AD_Org:
      | AD_Org_ID.Identifier | Name                             |
      | otherOrg             | SEPA direct debit test other org |
    And metasfresh contains C_BPartners:
      | Identifier    | Name                                                                                     | Value          | IsCustomer | M_PricingSystem_ID | PaymentRule |
      | noDescDebtor  | Debtor NoDescription                                                                     | nodesc_@Date@  | Y          | pricingSystem      | D           |
      | longDebtor    | Bäcker & Söhne € Großhandel XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX | long_@Date@    | Y          | pricingSystem      | D           |
      | orgStarDebtor | Debtor OrgStarMandate                                                                    | orgstar_@Date@ | Y          | pricingSystem      | D           |
    And metasfresh contains C_BPartners without locations:
      | Identifier      | Name                    | Value        | IsCustomer | M_PricingSystem_ID | PaymentRule |
      | noBicDebtor     | Debtor NoBIC            | nobic_@Date@ | Y          | pricingSystem      | D           |
      | usedLaterDebtor | Debtor MandateUsedLater | later_@Date@ | Y          | pricingSystem      | D           |
    And metasfresh contains C_Location:
      | C_Location_ID.Identifier | CountryCode | OPT.Address1     | OPT.Postal | OPT.City |
      | noBicLoc                 | DE          | Hauptstraße 5    | 50667      | Köln     |
      | usedLaterLoc             | CH          | Bahnhofstrasse 1 | 8001       | Zürich   |
    And metasfresh contains C_BPartner_Locations:
      | Identifier   | C_BPartner_ID   | OPT.C_Location_ID.Identifier | OPT.IsBillTo | OPT.IsBillToDefault |
      | noBicBPL     | noBicDebtor     | noBicLoc                     | Y            | Y                   |
      | usedLaterBPL | usedLaterDebtor | usedLaterLoc                 | Y            | Y                   |
    And metasfresh contains C_Bank:
      | C_Bank_ID | Name                 | RoutingNo | SwiftCode   |
      | b2bBank   | SEPA Debtor Bank B2B | 20000000  | TESTDEB2XXX |
      | chBank    | SEPA Debtor Bank CH  | 50000000  | TESTCHZZXXX |
    And metasfresh contains C_Bank:
      | C_Bank_ID | Name                         | RoutingNo |
      | noBicBank | SEPA Debtor Bank without BIC | 40000000  |
    And metasfresh contains C_BP_BankAccount
      | Identifier    | C_BPartner_ID   | C_Currency_ID | IBAN                   | C_Bank_ID |
      | noBicAcct     | noBicDebtor     | EUR           | DE47400000001000000004 | noBicBank |
      | noDescAcct    | noDescDebtor    | EUR           | DE35200000001000000005 | b2bBank   |
      | longAcct      | longDebtor      | EUR           | DE08200000001000000006 | b2bBank   |
      | orgStarAcct   | orgStarDebtor   | EUR           | DE51200000001000000008 | b2bBank   |
      | usedLaterAcct | usedLaterDebtor | EUR           | CH5604835012345678009  | chBank    |
    And metasfresh contains C_BP_BankAccount_DirectDebitMandate:
      | C_BP_BankAccount_DirectDebitMandate_ID | C_BP_BankAccount_ID | MandateType | MandateReference  | MandateDate | IsRecurring | DateLastUsed | AD_Org_ID | IsActive |
      | noBicMandate                           | noBicAcct           | B2B         | MREF-NOBIC-001    | 2013-09-23  | Y           | 2025-09-04   | sellerOrg | Y        |
      | otherOrgMandate                        | noBicAcct           | B2B         | MREF-OTHERORG-001 | 2013-09-23  | Y           | 2025-09-04   | otherOrg  | Y        |
      | noDescMandate                          | noDescAcct          | B2B         | MREF-NODESC-001   | 2013-09-23  | Y           | 2025-09-04   | sellerOrg | Y        |
      | longMandate                            | longAcct            | B2B         | MREF-(A/B)+1.2    | 2014-03-11  | Y           | 2025-09-04   | sellerOrg | Y        |
      | orgStarMandate                         | orgStarAcct         | B2B         | MREF-ORGSTAR-001  | 2020-05-04  | Y           | 2025-09-04   | orgStar   | Y        |
      | inactiveMandate                        | orgStarAcct         | B2B         | MREF-INACTIVE-001 | 2019-02-03  | Y           | 2025-09-04   | sellerOrg | N        |
      | usedLaterMandate                       | usedLaterAcct       | B2B         | MREF-LATER-001    | 2013-09-23  | Y           | 2026-12-01   | sellerOrg | Y        |
    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | IsSOTrx | DateInvoiced | C_Currency_ID | C_PaymentTerm_ID | PaymentRule | AD_Org_ID |
      | longInv    | longDebtor    | Y       | 2026-10-28   | EUR           | pt_sepa          | D           | sellerOrg |
      | orgStarInv | orgStarDebtor | Y       | 2026-10-28   | EUR           | pt_sepa          | D           | sellerOrg |
    # bill-to locations with a full address; the price list is given because the Swiss location has no price list of its country
    And metasfresh contains C_Invoice:
      | Identifier   | C_BPartner_ID   | C_BPartner_Location_ID | M_PriceList_ID | IsSOTrx | DateInvoiced | C_Currency_ID | C_PaymentTerm_ID | PaymentRule | AD_Org_ID |
      | noBicInv     | noBicDebtor     | noBicBPL               | pl_sales       | Y       | 2026-10-28   | EUR           | pt_sepa          | D           | sellerOrg |
      | usedLaterInv | usedLaterDebtor | usedLaterBPL           | pl_sales       | Y       | 2026-10-28   | EUR           | pt_sepa          | D           | sellerOrg |
    # no description
    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | IsSOTrx | DateInvoiced | C_Currency_ID | C_PaymentTerm_ID | PaymentRule | AD_Org_ID |
      | noDescInv  | noDescDebtor  | Y       | 2026-10-28   | EUR           | pt_sepa          | D           | sellerOrg |
    And metasfresh contains C_InvoiceLines
      | Identifier    | C_Invoice_ID | M_Product_ID | QtyInvoiced | Price | C_Tax_ID |
      | noBicInvL     | noBicInv     | product      | 1 PCE       | 10.00 | zeroTax  |
      | noDescInvL    | noDescInv    | product      | 1 PCE       | 20.00 | zeroTax  |
      | longInvL      | longInv      | product      | 1 PCE       | 30.00 | zeroTax  |
      | orgStarInvL   | orgStarInv   | product      | 1 PCE       | 50.00 | zeroTax  |
      | usedLaterInvL | usedLaterInv | product      | 1 PCE       | 60.00 | zeroTax  |
    And update C_Invoice:
      | Identifier   | Description                                                                                                                                                   |
      | longInv      | Rechnung „Nr. 1“ YYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYY |
      | orgStarInv   | Invoice OrgStarMandate                                                                                                                                        |
      | noBicInv     | Invoice NoBIC                                                                                                                                                 |
      | usedLaterInv | Invoice MandateUsedLater                                                                                                                                      |
    And the invoice identified by noBicInv is completed
    And the invoice identified by noDescInv is completed
    And the invoice identified by longInv is completed
    And the invoice identified by orgStarInv is completed
    And the invoice identified by usedLaterInv is completed
    And metasfresh contains Pay Selection
      | Identifier          | C_BP_BankAccount_ID | PaySelectionTrxType | PayDate    | AD_Org_ID |
      | debtorsPaySelection | orgAcct             | DD                  | 2026-11-02 | sellerOrg |
    And "Create from..." is invoked for pay selection debtorsPaySelection, using following parameters:
      | MatchRequirement | C_BPartner_ID | PayDate    |
      | CDD              | noBicDebtor   | 2026-11-02 |
    And "Create from..." is invoked for pay selection debtorsPaySelection, using following parameters:
      | MatchRequirement | C_BPartner_ID | PayDate    |
      | CDD              | noDescDebtor  | 2026-11-02 |
    And "Create from..." is invoked for pay selection debtorsPaySelection, using following parameters:
      | MatchRequirement | C_BPartner_ID | PayDate    |
      | CDD              | longDebtor    | 2026-11-02 |
    And "Create from..." is invoked for pay selection debtorsPaySelection, using following parameters:
      | MatchRequirement | C_BPartner_ID | PayDate    |
      | CDD              | orgStarDebtor | 2026-11-02 |
    And "Create from..." is invoked for pay selection debtorsPaySelection, using following parameters:
      | MatchRequirement | C_BPartner_ID   | PayDate    |
      | CDD              | usedLaterDebtor | 2026-11-02 |
    And the pay selection identified by debtorsPaySelection is completed
    And the Pay selection identified by debtorsPaySelection has exactly the following lines
      | C_Invoice_ID | OpenAmt | PayAmt | C_BP_BankAccount_ID |
      | noBicInv     | 10.00   | 10.00  | noBicAcct           |
      | noDescInv    | 20.00   | 20.00  | noDescAcct          |
      | longInv      | 30.00   | 30.00  | longAcct            |
      | orgStarInv   | 50.00   | 50.00  | orgStarAcct         |
      | usedLaterInv | 60.00   | 60.00  | usedLaterAcct       |

    When the SEPA export process is run for pay selection "debtorsPaySelection"

    Then the SEPA export result is a single XML file
    And the pay selection "debtorsPaySelection" has SEPA_Export records:
      | SEPA_Protocol   | NumberOfLines |
      | pain.008.001.08 | 5             |
    And the SEPA direct debit file for scheme B2B is:
      """
      <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
      <Document xsi:schemaLocation="urn:iso:std:iso:20022:tech:xsd:pain.008.001.08 pain.008.001.08.xsd" xmlns="urn:iso:std:iso:20022:tech:xsd:pain.008.001.08" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
          <CstmrDrctDbtInitn>
              <GrpHdr>
                  <MsgId>${ignore}</MsgId>
                  <CreDtTm>2026-10-28T10:00:00.000+01:00</CreDtTm>
                  <NbOfTxs>5</NbOfTxs>
                  <CtrlSum>170.00</CtrlSum>
                  <InitgPty>
                      <Nm>SEPA Test Creditor GmbH</Nm>
                  </InitgPty>
              </GrpHdr>
              <PmtInf>
                  <PmtInfId>${ignore}</PmtInfId>
                  <PmtMtd>DD</PmtMtd>
                  <NbOfTxs>1</NbOfTxs>
                  <CtrlSum>10.00</CtrlSum>
                  <PmtTpInf>
                      <SvcLvl>
                          <Cd>SEPA</Cd>
                      </SvcLvl>
                      <LclInstrm>
                          <Cd>B2B</Cd>
                      </LclInstrm>
                      <SeqTp>RCUR</SeqTp>
                  </PmtTpInf>
                  <ReqdColltnDt>2026-11-02</ReqdColltnDt>
                  <Cdtr>
                      <Nm>SEPA Test Creditor GmbH</Nm>
                      <PstlAdr>
                          <StrtNm>Musterstrasse 1</StrtNm>
                          <PstCd>53111</PstCd>
                          <TwnNm>Testhausen</TwnNm>
                          <Ctry>DE</Ctry>
                      </PstlAdr>
                  </Cdtr>
                  <CdtrAcct>
                      <Id>
                          <IBAN>DE91100000000123456789</IBAN>
                      </Id>
                  </CdtrAcct>
                  <CdtrAgt>
                      <FinInstnId>
                          <BICFI>TESTDEC0XXX</BICFI>
                      </FinInstnId>
                  </CdtrAgt>
                  <ChrgBr>SLEV</ChrgBr>
                  <CdtrSchmeId>
                      <Id>
                          <PrvtId>
                              <Othr>
                                  <Id>DE98ZZZ09999999999</Id>
                                  <SchmeNm>
                                      <Prtry>SEPA</Prtry>
                                  </SchmeNm>
                              </Othr>
                          </PrvtId>
                      </Id>
                  </CdtrSchmeId>
                  <DrctDbtTxInf>
                      <PmtId>
                          <EndToEndId>${ignore}</EndToEndId>
                      </PmtId>
                      <InstdAmt Ccy="EUR">10.00</InstdAmt>
                      <DrctDbtTx>
                          <MndtRltdInf>
                              <MndtId>MREF-NOBIC-001</MndtId>
                              <DtOfSgntr>2013-09-23</DtOfSgntr>
                              <AmdmntInd>false</AmdmntInd>
                          </MndtRltdInf>
                      </DrctDbtTx>
                      <!-- the debtor bank has no BIC -->
                      <DbtrAgt>
                          <FinInstnId>
                              <Othr>
                                  <Id>NOTPROVIDED</Id>
                              </Othr>
                          </FinInstnId>
                      </DbtrAgt>
                      <Dbtr>
                          <Nm>Debtor NoBIC</Nm>
                          <PstlAdr>
                              <StrtNm>Hauptstrasse 5</StrtNm>
                              <PstCd>50667</PstCd>
                              <TwnNm>Koeln</TwnNm>
                              <Ctry>DE</Ctry>
                          </PstlAdr>
                      </Dbtr>
                      <DbtrAcct>
                          <Id>
                              <IBAN>DE47400000001000000004</IBAN>
                          </Id>
                      </DbtrAcct>
                      <RmtInf>
                          <Ustrd>Invoice NoBIC</Ustrd>
                      </RmtInf>
                  </DrctDbtTxInf>
              </PmtInf>
              <PmtInf>
                  <PmtInfId>${ignore}</PmtInfId>
                  <PmtMtd>DD</PmtMtd>
                  <NbOfTxs>1</NbOfTxs>
                  <CtrlSum>20.00</CtrlSum>
                  <PmtTpInf>
                      <SvcLvl>
                          <Cd>SEPA</Cd>
                      </SvcLvl>
                      <LclInstrm>
                          <Cd>B2B</Cd>
                      </LclInstrm>
                      <SeqTp>RCUR</SeqTp>
                  </PmtTpInf>
                  <ReqdColltnDt>2026-11-02</ReqdColltnDt>
                  <Cdtr>
                      <Nm>SEPA Test Creditor GmbH</Nm>
                      <PstlAdr>
                          <StrtNm>Musterstrasse 1</StrtNm>
                          <PstCd>53111</PstCd>
                          <TwnNm>Testhausen</TwnNm>
                          <Ctry>DE</Ctry>
                      </PstlAdr>
                  </Cdtr>
                  <CdtrAcct>
                      <Id>
                          <IBAN>DE91100000000123456789</IBAN>
                      </Id>
                  </CdtrAcct>
                  <CdtrAgt>
                      <FinInstnId>
                          <BICFI>TESTDEC0XXX</BICFI>
                      </FinInstnId>
                  </CdtrAgt>
                  <ChrgBr>SLEV</ChrgBr>
                  <CdtrSchmeId>
                      <Id>
                          <PrvtId>
                              <Othr>
                                  <Id>DE98ZZZ09999999999</Id>
                                  <SchmeNm>
                                      <Prtry>SEPA</Prtry>
                                  </SchmeNm>
                              </Othr>
                          </PrvtId>
                      </Id>
                  </CdtrSchmeId>
                  <DrctDbtTxInf>
                      <PmtId>
                          <EndToEndId>${ignore}</EndToEndId>
                      </PmtId>
                      <InstdAmt Ccy="EUR">20.00</InstdAmt>
                      <DrctDbtTx>
                          <MndtRltdInf>
                              <MndtId>MREF-NODESC-001</MndtId>
                              <DtOfSgntr>2013-09-23</DtOfSgntr>
                              <AmdmntInd>false</AmdmntInd>
                          </MndtRltdInf>
                      </DrctDbtTx>
                      <DbtrAgt>
                          <FinInstnId>
                              <BICFI>TESTDEB2XXX</BICFI>
                          </FinInstnId>
                      </DbtrAgt>
                      <Dbtr>
                          <Nm>Debtor NoDescription</Nm>
                      </Dbtr>
                      <DbtrAcct>
                          <Id>
                              <IBAN>DE35200000001000000005</IBAN>
                          </Id>
                      </DbtrAcct>
                      <!-- no RmtInf: the invoice has no description -->
                  </DrctDbtTxInf>
              </PmtInf>
              <PmtInf>
                  <PmtInfId>${ignore}</PmtInfId>
                  <PmtMtd>DD</PmtMtd>
                  <NbOfTxs>1</NbOfTxs>
                  <CtrlSum>30.00</CtrlSum>
                  <PmtTpInf>
                      <SvcLvl>
                          <Cd>SEPA</Cd>
                      </SvcLvl>
                      <LclInstrm>
                          <Cd>B2B</Cd>
                      </LclInstrm>
                      <SeqTp>RCUR</SeqTp>
                  </PmtTpInf>
                  <ReqdColltnDt>2026-11-02</ReqdColltnDt>
                  <Cdtr>
                      <Nm>SEPA Test Creditor GmbH</Nm>
                      <PstlAdr>
                          <StrtNm>Musterstrasse 1</StrtNm>
                          <PstCd>53111</PstCd>
                          <TwnNm>Testhausen</TwnNm>
                          <Ctry>DE</Ctry>
                      </PstlAdr>
                  </Cdtr>
                  <CdtrAcct>
                      <Id>
                          <IBAN>DE91100000000123456789</IBAN>
                      </Id>
                  </CdtrAcct>
                  <CdtrAgt>
                      <FinInstnId>
                          <BICFI>TESTDEC0XXX</BICFI>
                      </FinInstnId>
                  </CdtrAgt>
                  <ChrgBr>SLEV</ChrgBr>
                  <CdtrSchmeId>
                      <Id>
                          <PrvtId>
                              <Othr>
                                  <Id>DE98ZZZ09999999999</Id>
                                  <SchmeNm>
                                      <Prtry>SEPA</Prtry>
                                  </SchmeNm>
                              </Othr>
                          </PrvtId>
                      </Id>
                  </CdtrSchmeId>
                  <DrctDbtTxInf>
                      <PmtId>
                          <EndToEndId>${ignore}</EndToEndId>
                      </PmtId>
                      <InstdAmt Ccy="EUR">30.00</InstdAmt>
                      <DrctDbtTx>
                          <MndtRltdInf>
                              <!-- text conversion, see the scenario description: mandate reference kept as it is, debtor name and remittance info -->
                              <MndtId>MREF-(A/B)+1.2</MndtId>
                              <DtOfSgntr>2014-03-11</DtOfSgntr>
                              <AmdmntInd>false</AmdmntInd>
                          </MndtRltdInf>
                      </DrctDbtTx>
                      <DbtrAgt>
                          <FinInstnId>
                              <BICFI>TESTDEB2XXX</BICFI>
                          </FinInstnId>
                      </DbtrAgt>
                      <Dbtr>
                          <Nm>Baecker   Soehne   Grosshandel XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX</Nm>
                      </Dbtr>
                      <DbtrAcct>
                          <Id>
                              <IBAN>DE08200000001000000006</IBAN>
                          </Id>
                      </DbtrAcct>
                      <RmtInf>
                          <Ustrd>Rechnung  Nr. 1  YYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYY</Ustrd>
                      </RmtInf>
                  </DrctDbtTxInf>
              </PmtInf>
              <PmtInf>
                  <PmtInfId>${ignore}</PmtInfId>
                  <PmtMtd>DD</PmtMtd>
                  <NbOfTxs>1</NbOfTxs>
                  <CtrlSum>50.00</CtrlSum>
                  <PmtTpInf>
                      <SvcLvl>
                          <Cd>SEPA</Cd>
                      </SvcLvl>
                      <LclInstrm>
                          <Cd>B2B</Cd>
                      </LclInstrm>
                      <SeqTp>RCUR</SeqTp>
                  </PmtTpInf>
                  <ReqdColltnDt>2026-11-02</ReqdColltnDt>
                  <Cdtr>
                      <Nm>SEPA Test Creditor GmbH</Nm>
                      <PstlAdr>
                          <StrtNm>Musterstrasse 1</StrtNm>
                          <PstCd>53111</PstCd>
                          <TwnNm>Testhausen</TwnNm>
                          <Ctry>DE</Ctry>
                      </PstlAdr>
                  </Cdtr>
                  <CdtrAcct>
                      <Id>
                          <IBAN>DE91100000000123456789</IBAN>
                      </Id>
                  </CdtrAcct>
                  <CdtrAgt>
                      <FinInstnId>
                          <BICFI>TESTDEC0XXX</BICFI>
                      </FinInstnId>
                  </CdtrAgt>
                  <ChrgBr>SLEV</ChrgBr>
                  <CdtrSchmeId>
                      <Id>
                          <PrvtId>
                              <Othr>
                                  <Id>DE98ZZZ09999999999</Id>
                                  <SchmeNm>
                                      <Prtry>SEPA</Prtry>
                                  </SchmeNm>
                              </Othr>
                          </PrvtId>
                      </Id>
                  </CdtrSchmeId>
                  <DrctDbtTxInf>
                      <PmtId>
                          <EndToEndId>${ignore}</EndToEndId>
                      </PmtId>
                      <InstdAmt Ccy="EUR">50.00</InstdAmt>
                      <DrctDbtTx>
                          <MndtRltdInf>
                              <!-- the org-* mandate; the inactive mandate MREF-INACTIVE-001 of the export org is not used -->
                              <MndtId>MREF-ORGSTAR-001</MndtId>
                              <DtOfSgntr>2020-05-04</DtOfSgntr>
                              <AmdmntInd>false</AmdmntInd>
                          </MndtRltdInf>
                      </DrctDbtTx>
                      <DbtrAgt>
                          <FinInstnId>
                              <BICFI>TESTDEB2XXX</BICFI>
                          </FinInstnId>
                      </DbtrAgt>
                      <Dbtr>
                          <Nm>Debtor OrgStarMandate</Nm>
                      </Dbtr>
                      <DbtrAcct>
                          <Id>
                              <IBAN>DE51200000001000000008</IBAN>
                          </Id>
                      </DbtrAcct>
                      <RmtInf>
                          <Ustrd>Invoice OrgStarMandate</Ustrd>
                      </RmtInf>
                  </DrctDbtTxInf>
              </PmtInf>
              <PmtInf>
                  <PmtInfId>${ignore}</PmtInfId>
                  <PmtMtd>DD</PmtMtd>
                  <NbOfTxs>1</NbOfTxs>
                  <CtrlSum>60.00</CtrlSum>
                  <PmtTpInf>
                      <SvcLvl>
                          <Cd>SEPA</Cd>
                      </SvcLvl>
                      <LclInstrm>
                          <Cd>B2B</Cd>
                      </LclInstrm>
                      <SeqTp>RCUR</SeqTp>
                  </PmtTpInf>
                  <ReqdColltnDt>2026-11-02</ReqdColltnDt>
                  <Cdtr>
                      <Nm>SEPA Test Creditor GmbH</Nm>
                      <PstlAdr>
                          <StrtNm>Musterstrasse 1</StrtNm>
                          <PstCd>53111</PstCd>
                          <TwnNm>Testhausen</TwnNm>
                          <Ctry>DE</Ctry>
                      </PstlAdr>
                  </Cdtr>
                  <CdtrAcct>
                      <Id>
                          <IBAN>DE91100000000123456789</IBAN>
                      </Id>
                  </CdtrAcct>
                  <CdtrAgt>
                      <FinInstnId>
                          <BICFI>TESTDEC0XXX</BICFI>
                      </FinInstnId>
                  </CdtrAgt>
                  <ChrgBr>SLEV</ChrgBr>
                  <CdtrSchmeId>
                      <Id>
                          <PrvtId>
                              <Othr>
                                  <Id>DE98ZZZ09999999999</Id>
                                  <SchmeNm>
                                      <Prtry>SEPA</Prtry>
                                  </SchmeNm>
                              </Othr>
                          </PrvtId>
                      </Id>
                  </CdtrSchmeId>
                  <DrctDbtTxInf>
                      <PmtId>
                          <EndToEndId>${ignore}</EndToEndId>
                      </PmtId>
                      <InstdAmt Ccy="EUR">60.00</InstdAmt>
                      <DrctDbtTx>
                          <MndtRltdInf>
                              <!-- the mandate was last used after the collection date -->
                              <MndtId>MREF-LATER-001</MndtId>
                              <DtOfSgntr>2013-09-23</DtOfSgntr>
                              <AmdmntInd>false</AmdmntInd>
                          </MndtRltdInf>
                      </DrctDbtTx>
                      <DbtrAgt>
                          <FinInstnId>
                              <BICFI>TESTCHZZXXX</BICFI>
                          </FinInstnId>
                      </DbtrAgt>
                      <!-- a Swiss debtor: the address is mandatory outside the EEA -->
                      <Dbtr>
                          <Nm>Debtor MandateUsedLater</Nm>
                          <PstlAdr>
                              <StrtNm>Bahnhofstrasse 1</StrtNm>
                              <PstCd>8001</PstCd>
                              <TwnNm>Zuerich</TwnNm>
                              <Ctry>CH</Ctry>
                          </PstlAdr>
                      </Dbtr>
                      <DbtrAcct>
                          <Id>
                              <IBAN>CH5604835012345678009</IBAN>
                          </Id>
                      </DbtrAcct>
                      <RmtInf>
                          <Ustrd>Invoice MandateUsedLater</Ustrd>
                      </RmtInf>
                  </DrctDbtTxInf>
              </PmtInf>
          </CstmrDrctDbtInitn>
      </Document>
      """
    And validate C_BP_BankAccount_DirectDebitMandate:
      | C_BP_BankAccount_DirectDebitMandate_ID | DateLastUsed |
      | usedLaterMandate                       | 2026-12-01   |
      | otherOrgMandate                        | 2025-09-04   |

  @from:cucumber
  @Id:S32708_TC14
  Scenario: creditor address from the org BP location, three exports
    The creditor is the org BP; its address comes from the org BP's default bill-to location (GLN 4000000000129),
    never from the debtor bank account's holder data (A_Name, A_City). There is one creditor per file, so the scenario exports three times
    and re-points that location before each export; each export expects a different Cdtr than the one before, so a re-point that did not take effect fails the export after it:
    - 1. a city of only disallowed characters (empty after conversion) -> no Cdtr.PstlAdr, the export still succeeds
    - 2. a location with a city (the Background's) -> Cdtr.PstlAdr with street, post code, town and country of the org BP, not the debtor account holder's city
    - 3. a location without a city -> no Cdtr.PstlAdr at all
    Given metasfresh contains C_BPartners:
      | Identifier     | Name                    | Value           | IsCustomer | M_PricingSystem_ID | PaymentRule |
      | withCityDebtor | Debtor CreditorWithCity | withcity_@Date@ | Y          | pricingSystem      | D           |
      | noCityDebtor   | Debtor CreditorNoCity   | nocity_@Date@   | Y          | pricingSystem      | D           |
      | euroCityDebtor | Debtor CreditorEuroCity | eurocity_@Date@ | Y          | pricingSystem      | D           |
    And metasfresh contains C_Bank:
      | C_Bank_ID | Name                 | RoutingNo | SwiftCode   |
      | b2bBank   | SEPA Debtor Bank B2B | 20000000  | TESTDEB2XXX |
    And metasfresh contains C_BP_BankAccount
      | Identifier   | C_BPartner_ID  | C_Currency_ID | IBAN                   | C_Bank_ID | A_Name              | A_City      |
      | withCityAcct | withCityDebtor | EUR           | DE35200000001000000005 | b2bBank   | Account Holder Name | Holder City |
      | noCityAcct   | noCityDebtor   | EUR           | DE08200000001000000006 | b2bBank   | Account Holder Name | Holder City |
      | euroCityAcct | euroCityDebtor | EUR           | DE78200000001000000007 | b2bBank   | Account Holder Name | Holder City |
    And metasfresh contains C_BP_BankAccount_DirectDebitMandate:
      | C_BP_BankAccount_DirectDebitMandate_ID | C_BP_BankAccount_ID | MandateType | MandateReference  | MandateDate | IsRecurring | DateLastUsed | AD_Org_ID |
      | withCityMandate                        | withCityAcct        | B2B         | MREF-WITHCITY-001 | 2013-09-23  | Y           | 2025-09-04   | sellerOrg |
      | noCityMandate                          | noCityAcct          | B2B         | MREF-NOCITY-001   | 2013-09-23  | Y           | 2025-09-04   | sellerOrg |
      | euroCityMandate                        | euroCityAcct        | B2B         | MREF-EUROCITY-001 | 2013-09-23  | Y           | 2025-09-04   | sellerOrg |
    And metasfresh contains C_Invoice:
      | Identifier  | C_BPartner_ID  | IsSOTrx | DateInvoiced | C_Currency_ID | C_PaymentTerm_ID | PaymentRule | AD_Org_ID |
      | withCityInv | withCityDebtor | Y       | 2026-10-28   | EUR           | pt_sepa          | D           | sellerOrg |
      | noCityInv   | noCityDebtor   | Y       | 2026-10-28   | EUR           | pt_sepa          | D           | sellerOrg |
      | euroCityInv | euroCityDebtor | Y       | 2026-10-28   | EUR           | pt_sepa          | D           | sellerOrg |
    And metasfresh contains C_InvoiceLines
      | Identifier   | C_Invoice_ID | M_Product_ID | QtyInvoiced | Price  | C_Tax_ID |
      | withCityInvL | withCityInv  | product      | 1 PCE       | 100.00 | zeroTax  |
      | noCityInvL   | noCityInv    | product      | 1 PCE       | 200.00 | zeroTax  |
      | euroCityInvL | euroCityInv  | product      | 1 PCE       | 300.00 | zeroTax  |
    And update C_Invoice:
      | Identifier  | Description      |
      | withCityInv | Invoice WithCity |
      | noCityInv   | Invoice NoCity   |
      | euroCityInv | Invoice EuroCity |
    And the invoice identified by withCityInv is completed
    And the invoice identified by noCityInv is completed
    And the invoice identified by euroCityInv is completed
    And metasfresh contains Pay Selection
      | Identifier           | C_BP_BankAccount_ID | PaySelectionTrxType | PayDate    | AD_Org_ID |
      | withCityPaySelection | orgAcct             | DD                  | 2026-11-02 | sellerOrg |
      | noCityPaySelection   | orgAcct             | DD                  | 2026-11-02 | sellerOrg |
      | euroCityPaySelection | orgAcct             | DD                  | 2026-11-02 | sellerOrg |
    And "Create from..." is invoked for pay selection withCityPaySelection, using following parameters:
      | MatchRequirement | C_BPartner_ID  | PayDate    |
      | CDD              | withCityDebtor | 2026-11-02 |
    And "Create from..." is invoked for pay selection noCityPaySelection, using following parameters:
      | MatchRequirement | C_BPartner_ID | PayDate    |
      | CDD              | noCityDebtor  | 2026-11-02 |
    And "Create from..." is invoked for pay selection euroCityPaySelection, using following parameters:
      | MatchRequirement | C_BPartner_ID  | PayDate    |
      | CDD              | euroCityDebtor | 2026-11-02 |
    And the pay selection identified by withCityPaySelection is completed
    And the pay selection identified by noCityPaySelection is completed
    And the pay selection identified by euroCityPaySelection is completed
    And the Pay selection identified by withCityPaySelection has exactly the following lines
      | C_Invoice_ID | OpenAmt | PayAmt | C_BP_BankAccount_ID |
      | withCityInv  | 100.00  | 100.00 | withCityAcct        |
    And the Pay selection identified by noCityPaySelection has exactly the following lines
      | C_Invoice_ID | OpenAmt | PayAmt | C_BP_BankAccount_ID |
      | noCityInv    | 200.00  | 200.00 | noCityAcct          |
    And the Pay selection identified by euroCityPaySelection has exactly the following lines
      | C_Invoice_ID | OpenAmt | PayAmt | C_BP_BankAccount_ID |
      | euroCityInv  | 300.00  | 300.00 | euroCityAcct        |

    # 1. a city of only disallowed characters
    Given metasfresh contains C_Location:
      | C_Location_ID.Identifier | CountryCode | OPT.Address1    | OPT.Postal | OPT.City |
      | euroCityLoc              | DE          | Musterstrasse 3 | 53111      | €€       |
    And metasfresh contains C_BPartner_Locations:
      | Identifier  | GLN           | C_BPartner_ID | OPT.C_Location_ID.Identifier | OPT.IsBillTo | OPT.IsBillToDefault |
      | creditorBPL | 4000000000129 | creditorBP    | euroCityLoc                  | Y            | Y                   |
    When the SEPA export process is run for pay selection "euroCityPaySelection"
    Then the SEPA export result is a single XML file
    And the SEPA direct debit file for scheme B2B is:
      """
      <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
      <Document xsi:schemaLocation="urn:iso:std:iso:20022:tech:xsd:pain.008.001.08 pain.008.001.08.xsd" xmlns="urn:iso:std:iso:20022:tech:xsd:pain.008.001.08" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
          <CstmrDrctDbtInitn>
              <GrpHdr>
                  <MsgId>${ignore}</MsgId>
                  <CreDtTm>2026-10-28T10:00:00.000+01:00</CreDtTm>
                  <NbOfTxs>1</NbOfTxs>
                  <CtrlSum>300.00</CtrlSum>
                  <InitgPty>
                      <Nm>SEPA Test Creditor GmbH</Nm>
                  </InitgPty>
              </GrpHdr>
              <PmtInf>
                  <PmtInfId>${ignore}</PmtInfId>
                  <PmtMtd>DD</PmtMtd>
                  <NbOfTxs>1</NbOfTxs>
                  <CtrlSum>300.00</CtrlSum>
                  <PmtTpInf>
                      <SvcLvl>
                          <Cd>SEPA</Cd>
                      </SvcLvl>
                      <LclInstrm>
                          <Cd>B2B</Cd>
                      </LclInstrm>
                      <SeqTp>RCUR</SeqTp>
                  </PmtTpInf>
                  <ReqdColltnDt>2026-11-02</ReqdColltnDt>
                  <Cdtr>
                      <Nm>SEPA Test Creditor GmbH</Nm>
                      <!-- no PstlAdr: the city is empty after the conversion -->
                  </Cdtr>
                  <CdtrAcct>
                      <Id>
                          <IBAN>DE91100000000123456789</IBAN>
                      </Id>
                  </CdtrAcct>
                  <CdtrAgt>
                      <FinInstnId>
                          <BICFI>TESTDEC0XXX</BICFI>
                      </FinInstnId>
                  </CdtrAgt>
                  <ChrgBr>SLEV</ChrgBr>
                  <CdtrSchmeId>
                      <Id>
                          <PrvtId>
                              <Othr>
                                  <Id>DE98ZZZ09999999999</Id>
                                  <SchmeNm>
                                      <Prtry>SEPA</Prtry>
                                  </SchmeNm>
                              </Othr>
                          </PrvtId>
                      </Id>
                  </CdtrSchmeId>
                  <DrctDbtTxInf>
                      <PmtId>
                          <EndToEndId>${ignore}</EndToEndId>
                      </PmtId>
                      <InstdAmt Ccy="EUR">300.00</InstdAmt>
                      <DrctDbtTx>
                          <MndtRltdInf>
                              <MndtId>MREF-EUROCITY-001</MndtId>
                              <DtOfSgntr>2013-09-23</DtOfSgntr>
                              <AmdmntInd>false</AmdmntInd>
                          </MndtRltdInf>
                      </DrctDbtTx>
                      <DbtrAgt>
                          <FinInstnId>
                              <BICFI>TESTDEB2XXX</BICFI>
                          </FinInstnId>
                      </DbtrAgt>
                      <Dbtr>
                          <Nm>Debtor CreditorEuroCity</Nm>
                      </Dbtr>
                      <DbtrAcct>
                          <Id>
                              <IBAN>DE78200000001000000007</IBAN>
                          </Id>
                      </DbtrAcct>
                      <RmtInf>
                          <Ustrd>Invoice EuroCity</Ustrd>
                      </RmtInf>
                  </DrctDbtTxInf>
              </PmtInf>
          </CstmrDrctDbtInitn>
      </Document>
      """

    # 2. back to the Background's location, with a city
    Given metasfresh contains C_BPartner_Locations:
      | Identifier  | GLN           | C_BPartner_ID | OPT.C_Location_ID.Identifier | OPT.IsBillTo | OPT.IsBillToDefault |
      | creditorBPL | 4000000000129 | creditorBP    | creditorLoc                  | Y            | Y                   |
    When the SEPA export process is run for pay selection "withCityPaySelection"
    Then the SEPA export result is a single XML file
    And the SEPA direct debit file for scheme B2B is:
      """
      <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
      <Document xsi:schemaLocation="urn:iso:std:iso:20022:tech:xsd:pain.008.001.08 pain.008.001.08.xsd" xmlns="urn:iso:std:iso:20022:tech:xsd:pain.008.001.08" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
          <CstmrDrctDbtInitn>
              <GrpHdr>
                  <MsgId>${ignore}</MsgId>
                  <CreDtTm>2026-10-28T10:00:00.000+01:00</CreDtTm>
                  <NbOfTxs>1</NbOfTxs>
                  <CtrlSum>100.00</CtrlSum>
                  <InitgPty>
                      <Nm>SEPA Test Creditor GmbH</Nm>
                  </InitgPty>
              </GrpHdr>
              <PmtInf>
                  <PmtInfId>${ignore}</PmtInfId>
                  <PmtMtd>DD</PmtMtd>
                  <NbOfTxs>1</NbOfTxs>
                  <CtrlSum>100.00</CtrlSum>
                  <PmtTpInf>
                      <SvcLvl>
                          <Cd>SEPA</Cd>
                      </SvcLvl>
                      <LclInstrm>
                          <Cd>B2B</Cd>
                      </LclInstrm>
                      <SeqTp>RCUR</SeqTp>
                  </PmtTpInf>
                  <ReqdColltnDt>2026-11-02</ReqdColltnDt>
                  <Cdtr>
                      <Nm>SEPA Test Creditor GmbH</Nm>
                      <!-- the org BP's location, not the debtor account holder's city -->
                      <PstlAdr>
                          <StrtNm>Musterstrasse 1</StrtNm>
                          <PstCd>53111</PstCd>
                          <TwnNm>Testhausen</TwnNm>
                          <Ctry>DE</Ctry>
                      </PstlAdr>
                  </Cdtr>
                  <CdtrAcct>
                      <Id>
                          <IBAN>DE91100000000123456789</IBAN>
                      </Id>
                  </CdtrAcct>
                  <CdtrAgt>
                      <FinInstnId>
                          <BICFI>TESTDEC0XXX</BICFI>
                      </FinInstnId>
                  </CdtrAgt>
                  <ChrgBr>SLEV</ChrgBr>
                  <CdtrSchmeId>
                      <Id>
                          <PrvtId>
                              <Othr>
                                  <Id>DE98ZZZ09999999999</Id>
                                  <SchmeNm>
                                      <Prtry>SEPA</Prtry>
                                  </SchmeNm>
                              </Othr>
                          </PrvtId>
                      </Id>
                  </CdtrSchmeId>
                  <DrctDbtTxInf>
                      <PmtId>
                          <EndToEndId>${ignore}</EndToEndId>
                      </PmtId>
                      <InstdAmt Ccy="EUR">100.00</InstdAmt>
                      <DrctDbtTx>
                          <MndtRltdInf>
                              <MndtId>MREF-WITHCITY-001</MndtId>
                              <DtOfSgntr>2013-09-23</DtOfSgntr>
                              <AmdmntInd>false</AmdmntInd>
                          </MndtRltdInf>
                      </DrctDbtTx>
                      <DbtrAgt>
                          <FinInstnId>
                              <BICFI>TESTDEB2XXX</BICFI>
                          </FinInstnId>
                      </DbtrAgt>
                      <Dbtr>
                          <Nm>Debtor CreditorWithCity</Nm>
                      </Dbtr>
                      <DbtrAcct>
                          <Id>
                              <IBAN>DE35200000001000000005</IBAN>
                          </Id>
                      </DbtrAcct>
                      <RmtInf>
                          <Ustrd>Invoice WithCity</Ustrd>
                      </RmtInf>
                  </DrctDbtTxInf>
              </PmtInf>
          </CstmrDrctDbtInitn>
      </Document>
      """

    # 3. a location without a city
    Given metasfresh contains C_Location:
      | C_Location_ID.Identifier | CountryCode | OPT.Address1    | OPT.Postal |
      | noCityLoc                | DE          | Musterstrasse 2 | 53111      |
    And metasfresh contains C_BPartner_Locations:
      | Identifier  | GLN           | C_BPartner_ID | OPT.C_Location_ID.Identifier | OPT.IsBillTo | OPT.IsBillToDefault |
      | creditorBPL | 4000000000129 | creditorBP    | noCityLoc                    | Y            | Y                   |
    When the SEPA export process is run for pay selection "noCityPaySelection"
    Then the SEPA export result is a single XML file
    And the SEPA direct debit file for scheme B2B is:
      """
      <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
      <Document xsi:schemaLocation="urn:iso:std:iso:20022:tech:xsd:pain.008.001.08 pain.008.001.08.xsd" xmlns="urn:iso:std:iso:20022:tech:xsd:pain.008.001.08" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
          <CstmrDrctDbtInitn>
              <GrpHdr>
                  <MsgId>${ignore}</MsgId>
                  <CreDtTm>2026-10-28T10:00:00.000+01:00</CreDtTm>
                  <NbOfTxs>1</NbOfTxs>
                  <CtrlSum>200.00</CtrlSum>
                  <InitgPty>
                      <Nm>SEPA Test Creditor GmbH</Nm>
                  </InitgPty>
              </GrpHdr>
              <PmtInf>
                  <PmtInfId>${ignore}</PmtInfId>
                  <PmtMtd>DD</PmtMtd>
                  <NbOfTxs>1</NbOfTxs>
                  <CtrlSum>200.00</CtrlSum>
                  <PmtTpInf>
                      <SvcLvl>
                          <Cd>SEPA</Cd>
                      </SvcLvl>
                      <LclInstrm>
                          <Cd>B2B</Cd>
                      </LclInstrm>
                      <SeqTp>RCUR</SeqTp>
                  </PmtTpInf>
                  <ReqdColltnDt>2026-11-02</ReqdColltnDt>
                  <Cdtr>
                      <Nm>SEPA Test Creditor GmbH</Nm>
                      <!-- no PstlAdr: the location has no city -->
                  </Cdtr>
                  <CdtrAcct>
                      <Id>
                          <IBAN>DE91100000000123456789</IBAN>
                      </Id>
                  </CdtrAcct>
                  <CdtrAgt>
                      <FinInstnId>
                          <BICFI>TESTDEC0XXX</BICFI>
                      </FinInstnId>
                  </CdtrAgt>
                  <ChrgBr>SLEV</ChrgBr>
                  <CdtrSchmeId>
                      <Id>
                          <PrvtId>
                              <Othr>
                                  <Id>DE98ZZZ09999999999</Id>
                                  <SchmeNm>
                                      <Prtry>SEPA</Prtry>
                                  </SchmeNm>
                              </Othr>
                          </PrvtId>
                      </Id>
                  </CdtrSchmeId>
                  <DrctDbtTxInf>
                      <PmtId>
                          <EndToEndId>${ignore}</EndToEndId>
                      </PmtId>
                      <InstdAmt Ccy="EUR">200.00</InstdAmt>
                      <DrctDbtTx>
                          <MndtRltdInf>
                              <MndtId>MREF-NOCITY-001</MndtId>
                              <DtOfSgntr>2013-09-23</DtOfSgntr>
                              <AmdmntInd>false</AmdmntInd>
                          </MndtRltdInf>
                      </DrctDbtTx>
                      <DbtrAgt>
                          <FinInstnId>
                              <BICFI>TESTDEB2XXX</BICFI>
                          </FinInstnId>
                      </DbtrAgt>
                      <Dbtr>
                          <Nm>Debtor CreditorNoCity</Nm>
                      </Dbtr>
                      <DbtrAcct>
                          <Id>
                              <IBAN>DE08200000001000000006</IBAN>
                          </Id>
                      </DbtrAcct>
                      <RmtInf>
                          <Ustrd>Invoice NoCity</Ustrd>
                      </RmtInf>
                  </DrctDbtTxInf>
              </PmtInf>
          </CstmrDrctDbtInitn>
      </Document>
      """
