-- The Zustellstatus (DeliveredState) list VALUES render in English, while the field caption is correct.
--
-- Root cause: on this instance de_DE is the BASE language, and a base-language session never reads the
-- translation table. MLookupFactory#getLookup_List builds two display variants --
--   displayColumnSQL_BaseLang = AD_Ref_List.Name
--   displayColumnSQL_Trl      = trl.Name   (INNER JOIN AD_Ref_List_Trl)
-- -- and the base-language session uses the first. 5822160_sys_M_ShipperTransportation_DeliveredState.sql
-- put the German text into AD_Ref_List_Trl and left AD_Ref_List.Name in English, so the German rows are
-- correct and simply never consulted.
--
-- The sibling list on the same window proves the convention: TransportDirection carries its German
-- directly in AD_Ref_List.Name ('Eingehend' / 'Ausgehend' / 'Streckengeschäft') and renders correctly.
-- So on a German-base-language instance the BASE row holds German and the en_US translation holds English
-- -- the inverse of what 5822160 assumed.
--
-- This is therefore neither a cache nor a missing-data problem: a cache reset, a cookie reset and an app
-- server restart all leave it unchanged, because the value being rendered is the one stored here.
--
-- Only AD_Ref_List.Name/Description are touched. The AD_Ref_List_Trl rows stay exactly as they are: the
-- en_US translation already holds the English wording, so English sessions are unaffected by this change.

UPDATE AD_Ref_List
   SET Name        = 'Nicht zugestellt',
       Description = 'Kein zugeordneter Lieferplan ist zugestellt',
       Updated     = TO_TIMESTAMP('2026-09-14 11:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
 WHERE AD_Ref_List_ID = 544357
;

UPDATE AD_Ref_List
   SET Name        = 'Teilweise zugestellt',
       Description = 'Einige zugeordnete Lieferpläne sind zugestellt, andere nicht',
       Updated     = TO_TIMESTAMP('2026-09-14 11:00:01','YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
 WHERE AD_Ref_List_ID = 544358
;

UPDATE AD_Ref_List
   SET Name        = 'Vollständig zugestellt',
       Description = 'Jeder zugeordnete Lieferplan ist zugestellt',
       Updated     = TO_TIMESTAMP('2026-09-14 11:00:02','YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
 WHERE AD_Ref_List_ID = 544359
;

-- Keep the en_US translation explicitly English, so an English session still reads English once the base
-- row is German. These rows already hold this text; the statement is here so the pairing is not left to
-- depend on what 5822160 happened to seed.

UPDATE AD_Ref_List_Trl
   SET Name = 'Not delivered',  Description = 'No allocation''s planning is delivered',
       IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-09-14 11:00:03','YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
 WHERE AD_Ref_List_ID = 544357 AND AD_Language = 'en_US'
;

UPDATE AD_Ref_List_Trl
   SET Name = 'Partly delivered', Description = 'Some allocations'' plannings are delivered, some are not',
       IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-09-14 11:00:04','YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
 WHERE AD_Ref_List_ID = 544358 AND AD_Language = 'en_US'
;

UPDATE AD_Ref_List_Trl
   SET Name = 'Fully delivered', Description = 'Every allocation''s planning is delivered',
       IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-09-14 11:00:05','YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
 WHERE AD_Ref_List_ID = 544359 AND AD_Language = 'en_US'
;
