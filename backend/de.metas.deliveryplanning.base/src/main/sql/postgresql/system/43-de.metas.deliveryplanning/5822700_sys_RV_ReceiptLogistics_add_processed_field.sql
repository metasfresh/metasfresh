-- AD metadata for the Processed column added to RV_ReceiptLogistics by 5822690: reuses the existing
-- AD_Element 1047 ("Processed" / "Verarbeitet", already carrying de_DE/de_CH/en_US translations and
-- already backing M_Delivery_Planning.Processed (585273) and M_ReceiptSchedule.Processed (549513)) --
-- no new element, no AD_Name_ID override, per the shared "reuse the planning's AD_Elements" rule.
-- No customer override window exists for AD_Window 542190 (checked: zero AD_Window rows with
-- Overrides_Window_ID=542190), so this is the only script this change needs.
--
-- UI placement: "main" element group (555764), right after IsPlanned -- SeqNo 6 (IsPlanned=5, next
-- main SeqNo=50) on the form, SeqNoGrid 26 (IsPlanned=25, next=30) on the grid. IsPlanned already
-- answers "does a planning exist for this row"; Processed answers "is it done" -- the two per-row
-- state flags belong together at the front of the grid, exactly where a dispatcher scanning rows for
-- a done/not-done marker would look, rather than buried among the identity/date columns.
--
-- Read-only like every other column on this view-backed window: IsUpdateable='N' on AD_Column,
-- IsReadOnly='Y' on AD_Field.
--
-- Filter: IsSelectionColumn='Y' at SelectionColumnSeqNo=198 -- a real, defaulted filter (not seq 0),
-- matching M_ReceiptSchedule's own window (541954), where Processed is already a real-seq filter
-- (260, second-to-last before AD_Org_ID 280). Placed just before this window's own AD_Org_ID (200,
-- "kept last" per 5822680's header comment) -- it is the flag that lets a dispatcher hide finished
-- rows, which is its main job, so it belongs in the default filter bar rather than at seq 0.
--
-- Deliberately NOT a readiness or status column: Processed is a plain flag shown as itself, nothing
-- more -- same discipline IsPlanned's own header comment (5822580) already established for this window.

-- Column: RV_ReceiptLogistics.Processed
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsTranslated,IsUpdateable,Name,PersonalDataCategory,SelectionColumnSeqNo,Updated,UpdatedBy,Version)
VALUES (0,593507 /*From ID Server*/,1047,0,20,NULL,542644,'Processed',TO_TIMESTAMP('2026-09-04 12:00:00','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,'Checkbox sagt aus, ob der Datensatz verarbeitet wurde. ','D',1,'Verarbeitete Datensatz dürfen in der Regel nich mehr geändert werden.','Y','N','N','N','N','N','N','N','N','Y','N','N','Verarbeitet','NP',198,TO_TIMESTAMP('2026-09-04 12:00:01','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=593507 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;
UPDATE AD_Column_Trl ct SET Name = et.Name, Description = et.Description, IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-09-04 12:00:02','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy = 100
FROM AD_Column c JOIN AD_Element_Trl et ON et.AD_Element_ID = c.AD_Element_ID
WHERE ct.AD_Column_ID = c.AD_Column_ID AND et.AD_Language = ct.AD_Language AND c.AD_Column_ID = 593507 AND ct.AD_Language <> 'de_DE' AND et.IsTranslated = 'Y'
;

-- Field
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy)
VALUES (0,593507,784956 /*From ID Server*/,0,549491,TO_TIMESTAMP('2026-09-04 12:00:03','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,'Checkbox sagt aus, ob der Datensatz verarbeitet wurde. ',1,'D','Verarbeitete Datensatz dürfen in der Regel nich mehr geändert werden.','Y','Y','Y','N','N','N','Y','N','Verarbeitet',TO_TIMESTAMP('2026-09-04 12:00:04','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100)
;
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=784956 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;
UPDATE AD_Field_Trl ft SET Name = et.Name, Description = et.Description, Help = et.Help, IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-09-04 12:00:05','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy = 100
FROM AD_Field f JOIN AD_Column c ON c.AD_Column_ID = f.AD_Column_ID JOIN AD_Element_Trl et ON et.AD_Element_ID = c.AD_Element_ID
WHERE ft.AD_Field_ID = f.AD_Field_ID AND et.AD_Language = ft.AD_Language AND f.AD_Field_ID = 784956 AND ft.AD_Language <> 'de_DE' AND et.IsTranslated = 'Y'
;

-- UI placement: "main" group, right after IsPlanned
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy)
VALUES (0,784956,0,549491,555764,654722 /*From ID Server*/,'F',TO_TIMESTAMP('2026-09-04 12:00:06','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,'Verarbeitet','Checkbox sagt aus, ob der Datensatz verarbeitet wurde. ','Y','N','N','Y','Y','N','N',0,'Verarbeitet',6,26,0,TO_TIMESTAMP('2026-09-04 12:00:07','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100)
;
