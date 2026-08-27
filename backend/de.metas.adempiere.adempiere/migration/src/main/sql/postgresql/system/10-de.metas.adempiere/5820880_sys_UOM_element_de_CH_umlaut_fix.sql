-- AD_Element 215 (C_UOM_ID / "Masseinheit" = UOM), used by 170 AD_Column rows + 3 AD_Process_Para
-- across the whole system (verified via impact query, incl. M_Delivery_Planning.C_UOM_ID/585130).
-- de_CH carried a verbatim copy-paste of de_DE ("Massheinheit" with sharp s, i.e. 'ß'), which is
-- wrong for Swiss German -- de_CH never uses 'ß', it is written 'ss'. Fixing the shared element is
-- correct everywhere it is used (this is not a de_DE/de_CH content difference, only an orthography
-- rule), so no fork is needed.
UPDATE AD_Element_Trl
   SET Name='Masseinheit', Description='Masseinheit', IsTranslated='Y',
       Updated=TO_TIMESTAMP('2026-08-27 15:10:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
 WHERE AD_Element_ID=215 AND AD_Language='de_CH'
;

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(215, 'de_CH');
