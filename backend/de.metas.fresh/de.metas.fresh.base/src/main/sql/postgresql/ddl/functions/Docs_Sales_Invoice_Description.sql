DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.docs_sales_invoice_description(IN record_id   numeric,
                                                                                          IN ad_language Character Varying(6))
;

CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.docs_sales_invoice_description(c_invoice_id numeric,
                                                                                             ad_language  character varying)
    RETURNS TABLE
            (
                description      character varying,
                documentno       character varying,
                reference        character varying,
                dateinvoiced     timestamp WITHOUT TIME ZONE,
                vataxid          character varying,
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
                order_docno      text,
                inout_docno      text,
                io_movementdate  date,
                iscreditmemo     character,
                creditmemo_docno character varying
            )
    STABLE
    LANGUAGE sql
AS
$$
SELECT i.description                         AS description,
       i.documentno                          AS documentno,
       i.poreference                         AS reference,
       i.dateinvoiced                        AS dateinvoiced,
       bp.VATaxID,
       bp.value                              AS bp_value,
       bp.eori                               AS eori,
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
       COALESCE(dtt.PrintName, dt.PrintName) AS PrintName,
       o.docno                               AS order_docno,
       io.docno                              AS inout_docno,
       io.DateFrom                           AS io_movementdate,
       CASE
           WHEN dt.docbasetype = 'ARC'
               THEN 'Y'
               ELSE 'N'
       END                                   AS isCreditMemo,
       cm.documentno                         AS creditmemo_docNo
FROM C_Invoice i
         JOIN C_BPartner bp ON i.C_BPartner_ID = bp.C_BPartner_ID AND bp.isActive = 'Y'
         LEFT JOIN AD_User srep ON i.SalesRep_ID = srep.AD_User_ID AND srep.AD_User_ID <> 100 AND srep.isActive = 'Y'
         LEFT JOIN AD_User cont ON i.AD_User_ID = cont.AD_User_ID AND cont.isActive = 'Y'
         LEFT JOIN C_Greeting cogr ON cont.C_Greeting_ID = cogr.C_Greeting_ID AND cogr.isActive = 'Y'
         LEFT JOIN C_Greeting srgr ON srep.C_Greeting_ID = srgr.C_Greeting_ID AND srgr.isActive = 'Y'
         LEFT JOIN C_Invoice cm ON cm.C_Invoice_id = i.ref_invoice_id
         LEFT OUTER JOIN C_DocType dt ON i.C_DocTypeTarget_ID = dt.C_DocType_ID AND dt.isActive = 'Y'
         LEFT OUTER JOIN C_DocType_Trl dtt ON i.C_DocTypeTarget_ID = dtt.C_DocType_ID AND dtt.AD_Language = $2 AND dtt.isActive = 'Y'
         LEFT JOIN LATERAL
    (
    SELECT First_Agg(o.DocumentNo ORDER BY o.DocumentNo) ||
           CASE WHEN COUNT(o.documentNo) > 1 THEN ' ff.' ELSE '' END AS DocNo
    FROM C_InvoiceLine il
             JOIN C_OrderLine ol ON il.C_OrderLine_ID = ol.C_OrderLine_ID
             JOIN C_Order o ON ol.C_Order_ID = o.C_Order_ID

    WHERE il.C_Invoice_ID = $1
    ) o ON TRUE

         LEFT JOIN LATERAL
    (
    SELECT First_Agg(io.DocumentNo ORDER BY io.DocumentNo) ||
           CASE WHEN COUNT(io.documentNo) > 1 THEN ' ff.' ELSE '' END AS DocNo,
           MIN(io.MovementDate)::date                                 AS DateFrom
    FROM C_InvoiceLine il
             JOIN M_InOutLine iol ON il.M_InOutLine_ID = iol.M_InOutLine_ID
             JOIN M_InOut io ON iol.M_InOut_ID = io.M_InOut_ID

    WHERE il.C_Invoice_ID = $1
    ) io ON TRUE


WHERE i.C_Invoice_ID = $1
  AND i.isActive = 'Y'

$$
;

ALTER FUNCTION de_metas_endcustomer_fresh_reports.docs_sales_invoice_description(numeric, varchar) OWNER TO metasfresh
;