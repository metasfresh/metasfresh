-- 09.11.2015 18:33
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='	exists 
	(
		select 1 from C_Invoice i
		inner join C_InvoiceLine il on i.C_Invoice_ID = il.C_Invoice_ID
		inner join C_Invoice_Line_Alloc ila on il.C_InvoiceLine_ID = ila.C_InvoiceLine_ID 
		inner join C_Invoice_Candidate ic on ila.C_Invoice_Candidate_ID = ic.C_Invoice_Candidate_ID
		where ila.C_Invoice_Candidate_ID = @C_Invoice_Candidate_ID@
			and C_Invoice.C_Invoice_ID = i.C_Invoice_ID
			and i.IsSOTrx = ic.IsSOTrx
			and i.IsSOTrx = ''Y''
)',Updated=TO_TIMESTAMP('2015-11-09 18:33:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540556
;

-- 09.11.2015 18:35
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='exists 
	(
		select 1 from C_Invoice i
		inner join C_InvoiceLine il on i.C_Invoice_ID = il.C_Invoice_ID
		inner join C_Invoice_Line_Alloc ila on il.C_InvoiceLine_ID = ila.C_InvoiceLine_ID 
		inner join C_Invoice_Candidate ic on ila.C_Invoice_Candidate_ID = ic.C_Invoice_Candidate_ID
		where ila.C_Invoice_Candidate_ID = @C_Invoice_Candidate_ID@
			and C_Invoice.C_Invoice_ID = i.C_Invoice_ID
			and i.IsSOTrx = ic.IsSOTrx
			and i.IsSOTrx = ''N''
)',Updated=TO_TIMESTAMP('2015-11-09 18:35:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540562
;

-- 09.11.2015 18:36
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='exists 
	(
		select 1 from C_Invoice_Candidate ic
		inner join C_Invoice_Line_Alloc ila on ic.C_Invoice_Candidate_ID = ila.C_Invoice_Candidate_ID
		inner join C_InvoiceLine il on ila.C_InvoiceLine_ID = il.C_InvoiceLine_ID
		inner join C_Invoice i  on il.C_Invoice_ID = i.C_Invoice_ID

		where i.C_Invoice_ID = @C_Invoice_ID@
			and C_Invoice_Candidate.C_Invoice_Candidate_ID = ic.C_Invoice_Candidate_ID
	)',Updated=TO_TIMESTAMP('2015-11-09 18:36:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540559
;

