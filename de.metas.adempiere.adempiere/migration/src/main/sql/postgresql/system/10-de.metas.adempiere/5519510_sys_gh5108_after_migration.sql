drop function if exists after_migration();

create or replace function after_migration()
returns void
as
$BODY$ 
begin
	perform dba_seq_check_native();
	perform update_sequences();
	
	perform role_access_update();

	perform AD_Element_Link_Create_Missing();
end;
$BODY$
LANGUAGE plpgsql
VOLATILE
;

COMMENT ON FUNCTION public.after_migration()
  IS 'Function called by the sql migration tool after all scripts were executed.';

