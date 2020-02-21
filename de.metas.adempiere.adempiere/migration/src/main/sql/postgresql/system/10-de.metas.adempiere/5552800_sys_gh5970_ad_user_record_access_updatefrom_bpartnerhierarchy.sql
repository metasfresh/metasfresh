drop function if exists ad_user_record_access_updatefrom_bpartnerhierarchy();
create function ad_user_record_access_updatefrom_bpartnerhierarchy() returns void
    language plpgsql
as
$$
declare
  v_count numeric;
begin
  drop table if exists TMP_RecordsToGrantAccess;
  create temporary table TMP_RecordsToGrantAccess
  (
      ad_table_id numeric,
      record_id   numeric,
      ad_user_id  numeric,
      comments    text
  );


--
-- BPartners
--
with recursive bpartner as (
    select c_bpartner_id                                                      as root_bpartner_id,
           bp.name                                                            as root_bpartner_name,
           salesrep_id                                                        as root_salesRep_ID,
           (select u.Name from AD_user u where u.ad_user_id = bp.salesrep_id) as root_salesRep_Name,
           c_bpartner_id,
           bp.name                                                            as BPName
    from c_bpartner bp
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
           child.name as BPName
    from bpartner parent
             inner join c_bpartner child on child.c_bpartner_salesrep_id = parent.c_bpartner_id
)
insert
into TMP_RecordsToGrantAccess(ad_table_id, record_id, ad_user_id, comments)
select 291,
       c_bpartner_id,
       root_salesRep_ID,
       'BP: ' || BPName || '(Root: ' || root_bpartner_name || ')' as comments
from bpartner
order by root_bpartner_id
-- select * from bpartner order by root_bpartner_id
;

  GET DIAGNOSTICS v_count = ROW_COUNT;
  raise notice 'Considered % C_BPartner(s)', v_count;

  --
  -- Orders
  --
  insert
  into TMP_RecordsToGrantAccess(ad_table_id, record_id, ad_user_id, comments)
  select 259 as AD_Table_ID,
         o.c_order_id,
         a.ad_user_id,
         ''  as comments
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
  into TMP_RecordsToGrantAccess(ad_table_id, record_id, ad_user_id, comments)
  select 318 as AD_Table_ID,
         i.c_invoice_id,
         a.ad_user_id,
         ''  as comments
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
  into TMP_RecordsToGrantAccess(ad_table_id, record_id, ad_user_id, comments)
  select 319 as AD_Table_ID,
         io.m_inout_id,
         a.ad_user_id,
         ''  as comments
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
  into TMP_RecordsToGrantAccess(ad_table_id, record_id, ad_user_id, comments)
  select 335 as AD_Table_ID,
         p.c_payment_id,
         a.ad_user_id,
         ''  as comments
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
  into TMP_RecordsToGrantAccess(ad_table_id, record_id, ad_user_id, comments)
  select 417 as AD_Table_ID,
         r.R_Request_Id,
         a.ad_user_id,
         ''  as comments
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
  into TMP_RecordsToGrantAccess(ad_table_id, record_id, ad_user_id, comments)
  select 114 as AD_Table_ID,
        u.AD_User_ID,
        a.ad_user_id,
        '' as comments
  from TMP_RecordsToGrantAccess a
          inner join AD_User u on u.c_bpartner_id = a.record_id
  ;
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
  delete from ad_user_record_access where PermissionIssuer='AUTO_BP_HIERARCHY';
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
      created, createdby, updated, updatedby, --
      ad_user_record_access_id --
  )
  select a.ad_table_id,
         a.record_id,
         permissions.Access,
         a.ad_user_id,
         'AUTO_BP_HIERARCHY',
         --
         1000000 as ad_client_id,
         0       as AD_Org_ID,
         'Y'     as IsActive,
         now(),
         99,
         now(),
         99,
         nextval('ad_user_record_access_seq')
  from TMP_RecordsToGrantAccess a,
       (select 'R' as Access
        union all
        select 'W'
       ) as permissions
  ;
  GET DIAGNOSTICS v_count = ROW_COUNT;
  raise notice 'Inserted % AD_User_Record_Access records', v_count;
end;
$$;

alter function ad_user_record_access_updatefrom_bpartnerhierarchy() owner to metasfresh;

