
DELETE FROM AD_Menu WHERE AD_Process_ID IN (540618, 540724);
DELETE FROM AD_Scheduler WHERE AD_Process_ID IN (540618, 540724);
DELETE FROM AD_Process WHERE AD_Process_ID IN (540618, 540724);

DROP VIEW IF EXISTS "de.metas.fresh".X_MRP_ProductInfo_AttributeVal_Raw_V;
DROP VIEW IF EXISTS X_MRP_ProductInfo_V;
DROP VIEW IF EXISTS X_MRP_ProductInfo_V_deprecated;

DROP FUNCTION IF EXISTS  "de.metas.fresh".MRP_ProductInfo_Poor_Mans_MRP_V(numeric, numeric);
DROP FUNCTION IF EXISTS  x_mrp_productinfo_attributeval_v(date, numeric);
DROP FUNCTION IF EXISTS X_MRP_ProductInfo_Detail_Fallback_V(date);
DROP FUNCTION IF EXISTS X_MRP_ProductInfo_Detail_Insert_Fallback(date);
DROP FUNCTION IF EXISTS  "de.metas.fresh".X_MRP_ProductInfo_Detail_Update_Poor_Mans_MRP(date, numeric, numeric);
DROP FUNCTION IF EXISTS X_MRP_ProductInfo_Detail_V(date, numeric);
DROP FUNCTION IF EXISTS  "de.metas.fresh".X_MRP_ProductInfo_Detail_MV_Refresh(date, numeric, numeric);

DROP FUNCTION IF EXISTS  x_mrp_productinfo_detail_v_experimental(date,date);

DROP TABLE IF EXISTS X_MRP_ProductInfo_Detail_MV CASCADE;
