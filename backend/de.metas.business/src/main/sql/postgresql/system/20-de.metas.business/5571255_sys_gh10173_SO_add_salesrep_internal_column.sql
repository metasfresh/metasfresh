
-- 2020-10-30T15:43:14.927Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,572014,543385,0,30,540401,259,'SalesRepIntern_ID',TO_TIMESTAMP('2020-10-30 16:43:14','YYYY-MM-DD HH24:MI:SS'),100,'N','Sales Responsible Internal','D',0,10,'','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Sales Responsible',0,0,TO_TIMESTAMP('2020-10-30 16:43:14','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2020-10-30T15:43:14.967Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=572014 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2020-10-30T15:43:15.061Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(543385) 
;

-- 2020-10-30T15:43:35.996Z
-- URL zum Konzept
/* DDL */ SELECT public.db_alter_table('C_Order','ALTER TABLE public.C_Order ADD COLUMN SalesRepIntern_ID NUMERIC(10)')
;

-- 2020-10-30T15:43:36.952Z
-- URL zum Konzept
ALTER TABLE C_Order ADD CONSTRAINT SalesRepIntern_COrder FOREIGN KEY (SalesRepIntern_ID) REFERENCES public.AD_User DEFERRABLE INITIALLY DEFERRED
;

-- 2020-10-30T15:45:16.379Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='', Help='', IsTranslated='Y',Updated=TO_TIMESTAMP('2020-10-30 16:45:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=573173 AND AD_Language='de_CH'
;

-- 2020-10-30T15:45:16.417Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(573173,'de_CH') 
;

-- 2020-10-30T15:45:21.259Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='', Help='',Updated=TO_TIMESTAMP('2020-10-30 16:45:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=573173 AND AD_Language='en_US'
;

-- 2020-10-30T15:45:21.290Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(573173,'en_US') 
;

-- 2020-10-30T15:45:26.562Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='', Help='',Updated=TO_TIMESTAMP('2020-10-30 16:45:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=573173 AND AD_Language='nl_NL'
;

-- 2020-10-30T15:45:26.593Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(573173,'nl_NL') 
;

-- 2020-10-30T15:45:41.097Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='', Help='', IsTranslated='Y',Updated=TO_TIMESTAMP('2020-10-30 16:45:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=573173 AND AD_Language='de_DE'
;

-- 2020-10-30T15:45:41.121Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(573173,'de_DE') 
;

-- 2020-10-30T15:45:41.176Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(573173,'de_DE') 
;

-- 2020-10-30T15:45:41.199Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnName=NULL, Name='Verkäufer', Description='', Help='' WHERE AD_Element_ID=573173
;

-- 2020-10-30T15:45:41.232Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName=NULL, Name='Verkäufer', Description='', Help='' WHERE AD_Element_ID=573173 AND IsCentrallyMaintained='Y'
;

-- 2020-10-30T15:45:41.253Z
-- URL zum Konzept
UPDATE AD_Field SET Name='Verkäufer', Description='', Help='' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=573173) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 573173)
;

-- 2020-10-30T15:45:41.309Z
-- URL zum Konzept
UPDATE AD_Tab SET Name='Verkäufer', Description='', Help='', CommitWarning = NULL WHERE AD_Element_ID = 573173
;

-- 2020-10-30T15:45:41.333Z
-- URL zum Konzept
UPDATE AD_WINDOW SET Name='Verkäufer', Description='', Help='' WHERE AD_Element_ID = 573173
;

-- 2020-10-30T15:45:41.357Z
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'Verkäufer', Description = '', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 573173
;
