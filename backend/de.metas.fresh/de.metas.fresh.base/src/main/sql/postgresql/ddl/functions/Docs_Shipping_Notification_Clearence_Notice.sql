DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Shipping_Notification_Clearence_Notice(p_record_id numeric)
;

CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Shipping_Notification_Clearence_Notice(p_record_id numeric)
    RETURNS TABLE
            (
                ad_org_id                  numeric,
                ad_user_id                 numeric,
                m_shipping_notification_id numeric,
                c_bpartner_id              numeric,
                c_bpartner_location_id     numeric,
                ShipFrom_Partner_ID        numeric,
                documentno                 varchar,
                poreference                varchar,
                orgName                    varchar,
                orgAddress                 varchar,
                varcharorgvataxid          varchar,
                orgPhone                   varchar,
                orgPhone2                  varchar,
                orgfax                     varchar,
                orgEmail                   varchar,
                orgURL                     varchar,
                customerAddress            varchar,
                customerPhone              varchar,
                customerFax                varchar,
                customerEmail              varchar,
                DeliveryFrom               varchar,
                deliveryfromPhone          varchar,
                deliveryfromFax            varchar,
                deliveryfromEmail          varchar,
                locatorName                varchar
            )
    STABLE
    LANGUAGE sql
AS
$$
SELECT sn.ad_org_id,
       sn.ad_user_id,
       sn.m_shipping_notification_id,
       sn.c_bpartner_id,
       sn.c_bpartner_location_id,
       sn.ShipFrom_Partner_ID,
       sn.documentno,
       sn.poreference,
       t3.name                                              AS orgName,
       (COALESCE(t3.address || E'\n', '') ||
        COALESCE(t3.postal, '') || ' ' || COALESCE(t3.city, '')) AS orgAddress,
       t3.vataxid                                           AS orgvataxid,
       t3.phone                                             AS orgPhone,
       t3.phone2                                            AS orgPhone2,
       t3.fax                                               AS orgfax,
       t3.email                                             AS orgEmail,
       t3.url                                               AS orgURL,
       sn.bpartneraddress                                   AS customerAddress,
       u.phone                                              AS customerPhone,
       u.fax                                                AS customerFax,
       u.email                                              AS customerEmail,
       sn.shipfromaddress                                   AS DeliveryFrom,
       wu.phone                                             AS deliveryfromPhone,
       wu.fax                                               AS deliveryfromFax,
       wu.email                                             AS deliveryfromEmail,
       COALESCE(l.name, l.value)                            AS locatorName
FROM M_Shipping_Notification sn
         INNER JOIN C_BPartner bp ON sn.C_BPartner_ID = bp.C_BPartner_ID
         LEFT OUTER JOIN AD_user u ON COALESCE(sn.ad_user_id, (SELECT ad_user_id FROM AD_user u1 WHERE u1.c_bpartner_id = sn.C_BPartner_ID ORDER BY isdefaultcontact DESC LIMIT 1)) = u.ad_user_id
         INNER JOIN c_bpartner wbp ON sn.ShipFrom_Partner_ID = wbp.c_bpartner_id
         LEFT OUTER JOIN AD_user wu ON COALESCE(sn.shipfrom_user_id, (SELECT ad_user_id FROM AD_user u2 WHERE u2.c_bpartner_id = sn.ShipFrom_Partner_ID ORDER BY isdefaultcontact DESC LIMIT 1)) = wu.ad_user_id
         INNER JOIN m_locator l ON sn.m_locator_id = l.m_locator_id
         INNER JOIN de_metas_endcustomer_fresh_reports.Docs_Generics_Org_Report(NULL, 'Y', sn.ad_org_ID) AS t3 ON TRUE
WHERE sn.M_Shipping_Notification_ID = p_record_id
  AND sn.isActive = 'Y'
    ;
$$
;