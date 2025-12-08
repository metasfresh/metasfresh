-- Column: DD_Order_Candidate.C_OrderLineSO_ID
-- Column: DD_Order_Candidate.C_OrderLineSO_ID
-- 2024-07-18T18:02:46.634Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588861,542435,0,30,271,542424,'XX','C_OrderLineSO_ID',TO_TIMESTAMP('2024-07-18 21:02:46','YYYY-MM-DD HH24:MI:SS'),100,'N','Auftragsposition','EE01',0,10,'"Auftragsposition" bezeichnet eine einzelne Position in einem Auftrag.','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Auftragsposition',0,0,TO_TIMESTAMP('2024-07-18 21:02:46','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-07-18T18:02:46.637Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588861 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-07-18T18:02:46.642Z
/* DDL */  select update_Column_Translation_From_AD_Element(542435) 
;

-- 2024-07-18T18:02:47.354Z
/* DDL */ SELECT public.db_alter_table('DD_Order_Candidate','ALTER TABLE public.DD_Order_Candidate ADD COLUMN C_OrderLineSO_ID NUMERIC(10)')
;

-- 2024-07-18T18:02:47.363Z
ALTER TABLE DD_Order_Candidate ADD CONSTRAINT COrderLineSO_DDOrderCandidate FOREIGN KEY (C_OrderLineSO_ID) REFERENCES public.C_OrderLine DEFERRABLE INITIALLY DEFERRED
;

-- Field: Distribution Order Candidate -> Distribution Order Candidate -> Auftragsposition
-- Column: DD_Order_Candidate.C_OrderLineSO_ID
-- Field: Distribution Order Candidate(541807,EE01) -> Distribution Order Candidate(547559,EE01) -> Auftragsposition
-- Column: DD_Order_Candidate.C_OrderLineSO_ID
-- 2024-07-18T18:03:10.243Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588861,729103,0,547559,TO_TIMESTAMP('2024-07-18 21:03:10','YYYY-MM-DD HH24:MI:SS'),100,'Auftragsposition',10,'EE01','"Auftragsposition" bezeichnet eine einzelne Position in einem Auftrag.','Y','N','N','N','N','N','N','N','Auftragsposition',TO_TIMESTAMP('2024-07-18 21:03:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-07-18T18:03:10.247Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=729103 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-07-18T18:03:10.252Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542435) 
;

-- 2024-07-18T18:03:10.268Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=729103
;

-- 2024-07-18T18:03:10.270Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(729103)
;

-- Field: Distribution Order Candidate -> Distribution Order Candidate -> Auftragsposition
-- Column: DD_Order_Candidate.C_OrderLineSO_ID
-- Field: Distribution Order Candidate(541807,EE01) -> Distribution Order Candidate(547559,EE01) -> Auftragsposition
-- Column: DD_Order_Candidate.C_OrderLineSO_ID
-- 2024-07-18T18:03:20.544Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2024-07-18 21:03:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=729103
;

-- UI Element: Distribution Order Candidate -> Distribution Order Candidate.Auftragsposition
-- Column: DD_Order_Candidate.C_OrderLineSO_ID
-- UI Element: Distribution Order Candidate(541807,EE01) -> Distribution Order Candidate(547559,EE01) -> main -> 20 -> forward document.Auftragsposition
-- Column: DD_Order_Candidate.C_OrderLineSO_ID
-- 2024-07-18T18:05:01.566Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,729103,0,547559,551865,625018,'F',TO_TIMESTAMP('2024-07-18 21:05:01','YYYY-MM-DD HH24:MI:SS'),100,'Auftragsposition','"Auftragsposition" bezeichnet eine einzelne Position in einem Auftrag.','Y','N','Y','N','N','Auftragsposition',30,0,0,TO_TIMESTAMP('2024-07-18 21:05:01','YYYY-MM-DD HH24:MI:SS'),100)
;

