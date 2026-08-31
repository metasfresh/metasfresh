-- The ETD/ATD/ETA/ATA dates on AD_Tab 546674 stay editable while a planning is unallocated and
-- become read-only only while it is actively allocated to a delivery instruction.
-- Only ReadOnlyLogic on the 4 existing date fields is updated; no AD_Field is created.
--
-- IDs allocated from idserver.metas.de on 2026-08-28:
--   AD_MigrationScript 5821170 (this file)

UPDATE AD_Field
SET    ReadOnlyLogic = '@IsAllocated@=''Y''',
       Updated       = TO_TIMESTAMP('2026-08-28 09:30:00', 'YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy     = 100
WHERE  AD_Field_ID IN (708098 /*ETD*/, 708099 /*ATD*/, 708095 /*ETA*/, 708096 /*ATA*/)
  AND  AD_Tab_ID = 546674
;
