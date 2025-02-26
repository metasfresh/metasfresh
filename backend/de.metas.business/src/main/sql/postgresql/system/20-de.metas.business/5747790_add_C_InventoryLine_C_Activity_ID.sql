-- Run mode: SWING_CLIENT

-- Column: M_InventoryLine.C_Activity_ID
-- 2025-02-25T18:32:27.729Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589740,1005,0,30,540608,322,'C_Activity_ID',TO_TIMESTAMP('2025-02-25 20:32:27.552','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Kostenstelle','D',0,10,'Erfassung der zugehörigen Kostenstelle','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Kostenstelle',0,0,TO_TIMESTAMP('2025-02-25 20:32:27.552','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2025-02-25T18:32:27.736Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589740 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-02-25T18:32:27.740Z
/* DDL */  select update_Column_Translation_From_AD_Element(1005)
;

-- 2025-02-25T18:32:30.961Z
/* DDL */ SELECT public.db_alter_table('M_InventoryLine','ALTER TABLE public.M_InventoryLine ADD COLUMN C_Activity_ID NUMERIC(10)')
;

-- 2025-02-25T18:32:31.074Z
ALTER TABLE M_InventoryLine ADD CONSTRAINT CActivity_MInventoryLine FOREIGN KEY (C_Activity_ID) REFERENCES public.C_Activity DEFERRABLE INITIALLY DEFERRED
;

-- Field: Inventur(168,D) -> Bestandszählungs Position(256,D) -> Kostenstelle
-- Column: M_InventoryLine.C_Activity_ID
-- 2025-02-25T18:35:39.282Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsForbidNewRecordCreation,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,589740,740344,0,256,0,TO_TIMESTAMP('2025-02-25 20:35:39.086','YYYY-MM-DD HH24:MI:SS.US'),100,'Kostenstelle',0,'D','Erfassung der zugehörigen Kostenstelle',0,'Y','Y','Y','N','N','N','N','N','N','Kostenstelle',0,200,0,1,1,TO_TIMESTAMP('2025-02-25 20:35:39.086','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2025-02-25T18:35:39.286Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=740344 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-02-25T18:35:39.289Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1005)
;

-- 2025-02-25T18:35:39.387Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=740344
;

-- 2025-02-25T18:35:39.390Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(740344)
;

-- UI Element: Inventur(168,D) -> Bestandszählungs Position(256,D) -> main -> 10 -> default.Kostenstelle
-- Column: M_InventoryLine.C_Activity_ID
-- 2025-02-25T18:37:03.315Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,740344,0,256,541513,630653,'F',TO_TIMESTAMP('2025-02-25 20:37:03.14','YYYY-MM-DD HH24:MI:SS.US'),100,'Kostenstelle','Erfassung der zugehörigen Kostenstelle','Y','N','N','Y','N','N','N',0,'Kostenstelle',180,0,0,TO_TIMESTAMP('2025-02-25 20:37:03.14','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Inventur(168,D) -> Bestandszählungs Position(256,D) -> main -> 10 -> default.Kostenstelle
-- Column: M_InventoryLine.C_Activity_ID
-- 2025-02-25T19:06:44.700Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2025-02-25 21:06:44.7','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=630653
;

-- UI Element: Inventur(168,D) -> Bestandszählungs Position(256,D) -> main -> 10 -> default.Sektion
-- Column: M_InventoryLine.AD_Org_ID
-- 2025-02-25T19:06:44.715Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2025-02-25 21:06:44.715','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=551246
;

