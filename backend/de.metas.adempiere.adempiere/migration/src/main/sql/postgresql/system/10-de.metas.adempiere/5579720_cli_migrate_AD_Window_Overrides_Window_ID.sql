/*
DROP TABLE IF EXISTS tmp_ad_windows_old_and_new
;

CREATE TEMPORARY TABLE tmp_ad_windows_old_and_new AS
SELECT w.ad_window_id,
       w.name     AS      windowname,
       w_old.ad_window_id old_window_id,
       w_old.name AS      old_windowName
FROM ad_window w
         INNER JOIN ad_window w_old ON w_old.name = w.name || '_OLD'
WHERE w.overrides_window_id IS NULL
;

-- SELECT * FROM tmp_ad_windows_old_and_new;

-- drop table if exists backup.ad_window_bkp20210219;
CREATE TABLE backup.ad_window_bkp20210219 AS
SELECT *
FROM ad_window
;

UPDATE ad_window w
SET overrides_window_id=t.old_window_id
FROM tmp_ad_windows_old_and_new t
WHERE w.ad_window_id = t.ad_window_id
  AND w.overrides_window_id IS NULL
;

*/
