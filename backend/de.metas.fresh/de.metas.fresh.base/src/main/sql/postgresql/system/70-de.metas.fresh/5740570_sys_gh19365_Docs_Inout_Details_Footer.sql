DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Inout_Details_Footer(IN p_InOut_ID numeric,
                                                                                     IN p_Language Character Varying(6))
;

CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Inout_Details_Footer(IN p_InOut_ID numeric,
                                                                                        IN p_Language Character Varying(6))

    RETURNS TABLE
            (
                textleft         text,
                textcenter       text,
                language         character varying,
                m_inout_id       numeric(10, 0),
                tag              text,
                pozition         integer,
                Incoterms        character varying,
                incotermlocation character varying,
                deliveryrule     character varying,
                deliveryviarule  character varying
            )

AS
$$

SELECT footer.*,
       COALESCE(o_dr_trl.name, o_dr.name)   AS deliveryrule,
       COALESCE(o_dvr_trl.name, o_dvr.name) AS deliveryviarule
FROM (
         --Docnote DE
         SELECT NULL                                                                                        AS textleft,
                CASE
                    WHEN io.descriptionbottom IS NOT NULL
                        THEN E'\n\n\n'
                        ELSE ''
                END || dt.documentnote                                                                      AS textcenter,
                (SELECT l.AD_Language FROM AD_Language l WHERE l.IsBaseLanguage = 'Y' AND l.isActive = 'Y') AS language,
                io.m_inout_id                                                                               AS m_inout_id,
                'docnote'                                                                                   AS tag,
                3                                                                                           AS pozition,
                COALESCE(inc_trl.name, inc.name)                                                            AS Incoterms,
                io.incotermlocation
         FROM m_inout io
                  LEFT JOIN c_doctype dt ON io.c_doctype_id = dt.c_doctype_id AND dt.isActive = 'Y'
                  LEFT OUTER JOIN C_Incoterms inc ON io.c_incoterms_id = inc.c_incoterms_id
                  LEFT OUTER JOIN C_Incoterms_trl inc_trl ON inc.c_incoterms_id = inc_trl.c_incoterms_id AND inc_trl.ad_language = p_Language
         WHERE io.isActive = 'Y'
         UNION
         ---------------------------------------------------------------------------------------------
         --Docnote TRL
         SELECT NULL                             AS textleft,
                CASE
                    WHEN io.descriptionbottom IS NOT NULL
                        THEN E'\n\n\n'
                        ELSE ''
                END || dt.documentnote           AS textcenter,
                dt.ad_language                   AS language,
                io.m_inout_id                    AS m_inout_id,
                'docnote'                        AS tag,
                3                                AS pozition,
                COALESCE(inc_trl.name, inc.name) AS Incoterms,
                io.incotermlocation
         FROM m_inout io
                  LEFT JOIN c_doctype_trl dt ON io.c_doctype_id = dt.c_doctype_id AND dt.isActive = 'Y'
                  LEFT OUTER JOIN C_Incoterms inc ON io.c_incoterms_id = inc.c_incoterms_id
                  LEFT OUTER JOIN C_Incoterms_trl inc_trl ON inc.c_incoterms_id = inc_trl.c_incoterms_id AND inc_trl.ad_language = p_Language
         WHERE io.isActive = 'Y'
         UNION
         ---------------------------------------------------------------------------------------------
         --Descriptionbottom
         SELECT io.descriptionbottom             AS textleft,
                NULL                             AS textcenter,
                NULL                             AS language,
                io.m_inout_id                    AS m_inout_id,
                'descr'                          AS tag,
                2                                AS pozition,
                COALESCE(inc_trl.name, inc.name) AS Incoterms,
                io.incotermlocation
         FROM m_inout io
                  LEFT OUTER JOIN C_Incoterms inc ON io.c_incoterms_id = inc.c_incoterms_id
                  LEFT OUTER JOIN C_Incoterms_trl inc_trl ON inc.c_incoterms_id = inc_trl.c_incoterms_id AND inc_trl.ad_language = p_Language
         WHERE io.isActive = 'Y') footer
         INNER JOIN m_inout io ON io.m_inout_id = footer.m_inout_id
         LEFT JOIN AD_Ref_List o_dr ON o_dr.AD_Reference_ID = 151 AND o_dr.value = io.deliveryrule
         LEFT JOIN AD_Ref_List_Trl o_dr_trl ON o_dr.ad_ref_list_id = o_dr_trl.ad_ref_list_id AND o_dr_trl.ad_language = p_Language
         LEFT JOIN AD_Ref_List o_dvr ON o_dvr.AD_Reference_ID = 152 AND o_dvr.value = io.deliveryviarule
         LEFT JOIN AD_Ref_List_Trl o_dvr_trl ON o_dvr.ad_ref_list_id = o_dvr_trl.ad_ref_list_id AND o_dvr_trl.ad_language = p_Language

WHERE footer.m_inout_id = p_InOut_ID
  AND footer.language = p_Language
ORDER BY pozition

$$
    LANGUAGE sql STABLE
;
