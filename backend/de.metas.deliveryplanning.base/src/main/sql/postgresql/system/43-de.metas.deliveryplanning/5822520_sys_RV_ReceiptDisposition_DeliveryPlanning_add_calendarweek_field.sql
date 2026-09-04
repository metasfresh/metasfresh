-- AD metadata for the CalendarWeek column added to RV_ReceiptDisposition_DeliveryPlanning by 5822510: the AD_Column, its
-- AD_Field on the receipt-logistics tab (549491), and the AD_UI_Element that places it right next to
-- ETA -- in the "dates" element group (555765), between ETA (SeqNo 10 / SeqNoGrid 10) and
-- DatePromised_Effective (SeqNo 20 / SeqNoGrid 20) on both the form and the grid. Reuses the existing
-- AD_Element 583880 (CalendarWeek / "KW"), the same element M_ReceiptSchedule.CalendarWeek already
-- uses on window 541954 (untouched by this script), so the caption and its de_DE/de_CH/en_US/fr_CH
-- translations are already correct and merely propagated onto the new records.
-- No filter configuration here -- that is a later task once every filtered column exists.

-- Column: RV_ReceiptDisposition_DeliveryPlanning.CalendarWeek
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsTranslated,IsUpdateable,Name,PersonalDataCategory,SelectionColumnSeqNo,Updated,UpdatedBy,Version)
VALUES (0,593495 /*From ID Server*/,583880,0,22,NULL,542644,'CalendarWeek',TO_TIMESTAMP('2026-09-03 09:10:00','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,'D',2,'Y','N','N','N','N','N','N','N','N','N','N','N','KW','NP',0,TO_TIMESTAMP('2026-09-03 09:10:01','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=593495 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- Field
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy)
VALUES (0,593495,784946 /*From ID Server*/,0,549491,TO_TIMESTAMP('2026-09-03 09:10:02','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,2,'D','Y','N','N','N','N','N','Y','N','KW',TO_TIMESTAMP('2026-09-03 09:10:03','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=784946 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- Pull the non-base-language captions down from the already-translated AD_Element 583880 onto the new field
UPDATE AD_Field_Trl ft
SET Name = et.Name, Description = et.Description, Help = et.Help, IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-09-03 09:10:04','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy = 100
FROM AD_Field f
         JOIN AD_Column c ON c.AD_Column_ID = f.AD_Column_ID
         JOIN AD_Element_Trl et ON et.AD_Element_ID = c.AD_Element_ID
WHERE ft.AD_Field_ID = f.AD_Field_ID
  AND et.AD_Language = ft.AD_Language
  AND f.AD_Field_ID = 784946
  AND ft.AD_Language <> 'de_DE'
  AND et.IsTranslated = 'Y'
;

-- UI placement: "dates" element group, between ETA (SeqNo/SeqNoGrid 10) and DatePromised_Effective (20)
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy)
VALUES (0,784946,0,549491,555765,654712 /*From ID Server*/,'F',TO_TIMESTAMP('2026-09-03 09:10:05','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,NULL,NULL,'Y','N','N','Y','Y','N','N',0,'KW',15,15,0,TO_TIMESTAMP('2026-09-03 09:10:06','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100)
;
