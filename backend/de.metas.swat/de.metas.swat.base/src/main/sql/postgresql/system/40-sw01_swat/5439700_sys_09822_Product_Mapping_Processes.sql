

-- 12.02.2016 18:09
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsBetaFunctionality,IsDirectPrint,IsOneInstanceOnly,IsReport,IsServerProcess,LockWaitTimeout,Name,RefreshAllAfterExecution,ShowHelp,Statistic_Count,Statistic_Seconds,Type,Updated,UpdatedBy,Value) VALUES ('7',0,0,540657,'Y','de.metas.product.process.M_Product_Create_Mappings_Process','N',TO_TIMESTAMP('2016-02-12 18:09:12','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.product','Y','N','N','N','N','N',0,'M_Product_Create_Mappings_Process','N','Y',0,0,'Java',TO_TIMESTAMP('2016-02-12 18:09:12','YYYY-MM-DD HH24:MI:SS'),100,'10000002')
;

-- 12.02.2016 18:09
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_ID=540657 AND NOT EXISTS (SELECT * FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;



-- 12.02.2016 18:10
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,542976,0,'AD_Org_Target_ID',TO_TIMESTAMP('2016-02-12 18:10:25','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.product','Y','AD_Org_Target_ID','AD_Org_Target_ID',TO_TIMESTAMP('2016-02-12 18:10:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 12.02.2016 18:10
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=542976 AND NOT EXISTS (SELECT * FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 12.02.2016 18:12
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540637,TO_TIMESTAMP('2016-02-12 18:12:05','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.product','Y','N','AD_Org_Different_From_Current',TO_TIMESTAMP('2016-02-12 18:12:05','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 12.02.2016 18:12
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540637 AND NOT EXISTS (SELECT * FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 12.02.2016 18:13
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Display,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,OrderByClause,Updated,UpdatedBy,WhereClause) VALUES (0,522,528,0,540637,155,TO_TIMESTAMP('2016-02-12 18:13:02','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.product','Y','N','AD_Org.Value',TO_TIMESTAMP('2016-02-12 18:13:02','YYYY-MM-DD HH24:MI:SS'),100,NULL)
;

-- 12.02.2016 18:14
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='AD_Org.AD_Org_ID<>@AD_Org_ID/-1@',Updated=TO_TIMESTAMP('2016-02-12 18:14:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540637
;

-- 12.02.2016 18:14
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Reference_Value_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,542976,0,540657,540897,18,540637,'AD_Org_Target_ID',TO_TIMESTAMP('2016-02-12 18:14:22','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.product',0,'Y','N','Y','N','N','N','AD_Org_Target_ID',10,TO_TIMESTAMP('2016-02-12 18:14:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 12.02.2016 18:14
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_Para_ID=540897 AND NOT EXISTS (SELECT * FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 12.02.2016 18:14
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET IsMandatory='Y',Updated=TO_TIMESTAMP('2016-02-12 18:14:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=540897
;

-- 12.02.2016 18:15
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,542977,0,'M_Product_Target_ID',TO_TIMESTAMP('2016-02-12 18:15:06','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.product','Y','M_Product_Target_ID','M_Product_Target_ID',TO_TIMESTAMP('2016-02-12 18:15:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 12.02.2016 18:15
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=542977 AND NOT EXISTS (SELECT * FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 12.02.2016 18:15
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,542977,0,540657,540898,18,'M_Product_Target_ID',TO_TIMESTAMP('2016-02-12 18:15:34','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.product',0,'Y','N','Y','N','N','N','M_Product_Target_ID',20,TO_TIMESTAMP('2016-02-12 18:15:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 12.02.2016 18:15
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_Para_ID=540898 AND NOT EXISTS (SELECT * FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 12.02.2016 18:16
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540638,TO_TIMESTAMP('2016-02-12 18:16:18','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.product','Y','N','M_Product_Of_Org',TO_TIMESTAMP('2016-02-12 18:16:18','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 12.02.2016 18:16
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540638 AND NOT EXISTS (SELECT * FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 12.02.2016 18:17
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy) VALUES (0,1402,0,540638,208,TO_TIMESTAMP('2016-02-12 18:17:00','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.product','Y','N',TO_TIMESTAMP('2016-02-12 18:17:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 15.02.2016 11:20
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET AD_Reference_Value_ID=540511,Updated=TO_TIMESTAMP('2016-02-15 11:20:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=540898
;

-- 15.02.2016 11:20
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsBetaFunctionality,IsDirectPrint,IsOneInstanceOnly,IsReport,IsServerProcess,LockWaitTimeout,Name,RefreshAllAfterExecution,ShowHelp,Statistic_Count,Statistic_Seconds,Type,Updated,UpdatedBy,Value) VALUES ('7',0,0,540658,'Y','/de.metas.adempiere.adempiere.base/src/main/java/de/metas/product/process/M_Product_Remove_Mapping_Process.java','N',TO_TIMESTAMP('2016-02-15 11:20:46','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.product','Y','N','N','N','N','N',0,'M_Product_Remove_Mapping_Process','N','Y',0,0,'Java',TO_TIMESTAMP('2016-02-15 11:20:46','YYYY-MM-DD HH24:MI:SS'),100,'10000003')
;

-- 15.02.2016 11:20
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_ID=540658 AND NOT EXISTS (SELECT * FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;








-- 16.02.2016 11:34
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,Description,DisplayLogic,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,454,0,540657,540899,19,'M_Product_ID',TO_TIMESTAMP('2016-02-16 11:34:45','YYYY-MM-DD HH24:MI:SS'),100,'@M_Product_ID@','Produkt, Leistung, Artikel','1=2','de.metas.product',0,'Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.','Y','N','Y','N','Y','N','Produkt',30,TO_TIMESTAMP('2016-02-16 11:34:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 16.02.2016 11:34
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_Para_ID=540899 AND NOT EXISTS (SELECT * FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;





-- 16.02.2016 11:53
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET DisplayLogic=NULL, ReadOnlyLogic='1=1',Updated=TO_TIMESTAMP('2016-02-16 11:53:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=540899
;

-- 16.02.2016 11:53
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET IsActive='Y', SeqNo=10,Updated=TO_TIMESTAMP('2016-02-16 11:53:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=540899
;

-- 16.02.2016 11:53
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET IsActive='Y', SeqNo=20,Updated=TO_TIMESTAMP('2016-02-16 11:53:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=540897
;

-- 16.02.2016 11:53
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET IsActive='Y', SeqNo=30,Updated=TO_TIMESTAMP('2016-02-16 11:53:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=540898
;







-- 16.02.2016 11:56
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET DefaultValue='M_Product.M_Product_ID',Updated=TO_TIMESTAMP('2016-02-16 11:56:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=540899
;

-- 16.02.2016 12:01
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Process_Para_Trl WHERE AD_Process_Para_ID=540899
;

-- 16.02.2016 12:01
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Process_Para WHERE AD_Process_Para_ID=540899
;








-- 16.02.2016 13:08
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Parent_Column_ID=1402,Updated=TO_TIMESTAMP('2016-02-16 13:08:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=540722
;

-- 16.02.2016 13:10
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET AD_Column_ID=553167, Parent_Column_ID=NULL,Updated=TO_TIMESTAMP('2016-02-16 13:10:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=540722
;
















-- 16.02.2016 13:18
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Parent_Column_ID=1402,Updated=TO_TIMESTAMP('2016-02-16 13:18:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=540722
;

-- 16.02.2016 13:21
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,Description,DisplayLogic,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,454,0,540657,540900,19,'M_Product_ID',TO_TIMESTAMP('2016-02-16 13:21:30','YYYY-MM-DD HH24:MI:SS'),100,'@M_Product_ID@','Produkt, Leistung, Artikel','1=1','de.metas.product',0,'Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.','Y','N','Y','N','Y','N','Produkt',40,TO_TIMESTAMP('2016-02-16 13:21:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 16.02.2016 13:21
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_Para_ID=540900 AND NOT EXISTS (SELECT * FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;




















-- 16.02.2016 13:28
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET DisplayLogic='1=2',Updated=TO_TIMESTAMP('2016-02-16 13:28:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=540900
;

-- 16.02.2016 13:30
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET DisplayLogic=NULL, ReadOnlyLogic='1=1',Updated=TO_TIMESTAMP('2016-02-16 13:30:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=540900
;

-- 16.02.2016 13:33
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET AD_Column_ID=NULL,Updated=TO_TIMESTAMP('2016-02-16 13:33:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=540722
;

-- 16.02.2016 13:38
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET AD_Column_ID=553167, Parent_Column_ID=NULL,Updated=TO_TIMESTAMP('2016-02-16 13:38:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=540722
;

-- 16.02.2016 13:43
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET AD_Reference_ID=30, AD_Val_Rule_ID=231,Updated=TO_TIMESTAMP('2016-02-16 13:43:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=540900
;

-- 16.02.2016 13:45
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540639,TO_TIMESTAMP('2016-02-16 13:45:24','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.product','Y','N','M_Product_Ctx',TO_TIMESTAMP('2016-02-16 13:45:24','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 16.02.2016 13:45
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540639 AND NOT EXISTS (SELECT * FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 16.02.2016 13:45
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Display,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy) VALUES (0,1410,1402,0,540639,208,TO_TIMESTAMP('2016-02-16 13:45:51','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.product','Y','N',TO_TIMESTAMP('2016-02-16 13:45:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 16.02.2016 13:46
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='exists
(select 1 from M_Product p on M_Product.M_Product_ID = p.M_Product_ID)',Updated=TO_TIMESTAMP('2016-02-16 13:46:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540639
;

-- 16.02.2016 13:46
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET AD_Reference_Value_ID=540639, AD_Val_Rule_ID=NULL,Updated=TO_TIMESTAMP('2016-02-16 13:46:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=540900
;

-- 16.02.2016 13:47
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET DefaultValue='select M_Product.M_Product_ID',Updated=TO_TIMESTAMP('2016-02-16 13:47:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=540900
;

-- 16.02.2016 13:48
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET AD_Reference_Value_ID=NULL,Updated=TO_TIMESTAMP('2016-02-16 13:48:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=540900
;

-- 16.02.2016 13:50
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET AD_Reference_Value_ID=540272, DefaultValue='@M_Product_ID@',Updated=TO_TIMESTAMP('2016-02-16 13:50:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=540900
;

-- 16.02.2016 13:52
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET AD_Column_ID=NULL, Parent_Column_ID=1402,Updated=TO_TIMESTAMP('2016-02-16 13:52:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=540722
;

-- 16.02.2016 13:53
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET AD_Column_ID=553167,Updated=TO_TIMESTAMP('2016-02-16 13:53:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=540722
;

-- 16.02.2016 14:26
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET IsMandatory='N',Updated=TO_TIMESTAMP('2016-02-16 14:26:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=540900
;

-- 16.02.2016 14:27
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='exists
(select 1 from M_Product p where M_Product.M_Product_ID = p.M_Product_ID)',Updated=TO_TIMESTAMP('2016-02-16 14:27:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540639
;

-- 16.02.2016 14:28
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET AD_Reference_Value_ID=540639,Updated=TO_TIMESTAMP('2016-02-16 14:28:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=540900
;

-- 16.02.2016 14:30
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Parent_Column_ID=NULL,Updated=TO_TIMESTAMP('2016-02-16 14:30:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=540722
;









-- 16.02.2016 14:41
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET IsCheckParentsChanged='Y', IsSearchActive='Y',Updated=TO_TIMESTAMP('2016-02-16 14:41:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=540722
;

-- 16.02.2016 14:41
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Parent_Column_ID=1402,Updated=TO_TIMESTAMP('2016-02-16 14:41:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=540722
;

-- 16.02.2016 14:53
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Parent_Column_ID=NULL,Updated=TO_TIMESTAMP('2016-02-16 14:53:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=540722
;

-- 16.02.2016 14:54
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Parent_Column_ID=1402,Updated=TO_TIMESTAMP('2016-02-16 14:54:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=540722
;

-- 16.02.2016 14:55
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET DefaultValue='@#M_Product_ID@',Updated=TO_TIMESTAMP('2016-02-16 14:55:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=540900
;

-- 16.02.2016 14:56
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET DefaultValue='@SQL = SELECT M_Product_ID form M_Product',Updated=TO_TIMESTAMP('2016-02-16 14:56:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=540900
;

-- 16.02.2016 15:36
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Process_Para_Trl WHERE AD_Process_Para_ID=540900
;

-- 16.02.2016 15:36
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Process_Para WHERE AD_Process_Para_ID=540900
;

-- 16.02.2016 15:37
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Table_Process WHERE AD_Process_ID=540657 AND AD_Table_ID=540704
;

-- 16.02.2016 15:37
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy) VALUES (0,0,540657,208,TO_TIMESTAMP('2016-02-16 15:37:25','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.product','Y',TO_TIMESTAMP('2016-02-16 15:37:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 16.02.2016 15:38
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Reference SET Name='M_Product_Of_Org_Not_Mapped',Updated=TO_TIMESTAMP('2016-02-16 15:38:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540638
;

-- 16.02.2016 15:38
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Reference_Trl SET IsTranslated='N' WHERE AD_Reference_ID=540638
;

-- 16.02.2016 15:39
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET AD_Reference_Value_ID=540638,Updated=TO_TIMESTAMP('2016-02-16 15:39:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=540898
;

-- 16.02.2016 15:40
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='M_Product.AD_Org_ID = @AD_Org_Target_ID@ AND M_Product.M_Product_Mapping_ID IS NULL',Updated=TO_TIMESTAMP('2016-02-16 15:40:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540638
;

-- 16.02.2016 15:42
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='M_Product.AD_Org_ID = @AD_Org_Target_ID/-1@ AND M_Product.M_Product_Mapping_ID IS NULL',Updated=TO_TIMESTAMP('2016-02-16 15:42:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540638
;

-- 16.02.2016 15:45
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET AD_Display=1410,Updated=TO_TIMESTAMP('2016-02-16 15:45:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540638
;

-- 16.02.2016 15:51
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='M_Product.AD_Org_ID = @AD_Org_Target_ID/-1@ ',Updated=TO_TIMESTAMP('2016-02-16 15:51:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540638
;

-- 16.02.2016 15:57
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET IsMandatory='Y',Updated=TO_TIMESTAMP('2016-02-16 15:57:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=540898
;

-- 16.02.2016 15:57
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET DisplayLogic='@AD_Org_Target_ID@ > 0',Updated=TO_TIMESTAMP('2016-02-16 15:57:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=540898
;

-- 16.02.2016 15:59
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET AD_Reference_Value_ID=540272,Updated=TO_TIMESTAMP('2016-02-16 15:59:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=540898
;

-- 16.02.2016 16:00
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540318,TO_TIMESTAMP('2016-02-16 16:00:30','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.product','Y','M_Product_Org_NotMapped','S',TO_TIMESTAMP('2016-02-16 16:00:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 16.02.2016 16:00
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='M_Product.AD_Org_ID = @AD_Org_Target_ID/-1@ AND M_Product.M_Product_Mapping_ID IS NULL',Updated=TO_TIMESTAMP('2016-02-16 16:00:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540318
;

-- 16.02.2016 16:01
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET AD_Val_Rule_ID=540318,Updated=TO_TIMESTAMP('2016-02-16 16:01:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=540898
;

-- 16.02.2016 16:02
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET AD_Reference_ID=30, AD_Reference_Value_ID=NULL,Updated=TO_TIMESTAMP('2016-02-16 16:02:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=540898
;

-- 16.02.2016 16:02
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET AD_Reference_Value_ID=540272,Updated=TO_TIMESTAMP('2016-02-16 16:02:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=540898
;

-- 16.02.2016 16:04
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='M_Product.AD_Org_ID = @AD_Org_Target_ID/-1@',Updated=TO_TIMESTAMP('2016-02-16 16:04:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540318
;

-- 16.02.2016 16:06
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET IsAutocomplete='Y',Updated=TO_TIMESTAMP('2016-02-16 16:06:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=540898
;

-- 16.02.2016 16:11
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='M_Product.AD_Org_ID = @AD_Org_Target_ID/-1@ 
AND 
(M_Product.M_Product_Mapping_ID IS NULL OR not exists
(select 1 from M_Product p where p.AD_Org_ID = @AD_Org_ID@ and p.M_Product_Mapping_ID =M_Product.M_Product_Mapping_ID ))',Updated=TO_TIMESTAMP('2016-02-16 16:11:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540318
;

-- 16.02.2016 16:38
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Reference_Trl WHERE AD_Reference_ID=540638
;

-- 16.02.2016 16:38
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Reference WHERE AD_Reference_ID=540638
;

-- 16.02.2016 16:39
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET DisplayLogic=NULL,Updated=TO_TIMESTAMP('2016-02-16 16:39:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=540898
;

-- 16.02.2016 17:58
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,542976,0,540658,540901,19,'AD_Org_Target_ID',TO_TIMESTAMP('2016-02-16 17:58:34','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.product',0,'Y','N','Y','N','N','N','AD_Org_Target_ID',10,TO_TIMESTAMP('2016-02-16 17:58:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 16.02.2016 17:58
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_Para_ID=540901 AND NOT EXISTS (SELECT * FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 16.02.2016 17:59
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET AD_Reference_Value_ID=540637,Updated=TO_TIMESTAMP('2016-02-16 17:59:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=540901
;

-- 16.02.2016 18:07
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540640,TO_TIMESTAMP('2016-02-16 18:07:55','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.product','Y','N','AD_Org_Mapped_Products',TO_TIMESTAMP('2016-02-16 18:07:55','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 16.02.2016 18:07
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540640 AND NOT EXISTS (SELECT * FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 16.02.2016 18:08
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Display,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy) VALUES (0,522,528,0,540640,155,TO_TIMESTAMP('2016-02-16 18:08:21','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.product','Y','N',TO_TIMESTAMP('2016-02-16 18:08:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 16.02.2016 18:09
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='exists
(
select 1 from M_Product p where p.AD_Org_ID = AD_Org.AD_Org_ID
and p.M_Product_Mapping_ID = @M_Product_Mapping_ID@
)',Updated=TO_TIMESTAMP('2016-02-16 18:09:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540640
;

-- 16.02.2016 18:10
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET AD_Reference_Value_ID=540640,Updated=TO_TIMESTAMP('2016-02-16 18:10:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=540901
;

-- 16.02.2016 18:11
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540641,TO_TIMESTAMP('2016-02-16 18:11:24','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.product','Y','N','M_Product_With_Mapping',TO_TIMESTAMP('2016-02-16 18:11:24','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 16.02.2016 18:11
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540641 AND NOT EXISTS (SELECT * FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 16.02.2016 18:11
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Display,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy) VALUES (0,1410,1402,0,540641,208,TO_TIMESTAMP('2016-02-16 18:11:51','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.product','Y','N',TO_TIMESTAMP('2016-02-16 18:11:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 16.02.2016 18:12
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='M_Product.AD_Org_ID = @AD_Org_Target_ID@ and M_Product.M_Product_Mapping_ID = @M_Product_Mapping_ID@',Updated=TO_TIMESTAMP('2016-02-16 18:12:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540641
;

-- 16.02.2016 18:12
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Reference_Value_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,542977,0,540658,540902,18,540641,'M_Product_Target_ID',TO_TIMESTAMP('2016-02-16 18:12:48','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.product',0,'Y','N','Y','N','N','N','M_Product_Target_ID',20,TO_TIMESTAMP('2016-02-16 18:12:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 16.02.2016 18:12
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_Para_ID=540902 AND NOT EXISTS (SELECT * FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 16.02.2016 18:12
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET IsMandatory='Y',Updated=TO_TIMESTAMP('2016-02-16 18:12:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=540902
;

-- 16.02.2016 18:12
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET IsMandatory='Y',Updated=TO_TIMESTAMP('2016-02-16 18:12:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=540901
;

-- 16.02.2016 18:14
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Classname='de.metas.product.process.M_Product_Remove_Mapping_Process',Updated=TO_TIMESTAMP('2016-02-16 18:14:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540658
;

-- 16.02.2016 18:16
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET AD_Reference_ID=18,Updated=TO_TIMESTAMP('2016-02-16 18:16:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=540901
;

-- 16.02.2016 18:17
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='M_Product.AD_Org_ID = @AD_Org_Target_ID/1@ and M_Product.M_Product_Mapping_ID = @M_Product_Mapping_ID@',Updated=TO_TIMESTAMP('2016-02-16 18:17:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540641
;

-- 16.02.2016 18:17
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='M_Product.AD_Org_ID = @AD_Org_Target_ID/-1@ and M_Product.M_Product_Mapping_ID = @M_Product_Mapping_ID@',Updated=TO_TIMESTAMP('2016-02-16 18:17:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540641
;

-- 16.02.2016 18:18
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='M_Product.AD_Org_ID = @AD_Org_Target_ID/-1@',Updated=TO_TIMESTAMP('2016-02-16 18:18:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540641
;

-- 16.02.2016 18:19
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='exists
(
select 1 from M_Product p where p.AD_Org_ID = AD_Org.AD_Org_ID
and p.M_Product_Mapping_ID = @M_Product_Mapping_ID@
)
and AD_Org.AD_Org_ID <> @AD_Org_ID@',Updated=TO_TIMESTAMP('2016-02-16 18:19:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540640
;

-- 16.02.2016 18:33
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET AD_Reference_Value_ID=NULL,Updated=TO_TIMESTAMP('2016-02-16 18:33:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=540902
;

-- 16.02.2016 18:33
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Reference_Trl WHERE AD_Reference_ID=540641
;

-- 16.02.2016 18:33
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Reference WHERE AD_Reference_ID=540641
;

-- 16.02.2016 18:33
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540319,TO_TIMESTAMP('2016-02-16 18:33:41','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.product','Y','M_Product_With_Mapping','S',TO_TIMESTAMP('2016-02-16 18:33:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 16.02.2016 18:33
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='M_Product.AD_Org_ID = @AD_Org_Target_ID/-1@',Updated=TO_TIMESTAMP('2016-02-16 18:33:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540319
;

-- 16.02.2016 18:34
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET AD_Reference_Value_ID=540272,Updated=TO_TIMESTAMP('2016-02-16 18:34:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=540902
;

-- 16.02.2016 18:34
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET AD_Val_Rule_ID=540319,Updated=TO_TIMESTAMP('2016-02-16 18:34:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=540902
;

-- 16.02.2016 18:35
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='M_Product.AD_Org_ID = @AD_Org_Target_ID/-1@  and M_Product.M_Product_Mapping_ID = @M_Product_Mapping_ID@',Updated=TO_TIMESTAMP('2016-02-16 18:35:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540319
;



---- parameters not needed in the removing mapping process ( discussed with Tobi)




-- 17.02.2016 13:51
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET AD_Reference_Value_ID=NULL,Updated=TO_TIMESTAMP('2016-02-17 13:51:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=540901
;

-- 17.02.2016 13:51
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Reference_Trl WHERE AD_Reference_ID=540640
;

-- 17.02.2016 13:51
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Reference WHERE AD_Reference_ID=540640
;

-- 17.02.2016 13:51
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Process_Para_Trl WHERE AD_Process_Para_ID=540901
;

-- 17.02.2016 13:51
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Process_Para WHERE AD_Process_Para_ID=540901
;

-- 17.02.2016 13:52
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET AD_Val_Rule_ID=NULL,Updated=TO_TIMESTAMP('2016-02-17 13:52:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=540902
;

-- 17.02.2016 13:52
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Val_Rule WHERE AD_Val_Rule_ID=540319
;

-- 17.02.2016 13:52
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Process_Para_Trl WHERE AD_Process_Para_ID=540902
;

-- 17.02.2016 13:52
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Process_Para WHERE AD_Process_Para_ID=540902
;






-- 17.02.2016 14:25
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET RefreshAllAfterExecution='Y',Updated=TO_TIMESTAMP('2016-02-17 14:25:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540658
;



-- 17.02.2016 15:22
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540320,TO_TIMESTAMP('2016-02-17 15:22:08','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.product','Y','AD_Org_NoSameProductMapping','S',TO_TIMESTAMP('2016-02-17 15:22:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 17.02.2016 15:23
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='not exists
(
	select 1 from M_Product p where p.AD_Org_ID = AD_Org.AD_Org_ID and p.M_Product_Mapping_ID = @M_Product_Mapping_ID/-1@	
)',Updated=TO_TIMESTAMP('2016-02-17 15:23:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540320
;

-- 17.02.2016 15:23
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET AD_Val_Rule_ID=540320,Updated=TO_TIMESTAMP('2016-02-17 15:23:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=540897
;

