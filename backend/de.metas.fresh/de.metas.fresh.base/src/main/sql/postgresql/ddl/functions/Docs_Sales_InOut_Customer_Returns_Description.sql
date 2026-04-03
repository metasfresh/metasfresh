DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Sales_InOut_Customer_Returns_Description(IN p_record_id   numeric,
                                                                                                         IN p_AD_Language Character Varying(6))
;

CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Sales_InOut_Customer_Returns_Description(IN p_record_id   numeric,
                                                                                                            IN p_AD_Language Character Varying(6))
    RETURNS TABLE
            (
                printname    character varying(60),
                documentno   character varying(30),
                bp_value     character varying(40),
                movementdate timestamp without time zone,
                sr_name      text,
                sr_phone     character varying,
                sr_fax       character varying,
                sr_email     character varying,
                vataxid      character varying,
                order_docno  text,
                description  character varying
            )
AS
$$
SELECT COALESCE(dtt.printName, dt.printName) AS printname,
       io.DocumentNo                         AS documentNo,
       bp.Value                              AS BP_Value,
       io.movementDate                       AS movementDate,
       COALESCE(srgr.name, '') ||
       COALESCE(' ' || srep.title, '') ||
       COALESCE(' ' || srep.firstName, '') ||
       COALESCE(' ' || srep.lastName, '')    AS sr_name,
       srep.phone                            AS sr_phone,
       srep.fax                              AS sr_fax,
       CASE
           WHEN report.IsHiddenReportElement(io.C_DocType_ID, 'SalesRep_Email') = 'N' THEN
               srep.email
       END                                   AS sr_email,
       CASE
           WHEN report.IsHiddenReportElement(io.C_DocType_ID, 'VATaxID') = 'N' THEN
               bp.VATaxID
       END                                   AS VATaxID,
       o.docno                               AS order_docno,
       io.description                        AS description

FROM M_Inout io --customer return
         INNER JOIN C_DocType dt ON io.C_DocType_ID = dt.C_DocType_ID
         LEFT OUTER JOIN C_DocType_Trl dtt ON dt.C_DocType_ID = dtt.C_DocType_ID AND dtt.AD_Language = p_AD_Language
         INNER JOIN C_BPartner bp ON io.C_BPartner_ID = bp.C_BPartner_ID
         LEFT OUTER JOIN AD_User srep ON io.SalesRep_ID = srep.AD_User_ID
         LEFT OUTER JOIN C_Greeting srgr ON srep.C_Greeting_ID = srgr.C_Greeting_ID
         LEFT JOIN LATERAL
    (
    SELECT First_Agg(o.DocumentNo ORDER BY o.DocumentNo) ||
           CASE WHEN COUNT(DISTINCT o.documentNo) > 1 THEN ' ff.' ELSE '' END AS DocNo
    FROM M_InOutLine iol
             JOIN C_OrderLine ol ON iol.C_OrderLine_ID = ol.C_OrderLine_ID
             JOIN C_Order o ON ol.C_Order_ID = o.C_Order_ID
             LEFT JOIN C_Order offer ON offer.C_Order_ID = o.ref_proposal_id
    WHERE iol.M_InOut_ID = p_record_id
    GROUP BY o.billtoaddress
    ) o ON TRUE
WHERE io.M_InOut_ID = p_record_id
$$
    LANGUAGE sql STABLE
;