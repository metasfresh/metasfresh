-- Surface M_ShipperTransportation.M_Delivery_Planning_Type (AD_Column 593410) in the UI.
--
-- The column was added by 5820430 with no AD_Field and no AD_UI_Element, so the three-valued
-- direction (Incoming / Outgoing / Dropship) it holds is invisible. It replaces the IsSOTrx flag
-- on both windows over M_ShipperTransportation, so it is wired into both main tabs:
--   AD_Window 540020 "Transport Auftrag" -> AD_Tab 540096 "Speditionslieferung"
--   AD_Window 541657 "Lieferanweisungen" -> AD_Tab 546732 "Lieferanweisungen"
-- Neither window has an override window (AD_Window.Overrides_Window_ID), so there is no
-- companion customer-repo script.
--
-- Placement and editability differ per window, because the field's ROLE differs per window.
--
--   AD_Window 540020 / AD_Tab 540096 "Speditionslieferung" -- IsInsertRecord='Y', so this is the
--   one window that offers a 'New' action. The column is MANDATORY and 5821080 removes its default
--   on purpose, so that a missing direction surfaces instead of being silently filled; on a new
--   record the user therefore has to supply it, and IsReadOnly='Y' would leave a required field
--   that cannot be filled and a record that can never be saved. So: IsReadOnly='N' here, and the
--   element sits FIRST in the left column's UIStyle='primary' group 540667 -- a mandatory,
--   user-entered field belongs top-left, not among the right-column "flags" whose other members
--   (IsBookingConfirmed, IsBLReceived, IsWENotice) are all booleans.
--
--   AD_Window 541657 / AD_Tab 546732 "Lieferanweisungen" -- IsInsertRecord='N': this window has no
--   'New' action at all, so no user ever has to supply a direction here. It stays IsReadOnly='Y',
--   like IsSOTrx and like the Delivery Planning sibling, and it stays where IsSOTrx sat: first in
--   the right-column "flags" group 555562 (SeqNo 12), so dropping the IsSOTrx element in 5820850
--   leaves no hole. Read-only is what protects the invariant here: the direction of a delivery
--   instruction follows the plannings allocated to it, and the combine-time admissibility check
--   already guarantees that all of them share one direction -- a hand edit could only break that,
--   silently, through Complete and onto the printed document.
--
-- Nothing is lost by making 540020's copy editable: every programmatic creation path sets the
-- direction in Java, which an AD_Field flag never affected either way, and Document#computeReadonly
-- propagates Processed generically, so the field locks once the document is processed, exactly like
-- every other field on these tabs (none of which carries an explicit ReadOnlyLogic either).
--
-- The right-column "flags" placement kept on 546732 is also what M_Delivery_Planning.
-- M_Delivery_Planning_Type (AD_Column 585005) uses on the Delivery Planning window, where the
-- direction is likewise derived rather than typed.
--
-- Label: AD_Field.AD_Name_ID is set to AD_Element 540579 ("Richtung" / "Direction"), an existing
-- fully-translated core element, instead of inheriting the column's shared element 581679
-- ("Lieferplanung Art" / "Type"). That element names the *delivery planning* and is wrong on a
-- transport order, which has no delivery planning at all; element 581679 is shared with
-- AD_Column 585005 and must therefore not be mutated (field-level override is the correct fork).
--
-- Grid + filter: IsSOTrx is AD_Column.IsSelectionColumn='Y' (SelectionColumnSeqNo=170) today, so
-- the transport grids can be narrowed by direction. Dropping IsSOTrx without transferring that
-- would remove a filter users have. The direction column therefore becomes a selection column
-- too (SelectionColumnSeqNo=175, i.e. the same neighbourhood), which also matches AD_Column
-- 585005 on the Delivery Planning side. Because a selection column must be visible in the grid,
-- both AD_UI_Elements are IsDisplayedGrid='Y' (SeqNoGrid chosen collision-free on both the
-- AD_UI_Element and the AD_Field layer, on each tab).

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
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,783020,0,540096,540667,653669 /*From ID Server*/,'F',TO_TIMESTAMP('2026-08-26 12:02:00','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','Y','N','N',0,'Richtung',5,65,0,TO_TIMESTAMP('2026-08-26 12:02:00','YYYY-MM-DD HH24:MI:SS'),100)
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
