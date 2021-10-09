-- 2020-11-23T09:50:02.003Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,578567,0,'C_Invoice_Candidate_Recompute_ID',TO_TIMESTAMP('2020-11-23 11:50:01','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.invoicecandidate','Y','C_Invoice_Candidate_Recompute','C_Invoice_Candidate_Recompute',TO_TIMESTAMP('2020-11-23 11:50:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-11-23T09:50:02.006Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=578567 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2020-11-23T09:50:02.255Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,572194,578567,0,13,540677,'C_Invoice_Candidate_Recompute_ID',TO_TIMESTAMP('2020-11-23 11:50:01','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.invoicecandidate',10,'Y','Y','N','N','N','Y','Y','N','N','N','N','C_Invoice_Candidate_Recompute',TO_TIMESTAMP('2020-11-23 11:50:01','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 2020-11-23T09:50:02.259Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=572194 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2020-11-23T09:50:02.291Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(578567) 
;

-- 2020-11-23T09:50:02.312Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER SEQUENCE C_INVOICE_CANDIDATE_RECOMPUTE_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 RESTART 1000000
;

-- 2020-11-23T09:50:02.322Z
ALTER TABLE C_Invoice_Candidate_Recompute ADD COLUMN C_Invoice_Candidate_Recompute_ID numeric(10,0)
    DEFAULT nextval('c_invoice_candidate_recompute_seq')
    NOT NULL;

ALTER TABLE C_Invoice_Candidate_Recompute ADD CONSTRAINT C_Invoice_Candidate_Recompute_pkey PRIMARY KEY (C_Invoice_Candidate_Recompute_ID);

