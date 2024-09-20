DROP FUNCTION IF EXISTS C_Invoice_Adv_Search_Update(
    p_C_Invoice_IDs numeric[]
)
;


DROP FUNCTION IF EXISTS C_Invoice_Adv_Search_Update()
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
    v_transactionId        text;
    v_deleted_documentIds  varchar[];
    v_inserted_documentIds varchar[];
BEGIN
    RAISE NOTICE 'p_C_Invoice_IDs: %', p_C_Invoice_IDs;
    IF (p_C_Invoice_IDs IS NULL OR CARDINALITY(p_C_Invoice_IDs) <= 0) THEN
        RAISE EXCEPTION 'At least one C_Invoice_ID shall be specified';
    END IF;

    v_transactionId = gen_random_uuid()::text;
    RAISE NOTICE 'Transaction ID: %', v_transactionId;

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
                                      c_doctype_id, c_invoice_id, c_year_id, m_warehouse_id, bpartnervalue)
    SELECT v.ad_client_id,
           v.address1,
           v. ad_org_id,
           v.bpname,
           v.calendarname,
           v. c_bpartner_id,
           v. city,
           v. companyname,
           v. created,
           v. createdby,
           v. description,
           v. descriptionbottom,
           v. doctypename,
           v. documentno,
           v. es_documentid,
           v. externalid,
           v. firstname,
           v. fiscalyear,
           v. isactive,
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
           v.bpartnervalue

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
                                      c_doctype_id, c_invoice_id, c_year_id, m_warehouse_id, bpartnervalue)

    SELECT v.ad_client_id,
           v.address1,
           v. ad_org_id,
           v.bpname,
           v.calendarname,
           v. c_bpartner_id,
           v. city,
           v. companyname,
           v. created,
           v. createdby,
           v. description,
           v. descriptionbottom,
           v. doctypename,
           v. documentno,
           v. es_documentid,
           v. externalid,
           v. firstname,
           v. fiscalyear,
           v. isactive,
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
           v.bpartnervalue
    FROM c_invoice_adv_search_v v;
    GET DIAGNOSTICS rowcount = ROW_COUNT;
    RAISE NOTICE 'Inserted % rows', rowcount;
END;
$BODY$
;


