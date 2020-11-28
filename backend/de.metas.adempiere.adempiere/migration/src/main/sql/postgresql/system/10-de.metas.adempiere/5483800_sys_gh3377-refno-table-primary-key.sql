-- 2018-01-23T17:19:37.370
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,543782,0,'C_ReferenceNo_Type_Table_ID',TO_TIMESTAMP('2018-01-23 17:19:37','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.document.refid','Y','Reference No Type Table','Reference No Type Table',TO_TIMESTAMP('2018-01-23 17:19:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-23T17:19:37.376
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=543782 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2018-01-23T17:19:37.448
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,558623,543782,0,13,540405,'N','C_ReferenceNo_Type_Table_ID',TO_TIMESTAMP('2018-01-23 17:19:37','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.document.refid',10,'Y','Y','N','N','N','Y','Y','N','N','N','N','Reference No Type Table',TO_TIMESTAMP('2018-01-23 17:19:37','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 2018-01-23T17:19:37.450
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=558623 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-01-23T17:19:37.454
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER SEQUENCE C_REFERENCENO_TYPE_TABLE_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 RESTART 1000000
;

-- 2018-01-23T17:19:37.455
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE C_ReferenceNo_Type_Table ADD COLUMN C_ReferenceNo_Type_Table_ID numeric(10,0) NOT NULL DEFAULT nextval('c_referenceno_type_table_seq')
;

-- 2018-01-23T17:19:37.479
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE C_ReferenceNo_Type_Table DROP CONSTRAINT IF EXISTS c_referenceno_type_table_pkey
;

-- 2018-01-23T17:19:37.479
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE C_ReferenceNo_Type_Table DROP CONSTRAINT IF EXISTS c_referenceno_type_table_key
;

-- 2018-01-23T17:19:37.483
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE C_ReferenceNo_Type_Table ADD CONSTRAINT c_referenceno_type_table_pkey PRIMARY KEY (C_ReferenceNo_Type_Table_ID)
;

-- 2018-01-23T17:19:37.536
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,558623,561514,0,540434,TO_TIMESTAMP('2018-01-23 17:19:37','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.document.refid','Y','N','N','N','N','N','N','N','Reference No Type Table',TO_TIMESTAMP('2018-01-23 17:19:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-23T17:19:37.538
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=561514 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2018-01-23T17:19:37.562
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543782,NULL) 
;

