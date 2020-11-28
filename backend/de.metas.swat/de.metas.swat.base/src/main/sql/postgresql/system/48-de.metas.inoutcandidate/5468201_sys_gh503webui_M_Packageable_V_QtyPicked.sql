-- 2017-07-20T11:53:55.860
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,557019,215,0,19,540823,'C_UOM_ID',TO_TIMESTAMP('2017-07-20 11:53:55','YYYY-MM-DD HH24:MI:SS'),100,'Maßeinheit','de.metas.inoutcandidate',10,'Eine eindeutige (nicht monetäre) Maßeinheit','Y','N','N','N','N','N','N','N','N','N','Maßeinheit',TO_TIMESTAMP('2017-07-20 11:53:55','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2017-07-20T11:53:55.910
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=557019 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2017-07-20T11:53:56.014
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,543384,0,'qtypickedplanned',TO_TIMESTAMP('2017-07-20 11:53:55','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.inoutcandidate','Y','qtypickedplanned','qtypickedplanned',TO_TIMESTAMP('2017-07-20 11:53:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-20T11:53:56.072
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=543384 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2017-07-20T11:53:56.191
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,557020,543384,0,29,540823,'qtypickedplanned',TO_TIMESTAMP('2017-07-20 11:53:55','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.inoutcandidate',131089,'Y','N','N','N','N','N','N','N','N','N','qtypickedplanned',TO_TIMESTAMP('2017-07-20 11:53:55','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2017-07-20T11:53:56.196
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=557020 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2017-07-20T11:55:09.019
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='QtyPickedPlanned', Name='Qty picked (planned)', PrintName='Qty picked (planned)',Updated=TO_TIMESTAMP('2017-07-20 11:55:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543384
;

-- 2017-07-20T11:55:09.023
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='QtyPickedPlanned', Name='Qty picked (planned)', Description=NULL, Help=NULL WHERE AD_Element_ID=543384
;

-- 2017-07-20T11:55:09.411
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='QtyPickedPlanned', Name='Qty picked (planned)', Description=NULL, Help=NULL, AD_Element_ID=543384 WHERE UPPER(ColumnName)='QTYPICKEDPLANNED' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2017-07-20T11:55:09.434
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='QtyPickedPlanned', Name='Qty picked (planned)', Description=NULL, Help=NULL WHERE AD_Element_ID=543384 AND IsCentrallyMaintained='Y'
;

-- 2017-07-20T11:55:09.437
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Qty picked (planned)', Description=NULL, Help=NULL WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=543384) AND IsCentrallyMaintained='Y'
;

-- 2017-07-20T11:55:09.512
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Qty picked (planned)', Name='Qty picked (planned)' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=543384)
;

-- 2017-07-20T11:55:17.621
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-07-20 11:55:17','YYYY-MM-DD HH24:MI:SS'),Name='Qty picked (planned)',PrintName='Qty picked (planned)' WHERE AD_Element_ID=543384 AND AD_Language='de_CH'
;

-- 2017-07-20T11:55:17.686
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543384,'de_CH') 
;

-- 2017-07-20T11:55:22.067
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-07-20 11:55:22','YYYY-MM-DD HH24:MI:SS'),Name='Qty picked (planned)',PrintName='Qty picked (planned)' WHERE AD_Element_ID=543384 AND AD_Language='nl_NL'
;

-- 2017-07-20T11:55:22.083
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543384,'nl_NL') 
;

-- 2017-07-20T11:55:26.966
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-07-20 11:55:26','YYYY-MM-DD HH24:MI:SS'),Name='Qty picked (planned)',PrintName='Qty picked (planned)' WHERE AD_Element_ID=543384 AND AD_Language='en_US'
;

-- 2017-07-20T11:55:26.981
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543384,'en_US') 
;

