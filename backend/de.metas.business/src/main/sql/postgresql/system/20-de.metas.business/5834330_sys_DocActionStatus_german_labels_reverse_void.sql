-- Align the German names of the cancel doc actions and doc statuses (base name + de_DE + de_CH):
--   DocAction RC (Reverse-Correct, AD_Ref_List_ID 184): "Storno" / de_CH "Verarbeitung (Storno)" -> "Stornieren"
--   DocAction VO (Void,            AD_Ref_List_ID 182): "Stornieren" -> "Annullieren"
--   DocStatus RE (Reversed,        AD_Ref_List_ID 176): "Rückgängig" -> "Storniert"
--   DocStatus VO (Voided,          AD_Ref_List_ID 172): "Storniert"  -> "Annulliert"
-- Only the German names change. English and the other languages are untouched; Invalidate (IN, 187) is untouched.

UPDATE AD_Ref_List
SET Name='Stornieren', Updated=TO_TIMESTAMP('2026-11-11 15:30:00', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Ref_List_ID = 184;

UPDATE AD_Ref_List
SET Name='Annullieren', Updated=TO_TIMESTAMP('2026-11-11 15:30:01', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Ref_List_ID = 182;

UPDATE AD_Ref_List
SET Name='Storniert', Updated=TO_TIMESTAMP('2026-11-11 15:30:02', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Ref_List_ID = 176;

UPDATE AD_Ref_List
SET Name='Annulliert', Updated=TO_TIMESTAMP('2026-11-11 15:30:03', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Ref_List_ID = 172;

UPDATE AD_Ref_List_Trl
SET Name='Stornieren', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-11-11 15:30:04', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Ref_List_ID = 184 AND AD_Language IN ('de_DE', 'de_CH');

UPDATE AD_Ref_List_Trl
SET Name='Annullieren', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-11-11 15:30:05', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Ref_List_ID = 182 AND AD_Language IN ('de_DE', 'de_CH');

UPDATE AD_Ref_List_Trl
SET Name='Storniert', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-11-11 15:30:06', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Ref_List_ID = 176 AND AD_Language IN ('de_DE', 'de_CH');

UPDATE AD_Ref_List_Trl
SET Name='Annulliert', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-11-11 15:30:07', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Ref_List_ID = 172 AND AD_Language IN ('de_DE', 'de_CH');
