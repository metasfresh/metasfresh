-- Tax Declaration Iter 5 — force-include 'de.metas.acct' columns of C_TaxDeclaration in the generated model.
--
-- Background:
--   GenerateModel filters columns by EntityType. The C_TaxDeclaration table has EntityType='D'
--   (Dictionary), but three of its columns (DocumentNo, DateAcct, C_Period_ID) were created with
--   EntityType='de.metas.acct' — which is NOT in the hardcoded SYSTEM_MAINTAINED_ENTITY_TYPES
--   list (`EntityTypesCache.java`). Result: subsequent model regens DROP those columns from the
--   generated I_/X_ files unless IsForceIncludeInGeneratedModel='Y' is set explicitly.
--
-- Without this script, every developer who regenerates the model loses DocumentNo / DateAcct /
-- C_Period_ID accessors and the existing C_TaxDeclaration callout + tab callout stop compiling.

UPDATE AD_Column SET IsForceIncludeInGeneratedModel='Y', Updated=TIMESTAMP '2026-05-21 00:00:00', UpdatedBy=100
WHERE AD_Column_ID IN (592556, 592557, 592558) -- DocumentNo, DateAcct, C_Period_ID
;
