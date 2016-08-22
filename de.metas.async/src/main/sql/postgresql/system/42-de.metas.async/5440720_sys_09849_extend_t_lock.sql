
--
-- note: (ad_table_id, record_id) can't be the primary key anymore, so we add a dedicated one
--

ALTER TABLE t_lock DROP CONSTRAINT IF EXISTS t_lock_pkey;
ALTER TABLE t_lock ADD COLUMN t_lock_id BIGSERIAL PRIMARY KEY;
ALTER TABLE t_lock ADD COLUMN IsAllowMultipleOwners char(1) NOT NULL DEFAULT 'N';

CREATE UNIQUE INDEX t_lock_reference_singleowner
   ON t_lock (ad_table_id, record_id)
   WHERE IsAllowMultipleOwners='N';
COMMENT ON INDEX t_lock_reference_singleowner IS 'task 09849: if a lock was created with IsAllowMultipleOwners=''N'', then this index makes sure that no other lock can reference the record in question';

CREATE UNIQUE INDEX t_lock_reference_multipleowners
   ON t_lock (ad_table_id, record_id, owner)
   WHERE IsAllowMultipleOwners='Y';
COMMENT ON INDEX t_lock_reference_multipleowners IS 'task 09849: if a lock was created with IsAllowMultipleOwners=''Y'', then this index allows multipe references on the same record, as long as they have different owners';

-- avoid null owner values, because nullable columns don't play well with unique indices
ALTER TABLE t_lock ALTER COLUMN owner SET DEFAULT 'NULL'::character varying;
UPDATE t_lock SET owner='NULL' WHERE owner IS NULL;

ALTER TABLE t_lock ALTER COLUMN owner SET NOT NULL;
