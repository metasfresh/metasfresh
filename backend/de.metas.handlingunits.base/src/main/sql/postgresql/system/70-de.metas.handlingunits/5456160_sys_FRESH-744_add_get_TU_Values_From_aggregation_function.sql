drop function if exists "de.metas.handlingunits".get_TU_Values_From_Aggregation(p_M_HU_ID numeric);
create or replace function "de.metas.handlingunits".get_TU_Values_From_Aggregation(p_M_HU_ID numeric)
returns table
	(
		huvalue character varying,
		qty numeric
	)
as $$
declare
	v_TU_Qty numeric;
	c_TU_Qty numeric;
	v_TU_Value character varying;
	v_TU_New_Value character varying;
	all_Values character varying[] := '{}';
begin
	--
	-- Fetch HU infos for given HU
	select hui.qty, hui.qty, hu.value
	into v_TU_Qty, c_TU_Qty, v_TU_Value
	from M_HU hu
	inner join m_hu_item hui on hu.m_hu_item_parent_id=hui.M_HU_Item_ID and hui.itemtype ='HA'
	where hu.M_HU_ID=p_M_HU_ID
	;
	-- if there are more than 1 aggregated TUs, crete values for all
	if(1 < v_TU_Qty) then
		while(0 < v_TU_Qty)
			loop
			v_TU_New_Value := v_TU_Value || '-'::character varying || v_TU_Qty::character varying;
			all_Values := all_Values || v_TU_New_Value;
			v_TU_Qty := v_TU_Qty - 1;
		end loop;
	return query SELECT UNNEST(all_Values), c_TU_Qty;
	-- if there is just 1 TU return it
	elsif (1 = v_TU_Qty OR v_TU_Qty is null) then
	return query 
		select value, c_TU_Qty
		from m_hu 
		where m_hu_id = p_M_HU_ID;
	
	-- number of TUs <= 0 , shall not happen
	else return;
	end if;

	return;
end;
	
$$
LANGUAGE plpgsql STABLE STRICT;
--
COMMENT ON FUNCTION "de.metas.handlingunits".get_TU_Values_From_Aggregation(numeric) IS 'This function returns all TU values for a given M_HU_ID and the qty of the TUs, i.e.
* if the M_HU_ID is an aggregated TU it will return all included TUs (e.g. adding -1 to the value)
* else that HU will be returned (if it is only one)
';