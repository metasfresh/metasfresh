DROP FUNCTION IF EXISTS C_Invoice_Adv_Search_Update(
    p_C_Invoice_IDs numeric[]
)
;


DROP FUNCTION IF EXISTS C_Invoice_Adv_Search_Update()
;

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
       i.ispaid,
       i.ispartiallypaid,
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

CREATE FUNCTION C_Invoice_Adv_Search_Update(p_c_invoice_ids numeric[])
    RETURNS TABLE
            (
                operation     character,
                es_documentid character varying
            )
    LANGUAGE plpgsql
AS
$BODY$
DECLARE
    v_deleted_documentIds  varchar[];
    v_inserted_documentIds varchar[];
BEGIN
    RAISE NOTICE 'p_C_Invoice_IDs: %', p_C_Invoice_IDs;
    IF (p_C_Invoice_IDs IS NULL OR CARDINALITY(p_C_Invoice_IDs) <= 0) THEN
        RAISE EXCEPTION 'At least one C_Invoice_ID shall be specified';
    END IF;

    --
    -- Delete old rows
    WITH deleted_rows AS (
        DELETE FROM C_Invoice_Adv_Search t
            WHERE
                (p_C_Invoice_IDs IS NULL OR t.C_Invoice_ID = ANY (p_C_Invoice_IDs))
            RETURNING t.es_documentid)
    SELECT ARRAY_AGG(deleted_rows.es_documentid)
    INTO v_deleted_documentIds
    FROM deleted_rows;

    --
    -- Insert new rows
    WITH inserted_rows AS (
        INSERT INTO c_invoice_adv_search (ad_client_id, address1, ad_org_id, bpname, calendarname, c_bpartner_id, city, companyname, created, createdby, description, descriptionbottom, doctypename, documentno, es_documentid, externalid, firstname, fiscalyear, isactive, iscompany, lastname, poreference, postal, updated, updatedby, warehousename, ad_user_id, c_bpartner_location_id, c_calendar_id,
                                          c_doctype_id, c_invoice_id, c_year_id, m_warehouse_id, bpartnervalue, ispaid, ispartiallypaid)
            SELECT v.ad_client_id,
                   v.address1,
                   v.ad_org_id,
                   v.bpname,
                   v.calendarname,
                   v.c_bpartner_id,
                   v.city,
                   v.companyname,
                   v.created,
                   v.createdby,
                   v.description,
                   v.descriptionbottom,
                   v.doctypename,
                   v.documentno,
                   v.es_documentid,
                   v.externalid,
                   v.firstname,
                   v.fiscalyear,
                   v.isactive,
                   v.iscompany,
                   v.lastname,
                   v.poreference,
                   v.postal,
                   v.updated,
                   v.updatedby,
                   v.warehousename,
                   v.ad_user_id,
                   v.c_bpartner_location_id,
                   v.c_calendar_id,
                   v.c_doctype_id,
                   v.c_invoice_id,
                   v.c_year_id,
                   v.m_warehouse_id,
                   v.bpartnervalue,
                   v.ispaid,
                   v.ispartiallypaid

            FROM C_Invoice_ADV_Search_v v
            WHERE (p_C_Invoice_IDs IS NULL OR v.C_Invoice_ID = ANY (p_C_Invoice_IDs))
            RETURNING c_invoice_adv_search.es_documentid)
    SELECT ARRAY_AGG(inserted_rows.es_documentid)
    INTO v_inserted_documentIds
    FROM inserted_rows;

    v_deleted_documentIds := (SELECT ARRAY(SELECT UNNEST(v_deleted_documentIds) EXCEPT SELECT UNNEST(v_inserted_documentIds)));

    RAISE NOTICE 'Deleted % rows: %', CARDINALITY(v_deleted_documentIds), v_deleted_documentIds;
    RAISE NOTICE 'Inserted/Updated % rows: %', CARDINALITY(v_inserted_documentIds), v_inserted_documentIds;


    RETURN QUERY
        SELECT 'D'::char(1) AS operation,
               t.t          AS es_documentid
        FROM UNNEST(v_deleted_documentIds) t
        --
        UNION ALL
        --
        SELECT 'I'::char(1) AS operation,
               t.t          AS es_documentid
        FROM UNNEST(v_inserted_documentIds) t;
END;
$BODY$
;

CREATE FUNCTION c_invoice_adv_search_update() RETURNS void
    LANGUAGE plpgsql
AS
$BODY$
DECLARE
    rowcount numeric;
BEGIN
    DELETE FROM c_invoice_adv_search WHERE TRUE;
    GET DIAGNOSTICS rowcount = ROW_COUNT;
    RAISE NOTICE 'Removed % rows', rowcount;

    INSERT INTO c_invoice_adv_search (ad_client_id, address1, ad_org_id, bpname, calendarname, c_bpartner_id, city, companyname, created, createdby, description, descriptionbottom, doctypename, documentno, es_documentid, externalid, firstname, fiscalyear, isactive, iscompany, lastname, poreference, postal, updated, updatedby, warehousename, ad_user_id, c_bpartner_location_id, c_calendar_id,
                                      c_doctype_id, c_invoice_id, c_year_id, m_warehouse_id, bpartnervalue, ispaid, ispartiallypaid)

    SELECT v.ad_client_id,
           v.address1,
           v.ad_org_id,
           v.bpname,
           v.calendarname,
           v.c_bpartner_id,
           v.city,
           v.companyname,
           v.created,
           v.createdby,
           v.description,
           v.descriptionbottom,
           v.doctypename,
           v.documentno,
           v.es_documentid,
           v.externalid,
           v.firstname,
           v.fiscalyear,
           v.isactive,
           v.iscompany,
           v.lastname,
           v.poreference,
           v.postal,
           v.updated,
           v.updatedby,
           v.warehousename,
           v.ad_user_id,
           v.c_bpartner_location_id,
           v.c_calendar_id,
           v.c_doctype_id,
           v.c_invoice_id,
           v.c_year_id,
           v.m_warehouse_id,
           v.bpartnervalue,
           v.ispaid,
           v.ispartiallypaid
    FROM c_invoice_adv_search_v v;
    GET DIAGNOSTICS rowcount = ROW_COUNT;
    RAISE NOTICE 'Inserted % rows', rowcount;
END;
$BODY$
;
