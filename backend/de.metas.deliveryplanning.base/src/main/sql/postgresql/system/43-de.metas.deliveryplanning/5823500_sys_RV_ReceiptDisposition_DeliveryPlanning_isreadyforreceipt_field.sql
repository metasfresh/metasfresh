-- AD metadata for the IsReadyForReceipt column added to RV_ReceiptDisposition_DeliveryPlanning by 5823490:
-- the AD_Column, its AD_Field on the receipt-disposition delivery-planning tab (549491), and the
-- AD_UI_Element that places it next to the other two row-state flags in the "main" element group (555764) -
-- IsPlanned (SeqNo 5 / SeqNoGrid 25) and Processed (6 / 26), so this one takes 7 / 27. Read-only like every
-- other column on this view-backed window (IsUpdateable='N', IsReadOnly='Y').
--
-- The AD_Element is 585451, created by 5823480 for M_Delivery_Planning.IsReadyForReceipt and REUSED here
-- rather than duplicated: the two columns carry the same concept, and its wording already covers both row
-- types ("a row with no delivery planning is always ready").
--
-- Filterable AND in the default filter panel, at SelectionColumnSeqNo=100 - unlike IsPlanned (seq 0,
-- filterable but not defaulted). This is the one column the window is meant to be narrowed by, so it earns a
-- panel slot; 100 puts it after the two document-number filters C_Order_ID(80) / POReference(90) and before
-- ContainerNo(180), which is the documented filter order (document no. -> partner -> date -> status -> org)
-- with AD_Org_ID(200) still last. The 16 columns 5822460/5822680 already configured are untouched.
--
-- No default filter VALUE is set here. Which readiness the grid opens on is a configuration decision, not a
-- property of the column, and is settled per instance.
--
-- IDs allocated from idserver.metas.de on 2026-09-09:
--   AD_MigrationScript 5823500 (this file), AD_Column 593534, AD_Field 784967, AD_UI_Element 654733

-- Column: RV_ReceiptDisposition_DeliveryPlanning.IsReadyForReceipt
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsTranslated,IsUpdateable,Name,PersonalDataCategory,SelectionColumnSeqNo,Updated,UpdatedBy,Version)
VALUES (0,593534 /*From ID Server*/,585451,0,20,NULL,542644,'IsReadyForReceipt',TO_TIMESTAMP('2026-09-09 09:02:00','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,
        'Zeigt an, ob der Wareneingang für diese Zeile durchgeführt werden kann. Eine Lieferplanung ist bereit, sobald sie einer abgeschlossenen Auslieferungsanweisung zugeordnet ist; eine Zeile ohne Lieferplanung ist immer bereit.',
        'D',1,NULL,'Y','N','N','N','N','N','N','N','N','Y','N','N','Bereit für Wareneingang','NP',100,
        TO_TIMESTAMP('2026-09-09 09:02:01','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Column_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Column t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=593534
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- Field
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy)
VALUES (0,593534,784967 /*From ID Server*/,0,549491,TO_TIMESTAMP('2026-09-09 09:02:02','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,
        'Zeigt an, ob der Wareneingang für diese Zeile durchgeführt werden kann. Eine Lieferplanung ist bereit, sobald sie einer abgeschlossenen Auslieferungsanweisung zugeordnet ist; eine Zeile ohne Lieferplanung ist immer bereit.',1,'D',NULL,'Y','Y','Y','N','N','N','Y','N','Bereit für Wareneingang',
        TO_TIMESTAMP('2026-09-09 09:02:03','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Field t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=784967
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- Takes the ELEMENT id, never the field id. Re-run here rather than relying on 5823480's call because the
-- AD_Column and AD_Field this has to translate did not exist yet when that script ran - until it runs, both
-- carry the German base text in every language.
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585451);

DELETE FROM AD_Element_Link WHERE AD_Field_ID=784967;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(784967);

-- UI placement: "main" element group, immediately after IsPlanned and Processed on both the form and the grid
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy)
VALUES (0,784967,0,549491,555764,654733 /*From ID Server*/,'F',TO_TIMESTAMP('2026-09-09 09:02:04','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,
        'Bereit für Wareneingang',NULL,'Y','N','N','Y','Y','N','N',0,'Bereit für Wareneingang',7,27,0,
        TO_TIMESTAMP('2026-09-09 09:02:05','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100)
;
