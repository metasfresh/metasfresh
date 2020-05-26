DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_DD_Order_Details_Footer( IN DD_Order_ID numeric, IN AD_Language Character Varying (6) );
CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.Docs_DD_Order_Details_Footer(IN DD_Order_ID numeric,
                                                                                           IN AD_Language Character Varying(6))

    RETURNS TABLE
            (
                textleft    text,
                textcenter  text,
                language    character varying,
                dd_order_id numeric(10, 0),
                tag         text,
                pozition    integer

            )

AS
$$

SELECT *
FROM (
         --Docnote DE
         SELECT NULL                                                                                AS textleft,
                CASE
                    WHEN ddo.description IS NOT NULL
                        THEN E'\n\n\n'
                        ELSE ''
                END || dt.documentnote                                                              AS textcenter,
                (SELECT AD_Language FROM AD_Language WHERE IsBaseLanguage = 'Y' AND isActive = 'Y') AS language,
                ddo.dd_order_id                                                                     AS dd_order_id,
                'docnote'                                                                           AS tag,
                3                                                                                   AS pozition
         FROM dd_order ddo
                  LEFT JOIN c_doctype dt ON ddo.c_doctype_id = dt.c_doctype_id AND dt.isActive = 'Y'
         WHERE ddo.isActive = 'Y'
         UNION
         ---------------------------------------------------------------------------------------------
         --Docnote TRL
         SELECT NULL                   AS textleft,
                CASE
                    WHEN ddo.description IS NOT NULL
                        THEN E'\n\n\n'
                        ELSE ''
                END || dt.documentnote AS textcenter,
                dt.ad_language         AS language,
                ddo.dd_order_id        AS dd_order_id,
                'docnote'              AS tag,
                3                      AS pozition
         FROM dd_order ddo
                  LEFT JOIN c_doctype_trl dt ON ddo.c_doctype_id = dt.c_doctype_id AND dt.isActive = 'Y'
         WHERE ddo.isActive = 'Y'
         UNION
         ---------------------------------------------------------------------------------------------
         --Descriptionbottom
         SELECT ddo.description AS textleft,
                NULL            AS textcenter,
                NULL            AS language,
                ddo.dd_order_id AS dd_order_id,
                'descr'         AS tag,
                2               AS pozition
         FROM dd_order ddo
         WHERE ddo.isActive = 'Y'
     ) footer
WHERE footer.dd_order_id = $1
  AND (footer.language = $2 OR footer.language IS NULL)
  AND (textleft <> '' OR textcenter <> '')
  AND (textleft IS NULL OR textcenter IS NULL)
ORDER BY pozition

$$
    LANGUAGE SQL STABLE
;
