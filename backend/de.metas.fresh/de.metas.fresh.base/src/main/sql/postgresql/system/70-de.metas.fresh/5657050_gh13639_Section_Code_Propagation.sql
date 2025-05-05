-- Column: M_ReceiptSchedule.M_SectionCode_ID
-- Column: M_ReceiptSchedule.M_SectionCode_ID
-- 2022-09-21T19:01:46.898Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584430,581238,0,19,540524,'M_SectionCode_ID',TO_TIMESTAMP('2022-09-21 20:01:46.299','YYYY-MM-DD HH24:MI:SS.US'),100,'N','de.metas.inoutcandidate',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Section Code',0,0,TO_TIMESTAMP('2022-09-21 20:01:46.299','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2022-09-21T19:01:46.901Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584430 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-09-21T19:01:46.922Z
/* DDL */  select update_Column_Translation_From_AD_Element(581238) 
;

-- 2022-09-21T19:01:49.501Z
/* DDL */ SELECT public.db_alter_table('M_ReceiptSchedule','ALTER TABLE public.M_ReceiptSchedule ADD COLUMN M_SectionCode_ID NUMERIC(10)')
;

-- 2022-09-21T19:01:49.632Z
ALTER TABLE M_ReceiptSchedule ADD CONSTRAINT MSectionCode_MReceiptSchedule FOREIGN KEY (M_SectionCode_ID) REFERENCES public.M_SectionCode DEFERRABLE INITIALLY DEFERRED
;

-- 2022-09-21T19:02:19.528Z
INSERT INTO t_alter_column values('m_receiptschedule','M_SectionCode_ID','NUMERIC(10)',null,null)
;

-- Field: Wareneingangsdisposition -> Wareneingangsdisposition -> Section Code
-- Column: M_ReceiptSchedule.M_SectionCode_ID
-- Field: Wareneingangsdisposition(540196,de.metas.inoutcandidate) -> Wareneingangsdisposition(540526,de.metas.inoutcandidate) -> Section Code
-- Column: M_ReceiptSchedule.M_SectionCode_ID
-- 2022-09-21T19:05:51.970Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,584430,707324,0,540526,0,TO_TIMESTAMP('2022-09-21 20:05:51.685','YYYY-MM-DD HH24:MI:SS.US'),100,0,'de.metas.inoutcandidate',0,'Y','Y','Y','N','N','N','N','N','Section Code',0,220,0,1,1,TO_TIMESTAMP('2022-09-21 20:05:51.685','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2022-09-21T19:05:51.972Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=707324 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-09-21T19:05:51.974Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581238) 
;

-- 2022-09-21T19:05:51.978Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=707324
;

-- 2022-09-21T19:05:51.979Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(707324)
;

-- UI Element: Wareneingangsdisposition -> Wareneingangsdisposition.Section Code
-- Column: M_ReceiptSchedule.M_SectionCode_ID
-- UI Element: Wareneingangsdisposition(540196,de.metas.inoutcandidate) -> Wareneingangsdisposition(540526,de.metas.inoutcandidate) -> main -> 20 -> org.Section Code
-- Column: M_ReceiptSchedule.M_SectionCode_ID
-- 2022-09-21T19:07:52.199Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,707324,0,540526,540134,613022,'F',TO_TIMESTAMP('2022-09-21 20:07:51.933','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Section Code',5,0,0,TO_TIMESTAMP('2022-09-21 20:07:51.933','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- Field: Wareneingangsdisposition -> Wareneingangsdisposition -> Section Code
-- Column: M_ReceiptSchedule.M_SectionCode_ID
-- Field: Wareneingangsdisposition(540196,de.metas.inoutcandidate) -> Wareneingangsdisposition(540526,de.metas.inoutcandidate) -> Section Code
-- Column: M_ReceiptSchedule.M_SectionCode_ID
-- 2022-09-21T19:08:52.417Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2022-09-21 20:08:52.417','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=707324
;

