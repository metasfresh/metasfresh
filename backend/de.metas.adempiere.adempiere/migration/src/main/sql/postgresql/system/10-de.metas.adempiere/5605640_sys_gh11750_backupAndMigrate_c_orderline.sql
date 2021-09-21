create table backup.c_orderline_gh11750 AS
    table c_orderline;
	
insert into c_po_orderline_alloc(c_po_orderline_alloc_id, ad_client_id, ad_org_id, created, createdby, isactive,
                                 updated, updatedby, c_po_orderline_id, c_so_orderline_id)
select nextval('c_po_orderline_alloc_seq'),
       ad_client_id,
       ad_org_id,
       created,
       createdby,
       isactive,
       updated,
       updatedby,
       link_orderline_id as c_po_orderline_id,
       c_orderline_id as c_so_orderline_id
from c_orderline
where link_orderline_id is not null;