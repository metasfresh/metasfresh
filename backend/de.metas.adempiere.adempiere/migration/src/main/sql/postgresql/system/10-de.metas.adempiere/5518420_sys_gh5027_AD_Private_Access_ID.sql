-- 2019-04-05T22:04:23.339
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,576563,0,'AD_Private_Access_ID',TO_TIMESTAMP('2019-04-05 22:04:23','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','AD_Private_Access','AD_Private_Access',TO_TIMESTAMP('2019-04-05 22:04:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-04-05T22:04:23.349
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=576563 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-04-05T22:04:23.574
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,567637,576563,0,13,627,'AD_Private_Access_ID',TO_TIMESTAMP('2019-04-05 22:04:23','YYYY-MM-DD HH24:MI:SS'),100,'D',10,'Y','Y','N','N','N','Y','Y','N','N','N','N','AD_Private_Access',TO_TIMESTAMP('2019-04-05 22:04:23','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 2019-04-05T22:04:23.585
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=567637 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-04-05T22:04:23.628
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER SEQUENCE AD_PRIVATE_ACCESS_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 RESTART 1000000
;

-- 2019-04-05T22:04:23.638
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE AD_Private_Access ADD COLUMN AD_Private_Access_ID numeric(10,0)
;

-- 2019-04-05T22:04:23.645
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE AD_Private_Access ALTER COLUMN AD_Private_Access_ID SET DEFAULT nextval('ad_private_access_seq')
;

-- 2019-04-05T22:04:23.661
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE AD_Private_Access ALTER COLUMN AD_Private_Access_ID SET NOT NULL
;

-- 2019-04-05T22:04:23.667
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE AD_Private_Access DROP CONSTRAINT IF EXISTS ad_private_access_pkey
;

-- 2019-04-05T22:04:23.674
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE AD_Private_Access DROP CONSTRAINT IF EXISTS ad_private_access_key
;

-- 2019-04-05T22:04:23.680
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE AD_Private_Access ADD CONSTRAINT ad_private_access_pkey PRIMARY KEY (AD_Private_Access_ID)
;

