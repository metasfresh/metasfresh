-- nShift Lieferweg: add carrier fields to the Sales Order window (public window 143, header tab 186).
-- Three fields, all in the advanced-edit element group (AD_UI_ElementGroup 540499), each with
-- field-level DisplayLogic '@M_Shipper_ID@!0' (only shown when a shipper is set):
--   1. Carrier_Product_ID    (C_Order.Carrier_Product_ID,    AD_Column 592988, element 584116)
--   2. Carrier_Goods_Type_ID (C_Order.Carrier_Goods_Type_ID, AD_Column 592989, element 584112)
--   3. Carrier services       -> multi-value junction C_Order_Carrier_Service.Carrier_Service_ID
--      (AD_Column 592987, element 584113) surfaced via a Labels UI element (AD_UI_ElementType='L'),
--      mirroring the Workplace BPartner-Group labels wiring (5806910: tab 549293 / field 780737 /
--      ui-element 652032). The Labels widget's visibility is read from its selector field's
--      DisplayLogic (LayoutFactory -> GridTabVOBasedDocumentEntityDescriptorFactory#extractLabelDisplayLogic),
--      so the selector AD_Field carries '@M_Shipper_ID@!0'.
-- Dropdown constraints come from the COLUMN-level AD_Val_Rule_ID already set in Task 1
-- (goods type 540793, service 540794, product 540751); the fields deliberately carry NO own val rule.
-- IDs allocated from idserver.metas.de on 2026-07-23:
--   AD_Field 781770 (Carrier_Product_ID field), AD_UI_Element 652702
--   AD_Field 781771 (Carrier_Goods_Type_ID field), AD_UI_Element 652703
--   AD_Tab 549353 (hidden labels tab), AD_Element 585126 (tab caption),
--   AD_Field 781772 (services selector field), AD_UI_Element 652704 (Labels element)
-- Reused elements: 584116 (Carrier_Product_ID), 584112 (Carrier_Goods_Type_ID), 584113 (Carrier_Service_ID).

-- ============================================================================
-- 1) Carrier_Product_ID field (advanced edit) + UI element
-- ============================================================================
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,DisplayLogic,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy)
VALUES (0,592988,781770 /*From ID Server*/,0,186,TO_TIMESTAMP('2026-07-23 10:02:00','YYYY-MM-DD HH24:MI:SS'),100,10,'@M_Shipper_ID@!0','D','Y','Y','N','N','N','N','N','Lieferweg-Produkt',TO_TIMESTAMP('2026-07-23 10:02:00','YYYY-MM-DD HH24:MI:SS'),100)
;
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=781770 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;
/* DDL */ select update_FieldTranslation_From_AD_Name_Element(584116)
;
DELETE FROM AD_Element_Link WHERE AD_Field_ID=781770
;
/* DDL */ select AD_Element_Link_Create_Missing_Field(781770)
;
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Org_ID,AD_Field_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy)
VALUES (0,0,781770,186,652702 /*From ID Server*/,540499,'F',TO_TIMESTAMP('2026-07-23 10:02:30','YYYY-MM-DD HH24:MI:SS'),100,'Y','Y','Y','N','N','Lieferweg-Produkt',500,0,0,TO_TIMESTAMP('2026-07-23 10:02:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- ============================================================================
-- 2) Carrier_Goods_Type_ID field (advanced edit) + UI element
-- ============================================================================
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,DisplayLogic,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy)
VALUES (0,592989,781771 /*From ID Server*/,0,186,TO_TIMESTAMP('2026-07-23 10:03:00','YYYY-MM-DD HH24:MI:SS'),100,10,'@M_Shipper_ID@!0','D','Y','Y','N','N','N','N','N','Materialzuordnung je Lieferweg',TO_TIMESTAMP('2026-07-23 10:03:00','YYYY-MM-DD HH24:MI:SS'),100)
;
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=781771 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;
/* DDL */ select update_FieldTranslation_From_AD_Name_Element(584112)
;
DELETE FROM AD_Element_Link WHERE AD_Field_ID=781771
;
/* DDL */ select AD_Element_Link_Create_Missing_Field(781771)
;
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Org_ID,AD_Field_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy)
VALUES (0,0,781771,186,652703 /*From ID Server*/,540499,'F',TO_TIMESTAMP('2026-07-23 10:03:30','YYYY-MM-DD HH24:MI:SS'),100,'Y','Y','Y','N','N','Materialzuordnung je Lieferweg',510,0,0,TO_TIMESTAMP('2026-07-23 10:03:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- ============================================================================
-- 3) Carrier services (multi-value) via Labels UI element on the host tab
-- ============================================================================
-- 3a) Tab caption element -----------------------------------------------------
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy)
VALUES (0,585126 /*From ID Server*/,0,TO_TIMESTAMP('2026-07-23 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Lieferweg-Services','Lieferweg-Services',TO_TIMESTAMP('2026-07-23 10:00:00','YYYY-MM-DD HH24:MI:SS'),100)
;
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=585126 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Carrier Services', PrintName='Carrier Services',Updated=TO_TIMESTAMP('2026-07-23 10:00:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=585126 AND AD_Language='en_US'
;
/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(585126,'en_US')
;

-- 3b) Hidden labels tab (TabLevel=1, no parent binding -> framework infers link via C_Order_ID) ---
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,InternalName,IsActive,IsAdvancedTab,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy)
VALUES (0,585126,0,549353 /*From ID Server*/,542628,143,'Y',TO_TIMESTAMP('2026-07-23 10:01:00','YYYY-MM-DD HH24:MI:SS'),100,'D','N','N','C_Order_Carrier_Service','Y','N','Y','N','N','N','Y','Y','N','N','Y','Y','N','N','N',0,'Lieferweg-Services','N',100,1,TO_TIMESTAMP('2026-07-23 10:01:00','YYYY-MM-DD HH24:MI:SS'),100)
;
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Tab_ID=549353 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;
/* DDL */ select update_tab_translation_from_ad_element(585126)
;

-- 3c) Selector field = the value column (Carrier_Service_ID) on the labels tab. -----------------
--      Carries DisplayLogic '@M_Shipper_ID@!0' (governs the Labels widget visibility).
--      NO AD_Val_Rule_ID -> lookup inherits the COLUMN val rule 540794 (set in Task 1).
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,DisplayLogic,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy)
VALUES (0,592987,781772 /*From ID Server*/,0,549353,TO_TIMESTAMP('2026-07-23 10:04:00','YYYY-MM-DD HH24:MI:SS'),100,10,'@M_Shipper_ID@!0','D','Y','N','N','N','N','N','N','Lieferweg-Service',TO_TIMESTAMP('2026-07-23 10:04:00','YYYY-MM-DD HH24:MI:SS'),100)
;
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=781772 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;
/* DDL */ select update_FieldTranslation_From_AD_Name_Element(584113)
;
DELETE FROM AD_Element_Link WHERE AD_Field_ID=781772
;
/* DDL */ select AD_Element_Link_Create_Missing_Field(781772)
;

-- 3d) Labels UI element on the host main tab (186), advanced edit group 540499. Type 'L'. --------
--      LayoutFactory auto-hides the backing tab (549353) because this element IsDisplayed='Y'.
--      NOTE: the Labels widget's DisplayLogic is NOT stored on this AD_UI_Element (there is no such
--      column); it is read from the SELECTOR field (781772) by
--      GridTabVOBasedDocumentEntityDescriptorFactory#extractLabelDisplayLogic. That is why the
--      '@M_Shipper_ID@!0' DisplayLogic lives on AD_Field 781772 above (evaluated in the host
--      C_Order document context), not here.
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,Labels_Selector_Field_ID,Labels_Tab_ID,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy)
VALUES (0,0,186,652704 /*From ID Server*/,540499,'L',TO_TIMESTAMP('2026-07-23 10:04:30','YYYY-MM-DD HH24:MI:SS'),100,'Y','Y','N','Y','N','N','N',781772,549353,0,'Lieferweg-Services',520,0,0,TO_TIMESTAMP('2026-07-23 10:04:30','YYYY-MM-DD HH24:MI:SS'),100)
;

SELECT add_missing_translations()
;
