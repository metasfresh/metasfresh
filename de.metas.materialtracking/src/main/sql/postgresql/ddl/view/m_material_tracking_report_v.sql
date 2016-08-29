
DROP VIEW IF EXISTS "de.metas.materialtracking".M_Material_Tracking_Report_V;
CREATE OR REPLACE VIEW "de.metas.materialtracking".M_Material_Tracking_Report_V AS
SELECT 
	mt.Lot AS mt_Lot,
	mt.Processed AS mt_Processed,
	lc.Name || ' ('|| lcv.ValidFrom ::date || ' - ' || lcv.ValidTo::date || ')' AS lkv,
	bp.Value AS bp_value,
	bp.Name AS bp_Name,
	ppo.DocumentNo AS ppo_DocumentNo,
	ppo_dt.Name AS ppo_DocType_Name,
	ppo.DocStatus AS ppo_DocStatus,
	p_raw.Value AS p_raw_Value,
	p_raw.Name AS p_raw_Name, 
	(select count(7) from M_HU_Assignment hu_raw where hu_raw.Record_ID=cc_raw.PP_Cost_Collector_ID AND hu_raw.AD_Table_ID=get_table_id('PP_Cost_Collector') and hu_raw.IsActive='Y') AS tu_raw_issued,
	cc_raw.MovementQty AS qty_raw_Issued,
	uom_raw.UOMSymbol AS uom_raw_symbol,
	p_main.Value AS p_main_Value,
	p_main.Name AS p_main_Name,
	cc_main.MovementQty AS qty_main_received,
	uom_main.UOMSymbol AS uom_main_symbol,
	p_co1.Value AS p_co1_Value,
	p_co1.Name AS p_co1_Name,
	cc_co1.MovementQty*-1 AS qty_co1_received, -- qty is degated because a co product receipt is a "negative issue"
	uom_co1.UOMSymbol AS uom_co1_symbol
FROM M_Material_Tracking mt
	LEFT JOIN C_BPartner bp ON bp.C_BPartner_ID=mt.C_BPartner_ID
	LEFT JOIN M_QualityInsp_LagerKonf_Version lcv ON lcv.M_QualityInsp_LagerKonf_Version_ID=mt.M_QualityInsp_LagerKonf_Version_ID
		LEFT JOIN M_QualityInsp_LagerKonf lc ON lc.M_QualityInsp_LagerKonf_ID=lcv.M_QualityInsp_LagerKonf_ID
	LEFT JOIN M_Material_Tracking_Ref mtr ON mtr.M_Material_Tracking_ID=mt.M_Material_Tracking_ID AND mtr.AD_Table_ID=get_table_id('PP_Order') AND mtr.IsActive='Y'
		LEFT JOIN PP_Order ppo ON ppo.PP_Order_ID=mtr.Record_ID
			LEFT JOIN C_DocType ppo_dt ON ppo_dt.C_DocType_ID=ppo.C_DocType_ID
		LEFT JOIN PP_Cost_Collector cc_raw ON cc_raw.PP_Order_ID=mtr.Record_ID -- Record_ID = PP_Order_ID
				AND cc_raw.IsActive='Y' AND cc_raw.DocStatus IN ('CO', 'CL')
				AND cc_raw.CostCollectorType='110' -- component issue
			LEFT JOIN M_Product p_raw ON p_raw.M_Product_ID=cc_raw.M_Product_ID
				LEFT JOIN C_UOM uom_raw ON uom_raw.C_UOM_ID=p_raw.C_UOM_ID
		LEFT JOIN PP_Cost_Collector cc_main ON cc_main.PP_Order_ID=mtr.Record_ID -- Record_ID = PP_Order_ID
				AND cc_main.IsActive='Y' AND cc_main.DocStatus IN ('CO', 'CL')
				AND cc_main.CostCollectorType='100' -- material receipt
			LEFT JOIN M_Product p_main ON p_main.M_Product_ID=cc_main.M_Product_ID
				LEFT JOIN C_UOM uom_main ON uom_main.C_UOM_ID=p_main.C_UOM_ID
		LEFT JOIN PP_Order_BOMLine ppo_bl_co1 ON  ppo_bl_co1.PP_Order_ID=mtr.Record_ID
				AND ppo_bl_co1.ComponentType='CP' 
				AND ppo_bl_co1.IsActive='Y'
			LEFT JOIN PP_Cost_Collector cc_co1 ON cc_co1.PP_Order_ID=mtr.Record_ID -- Record_ID = PP_Order_ID
					AND cc_co1.IsActive='Y' AND cc_co1.DocStatus IN ('CO', 'CL')
					AND cc_co1.PP_Order_BOMLine_ID=ppo_bl_co1.PP_Order_BOMLine_ID
					AND cc_co1.CostCollectorType='150' -- mix variance
					
				LEFT JOIN M_Product p_co1 ON p_co1.M_Product_ID=cc_co1.M_Product_ID
					LEFT JOIN C_UOM uom_co1 ON uom_co1.C_UOM_ID=p_co1.C_UOM_ID
WHERE true;
COMMENT ON VIEW "de.metas.materialtracking".M_Material_Tracking_Report_V IS '
See https://github.com/metasfresh/metasfresh/issues/320
Suggestest german column titles:
* mt_Lot: Vorgangs-ID
* mt_processed: Vorgang abgeschl.
* lkv: Lagerkonf-Version
* bp_value and bp_name: Geschäftspartner
* bp_name: Produktionsauftrag
* ppo_doctype_name: Belegart
* ppo_docstatus: Belegstatus
* p_raw_value and p_raw_name: Rohprodukt
* tu_raw_issued: Anz. Paloxen
* qty_raw_issued Menge	
* uom_raw_symbol Einheit
* p_main_value and p_main_name: Hauptprodukt
* qty_main_received: Menge
* uom_main_symbol: Einheit
* p_co1_value and p_co1_name: Nebenprodukt
* qty_co1_received: Menge
* uom_co1_symbol: Einheit																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																											
';
