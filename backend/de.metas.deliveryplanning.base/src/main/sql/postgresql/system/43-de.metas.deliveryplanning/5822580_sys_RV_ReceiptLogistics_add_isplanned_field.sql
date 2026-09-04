-- AD metadata for the IsPlanned column added to RV_ReceiptLogistics by 5822570: a new AD_Element (no
-- existing element for this concept -- checked), the AD_Column, its AD_Field on the receipt-logistics
-- tab (549491), and the AD_UI_Element that places it as the FIRST field of the "main" element group
-- (555764) -- leading M_Delivery_Planning_ID (SeqNo 10) on the form and C_BPartner_ID (SeqNoGrid 30)
-- on the grid -- so the row type is legible at a glance instead of only by the blank
-- M_Delivery_Planning_ID cell seven columns in. Read-only like every other column on this view-backed
-- window (IsUpdateable='N', IsReadOnly='Y'). Filterable but kept out of the default filter panel
-- (SelectionColumnSeqNo=0), matching the treatment already given to the date columns and C_BPartner_ID
-- on this same table.
--
-- Deliberately NOT a readiness/status column and NOT actionability: it restates the row's ISPLANNED
-- literal (5822570) for the UI; whether the row can currently be received stays with the receipt
-- process's own preconditions.

-- Element: no existing AD_Element for this concept (checked: no ColumnName='IsPlanned' anywhere in AD)
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,Help,IsActive,Name,PrintName,Updated,UpdatedBy)
VALUES (0,585425 /*From ID Server*/,0,'IsPlanned',TO_TIMESTAMP('2026-09-04 09:20:00','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,
        'Kennzeichnet, ob die Zeile auf einer Lieferplanung beruht (geplant) oder nur auf einem Wareneingangs-Zeitplan ohne Lieferplanung (nicht geplant).',
        'D',NULL,'Y','Geplant','Geplant',TO_TIMESTAMP('2026-09-04 09:20:00','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Element_ID, t.Description,t.Help,t.Name,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Element t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Element_ID=585425
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

UPDATE AD_Element_Trl
SET Name='Planned', Description='Indicates whether the row is backed by a delivery planning (planned) or only by a receipt schedule with no delivery planning (unplanned).', IsTranslated='Y',
    Updated=TO_TIMESTAMP('2026-09-04 09:20:12','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100
WHERE AD_Language='en_US' AND AD_Element_ID=585425
;

-- Base-language text is already correct for de_DE / de_CH -- just flip IsTranslated
UPDATE AD_Element_Trl
SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-04 09:20:13','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100
WHERE AD_Language IN ('de_DE','de_CH') AND AD_Element_ID=585425
;

-- No update_TRL_Tables_On_AD_Element_TRL_Update() call here: the AD_Column/AD_Field this element
-- backs are created further below IN THIS SAME script, so there is nothing yet for the propagation
-- function to find. The explicit AD_Field_Trl UPDATE near the bottom does the equivalent propagation
-- once the field exists (same shape as 5822480/5822520 above it).

-- Column: RV_ReceiptLogistics.IsPlanned
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsTranslated,IsUpdateable,Name,PersonalDataCategory,SelectionColumnSeqNo,Updated,UpdatedBy,Version)
VALUES (0,593497 /*From ID Server*/,585425,0,20,NULL,542644,'IsPlanned',TO_TIMESTAMP('2026-09-04 09:21:00','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,
        'Kennzeichnet, ob die Zeile auf einer Lieferplanung beruht (geplant) oder nur auf einem Wareneingangs-Zeitplan ohne Lieferplanung (nicht geplant).','D',1,NULL,'Y','N','N','N','N','N','N','N','N','Y','N','N','Geplant','NP',0,
        TO_TIMESTAMP('2026-09-04 09:21:01','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Column_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Column t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=593497
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- Field
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy)
VALUES (0,593497,784947 /*From ID Server*/,0,549491,TO_TIMESTAMP('2026-09-04 09:21:02','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,
        'Kennzeichnet, ob die Zeile auf einer Lieferplanung beruht (geplant) oder nur auf einem Wareneingangs-Zeitplan ohne Lieferplanung (nicht geplant).',1,'D',NULL,'Y','Y','Y','N','N','N','Y','N','Geplant',
        TO_TIMESTAMP('2026-09-04 09:21:03','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Field t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=784947
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- Pull the non-base-language captions down from the already-translated AD_Element 585425 onto the new field
UPDATE AD_Field_Trl ft
SET Name = et.Name, Description = et.Description, Help = et.Help, IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-09-04 09:21:04','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy = 100
FROM AD_Field f
         JOIN AD_Column c ON c.AD_Column_ID = f.AD_Column_ID
         JOIN AD_Element_Trl et ON et.AD_Element_ID = c.AD_Element_ID
WHERE ft.AD_Field_ID = f.AD_Field_ID
  AND et.AD_Language = ft.AD_Language
  AND f.AD_Field_ID = 784947
  AND ft.AD_Language <> 'de_DE'
  AND et.IsTranslated = 'Y'
;

-- UI placement: "main" element group, leading M_Delivery_Planning_ID on the form and C_BPartner_ID on the grid
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy)
VALUES (0,784947,0,549491,555764,654713 /*From ID Server*/,'F',TO_TIMESTAMP('2026-09-04 09:21:05','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,
        'Geplant',NULL,'Y','N','N','Y','Y','N','N',0,'Geplant',5,25,0,
        TO_TIMESTAMP('2026-09-04 09:21:06','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100)
;
