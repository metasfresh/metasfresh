

DROP FUNCTION IF EXISTS "de.metas.fresh".Migrate_X_MRP_ProductInfo_Detail_MV_Data(integer);
CREATE OR REPLACE FUNCTION "de.metas.fresh".Migrate_X_MRP_ProductInfo_Detail_MV_Data(IN p_maxNumber integer)
RETURNS INTEGER AS
$BODY$
DECLARE
	Count_Updated bigint;
	Count_NeedToUpdate bigint;
BEGIN

	RAISE NOTICE 'Checking how many records still need to be updated..';
	SELECT count(9) FROM X_MRP_ProductInfo_Detail_MV mv WHERE  X_MRP_ProductInfo_Detail_MV_ID IS NULL INTO Count_NeedToUpdate;
	RAISE NOTICE 'Need to update % records; p_maxNumber is %', Count_NeedToUpdate, p_maxNumber;
	
	IF (Count_NeedToUpdate <= 0)
	THEN
		RAISE NOTICE '!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!';
		RAISE NOTICE '!! Nothing to do for this function !!';
		RAISE NOTICE '!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!';
		
		RETURN Count_NeedToUpdate;
	END IF;
	
	UPDATE X_MRP_ProductInfo_Detail_MV mv_outer
	SET 
		AD_Client_ID=d.AD_Client_ID,
		AD_Org_ID=d.AD_Org_ID,
		Created=d.DateGeneral,
		Createdby=99,
		IsActive='Y',
		Updated=d.DateGeneral,
		Updatedby=99,
		X_MRP_ProductInfo_Detail_MV_ID=nextval('X_MRP_PRODUCTINFO_DETAIL_MV_SEQ')
	FROM (
		SELECT mv.M_Product_ID, mv.ASIKey, mv.DateGeneral, p.AD_Client_ID, p.AD_Org_ID
		FROM X_MRP_ProductInfo_Detail_MV mv
			JOIN M_Product p ON p.M_Product_ID=mv.M_Product_ID
		WHERE X_MRP_ProductInfo_Detail_MV_ID IS NULL
		LIMIT p_maxNumber
		FOR UPDATE
	) d
	WHERE mv_outer.M_Product_ID=d.M_Product_ID
		AND mv_outer.ASIKey=d.ASIKey
		AND mv_outer.DateGeneral=d.DateGeneral;
		
	GET DIAGNOSTICS Count_Updated = ROW_COUNT;
	RAISE NOTICE 'Updated % records', Count_Updated;
	
	IF (Count_Updated >= Count_NeedToUpdate)
	THEN
		RAISE NOTICE 'Finalizing the table''s DDL';
		
		ALTER TABLE X_MRP_ProductInfo_Detail_MV ALTER COLUMN ad_client_id SET NOT NULL;
		ALTER TABLE X_MRP_ProductInfo_Detail_MV ALTER COLUMN ad_org_id SET NOT NULL;
		ALTER TABLE X_MRP_ProductInfo_Detail_MV ALTER COLUMN created SET NOT NULL;
		ALTER TABLE X_MRP_ProductInfo_Detail_MV ALTER COLUMN createdby SET NOT NULL;
		ALTER TABLE X_MRP_ProductInfo_Detail_MV ALTER COLUMN isactive SET NOT NULL;
		ALTER TABLE X_MRP_ProductInfo_Detail_MV ALTER COLUMN updated SET NOT NULL;
		ALTER TABLE X_MRP_ProductInfo_Detail_MV ALTER COLUMN updatedby SET NOT NULL;
		ALTER TABLE X_MRP_ProductInfo_Detail_MV ALTER COLUMN X_MRP_ProductInfo_Detail_MV_ID SET NOT NULL;

		ALTER TABLE x_mrp_productinfo_detail_mv DROP CONSTRAINT x_mrp_productinfo_detail_mv_pk;
		ALTER TABLE X_MRP_ProductInfo_Detail_MV
		  ADD CONSTRAINT X_MRP_ProductInfo_Detail_MV_Key PRIMARY KEY(X_MRP_ProductInfo_Detail_MV_ID);
		ALTER TABLE x_mrp_productinfo_detail_mv 
		  ALTER COLUMN x_mrp_productinfo_detail_mv_id SET DEFAULT nextval('X_MRP_PRODUCTINFO_DETAIL_MV_SEQ');
		  
		CREATE UNIQUE INDEX x_mrp_productinfo_detail_mv_uc
			ON x_mrp_productinfo_detail_mv (dategeneral, m_product_id, asikey, isfallback);
			
		RAISE NOTICE '!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!';
		RAISE NOTICE '!! Done; no further runs of this function are needed !!';
		RAISE NOTICE '!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!';
	ELSE
		RAISE NOTICE '!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!';
		RAISE NOTICE '!! PLEASE RERUN THIS FUNCTION TO FINISH THE MIGRATION !!';
		RAISE NOTICE '!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!';
	END IF;
	
	RETURN Count_Updated;

END;
$BODY$
  LANGUAGE plpgsql VOLATILE;
COMMENT ON FUNCTION  "de.metas.fresh".Migrate_X_MRP_ProductInfo_Detail_MV_Data(integer) IS 
'Sets important values like X_MRP_ProductInfo_Detail_MV_ID and AD_Client_ID to X_MRP_ProductInfo_Detail_MV and finalizes the DDL (e.g. making AD_Client_ID NOT NULL).
See https://github.com/metasfresh/metasfresh/issues/213';

-- run the function with de.metas.migration.async.ExecuteSQLWorkpackageProcessor
-- doesn't work; this function needs *UPDATEs*
-- SELECT "de.metas.async".executesqlasync('SELECT Migrate_X_MRP_ProductInfo_Detail_MV_Data(500)');

SELECT "de.metas.fresh".Migrate_X_MRP_ProductInfo_Detail_MV_Data(500000);

