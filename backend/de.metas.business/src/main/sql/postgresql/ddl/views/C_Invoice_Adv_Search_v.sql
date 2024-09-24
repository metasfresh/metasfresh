DROP VIEW IF EXISTS C_Invoice_Adv_Search_v
;

CREATE OR REPLACE VIEW C_Invoice_Adv_Search_v AS
SELECT i.c_invoice_id,
       bp.c_bpartner_id,
       bpl.c_bpartner_location_id,

       COALESCE(u.ad_user_id, -1)                                                                              AS AD_User_ID,
       COALESCE(dt.c_doctype_id, -1)                                                                           AS C_DocType_ID,
       COALESCE(wh.m_warehouse_id, -1)                                                                         AS M_Warehouse_ID,
       COALESCE(cal.c_calendar_id, -1)                                                                         AS C_Calendar_ID,
       COALESCE(year.c_year_id, -1)                                                                            AS C_Year_ID,

       i.documentno,
       i.poreference,
       bp.value                                                                                                AS BPartnerValue,
       (SELECT ExternalReference
        FROM S_ExternalReference
        WHERE Type = 'BPartner'
          AND ExternalSystem = 'Other'
          AND referenced_ad_table_id = 291
          AND record_id = bp.C_BPartner_ID)                                                                    AS externalid,
       bp.iscompany,
       bp.name                                                                                                 AS bpName,
       COALESCE(u.firstname, bp.firstname)                                                                     AS firstname,
       COALESCE(u.lastname, bp.lastname)                                                                       AS lastname,
       bp.companyname                                                                                          AS companyname,
       l.address1,
       l.city,
       l.postal,
       dt.name                                                                                                 AS DocTypeName,
       wh.name                                                                                                 AS WarehouseName,
       cal.name                                                                                                AS CalendarName,
       year.fiscalyear                                                                                         AS FiscalYear,
       i.description,
       i.descriptionbottom,
       i.ad_client_id,
       i.ad_org_id,
       i.isactive,
       LEAST(i.created, bp.created, bpl.created, u.created, dt.created, wh.created, cal.created, year.created) AS created,
       0::numeric                                                                                              AS createdby,
       GREATEST(bp.updated, bpl.updated, u.updated, dt.updated, wh.updated, cal.updated, year.updated)         AS updated,
       0::numeric                                                                                              AS updatedby,
       --
       i.c_invoice_ID
           || '-' || bp.c_bpartner_id
           || '-' || bpl.c_bpartner_location_id
           || '-' || COALESCE(u.ad_user_id::varchar, 'X')
           || '-' || COALESCE(dt.c_doctype_id::varchar, 'X')
           || '-' || COALESCE(wh.m_warehouse_id::varchar, 'X')
           || '-' || COALESCE(cal.c_calendar_id::varchar, 'X')
           || '-' || COALESCE(year.c_year_id::varchar, 'X')                                                    AS es_documentid
FROM C_Invoice i
         INNER JOIN C_BPartner bp ON i.c_bpartner_id = bp.c_bpartner_id
         INNER JOIN C_BPartner_location bpl ON i.c_bpartner_location_id = bpl.c_bpartner_location_id
         INNER JOIN c_location l ON bpl.c_location_id = l.c_location_id AND l.isactive = 'Y'
         LEFT JOIN AD_User u ON i.ad_user_id = u.ad_user_id
         INNER JOIN c_doctype dt ON i.c_doctypetarget_id = dt.c_doctype_id
         LEFT JOIN m_warehouse wh ON i.m_warehouse_id = wh.m_warehouse_id
         LEFT JOIN c_calendar cal ON i.c_harvesting_calendar_id = cal.c_calendar_id
         LEFT JOIN c_year year ON i.harvesting_year_id = year.c_year_id
         INNER JOIN ad_org o ON i.ad_org_id = o.ad_org_id AND o.isactive = 'Y'
;

