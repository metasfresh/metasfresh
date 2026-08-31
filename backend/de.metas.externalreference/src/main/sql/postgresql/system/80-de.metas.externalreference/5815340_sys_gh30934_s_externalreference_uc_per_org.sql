-- Make S_ExternalReference uniqueness per-org, consistent with the org-scoped lookup.
--
-- The unique index on (ExternalReference, Type, ExternalSystem_ID) was org-agnostic (global), but
-- ExternalReferenceRepository resolves references filtered by AD_Org_ID (the lookup is org-scoped).
-- So two different orgs using the same external system + reference code collided on the global
-- index even though the lookup treats them as distinct per org. Add AD_Org_ID to the index so the
-- enforced uniqueness matches the (org-scoped) lookup: one external reference per org.
--
-- Loosening change: every row that satisfied the old (narrower) index still satisfies the new one,
-- so no data migration / dedup is required.
--
-- The sibling index (Type, ExternalSystem_ID, Record_ID) is intentionally NOT changed: Record_ID is
-- a metasfresh id that already belongs to exactly one org, so that index cannot collide across orgs.

-- AD dictionary: add AD_Org_ID (SeqNo 40, tenant column last) to the index definition (AD_Index_Table 540525)
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy)
VALUES (0,570574,541534 /*From ID Server*/,540525,0,TO_TIMESTAMP('2026-07-22 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalreference','Y',40,TO_TIMESTAMP('2026-07-22 10:00:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- physical index: recreate including AD_Org_ID (tenant column last, per composite-index selectivity)
DROP INDEX IF EXISTS idx_s_externalreference_externalsystem_type_externalreferenc
;

CREATE UNIQUE INDEX IDX_S_ExternalReference_ExternalSystem_Type_ExternalReferenc ON S_ExternalReference (ExternalReference,Type,ExternalSystem_ID,AD_Org_ID) WHERE S_ExternalReference.isActive='Y'
;
