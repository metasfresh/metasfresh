DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Inout_Details_Footer(IN M_InOut_ID  numeric,
                                                                                     IN AD_Language Character Varying(6))
;

CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Inout_Details_Footer(IN M_InOut_ID  numeric,
                                                                                        IN AD_Language Character Varying(6))

    RETURNS TABLE
            (
                textleft         text,
                textcenter       text,
                language         character varying,
                m_inout_id       numeric(10, 0),
                tag              text,
                pozition         integer,
                Incoterms        character varying,
                incotermlocation character varying

            )

AS
$$

SELECT footer.*,
       COALESCE(inc_trl.name, inc.name) AS Incoterms,
       o.incotermlocation
FROM (
         --Docnote DE
         SELECT NULL                                                                                AS textleft,
                CASE
                    WHEN io.descriptionbottom IS NOT NULL
                        THEN E'\n\n\n'
                        ELSE ''
                END || dt.documentnote                                                              AS textcenter,
                (SELECT AD_Language FROM AD_Language WHERE IsBaseLanguage = 'Y' AND isActive = 'Y') AS language,
                io.m_inout_id                                                                       AS m_inout_id,
                'docnote'                                                                           AS tag,
                3                                                                                   AS pozition
         FROM m_inout io
                  LEFT JOIN c_doctype dt ON io.c_doctype_id = dt.c_doctype_id AND dt.isActive = 'Y'
         WHERE io.isActive = 'Y'
         UNION
         ---------------------------------------------------------------------------------------------
         --Docnote TRL
         SELECT NULL                   AS textleft,
                CASE
                    WHEN io.descriptionbottom IS NOT NULL
                        THEN E'\n\n\n'
                        ELSE ''
                END || dt.documentnote AS textcenter,
                dt.ad_language         AS language,
                io.m_inout_id          AS m_inout_id,
                'docnote'              AS tag,
                3                      AS pozition
         FROM m_inout io
                  LEFT JOIN c_doctype_trl dt ON io.c_doctype_id = dt.c_doctype_id AND dt.isActive = 'Y'
         WHERE io.isActive = 'Y'
         UNION
         ---------------------------------------------------------------------------------------------
         --Descriptionbottom
         SELECT io.descriptionbottom AS textleft,
                NULL                 AS textcenter,
                NULL                 AS language,
                io.m_inout_id        AS m_inout_id,
                'descr'              AS tag,
                2                    AS pozition
         FROM m_inout io
         WHERE io.isActive = 'Y') footer
         INNER JOIN m_inout io ON io.m_inout_id = footer.m_inout_id
         INNER JOIN c_order o ON io.c_order_id = o.c_order_id
         LEFT OUTER JOIN C_Incoterms inc ON o.c_incoterms_id = inc.c_incoterms_id
         LEFT OUTER JOIN C_Incoterms_trl inc_trl ON inc.c_incoterms_id = inc_trl.c_incoterms_id AND inc_trl.ad_language = 'de_DE'

WHERE footer.m_inout_id = $1
  AND footer.language = $2
ORDER BY pozition

$$
    LANGUAGE sql STABLE
;
