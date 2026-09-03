-- AD metadata for the C_UOM_ID column added to RV_ReceiptLogistics by 5822480: the AD_Column, its
-- AD_Field on the receipt-logistics tab (549491), and the AD_UI_Element that places it right next to
-- QtyOrdered -- in the same "main" element group (555764), directly after QtyOrdered on both the form
-- (SeqNo 75, between QtyOrdered's 70 and Lager's 80) and the grid (SeqNoGrid 55, between QtyOrdered's
-- 50 and Lager's 60). Reuses the existing AD_Element 215 (C_UOM_ID / "Maßeinheit"), the same element
-- already used by M_ReceiptSchedule.C_UOM_ID and M_Delivery_Planning.C_UOM_ID, so the caption and its
-- de_DE/de_CH/en_US translations are already correct and merely propagated onto the new records.
-- No filter configuration here -- that is a later task once every filtered column exists.

-- Column: RV_ReceiptLogistics.C_UOM_ID
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsTranslated,IsUpdateable,Name,PersonalDataCategory,SelectionColumnSeqNo,Updated,UpdatedBy,Version)
VALUES (0,593494 /*From ID Server*/,215,0,30,NULL,542644,'C_UOM_ID',TO_TIMESTAMP('2026-09-03 09:01:00','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,'Maßeinheit','D',10,'Eine eindeutige (nicht monetäre) Maßeinheit','Y','N','N','N','N','N','N','N','N','N','N','N','Maßeinheit','NP',0,TO_TIMESTAMP('2026-09-03 09:01:01','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=593494 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- Field
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy)
VALUES (0,593494,784945 /*From ID Server*/,0,549491,TO_TIMESTAMP('2026-09-03 09:01:02','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,'Maßeinheit',10,'D','Eine eindeutige (nicht monetäre) Maßeinheit','Y','N','N','N','N','N','Y','N','Maßeinheit',TO_TIMESTAMP('2026-09-03 09:01:03','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=784945 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- Pull the non-base-language captions down from the already-translated AD_Element 215 onto the new field
UPDATE AD_Field_Trl ft
SET Name = et.Name, Description = et.Description, Help = et.Help, IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-09-03 09:01:04','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy = 100
FROM AD_Field f
         JOIN AD_Column c ON c.AD_Column_ID = f.AD_Column_ID
         JOIN AD_Element_Trl et ON et.AD_Element_ID = c.AD_Element_ID
WHERE ft.AD_Field_ID = f.AD_Field_ID
  AND et.AD_Language = ft.AD_Language
  AND f.AD_Field_ID = 784945
  AND ft.AD_Language <> 'de_DE'
  AND et.IsTranslated = 'Y'
;

-- UI placement: same "main" element group as QtyOrdered, right after it on both surfaces
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy)
VALUES (0,784945,0,549491,555764,654711 /*From ID Server*/,'F',TO_TIMESTAMP('2026-09-03 09:01:05','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,'Maßeinheit','Eine eindeutige (nicht monetäre) Maßeinheit','Y','N','N','Y','Y','N','N',0,'Maßeinheit',75,55,0,TO_TIMESTAMP('2026-09-03 09:01:06','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100)
;
