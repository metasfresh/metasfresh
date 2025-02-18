-- Run mode: SWING_CLIENT

-- Column: AD_BusinessRule.AD_Message_ID
-- 2025-02-13T09:06:09.145Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589667,1752,0,19,542456,'XX','AD_Message_ID',TO_TIMESTAMP('2025-02-13 09:06:08.847000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','System Message','D',0,10,'Information and Error messages','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Meldung',0,0,TO_TIMESTAMP('2025-02-13 09:06:08.847000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-02-13T09:06:09.149Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589667 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-02-13T09:06:09.176Z
/* DDL */  select update_Column_Translation_From_AD_Element(1752)
;

-- Column: AD_BusinessRule.AD_Message_ID
-- 2025-02-13T09:06:23.531Z
UPDATE AD_Column SET IsMandatory='N',Updated=TO_TIMESTAMP('2025-02-13 09:06:23.531000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=589667
;

-- 2025-02-13T09:06:50.285Z
/* DDL */ SELECT public.db_alter_table('AD_BusinessRule','ALTER TABLE public.AD_BusinessRule ADD COLUMN AD_Message_ID NUMERIC(10)')
;

-- 2025-02-13T09:06:50.297Z
ALTER TABLE AD_BusinessRule ADD CONSTRAINT ADMessage_ADBusinessRule FOREIGN KEY (AD_Message_ID) REFERENCES public.AD_Message DEFERRABLE INITIALLY DEFERRED
;

-- Column: AD_BusinessRule.AD_Message_ID
-- 2025-02-13T09:07:09.378Z
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2025-02-13 09:07:09.378000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=589667
;

-- 2025-02-13T09:07:27.315Z
INSERT INTO t_alter_column values('ad_businessrule','AD_Message_ID','NUMERIC(10)',null,null)
;

--TODO add migration

-- 2025-02-13T09:07:27.319Z
INSERT INTO t_alter_column values('ad_businessrule','AD_Message_ID',null,'NOT NULL',null)
;

-- UI Element: Business Rule(541837,D) -> Business Rule(547699,D) -> main -> 10 -> warning.Warning Message
-- Column: AD_BusinessRule.WarningMessage
-- 2025-02-13T09:12:28.874Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=627406
;

-- 2025-02-13T09:12:28.883Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=734101
;

-- Field: Business Rule(541837,D) -> Business Rule(547699,D) -> Warning Message
-- Column: AD_BusinessRule.WarningMessage
-- 2025-02-13T09:12:28.890Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=734101
;

-- 2025-02-13T09:12:28.895Z
DELETE FROM AD_Field WHERE AD_Field_ID=734101
;

-- 2025-02-13T09:12:28.897Z
/* DDL */ SELECT public.db_alter_table('AD_BusinessRule','ALTER TABLE AD_BusinessRule DROP COLUMN IF EXISTS WarningMessage')
;

-- Column: AD_BusinessRule.WarningMessage
-- 2025-02-13T09:12:28.918Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=589504
;

-- 2025-02-13T09:12:28.923Z
DELETE FROM AD_Column WHERE AD_Column_ID=589504
;

-- Field: Business Rule(541837,D) -> Business Rule(547699,D) -> Warning Message
-- Column: AD_BusinessRule.AD_Message_ID
-- 2025-02-13T09:35:18.404Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,FacetFilterSeqNo,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,589667,738005,583399,0,547699,0,TO_TIMESTAMP('2025-02-13 09:35:18.192000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0,'D',0,0,'Y','Y','Y','N','N','N','N','N','N','N',0,'Warning Message',0,0,10,0,1,1,TO_TIMESTAMP('2025-02-13 09:35:18.192000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-02-13T09:35:18.407Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=738005 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-02-13T09:35:18.436Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583399)
;

-- 2025-02-13T09:35:18.449Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=738005
;

-- 2025-02-13T09:35:18.451Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(738005)
;

-- UI Element: Business Rule(541837,D) -> Business Rule(547699,D) -> main -> 10 -> warning.Warning Message
-- Column: AD_BusinessRule.AD_Message_ID
-- 2025-02-13T09:42:34.985Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,738005,0,547699,552194,629538,'F',TO_TIMESTAMP('2025-02-13 09:42:34.806000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Warning Message',10,0,0,TO_TIMESTAMP('2025-02-13 09:42:34.806000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Column: AD_BusinessRule.AD_Message_ID
-- 2025-02-14T08:24:39.277Z
UPDATE AD_Column SET AD_Reference_ID=30, AD_Reference_Value_ID=102, IsExcludeFromZoomTargets='Y',Updated=TO_TIMESTAMP('2025-02-14 08:24:39.277000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=589667
;
