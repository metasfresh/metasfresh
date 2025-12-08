--
-- DROP PK
ALTER TABLE AD_Process_Access
    DROP CONSTRAINT IF EXISTS AD_Process_Access_pkey
;

ALTER TABLE AD_Process_Access
    DROP CONSTRAINT IF EXISTS AD_Process_Access_key
;

--
-- Update ID 
UPDATE AD_Process_Access t -- intermediate unique violations are ignored now
SET AD_Process_Access_ID = t1.new_id
FROM (SELECT AD_Process_Access_ID, 1000000 - 1 + ROW_NUMBER() OVER (ORDER BY AD_Process_Access_ID) AS new_id FROM AD_Process_Access) t1
WHERE t.AD_Process_Access_ID = t1.AD_Process_Access_ID
;

--
-- Add PK back
ALTER TABLE AD_Process_Access
    ADD CONSTRAINT AD_Process_Access_pkey PRIMARY KEY (AD_Process_Access_ID)
;

--
-- Update sequence
SELECT dba_seq_check_native('AD_Process_Access')
;

