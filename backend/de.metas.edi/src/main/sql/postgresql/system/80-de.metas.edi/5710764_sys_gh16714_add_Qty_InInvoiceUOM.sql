
-- 2023-11-16T00:00:32.905Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582805,0,'QtyCUsPerTU_InInvoiceUOM',TO_TIMESTAMP('2023-11-16 01:00:32','YYYY-MM-DD HH24:MI:SS'),100,'Menge der CUs pro Einzelgebinde (normalerweise TU) in der jew. Preiseinheit','de.metas.esb.edi','Y','Menge CU/TU (Preiseinh.)','Menge CU/TU (Preiseinh.)',TO_TIMESTAMP('2023-11-16 01:00:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-11-16T00:00:32.907Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582805 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: QtyCUsPerTU_InInvoiceUOM
-- 2023-11-16T00:01:11.024Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2023-11-16 01:01:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582805 AND AD_Language='de_CH'
;

-- 2023-11-16T00:01:11.029Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582805,'de_CH') 
;

-- Element: QtyCUsPerTU_InInvoiceUOM
-- 2023-11-16T00:01:13.615Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2023-11-16 01:01:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582805 AND AD_Language='de_DE'
;

-- 2023-11-16T00:01:13.619Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582805,'de_DE') 
;

-- 2023-11-16T00:01:13.621Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582805,'de_DE') 
;

-- Column: EDI_Desadv_Pack_Item.QtyCUsPerTU_InInvoiceUOM
-- Column: EDI_Desadv_Pack_Item.QtyCUsPerTU_InInvoiceUOM
-- 2023-11-16T00:01:23.173Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587650,582805,0,29,542171,'QtyCUsPerTU_InInvoiceUOM',TO_TIMESTAMP('2023-11-16 01:01:23','YYYY-MM-DD HH24:MI:SS'),100,'N','Menge der CUs pro Einzelgebinde (normalerweise TU) in der jew. Preiseinheit','de.metas.esb.edi',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Menge CU/TU (Preiseinh.)',0,0,TO_TIMESTAMP('2023-11-16 01:01:23','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-11-16T00:01:23.175Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587650 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-11-16T00:01:23.180Z
/* DDL */  select update_Column_Translation_From_AD_Element(582805) 
;

-- Element: QtyCUsPerTU_InInvoiceUOM
-- 2023-11-16T00:03:08.744Z
UPDATE AD_Element_Trl SET Description='Number of CUs per package (usually TU) in pricing-UOM', IsTranslated='Y', Name='Qty CU per TU  (pricing-UOM)', PrintName='Qty CU per TU  (pricing-UOM)',Updated=TO_TIMESTAMP('2023-11-16 01:03:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582805 AND AD_Language='en_US'
;

-- 2023-11-16T00:03:08.748Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582805,'en_US') 
;

-- 2023-11-16T00:03:40.952Z
/* DDL */ SELECT public.db_alter_table('EDI_Desadv_Pack_Item','ALTER TABLE public.EDI_Desadv_Pack_Item ADD COLUMN QtyCUsPerTU_InInvoiceUOM NUMERIC')
;

-- 2023-11-16T00:05:14.942Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582806,0,'QtyCUsPerLU_InInvoiceUOM',TO_TIMESTAMP('2023-11-16 01:05:14','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi','Y','Menge CU/LU (Preiseinh.)','Menge CU/LU (Preiseinh.)',TO_TIMESTAMP('2023-11-16 01:05:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-11-16T00:05:14.944Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582806 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: QtyCUsPerLU_InInvoiceUOM
-- 2023-11-16T00:05:42.346Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2023-11-16 01:05:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582806 AND AD_Language='de_DE'
;

-- 2023-11-16T00:05:42.350Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582806,'de_DE') 
;

-- 2023-11-16T00:05:42.353Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582806,'de_DE') 
;

-- Element: QtyCUsPerLU_InInvoiceUOM
-- 2023-11-16T00:05:43.264Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2023-11-16 01:05:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582806 AND AD_Language='de_CH'
;

-- 2023-11-16T00:05:43.268Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582806,'de_CH') 
;

-- Element: QtyCUsPerLU_InInvoiceUOM
-- 2023-11-16T00:06:05.650Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Qty CU per LU  (pricing-UOM)', PrintName='Qty CU per LU  (pricing-UOM)',Updated=TO_TIMESTAMP('2023-11-16 01:06:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582806 AND AD_Language='en_US'
;

-- 2023-11-16T00:06:05.655Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582806,'en_US') 
;

-- Column: EDI_Desadv_Pack_Item.QtyCUsPerLU_InInvoiceUOM
-- Column: EDI_Desadv_Pack_Item.QtyCUsPerLU_InInvoiceUOM
-- 2023-11-16T00:06:26.738Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587651,582806,0,29,542171,'QtyCUsPerLU_InInvoiceUOM',TO_TIMESTAMP('2023-11-16 01:06:26','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.esb.edi',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Menge CU/LU (Preiseinh.)',0,0,TO_TIMESTAMP('2023-11-16 01:06:26','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-11-16T00:06:26.741Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587651 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-11-16T00:06:26.745Z
/* DDL */  select update_Column_Translation_From_AD_Element(582806) 
;

-- 2023-11-16T00:06:42.358Z
/* DDL */ SELECT public.db_alter_table('EDI_Desadv_Pack_Item','ALTER TABLE public.EDI_Desadv_Pack_Item ADD COLUMN QtyCUsPerLU_InInvoiceUOM NUMERIC')
;

----------------------------

-- Table: EDI_Desadv_Pack_Item
-- Table: EDI_Desadv_Pack_Item
-- 2023-11-16T00:31:00.432Z
UPDATE AD_Table SET AD_Window_ID=541543,Updated=TO_TIMESTAMP('2023-11-16 01:31:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=542171
;

-- Field: EDI Lieferavis Pack (DESADV) -> Pack Item -> Menge CU/TU (Preiseinh.)
-- Column: EDI_Desadv_Pack_Item.QtyCUsPerTU_InInvoiceUOM
-- Field: EDI Lieferavis Pack (DESADV)(541543,de.metas.esb.edi) -> Pack Item(546396,de.metas.esb.edi) -> Menge CU/TU (Preiseinh.)
-- Column: EDI_Desadv_Pack_Item.QtyCUsPerTU_InInvoiceUOM
-- 2023-11-16T00:32:12.580Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587650,721872,0,546396,TO_TIMESTAMP('2023-11-16 01:32:12','YYYY-MM-DD HH24:MI:SS'),100,'Menge der CUs pro Einzelgebinde (normalerweise TU) in der jew. Preiseinheit',10,'de.metas.esb.edi','Y','Y','N','N','N','N','N','Menge CU/TU (Preiseinh.)',TO_TIMESTAMP('2023-11-16 01:32:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-11-16T00:32:12.584Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=721872 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-11-16T00:32:12.587Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582805) 
;

-- 2023-11-16T00:32:12.605Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=721872
;

-- 2023-11-16T00:32:12.609Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(721872)
;

-- Field: EDI Lieferavis Pack (DESADV) -> Pack Item -> Menge CU/LU (Preiseinh.)
-- Column: EDI_Desadv_Pack_Item.QtyCUsPerLU_InInvoiceUOM
-- Field: EDI Lieferavis Pack (DESADV)(541543,de.metas.esb.edi) -> Pack Item(546396,de.metas.esb.edi) -> Menge CU/LU (Preiseinh.)
-- Column: EDI_Desadv_Pack_Item.QtyCUsPerLU_InInvoiceUOM
-- 2023-11-16T00:32:12.722Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587651,721873,0,546396,TO_TIMESTAMP('2023-11-16 01:32:12','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.esb.edi','Y','Y','N','N','N','N','N','Menge CU/LU (Preiseinh.)',TO_TIMESTAMP('2023-11-16 01:32:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-11-16T00:32:12.724Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=721873 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-11-16T00:32:12.726Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582806) 
;

-- 2023-11-16T00:32:12.731Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=721873
;

-- 2023-11-16T00:32:12.732Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(721873)
;

-- UI Element: EDI Lieferavis Pack (DESADV) -> Pack Item.QtyCUsPerTU_InInvoiceUOM
-- Column: EDI_Desadv_Pack_Item.QtyCUsPerTU_InInvoiceUOM
-- UI Element: EDI Lieferavis Pack (DESADV)(541543,de.metas.esb.edi) -> Pack Item(546396,de.metas.esb.edi) -> main -> 10 -> main.QtyCUsPerTU_InInvoiceUOM
-- Column: EDI_Desadv_Pack_Item.QtyCUsPerTU_InInvoiceUOM
-- 2023-11-16T00:33:25.182Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,721872,0,546396,549399,621314,'F',TO_TIMESTAMP('2023-11-16 01:33:24','YYYY-MM-DD HH24:MI:SS'),100,'Menge der CUs pro Einzelgebinde (normalerweise TU) in der jew. Preiseinheit','Y','N','N','Y','N','N','N',0,'QtyCUsPerTU_InInvoiceUOM',85,0,0,TO_TIMESTAMP('2023-11-16 01:33:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: EDI Lieferavis Pack (DESADV) -> Pack Item.QtyCUsPerLU_InInvoiceUOM
-- Column: EDI_Desadv_Pack_Item.QtyCUsPerLU_InInvoiceUOM
-- UI Element: EDI Lieferavis Pack (DESADV)(541543,de.metas.esb.edi) -> Pack Item(546396,de.metas.esb.edi) -> main -> 10 -> main.QtyCUsPerLU_InInvoiceUOM
-- Column: EDI_Desadv_Pack_Item.QtyCUsPerLU_InInvoiceUOM
-- 2023-11-16T00:33:52.625Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,721873,0,546396,549399,621315,'F',TO_TIMESTAMP('2023-11-16 01:33:52','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'QtyCUsPerLU_InInvoiceUOM',95,0,0,TO_TIMESTAMP('2023-11-16 01:33:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: EDI Lieferavis Pack (DESADV) -> Pack Item.Bewegungs-Menge
-- Column: EDI_Desadv_Pack_Item.MovementQty
-- UI Element: EDI Lieferavis Pack (DESADV)(541543,de.metas.esb.edi) -> Pack Item(546396,de.metas.esb.edi) -> main -> 10 -> main.Bewegungs-Menge
-- Column: EDI_Desadv_Pack_Item.MovementQty
-- 2023-11-16T00:34:13.113Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2023-11-16 01:34:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=609670
;

-- UI Element: EDI Lieferavis Pack (DESADV) -> Pack Item.Lieferung/Wareneingang
-- Column: EDI_Desadv_Pack_Item.M_InOut_ID
-- UI Element: EDI Lieferavis Pack (DESADV)(541543,de.metas.esb.edi) -> Pack Item(546396,de.metas.esb.edi) -> main -> 10 -> main.Lieferung/Wareneingang
-- Column: EDI_Desadv_Pack_Item.M_InOut_ID
-- 2023-11-16T00:34:13.127Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2023-11-16 01:34:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=609672
;

-- UI Element: EDI Lieferavis Pack (DESADV) -> Pack Item.Versand-/Wareneingangsposition
-- Column: EDI_Desadv_Pack_Item.M_InOutLine_ID
-- UI Element: EDI Lieferavis Pack (DESADV)(541543,de.metas.esb.edi) -> Pack Item(546396,de.metas.esb.edi) -> main -> 10 -> main.Versand-/Wareneingangsposition
-- Column: EDI_Desadv_Pack_Item.M_InOutLine_ID
-- 2023-11-16T00:34:13.141Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2023-11-16 01:34:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=609671
;

-- UI Element: EDI Lieferavis Pack (DESADV) -> Pack Item.Verpackungskapazität
-- Column: EDI_Desadv_Pack_Item.QtyItemCapacity
-- UI Element: EDI Lieferavis Pack (DESADV)(541543,de.metas.esb.edi) -> Pack Item(546396,de.metas.esb.edi) -> main -> 10 -> main.Verpackungskapazität
-- Column: EDI_Desadv_Pack_Item.QtyItemCapacity
-- 2023-11-16T00:34:13.153Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2023-11-16 01:34:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=609673
;

-- UI Element: EDI Lieferavis Pack (DESADV) -> Pack Item.TU Anzahl
-- Column: EDI_Desadv_Pack_Item.QtyTU
-- UI Element: EDI Lieferavis Pack (DESADV)(541543,de.metas.esb.edi) -> Pack Item(546396,de.metas.esb.edi) -> main -> 10 -> main.TU Anzahl
-- Column: EDI_Desadv_Pack_Item.QtyTU
-- 2023-11-16T00:34:13.166Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2023-11-16 01:34:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=609674
;

-- UI Element: EDI Lieferavis Pack (DESADV) -> Pack Item.Menge CU/TU
-- Column: EDI_Desadv_Pack_Item.QtyCUsPerTU
-- UI Element: EDI Lieferavis Pack (DESADV)(541543,de.metas.esb.edi) -> Pack Item(546396,de.metas.esb.edi) -> main -> 10 -> main.Menge CU/TU
-- Column: EDI_Desadv_Pack_Item.QtyCUsPerTU
-- 2023-11-16T00:34:13.179Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2023-11-16 01:34:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=609675
;

-- UI Element: EDI Lieferavis Pack (DESADV) -> Pack Item.QtyCUsPerLU_InInvoiceUOM
-- Column: EDI_Desadv_Pack_Item.QtyCUsPerLU_InInvoiceUOM
-- UI Element: EDI Lieferavis Pack (DESADV)(541543,de.metas.esb.edi) -> Pack Item(546396,de.metas.esb.edi) -> main -> 10 -> main.QtyCUsPerLU_InInvoiceUOM
-- Column: EDI_Desadv_Pack_Item.QtyCUsPerLU_InInvoiceUOM
-- 2023-11-16T00:34:13.191Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2023-11-16 01:34:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=621315
;

-- UI Element: EDI Lieferavis Pack (DESADV) -> Pack Item.QtyCUsPerTU_InInvoiceUOM
-- Column: EDI_Desadv_Pack_Item.QtyCUsPerTU_InInvoiceUOM
-- UI Element: EDI Lieferavis Pack (DESADV)(541543,de.metas.esb.edi) -> Pack Item(546396,de.metas.esb.edi) -> main -> 10 -> main.QtyCUsPerTU_InInvoiceUOM
-- Column: EDI_Desadv_Pack_Item.QtyCUsPerTU_InInvoiceUOM
-- 2023-11-16T00:34:13.205Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2023-11-16 01:34:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=621314
;

-- UI Element: EDI Lieferavis Pack (DESADV) -> Pack Item.Mindesthaltbarkeitsdatum
-- Column: EDI_Desadv_Pack_Item.BestBeforeDate
-- UI Element: EDI Lieferavis Pack (DESADV)(541543,de.metas.esb.edi) -> Pack Item(546396,de.metas.esb.edi) -> main -> 10 -> main.Mindesthaltbarkeitsdatum
-- Column: EDI_Desadv_Pack_Item.BestBeforeDate
-- 2023-11-16T00:34:13.218Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2023-11-16 01:34:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=609677
;

-- UI Element: EDI Lieferavis Pack (DESADV) -> Pack Item.Chargennummer
-- Column: EDI_Desadv_Pack_Item.LotNumber
-- UI Element: EDI Lieferavis Pack (DESADV)(541543,de.metas.esb.edi) -> Pack Item(546396,de.metas.esb.edi) -> main -> 10 -> main.Chargennummer
-- Column: EDI_Desadv_Pack_Item.LotNumber
-- 2023-11-16T00:34:13.229Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2023-11-16 01:34:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=609678
;

-- UI Element: EDI Lieferavis Pack (DESADV) -> Pack Item.Aktiv
-- Column: EDI_Desadv_Pack_Item.IsActive
-- UI Element: EDI Lieferavis Pack (DESADV)(541543,de.metas.esb.edi) -> Pack Item(546396,de.metas.esb.edi) -> main -> 10 -> main.Aktiv
-- Column: EDI_Desadv_Pack_Item.IsActive
-- 2023-11-16T00:34:13.241Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2023-11-16 01:34:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=609679
;

-- UI Element: EDI Lieferavis Pack (DESADV) -> Pack Item.Sektion
-- Column: EDI_Desadv_Pack_Item.AD_Org_ID
-- UI Element: EDI Lieferavis Pack (DESADV)(541543,de.metas.esb.edi) -> Pack Item(546396,de.metas.esb.edi) -> main -> 10 -> main.Sektion
-- Column: EDI_Desadv_Pack_Item.AD_Org_ID
-- 2023-11-16T00:34:13.253Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=140,Updated=TO_TIMESTAMP('2023-11-16 01:34:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=609680
;

-- UI Element: EDI Lieferavis Pack (DESADV) -> Pack Item.Sektion
-- Column: EDI_Desadv_Pack_Item.AD_Org_ID
-- UI Element: EDI Lieferavis Pack (DESADV)(541543,de.metas.esb.edi) -> Pack Item(546396,de.metas.esb.edi) -> main -> 10 -> main.Sektion
-- Column: EDI_Desadv_Pack_Item.AD_Org_ID
-- 2023-11-16T00:34:23.716Z
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2023-11-16 01:34:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=609680
;
