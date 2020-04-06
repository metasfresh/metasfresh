
-- 2017-07-19T16:50:43.269
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,543381,0,'AD_BoilerPlate_Var_Eval_ID',TO_TIMESTAMP('2017-07-19 16:50:43','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.letters','Y','Boiler Plate Variable Evaluation Timing','Boiler Plate Variable Evaluation Timing',TO_TIMESTAMP('2017-07-19 16:50:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-19T16:50:43.274
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=543381 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2017-07-19T16:50:43.307
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,557016,543381,0,13,540124,'N','AD_BoilerPlate_Var_Eval_ID',TO_TIMESTAMP('2017-07-19 16:50:43','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.letters',10,'Y','Y','N','N','N','Y','Y','N','N','N','N','Boiler Plate Variable Evaluation Timing',TO_TIMESTAMP('2017-07-19 16:50:43','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 2017-07-19T16:50:43.309
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=557016 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2017-07-19T16:50:43.314
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER SEQUENCE AD_BOILERPLATE_VAR_EVAL_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 RESTART 1000000
;

-- 2017-07-19T16:50:43.314
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE AD_BoilerPlate_Var_Eval ADD COLUMN AD_BoilerPlate_Var_Eval_ID numeric(10,0) NOT NULL DEFAULT nextval('ad_boilerplate_var_eval_seq')
;

-- 2017-07-19T16:50:43.333
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE AD_BoilerPlate_Var_Eval DROP CONSTRAINT IF EXISTS ad_boilerplate_var_eval_pkey
;

-- 2017-07-19T16:50:43.333
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE AD_BoilerPlate_Var_Eval DROP CONSTRAINT IF EXISTS ad_boilerplate_var_eval_key
;

-- 2017-07-19T16:50:43.335
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE AD_BoilerPlate_Var_Eval ADD CONSTRAINT ad_boilerplate_var_eval_pkey PRIMARY KEY (AD_BoilerPlate_Var_Eval_ID)
;

-- 2017-07-19T16:50:43.384
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,557016,558813,0,540134,TO_TIMESTAMP('2017-07-19 16:50:43','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.letters','Y','Y','N','N','N','N','N','N','N','Boiler Plate Variable Evaluation Timing',TO_TIMESTAMP('2017-07-19 16:50:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-19T16:50:43.387
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=558813 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-07-19T17:14:50.097
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy) VALUES (0,540399,0,540124,TO_TIMESTAMP('2017-07-19 17:14:49','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Y','AD_BoilerPlate_Var_Eval_Unique','N',TO_TIMESTAMP('2017-07-19 17:14:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-19T17:14:50.108
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Index_Table_ID=540399 AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 2017-07-19T17:21:38.176
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,542325,540799,540399,0,TO_TIMESTAMP('2017-07-19 17:21:38','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',10,TO_TIMESTAMP('2017-07-19 17:21:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-19T17:21:46.804
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,542327,540800,540399,0,TO_TIMESTAMP('2017-07-19 17:21:46','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',20,TO_TIMESTAMP('2017-07-19 17:21:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-19T17:21:50.078
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE UNIQUE INDEX AD_BoilerPlate_Var_Eval_Unique ON AD_BoilerPlate_Var_Eval (AD_BoilerPlate_Var_ID,C_DocType_ID)
;

