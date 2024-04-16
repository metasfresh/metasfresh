--
-- DROP PK
ALTER TABLE AD_Task_Access
    DROP CONSTRAINT IF EXISTS AD_Task_Access_pkey
;

ALTER TABLE AD_Task_Access
    DROP CONSTRAINT IF EXISTS AD_Task_Access_key
;

--
-- Update ID 
UPDATE AD_Task_Access t -- intermediate unique violations are ignored now
SET AD_Task_Access_ID = t1.new_id
FROM (SELECT AD_Task_Access_ID, ROW_NUMBER() OVER (ORDER BY AD_Task_Access_ID) AS new_id FROM AD_Task_Access) t1
WHERE t.AD_Task_Access_ID = t1.AD_Task_Access_ID
;

--
-- Add PK back
ALTER TABLE AD_Task_Access
    ADD CONSTRAINT AD_Task_Access_pkey PRIMARY KEY (AD_Task_Access_ID)
;

--
-- Update sequence
SELECT dba_seq_check_native('AD_Task_Access')
;

