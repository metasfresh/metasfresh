DROP VIEW IF EXISTS ad_table_windows_v
;

CREATE OR REPLACE VIEW ad_table_windows_v AS
SELECT t.tablename,
       w.isexcludefromzoomtargets,
       w.ZoomIntoPriority                                                AS priority,
       w.ad_window_id,
       (CASE WHEN w.ad_window_id = t.ad_window_id THEN 'Y' ELSE 'N' END) AS IsDefaultSOWindow,
       (CASE WHEN w.ad_window_id = t.po_window_id THEN 'Y' ELSE 'N' END) AS IsDefaultPOWindow,
       --
       tt.whereclause                                                    AS tab_whereclause
FROM ad_table t
         INNER JOIN ad_tab tt ON tt.ad_table_id = t.ad_table_id AND tt.isactive = 'Y' AND tt.seqno = 10 AND tt.tablevel = 0
         INNER JOIN ad_window w ON w.ad_window_id = tt.ad_window_id AND w.isactive = 'Y'
WHERE t.isactive = 'Y'
  AND EXISTS(SELECT 1 FROM ad_field f WHERE f.ad_tab_id = tt.ad_tab_id AND f.isactive = 'Y') -- Not a placeholder window, i.e. tab has fields
;

