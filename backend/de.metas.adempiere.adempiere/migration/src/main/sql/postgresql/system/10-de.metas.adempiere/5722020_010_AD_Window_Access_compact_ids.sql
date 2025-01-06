--
-- DROP PK
ALTER TABLE AD_Window_Access
    DROP CONSTRAINT IF EXISTS AD_Window_Access_pkey
;

ALTER TABLE AD_Window_Access
    DROP CONSTRAINT IF EXISTS AD_Window_Access_key
;

--
-- Update ID 
UPDATE AD_Window_Access t -- intermediate unique violations are ignored now
SET AD_Window_Access_ID = t1.new_id
FROM (SELECT AD_Window_Access_ID, 1000000 - 1 + ROW_NUMBER() OVER (ORDER BY AD_Window_Access_ID) AS new_id FROM AD_Window_Access) t1
WHERE t.AD_Window_Access_ID = t1.AD_Window_Access_ID
;

--
-- Add PK back
ALTER TABLE AD_Window_Access
    ADD CONSTRAINT ad_window_access_pkey PRIMARY KEY (AD_Window_Access_ID)
;

--
-- Update sequence
SELECT dba_seq_check_native('AD_Window_Access')
;


-- select min(ad_window_access_id), max(ad_window_access_id) from ad_window_access;