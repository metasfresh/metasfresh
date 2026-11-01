-- 5823960 (already integrated) inserted the en_US AD_Ref_List_Trl row with IsTranslated left at 'N' (the
-- seed default) and only overrode Description/Name - never flipping IsTranslated to 'Y' for the actual
-- override language, unlike every other AD_Ref_List value in this PR. Text is unaffected; flag only.

UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-10 22:45:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language='en_US' AND AD_Ref_List_ID=544366
;
