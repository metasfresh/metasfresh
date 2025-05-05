-- Column: I_BPartner.A_Name
-- 2023-06-21T09:33:27.117493800Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586837,1354,0,10,533,'A_Name',TO_TIMESTAMP('2023-06-21 11:33:26.902','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Name auf Kreditkarte oder des Kontoeigners','D',0,60,'E','"Name" bezeichnet den Namen des Eigentümers von Kreditkarte oder Konto.','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','Y','N',0,'Name auf Kreditkarte',0,0,TO_TIMESTAMP('2023-06-21 11:33:26.902','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-06-21T09:33:27.122680800Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586837 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-06-21T09:33:27.761422600Z
/* DDL */  select update_Column_Translation_From_AD_Element(1354) 
;

-- 2023-06-21T09:33:53.451390800Z
/* DDL */ SELECT public.db_alter_table('I_BPartner','ALTER TABLE public.I_BPartner ADD COLUMN A_Name VARCHAR(60)')
;

-- Column: I_BPartner.AccountNo
-- 2023-06-21T09:35:37.186962400Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586838,840,0,10,533,'AccountNo',TO_TIMESTAMP('2023-06-21 11:35:37.058','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Kontonummer','D',0,20,'The Account Number indicates the Number assigned to this bank account. ','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Konto-Nr.',0,0,TO_TIMESTAMP('2023-06-21 11:35:37.058','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-06-21T09:35:37.188525800Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586838 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-06-21T09:35:37.744703900Z
/* DDL */  select update_Column_Translation_From_AD_Element(840) 
;

-- 2023-06-21T09:35:40.721767700Z
/* DDL */ SELECT public.db_alter_table('I_BPartner','ALTER TABLE public.I_BPartner ADD COLUMN AccountNo VARCHAR(20)')
;

-- 2023-06-21T09:52:20.262000200Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582458,0,'location_bpartner_name',TO_TIMESTAMP('2023-06-21 11:52:20.139','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Location BPartner Name','Location BPartner Name',TO_TIMESTAMP('2023-06-21 11:52:20.139','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-06-21T09:52:20.266161Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582458 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: location_bpartner_name
-- 2023-06-21T09:52:31.457501600Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2023-06-21 11:52:31.456','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582458 AND AD_Language='en_US'
;

-- 2023-06-21T09:52:31.459970100Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582458,'en_US') 
;

-- Element: location_bpartner_name
-- 2023-06-21T09:53:02.662464700Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Adresse Abw. Firmenname', PrintName='Adresse Abw. Firmenname',Updated=TO_TIMESTAMP('2023-06-21 11:53:02.662','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582458 AND AD_Language='de_CH'
;

-- 2023-06-21T09:53:02.664576100Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582458,'de_CH') 
;

-- Element: location_bpartner_name
-- 2023-06-21T09:53:30.459009600Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Adresse Abw. Firmenname', PrintName='Adresse Abw. Firmenname',Updated=TO_TIMESTAMP('2023-06-21 11:53:30.459','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582458 AND AD_Language='de_DE'
;

-- 2023-06-21T09:53:30.460043900Z
UPDATE AD_Element SET Name='Adresse Abw. Firmenname', PrintName='Adresse Abw. Firmenname', Updated=TO_TIMESTAMP('2023-06-21 11:53:30.46','YYYY-MM-DD HH24:MI:SS.US') WHERE AD_Element_ID=582458
;

-- 2023-06-21T09:53:30.836589300Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582458,'de_DE') 
;

-- 2023-06-21T09:53:30.838180800Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582458,'de_DE') 
;

-- 2023-06-21T09:56:07.742705500Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582459,0,'location_name',TO_TIMESTAMP('2023-06-21 11:56:07.62','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Adresse Name','Adresse Name',TO_TIMESTAMP('2023-06-21 11:56:07.62','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-06-21T09:56:07.744301500Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582459 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: location_name
-- 2023-06-21T09:56:12.170655400Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2023-06-21 11:56:12.17','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582459 AND AD_Language='de_DE'
;

-- 2023-06-21T09:56:12.172744500Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582459,'de_DE') 
;

-- 2023-06-21T09:56:12.173322500Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582459,'de_DE') 
;

-- Element: location_name
-- 2023-06-21T09:56:13.524647600Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2023-06-21 11:56:13.524','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582459 AND AD_Language='de_CH'
;

-- 2023-06-21T09:56:13.526767Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582459,'de_CH') 
;

-- Element: location_name
-- 2023-06-21T09:56:30.596417900Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Location Name', PrintName='Location Name',Updated=TO_TIMESTAMP('2023-06-21 11:56:30.596','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582459 AND AD_Language='en_US'
;

-- 2023-06-21T09:56:30.598527700Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582459,'en_US') 
;

-- Column: I_BPartner.location_bpartner_name
-- 2023-06-21T09:57:17.701353700Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586839,582458,0,10,533,'location_bpartner_name',TO_TIMESTAMP('2023-06-21 11:57:17.578','YYYY-MM-DD HH24:MI:SS.US'),100,'N','D',0,255,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Adresse Abw. Firmenname',0,0,TO_TIMESTAMP('2023-06-21 11:57:17.578','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-06-21T09:57:17.702912Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586839 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-06-21T09:57:18.292411400Z
/* DDL */  select update_Column_Translation_From_AD_Element(582458) 
;

-- 2023-06-21T09:57:21.402542800Z
/* DDL */ SELECT public.db_alter_table('I_BPartner','ALTER TABLE public.I_BPartner ADD COLUMN location_bpartner_name VARCHAR(255)')
;

-- Column: I_BPartner.location_name
-- 2023-06-21T09:57:42.682378100Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586840,582459,0,10,533,'location_name',TO_TIMESTAMP('2023-06-21 11:57:42.557','YYYY-MM-DD HH24:MI:SS.US'),100,'N','D',0,255,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Adresse Name',0,0,TO_TIMESTAMP('2023-06-21 11:57:42.557','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-06-21T09:57:42.683940100Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586840 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-06-21T09:57:43.330617700Z
/* DDL */  select update_Column_Translation_From_AD_Element(582459) 
;

-- 2023-06-21T09:57:45.129433Z
/* DDL */ SELECT public.db_alter_table('I_BPartner','ALTER TABLE public.I_BPartner ADD COLUMN location_name VARCHAR(255)')
;

