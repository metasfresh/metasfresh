-- AD metadata for the QtyToMove column 5823590 adds to RV_ReceiptDisposition_DeliveryPlanning. The view body
-- and both owner rulings behind it are in 5823590.
--
-- NO NEW ELEMENT AND NO TRANSLATION AUTHORING. AD_Element 542204 ('QtyToMove') already exists and already
-- carries de_DE / de_CH "Menge zu bewegen" and en_US "Quantity to move"; fr_CH carries the en_US text with
-- IsTranslated='N', the convention this change set states in 5820520. So every caption below is PROPAGATED
-- from the element, never typed.
--
-- The element is SHARED and this script does not mutate it - it only points one more record at it. Its two
-- existing users are AD_Columns literally named QtyToMove (M_ReceiptSchedule 549517, QtyDemand_QtySupply_V
-- 584382), so the propagation call is value-preserving for both. Note 5823320 pointed AD_Column 593501 AWAY
-- from this element when it renamed that column to PlannedDischargeQuantity; the element itself was left
-- untouched then and is left untouched now.
--
-- IDs allocated from idserver.metas.de on 2026-09-09:
--   AD_Column     593536 (RV_ReceiptDisposition_DeliveryPlanning.QtyToMove)
--   AD_Field      784968
--   AD_UI_Element 654734
--
-- Placement mirrors AD_Column 593501 / AD_Field 784950 / AD_UI_Element 654716 exactly (Quantity reference 29,
-- field length 14, read-only field) and puts the figure FIRST of the three discharge quantities, so the grid
-- reads ordered (SeqNoGrid 50), to-move (56), planned (57), actual (58); the form reads 70, 71, 72, 73.
--
-- Deliberately NOT a selection column: 5822680 configured this window's filter set and adding to it is a
-- separate decision, not part of making the figure visible.

INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsTranslated,IsUpdateable,Name,PersonalDataCategory,SelectionColumnSeqNo,Updated,UpdatedBy,Version)
VALUES (0,593536 /*From ID Server*/,542204,0,29,NULL,542644,'QtyToMove',TO_TIMESTAMP('2026-09-09 21:00:00','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,NULL,'D',14,NULL,'Y','N','N','N','N','N','N','N','N','N','N','N','Menge zu bewegen','NP',0,TO_TIMESTAMP('2026-09-09 21:00:00','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=593536 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;
/* DDL */ select update_Column_Translation_From_AD_Element(542204)
;

INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy)
VALUES (0,593536,784968 /*From ID Server*/,0,549491,TO_TIMESTAMP('2026-09-09 21:01:00','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,NULL,14,'D',NULL,'Y','N','N','N','N','N','Y','N','Menge zu bewegen',TO_TIMESTAMP('2026-09-09 21:01:00','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100)
;
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=784968 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;
/* DDL */ select update_FieldTranslation_From_AD_Name_Element(542204)
;
/* DDL */ select AD_Element_Link_Create_Missing_Field(784968)
;

-- UI placement: same "main" group as the other two discharge figures, immediately before the planned one in
-- form and grid.
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy)
VALUES (0,784968,0,549491,555764,654734 /*From ID Server*/,'F',TO_TIMESTAMP('2026-09-09 21:02:00','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,'Menge zu bewegen',NULL,'Y','N','N','Y','Y','N','N',0,'Menge zu bewegen',71,56,0,TO_TIMESTAMP('2026-09-09 21:02:00','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100)
;
