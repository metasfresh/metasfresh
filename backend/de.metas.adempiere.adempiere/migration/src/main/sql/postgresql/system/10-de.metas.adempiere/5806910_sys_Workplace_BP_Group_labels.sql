-- Workplace BPartner-Group Labels wiring (me03 #30326)
-- Adds the hidden labels tab + selector field + Labels UI element for C_Workplace_BP_Group
-- on the Workplace window (AD_Window 541744 / main tab AD_Tab 547260, right UI column 547140).
-- The Labels AD_UI_Element (IsDisplayed='Y') makes LayoutFactory auto-hide the backing tab.
-- Cloned from CS_Creditpass_BP_Group labels wiring (tab 541681 / field 578040 / ui-element 558013).
-- IDs allocated from idserver.metas.de:
--   AD_Tab 549293, AD_Element 584963 (tab caption), AD_Field 780737,
--   AD_UI_ElementGroup 555431 ("restrictions"), AD_UI_Element 652032.
-- Reused: value column 592759 (C_Workplace_BP_Group.C_BP_Group_ID), element 1383 (C_BP_Group_ID).

-- Tab caption element --------------------------------------------------------
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584963 /*From ID Server*/,0,TO_TIMESTAMP('2026-06-09 09:20:00','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Geschäftspartnergruppen','Geschäftspartnergruppen',TO_TIMESTAMP('2026-06-09 09:20:00','YYYY-MM-DD HH24:MI:SS'),100)
;
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584963 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='BPartner Groups', PrintName='BPartner Groups',Updated=TO_TIMESTAMP('2026-06-09 09:20:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=584963 AND AD_Language='en_US'
;
/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(584963,'en_US')
;

-- Labels tab (TabLevel=1, no parent binding -> framework infers link via host key column) -----
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,InternalName,IsActive,IsAdvancedTab,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,584963,0,549293 /*From ID Server*/,542615,541744,'Y',TO_TIMESTAMP('2026-06-09 09:20:02','YYYY-MM-DD HH24:MI:SS'),100,'D','N','N','C_Workplace_BP_Group','Y','N','Y','N','N','N','Y','Y','N','N','Y','Y','N','N','N',0,'Geschäftspartnergruppen','N',100,1,TO_TIMESTAMP('2026-06-09 09:20:02','YYYY-MM-DD HH24:MI:SS'),100)
;
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Tab_ID=549293 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;
/* DDL */ select update_tab_translation_from_ad_element(584963)
;

-- Selector field (the value column on the labels tab) ------------------------
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,592759,780737 /*From ID Server*/,0,549293,TO_TIMESTAMP('2026-06-09 09:20:03','YYYY-MM-DD HH24:MI:SS'),100,'Geschäftspartnergruppe',10,'D','Eine Geschäftspartner-Gruppe bietet Ihnen die Möglichkeit, Standard-Werte für einzelne Geschäftspartner zu verwenden.','Y','N','N','N','N','N','N','Geschäftspartnergruppe',TO_TIMESTAMP('2026-06-09 09:20:03','YYYY-MM-DD HH24:MI:SS'),100)
;
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=780737 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;
-- Mandatory AD_Field post-INSERT sequence (value column's element is 1383).
/* DDL */ select update_FieldTranslation_From_AD_Name_Element(1383)
;
DELETE FROM AD_Element_Link WHERE AD_Field_ID=780737
;
/* DDL */ select AD_Element_Link_Create_Missing_Field(780737)
;

-- New element group "restrictions" in the right UI column (SeqNo 40, after flags/orgs/limits) --
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547140,555431 /*From ID Server*/,TO_TIMESTAMP('2026-06-09 09:20:04','YYYY-MM-DD HH24:MI:SS'),100,'Y','restrictions',40,TO_TIMESTAMP('2026-06-09 09:20:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Labels UI element on the host main tab (547260). Type 'L', IsDisplayed='Y'. ----------------
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,Labels_Selector_Field_ID,Labels_Tab_ID,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,0,547260,652032 /*From ID Server*/,555431,'L',TO_TIMESTAMP('2026-06-09 09:20:05','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',780737,549293,0,'Geschäftspartnergruppen',10,0,0,TO_TIMESTAMP('2026-06-09 09:20:05','YYYY-MM-DD HH24:MI:SS'),100)
;

SELECT add_missing_translations()
;
