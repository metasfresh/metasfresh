-- AD_Element 215 (C_UOM_ID) is shared by 170 AD_Column rows and 3 AD_Process_Para, so fixing it on
-- the element fixes it everywhere. Its de_CH row was a verbatim copy of de_DE and carried 'ß';
-- Swiss German writes 'ss'. Orthography only -- not a content divergence -- so no fork.
UPDATE AD_Element_Trl
   SET Name='Masseinheit', Description='Masseinheit', IsTranslated='Y',
       Updated=TO_TIMESTAMP('2026-08-27 15:10:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
 WHERE AD_Element_ID=215 AND AD_Language='de_CH'
;

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(215, 'de_CH');
