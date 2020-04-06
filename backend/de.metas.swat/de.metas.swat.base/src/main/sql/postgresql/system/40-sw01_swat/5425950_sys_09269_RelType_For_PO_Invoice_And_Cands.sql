-- 03.09.2015 15:08
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='	
	
	EXISTS 
	(
		SELECT 1 FROM C_Invoice i
		INNER JOIN C_InvoiceLine il ON i.C_Invoice_ID = il.C_Invoice_ID
		INNER JOIN C_Invoice_Line_Alloc ila ON il.C_InvoiceLine_ID = ila.C_InvoiceLine_ID 
		WHERE ila.C_Invoice_Candidate_ID = @C_Invoice_Candidate_ID@
			AND C_Invoice.C_Invoice_ID = i.C_Invoice_ID
			AND i.IsSOTrx = @IsSOTrx@
	)',Updated=TO_TIMESTAMP('2015-09-03 15:08:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540556
;

-- 03.09.2015 15:13
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='	
	
	EXISTS 
	(
		SELECT 1 FROM C_Invoice i
		INNER JOIN C_InvoiceLine il ON i.C_Invoice_ID = il.C_Invoice_ID
		INNER JOIN C_Invoice_Line_Alloc ila ON il.C_InvoiceLine_ID = ila.C_InvoiceLine_ID 
		WHERE ila.C_Invoice_Candidate_ID = @C_Invoice_Candidate_ID@
			AND C_Invoice.C_Invoice_ID = i.C_Invoice_ID
			AND i.IsSOTrx = @IsSOTrx@ :: text
	)',Updated=TO_TIMESTAMP('2015-09-03 15:13:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540556
;

-- 03.09.2015 15:14
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='	
	
	EXISTS 
	(
		SELECT 1 FROM C_Invoice i
		INNER JOIN C_InvoiceLine il ON i.C_Invoice_ID = il.C_Invoice_ID
		INNER JOIN C_Invoice_Line_Alloc ila ON il.C_InvoiceLine_ID = ila.C_InvoiceLine_ID 
		WHERE ila.C_Invoice_Candidate_ID = @C_Invoice_Candidate_ID@
			AND C_Invoice.C_Invoice_ID = i.C_Invoice_ID
			AND i.IsSOTrx :: text= @IsSOTrx@
	)',Updated=TO_TIMESTAMP('2015-09-03 15:14:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540556
;

-- 03.09.2015 15:16
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='	
	
	EXISTS 
	(
		SELECT 1 FROM C_Invoice i
		INNER JOIN C_InvoiceLine il ON i.C_Invoice_ID = il.C_Invoice_ID
		INNER JOIN C_Invoice_Line_Alloc ila ON il.C_InvoiceLine_ID = ila.C_InvoiceLine_ID 
		INNER JOIN I_C_Invoice_Candidate ic on ila.C_Invoice_Candidate_ID = ic.C_Invoice_Candidate_ID
		WHERE ila.C_Invoice_Candidate_ID = @C_Invoice_Candidate_ID@
			AND C_Invoice.C_Invoice_ID = i.C_Invoice_ID
			AND i.IsSOTrx = ic.IsSOTrx
)',Updated=TO_TIMESTAMP('2015-09-03 15:16:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540556
;

-- 03.09.2015 15:17
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='	
	
	EXISTS 
	(
		SELECT 1 FROM C_Invoice i
		INNER JOIN C_InvoiceLine il ON i.C_Invoice_ID = il.C_Invoice_ID
		INNER JOIN C_Invoice_Line_Alloc ila ON il.C_InvoiceLine_ID = ila.C_InvoiceLine_ID 
		INNER JOIN C_Invoice_Candidate ic on ila.C_Invoice_Candidate_ID = ic.C_Invoice_Candidate_ID
		WHERE ila.C_Invoice_Candidate_ID = @C_Invoice_Candidate_ID@
			AND C_Invoice.C_Invoice_ID = i.C_Invoice_ID
			AND i.IsSOTrx = ic.IsSOTrx
)',Updated=TO_TIMESTAMP('2015-09-03 15:17:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540556
;

-- 03.09.2015 15:36
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Source_ID,AD_Reference_Target_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsDirected,IsExplicit,Name,Role_Source,Updated,UpdatedBy) VALUES (0,0,540266,540556,540120,TO_TIMESTAMP('2015-09-03 15:36:34','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','N','N','C_InvoiceCandidate -> Invoice (PO ONLY)','Invoice',TO_TIMESTAMP('2015-09-03 15:36:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 03.09.2015 15:36
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET Name='C_InvoiceCandidate -> Invoice (SO ONLY)',Updated=TO_TIMESTAMP('2015-09-03 15:36:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540116
;

-- 03.09.2015 15:40
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET Role_Source=NULL,Updated=TO_TIMESTAMP('2015-09-03 15:40:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540120
;

-- 03.09.2015 15:43
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,53331,541121,TO_TIMESTAMP('2015-09-03 15:43:02','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','Eingangsrechnung',TO_TIMESTAMP('2015-09-03 15:43:02','YYYY-MM-DD HH24:MI:SS'),100,'PO_Invoice','Eingangsrechnung')
;

-- 03.09.2015 15:43
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Ref_List_ID=541121 AND NOT EXISTS (SELECT * FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 03.09.2015 15:43
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540562,TO_TIMESTAMP('2015-09-03 15:43:45','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','N','RelType C_Invoice_Candidate->C_Invoice (PO ONLY)',TO_TIMESTAMP('2015-09-03 15:43:45','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 03.09.2015 15:43
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540562 AND NOT EXISTS (SELECT * FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 03.09.2015 15:43
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Reference SET Name='RelType C_Invoice_Candidate->C_Invoice (SO ONLY)',Updated=TO_TIMESTAMP('2015-09-03 15:43:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540556
;

-- 03.09.2015 15:43
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Reference_Trl SET IsTranslated='N' WHERE AD_Reference_ID=540556
;

-- 03.09.2015 15:44
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET AD_Window_ID=167,Updated=TO_TIMESTAMP('2015-09-03 15:44:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540556
;

-- 03.09.2015 15:44
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy,WhereClause) VALUES (0,3484,0,540562,318,TO_TIMESTAMP('2015-09-03 15:44:39','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','N',TO_TIMESTAMP('2015-09-03 15:44:39','YYYY-MM-DD HH24:MI:SS'),100,'	
	
	EXISTS 
	(
		SELECT 1 FROM C_Invoice i
		INNER JOIN C_InvoiceLine il ON i.C_Invoice_ID = il.C_Invoice_ID
		INNER JOIN C_Invoice_Line_Alloc ila ON il.C_InvoiceLine_ID = ila.C_InvoiceLine_ID 
		INNER JOIN C_Invoice_Candidate ic on ila.C_Invoice_Candidate_ID = ic.C_Invoice_Candidate_ID
		WHERE ila.C_Invoice_Candidate_ID = @C_Invoice_Candidate_ID@
			AND C_Invoice.C_Invoice_ID = i.C_Invoice_ID
			AND i.IsSOTrx = ic.IsSOTrx
)')
;

-- 03.09.2015 15:44
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET AD_Window_ID=183,Updated=TO_TIMESTAMP('2015-09-03 15:44:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540562
;

-- 03.09.2015 15:44
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET Role_Source='PO_Invoice',Updated=TO_TIMESTAMP('2015-09-03 15:44:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540120
;

-- 03.09.2015 15:45
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Target_ID=540562,Updated=TO_TIMESTAMP('2015-09-03 15:45:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540120
;

-- 03.09.2015 15:46
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='	
	
	EXISTS 
	(
		SELECT 1 FROM C_Invoice i
		INNER JOIN C_InvoiceLine il ON i.C_Invoice_ID = il.C_Invoice_ID
		INNER JOIN C_Invoice_Line_Alloc ila ON il.C_InvoiceLine_ID = ila.C_InvoiceLine_ID 
		INNER JOIN C_Invoice_Candidate ic on ila.C_Invoice_Candidate_ID = ic.C_Invoice_Candidate_ID
		WHERE ila.C_Invoice_Candidate_ID = @C_Invoice_Candidate_ID@
			AND C_Invoice.C_Invoice_ID = i.C_Invoice_ID
			AND i.IsSOTrx = ic.IsSOTrx
			AND i.IsSOTrx = ''N''
)',Updated=TO_TIMESTAMP('2015-09-03 15:46:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540562
;

-- 03.09.2015 15:46
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='	
	
	EXISTS 
	(
		SELECT 1 FROM C_Invoice i
		INNER JOIN C_InvoiceLine il ON i.C_Invoice_ID = il.C_Invoice_ID
		INNER JOIN C_Invoice_Line_Alloc ila ON il.C_InvoiceLine_ID = ila.C_InvoiceLine_ID 
		INNER JOIN C_Invoice_Candidate ic on ila.C_Invoice_Candidate_ID = ic.C_Invoice_Candidate_ID
		WHERE ila.C_Invoice_Candidate_ID = @C_Invoice_Candidate_ID@
			AND C_Invoice.C_Invoice_ID = i.C_Invoice_ID
			AND i.IsSOTrx = ic.IsSOTrx
			AND i.IsSOTrx = ''Y''
)',Updated=TO_TIMESTAMP('2015-09-03 15:46:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540556
;

