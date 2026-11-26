-- Place the "Gebinde leeren erlauben" / "Vor Buchung bestätigen" flags after the other flags of the group.
-- 5822970 inserted them with SeqNo/SeqNoGrid 30/40, which are also used by "Receive Unit Type" (5789610)
-- and "MHD bearbeitbar" (5809490) in the same element group 551690.

-- UI Element: MobileUI Manufacturing Configuration(541788,D) -> MobileUI Manufacturing Configuration(547483,D) -> main -> 30 -> flags.Gebinde leeren erlauben
-- Column: MobileUI_MFG_Config.IsAllowEmptyingHUs
-- 2026-10-07T08:30:00.000Z
UPDATE AD_UI_Element SET SeqNo=60, SeqNoGrid=60,Updated=TO_TIMESTAMP('2026-10-07 08:30:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=654723
;

-- UI Element: MobileUI Manufacturing Configuration(541788,D) -> MobileUI Manufacturing Configuration(547483,D) -> main -> 30 -> flags.Vor Buchung bestätigen
-- Column: MobileUI_MFG_Config.IsConfirmEmptyingHU
-- 2026-10-07T08:30:01.000Z
UPDATE AD_UI_Element SET SeqNo=70, SeqNoGrid=70,Updated=TO_TIMESTAMP('2026-10-07 08:30:01.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=654724
;
