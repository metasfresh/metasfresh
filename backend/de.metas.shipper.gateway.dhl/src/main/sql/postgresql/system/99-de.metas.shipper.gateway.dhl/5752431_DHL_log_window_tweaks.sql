-- Run mode: SWING_CLIENT

-- Field: DHL Versandauftragsprotokoll(541879,D) -> Dhl ShipmentOrder Log(547956,D) -> Erstellt
-- Column: Dhl_ShipmentOrder_Log.Created
-- 2025-04-22T10:53:18.887Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,FacetFilterSeqNo,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,569230,741983,0,547956,0,TO_TIMESTAMP('2025-04-22 10:53:18.693000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Datum, an dem dieser Eintrag erstellt wurde',0,'D',0,'Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.',0,'Y','Y','Y','N','N','N','N','N','N','N',0,'Erstellt',0,0,100,0,1,1,TO_TIMESTAMP('2025-04-22 10:53:18.693000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-04-22T10:53:18.889Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=741983 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-04-22T10:53:18.915Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(245)
;

-- 2025-04-22T10:53:19.061Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=741983
;

-- 2025-04-22T10:53:19.063Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(741983)
;

-- UI Element: DHL Versandauftragsprotokoll(541879,D) -> Dhl ShipmentOrder Log(547956,D) -> main -> 10 -> msg.Erstellt
-- Column: Dhl_ShipmentOrder_Log.Created
-- 2025-04-22T10:55:38.224Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,741983,0,547956,552739,631378,'F',TO_TIMESTAMP('2025-04-22 10:55:38.036000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Datum, an dem dieser Eintrag erstellt wurde','Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','N','N','Y','N','N','N',0,'Erstellt',50,0,0,TO_TIMESTAMP('2025-04-22 10:55:38.036000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: DHL Versandauftragsprotokoll(541879,D) -> Dhl ShipmentOrder Log(547956,D) -> main -> 10 -> msg.Erstellt
-- Column: Dhl_ShipmentOrder_Log.Created
-- 2025-04-22T10:56:21.577Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2025-04-22 10:56:21.577000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=631378
;

-- UI Element: DHL Versandauftragsprotokoll(541879,D) -> Dhl ShipmentOrder Log(547956,D) -> main -> 10 -> msg.Konfig-Zusammenfassung
-- Column: Dhl_ShipmentOrder_Log.ConfigSummary
-- 2025-04-22T10:56:21.583Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2025-04-22 10:56:21.583000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=631362
;

-- UI Element: DHL Versandauftragsprotokoll(541879,D) -> Dhl ShipmentOrder Log(547956,D) -> main -> 20 -> flags.Fehler
-- Column: Dhl_ShipmentOrder_Log.IsError
-- 2025-04-22T10:56:21.588Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2025-04-22 10:56:21.588000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=631367
;

-- UI Element: DHL Versandauftragsprotokoll(541879,D) -> Dhl ShipmentOrder Log(547956,D) -> main -> 10 -> msg.Nachricht anfordern
-- Column: Dhl_ShipmentOrder_Log.RequestMessage
-- 2025-04-22T10:56:21.593Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2025-04-22 10:56:21.593000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=631363
;

-- UI Element: DHL Versandauftragsprotokoll(541879,D) -> Dhl ShipmentOrder Log(547956,D) -> main -> 10 -> msg.Antwortnachricht
-- Column: Dhl_ShipmentOrder_Log.ResponseMessage
-- 2025-04-22T10:56:21.598Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2025-04-22 10:56:21.598000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=631364
;

-- UI Element: DHL Versandauftragsprotokoll(541879,D) -> Dhl ShipmentOrder Log(547956,D) -> main -> 10 -> msg.Probleme
-- Column: Dhl_ShipmentOrder_Log.AD_Issue_ID
-- 2025-04-22T10:56:21.603Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2025-04-22 10:56:21.603000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=631365
;

