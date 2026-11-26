-- Surface M_ShipperTransportation's DeliveredState (AD_Column 593468, added by 5822160) on both
-- windows over the table -- AD_Tab 546732 (AD_Window 541657 "Lieferanweisungen") and AD_Tab 540096
-- (AD_Window 540020 "Transport Auftrag"). 5822160 created the column and its element but never
-- wired an AD_Field/AD_UI_Element, so the three-state delivered indicator (Not/Partly/Fully
-- delivered) it computes was invisible everywhere.
--
-- Placed in the "rest" group, immediately before DocStatus -- the group where each window already
-- shows the document's own status, and the group a dispatcher already scans for state. Read-only:
-- the value is derived, not user-entered.

-- ============================================================================
-- 1) AD_Window 541657 / AD_Tab 546732 "Lieferanweisungen" -- group "rest" (550202)
-- ============================================================================
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SortNo,Updated,UpdatedBy)
VALUES (0,593468,784921 /*From ID Server*/,0,546732,TO_TIMESTAMP('2026-09-03 11:00:00','YYYY-MM-DD HH24:MI:SS'),100,0,'D','Y','Y','Y','N','N','N','Y','N','Zustellstatus',0,TO_TIMESTAMP('2026-09-03 11:00:00','YYYY-MM-DD HH24:MI:SS'),100)
;
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=784921 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;
/* DDL */ select update_FieldTranslation_From_AD_Name_Element(585421)
;
DELETE FROM AD_Element_Link WHERE AD_Field_ID=784921
;
/* DDL */ select AD_Element_Link_Create_Missing_Field(784921)
;
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,784921,0,546732,550202,654692 /*From ID Server*/,'F',TO_TIMESTAMP('2026-09-03 11:01:00','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','Y','N','N',0,'Zustellstatus',5,100,0,TO_TIMESTAMP('2026-09-03 11:01:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- ============================================================================
-- 2) AD_Window 540020 / AD_Tab 540096 "Speditionslieferung" -- group "rest" (541697)
-- ============================================================================
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SortNo,Updated,UpdatedBy)
VALUES (0,593468,784922 /*From ID Server*/,0,540096,TO_TIMESTAMP('2026-09-03 11:02:00','YYYY-MM-DD HH24:MI:SS'),100,0,'D','Y','Y','Y','N','N','N','Y','N','Zustellstatus',0,TO_TIMESTAMP('2026-09-03 11:02:00','YYYY-MM-DD HH24:MI:SS'),100)
;
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=784922 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;
/* DDL */ select update_FieldTranslation_From_AD_Name_Element(585421)
;
DELETE FROM AD_Element_Link WHERE AD_Field_ID=784922
;
/* DDL */ select AD_Element_Link_Create_Missing_Field(784922)
;
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,784922,0,540096,541697,654693 /*From ID Server*/,'F',TO_TIMESTAMP('2026-09-03 11:03:00','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','Y','N','N',0,'Zustellstatus',5,255,0,TO_TIMESTAMP('2026-09-03 11:03:00','YYYY-MM-DD HH24:MI:SS'),100)
;
