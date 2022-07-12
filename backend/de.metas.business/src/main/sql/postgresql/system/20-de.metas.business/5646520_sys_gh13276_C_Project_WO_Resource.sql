-- 2022-07-12T10:56:39.888Z
UPDATE AD_Element_Trl SET Description='', Help='', IsTranslated='Y', Name='Dauer', PrintName='Dauer',Updated=TO_TIMESTAMP('2022-07-12 12:56:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=2320 AND AD_Language='de_CH'
;

-- 2022-07-12T10:56:39.890Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(2320,'de_CH') 
;

-- 2022-07-12T10:56:50.819Z
UPDATE AD_Element_Trl SET Description='', Help='', IsTranslated='Y', Name='Dauer', PrintName='Dauer',Updated=TO_TIMESTAMP('2022-07-12 12:56:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=2320 AND AD_Language='de_DE'
;

-- 2022-07-12T10:56:50.821Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(2320,'de_DE') 
;

-- 2022-07-12T10:56:50.829Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(2320,'de_DE') 
;

-- 2022-07-12T10:56:50.831Z
UPDATE AD_Column SET ColumnName='Duration', Name='Dauer', Description='', Help='' WHERE AD_Element_ID=2320
;

-- 2022-07-12T10:56:50.833Z
UPDATE AD_Process_Para SET ColumnName='Duration', Name='Dauer', Description='', Help='', AD_Element_ID=2320 WHERE UPPER(ColumnName)='DURATION' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-07-12T10:56:50.835Z
UPDATE AD_Process_Para SET ColumnName='Duration', Name='Dauer', Description='', Help='' WHERE AD_Element_ID=2320 AND IsCentrallyMaintained='Y'
;

-- 2022-07-12T10:56:50.836Z
UPDATE AD_Field SET Name='Dauer', Description='', Help='' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=2320) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 2320)
;

-- 2022-07-12T10:56:50.850Z
UPDATE AD_PrintFormatItem pi SET PrintName='Dauer', Name='Dauer' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=2320)
;

-- 2022-07-12T10:56:50.853Z
UPDATE AD_Tab SET Name='Dauer', Description='', Help='', CommitWarning = NULL WHERE AD_Element_ID = 2320
;

-- 2022-07-12T10:56:50.856Z
UPDATE AD_WINDOW SET Name='Dauer', Description='', Help='' WHERE AD_Element_ID = 2320
;

-- 2022-07-12T10:56:50.857Z
UPDATE AD_Menu SET   Name = 'Dauer', Description = '', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 2320
;

-- 2022-07-12T10:57:35.205Z
UPDATE AD_Element_Trl SET Description='Masseinheit der Dauer', Help='', IsTranslated='Y', Name='Einheit', PrintName='Einheit',Updated=TO_TIMESTAMP('2022-07-12 12:57:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=2321 AND AD_Language='de_CH'
;

-- 2022-07-12T10:57:35.207Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(2321,'de_CH') 
;

-- 2022-07-12T10:57:46.564Z
UPDATE AD_Element_Trl SET Description='Masseinheit der Dauer', Help='', IsTranslated='Y', Name='Einheit', PrintName='Einheit',Updated=TO_TIMESTAMP('2022-07-12 12:57:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=2321 AND AD_Language='de_DE'
;

-- 2022-07-12T10:57:46.565Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(2321,'de_DE') 
;

-- 2022-07-12T10:57:46.573Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(2321,'de_DE') 
;

-- 2022-07-12T10:57:46.575Z
UPDATE AD_Column SET ColumnName='DurationUnit', Name='Einheit', Description='Masseinheit der Dauer', Help='' WHERE AD_Element_ID=2321
;

-- 2022-07-12T10:57:46.576Z
UPDATE AD_Process_Para SET ColumnName='DurationUnit', Name='Einheit', Description='Masseinheit der Dauer', Help='', AD_Element_ID=2321 WHERE UPPER(ColumnName)='DURATIONUNIT' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-07-12T10:57:46.579Z
UPDATE AD_Process_Para SET ColumnName='DurationUnit', Name='Einheit', Description='Masseinheit der Dauer', Help='' WHERE AD_Element_ID=2321 AND IsCentrallyMaintained='Y'
;

-- 2022-07-12T10:57:46.580Z
UPDATE AD_Field SET Name='Einheit', Description='Masseinheit der Dauer', Help='' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=2321) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 2321)
;

-- 2022-07-12T10:57:46.591Z
UPDATE AD_PrintFormatItem pi SET PrintName='Einheit', Name='Einheit' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=2321)
;

-- 2022-07-12T10:57:46.594Z
UPDATE AD_Tab SET Name='Einheit', Description='Masseinheit der Dauer', Help='', CommitWarning = NULL WHERE AD_Element_ID = 2321
;

-- 2022-07-12T10:57:46.597Z
UPDATE AD_WINDOW SET Name='Einheit', Description='Masseinheit der Dauer', Help='' WHERE AD_Element_ID = 2321
;

-- 2022-07-12T10:57:46.599Z
UPDATE AD_Menu SET   Name = 'Einheit', Description = 'Masseinheit der Dauer', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 2321
;

-- 2022-07-12T11:00:17.537Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581117,0,'WOTestFacilityGroupName',TO_TIMESTAMP('2022-07-12 13:00:17','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Prüfanlagengruppe','Prüfanlagengruppe',TO_TIMESTAMP('2022-07-12 13:00:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-12T11:00:17.539Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581117 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2022-07-12T11:00:20.329Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-07-12 13:00:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581117 AND AD_Language='de_CH'
;

-- 2022-07-12T11:00:20.330Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581117,'de_CH') 
;

-- 2022-07-12T11:00:22.184Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-07-12 13:00:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581117 AND AD_Language='de_DE'
;

-- 2022-07-12T11:00:22.186Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581117,'de_DE') 
;

-- 2022-07-12T11:00:22.193Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581117,'de_DE') 
;

-- 2022-07-12T11:00:26.676Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Test facility group', PrintName='Test facility group',Updated=TO_TIMESTAMP('2022-07-12 13:00:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581117 AND AD_Language='en_US'
;

-- 2022-07-12T11:00:26.678Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581117,'en_US') 
;

-- Column: C_Project_WO_Resource.WOTestFacilityGroupName
-- 2022-07-12T11:00:39.427Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583643,581117,0,10,542161,'WOTestFacilityGroupName',TO_TIMESTAMP('2022-07-12 13:00:39','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,255,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Prüfanlagengruppe',0,0,TO_TIMESTAMP('2022-07-12 13:00:39','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-07-12T11:00:39.429Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583643 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-07-12T11:00:39.433Z
/* DDL */  select update_Column_Translation_From_AD_Element(581117) 
;

-- 2022-07-12T11:00:40.168Z
/* DDL */ SELECT public.db_alter_table('C_Project_WO_Resource','ALTER TABLE public.C_Project_WO_Resource ADD COLUMN WOTestFacilityGroupName VARCHAR(255)')
;

