-- 2020-11-23T09:33:42.772Z
-- URL zum Konzept
INSERT INTO AD_UI_ElementField (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_UI_ElementField_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,SeqNo,Type,Updated,UpdatedBy) VALUES (0,6516,0,540815,544472,TO_TIMESTAMP('2020-11-23 11:33:41','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,'widget',TO_TIMESTAMP('2020-11-23 11:33:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-11-23T09:34:12.058Z
-- URL zum Konzept
DELETE FROM AD_UI_ElementField WHERE AD_UI_ElementField_ID=540815
;

-- 2020-11-23T09:39:41.944Z
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,578566,0,'C_BPartner_Alt_Location_ID',TO_TIMESTAMP('2020-11-23 11:39:41','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Alt. Standort','Alt. Standort',TO_TIMESTAMP('2020-11-23 11:39:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-11-23T09:39:42.004Z
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=578566 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2020-11-23T09:40:24.309Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='Identifiziert die (Liefer-) Adresse des Geschäftspartners', Help='Identifiziert die Adresse des Geschäftspartners',Updated=TO_TIMESTAMP('2020-11-23 11:40:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578566 AND AD_Language='nl_NL'
;

-- 2020-11-23T09:40:24.410Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578566,'nl_NL') 
;

-- 2020-11-23T09:40:36.700Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='Identifiziert die (Liefer-) Adresse des Geschäftspartners', Help='Identifiziert die Adresse des Geschäftspartners',Updated=TO_TIMESTAMP('2020-11-23 11:40:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578566 AND AD_Language='de_DE'
;

-- 2020-11-23T09:40:36.760Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578566,'de_DE') 
;

-- 2020-11-23T09:40:36.886Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(578566,'de_DE') 
;

-- 2020-11-23T09:40:36.941Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='C_BPartner_Alt_Location_ID', Name='Alt. Standort', Description='Identifiziert die (Liefer-) Adresse des Geschäftspartners', Help='Identifiziert die Adresse des Geschäftspartners' WHERE AD_Element_ID=578566
;

-- 2020-11-23T09:40:36.995Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='C_BPartner_Alt_Location_ID', Name='Alt. Standort', Description='Identifiziert die (Liefer-) Adresse des Geschäftspartners', Help='Identifiziert die Adresse des Geschäftspartners', AD_Element_ID=578566 WHERE UPPER(ColumnName)='C_BPARTNER_ALT_LOCATION_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2020-11-23T09:40:37.051Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='C_BPartner_Alt_Location_ID', Name='Alt. Standort', Description='Identifiziert die (Liefer-) Adresse des Geschäftspartners', Help='Identifiziert die Adresse des Geschäftspartners' WHERE AD_Element_ID=578566 AND IsCentrallyMaintained='Y'
;

-- 2020-11-23T09:40:37.104Z
-- URL zum Konzept
UPDATE AD_Field SET Name='Alt. Standort', Description='Identifiziert die (Liefer-) Adresse des Geschäftspartners', Help='Identifiziert die Adresse des Geschäftspartners' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=578566) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 578566)
;

-- 2020-11-23T09:40:37.185Z
-- URL zum Konzept
UPDATE AD_Tab SET Name='Alt. Standort', Description='Identifiziert die (Liefer-) Adresse des Geschäftspartners', Help='Identifiziert die Adresse des Geschäftspartners', CommitWarning = NULL WHERE AD_Element_ID = 578566
;

-- 2020-11-23T09:40:37.241Z
-- URL zum Konzept
UPDATE AD_WINDOW SET Name='Alt. Standort', Description='Identifiziert die (Liefer-) Adresse des Geschäftspartners', Help='Identifiziert die Adresse des Geschäftspartners' WHERE AD_Element_ID = 578566
;

-- 2020-11-23T09:40:37.296Z
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'Alt. Standort', Description = 'Identifiziert die (Liefer-) Adresse des Geschäftspartners', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 578566
;

-- 2020-11-23T09:40:41.143Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='Identifiziert die (Liefer-) Adresse des Geschäftspartners',Updated=TO_TIMESTAMP('2020-11-23 11:40:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578566 AND AD_Language='de_CH'
;

-- 2020-11-23T09:40:41.199Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578566,'de_CH') 
;

-- 2020-11-23T09:40:50.562Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Help='Identifiziert die Adresse des Geschäftspartners',Updated=TO_TIMESTAMP('2020-11-23 11:40:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578566 AND AD_Language='de_CH'
;

-- 2020-11-23T09:40:50.616Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578566,'de_CH') 
;

-- 2020-11-23T09:41:20.379Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='Partner Location', IsTranslated='Y',Updated=TO_TIMESTAMP('2020-11-23 11:41:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578566 AND AD_Language='en_US'
;

-- 2020-11-23T09:41:20.432Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578566,'en_US') 
;

-- 2020-11-23T09:41:49.343Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='', Name='Partner Location', PO_Name='Partner Location', PO_PrintName='Partner Location', PrintName='Location',Updated=TO_TIMESTAMP('2020-11-23 11:41:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578566 AND AD_Language='en_US'
;

-- 2020-11-23T09:41:49.398Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578566,'en_US') 
;

-- 2020-11-23T09:42:14.454Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET PO_Description='Identifies the (ship from) address for this Business Partner',Updated=TO_TIMESTAMP('2020-11-23 11:42:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578566 AND AD_Language='en_US'
;

-- 2020-11-23T09:42:14.508Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578566,'en_US') 
;

-- 2020-11-23T09:42:32.825Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET PO_Help='The Partner address indicates the location of a Business Partner',Updated=TO_TIMESTAMP('2020-11-23 11:42:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578566 AND AD_Language='en_US'
;

-- 2020-11-23T09:42:32.878Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578566,'en_US') 
;

-- 2020-11-23T09:42:53.685Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET PO_Description='Identifiziert die (Auslieferungs-) Adresse des Geschäftspartners',Updated=TO_TIMESTAMP('2020-11-23 11:42:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578566 AND AD_Language='nl_NL'
;

-- 2020-11-23T09:42:53.739Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578566,'nl_NL') 
;

-- 2020-11-23T09:42:57.891Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET PO_Description='Identifiziert die (Auslieferungs-) Adresse des Geschäftspartners',Updated=TO_TIMESTAMP('2020-11-23 11:42:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578566 AND AD_Language='de_DE'
;

-- 2020-11-23T09:42:57.942Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578566,'de_DE') 
;

-- 2020-11-23T09:42:58.065Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(578566,'de_DE') 
;

-- 2020-11-23T09:43:06.059Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET PO_Description='Identifiziert die (Auslieferungs-) Adresse des Geschäftspartners', PO_Name='Standort', PO_PrintName='Standort',Updated=TO_TIMESTAMP('2020-11-23 11:43:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578566 AND AD_Language='de_CH'
;

-- 2020-11-23T09:43:06.112Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578566,'de_CH') 
;

-- 2020-11-23T09:43:09.126Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET PO_Name='Standort', PO_PrintName='Standort',Updated=TO_TIMESTAMP('2020-11-23 11:43:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578566 AND AD_Language='de_DE'
;

-- 2020-11-23T09:43:09.177Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578566,'de_DE') 
;

-- 2020-11-23T09:43:09.293Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(578566,'de_DE') 
;

-- 2020-11-23T09:43:12.880Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET PO_Name='Standort', PO_PrintName='Standort',Updated=TO_TIMESTAMP('2020-11-23 11:43:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578566 AND AD_Language='nl_NL'
;

-- 2020-11-23T09:43:12.935Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578566,'nl_NL') 
;

-- 2020-11-23T09:43:33.081Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET PO_Help='Identifiziert die Adresse des Geschäftspartners',Updated=TO_TIMESTAMP('2020-11-23 11:43:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578566 AND AD_Language='de_CH'
;

-- 2020-11-23T09:43:33.133Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578566,'de_CH') 
;

-- 2020-11-23T09:43:35.602Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET PO_Help='Identifiziert die Adresse des Geschäftspartners',Updated=TO_TIMESTAMP('2020-11-23 11:43:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578566 AND AD_Language='de_DE'
;

-- 2020-11-23T09:43:35.656Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578566,'de_DE') 
;

-- 2020-11-23T09:43:35.777Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(578566,'de_DE') 
;

-- 2020-11-23T09:43:39.538Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET PO_Help='Identifiziert die Adresse des Geschäftspartners',Updated=TO_TIMESTAMP('2020-11-23 11:43:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578566 AND AD_Language='nl_NL'
;

-- 2020-11-23T09:43:39.591Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578566,'nl_NL') 
;

-- 2020-11-23T09:44:20.993Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Val_Rule_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,572193,578566,0,19,114,131,'C_BPartner_Alt_Location_ID',TO_TIMESTAMP('2020-11-23 11:44:20','YYYY-MM-DD HH24:MI:SS'),100,'N','Identifiziert die (Liefer-) Adresse des Geschäftspartners','D',0,10,'Identifiziert die Adresse des Geschäftspartners','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Alt. Standort',0,0,TO_TIMESTAMP('2020-11-23 11:44:20','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2020-11-23T09:44:21.061Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=572193 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2020-11-23T09:44:21.123Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(578566) 
;

-- 2020-11-23T09:46:22.960Z
-- URL zum Konzept
UPDATE AD_Column SET Version=1.000000000000,Updated=TO_TIMESTAMP('2020-11-23 11:46:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=572193
;

-- 2020-11-23T09:51:10.431Z
-- URL zum Konzept
UPDATE AD_Column SET AD_Reference_ID=30, AD_Reference_Value_ID=159,Updated=TO_TIMESTAMP('2020-11-23 11:51:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=572193
;

-- 2020-11-23T09:55:58.595Z
-- URL zum Konzept
/* DDL */ SELECT public.db_alter_table('AD_User','ALTER TABLE public.AD_User ADD COLUMN C_BPartner_Alt_Location_ID NUMERIC(10)')
;

-- 2020-11-23T09:55:58.963Z
-- URL zum Konzept
ALTER TABLE AD_User ADD CONSTRAINT CBPartnerAltLocation_ADUser FOREIGN KEY (C_BPartner_Alt_Location_ID) REFERENCES public.C_BPartner_Location DEFERRABLE INITIALLY DEFERRED
;

-- 2020-11-23T10:04:12.264Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,572193,626429,0,118,0,TO_TIMESTAMP('2020-11-23 12:04:11','YYYY-MM-DD HH24:MI:SS'),100,'Identifiziert die (Liefer-) Adresse des Geschäftspartners',0,'U','Identifiziert die Adresse des Geschäftspartners',0,'Y','Y','Y','N','N','N','N','N','Alt. Standort',450,450,0,1,1,TO_TIMESTAMP('2020-11-23 12:04:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-11-23T10:04:12.329Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=626429 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-11-23T10:04:12.386Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578566) 
;

-- 2020-11-23T10:04:12.452Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=626429
;

-- 2020-11-23T10:04:12.507Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(626429)
;

-- 2020-11-23T10:04:28.073Z
-- URL zum Konzept
UPDATE AD_Field SET EntityType='D',Updated=TO_TIMESTAMP('2020-11-23 12:04:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=626429
;

-- 2020-11-23T10:06:29.021Z
-- URL zum Konzept
INSERT INTO AD_UI_ElementField (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_UI_ElementField_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,SeqNo,Type,Updated,UpdatedBy) VALUES (0,626429,0,540816,544472,TO_TIMESTAMP('2020-11-23 12:06:28','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,'widget',TO_TIMESTAMP('2020-11-23 12:06:28','YYYY-MM-DD HH24:MI:SS'),100)
;
