-- Run mode: SWING_CLIENT

-- 2025-09-01T13:24:34.006Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583889,0,'IsAllowIssuingAnyHU',TO_TIMESTAMP('2025-09-01 13:24:33.589000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Allow issuing any HU','Allow issuing any HU',TO_TIMESTAMP('2025-09-01 13:24:33.589000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-01T13:24:34.089Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583889 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: MobileUI_MFG_Config.IsAllowIssuingAnyHU
-- 2025-09-01T13:24:55.612Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,590707,583889,0,20,542397,'XX','IsAllowIssuingAnyHU',TO_TIMESTAMP('2025-09-01 13:24:55.408000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','N','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Allow issuing any HU',0,0,TO_TIMESTAMP('2025-09-01 13:24:55.408000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-09-01T13:24:55.622Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=590707 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-09-01T13:24:55.805Z
/* DDL */  select update_Column_Translation_From_AD_Element(583889)
;

-- 2025-09-01T13:24:56.897Z
/* DDL */ SELECT public.db_alter_table('MobileUI_MFG_Config','ALTER TABLE public.MobileUI_MFG_Config ADD COLUMN IsAllowIssuingAnyHU CHAR(1) DEFAULT ''N'' CHECK (IsAllowIssuingAnyHU IN (''Y'',''N'')) NOT NULL')
;

-- Field: MobileUI Manufacturing Configuration(541788,D) -> MobileUI Manufacturing Configuration(547483,D) -> Allow issuing any HU
-- Column: MobileUI_MFG_Config.IsAllowIssuingAnyHU
-- 2025-09-01T13:25:20.085Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,590707,752678,0,547483,TO_TIMESTAMP('2025-09-01 13:25:19.819000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,1,'D','Y','N','N','N','N','N','N','N','Allow issuing any HU',TO_TIMESTAMP('2025-09-01 13:25:19.819000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-01T13:25:20.117Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=752678 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-01T13:25:20.122Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583889)
;

-- 2025-09-01T13:25:20.141Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=752678
;

-- 2025-09-01T13:25:20.146Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(752678)
;

-- UI Element: MobileUI Manufacturing Configuration(541788,D) -> MobileUI Manufacturing Configuration(547483,D) -> main -> 20 -> flags.Allow issuing any HU
-- Column: MobileUI_MFG_Config.IsAllowIssuingAnyHU
-- 2025-09-01T13:25:50.409Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,752678,0,547483,551690,636567,'F',TO_TIMESTAMP('2025-09-01 13:25:50.155000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','N','N','Allow issuing any HU',20,0,0,TO_TIMESTAMP('2025-09-01 13:25:50.155000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: MobileUI Manufacturing Configuration(541788,D) -> MobileUI Manufacturing Configuration(547483,D) -> main -> 20 -> flags.Scan der Ressource erforderlich
-- Column: MobileUI_MFG_Config.IsScanResourceRequired
-- 2025-09-01T13:26:07.488Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2025-09-01 13:26:07.488000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=623746
;

-- UI Element: MobileUI Manufacturing Configuration(541788,D) -> MobileUI Manufacturing Configuration(547483,D) -> main -> 20 -> flags.Allow issuing any HU
-- Column: MobileUI_MFG_Config.IsAllowIssuingAnyHU
-- 2025-09-01T13:26:07.499Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2025-09-01 13:26:07.499000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=636567
;

-- Column: MobileUI_UserProfile_MFG.IsAllowIssuingAnyHU
-- 2025-09-02T05:39:55.547Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,590708,583889,0,17,319,542263,'XX','IsAllowIssuingAnyHU',TO_TIMESTAMP('2025-09-02 05:39:55.228000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Allow issuing any HU',0,0,TO_TIMESTAMP('2025-09-02 05:39:55.228000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-09-02T05:39:55.659Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=590708 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-09-02T05:39:55.674Z
/* DDL */  select update_Column_Translation_From_AD_Element(583889)
;

-- 2025-09-02T05:39:56.975Z
/* DDL */ SELECT public.db_alter_table('MobileUI_UserProfile_MFG','ALTER TABLE public.MobileUI_UserProfile_MFG ADD COLUMN IsAllowIssuingAnyHU CHAR(1)')
;

-- Field: Nutzer(108,D) -> Mobile UI Nutzerprofil - Produktion(546679,D) -> Allow issuing any HU
-- Column: MobileUI_UserProfile_MFG.IsAllowIssuingAnyHU
-- 2025-09-02T05:40:26.179Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,590708,752679,0,546679,TO_TIMESTAMP('2025-09-02 05:40:25.992000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,1,'D','Y','N','N','N','N','N','N','N','Allow issuing any HU',TO_TIMESTAMP('2025-09-02 05:40:25.992000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-02T05:40:26.202Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=752679 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-02T05:40:26.210Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583889)
;

-- 2025-09-02T05:40:26.216Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=752679
;

-- 2025-09-02T05:40:26.222Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(752679)
;

-- UI Element: Nutzer(108,D) -> Mobile UI Nutzerprofil - Produktion(546679,D) -> primary -> 20 -> primary.Allow issuing any HU
-- Column: MobileUI_UserProfile_MFG.IsAllowIssuingAnyHU
-- 2025-09-02T05:41:16.195Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,752679,0,546679,550042,636568,'F',TO_TIMESTAMP('2025-09-02 05:41:15.992000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','N','N','Allow issuing any HU',20,0,0,TO_TIMESTAMP('2025-09-02 05:41:15.992000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Nutzer(108,D) -> Mobile UI Nutzerprofil - Produktion(546679,D) -> primary -> 20 -> primary.Allow issuing any HU
-- Column: MobileUI_UserProfile_MFG.IsAllowIssuingAnyHU
-- 2025-09-02T05:41:25.451Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2025-09-02 05:41:25.451000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=636568
;

