CREATE TABLE backup.ad_treebar_20200922 AS
SELECT *
FROM ad_treebar;

DROP TABLE IF EXISTS tmp_ad_treebar_duplicates;
CREATE TEMPORARY TABLE tmp_ad_treebar_duplicates AS
SELECT tb.ad_user_id,
       tb.node_id,
       count(1) AS count,
       (SELECT z.ctid
        FROM AD_TreeBar z
        WHERE z.ad_user_id = tb.ad_user_id
          AND z.node_id = tb.node_id
        ORDER BY z.IsActive DESC, z.created, z.ctid
        LIMIT 1
       )        AS first_ctid
FROM AD_TreeBar tb
WHERE TRUE
GROUP BY ad_user_id, node_id
HAVING count(1) > 1;

-- select * from tmp_ad_treebar_duplicates;

DELETE
FROM ad_treebar tb
WHERE exists(SELECT 1
             FROM tmp_ad_treebar_duplicates d
             WHERE d.ad_user_id = tb.ad_user_id
               AND d.node_id = tb.node_id
               AND tb.ctid != d.first_ctid)
;

