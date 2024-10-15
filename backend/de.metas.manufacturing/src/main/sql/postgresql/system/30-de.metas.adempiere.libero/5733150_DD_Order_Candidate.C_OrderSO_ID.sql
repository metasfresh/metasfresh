-- Column: DD_Order_Candidate.C_OrderSO_ID
-- Column: DD_Order_Candidate.C_OrderSO_ID
-- 2024-09-12T07:21:22.509Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588975,543479,0,30,290,542424,'XX','C_OrderSO_ID',TO_TIMESTAMP('2024-09-12 10:21:22','YYYY-MM-DD HH24:MI:SS'),100,'N','Auftrag','EE01',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Auftrag',0,0,TO_TIMESTAMP('2024-09-12 10:21:22','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-09-12T07:21:22.512Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588975 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-09-12T07:21:22.542Z
/* DDL */  select update_Column_Translation_From_AD_Element(543479) 
;

-- 2024-09-12T07:21:23.358Z
/* DDL */ SELECT public.db_alter_table('DD_Order_Candidate','ALTER TABLE public.DD_Order_Candidate ADD COLUMN C_OrderSO_ID NUMERIC(10)')
;

-- 2024-09-12T07:21:23.376Z
ALTER TABLE DD_Order_Candidate ADD CONSTRAINT COrderSO_DDOrderCandidate FOREIGN KEY (C_OrderSO_ID) REFERENCES public.C_Order DEFERRABLE INITIALLY DEFERRED
;

-- Field: Distributionsdisposition -> Distributionsdisposition -> Auftrag
-- Column: DD_Order_Candidate.C_OrderSO_ID
-- Field: Distributionsdisposition(541807,EE01) -> Distributionsdisposition(547559,EE01) -> Auftrag
-- Column: DD_Order_Candidate.C_OrderSO_ID
-- 2024-09-12T07:21:44.456Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588975,729879,0,547559,TO_TIMESTAMP('2024-09-12 10:21:44','YYYY-MM-DD HH24:MI:SS'),100,'Auftrag',10,'EE01','Y','N','N','N','N','N','N','N','Auftrag',TO_TIMESTAMP('2024-09-12 10:21:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-09-12T07:21:44.460Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=729879 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-09-12T07:21:44.463Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543479) 
;

-- 2024-09-12T07:21:44.475Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=729879
;

-- 2024-09-12T07:21:44.478Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(729879)
;

-- Field: Distributionsdisposition -> Distributionsdisposition -> Auftrag
-- Column: DD_Order_Candidate.C_OrderSO_ID
-- Field: Distributionsdisposition(541807,EE01) -> Distributionsdisposition(547559,EE01) -> Auftrag
-- Column: DD_Order_Candidate.C_OrderSO_ID
-- 2024-09-12T07:22:00.499Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2024-09-12 10:22:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=729879
;

-- UI Element: Distributionsdisposition -> Distributionsdisposition.Auftrag
-- Column: DD_Order_Candidate.C_OrderSO_ID
-- UI Element: Distributionsdisposition(541807,EE01) -> Distributionsdisposition(547559,EE01) -> main -> 20 -> forward document.Auftrag
-- Column: DD_Order_Candidate.C_OrderSO_ID
-- 2024-09-12T07:22:40.685Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,729879,0,547559,551865,625333,'F',TO_TIMESTAMP('2024-09-12 10:22:40','YYYY-MM-DD HH24:MI:SS'),100,'Auftrag','Y','N','Y','N','N','Auftrag',60,0,0,TO_TIMESTAMP('2024-09-12 10:22:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Distributionsdisposition -> Distributionsdisposition.Auftrag
-- Column: DD_Order_Candidate.C_OrderSO_ID
-- UI Element: Distributionsdisposition(541807,EE01) -> Distributionsdisposition(547559,EE01) -> main -> 20 -> forward document.Auftrag
-- Column: DD_Order_Candidate.C_OrderSO_ID
-- 2024-09-12T07:22:50.565Z
UPDATE AD_UI_Element SET SeqNo=5,Updated=TO_TIMESTAMP('2024-09-12 10:22:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=625333
;

-- Field: Distributionsdisposition -> Distributionsdisposition -> Auftrag
-- Column: DD_Order_Candidate.C_OrderSO_ID
-- Field: Distributionsdisposition(541807,EE01) -> Distributionsdisposition(547559,EE01) -> Auftrag
-- Column: DD_Order_Candidate.C_OrderSO_ID
-- 2024-09-12T07:23:10.213Z
UPDATE AD_Field SET DisplayLogic='@C_OrderSO_ID/0@>0',Updated=TO_TIMESTAMP('2024-09-12 10:23:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=729879
;

-- Field: Distributionsdisposition -> Distributionsdisposition -> Auftragsposition
-- Column: DD_Order_Candidate.C_OrderLineSO_ID
-- Field: Distributionsdisposition(541807,EE01) -> Distributionsdisposition(547559,EE01) -> Auftragsposition
-- Column: DD_Order_Candidate.C_OrderLineSO_ID
-- 2024-09-12T07:23:16.502Z
UPDATE AD_Field SET DisplayLogic='@C_OrderLineSO_ID/0@>0',Updated=TO_TIMESTAMP('2024-09-12 10:23:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=729103
;

-- Column: DD_Order_Candidate.C_OrderSO_ID
-- Column: DD_Order_Candidate.C_OrderSO_ID
-- 2024-09-12T07:25:37.597Z
UPDATE AD_Column SET IsExcludeFromZoomTargets='N',Updated=TO_TIMESTAMP('2024-09-12 10:25:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=588975
;

