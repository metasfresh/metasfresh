-- Workplace Document-Type Labels wiring (me03 #30326)
-- Adds the hidden labels tab + selector field + Labels UI element for C_Workplace_DocType
-- on the Workplace window (AD_Window 541744 / main tab AD_Tab 547260, right UI column 547140).
-- The labels tab WhereClause restricts the picker to order doctypes (C_DocType.DocBaseType='SOO').
-- The Labels AD_UI_Element (IsDisplayed='Y') makes LayoutFactory auto-hide the backing tab.
-- Re-uses the "restrictions" element group 555431 created by script 5806910.
-- IDs allocated from idserver.metas.de:
--   AD_Tab 549294, AD_Element 584964 (tab caption), AD_Field 780738, AD_UI_Element 652033.
-- Reused: value column 592769 (C_Workplace_DocType.C_DocType_ID), element 196 (C_DocType_ID).

-- Tab caption element --------------------------------------------------------
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584964 /*From ID Server*/,0,TO_TIMESTAMP('2026-06-09 09:30:00','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Belegarten','Belegarten',TO_TIMESTAMP('2026-06-09 09:30:00','YYYY-MM-DD HH24:MI:SS'),100)
;
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584964 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Document Types', PrintName='Document Types',Updated=TO_TIMESTAMP('2026-06-09 09:30:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=584964 AND AD_Language='en_US'
;
/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(584964,'en_US')
;

-- Labels tab (TabLevel=1; WhereClause filters the picker to order doctypes) ------------------
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,InternalName,IsActive,IsAdvancedTab,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy,WhereClause) VALUES (0,584964,0,549294 /*From ID Server*/,542616,541744,'Y',TO_TIMESTAMP('2026-06-09 09:30:02','YYYY-MM-DD HH24:MI:SS'),100,'D','N','N','C_Workplace_DocType','Y','N','Y','N','N','N','Y','Y','N','N','Y','Y','N','N','N',0,'Belegarten','N',110,1,TO_TIMESTAMP('2026-06-09 09:30:02','YYYY-MM-DD HH24:MI:SS'),100,'C_DocType.DocBaseType=''SOO''')
;
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Tab_ID=549294 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;
/* DDL */ select update_tab_translation_from_ad_element(584964)
;

-- Selector field (the value column on the labels tab) ------------------------
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,592769,780738 /*From ID Server*/,0,549294,TO_TIMESTAMP('2026-06-09 09:30:03','YYYY-MM-DD HH24:MI:SS'),100,'Belegart oder Regeln',10,'D','Die Belegart bestimmt die Belegfolge und Verarbeitungsregeln.','Y','N','N','N','N','N','N','Belegart',TO_TIMESTAMP('2026-06-09 09:30:03','YYYY-MM-DD HH24:MI:SS'),100)
;
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=780738 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;
-- Mandatory AD_Field post-INSERT sequence (value column's element is 196).
/* DDL */ select update_FieldTranslation_From_AD_Name_Element(196)
;
DELETE FROM AD_Element_Link WHERE AD_Field_ID=780738
;
/* DDL */ select AD_Element_Link_Create_Missing_Field(780738)
;

-- Labels UI element on the host main tab (547260), in the "restrictions" group 555431. -------
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,Labels_Selector_Field_ID,Labels_Tab_ID,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,0,547260,652033 /*From ID Server*/,555431,'L',TO_TIMESTAMP('2026-06-09 09:30:04','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',780738,549294,0,'Belegarten',20,0,0,TO_TIMESTAMP('2026-06-09 09:30:04','YYYY-MM-DD HH24:MI:SS'),100)
;

SELECT add_missing_translations()
;
