-- Run mode: SWING_CLIENT

-- Column: C_Workplace.PickFrom_Locator_ID
-- 2025-11-12T19:20:22.723Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,AD_Val_Rule_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,591510,580106,0,30,191,542375,127,'XX','PickFrom_Locator_ID',TO_TIMESTAMP('2025-11-12 19:20:22.326000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Pick From Locator',0,0,TO_TIMESTAMP('2025-11-12 19:20:22.326000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-11-12T19:20:22.749Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591510 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-11-12T19:20:22.935Z
/* DDL */  select update_Column_Translation_From_AD_Element(580106)
;

-- 2025-11-12T19:20:30.148Z
/* DDL */ SELECT public.db_alter_table('C_Workplace','ALTER TABLE public.C_Workplace ADD COLUMN PickFrom_Locator_ID NUMERIC(10)')
;

-- 2025-11-12T19:20:30.168Z
ALTER TABLE C_Workplace ADD CONSTRAINT PickFromLocator_CWorkplace FOREIGN KEY (PickFrom_Locator_ID) REFERENCES public.M_Locator DEFERRABLE INITIALLY DEFERRED
;

-- Field: Arbeitsplatz(541744,D) -> Arbeitsplatz(547260,D) -> Pick From Locator
-- Column: C_Workplace.PickFrom_Locator_ID
-- 2025-11-12T19:20:49.316Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591510,756060,0,547260,TO_TIMESTAMP('2025-11-12 19:20:49.020000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'D','Y','N','N','N','N','N','N','N','Pick From Locator',TO_TIMESTAMP('2025-11-12 19:20:49.020000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-11-12T19:20:49.321Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=756060 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-11-12T19:20:49.327Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(580106)
;

-- 2025-11-12T19:20:49.348Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=756060
;

-- 2025-11-12T19:20:49.356Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(756060)
;

-- UI Element: Arbeitsplatz(541744,D) -> Arbeitsplatz(547260,D) -> main -> 10 -> main.Pick From Locator
-- Column: C_Workplace.PickFrom_Locator_ID
-- 2025-11-12T19:21:24.588Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,756060,0,547260,551256,638631,'F',TO_TIMESTAMP('2025-11-12 19:21:24.307000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','N','N','Pick From Locator',40,0,0,TO_TIMESTAMP('2025-11-12 19:21:24.307000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

