-- Run mode: SWING_CLIENT

-- Field: Ausgehende Belege(540170,de.metas.document.archive) -> Ausgehende Belege(540474,de.metas.document.archive) -> Zugesagter Termin
-- Column: C_Doc_Outbound_Log.DatePromised
-- 2026-01-05T14:59:29.968Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,FacetFilterSeqNo,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,591445,760968,0,540474,0,TO_TIMESTAMP('2026-01-05 14:59:29.006000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Zugesagter Termin für diesen Auftrag',0,'D',0,'Der "Zugesagte Termin" gibt das Datum an, für den (wenn zutreffend) dieser Auftrag zugesagt wurde.',0,'Y','Y','Y','N','N','N','N','N','N','N',0,'Zugesagter Termin',0,0,220,0,1,1,TO_TIMESTAMP('2026-01-05 14:59:29.006000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-01-05T14:59:30.045Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=760968 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-01-05T14:59:30.149Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(269)
;

-- 2026-01-05T14:59:30.226Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=760968
;

-- 2026-01-05T14:59:30.289Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(760968)
;

-- UI Element: Ausgehende Belege(540170,de.metas.document.archive) -> Ausgehende Belege(540474,de.metas.document.archive) -> main -> 20 -> doc.Zugesagter Termin
-- Column: C_Doc_Outbound_Log.DatePromised
-- 2026-01-05T15:01:45.147Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,760968,0,540474,541071,641307,'F',TO_TIMESTAMP('2026-01-05 15:01:44.572000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Zugesagter Termin für diesen Auftrag','Der "Zugesagte Termin" gibt das Datum an, für den (wenn zutreffend) dieser Auftrag zugesagt wurde.','Y','N','N','Y','N','N','N',0,'Zugesagter Termin',35,0,0,TO_TIMESTAMP('2026-01-05 15:01:44.572000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Ausgehende Belege(540170,de.metas.document.archive) -> Ausgehende Belege(540474,de.metas.document.archive) -> main -> 20 -> doc.Zugesagter Termin
-- Column: C_Doc_Outbound_Log.DatePromised
-- 2026-01-05T15:02:02.325Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2026-01-05 15:02:02.324000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=641307
;

-- UI Element: Ausgehende Belege(540170,de.metas.document.archive) -> Ausgehende Belege(540474,de.metas.document.archive) -> main -> 10 -> default.Geschäftspartner
-- Column: C_Doc_Outbound_Log.C_BPartner_ID
-- 2026-01-05T15:02:02.736Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2026-01-05 15:02:02.736000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=543687
;

-- UI Element: Ausgehende Belege(540170,de.metas.document.archive) -> Ausgehende Belege(540474,de.metas.document.archive) -> main -> 10 -> default.Geschäftspartnergruppe
-- Column: C_Doc_Outbound_Log.C_BP_Group_ID
-- 2026-01-05T15:02:03.180Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2026-01-05 15:02:03.180000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=543688
;

-- UI Element: Ausgehende Belege(540170,de.metas.document.archive) -> Ausgehende Belege(540474,de.metas.document.archive) -> main -> 10 -> default.Erstellt durch
-- Column: C_Doc_Outbound_Log.CreatedBy
-- 2026-01-05T15:02:03.648Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2026-01-05 15:02:03.647000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=548021
;

-- UI Element: Ausgehende Belege(540170,de.metas.document.archive) -> Ausgehende Belege(540474,de.metas.document.archive) -> main -> 20 -> doc.Referenz
-- Column: C_Doc_Outbound_Log.POReference
-- 2026-01-05T15:02:04.090Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2026-01-05 15:02:04.090000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=548017
;

-- UI Element: Ausgehende Belege(540170,de.metas.document.archive) -> Ausgehende Belege(540474,de.metas.document.archive) -> main -> 10 -> stats.Anz. gedruckt
-- Column: C_Doc_Outbound_Log.PrintCount
-- 2026-01-05T15:02:04.756Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2026-01-05 15:02:04.756000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=543691
;

-- UI Element: Ausgehende Belege(540170,de.metas.document.archive) -> Ausgehende Belege(540474,de.metas.document.archive) -> main -> 10 -> stats.Zuletzt gedruckt
-- Column: C_Doc_Outbound_Log.DateLastPrint
-- 2026-01-05T15:02:05.174Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2026-01-05 15:02:05.174000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=543702
;

-- UI Element: Ausgehende Belege(540170,de.metas.document.archive) -> Ausgehende Belege(540474,de.metas.document.archive) -> main -> 20 -> flags.Rechnung eMail erlaubt
-- Column: C_Doc_Outbound_Log.IsInvoiceEmailEnabled
-- 2026-01-05T15:02:05.568Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2026-01-05 15:02:05.567000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=543706
;

-- UI Element: Ausgehende Belege(540170,de.metas.document.archive) -> Ausgehende Belege(540474,de.metas.document.archive) -> main -> 10 -> stats.Anz. gemailt
-- Column: C_Doc_Outbound_Log.EMailCount
-- 2026-01-05T15:02:05.973Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2026-01-05 15:02:05.971000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=543692
;

-- UI Element: Ausgehende Belege(540170,de.metas.document.archive) -> Ausgehende Belege(540474,de.metas.document.archive) -> main -> 10 -> stats.Zuletzt gemailt
-- Column: C_Doc_Outbound_Log.DateLastEMail
-- 2026-01-05T15:02:06.380Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=140,Updated=TO_TIMESTAMP('2026-01-05 15:02:06.380000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=543703
;

-- UI Element: Ausgehende Belege(540170,de.metas.document.archive) -> Ausgehende Belege(540474,de.metas.document.archive) -> main -> 10 -> stats.Derzeitige Mailadresse
-- Column: C_Doc_Outbound_Log.CurrentEMailAddress
-- 2026-01-05T15:02:06.780Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=150,Updated=TO_TIMESTAMP('2026-01-05 15:02:06.779000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=552433
;

-- UI Element: Ausgehende Belege(540170,de.metas.document.archive) -> Ausgehende Belege(540474,de.metas.document.archive) -> main -> 10 -> stats.Cc Mailadresse
-- Column: C_Doc_Outbound_Log.CurrentEMailAddressCC
-- 2026-01-05T15:02:07.206Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=160,Updated=TO_TIMESTAMP('2026-01-05 15:02:07.206000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637269
;

-- UI Element: Ausgehende Belege(540170,de.metas.document.archive) -> Ausgehende Belege(540474,de.metas.document.archive) -> main -> 10 -> stats.Anz. PDF
-- Column: C_Doc_Outbound_Log.PDFCount
-- 2026-01-05T15:02:07.605Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=170,Updated=TO_TIMESTAMP('2026-01-05 15:02:07.605000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=543693
;

-- UI Element: Ausgehende Belege(540170,de.metas.document.archive) -> Ausgehende Belege(540474,de.metas.document.archive) -> main -> 10 -> stats.Anz. gespeichert
-- Column: C_Doc_Outbound_Log.StoreCount
-- 2026-01-05T15:02:08.008Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=180,Updated=TO_TIMESTAMP('2026-01-05 15:02:08.008000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=553992
;

-- UI Element: Ausgehende Belege(540170,de.metas.document.archive) -> Ausgehende Belege(540474,de.metas.document.archive) -> main -> 10 -> stats.Zuletzt gespeichert
-- Column: C_Doc_Outbound_Log.DateLastStore
-- 2026-01-05T15:02:08.418Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=190,Updated=TO_TIMESTAMP('2026-01-05 15:02:08.418000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=553993
;

-- UI Element: Ausgehende Belege(540170,de.metas.document.archive) -> Ausgehende Belege(540474,de.metas.document.archive) -> main -> 10 -> default.Async Batch
-- Column: C_Doc_Outbound_Log.C_Async_Batch_ID
-- 2026-01-05T15:02:08.817Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=200,Updated=TO_TIMESTAMP('2026-01-05 15:02:08.817000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=587063
;

-- UI Element: Ausgehende Belege(540170,de.metas.document.archive) -> Ausgehende Belege(540474,de.metas.document.archive) -> main -> 20 -> flags.per EDI übermitteln
-- Column: C_Doc_Outbound_Log.IsEdiEnabled
-- 2026-01-05T15:02:09.207Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=210,Updated=TO_TIMESTAMP('2026-01-05 15:02:09.207000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=543690
;

-- UI Element: Ausgehende Belege(540170,de.metas.document.archive) -> Ausgehende Belege(540474,de.metas.document.archive) -> main -> 20 -> flags.EDI Status exportieren
-- Column: C_Doc_Outbound_Log.EDI_ExportStatus
-- 2026-01-05T15:02:09.600Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=220,Updated=TO_TIMESTAMP('2026-01-05 15:02:09.600000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=543705
;

-- UI Element: Ausgehende Belege(540170,de.metas.document.archive) -> Ausgehende Belege(540474,de.metas.document.archive) -> main -> 20 -> org.Sektion
-- Column: C_Doc_Outbound_Log.AD_Org_ID
-- 2026-01-05T15:02:10.003Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=230,Updated=TO_TIMESTAMP('2026-01-05 15:02:10.003000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=543700
;

