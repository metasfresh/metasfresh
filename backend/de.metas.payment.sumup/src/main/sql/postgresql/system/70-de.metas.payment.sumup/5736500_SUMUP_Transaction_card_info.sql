-- 2024-10-09T18:00:43.668Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583317,0,'CardType',TO_TIMESTAMP('2024-10-09 21:00:43','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Card Type','Card Type',TO_TIMESTAMP('2024-10-09 21:00:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-10-09T18:00:43.676Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583317 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: SUMUP_Transaction.CardType
-- Column: SUMUP_Transaction.CardType
-- 2024-10-09T18:01:32.633Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589289,583317,0,10,542443,'XX','CardType',TO_TIMESTAMP('2024-10-09 21:01:31','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.payment.sumup',0,40,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Card Type',0,0,TO_TIMESTAMP('2024-10-09 21:01:31','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-10-09T18:01:32.637Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589289 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-10-09T18:01:32.640Z
/* DDL */  select update_Column_Translation_From_AD_Element(583317) 
;

-- 2024-10-09T18:01:33.362Z
/* DDL */ SELECT public.db_alter_table('SUMUP_Transaction','ALTER TABLE public.SUMUP_Transaction ADD COLUMN CardType VARCHAR(40)')
;

-- 2024-10-09T18:02:36.125Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583318,0,'CardLast4Digits',TO_TIMESTAMP('2024-10-09 21:02:35','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Card Last 4 Digits','Card Last 4 Digits',TO_TIMESTAMP('2024-10-09 21:02:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-10-09T18:02:36.126Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583318 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: SUMUP_Transaction.CardLast4Digits
-- Column: SUMUP_Transaction.CardLast4Digits
-- 2024-10-09T18:04:26.679Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589290,583318,0,10,542443,'XX','CardLast4Digits',TO_TIMESTAMP('2024-10-09 21:04:26','YYYY-MM-DD HH24:MI:SS'),100,'N','0','de.metas.payment.sumup',0,4,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Card Last 4 Digits',0,0,TO_TIMESTAMP('2024-10-09 21:04:26','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-10-09T18:04:26.680Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589290 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-10-09T18:04:26.683Z
/* DDL */  select update_Column_Translation_From_AD_Element(583318) 
;

-- 2024-10-09T18:04:27.554Z
/* DDL */ SELECT public.db_alter_table('SUMUP_Transaction','ALTER TABLE public.SUMUP_Transaction ADD COLUMN CardLast4Digits VARCHAR(4)')
;

-- Column: SUMUP_Transaction.CardLast4Digits
-- Column: SUMUP_Transaction.CardLast4Digits
-- 2024-10-09T18:04:36.112Z
UPDATE AD_Column SET DefaultValue='', IsMandatory='N',Updated=TO_TIMESTAMP('2024-10-09 21:04:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=589290
;

-- 2024-10-09T18:04:36.670Z
INSERT INTO t_alter_column values('sumup_transaction','CardLast4Digits','VARCHAR(4)',null,null)
;

-- 2024-10-09T18:04:36.676Z
INSERT INTO t_alter_column values('sumup_transaction','CardLast4Digits',null,'NULL',null)
;

-- Field: SumUp Transaction -> SumUp Transaction -> Card Type
-- Column: SUMUP_Transaction.CardType
-- Field: SumUp Transaction(541825,de.metas.payment.sumup) -> SumUp Transaction(547617,de.metas.payment.sumup) -> Card Type
-- Column: SUMUP_Transaction.CardType
-- 2024-10-09T18:05:27.090Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589289,731893,0,547617,TO_TIMESTAMP('2024-10-09 21:05:26','YYYY-MM-DD HH24:MI:SS'),100,40,'de.metas.payment.sumup','Y','N','N','N','N','N','N','N','Card Type',TO_TIMESTAMP('2024-10-09 21:05:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-10-09T18:05:27.094Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=731893 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-10-09T18:05:27.099Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583317) 
;

-- 2024-10-09T18:05:27.104Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=731893
;

-- 2024-10-09T18:05:27.107Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(731893)
;

-- Field: SumUp Transaction -> SumUp Transaction -> Card Last 4 Digits
-- Column: SUMUP_Transaction.CardLast4Digits
-- Field: SumUp Transaction(541825,de.metas.payment.sumup) -> SumUp Transaction(547617,de.metas.payment.sumup) -> Card Last 4 Digits
-- Column: SUMUP_Transaction.CardLast4Digits
-- 2024-10-09T18:05:27.233Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589290,731894,0,547617,TO_TIMESTAMP('2024-10-09 21:05:27','YYYY-MM-DD HH24:MI:SS'),100,4,'de.metas.payment.sumup','Y','N','N','N','N','N','N','N','Card Last 4 Digits',TO_TIMESTAMP('2024-10-09 21:05:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-10-09T18:05:27.235Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=731894 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-10-09T18:05:27.236Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583318) 
;

-- 2024-10-09T18:05:27.239Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=731894
;

-- 2024-10-09T18:05:27.240Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(731894)
;

-- UI Column: SumUp Transaction(541825,de.metas.payment.sumup) -> SumUp Transaction(547617,de.metas.payment.sumup) -> main -> 20
-- UI Element Group: card
-- 2024-10-09T18:06:24.714Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547590,552040,TO_TIMESTAMP('2024-10-09 21:06:24','YYYY-MM-DD HH24:MI:SS'),100,'Y','card',30,TO_TIMESTAMP('2024-10-09 21:06:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: SumUp Transaction(541825,de.metas.payment.sumup) -> SumUp Transaction(547617,de.metas.payment.sumup) -> main -> 20
-- UI Element Group: created/updated
-- 2024-10-09T18:06:30.787Z
UPDATE AD_UI_ElementGroup SET SeqNo=990,Updated=TO_TIMESTAMP('2024-10-09 21:06:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=552035
;

-- UI Column: SumUp Transaction(541825,de.metas.payment.sumup) -> SumUp Transaction(547617,de.metas.payment.sumup) -> main -> 20
-- UI Element Group: card
-- 2024-10-09T18:06:33.391Z
UPDATE AD_UI_ElementGroup SET SeqNo=20,Updated=TO_TIMESTAMP('2024-10-09 21:06:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=552040
;

-- UI Element: SumUp Transaction -> SumUp Transaction.Card Type
-- Column: SUMUP_Transaction.CardType
-- UI Element: SumUp Transaction(541825,de.metas.payment.sumup) -> SumUp Transaction(547617,de.metas.payment.sumup) -> main -> 20 -> card.Card Type
-- Column: SUMUP_Transaction.CardType
-- 2024-10-09T18:06:44.023Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,731893,0,547617,552040,626175,'F',TO_TIMESTAMP('2024-10-09 21:06:42','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Card Type',10,0,0,TO_TIMESTAMP('2024-10-09 21:06:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: SumUp Transaction -> SumUp Transaction.Card Last 4 Digits
-- Column: SUMUP_Transaction.CardLast4Digits
-- UI Element: SumUp Transaction(541825,de.metas.payment.sumup) -> SumUp Transaction(547617,de.metas.payment.sumup) -> main -> 20 -> card.Card Last 4 Digits
-- Column: SUMUP_Transaction.CardLast4Digits
-- 2024-10-09T18:06:50.841Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,731894,0,547617,552040,626176,'F',TO_TIMESTAMP('2024-10-09 21:06:49','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Card Last 4 Digits',20,0,0,TO_TIMESTAMP('2024-10-09 21:06:49','YYYY-MM-DD HH24:MI:SS'),100)
;

