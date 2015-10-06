-- 17.09.2015 19:02
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsBetaFunctionality,IsDirectPrint,IsOneInstanceOnly,IsReport,IsServerProcess,JasperReport,LockWaitTimeout,Name,RefreshAllAfterExecution,ShowHelp,Statistic_Count,Statistic_Seconds,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,540609,'org.compiere.report.ReportStarter','N',TO_TIMESTAMP('2015-09-17 19:02:28','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.fresh','Y','N','N','N','Y','N','@PREFIX@de/metas/docs/sales/ordercheckup/report.jasper',0,'Bestellkontrolle','N','N',0,0,'Java',TO_TIMESTAMP('2015-09-17 19:02:28','YYYY-MM-DD HH24:MI:SS'),100,'C_Order_MFGWarehouse_Report')
;

-- 17.09.2015 19:02
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_ID=540609 AND NOT EXISTS (SELECT * FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 17.09.2015 19:04
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,187,0,540609,540772,30,'C_BPartner_ID',TO_TIMESTAMP('2015-09-17 19:04:56','YYYY-MM-DD HH24:MI:SS'),100,'Bezeichnet einen Geschäftspartner','de.metas.fresh',0,'Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.','Y','N','Y','N','N','N','Geschäftspartner',10,TO_TIMESTAMP('2015-09-17 19:04:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 17.09.2015 19:04
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_Para_ID=540772 AND NOT EXISTS (SELECT * FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 17.09.2015 19:05
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,269,0,540609,540773,15,'DatePromised',TO_TIMESTAMP('2015-09-17 19:05:08','YYYY-MM-DD HH24:MI:SS'),100,'Zugesagter Termin für diesen Auftrag','de.metas.fresh',0,'Der "Zugesagte Termin" gibt das Datum an, für den (wenn zutreffend) dieser Auftrag zugesagt wurde.','Y','N','Y','N','N','N','Zugesagter Termin',20,TO_TIMESTAMP('2015-09-17 19:05:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 17.09.2015 19:05
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_Para_ID=540773 AND NOT EXISTS (SELECT * FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 17.09.2015 19:29
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_PrintFormat (AD_Client_ID,AD_Org_ID,AD_PrintColor_ID,AD_PrintFont_ID,AD_Printformat_Group,AD_PrintFormat_ID,AD_PrintPaper_ID,AD_Table_ID,CreateCopy,Created,CreatedBy,FooterMargin,HeaderMargin,IsActive,IsDefault,IsForm,IsStandardHeaderFooter,IsTableBased,JasperProcess_ID,Name,Updated,UpdatedBy) VALUES (0,0,100,136,'SO',540045,102,540683,'N',TO_TIMESTAMP('2015-09-17 19:29:31','YYYY-MM-DD HH24:MI:SS'),100,0,0,'Y','N','N','Y','Y',540609,'C_Order_MFGWarehouse_Report',TO_TIMESTAMP('2015-09-17 19:29:31','YYYY-MM-DD HH24:MI:SS'),100)
;



































-- 18.09.2015 13:21
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET IsActive='N',Updated=TO_TIMESTAMP('2015-09-18 13:21:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540602
;

-- 18.09.2015 13:33
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Classname='de.metas.fresh.ordercheckup.process.C_Order_MFGWarehouse_Report_Generate', Value='C_Order_MFGWarehouse_Report_Generate',Updated=TO_TIMESTAMP('2015-09-18 13:33:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540603
;

-- 18.09.2015 13:36
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Process_Para_Trl WHERE AD_Process_Para_ID=540772
;

-- 18.09.2015 13:36
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Process_Para WHERE AD_Process_Para_ID=540772
;

-- 18.09.2015 13:36
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Process_Para_Trl WHERE AD_Process_Para_ID=540773
;

-- 18.09.2015 13:36
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Process_Para WHERE AD_Process_Para_ID=540773
;

-- 18.09.2015 13:36
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET IsActive='N',Updated=TO_TIMESTAMP('2015-09-18 13:36:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540457
;

-- 18.09.2015 13:37
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Value='C_Order_MFGWarehouse_Report_ForWarehouse',Updated=TO_TIMESTAMP('2015-09-18 13:37:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540601
;










/* NOTE: because there are a LOT of archives created for those 2 processes, Mark and me decided to keep them for history, but we just deactivate them
delete from AD_Table_Process where AD_Process_ID=540602 and AD_Table_ID=259;

-- 18.09.2015 13:40
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Process_Trl WHERE AD_Process_ID=540602
;

-- 18.09.2015 13:40
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Process WHERE AD_Process_ID=540602
;

-- 18.09.2015 13:50
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Process_Trl WHERE AD_Process_ID=540457
;

-- 18.09.2015 13:50
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Process WHERE AD_Process_ID=540457
;
*/
update AD_Process set IsActive='N' where AD_Process_ID in (540602, 540457) and IsActive='Y';
