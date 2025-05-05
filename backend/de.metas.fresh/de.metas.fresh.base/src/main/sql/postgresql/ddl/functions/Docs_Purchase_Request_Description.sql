DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Purchase_Request_Description(numeric,
                                                                                     varchar)
;

CREATE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Purchase_Request_Description(record_id  numeric,
                                                                             p_language character varying)
    RETURNS TABLE
            (
                description  text,
                documentno   character varying,
                datedoc      timestamp WITHOUT TIME ZONE,
                daterequired timestamp WITHOUT TIME ZONE,
                requestor    character varying,
                receiver     character varying,
                deliverto    character varying,
                printname    character varying
            )
    STABLE
    LANGUAGE sql
AS
$$
SELECT r.description                         AS description,
       r.documentno                          AS documentno,
       r.datedoc                             AS datedoc,
       r.daterequired                        AS daterequired,

       COALESCE(requestor.firstName, '') ||' '||
       COALESCE(requestor.lastName, '')      AS requestor,

       COALESCE(receiver.firstName, '') ||' '||
       COALESCE(receiver.lastName, '')       AS receiver,

       COALESCE(loc.address1, '') ||
       COALESCE(E'\n' || loc.postal, '') ||
       COALESCE(E'\n' || loc.city, '') ||
       COALESCE(E'\n' || count_trl.name, '') AS deliverto,

       COALESCE(dtt.PrintName, dt.PrintName) AS PrintName

FROM M_Requisition r
         INNER JOIN AD_User requestor ON r.AD_User_ID = requestor.AD_User_ID AND requestor.isActive = 'Y'
         LEFT OUTER JOIN C_Greeting reqgr ON requestor.C_Greeting_ID = reqgr.C_Greeting_ID AND reqgr.isActive = 'Y'

         LEFT OUTER JOIN AD_User receiver ON r.Receiver_ID = receiver.AD_User_ID AND receiver.isActive = 'Y'
         LEFT OUTER JOIN C_Greeting recgr ON receiver.C_Greeting_ID = recgr.C_Greeting_ID AND recgr.isActive = 'Y'

         LEFT OUTER JOIN c_bpartner_location rbpl ON r.warehouse_location_id = rbpl.c_bpartner_location_id
         LEFT OUTER JOIN c_location loc ON rbpl.c_location_id = loc.c_location_id
         LEFT OUTER JOIN c_country count ON loc.c_country_id = count.c_country_id
         LEFT OUTER JOIN c_country_trl count_trl ON count.c_country_id = count_trl.c_country_id AND count_trl.AD_Language = p_language

         LEFT OUTER JOIN C_DocType dt ON r.C_DocType_ID = dt.C_DocType_ID AND dt.isActive = 'Y'
         LEFT OUTER JOIN C_DocType_Trl dtt ON dt.C_DocType_ID = dtt.C_DocType_ID AND dtt.AD_Language = p_language AND dtt.isActive = 'Y'

WHERE r.M_Requisition_ID = record_id
  AND r.isActive = 'Y'

$$
;

ALTER FUNCTION de_metas_endcustomer_fresh_reports.Docs_Purchase_Request_Description(numeric, varchar) OWNER TO metasfresh
;
