-- 2024-10-04T11:31:02.682Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583305,0,'POSPaymentProcessor',TO_TIMESTAMP('2024-10-04 14:31:02','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.pos','Y','Payment Processor','Payment Processor',TO_TIMESTAMP('2024-10-04 14:31:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-10-04T11:31:02.685Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583305 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Name: POSPaymentProcessor
-- 2024-10-04T11:31:48.699Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541896,TO_TIMESTAMP('2024-10-04 14:31:48','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.pos','Y','N','POSPaymentProcessor',TO_TIMESTAMP('2024-10-04 14:31:48','YYYY-MM-DD HH24:MI:SS'),100,'L')
;

-- 2024-10-04T11:31:48.703Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541896 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Column: C_POS.POSPaymentProcessor
-- Column: C_POS.POSPaymentProcessor
-- 2024-10-04T11:33:51.735Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589238,583305,0,17,541896,748,'XX','POSPaymentProcessor',TO_TIMESTAMP('2024-10-04 14:33:51','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.pos',0,40,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Payment Processor',0,0,TO_TIMESTAMP('2024-10-04 14:33:51','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-10-04T11:33:51.739Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589238 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-10-04T11:33:51.743Z
/* DDL */  select update_Column_Translation_From_AD_Element(583305) 
;

-- 2024-10-04T11:33:54.935Z
/* DDL */ SELECT public.db_alter_table('C_POS','ALTER TABLE public.C_POS ADD COLUMN POSPaymentProcessor VARCHAR(40)')
;

-- UI Column: POS-Terminal(338,D) -> POS-Terminal(676,D) -> main -> 10
-- UI Element Group: card payments
-- 2024-10-04T11:35:41.269Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547555,552034,TO_TIMESTAMP('2024-10-04 14:35:41','YYYY-MM-DD HH24:MI:SS'),100,'Y','card payments',35,TO_TIMESTAMP('2024-10-04 14:35:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Field: POS-Terminal -> POS-Terminal -> Payment Processor
-- Column: C_POS.POSPaymentProcessor
-- Field: POS-Terminal(338,D) -> POS-Terminal(676,D) -> Payment Processor
-- Column: C_POS.POSPaymentProcessor
-- 2024-10-04T11:35:52.925Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589238,731841,0,676,TO_TIMESTAMP('2024-10-04 14:35:51','YYYY-MM-DD HH24:MI:SS'),100,40,'D','Y','N','N','N','N','N','N','N','Payment Processor',TO_TIMESTAMP('2024-10-04 14:35:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-10-04T11:35:52.928Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=731841 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-10-04T11:35:52.930Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583305) 
;

-- 2024-10-04T11:35:52.937Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=731841
;

-- 2024-10-04T11:35:52.939Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(731841)
;

-- UI Element: POS-Terminal -> POS-Terminal.Payment Processor
-- Column: C_POS.POSPaymentProcessor
-- UI Element: POS-Terminal(338,D) -> POS-Terminal(676,D) -> main -> 10 -> card payments.Payment Processor
-- Column: C_POS.POSPaymentProcessor
-- 2024-10-04T11:36:13.314Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,731841,0,676,552034,626143,'F',TO_TIMESTAMP('2024-10-04 14:36:13','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Payment Processor',10,0,0,TO_TIMESTAMP('2024-10-04 14:36:13','YYYY-MM-DD HH24:MI:SS'),100)
;

