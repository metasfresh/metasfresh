-- Element 585530 "Abrufvertrag" (caption of C_OrderLine.C_Flatrate_Term_ID on the sales and purchase order line tabs):
-- fr_CH is not translated and shows the German text; give it the current German description (it still had an
-- earlier wording) and propagate it to the fields.

UPDATE AD_Element_Trl trl
SET Description = e.Description,
    Updated     = TO_TIMESTAMP('2026-10-09 19:10:01', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy   = 100
FROM AD_Element e
WHERE e.AD_Element_ID = trl.AD_Element_ID
  AND trl.AD_Element_ID = 585530
  AND trl.AD_Language = 'fr_CH'
  AND trl.IsTranslated = 'N'
;
/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(585530, 'fr_CH')
;
