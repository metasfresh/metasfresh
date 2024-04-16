--
-- DROP PK
ALTER TABLE AD_Workflow_Access
    DROP CONSTRAINT IF EXISTS AD_Workflow_Access_pkey
;

ALTER TABLE AD_Workflow_Access
    DROP CONSTRAINT IF EXISTS AD_Workflow_Access_key
;

--
-- Update ID 
UPDATE AD_Workflow_Access t -- intermediate unique violations are ignored now
SET AD_Workflow_Access_ID = t1.new_id
FROM (SELECT AD_Workflow_Access_ID, ROW_NUMBER() OVER (ORDER BY AD_Workflow_Access_ID) AS new_id FROM AD_Workflow_Access) t1
WHERE t.AD_Workflow_Access_ID = t1.AD_Workflow_Access_ID
;

--
-- Add PK back
ALTER TABLE AD_Workflow_Access
    ADD CONSTRAINT AD_Workflow_Access_pkey PRIMARY KEY (AD_Workflow_Access_ID)
;

--
-- Update sequence
SELECT dba_seq_check_native('AD_Workflow_Access')
;

