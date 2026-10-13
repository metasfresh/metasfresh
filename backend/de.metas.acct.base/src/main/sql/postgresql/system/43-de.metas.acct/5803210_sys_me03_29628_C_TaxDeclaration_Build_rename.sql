-- Tax Declaration: rename C_TaxDeclaration_Build process (AD_Process_ID=585615)
-- DE base: "Zeilen erstellen" (was "Steuererklärung aufbauen" — not ERP-idiomatic)
-- EN trl:  "Create lines" (was "Build Tax Declaration")
-- Description added (was empty)

-- 1. Base record (German is base language)
UPDATE AD_Process
SET Name = 'Zeilen erstellen',
    Description = 'Erstellt die Steuererklärungszeilen durch Aggregation der Fact_Acct-Einträge nach Steuercode.',
    Updated = TIMESTAMP '2026-05-19 00:00:00', UpdatedBy = 100
WHERE AD_Process_ID = 585615;

-- 2. German translations (mark as translated so update_TRL_Tables / re-runs don't overwrite)
UPDATE AD_Process_Trl
SET IsTranslated = 'Y',
    Name = 'Zeilen erstellen',
    Description = 'Erstellt die Steuererklärungszeilen durch Aggregation der Fact_Acct-Einträge nach Steuercode.',
    Updated = TIMESTAMP '2026-05-19 00:00:00', UpdatedBy = 100
WHERE AD_Process_ID = 585615 AND AD_Language IN ('de_DE', 'de_CH');

-- 3. English translation
UPDATE AD_Process_Trl
SET IsTranslated = 'Y',
    Name = 'Create lines',
    Description = 'Creates the tax declaration lines by aggregating Fact_Acct entries by VAT code.',
    Updated = TIMESTAMP '2026-05-19 00:00:00', UpdatedBy = 100
WHERE AD_Process_ID = 585615 AND AD_Language = 'en_US';

-- 4. Other untranslated languages (e.g. fr_CH) — propagate base text so they don't keep the
--    old name. IsTranslated stays 'N' so a later translator overrides them.
UPDATE AD_Process_Trl
SET Name = 'Zeilen erstellen',
    Description = 'Erstellt die Steuererklärungszeilen durch Aggregation der Fact_Acct-Einträge nach Steuercode.',
    Updated = TIMESTAMP '2026-05-19 00:00:00', UpdatedBy = 100
WHERE AD_Process_ID = 585615 AND IsTranslated = 'N';
