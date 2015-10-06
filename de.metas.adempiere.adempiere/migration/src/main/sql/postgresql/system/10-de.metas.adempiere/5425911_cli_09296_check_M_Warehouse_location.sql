--
-- Set M_Warehouse.C_Location_ID if it was not already set
update M_Warehouse wh set C_Location_ID=(select bpl.C_Location_ID from C_BPartner_Location bpl where bpl.C_BPartner_Location_ID=wh.C_BPartner_Location_ID)
where wh.C_BPartner_Location_ID is not null
and wh.C_Location_ID is null;






--
-- This script makes sure the M_Warehouse.C_Location_ID is correctly set.
-- NOTE: data IS NOT UPDATED, just errors are fired.
do $$
declare
	r record;
begin
	for r in select
			wh.M_Warehouse_ID
			, wh.Name as WHName
			, wh.C_Location_ID as WH_Location_ID
			, bpl.C_BPartner_Location_ID
			, bpl.C_Location_ID as BPL_Location_ID
		from M_Warehouse wh
		left outer join C_BPartner_Location bpl on (bpl.C_BPartner_Location_ID=wh.C_BPartner_Location_ID)
	loop
		if (r.C_BPartner_Location_ID is null) then
			raise exception 'C_BPartner_Location_ID not set for warehouse %', r.WHName;
		end if;

		if (r.WH_Location_ID is null) then
			raise exception 'M_Warehouse.C_Location_ID not set for warehouse %', r.WHName;
		end if;

		if (r.WH_Location_ID <> r.BPL_Location_ID) then
			raise exception 'M_Warehouse.C_Location_ID not matching C_BPartner_Location.C_Location_ID for warehouse %', r.WHName;
		end if;
	end loop;
end; $$
