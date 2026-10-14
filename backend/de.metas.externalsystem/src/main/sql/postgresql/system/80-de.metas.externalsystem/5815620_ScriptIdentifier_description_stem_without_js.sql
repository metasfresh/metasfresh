-- Correct the misleading description of the shared ScriptIdentifier element (AD_Element 584103,
-- ColumnName ScriptIdentifier, label "Skript-Kennung"), used by both the scripted EXPORT
-- conversion config (AD_Column 591295) and the scripted IMPORT conversion config (AD_Column 591364).
--
-- Old text ("Name der JavaScript-Datei ...") wrongly implied the full filename WITH the .js
-- extension. The loader appends ".js" to the entered value, so the field must hold the file-name
-- STEM only (e.g. edifact2olcand for the file edifact2olcand.js); entering ".js" breaks loading.
--
-- This mutates the SHARED element (the corrected wording is correct in every usage) and lets the
-- standard AD translation propagation cascade the new text to the two AD_Columns, their AD_Fields
-- (all AD_Name_ID IS NULL -> inherit from the column) and the _Trl rows of each. AD_UI_Element has
-- no _Trl and is not covered by the propagation functions, so its Description is corrected directly.
-- Base AD language on the target stack is de_CH; the German text therefore also lands in the base
-- Description columns via propagation.

-- 1) AD_Element_Trl: German languages (base de_CH + de_DE) -- authoritative German text
UPDATE AD_Element_Trl
SET Description = 'Kennung des JavaScript-Konvertierungsskripts: der Dateiname ohne die Endung .js (z. B. edifact2olcand für die Datei edifact2olcand.js).',
    IsTranslated = 'Y',
    Updated = TO_TIMESTAMP('2026-07-22 10:00:00', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Element_ID = 584103 AND AD_Language IN ('de_DE', 'de_CH');

-- 2) AD_Element_Trl: English
UPDATE AD_Element_Trl
SET Description = 'Identifier of the JavaScript conversion script: the file name without the .js extension (e.g. edifact2olcand for the file edifact2olcand.js).',
    IsTranslated = 'Y',
    Updated = TO_TIMESTAMP('2026-07-22 10:00:01', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Element_ID = 584103 AND AD_Language = 'en_US';

-- 3) AD_Element_Trl: fr_CH / it_CH -- German fallback text (fr/it not invented); left IsTranslated='N'
UPDATE AD_Element_Trl
SET Description = 'Kennung des JavaScript-Konvertierungsskripts: der Dateiname ohne die Endung .js (z. B. edifact2olcand für die Datei edifact2olcand.js).',
    IsTranslated = 'N',
    Updated = TO_TIMESTAMP('2026-07-22 10:00:02', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Element_ID = 584103 AND AD_Language IN ('fr_CH', 'it_CH');

-- 4) AD_Element base Description (de_CH is the base language -> German text)
UPDATE AD_Element
SET Description = 'Kennung des JavaScript-Konvertierungsskripts: der Dateiname ohne die Endung .js (z. B. edifact2olcand für die Datei edifact2olcand.js).',
    Updated = TO_TIMESTAMP('2026-07-22 10:00:03', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Element_ID = 584103;

-- 5) Propagate the corrected element text to AD_Column(_Trl) and AD_Field(_Trl) for all languages
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584103);

-- 6) AD_UI_Element has no _Trl companion and is not covered by propagation -> correct directly.
--    All four UI elements render the ScriptIdentifier field (export tabs + import tabs).
UPDATE AD_UI_Element
SET Description = 'Kennung des JavaScript-Konvertierungsskripts: der Dateiname ohne die Endung .js (z. B. edifact2olcand für die Datei edifact2olcand.js).',
    Updated = TO_TIMESTAMP('2026-07-22 10:00:04', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_UI_Element_ID IN (637781, 637855, 637874, 637883);
