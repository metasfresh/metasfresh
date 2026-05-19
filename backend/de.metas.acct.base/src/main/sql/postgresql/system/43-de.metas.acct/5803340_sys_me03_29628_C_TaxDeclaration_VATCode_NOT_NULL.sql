-- Tax Declaration Iter4: VATCode is mandatory on C_TaxDeclarationLine + C_TaxDeclarationAcct
-- (Mark's design: VATCode is the authoritative aggregation key; rows without it have no
-- place in a tax declaration). Clean up any pre-existing rows that lack a VATCode (created
-- before 5803320 added the column), then SET NOT NULL.

-- 1. Wipe rows that have no VATCode (pre-5803320 rebuild output).
DELETE FROM C_TaxDeclarationAcct WHERE VATCode IS NULL;
DELETE FROM C_TaxDeclarationLine WHERE VATCode IS NULL;

-- 2. AD_Column: IsMandatory='Y'
UPDATE AD_Column SET IsMandatory='Y', Updated=TIMESTAMP '2026-05-19 00:00:00', UpdatedBy=100
WHERE AD_Column_ID IN (592559, 592560);

-- 3. Physical DDL: SET NOT NULL
ALTER TABLE C_TaxDeclarationLine ALTER COLUMN VATCode SET NOT NULL;
ALTER TABLE C_TaxDeclarationAcct ALTER COLUMN VATCode SET NOT NULL;
