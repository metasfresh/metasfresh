-- me03 #30443 — F01010.4 Invoice Accounting Overrides — fix: German help in AD_Field_Trl
--
-- Task 3's migration (5808490) set override-specific German Help in AD_Field.Help (base column)
-- for fields 710152/710153/710154 (window 541659), but only seeded MISSING _Trl rows.
-- The de_DE/de_CH rows already existed and carried stale element-propagated help text.
-- A direct UPDATE with a fresh timestamp is overwritten by the after-migration full sync
-- (update_TRL_Tables_On_AD_Element_TRL_Update) because the guard "f_trl.updated <> e_trl.updated"
-- passes when the timestamps differ.
--
-- Fix: UPDATE AD_Field_Trl with the override German text AND set Updated to match the
-- corresponding AD_Element_Trl.Updated exactly — the guard then evaluates FALSE and the
-- after-migration sync leaves our rows alone.
--
-- Field 710155 (AccountName) is correct via element-577539 propagation — not touched.
-- en_US rows are not touched.
-- The Updated timestamps used below are copied verbatim from AD_Element_Trl.Updated
-- for the respective element and language (queried from live DB before authoring).

-- 710152 (C_Invoice_ID, element 1008):
--   de_DE element_trl.updated = 2018-11-26 07:57:00.353716
--   de_CH element_trl.updated = 2016-01-15 11:03:03.970374
UPDATE AD_Field_Trl
SET    Help         = 'Die Rechnung, für die dieses Konto überschrieben wird.',
       IsTranslated = 'Y',
       Updated      = TO_TIMESTAMP('2018-11-26 07:57:00.353716', 'YYYY-MM-DD HH24:MI:SS.US'),
       UpdatedBy    = 100
WHERE  AD_Field_ID  = 710152
  AND  AD_Language  = 'de_DE';

UPDATE AD_Field_Trl
SET    Help         = 'Die Rechnung, für die dieses Konto überschrieben wird.',
       IsTranslated = 'Y',
       Updated      = TO_TIMESTAMP('2016-01-15 11:03:03.970374', 'YYYY-MM-DD HH24:MI:SS.US'),
       UpdatedBy    = 100
WHERE  AD_Field_ID  = 710152
  AND  AD_Language  = 'de_CH';

-- 710153 (C_InvoiceLine_ID, element 1076):
--   de_DE element_trl.updated = 2018-11-26 07:57:00.353716
--   de_CH element_trl.updated = 2016-01-15 11:03:03.970374
UPDATE AD_Field_Trl
SET    Help         = 'Die Rechnungsposition, für die dieses Konto überschrieben wird. Leer lassen, um die Überschreibung auf alle Positionen der Rechnung anzuwenden.',
       IsTranslated = 'Y',
       Updated      = TO_TIMESTAMP('2018-11-26 07:57:00.353716', 'YYYY-MM-DD HH24:MI:SS.US'),
       UpdatedBy    = 100
WHERE  AD_Field_ID  = 710153
  AND  AD_Language  = 'de_DE';

UPDATE AD_Field_Trl
SET    Help         = 'Die Rechnungsposition, für die dieses Konto überschrieben wird. Leer lassen, um die Überschreibung auf alle Positionen der Rechnung anzuwenden.',
       IsTranslated = 'Y',
       Updated      = TO_TIMESTAMP('2016-01-15 11:03:03.970374', 'YYYY-MM-DD HH24:MI:SS.US'),
       UpdatedBy    = 100
WHERE  AD_Field_ID  = 710153
  AND  AD_Language  = 'de_CH';

-- 710154 (C_AcctSchema_ID, element 181):
--   de_DE element_trl.updated = 2018-11-26 07:57:00.353716
--   de_CH element_trl.updated = 2019-12-16 13:09:53
UPDATE AD_Field_Trl
SET    Help         = 'Das Buchführungsschema, in dessen Kontenrahmen das überschreibende Konto gesucht wird.',
       IsTranslated = 'Y',
       Updated      = TO_TIMESTAMP('2018-11-26 07:57:00.353716', 'YYYY-MM-DD HH24:MI:SS.US'),
       UpdatedBy    = 100
WHERE  AD_Field_ID  = 710154
  AND  AD_Language  = 'de_DE';

UPDATE AD_Field_Trl
SET    Help         = 'Das Buchführungsschema, in dessen Kontenrahmen das überschreibende Konto gesucht wird.',
       IsTranslated = 'Y',
       Updated      = TO_TIMESTAMP('2019-12-16 13:09:53', 'YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy    = 100
WHERE  AD_Field_ID  = 710154
  AND  AD_Language  = 'de_CH';
