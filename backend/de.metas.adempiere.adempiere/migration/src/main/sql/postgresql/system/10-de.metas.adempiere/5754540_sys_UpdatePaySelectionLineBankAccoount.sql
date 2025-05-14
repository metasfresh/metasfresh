UPDATE ad_column SET isidentifier = 'Y', seqno = 50 WHERE ad_column_id = 542083;

UPDATE ad_val_rule SET code = e'    C_BP_BankAccount.C_BPartner_ID=@C_BPartner_ID@

/* the invoice\'s currency needs to match the account\'s currency */
AND C_BP_BankAccount.C_Currency_ID IN (select i.C_Currency_ID from C_invoice i where i.C_Invoice_ID=@C_Invoice_ID@ UNION ALL select p.C_Currency_ID from C_Payment p where p.C_Payment_ID=@Original_Payment_ID@)

AND (C_BP_BankAccount.BPBankAcctUse = \'B\'
	OR C_BP_BankAccount.BPBankAcctUse = \'@PaymentRule@\'
	/*Allow selection of NON-Standard types of usage like "Provision", because we can\'t know if they make sense or not*/
	OR C_BP_BankAccount.BPBankAcctUse NOT IN (\'B\',\'D\',\'N\',\'T\')
)' WHERE ad_val_rule_id = 540104;
