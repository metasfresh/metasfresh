-- Run mode: SWING_CLIENT

-- 2025-08-29T14:24:25.662Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583887,0,'IsConsideredOnlyScheduledJobs',TO_TIMESTAMP('2025-08-29 14:24:25.411000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.handlingunits','Y','Consider only scheduled jobs','Consider only scheduled jobs',TO_TIMESTAMP('2025-08-29 14:24:25.411000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-08-29T14:24:25.673Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583887 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2025-08-29T14:24:36.254Z
UPDATE AD_Element SET EntityType='D',Updated=TO_TIMESTAMP('2025-08-29 14:24:36.250000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583887
;

-- 2025-08-29T14:24:36.269Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583887,'de_DE')
;

-- Column: MobileUI_UserProfile_Picking.IsConsideredOnlyScheduledJobs
-- 2025-08-29T14:24:47.288Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,590705,583887,0,20,542373,'XX','IsConsideredOnlyScheduledJobs',TO_TIMESTAMP('2025-08-29 14:24:47.004000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','N','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Consider only scheduled jobs',0,0,TO_TIMESTAMP('2025-08-29 14:24:47.004000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-08-29T14:24:47.294Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=590705 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-08-29T14:24:47.303Z
/* DDL */  select update_Column_Translation_From_AD_Element(583887)
;

-- 2025-08-29T14:24:48.768Z
/* DDL */ SELECT public.db_alter_table('MobileUI_UserProfile_Picking','ALTER TABLE public.MobileUI_UserProfile_Picking ADD COLUMN IsConsideredOnlyScheduledJobs CHAR(1) DEFAULT ''N'' CHECK (IsConsideredOnlyScheduledJobs IN (''Y'',''N'')) NOT NULL')
;

-- Field: Mobile UI Kommissionierprofil(541743,D) -> Mobile UI Kommissionierprofil(547258,D) -> Consider only scheduled jobs
-- Column: MobileUI_UserProfile_Picking.IsConsideredOnlyScheduledJobs
-- 2025-08-29T14:25:10.518Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,590705,752675,0,547258,TO_TIMESTAMP('2025-08-29 14:25:09.092000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,1,'D','Y','N','N','N','N','N','N','N','Consider only scheduled jobs',TO_TIMESTAMP('2025-08-29 14:25:09.092000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-08-29T14:25:10.523Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=752675 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-08-29T14:25:10.529Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583887)
;

-- 2025-08-29T14:25:10.555Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=752675
;

-- 2025-08-29T14:25:10.563Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(752675)
;

-- UI Element: Mobile UI Kommissionierprofil(541743,D) -> Mobile UI Kommissionierprofil(547258,D) -> main -> 20 -> flags.Consider only scheduled jobs
-- Column: MobileUI_UserProfile_Picking.IsConsideredOnlyScheduledJobs
-- 2025-08-29T14:25:31.595Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,752675,0,547258,551252,636564,'F',TO_TIMESTAMP('2025-08-29 14:25:31.281000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','N','N','Consider only scheduled jobs',160,0,0,TO_TIMESTAMP('2025-08-29 14:25:31.281000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

