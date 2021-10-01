-- 2021-09-24T12:24:50.159Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,579925,0,'IsVendorInOrderLinesRequired',TO_TIMESTAMP('2021-09-24 15:24:50','YYYY-MM-DD HH24:MI:SS'),100,'If ticked, then the process only creates purchase order lines for sales order lines that have a vendor set.','D','Y','Require vendor set in order line','Require vendor set in order line',TO_TIMESTAMP('2021-09-24 15:24:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-24T12:24:50.165Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=579925 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-09-24T12:25:15.685Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Wenn angehakt werden nur solche Auftragspositionen berücksichtigt, in denen ein Lieferant angegeben ist.', Name='Lieferant in Auftragspos. erforderlich', PrintName='Lieferant in Auftragspos. erforderlich',Updated=TO_TIMESTAMP('2021-09-24 15:25:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579925 AND AD_Language='de_CH'
;

-- 2021-09-24T12:25:15.690Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579925,'de_CH') 
;

-- 2021-09-24T12:25:28.642Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Wenn angehakt werden nur solche Auftragspositionen berücksichtigt, in denen ein Lieferant angegeben ist.', Name='Lieferant in Auftragspos. erforderlich', PrintName='Lieferant in Auftragspos. erforderlich',Updated=TO_TIMESTAMP('2021-09-24 15:25:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579925 AND AD_Language='de_DE'
;

-- 2021-09-24T12:25:28.642Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579925,'de_DE') 
;

-- 2021-09-24T12:25:28.647Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(579925,'de_DE') 
;

-- 2021-09-24T12:25:28.647Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsVendorInOrderLinesRequired', Name='Lieferant in Auftragspos. erforderlich', Description='Wenn angehakt werden nur solche Auftragspositionen berücksichtigt, in denen ein Lieferant angegeben ist.', Help=NULL WHERE AD_Element_ID=579925
;

-- 2021-09-24T12:25:28.648Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsVendorInOrderLinesRequired', Name='Lieferant in Auftragspos. erforderlich', Description='Wenn angehakt werden nur solche Auftragspositionen berücksichtigt, in denen ein Lieferant angegeben ist.', Help=NULL, AD_Element_ID=579925 WHERE UPPER(ColumnName)='ISVENDORINORDERLINESREQUIRED' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-09-24T12:25:28.649Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsVendorInOrderLinesRequired', Name='Lieferant in Auftragspos. erforderlich', Description='Wenn angehakt werden nur solche Auftragspositionen berücksichtigt, in denen ein Lieferant angegeben ist.', Help=NULL WHERE AD_Element_ID=579925 AND IsCentrallyMaintained='Y'
;

-- 2021-09-24T12:25:28.650Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Lieferant in Auftragspos. erforderlich', Description='Wenn angehakt werden nur solche Auftragspositionen berücksichtigt, in denen ein Lieferant angegeben ist.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579925) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579925)
;

-- 2021-09-24T12:25:28.661Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Lieferant in Auftragspos. erforderlich', Name='Lieferant in Auftragspos. erforderlich' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=579925)
;

-- 2021-09-24T12:25:28.661Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Lieferant in Auftragspos. erforderlich', Description='Wenn angehakt werden nur solche Auftragspositionen berücksichtigt, in denen ein Lieferant angegeben ist.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579925
;

-- 2021-09-24T12:25:28.663Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Lieferant in Auftragspos. erforderlich', Description='Wenn angehakt werden nur solche Auftragspositionen berücksichtigt, in denen ein Lieferant angegeben ist.', Help=NULL WHERE AD_Element_ID = 579925
;

-- 2021-09-24T12:25:28.663Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Lieferant in Auftragspos. erforderlich', Description = 'Wenn angehakt werden nur solche Auftragspositionen berücksichtigt, in denen ein Lieferant angegeben ist.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579925
;

-- 2021-09-24T12:25:41.261Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Wenn angehakt werden nur solche Auftragspositionen berücksichtigt, in denen ein Lieferant angegeben ist.', Name='Lieferant in Auftragspos. erforderlich', PrintName='Lieferant in Auftragspos. erforderlich',Updated=TO_TIMESTAMP('2021-09-24 15:25:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579925 AND AD_Language='nl_NL'
;

-- 2021-09-24T12:25:41.262Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579925,'nl_NL') 
;

-- 2021-09-24T12:26:44.033Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,Description,DisplayLogic,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,579925,0,193,542108,20,'IsVendorInOrderLinesRequired',TO_TIMESTAMP('2021-09-24 15:26:43','YYYY-MM-DD HH24:MI:SS'),100,'N','Wenn angehakt werden nur solche Auftragspositionen berücksichtigt, in denen ein Lieferant angegeben ist.','1=0','de.metas.order',0,'Y','N','Y','N','Y','N','Lieferant in Auftragspos. erforderlich',90,TO_TIMESTAMP('2021-09-24 15:26:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-24T12:26:44.036Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542108 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

