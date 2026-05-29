-- Run mode: SWING_CLIENT

-- UI Element: Transport Auftrag(540020,METAS_SHIPPING) -> Speditionslieferung(540096,METAS_SHIPPING) -> main -> 20 -> doctype.ETD
-- Column: M_ShipperTransportation.ETD
-- 2025-10-03T13:26:27.742Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=540669, SeqNo=70,Updated=TO_TIMESTAMP('2025-10-03 13:26:27.742000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637548
;

-- UI Element: Transport Auftrag(540020,METAS_SHIPPING) -> Speditionslieferung(540096,METAS_SHIPPING) -> main -> 20 -> doctype.ETA
-- Column: M_ShipperTransportation.ETA
-- 2025-10-03T13:28:24.097Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=540669, SeqNo=80,Updated=TO_TIMESTAMP('2025-10-03 13:28:24.096000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637549
;

-- UI Element: Transport Auftrag(540020,METAS_SHIPPING) -> Speditionslieferung(540096,METAS_SHIPPING) -> main -> 20 -> doctype.ATD
-- Column: M_ShipperTransportation.ATD
-- 2025-10-03T13:28:51.687Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=540669, SeqNo=90,Updated=TO_TIMESTAMP('2025-10-03 13:28:51.687000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637550
;

-- UI Element: Transport Auftrag(540020,METAS_SHIPPING) -> Speditionslieferung(540096,METAS_SHIPPING) -> main -> 20 -> doctype.ATA
-- Column: M_ShipperTransportation.ATA
-- 2025-10-03T13:29:34.190Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=540669, SeqNo=100,Updated=TO_TIMESTAMP('2025-10-03 13:29:34.190000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637551
;

-- UI Element: Transport Auftrag(540020,METAS_SHIPPING) -> Speditionslieferung(540096,METAS_SHIPPING) -> main -> 20 -> doctype.CRD
-- Column: M_ShipperTransportation.CRD
-- 2025-10-03T13:29:59.230Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=540669, SeqNo=110,Updated=TO_TIMESTAMP('2025-10-03 13:29:59.229000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637552
;

-- UI Element: Transport Auftrag(540020,METAS_SHIPPING) -> Speditionslieferung(540096,METAS_SHIPPING) -> main -> 20 -> flags.Buchung bestätigt
-- Column: M_ShipperTransportation.IsBookingConfirmed
-- 2025-10-03T13:31:06.825Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=553415, SeqNo=20,Updated=TO_TIMESTAMP('2025-10-03 13:31:06.825000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637553
;

-- UI Element: Transport Auftrag(540020,METAS_SHIPPING) -> Speditionslieferung(540096,METAS_SHIPPING) -> main -> 20 -> flags.B/L erhalten
-- Column: M_ShipperTransportation.IsBLReceived
-- 2025-10-03T13:32:04.423Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=553415, SeqNo=30,Updated=TO_TIMESTAMP('2025-10-03 13:32:04.423000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637556
;

-- UI Element: Transport Auftrag(540020,METAS_SHIPPING) -> Speditionslieferung(540096,METAS_SHIPPING) -> main -> 20 -> doctype.B/L Datum
-- Column: M_ShipperTransportation.BLDate
-- 2025-10-03T13:32:37.887Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=540669, SeqNo=120,Updated=TO_TIMESTAMP('2025-10-03 13:32:37.886000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637557
;

-- UI Element: Transport Auftrag(540020,METAS_SHIPPING) -> Speditionslieferung(540096,METAS_SHIPPING) -> main -> 20 -> flags.WE Avis
-- Column: M_ShipperTransportation.IsWENotice
-- 2025-10-03T13:33:11.004Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=553415, SeqNo=40,Updated=TO_TIMESTAMP('2025-10-03 13:33:11.004000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637558
;

-- UI Column: Transport Auftrag(540020,METAS_SHIPPING) -> Speditionslieferung(540096,METAS_SHIPPING) -> main -> 20
-- UI Element Group: dates
-- 2025-10-03T13:42:10.667Z
UPDATE AD_UI_ElementGroup SET Name='dates',Updated=TO_TIMESTAMP('2025-10-03 13:42:10.667000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=540669
;

-- UI Column: Transport Auftrag(540020,METAS_SHIPPING) -> Speditionslieferung(540096,METAS_SHIPPING) -> main -> 20
-- UI Element Group: doctype
-- 2025-10-03T13:42:51.998Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540388,553604,TO_TIMESTAMP('2025-10-03 13:42:51.068000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','doctype',2,TO_TIMESTAMP('2025-10-03 13:42:51.068000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Transport Auftrag(540020,METAS_SHIPPING) -> Speditionslieferung(540096,METAS_SHIPPING) -> main -> 20 -> doctype.Belegart
-- Column: M_ShipperTransportation.C_DocType_ID
-- 2025-10-03T13:43:24.743Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=553604, SeqNo=10,Updated=TO_TIMESTAMP('2025-10-03 13:43:24.743000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=545705
;

-- UI Element: Transport Auftrag(540020,METAS_SHIPPING) -> Speditionslieferung(540096,METAS_SHIPPING) -> main -> 20 -> doctype.Beleg Nr.
-- Column: M_ShipperTransportation.DocumentNo
-- 2025-10-03T13:43:42.999Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=553604, SeqNo=20,Updated=TO_TIMESTAMP('2025-10-03 13:43:42.999000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=545706
;

-- UI Element: Transport Auftrag(540020,METAS_SHIPPING) -> Speditionslieferung(540096,METAS_SHIPPING) -> main -> 20 -> doctype.Belegdatum
-- Column: M_ShipperTransportation.DateDoc
-- 2025-10-03T14:00:25.275Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=553604, SeqNo=30,Updated=TO_TIMESTAMP('2025-10-03 14:00:25.274000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=545707
;
