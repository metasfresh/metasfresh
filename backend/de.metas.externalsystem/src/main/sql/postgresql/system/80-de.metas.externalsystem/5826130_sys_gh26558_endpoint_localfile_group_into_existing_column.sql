-- Packzettel Import — host the LOCAL_FILE field group in an existing UI column instead of a third one
--
-- 5826060_sys_gh26558_endpoint_localfile_fields.sql put the LOCAL_FILE element group (555778) into a
-- NEW, third AD_UI_Column (549759) of the "Transport" section (547602). The WebUI divides a section's
-- 12-unit grid evenly across its columns (frontend/src/components/window/Section.js), so a third column
-- takes every column from col-sm-6 to col-sm-4 — measured on the rendered window at a 1280px viewport:
-- 607px -> 405px per column. That narrowing is not free for the two PRE-EXISTING groups: the HTTP and
-- SFTP field labels truncate much harder (e.g. "SFTP-Verzeichnispfad" -> "SFTP-Verz...",
-- "SFTP-Dateinamensmuster" -> "SFTP-Datei..."), so an administrator configuring ANY endpoint — not just
-- a local-file one — can no longer read the labels.
--
-- Only one transport's group is ever displayed (each field is DisplayLogic-gated on TransportType), so
-- the LOCAL_FILE group can share a column with another transport's group without them ever competing
-- for space. Move it into the section's first column, next to the HTTP group, and retire the now-empty
-- third column — the section is back to two columns, and the HTTP/SFTP groups back to their original
-- width.
--
-- Affected records:
--   AD_UI_ElementGroup 555778 ("LOCAL_FILE") -> AD_UI_Column 549281 (first column of section 547602)
--   AD_UI_Column       549759 (the third column, now empty) -> IsActive='N'

-- ============================================================
-- 1. Move the LOCAL_FILE element group into the section's first column
-- ============================================================
UPDATE AD_UI_ElementGroup
SET AD_UI_Column_ID = 549281,
    SeqNo           = 20,
    Updated         = TO_TIMESTAMP('2026-09-24 12:00:00', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy       = 100
WHERE AD_UI_ElementGroup_ID = 555778;

-- ============================================================
-- 2. Retire the now-empty third column
-- ============================================================
UPDATE AD_UI_Column
SET IsActive  = 'N',
    Updated   = TO_TIMESTAMP('2026-09-24 12:00:00', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy = 100
WHERE AD_UI_Column_ID = 549759;
