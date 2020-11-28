-- 07.07.2015 11:56:39 OESZ
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,542864,0,'IsAllowMigratiponScripts',TO_TIMESTAMP('2015-07-07 11:56:39','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','Allow Migratipon Scripts','Allow Migratipon Scripts',TO_TIMESTAMP('2015-07-07 11:56:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 07.07.2015 11:56:39 OESZ
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=542864 AND NOT EXISTS (SELECT * FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 07.07.2015 11:56:54 OESZ
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DefaultValue,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,552577,542864,0,20,156,'N','IsAllowMigratiponScripts',TO_TIMESTAMP('2015-07-07 11:56:54','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.swat',1,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','Y','Allow Migratipon Scripts',0,TO_TIMESTAMP('2015-07-07 11:56:54','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 07.07.2015 11:56:54 OESZ
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=552577 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 07.07.2015 11:56:58 OESZ
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE AD_Role ADD COLUMN IsAllowedMigrationScripts CHAR(1) DEFAULT 'N' CHECK (IsAllowedMigrationScripts IN ('Y','N'))
;

update ad_element 
set ColumnName = 'IsAllowedMigrationScripts'
where AD_Element_ID = 542864;

update AD_Column
set ColumnName = 'IsAllowedMigrationScripts'
where AD_Column_ID = 552577;