DROP VIEW IF EXISTS ad_table_related_windows_v
;

CREATE VIEW ad_table_related_windows_v AS
SELECT fk.ref_tablename                                                                                AS source_tableName,
       --
       fk.tablename                                                                                    AS target_tableName,
       fk.columnname                                                                                   AS target_columnName,
       c.columnsql                                                                                     AS target_columnSql,
       (CASE
            WHEN EXISTS(SELECT 1
                        FROM ad_column zz
                        WHERE zz.ad_table_id = t.ad_table_id
                          AND zz.columnname = 'IsSOTrx'
                          AND zz.isactive = 'Y'
                          AND zz.ad_reference_id = 20)
                THEN 'Y'
                ELSE 'N'
        END)                                                                                           AS target_has_issotrx_column,
       --
       tt.whereclause                                                                                  AS tab_whereClause,
       w.ad_window_id                                                                                  AS target_window_id,
       -- s_t.ad_window_id                                                                                AS default_so_window_id,
       -- COALESCE(s_t.po_window_id, s_t.ad_window_id)                                                    AS default_po_window_id,
       (CASE WHEN w.ad_window_id = s_t.ad_window_id THEN 'Y' ELSE 'N' END)                             AS IsDefaultSOWindow,
       (CASE WHEN w.ad_window_id = COALESCE(s_t.po_window_id, s_t.ad_window_id) THEN 'Y' ELSE 'N' END) AS IsDefaultPOWindow,
       w.name                                                                                          AS target_window_name,
       (SELECT ARRAY_AGG(ARRAY [wtrl.ad_language, wtrl.name])
        FROM ad_window_trl wtrl
        WHERE wtrl.ad_window_id = w.ad_window_id)                                                      AS target_window_name_trls,
       COALESCE(w.internalname, w.name)                                                                AS target_window_internalname
FROM db_columns_fk fk
         INNER JOIN ad_table s_t
                    ON s_t.tablename = fk.ref_tablename AND s_t.isactive = 'Y'
         INNER JOIN ad_table t ON t.tablename = fk.tablename AND t.isactive = 'Y'
         INNER JOIN ad_column C ON C.ad_table_id = t.ad_table_id AND C.columnname = fk.columnname AND C.isactive = 'Y'
         INNER JOIN ad_tab tt ON tt.ad_table_id = t.ad_table_id AND tt.isactive = 'Y' AND tt.seqno = 10 AND tt.tablevel = 0
         INNER JOIN ad_window W ON W.ad_window_id = tt.ad_window_id AND W.isactive = 'Y'
;

