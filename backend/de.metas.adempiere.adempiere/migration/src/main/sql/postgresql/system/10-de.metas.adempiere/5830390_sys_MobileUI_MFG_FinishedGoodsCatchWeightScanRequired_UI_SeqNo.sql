-- MobileUI Manufacturing: the "Finished goods: require catch-weight scan" UI elements (655049, 655050, added by 5829760 at SeqNo 100)
-- shared SeqNo 100 with "Vereinnahmen ohne Packvorschrift erlauben" (653685, 653686, added by 5821230) in the same element groups
-- 551690 / 550042, which made their display order undefined. Show the scan flag after it.

-- 2026-10-20 21:30:00
UPDATE AD_UI_Element SET SeqNo=110, SeqNoGrid=110, Updated=TO_TIMESTAMP('2026-10-20 21:30:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE AD_UI_Element_ID IN (655049, 655050)
;
