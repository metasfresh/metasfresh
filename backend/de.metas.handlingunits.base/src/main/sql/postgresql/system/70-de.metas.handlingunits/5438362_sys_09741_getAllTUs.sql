drop function if exists "de.metas.handlingunits".get_All_TUs(p_M_HU_ID numeric);
create or replace function "de.metas.handlingunits".get_All_TUs(p_M_HU_ID numeric)
returns setof M_HU
as $$
declare
	v_HU_UnitType varchar;
begin
	--
	-- Fetch HU infos for given HU
	select piv.HU_UnitType
	into v_HU_UnitType
	from M_HU hu
	inner join M_HU_PI_Version piv on (piv.M_HU_PI_Version_ID=hu.M_HU_PI_Version_ID)
	where hu.M_HU_ID=p_M_HU_ID
	;

	--
	-- LU: return all TUs
	if (v_HU_UnitType = 'LU') then
		return query
			select tu.*
			from M_HU_Item hui
			inner join M_HU tu on (tu.M_HU_Item_Parent_ID=hui.M_HU_Item_ID)
			where hui.M_HU_ID=p_M_HU_ID;
	--
	-- TU/VHU: return only that one
	elsif (v_HU_UnitType = 'TU' or v_HU_UnitType = 'V') then
		-- NOTE: in case of VHU, we don't check if the parent is a LU (i.e. we have a VHU on LU), but we just assume it
		return query
			select tu.*
			from M_HU tu
			where tu.M_HU_ID=p_M_HU_ID;
	else
		-- Unknown HU_UnitType, shall not happen
		return;
	end if;

	return;
end;
$$
LANGUAGE plpgsql STABLE STRICT;
--
COMMENT ON FUNCTION "de.metas.handlingunits".get_All_TUs(numeric) IS 'This function returns all TUs for a given M_HU_ID, i.e.
* if the M_HU_ID is an LU it will return all included TUs
* else that HU will be returned (we are considering it an TU)
';



--
-- TEST
-- select * from "de.metas.handlingunits".get_All_TUs(10886709); -- LU
-- select * from "de.metas.handlingunits".get_All_TUs(10886722); -- TU
-- select * from "de.metas.handlingunits".get_All_TUs(10884664); -- LU with VHUs
-- select * from "de.metas.handlingunits".get_All_TUs(10884663); -- VHU on LU
-- select * from "de.metas.handlingunits".get_All_TUs(10812060); -- TU, without LU
