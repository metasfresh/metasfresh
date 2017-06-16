-- 2017-06-15T09:08:29.716
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,543369,0,'M_AttributeUse_ID',TO_TIMESTAMP('2017-06-15 09:08:29','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','M_AttributeUse','M_AttributeUse',TO_TIMESTAMP('2017-06-15 09:08:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-15T09:08:29.737
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=543369 AND NOT EXISTS (SELECT * FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2017-06-15T09:08:29.847
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,556955,543369,0,13,563,'N','M_AttributeUse_ID',TO_TIMESTAMP('2017-06-15 09:08:29','YYYY-MM-DD HH24:MI:SS'),100,'D',10,'Y','Y','N','N','N','Y','Y','N','N','N','N','M_AttributeUse',TO_TIMESTAMP('2017-06-15 09:08:29','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 2017-06-15T09:08:29.849
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=556955 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2017-06-15T09:08:29.859
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER SEQUENCE M_ATTRIBUTEUSE_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 RESTART 1000000
;

-- 2017-06-15T09:08:29.861
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE M_AttributeUse ADD COLUMN M_AttributeUse_ID numeric(10,0) NOT NULL DEFAULT nextval('m_attributeuse_seq')
;

-- 2017-06-15T09:08:29.941
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE M_AttributeUse DROP CONSTRAINT IF EXISTS m_attributeuse_pkey
;

-- 2017-06-15T09:08:29.954
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE M_AttributeUse DROP CONSTRAINT IF EXISTS m_attributeuse_key
;

-- 2017-06-15T09:08:29.956
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE M_AttributeUse ADD CONSTRAINT m_attributeuse_pkey PRIMARY KEY (M_AttributeUse_ID)
;

-- 2017-06-15T09:08:30.164
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,556955,558766,0,467,TO_TIMESTAMP('2017-06-15 09:08:30','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','Y','N','N','N','N','N','N','N','M_AttributeUse',TO_TIMESTAMP('2017-06-15 09:08:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-15T09:08:30.168
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=558766 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;



create unique index M_AttributeUse_UQ on M_AttributeUse(M_AttributeSet_ID, M_Attribute_ID);

