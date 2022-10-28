## Data mapping from camt53 to metasfresh

### Header

- document path for `Document.BkToCstmrStmt.Stmt` 
- mf request `BankStatementCreateRequest`
- all values are set on `C_BankStatement`

|Document field|metasfresh-json|mandatory in mf|metasfresh-column|note|
|---|---|---|---|---|
|Stmt.CreDtTm|name|Y|Name|---|
|Stmt.CreDtTm|statementDate|Y|StatementDate|---|
|Stmt.Acct.Id.IBAN|orgBankAccountId|Y|C_BP_BankAccount_ID|`BankAccountId` resolved from IBAN|
|Stmt.Acct.Id.Othr.Id|orgBankAccountId|Y|C_BP_BankAccount_ID|`BankAccountId` resolved from AccountNo|
|Stmt.Acct.Scvr.FinInstnId.BIC|orgBankAccountId|Y|C_BP_BankAccount_ID|`BankAccountId` resolved from swiftCode|
|Stmt.Acct.Ccy|---|---|---|validate that currency set on BankAccountId matches statement, otherwise the statement is skipped|
|Stmt.Bal.Tp.CdtDbtInd|---|---|---|credit/debit indicator|
|Stmt.Bal.Tp.Cd|---|---|---|specifies Balance type; the following values are supported: `OPBD`(Openning balance);`CLAV`(Closing available balance);`OPAV`(Opening available balance);`PRCD`(Last closing balance);`ITBD`(Interim booked)|
|Stmt.Bal.Tp.Amt.value|beginningBalance|N|BeginningBalance|computed using credit/debit indicator and balance type|
|---|orgBankAccountId|Y|C_BP_BankAccount_ID|`BankAccountId` resolved as described above|
|---|orgId|Y|AD_Org_ID|set from `BankAccountId` record|
|---|bankStatementImportFileId|N|C_BankStatement_Import_File|id of the BankStatement_Import_File record|
|---|---|Y|DocStatus|default value `Drafted`|
|---|---|Y|DocAction|default value `Complete`|


### Line

- all details are taken from `Stmt`
- mf request `BankStatementLineCreateRequest`
- all values are set on `C_BankStatementLine`

|Document field|metasfresh-json|mandatory in mf|metasfresh-column|note|
|---|---|---|---|---|
|Ntry.NtryRef|---|---|---|reference of the statement within file|
|Ntry.ValDt|---|Y|StatementLineDate|---|
|Ntry.ValDt|---|Y|DateAcct|---|
|Ntry.ValDt|---|Y|ValutaDate|---|
|Ntry.AcctSvcrRef|referenceNo|N|ReferenceNo|---|
|Ntry.CdtDbtInd|---|---|---|credit/debit indicator|
|Ntry.Amt.value|trxAmt.value|Y|TrxAmt|---|
|Ntry.Amt.value|statementAmt.value|Y|StmtAmt|---|
|Ntry.Amt.ccy|statementAmt.currencyID|Y|C_Currency_ID|---|
|Ntry.NtryRef.AddtlNtryInf|---|---|N|Description|reference of the statement within file. used to compute `Description`|
|Ntry.NtryDtls.TxDtls.RmtInf.Ustrd|memo|N|Memo|---|
|Ntry.NtryDtls.TxDtls.RltdPties.Dbtr.Nm|importedBillPartnerName|N|ImportedBillPartnerName|computed from `debit` indicator|
|Ntry.NtryDtls.TxDtls.RltdPties.Cdtr.Nm|importedBillPartnerName|N|ImportedBillPartnerName|computed from `credit` indicator|
|Ntry.NtryDtls.TxDtls.AmtDtls.CntrValAmt.CcyXchg.XchgRate|currencyRate|N|CurrencyRate|---|
|Ntry.Intrst.Amt.value|interestAmt.value|Y|InterestAmt|computed using currency attribute`Ntry.Intrst.Amt.ccy`. if not found, then `ZERO` is set|
|---|invoiceId|N|C_Invoice|resolved invoice matching `Description` and `StatementLineDate`|
|---|bpartnerId|N|C_BPartner_ID|`C_Invoice.C_BPartner_ID`|
|---|orgId|Y|AD_Org_ID|same as `C_BankStatement`|
|---|bankStatementId|Y|C_BankStatement_ID|---|
|---|isUpdateAmountsFromInvoice|Y|IsUpdateAmountsFromInvoice|always `false`|


