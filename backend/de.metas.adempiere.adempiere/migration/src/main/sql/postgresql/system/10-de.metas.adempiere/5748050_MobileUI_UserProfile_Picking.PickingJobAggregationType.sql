-- Run mode: SWING_CLIENT

-- Column: MobileUI_UserProfile_Picking.PickingJobAggregationType
-- 2025-02-27T12:02:59.052Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589745,583513,0,17,541931,542373,'XX','PickingJobAggregationType',TO_TIMESTAMP('2025-02-27 12:02:58.877000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','sales_order','D',0,20,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Aggregation Type',0,0,TO_TIMESTAMP('2025-02-27 12:02:58.877000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-02-27T12:02:59.054Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589745 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-02-27T12:02:59.058Z
/* DDL */  select update_Column_Translation_From_AD_Element(583513)
;

-- 2025-02-27T12:02:59.765Z
/* DDL */ SELECT public.db_alter_table('MobileUI_UserProfile_Picking','ALTER TABLE public.MobileUI_UserProfile_Picking ADD COLUMN PickingJobAggregationType VARCHAR(20) DEFAULT ''sales_order'' NOT NULL')
;

-- Field: Mobile UI Kommissionierprofil(541743,D) -> Mobile UI Kommissionierprofil(547258,D) -> Aggregation Type
-- Column: MobileUI_UserProfile_Picking.PickingJobAggregationType
-- 2025-02-27T12:03:18.330Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589745,740349,0,547258,TO_TIMESTAMP('2025-02-27 12:03:18.177000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,20,'D','Y','N','N','N','N','N','N','N','Aggregation Type',TO_TIMESTAMP('2025-02-27 12:03:18.177000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-02-27T12:03:18.332Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=740349 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-02-27T12:03:18.333Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583513)
;

-- 2025-02-27T12:03:18.336Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=740349
;

-- 2025-02-27T12:03:18.337Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(740349)
;

-- UI Element: Mobile UI Kommissionierprofil(541743,D) -> Mobile UI Kommissionierprofil(547258,D) -> main -> 10 -> default.Aggregation Type
-- Column: MobileUI_UserProfile_Picking.PickingJobAggregationType
-- 2025-02-27T12:03:50.305Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,740349,0,547258,551251,630658,'F',TO_TIMESTAMP('2025-02-27 12:03:50.129000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','N','N','Aggregation Type',20,0,0,TO_TIMESTAMP('2025-02-27 12:03:50.129000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Mobile UI Kommissionierprofil(541743,D) -> Mobile UI Kommissionierprofil(547258,D) -> main -> 10 -> default.Aggregation Type
-- Column: MobileUI_UserProfile_Picking.PickingJobAggregationType
-- 2025-02-27T12:04:03.390Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2025-02-27 12:04:03.390000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=630658
;

-- UI Element: Mobile UI Kommissionierprofil(541743,D) -> Mobile UI Kommissionierprofil(547258,D) -> main -> 10 -> settings.Erstellen von Lieferung Richtlinie
-- Column: MobileUI_UserProfile_Picking.CreateShipmentPolicy
-- 2025-02-27T12:04:03.400Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2025-02-27 12:04:03.400000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=621123
;

-- UI Element: Mobile UI Kommissionierprofil(541743,D) -> Mobile UI Kommissionierprofil(547258,D) -> main -> 20 -> flags.Aktiv
-- Column: MobileUI_UserProfile_Picking.IsActive
-- 2025-02-27T12:04:03.407Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2025-02-27 12:04:03.407000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=621116
;

-- UI Element: Mobile UI Kommissionierprofil(541743,D) -> Mobile UI Kommissionierprofil(547258,D) -> main -> 20 -> org.Sektion
-- Column: MobileUI_UserProfile_Picking.AD_Org_ID
-- 2025-02-27T12:04:03.414Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2025-02-27 12:04:03.414000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=621117
;

