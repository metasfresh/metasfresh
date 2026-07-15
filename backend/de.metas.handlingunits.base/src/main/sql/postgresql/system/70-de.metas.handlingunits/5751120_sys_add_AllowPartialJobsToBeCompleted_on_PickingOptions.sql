-- Run mode: SWING_CLIENT

-- Column: MobileUI_UserProfile_Picking_Job.IsAllowCompletingPartialPickingJob
-- 2025-04-04T17:23:14.578Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589876,583562,0,20,542464,'XX','IsAllowCompletingPartialPickingJob',TO_TIMESTAMP('2025-04-04 17:23:14.409000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','N','Nutzer können Aufträge fertigstellen, ohne die gesamte bestellte Menge zu kommissionieren.','D',0,1,'Nutzer können Aufträge fertigstellen, ohne die gesamte bestellte Menge zu kommissionieren.','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Teilweise Kommissionierung erlauben',0,0,TO_TIMESTAMP('2025-04-04 17:23:14.409000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-04-04T17:23:14.589Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589876 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-04-04T17:23:14.603Z
/* DDL */  select update_Column_Translation_From_AD_Element(583562)
;

-- 2025-04-04T17:23:27.500Z
/* DDL */ SELECT public.db_alter_table('MobileUI_UserProfile_Picking_Job','ALTER TABLE public.MobileUI_UserProfile_Picking_Job ADD COLUMN IsAllowCompletingPartialPickingJob CHAR(1) DEFAULT ''N'' CHECK (IsAllowCompletingPartialPickingJob IN (''Y'',''N'')) NOT NULL')
;

-- Field: Mobile UI Kommissionieraufgabe Optionen(541862,D) -> Mobile UI Kommissionieraufgabe Optionen(547823,D) -> Teilweise Kommissionierung erlauben
-- Column: MobileUI_UserProfile_Picking_Job.IsAllowCompletingPartialPickingJob
-- 2025-04-04T17:24:49.188Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589876,741948,0,547823,TO_TIMESTAMP('2025-04-04 17:24:49.027000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Nutzer können Aufträge fertigstellen, ohne die gesamte bestellte Menge zu kommissionieren.',1,'D','Nutzer können Aufträge fertigstellen, ohne die gesamte bestellte Menge zu kommissionieren.','Y','N','N','N','N','N','N','N','Teilweise Kommissionierung erlauben',TO_TIMESTAMP('2025-04-04 17:24:49.027000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-04-04T17:24:49.192Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=741948 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-04-04T17:24:49.201Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583562)
;

-- 2025-04-04T17:24:49.209Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=741948
;

-- 2025-04-04T17:24:49.220Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(741948)
;

-- UI Element: Mobile UI Kommissionieraufgabe Optionen(541862,D) -> Mobile UI Kommissionieraufgabe Optionen(547823,D) -> main -> 20 -> flags.Teilweise Kommissionierung erlauben
-- Column: MobileUI_UserProfile_Picking_Job.IsAllowCompletingPartialPickingJob
-- 2025-04-04T17:25:10.357Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,741948,0,547823,631324,552482,'F',TO_TIMESTAMP('2025-04-04 17:25:10.215000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Nutzer können Aufträge fertigstellen, ohne die gesamte bestellte Menge zu kommissionieren.','Nutzer können Aufträge fertigstellen, ohne die gesamte bestellte Menge zu kommissionieren.','Y','N','N','Y','N','N','N',0,'Teilweise Kommissionierung erlauben',100,0,0,TO_TIMESTAMP('2025-04-04 17:25:10.215000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

update MobileUI_UserProfile_Picking_Job set IsAllowCompletingPartialPickingJob = 'Y' where 1=1
;