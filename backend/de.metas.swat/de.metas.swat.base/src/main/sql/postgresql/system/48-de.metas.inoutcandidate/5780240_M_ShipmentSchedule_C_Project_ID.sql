-- Run mode: SWING_CLIENT

-- Column: M_ShipmentSchedule.C_Project_ID
-- 2025-12-09T19:33:07.013Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,591698,208,0,19,500221,'XX','C_Project_ID',TO_TIMESTAMP('2025-12-09 19:33:06.678000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Financial Project','de.metas.inoutcandidate',0,10,'A Project allows you to track and control internal or external activities.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Projekt',0,0,TO_TIMESTAMP('2025-12-09 19:33:06.678000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-12-09T19:33:07.019Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591698 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-12-09T19:33:07.047Z
/* DDL */  select update_Column_Translation_From_AD_Element(208)
;

-- 2025-12-09T19:33:09.739Z
/* DDL */ SELECT public.db_alter_table('M_ShipmentSchedule','ALTER TABLE public.M_ShipmentSchedule ADD COLUMN C_Project_ID NUMERIC(10)')
;

-- 2025-12-09T19:33:10.028Z
ALTER TABLE M_ShipmentSchedule ADD CONSTRAINT CProject_MShipmentSchedule FOREIGN KEY (C_Project_ID) REFERENCES public.C_Project DEFERRABLE INITIALLY DEFERRED
;

-- Field: Lieferdisposition(500221,de.metas.inoutcandidate) -> Auslieferplan(500221,de.metas.inoutcandidate) -> Projekt
-- Column: M_ShipmentSchedule.C_Project_ID
-- 2025-12-10T08:29:25.996Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,FacetFilterSeqNo,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,591698,760258,0,500221,0,TO_TIMESTAMP('2025-12-10 08:29:25.623000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Financial Project',0,'@$Element_PJ/''X''@=''Y''','D',0,'A Project allows you to track and control internal or external activities.',0,'Y','Y','Y','N','N','N','N','N','N','N',0,'Projekt',0,0,770,0,1,1,TO_TIMESTAMP('2025-12-10 08:29:25.623000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-12-10T08:29:26.003Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=760258 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-12-10T08:29:26.031Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(208)
;

-- 2025-12-10T08:29:26.070Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=760258
;

-- 2025-12-10T08:29:26.073Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(760258)
;

-- UI Element: Lieferdisposition(500221,de.metas.inoutcandidate) -> Auslieferplan(500221,de.metas.inoutcandidate) -> advanced edit -> 10 -> advanced edit.Projekt
-- Column: M_ShipmentSchedule.C_Project_ID
-- 2025-12-10T08:29:58.678Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,760258,0,500221,540052,640833,'F',TO_TIMESTAMP('2025-12-10 08:29:58.439000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Financial Project','A Project allows you to track and control internal or external activities.','Y','N','N','Y','N','N','N',0,'Projekt',400,0,0,TO_TIMESTAMP('2025-12-10 08:29:58.439000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Lieferdisposition(500221,de.metas.inoutcandidate) -> Auslieferplan(500221,de.metas.inoutcandidate) -> advanced edit -> 10 -> advanced edit.Projekt
-- Column: M_ShipmentSchedule.C_Project_ID
-- 2025-12-10T08:30:08.608Z
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2025-12-10 08:30:08.607000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=640833
;

