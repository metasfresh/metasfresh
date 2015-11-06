DROP VIEW IF EXISTS RV_M_Material_Tracking_HU_Details;

CREATE OR REPLACE VIEW RV_M_Material_Tracking_HU_Details
AS
SELECT
	mt.Lot
	, mt.M_Material_Tracking_ID
	, hu.M_HU_ID
	, hu.HUStatus
	, hu.M_Locator_ID
	, hs.M_Product_ID
	, hs.Qty, hs.C_UOM_ID
	--
	-- IsWaschprobe attribute
	, COALESCE(
		(select hua_qi.Value
			from M_HU_Attribute hua_qi
			inner join M_Attribute a_qi on (a_qi.M_Attribute_ID=hua_qi.M_Attribute_ID and a_qi.Value='IsQualityInspection')
			where hua_qi.M_HU_ID=hu.M_HU_ID
		)
		, 'N')::char(1) as IsQualityInspection
	--
	-- Standard columns
	, hu.Created
	, hu.CreatedBy
	, hu.Updated
	, hu.UpdatedBy
	, hu.IsActive
	, hu.AD_Client_ID
	, hu.AD_Org_ID
FROM  M_Material_Tracking mt 
INNER JOIN M_Attribute a ON (a.Value='M_Material_Tracking_ID')
INNER JOIN M_HU_Attribute hu_at ON (hu_at.M_Attribute_ID=a.M_Attribute_ID AND hu_at.Value=mt.M_Material_Tracking_ID::text)
INNER JOIN M_HU hu ON (hu_at.M_HU_ID=hu.M_HU_ID)
INNER JOIN M_HU_Storage hs ON (hu.M_HU_ID = hs.M_HU_ID)
WHERE 
	a.Value='M_Material_Tracking_ID'
	AND hu.M_HU_Item_Parent_ID IS NULL
;

