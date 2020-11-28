-- 24.09.2015 18:23
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,Description,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540577,TO_TIMESTAMP('2015-09-24 18:23:39','YYYY-MM-DD HH24:MI:SS'),100,'Auftrag','de.metas.swat','Y','N','M_InOutSO DRAFT INCLUDED',TO_TIMESTAMP('2015-09-24 18:23:39','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 24.09.2015 18:23
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540577 AND NOT EXISTS (SELECT * FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 24.09.2015 18:24
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Display,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy,WhereClause) VALUES (0,3791,3521,0,540577,319,TO_TIMESTAMP('2015-09-24 18:24:38','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','N',TO_TIMESTAMP('2015-09-24 18:24:38','YYYY-MM-DD HH24:MI:SS'),100,' 1=2')
;

-- 24.09.2015 18:24
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Reference SET Description=NULL,Updated=TO_TIMESTAMP('2015-09-24 18:24:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540577
;

-- 24.09.2015 18:24
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Reference_Trl SET IsTranslated='N' WHERE AD_Reference_ID=540577
;

-- 24.09.2015 18:26
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Source_ID,AD_Reference_Target_ID,AD_RelationType_ID,Created,CreatedBy,Description,EntityType,IsActive,IsDirected,IsExplicit,Name,Role_Source,Role_Target,Updated,UpdatedBy) VALUES (0,0,540577,540565,540128,TO_TIMESTAMP('2015-09-24 18:26:06','YYYY-MM-DD HH24:MI:SS'),100,NULL,'de.metas.swat','Y','Y','N','M_InOut -> Invoice (SO)','Invoice','Order',TO_TIMESTAMP('2015-09-24 18:26:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 24.09.2015 18:26
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540578,TO_TIMESTAMP('2015-09-24 18:26:38','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','N','RelType M_InOut -> Invoice SO',TO_TIMESTAMP('2015-09-24 18:26:38','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 24.09.2015 18:26
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540578 AND NOT EXISTS (SELECT * FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 24.09.2015 18:27
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Display,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy) VALUES (0,3492,3484,0,540578,318,TO_TIMESTAMP('2015-09-24 18:27:38','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','N',TO_TIMESTAMP('2015-09-24 18:27:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 25.09.2015 12:07
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET AD_Window_ID=167, WhereClause='ISSOTRX = ''Y''',Updated=TO_TIMESTAMP('2015-09-25 12:07:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540578
;

-- 25.09.2015 12:08
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Target_ID=540578, Role_Source=NULL, Role_Target=NULL,Updated=TO_TIMESTAMP('2015-09-25 12:08:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540128
;

-- 25.09.2015 12:08
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Reference SET Description='false condition', Name='M_InOut DRAFT INCLUDED',Updated=TO_TIMESTAMP('2015-09-25 12:08:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540577
;

-- 25.09.2015 12:08
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Reference_Trl SET IsTranslated='N' WHERE AD_Reference_ID=540577
;

-- 25.09.2015 12:09
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540579,TO_TIMESTAMP('2015-09-25 12:09:06','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','N','RelType M_InOut -> Invoice PO',TO_TIMESTAMP('2015-09-25 12:09:06','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 25.09.2015 12:09
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540579 AND NOT EXISTS (SELECT * FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 25.09.2015 12:24
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Source_ID,AD_Reference_Target_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsDirected,IsExplicit,Name,Updated,UpdatedBy) VALUES (0,0,540577,540578,540129,TO_TIMESTAMP('2015-09-25 12:24:24','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','Y','N','M_InOut -> Invoice (PO)',TO_TIMESTAMP('2015-09-25 12:24:24','YYYY-MM-DD HH24:MI:SS'),100)
;






UPDATE AD_RelationType 
SET  AD_Reference_TARGET_ID=540579
WHERE AD_RelationType_ID=540129;




-- 25.09.2015 14:31
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Display,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy,WhereClause) VALUES (0,3492,3484,0,540579,318,TO_TIMESTAMP('2015-09-25 14:31:11','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','N',TO_TIMESTAMP('2015-09-25 14:31:11','YYYY-MM-DD HH24:MI:SS'),100,'ISSOTRX = ''N''')
;

-- 25.09.2015 14:32
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsDirected,IsExplicit,Name,Updated,UpdatedBy) VALUES (0,0,540130,TO_TIMESTAMP('2015-09-25 14:32:14','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','N','N','C_Invoice -> M_InOut (SO)',TO_TIMESTAMP('2015-09-25 14:32:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 25.09.2015 14:54
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Source_ID=540571,Updated=TO_TIMESTAMP('2015-09-25 14:54:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540130
;

-- 25.09.2015 14:54
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Reference SET Name='C_Invoice SO TO Order DRAFT INCLUDED',Updated=TO_TIMESTAMP('2015-09-25 14:54:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540571
;

-- 25.09.2015 14:54
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Reference_Trl SET IsTranslated='N' WHERE AD_Reference_ID=540571
;

-- 25.09.2015 14:55
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Reference SET Name='C_Invoice SO -> Order DRAFT INCLUDED',Updated=TO_TIMESTAMP('2015-09-25 14:55:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540571
;

-- 25.09.2015 14:55
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Reference_Trl SET IsTranslated='N' WHERE AD_Reference_ID=540571
;

-- 25.09.2015 15:10
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,Description,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540583,TO_TIMESTAMP('2015-09-25 15:10:08','YYYY-MM-DD HH24:MI:SS'),100,'Ausgangsrechnung','de.metas.swat','Y','N','C_Invoice SO -> M_InOut DRAFT INCLUDED',TO_TIMESTAMP('2015-09-25 15:10:08','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 25.09.2015 15:10
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540583 AND NOT EXISTS (SELECT * FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 25.09.2015 15:11
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy) VALUES (0,3484,0,540583,318,TO_TIMESTAMP('2015-09-25 15:11:05','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','N',TO_TIMESTAMP('2015-09-25 15:11:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 25.09.2015 15:15
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='C_Invoice.IsSOTrx=''Y'' 
AND
EXISTS
(
SELECT 1
FROM M_InOut io
LEFT JOIN M_InOutLine iol ON io.M_InOut_ID = iol.M_InOut_ID
LEFT JOIN C_InvoiceLine il ON il.M_InOutline_ID = iol.M_InOutline_ID
WHERE
	(il.C_Invoice_ID=@C_Invoice_ID/-1@)
	OR
	(io.M_InOut_ID = @M_InOut_ID/-1@)
AND C_Invoice.IsSOTrx = io.IsSOTrx
AND C_Invoice.IsSOTrx = ''Y''
AND il.C_Invoice_ID = C_Invoice.C_Invoice_ID
)',Updated=TO_TIMESTAMP('2015-09-25 15:15:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540583
;

-- 25.09.2015 15:15
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Source_ID=540583,Updated=TO_TIMESTAMP('2015-09-25 15:15:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540124
;

-- 25.09.2015 15:32
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540584,TO_TIMESTAMP('2015-09-25 15:32:40','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','N','RelType C_Invoice -> M_InOut (SO)',TO_TIMESTAMP('2015-09-25 15:32:40','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 25.09.2015 15:32
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540584 AND NOT EXISTS (SELECT * FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 25.09.2015 16:44
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy,WhereClause) VALUES (0,3521,0,540584,319,TO_TIMESTAMP('2015-09-25 16:44:57','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','N',TO_TIMESTAMP('2015-09-25 16:44:57','YYYY-MM-DD HH24:MI:SS'),100,'
EXISTS
(
SELECT 1
	FROM C_Invoice i
	JOIN C_InvoiceLine il on i.C_Invoice_ID = il.C_Invoice_ID
	JOIN M_InOutLine iol on il.M_InOutline_ID = iol.M_InOutline_ID
	JOIN M_InOut io on iol.M_InOut_ID = io.M_InOut_ID

	WHERE 
		
		M_InOut.M_InOut_ID = io.M_InOut_ID
		AND 
		(
		i.C_Invoice_ID = @C_Invoice_ID/-1@
		OR
		M_InOut.M_InOut_ID = @M_InOut_ID/-1@
		)
		AND i.IsSOTrx = M_InOut.IsSOTrx
		AND i.IsSOTrx = ''Y''
)')
;

-- 25.09.2015 16:45
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Target_ID=540584,Updated=TO_TIMESTAMP('2015-09-25 16:45:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540124
;

-- 25.09.2015 16:46
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Source_ID=540571,Updated=TO_TIMESTAMP('2015-09-25 16:46:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540124
;

-- 25.09.2015 16:46
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Source_ID=540583,Updated=TO_TIMESTAMP('2015-09-25 16:46:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540130
;

-- 25.09.2015 16:47
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Target_ID=540567,Updated=TO_TIMESTAMP('2015-09-25 16:47:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540124
;

-- 25.09.2015 16:47
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Target_ID=540584,Updated=TO_TIMESTAMP('2015-09-25 16:47:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540130
;

-- 25.09.2015 16:47
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsDirected,IsExplicit,Name,Updated,UpdatedBy) VALUES (0,0,540131,TO_TIMESTAMP('2015-09-25 16:47:40','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','N','N','C_Invoice -> M_InOut (PO)',TO_TIMESTAMP('2015-09-25 16:47:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 25.09.2015 16:52
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,Description,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540585,TO_TIMESTAMP('2015-09-25 16:52:17','YYYY-MM-DD HH24:MI:SS'),100,'Ausgangsrechnung','de.metas.swat','Y','N','C_Invoice PO -> M_InOut DRAFT INCLUDED',TO_TIMESTAMP('2015-09-25 16:52:17','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 25.09.2015 16:52
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540585 AND NOT EXISTS (SELECT * FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 25.09.2015 16:53
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy,WhereClause) VALUES (0,3484,0,540585,318,TO_TIMESTAMP('2015-09-25 16:53:01','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','N',TO_TIMESTAMP('2015-09-25 16:53:01','YYYY-MM-DD HH24:MI:SS'),100,'C_Invoice.IsSOTrx=''N'' 
AND
EXISTS
(
SELECT 1
FROM M_InOut io
LEFT JOIN M_InOutLine iol ON io.M_InOut_ID = iol.M_InOut_ID
LEFT JOIN C_InvoiceLine il ON il.M_InOutline_ID = iol.M_InOutline_ID
WHERE
	(il.C_Invoice_ID=@C_Invoice_ID/-1@)
	OR
	(io.M_InOut_ID = @M_InOut_ID/-1@)
AND C_Invoice.IsSOTrx = io.IsSOTrx
AND C_Invoice.IsSOTrx = ''N''
AND il.C_Invoice_ID = C_Invoice.C_Invoice_ID
)')
;

-- 25.09.2015 16:53
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Source_ID=NULL,Updated=TO_TIMESTAMP('2015-09-25 16:53:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540130
;

-- 25.09.2015 16:53
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Source_ID=540583,Updated=TO_TIMESTAMP('2015-09-25 16:53:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540130
;

-- 25.09.2015 16:53
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Source_ID=540585,Updated=TO_TIMESTAMP('2015-09-25 16:53:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540131
;

-- 25.09.2015 16:57
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540586,TO_TIMESTAMP('2015-09-25 16:57:07','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','N','RelType C_Invoice -> M_InOut (PO)',TO_TIMESTAMP('2015-09-25 16:57:07','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 25.09.2015 16:57
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540586 AND NOT EXISTS (SELECT * FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 25.09.2015 16:57
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy,WhereClause) VALUES (0,3521,0,540586,319,TO_TIMESTAMP('2015-09-25 16:57:39','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','N',TO_TIMESTAMP('2015-09-25 16:57:39','YYYY-MM-DD HH24:MI:SS'),100,'
EXISTS
(
SELECT 1
	FROM C_Invoice i
	JOIN C_InvoiceLine il on i.C_Invoice_ID = il.C_Invoice_ID
	JOIN M_InOutLine iol on il.M_InOutline_ID = iol.M_InOutline_ID
	JOIN M_InOut io on iol.M_InOut_ID = io.M_InOut_ID

	WHERE 
		
		M_InOut.M_InOut_ID = io.M_InOut_ID
		AND 
		(
		i.C_Invoice_ID = @C_Invoice_ID/-1@
		OR
		M_InOut.M_InOut_ID = @M_InOut_ID/-1@
		)
		AND i.IsSOTrx = M_InOut.IsSOTrx
		AND i.IsSOTrx = ''N''
)')
;

-- 25.09.2015 16:58
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Target_ID=540586,Updated=TO_TIMESTAMP('2015-09-25 16:58:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540131
;

-- 25.09.2015 16:58
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET AD_Window_ID=184,Updated=TO_TIMESTAMP('2015-09-25 16:58:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540586
;

-- 25.09.2015 16:59
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET AD_Window_ID=169,Updated=TO_TIMESTAMP('2015-09-25 16:59:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540584
;

-- 25.09.2015 16:59
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET AD_Window_ID=183,Updated=TO_TIMESTAMP('2015-09-25 16:59:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540579
;

-- 25.09.2015 17:19
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET AD_Window_ID=184,Updated=TO_TIMESTAMP('2015-09-25 17:19:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540577
;

-- 25.09.2015 17:19
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET AD_Window_ID=NULL,Updated=TO_TIMESTAMP('2015-09-25 17:19:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540577
;

-- 25.09.2015 17:43
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='c_invoice.ISSOTRX =@IsSoTrx@',Updated=TO_TIMESTAMP('2015-09-25 17:43:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540578
;

-- 25.09.2015 17:44
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='c_invoice.ISSOTRX =@IsSoTrx@',Updated=TO_TIMESTAMP('2015-09-25 17:44:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540579
;

-- 25.09.2015 17:46
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,Description,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540587,TO_TIMESTAMP('2015-09-25 17:46:19','YYYY-MM-DD HH24:MI:SS'),100,'false condition','de.metas.swat','Y','N','M_InOut PO DRAFT INCLUDED',TO_TIMESTAMP('2015-09-25 17:46:19','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 25.09.2015 17:46
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540587 AND NOT EXISTS (SELECT * FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 25.09.2015 17:49
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy,WhereClause) VALUES (0,3521,0,540587,319,184,TO_TIMESTAMP('2015-09-25 17:49:44','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','N',TO_TIMESTAMP('2015-09-25 17:49:44','YYYY-MM-DD HH24:MI:SS'),100,'1=2')
;

-- 25.09.2015 17:50
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Source_ID=540587,Updated=TO_TIMESTAMP('2015-09-25 17:50:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540129
;

-- 25.09.2015 17:50
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Reference SET Name='M_InOut SO DRAFT INCLUDED',Updated=TO_TIMESTAMP('2015-09-25 17:50:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540577
;

-- 25.09.2015 17:50
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Reference_Trl SET IsTranslated='N' WHERE AD_Reference_ID=540577
;

-- 25.09.2015 17:50
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET AD_Window_ID=169,Updated=TO_TIMESTAMP('2015-09-25 17:50:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540577
;

-- 25.09.2015 17:50
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET AD_Window_ID=167,Updated=TO_TIMESTAMP('2015-09-25 17:50:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540583
;

-- 25.09.2015 17:50
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET AD_Window_ID=183,Updated=TO_TIMESTAMP('2015-09-25 17:50:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540585
;







-- 28.09.2015 12:21
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='C_Invoice.IsSOTrx=@IsSoTrx@
AND
EXISTS
(
SELECT 1
FROM M_InOut io
LEFT JOIN M_InOutLine iol ON io.M_InOut_ID = iol.M_InOut_ID
LEFT JOIN C_InvoiceLine il ON il.M_InOutline_ID = iol.M_InOutline_ID
WHERE
	(il.C_Invoice_ID=@C_Invoice_ID/-1@)
	OR
	(io.M_InOut_ID = @M_InOut_ID/-1@)
AND C_Invoice.IsSOTrx = io.IsSOTrx
AND C_Invoice.IsSOTrx = @IsSoTrx@
AND il.C_Invoice_ID = C_Invoice.C_Invoice_ID
)',Updated=TO_TIMESTAMP('2015-09-28 12:21:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540583
;

-- 28.09.2015 13:13
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET Name='C_Order -> Invoice (SO)',Updated=TO_TIMESTAMP('2015-09-28 13:13:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540128
;

-- 28.09.2015 13:15
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='
EXISTS
(
	SELECT 1
	FROM C_Invoice i
	JOIN C_InvoiceLine il on i.C_Invoice_ID = il.C_Invoice_ID
	JOIN C_OrderLine ol on il.C_OrderLine_ID = ol.C_OrderLine_ID

	WHERE 
		
		C_Order.C_Order_ID = ol.C_Order_ID
		AND 
		(
		i.C_Invoice_ID = @C_Invoice_ID/-1@
		OR
		C_Order.C_Order_ID = @C_Order_ID/-1@
		)
		AND i.IsSOTrx = C_Order.IsSOTrx
		AND i.IsSOTrx = ''Y''
	
)',Updated=TO_TIMESTAMP('2015-09-28 13:15:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540567
;

-- 28.09.2015 13:19
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='EXISTS
(
SELECT 1
FROM M_InOut io
LEFT JOIN M_InOutLine iol ON io.M_InOut_ID = iol.M_InOut_ID
LEFT JOIN C_InvoiceLine il ON il.M_InOutline_ID = iol.M_InOutline_ID
WHERE
	(il.C_Invoice_ID=@C_Invoice_ID/-1@)
	OR
	(io.M_InOut_ID = @M_InOut_ID/-1@)
AND C_Invoice.IsSOTrx = io.IsSOTrx
AND C_Invoice.IsSOTrx = ''N''
AND il.C_Invoice_ID = C_Invoice.C_Invoice_ID
)',Updated=TO_TIMESTAMP('2015-09-28 13:19:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540585
;

-- 28.09.2015 13:56
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='
EXISTS
(
SELECT 1
FROM M_InOut io
LEFT JOIN M_InOutLine iol ON io.M_InOut_ID = iol.M_InOut_ID
LEFT JOIN C_InvoiceLine il ON il.M_InOutline_ID = iol.M_InOutline_ID
JOIN C_Invoice i ON il.c_invoice_ID = i.C_Invoice_ID
WHERE
	(i.C_Invoice_ID=@C_Invoice_ID/-1@)
	OR
	(io.M_InOut_ID = @M_InOut_ID/-1@)
AND i.C_Invoice_ID = C_Invoice.C_Invoice_ID
AND C_Invoice.IsSOTrx = io.IsSOTrx
AND C_Invoice.IsSOTrx = @IsSoTrx@
)',Updated=TO_TIMESTAMP('2015-09-28 13:56:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540583
;

-- 28.09.2015 13:58
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET Name='M_InOut -> Invoice (SO)',Updated=TO_TIMESTAMP('2015-09-28 13:58:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540128
;

-- 28.09.2015 15:05
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET IsActive='N',Updated=TO_TIMESTAMP('2015-09-28 15:05:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=1000003
;
-- 28.09.2015 16:20
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='
EXISTS
(
SELECT 1
FROM M_InOut io
LEFT JOIN M_InOutLine iol ON io.M_InOut_ID = iol.M_InOut_ID
LEFT JOIN C_InvoiceLine il ON il.M_InOutline_ID = iol.M_InOutline_ID
JOIN C_Invoice i ON il.c_invoice_ID = i.C_Invoice_ID
WHERE
	(i.C_Invoice_ID=@C_Invoice_ID/-1@)
	OR
	(io.M_InOut_ID = @M_InOut_ID/-1@)

)',Updated=TO_TIMESTAMP('2015-09-28 16:20:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540583
;

-- 28.09.2015 16:23
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='EXISTS
(
SELECT 1
FROM M_InOut io
LEFT JOIN M_InOutLine iol ON io.M_InOut_ID = iol.M_InOut_ID
LEFT JOIN C_InvoiceLine il ON il.M_InOutline_ID = iol.M_InOutline_ID
WHERE
	
	(io.M_InOut_ID = @M_InOut_ID/-1@)
AND C_Invoice.IsSOTrx = io.IsSOTrx
AND C_Invoice.IsSOTrx = ''N''
AND il.C_Invoice_ID = C_Invoice.C_Invoice_ID
)',Updated=TO_TIMESTAMP('2015-09-28 16:23:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540585
;





-- 28.09.2015 16:23
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='
EXISTS
(
SELECT 1
	FROM C_Invoice i
	JOIN C_InvoiceLine il on i.C_Invoice_ID = il.C_Invoice_ID
	JOIN M_InOutLine iol on il.M_InOutline_ID = iol.M_InOutline_ID
	JOIN M_InOut io on iol.M_InOut_ID = io.M_InOut_ID

	WHERE 
		
		M_InOut.M_InOut_ID = io.M_InOut_ID
		AND 
		(
		
		M_InOut.M_InOut_ID = @M_InOut_ID/-1@
		)
		AND i.IsSOTrx = M_InOut.IsSOTrx
		AND i.IsSOTrx = ''N''
)',Updated=TO_TIMESTAMP('2015-09-28 16:23:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540586
;

-- 28.09.2015 16:29
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='
EXISTS
(
SELECT 1
FROM M_InOut io
LEFT JOIN M_InOutLine iol ON io.M_InOut_ID = iol.M_InOut_ID
LEFT JOIN C_InvoiceLine il ON il.M_InOutline_ID = iol.M_InOutline_ID
JOIN C_Invoice i ON il.c_invoice_ID = i.C_Invoice_ID
WHERE
	(i.C_Invoice_ID=@C_Invoice_ID/-1@)
	
	

)',Updated=TO_TIMESTAMP('2015-09-28 16:29:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540583
;




-- 28.09.2015 16:31
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='
EXISTS
(
SELECT 1
FROM M_InOut io
LEFT JOIN M_InOutLine iol ON io.M_InOut_ID = iol.M_InOut_ID
LEFT JOIN C_InvoiceLine il ON il.M_InOutline_ID = iol.M_InOutline_ID
JOIN C_Invoice i ON il.c_invoice_ID = i.C_Invoice_ID
WHERE
	(i.C_Invoice_ID=@C_Invoice_ID/-1@)
)',Updated=TO_TIMESTAMP('2015-09-28 16:31:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540583
;

-- 28.09.2015 16:32
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='C_Invoice.IsSOTrx=''Y'' 
AND

EXISTS
(
SELECT 1
FROM M_InOut io
LEFT JOIN M_InOutLine iol ON io.M_InOut_ID = iol.M_InOut_ID
LEFT JOIN C_InvoiceLine il ON il.M_InOutline_ID = iol.M_InOutline_ID
JOIN C_Invoice i ON il.c_invoice_ID = i.C_Invoice_ID
WHERE
	(i.C_Invoice_ID=@C_Invoice_ID/-1@)
)',Updated=TO_TIMESTAMP('2015-09-28 16:32:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540583
;

-- 28.09.2015 16:33
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='
EXISTS
(
SELECT 1
	FROM C_Invoice i
	JOIN C_InvoiceLine il on i.C_Invoice_ID = il.C_Invoice_ID
	JOIN M_InOutLine iol on il.M_InOutline_ID = iol.M_InOutline_ID
	JOIN M_InOut io on iol.M_InOut_ID = io.M_InOut_ID

	WHERE 
		
		M_InOut.M_InOut_ID = io.M_InOut_ID
		AND 
		(
		
		M_InOut.M_InOut_ID = @M_InOut_ID/-1@
		)
		AND i.IsSOTrx = M_InOut.IsSOTrx
		AND i.IsSOTrx = ''Y''
)',Updated=TO_TIMESTAMP('2015-09-28 16:33:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540584
;

-- 28.09.2015 16:37
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='M_InOut.IsSoTrx = @IsSoTrx@',Updated=TO_TIMESTAMP('2015-09-28 16:37:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540577
;

-- 28.09.2015 16:37
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET IsDirected='N',Updated=TO_TIMESTAMP('2015-09-28 16:37:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540128
;

-- 28.09.2015 16:38
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET IsDirected='Y',Updated=TO_TIMESTAMP('2015-09-28 16:38:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540128
;

-- 28.09.2015 16:40
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='
EXISTS
(
	SELECT 1 
		FROM C_Invoice i
		JOIN C_InvoiceLine il on i.C_Invoice_ID = il.C_Invoice_ID
		JOIN M_InOutLine iol on il.M_InOutline_ID = iol.M_InOutline_ID
		JOIN M_InOut io on iol.M_InOut_ID = io.M_InOut_ID
		WHERE 
		
		M_InOut.M_InOut_ID = io.M_InOut_ID
		and io. M_InOut_ID = @M_InOut_ID/-1@
)',Updated=TO_TIMESTAMP('2015-09-28 16:40:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540583
;

-- 28.09.2015 16:41
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='
EXISTS
(
	SELECT 1 
		FROM C_Invoice i
		JOIN C_InvoiceLine il on i.C_Invoice_ID = il.C_Invoice_ID
		JOIN M_InOutLine iol on il.M_InOutline_ID = iol.M_InOutline_ID
		JOIN M_InOut io on iol.M_InOut_ID = io.M_InOut_ID
		WHERE 
		
		M_InOut.M_InOut_ID = io.M_InOut_ID
		and io.M_InOut_ID = @M_InOut_ID/-1@
)',Updated=TO_TIMESTAMP('2015-09-28 16:41:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540583
;

-- 28.09.2015 16:41
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='
EXISTS
(
	SELECT 1 
		FROM C_Invoice i
		JOIN C_InvoiceLine il on i.C_Invoice_ID = il.C_Invoice_ID
		JOIN M_InOutLine iol on il.M_InOutline_ID = iol.M_InOutline_ID
		JOIN M_InOut io on iol.M_InOut_ID = io.M_InOut_ID
		WHERE 
		
		C_Invoice.C_Invoice_ID = i.C_Invoice_ID
		and io.M_InOut_ID = @M_InOut_ID/-1@
)',Updated=TO_TIMESTAMP('2015-09-28 16:41:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540583
;

-- 28.09.2015 16:43
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='
EXISTS
(
	SELECT 1 
		FROM C_Invoice i
		JOIN C_InvoiceLine il on i.C_Invoice_ID = il.C_Invoice_ID
		JOIN M_InOutLine iol on il.M_InOutline_ID = iol.M_InOutline_ID
		JOIN M_InOut io on iol.M_InOut_ID = io.M_InOut_ID
		WHERE 
		
		C_Invoice.C_Invoice_ID = i.C_Invoice_ID
		
)',Updated=TO_TIMESTAMP('2015-09-28 16:43:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540583
;









-- 28.09.2015 16:46
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='
EXISTS
(
	SELECT 1 
		FROM C_Invoice i
		JOIN C_InvoiceLine il on i.C_Invoice_ID = il.C_Invoice_ID
		JOIN M_InOutLine iol on il.M_InOutline_ID = iol.M_InOutline_ID
		JOIN M_InOut io on iol.M_InOut_ID = io.M_InOut_ID
		WHERE 
		
		C_Invoice.C_Invoice_ID = i.C_Invoice_ID
		AND
(
i.C_Invoice_ID = @C_Invoice_ID/-1@
OR
io.M_InOut_ID = @M_InOut_ID/-1@
)
		
)',Updated=TO_TIMESTAMP('2015-09-28 16:46:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540583
;





-- 28.09.2015 17:46
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='EXISTS
(
SELECT 1
FROM M_InOut io
LEFT JOIN M_InOutLine iol ON io.M_InOut_ID = iol.M_InOut_ID
LEFT JOIN C_InvoiceLine il ON il.M_InOutline_ID = iol.M_InOutline_ID
JOIN C_Invoice i on il.C_Invoice_ID = i.C_Invoice_ID
WHERE
	(i.C_Invoice_ID = @C_Invoice_ID/-1@
	OR
	io.M_InOut_ID = @M_InOut_ID/-1@)
AND i = io.IsSOTrx
AND i.IsSOTrx = ''N''
AND il.C_Invoice_ID =i.C_Invoice_ID
)',Updated=TO_TIMESTAMP('2015-09-28 17:46:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540585
;

-- 28.09.2015 17:47
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='EXISTS
(
SELECT 1
FROM M_InOut io
LEFT JOIN M_InOutLine iol ON io.M_InOut_ID = iol.M_InOut_ID
LEFT JOIN C_InvoiceLine il ON il.M_InOutline_ID = iol.M_InOutline_ID
JOIN C_Invoice i on il.C_Invoice_ID = i.C_Invoice_ID
WHERE
	(i.C_Invoice_ID = @C_Invoice_ID/-1@
	OR
	io.M_InOut_ID = @M_InOut_ID/-1@)
AND i.IsSoTrx = io.IsSOTrx
AND i.IsSOTrx = ''N''
AND il.C_Invoice_ID =i.C_Invoice_ID
)',Updated=TO_TIMESTAMP('2015-09-28 17:47:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540585
;

-- 28.09.2015 17:49
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='EXISTS
(
SELECT 1
FROM M_InOut io
LEFT JOIN M_InOutLine iol ON io.M_InOut_ID = iol.M_InOut_ID
LEFT JOIN C_InvoiceLine il ON il.M_InOutline_ID = iol.M_InOutline_ID
JOIN C_Invoice i on il.C_Invoice_ID = i.C_Invoice_ID
WHERE
	(i.C_Invoice_ID = @C_Invoice_ID/-1@
	)
AND i.IsSoTrx = io.IsSOTrx
AND i.IsSOTrx = ''N''

)',Updated=TO_TIMESTAMP('2015-09-28 17:49:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540585
;

-- 28.09.2015 17:53
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='
EXISTS
(
	SELECT 1 
		FROM C_Invoice i
		JOIN C_InvoiceLine il on i.C_Invoice_ID = il.C_Invoice_ID
		JOIN M_InOutLine iol on il.M_InOutline_ID = iol.M_InOutline_ID
		JOIN M_InOut io on iol.M_InOut_ID = io.M_InOut_ID
		WHERE 
		
		C_Invoice.C_Invoice_ID = i.C_Invoice_ID
		AND
(
i.C_Invoice_ID = @C_Invoice_ID/-1@
OR
io.M_InOut_ID = @M_InOut_ID/-1@
)
		
)',Updated=TO_TIMESTAMP('2015-09-28 17:53:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540585
;

-- 28.09.2015 17:54
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='M_InOut.IsSoTrx = @IsSoTrx@',Updated=TO_TIMESTAMP('2015-09-28 17:54:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540587
;

-- 28.09.2015 17:58
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='
EXISTS
(
	SELECT 1 
		FROM C_Invoice i
		JOIN C_InvoiceLine il on i.C_Invoice_ID = il.C_Invoice_ID
		JOIN M_InOutLine iol on il.M_InOutline_ID = iol.M_InOutline_ID
		JOIN M_InOut io on iol.M_InOut_ID = io.M_InOut_ID
		WHERE 
		
		C_Invoice.C_Invoice_ID = i.C_Invoice_ID
		AND
(
i.C_Invoice_ID = @C_Invoice_ID/-1@
OR
io.M_InOut_ID = @M_InOut_ID/-1@
)
AND io.issotrx = i.issotrx
and io.issotrx = ''Y''
)',Updated=TO_TIMESTAMP('2015-09-28 17:58:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540583
;

-- 28.09.2015 17:58
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='
EXISTS
(
	SELECT 1 
		FROM C_Invoice i
		JOIN C_InvoiceLine il on i.C_Invoice_ID = il.C_Invoice_ID
		JOIN M_InOutLine iol on il.M_InOutline_ID = iol.M_InOutline_ID
		JOIN M_InOut io on iol.M_InOut_ID = io.M_InOut_ID
		WHERE 
		
		C_Invoice.C_Invoice_ID = i.C_Invoice_ID
		AND
(
i.C_Invoice_ID = @C_Invoice_ID/-1@
OR
io.M_InOut_ID = @M_InOut_ID/-1@
)
	
AND io.issotrx = i.issotrx
and io.issotrx = ''N''	
)',Updated=TO_TIMESTAMP('2015-09-28 17:58:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540585
;

-- 28.09.2015 18:03
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='
EXISTS
(
SELECT 1
	FROM C_Invoice i
	JOIN C_InvoiceLine il on i.C_Invoice_ID = il.C_Invoice_ID
	JOIN M_InOutLine iol on il.M_InOutline_ID = iol.M_InOutline_ID
	JOIN M_InOut io on iol.M_InOut_ID = io.M_InOut_ID

	WHERE 
		
		M_InOut.M_InOut_ID = io.M_InOut_ID
		AND 
		(
		i.C_Invoice_ID = @C_Invoice_ID/-1@ OR
		io.M_InOut_ID = @M_InOut_ID/-1@
		)
		AND i.IsSOTrx = M_InOut.IsSOTrx
		AND i.IsSOTrx = ''N''
)',Updated=TO_TIMESTAMP('2015-09-28 18:03:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540586
;






