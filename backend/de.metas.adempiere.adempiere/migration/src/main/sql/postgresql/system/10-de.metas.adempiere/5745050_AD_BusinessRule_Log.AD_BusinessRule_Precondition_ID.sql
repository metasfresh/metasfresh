-- Column: AD_BusinessRule_Precondition.PreconditionType
-- Column: AD_BusinessRule_Precondition.PreconditionType
-- 2025-01-28T11:06:30.741Z
UPDATE AD_Column SET IsIdentifier='Y', SeqNo=10,Updated=TO_TIMESTAMP('2025-01-28 11:06:30.741000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=589515
;

-- Column: AD_BusinessRule_Precondition.PreconditionSQL
-- Column: AD_BusinessRule_Precondition.PreconditionSQL
-- 2025-01-28T11:06:40.559Z
UPDATE AD_Column SET IsIdentifier='Y', SeqNo=20,Updated=TO_TIMESTAMP('2025-01-28 11:06:40.559000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=589516
;

-- Column: AD_BusinessRule_Log.AD_BusinessRule_Precondition_ID
-- Column: AD_BusinessRule_Log.AD_BusinessRule_Precondition_ID
-- 2025-01-28T11:06:46.955Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589624,583400,0,30,542460,'XX','AD_BusinessRule_Precondition_ID',TO_TIMESTAMP('2025-01-28 11:06:46.656000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Business Rule Precondition',0,0,TO_TIMESTAMP('2025-01-28 11:06:46.656000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-01-28T11:06:46.961Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589624 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-01-28T11:06:47.146Z
/* DDL */  select update_Column_Translation_From_AD_Element(583400) 
;

-- 2025-01-28T11:06:51.056Z
/* DDL */ SELECT public.db_alter_table('AD_BusinessRule_Log','ALTER TABLE public.AD_BusinessRule_Log ADD COLUMN AD_BusinessRule_Precondition_ID NUMERIC(10)')
;

-- 2025-01-28T11:06:51.072Z
ALTER TABLE AD_BusinessRule_Log ADD CONSTRAINT ADBusinessRulePrecondition_ADBusinessRuleLog FOREIGN KEY (AD_BusinessRule_Precondition_ID) REFERENCES public.AD_BusinessRule_Precondition DEFERRABLE INITIALLY DEFERRED
;

-- UI Element: Business Rule Log -> Business Rule Log.Business Rule Log
-- Column: AD_BusinessRule_Log.AD_BusinessRule_Log_ID
-- UI Element: Business Rule Log(541840,D) -> Business Rule Log(547733,D) -> main -> 20 -> context.Business Rule Log
-- Column: AD_BusinessRule_Log.AD_BusinessRule_Log_ID
-- 2025-01-28T11:10:13.373Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,734629,0,547733,552261,628426,'F',TO_TIMESTAMP('2025-01-28 11:10:13.076000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','N','N','Business Rule Log',80,0,0,TO_TIMESTAMP('2025-01-28 11:10:13.076000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Business Rule Log -> Business Rule Log.Business Rule Log
-- Column: AD_BusinessRule_Log.AD_BusinessRule_Log_ID
-- UI Element: Business Rule Log(541840,D) -> Business Rule Log(547733,D) -> main -> 20 -> context.Business Rule Log
-- Column: AD_BusinessRule_Log.AD_BusinessRule_Log_ID
-- 2025-01-28T11:10:23.472Z
UPDATE AD_UI_Element SET SeqNo=15,Updated=TO_TIMESTAMP('2025-01-28 11:10:23.472000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=628426
;

-- Column: AD_BusinessRule_Log.AD_BusinessRule_Precondition_ID
-- Column: AD_BusinessRule_Log.AD_BusinessRule_Precondition_ID
-- 2025-01-28T11:10:45.623Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2025-01-28 11:10:45.623000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=589624
;

-- UI Element: Business Rule Log -> Business Rule Log.Business Rule Log
-- Column: AD_BusinessRule_Log.AD_BusinessRule_Log_ID
-- UI Element: Business Rule Log(541840,D) -> Business Rule Log(547733,D) -> main -> 20 -> context.Business Rule Log
-- Column: AD_BusinessRule_Log.AD_BusinessRule_Log_ID
-- 2025-01-28T11:26:07.366Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=628426
;

-- Field: Business Rule Log -> Business Rule Log -> Business Rule Precondition
-- Column: AD_BusinessRule_Log.AD_BusinessRule_Precondition_ID
-- Field: Business Rule Log(541840,D) -> Business Rule Log(547733,D) -> Business Rule Precondition
-- Column: AD_BusinessRule_Log.AD_BusinessRule_Precondition_ID
-- 2025-01-28T11:26:17.756Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589624,735593,0,547733,TO_TIMESTAMP('2025-01-28 11:26:17.575000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'D','Y','N','N','N','N','N','N','N','Business Rule Precondition',TO_TIMESTAMP('2025-01-28 11:26:17.575000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-01-28T11:26:17.761Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=735593 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-01-28T11:26:17.765Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583400) 
;

-- 2025-01-28T11:26:17.786Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=735593
;

-- 2025-01-28T11:26:17.792Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(735593)
;

-- UI Element: Business Rule Log -> Business Rule Log.Business Rule Precondition
-- Column: AD_BusinessRule_Log.AD_BusinessRule_Precondition_ID
-- UI Element: Business Rule Log(541840,D) -> Business Rule Log(547733,D) -> main -> 20 -> context.Business Rule Precondition
-- Column: AD_BusinessRule_Log.AD_BusinessRule_Precondition_ID
-- 2025-01-28T11:26:36.229Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,735593,0,547733,552261,628427,'F',TO_TIMESTAMP('2025-01-28 11:26:35.996000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','N','N','Business Rule Precondition',80,0,0,TO_TIMESTAMP('2025-01-28 11:26:35.996000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Business Rule Log -> Business Rule Log.Business Rule Precondition
-- Column: AD_BusinessRule_Log.AD_BusinessRule_Precondition_ID
-- UI Element: Business Rule Log(541840,D) -> Business Rule Log(547733,D) -> main -> 20 -> context.Business Rule Precondition
-- Column: AD_BusinessRule_Log.AD_BusinessRule_Precondition_ID
-- 2025-01-28T11:26:46.108Z
UPDATE AD_UI_Element SET SeqNo=15,Updated=TO_TIMESTAMP('2025-01-28 11:26:46.107000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=628427
;

-- Tab: Business Rule Log -> Business Rule Log
-- Table: AD_BusinessRule_Log
-- Tab: Business Rule Log(541840,D) -> Business Rule Log
-- Table: AD_BusinessRule_Log
-- 2025-01-28T11:27:15.987Z
UPDATE AD_Tab SET IsInsertRecord='N', IsReadOnly='Y',Updated=TO_TIMESTAMP('2025-01-28 11:27:15.986000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Tab_ID=547733
;

