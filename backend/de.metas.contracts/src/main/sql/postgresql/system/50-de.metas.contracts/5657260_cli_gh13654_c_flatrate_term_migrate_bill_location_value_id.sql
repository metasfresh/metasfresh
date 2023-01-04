-- sets empty c_flatrate_term.bill_location_value_id of existing terms. this is needed for c_invoice_candidates's header aggregation key

create table backup.c_flatrate_term_20220919_bill_location_value_id as
select ft.c_flatrate_term_id, ft.bill_location_id, ft.bill_location_value_id, bpl.c_location_id
from c_flatrate_term ft
         join c_bpartner_location bpl on bpl.c_bpartner_location_id=ft.bill_location_id
where true
  and ft.bill_location_value_id is null;


create table backup.c_flatrate_term_20220919_pre_bill_location_value_id_bkp as select * from c_flatrate_term;

update c_flatrate_term ft set bill_location_value_id=data.c_location_id, updatedby=99, updated='2022-09-19 10:39'
from backup.c_flatrate_term_20220919_bill_location_value_id data
where data.c_flatrate_term_id=ft.c_flatrate_term_id;
