--
-- DROP PK
ALTER TABLE AD_Document_Action_Access
    DROP CONSTRAINT IF EXISTS AD_Document_Action_Access_pkey
;

ALTER TABLE AD_Document_Action_Access
    DROP CONSTRAINT IF EXISTS AD_Document_Action_Access_key
;

--
-- Update ID 
UPDATE AD_Document_Action_Access t -- intermediate unique violations are ignored now
SET AD_Document_Action_Access_ID = t1.new_id
FROM (SELECT AD_Document_Action_Access_ID, 1000000 - 1 + ROW_NUMBER() OVER (ORDER BY AD_Document_Action_Access_ID) AS new_id FROM AD_Document_Action_Access) t1
WHERE t.AD_Document_Action_Access_ID = t1.AD_Document_Action_Access_ID
;

--
-- Add PK back
ALTER TABLE AD_Document_Action_Access
    ADD CONSTRAINT AD_Document_Action_Access_pkey PRIMARY KEY (AD_Document_Action_Access_ID)
;

--
-- Update sequence
SELECT dba_seq_check_native('AD_Document_Action_Access')
;

