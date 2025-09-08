-- 2025-01-27T12:43:14.163Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583439,0,'Transport_Temperature',TO_TIMESTAMP('2025-01-27 12:43:13.989000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Transport Temperature','Transport Temperature',TO_TIMESTAMP('2025-01-27 12:43:13.989000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-01-27T12:43:14.172Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583439 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: Transport_Temperature
-- 2025-01-27T12:43:20.388Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Transporttemperatur', PrintName='Transporttemperatur',Updated=TO_TIMESTAMP('2025-01-27 12:43:20.387000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583439 AND AD_Language='de_CH'
;

-- 2025-01-27T12:43:20.418Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583439,'de_CH') 
;

-- Element: Transport_Temperature
-- 2025-01-27T12:43:24.328Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Transporttemperatur', PrintName='Transporttemperatur',Updated=TO_TIMESTAMP('2025-01-27 12:43:24.328000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583439 AND AD_Language='de_DE'
;

-- 2025-01-27T12:43:24.330Z
UPDATE AD_Element SET Name='Transporttemperatur', PrintName='Transporttemperatur', Updated=TO_TIMESTAMP('2025-01-27 12:43:24.330000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC' WHERE AD_Element_ID=583439
;

-- 2025-01-27T12:43:24.783Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583439,'de_DE') 
;

-- 2025-01-27T12:43:24.785Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583439,'de_DE') 
;

-- Column: M_Product.Transport_Temperature
-- Column: M_Product.Transport_Temperature
-- 2025-01-27T12:44:15.371Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,
                       IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,
                       IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,
                       IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version)
VALUES (0,589614,583439,0,10,208,'Transport_Temperature',TO_TIMESTAMP('2025-01-27 12:44:15.213000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','D',0,250,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Transporttemperatur',0,0,TO_TIMESTAMP('2025-01-27 12:44:15.213000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-01-27T12:44:15.373Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589614 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-01-27T12:44:15.376Z
/* DDL */  select update_Column_Translation_From_AD_Element(583439) 
;

-- Column: M_Product.Transport_Temperature
-- Column: M_Product.Transport_Temperature
-- 2025-01-27T12:44:49.455Z
UPDATE AD_Column SET AD_Reference_ID=14, FieldLength=2000,Updated=TO_TIMESTAMP('2025-01-27 12:44:49.455000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=589614
;

-- 2025-01-27T12:44:50.377Z
/* DDL */ SELECT public.db_alter_table('M_Product','ALTER TABLE public.M_Product ADD COLUMN Transport_Temperature VARCHAR(2000)')
;

