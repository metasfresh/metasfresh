DROP FUNCTION IF EXISTS ad_user_record_access_updatefrom_bpartnerhierarchy()
;

CREATE FUNCTION ad_user_record_access_updatefrom_bpartnerhierarchy()
    RETURNS void
    LANGUAGE plpgsql
AS
$$
DECLARE
    v_count numeric;
BEGIN
    DROP TABLE IF EXISTS TMP_RecordsToGrantAccess;
    CREATE TEMPORARY TABLE TMP_RecordsToGrantAccess
    (
        ad_table_id numeric NOT NULL,
        record_id   numeric NOT NULL,
        ad_user_id  numeric NOT NULL,
        access      varchar,
        comments    text,
        id          numeric NOT NULL,
        parent_id   numeric,
        root_id     numeric,
        level       integer
    );
    CREATE UNIQUE INDEX ON TMP_RecordsToGrantAccess (id);


    --
    -- BPartners
    --
    WITH RECURSIVE bpartner AS (
        SELECT bp.c_bpartner_id                     AS root_bpartner_id,
               bp.name                              AS root_bpartner_name,
               u.ad_user_id                         AS root_salesRep_ID,
               u.name                               AS root_salesRep_Name,
               bp.c_bpartner_id,
               bp.name                              AS BPName,
               permissions.access                   AS access,
               NEXTVAL('ad_user_record_access_seq') AS id,
               NULL::numeric                        AS parent_id,
               NULL::numeric                        AS root_id,
               0::integer                           AS level
        FROM c_bpartner bp
                 INNER JOIN ad_user u ON u.c_bpartner_id = bp.c_bpartner_id AND u.isactive = 'Y' AND u.issystemuser = 'Y'
                ,
             (SELECT 'R' AS access
              UNION ALL
              SELECT 'W' AS access
             ) AS permissions
        --
        UNION ALL
        --
        SELECT parent.root_bpartner_id,
               parent.root_bpartner_name,
               parent.root_salesRep_ID,
               parent.root_salesRep_Name,
               child.c_bpartner_id,
               child.name                           AS BPName,
               parent.access                        AS ACCESS,
               NEXTVAL('ad_user_record_access_seq') AS id,
               parent.id                            AS parent_id,
               parent.id                            AS root_id,
               parent.level + 1                     AS level
        FROM bpartner parent
                 INNER JOIN c_bpartner child
                            ON child.c_bpartner_salesrep_id = parent.c_bpartner_id
        WHERE child.c_bpartner_id <> parent.c_bpartner_id -- avoid recursion
    )
    INSERT
    INTO TMP_RecordsToGrantAccess(ad_table_id, record_id, ad_user_id, access, comments, id, parent_id, root_id, level)
    SELECT 291                                                        AS ad_table_id,
           c_bpartner_id                                              AS record_id,
           root_salesRep_ID                                           AS ad_user_id,
           bpartner.access                                            AS access,
           'BP: ' || BPName || '(Root: ' || root_bpartner_name || ')' AS comments,
           bpartner.id                                                AS id,
           bpartner.parent_id                                         AS parent_id,
           bpartner.root_id                                           AS root_id,
           bpartner.level                                             AS level
    FROM bpartner
    ORDER BY root_bpartner_id;

    -- select * from bpartner order by root_bpartner_id
    GET DIAGNOSTICS v_count = ROW_COUNT;
    RAISE NOTICE 'Considered % C_BPartner(s)', v_count;

    --
    -- Set Root_ID
    --
    UPDATE TMP_RecordsToGrantAccess SET root_id=id WHERE root_id IS NULL;
    GET DIAGNOSTICS v_count = ROW_COUNT;
    RAISE NOTICE 'Set Root_ID for % records', v_count;

    --
    -- Orders
    --
    INSERT
    INTO TMP_RecordsToGrantAccess(ad_table_id, record_id, ad_user_id, access, comments, id, parent_id, root_id)
    SELECT 259                                  AS AD_Table_ID,
           o.c_order_id                         AS record_id,
           a.ad_user_id                         AS ad_user_id,
           a.access                             AS access,
           ''                                   AS comments,
           NEXTVAL('ad_user_record_access_seq') AS id,
           a.id                                 AS parent_id,
           a.root_id                            AS root_id
    FROM TMP_RecordsToGrantAccess a
             INNER JOIN C_Order o ON o.c_bpartner_id = a.record_id
    WHERE a.ad_table_id = 291 -- C_BPartner
    ;
    GET DIAGNOSTICS v_count = ROW_COUNT;
    RAISE NOTICE 'Considered % C_Order(s)', v_count;

    --
    -- Invoices
    --
    INSERT
    INTO TMP_RecordsToGrantAccess(ad_table_id, record_id, ad_user_id, access, comments, id, parent_id, root_id)
    SELECT 318                                  AS AD_Table_ID,
           i.c_invoice_id                       AS record_id,
           a.ad_user_id                         AS ad_user_id,
           a.access                             AS access,
           ''                                   AS comments,
           NEXTVAL('ad_user_record_access_seq') AS id,
           a.id                                 AS parent_id,
           a.root_id                            AS root_id
    FROM TMP_RecordsToGrantAccess a
             INNER JOIN c_invoice i ON i.c_bpartner_id = a.record_id
    WHERE a.ad_table_id = 291 -- C_BPartner
    ;
    GET DIAGNOSTICS v_count = ROW_COUNT;
    RAISE NOTICE 'Considered % C_Invoice(s)', v_count;

    --
    -- InOuts
    --
    INSERT
    INTO TMP_RecordsToGrantAccess(ad_table_id, record_id, ad_user_id, access, comments, id, parent_id, root_id)
    SELECT 319                                  AS AD_Table_ID,
           io.m_inout_id                        AS record_id,
           a.ad_user_id                         AS ad_user_id,
           a.access                             AS access,
           ''                                   AS comments,
           NEXTVAL('ad_user_record_access_seq') AS id,
           a.id                                 AS parent_id,
           a.root_id                            AS root_id
    FROM TMP_RecordsToGrantAccess a
             INNER JOIN m_inout io ON io.c_bpartner_id = a.record_id
    WHERE a.ad_table_id = 291 -- C_BPartner
    ;
    GET DIAGNOSTICS v_count = ROW_COUNT;
    RAISE NOTICE 'Considered % M_InOut(s)', v_count;


    --
    -- Payments
    --
    INSERT
    INTO TMP_RecordsToGrantAccess(ad_table_id, record_id, ad_user_id, access, comments, id, parent_id, root_id)
    SELECT 335                                  AS AD_Table_ID,
           p.c_payment_id                       AS record_id,
           a.ad_user_id                         AS ad_user_id,
           a.access                             AS access,
           ''                                   AS comments,
           NEXTVAL('ad_user_record_access_seq') AS id,
           a.id                                 AS parent_id,
           a.root_id                            AS root_id
    FROM TMP_RecordsToGrantAccess a
             INNER JOIN c_payment p ON p.c_bpartner_id = a.record_id
    WHERE a.ad_table_id = 291 -- C_BPartner
    ;
    GET DIAGNOSTICS v_count = ROW_COUNT;
    RAISE NOTICE 'Considered % C_Payment(s)', v_count;

    --
    -- Requests (C_BPartner_ID)
    --
    INSERT
    INTO TMP_RecordsToGrantAccess(ad_table_id, record_id, ad_user_id, access, comments, id, parent_id, root_id)
    SELECT 417                                  AS AD_Table_ID,
           r.R_Request_Id                       AS record_id,
           a.ad_user_id                         AS ad_user_id,
           a.access                             AS access,
           ''                                   AS comments,
           NEXTVAL('ad_user_record_access_seq') AS id,
           a.id                                 AS parent_id,
           a.root_id                            AS root_id
    FROM TMP_RecordsToGrantAccess a
             INNER JOIN R_Request r ON r.c_bpartner_id = a.record_id
    WHERE a.ad_table_id = 291 -- C_BPartner
    ;
    GET DIAGNOSTICS v_count = ROW_COUNT;
    RAISE NOTICE 'Considered % R_Request(s) via their R_Request.C_BPartner_ID', v_count;
    --
    -- Requests (CreatedBy)
    --
    INSERT
    INTO TMP_RecordsToGrantAccess(ad_table_id, record_id, ad_user_id, access, comments, id, parent_id, root_id)
    SELECT 417                                  AS AD_Table_ID,
           r.R_Request_Id                       AS record_id,
           r.createdby                          AS ad_user_id,
           permissions.access                   AS access,
           ''                                   AS comments,
           NEXTVAL('ad_user_record_access_seq') AS id,
           NULL::numeric                        AS parent_id,
           NULL::numeric                        AS root_id
    FROM R_Request r,
         (SELECT 'R' AS access
          UNION ALL
          SELECT 'W' AS access
         ) AS permissions
    WHERE TRUE;
    GET DIAGNOSTICS v_count = ROW_COUNT;
    RAISE NOTICE 'Considered % R_Request(s) via their R_Request.CreatedBy', v_count;


    --
    -- Users
    -- grant access of the users linked to the partners
    INSERT
    INTO TMP_RecordsToGrantAccess(ad_table_id, record_id, ad_user_id, access, comments, id, parent_id, root_id)
    SELECT 114                                  AS AD_Table_ID,
           u.AD_User_ID                         AS record_id,
           a.ad_user_id                         AS ad_user_id,
           a.access                             AS access,
           ''                                   AS comments,
           NEXTVAL('ad_user_record_access_seq') AS id,
           a.id                                 AS parent_id,
           a.root_id                            AS root_id
    FROM TMP_RecordsToGrantAccess a
             INNER JOIN AD_User u ON u.c_bpartner_id = a.record_id;
    GET DIAGNOSTICS v_count = ROW_COUNT;
    RAISE NOTICE 'Considered % AD_User(s)', v_count;

    --
    -- Check TMP_RecordsToGrantAccess
    -- select * from TMP_RecordsToGrantAccess;


    --
    --
    -- Update ad_user_record_access
    --
    --
    -- remove existing access rules
    DELETE FROM ad_user_record_access WHERE PermissionIssuer = 'AUTO_BP_HIERARCHY';
    GET DIAGNOSTICS v_count = ROW_COUNT;
    RAISE NOTICE 'Removed % previous AD_User_Record_Access records', v_count;
    --
    --
    INSERT INTO ad_user_record_access ( --
        ad_table_id, --
        record_id, --
        --
        access, --
        --
        -- ad_usergroup_id, --
        ad_user_id, --
        PermissionIssuer,
        --
        -- validfrom, validto --
        ad_client_id, --
        ad_org_id, --
        isactive,
        created, createdby, updated, updatedby,
        --
        ad_user_record_access_id, parent_id, root_id)
    SELECT a.ad_table_id       AS ad_table_id,
           a.record_id         AS record_id,
           a.access            AS access,
           a.ad_user_id        AS ad_user_id,
           'AUTO_BP_HIERARCHY' AS permissionissuer,
           --
           1000000             AS ad_client_id,
           0                   AS AD_Org_ID,
           'Y'                 AS IsActive,
           NOW()               AS created,
           99                  AS createdby,
           NOW()               AS updatedby,
           99                  AS updated,
           --
           a.id                AS ad_user_record_access_id,
           a.parent_id         AS parent_id,
           a.root_id           AS root_id
    FROM TMP_RecordsToGrantAccess a;
    GET DIAGNOSTICS v_count = ROW_COUNT;
    RAISE NOTICE 'Inserted % AD_User_Record_Access records', v_count;
END;
$$
;

-- alter function ad_user_record_access_updatefrom_bpartnerhierarchy() owner to metasfresh;

-- Test
/*
select ad_user_record_access_updatefrom_bpartnerhierarchy();
 */

