-- 2025-06-02T15:08:36.618Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583679,0,'IsCreateWarningOnTarget',TO_TIMESTAMP('2025-06-02 15:08:36.331000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Create Warning On Target','Create Warning On Target',TO_TIMESTAMP('2025-06-02 15:08:36.331000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-06-02T15:08:36.623Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583679 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: IsCreateWarningOnTarget
-- 2025-06-02T15:09:29.191Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Warnung auf Ziel erstellen', PrintName='Warnung auf Ziel erstellen',Updated=TO_TIMESTAMP('2025-06-02 15:09:29.191000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583679 AND AD_Language='de_CH'
;

-- 2025-06-02T15:09:29.197Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-06-02T15:09:31.418Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583679,'de_CH')
;

-- Element: IsCreateWarningOnTarget
-- 2025-06-02T15:09:39.171Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Warnung auf Ziel erstellen', PrintName='Warnung auf Ziel erstellen',Updated=TO_TIMESTAMP('2025-06-02 15:09:39.171000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583679 AND AD_Language='de_DE'
;

-- 2025-06-02T15:09:39.172Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-06-02T15:09:39.880Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583679,'de_DE')
;

-- 2025-06-02T15:09:39.883Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583679,'de_DE')
;

-- Column: AD_BusinessRule.IsCreateWarningOnTarget
-- 2025-06-02T15:10:26.864Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,590137,583679,0,20,542456,'XX','IsCreateWarningOnTarget',TO_TIMESTAMP('2025-06-02 15:10:26.677000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Y','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Warnung auf Ziel erstellen',0,0,TO_TIMESTAMP('2025-06-02 15:10:26.677000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-06-02T15:10:26.873Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=590137 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-06-02T15:10:26.889Z
/* DDL */  select update_Column_Translation_From_AD_Element(583679)
;

-- 2025-06-02T15:10:32.354Z
/* DDL */ SELECT public.db_alter_table('AD_BusinessRule','ALTER TABLE public.AD_BusinessRule ADD COLUMN IsCreateWarningOnTarget CHAR(1) DEFAULT ''Y'' CHECK (IsCreateWarningOnTarget IN (''Y'',''N'')) NOT NULL')
;



-- Field: Business Rule(541837,D) -> Business Rule(547699,D) -> Warnung auf Ziel erstellen
-- Column: AD_BusinessRule.IsCreateWarningOnTarget
-- 2025-06-02T15:56:23.735Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,590137,747486,0,547699,TO_TIMESTAMP('2025-06-02 15:56:23.601000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,1,'D','Y','N','N','N','N','N','N','N','Warnung auf Ziel erstellen',TO_TIMESTAMP('2025-06-02 15:56:23.601000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-06-02T15:56:23.736Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=747486 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-06-02T15:56:23.738Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583679)
;

-- 2025-06-02T15:56:23.742Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=747486
;

-- 2025-06-02T15:56:23.743Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(747486)
;

-- UI Element: Business Rule(541837,D) -> Business Rule(547699,D) -> main -> 20 -> flags.Warnung auf Ziel erstellen
-- Column: AD_BusinessRule.IsCreateWarningOnTarget
-- 2025-06-02T15:56:41.287Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,747486,0,547699,552195,633895,'F',TO_TIMESTAMP('2025-06-02 15:56:41.082000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','N','N','Warnung auf Ziel erstellen',30,0,0,TO_TIMESTAMP('2025-06-02 15:56:41.082000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

