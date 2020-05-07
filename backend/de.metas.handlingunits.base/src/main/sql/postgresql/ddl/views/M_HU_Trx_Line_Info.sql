CREATE OR REPLACE VIEW "de.metas.handlingunits".M_HU_Trx_Line_Info AS
SELECT
	wh.M_Warehouse_ID, 
	l.M_Locator_ID, 
	wh.Name as WHNAme,
	hu.M_Locator_ID as HU_M_Locator_ID,
	hus.M_Product_ID as hus_M_Product_ID,
	hus.C_UOM_ID as hus_C_UOM_ID,
	hutl.qty
	, hutl.M_HU_ID
	, "de.metas.handlingunits".huInfo(hutl.M_HU_ID) as huInfo
	, hutl.Created
	, hutl.DateTrx
	, hutl.M_Product_ID, hutl.C_UOM_ID
	, hutl.IsActive
	, (select t.TableName from AD_Table t where t.AD_Table_ID=hutl.AD_Table_ID) as TableName
	, (select u.Name from AD_User u where u.AD_User_ID=hutl.CreatedBy) as CreatedBy
	, hutl.M_HU_Trx_Line_ID
	--
	, (select t.TableName from AD_Table t where t.AD_Table_ID=hutl2.AD_Table_ID) as TableName2
	-- , hutl2.Record_ID
	, hutl2.Qty as Qty2
	, hutl2.M_Locator_ID as hutl2_M_Locator_ID
	-- , hutl.*
FROM
	M_Warehouse wh
	LEFT OUTER JOIN M_Locator l ON wh.M_Warehouse_ID = l.M_Warehouse_ID
	LEFT OUTER JOIN M_HU_Trx_line hutl ON l.M_Locator_ID = hutl.M_locator_ID
	LEFT OUTER JOIN M_HU_Trx_line hutl2 ON (hutl2.M_HU_Trx_Line_ID=hutl.Parent_HU_Trx_Line_ID)
	
	LEFT OUTER JOIN M_HU hu ON hutl.M_HU_ID = hu.M_HU_ID
	LEFT OUTER JOIN M_HU_Storage hus ON hu.M_HU_ID = hus.M_HU_ID
WHERE true
--	AND hutl.DateTrx::date < '2015-01-01'
--	AND hutl.huStatus IN ('A', 'S') -- qonly display transactions if status is stocked, A = Active, S = Picked
	-- and wh.M_Warehouse_ID=1000056
--	and hus.M_Product_ID=2000008
--	and hutl.M_HU_ID=1007346

order by
hutl.Created
, hutl.M_Locator_ID
, hutl.M_HU_ID
, hutl.DateTrx;

COMMENT ON VIEW "de.metas.handlingunits".M_HU_Trx_Line_Info IS 'Shows a lot of usefull information about a particular M_HU_Trx_line and its parent trx line.'
;