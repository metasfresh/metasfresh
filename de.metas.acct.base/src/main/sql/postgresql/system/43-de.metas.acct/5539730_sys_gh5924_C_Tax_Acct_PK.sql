-- 2019-12-19T05:14:04.453Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,577440,0,'C_Tax_Acct_ID',TO_TIMESTAMP('2019-12-19 07:14:04','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','C_Tax_Acct','C_Tax_Acct',TO_TIMESTAMP('2019-12-19 07:14:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-12-19T05:14:04.461Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=577440 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-12-19T05:14:04.668Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,569784,577440,0,13,399,'C_Tax_Acct_ID',TO_TIMESTAMP('2019-12-19 07:14:04','YYYY-MM-DD HH24:MI:SS'),100,'D',10,'Y','Y','N','N','N','Y','Y','N','N','N','N','C_Tax_Acct',TO_TIMESTAMP('2019-12-19 07:14:04','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 2019-12-19T05:14:04.670Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=569784 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-12-19T05:14:04.723Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(577440) 
;

-- 2019-12-19T05:14:04.767Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER SEQUENCE C_TAX_ACCT_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 RESTART 1000000
;

-- 2019-12-19T05:14:04.776Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE C_Tax_Acct ADD COLUMN C_Tax_Acct_ID numeric(10,0)
;

-- 2019-12-19T05:14:04.878Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax_Acct SET C_Tax_Acct_ID=540002 WHERE 1=1 AND C_AcctSchema_ID=1000000.000000 AND C_Tax_ID=540003.000000
;

-- 2019-12-19T05:14:04.960Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax_Acct SET C_Tax_Acct_ID=540003 WHERE 1=1 AND C_AcctSchema_ID=1000000.000000 AND C_Tax_ID=540006.000000
;

-- 2019-12-19T05:14:05.047Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax_Acct SET C_Tax_Acct_ID=540004 WHERE 1=1 AND C_AcctSchema_ID=1000000.000000 AND C_Tax_ID=540007.000000
;

-- 2019-12-19T05:14:05.129Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax_Acct SET C_Tax_Acct_ID=540005 WHERE 1=1 AND C_AcctSchema_ID=1000000.000000 AND C_Tax_ID=1000000.000000
;

-- 2019-12-19T05:14:05.213Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax_Acct SET C_Tax_Acct_ID=540006 WHERE 1=1 AND C_AcctSchema_ID=1000000.000000 AND C_Tax_ID=1000003.000000
;

-- 2019-12-19T05:14:05.294Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax_Acct SET C_Tax_Acct_ID=540007 WHERE 1=1 AND C_AcctSchema_ID=1000000.000000 AND C_Tax_ID=1000004.000000
;

-- 2019-12-19T05:14:05.381Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax_Acct SET C_Tax_Acct_ID=540008 WHERE 1=1 AND C_AcctSchema_ID=1000000.000000 AND C_Tax_ID=1000005.000000
;

-- 2019-12-19T05:14:05.466Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax_Acct SET C_Tax_Acct_ID=540009 WHERE 1=1 AND C_AcctSchema_ID=1000000.000000 AND C_Tax_ID=1000006.000000
;

-- 2019-12-19T05:14:05.549Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax_Acct SET C_Tax_Acct_ID=540010 WHERE 1=1 AND C_AcctSchema_ID=1000000.000000 AND C_Tax_ID=1000009.000000
;

-- 2019-12-19T05:14:05.646Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax_Acct SET C_Tax_Acct_ID=540011 WHERE 1=1 AND C_AcctSchema_ID=1000000.000000 AND C_Tax_ID=1000010.000000
;

-- 2019-12-19T05:14:05.716Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax_Acct SET C_Tax_Acct_ID=540012 WHERE 1=1 AND C_AcctSchema_ID=1000000.000000 AND C_Tax_ID=1000011.000000
;

-- 2019-12-19T05:14:05.800Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax_Acct SET C_Tax_Acct_ID=540013 WHERE 1=1 AND C_AcctSchema_ID=1000000.000000 AND C_Tax_ID=1000012.000000
;

-- 2019-12-19T05:14:05.881Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax_Acct SET C_Tax_Acct_ID=540014 WHERE 1=1 AND C_AcctSchema_ID=1000000.000000 AND C_Tax_ID=1000013.000000
;

-- 2019-12-19T05:14:05.967Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax_Acct SET C_Tax_Acct_ID=540015 WHERE 1=1 AND C_AcctSchema_ID=1000000.000000 AND C_Tax_ID=1000014.000000
;

-- 2019-12-19T05:14:06.047Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax_Acct SET C_Tax_Acct_ID=540016 WHERE 1=1 AND C_AcctSchema_ID=1000000.000000 AND C_Tax_ID=1000016.000000
;

-- 2019-12-19T05:14:06.133Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax_Acct SET C_Tax_Acct_ID=540017 WHERE 1=1 AND C_AcctSchema_ID=1000000.000000 AND C_Tax_ID=1000017.000000
;

-- 2019-12-19T05:14:06.214Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax_Acct SET C_Tax_Acct_ID=540018 WHERE 1=1 AND C_AcctSchema_ID=1000000.000000 AND C_Tax_ID=1000018.000000
;

-- 2019-12-19T05:14:06.284Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax_Acct SET C_Tax_Acct_ID=540019 WHERE 1=1 AND C_AcctSchema_ID=1000000.000000 AND C_Tax_ID=1000019.000000
;

-- 2019-12-19T05:14:06.393Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax_Acct SET C_Tax_Acct_ID=540020 WHERE 1=1 AND C_AcctSchema_ID=1000000.000000 AND C_Tax_ID=1000020.000000
;

-- 2019-12-19T05:14:06.476Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax_Acct SET C_Tax_Acct_ID=540021 WHERE 1=1 AND C_AcctSchema_ID=1000000.000000 AND C_Tax_ID=1000021.000000
;

-- 2019-12-19T05:14:06.560Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax_Acct SET C_Tax_Acct_ID=540022 WHERE 1=1 AND C_AcctSchema_ID=1000000.000000 AND C_Tax_ID=1000022.000000
;

-- 2019-12-19T05:14:06.645Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax_Acct SET C_Tax_Acct_ID=540023 WHERE 1=1 AND C_AcctSchema_ID=1000000.000000 AND C_Tax_ID=1000023.000000
;

-- 2019-12-19T05:14:06.733Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax_Acct SET C_Tax_Acct_ID=540024 WHERE 1=1 AND C_AcctSchema_ID=1000000.000000 AND C_Tax_ID=1000024.000000
;

-- 2019-12-19T05:14:06.812Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax_Acct SET C_Tax_Acct_ID=540025 WHERE 1=1 AND C_AcctSchema_ID=1000000.000000 AND C_Tax_ID=1000025.000000
;

-- 2019-12-19T05:14:06.899Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax_Acct SET C_Tax_Acct_ID=540026 WHERE 1=1 AND C_AcctSchema_ID=1000000.000000 AND C_Tax_ID=1000026.000000
;

-- 2019-12-19T05:14:06.980Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax_Acct SET C_Tax_Acct_ID=540027 WHERE 1=1 AND C_AcctSchema_ID=1000000.000000 AND C_Tax_ID=1000027.000000
;

-- 2019-12-19T05:14:07.073Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax_Acct SET C_Tax_Acct_ID=540028 WHERE 1=1 AND C_AcctSchema_ID=1000000.000000 AND C_Tax_ID=1000028.000000
;

-- 2019-12-19T05:14:07.164Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax_Acct SET C_Tax_Acct_ID=540029 WHERE 1=1 AND C_AcctSchema_ID=1000000.000000 AND C_Tax_ID=1000029.000000
;

-- 2019-12-19T05:14:07.248Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax_Acct SET C_Tax_Acct_ID=540030 WHERE 1=1 AND C_AcctSchema_ID=1000000.000000 AND C_Tax_ID=1000030.000000
;

-- 2019-12-19T05:14:07.331Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax_Acct SET C_Tax_Acct_ID=540031 WHERE 1=1 AND C_AcctSchema_ID=1000000.000000 AND C_Tax_ID=1000031.000000
;

-- 2019-12-19T05:14:07.419Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Tax_Acct SET C_Tax_Acct_ID=540032 WHERE 1=1 AND C_AcctSchema_ID=1000000.000000 AND C_Tax_ID=1000032.000000
;

-- 2019-12-19T05:14:07.427Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE C_Tax_Acct ALTER COLUMN C_Tax_Acct_ID SET DEFAULT nextval('c_tax_acct_seq')
;

-- 2019-12-19T05:14:07.439Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE C_Tax_Acct ALTER COLUMN C_Tax_Acct_ID SET NOT NULL
;

-- 2019-12-19T05:14:07.448Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE C_Tax_Acct DROP CONSTRAINT IF EXISTS c_tax_acct_pkey
;

-- 2019-12-19T05:14:07.462Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE C_Tax_Acct DROP CONSTRAINT IF EXISTS c_tax_acct_key
;

-- 2019-12-19T05:14:07.469Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE C_Tax_Acct ADD CONSTRAINT c_tax_acct_pkey PRIMARY KEY (C_Tax_Acct_ID)
;

-- 2019-12-19T05:14:07.658Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,569784,593806,0,333,TO_TIMESTAMP('2019-12-19 07:14:07','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','C_Tax_Acct',TO_TIMESTAMP('2019-12-19 07:14:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-12-19T05:14:07.660Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=593806 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-12-19T05:14:07.663Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577440) 
;

-- 2019-12-19T05:14:07.683Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=593806
;

-- 2019-12-19T05:14:07.690Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(593806)
;

-- 2019-12-19T05:14:07.809Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,569784,593807,0,53077,TO_TIMESTAMP('2019-12-19 07:14:07','YYYY-MM-DD HH24:MI:SS'),100,10,'EE04','Y','N','N','N','N','N','N','N','C_Tax_Acct',TO_TIMESTAMP('2019-12-19 07:14:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-12-19T05:14:07.813Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=593807 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-12-19T05:14:07.820Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577440) 
;

-- 2019-12-19T05:14:07.828Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=593807
;

-- 2019-12-19T05:14:07.834Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(593807)
;

-- 2019-12-19T05:27:20.149Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET IsChangeLog='Y',Updated=TO_TIMESTAMP('2019-12-19 07:27:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=399
;

