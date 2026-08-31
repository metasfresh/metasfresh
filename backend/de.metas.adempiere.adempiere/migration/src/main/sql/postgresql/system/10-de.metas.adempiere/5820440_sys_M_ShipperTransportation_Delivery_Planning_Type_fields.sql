-- Surface M_ShipperTransportation's three-valued transport direction (AD_Column 593410) on both
-- windows over the table -- AD_Tab 540096 (AD_Window 540020 "Transport Auftrag") and AD_Tab 546732
-- (AD_Window 541657 "Lieferanweisungen") -- replacing the IsSOTrx flag, and take over IsSOTrx's
-- filter slot on AD_Column.
--
-- IsReadOnly differs by window: 540020 is the only one with IsInsertRecord='Y' and the column is
-- mandatory without a default, so a read-only field there would leave a required field nobody can
-- fill; 541657 offers no 'New' action and stays read-only.
--
-- An Overrides_Window_ID code search is a floor, not a ceiling: it cannot prove a customer override
-- window absent, only present. A negative result is an open risk, not a cleared one.

-- ============================================================================
-- 1) Make the direction filterable, taking over the IsSOTrx filter slot.
-- ============================================================================
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=175, Updated=TO_TIMESTAMP('2026-08-26 12:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Column_ID=593410
;

-- ============================================================================
-- 2) AD_Window 540020 / AD_Tab 540096 "Speditionslieferung"
-- ============================================================================
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,Updated,UpdatedBy) VALUES (0,593410,783020 /*From ID Server*/,0,540096,TO_TIMESTAMP('2026-08-26 12:01:00','YYYY-MM-DD HH24:MI:SS'),100,0,'D','Y','Y','Y','N','N','N','N','N','Lieferplanung Art',0,205,0,TO_TIMESTAMP('2026-08-26 12:01:00','YYYY-MM-DD HH24:MI:SS'),100)
;
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=783020 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;
/* DDL */ select update_FieldTranslation_From_AD_Name_Element(581679)
;
DELETE FROM AD_Element_Link WHERE AD_Field_ID=783020
;
/* DDL */ select AD_Element_Link_Create_Missing_Field(783020)
;
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,783020,0,540096,540667,653669 /*From ID Server*/,'F',TO_TIMESTAMP('2026-08-26 12:02:00','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','Y','N','N',0,'Richtung',5,35,0,TO_TIMESTAMP('2026-08-26 12:02:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- ============================================================================
-- 3) AD_Window 541657 / AD_Tab 546732 "Lieferanweisungen"
-- ============================================================================
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,Updated,UpdatedBy) VALUES (0,593410,783021 /*From ID Server*/,0,546732,TO_TIMESTAMP('2026-08-26 12:03:00','YYYY-MM-DD HH24:MI:SS'),100,0,'D','Y','Y','Y','N','N','N','Y','N','Lieferplanung Art',185,200,0,TO_TIMESTAMP('2026-08-26 12:03:00','YYYY-MM-DD HH24:MI:SS'),100)
;
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=783021 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;
/* DDL */ select update_FieldTranslation_From_AD_Name_Element(581679)
;
DELETE FROM AD_Element_Link WHERE AD_Field_ID=783021
;
/* DDL */ select AD_Element_Link_Create_Missing_Field(783021)
;
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,783021,0,546732,555562,653670 /*From ID Server*/,'F',TO_TIMESTAMP('2026-08-26 12:04:00','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','Y','N','N',0,'Richtung',12,35,0,TO_TIMESTAMP('2026-08-26 12:04:00','YYYY-MM-DD HH24:MI:SS'),100)
;
