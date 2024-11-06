## Data mapping from camt53 to metasfresh

### Header

- document path for `Document.BkToCstmrStmt.Stmt`
- mf request `BankStatementCreateRequest`
- all values are set on `C_BankStatement`

|Document field|metasfresh-json|mandatory in mf|metasfresh-column| note                                                                                              |
|---|---|---|---|---------------------------------------------------------------------------------------------------|
|Stmt.CreDtTm|name|Y|Name| ---                                                                                               |
|Stmt.CreDtTm|statementDate|Y|StatementDate| ---                                                                                               |
|Stmt.Acct.Id.IBAN|orgBankAccountId|Y|C_BP_BankAccount_ID| `BankAccountId` resolved from IBAN                                                                |
|Stmt.Acct.Id.Othr.Id|orgBankAccountId|Y|C_BP_BankAccount_ID| `BankAccountId` resolved from AccountNo and swiftCode (Bank)                                      |
|Stmt.Acct.Scvr.FinInstnId.BIC|orgBankAccountId|Y|C_BP_BankAccount_ID| Taken into account to determine `BankId` when resolving `BankAccountId`                           |
|Stmt.Acct.Ccy|---|---|---| validate that currency set on BankAccountId matches statement, otherwise the statement is skipped |
|Stmt.Bal.Tp.CdtDbtInd|---|---|---| credit/debit indicator                                                                            |
|Stmt.Bal.Tp.Cd|---|---|---| specifies Balance type                                                                            |
|Stmt.Bal.Tp.Amt.value|beginningBalance|N|BeginningBalance| computed using credit/debit indicator (`Stmt.Bal.Tp.CdtDbtInd`) and balance type (`Stmt.Bal.Tp.Cd`)                      |
|---|orgId|Y|AD_Org_ID| set from `BankAccount` record                                                                   |
|---|bankStatementImportFileId|N|C_BankStatement_Import_File| id of the BankStatement_Import_File record                                                        |
|---|---|Y|DocStatus| default value `Drafted`                                                                           |
|---|---|Y|DocAction| default value `Complete`                                                                          |

### Line

- all details are taken from `Stmt`
- mf request `BankStatementLineCreateRequest`
- all values are set on `C_BankStatementLine`

| Document field                                           |metasfresh-json|mandatory in mf|metasfresh-column| note                                                                                     |
|----------------------------------------------------------|---|---|---|------------------------------------------------------------------------------------------|
| Ntry.NtryRef                                             |---|---|---| reference of the statement within file                                                   |
| Ntry.ValDt                                               |---|Y|StatementLineDate| ---                                                                                      |
| Ntry.ValDt                                               |---|Y|DateAcct| ---                                                                                      |
| Ntry.ValDt                                               |---|Y|ValutaDate| ---                                                                                      |
| Ntry.AcctSvcrRef                                         |referenceNo|N|ReferenceNo| ---                                                                                      |
| Ntry.CdtDbtInd                                           |---|---|---| credit/debit indicator                                                                   |
| Ntry.Amt.value                                           |trxAmt.value|Y|TrxAmt| ---                                                                                      |
| Ntry.Amt.value                                           |statementAmt.value|Y|StmtAmt| ---                                                                                      |
| Ntry.Amt.ccy                                             |statementAmt.currencyID|Y|C_Currency_ID| ---                                                                                      |
| Ntry.NtryRef.AddtlNtryInf                                |---|---|N| Description                                                                              |Additional entry details. Used to compute `Description`|
| Ntry.NtryDtls.TxDtls.RmtInf.Ustrd                        |memo|N|Memo| ---                                                                                      |
| Ntry.NtryDtls.TxDtls.RltdPties.Dbtr.Nm                   |importedBillPartnerName|N|ImportedBillPartnerName| computed from `debit` indicator, if the `Ntry.CdtDbtInd` == `CRDT`                       |
| Ntry.NtryDtls.TxDtls.RltdPties.Cdtr.Nm                   |importedBillPartnerName|N|ImportedBillPartnerName| computed from `credit` indicator, if the `Ntry.CdtDbtInd` == `DBIT `                      |
| Ntry.NtryDtls.TxDtls.AmtDtls.CntrValAmt.CcyXchg.XchgRate |currencyRate|N|CurrencyRate| ---                                                                                      |
| Ntry.Intrst.Amt.value                                    |interestAmt.value|Y|InterestAmt| computed using currency attribute`Ntry.Intrst.Amt.ccy`. if not found, then `ZERO` is set |
| ---                                                      |invoiceId|N|C_Invoice| resolved invoice matching `Description` and `StatementLineDate`                          |
| ---                                                      |bpartnerId|N|C_BPartner_ID| `C_Invoice.C_BPartner_ID`                                                                |
| ---                                                      |orgId|Y|AD_Org_ID| same as `C_BankStatement`                                                                |
| ---                                                      |bankStatementId|Y|C_BankStatement_ID| ---                                                                                      |
| ---                                                      |isUpdateAmountsFromInvoice|Y|IsUpdateAmountsFromInvoice| always `false`                                                                           |


