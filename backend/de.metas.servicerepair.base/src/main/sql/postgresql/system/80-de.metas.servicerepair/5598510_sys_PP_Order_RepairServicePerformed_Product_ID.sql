-- 2021-07-15T07:27:59.682Z
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,579498,0,'RepairServicePerformed_Product_ID',TO_TIMESTAMP('2021-07-15 10:27:59','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.servicerepair','Y','Dienstleistungsaufwand','Dienstleistungsaufwand',TO_TIMESTAMP('2021-07-15 10:27:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-07-15T07:27:59.719Z
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=579498 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-07-15T07:28:09.631Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-07-15 10:28:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579498 AND AD_Language='de_CH'
;

-- 2021-07-15T07:28:09.698Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579498,'de_CH') 
;

-- 2021-07-15T07:28:11.447Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-07-15 10:28:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579498 AND AD_Language='de_DE'
;

-- 2021-07-15T07:28:11.485Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579498,'de_DE') 
;

-- 2021-07-15T07:28:11.570Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(579498,'de_DE') 
;

-- 2021-07-15T07:28:25.095Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Repair Service Performed', PrintName='Repair Service Performed',Updated=TO_TIMESTAMP('2021-07-15 10:28:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579498 AND AD_Language='en_US'
;

-- 2021-07-15T07:28:25.130Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579498,'en_US') 
;

-- 2021-07-15T07:29:28.419Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,AD_Val_Rule_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,575062,579498,0,30,540272,53027,540531,'RepairServicePerformed_Product_ID',TO_TIMESTAMP('2021-07-15 10:29:27','YYYY-MM-DD HH24:MI:SS'),100,'N','EE01',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Dienstleistungsaufwand',0,0,TO_TIMESTAMP('2021-07-15 10:29:27','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-07-15T07:29:28.462Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=575062 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-07-15T07:29:28.542Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(579498) 
;

-- 2021-07-15T07:29:41.914Z
-- URL zum Konzept
UPDATE AD_Column SET EntityType='de.metas.servicerepair', IsForceIncludeInGeneratedModel='Y',Updated=TO_TIMESTAMP('2021-07-15 10:29:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=575062
;

-- 2021-07-15T07:30:22.307Z
-- URL zum Konzept
UPDATE AD_Column SET ReadOnlyLogic='@DocStatus/X@!CO',Updated=TO_TIMESTAMP('2021-07-15 10:30:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=575062
;


-- 2021-07-15T07:35:28.263Z
-- URL zum Konzept
/* DDL */ SELECT public.db_alter_table('PP_Order','ALTER TABLE public.PP_Order ADD COLUMN RepairServicePerformed_Product_ID NUMERIC(10)')
;

-- 2021-07-15T07:35:28.719Z
-- URL zum Konzept
ALTER TABLE PP_Order ADD CONSTRAINT RepairServicePerformedProduct_PPOrder FOREIGN KEY (RepairServicePerformed_Product_ID) REFERENCES public.M_Product DEFERRABLE INITIALLY DEFERRED
;

-- 2021-07-15T08:11:41.006Z
-- URL zum Konzept
UPDATE AD_Column SET IsAlwaysUpdateable='Y',Updated=TO_TIMESTAMP('2021-07-15 11:11:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=575062
;

