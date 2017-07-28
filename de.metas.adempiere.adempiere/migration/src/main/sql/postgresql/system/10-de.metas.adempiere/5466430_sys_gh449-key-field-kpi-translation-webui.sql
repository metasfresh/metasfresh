-- 2017-06-28T14:44:24.988
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,556982,543363,0,13,540828,'N','WEBUI_KPI_Field_Trl_ID',TO_TIMESTAMP('2017-06-28 14:44:24','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.ui.web',10,'Y','Y','N','N','N','Y','Y','N','N','N','N','WEBUI_KPI_Field_Trl',TO_TIMESTAMP('2017-06-28 14:44:24','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 2017-06-28T14:44:24.996
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=556982 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2017-06-28T14:44:25.001
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE SEQUENCE WEBUI_KPI_FIELD_TRL_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 START 1000000
;

-- 2017-06-28T14:44:25.003
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE WEBUI_KPI_Field_Trl ADD COLUMN WEBUI_KPI_Field_Trl_ID numeric(10,0) NOT NULL DEFAULT nextval('webui_kpi_field_trl_seq')
;

-- 2017-06-28T14:44:25.048
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE WEBUI_KPI_Field_Trl DROP CONSTRAINT IF EXISTS webui_kpi_field_trl_pkey
;

-- 2017-06-28T14:44:25.048
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE WEBUI_KPI_Field_Trl DROP CONSTRAINT IF EXISTS webui_kpi_field_trl_key
;

-- 2017-06-28T14:44:25.049
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE WEBUI_KPI_Field_Trl ADD CONSTRAINT webui_kpi_field_trl_pkey PRIMARY KEY (WEBUI_KPI_Field_Trl_ID)
;

-- 2017-06-28T14:44:25.104
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,556982,558787,0,540837,TO_TIMESTAMP('2017-06-28 14:44:25','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.ui.web','Y','Y','N','N','N','N','N','N','N','WEBUI_KPI_Field_Trl',TO_TIMESTAMP('2017-06-28 14:44:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-28T14:44:25.106
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=558787 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-06-28T14:44:25.135
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,556982,558788,0,540835,TO_TIMESTAMP('2017-06-28 14:44:25','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.ui.web','Y','Y','N','N','N','N','N','N','N','WEBUI_KPI_Field_Trl',TO_TIMESTAMP('2017-06-28 14:44:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-28T14:44:25.136
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=558788 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

