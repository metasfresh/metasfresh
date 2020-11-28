-- 2018-07-05T17:48:34.713
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,544172,0,'created_print_job_instructions',TO_TIMESTAMP('2018-07-05 17:48:34','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.printing','Y','created_print_job_instructions','created_print_job_instructions',TO_TIMESTAMP('2018-07-05 17:48:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-07-05T17:48:34.717
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=544172 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2018-07-05T17:48:34.796
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,560662,544172,0,16,541002,'created_print_job_instructions',TO_TIMESTAMP('2018-07-05 17:48:34','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.printing',35,'Y','Y','N','N','N','N','N','N','N','N','N','created_print_job_instructions',TO_TIMESTAMP('2018-07-05 17:48:34','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2018-07-05T17:48:34.802
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=560662 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-07-05T17:50:09.425
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,560662,565095,0,541163,TO_TIMESTAMP('2018-07-05 17:50:09','YYYY-MM-DD HH24:MI:SS'),100,35,'de.metas.printing','Y','Y','N','N','N','N','N','created_print_job_instructions',TO_TIMESTAMP('2018-07-05 17:50:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-07-05T17:50:09.438
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=565095 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2018-07-05T17:50:30.764
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET SeqNo=135,Updated=TO_TIMESTAMP('2018-07-05 17:50:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=565095
;

-- 2018-07-05T17:50:45.334
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsSameLine='Y',Updated=TO_TIMESTAMP('2018-07-05 17:50:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=565078
;

-- 2018-07-05T17:59:16.801
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='Created_Print_Job_Instructions', Name='Created Print Job Instructions', PrintName='Created Print Job Instructions',Updated=TO_TIMESTAMP('2018-07-05 17:59:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=544172
;

-- 2018-07-05T17:59:16.816
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='Created_Print_Job_Instructions', Name='Created Print Job Instructions', Description=NULL, Help=NULL WHERE AD_Element_ID=544172
;

-- 2018-07-05T17:59:16.819
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Created_Print_Job_Instructions', Name='Created Print Job Instructions', Description=NULL, Help=NULL, AD_Element_ID=544172 WHERE UPPER(ColumnName)='CREATED_PRINT_JOB_INSTRUCTIONS' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2018-07-05T17:59:16.823
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Created_Print_Job_Instructions', Name='Created Print Job Instructions', Description=NULL, Help=NULL WHERE AD_Element_ID=544172 AND IsCentrallyMaintained='Y'
;

-- 2018-07-05T17:59:16.824
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Created Print Job Instructions', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=544172) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 544172)
;

-- 2018-07-05T17:59:16.838
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Created Print Job Instructions', Name='Created Print Job Instructions' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=544172)
;

