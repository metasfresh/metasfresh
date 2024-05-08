DROP FUNCTION IF EXISTS C_BPartner_Adv_Search_Update()
;


DROP FUNCTION IF EXISTS C_BPartner_Adv_Search_Update(
    p_C_BPartner_IDs numeric[]
)
;


CREATE OR REPLACE FUNCTION C_BPartner_Adv_Search_Update(
    p_C_BPartner_IDs numeric[]
)
    RETURNS table
            (
                operation     char(1),
                es_documentid varchar(255)
            )
AS
$BODY$
DECLARE
    v_transactionId        text;
    rowcount               integer;
    v_deleted_documentIds  varchar[];
    v_inserted_documentIds varchar[];
BEGIN
    RAISE NOTICE 'p_C_BPartner_IDs: %', p_C_BPartner_IDs;
    IF (p_C_BPartner_IDs IS NULL OR CARDINALITY(p_C_BPartner_IDs) <= 0) THEN
        RAISE EXCEPTION 'At least one C_BPartner_ID shall be specified';
    END IF;

    v_transactionId = gen_random_uuid()::text;
    RAISE NOTICE 'Transaction ID: %', v_transactionId;

    --
    -- Delete old rows
    WITH deleted_rows AS (
        DELETE FROM C_BPartner_Adv_Search t
            WHERE
                (p_C_BPartner_IDs IS NULL OR t.C_BPartner_ID = ANY (p_C_BPartner_IDs))
            RETURNING t.es_documentid
    )
    SELECT ARRAY_AGG(deleted_rows.es_documentid)
    INTO v_deleted_documentIds
    FROM deleted_rows;

    --
    -- Insert new rows
    WITH inserted_rows AS (
        INSERT INTO c_bpartner_adv_search (ad_client_id, ad_org_id, address1, c_bp_contact_id, c_bpartner_id, c_bpartner_location_id, city, created, createdby, es_documentid, firstname, isactive, iscompany, lastname, name, postal, updated, updatedby, value)
            SELECT v.ad_client_id,
                   v.ad_org_id,
                   v.address1,
                   v.c_bp_contact_id,
                   v.c_bpartner_id,
                   v.c_bpartner_location_id,
                   v.city,
                   v.created,
                   v.createdby,
                   v.es_documentid,
                   v.firstname,
                   v.isactive,
                   v.iscompany,
                   v.lastname,
                   v.name,
                   v.postal,
                   v.updated,
                   v.updatedby,
                   v.value
            FROM c_bpartner_adv_search_v v
            WHERE (p_C_BPartner_IDs IS NULL OR v.C_BPartner_ID = ANY (p_C_BPartner_IDs))
            RETURNING c_bpartner_adv_search.es_documentid
    )
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
    LANGUAGE plpgsql
    VOLATILE
    COST 100
;


CREATE OR REPLACE FUNCTION C_BPartner_Adv_Search_Update()
    RETURNS void
AS
$BODY$
DECLARE
    rowcount numeric;
BEGIN
    DELETE FROM c_bpartner_adv_search WHERE 1 = 1;
    GET DIAGNOSTICS rowcount = ROW_COUNT;
    RAISE NOTICE 'Removed % rows', rowcount;

    INSERT INTO c_bpartner_adv_search (ad_client_id, ad_org_id, address1, c_bp_contact_id, c_bpartner_id, c_bpartner_location_id, city, created, createdby, es_documentid, firstname, isactive, iscompany, lastname, name, postal, updated, updatedby, value)
    SELECT v.ad_client_id,
           v.ad_org_id,
           v.address1,
           v.c_bp_contact_id,
           v.c_bpartner_id,
           v.c_bpartner_location_id,
           v.city,
           v.created,
           v.createdby,
           v.es_documentid,
           v.firstname,
           v.isactive,
           v.iscompany,
           v.lastname,
           v.name,
           v.postal,
           v.updated,
           v.updatedby,
           v.value
    FROM c_bpartner_adv_search_v v;
    GET DIAGNOSTICS rowcount = ROW_COUNT;
    RAISE NOTICE 'Inserted % rows', rowcount;
END;
$BODY$
    LANGUAGE plpgsql
    VOLATILE
    COST 100
;

