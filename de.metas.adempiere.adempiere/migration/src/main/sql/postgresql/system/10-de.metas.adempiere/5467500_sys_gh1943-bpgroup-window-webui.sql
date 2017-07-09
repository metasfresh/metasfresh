-- 2017-07-09T09:09:50.606
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,543380,0,'C_BP_Group_Acct_ID',TO_TIMESTAMP('2017-07-09 09:09:50','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Business Partner Group Acct','Business Partner Group Acct',TO_TIMESTAMP('2017-07-09 09:09:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-09T09:09:50.614
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=543380 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2017-07-09T09:09:50.656
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,557011,543380,0,13,395,'N','C_BP_Group_Acct_ID',TO_TIMESTAMP('2017-07-09 09:09:50','YYYY-MM-DD HH24:MI:SS'),100,'D',10,'Y','Y','N','N','N','Y','Y','N','N','N','N','Business Partner Group Acct',TO_TIMESTAMP('2017-07-09 09:09:50','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 2017-07-09T09:09:50.658
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=557011 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2017-07-09T09:09:50.661
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER SEQUENCE C_BP_GROUP_ACCT_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 RESTART 1000000
;

-- 2017-07-09T09:09:50.661
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE C_BP_Group_Acct ADD COLUMN C_BP_Group_Acct_ID numeric(10,0) NOT NULL DEFAULT nextval('c_bp_group_acct_seq')
;

-- 2017-07-09T09:09:50.688
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE C_BP_Group_Acct DROP CONSTRAINT IF EXISTS c_bp_group_acct_pkey
;

-- 2017-07-09T09:09:50.691
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE C_BP_Group_Acct DROP CONSTRAINT IF EXISTS c_bp_group_acct_key
;

-- 2017-07-09T09:09:50.691
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE C_BP_Group_Acct ADD CONSTRAINT c_bp_group_acct_pkey PRIMARY KEY (C_BP_Group_Acct_ID)
;

-- 2017-07-09T09:09:50.733
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,557011,558812,0,323,TO_TIMESTAMP('2017-07-09 09:09:50','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','Y','N','N','N','N','N','N','N','Business Partner Group Acct',TO_TIMESTAMP('2017-07-09 09:09:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-09T09:09:50.734
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=558812 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

