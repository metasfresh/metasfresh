-- Run mode: SWING_CLIENT

-- Field: Wareneingangsdisposition - Einkauf_OLD(541954,de.metas.inoutcandidate) -> Wareneingangsdisposition(548451,de.metas.inoutcandidate) -> Bestätigt durch Lieferant
-- Column: M_ReceiptSchedule.IsConfirmedBySupplier
-- 2026-07-02T12:39:05.989Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,FacetFilterSeqNo,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,591673,781319,0,548451,0,TO_TIMESTAMP('2026-07-02 12:39:04.697000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0,'D',0,0,'Y','Y','Y','N','N','N','N','N','N','N',0,'Bestätigt durch Lieferant',0,0,420,0,1,1,TO_TIMESTAMP('2026-07-02 12:39:04.697000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-07-02T12:39:06.058Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=781319 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-07-02T12:39:06.315Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584306)
;

-- 2026-07-02T12:39:06.410Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=781319
;

-- 2026-07-02T12:39:06.481Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(781319)
;

-- UI Element: Wareneingangsdisposition - Einkauf_OLD(541954,de.metas.inoutcandidate) -> Wareneingangsdisposition(548451,de.metas.inoutcandidate) -> main -> 20 -> flags.Bestätigt durch Lieferant
-- Column: M_ReceiptSchedule.IsConfirmedBySupplier
-- 2026-07-02T12:40:02.578Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,781319,0,548451,553603,652427,'F',TO_TIMESTAMP('2026-07-02 12:40:01.736000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Bestätigt durch Lieferant',40,0,0,TO_TIMESTAMP('2026-07-02 12:40:01.736000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Wareneingangsdisposition - Einkauf_OLD(541954,de.metas.inoutcandidate) -> Wareneingangsdisposition(548451,de.metas.inoutcandidate) -> main -> 20 -> flags.Bestätigt durch Lieferant
-- Column: M_ReceiptSchedule.IsConfirmedBySupplier
-- 2026-07-02T12:40:20.231Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=200,Updated=TO_TIMESTAMP('2026-07-02 12:40:20.230000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=652427
;

-- UI Element: Wareneingangsdisposition - Einkauf_OLD(541954,de.metas.inoutcandidate) -> Wareneingangsdisposition(548451,de.metas.inoutcandidate) -> main -> 10 -> logistics.Container-Nr.
-- Column: M_ReceiptSchedule.ContainerNo
-- 2026-07-02T12:40:20.631Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=210,Updated=TO_TIMESTAMP('2026-07-02 12:40:20.630000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637646
;

-- UI Element: Wareneingangsdisposition - Einkauf_OLD(541954,de.metas.inoutcandidate) -> Wareneingangsdisposition(548451,de.metas.inoutcandidate) -> main -> 10 -> logistics.Tracking-Nr.
-- Column: M_ReceiptSchedule.TrackingID
-- 2026-07-02T12:40:21.076Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=220,Updated=TO_TIMESTAMP('2026-07-02 12:40:21.076000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637647
;

-- UI Element: Wareneingangsdisposition - Einkauf_OLD(541954,de.metas.inoutcandidate) -> Wareneingangsdisposition(548451,de.metas.inoutcandidate) -> main -> 20 -> flags.B/L erhalten
-- Column: M_ReceiptSchedule.IsBLReceived
-- 2026-07-02T12:40:21.526Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=230,Updated=TO_TIMESTAMP('2026-07-02 12:40:21.526000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637648
;

-- UI Element: Wareneingangsdisposition - Einkauf_OLD(541954,de.metas.inoutcandidate) -> Wareneingangsdisposition(548451,de.metas.inoutcandidate) -> main -> 20 -> flags.WE Avis
-- Column: M_ReceiptSchedule.IsWENotice
-- 2026-07-02T12:40:22.268Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=240,Updated=TO_TIMESTAMP('2026-07-02 12:40:22.268000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637650
;

-- UI Element: Wareneingangsdisposition - Einkauf_OLD(541954,de.metas.inoutcandidate) -> Wareneingangsdisposition(548451,de.metas.inoutcandidate) -> main -> 10 -> logistics.Vessel Name
-- Column: M_ReceiptSchedule.VesselName
-- 2026-07-02T12:40:22.680Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=250,Updated=TO_TIMESTAMP('2026-07-02 12:40:22.680000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637651
;

-- UI Element: Wareneingangsdisposition - Einkauf_OLD(541954,de.metas.inoutcandidate) -> Wareneingangsdisposition(548451,de.metas.inoutcandidate) -> main -> 10 -> logistics.POL
-- Column: M_ReceiptSchedule.POL_ID
-- 2026-07-02T12:40:23.101Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=260,Updated=TO_TIMESTAMP('2026-07-02 12:40:23.101000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637652
;

-- UI Element: Wareneingangsdisposition - Einkauf_OLD(541954,de.metas.inoutcandidate) -> Wareneingangsdisposition(548451,de.metas.inoutcandidate) -> main -> 10 -> logistics.POD
-- Column: M_ReceiptSchedule.POD_ID
-- 2026-07-02T12:40:23.813Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=270,Updated=TO_TIMESTAMP('2026-07-02 12:40:23.813000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637653
;

-- UI Element: Wareneingangsdisposition - Einkauf_OLD(541954,de.metas.inoutcandidate) -> Wareneingangsdisposition(548451,de.metas.inoutcandidate) -> main -> 10 -> logistics.Spediteur
-- Column: M_ReceiptSchedule.Shipper_BPartner_ID
-- 2026-07-02T12:40:24.216Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=280,Updated=TO_TIMESTAMP('2026-07-02 12:40:24.215000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637654
;

-- UI Element: Wareneingangsdisposition - Einkauf_OLD(541954,de.metas.inoutcandidate) -> Wareneingangsdisposition(548451,de.metas.inoutcandidate) -> main -> 20 -> org.Verarbeitet
-- Column: M_ReceiptSchedule.Processed
-- 2026-07-02T12:40:24.627Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=290,Updated=TO_TIMESTAMP('2026-07-02 12:40:24.627000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637585
;

-- UI Element: Wareneingangsdisposition - Einkauf_OLD(541954,de.metas.inoutcandidate) -> Wareneingangsdisposition(548451,de.metas.inoutcandidate) -> main -> 10 -> target.Ziel-Lager
-- Column: M_ReceiptSchedule.M_Warehouse_Dest_ID
-- 2026-07-02T12:40:25.025Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=300,Updated=TO_TIMESTAMP('2026-07-02 12:40:25.025000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637571
;

-- UI Element: Wareneingangsdisposition - Einkauf_OLD(541954,de.metas.inoutcandidate) -> Wareneingangsdisposition(548451,de.metas.inoutcandidate) -> main -> 20 -> org.Lager
-- Column: M_ReceiptSchedule.M_Warehouse_ID
-- 2026-07-02T12:40:25.443Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=310,Updated=TO_TIMESTAMP('2026-07-02 12:40:25.443000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637582
;

