-- 2017-07-04T16:58:42.581
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,557002,542583,0,30,540499,540832,'N','VHU_ID',TO_TIMESTAMP('2017-07-04 16:58:42','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.handlingunits',10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Virtual Handling Unit (VHU)',0,TO_TIMESTAMP('2017-07-04 16:58:42','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2017-07-04T16:58:42.591
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=557002 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2017-07-04T17:00:28.968
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Name='CU Handling Unit (VHU)', PrintName='CU Handling Unit (VHU)',Updated=TO_TIMESTAMP('2017-07-04 17:00:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542583
;

-- 2017-07-04T17:00:28.976
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='VHU_ID', Name='CU Handling Unit (VHU)', Description=NULL, Help=NULL WHERE AD_Element_ID=542583
;

-- 2017-07-04T17:00:28.987
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='VHU_ID', Name='CU Handling Unit (VHU)', Description=NULL, Help=NULL, AD_Element_ID=542583 WHERE UPPER(ColumnName)='VHU_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2017-07-04T17:00:28.990
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='VHU_ID', Name='CU Handling Unit (VHU)', Description=NULL, Help=NULL WHERE AD_Element_ID=542583 AND IsCentrallyMaintained='Y'
;

-- 2017-07-04T17:00:28.991
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='CU Handling Unit (VHU)', Description=NULL, Help=NULL WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=542583) AND IsCentrallyMaintained='Y'
;

-- 2017-07-04T17:00:29.016
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='CU Handling Unit (VHU)', Name='CU Handling Unit (VHU)' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=542583)
;

-- 2017-07-04T17:00:53.018
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='VHU_Source_ID', Name='Ursprungs-VHU', PrintName='Ursprungs-VHU',Updated=TO_TIMESTAMP('2017-07-04 17:00:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543377
;

-- 2017-07-04T17:00:53.020
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='VHU_Source_ID', Name='Ursprungs-VHU', Description=NULL, Help=NULL WHERE AD_Element_ID=543377
;

-- 2017-07-04T17:00:53.031
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='VHU_Source_ID', Name='Ursprungs-VHU', Description=NULL, Help=NULL, AD_Element_ID=543377 WHERE UPPER(ColumnName)='VHU_SOURCE_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2017-07-04T17:00:53.032
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='VHU_Source_ID', Name='Ursprungs-VHU', Description=NULL, Help=NULL WHERE AD_Element_ID=543377 AND IsCentrallyMaintained='Y'
;

-- 2017-07-04T17:00:53.033
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Ursprungs-VHU', Description=NULL, Help=NULL WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=543377) AND IsCentrallyMaintained='Y'
;

-- 2017-07-04T17:00:53.046
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Ursprungs-VHU', Name='Ursprungs-VHU' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=543377)
;


-- 2017-07-04T17:02:38.200
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2017-07-04 17:02:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=557002
;


-- 2017-07-04T17:02:50.225
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsUpdateable='N',Updated=TO_TIMESTAMP('2017-07-04 17:02:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=556997
;

