-- VAT-ID online check: correct the RestApiBaseURL help text on AD_Field 781903.
--
-- The default VATaxID_Config record seeds RestApiBaseURL with the official EU endpoint, so the field
-- arrives filled in. Its help text told the administrator the opposite -- that there is no pre-filled
-- value and one must be entered before the first check -- which reads as "this is not configured yet"
-- on a field that is already correct, and invites a needless edit.
--
-- AD_Element 585173 is the dedicated element created for this field alone (wired via
-- AD_Field.AD_Name_ID), not the shared column-level element 576182, so updating it here touches
-- nothing else.

UPDATE AD_Element
SET Description = 'Basis-URL des VIES-REST-Dienstes für die USt-IdNr.-Prüfung. Vorbelegt mit dem offiziellen EU-Endpunkt; nur ändern, wenn ein abweichender Dienst verwendet wird.',
    Updated     = TO_TIMESTAMP('2026-08-16 14:10:00', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy   = 100
WHERE AD_Element_ID = 585173;

UPDATE AD_Element_Trl
SET Description  = 'Base URL of the VIES REST service used for VAT-ID checks. Pre-filled with the official EU endpoint; change it only if a different service is used.',
    IsTranslated = 'Y',
    Updated      = TO_TIMESTAMP('2026-08-16 14:10:10', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy    = 100
WHERE AD_Language = 'en_US' AND AD_Element_ID = 585173;

-- de_CH is identical to de_DE here: the text contains no 'ß' and no term with a Swiss variant.
UPDATE AD_Element_Trl
SET Description  = 'Basis-URL des VIES-REST-Dienstes für die USt-IdNr.-Prüfung. Vorbelegt mit dem offiziellen EU-Endpunkt; nur ändern, wenn ein abweichender Dienst verwendet wird.',
    IsTranslated = 'Y',
    Updated      = TO_TIMESTAMP('2026-08-16 14:10:11', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy    = 100
WHERE AD_Language IN ('de_DE', 'de_CH') AND AD_Element_ID = 585173;

-- Any remaining language row still carries the German fallback copied from the element when the row was
-- created, i.e. the very sentence being corrected here. Track the element rather than leave the wrong
-- statement behind in a locale nobody has translated yet.
UPDATE AD_Element_Trl trl
SET Description = e.Description,
    Updated     = TO_TIMESTAMP('2026-08-16 14:10:12', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy   = 100
FROM AD_Element e
WHERE e.AD_Element_ID = trl.AD_Element_ID
  AND trl.AD_Element_ID = 585173
  AND trl.IsTranslated = 'N';

SELECT update_FieldTranslation_From_AD_Name_Element(585173);
