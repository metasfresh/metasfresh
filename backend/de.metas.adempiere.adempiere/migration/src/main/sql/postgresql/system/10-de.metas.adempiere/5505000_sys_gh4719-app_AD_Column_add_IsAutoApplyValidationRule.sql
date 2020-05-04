
-- 2018-10-31T10:37:16.923
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,575835,0,'IsAutoivalidateForNewRecord',TO_TIMESTAMP('2018-10-31 10:37:16','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Validierungsregel automatisch anwenden','Validierungsregel automatisch anwenden',TO_TIMESTAMP('2018-10-31 10:37:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-10-31T10:37:16.934
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=575835 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2018-10-31T10:37:22.961
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-10-31 10:37:22','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Element_ID=575835 AND AD_Language='de_CH'
;

-- 2018-10-31T10:37:22.968
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(575835,'de_CH') 
;

-- 2018-10-31T10:38:50.827
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-10-31 10:38:50','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Auto-apply validation rule',PrintName='Auto-apply validation rule',Description='If a validation rule (AD_Val_Rule_ID) is set and a new record is created where the column is empty, then apply the validation rule and insert the first result into the new record.' WHERE AD_Element_ID=575835 AND AD_Language='en_US'
;

-- 2018-10-31T10:38:50.831
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(575835,'en_US') 
;

-- 2018-10-31T10:38:54.389
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Description='If a validation rule (AD_Val_Rule_ID) is set and a new record is created where the column is empty, then apply the validation rule and insert the first result into the new record.',Updated=TO_TIMESTAMP('2018-10-31 10:38:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=575835
;

-- 2018-10-31T10:38:54.391
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsAutoivalidateForNewRecord', Name='Validierungsregel automatisch anwenden', Description='If a validation rule (AD_Val_Rule_ID) is set and a new record is created where the column is empty, then apply the validation rule and insert the first result into the new record.', Help=NULL WHERE AD_Element_ID=575835
;

-- 2018-10-31T10:38:54.392
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsAutoivalidateForNewRecord', Name='Validierungsregel automatisch anwenden', Description='If a validation rule (AD_Val_Rule_ID) is set and a new record is created where the column is empty, then apply the validation rule and insert the first result into the new record.', Help=NULL, AD_Element_ID=575835 WHERE UPPER(ColumnName)='ISAUTOIVALIDATEFORNEWRECORD' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2018-10-31T10:38:54.393
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsAutoivalidateForNewRecord', Name='Validierungsregel automatisch anwenden', Description='If a validation rule (AD_Val_Rule_ID) is set and a new record is created where the column is empty, then apply the validation rule and insert the first result into the new record.', Help=NULL WHERE AD_Element_ID=575835 AND IsCentrallyMaintained='Y'
;

-- 2018-10-31T10:38:54.394
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Validierungsregel automatisch anwenden', Description='If a validation rule (AD_Val_Rule_ID) is set and a new record is created where the column is empty, then apply the validation rule and insert the first result into the new record.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=575835) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 575835)
;

-- 2018-10-31T10:39:21.249
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,DefaultValue,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAutocomplete,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,IsAdvancedText,IsLazyLoading,AD_Table_ID,IsCalculated,AD_Column_ID,IsDimension,IsMandatory,IsStaleable,IsUseDocSequence,IsRangeFilter,IsShowFilterIncrementButtons,IsDLMPartitionBoundary,IsGenericZoomKeyColumn,SelectionColumnSeqNo,AD_Org_ID,AD_Element_ID,EntityType,IsForceIncludeInGeneratedModel,IsGenericZoomOrigin,Description,ColumnName,Name) VALUES (20,'N',1,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2018-10-31 10:39:21','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','N','N','Y','N',TO_TIMESTAMP('2018-10-31 10:39:21','YYYY-MM-DD HH24:MI:SS'),100,'N','N',101,'N',563397,'N','Y','N','N','N','N','N','N',0,0,575835,'D','N','N','If a validation rule (AD_Val_Rule_ID) is set and a new record is created where the column is empty, then apply the validation rule and insert the first result into the new record.','IsAutoivalidateForNewRecord','Validierungsregel automatisch anwenden')
;

-- 2018-10-31T10:39:21.251
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=563397 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-10-31T10:40:31.186
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsSameLine='N',Updated=TO_TIMESTAMP('2018-10-31 10:40:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=172
;

-- 2018-10-31T10:42:44.543
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='N',Updated=TO_TIMESTAMP('2018-10-31 10:42:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=168
;

-- 2018-10-31T10:42:59.277
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET TechnicalNote='will be set from AD_Element',Updated=TO_TIMESTAMP('2018-10-31 10:42:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=168
;

-- 2018-10-31T10:45:04.591
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,563397,569781,0,101,0,TO_TIMESTAMP('2018-10-31 10:45:04','YYYY-MM-DD HH24:MI:SS'),100,'If a validation rule (AD_Val_Rule_ID) is set and a new record is created where the column is empty, then apply the validation rule and insert the first result into the new record.',0,'@AD_Reference_ID@=17 | @AD_Reference_ID@=18 | @AD_Reference_ID@=19 | @AD_Reference_ID@=28 | @AD_Reference_ID@=30','D',0,'Y','Y','Y','N','N','N','N','Y','Validierungsregel automatisch anwenden',173,173,0,1,1,TO_TIMESTAMP('2018-10-31 10:45:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-10-31T10:45:04.594
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=569781 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2018-10-31T10:49:36.122
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsSameLine='N',Updated=TO_TIMESTAMP('2018-10-31 10:49:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=5122
;


-- 2018-10-31T10:53:41.897
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='IsAutoApplyValidationRule',Updated=TO_TIMESTAMP('2018-10-31 10:53:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=575835
;

-- 2018-10-31T10:53:41.902
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsAutoApplyValidationRule', Name='Validierungsregel automatisch anwenden', Description='If a validation rule (AD_Val_Rule_ID) is set and a new record is created where the column is empty, then apply the validation rule and insert the first result into the new record.', Help=NULL WHERE AD_Element_ID=575835
;

-- 2018-10-31T10:53:41.905
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsAutoApplyValidationRule', Name='Validierungsregel automatisch anwenden', Description='If a validation rule (AD_Val_Rule_ID) is set and a new record is created where the column is empty, then apply the validation rule and insert the first result into the new record.', Help=NULL, AD_Element_ID=575835 WHERE UPPER(ColumnName)='ISAUTOAPPLYVALIDATIONRULE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2018-10-31T10:53:41.907
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsAutoApplyValidationRule', Name='Validierungsregel automatisch anwenden', Description='If a validation rule (AD_Val_Rule_ID) is set and a new record is created where the column is empty, then apply the validation rule and insert the first result into the new record.', Help=NULL WHERE AD_Element_ID=575835 AND IsCentrallyMaintained='Y'
;

-- 2018-11-01T13:54:35.912
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsSameLine='Y', SeqNo=165,Updated=TO_TIMESTAMP('2018-11-01 13:54:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=171
;

