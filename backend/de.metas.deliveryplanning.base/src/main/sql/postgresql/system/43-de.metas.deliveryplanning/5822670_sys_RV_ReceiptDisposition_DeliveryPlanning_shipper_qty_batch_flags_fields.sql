-- AD metadata for the seven columns 5822660 added to RV_ReceiptDisposition_DeliveryPlanning: M_Shipper_ID, QtyToMove,
-- Batch, IsConfirmedBySupplier, IsBLReceived, IsBookingConfirmed, IsWENotice. Each reuses the
-- AD_Element of the source column it mirrors (Lieferweg/455, Menge zu bewegen/542204, Stapel Nr./
-- 581692, Bestätigt durch Lieferant/584306, B/L erhalten/584074, Buchung bestätigt/584071,
-- WE Avis/584076), so captions and their existing de_DE/de_CH/en_US translations are already correct
-- and merely propagated onto the new records -- no new element, no translation authoring here.
-- M_Shipper_ID, QtyToMove and Batch join the "main" element group (555764) next to the fields they
-- relate to; the three shipper-transportation flags join the existing "flags" group (555766), next to
-- IsActive. No filter configuration here -- that is Task W6b's own dedicated script, once every
-- filtered column exists.

-- ============================================================================
-- Column: RV_ReceiptDisposition_DeliveryPlanning.M_Shipper_ID
-- ============================================================================
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsTranslated,IsUpdateable,Name,PersonalDataCategory,SelectionColumnSeqNo,Updated,UpdatedBy,Version)
VALUES (0,593500 /*From ID Server*/,455,0,30,NULL,542644,'M_Shipper_ID',TO_TIMESTAMP('2026-09-04 10:00:00','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,'Methode oder Art der Warenlieferung','D',10,NULL,'Y','N','N','N','N','N','N','N','N','N','N','N','Lieferweg','NP',0,TO_TIMESTAMP('2026-09-04 10:00:01','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=593500 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;
UPDATE AD_Column_Trl ct SET Name = et.Name, Description = et.Description, IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-09-04 10:00:02','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy = 100
FROM AD_Column c JOIN AD_Element_Trl et ON et.AD_Element_ID = c.AD_Element_ID
WHERE ct.AD_Column_ID = c.AD_Column_ID AND et.AD_Language = ct.AD_Language AND c.AD_Column_ID = 593500 AND ct.AD_Language <> 'de_DE' AND et.IsTranslated = 'Y'
;

INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy)
VALUES (0,593500,784949 /*From ID Server*/,0,549491,TO_TIMESTAMP('2026-09-04 10:00:03','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,'Methode oder Art der Warenlieferung',10,'D',NULL,'Y','N','N','N','N','N','Y','N','Lieferweg',TO_TIMESTAMP('2026-09-04 10:00:04','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100)
;
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=784949 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;
UPDATE AD_Field_Trl ft SET Name = et.Name, Description = et.Description, Help = et.Help, IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-09-04 10:00:05','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy = 100
FROM AD_Field f JOIN AD_Column c ON c.AD_Column_ID = f.AD_Column_ID JOIN AD_Element_Trl et ON et.AD_Element_ID = c.AD_Element_ID
WHERE ft.AD_Field_ID = f.AD_Field_ID AND et.AD_Language = ft.AD_Language AND f.AD_Field_ID = 784949 AND ft.AD_Language <> 'de_DE' AND et.IsTranslated = 'Y'
;

-- UI placement: "main" group, right after ContainerNo on both surfaces
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy)
VALUES (0,784949,0,549491,555764,654715 /*From ID Server*/,'F',TO_TIMESTAMP('2026-09-04 10:00:06','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,'Lieferweg','Methode oder Art der Warenlieferung','Y','N','N','Y','Y','N','N',0,'Lieferweg',94,104,0,TO_TIMESTAMP('2026-09-04 10:00:07','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- ============================================================================
-- Column: RV_ReceiptDisposition_DeliveryPlanning.QtyToMove
-- ============================================================================
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsTranslated,IsUpdateable,Name,PersonalDataCategory,SelectionColumnSeqNo,Updated,UpdatedBy,Version)
VALUES (0,593501 /*From ID Server*/,542204,0,29,NULL,542644,'QtyToMove',TO_TIMESTAMP('2026-09-04 10:01:00','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,NULL,'D',14,NULL,'Y','N','N','N','N','N','N','N','N','N','N','N','Menge zu bewegen','NP',0,TO_TIMESTAMP('2026-09-04 10:01:01','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=593501 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;
UPDATE AD_Column_Trl ct SET Name = et.Name, Description = et.Description, IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-09-04 10:01:02','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy = 100
FROM AD_Column c JOIN AD_Element_Trl et ON et.AD_Element_ID = c.AD_Element_ID
WHERE ct.AD_Column_ID = c.AD_Column_ID AND et.AD_Language = ct.AD_Language AND c.AD_Column_ID = 593501 AND ct.AD_Language <> 'de_DE' AND et.IsTranslated = 'Y'
;

INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy)
VALUES (0,593501,784950 /*From ID Server*/,0,549491,TO_TIMESTAMP('2026-09-04 10:01:03','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,NULL,14,'D',NULL,'Y','N','N','N','N','N','Y','N','Menge zu bewegen',TO_TIMESTAMP('2026-09-04 10:01:04','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100)
;
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=784950 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;
UPDATE AD_Field_Trl ft SET Name = et.Name, Description = et.Description, Help = et.Help, IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-09-04 10:01:05','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy = 100
FROM AD_Field f JOIN AD_Column c ON c.AD_Column_ID = f.AD_Column_ID JOIN AD_Element_Trl et ON et.AD_Element_ID = c.AD_Element_ID
WHERE ft.AD_Field_ID = f.AD_Field_ID AND et.AD_Language = ft.AD_Language AND f.AD_Field_ID = 784950 AND ft.AD_Language <> 'de_DE' AND et.IsTranslated = 'Y'
;

-- UI placement: "main" group, right after QtyOrdered/before C_UOM_ID (open figure next to the ordered one)
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy)
VALUES (0,784950,0,549491,555764,654716 /*From ID Server*/,'F',TO_TIMESTAMP('2026-09-04 10:01:06','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,'Menge zu bewegen',NULL,'Y','N','N','Y','Y','N','N',0,'Menge zu bewegen',72,57,0,TO_TIMESTAMP('2026-09-04 10:01:07','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- ============================================================================
-- Column: RV_ReceiptDisposition_DeliveryPlanning.Batch
-- ============================================================================
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsTranslated,IsUpdateable,Name,PersonalDataCategory,SelectionColumnSeqNo,Updated,UpdatedBy,Version)
VALUES (0,593502 /*From ID Server*/,581692,0,10,NULL,542644,'Batch',TO_TIMESTAMP('2026-09-04 10:02:00','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,NULL,'D',250,NULL,'Y','N','N','N','N','N','N','N','N','N','N','N','Stapel Nr.','NP',0,TO_TIMESTAMP('2026-09-04 10:02:01','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=593502 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;
UPDATE AD_Column_Trl ct SET Name = et.Name, Description = et.Description, IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-09-04 10:02:02','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy = 100
FROM AD_Column c JOIN AD_Element_Trl et ON et.AD_Element_ID = c.AD_Element_ID
WHERE ct.AD_Column_ID = c.AD_Column_ID AND et.AD_Language = ct.AD_Language AND c.AD_Column_ID = 593502 AND ct.AD_Language <> 'de_DE' AND et.IsTranslated = 'Y'
;

INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy)
VALUES (0,593502,784951 /*From ID Server*/,0,549491,TO_TIMESTAMP('2026-09-04 10:02:03','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,NULL,250,'D',NULL,'Y','N','N','N','N','N','Y','N','Stapel Nr.',TO_TIMESTAMP('2026-09-04 10:02:04','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100)
;
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=784951 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;
UPDATE AD_Field_Trl ft SET Name = et.Name, Description = et.Description, Help = et.Help, IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-09-04 10:02:05','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy = 100
FROM AD_Field f JOIN AD_Column c ON c.AD_Column_ID = f.AD_Column_ID JOIN AD_Element_Trl et ON et.AD_Element_ID = c.AD_Element_ID
WHERE ft.AD_Field_ID = f.AD_Field_ID AND et.AD_Language = ft.AD_Language AND f.AD_Field_ID = 784951 AND ft.AD_Language <> 'de_DE' AND et.IsTranslated = 'Y'
;

-- UI placement: "main" group, after ContainerNo/before Shipper
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy)
VALUES (0,784951,0,549491,555764,654717 /*From ID Server*/,'F',TO_TIMESTAMP('2026-09-04 10:02:06','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,'Stapel Nr.',NULL,'Y','N','N','Y','Y','N','N',0,'Stapel Nr.',92,102,0,TO_TIMESTAMP('2026-09-04 10:02:07','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- ============================================================================
-- Column: RV_ReceiptDisposition_DeliveryPlanning.IsConfirmedBySupplier
-- ============================================================================
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsTranslated,IsUpdateable,Name,PersonalDataCategory,SelectionColumnSeqNo,Updated,UpdatedBy,Version)
VALUES (0,593503 /*From ID Server*/,584306,0,20,NULL,542644,'IsConfirmedBySupplier',TO_TIMESTAMP('2026-09-04 10:03:00','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,NULL,'D',1,NULL,'Y','N','N','N','N','N','N','N','N','N','N','N','Bestätigt durch Lieferant','NP',0,TO_TIMESTAMP('2026-09-04 10:03:01','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=593503 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;
UPDATE AD_Column_Trl ct SET Name = et.Name, Description = et.Description, IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-09-04 10:03:02','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy = 100
FROM AD_Column c JOIN AD_Element_Trl et ON et.AD_Element_ID = c.AD_Element_ID
WHERE ct.AD_Column_ID = c.AD_Column_ID AND et.AD_Language = ct.AD_Language AND c.AD_Column_ID = 593503 AND ct.AD_Language <> 'de_DE' AND et.IsTranslated = 'Y'
;

INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy)
VALUES (0,593503,784952 /*From ID Server*/,0,549491,TO_TIMESTAMP('2026-09-04 10:03:03','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,NULL,1,'D',NULL,'Y','N','N','N','N','N','Y','N','Bestätigt durch Lieferant',TO_TIMESTAMP('2026-09-04 10:03:04','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100)
;
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=784952 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;
UPDATE AD_Field_Trl ft SET Name = et.Name, Description = et.Description, Help = et.Help, IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-09-04 10:03:05','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy = 100
FROM AD_Field f JOIN AD_Column c ON c.AD_Column_ID = f.AD_Column_ID JOIN AD_Element_Trl et ON et.AD_Element_ID = c.AD_Element_ID
WHERE ft.AD_Field_ID = f.AD_Field_ID AND et.AD_Language = ft.AD_Language AND f.AD_Field_ID = 784952 AND ft.AD_Language <> 'de_DE' AND et.IsTranslated = 'Y'
;

-- UI placement: "flags" group, right after IsActive
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy)
VALUES (0,784952,0,549491,555766,654718 /*From ID Server*/,'F',TO_TIMESTAMP('2026-09-04 10:03:06','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,'Bestätigt durch Lieferant',NULL,'Y','N','N','Y','Y','N','N',0,'Bestätigt durch Lieferant',20,132,0,TO_TIMESTAMP('2026-09-04 10:03:07','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- ============================================================================
-- Column: RV_ReceiptDisposition_DeliveryPlanning.IsBLReceived
-- ============================================================================
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsTranslated,IsUpdateable,Name,PersonalDataCategory,SelectionColumnSeqNo,Updated,UpdatedBy,Version)
VALUES (0,593504 /*From ID Server*/,584074,0,20,NULL,542644,'IsBLReceived',TO_TIMESTAMP('2026-09-04 10:04:00','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,'Ist das Konnossement eingegangen?','D',1,NULL,'Y','N','N','N','N','N','N','N','N','N','N','N','B/L erhalten','NP',0,TO_TIMESTAMP('2026-09-04 10:04:01','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=593504 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;
UPDATE AD_Column_Trl ct SET Name = et.Name, Description = et.Description, IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-09-04 10:04:02','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy = 100
FROM AD_Column c JOIN AD_Element_Trl et ON et.AD_Element_ID = c.AD_Element_ID
WHERE ct.AD_Column_ID = c.AD_Column_ID AND et.AD_Language = ct.AD_Language AND c.AD_Column_ID = 593504 AND ct.AD_Language <> 'de_DE' AND et.IsTranslated = 'Y'
;

INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy)
VALUES (0,593504,784953 /*From ID Server*/,0,549491,TO_TIMESTAMP('2026-09-04 10:04:03','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,'Ist das Konnossement eingegangen?',1,'D',NULL,'Y','N','N','N','N','N','Y','N','B/L erhalten',TO_TIMESTAMP('2026-09-04 10:04:04','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100)
;
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=784953 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;
UPDATE AD_Field_Trl ft SET Name = et.Name, Description = et.Description, Help = et.Help, IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-09-04 10:04:05','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy = 100
FROM AD_Field f JOIN AD_Column c ON c.AD_Column_ID = f.AD_Column_ID JOIN AD_Element_Trl et ON et.AD_Element_ID = c.AD_Element_ID
WHERE ft.AD_Field_ID = f.AD_Field_ID AND et.AD_Language = ft.AD_Language AND f.AD_Field_ID = 784953 AND ft.AD_Language <> 'de_DE' AND et.IsTranslated = 'Y'
;

-- UI placement: "flags" group, after IsConfirmedBySupplier
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy)
VALUES (0,784953,0,549491,555766,654719 /*From ID Server*/,'F',TO_TIMESTAMP('2026-09-04 10:04:06','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,'B/L erhalten','Ist das Konnossement eingegangen?','Y','N','N','Y','Y','N','N',0,'B/L erhalten',30,134,0,TO_TIMESTAMP('2026-09-04 10:04:07','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- ============================================================================
-- Column: RV_ReceiptDisposition_DeliveryPlanning.IsBookingConfirmed
-- ============================================================================
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsTranslated,IsUpdateable,Name,PersonalDataCategory,SelectionColumnSeqNo,Updated,UpdatedBy,Version)
VALUES (0,593505 /*From ID Server*/,584071,0,20,NULL,542644,'IsBookingConfirmed',TO_TIMESTAMP('2026-09-04 10:05:00','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,'Wurde die Verschiffungsbuchung bestätigt?','D',1,NULL,'Y','N','N','N','N','N','N','N','N','N','N','N','Buchung bestätigt','NP',0,TO_TIMESTAMP('2026-09-04 10:05:01','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=593505 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;
UPDATE AD_Column_Trl ct SET Name = et.Name, Description = et.Description, IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-09-04 10:05:02','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy = 100
FROM AD_Column c JOIN AD_Element_Trl et ON et.AD_Element_ID = c.AD_Element_ID
WHERE ct.AD_Column_ID = c.AD_Column_ID AND et.AD_Language = ct.AD_Language AND c.AD_Column_ID = 593505 AND ct.AD_Language <> 'de_DE' AND et.IsTranslated = 'Y'
;

INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy)
VALUES (0,593505,784954 /*From ID Server*/,0,549491,TO_TIMESTAMP('2026-09-04 10:05:03','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,'Wurde die Verschiffungsbuchung bestätigt?',1,'D',NULL,'Y','N','N','N','N','N','Y','N','Buchung bestätigt',TO_TIMESTAMP('2026-09-04 10:05:04','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100)
;
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=784954 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;
UPDATE AD_Field_Trl ft SET Name = et.Name, Description = et.Description, Help = et.Help, IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-09-04 10:05:05','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy = 100
FROM AD_Field f JOIN AD_Column c ON c.AD_Column_ID = f.AD_Column_ID JOIN AD_Element_Trl et ON et.AD_Element_ID = c.AD_Element_ID
WHERE ft.AD_Field_ID = f.AD_Field_ID AND et.AD_Language = ft.AD_Language AND f.AD_Field_ID = 784954 AND ft.AD_Language <> 'de_DE' AND et.IsTranslated = 'Y'
;

-- UI placement: "flags" group, after IsBLReceived
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy)
VALUES (0,784954,0,549491,555766,654720 /*From ID Server*/,'F',TO_TIMESTAMP('2026-09-04 10:05:06','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,'Buchung bestätigt','Wurde die Verschiffungsbuchung bestätigt?','Y','N','N','Y','Y','N','N',0,'Buchung bestätigt',40,136,0,TO_TIMESTAMP('2026-09-04 10:05:07','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- ============================================================================
-- Column: RV_ReceiptDisposition_DeliveryPlanning.IsWENotice
-- ============================================================================
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsTranslated,IsUpdateable,Name,PersonalDataCategory,SelectionColumnSeqNo,Updated,UpdatedBy,Version)
VALUES (0,593506 /*From ID Server*/,584076,0,20,NULL,542644,'IsWENotice',TO_TIMESTAMP('2026-09-04 10:06:00','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,'Wurde die Containerplanung abgeschlossen?','D',1,NULL,'Y','N','N','N','N','N','N','N','N','N','N','N','WE Avis','NP',0,TO_TIMESTAMP('2026-09-04 10:06:01','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=593506 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;
UPDATE AD_Column_Trl ct SET Name = et.Name, Description = et.Description, IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-09-04 10:06:02','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy = 100
FROM AD_Column c JOIN AD_Element_Trl et ON et.AD_Element_ID = c.AD_Element_ID
WHERE ct.AD_Column_ID = c.AD_Column_ID AND et.AD_Language = ct.AD_Language AND c.AD_Column_ID = 593506 AND ct.AD_Language <> 'de_DE' AND et.IsTranslated = 'Y'
;

INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy)
VALUES (0,593506,784955 /*From ID Server*/,0,549491,TO_TIMESTAMP('2026-09-04 10:06:03','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,'Wurde die Containerplanung abgeschlossen?',1,'D',NULL,'Y','N','N','N','N','N','Y','N','WE Avis',TO_TIMESTAMP('2026-09-04 10:06:04','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100)
;
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=784955 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;
UPDATE AD_Field_Trl ft SET Name = et.Name, Description = et.Description, Help = et.Help, IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-09-04 10:06:05','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy = 100
FROM AD_Field f JOIN AD_Column c ON c.AD_Column_ID = f.AD_Column_ID JOIN AD_Element_Trl et ON et.AD_Element_ID = c.AD_Element_ID
WHERE ft.AD_Field_ID = f.AD_Field_ID AND et.AD_Language = ft.AD_Language AND f.AD_Field_ID = 784955 AND ft.AD_Language <> 'de_DE' AND et.IsTranslated = 'Y'
;

-- UI placement: "flags" group, after IsBookingConfirmed
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy)
VALUES (0,784955,0,549491,555766,654721 /*From ID Server*/,'F',TO_TIMESTAMP('2026-09-04 10:06:06','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,'WE Avis','Wurde die Containerplanung abgeschlossen?','Y','N','N','Y','Y','N','N',0,'WE Avis',50,138,0,TO_TIMESTAMP('2026-09-04 10:06:07','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100)
;
