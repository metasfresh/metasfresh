
DROP VIEW IF EXISTS "de.metas.fresh".OrderBy_ProductGroup_V;
CREATE OR REPLACE VIEW "de.metas.fresh".OrderBy_ProductGroup_V AS
SELECT 
	p.M_Product_ID, 
	p.Name AS OrderBy_ProductName,
	CASE 
		WHEN pc.Name ilike 'Früchte' THEN '02_Fruits'
		WHEN pc.Name ilike 'Kartoffeln' THEN '03_Potatos'
		ELSE '01_Rest'
	END AS OrderBy_ProductGroup
FROM M_Product p
	JOIN M_Product_Category pc ON pc.M_Product_Category_ID=p.M_Product_Category_ID
;
COMMENT ON VIEW "de.metas.fresh".OrderBy_ProductGroup_V IS 'see task 08924'
;


--- Fresh_QtyOnHand_Line_OnUpdate
--------------------------------
--table might even not yet exist with this name
--DROP TRIGGER IF EXISTS Fresh_QtyOnHand_Line_OnUpdate_Trigger ON Fresh_QtyOnHand_Line;
DROP FUNCTION IF EXISTS "de.metas.fresh".Fresh_QtyOnHand_Line_OnUpdate() CASCADE;

CREATE OR REPLACE FUNCTION "de.metas.fresh".Fresh_QtyOnHand_Line_OnUpdate()
  RETURNS trigger AS
$BODY$
DECLARE
	v_DateDoc DATE;
BEGIN
	--
	-- Update DateDoc from header
	-- NOTE: we are updating it only if it's null because else, the Java BL will take care
	if (NEW.DateDoc IS NULL) then
		select h.DateDoc
			into NEW.DateDoc
			from Fresh_QtyOnHand h where h.Fresh_QtyOnHand_ID=NEW.Fresh_QtyOnHand_ID;
		--
		NEW.DateDoc := v_DateDoc;
	end if;
	--
	-- Update ASIKey
	if (NEW.M_AttributeSetInstance_ID is not null)
	then
		NEW.ASIKey := COALESCE(generateHUStorageASIKey(NEW.M_AttributeSetInstance_ID), '-');
	else
		NEW.ASIKey := '-';
	end if;

	return NEW;
END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION "de.metas.fresh".Fresh_QtyOnHand_Line_OnUpdate() OWNER TO adempiere;

CREATE TRIGGER Fresh_QtyOnHand_Line_OnUpdate_Trigger
	BEFORE INSERT OR UPDATE
	ON Fresh_QtyOnHand_Line
	FOR EACH ROW
	EXECUTE PROCEDURE "de.metas.fresh".Fresh_QtyOnHand_Line_OnUpdate();

--- PP_Product_Bom_And_Component
------------------------------
DROP VIEW IF EXISTS "de.metas.fresh".MRP_ProductInfo_Poor_Mans_MRP_V;
DROP VIEW IF EXISTS "de.metas.fresh".PP_Product_Bom_And_Component;

CREATE OR REPLACE VIEW "de.metas.fresh".PP_Product_Bom_And_Component AS
SELECT 
	-- bom (header) stuff
	b.PP_Product_Bom_ID, 
	b.M_Product_ID AS b_M_Product_ID, b_p.Value as b_p_Value, b_p.Name as b_p_Name,
	b.M_AttributeSetInstance_ID AS b_M_AttributeSetInstance_ID,  
	"de.metas.dimension".DIM_Get_GroupName('MRP_Product_Info_ASI_Values', GenerateHUStorageASIKey(b.M_AttributeSetInstance_ID)) AS b_M_AttributeSetGroupNames,
	
	-- bomline stuff
	bl_p.M_Product_ID AS bl_M_Product_ID, bl_p.Value as bl_p_Value, bl_p.Name as bl_p_Name,
	bl.M_AttributeSetInstance_ID AS bl_M_AttributeSetInstance_ID,
	bl.QtyBatch, bl.QtyBom,
	bl_p.IsPurchased,
	
	-- factors to calculate the required qty of the package/component product (including UOM conversion!) for a given qty of the BOM product
	CASE
		WHEN bl.IsQtyPercentage='Y' 
		THEN bl.QtyBatch/100
		ELSE bl.QtyBom
	END AS bl_Factor_BOM,
	CASE
		WHEN bl.IsQtyPercentage='Y' 
		THEN COALESCE(conv_b_bl.MultiplyRate,1)
		ELSE 1
	END AS bl_Factor_b_To_bl,
	conv_bl_p.MultiplyRate AS bl_Factor_bl_To_Product

FROM PP_Product_Bom b
	JOIN M_Product b_p ON b_p.M_Product_ID=b.M_Product_ID
	JOIN PP_Product_BomLine bl ON bl.PP_Product_Bom_ID=b.PP_Product_Bom_ID
			JOIN M_Product bl_p ON bl_p.M_Product_ID=bl.M_Product_ID
	
	-- UOM conversion from the bom's UOM (e.g. Stk) to the UOM of the bom-line (e.g. Kg)
	LEFT JOIN C_UOM_Conversion_V conv_b_bl ON conv_b_bl.M_Product_ID=b.M_Product_ID
		AND conv_b_bl.C_UOM_From_ID=b.C_UOM_ID
		AND conv_b_bl.C_UOM_To_ID=bl.C_UOM_ID
		
	-- UOM conversion from the bom-line's UOM (e.g. mm) to the UOM of the bom-line's product (e.g. Rolle)
	JOIN C_UOM_Conversion_V conv_bl_p ON conv_bl_p.M_Product_ID=bl.M_Product_ID
		AND conv_bl_p.C_UOM_From_ID=COALESCE(bl.C_UOM_ID, bl_p.C_UOM_ID) -- cover the case that bomline-uom is empty
		AND conv_bl_p.C_UOM_To_ID=bl_p.C_UOM_ID
WHERE true
	AND b.IsActive='Y' AND bl.IsActive='Y' AND bl_p.IsActive='Y'
	AND bl.ComponentType IN ('CO','PK')
--	AND b_p.Value='P000787'-- "AB Alicesalat 250g";"Alicesalat, endproduct", Stk
--	AND bl_p.Value='P000328' --Frisee, purchased, raw matrerial, kg
;

COMMENT ON VIEW "de.metas.fresh".PP_Product_Bom_And_Component IS 'task 08682: added this view in the attempt to make the view MRP_ProductInfo_Poor_Mans_MRP_V more "tidy". 
But note that the view is also used in the view M_Product_ID_M_AttributeSetInstance_ID_V.'
;

--- MRP_ProductInfo_Poor_Mans_MRP_V (function)
-------------------------------
CREATE OR REPLACE FUNCTION "de.metas.fresh".MRP_ProductInfo_Poor_Mans_MRP_V(
		IN M_Product_ID numeric, 
		IN M_AttributesetInstance_ID numeric) 
	RETURNS TABLE (
	  pp_product_bom_id numeric(10,0),
	  b_m_product_id numeric(10,0),
	  b_p_value character varying(40),
	  b_p_name character varying(255),
	  b_m_attributesetinstance_id numeric(10,0),
	  b_m_attributesetgroupnames character varying[],
	  bl_m_product_id numeric(10,0),
	  bl_p_value character varying(40),
	  bl_p_name character varying(255),
	  bl_m_attributesetinstance_id numeric(10,0),
	  qtybatch numeric,
	  qtybom numeric,
	  ispurchased character(1),
	  bl_factor_bom numeric,
	  bl_factor_b_to_bl numeric,
	  bl_factor_bl_to_product numeric,
	  factor_recursive numeric,
	  search_depth integer,
	  path numeric(10,0)[],
	  cycle boolean
	) AS
$BODY$
WITH 
RECURSIVE PP_Product_Bom_Recursive AS (

	-- Begin at the upmost bom
	SELECT 
		*,
		(bl_Factor_BOM * bl_Factor_b_To_bl * bl_Factor_bl_To_Product) AS Factor_Recursive,
		1 as search_depth,
		ARRAY[PP_Product_Bom_ID] as path, 
		false as cycle 
	FROM "de.metas.fresh".PP_Product_Bom_And_Component
	WHERE b_M_Product_ID=$1 AND b_M_AttributeSetInstance_ID=COALESCE($2, b_M_AttributeSetInstance_ID)

	UNION ALL
	-- TBH I don't yet fully understand what I'm doing here :-$, but it works
	SELECT 
		r.PP_Product_Bom_ID, 
		b.b_M_Product_ID, b.b_p_Value, b.b_p_Name,
		b.b_M_AttributeSetInstance_ID,
		"de.metas.dimension".DIM_Get_GroupName('MRP_Product_Info_ASI_Values', GenerateHUStorageASIKey(b.b_M_AttributeSetInstance_ID)),
		r.bl_M_Product_ID, r.bl_p_Value, r.bl_p_Name,
		r.bl_M_AttributeSetInstance_ID,
		r.QtyBatch, r.QtyBom, r.IsPurchased, r.bl_Factor_BOM, r.bl_Factor_b_To_bl, r.bl_Factor_bl_To_Product,

		(b.bl_Factor_BOM * b.bl_Factor_b_To_bl * b.bl_Factor_bl_To_Product) * Factor_Recursive,
		search_depth+1,
		(b.PP_Product_Bom_ID|| path)::numeric(10,0)[], -- example "{1000002,2002017,2002387}"
		b.PP_Product_Bom_ID = ANY(path)
	FROM 
		"de.metas.fresh".PP_Product_Bom_And_Component b, 
		PP_Product_Bom_Recursive r -- this is the recursive relf-reference
	WHERE true
		-- select the BOM which has a line-product that is the recursive result's BOM ("main") product
		AND b.bl_M_Product_ID=r.b_M_Product_ID 
		AND COALESCE(GenerateHUStorageASIKey(b.b_M_AttributeSetInstance_ID),'')=COALESCE(GenerateHUStorageASIKey(r.bl_M_AttributeSetInstance_ID),'')
		-- just a precaution to avoid too deep searches (perf)
		AND r.search_depth < 6
		AND NOT cycle
	)
SELECT *
FROM PP_Product_Bom_Recursive
WHERE true 
--	AND b_M_Product_ID=2000070 -- AB Apero Gemüse mit Sauce 350g
--	AND bl_p_M_Product_ID=2000816 -- P001678_Karotten Stäbli Halbfabrikat
--	AND bl_p_M_Product_ID=2001416 -- P000367 Karotten gross gewaschen
;
$BODY$
LANGUAGE sql STABLE;
COMMENT ON FUNCTION "de.metas.fresh".MRP_ProductInfo_Poor_Mans_MRP_V(numeric, numeric) IS
'Returns BOM related information about the raw materials of the given M_Product_ID and M_AttributesetInstance_ID. M_AttributesetInstance_ID may also be null in order not to filter by M_AttributesetInstance_ID'
;

--- X_MRP_ProductInfo_Detail_Update_Poor_Mans_MRP(date, nummeric, numeric)
--------------------------------
DROP FUNCTION IF EXISTS  "de.metas.fresh".X_MRP_ProductInfo_Detail_Update_Poor_Mans_MRP(date, numeric, numeric);
CREATE OR REPLACE FUNCTION "de.metas.fresh".X_MRP_ProductInfo_Detail_Update_Poor_Mans_MRP(IN DateFrom date, IN M_Product_ID numeric, IN M_AttributesetInstance_ID numeric) 
	RETURNS VOID AS
$BODY$

	UPDATE X_MRP_ProductInfo_Detail_MV 
	SET fresh_qtymrp=0
	WHERE DateGeneral::date = $1 AND M_Product_ID = $2 AND ASIKey = GenerateHUStorageASIKey($3,'')
	;

	UPDATE X_MRP_ProductInfo_Detail_MV mv
	SET 
		fresh_qtymrp = CEIL(Poor_Mans_MRP_Purchase_Qty),
		IsFallback='N'
	FROM (
		-- to get our data,
		-- join the "end-product" mv line with the poor man's MRP record's end-product (b_m_Product_ID) column, and multiply the "end-product" mv's QtyOrdered_Sale_OnDate with the BOM-line's factor
		-- that way way get the Qtys needed for the raw materials
		SELECT 
			COALESCE(v_exact.bl_M_Product_ID, v_fallback.bl_M_Product_ID) as bl_M_Product_ID, 
			GenerateHUStorageASIKey(COALESCE(v_exact.b_M_AttributeSetInstance_ID, v_fallback.b_M_AttributeSetInstance_ID),'') as ASIKey, 
			mv2.DateGeneral,
			SUM(mv2.QtyOrdered_Sale_OnDate * COALESCE(v_exact.Factor_Recursive, v_fallback.Factor_Recursive)) as Poor_Mans_MRP_Purchase_Qty
			
		FROM X_MRP_ProductInfo_Detail_MV mv2
			-- join the "end-product" mv line with the poor man's MRP record's end-product (b_m_Product_ID)
			LEFT JOIN "de.metas.fresh".MRP_ProductInfo_Poor_Mans_MRP_V($2,$3) v_exact 
				ON mv2.M_Product_ID=v_exact.b_m_Product_ID
					AND mv2.ASIKey = GenerateHUStorageASIKey(v_exact.b_M_AttributeSetInstance_ID,'') 
					AND v_exact.IsPurchased='Y'

			LEFT JOIN "de.metas.fresh".MRP_ProductInfo_Poor_Mans_MRP_V($2,$3) v_fallback 
				ON mv2.M_Product_ID=v_fallback.b_m_Product_ID
					AND v_exact.bl_m_Product_ID IS NULL -- it is important to only join v_fallback, if there was no _vevact to be found. Otherwise we would join one v_fallback for each bomline-product 
					AND GenerateHUStorageASIKey(v_fallback.b_M_AttributeSetInstance_ID,'')=''
					AND v_fallback.IsPurchased='Y'

		WHERE true
			AND COALESCE(v_exact.IsPurchased,v_fallback.IsPurchased)='Y'
			AND mv2.IsFallback='N' -- we can exclude endproducts of fallback records, because they don't have a reserved qty, so they don't contribute to any raw material's Poor_Mans_MRP_Purchase_Qty
			AND mv2.DateGeneral::date = $1 AND mv2.M_Product_ID = $2 AND mv2.ASIKey = GenerateHUStorageASIKey($3,'')
		GROUP BY 
			COALESCE(v_exact.bl_M_Product_ID, v_fallback.bl_M_Product_ID), 
			GenerateHUStorageASIKey(COALESCE(v_exact.b_M_AttributeSetInstance_ID, v_fallback.b_M_AttributeSetInstance_ID),''), 
			mv2.DateGeneral
	) data
	WHERE true
		AND data.bl_M_Product_ID = mv.M_Product_ID 
		AND data.ASIKey = mv.ASIKey
		AND data.DateGeneral=mv.DateGeneral 
		-- AND mv.IsFallback='N' there is no reason not to update records that were supplemented by X_MRP_ProductInfo_Detail_Fallback_V
	;
$BODY$
LANGUAGE sql VOLATILE;


--- MRP_ProductInfo_Poor_Mans_MRP_V (view), for the date range function
------------------------------
DROP VIEW IF EXISTS "de.metas.fresh".MRP_ProductInfo_Poor_Mans_MRP_V;
CREATE OR REPLACE VIEW "de.metas.fresh".MRP_ProductInfo_Poor_Mans_MRP_V AS
WITH 
RECURSIVE PP_Product_Bom_Recursive AS (

	-- Begin at the upmost bom
	SELECT 
		*,
		(bl_Factor_BOM * bl_Factor_b_To_bl * bl_Factor_bl_To_Product) AS Factor_Recursive,
		1 as search_depth,
		ARRAY[PP_Product_Bom_ID] as path, 
		false as cycle 
	FROM "de.metas.fresh".PP_Product_Bom_And_Component

	UNION ALL
	-- TBH I don't yet fully understand what I'm doing here :-$, but it works
	SELECT 
		r.PP_Product_Bom_ID, 
		b.b_M_Product_ID, b.b_p_Value, b.b_p_Name,
		b.b_M_AttributeSetInstance_ID,
		"de.metas.dimension".DIM_Get_GroupName('MRP_Product_Info_ASI_Values', GenerateHUStorageASIKey(b.b_M_AttributeSetInstance_ID)),
		r.bl_M_Product_ID, r.bl_p_Value, r.bl_p_Name,
		r.bl_M_AttributeSetInstance_ID,
		r.QtyBatch, r.QtyBom, r.IsPurchased, r.bl_Factor_BOM, r.bl_Factor_b_To_bl, r.bl_Factor_bl_To_Product,

		(b.bl_Factor_BOM * b.bl_Factor_b_To_bl * b.bl_Factor_bl_To_Product) * Factor_Recursive,
		search_depth+1,
		(b.PP_Product_Bom_ID|| path)::numeric(10,0)[], -- example "{1000002,2002017,2002387}"
		b.PP_Product_Bom_ID = ANY(path)
	FROM 
		"de.metas.fresh".PP_Product_Bom_And_Component b, 
		PP_Product_Bom_Recursive r -- this is the recursive relf-reference
	WHERE true
		-- select the BOM which has a line-product that is the recursive result's BOM ("main") product
		AND b.bl_M_Product_ID=r.b_M_Product_ID 
		AND COALESCE(GenerateHUStorageASIKey(b.b_M_AttributeSetInstance_ID),'')=COALESCE(GenerateHUStorageASIKey(r.bl_M_AttributeSetInstance_ID),'')
		-- just a precaution to avoid too deep searches (perf)
		AND r.search_depth < 6
		AND NOT cycle
	)
SELECT *
FROM PP_Product_Bom_Recursive
WHERE true 
--	AND b_M_Product_ID=2000070 -- AB Apero Gem�se mit Sauce 350g
--	AND bl_p_M_Product_ID=2000816 -- St�bli Halbfabrikat
--	AND bl_p_M_Product_ID=2001416 -- Karotten gross gewaschen
;
COMMENT ON VIEW "de.metas.fresh".MRP_ProductInfo_Poor_Mans_MRP_V IS 'task 08682: first attempt to use recursive SQL. The goal is to find the raw materials for end products.
For an endproduct (b_M_Product_ID) the view selects its raw materials, and a factor (Factor_Recursive).
The query works over multiple levels of PP_Product_BOMs and bom-lines  
';


--- X_MRP_ProductInfo_Detail_Update_Poor_Mans_MRP(date, date)
--------------------------------
DROP FUNCTION IF EXISTS  "de.metas.fresh".X_MRP_ProductInfo_Detail_Update_Poor_Mans_MRP(date, date);
CREATE OR REPLACE FUNCTION "de.metas.fresh".X_MRP_ProductInfo_Detail_Update_Poor_Mans_MRP(IN DateFrom date, IN DateTo date) RETURNS VOID AS
$BODY$

	UPDATE X_MRP_ProductInfo_Detail_MV 
	SET fresh_qtymrp=0
	WHERE DateGeneral::date >= $1 AND DateGeneral::date <= $2
	;

	UPDATE X_MRP_ProductInfo_Detail_MV mv
	SET 
		fresh_qtymrp = CEIL(Poor_Mans_MRP_Purchase_Qty),
		IsFallback='N'
	FROM (
		-- to get our data,
		-- join the "end-product" mv line with the poor man's MRP record's end-product (b_m_Product_ID) column, and multiply the "end-product" mv's QtyOrdered_Sale_OnDate with the BOM-line's factor
		-- that way way get the Qtys needed for the raw materials
		SELECT 
			COALESCE(v_exact.bl_M_Product_ID, v_fallback.bl_M_Product_ID) as bl_M_Product_ID, 
			GenerateHUStorageASIKey(COALESCE(v_exact.b_M_AttributeSetInstance_ID, v_fallback.b_M_AttributeSetInstance_ID),'') as ASIKey, 
			mv2.DateGeneral,
			SUM(mv2.QtyOrdered_Sale_OnDate * COALESCE(v_exact.Factor_Recursive, v_fallback.Factor_Recursive)) as Poor_Mans_MRP_Purchase_Qty
			
		FROM X_MRP_ProductInfo_Detail_MV mv2
			-- join the "end-product" mv line with the poor man's MRP record's end-product (b_m_Product_ID)
			LEFT JOIN "de.metas.fresh".MRP_ProductInfo_Poor_Mans_MRP_V v_exact 
				ON mv2.M_Product_ID=v_exact.b_m_Product_ID
					AND mv2.ASIKey = GenerateHUStorageASIKey(v_exact.b_M_AttributeSetInstance_ID,'') 
					AND v_exact.IsPurchased='Y'

			LEFT JOIN "de.metas.fresh".MRP_ProductInfo_Poor_Mans_MRP_V v_fallback 
				ON mv2.M_Product_ID=v_fallback.b_m_Product_ID
					AND v_exact.bl_m_Product_ID IS NULL -- it is important to only join v_fallback, if there was no _vevact to be found. Otherwise we would join one v_fallback for each bomline-product 
					AND GenerateHUStorageASIKey(v_fallback.b_M_AttributeSetInstance_ID,'')=''
					AND v_fallback.IsPurchased='Y'

		WHERE true
			AND COALESCE(v_exact.IsPurchased,v_fallback.IsPurchased)='Y'
			AND mv2.IsFallback='N' -- we can exclude endproducts of fallback records, because they don't have a reserved qty, so they don't contribute to any raw material's Poor_Mans_MRP_Purchase_Qty
		GROUP BY 
			COALESCE(v_exact.bl_M_Product_ID, v_fallback.bl_M_Product_ID), 
			GenerateHUStorageASIKey(COALESCE(v_exact.b_M_AttributeSetInstance_ID, v_fallback.b_M_AttributeSetInstance_ID),''), 
			mv2.DateGeneral
	) data
	WHERE true
		AND data.bl_M_Product_ID = mv.M_Product_ID 
		AND data.ASIKey = mv.ASIKey
		AND data.DateGeneral=mv.DateGeneral 
		-- AND mv.IsFallback='N' there is no reason not to update records that were supplemented by X_MRP_ProductInfo_Detail_Fallback_V
		AND mv.DateGeneral::date >= $1 AND mv.DateGeneral::date <= $2
	;
$BODY$
LANGUAGE sql VOLATILE;

--- M_Product_ID_M_AttributeSetInstance_ID_V
------------------------------------
DROP VIEW IF EXISTS "de.metas.fresh".M_Product_ID_M_AttributeSetInstance_ID_V;
CREATE OR REPLACE VIEW "de.metas.fresh".M_Product_ID_M_AttributeSetInstance_ID_V AS
SELECT M_Product_ID, M_AttributeSetInstance_ID, DateGeneral
FROM (
	SELECT t.M_Product_ID, t.M_AttributeSetInstance_ID, t.MovementDate::date as DateGeneral, 'M_Transaction' as source
	FROM M_Transaction t
	UNION
	SELECT ol.M_Product_ID, ol.M_AttributeSetInstance_ID, 
		COALESCE(
			sched.PreparationDate_Override, sched.PreparationDate, -- task 08931: the user works with PreparationDate instead of DatePromised 
			o.PreparationDate, -- fallback in case the schedule does not exist yet
			o.DatePromised -- fallback in case of orders created by the system (e.g. EDI) and missing proper tour planning master data 
		)::Date AS DateGeneral,
		'C_OrderLine' 
	FROM C_Order o
		JOIN C_OrderLine ol ON ol.C_Order_ID=o.C_Order_ID
		LEFT JOIN M_ShipmentSchedule sched ON sched.C_OrderLine_ID=ol.C_OrderLine_ID AND sched.IsActive='Y'
	WHERE o.DocStatus IN ('CO', 'CL')
	UNION
	SELECT qohl.M_Product_ID, qohl.M_AttributeSetInstance_ID, qoh.DateDoc::date, 'Fresh_QtyOnHand_Line'
	FROM Fresh_QtyOnHand qoh
		JOIN Fresh_QtyOnHand_Line qohl ON qoh.fresh_qtyonhand_id = qohl.Fresh_qtyonhand_id
	WHERE qoh.Processed='Y'
--	UNION
--	SELECT mrp.bl_M_Product_ID, mrp.bl_M_AttributeSetInstance_ID, NULL:date
--	FROM "de.metas.Fresh".PP_Product_Bom_And_Component mrp
) data
WHERE true
	AND M_Product_ID IS NOT NULL
--	AND M_Product_ID=(select M_Product_ID from M_Product where Value='P000037')
--	AND DateGeneral::date='2015-04-09'::date
GROUP BY M_Product_ID, M_AttributeSetInstance_ID, DateGeneral;

COMMENT ON VIEW "de.metas.fresh".M_Product_ID_M_AttributeSetInstance_ID_V IS 'Used in X_MRP_ProductInfo_Detail_V to enumerate all the products and ASIs for which we need numbers.
Note: i tried changing this view into an SQL function with dateFrom and dateTo as where-clause paramters, but it didn''t bring any gain in X_MRP_ProductInfo_Detail_V.'
;

--- RV_C_OrderLine_QtyOrderedReservedPromised_OnDate_V
------------------------------------
DROP VIEW IF EXISTS "de.metas.fresh".RV_C_OrderLine_QtyOrderedReservedPromised_OnDate_V;
CREATE OR REPLACE VIEW "de.metas.fresh".RV_C_OrderLine_QtyOrderedReservedPromised_OnDate_V AS
SELECT 
	M_Product_ID, 
	DateGeneral::Date, 
	M_AttributeSetInstance_ID,
	SUM(QtyReserved_Sale) AS QtyReserved_Sale,
	SUM(QtyReserved_Purchase) AS QtyReserved_Purchase,
	SUM(QtyReserved_Purchase) - SUM(QtyReserved_Sale) as QtyPromised,
	SUM(QtyOrdered_Sale) AS QtyOrdered_Sale
FROM
(
SELECT
	COALESCE(
		sched.PreparationDate_Override, sched.PreparationDate, -- task 08931: the user needs to plan with PreparationDate instead of DatePromised 
		o.PreparationDate, -- fallback in case the schedule does not exist yet
		o.DatePromised -- fallback in case of orders created by the system (e.g. EDI) and missing proper tour planning master data 
	) AS DateGeneral,
	ol.M_Product_ID, 
	ol.M_AttributeSetInstance_ID,
	CASE WHEN o.IsSOTrx='Y' THEN COALESCE(ol.QtyReserved,0) ELSE 0 END AS QtyReserved_Sale,
	CASE WHEN o.IsSOTrx='N' THEN COALESCE(ol.QtyReserved,0) ELSE 0 END AS QtyReserved_Purchase,
	CASE WHEN o.IsSOTrx='Y' THEN COALESCE(ol.QtyOrdered,0) ELSE 0 END AS QtyOrdered_Sale
FROM C_Order o
	JOIN C_OrderLine ol ON ol.C_Order_ID=o.C_Order_ID AND ol.IsActive='Y' AND ol.IsHUStorageDisabled='N'
	LEFT JOIN M_ShipmentSchedule sched ON sched.C_OrderLine_ID=ol.C_OrderLine_ID AND sched.IsActive='Y'
WHERE true
	AND o.IsActive='Y'
	AND o.DocStatus IN ('CO', 'CL')
) data
GROUP BY 
	DateGeneral,
	M_Product_ID, 
	M_AttributeSetInstance_ID
;

--- RV_HU_QtyMaterialentnahme_OnDate
------------------------------------
DROP VIEW IF EXISTS "de.metas.fresh".RV_HU_QtyMaterialentnahme_OnDate;
CREATE OR REPLACE VIEW "de.metas.fresh".RV_HU_QtyMaterialentnahme_OnDate AS
SELECT 
	COALESCE(SUM(t.MovementQty), 0) AS QtyMaterialentnahme,
	TRUNC(t.MovementDate,'DD') as Updated_Date,
	t.M_Product_ID,
	t.M_AttributeSetInstance_ID
FROM M_Transaction t 
	inner join M_Locator loc on loc.M_Locator_ID=t.M_Locator_ID
		and loc.M_Warehouse_ID=(
			select COALESCE(value::integer, -1) from AD_SysConfig 
			where Name='de.metas.handlingunits.client.terminal.inventory.model.InventoryHUEditorModel#DirectMove_Warehouse_ID'
				and IsActive='Y'
			order by AD_Client_ID desc, AD_Org_ID desc
			limit 1)
GROUP BY TRUNC(t.MovementDate,'DD'), t.M_Product_ID, t.M_AttributeSetInstance_ID
;
COMMENT ON VIEW "de.metas.fresh".RV_HU_QtyMaterialentnahme_OnDate IS 'Task 08476: Despite the name this view is based on M_Transaction, but not that said M_Transactions are created for certain HUs selected by the user. In the user''s context, the whole use-cate/workflow is called "Materialentnahme"';


--- M_Transaction_update_X_Fresh_QtyOnHand_OnDate()
---------------------------
DROP TRIGGER IF EXISTS M_Transaction_update_X_Fresh_QtyOnHand_OnDate_INSERT_trigger ON M_Transaction;
DROP TRIGGER IF EXISTS M_Transaction_update_X_Fresh_QtyOnHand_OnDate_DELETE_trigger ON M_Transaction;
DROP FUNCTION IF EXISTS "de.metas.fresh".M_Transaction_update_X_Fresh_QtyOnHand_OnDate();
CREATE OR REPLACE FUNCTION "de.metas.fresh".m_transaction_update_x_Fresh_qtyonhand_ondate()
  RETURNS trigger AS
$BODY$
DECLARE
	v_M_Product_ID				NUMERIC(10);
	v_M_AttributeSetInstance_ID	NUMERIC(10);
	v_Date						DATE;
	v_Qty						NUMERIC;
	v_Record					RECORD;
BEGIN
	IF (TG_OP='INSERT') THEN
		v_M_Product_ID 				:= NEW.M_Product_ID;
		v_M_AttributeSetInstance_ID	:= NEW.M_AttributeSetInstance_ID;
		v_Date 						:= NEW.MovementDate;
		v_Qty 						:= NEW.MovementQty;
		v_Record 					:= NEW;
	ELSIF (TG_OP='DELETE') THEN
		v_M_Product_ID 				:= OLD.M_Product_ID;
		v_M_AttributeSetInstance_ID	:= OLD.M_AttributeSetInstance_ID;
		v_Date 						:= OLD.MovementDate;
		v_Qty 						:= OLD.MovementQty * -1;
		v_Record 					:= OLD;
	ELSE
		RAISE EXCEPTION 'Trigger operation not supported';
	END IF;

	LOOP
		-- first try to update the key
		UPDATE "de.metas.fresh".X_Fresh_QtyOnHand_OnDate t SET Qty=Qty+v_Qty 
		WHERE t.M_Product_ID=v_M_Product_ID
				AND t.M_AttributeSetInstance_ID=v_M_AttributeSetInstance_ID
				AND t.MovementDate=v_Date;
		IF found THEN
			RETURN v_Record;
		END IF;
		-- not there, so try to insert the key
		-- if someone else inserts the same key concurrently,
		-- we could get a unique-key failure
		BEGIN
			INSERT INTO "de.metas.fresh".X_Fresh_QtyOnHand_OnDate(M_Product_ID, M_AttributeSetInstance_ID, MovementDate, Qty) VALUES (v_M_Product_ID, v_M_AttributeSetInstance_ID, v_Date, v_Qty);
			RETURN v_Record;
		EXCEPTION WHEN unique_violation THEN
			-- do nothing, and loop to try the UPDATE again
		END;
	END LOOP;		
END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION "de.metas.fresh".m_transaction_update_x_Fresh_qtyonhand_ondate()
  OWNER TO adempiere;
COMMENT ON FUNCTION "de.metas.fresh".m_transaction_update_x_Fresh_qtyonhand_ondate() IS 'the function is VOLATILE because it is called from a trigger function and to avoid the error "ERROR: UPDATE is not allowed in a non-volatile function';

DROP TRIGGER IF EXISTS M_Transaction_update_X_Fresh_QtyOnHand_OnDate_INSERT_trigger ON M_Transaction;
CREATE TRIGGER M_Transaction_update_X_Fresh_QtyOnHand_OnDate_INSERT_trigger
	AFTER INSERT
	ON M_Transaction
	FOR EACH ROW
	WHEN (NEW.MovementType IN ('C-', 'C+', 'V-', 'V+'))
	EXECUTE PROCEDURE "de.metas.fresh".M_Transaction_update_X_Fresh_QtyOnHand_OnDate();
--
DROP TRIGGER IF EXISTS M_Transaction_update_X_Fresh_QtyOnHand_OnDate_DELETE_trigger ON M_Transaction;
CREATE TRIGGER M_Transaction_update_X_Fresh_QtyOnHand_OnDate_DELETE_trigger
	AFTER DELETE
	ON M_Transaction
	FOR EACH ROW
	WHEN (OLD.MovementType IN ('C-', 'C+', 'V-', 'V+'))
	EXECUTE PROCEDURE "de.metas.fresh".M_Transaction_update_X_Fresh_QtyOnHand_OnDate();
