-- Column: SUMUP_API_Log.C_POS_Order_ID
-- Column: SUMUP_API_Log.C_POS_Order_ID
-- 2024-10-05T10:52:59.553Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589241,583266,0,30,542442,'XX','C_POS_Order_ID',TO_TIMESTAMP('2024-10-05 13:52:59','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.payment.sumup',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'POS Order',0,0,TO_TIMESTAMP('2024-10-05 13:52:59','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-10-05T10:52:59.561Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589241 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-10-05T10:52:59.567Z
/* DDL */  select update_Column_Translation_From_AD_Element(583266) 
;

-- 2024-10-05T10:53:01.014Z
/* DDL */ SELECT public.db_alter_table('SUMUP_API_Log','ALTER TABLE public.SUMUP_API_Log ADD COLUMN C_POS_Order_ID NUMERIC(10)')
;

-- 2024-10-05T10:53:01.026Z
ALTER TABLE SUMUP_API_Log ADD CONSTRAINT CPOSOrder_SUMUPAPILog FOREIGN KEY (C_POS_Order_ID) REFERENCES public.C_POS_Order DEFERRABLE INITIALLY DEFERRED
;

-- Column: SUMUP_API_Log.C_POS_Payment_ID
-- Column: SUMUP_API_Log.C_POS_Payment_ID
-- 2024-10-05T10:53:20.137Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589242,583269,0,30,542442,'XX','C_POS_Payment_ID',TO_TIMESTAMP('2024-10-05 13:53:19','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.payment.sumup',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'POS Payment',0,0,TO_TIMESTAMP('2024-10-05 13:53:19','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-10-05T10:53:20.139Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589242 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-10-05T10:53:20.143Z
/* DDL */  select update_Column_Translation_From_AD_Element(583269) 
;

-- 2024-10-05T10:53:20.718Z
/* DDL */ SELECT public.db_alter_table('SUMUP_API_Log','ALTER TABLE public.SUMUP_API_Log ADD COLUMN C_POS_Payment_ID NUMERIC(10)')
;

-- 2024-10-05T10:53:20.725Z
ALTER TABLE SUMUP_API_Log ADD CONSTRAINT CPOSPayment_SUMUPAPILog FOREIGN KEY (C_POS_Payment_ID) REFERENCES public.C_POS_Payment DEFERRABLE INITIALLY DEFERRED
;

-- Field: SumUp API Log -> SumUp API Log -> POS Order
-- Column: SUMUP_API_Log.C_POS_Order_ID
-- Field: SumUp API Log(541824,de.metas.payment.sumup) -> SumUp API Log(547616,de.metas.payment.sumup) -> POS Order
-- Column: SUMUP_API_Log.C_POS_Order_ID
-- 2024-10-05T10:54:03.648Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589241,731846,0,547616,TO_TIMESTAMP('2024-10-05 13:54:03','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.payment.sumup','Y','N','N','N','N','N','N','N','POS Order',TO_TIMESTAMP('2024-10-05 13:54:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-10-05T10:54:03.653Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=731846 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-10-05T10:54:03.657Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583266) 
;

-- 2024-10-05T10:54:03.669Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=731846
;

-- 2024-10-05T10:54:03.673Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(731846)
;

-- Field: SumUp API Log -> SumUp API Log -> POS Payment
-- Column: SUMUP_API_Log.C_POS_Payment_ID
-- Field: SumUp API Log(541824,de.metas.payment.sumup) -> SumUp API Log(547616,de.metas.payment.sumup) -> POS Payment
-- Column: SUMUP_API_Log.C_POS_Payment_ID
-- 2024-10-05T10:54:03.801Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589242,731847,0,547616,TO_TIMESTAMP('2024-10-05 13:54:03','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.payment.sumup','Y','N','N','N','N','N','N','N','POS Payment',TO_TIMESTAMP('2024-10-05 13:54:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-10-05T10:54:03.802Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=731847 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-10-05T10:54:03.804Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583269) 
;

-- 2024-10-05T10:54:03.809Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=731847
;

-- 2024-10-05T10:54:03.810Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(731847)
;

-- Column: SUMUP_API_Log.C_POS_Payment_ID
-- Column: SUMUP_API_Log.C_POS_Payment_ID
-- 2024-10-05T10:54:15.258Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2024-10-05 13:54:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=589242
;

-- Column: SUMUP_API_Log.C_POS_Order_ID
-- Column: SUMUP_API_Log.C_POS_Order_ID
-- 2024-10-05T10:54:19.610Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2024-10-05 13:54:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=589241
;

-- UI Column: SumUp API Log(541824,de.metas.payment.sumup) -> SumUp API Log(547616,de.metas.payment.sumup) -> main -> 10
-- UI Element Group: pos context
-- 2024-10-05T10:54:59.986Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547587,552036,TO_TIMESTAMP('2024-10-05 13:54:59','YYYY-MM-DD HH24:MI:SS'),100,'Y','pos context',30,TO_TIMESTAMP('2024-10-05 13:54:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: SumUp API Log -> SumUp API Log.POS Order
-- Column: SUMUP_API_Log.C_POS_Order_ID
-- UI Element: SumUp API Log(541824,de.metas.payment.sumup) -> SumUp API Log(547616,de.metas.payment.sumup) -> main -> 10 -> pos context.POS Order
-- Column: SUMUP_API_Log.C_POS_Order_ID
-- 2024-10-05T10:55:08.984Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,731846,0,547616,552036,626148,'F',TO_TIMESTAMP('2024-10-05 13:55:08','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','POS Order',10,0,0,TO_TIMESTAMP('2024-10-05 13:55:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: SumUp API Log -> SumUp API Log.POS Payment
-- Column: SUMUP_API_Log.C_POS_Payment_ID
-- UI Element: SumUp API Log(541824,de.metas.payment.sumup) -> SumUp API Log(547616,de.metas.payment.sumup) -> main -> 10 -> pos context.POS Payment
-- Column: SUMUP_API_Log.C_POS_Payment_ID
-- 2024-10-05T10:55:15.588Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,731847,0,547616,552036,626149,'F',TO_TIMESTAMP('2024-10-05 13:55:15','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','POS Payment',20,0,0,TO_TIMESTAMP('2024-10-05 13:55:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Field: SumUp API Log -> SumUp API Log -> SumUp API Log
-- Column: SUMUP_API_Log.SUMUP_API_Log_ID
-- Field: SumUp API Log(541824,de.metas.payment.sumup) -> SumUp API Log(547616,de.metas.payment.sumup) -> SumUp API Log
-- Column: SUMUP_API_Log.SUMUP_API_Log_ID
-- 2024-10-05T11:51:17.191Z
UPDATE AD_Field SET SortNo=-1.000000000000,Updated=TO_TIMESTAMP('2024-10-05 14:51:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=731816
;

