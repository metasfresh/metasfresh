-- AD_Element 577279 (PickupTimeTo) is used by 4 AD_Column rows. Its en_US AD_Element_Trl row carried
-- the German text verbatim while flagged IsTranslated='Y'. fr_CH carries the same German text but is
-- flagged 'N', which is correct for an untranslated fallback, so it is left untouched.
UPDATE AD_Element_Trl
   SET Name='Pickup Time To', Description='Pickup Time To', IsTranslated='Y',
       Updated=TO_TIMESTAMP('2026-08-27 15:15:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
 WHERE AD_Element_ID=577279 AND AD_Language='en_US'
;

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(577279, 'en_US');
