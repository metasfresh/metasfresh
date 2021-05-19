DROP TABLE IF EXISTS tmp_column_isexcludefromzoomtargets
;

CREATE TEMPORARY TABLE tmp_column_isexcludefromzoomtargets AS
SELECT (CASE WHEN fk.columnname LIKE '%Effective_ID' THEN 'N' ELSE 'Y' END) AS isexcludefromzoomtargets,
       t.source_tablename,
       t.target_tablename,
       fk.columnname                                                        AS target_columnname,
       c.ad_column_id                                                       AS target_column_id
       --
       --        ,
       --        fk.ad_reference_name                                                 AS target_column_displayName,
       --        fk.ad_reference_value_id                                             AS target_column_ref_value_id,
       --        COUNT(1) OVER (PARTITION BY t.source_tablename, t.target_tablename)  AS count_columns
FROM (
         SELECT r.source_tablename,
                r.target_tablename
         FROM ad_table_related_windows_v r
         WHERE TRUE
           AND r.target_columnname LIKE '%Effective_ID'
         GROUP BY r.target_tablename, r.source_tablename
     ) t
         INNER JOIN db_columns_fk fk
                    ON fk.ref_tablename = t.source_tablename
                        AND fk.tablename = t.target_tablename
         LEFT OUTER JOIN ad_column c ON c.ad_table_id = get_table_id(t.target_tablename) AND c.columnname = fk.columnname
ORDER BY t.target_tablename, t.source_tablename
;

CREATE INDEX ON tmp_column_isexcludefromzoomtargets (target_tablename, target_columnname)
;

CREATE UNIQUE INDEX ON tmp_column_isexcludefromzoomtargets (target_column_id)
;


INSERT INTO tmp_column_isexcludefromzoomtargets
SELECT (CASE
            WHEN fk.tablename = 'C_Invoice_Candidate' AND fk.columnname = 'C_OrderLine_ID'       THEN 'Y' -- preserve already excluded
            WHEN fk.tablename = 'C_Order' AND fk.columnname = 'Bill_BPartner_ID'                 THEN 'Y' -- preserve already excluded
            WHEN c.ad_reference_id = 19/*table dir*/                                             THEN 'N'
            WHEN c.ad_reference_id = 30/*search*/ AND c.ad_reference_value_id IS NULL            THEN 'N'
            WHEN c.ad_reference_id IN (18, 19, 30) AND fk.columnname = fk.ref_tablename || '_ID' THEN 'N'
                                                                                                 ELSE 'Y'
        END)            AS isexcludefromzoomtargets,
       fk.ref_tablename AS source_tablename,
       fk.tablename     AS target_tablename,
       fk.columnname    AS target_columnname,
       c.ad_column_id   AS target_column_id
       --
       -- , fk.*
FROM db_columns_fk fk
         LEFT OUTER JOIN ad_column c ON c.ad_table_id = get_table_id(fk.tablename) AND c.columnname = fk.columnname

WHERE TRUE
  AND NOT EXISTS(SELECT 1 FROM tmp_column_isexcludefromzoomtargets t WHERE t.target_tablename = fk.tablename AND t.target_columnname = fk.columnname)
--  AND (fk.tablename = 'R_Request' AND fk.ref_tablename = 'C_BPartner')
;


/* Check:
select * from tmp_column_isexcludefromzoomtargets
   where true
   and target_tablename='R_Request' and source_tablename='C_BPartner'
   ;
 */


/*
SELECT t.tablename,
       c.columnname,
       c.ad_reference_id,
       c.ad_reference_value_id,
       c.isexcludefromzoomtargets,
       z.isexcludefromzoomtargets AS isexcludefromzoomtargets_new
FROM ad_column c
         INNER JOIN ad_table t ON t.ad_table_id = c.ad_table_id
         INNER JOIN tmp_column_isexcludefromzoomtargets z ON z.target_column_id = c.ad_column_id
WHERE z.isexcludefromzoomtargets != c.isexcludefromzoomtargets
ORDER BY t.tablename, c.columnname
;
 */

--
--
--
-- Update
--
--
--
CREATE TABLE backup.ad_column_bkp20210519 AS
SELECT *
FROM ad_column
;

UPDATE ad_column c
SET isexcludefromzoomtargets=t.isexcludefromzoomtargets
FROM tmp_column_isexcludefromzoomtargets t
WHERE c.ad_column_id = t.target_column_id
  AND c.isexcludefromzoomtargets != t.isexcludefromzoomtargets
;



--
--
-- Check after update
--
--
/*


SELECT t.tablename, c.columnname, c.isexcludefromzoomtargets, c.ad_reference_id, c.ad_reference_value_id
FROM ad_column c
         INNER JOIN ad_table t ON t.ad_table_id = c.ad_table_id
WHERE true
and t.tablename='R_Request' and c.columnname like '%BP%'
;



SELECT r.source_tablename,
       r.target_tablename,
       r.target_columnname,
       -- c.ad_reference_id,
       (SELECT z.name FROM ad_reference z WHERE z.ad_reference_id = c.ad_reference_id) AS refname,
       c.ad_reference_value_id
FROM ad_table_related_windows_v r
         LEFT OUTER JOIN ad_column c ON c.ad_table_id = get_table_id(r.target_tablename) AND c.columnname = r.target_columnname
WHERE source_tablename = 'C_BPartner'
  AND target_tablename = 'R_Request'
;




SELECT *
FROM tmp_column_isexcludefromzoomtargets
WHERE source_tablename = 'C_BPartner'
  AND target_tablename = 'R_Request'



 */



