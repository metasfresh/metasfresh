drop function if exists ad_user_record_access_updatefrom_bpartnerhierarchy();

create function ad_user_record_access_updatefrom_bpartnerhierarchy()
    returns void
    language plpgsql
as
$$
declare
    v_count numeric;
begin
    drop table if exists TMP_RecordsToGrantAccess;
    create temporary table TMP_RecordsToGrantAccess
    (
        ad_table_id numeric not null,
        record_id   numeric not null,
        ad_user_id  numeric not null,
        access      varchar,
        comments    text,
        id          numeric not null,
        parent_id   numeric,
        root_id     numeric
    );
    create unique index on TMP_RecordsToGrantAccess (id);


    --
    -- BPartners
    --
    with recursive bpartner as (
        select c_bpartner_id                                                      as root_bpartner_id,
               bp.name                                                            as root_bpartner_name,
               salesrep_id                                                        as root_salesRep_ID,
               (select u.Name from AD_user u where u.ad_user_id = bp.salesrep_id) as root_salesRep_Name,
               c_bpartner_id,
               bp.name                                                            as BPName,
               permissions.access                                                 as access,
               nextval('ad_user_record_access_seq')                               as id,
               null::numeric                                                      as parent_id,
               null::numeric                                                      as root_id
        from c_bpartner bp,
             (select 'R' as access
              union all
              select 'W' as access
             ) as permissions
        where SalesRep_ID is not null
          and c_bpartner_salesrep_id is null
          --
        union all
        --
        select parent.root_bpartner_id,
               parent.root_bpartner_name,
               parent.root_salesRep_ID,
               parent.root_salesRep_Name,
               child.c_bpartner_id,
               child.name                           as BPName,
               parent.access                        as access,
               nextval('ad_user_record_access_seq') as id,
               parent.id                            as parent_id,
               parent.id                            as root_id
        from bpartner parent
                 inner join c_bpartner child on child.c_bpartner_salesrep_id = parent.c_bpartner_id
    )
    insert
    into TMP_RecordsToGrantAccess(ad_table_id, record_id, ad_user_id, access, comments, id, parent_id, root_id)
    select 291                                                        as ad_table_id,
           c_bpartner_id                                              as record_id,
           root_salesRep_ID                                           as ad_user_id,
           bpartner.access                                            as access,
           'BP: ' || BPName || '(Root: ' || root_bpartner_name || ')' as comments,
           bpartner.id                                                as id,
           bpartner.parent_id                                         as parent_id,
           bpartner.root_id                                           as root_id
    from bpartner
    order by root_bpartner_id;
    -- select * from bpartner order by root_bpartner_id
    GET DIAGNOSTICS v_count = ROW_COUNT;
    raise notice 'Considered % C_BPartner(s)', v_count;

    --
    -- Set Root_ID
    --
    update TMP_RecordsToGrantAccess set root_id=id where root_id is null;
    GET DIAGNOSTICS v_count = ROW_COUNT;
    raise notice 'Set Root_ID for % records', v_count;

    --
    -- Orders
    --
    insert
    into TMP_RecordsToGrantAccess(ad_table_id, record_id, ad_user_id, access, comments, id, parent_id, root_id)
    select 259                                  as AD_Table_ID,
           o.c_order_id                         as record_id,
           a.ad_user_id                         as ad_user_id,
           a.access                             as access,
           ''                                   as comments,
           nextval('ad_user_record_access_seq') as id,
           a.id                                 as parent_id,
           a.root_id                            as root_id
    from TMP_RecordsToGrantAccess a
             inner join C_Order o on o.c_bpartner_id = a.record_id
    where a.ad_table_id = 291 -- C_BPartner
    ;
    GET DIAGNOSTICS v_count = ROW_COUNT;
    raise notice 'Considered % C_Order(s)', v_count;

    --
    -- Invoices
    --
    insert
    into TMP_RecordsToGrantAccess(ad_table_id, record_id, ad_user_id, access, comments, id, parent_id, root_id)
    select 318                                  as AD_Table_ID,
           i.c_invoice_id                       as record_id,
           a.ad_user_id                         as ad_user_id,
           a.access                             as access,
           ''                                   as comments,
           nextval('ad_user_record_access_seq') as id,
           a.id                                 as parent_id,
           a.root_id                            as root_id
    from TMP_RecordsToGrantAccess a
             inner join c_invoice i on i.c_bpartner_id = a.record_id
    where a.ad_table_id = 291 -- C_BPartner
    ;
    GET DIAGNOSTICS v_count = ROW_COUNT;
    raise notice 'Considered % C_Invoice(s)', v_count;

    --
    -- InOuts
    --
    insert
    into TMP_RecordsToGrantAccess(ad_table_id, record_id, ad_user_id, access, comments, id, parent_id, root_id)
    select 319                                  as AD_Table_ID,
           io.m_inout_id                        as record_id,
           a.ad_user_id                         as ad_user_id,
           a.access                             as access,
           ''                                   as comments,
           nextval('ad_user_record_access_seq') as id,
           a.id                                 as parent_id,
           a.root_id                            as root_id
    from TMP_RecordsToGrantAccess a
             inner join m_inout io on io.c_bpartner_id = a.record_id
    where a.ad_table_id = 291 -- C_BPartner
    ;
    GET DIAGNOSTICS v_count = ROW_COUNT;
    raise notice 'Considered % M_InOut(s)', v_count;


    --
    -- Payments
    --
    insert
    into TMP_RecordsToGrantAccess(ad_table_id, record_id, ad_user_id, access, comments, id, parent_id, root_id)
    select 335                                  as AD_Table_ID,
           p.c_payment_id                       as record_id,
           a.ad_user_id                         as ad_user_id,
           a.access                             as access,
           ''                                   as comments,
           nextval('ad_user_record_access_seq') as id,
           a.id                                 as parent_id,
           a.root_id                            as root_id
    from TMP_RecordsToGrantAccess a
             inner join c_payment p on p.c_bpartner_id = a.record_id
    where a.ad_table_id = 291 -- C_BPartner
    ;
    GET DIAGNOSTICS v_count = ROW_COUNT;
    raise notice 'Considered % C_Payment(s)', v_count;

    --
    -- Requests
    --
    insert
    into TMP_RecordsToGrantAccess(ad_table_id, record_id, ad_user_id, access, comments, id, parent_id, root_id)
    select 417                                  as AD_Table_ID,
           r.R_Request_Id                       as record_id,
           a.ad_user_id                         as ad_user_id,
           a.access                             as access,
           ''                                   as comments,
           nextval('ad_user_record_access_seq') as id,
           a.id                                 as parent_id,
           a.root_id                            as root_id
    from TMP_RecordsToGrantAccess a
             inner join R_Request r on r.c_bpartner_id = a.record_id
    where a.ad_table_id = 291 -- C_BPartner
    ;
    GET DIAGNOSTICS v_count = ROW_COUNT;
    raise notice 'Considered % R_Request(s)', v_count;


    --
    -- Users
    -- grant access of the users linked to the partners
    insert
    into TMP_RecordsToGrantAccess(ad_table_id, record_id, ad_user_id, access, comments, id, parent_id, root_id)
    select 114                                  as AD_Table_ID,
           u.AD_User_ID                         as record_id,
           a.ad_user_id                         as ad_user_id,
           a.access                             as access,
           ''                                   as comments,
           nextval('ad_user_record_access_seq') as id,
           a.id                                 as parent_id,
           a.root_id                            as root_id
    from TMP_RecordsToGrantAccess a
             inner join AD_User u on u.c_bpartner_id = a.record_id;
    GET DIAGNOSTICS v_count = ROW_COUNT;
    raise notice 'Considered % AD_User(s)', v_count;

    --
    -- Check TMP_RecordsToGrantAccess
    -- select * from TMP_RecordsToGrantAccess;


    --
    --
    -- Update ad_user_record_access
    --
    --
    -- remove existing access rules
    delete from ad_user_record_access where PermissionIssuer = 'AUTO_BP_HIERARCHY';
    GET DIAGNOSTICS v_count = ROW_COUNT;
    raise notice 'Removed % previous AD_User_Record_Access records', v_count;
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
    select a.ad_table_id       as ad_table_id,
           a.record_id         as record_id,
           a.access            as access,
           a.ad_user_id        as ad_user_id,
           'AUTO_BP_HIERARCHY' as permissionissuer,
           --
           1000000             as ad_client_id,
           0                   as AD_Org_ID,
           'Y'                 as IsActive,
           now()               as created,
           99                  as createdby,
           now()               as updatedby,
           99                  as updated,
           --
           a.id                as ad_user_record_access_id,
           a.parent_id         as parent_id,
           a.root_id           as root_id
    from TMP_RecordsToGrantAccess a;
    GET DIAGNOSTICS v_count = ROW_COUNT;
    raise notice 'Inserted % AD_User_Record_Access records', v_count;
end;
$$;

-- alter function ad_user_record_access_updatefrom_bpartnerhierarchy() owner to metasfresh;

-- Test
/*
select ad_user_record_access_updatefrom_bpartnerhierarchy();
 */

