-- M_Delivery_Planning: put the four filter columns from 5821150 into the rendered layout of
-- AD_Tab 546674 (AD_Window 541632).
--
-- 5821150 made IsAllocated, IsDelivered, ShipFrom_Location_ID and ShipTo_Location_ID
-- AD_Column.IsSelectionColumn='Y' but gave them no AD_Field and no AD_UI_Element, so the planner
-- gets a filter widget for each one and no column showing what it filtered on. IsDelivered is the
-- sharp case: it also carries FilterDefaultValue='N', so the window opens pre-filtered with nothing
-- on screen explaining the rows that are missing. A filter column has to be visible in the grid.
--
-- Each column gets an AD_Field plus an AD_UI_Element, so it renders in BOTH surfaces: the
-- single-record form and the grid.
--
-- Form placement:
--   IsAllocated, IsDelivered   -> the existing 'flags' group 550029, after IsClosed and before
--                                TransportDirection, in lifecycle order (allocated, then delivered).
--   ShipFrom_/ShipTo_Location_ID -> a new 'address' group in the left UI column, at SeqNo 15 between
--                                'default' (10) and 'links' (20). This mirrors the delivery
--                                instruction tab 546732, which holds its Verladeadresse and
--                                Lieferadresse in an 'address' group at the same SeqNo 15 of its own
--                                left column. Addresses are not flags and none of the other existing
--                                groups (dates, links, qtys, Receipt/Shipment, org) fits them.
--
-- Grid placement (AD_UI_Element.SeqNoGrid; free slots, no collision on this tab):
--   IsDelivered 55           -- next to IsClosed 50, with the other lifecycle flags
--   ShipFrom 112, ShipTo 114 -- as a pair right after ShipToLocation_Name 110
--   IsAllocated 215          -- immediately before M_ShipperTransportation_ID 220, the transport
--                               order it reports on
-- AD_Org_ID keeps SeqNoGrid 390 and stays last in the grid.
--
-- AD_Field.SeqNo/SeqNoGrid stay 0, matching every other field of this tab: 546674 is section-backed,
-- so the WebUI orders the grid from AD_UI_Element and the AD_Field layer carries no ordering here.
-- IsReadOnly='Y' on all four -- they are ColumnSQL columns with IsUpdateable='N'.
--
-- Field captions come from the backing AD_Element via update_FieldTranslation_From_AD_Name_Element,
-- so de_DE, de_CH and en_US all carry the element's own IsTranslated flag. For IsAllocated,
-- ShipFrom_Location_ID and ShipTo_Location_ID that is 'Y' on all three; fr_CH stays 'N'.
-- IsDelivered hangs off the shared core element 367, which is 'N' on de_DE/de_CH system-wide and is
-- also used by C_Order, RV_C_OrderLine_Overview and DD_OrderLine fields, so it is not this window's
-- flag to assert. 5821150 accepted the same inherited 'N' on AD_Column_Trl 593413; the field layer
-- matches the column layer. Overriding it here would not survive anyway: after_migration's
-- sync_translations re-syncs every AD_Field_Trl from its element at the end of the run.
--
-- IDs allocated from idserver.metas.de on 2026-09-01:
--   AD_MigrationScript    5821440 (this file)
--   AD_UI_ElementGroup    555641 (address)
--   AD_Field              783046 (IsAllocated), 783047 (IsDelivered),
--                         783048 (ShipFrom_Location_ID), 783049 (ShipTo_Location_ID)
--   AD_UI_Element         653689, 653690, 653691, 653692

-- ============================================================================
-- 1) AD_Field: M_Delivery_Planning.IsAllocated (AD_Column 593412, AD_Element 585384)
-- ============================================================================
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,Updated,UpdatedBy) VALUES (0,593412,783046 /*From ID Server*/,0,546674,TO_TIMESTAMP('2026-09-01 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','Y','Y','N','N','N','Y','N','Zugeordnet',0,0,TO_TIMESTAMP('2026-09-01 10:00:00','YYYY-MM-DD HH24:MI:SS'),100)
;
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=783046 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;
SELECT AD_Element_Link_Create_Missing_Field(783046);

-- ============================================================================
-- 2) AD_Field: M_Delivery_Planning.IsDelivered (AD_Column 593413, AD_Element 367)
-- ============================================================================
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,Updated,UpdatedBy) VALUES (0,593413,783047 /*From ID Server*/,0,546674,TO_TIMESTAMP('2026-09-01 10:00:01','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','Y','Y','N','N','N','Y','N','Zugestellt',0,0,TO_TIMESTAMP('2026-09-01 10:00:01','YYYY-MM-DD HH24:MI:SS'),100)
;
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=783047 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;
SELECT AD_Element_Link_Create_Missing_Field(783047);

-- ============================================================================
-- 3) AD_Field: M_Delivery_Planning.ShipFrom_Location_ID (AD_Column 593414, AD_Element 585385)
-- ============================================================================
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,Updated,UpdatedBy) VALUES (0,593414,783048 /*From ID Server*/,0,546674,TO_TIMESTAMP('2026-09-01 10:00:02','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','Y','Y','N','N','N','Y','N','Verladeadresse',0,0,TO_TIMESTAMP('2026-09-01 10:00:02','YYYY-MM-DD HH24:MI:SS'),100)
;
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=783048 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;
SELECT AD_Element_Link_Create_Missing_Field(783048);

-- ============================================================================
-- 4) AD_Field: M_Delivery_Planning.ShipTo_Location_ID (AD_Column 593415, AD_Element 585386)
-- ============================================================================
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,Updated,UpdatedBy) VALUES (0,593415,783049 /*From ID Server*/,0,546674,TO_TIMESTAMP('2026-09-01 10:00:03','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','Y','Y','N','N','N','Y','N','Lieferadresse',0,0,TO_TIMESTAMP('2026-09-01 10:00:03','YYYY-MM-DD HH24:MI:SS'),100)
;
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=783049 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;
SELECT AD_Element_Link_Create_Missing_Field(783049);

-- ============================================================================
-- 5) Caption + description per language, from the backing AD_Element
-- ============================================================================
/* DDL */ select update_FieldTranslation_From_AD_Name_Element(585384,'de_DE')
;
/* DDL */ select update_FieldTranslation_From_AD_Name_Element(585384,'de_CH')
;
/* DDL */ select update_FieldTranslation_From_AD_Name_Element(585384,'en_US')
;
/* DDL */ select update_FieldTranslation_From_AD_Name_Element(585385,'de_DE')
;
/* DDL */ select update_FieldTranslation_From_AD_Name_Element(585385,'de_CH')
;
/* DDL */ select update_FieldTranslation_From_AD_Name_Element(585385,'en_US')
;
/* DDL */ select update_FieldTranslation_From_AD_Name_Element(585386,'de_DE')
;
/* DDL */ select update_FieldTranslation_From_AD_Name_Element(585386,'de_CH')
;
/* DDL */ select update_FieldTranslation_From_AD_Name_Element(585386,'en_US')
;
/* DDL */ select update_FieldTranslation_From_AD_Name_Element(367,'de_DE')
;
/* DDL */ select update_FieldTranslation_From_AD_Name_Element(367,'de_CH')
;
/* DDL */ select update_FieldTranslation_From_AD_Name_Element(367,'en_US')
;

-- ============================================================================
-- 6) 'address' element group, left UI column 546454, between 'default' (10) and 'links' (20)
-- ============================================================================
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,546454,555641 /*From ID Server*/,TO_TIMESTAMP('2026-09-01 10:01:00','YYYY-MM-DD HH24:MI:SS'),100,'Y','address',15,NULL,TO_TIMESTAMP('2026-09-01 10:01:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- ============================================================================
-- 7) AD_UI_Element: form position + grid column for each of the four
-- ============================================================================

-- flags group 550029: IsActive 10, Processed 20, IsClosed 30, [40 Zugeordnet, 45 Zugestellt], TransportDirection 50
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,783046,0,546674,550029,653689 /*From ID Server*/,'F',TO_TIMESTAMP('2026-09-01 10:02:00','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Zugeordnet',40,215,0,TO_TIMESTAMP('2026-09-01 10:02:00','YYYY-MM-DD HH24:MI:SS'),100)
;
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,783047,0,546674,550029,653690 /*From ID Server*/,'F',TO_TIMESTAMP('2026-09-01 10:02:01','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Zugestellt',45,55,0,TO_TIMESTAMP('2026-09-01 10:02:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- address group 555641: Verladeadresse 10, Lieferadresse 20 -- same order as tab 546732
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,783048,0,546674,555641,653691 /*From ID Server*/,'F',TO_TIMESTAMP('2026-09-01 10:02:02','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Verladeadresse',10,112,0,TO_TIMESTAMP('2026-09-01 10:02:02','YYYY-MM-DD HH24:MI:SS'),100)
;
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,783049,0,546674,555641,653692 /*From ID Server*/,'F',TO_TIMESTAMP('2026-09-01 10:02:03','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Lieferadresse',20,114,0,TO_TIMESTAMP('2026-09-01 10:02:03','YYYY-MM-DD HH24:MI:SS'),100)
;
