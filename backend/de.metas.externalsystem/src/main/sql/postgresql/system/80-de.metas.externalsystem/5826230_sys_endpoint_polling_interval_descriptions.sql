-- Brings the two polling-interval field descriptions in line with their labels, which were renamed from
-- "Abfragefrequenz / Polling Frequency" to "Abfrageintervall / Polling Interval" (script 5826210). Both
-- descriptions still read "Wie oft ... geprueft wird" / "How often ... is checked" -- the frequency reading
-- the rename removed, so the operator sees a corrected label beside an uncorrected tooltip.
--
-- Two elements, because they name the same concept on the two transports and the label rename already
-- treats the SFTP one as its consistency anchor:
--   AD_Element 585488 -- the LOCAL_FILE field label element (no ColumnName; used by AD_Field 785060 only)
--   AD_Element 584678 -- SftpPollingIntervalMs
--
-- Text only: no Name/PrintName, no column, no behaviour. fr_CH on 585488 is an untranslated placeholder
-- mirroring the German (IsTranslated stays 'N'); fr_CH on 584678 is a real translation and is corrected
-- the same way as the other languages. AD_Element itself and the AD_Column/AD_Field translations are
-- updated by the propagation call after each element.
--
-- IDs allocated from idserver.metas.de on 2026-09-24:
--   AD_MigrationScript 5826230 (this script)

-- ============================================================
-- AD_Element 585488 -- LOCAL_FILE polling interval
-- ============================================================
UPDATE AD_Element_Trl
SET Description = 'Zeitlicher Abstand zwischen zwei Abfragen des lokalen Verzeichnisses auf neue Dateien, in Millisekunden.',
    Updated     = TO_TIMESTAMP('2026-09-24 12:00:10', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy   = 100
WHERE AD_Element_ID = 585488 AND AD_Language IN ('de_DE', 'de_CH');

UPDATE AD_Element_Trl
SET Description = 'Time between two polls of the local directory for new files, in milliseconds.',
    Updated     = TO_TIMESTAMP('2026-09-24 12:00:20', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy   = 100
WHERE AD_Element_ID = 585488 AND AD_Language = 'en_US';

-- fr_CH is an untranslated placeholder mirroring the German text (IsTranslated stays 'N').
UPDATE AD_Element_Trl
SET Description = 'Zeitlicher Abstand zwischen zwei Abfragen des lokalen Verzeichnisses auf neue Dateien, in Millisekunden.',
    Updated     = TO_TIMESTAMP('2026-09-24 12:00:30', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy   = 100
WHERE AD_Element_ID = 585488 AND AD_Language = 'fr_CH';

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585488);

-- ============================================================
-- AD_Element 584678 -- SFTP polling interval (SftpPollingIntervalMs)
-- ============================================================
UPDATE AD_Element_Trl
SET Description = 'Zeitlicher Abstand zwischen zwei Abfragen des SFTP-Servers auf neue Dateien, in Millisekunden (Standard: 60000 = 1 Minute) (nur Import)',
    Updated     = TO_TIMESTAMP('2026-09-24 12:01:10', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy   = 100
WHERE AD_Element_ID = 584678 AND AD_Language IN ('de_DE', 'de_CH');

UPDATE AD_Element_Trl
SET Description = 'Time between two polls of the SFTP server for new files, in milliseconds (default: 60000 = 1 minute) (import only)',
    Updated     = TO_TIMESTAMP('2026-09-24 12:01:20', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy   = 100
WHERE AD_Element_ID = 584678 AND AD_Language = 'en_US';

UPDATE AD_Element_Trl
SET Description = 'Intervalle entre deux vérifications des nouveaux fichiers sur le serveur SFTP, en millisecondes (par défaut : 60000 = 1 minute) (import uniquement)',
    Updated     = TO_TIMESTAMP('2026-09-24 12:01:30', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy   = 100
WHERE AD_Element_ID = 584678 AND AD_Language = 'fr_CH';

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584678);
