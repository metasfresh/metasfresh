drop view if exists "de.metas.handlingunits".M_HU_PI_Attribute_Actual_v;
create or replace view "de.metas.handlingunits".M_HU_PI_Attribute_Actual_v
as
select
/**
 * View used to get the actual M_HU_PI_Attribute(s) to be used for a particual M_HU_PI.
 */
	t.*
	, a.Value M_Attribute_ValueName
	, a.Name M_Attribute_Name
from
(
	--
	-- Direct attributes (defined directly on particular M_HU_PI_Version_ID)
	select 
		pi.M_HU_PI_ID
		, pi.Name as PI_Name
		, piv.M_HU_PI_Version_ID
		, pia.M_HU_PI_Attribute_ID
		, pia.M_Attribute_ID
		, 'Direct' as InheritanceType
	from M_HU_PI pi
	inner join M_HU_PI_Version piv on (piv.M_HU_PI_ID=pi.M_HU_PI_ID and piv.IsActive='Y')
	inner join M_HU_PI_Attribute pia on (
		pia.M_HU_PI_Version_ID=piv.M_HU_PI_Version_ID
		and pia.IsActive='Y'
	)
	--
	union all
	--
	-- Indirect attributes (inherited from NoPI)
	select 
		pi.M_HU_PI_ID
		, pi.Name as PI_Name
		, piv.M_HU_PI_Version_ID
		, pia.M_HU_PI_Attribute_ID
		, pia.M_Attribute_ID
		, 'Template' as InheritanceType
	from M_HU_PI pi
	inner join M_HU_PI_Version piv on (piv.M_HU_PI_ID=pi.M_HU_PI_ID and piv.IsActive='Y')
	inner join M_HU_PI_Attribute pia on (
		pia.M_HU_PI_Version_ID=100 -- NoPI
		and pia.IsActive='Y'
	)
	where
		-- this is not the NoPI
		pi.M_HU_PI_ID<>100
		-- no direct attribute exists:
		and not exists (select 1 from M_HU_PI_Attribute pia0 where pia0.M_HU_PI_Version_ID=piv.M_HU_PI_Version_ID and pia0.M_Attribute_ID=pia.M_Attribute_ID and pia0.IsActive='Y')
	--
) t
left outer join M_Attribute a on (a.M_Attribute_ID=t.M_Attribute_ID)
-- order by t.M_HU_PI_ID, t.M_HU_PI_Version_ID, a.Value
;




