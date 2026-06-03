-- Speed up the picking "find pickable source HUs" search on large M_HU_Attribute datasets.
--
-- WHY: HUStorageQuery.createQueryBuilder_for_M_HU_Storages builds an EXISTS over M_HU_Attribute with
-- predicate (M_HU_ID = ?, M_Attribute_ID = ?, ValueNumber = ?, IsActive = 'Y'), probed once per
-- candidate HU during ForcePick* / source-HU retrieval. The existing index
-- m_hu_attribute_parent_index (M_HU_ID, M_Attribute_ID) does NOT cover ValueNumber, so each probe
-- heap-fetches to evaluate it. On large datasets (m_hu_attribute can reach hundreds of millions of
-- rows) that is millions of cold random reads -> seconds per pick. This partial covering index lets
-- the EXISTS be satisfied
-- index-only and (together with the destroyed-HU attribute soft-archive) keeps the active slice
-- cache-resident.
--
-- NON-CONCURRENT ON PURPOSE: CREATE INDEX CONCURRENTLY cannot run inside the migration tool's single
-- transaction / multi-command string (see de.metas.dlm 5453734_sys_gh489_DDL.sql:148-151). On small
-- and CI databases the statement below is instant. On LARGE production databases the index MUST be
-- pre-created CONCURRENTLY off-peak BEFORE this rollout runs (via an operational DBA
-- runbook) so that this IF NOT EXISTS statement is a no-op and does not
-- take an ACCESS EXCLUSIVE lock on the picking-critical M_HU_Attribute table.

CREATE INDEX IF NOT EXISTS m_hu_attribute_hu_attr_valnum
    ON M_HU_Attribute (M_HU_ID, M_Attribute_ID, ValueNumber)
    WHERE IsActive = 'Y';
