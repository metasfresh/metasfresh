
-- init the table
select "de.metas.fresh".X_MRP_ProductInfo_Detail_Update_Poor_Mans_MRP(currentDate::date, p.M_Product_ID, p.M_AttributeSetInstance_ID)
from
--	generate_series($1, $2, '1 day') AS currentDate
	generate_series((now() - interval '4 day')::date, (now() + interval '4 days')::date, '1 day') AS currentDate
		JOIN "de.metas.fresh".M_Product_ID_M_AttributeSetInstance_ID_V p ON p.DateGeneral::Date=currentDate::Date
;
