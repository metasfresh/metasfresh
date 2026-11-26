-- AD metadata for the two quantity columns 5823310 leaves on RV_ReceiptDisposition_DeliveryPlanning:
-- PlannedDischargeQuantity (the renamed QtyToMove) and the new ActualDischargeQuantity. Two owner rulings
-- (2026-09-09); the view body and the reasoning behind both are in 5823310.
--
-- Ruling A - AD_Column 593501 is RENAMED, not replaced: same column record, same AD_Field (784950), same
-- AD_UI_Element (654716), so its placement, its grid position and the filter configuration 5822680 gave it all
-- survive untouched. What changes is the name and what names it: ColumnName QtyToMove -> PlannedDischargeQuantity
-- and AD_Element 542204 ("Menge zu bewegen", M_ReceiptSchedule's term) -> 581795 ("Geplante Entlademenge", the
-- planning's). Precedent for the shape: 5819390 step 4b renames four view AD_Columns onto different elements the
-- same way.
--
-- Ruling B - ActualDischargeQuantity is a new AD_Column (593530) + AD_Field (784963) + AD_UI_Element (654729),
-- mirroring 593501 / 784950 / 654716 exactly (Quantity reference 29, field length 14, read-only field) and sitting
-- immediately after the planned figure in both the form (SeqNo 73, after 72) and the grid (SeqNoGrid 58, after 57).
--
-- NO NEW ELEMENT AND NO TRANSLATION AUTHORING. Both elements already exist and already carry correct de_DE, de_CH
-- and en_US rows - "Geplante Entlademenge" / "Geplante Entlademenge" / "Planned Discharge Quantity" on 581795, and
-- "Tatsaechliche Entlademenge" / "Tatsaechliche Entlademenge" / "Actual Discharge Quantity" on 581796 (umlauts as
-- written in the data below; this comment stays ASCII). fr_CH carries the en_US text with IsTranslated='N', the
-- convention this change set states in 5820520 and follows in 5822780. So every caption here is PROPAGATED from
-- the element, never typed - which is also why the language-swap defect this branch shipped once before cannot
-- recur through this script.
--
-- Both elements are shared, and this script does not mutate either - it only points more records at them. Their
-- nine existing users are all AD_Columns literally named PlannedDischargeQuantity / ActualDischargeQuantity
-- (M_Delivery_Planning 585216/585217, M_ShippingPackage 593471/585498, I_DeliveryPlanning 585800/585801, the three
-- delivery-instruction / history views 585668/593424/585513), so the propagation calls below are value-preserving
-- for every one of them.
--
-- ActualDischargeQuantity is deliberately NOT a selection column: 5822680 configured the filter set for this
-- window and adding to it is a separate decision, not part of making the actual figure visible.

-- ============================================================================
-- Ruling A: rename AD_Column 593501 QtyToMove -> PlannedDischargeQuantity, onto element 581795
-- ============================================================================
UPDATE AD_Column SET ColumnName='PlannedDischargeQuantity', AD_Element_ID=581795, Updated=TO_TIMESTAMP('2026-09-09 12:00:00','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE AD_Column_ID=593501
;

-- AD_Column.Name + AD_Column_Trl (all four active system languages) from element 581795
/* DDL */ select update_Column_Translation_From_AD_Element(581795)
;

-- AD_Field 784950 keeps its AD_Column but now inherits the planning's caption (its AD_Name_ID is NULL, so the
-- via-AD_Column path applies)
/* DDL */ select update_FieldTranslation_From_AD_Name_Element(581795)
;

-- The element link still names 542204; rebuild it from the field's current column
DELETE FROM AD_Element_Link WHERE AD_Field_ID=784950
;
/* DDL */ select AD_Element_Link_Create_Missing_Field(784950)
;

-- AD_UI_Element has no _Trl and no propagation function; its Name/Description are copied from the field by the
-- ORM callout, which raw SQL bypasses - so set them here, matching what 5822670 wrote for this row.
UPDATE AD_UI_Element SET Name='Geplante Entlademenge', Description='Geplante Entlademenge', Updated=TO_TIMESTAMP('2026-09-09 12:00:30','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE AD_UI_Element_ID=654716
;

-- ============================================================================
-- Ruling B: new column RV_ReceiptDisposition_DeliveryPlanning.ActualDischargeQuantity
-- ============================================================================
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsTranslated,IsUpdateable,Name,PersonalDataCategory,SelectionColumnSeqNo,Updated,UpdatedBy,Version)
VALUES (0,593530 /*From ID Server*/,581796,0,29,NULL,542644,'ActualDischargeQuantity',TO_TIMESTAMP('2026-09-09 12:01:00','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,NULL,'D',14,NULL,'Y','N','N','N','N','N','N','N','N','N','N','N','Tatsächliche Entlademenge','NP',0,TO_TIMESTAMP('2026-09-09 12:01:00','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=593530 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;
/* DDL */ select update_Column_Translation_From_AD_Element(581796)
;

INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy)
VALUES (0,593530,784963 /*From ID Server*/,0,549491,TO_TIMESTAMP('2026-09-09 12:02:00','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,NULL,14,'D',NULL,'Y','N','N','N','N','N','Y','N','Tatsächliche Entlademenge',TO_TIMESTAMP('2026-09-09 12:02:00','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100)
;
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=784963 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;
/* DDL */ select update_FieldTranslation_From_AD_Name_Element(581796)
;
/* DDL */ select AD_Element_Link_Create_Missing_Field(784963)
;

-- UI placement: same "main" group as the planned figure, immediately after it in form and grid
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy)
VALUES (0,784963,0,549491,555764,654729 /*From ID Server*/,'F',TO_TIMESTAMP('2026-09-09 12:02:30','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,'Tatsächliche Entlademenge',NULL,'Y','N','N','Y','Y','N','N',0,'Tatsächliche Entlademenge',73,58,0,TO_TIMESTAMP('2026-09-09 12:02:31','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100)
;
