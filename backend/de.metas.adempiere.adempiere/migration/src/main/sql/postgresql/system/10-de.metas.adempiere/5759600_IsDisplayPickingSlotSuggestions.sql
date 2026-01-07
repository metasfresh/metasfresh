-- Run mode: SWING_CLIENT

-- 2025-07-02T11:39:53.346Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583768,0,'IsDisplayPickingSlotSuggestions',TO_TIMESTAMP('2025-07-02 11:39:53.031000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Kommissionierfach-Vorschläge anzeigen','Kommissionierfach-Vorschläge anzeigen',TO_TIMESTAMP('2025-07-02 11:39:53.031000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-07-02T11:39:53.461Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583768 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: IsDisplayPickingSlotSuggestions
-- 2025-07-02T11:40:10.598Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-07-02 11:40:10.598000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583768 AND AD_Language='de_CH'
;

-- 2025-07-02T11:40:10.799Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583768,'de_CH')
;

-- 2025-07-02T11:40:10.804Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583768,'de_CH')
;

-- Element: IsDisplayPickingSlotSuggestions
-- 2025-07-02T11:40:12.327Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-07-02 11:40:12.327000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583768 AND AD_Language='de_DE'
;

-- 2025-07-02T11:40:12.330Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583768,'de_DE')
;

-- Element: IsDisplayPickingSlotSuggestions
-- 2025-07-02T11:40:17.655Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Show Picking Tray Suggestions', PrintName='Show Picking Tray Suggestions',Updated=TO_TIMESTAMP('2025-07-02 11:40:17.654000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583768 AND AD_Language='en_US'
;

-- 2025-07-02T11:40:17.656Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-07-02T11:40:17.940Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583768,'en_US')
;

-- Column: MobileUI_UserProfile_Picking_Job.IsDisplayPickingSlotSuggestions
-- 2025-07-02T11:41:24.458Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,590479,583768,0,17,319,542464,'XX','IsDisplayPickingSlotSuggestions',TO_TIMESTAMP('2025-07-02 11:41:24.271000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Kommissionierfach-Vorschläge anzeigen',0,0,TO_TIMESTAMP('2025-07-02 11:41:24.271000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-07-02T11:41:24.468Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=590479 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-07-02T11:41:24.475Z
/* DDL */  select update_Column_Translation_From_AD_Element(583768)
;

-- 2025-07-02T11:41:25.605Z
/* DDL */ SELECT public.db_alter_table('MobileUI_UserProfile_Picking_Job','ALTER TABLE public.MobileUI_UserProfile_Picking_Job ADD COLUMN IsDisplayPickingSlotSuggestions CHAR(1)')
;

-- Field: Mobile UI Kommissionieraufgabe Optionen(541862,D) -> Mobile UI Kommissionieraufgabe Optionen(547823,D) -> Kommissionierfach-Vorschläge anzeigen
-- Column: MobileUI_UserProfile_Picking_Job.IsDisplayPickingSlotSuggestions
-- 2025-07-02T11:41:52.272Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,590479,748162,0,547823,TO_TIMESTAMP('2025-07-02 11:41:51.018000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,1,'D','Y','N','N','N','N','N','N','N','Kommissionierfach-Vorschläge anzeigen',TO_TIMESTAMP('2025-07-02 11:41:51.018000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-07-02T11:41:52.293Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=748162 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-07-02T11:41:52.299Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583768)
;

-- 2025-07-02T11:41:52.316Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=748162
;

-- 2025-07-02T11:41:52.321Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(748162)
;

-- UI Element: Mobile UI Kommissionieraufgabe Optionen(541862,D) -> Mobile UI Kommissionieraufgabe Optionen(547823,D) -> main -> 20 -> flags.Kommissionierfach-Vorschläge anzeigen
-- Column: MobileUI_UserProfile_Picking_Job.IsDisplayPickingSlotSuggestions
-- 2025-07-02T11:42:27.419Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,748162,0,547823,552482,634370,'F',TO_TIMESTAMP('2025-07-02 11:42:27.173000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','N','N','Kommissionierfach-Vorschläge anzeigen',140,0,0,TO_TIMESTAMP('2025-07-02 11:42:27.173000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Column: MobileUI_UserProfile_Picking.IsDisplayPickingSlotSuggestions
-- 2025-07-02T11:43:44.293Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,590480,583768,0,20,542373,'XX','IsDisplayPickingSlotSuggestions',TO_TIMESTAMP('2025-07-02 11:43:44.111000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','N','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Kommissionierfach-Vorschläge anzeigen',0,0,TO_TIMESTAMP('2025-07-02 11:43:44.111000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-07-02T11:43:44.300Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=590480 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-07-02T11:43:44.303Z
/* DDL */  select update_Column_Translation_From_AD_Element(583768)
;

-- 2025-07-02T11:43:45.048Z
/* DDL */ SELECT public.db_alter_table('MobileUI_UserProfile_Picking','ALTER TABLE public.MobileUI_UserProfile_Picking ADD COLUMN IsDisplayPickingSlotSuggestions CHAR(1) DEFAULT ''N'' CHECK (IsDisplayPickingSlotSuggestions IN (''Y'',''N'')) NOT NULL')
;

-- Field: Mobile UI Kommissionierprofil(541743,D) -> Mobile UI Kommissionierprofil(547258,D) -> Kommissionierfach-Vorschläge anzeigen
-- Column: MobileUI_UserProfile_Picking.IsDisplayPickingSlotSuggestions
-- 2025-07-02T11:43:59.977Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,590480,748163,0,547258,TO_TIMESTAMP('2025-07-02 11:43:59.808000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,1,'D','Y','N','N','N','N','N','N','N','Kommissionierfach-Vorschläge anzeigen',TO_TIMESTAMP('2025-07-02 11:43:59.808000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-07-02T11:43:59.988Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=748163 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-07-02T11:43:59.990Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583768)
;

-- 2025-07-02T11:43:59.993Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=748163
;

-- 2025-07-02T11:43:59.994Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(748163)
;

-- UI Element: Mobile UI Kommissionierprofil(541743,D) -> Mobile UI Kommissionierprofil(547258,D) -> main -> 20 -> flags.Kommissionierfach-Vorschläge anzeigen
-- Column: MobileUI_UserProfile_Picking.IsDisplayPickingSlotSuggestions
-- 2025-07-02T11:44:18.003Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,748163,0,547258,551252,634371,'F',TO_TIMESTAMP('2025-07-02 11:44:17.825000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','N','N','Kommissionierfach-Vorschläge anzeigen',150,0,0,TO_TIMESTAMP('2025-07-02 11:44:17.825000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

