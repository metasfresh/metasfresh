-- 2026-05-30
-- Tax Declaration — rename AD_Process 585627 "Check Drift" → "Check Correction Need".
-- me03 epic 28717 (issue 29632). See https://github.com/metasfresh/me03/issues/29632

-- Base (DE) name + description
UPDATE AD_Process
SET Name = 'Berichtigungsbedarf prüfen',
    Description = 'Prüft, ob für diese Voranmeldung eine Berichtigung erforderlich ist.',
    Updated = TIMESTAMP '2026-05-30 00:00:00', UpdatedBy = 100
WHERE AD_Process_ID = 585627;

-- Non-translated Trl rows mirror the base (DE) text
UPDATE AD_Process_Trl
SET Name = 'Berichtigungsbedarf prüfen',
    Description = 'Prüft, ob für diese Voranmeldung eine Berichtigung erforderlich ist.',
    Updated = TIMESTAMP '2026-05-30 00:00:00', UpdatedBy = 100
WHERE AD_Process_ID = 585627 AND IsTranslated = 'N';

-- en_US translation
UPDATE AD_Process_Trl
SET Name = 'Check Correction Need',
    Description = 'Check whether this declaration requires a correction.',
    IsTranslated = 'Y',
    Updated = TIMESTAMP '2026-05-30 00:00:01', UpdatedBy = 100
WHERE AD_Language = 'en_US' AND AD_Process_ID = 585627;
