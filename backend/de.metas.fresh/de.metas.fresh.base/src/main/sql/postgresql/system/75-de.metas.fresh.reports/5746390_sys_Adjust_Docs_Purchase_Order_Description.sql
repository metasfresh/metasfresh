DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Purchase_Order_Description(IN record_id  numeric,
                                                                                           IN p_language Character Varying(6))
;

CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Purchase_Order_Description(record_id  numeric,
                                                                                              p_language character varying)
    RETURNS TABLE
            (
                description   character varying,
                documentno    character varying,
                reference     text,
                dateordered   timestamp WITHOUT TIME ZONE,
                datepromised  timestamp WITH TIME ZONE,
                deliverto     character varying,
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
                billtoaddress character varying
            )
    STABLE
    LANGUAGE sql
AS
$$
SELECT o.description                         AS description,
       o.documentno                          AS documentno,
       TRIM(o.poreference)                   AS reference,
       o.dateordered                         AS dateordered,
       o.datepromised                        AS datepromised,
       REPLACE(
               REPLACE(o.DeliveryToAddress, E'\r\n', ' | '),
               E'\n', ' | '
       )                                     AS deliverto,
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
       TRIM(
               COALESCE(srgr.name, '') ||
               COALESCE(' ' || srep.title, '') ||
               COALESCE(' ' || srep.firstName, '') ||
               COALESCE(' ' || srep.lastName, '')
       )                                     AS sr_name,
       srep.phone                            AS sr_phone,
       srep.fax                              AS sr_fax,
       srep.email                            AS sr_email,
       COALESCE(dtt.PrintName, dt.PrintName) AS PrintName,
       REPLACE(
               REPLACE(o.billtoaddress, E'\r\n', ' | '),
               E'\n', ' | '
       )                                     AS billtoaddress
FROM C_Order o
         INNER JOIN C_BPartner bp ON o.C_BPartner_ID = bp.C_BPartner_ID
         LEFT OUTER JOIN AD_User srep ON o.SalesRep_ID = srep.AD_User_ID
         LEFT OUTER JOIN AD_User cont ON o.AD_User_ID = cont.AD_User_ID
         LEFT OUTER JOIN C_Greeting cogr ON cont.C_Greeting_ID = cogr.C_Greeting_ID
         LEFT OUTER JOIN C_Greeting srgr ON srep.C_Greeting_ID = srgr.C_Greeting_ID
         LEFT OUTER JOIN C_DocType dt ON o.C_DocTypeTarget_ID = dt.C_DocType_ID
         LEFT OUTER JOIN C_DocType_Trl dtt ON o.C_DocTypeTarget_ID = dtt.C_DocType_ID AND dtt.AD_Language = p_language

WHERE o.c_order_id = record_id
  AND o.isActive = 'Y'
$$
;

