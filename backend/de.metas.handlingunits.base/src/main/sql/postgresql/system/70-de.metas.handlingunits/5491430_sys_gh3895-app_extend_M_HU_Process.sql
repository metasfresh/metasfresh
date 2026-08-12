-- 2018-04-20T11:56:05.664
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,543977,0,'IsProvideAsUserAction',TO_TIMESTAMP('2018-04-20 11:56:05','YYYY-MM-DD HH24:MI:SS'),100,'Entscheidet, ob der Prozess als Aktion im Handling-Unit-Editor (WebUI und SwingUI) auswählbar sein soll.','de.metas.handlingunits','Y','Als Benutzeraktion verfügbar machen','Als Benutzeraktion verfügbar machen',TO_TIMESTAMP('2018-04-20 11:56:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-04-20T11:56:05.672
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=543977 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2018-04-20T11:56:09.262
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-04-20 11:56:09','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Element_ID=543977 AND AD_Language='de_CH'
;

-- 2018-04-20T11:56:09.294
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543977,'de_CH') 
;

-- 2018-04-20T11:56:25.260
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-04-20 11:56:25','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Make available as user action',PrintName='Make available as user action',Description='Decides on whether the process shall be available as user action in WebUI and SwingUI. ' WHERE AD_Element_ID=543977 AND AD_Language='en_US'
;

-- 2018-04-20T11:56:25.262
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543977,'en_US') 
;

-- 2018-04-20T11:56:45.357
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,559734,543977,0,20,540607,'N','IsProvideAsUserAction',TO_TIMESTAMP('2018-04-20 11:56:45','YYYY-MM-DD HH24:MI:SS'),100,'N','Y','Entscheidet, ob der Prozess als Aktion im Handling-Unit-Editor (WebUI und SwingUI) auswählbar sein soll.','de.metas.handlingunits',1,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','Als Benutzeraktion verfügbar machen',0,0,TO_TIMESTAMP('2018-04-20 11:56:45','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2018-04-20T11:56:45.359
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=559734 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-04-20T11:56:49.052
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('M_HU_Process','ALTER TABLE public.M_HU_Process ADD COLUMN IsProvideAsUserAction CHAR(1) DEFAULT ''Y'' CHECK (IsProvideAsUserAction IN (''Y'',''N'')) NOT NULL')
;

-- 2018-04-20T11:56:59.194
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET AD_Window_ID=540224, IsChangeLog='Y',Updated=TO_TIMESTAMP('2018-04-20 11:56:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=540607
;

-- 2018-04-20T11:57:36.063
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='IsApplyToLU', Name='Auf LUs anwenden', PrintName='Auf LUs anwenden',Updated=TO_TIMESTAMP('2018-04-20 11:57:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542500
;

-- 2018-04-20T11:57:36.065
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsApplyToLU', Name='Auf LUs anwenden', Description=NULL, Help=NULL WHERE AD_Element_ID=542500
;

-- 2018-04-20T11:57:36.067
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsApplyToLU', Name='Auf LUs anwenden', Description=NULL, Help=NULL, AD_Element_ID=542500 WHERE UPPER(ColumnName)='ISAPPLYTOLU' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2018-04-20T11:57:36.069
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsApplyToLU', Name='Auf LUs anwenden', Description=NULL, Help=NULL WHERE AD_Element_ID=542500 AND IsCentrallyMaintained='Y'
;

-- 2018-04-20T11:57:36.070
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Auf LUs anwenden', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=542500) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 542500)
;

-- 2018-04-20T11:57:36.085
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Auf LUs anwenden', Name='Auf LUs anwenden' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=542500)
;

-- 2018-04-20T11:57:48.070
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-04-20 11:57:48','YYYY-MM-DD HH24:MI:SS'),Name='Apply to LUs',PrintName='Apply to LUs' WHERE AD_Element_ID=542500 AND AD_Language='en_GB'
;

-- 2018-04-20T11:57:48.073
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542500,'en_GB') 
;

-- 2018-04-20T11:57:54.823
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-04-20 11:57:54','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Auf LUs anwenden',PrintName='Auf LUs anwenden' WHERE AD_Element_ID=542500 AND AD_Language='de_CH'
;

-- 2018-04-20T11:57:54.826
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542500,'de_CH') 
;

-- 2018-04-20T11:58:03.788
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-04-20 11:58:03','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Apply to LUs',PrintName='Apply to LUs' WHERE AD_Element_ID=542500 AND AD_Language='en_US'
;

-- 2018-04-20T11:58:03.793
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542500,'en_US') 
;

-- 2018-04-20T11:58:10.758
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-04-20 11:58:10','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Element_ID=542500 AND AD_Language='en_GB'
;

-- 2018-04-20T11:58:10.766
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542500,'en_GB') 
;

-- 2018-04-20T11:58:49.072
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='IsApplyToTU', Name='Auf TUs anwenden', PrintName='Auf TUs anwenden',Updated=TO_TIMESTAMP('2018-04-20 11:58:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542501
;

-- 2018-04-20T11:58:49.074
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsApplyToTU', Name='Auf TUs anwenden', Description=NULL, Help=NULL WHERE AD_Element_ID=542501
;

-- 2018-04-20T11:58:49.077
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsApplyToTU', Name='Auf TUs anwenden', Description=NULL, Help=NULL, AD_Element_ID=542501 WHERE UPPER(ColumnName)='ISAPPLYTOTU' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2018-04-20T11:58:49.079
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsApplyToTU', Name='Auf TUs anwenden', Description=NULL, Help=NULL WHERE AD_Element_ID=542501 AND IsCentrallyMaintained='Y'
;

-- 2018-04-20T11:58:49.080
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Auf TUs anwenden', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=542501) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 542501)
;

-- 2018-04-20T11:58:49.091
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Auf TUs anwenden', Name='Auf TUs anwenden' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=542501)
;

-- 2018-04-20T11:58:58.252
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-04-20 11:58:58','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Apply to TUs',PrintName='Apply to TUs' WHERE AD_Element_ID=542501 AND AD_Language='en_GB'
;

-- 2018-04-20T11:58:58.255
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542501,'en_GB') 
;

-- 2018-04-20T11:59:04.582
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-04-20 11:59:04','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Auf TUs anwenden',PrintName='Auf TUs anwenden' WHERE AD_Element_ID=542501 AND AD_Language='de_CH'
;

-- 2018-04-20T11:59:04.585
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542501,'de_CH') 
;

-- 2018-04-20T11:59:12.172
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-04-20 11:59:12','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Apply to TUs',PrintName='Apply to TUs' WHERE AD_Element_ID=542501 AND AD_Language='en_US'
;

-- 2018-04-20T11:59:12.178
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542501,'en_US') 
;

-- 2018-04-20T11:59:21.616
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='IsApplyToTUs',Updated=TO_TIMESTAMP('2018-04-20 11:59:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542501
;

-- 2018-04-20T11:59:21.617
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsApplyToTUs', Name='Auf TUs anwenden', Description=NULL, Help=NULL WHERE AD_Element_ID=542501
;

-- 2018-04-20T11:59:21.619
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsApplyToTUs', Name='Auf TUs anwenden', Description=NULL, Help=NULL, AD_Element_ID=542501 WHERE UPPER(ColumnName)='ISAPPLYTOTUS' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2018-04-20T11:59:21.621
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsApplyToTUs', Name='Auf TUs anwenden', Description=NULL, Help=NULL WHERE AD_Element_ID=542501 AND IsCentrallyMaintained='Y'
;

-- 2018-04-20T11:59:31.069
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='IsApplyToLUs',Updated=TO_TIMESTAMP('2018-04-20 11:59:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542500
;

-- 2018-04-20T11:59:31.071
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsApplyToLUs', Name='Auf LUs anwenden', Description=NULL, Help=NULL WHERE AD_Element_ID=542500
;

-- 2018-04-20T11:59:31.072
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsApplyToLUs', Name='Auf LUs anwenden', Description=NULL, Help=NULL, AD_Element_ID=542500 WHERE UPPER(ColumnName)='ISAPPLYTOLUS' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2018-04-20T11:59:31.074
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsApplyToLUs', Name='Auf LUs anwenden', Description=NULL, Help=NULL WHERE AD_Element_ID=542500 AND IsCentrallyMaintained='Y'
;

-- 2018-04-20T12:00:15.039
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='IsApplyToCUs', Name='Auf CUs anwenden', PrintName='Auf CUs anwenden',Updated=TO_TIMESTAMP('2018-04-20 12:00:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542502
;

-- 2018-04-20T12:00:15.040
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsApplyToCUs', Name='Auf CUs anwenden', Description=NULL, Help=NULL WHERE AD_Element_ID=542502
;

-- 2018-04-20T12:00:15.042
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsApplyToCUs', Name='Auf CUs anwenden', Description=NULL, Help=NULL, AD_Element_ID=542502 WHERE UPPER(ColumnName)='ISAPPLYTOCUS' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2018-04-20T12:00:15.044
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsApplyToCUs', Name='Auf CUs anwenden', Description=NULL, Help=NULL WHERE AD_Element_ID=542502 AND IsCentrallyMaintained='Y'
;

-- 2018-04-20T12:00:15.045
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Auf CUs anwenden', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=542502) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 542502)
;

-- 2018-04-20T12:00:15.056
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Auf CUs anwenden', Name='Auf CUs anwenden' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=542502)
;

-- 2018-04-20T12:00:23.996
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-04-20 12:00:23','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Auf CUs anwenden',PrintName='Auf CUs anwenden' WHERE AD_Element_ID=542502 AND AD_Language='de_CH'
;

-- 2018-04-20T12:00:23.998
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542502,'de_CH') 
;

-- 2018-04-20T12:00:33.673
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-04-20 12:00:33','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Apply to CUs',PrintName='Apply to CUs' WHERE AD_Element_ID=542502 AND AD_Language='en_GB'
;

-- 2018-04-20T12:00:33.676
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542502,'en_GB') 
;

-- 2018-04-20T12:00:41.514
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-04-20 12:00:41','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Apply to CUs',PrintName='Apply to CUs' WHERE AD_Element_ID=542502 AND AD_Language='en_US'
;

-- 2018-04-20T12:00:41.518
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542502,'en_US') 
;

-- 2018-04-20T12:02:42.500
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,543978,0,'IsOnlyApplyToTopLevel',TO_TIMESTAMP('2018-04-20 12:02:42','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits','Y','Nur auf Top-Level HU anwenden','Nur auf Top-Level HU anwenden',TO_TIMESTAMP('2018-04-20 12:02:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-04-20T12:02:42.501
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=543978 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2018-04-20T12:02:50.519
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-04-20 12:02:50','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Element_ID=543978 AND AD_Language='de_CH'
;

-- 2018-04-20T12:02:50.521
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543978,'de_CH') 
;

-- 2018-04-20T12:08:12.388
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-04-20 12:08:12','YYYY-MM-DD HH24:MI:SS'),Name='Only apply to top-level HUs',PrintName='Only apply to top-level HUs' WHERE AD_Element_ID=543978 AND AD_Language='en_US'
;

-- 2018-04-20T12:08:12.391
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543978,'en_US') 
;

-- 2018-04-20T12:08:22.020
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-04-20 12:08:22','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Element_ID=543978 AND AD_Language='en_US'
;

-- 2018-04-20T12:08:22.024
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543978,'en_US') 
;

-- 2018-04-20T12:09:18.943
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='IsApplyToTopLevelHUsOnly',Updated=TO_TIMESTAMP('2018-04-20 12:09:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543978
;

-- 2018-04-20T12:09:18.944
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsApplyToTopLevelHUsOnly', Name='Nur auf Top-Level HU anwenden', Description=NULL, Help=NULL WHERE AD_Element_ID=543978
;

-- 2018-04-20T12:09:18.946
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsApplyToTopLevelHUsOnly', Name='Nur auf Top-Level HU anwenden', Description=NULL, Help=NULL, AD_Element_ID=543978 WHERE UPPER(ColumnName)='ISAPPLYTOTOPLEVELHUSONLY' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2018-04-20T12:09:18.948
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsApplyToTopLevelHUsOnly', Name='Nur auf Top-Level HU anwenden', Description=NULL, Help=NULL WHERE AD_Element_ID=543978 AND IsCentrallyMaintained='Y'
;

-- 2018-04-20T12:09:29.881
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,559735,543978,0,20,540607,'N','IsApplyToTopLevelHUsOnly',TO_TIMESTAMP('2018-04-20 12:09:29','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.handlingunits',1,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','Nur auf Top-Level HU anwenden',0,0,TO_TIMESTAMP('2018-04-20 12:09:29','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2018-04-20T12:09:29.882
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=559735 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-04-20T12:14:20.015
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET DefaultValue='N',Updated=TO_TIMESTAMP('2018-04-20 12:14:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=559735
;

-- 2018-04-20T12:14:24.437
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('M_HU_Process','ALTER TABLE public.M_HU_Process ADD COLUMN IsApplyToTopLevelHUsOnly CHAR(1) DEFAULT ''N'' CHECK (IsApplyToTopLevelHUsOnly IN (''Y'',''N'')) NOT NULL')
;

/* DDL */ SELECT public.db_alter_table('M_HU_Process','ALTER TABLE M_HU_Process RENAME COLUMN IsApplyLU TO IsApplyToLUs');
/* DDL */ SELECT public.db_alter_table('M_HU_Process','ALTER TABLE M_HU_Process RENAME COLUMN IsApplyTU TO IsApplyToTUs');
/* DDL */ SELECT public.db_alter_table('M_HU_Process','ALTER TABLE M_HU_Process RENAME COLUMN IsApplyVirtualPI TO IsApplyToCUs');
-- 2018-04-20T14:15:40.648
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,559734,563612,0,540605,TO_TIMESTAMP('2018-04-20 14:15:40','YYYY-MM-DD HH24:MI:SS'),100,'Entscheidet, ob der Prozess als Aktion im Handling-Unit-Editor (WebUI und SwingUI) auswählbar sein soll.',1,'de.metas.handlingunits','Y','Y','N','N','N','N','N','Als Benutzeraktion verfügbar machen',TO_TIMESTAMP('2018-04-20 14:15:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-04-20T14:15:40.651
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=563612 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2018-04-20T14:15:40.714
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,559735,563613,0,540605,TO_TIMESTAMP('2018-04-20 14:15:40','YYYY-MM-DD HH24:MI:SS'),100,1,'de.metas.handlingunits','Y','Y','N','N','N','N','N','Nur auf Top-Level HU anwenden',TO_TIMESTAMP('2018-04-20 14:15:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-04-20T14:15:40.715
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=563613 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2018-04-20T14:16:13.005
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET SeqNo=42,Updated=TO_TIMESTAMP('2018-04-20 14:16:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=563612
;

-- 2018-04-20T14:16:55.701
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsSameLine='Y', SeqNo=45,Updated=TO_TIMESTAMP('2018-04-20 14:16:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=563613
;

-- Picking TU Label (Jasper)_Picking TU Label
-- 2018-04-20T14:20:07.490
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO M_HU_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,Created,CreatedBy,IsActive,IsApplyToCUs,IsApplyToLUs,IsApplyToTopLevelHUsOnly,IsApplyToTUs,IsProvideAsUserAction,M_HU_Process_ID,Updated,UpdatedBy) VALUES (0,0,540933,TO_TIMESTAMP('2018-04-20 14:20:07','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','Y','N',540016,TO_TIMESTAMP('2018-04-20 14:20:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Wareneingangsetikett LU (Jasper)_Wareneingangsetikett
-- 2018-04-20T14:24:51.897
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE M_HU_Process SET IsApplyToTopLevelHUsOnly='Y',Updated=TO_TIMESTAMP('2018-04-20 14:24:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE M_HU_Process_ID=540004
;

