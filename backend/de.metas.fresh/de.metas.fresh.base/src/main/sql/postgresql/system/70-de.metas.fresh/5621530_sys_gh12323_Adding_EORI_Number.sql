DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.docs_sales_order_description(IN record_id   numeric,
                                                                                        IN ad_language Character Varying(6))
;

CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.docs_sales_order_description(record_id   numeric,
                                                                                           ad_language character varying)
    RETURNS TABLE
            (
                description      character varying,
                documentno       character varying,
                dateordered      timestamp WITHOUT TIME ZONE,
                reference        text,
                isoffer          character,
                isprepay         character,
                offervaliddate   timestamp WITHOUT TIME ZONE,
                offervaliddays   numeric,
                bp_value         character varying,
                eori             character varying,
                cont_name        text,
                cont_phone       character varying,
                cont_fax         character varying,
                cont_email       character varying,
                sr_name          text,
                sr_phone         character varying,
                sr_fax           character varying,
                sr_email         character varying,
                printname        character varying,
                datepromised     timestamp WITH TIME ZONE,
                dt_description   text,
                offer_documentno character varying
            )
    STABLE
    LANGUAGE sql
AS
$$
SELECT o.description                             AS description,
       o.documentno                              AS documentno,
       o.dateordered                             AS dateordered,
       o.poreference                             AS reference,
       CASE
           WHEN dt.docbasetype = 'SOO' AND dt.docsubtype IN ('ON', 'OB')
               THEN 'Y'
               ELSE 'N'
       END                                       AS isoffer,
       CASE
           WHEN dt.docbasetype = 'SOO' AND dt.docsubtype = 'PR'
               THEN 'Y'
               ELSE 'N'
       END                                       AS isprepay,
       o.offervaliddate,
       o.offervaliddays,
       bp.value                                  AS bp_value,
       bp.eori                                   AS eori,
       COALESCE(cogrt.name, cogrt.name, '') ||
       COALESCE(' ' || cont.title, '') ||
       COALESCE(' ' || cont.firstName, '') ||
       COALESCE(' ' || cont.lastName, '')        AS cont_name,
       cont.phone                                AS cont_phone,
       cont.fax                                  AS cont_fax,
       cont.email                                AS cont_email,
       COALESCE(srgrt.name, srgr.name, '') ||
       COALESCE(' ' || srep.title, '') ||
       COALESCE(' ' || srep.firstName, '') ||
       COALESCE(' ' || srep.lastName, '')        AS sr_name,
       srep.phone                                AS sr_phone,
       srep.fax                                  AS sr_fax,
       srep.email                                AS sr_email,
       COALESCE(dtt.PrintName, dt.PrintName)     AS PrintName,
       o.datepromised,
       COALESCE(dtt.Description, dt.Description) AS dt_description,
       offer.documentno                          AS offer_documentno
FROM C_Order o
         INNER JOIN C_BPartner bp ON o.C_BPartner_ID = bp.C_BPartner_ID AND bp.isActive = 'Y'
         LEFT OUTER JOIN AD_User srep ON o.SalesRep_ID = srep.AD_User_ID AND srep.AD_User_ID <> 100 AND srep.isActive = 'Y'
         LEFT OUTER JOIN AD_User cont ON o.Bill_User_ID = cont.AD_User_ID AND cont.isActive = 'Y'
         LEFT OUTER JOIN C_DocType dt ON o.C_DocTypeTarget_ID = dt.C_DocType_ID AND dt.isActive = 'Y'
         LEFT OUTER JOIN C_DocType_Trl dtt ON o.C_DocTypeTarget_ID = dtt.C_DocType_ID AND dtt.AD_Language = $2 AND dtt.isActive = 'Y'

    -- Translatables
         LEFT OUTER JOIN C_Greeting cogr ON cont.C_Greeting_ID = cogr.C_Greeting_ID AND cogr.isActive = 'Y'
         LEFT OUTER JOIN C_Greeting_Trl cogrt ON cont.C_Greeting_ID = cogrt.C_Greeting_ID AND cogrt.ad_language = $2 AND cogrt.isActive = 'Y'
         LEFT OUTER JOIN C_Greeting srgr ON srep.C_Greeting_ID = srgr.C_Greeting_ID AND srgr.isActive = 'Y'
         LEFT OUTER JOIN C_Greeting_Trl srgrt ON srep.C_Greeting_ID = srgrt.C_Greeting_ID AND srgrt.ad_language = $2 AND srgrt.isActive = 'Y'

    -- proposal order
         LEFT JOIN C_Order offer ON offer.C_Order_ID = o.ref_proposal_id
WHERE o.C_Order_ID = $1
  AND o.isActive = 'Y'
$$
;

ALTER FUNCTION de_metas_endcustomer_fresh_reports.docs_sales_order_description(numeric, varchar) OWNER TO metasfresh
;

