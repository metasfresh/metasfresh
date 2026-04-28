-- 2018-10-17T16:35:11.290
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List (AD_Reference_ID,AD_Client_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,AD_Ref_List_ID,ValueName,AD_Org_ID,Name,Value,EntityType) VALUES (505210,0,'Y',TO_TIMESTAMP('2018-10-17 16:35:11','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2018-10-17 16:35:11','YYYY-MM-DD HH24:MI:SS'),100,541750,'AttachmentStored',0,'Anhang gespeichert','attacmentStored','D')
;

-- 2018-10-17T16:35:11.293
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Ref_List_ID=541750 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2018-10-17T16:35:16.716
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-10-17 16:35:16','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Ref_List_ID=541750 AND AD_Language='de_CH'
;

-- 2018-10-17T16:35:25.256
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-10-17 16:35:25','YYYY-MM-DD HH24:MI:SS'),Name='Attachment stored' WHERE AD_Ref_List_ID=541750 AND AD_Language='en_US'
;

-- 2018-10-17T16:35:29.617
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-10-17 16:35:29','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Ref_List_ID=541750 AND AD_Language='en_US'
;

-- 2018-10-17T16:35:42.755
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-10-17 16:35:42','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Ref_List_ID=505211 AND AD_Language='fr_CH'
;

-- 2018-10-17T16:35:46.457
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Ref_List_Trl WHERE AD_Ref_List_ID=505211 AND AD_Language='it_CH'
;

-- 2018-10-17T16:35:52.684
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Ref_List_Trl WHERE AD_Ref_List_ID=505211 AND AD_Language='fr_CH'
;

-- 2018-10-17T16:35:55.648
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Ref_List_Trl WHERE AD_Ref_List_ID=505211 AND AD_Language='en_GB'
;

-- 2018-10-17T16:35:58.408
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-10-17 16:35:58','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Ref_List_ID=505211 AND AD_Language='de_CH'
;

-- 2018-10-17T16:36:05.440
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-10-17 16:36:05','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Mail send' WHERE AD_Ref_List_ID=505211 AND AD_Language='en_US'
;

-- 2018-10-17T16:36:13.693
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Ref_List_Trl WHERE AD_Ref_List_ID=505212 AND AD_Language='it_CH'
;

-- 2018-10-17T16:36:15.246
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Ref_List_Trl WHERE AD_Ref_List_ID=505212 AND AD_Language='en_GB'
;

-- 2018-10-17T16:36:18.203
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-10-17 16:36:18','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Ref_List_ID=505212 AND AD_Language='de_CH'
;

-- 2018-10-17T16:36:24.010
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-10-17 16:36:24','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='PDF export' WHERE AD_Ref_List_ID=505212 AND AD_Language='en_US'
;

-- 2018-10-17T16:36:30.697
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Ref_List_Trl WHERE AD_Ref_List_ID=505212 AND AD_Language='fr_CH'
;

-- 2018-10-17T16:36:38.210
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Ref_List_Trl WHERE AD_Ref_List_ID=505210 AND AD_Language='fr_CH'
;

-- 2018-10-17T16:36:39.378
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Ref_List_Trl WHERE AD_Ref_List_ID=505210 AND AD_Language='it_CH'
;

-- 2018-10-17T16:36:40.611
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Ref_List_Trl WHERE AD_Ref_List_ID=505210 AND AD_Language='en_GB'
;

-- 2018-10-17T16:36:42.923
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-10-17 16:36:42','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Ref_List_ID=505210 AND AD_Language='de_CH'
;

-- 2018-10-17T16:36:47.999
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-10-17 16:36:47','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Print' WHERE AD_Ref_List_ID=505210 AND AD_Language='en_US'
;

-- 2018-10-17T16:37:00.780
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET FieldLength=15,Updated=TO_TIMESTAMP('2018-10-17 16:37:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=548166
;

-- 2018-10-17T16:37:05.798
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_doc_outbound_log_line','"action"','VARCHAR(15)',null,null)
;

-- 2018-10-17T16:38:17.700
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,IsActive,Created,CreatedBy,PrintName,Updated,UpdatedBy,AD_Element_ID,AD_Org_ID,Name,ColumnName,EntityType) VALUES (0,'Y',TO_TIMESTAMP('2018-10-17 16:38:17','YYYY-MM-DD HH24:MI:SS'),100,'Speicherort',TO_TIMESTAMP('2018-10-17 16:38:17','YYYY-MM-DD HH24:MI:SS'),100,544479,0,'Speicherort','StoreURI','de.metas.document.archive')
;

-- 2018-10-17T16:38:17.704
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, PrintName,PO_Description,PO_Help,PO_Name,PO_PrintName,Help,Name,Description, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.PrintName,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.Help,t.Name,t.Description, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=544479 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2018-10-17T16:38:20.619
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-10-17 16:38:20','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Element_ID=544479 AND AD_Language='de_CH'
;

-- 2018-10-17T16:38:20.655
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(544479,'de_CH') 
;

-- 2018-10-17T16:38:37.204
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-10-17 16:38:37','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Storage location',PrintName='Storage location' WHERE AD_Element_ID=544479 AND AD_Language='en_US'
;

-- 2018-10-17T16:38:37.209
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(544479,'en_US') 
;

-- 2018-10-17T16:39:31.905
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAutocomplete,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,IsAdvancedText,IsLazyLoading,AD_Table_ID,IsCalculated,AD_Column_ID,IsDimension,IsMandatory,IsStaleable,IsUseDocSequence,IsRangeFilter,IsShowFilterIncrementButtons,IsDLMPartitionBoundary,IsGenericZoomKeyColumn,SelectionColumnSeqNo,AD_Org_ID,AD_Element_ID,EntityType,IsForceIncludeInGeneratedModel,IsGenericZoomOrigin,Name,ColumnName) VALUES (10,1024,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2018-10-17 16:39:31','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','N','N','Y','N',TO_TIMESTAMP('2018-10-17 16:39:31','YYYY-MM-DD HH24:MI:SS'),100,'N','N',540454,'N',563296,'N','N','N','N','N','N','N','N',0,0,544479,'de.metas.document.archive','N','N','Speicherort','StoreURI')
;

-- 2018-10-17T16:39:31.908
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=563296 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-10-17T16:39:34.532
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_Doc_Outbound_Log_Line','ALTER TABLE public.C_Doc_Outbound_Log_Line ADD COLUMN StoreURI VARCHAR(1024)')
;

-- 2018-10-17T16:40:01.167
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsLazyLoading='Y',Updated=TO_TIMESTAMP('2018-10-17 16:40:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=548164
;

-- 2018-10-17T16:40:02.024
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsLazyLoading='Y',Updated=TO_TIMESTAMP('2018-10-17 16:40:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=551964
;

-- 2018-10-17T16:40:03.038
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsLazyLoading='Y',Updated=TO_TIMESTAMP('2018-10-17 16:40:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=548765
;

-- 2018-10-17T16:40:04.151
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsLazyLoading='Y',Updated=TO_TIMESTAMP('2018-10-17 16:40:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=551507
;

-- 2018-10-17T16:40:04.806
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsLazyLoading='Y',Updated=TO_TIMESTAMP('2018-10-17 16:40:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=548163
;

-- 2018-10-17T16:40:06.182
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsLazyLoading='Y',Updated=TO_TIMESTAMP('2018-10-17 16:40:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=551500
;

-- 2018-10-17T16:40:08.520
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsLazyLoading='Y',Updated=TO_TIMESTAMP('2018-10-17 16:40:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=551466
;

-- 2018-10-17T16:41:10.837
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,IsActive,Created,CreatedBy,PrintName,Updated,UpdatedBy,AD_Element_ID,AD_Org_ID,Name,ColumnName,EntityType) VALUES (0,'Y',TO_TIMESTAMP('2018-10-17 16:41:10','YYYY-MM-DD HH24:MI:SS'),100,'Anz. gespeichert',TO_TIMESTAMP('2018-10-17 16:41:10','YYYY-MM-DD HH24:MI:SS'),100,544480,0,'Anz. gespeichert','StoreCount','de.metas.document.archive')
;

-- 2018-10-17T16:41:10.839
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, PrintName,PO_Description,PO_Help,PO_Name,PO_PrintName,Help,Name,Description, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.PrintName,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.Help,t.Name,t.Description, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=544480 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2018-10-17T16:41:17.571
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-10-17 16:41:17','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Element_ID=544480 AND AD_Language='de_CH'
;

-- 2018-10-17T16:41:17.579
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(544480,'de_CH') 
;

-- 2018-10-17T16:41:30.590
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-10-17 16:41:30','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Stored count',PrintName='Stored count' WHERE AD_Element_ID=544480 AND AD_Language='en_US'
;

-- 2018-10-17T16:41:30.597
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(544480,'en_US') 
;

-- 2018-10-17T16:42:08.119
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,DefaultValue,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAutocomplete,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,IsAdvancedText,IsLazyLoading,AD_Table_ID,IsCalculated,AD_Column_ID,IsDimension,IsMandatory,IsStaleable,IsUseDocSequence,IsRangeFilter,IsShowFilterIncrementButtons,IsDLMPartitionBoundary,IsGenericZoomKeyColumn,SelectionColumnSeqNo,AD_Org_ID,AD_Element_ID,EntityType,IsForceIncludeInGeneratedModel,IsGenericZoomOrigin,Name,ColumnName) VALUES (11,'0',14,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2018-10-17 16:42:07','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','N','N','Y','N',TO_TIMESTAMP('2018-10-17 16:42:07','YYYY-MM-DD HH24:MI:SS'),100,'N','Y',540453,'N',563297,'N','Y','N','N','N','N','N','N',0,0,544480,'de.metas.document.archive','N','N','Anz. gespeichert','StoreCount')
;

-- 2018-10-17T16:42:08.122
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=563297 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-10-17T16:42:47.700
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Description='',Updated=TO_TIMESTAMP('2018-10-17 16:42:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=152
;

-- 2018-10-17T16:42:47.703
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='Action', Name='Aktion', Description='', Help='"Aktion" ist ein Auswahlfeld, das die für diesen Vorgang durchzuführende Aktion anzeigt.' WHERE AD_Element_ID=152
;

-- 2018-10-17T16:42:47.706
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Action', Name='Aktion', Description='', Help='"Aktion" ist ein Auswahlfeld, das die für diesen Vorgang durchzuführende Aktion anzeigt.', AD_Element_ID=152 WHERE UPPER(ColumnName)='ACTION' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2018-10-17T16:42:47.711
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Action', Name='Aktion', Description='', Help='"Aktion" ist ein Auswahlfeld, das die für diesen Vorgang durchzuführende Aktion anzeigt.' WHERE AD_Element_ID=152 AND IsCentrallyMaintained='Y'
;

-- 2018-10-17T16:42:47.714
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Aktion', Description='', Help='"Aktion" ist ein Auswahlfeld, das die für diesen Vorgang durchzuführende Aktion anzeigt.' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=152) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 152)
;

-- 2018-10-17T16:42:51.377
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Help='',Updated=TO_TIMESTAMP('2018-10-17 16:42:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=152
;

-- 2018-10-17T16:42:51.379
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='Action', Name='Aktion', Description='', Help='' WHERE AD_Element_ID=152
;

-- 2018-10-17T16:42:51.381
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Action', Name='Aktion', Description='', Help='', AD_Element_ID=152 WHERE UPPER(ColumnName)='ACTION' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2018-10-17T16:42:51.383
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Action', Name='Aktion', Description='', Help='' WHERE AD_Element_ID=152 AND IsCentrallyMaintained='Y'
;

-- 2018-10-17T16:42:51.384
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Aktion', Description='', Help='' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=152) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 152)
;

-- 2018-10-17T16:43:10.690
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List SET Value='attachmentStored',Updated=TO_TIMESTAMP('2018-10-17 16:43:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=541750
;

-- 2018-10-17T16:43:20.604
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET FieldLength=16,Updated=TO_TIMESTAMP('2018-10-17 16:43:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=548166
;

-- 2018-10-17T16:43:21.553
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_doc_outbound_log_line','"action"','VARCHAR(16)',null,null)
;

-- 2018-10-17T16:43:40.975
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsUpdateable='N', IsMandatory='N', ColumnSQL='(select COUNT(*) from  C_Doc_Outbound_Log_Line where C_Doc_Outbound_Log_Line.Action = ''attachmentStored'' AND C_Doc_Outbound_Log_Line.C_Doc_Outbound_Log_ID = C_Doc_Outbound_Log.C_Doc_Outbound_Log_ID)',Updated=TO_TIMESTAMP('2018-10-17 16:43:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=563297
;

-- 2018-10-17T16:46:01.546
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,IsActive,Created,CreatedBy,PrintName,Updated,UpdatedBy,AD_Element_ID,AD_Org_ID,Name,ColumnName,EntityType) VALUES (0,'Y',TO_TIMESTAMP('2018-10-17 16:46:01','YYYY-MM-DD HH24:MI:SS'),100,'Zuletzt gespeichert',TO_TIMESTAMP('2018-10-17 16:46:01','YYYY-MM-DD HH24:MI:SS'),100,544481,0,'Zuletzt gespeichert','DateLastStore','de.metas.document')
;

-- 2018-10-17T16:46:01.548
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, PrintName,PO_Description,PO_Help,PO_Name,PO_PrintName,Help,Name,Description, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.PrintName,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.Help,t.Name,t.Description, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=544481 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2018-10-17T16:46:04.908
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-10-17 16:46:04','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Element_ID=544481 AND AD_Language='de_CH'
;

-- 2018-10-17T16:46:04.917
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(544481,'de_CH') 
;

-- 2018-10-17T16:46:17.871
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-10-17 16:46:17','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Last store',PrintName='Last store' WHERE AD_Element_ID=544481 AND AD_Language='en_US'
;

-- 2018-10-17T16:46:17.876
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(544481,'en_US') 
;

-- 2018-10-17T16:46:52.705
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAutocomplete,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,IsAdvancedText,IsLazyLoading,AD_Table_ID,IsCalculated,AD_Column_ID,IsDimension,IsMandatory,IsStaleable,IsUseDocSequence,IsRangeFilter,IsShowFilterIncrementButtons,IsDLMPartitionBoundary,IsGenericZoomKeyColumn,SelectionColumnSeqNo,AD_Org_ID,AD_Element_ID,EntityType,IsForceIncludeInGeneratedModel,IsGenericZoomOrigin,Name,ColumnName) VALUES (16,7,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2018-10-17 16:46:52','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','N','N','Y','N',TO_TIMESTAMP('2018-10-17 16:46:52','YYYY-MM-DD HH24:MI:SS'),100,'N','N',540453,'Y',563298,'N','N','N','N','N','N','N','N',0,0,544481,'de.metas.document.archive','N','N','Zuletzt gespeichert','DateLastStore')
;

-- 2018-10-17T16:46:52.707
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=563298 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-10-17T16:46:56.738
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_Doc_Outbound_Log','ALTER TABLE public.C_Doc_Outbound_Log ADD COLUMN DateLastStore TIMESTAMP WITH TIME ZONE')
;

-- 2018-10-17T16:47:28.593
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_ID=30,Updated=TO_TIMESTAMP('2018-10-17 16:47:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=551466
;

-- 2018-10-17T16:47:46.853
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_ID=30,Updated=TO_TIMESTAMP('2018-10-17 16:47:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=560656
;

-- 2018-10-17T16:49:42.198
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,Updated,UpdatedBy,AD_Field_ID,IsDisplayedGrid,AD_Column_ID,AD_Org_ID,Name,EntityType) VALUES (540474,'N',14,'N','N','N','N',0,'Y',TO_TIMESTAMP('2018-10-17 16:49:42','YYYY-MM-DD HH24:MI:SS'),100,'N',TO_TIMESTAMP('2018-10-17 16:49:42','YYYY-MM-DD HH24:MI:SS'),100,569539,'N',563297,0,'Anz. gespeichert','de.metas.document.archive')
;

-- 2018-10-17T16:49:42.201
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=569539 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2018-10-17T16:49:42.316
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,Updated,UpdatedBy,AD_Field_ID,IsDisplayedGrid,AD_Column_ID,AD_Org_ID,Name,EntityType) VALUES (540474,'N',7,'N','N','N','N',0,'Y',TO_TIMESTAMP('2018-10-17 16:49:42','YYYY-MM-DD HH24:MI:SS'),100,'N',TO_TIMESTAMP('2018-10-17 16:49:42','YYYY-MM-DD HH24:MI:SS'),100,569540,'N',563298,0,'Zuletzt gespeichert','de.metas.document.archive')
;

-- 2018-10-17T16:49:42.318
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=569540 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2018-10-17T16:50:09.478
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET SeqNo=75,Updated=TO_TIMESTAMP('2018-10-17 16:50:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=569539
;

-- 2018-10-17T16:50:10.515
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsSameLine='N',Updated=TO_TIMESTAMP('2018-10-17 16:50:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=551737
;

-- 2018-10-17T16:50:31.938
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsSameLine='Y',Updated=TO_TIMESTAMP('2018-10-17 16:50:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=569539
;

-- 2018-10-17T16:50:58.933
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET SeqNo=135,Updated=TO_TIMESTAMP('2018-10-17 16:50:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=569540
;

-- 2018-10-17T16:52:27.479
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,Updated,UpdatedBy,AD_Field_ID,AD_Column_ID,AD_Org_ID,Name,EntityType) VALUES (540475,'Y',1024,'N','N','N','N',0,'Y',TO_TIMESTAMP('2018-10-17 16:52:27','YYYY-MM-DD HH24:MI:SS'),100,'N',TO_TIMESTAMP('2018-10-17 16:52:27','YYYY-MM-DD HH24:MI:SS'),100,569541,563296,0,'Speicherort','de.metas.document.archive')
;

-- 2018-10-17T16:52:27.480
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=569541 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2018-10-17T16:58:01.911
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Classname='de.metas.document.archive.storage.attachments.process.C_Doc_Outbound_Log_StoreAttachments',Updated=TO_TIMESTAMP('2018-10-17 16:58:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=541025
;

