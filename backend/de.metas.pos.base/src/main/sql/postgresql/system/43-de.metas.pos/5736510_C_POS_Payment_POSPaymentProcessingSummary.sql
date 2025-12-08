-- 2024-10-09T18:24:20.981Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583319,0,'POSPaymentProcessingSummary',TO_TIMESTAMP('2024-10-09 21:24:20','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.pos','Y','Payment Processing Summary','Payment Processing Summary',TO_TIMESTAMP('2024-10-09 21:24:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-10-09T18:24:20.984Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583319 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: C_POS_Payment.POSPaymentProcessingSummary
-- Column: C_POS_Payment.POSPaymentProcessingSummary
-- 2024-10-09T18:24:36.261Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589291,583319,0,10,542437,'XX','POSPaymentProcessingSummary',TO_TIMESTAMP('2024-10-09 21:24:36','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.pos',0,255,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Payment Processing Summary',0,0,TO_TIMESTAMP('2024-10-09 21:24:36','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-10-09T18:24:36.263Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589291 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-10-09T18:24:36.266Z
/* DDL */  select update_Column_Translation_From_AD_Element(583319) 
;

-- 2024-10-09T18:24:36.838Z
/* DDL */ SELECT public.db_alter_table('C_POS_Payment','ALTER TABLE public.C_POS_Payment ADD COLUMN POSPaymentProcessingSummary VARCHAR(255)')
;

-- Field: POS Order -> POS Payment -> Payment Processing Summary
-- Column: C_POS_Payment.POSPaymentProcessingSummary
-- Field: POS Order(541818,de.metas.pos) -> POS Payment(547593,de.metas.pos) -> Payment Processing Summary
-- Column: C_POS_Payment.POSPaymentProcessingSummary
-- 2024-10-09T18:24:49.547Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589291,731895,0,547593,TO_TIMESTAMP('2024-10-09 21:24:49','YYYY-MM-DD HH24:MI:SS'),100,255,'de.metas.pos','Y','N','N','N','N','N','N','N','Payment Processing Summary',TO_TIMESTAMP('2024-10-09 21:24:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-10-09T18:24:49.549Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=731895 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-10-09T18:24:49.551Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583319) 
;

-- 2024-10-09T18:24:49.556Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=731895
;

-- 2024-10-09T18:24:49.558Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(731895)
;

-- UI Element: POS Order -> POS Payment.Payment Processing Summary
-- Column: C_POS_Payment.POSPaymentProcessingSummary
-- UI Element: POS Order(541818,de.metas.pos) -> POS Payment(547593,de.metas.pos) -> main -> 20 -> status.Payment Processing Summary
-- Column: C_POS_Payment.POSPaymentProcessingSummary
-- 2024-10-09T18:25:09.014Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,731895,0,547593,551964,626177,'F',TO_TIMESTAMP('2024-10-09 21:25:08','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Payment Processing Summary',60,0,0,TO_TIMESTAMP('2024-10-09 21:25:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Field: POS Order -> POS Payment -> Payment Processing Summary
-- Column: C_POS_Payment.POSPaymentProcessingSummary
-- Field: POS Order(541818,de.metas.pos) -> POS Payment(547593,de.metas.pos) -> Payment Processing Summary
-- Column: C_POS_Payment.POSPaymentProcessingSummary
-- 2024-10-09T18:25:48.813Z
UPDATE AD_Field SET DisplayLogic='@POSPaymentProcessingSummary@!''''',Updated=TO_TIMESTAMP('2024-10-09 21:25:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=731895
;

-- UI Element: POS Order -> POS Payment.Zahlung
-- Column: C_POS_Payment.C_Payment_ID
-- UI Element: POS Order(541818,de.metas.pos) -> POS Payment(547593,de.metas.pos) -> main -> 20 -> status.Zahlung
-- Column: C_POS_Payment.C_Payment_ID
-- 2024-10-09T18:26:08.810Z
UPDATE AD_UI_Element SET SeqNo=990,Updated=TO_TIMESTAMP('2024-10-09 21:26:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=625769
;

-- UI Element: POS Order -> POS Payment.Payment Processing Summary
-- Column: C_POS_Payment.POSPaymentProcessingSummary
-- UI Element: POS Order(541818,de.metas.pos) -> POS Payment(547593,de.metas.pos) -> main -> 20 -> status.Payment Processing Summary
-- Column: C_POS_Payment.POSPaymentProcessingSummary
-- 2024-10-09T18:26:11.531Z
UPDATE AD_UI_Element SET SeqNo=50,Updated=TO_TIMESTAMP('2024-10-09 21:26:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=626177
;

