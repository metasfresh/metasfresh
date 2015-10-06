-- 23.09.2015 16:50
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,Classname,CopyFromProcess,Created,CreatedBy,Description,EntityType,IsActive,IsBetaFunctionality,IsDirectPrint,IsOneInstanceOnly,IsReport,IsServerProcess,LockWaitTimeout,Name,RefreshAllAfterExecution,ShowHelp,Statistic_Count,Statistic_Seconds,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,540612,'de.metas.payment.process.C_Payment_DiscountAllocation_Process','N',TO_TIMESTAMP('2015-09-23 16:50:49','YYYY-MM-DD HH24:MI:SS'),100,NULL,'de.metas.swat','Y','N','N','N','N','N',0,'Offene Zahlung- Skonto Zuordnung','N','Y',0,0,'Java',TO_TIMESTAMP('2015-09-23 16:50:49','YYYY-MM-DD HH24:MI:SS'),100,'C_Payment_DiscountAllocation_Process')
;

-- 23.09.2015 16:50
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_ID=540612 AND NOT EXISTS (SELECT * FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 23.09.2015 16:51
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET AccessLevel='3', AD_Form_ID=NULL, AD_PrintFormat_ID=NULL, AD_ReportView_ID=NULL, AD_Workflow_ID=NULL, Classname='de.metas.invoice.process.C_Invoice_DiscountAllocation_Process', Description='Erstellt zu Rechnungen mit geringen offenen Beträgen Skonto-Zuordnungen, und markiert sie als bezahlt.', Help=NULL, IsBetaFunctionality='N', IsDirectPrint='N', IsReport='N', IsServerProcess='N', JasperReport=NULL, ProcedureName=NULL, ShowHelp='Y',Updated=TO_TIMESTAMP('2015-09-23 16:51:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540612
;

-- 23.09.2015 16:51
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET IsTranslated='N' WHERE AD_Process_ID=540612
;

-- 23.09.2015 16:51
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,1526,0,540612,540782,12,'OpenAmt',TO_TIMESTAMP('2015-09-23 16:51:37','YYYY-MM-DD HH24:MI:SS'),100,'Betrag in der Währung der jeweiligen Rechnung. Beispiel: 0.05','de.metas.swat',0,'Y','N','Y','Y','N','Maximaler offener Betrag',10,TO_TIMESTAMP('2015-09-23 16:51:37','YYYY-MM-DD HH24:MI:SS'),100)
;


-- 23.09.2015 16:51
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,267,0,540612,540783,15,'DateInvoiced',TO_TIMESTAMP('2015-09-23 16:51:38','YYYY-MM-DD HH24:MI:SS'),100,'Datum auf der Rechnung','de.metas.swat',0,'"Rechnungsdatum" bezeichnet das auf der Rechnung verwendete Datum.','Y','N','Y','N','Y','Rechnungsdatum',20,TO_TIMESTAMP('2015-09-23 16:51:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 23.09.2015 16:51
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,1106,0,540612,540784,20,'IsSOTrx',TO_TIMESTAMP('2015-09-23 16:51:38','YYYY-MM-DD HH24:MI:SS'),100,'This is a Sales Transaction','de.metas.swat',1,'The Sales Transaction checkbox indicates if this item is a Sales Transaction.','Y','N','N','N','N','Verkaufsrechnungen (SOTrx)',30,TO_TIMESTAMP('2015-09-23 16:51:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 23.09.2015 16:52
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Classname='de.metas.payment.process.C_Payment_DiscountAllocation_Process', Description=NULL, Name='Offene Zahlung - Skonto Zuordnung',Updated=TO_TIMESTAMP('2015-09-23 16:52:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540612
;

-- 23.09.2015 16:52
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET IsTranslated='N' WHERE AD_Process_ID=540612
;

-- 23.09.2015 16:52
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET Description='Betrag in der Währung der jeweiligen Zahlung. Beispiel: 0.05',Updated=TO_TIMESTAMP('2015-09-23 16:52:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=540782
;


-- 23.09.2015 16:53
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET AD_Element_ID=1297, ColumnName='DateTrx', Description='Vorgangsdatum', Help='Das "Vorgangsdatum" bezeichnet das Datum des Vorgangs.', Name='Vorgangsdatum',Updated=TO_TIMESTAMP('2015-09-23 16:53:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=540783
;


-- 23.09.2015 16:55
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Menu_ID,AD_Org_ID,AD_Process_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('P',0,540649,0,540612,TO_TIMESTAMP('2015-09-23 16:55:29','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','C_Payment_DiscountAllocation_Process','Y','N','N','N','Offene Zahlung - Skonto Zuordnung',TO_TIMESTAMP('2015-09-23 16:55:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 23.09.2015 16:55
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Menu_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Menu_ID=540649 AND NOT EXISTS (SELECT * FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 23.09.2015 16:55
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT  INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 540649, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=540649)
;


-- 23.09.2015 16:58
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET EntityType='de.metas.payment',Updated=TO_TIMESTAMP('2015-09-23 16:58:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540612
;

-- 23.09.2015 16:58
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET EntityType='de.metas.payment',Updated=TO_TIMESTAMP('2015-09-23 16:58:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=540782
;

-- 23.09.2015 16:58
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET EntityType='de.metas.payment',Updated=TO_TIMESTAMP('2015-09-23 16:58:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=540783
;

-- 23.09.2015 16:58
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET EntityType='de.metas.payment',Updated=TO_TIMESTAMP('2015-09-23 16:58:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=540784
;

