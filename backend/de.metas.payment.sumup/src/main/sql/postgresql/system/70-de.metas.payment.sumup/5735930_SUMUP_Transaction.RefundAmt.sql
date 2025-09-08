-- Column: SUMUP_Transaction.RefundAmt
-- Column: SUMUP_Transaction.RefundAmt
-- 2024-10-05T13:15:17.933Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589243,544200,0,12,542443,'XX','RefundAmt',TO_TIMESTAMP('2024-10-05 16:15:17','YYYY-MM-DD HH24:MI:SS'),100,'N','0','Rückvergütungsbetrag pro Produkt-Einheit','de.metas.payment.sumup',0,10,'','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Rückvergütungsbetrag',0,0,TO_TIMESTAMP('2024-10-05 16:15:17','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-10-05T13:15:17.940Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589243 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-10-05T13:15:17.947Z
/* DDL */  select update_Column_Translation_From_AD_Element(544200) 
;

-- 2024-10-05T13:15:19.275Z
/* DDL */ SELECT public.db_alter_table('SUMUP_Transaction','ALTER TABLE public.SUMUP_Transaction ADD COLUMN RefundAmt NUMERIC DEFAULT 0 NOT NULL')
;

-- Field: SumUp Transaction -> SumUp Transaction -> Rückvergütungsbetrag
-- Column: SUMUP_Transaction.RefundAmt
-- Field: SumUp Transaction(541825,de.metas.payment.sumup) -> SumUp Transaction(547617,de.metas.payment.sumup) -> Rückvergütungsbetrag
-- Column: SUMUP_Transaction.RefundAmt
-- 2024-10-05T13:15:41.759Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589243,731848,0,547617,TO_TIMESTAMP('2024-10-05 16:15:41','YYYY-MM-DD HH24:MI:SS'),100,'Rückvergütungsbetrag pro Produkt-Einheit',10,'de.metas.payment.sumup','','Y','N','N','N','N','N','N','N','Rückvergütungsbetrag',TO_TIMESTAMP('2024-10-05 16:15:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-10-05T13:15:41.762Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=731848 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-10-05T13:15:41.765Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(544200) 
;

-- 2024-10-05T13:15:41.772Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=731848
;

-- 2024-10-05T13:15:41.777Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(731848)
;

-- UI Element: SumUp Transaction -> SumUp Transaction.Rückvergütungsbetrag
-- Column: SUMUP_Transaction.RefundAmt
-- UI Element: SumUp Transaction(541825,de.metas.payment.sumup) -> SumUp Transaction(547617,de.metas.payment.sumup) -> main -> 10 -> amount.Rückvergütungsbetrag
-- Column: SUMUP_Transaction.RefundAmt
-- 2024-10-05T13:16:18.628Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,731848,0,547617,552031,626150,'F',TO_TIMESTAMP('2024-10-05 16:16:18','YYYY-MM-DD HH24:MI:SS'),100,'Rückvergütungsbetrag pro Produkt-Einheit','','Y','N','Y','N','N','Rückvergütungsbetrag',30,0,0,TO_TIMESTAMP('2024-10-05 16:16:18','YYYY-MM-DD HH24:MI:SS'),100)
;

