-- 2022-07-25T14:59:44.660Z
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581178,0,'ExciseNumber',TO_TIMESTAMP('2022-07-25 15:59:43','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Verbrauchsteuernummer','Verbrauchsteuernummer',TO_TIMESTAMP('2022-07-25 15:59:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-25T14:59:44.732Z
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581178 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2022-07-25T15:00:13.697Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Excise Number', PrintName='Excise Number',Updated=TO_TIMESTAMP('2022-07-25 16:00:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581178 AND AD_Language='en_US'
;

-- 2022-07-25T15:00:13.772Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581178,'en_US') 
;

-- 2022-07-25T15:01:04.877Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583806,581178,0,10,190,'ExciseNumber',TO_TIMESTAMP('2022-07-25 16:01:04','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,30,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Verbrauchsteuernummer',0,0,TO_TIMESTAMP('2022-07-25 16:01:04','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-07-25T15:01:04.957Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583806 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-07-25T15:01:05.129Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(581178) 
;

-- 2022-07-25T15:01:20.953Z
-- URL zum Konzept
/* DDL */ SELECT public.db_alter_table('M_Warehouse','ALTER TABLE public.M_Warehouse ADD COLUMN ExciseNumber VARCHAR(30)')
;

-- 2022-07-25T14:58:18.730Z
-- URL zum Konzept
UPDATE AD_Column SET IsIdentifier='N',Updated=TO_TIMESTAMP('2022-07-25 15:58:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=3005
;

-- 2022-07-25T14:58:41.017Z
-- URL zum Konzept
UPDATE AD_Column SET IsIdentifier='Y', SeqNo=2,Updated=TO_TIMESTAMP('2022-07-25 15:58:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=573197
;

-- 2022-07-25T13:12:51.178Z
-- URL zum Konzept
UPDATE AD_Column SET AD_Reference_ID=30, IsExcludeFromZoomTargets='Y',Updated=TO_TIMESTAMP('2022-07-25 14:12:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=546592
;