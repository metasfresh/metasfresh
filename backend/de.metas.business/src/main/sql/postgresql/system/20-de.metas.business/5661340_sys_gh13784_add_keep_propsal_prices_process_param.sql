-- 2022-10-20T15:10:15.840Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581589,0,'IsKeepProposalPrices',TO_TIMESTAMP('2022-10-20 16:10:15','YYYY-MM-DD HH24:MI:SS'),100,'Maintain the porposal prices, no price calculation is required','D','Y','Keep Proposal Prices','Keep Proposal Prices',TO_TIMESTAMP('2022-10-20 16:10:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-10-20T15:10:15.850Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581589 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2022-10-20T15:10:56.800Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,Description,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,581589,0,531089,542330,20,'IsKeepProposalPrices',TO_TIMESTAMP('2022-10-20 16:10:56','YYYY-MM-DD HH24:MI:SS'),100,'N','Maintain the porposal prices, no price calculation is required','D',0,'Y','N','Y','N','N','N','Keep Proposal Prices',50,TO_TIMESTAMP('2022-10-20 16:10:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-10-20T15:10:56.804Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542330 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2022-10-20T16:08:11.931Z
UPDATE AD_Element_Trl SET Description='Wenn angehakt, dann findet keine Preis-Neuberechnung aufgrund der aktuellen Preis-Stammdaten statt', Name='Angebotspreise beibehalten', PrintName='Angebotspreise beibehalten',Updated=TO_TIMESTAMP('2022-10-20 17:08:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581589 AND AD_Language='de_DE'
;

-- 2022-10-20T16:08:11.959Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581589,'de_DE')
;

-- 2022-10-20T16:08:11.962Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581589,'de_DE')
;

-- 2022-10-20T16:08:44.311Z
UPDATE AD_Element_Trl SET Description='Wenn angehakt, dann findet keine Preis-Neuberechnung aufgrund der aktuellen Preis-Stammdaten statt', Name='Angebotspreise beibehalten', PrintName='Angebotspreise beibehalten',Updated=TO_TIMESTAMP('2022-10-20 17:08:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581589 AND AD_Language='de_CH'
;

-- 2022-10-20T16:08:44.317Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581589,'de_CH')
;

-- 2022-10-20T16:09:09.283Z
UPDATE AD_Element_Trl SET Description='Pflegen Sie die Angebotspreise, es ist keine Preiskalkulation erforderlich',Updated=TO_TIMESTAMP('2022-10-20 17:09:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581589 AND AD_Language='nl_NL'
;

-- 2022-10-20T16:09:09.287Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581589,'nl_NL')
;

-- 2022-10-20T16:09:23.049Z
UPDATE AD_Element_Trl SET Name='Angebotspreise beibehalten', PrintName='Angebotspreise beibehalten',Updated=TO_TIMESTAMP('2022-10-20 17:09:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581589 AND AD_Language='nl_NL'
;

-- 2022-10-20T16:09:23.050Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581589,'nl_NL')
;

-- 2022-10-20T16:09:55.053Z
UPDATE AD_Element_Trl SET Name='Garder Prix Proposition', PrintName='Garder Prix Proposition',Updated=TO_TIMESTAMP('2022-10-20 17:09:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581589 AND AD_Language='fr_CH'
;

-- 2022-10-20T16:09:55.057Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581589,'fr_CH')
;

-- 2022-10-20T16:10:07.572Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-10-20 17:10:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581589 AND AD_Language='de_DE'
;

-- 2022-10-20T16:10:07.576Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581589,'de_DE')
;

-- 2022-10-20T16:10:07.579Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581589,'de_DE')
;

