DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Sales_DD_Order_Description (IN Record_ID numeric, IN AD_Language Character Varying(6))
;

DROP TABLE IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Sales_DD_Order_Description
;

CREATE TABLE de_metas_endcustomer_fresh_reports.Docs_Sales_DD_Order_Description
(
    Description Character Varying(255),
    DocumentNo  Character Varying(30),
    dateordered  Timestamp WITHOUT TIME ZONE,
    Reference   Character Varying(40),
    BP_Value    Character Varying(40),
    Cont_Name   Character Varying(40),
    Cont_Phone  Character Varying(40),
    Cont_Fax    Character Varying(40),
    Cont_Email  Character Varying(60),
    SR_Name     Text,
    SR_Phone    Character Varying(40),
    SR_Fax      Character Varying(40),
    SR_Email    Character Varying(60),
    PrintName   Character Varying(60),
    order_docno Character Varying(30)
)
;


CREATE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Sales_DD_Order_Description(IN Record_ID   numeric,
                                                                                   IN AD_Language Character Varying(6))
    RETURNS SETOF de_metas_endcustomer_fresh_reports.Docs_Sales_DD_Order_Description
AS
$$
SELECT ddo.description                       AS description,
       ddo.documentno                        AS documentno,
       ddo.dateordered,
       ddo.poreference                       AS reference,
       bp.value                              AS bp_value,
       Coalesce(cogr.name, '') ||
       Coalesce(' ' || cont.title, '') ||
       Coalesce(' ' || cont.firstName, '') ||
       Coalesce(' ' || cont.lastName, '')    AS cont_name,
       cont.phone                            AS cont_phone,
       cont.fax                              AS cont_fax,
       cont.email                            AS cont_email,
       Coalesce(srgr.name, '') ||
       Coalesce(' ' || srep.title, '') ||
       Coalesce(' ' || srep.firstName, '') ||
       Coalesce(' ' || srep.lastName, '')    AS sr_name,
       srep.phone                            AS sr_phone,
       srep.fax                              AS sr_fax,
       srep.email                            AS sr_email,
       COALESCE(dtt.printname, dt.printname) AS printname,
       o.docno                               AS order_docno
FROM dd_order ddo
         INNER JOIN C_DocType dt ON ddo.C_DocType_ID = dt.C_DocType_ID AND dt.isActive = 'Y'
         LEFT OUTER JOIN C_DocType_Trl dtt ON dt.C_DocType_ID = dtt.C_DocType_ID AND dtt.AD_Language = $2 AND dtt.isActive = 'Y'
         INNER JOIN c_bpartner bp ON ddo.c_bpartner_id = bp.c_bpartner_id AND bp.isActive = 'Y'
         LEFT OUTER JOIN AD_User srep ON ddo.SalesRep_ID = srep.AD_User_ID AND srep.AD_User_ID <> 100 AND srep.isActive = 'Y'
         LEFT OUTER JOIN AD_User cont ON ddo.AD_User_ID = cont.AD_User_ID AND cont.isActive = 'Y'
         LEFT OUTER JOIN C_Greeting cogr ON cont.C_Greeting_ID = cogr.C_Greeting_ID AND cogr.isActive = 'Y'
         LEFT OUTER JOIN C_Greeting srgr ON srep.C_Greeting_ID = srgr.C_Greeting_ID AND srgr.isActive = 'Y'

         LEFT JOIN LATERAL
    (
    SELECT First_Agg(o.DocumentNo ORDER BY o.DocumentNo) ||
           CASE WHEN Count(DISTINCT o.documentNo) > 1 THEN ' ff.' ELSE '' END AS DocNo
    FROM dd_orderline ddol
             JOIN C_OrderLine ol ON ddol.C_OrderLineSO_ID = ol.C_OrderLine_ID
             JOIN C_Order o ON ol.C_Order_ID = o.C_Order_ID

    WHERE ddol.DD_Order_ID = $1
    ) o ON TRUE
WHERE ddo.dd_order_id = $1
  AND ddo.isActive = 'Y'
$$
    LANGUAGE SQL STABLE
;
