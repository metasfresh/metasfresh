DROP VIEW IF EXISTS C_BPartner_Export_View
;

CREATE VIEW C_BPartner_Export_View AS
SELECT bp.created,
       bp.updated,
       bp.createdby,
       bp.updatedby,
       bp.isactive,
       bp.ad_client_id,

       bp.C_Bpartner_ID                                                           AS C_BPartner_Export_ID,
       bp.c_bpartner_id,
       bp.AD_Org_ID,

       CASE
           WHEN term.c_flatrate_term_id IS NOT NULL THEN 'Mitglied'
           WHEN o.c_order_id IS NOT NULL            THEN 'Kunde'
                                                    ELSE 'Abonnent'
       END                                                                        AS Category,

       term.contractstatus,
       bp.Value                                                                   AS bpValue,
       CASE WHEN (bp.iscompany = 'N') THEN bp.name END AS bpName,
       bp.companyname,
       bp.ad_language,
       bp.excludefrompromotions,
       u.C_Greeting_ID,
       g.greeting,
       g.letter_salutation,

       u.firstname,
       u.lastname,
       u.birthday,
       l.address1,
       l.address2,
       l.address3,
       l.address4,
       l.C_Postal_ID,
       l.postal,
       l.city,
       l.c_country_id,
       u.emailuser,


       term.c_flatrate_term_id,
       term.masterstartdate,
       term.masterenddate,
       term.terminationreason,


       o.C_Order_ID,
       CASE
           WHEN ((o.c_bpartner_id != o.bill_bpartner_id) OR (o.c_bpartner_location_id != o.bill_location_id))
               THEN 'Y'
               ELSE 'N'
       END                                                                        AS hasDifferentBillpartner,

       schema.c_compensationgroup_schema_id,
       bp.mktg_campaign_id


FROM C_BPartner bp
         LEFT JOIN AD_User u
                   ON bp.C_BPartner_Id = u.C_BPartner_ID AND u.AD_User_ID = (SELECT AD_User_ID
                                                                              FROM AD_User
                                                                              WHERE C_BPartner_ID = bp.C_BPartner_ID
                                                                              ORDER BY isactive DESC, IsDefaultContact DESC
                                                                              LIMIT 1)
         LEFT JOIN C_BPartner_Location bpl
                   ON bp.C_BPartner_ID = bpl.C_BPartner_ID
                       AND bpl.c_bpartner_location_id = (SELECT c_bpartner_location_id
                                                          FROM c_bpartner_location
                                                          WHERE c_bpartner_id = bp.c_bpartner_id
                                                          ORDER BY isActive DESC, isbilltodefault DESC, isbillto DESC
                                                          LIMIT 1)
         LEFT JOIN C_Location l ON bpl.c_location_id = l.c_location_id
         LEFT JOIN C_Greeting g ON u.C_Greeting_ID = g.c_greeting_id
         LEFT JOIN C_Flatrate_Term term
                   ON term.bill_bpartner_id = bp.c_bpartner_id
                       AND term.C_Flatrate_Term_ID = (SELECT C_Flatrate_Term_ID
                                                       FROM C_Flatrate_Term
                                                       WHERE bill_bpartner_id = bp.c_bpartner_id
                                                         AND DocStatus IN ('CO', 'CL')
                                                       ORDER BY (COALESCE(masterenddate, endDate)) DESC
                                                       LIMIT 1)
         LEFT JOIN M_Product termProduct ON term.m_product_id = termProduct.m_product_id
         LEFT JOIN c_compensationgroup_schema schema ON termProduct.c_compensationgroup_schema_id = schema.c_compensationgroup_schema_id
         LEFT JOIN C_Order o
                   ON bp.c_bpartner_id = o.bill_bpartner_id
                       AND o.c_order_ID =(SELECT c_order_ID
                                            FROM C_Order
                                            WHERE c_bpartner_id = bp.c_bpartner_id
                                              AND issotrx = 'Y'
                                              AND docstatus IN ('CO', 'CL')
                                            ORDER BY dateordered DESC
                                            LIMIT 1)


;


CREATE OR REPLACE FUNCTION public.Update_C_BPartner_Export(
	)
    RETURNS boolean
    LANGUAGE 'plpgsql'
    COST 100
    VOLATILE 
AS $BODY$
BEGIN
 
 TRUNCATE C_BPartner_Export;
 
DROP VIEW IF EXISTS C_BPartner_Export_View
;

CREATE VIEW C_BPartner_Export_View AS
SELECT bp.created,
       bp.updated,
       bp.createdby,
       bp.updatedby,
       bp.isactive,
       bp.ad_client_id,

       bp.C_Bpartner_ID                                                           AS C_BPartner_Export_ID,
       bp.c_bpartner_id,
       bp.AD_Org_ID,

       CASE
           WHEN term.c_flatrate_term_id IS NOT NULL THEN 'Mitglied'
           WHEN o.c_order_id IS NOT NULL            THEN 'Kunde'
                                                    ELSE 'Abonnent'
       END                                                                        AS Category,

       term.contractstatus,
       bp.Value                                                                   AS bpValue,
       CASE WHEN (bp.iscompany = 'N') THEN bp.name END AS bpName,
       bp.companyname,
       bp.ad_language,
       bp.excludefrompromotions,
       u.C_Greeting_ID,
       g.greeting,
       g.letter_salutation,

       u.firstname,
       u.lastname,
       u.birthday,
       l.address1,
       l.address2,
       l.address3,
       l.address4,
       l.C_Postal_ID,
       l.postal,
       l.city,
       l.c_country_id,
       u.emailuser,


       term.c_flatrate_term_id,
       term.masterstartdate,
       term.masterenddate,
       term.terminationreason,


       o.C_Order_ID,
       CASE
           WHEN ((o.c_bpartner_id != o.bill_bpartner_id) OR (o.c_bpartner_location_id != o.bill_location_id))
               THEN 'Y'
               ELSE 'N'
       END                                                                        AS hasDifferentBillpartner,

       schema.c_compensationgroup_schema_id,
       bp.mktg_campaign_id


FROM C_BPartner bp
         LEFT JOIN AD_User u
                   ON bp.C_BPartner_Id = u.C_BPartner_ID AND u.AD_User_ID = (SELECT AD_User_ID
                                                                              FROM AD_User
                                                                              WHERE C_BPartner_ID = bp.C_BPartner_ID
                                                                              ORDER BY isactive DESC, IsDefaultContact DESC
                                                                              LIMIT 1)
         LEFT JOIN C_BPartner_Location bpl
                   ON bp.C_BPartner_ID = bpl.C_BPartner_ID
                       AND bpl.c_bpartner_location_id = (SELECT c_bpartner_location_id
                                                          FROM c_bpartner_location
                                                          WHERE c_bpartner_id = bp.c_bpartner_id
                                                          ORDER BY isActive DESC, isbilltodefault DESC, isbillto DESC
                                                          LIMIT 1)
         LEFT JOIN C_Location l ON bpl.c_location_id = l.c_location_id
         LEFT JOIN C_Greeting g ON u.C_Greeting_ID = g.c_greeting_id
         LEFT JOIN C_Flatrate_Term term
                   ON term.bill_bpartner_id = bp.c_bpartner_id
                       AND term.C_Flatrate_Term_ID = (SELECT C_Flatrate_Term_ID
                                                       FROM C_Flatrate_Term
                                                       WHERE bill_bpartner_id = bp.c_bpartner_id
                                                         AND DocStatus IN ('CO', 'CL')
                                                       ORDER BY (COALESCE(masterenddate, endDate)) DESC
                                                       LIMIT 1)
         LEFT JOIN M_Product termProduct ON term.m_product_id = termProduct.m_product_id
         LEFT JOIN c_compensationgroup_schema schema ON termProduct.c_compensationgroup_schema_id = schema.c_compensationgroup_schema_id
         LEFT JOIN C_Order o
                   ON bp.c_bpartner_id = o.bill_bpartner_id
                       AND o.c_order_ID =(SELECT c_order_ID
                                            FROM C_Order
                                            WHERE c_bpartner_id = bp.c_bpartner_id
                                              AND issotrx = 'Y'
                                              AND docstatus IN ('CO', 'CL')
                                            ORDER BY dateordered DESC
                                            LIMIT 1)


;









INSERT INTO c_bpartner_export
(ad_client_id, address1, address2, address3, address4, ad_org_id, birthday, bpname, bpvalue, category, c_bpartner_export_id,
 c_country_id, c_greeting_id, city, created, createdby, emailuser, firstname, greeting, isactive, lastname, letter_salutation,
 masterenddate, masterstartdate, postal, terminationreason, updated, updatedby, c_postal_id, ad_language, companyname, contractstatus,
 mktg_campaign_id, c_compensationgroup_schema_id, hasdifferentbillpartner, c_flatrate_term_id, C_Order_ID, C_BPartner_ID)

SELECT ad_client_id,
       address1,
       address2,
       address3,
       address4,
       ad_org_id,
       birthday,
       bpname,
       bpvalue,
       category,
       c_bpartner_export_id,
       c_country_id,
       c_greeting_id,
       city,
       created,
       createdby,
       emailuser,
       firstname,
       greeting,
       isactive,
       lastname,
       letter_salutation,
       masterenddate,
       masterstartdate,
       postal,
       terminationreason,
       updated,
       updatedby,
       c_postal_id,
       ad_language,
       companyname,
       contractstatus,
       mktg_campaign_id,
       c_compensationgroup_schema_id,
       hasdifferentbillpartner,
       c_flatrate_term_id,
       C_Order_ID,
       C_BPartner_ID

FROM c_bpartner_export_view
;



 return true;
END;
$BODY$;

ALTER FUNCTION public.Update_C_BPartner_Export()
    OWNER TO metasfresh;

COMMENT ON FUNCTION public.Update_C_BPartner_Export()
    IS 'This function truncates the table C_BPartner_Export, recreates the view C_BPartner_Export_View and inserts from the view into the table.
	It will be called once every night via a scheduler.'
	
	
	
--Select Update_C_BPartner_Export();