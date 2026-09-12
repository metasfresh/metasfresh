-- m_receiptschedule_alloc carried TWO identical btree(vhu_id) indexes, so every insert into a table that
-- grows with every receipt maintained both.
--
-- How it happened: m_receiptschedule_alloc_vhus exists only in the central base dump - no script in this
-- repository creates it, on any branch. In 2016 5452760_sys_gh539_add_missing_FKs.sql then swept for FK
-- columns lacking an index and added m_receiptschedule_alloc_vhu_id, unguarded; the create succeeded because
-- the existing index did not follow the <table>_<column> naming convention and so did not look like a hit.
--
-- Dropping the unnamed-by-convention one keeps the index that has a script and a conventional name. IF EXISTS
-- because the surviving name is the one every future database will get from its migration.

-- 2026-09-09T19:30:00.000Z
DROP INDEX IF EXISTS m_receiptschedule_alloc_vhus
;
