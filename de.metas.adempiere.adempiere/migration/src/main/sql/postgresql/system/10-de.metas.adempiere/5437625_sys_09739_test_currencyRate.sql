--
-- Makes sure the currencyRate function works after we introduced getDefaultConversionType_ID
do $$
declare
	r record;
	v_HasInvalidConversions boolean := false;
begin
	for r in (
		select *
		from (
			select 
			cr.AD_Client_ID, cr.AD_Org_ID
			, cr.C_Currency_ID
			, cr.C_Currency_ID_To
			, cr.ValidFrom, cr.ValidTo
			, cr.C_ConversionType_ID
			, cr.DivideRate
			, cr.MultiplyRate
			, currencyrate(
				cr.C_Currency_ID
				, cr.C_Currency_ID_To
				, cr.ValidFrom
				, (case when getDefaultConversionType_ID(cr.AD_Client_ID, cr.AD_Org_ID, cr.ValidFrom) = cr.C_ConversionType_ID then 0 else cr.C_ConversionType_ID end)
				, cr.AD_Client_ID -- p_client_id numeric
				, cr.AD_Org_ID -- p_org_id numeric
			) as MultiplyRate_Calc
			, cr.C_Conversion_Rate_ID
			from C_Conversion_Rate cr
		) t
		where true
		and MultiplyRate<>MultiplyRate_Calc
	)
	loop
		raise notice 'Invalid conversion: %', r;
		v_HasInvalidConversions := true;
	end loop;

	if (v_HasInvalidConversions) then
		raise exception 'Found invalid conversions. Check log.';
	else
		raise notice 'currencyRate function works correctly.';
	end if;
end $$;
