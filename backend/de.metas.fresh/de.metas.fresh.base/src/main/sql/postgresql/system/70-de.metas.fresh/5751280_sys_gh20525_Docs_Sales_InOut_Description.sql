DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Sales_InOut_Description(IN record_id  numeric,
                                                                                        IN p_language Character Varying(6))
;

CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Sales_InOut_Description(record_id  numeric,
                                                                                           p_language character varying)
    RETURNS TABLE
            (
                description   character varying,
                documentno    character varying,
                movementdate  timestamp WITHOUT TIME ZONE,
                reference     character varying,
                bp_value      character varying,
                eori          character varying,
                customernoatvendor character varying,
                cont_name     text,
                cont_phone    character varying,
                cont_fax      character varying,
                cont_email    character varying,
                sr_name       text,
                sr_phone      character varying,
                sr_fax        character varying,
                sr_email      character varying,
                printname     character varying,
                order_docno   text,
                billtoaddress character varying,
                PreparationDate    timestamp with time zone
            )
    STABLE
    LANGUAGE sql
AS
$$
SELECT io.description                        AS description,
       io.documentno                         AS documentno,
       io.movementdate                       AS movementdate,
       io.poreference                        AS reference,
       bp.value                              AS bp_value,
       bp.eori                               AS eori,
       bp.customernoatvendor                 AS customernoatvendor,
       COALESCE(cogr.name, '') ||
       COALESCE(' ' || cont.title, '') ||
       COALESCE(' ' || cont.firstName, '') ||
       COALESCE(' ' || cont.lastName, '')    AS cont_name,
       cont.phone                            AS cont_phone,
       cont.fax                              AS cont_fax,
       cont.email                            AS cont_email,
       COALESCE(srgr.name, '') ||
       COALESCE(' ' || srep.title, '') ||
       COALESCE(' ' || srep.firstName, '') ||
       COALESCE(' ' || srep.lastName, '')    AS sr_name,
       srep.phone                            AS sr_phone,
       srep.fax                              AS sr_fax,
       srep.email                            AS sr_email,
       COALESCE(dtt.printname, dt.printname) AS printname,
       o.docno                               AS order_docno,
       REPLACE(
               REPLACE(ord.billtoaddress, E'\r\n', ' | '),
               E'\n', ' | '
       )                                     AS billtoaddress,
       o.PreparationDate
FROM m_inout io
         INNER JOIN C_DocType dt ON io.C_DocType_ID = dt.C_DocType_ID
         LEFT OUTER JOIN C_DocType_Trl dtt ON dt.C_DocType_ID = dtt.C_DocType_ID AND dtt.AD_Language = p_language
         INNER JOIN c_bpartner bp ON io.c_bpartner_id = bp.c_bpartner_id
         INNER JOIN c_order ord ON io.c_order_id = ord.c_order_id
         LEFT OUTER JOIN AD_User srep ON io.SalesRep_ID = srep.AD_User_ID AND srep.AD_User_ID <> 100
         LEFT OUTER JOIN AD_User cont ON io.AD_User_ID = cont.AD_User_ID
         LEFT OUTER JOIN C_Greeting cogr ON cont.C_Greeting_ID = cogr.C_Greeting_ID
         LEFT OUTER JOIN C_Greeting srgr ON srep.C_Greeting_ID = srgr.C_Greeting_ID

         LEFT JOIN LATERAL
    (
    SELECT First_Agg(o.DocumentNo ORDER BY o.DocumentNo) ||
           CASE WHEN COUNT(DISTINCT o.documentNo) > 1 THEN ' ff.' ELSE '' END AS DocNo,
           o.PreparationDate
    FROM M_InOutLine iol
             JOIN C_OrderLine ol ON iol.C_OrderLine_ID = ol.C_OrderLine_ID
             JOIN C_Order o ON ol.C_Order_ID = o.C_Order_ID

    WHERE iol.M_InOut_ID = record_id
    GROUP BY o.PreparationDate
    ) o ON TRUE
WHERE io.m_inout_id = record_id
$$
;
