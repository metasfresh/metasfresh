-- 2024-07-22T10:35:04.833Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583193,0,'Forward_PP_Order_Candidate_ID',TO_TIMESTAMP('2024-07-22 13:35:04','YYYY-MM-DD HH24:MI:SS'),100,'EE01','Y','Produktionsdisposition','Produktionsdisposition',TO_TIMESTAMP('2024-07-22 13:35:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-07-22T10:35:04.839Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583193 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2024-07-22T10:35:17.042Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583194,0,'Forward_PP_OrderLine_Candidate_ID',TO_TIMESTAMP('2024-07-22 13:35:16','YYYY-MM-DD HH24:MI:SS'),100,'EE01','Y','Manufacturing Order Line Candidate','Manufacturing Order Line Candidate',TO_TIMESTAMP('2024-07-22 13:35:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-07-22T10:35:17.044Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583194 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: DD_Order_Candidate.Forward_PP_Order_Candidate_ID
-- Column: DD_Order_Candidate.Forward_PP_Order_Candidate_ID
-- 2024-07-22T10:35:51.430Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588869,583193,0,30,541494,542424,'XX','Forward_PP_Order_Candidate_ID',TO_TIMESTAMP('2024-07-22 13:35:51','YYYY-MM-DD HH24:MI:SS'),100,'N','EE01',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Produktionsdisposition',0,0,TO_TIMESTAMP('2024-07-22 13:35:51','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-07-22T10:35:51.431Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588869 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-07-22T10:35:51.461Z
/* DDL */  select update_Column_Translation_From_AD_Element(583193) 
;

-- 2024-07-22T10:35:53.310Z
/* DDL */ SELECT public.db_alter_table('DD_Order_Candidate','ALTER TABLE public.DD_Order_Candidate ADD COLUMN Forward_PP_Order_Candidate_ID NUMERIC(10)')
;

-- 2024-07-22T10:35:53.320Z
ALTER TABLE DD_Order_Candidate ADD CONSTRAINT ForwardPPOrderCandidate_DDOrderCandidate FOREIGN KEY (Forward_PP_Order_Candidate_ID) REFERENCES public.PP_Order_Candidate DEFERRABLE INITIALLY DEFERRED
;

-- Name: PP_OrderLine_Candidate
-- 2024-07-22T10:36:37.360Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541878,TO_TIMESTAMP('2024-07-22 13:36:37','YYYY-MM-DD HH24:MI:SS'),100,'EE01','Y','N','PP_OrderLine_Candidate',TO_TIMESTAMP('2024-07-22 13:36:37','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2024-07-22T10:36:37.363Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541878 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: PP_OrderLine_Candidate
-- Table: PP_OrderLine_Candidate
-- Key: PP_OrderLine_Candidate.PP_OrderLine_Candidate_ID
-- 2024-07-22T10:36:58.261Z
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy) VALUES (0,577896,0,541878,541914,TO_TIMESTAMP('2024-07-22 13:36:58','YYYY-MM-DD HH24:MI:SS'),100,'EE01','Y','N','N',TO_TIMESTAMP('2024-07-22 13:36:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Column: DD_Order_Candidate.Forward_PP_OrderLine_Candidate_ID
-- Column: DD_Order_Candidate.Forward_PP_OrderLine_Candidate_ID
-- 2024-07-22T10:37:09.038Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588870,583194,0,30,541878,542424,'XX','Forward_PP_OrderLine_Candidate_ID',TO_TIMESTAMP('2024-07-22 13:37:07','YYYY-MM-DD HH24:MI:SS'),100,'N','EE01',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Manufacturing Order Line Candidate',0,0,TO_TIMESTAMP('2024-07-22 13:37:07','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-07-22T10:37:09.040Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588870 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-07-22T10:37:09.043Z
/* DDL */  select update_Column_Translation_From_AD_Element(583194) 
;

-- 2024-07-22T10:37:11.215Z
/* DDL */ SELECT public.db_alter_table('DD_Order_Candidate','ALTER TABLE public.DD_Order_Candidate ADD COLUMN Forward_PP_OrderLine_Candidate_ID NUMERIC(10)')
;

-- 2024-07-22T10:37:11.221Z
ALTER TABLE DD_Order_Candidate ADD CONSTRAINT ForwardPPOrderLineCandidate_DDOrderCandidate FOREIGN KEY (Forward_PP_OrderLine_Candidate_ID) REFERENCES public.PP_OrderLine_Candidate DEFERRABLE INITIALLY DEFERRED
;

-- Field: Distribution Order Candidate -> Distribution Order Candidate -> Produktionsdisposition
-- Column: DD_Order_Candidate.Forward_PP_Order_Candidate_ID
-- Field: Distribution Order Candidate(541807,EE01) -> Distribution Order Candidate(547559,EE01) -> Produktionsdisposition
-- Column: DD_Order_Candidate.Forward_PP_Order_Candidate_ID
-- 2024-07-22T10:37:44.667Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588869,729142,0,547559,TO_TIMESTAMP('2024-07-22 13:37:44','YYYY-MM-DD HH24:MI:SS'),100,10,'EE01','Y','N','N','N','N','N','N','N','Produktionsdisposition',TO_TIMESTAMP('2024-07-22 13:37:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-07-22T10:37:44.670Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=729142 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-07-22T10:37:44.673Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583193) 
;

-- 2024-07-22T10:37:44.685Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=729142
;

-- 2024-07-22T10:37:44.687Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(729142)
;

-- Field: Distribution Order Candidate -> Distribution Order Candidate -> Manufacturing Order Line Candidate
-- Column: DD_Order_Candidate.Forward_PP_OrderLine_Candidate_ID
-- Field: Distribution Order Candidate(541807,EE01) -> Distribution Order Candidate(547559,EE01) -> Manufacturing Order Line Candidate
-- Column: DD_Order_Candidate.Forward_PP_OrderLine_Candidate_ID
-- 2024-07-22T10:37:44.797Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588870,729143,0,547559,TO_TIMESTAMP('2024-07-22 13:37:44','YYYY-MM-DD HH24:MI:SS'),100,10,'EE01','Y','N','N','N','N','N','N','N','Manufacturing Order Line Candidate',TO_TIMESTAMP('2024-07-22 13:37:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-07-22T10:37:44.799Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=729143 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-07-22T10:37:44.801Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583194) 
;

-- 2024-07-22T10:37:44.804Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=729143
;

-- 2024-07-22T10:37:44.804Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(729143)
;

-- Field: Distribution Order Candidate -> Distribution Order Candidate -> Produktionsdisposition
-- Column: DD_Order_Candidate.Forward_PP_Order_Candidate_ID
-- Field: Distribution Order Candidate(541807,EE01) -> Distribution Order Candidate(547559,EE01) -> Produktionsdisposition
-- Column: DD_Order_Candidate.Forward_PP_Order_Candidate_ID
-- 2024-07-22T10:39:25.163Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2024-07-22 13:39:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=729142
;

-- Field: Distribution Order Candidate -> Distribution Order Candidate -> Manufacturing Order Line Candidate
-- Column: DD_Order_Candidate.Forward_PP_OrderLine_Candidate_ID
-- Field: Distribution Order Candidate(541807,EE01) -> Distribution Order Candidate(547559,EE01) -> Manufacturing Order Line Candidate
-- Column: DD_Order_Candidate.Forward_PP_OrderLine_Candidate_ID
-- 2024-07-22T10:39:26.505Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2024-07-22 13:39:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=729143
;

-- UI Element: Distribution Order Candidate -> Distribution Order Candidate.Produktionsdisposition
-- Column: DD_Order_Candidate.Forward_PP_Order_Candidate_ID
-- UI Element: Distribution Order Candidate(541807,EE01) -> Distribution Order Candidate(547559,EE01) -> main -> 20 -> forward document.Produktionsdisposition
-- Column: DD_Order_Candidate.Forward_PP_Order_Candidate_ID
-- 2024-07-22T10:40:34.852Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,729142,0,547559,551865,625030,'F',TO_TIMESTAMP('2024-07-22 13:40:34','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Produktionsdisposition',40,0,0,TO_TIMESTAMP('2024-07-22 13:40:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Distribution Order Candidate -> Distribution Order Candidate.Manufacturing Order Line Candidate
-- Column: DD_Order_Candidate.Forward_PP_OrderLine_Candidate_ID
-- UI Element: Distribution Order Candidate(541807,EE01) -> Distribution Order Candidate(547559,EE01) -> main -> 20 -> forward document.Manufacturing Order Line Candidate
-- Column: DD_Order_Candidate.Forward_PP_OrderLine_Candidate_ID
-- 2024-07-22T10:40:42.137Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,729143,0,547559,551865,625031,'F',TO_TIMESTAMP('2024-07-22 13:40:41','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Manufacturing Order Line Candidate',50,0,0,TO_TIMESTAMP('2024-07-22 13:40:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Distribution Order Candidate -> Distribution Order Candidate.Auftragsposition
-- Column: DD_Order_Candidate.C_OrderLineSO_ID
-- UI Element: Distribution Order Candidate(541807,EE01) -> Distribution Order Candidate(547559,EE01) -> main -> 20 -> forward document.Auftragsposition
-- Column: DD_Order_Candidate.C_OrderLineSO_ID
-- 2024-07-22T10:41:00.275Z
UPDATE AD_UI_Element SET SeqNo=10,Updated=TO_TIMESTAMP('2024-07-22 13:41:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=625018
;

-- UI Element: Distribution Order Candidate -> Distribution Order Candidate.Forward Manufacturing Order
-- Column: DD_Order_Candidate.Forward_PP_Order_ID
-- UI Element: Distribution Order Candidate(541807,EE01) -> Distribution Order Candidate(547559,EE01) -> main -> 20 -> forward document.Forward Manufacturing Order
-- Column: DD_Order_Candidate.Forward_PP_Order_ID
-- 2024-07-22T10:41:07.661Z
UPDATE AD_UI_Element SET SeqNo=20,Updated=TO_TIMESTAMP('2024-07-22 13:41:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=625001
;

-- UI Element: Distribution Order Candidate -> Distribution Order Candidate.Produktionsdisposition
-- Column: DD_Order_Candidate.Forward_PP_Order_Candidate_ID
-- UI Element: Distribution Order Candidate(541807,EE01) -> Distribution Order Candidate(547559,EE01) -> main -> 20 -> forward document.Produktionsdisposition
-- Column: DD_Order_Candidate.Forward_PP_Order_Candidate_ID
-- 2024-07-22T10:41:15.193Z
UPDATE AD_UI_Element SET SeqNo=20,Updated=TO_TIMESTAMP('2024-07-22 13:41:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=625030
;

-- UI Element: Distribution Order Candidate -> Distribution Order Candidate.Manufacturing Order Line Candidate
-- Column: DD_Order_Candidate.Forward_PP_OrderLine_Candidate_ID
-- UI Element: Distribution Order Candidate(541807,EE01) -> Distribution Order Candidate(547559,EE01) -> main -> 20 -> forward document.Manufacturing Order Line Candidate
-- Column: DD_Order_Candidate.Forward_PP_OrderLine_Candidate_ID
-- 2024-07-22T10:41:20.294Z
UPDATE AD_UI_Element SET SeqNo=30,Updated=TO_TIMESTAMP('2024-07-22 13:41:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=625031
;

-- UI Element: Distribution Order Candidate -> Distribution Order Candidate.Forward Manufacturing Order
-- Column: DD_Order_Candidate.Forward_PP_Order_ID
-- UI Element: Distribution Order Candidate(541807,EE01) -> Distribution Order Candidate(547559,EE01) -> main -> 20 -> forward document.Forward Manufacturing Order
-- Column: DD_Order_Candidate.Forward_PP_Order_ID
-- 2024-07-22T10:41:29.235Z
UPDATE AD_UI_Element SET SeqNo=40,Updated=TO_TIMESTAMP('2024-07-22 13:41:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=625001
;

-- UI Element: Distribution Order Candidate -> Distribution Order Candidate.Forward Manufacturing Order BOM Line
-- Column: DD_Order_Candidate.Forward_PP_Order_BOMLine_ID
-- UI Element: Distribution Order Candidate(541807,EE01) -> Distribution Order Candidate(547559,EE01) -> main -> 20 -> forward document.Forward Manufacturing Order BOM Line
-- Column: DD_Order_Candidate.Forward_PP_Order_BOMLine_ID
-- 2024-07-22T10:41:31.377Z
UPDATE AD_UI_Element SET SeqNo=50,Updated=TO_TIMESTAMP('2024-07-22 13:41:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=625002
;

