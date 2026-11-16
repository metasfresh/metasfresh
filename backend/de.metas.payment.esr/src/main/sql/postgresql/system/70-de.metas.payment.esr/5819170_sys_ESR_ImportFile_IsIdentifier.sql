-- ESR_ImportFile had no identifier column at all: none of its 20 active AD_Columns carried
-- IsIdentifier='Y'. Every lookup that has to render an ESR_ImportFile record therefore failed with
-- "There are no lookup display columns defined for ESR_ImportFile table." The one place this is
-- reached from is ESR_ImportLine.ESR_ImportFile_ID, a Search reference.
--
-- FileName is the identifier a user recognises; Created disambiguates the same file imported twice,
-- which is the normal ESR re-import path. FileName is nullable, so the display degrades to the
-- timestamp alone rather than to nothing.
--
-- SeqNo is the identifier ordering (a record's display string is built from the IsIdentifier columns
-- in SeqNo order), so the two values must differ.

UPDATE AD_Column
SET IsIdentifier = 'Y',
    SeqNo = 1,
    Updated = TO_TIMESTAMP('2026-08-14 15:10:00', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy = 100
WHERE AD_Column_ID = 575155  -- ESR_ImportFile.FileName
;

UPDATE AD_Column
SET IsIdentifier = 'Y',
    SeqNo = 2,
    Updated = TO_TIMESTAMP('2026-08-14 15:10:01', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy = 100
WHERE AD_Column_ID = 575143  -- ESR_ImportFile.Created
;
