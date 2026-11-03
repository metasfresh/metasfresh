DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Sales_InOut_Description(IN record_id  numeric,
                                                                                        IN p_language Character Varying(6))
;

CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Sales_InOut_Description(record_id  numeric,
                                                                                           p_language character varying)
    RETURNS TABLE
            (
                description        character varying,
                documentno         character varying,
                movementdate       timestamp WITHOUT TIME ZONE,
                reference          character varying,
                bp_value           character varying,
                vataxid            character varying,
                eori               character varying,
                customernoatvendor character varying,
                cont_name          text,
                cont_phone         character varying,
                cont_fax           character varying,
                cont_email         character varying,
                sr_name            text,
                sr_phone           character varying,
                sr_fax             character varying,
                sr_email           character varying,
                printname          character varying,
                order_docno        text,
                order_date         text,
                offer_documentno   character varying,
                offer_date         text,
                billtoaddress      character varying,
                PreparationDate    text,
                docstatus          char(2),
                delivery_week_year character varying
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
       CASE
           WHEN report.IsHiddenReportElement(io.C_DocType_ID, 'VATaxID') = 'N' THEN
               bp.VATaxID
       END                                   AS VATaxID,
       bp.eori                               AS eori,
       CASE
           WHEN report.IsHiddenReportElement(io.C_DocType_ID, 'Customer_No_At_Vendor') = 'N' THEN
               bp.customernoatvendor
       END                                   AS customernoatvendor,
       COALESCE(cogr.name, '') ||
       COALESCE(' ' || cont.title, '') ||
       COALESCE(' ' || cont.firstName, '') ||
       COALESCE(' ' || cont.lastName, '')    AS cont_name,
       CASE
           WHEN report.IsHiddenReportElement(io.C_DocType_ID, 'Contact_Phone') = 'N' THEN
               cont.phone
       END                                   AS cont_phone,
       CASE
           WHEN report.IsHiddenReportElement(io.C_DocType_ID, 'Contact_Fax') = 'N' THEN
               cont.fax
       END                                   AS cont_fax,
       cont.email                            AS cont_email,
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
       COALESCE(dtt.printname, dt.printname) AS printname,
       o.docno                               AS order_docno,
       o.dateordered                         AS order_date,
       CASE
           WHEN report.IsHiddenReportElement(io.C_DocType_ID, 'Offer_DocumentNo') = 'N' THEN
               o.offer_documentno
       END                                   AS offer_documentno,
       CASE
           WHEN report.IsHiddenReportElement(io.C_DocType_ID, 'Offer_Date') = 'N' THEN
               o.offer_date
       END                                   AS offer_date,
       o.billtoaddress,
       o.PreparationDate,
       io.docstatus,
       CASE
           WHEN report.IsHiddenReportElement(io.C_DocType_ID, 'Delivery_Week_Year') = 'N' THEN
               TO_CHAR(io.MovementDate, 'WW') || '.' || TO_CHAR(io.MovementDate, 'YY')
       END                                   AS delivery_week_year
FROM m_inout io
         INNER JOIN C_DocType dt ON io.C_DocType_ID = dt.C_DocType_ID
         LEFT OUTER JOIN C_DocType_Trl dtt ON dt.C_DocType_ID = dtt.C_DocType_ID AND dtt.AD_Language = p_language
         INNER JOIN c_bpartner bp ON io.c_bpartner_id = bp.c_bpartner_id
         LEFT OUTER JOIN AD_User srep ON io.SalesRep_ID = srep.AD_User_ID AND srep.AD_User_ID <> 100
         LEFT OUTER JOIN AD_User cont ON io.AD_User_ID = cont.AD_User_ID
         LEFT OUTER JOIN C_Greeting cogr ON cont.C_Greeting_ID = cogr.C_Greeting_ID
         LEFT OUTER JOIN C_Greeting srgr ON srep.C_Greeting_ID = srgr.C_Greeting_ID

         LEFT JOIN LATERAL
    (
    SELECT First_Agg(o.DocumentNo ORDER BY o.DocumentNo) ||
           CASE WHEN COUNT(DISTINCT o.documentNo) > 1 THEN ' ff.' ELSE '' END AS DocNo,


           First_Agg(o.dateordered::text ORDER BY o.dateordered) ||
           CASE WHEN COUNT(o.dateordered) > 1 THEN ' ff.' ELSE '' END         AS dateordered,

           First_Agg(offer.DocumentNo ORDER BY offer.DocumentNo) ||
           CASE WHEN COUNT(offer.DocumentNo) > 1 THEN ' ff.' ELSE '' END      AS offer_documentno,

           First_Agg(offer.dateordered::text ORDER BY offer.dateordered) ||
           CASE WHEN COUNT(offer.dateordered) > 1 THEN ' ff.' ELSE '' END     AS offer_date,

           First_Agg(o.dateordered::text ORDER BY o.PreparationDate) ||
           CASE WHEN COUNT(o.dateordered) > 1 THEN ' ff.' ELSE '' END         AS PreparationDate,


           REPLACE(
                   REPLACE(o.billtoaddress, E'\r\n', ' | '),
                   E'\n', ' | '
           )                                                                  AS billtoaddress
    FROM M_InOutLine iol
             JOIN C_OrderLine ol ON iol.C_OrderLine_ID = ol.C_OrderLine_ID
             JOIN C_Order o ON ol.C_Order_ID = o.C_Order_ID
             LEFT JOIN C_Order offer ON offer.C_Order_ID = o.ref_proposal_id

    WHERE iol.M_InOut_ID = record_id
    GROUP BY o.billtoaddress
    ) o ON TRUE
WHERE io.m_inout_id = record_id
$$
;
