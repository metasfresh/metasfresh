-- Make C_TaxDeclaration, C_TaxDeclarationLine and C_TaxDeclarationAcct tabs read-only
-- once the declaration is completed (Processed='Y').

UPDATE AD_Tab SET ReadOnlyLogic='@Processed@=Y', Updated=TIMESTAMP '2026-05-21 00:00:00', UpdatedBy=100
WHERE AD_Tab_ID IN (549256, 549257, 549258) -- C_TaxDeclaration / Lines / Accounting Facts
;
