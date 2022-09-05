-- takes very long on large DB
-- to verify how long it really takes, i ignored it in the DB...
-- select migrationscript_ignore('10-de.metas.adempiere/5586680_cli_migration_location_fields.sql');
-- ...and am now applying it "manually"
-- start: 08:50
-- end: tried to cancel the final commit at 09:18; it stalled other migr scripts when run
-- however when i reran later, it looks like all *_value_id columns were migrated now.
-- => **so, it took around 30 minutes**

update c_olcand t set c_bpartner_location_value_id=f.c_location_id
from c_bpartner_location f
where f.c_bpartner_location_id=t.c_bpartner_location_id and t.c_bpartner_location_value_id is null;

update c_olcand t set c_bp_location_override_value_id=f.c_location_id
from c_bpartner_location f
where f.c_bpartner_location_id=t.c_bp_location_override_id and t.c_bp_location_override_value_id is null;

update c_olcand t set bill_location_value_id=f.c_location_id
from c_bpartner_location f
where f.c_bpartner_location_id=t.bill_location_id and t.bill_location_value_id is null;

update c_olcand t set dropship_location_value_id=f.c_location_id
from c_bpartner_location f
where f.c_bpartner_location_id=t.dropship_location_id and t.dropship_location_value_id is null;

update c_olcand t set dropship_location_override_value_id=f.c_location_id
from c_bpartner_location f
where f.c_bpartner_location_id=t.dropship_location_override_id and t.dropship_location_override_value_id is null;

update c_olcand t set handover_location_value_id=f.c_location_id
from c_bpartner_location f
where f.c_bpartner_location_id=t.handover_location_id and t.handover_location_value_id is null;

update c_olcand t set handover_location_override_value_id=f.c_location_id
from c_bpartner_location f
where f.c_bpartner_location_id=t.handover_location_override_id and t.handover_location_override_value_id is null;












update c_order t set c_bpartner_location_value_id=f.c_location_id
from c_bpartner_location f
where f.c_bpartner_location_id=t.c_bpartner_location_id and t.c_bpartner_location_value_id is null;

update c_order t set bill_location_value_id=f.c_location_id
from c_bpartner_location f
where f.c_bpartner_location_id=t.bill_location_id and t.bill_location_value_id is null;

update c_order t set dropship_location_value_id=f.c_location_id
from c_bpartner_location f
where f.c_bpartner_location_id=t.dropship_location_id and t.dropship_location_value_id is null;

update c_order t set handover_location_value_id=f.c_location_id
from c_bpartner_location f
where f.c_bpartner_location_id=t.handover_location_id and t.handover_location_value_id is null;















update m_shipmentschedule t set c_bpartner_location_value_id=f.c_location_id
from c_bpartner_location f
where f.c_bpartner_location_id=t.c_bpartner_location_id and t.c_bpartner_location_value_id is null;

update m_shipmentschedule t set c_bp_location_override_value_id=f.c_location_id
from c_bpartner_location f
where f.c_bpartner_location_id=t.c_bp_location_override_id and t.c_bp_location_override_value_id is null;

update m_shipmentschedule t set bill_location_value_id=f.c_location_id
from c_bpartner_location f
where f.c_bpartner_location_id=t.bill_location_id and t.bill_location_value_id is null;



















update c_invoice_candidate t set bill_location_value_id=f.c_location_id
from c_bpartner_location f
where f.c_bpartner_location_id=t.bill_location_id and t.bill_location_value_id is null;

update c_invoice_candidate t set bill_location_override_value_id=f.c_location_id
from c_bpartner_location f
where f.c_bpartner_location_id=t.bill_location_override_id and t.bill_location_override_value_id is null;













update m_inout t set c_bpartner_location_value_id=f.c_location_id
from c_bpartner_location f
where f.c_bpartner_location_id=t.c_bpartner_location_id and t.c_bpartner_location_value_id is null;

update m_inout t set dropship_location_value_id=f.c_location_id
from c_bpartner_location f
where f.c_bpartner_location_id=t.dropship_location_id and t.dropship_location_value_id is null;























update c_invoice t set c_bpartner_location_value_id=f.c_location_id
from c_bpartner_location f
where f.c_bpartner_location_id=t.c_bpartner_location_id and t.c_bpartner_location_value_id is null;

