-- Delivery Planning: fr_CH was left behind when AD_Element 581962 was renamed.
--
-- 5820940 renamed the element to "Lieferplanungen der Lieferanweisung" and propagated the new caption to
-- de_DE, de_CH and en_US. fr_CH is an active system language on this system
-- (AD_Language.IsActive='Y' AND IsSystemLanguage='Y') but was not among them, so its row still carries the
-- element's PREVIOUS caption -- "Delivery Instruction History" -- on both AD_Element_Trl and, through it,
-- AD_Tab_Trl for tab 546754. That is not an untranslated fallback a reader can recognise as such; it is a
-- stale caption for a tab that no longer means that, shown to anyone logged in with fr_CH.
--
-- No French translation exists for this element, so fr_CH is pointed at the en_US wording and left
-- IsTranslated='N' -- the state the seeding INSERTs give every other unhandled language, which is what a
-- translator later filters on.

UPDATE AD_Element_Trl
   SET Name='Delivery Plannings for this Instruction', Description='Delivery Plannings for this Instruction',
       PrintName='Delivery Plannings for this Instruction', IsTranslated='N',
       Updated=TO_TIMESTAMP('2026-08-27 23:30:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
 WHERE AD_Element_ID=581962 AND AD_Language='fr_CH'
;

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(581962, 'fr_CH');
