-- 5824060 (already integrated) inserted the en_US AD_Element_Trl override for IsCandidateOwnDetail (585454)
-- with IsTranslated left at 'N' - the identical defect 5824070 fixed for AD_Ref_List_Trl, reintroduced here
-- in the same PR. update_Column_Translation_From_AD_Element then copied that wrong flag straight onto the
-- AD_Column_Trl row (593551) too. Text is unaffected; flags only.

UPDATE AD_Element_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-10 23:20:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language='en_US' AND AD_Element_ID=585454
;

UPDATE AD_Column_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-10 23:20:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language='en_US' AND AD_Column_ID=593551
;
