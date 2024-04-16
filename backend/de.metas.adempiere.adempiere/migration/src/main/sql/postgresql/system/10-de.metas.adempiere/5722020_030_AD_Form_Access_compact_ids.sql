--
-- DROP PK
ALTER TABLE AD_Form_Access
    DROP CONSTRAINT IF EXISTS AD_Form_Access_pkey
;

ALTER TABLE AD_Form_Access
    DROP CONSTRAINT IF EXISTS AD_Form_Access_key
;

--
-- Update ID 
UPDATE AD_Form_Access t -- intermediate unique violations are ignored now
SET AD_Form_Access_ID = t1.new_id
FROM (SELECT AD_Form_Access_ID, ROW_NUMBER() OVER (ORDER BY AD_Form_Access_ID) AS new_id FROM AD_Form_Access) t1
WHERE t.AD_Form_Access_ID = t1.AD_Form_Access_ID
;

--
-- Add PK back
ALTER TABLE AD_Form_Access
    ADD CONSTRAINT AD_Form_Access_pkey PRIMARY KEY (AD_Form_Access_ID)
;

--
-- Update sequence
SELECT dba_seq_check_native('AD_Form_Access')
;

