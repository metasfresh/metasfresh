
--------------------------------------



-- 25.02.2016 19:01:02 OEZ
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,Classname,Created,CreatedBy,Description,EntityType,IsActive,IsBetaFunctionality,IsDirectPrint,IsOneInstanceOnly,IsReport,IsServerProcess,JasperReport,Name,ShowHelp,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,540661,'org.compiere.report.ReportStarter',TO_TIMESTAMP('2016-02-25 19:01:01','YYYY-MM-DD HH24:MI:SS'),100,'Summary Pdf  printing','de.metas.printing','Y','N','N','N','Y','N','resource:de/metas/reports/pdfprintingsummary/report.jasper','Summary Pdf  printing','Y','Java',TO_TIMESTAMP('2016-02-25 19:01:01','YYYY-MM-DD HH24:MI:SS'),100,'SummaryPdfPrinting')
;

-- 25.02.2016 19:01:02 OEZ
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_ID=540661 AND NOT EXISTS (SELECT * FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- -- 25.02.2016 19:01:02 OEZ
-- -- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- INSERT INTO AD_Process_Access (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Role_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,0,540661,0,TO_TIMESTAMP('2016-02-25 19:01:02','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2016-02-25 19:01:02','YYYY-MM-DD HH24:MI:SS'),100)
-- ;




-- -- 25.02.2016 19:01:02 OEZ
-- -- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- INSERT INTO AD_Process_Access (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Role_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (1000000,0,540661,1000038,TO_TIMESTAMP('2016-02-25 19:01:02','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2016-02-25 19:01:02','YYYY-MM-DD HH24:MI:SS'),100)
-- ;

-- -- 25.02.2016 19:01:02 OEZ
-- -- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- INSERT INTO AD_Process_Access (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Role_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (1000000,0,540661,1000000,TO_TIMESTAMP('2016-02-25 19:01:02','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2016-02-25 19:01:02','YYYY-MM-DD HH24:MI:SS'),100)
-- ;

-- 25.02.2016 19:01:46 OEZ
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,541955,0,540661,540905,30,'C_Print_Package_ID',TO_TIMESTAMP('2016-02-25 19:01:46','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.printing',0,'Y','Y','N','N','N','Print package',10,TO_TIMESTAMP('2016-02-25 19:01:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 25.02.2016 19:01:46 OEZ
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_Para_ID=540905 AND NOT EXISTS (SELECT * FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 25.02.2016 19:01:49 OEZ
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET IsMandatory='Y',Updated=TO_TIMESTAMP('2016-02-25 19:01:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=540905
;




-------------------------------------------------



-- 25.02.2016 19:02:02 OEZ
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,982,0,540661,540906,14,'Title',TO_TIMESTAMP('2016-02-25 19:02:02','YYYY-MM-DD HH24:MI:SS'),100,'Bezeichnung für diesen Eintrag','de.metas.printing',0,'"Titel" gibt die Bezeichnung für diesen Eintrag an.','Y','Y','N','Y','N','Titel',20,TO_TIMESTAMP('2016-02-25 19:02:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 25.02.2016 19:02:02 OEZ
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_Para_ID=540906 AND NOT EXISTS (SELECT * FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;




-- 29.02.2016 10:57:09 OEZ
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET AD_Element_ID=542012, ColumnName='C_Print_Job_Instructions_ID', Name='Druckanweisung',Updated=TO_TIMESTAMP('2016-02-29 10:57:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=540905
;

-- 29.02.2016 10:57:09 OEZ
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para_Trl SET IsTranslated='N' WHERE AD_Process_Para_ID=540905
;




------------


insert into AD_Message
values(543934,0,0,'Y','2016-08-09 17:21:34+03',100,'2016-08-09 17:21:34+03',100,'PDFPrintingAsyncBatchListener_PrintJob_Done_2','Pdf {0} von {1} wurde fertiggestellt. Die Datei enthält {2} Belege.','','I','de.metas.printing');


