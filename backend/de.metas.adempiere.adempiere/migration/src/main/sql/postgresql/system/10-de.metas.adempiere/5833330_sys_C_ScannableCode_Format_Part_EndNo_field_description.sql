-- C_ScannableCode_Format_Part.EndNo is optional: tell the user what an empty End No means.
-- Per-field description on the window field only (the shared AD_Element 1318 stays untouched).

UPDATE AD_Field
SET Description = 'Leer lassen: bis zum Ende des Codes bzw. bis zur Position vor dem nächsten Teil lesen.',
    Updated     = TO_TIMESTAMP('2026-11-05 19:00:00', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy   = 100
WHERE AD_Field_ID = 742020;

UPDATE AD_Field_Trl SET Description = 'Leer lassen: bis zum Ende des Codes bzw. bis zur Position vor dem nächsten Teil lesen.', IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-11-05 19:00:00', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100 WHERE AD_Field_ID = 742020 AND AD_Language IN ('de_DE', 'de_CH');
UPDATE AD_Field_Trl SET Description = 'Leave empty: read to the end of the code, or up to the position before the next part.', IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-11-05 19:00:00', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100 WHERE AD_Field_ID = 742020 AND AD_Language = 'en_US';
UPDATE AD_Field_Trl SET Description = 'Laisser vide : lire jusqu''à la fin du code, ou jusqu''à la position avant la partie suivante.', IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-11-05 19:00:00', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100 WHERE AD_Field_ID = 742020 AND AD_Language = 'fr_CH';
