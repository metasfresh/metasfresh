-- #############################################################################
-- Migration: rename ExternalSystem_Endpoint.SftpProcessedDirectory -> ProcessedDirectory
-- and SftpErrorDirectory -> ErrorDirectory, and relabel/redescribe them as LOCAL,
-- transport-agnostic archive folders used by BOTH the SFTP and the REST scripted-import
-- flow (not remote SFTP-only folders as the original naming implied).
--
-- Background: these two columns were added by 5814450 as SFTP-only settings (moved from
-- the config table, direction-tagged "(nur Import)"/"(import only)" by 5814550/5814570).
-- Live-testing on 2026-07-20 established that these folders are actually LOCAL container
-- paths (the archive location after a successful/failed import), useful for the REST
-- import flow too, not a remote SFTP-only concept. This migration renames the physical
-- column + AD_Column + drops the "SFTP"/"import only" framing from the AD_Element label
-- and description (DE/EN/FR/IT), and removes the SFTP-only AD_Field.DisplayLogic so both
-- fields are shown for BOTH TransportType values (HTTP/REST and SFTP).
--
-- SftpPollingIntervalMs is intentionally NOT touched -- polling itself remains SFTP-only.
--
-- Both AD_Elements 584679 (SftpProcessedDirectory) and 584680 (SftpErrorDirectory) are
-- verified DEDICATED to this table (only AD_Column usage, no other AD_Column/AD_Field/
-- AD_Window/AD_Tab reference) -- safe to mutate directly, no fork needed.
--
-- AD_Element.ColumnName is UNIQUE system-wide (not per-table). The plain 'ProcessedDirectory'
-- name is already taken by AD_Element 581583, backing an unrelated table's column
-- (ExternalSystem_Config_ProCareManagement_LocalFile.ProcessedDirectory -- a completely
-- different external system's local-file polling feature, no SFTP/REST relation). Its
-- wording ("Bearbeitetes Verzeichnis" / no transport mention) is wrong for our endpoint
-- usage (which must say "SFTP- und REST-Import"), so reusing that shared element would be
-- an incorrect mutation of an unrelated table's label -- per the mutate-vs-fork rule, this
-- migration therefore keeps AD_Element 584679/584680 as DEDICATED elements and gives them a
-- non-colliding ColumnName using the same 'ExtSysEndpoint_...' prefix already used for the
-- forked elements 585112/585113/585114 (5814560), rather than the bare physical column name.
-- The physical/AD_Column.ColumnName (table-scoped, not globally unique) is still renamed to
-- the plain 'ProcessedDirectory'/'ErrorDirectory' as intended.
--
-- No new AD_Element/AD_Column/AD_Field/AD_UI_Element IDs are required -- this migration
-- renames/relabels the EXISTING dedicated rows (584679/584680, 592968/592969, 781747/
-- 781748, 652677/652678). Only a fresh AD_MigrationScript sequence number was needed:
--   AD_MigrationScript 5815250 (from idserver.metas.de, 2026-07-21)
-- #############################################################################

-- ============================================================================
-- 1. Relabel + redescribe AD_Element 584679 (ProcessedDirectory) and 584680
--    (ErrorDirectory): drop "SFTP" from the label, drop the "(nur Import)"/"(import
--    only)" tag-suffix framing from the description, describe as a LOCAL archive
--    folder used by both SFTP and REST import.
-- ============================================================================

UPDATE AD_Element
SET ColumnName = 'ExtSysEndpoint_ProcessedDirectory',
    Name = 'Verzeichnis (verarbeitet)',
    PrintName = 'Verzeichnis (verarbeitet)',
    Description = 'Lokales Verzeichnis, in das erfolgreich verarbeitete Nutzdaten archiviert werden (SFTP- und REST-Import).',
    Updated = TO_TIMESTAMP('2026-07-21 20:00:00', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Element_ID = 584679;

UPDATE AD_Element_Trl
SET Name = 'Verzeichnis (verarbeitet)', PrintName = 'Verzeichnis (verarbeitet)',
    Description = 'Lokales Verzeichnis, in das erfolgreich verarbeitete Nutzdaten archiviert werden (SFTP- und REST-Import).',
    IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-07-21 20:00:01', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Element_ID = 584679 AND AD_Language IN ('de_DE', 'de_CH');

UPDATE AD_Element_Trl
SET Name = 'Processed Directory', PrintName = 'Processed Directory',
    Description = 'Local directory where successfully processed payloads are archived (SFTP and REST import).',
    IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-07-21 20:00:02', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Element_ID = 584679 AND AD_Language = 'en_US';

UPDATE AD_Element_Trl
SET Name = 'Répertoire (traité)', PrintName = 'Répertoire (traité)',
    Description = 'Répertoire local où les charges utiles traitées avec succès sont archivées (import SFTP et REST).',
    IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-07-21 20:00:03', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Element_ID = 584679 AND AD_Language = 'fr_CH';

UPDATE AD_Element_Trl
SET Name = 'Directory (elaborati)', PrintName = 'Directory (elaborati)',
    Description = 'Directory locale in cui vengono archiviati i payload elaborati con successo (import SFTP e REST).',
    IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-07-21 20:00:04', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Element_ID = 584679 AND AD_Language = 'it_CH';

UPDATE AD_Element
SET ColumnName = 'ExtSysEndpoint_ErrorDirectory',
    Name = 'Verzeichnis (Fehler)',
    PrintName = 'Verzeichnis (Fehler)',
    Description = 'Lokales Verzeichnis, in das fehlgeschlagene Nutzdaten archiviert werden (SFTP- und REST-Import).',
    Updated = TO_TIMESTAMP('2026-07-21 20:00:05', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Element_ID = 584680;

UPDATE AD_Element_Trl
SET Name = 'Verzeichnis (Fehler)', PrintName = 'Verzeichnis (Fehler)',
    Description = 'Lokales Verzeichnis, in das fehlgeschlagene Nutzdaten archiviert werden (SFTP- und REST-Import).',
    IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-07-21 20:00:06', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Element_ID = 584680 AND AD_Language IN ('de_DE', 'de_CH');

UPDATE AD_Element_Trl
SET Name = 'Error Directory', PrintName = 'Error Directory',
    Description = 'Local directory where failed payloads are archived (SFTP and REST import).',
    IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-07-21 20:00:07', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Element_ID = 584680 AND AD_Language = 'en_US';

UPDATE AD_Element_Trl
SET Name = 'Répertoire (erreur)', PrintName = 'Répertoire (erreur)',
    Description = 'Répertoire local où les charges utiles en erreur sont archivées (import SFTP et REST).',
    IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-07-21 20:00:08', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Element_ID = 584680 AND AD_Language = 'fr_CH';

UPDATE AD_Element_Trl
SET Name = 'Directory (errore)', PrintName = 'Directory (errore)',
    Description = 'Directory locale in cui vengono archiviati i payload non elaborati correttamente (import SFTP e REST).',
    IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-07-21 20:00:09', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Element_ID = 584680 AND AD_Language = 'it_CH';

-- ============================================================================
-- 2. Rename AD_Column (ColumnName + Description) then the physical column.
--    592968 = SftpProcessedDirectory, 592969 = SftpErrorDirectory on
--    ExternalSystem_Endpoint (AD_Table_ID 542551).
-- ============================================================================

UPDATE AD_Column
SET ColumnName = 'ProcessedDirectory',
    Description = 'Lokales Verzeichnis, in das erfolgreich verarbeitete Nutzdaten archiviert werden (SFTP- und REST-Import).',
    Updated = TO_TIMESTAMP('2026-07-21 20:00:10', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Column_ID = 592968;

SELECT public.db_alter_table('ExternalSystem_Endpoint',
	'ALTER TABLE public.ExternalSystem_Endpoint RENAME COLUMN SftpProcessedDirectory TO ProcessedDirectory');

UPDATE AD_Column
SET ColumnName = 'ErrorDirectory',
    Description = 'Lokales Verzeichnis, in das fehlgeschlagene Nutzdaten archiviert werden (SFTP- und REST-Import).',
    Updated = TO_TIMESTAMP('2026-07-21 20:00:11', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Column_ID = 592969;

SELECT public.db_alter_table('ExternalSystem_Endpoint',
	'ALTER TABLE public.ExternalSystem_Endpoint RENAME COLUMN SftpErrorDirectory TO ErrorDirectory');

-- ============================================================================
-- 3. Propagate the element name/description edits down to the derived
--    AD_Column(_Trl)/AD_Field(_Trl) rows -- run BEFORE the explicit AD_Field/
--    AD_UI_Element relabels in step 4, so those explicit values win last
--    (same ordering rule as 5814150's step 3/4 split).
-- ============================================================================

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584679, 'de_DE');
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584679, 'de_CH');
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584679, 'en_US');
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584679, 'fr_CH');
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584679, 'it_CH');
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584680, 'de_DE');
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584680, 'de_CH');
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584680, 'en_US');
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584680, 'fr_CH');
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584680, 'it_CH');

-- ============================================================================
-- 4. Relabel the two AD_Field rows (781747, 781748) on tab 548506 and remove
--    the SFTP-only DisplayLogic so both fields are shown regardless of
--    TransportType (HTTP/REST and SFTP alike). NULL DisplayLogic is the
--    "always shown" convention used by the other unconditional fields on this
--    tab (e.g. Suchschlüssel/Sektion/Aktiv/Mandant/Array-Fan-Out).
-- ============================================================================

UPDATE AD_Field
SET Name = 'Verzeichnis (verarbeitet)',
    Description = 'Lokales Verzeichnis, in das erfolgreich verarbeitete Nutzdaten archiviert werden (SFTP- und REST-Import).',
    DisplayLogic = NULL,
    Updated = TO_TIMESTAMP('2026-07-21 20:00:12', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Field_ID = 781747;

UPDATE AD_Field_Trl
SET Name = 'Verzeichnis (verarbeitet)',
    Description = 'Lokales Verzeichnis, in das erfolgreich verarbeitete Nutzdaten archiviert werden (SFTP- und REST-Import).',
    IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-07-21 20:00:13', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Field_ID = 781747 AND AD_Language = 'de_DE';

UPDATE AD_Field_Trl
SET Name = 'Processed Directory',
    Description = 'Local directory where successfully processed payloads are archived (SFTP and REST import).',
    IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-07-21 20:00:14', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Field_ID = 781747 AND AD_Language = 'en_US';

UPDATE AD_Field_Trl
SET Name = 'Répertoire (traité)',
    Description = 'Répertoire local où les charges utiles traitées avec succès sont archivées (import SFTP et REST).',
    IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-07-21 20:00:15', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Field_ID = 781747 AND AD_Language = 'fr_CH';

UPDATE AD_Field_Trl
SET Name = 'Directory (elaborati)',
    Description = 'Directory locale in cui vengono archiviati i payload elaborati con successo (import SFTP e REST).',
    IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-07-21 20:00:16', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Field_ID = 781747 AND AD_Language = 'it_CH';

UPDATE AD_Field
SET Name = 'Verzeichnis (Fehler)',
    Description = 'Lokales Verzeichnis, in das fehlgeschlagene Nutzdaten archiviert werden (SFTP- und REST-Import).',
    DisplayLogic = NULL,
    Updated = TO_TIMESTAMP('2026-07-21 20:00:17', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Field_ID = 781748;

UPDATE AD_Field_Trl
SET Name = 'Verzeichnis (Fehler)',
    Description = 'Lokales Verzeichnis, in das fehlgeschlagene Nutzdaten archiviert werden (SFTP- und REST-Import).',
    IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-07-21 20:00:18', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Field_ID = 781748 AND AD_Language = 'de_DE';

UPDATE AD_Field_Trl
SET Name = 'Error Directory',
    Description = 'Local directory where failed payloads are archived (SFTP and REST import).',
    IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-07-21 20:00:19', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Field_ID = 781748 AND AD_Language = 'en_US';

UPDATE AD_Field_Trl
SET Name = 'Répertoire (erreur)',
    Description = 'Répertoire local où les charges utiles en erreur sont archivées (import SFTP et REST).',
    IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-07-21 20:00:20', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Field_ID = 781748 AND AD_Language = 'fr_CH';

UPDATE AD_Field_Trl
SET Name = 'Directory (errore)',
    Description = 'Directory locale in cui vengono archiviati i payload non elaborati correttamente (import SFTP e REST).',
    IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-07-21 20:00:21', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Field_ID = 781748 AND AD_Language = 'it_CH';

-- ============================================================================
-- 5. Relabel the two AD_UI_Element rows (652677, 652678) -- name mirrors the
--    field/element label; group placement (554996) and DisplayLogic-driven
--    visibility follow the existing precedent of a field that must show across
--    both transports (AD_Field_ID 755950 "Kennwort" already sits inside the one
--    of the two transport-named groups with a cross-transport display condition
--    -- no group relocation needed).
-- ============================================================================

UPDATE AD_UI_Element
SET Name = 'Verzeichnis (verarbeitet)',
    Updated = TO_TIMESTAMP('2026-07-21 20:00:22', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_UI_Element_ID = 652677;

UPDATE AD_UI_Element
SET Name = 'Verzeichnis (Fehler)',
    Updated = TO_TIMESTAMP('2026-07-21 20:00:23', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_UI_Element_ID = 652678;
