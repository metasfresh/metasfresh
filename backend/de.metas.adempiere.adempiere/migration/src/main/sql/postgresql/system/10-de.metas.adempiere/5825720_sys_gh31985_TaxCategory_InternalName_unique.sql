-- Create unique index on C_TaxCategory.InternalName for active rows
-- Diagnostic: identify duplicate active InternalName values that would block the migration
-- Index creation fails if any duplicates exist; resolve them first

DO $$
DECLARE dups text;
BEGIN
    SELECT string_agg(internalname || ' (' || cnt || 'x)', ', ')
      INTO dups
      FROM (SELECT internalname, count(*) AS cnt
              FROM c_taxcategory
             WHERE isactive = 'Y' AND internalname IS NOT NULL
             GROUP BY internalname
            HAVING count(*) > 1) d;
    IF dups IS NOT NULL THEN
        RAISE EXCEPTION 'Duplicate active C_TaxCategory.InternalName values block this migration: %. Resolve them, then re-run.', dups;
    END IF;
END $$;

CREATE UNIQUE INDEX IF NOT EXISTS C_TaxCategory_InternalName_Unique
    ON C_TaxCategory (InternalName) WHERE IsActive = 'Y' AND InternalName IS NOT NULL;
