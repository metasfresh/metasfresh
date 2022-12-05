-- Column: M_ShipmentSchedule.M_SectionCode_ID
-- 2022-09-20T18:53:23.871Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584387,581238,0,19,500221,'M_SectionCode_ID',TO_TIMESTAMP('2022-09-20 19:53:23','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.inoutcandidate',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Section Code',0,0,TO_TIMESTAMP('2022-09-20 19:53:23','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-09-20T18:53:23.873Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584387 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-09-20T18:53:23.892Z
/* DDL */  select update_Column_Translation_From_AD_Element(581238) 
;

-- 2022-09-20T18:53:26.734Z
/* DDL */ SELECT public.db_alter_table('M_ShipmentSchedule','ALTER TABLE public.M_ShipmentSchedule ADD COLUMN M_SectionCode_ID NUMERIC(10)')
;

-- 2022-09-20T18:53:26.899Z
ALTER TABLE M_ShipmentSchedule ADD CONSTRAINT MSectionCode_MShipmentSchedule FOREIGN KEY (M_SectionCode_ID) REFERENCES public.M_SectionCode DEFERRABLE INITIALLY DEFERRED
;

-- Field: Lieferdisposition(500221,de.metas.inoutcandidate) -> Auslieferplan(500221,de.metas.inoutcandidate) -> Section Code
-- Column: M_ShipmentSchedule.M_SectionCode_ID
-- 2022-09-20T18:55:31.374Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,584387,707319,0,500221,0,TO_TIMESTAMP('2022-09-20 19:55:31','YYYY-MM-DD HH24:MI:SS'),100,0,'de.metas.inoutcandidate',0,'Y','Y','Y','N','N','N','N','N','Section Code',0,730,0,1,1,TO_TIMESTAMP('2022-09-20 19:55:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-09-20T18:55:31.376Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=707319 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-09-20T18:55:31.377Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581238) 
;

-- 2022-09-20T18:55:31.387Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=707319
;

-- 2022-09-20T18:55:31.417Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(707319)
;

-- UI Element: Lieferdisposition(500221,de.metas.inoutcandidate) -> Auslieferplan(500221,de.metas.inoutcandidate) -> main -> 20 -> org.Section Code
-- Column: M_ShipmentSchedule.M_SectionCode_ID
-- 2022-09-20T18:56:04.471Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,707319,0,500221,540973,613015,'F',TO_TIMESTAMP('2022-09-20 19:56:04','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Section Code',5,0,0,TO_TIMESTAMP('2022-09-20 19:56:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Field: Lieferdisposition(500221,de.metas.inoutcandidate) -> Auslieferplan(500221,de.metas.inoutcandidate) -> Section Code
-- Column: M_ShipmentSchedule.M_SectionCode_ID
-- 2022-09-20T19:11:16.793Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2022-09-20 20:11:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=707319
;

