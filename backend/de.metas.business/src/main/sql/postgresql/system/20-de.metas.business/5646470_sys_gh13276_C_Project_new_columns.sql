-- 2022-07-12T09:57:23.266Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581096,0,'Specialist_Consultant_ID',TO_TIMESTAMP('2022-07-12 11:57:23','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Fachberater','Fachberater',TO_TIMESTAMP('2022-07-12 11:57:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-12T09:57:23.274Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581096 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2022-07-12T09:57:27.572Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-07-12 11:57:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581096 AND AD_Language='de_CH'
;

-- 2022-07-12T09:57:27.608Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581096,'de_CH') 
;

-- 2022-07-12T09:57:30.128Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-07-12 11:57:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581096 AND AD_Language='de_DE'
;

-- 2022-07-12T09:57:30.130Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581096,'de_DE') 
;

-- 2022-07-12T09:57:30.139Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581096,'de_DE') 
;

-- 2022-07-12T09:57:47.724Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Specialist consultant', PrintName='Specialist consultant',Updated=TO_TIMESTAMP('2022-07-12 11:57:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581096 AND AD_Language='en_US'
;

-- 2022-07-12T09:57:47.726Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581096,'en_US') 
;

-- Column: C_Project.Specialist_Consultant_ID
-- 2022-07-12T09:58:26.635Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583614,581096,0,30,110,203,'Specialist_Consultant_ID',TO_TIMESTAMP('2022-07-12 11:58:26','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Fachberater',0,0,TO_TIMESTAMP('2022-07-12 11:58:26','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-07-12T09:58:26.638Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583614 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-07-12T09:58:26.643Z
/* DDL */  select update_Column_Translation_From_AD_Element(581096) 
;

-- 2022-07-12T10:00:27.801Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581097,0,'BPartnerDepartment',TO_TIMESTAMP('2022-07-12 12:00:27','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Geschäftspartner-Abteilung','Geschäftspartner-Abteilung',TO_TIMESTAMP('2022-07-12 12:00:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-12T10:00:27.804Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581097 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2022-07-12T10:00:54.317Z
UPDATE AD_Element_Trl SET Description='Abteilung / Kostenträger des Geschäftspartners', IsTranslated='Y',Updated=TO_TIMESTAMP('2022-07-12 12:00:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581097 AND AD_Language='de_CH'
;

-- 2022-07-12T10:00:54.319Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581097,'de_CH') 
;

-- 2022-07-12T10:01:04.192Z
UPDATE AD_Element_Trl SET Description='Abteilung / Kostenträger des Geschäftspartners', IsTranslated='Y',Updated=TO_TIMESTAMP('2022-07-12 12:01:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581097 AND AD_Language='de_DE'
;

-- 2022-07-12T10:01:04.195Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581097,'de_DE') 
;

-- 2022-07-12T10:01:04.203Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581097,'de_DE') 
;

-- 2022-07-12T10:01:04.205Z
UPDATE AD_Column SET ColumnName='BPartnerDepartment', Name='Geschäftspartner-Abteilung', Description='Abteilung / Kostenträger des Geschäftspartners', Help=NULL WHERE AD_Element_ID=581097
;

-- 2022-07-12T10:01:04.207Z
UPDATE AD_Process_Para SET ColumnName='BPartnerDepartment', Name='Geschäftspartner-Abteilung', Description='Abteilung / Kostenträger des Geschäftspartners', Help=NULL, AD_Element_ID=581097 WHERE UPPER(ColumnName)='BPARTNERDEPARTMENT' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-07-12T10:01:04.209Z
UPDATE AD_Process_Para SET ColumnName='BPartnerDepartment', Name='Geschäftspartner-Abteilung', Description='Abteilung / Kostenträger des Geschäftspartners', Help=NULL WHERE AD_Element_ID=581097 AND IsCentrallyMaintained='Y'
;

-- 2022-07-12T10:01:04.212Z
UPDATE AD_Field SET Name='Geschäftspartner-Abteilung', Description='Abteilung / Kostenträger des Geschäftspartners', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=581097) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 581097)
;

-- 2022-07-12T10:01:04.224Z
UPDATE AD_Tab SET Name='Geschäftspartner-Abteilung', Description='Abteilung / Kostenträger des Geschäftspartners', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 581097
;

-- 2022-07-12T10:01:04.226Z
UPDATE AD_WINDOW SET Name='Geschäftspartner-Abteilung', Description='Abteilung / Kostenträger des Geschäftspartners', Help=NULL WHERE AD_Element_ID = 581097
;

-- 2022-07-12T10:01:04.229Z
UPDATE AD_Menu SET   Name = 'Geschäftspartner-Abteilung', Description = 'Abteilung / Kostenträger des Geschäftspartners', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 581097
;

-- 2022-07-12T10:01:19.788Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Business partner department', PrintName='Business partner department',Updated=TO_TIMESTAMP('2022-07-12 12:01:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581097 AND AD_Language='en_US'
;

-- 2022-07-12T10:01:19.790Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581097,'en_US') 
;

-- Column: C_Project.BPartnerDepartment
-- 2022-07-12T10:01:33.619Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583615,581097,0,10,203,'BPartnerDepartment',TO_TIMESTAMP('2022-07-12 12:01:33','YYYY-MM-DD HH24:MI:SS'),100,'N','Abteilung / Kostenträger des Geschäftspartners','D',0,255,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Geschäftspartner-Abteilung',0,0,TO_TIMESTAMP('2022-07-12 12:01:33','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-07-12T10:01:33.622Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583615 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-07-12T10:01:33.626Z
/* DDL */  select update_Column_Translation_From_AD_Element(581097) 
;

-- 2022-07-12T10:01:37.159Z
/* DDL */ SELECT public.db_alter_table('C_Project','ALTER TABLE public.C_Project ADD COLUMN BPartnerDepartment VARCHAR(255)')
;

-- 2022-07-12T10:01:48.588Z
/* DDL */ SELECT public.db_alter_table('C_Project','ALTER TABLE public.C_Project ADD COLUMN Specialist_Consultant_ID NUMERIC(10)')
;

-- 2022-07-12T10:01:48.685Z
ALTER TABLE C_Project ADD CONSTRAINT SpecialistConsultant_CProject FOREIGN KEY (Specialist_Consultant_ID) REFERENCES public.AD_User DEFERRABLE INITIALLY DEFERRED
;

-- 2022-07-12T10:03:35.793Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581098,0,'DateOfProvisionByBPartner',TO_TIMESTAMP('2022-07-12 12:03:35','YYYY-MM-DD HH24:MI:SS'),100,'Vom Geschäftspartner geplantes Beistellungsdatum für die benötigten Ressourcen.','D','Y','Beistellungsdatum','Beistellungsdatum',TO_TIMESTAMP('2022-07-12 12:03:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-12T10:03:35.795Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581098 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2022-07-12T10:03:40.817Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-07-12 12:03:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581098 AND AD_Language='de_CH'
;

-- 2022-07-12T10:03:40.819Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581098,'de_CH') 
;

-- 2022-07-12T10:03:44.412Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-07-12 12:03:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581098 AND AD_Language='de_DE'
;

-- 2022-07-12T10:03:44.415Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581098,'de_DE') 
;

-- 2022-07-12T10:03:44.424Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581098,'de_DE') 
;

-- 2022-07-12T10:04:07.484Z
UPDATE AD_Element_Trl SET Description='Provision date planned by the business partner for the required resources.', IsTranslated='Y', Name='Date of provision', PrintName='Date of provision',Updated=TO_TIMESTAMP('2022-07-12 12:04:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581098 AND AD_Language='en_US'
;

-- 2022-07-12T10:04:07.487Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581098,'en_US') 
;

-- Column: C_Project.DateOfProvisionByBPartner
-- 2022-07-12T10:04:22.372Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583616,581098,0,15,203,'DateOfProvisionByBPartner',TO_TIMESTAMP('2022-07-12 12:04:22','YYYY-MM-DD HH24:MI:SS'),100,'N','Vom Geschäftspartner geplantes Beistellungsdatum für die benötigten Ressourcen.','D',0,7,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Beistellungsdatum',0,0,TO_TIMESTAMP('2022-07-12 12:04:22','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-07-12T10:04:22.374Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583616 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-07-12T10:04:22.379Z
/* DDL */  select update_Column_Translation_From_AD_Element(581098) 
;

-- 2022-07-12T10:04:23.105Z
/* DDL */ SELECT public.db_alter_table('C_Project','ALTER TABLE public.C_Project ADD COLUMN DateOfProvisionByBPartner TIMESTAMP WITHOUT TIME ZONE')
;

-- Column: C_Project.AD_User_InCharge_ID
-- 2022-07-12T10:05:14.739Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583617,541510,0,30,540401,203,'AD_User_InCharge_ID',TO_TIMESTAMP('2022-07-12 12:05:14','YYYY-MM-DD HH24:MI:SS'),100,'N','Person, die bei einem fachlichen Problem vom System informiert wird.','de.metas.swat',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Betreuer',0,0,TO_TIMESTAMP('2022-07-12 12:05:14','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-07-12T10:05:14.742Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583617 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-07-12T10:05:14.747Z
/* DDL */  select update_Column_Translation_From_AD_Element(541510) 
;

-- Column: C_Project.AD_User_InCharge_ID
-- 2022-07-12T10:05:19.778Z
UPDATE AD_Column SET IsAutocomplete='Y',Updated=TO_TIMESTAMP('2022-07-12 12:05:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=583617
;

-- 2022-07-12T10:05:20.437Z
/* DDL */ SELECT public.db_alter_table('C_Project','ALTER TABLE public.C_Project ADD COLUMN AD_User_InCharge_ID NUMERIC(10)')
;

-- 2022-07-12T10:05:20.526Z
ALTER TABLE C_Project ADD CONSTRAINT ADUserInCharge_CProject FOREIGN KEY (AD_User_InCharge_ID) REFERENCES public.AD_User DEFERRABLE INITIALLY DEFERRED
;

-- 2022-07-12T10:06:14.257Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581099,0,'BPartnerTargetDate',TO_TIMESTAMP('2022-07-12 12:06:14','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Zieltermin des Geschäftspartners','Zieltermin des Geschäftspartners',TO_TIMESTAMP('2022-07-12 12:06:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-12T10:06:14.259Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581099 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2022-07-12T10:06:18.495Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-07-12 12:06:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581099 AND AD_Language='de_CH'
;

-- 2022-07-12T10:06:18.497Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581099,'de_CH') 
;

-- 2022-07-12T10:06:21.287Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-07-12 12:06:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581099 AND AD_Language='de_DE'
;

-- 2022-07-12T10:06:21.290Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581099,'de_DE') 
;

-- 2022-07-12T10:06:21.299Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581099,'de_DE') 
;

-- 2022-07-12T10:06:33.197Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Target date of business-partner', PrintName='Target date of business-partner',Updated=TO_TIMESTAMP('2022-07-12 12:06:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581099 AND AD_Language='en_US'
;

-- 2022-07-12T10:06:33.199Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581099,'en_US') 
;

-- Column: C_Project.BPartnerTargetDate
-- 2022-07-12T10:07:15.050Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583618,581099,0,15,203,'BPartnerTargetDate',TO_TIMESTAMP('2022-07-12 12:07:14','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,7,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Zieltermin des Geschäftspartners',0,0,TO_TIMESTAMP('2022-07-12 12:07:14','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-07-12T10:07:15.052Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583618 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-07-12T10:07:15.057Z
/* DDL */  select update_Column_Translation_From_AD_Element(581099) 
;

-- 2022-07-12T10:07:15.765Z
/* DDL */ SELECT public.db_alter_table('C_Project','ALTER TABLE public.C_Project ADD COLUMN BPartnerTargetDate TIMESTAMP WITHOUT TIME ZONE')
;

-- 2022-07-12T10:08:48.992Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581100,0,'WOProjectCreatedDate',TO_TIMESTAMP('2022-07-12 12:08:48','YYYY-MM-DD HH24:MI:SS'),100,'Datum, an dem das Prüfprojekt erzeugt wurde.','D','Y','Projekt erstellt','Projekt erstellt',TO_TIMESTAMP('2022-07-12 12:08:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-12T10:08:48.994Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581100 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2022-07-12T10:08:52.616Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-07-12 12:08:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581100 AND AD_Language='de_CH'
;

-- 2022-07-12T10:08:52.618Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581100,'de_CH') 
;

-- 2022-07-12T10:08:54.847Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-07-12 12:08:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581100 AND AD_Language='de_DE'
;

-- 2022-07-12T10:08:54.849Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581100,'de_DE') 
;

-- 2022-07-12T10:08:54.858Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581100,'de_DE') 
;

-- 2022-07-12T10:09:14.155Z
UPDATE AD_Element_Trl SET Description='Date on which the test project was created.', IsTranslated='Y', Name='Project created', PrintName='Project created',Updated=TO_TIMESTAMP('2022-07-12 12:09:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581100 AND AD_Language='en_US'
;

-- 2022-07-12T10:09:14.157Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581100,'en_US') 
;

