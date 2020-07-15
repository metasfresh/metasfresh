--
-- Materialize M_HU_PI_Attribute_Actual_v to speed things up a bit
-- Duration: ~53ms
drop table if exists TMP_M_HU_PI_Attribute_Actual_v;
create temporary table TMP_M_HU_PI_Attribute_Actual_v as
select * from "de.metas.handlingunits".M_HU_PI_Attribute_Actual_v
where true
	-- Only for given M_Attribute
	and M_Attribute_ValueName='IsQualityInspection'
;
create index on TMP_M_HU_PI_Attribute_Actual_v(M_HU_PI_Version_ID);
analyze verbose TMP_M_HU_PI_Attribute_Actual_v;

--
-- Prepare M_HU_Attribute_ToInsert
-- Duration: ~6sec
drop table if exists TMP_M_HU_Attribute_ToInsert;
create temporary table TMP_M_HU_Attribute_ToInsert as
SELECT
	null::numeric as m_hu_attribute_id -- will be set later
	, pia.m_attribute_id
	, hu.m_hu_id
	--
	, null::varchar as value
	, null::varchar as valueInitial
	--
	, null::numeric as valueNumber
	, null::numeric as valueNumberInitial
	--
	, pia.m_hu_pi_attribute_id
	, hu.ad_client_id
	, hu.ad_org_id
	, now() as created
	, 0::numeric as createdby
	, now() as updated
	, 0::numeric as updatedby
	, 'Y'::char(1) as isactive
FROM M_HU hu
inner join TMP_M_HU_PI_Attribute_Actual_v pia ON (pia.M_HU_PI_Version_ID=hu.M_HU_PI_Version_ID)
where true
	and hu.IsActive='Y'
;
-- Duration: ~10sec
create index on TMP_M_HU_Attribute_ToInsert(M_HU_ID, M_HU_PI_Attribute_ID);
-- Duration: ~0.7sec
analyze verbose TMP_M_HU_Attribute_ToInsert;

-- select count(*) from TMP_M_HU_Attribute_ToInsert 

--
-- Delete those which are already exists
-- analyze verbose M_HU_Attribute; -- ~12sec
-- Duration: ~130sec
-- Duration: ~162sec (5843211 rows deleted)
delete from TMP_M_HU_Attribute_ToInsert t
using M_HU_Attribute hua
where (hua.M_HU_ID=t.M_HU_ID and hua.M_HU_PI_Attribute_ID=t.M_HU_PI_Attribute_ID);

--
-- Create and set M_Attribute_ID
-- Duration: ~26sec
update TMP_M_HU_Attribute_ToInsert set M_HU_Attribute_ID=nextval('m_hu_attribute_seq');

--
-- Insert
-- Duration: 540sec (HUGE!!!)
INSERT INTO M_HU_Attribute
(
	m_hu_attribute_id -- ID
	, m_attribute_id
	, m_hu_id
	--
	, value
	, valueInitial
	--
	, valueNumber
	, valueNumberInitial
	--
	, m_hu_pi_attribute_id
	, ad_client_id
	, ad_org_id
	, created
	, createdby
	, updated
	, updatedby
	, isactive
)
select
	m_hu_attribute_id -- ID
	, m_attribute_id
	, m_hu_id
	--
	, value
	, valueInitial
	--
	, valueNumber
	, valueNumberInitial
	--
	, m_hu_pi_attribute_id
	, ad_client_id
	, ad_org_id
	, created
	, createdby
	, updated
	, updatedby
	, isactive
from TMP_M_HU_Attribute_ToInsert;
