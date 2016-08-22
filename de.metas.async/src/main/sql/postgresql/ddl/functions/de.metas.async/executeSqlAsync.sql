create or replace function "de.metas.async".executeSqlAsync(p_Sql text)
returns void
as
$BODY$
declare
	p_AD_Client_ID numeric := 1000000;
	p_AD_Org_ID numeric := 0;
	p_CreatedBy numeric := 99; -- migration user
	p_AD_User_ID numeric := 100; -- SuperUser
	p_AD_Role_ID numeric := 1000000; -- Admin
	p_TS timestamp with time zone := now();
	--
	v_ProcessorClassname text := 'de.metas.migration.async.ExecuteSQLWorkpackageProcessor';
	v_C_Queue_PackageProcessor_ID numeric;
	--
	v_C_Queue_Block_ID numeric;
	v_C_Queue_Workpackage_ID numeric;
	
begin
	--
	-- Get C_Queue_PackageProcessor_ID
	select c_queue_packageprocessor_id
		into v_C_Queue_PackageProcessor_ID
		from c_queue_packageprocessor p
		where p.Classname=v_ProcessorClassname;
	if (coalesce(v_C_Queue_PackageProcessor_ID, 0) <= 0) then
		raise exception 'No C_Queue_PackageProcessor_ID found for %', v_ProcessorClassname;
	end if;
	
	--
	-- Create Queue Block
	v_C_Queue_Block_ID := nextval('c_queue_block_seq');
	INSERT INTO C_Queue_Block
	(
		C_Queue_Block_ID
		, c_queue_packageprocessor_id
		--
		, ad_client_id, ad_org_id
		, created, createdby, isactive, updated, updatedby
	)
	values (
		v_C_Queue_Block_ID
		, v_C_Queue_PackageProcessor_ID
		--
		, p_AD_Client_ID, p_AD_Org_ID
		, p_TS, p_CreatedBy, 'Y', p_TS, p_CreatedBy
	);

	--
	-- Create workpackage
	v_C_Queue_Workpackage_ID := nextval('c_queue_workpackage_seq');
	INSERT INTO C_Queue_Workpackage
	(
		c_queue_block_id
		, c_queue_workpackage_id
		, IsReadyForProcessing
		, ad_role_id, ad_user_id
		--
		, ad_client_id, ad_org_id, created, createdby, isactive, updated, updatedby
	)
	values (
		v_C_Queue_Block_ID
		, v_C_Queue_Workpackage_ID
		, 'N' -- IsReadyForProcessing
		, p_AD_Role_ID, p_AD_User_ID
		--
		, p_AD_Client_ID, p_AD_Org_ID, p_TS, p_CreatedBy, 'Y', p_TS, p_CreatedBy
	);
	
	--
	-- Workpackage parameter: Code
	INSERT INTO c_queue_workpackage_param(
			c_queue_workpackage_id
            , c_queue_workpackage_param_id
			, parametername
			, ad_reference_id
			, p_string
			--
			, ad_client_id, ad_org_id, created, createdby, isactive, updated, updatedby
	)
    VALUES (
		v_C_Queue_Workpackage_ID
		, nextval('c_queue_workpackage_param_seq')
		, 'Code'
		, 10 -- String
		, p_Sql
		--
		, p_AD_Client_ID, p_AD_Org_ID, p_TS, p_CreatedBy, 'Y', p_TS, p_CreatedBy
	);

	--
	-- Mark the workpackage as ready for processing
	update C_Queue_Workpackage set IsReadyForProcessing='Y' where C_Queue_Workpackage_ID=v_C_Queue_Workpackage_ID;

	raise notice 'Enqueued workpackage %', v_C_Queue_Workpackage_ID;
end;
$BODY$
LANGUAGE plpgsql VOLATILE COST 100;


COMMENT ON FUNCTION "de.metas.async".executeSqlAsync(p_Sql text)
IS 'Enqueues the given SQL to metasfresh async executor which will execute the query as many times until the SQL update count gets ZERO.';

