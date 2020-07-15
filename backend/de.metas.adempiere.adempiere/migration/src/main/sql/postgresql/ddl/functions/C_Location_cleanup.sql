DROP FUNCTION IF EXISTS C_Location_cleanup();

CREATE OR REPLACE FUNCTION C_Location_cleanup()
RETURNS void
AS $$
declare
	rows_count numeric := 0;
begin
	drop table if exists TMP_User_Locations;

	create temporary table TMP_User_Locations as
	select C_Location_ID from getC_Location_References();

	create index on TMP_User_Locations(C_Location_ID);

	update C_Location l set IsActive='N'
	where
		not exists (select 1 from TMP_User_Locations t where t.C_Location_ID=l.C_Location_ID)
		and IsActive='Y';
	GET DIAGNOSTICS rows_count = ROW_COUNT;
	raise notice 'Inactivated % C_Location records', rows_count;

	delete from C_Location where IsActive='N';
	GET DIAGNOSTICS rows_count = ROW_COUNT;
	raise notice 'Deleted % inactive C_Location records', rows_count;

	select count(1) into rows_count from C_Location;
	raise notice 'Remaining % C_Location records', rows_count;

	drop table if exists TMP_User_Locations;
end; $$
LANGUAGE plpgsql VOLATILE
;

-- select * from C_Location_cleanup();
