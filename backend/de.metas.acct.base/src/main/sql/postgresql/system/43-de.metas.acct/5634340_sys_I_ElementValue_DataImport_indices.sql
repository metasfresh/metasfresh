drop index if exists i_elementvalue_c_dataimport_id;
create index i_elementvalue_c_dataimport_id on i_elementvalue (c_dataimport_id);

drop index if exists i_elementvalue_c_dataimport_run_id;
create index i_elementvalue_c_dataimport_run_id on i_elementvalue (c_dataimport_run_id);

