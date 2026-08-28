-- Surface M_ShipperTransportation's three-valued direction (AD_Column 593410, added by 5820430 with
-- no AD_Field and no AD_UI_Element) on both windows over the table, replacing the IsSOTrx flag:
--   AD_Window 540020 "Transport Auftrag"  -> AD_Tab 540096 "Speditionslieferung"
--   AD_Window 541657 "Lieferanweisungen"  -> AD_Tab 546732 "Lieferanweisungen"
--
-- 540020 IS overridden -- two customer windows set Overrides_Window_ID=540020, each in its own repo,
-- and each gets a companion script in its own repo putting the field on its own tab; 541657 is not.
-- Both settled by an org-wide code search for Overrides_Window_ID=540020 / =541657, NOT by a local
-- DB: a local DB holds no Overrides_Window_ID rows at all, so it always answers "no override".
--
-- Editability differs per window because the role does. 540020 is the only one with
-- IsInsertRecord='Y', and the column is mandatory with its default deliberately removed by 5821080,
-- so IsReadOnly='Y' there would leave a required field nobody can fill and a record nobody can save.
-- Hence IsReadOnly='N', first in the left column's UIStyle='primary' group 540667. 541657 offers no
-- 'New' action, so it stays IsReadOnly='Y' and takes the slot IsSOTrx had: second in the
-- right-column "flags" group 555562, after IsActive and before IsBookingConfirmed. Read-only there
-- guards the invariant that an instruction's direction follows its allocated plannings, which the
-- combine-time admissibility check already keeps uniform; both copies lock once the document is
-- processed anyway, so neither needs a ReadOnlyLogic. AD_Name_ID=540579 ("Richtung") is a stop-gap
-- caption because the column's own element is still misnamed; 5820620 drops it again.
--
-- Filter + grid: IsSOTrx carries the direction filter today (SelectionColumnSeqNo=170) and 5820850
-- drops it, so the filter moves onto the direction column (175, matching AD_Column 585005 on the
-- Delivery Planning side). A selection column has to be visible in the grid, hence
-- IsDisplayedGrid='Y', at a SeqNoGrid free on both the AD_UI_Element and the AD_Field layer of
-- its tab -- on 540096 that is 35, keeping the C_DocType_ID/DocumentNo/DateDoc block contiguous.

-- ============================================================================
-- 1) Make the direction filterable, taking over the IsSOTrx filter slot.
-- ============================================================================
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=175, Updated=TO_TIMESTAMP('2026-08-26 12:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Column_ID=593410
;

-- ============================================================================
-- 2) AD_Window 540020 / AD_Tab 540096 "Speditionslieferung"
-- ============================================================================
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,Updated,UpdatedBy) VALUES (0,593410,783020 /*From ID Server*/,540579,0,540096,TO_TIMESTAMP('2026-08-26 12:01:00','YYYY-MM-DD HH24:MI:SS'),100,0,'D','Y','Y','Y','N','N','N','N','N','Richtung',0,205,0,TO_TIMESTAMP('2026-08-26 12:01:00','YYYY-MM-DD HH24:MI:SS'),100)
;
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=783020 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;
/* DDL */ select update_FieldTranslation_From_AD_Name_Element(540579)
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
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,Updated,UpdatedBy) VALUES (0,593410,783021 /*From ID Server*/,540579,0,546732,TO_TIMESTAMP('2026-08-26 12:03:00','YYYY-MM-DD HH24:MI:SS'),100,0,'D','Y','Y','Y','N','N','N','Y','N','Richtung',185,200,0,TO_TIMESTAMP('2026-08-26 12:03:00','YYYY-MM-DD HH24:MI:SS'),100)
;
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=783021 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;
/* DDL */ select update_FieldTranslation_From_AD_Name_Element(540579)
;
DELETE FROM AD_Element_Link WHERE AD_Field_ID=783021
;
/* DDL */ select AD_Element_Link_Create_Missing_Field(783021)
;
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,783021,0,546732,555562,653670 /*From ID Server*/,'F',TO_TIMESTAMP('2026-08-26 12:04:00','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','Y','N','N',0,'Richtung',12,55,0,TO_TIMESTAMP('2026-08-26 12:04:00','YYYY-MM-DD HH24:MI:SS'),100)
;
