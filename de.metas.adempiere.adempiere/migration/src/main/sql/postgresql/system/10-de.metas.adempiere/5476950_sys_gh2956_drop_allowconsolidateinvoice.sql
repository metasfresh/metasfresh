--set default aggregation for partner with activated consolidation
update c_bpartner
set so_invoice_aggregation_id= 1000000
where so_invoice_aggregation_id is null and allowconsolidateinvoice ='Y';

--dropping column
alter table c_bpartner drop column allowconsolidateinvoice;