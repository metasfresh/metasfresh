
DROP FUNCTION IF EXISTS refresh_X_MRP_ProductInfo_MV();
CREATE OR REPLACE FUNCTION refresh_X_MRP_ProductInfo_MV()
  RETURNS void AS
$BODY$
BEGIN

/* 
 * clean the table and recreate it from the view.
 */
DELETE FROM X_MRP_ProductInfo_MV;
INSERT INTO X_MRP_ProductInfo_MV SELECT * FROM X_MRP_ProductInfo_V;

PERFORM refresh_X_MRP_ProductInfo_MV_aux_temp( (now() - interval '4 day')::Date);
PERFORM refresh_X_MRP_ProductInfo_MV_aux_temp( (now() - interval '3 day')::Date);
PERFORM refresh_X_MRP_ProductInfo_MV_aux_temp( (now() - interval '2 day')::Date);
PERFORM refresh_X_MRP_ProductInfo_MV_aux_temp( (now() - interval '1 day')::Date);
PERFORM refresh_X_MRP_ProductInfo_MV_aux_temp( (now() + interval '0 day')::Date);
PERFORM refresh_X_MRP_ProductInfo_MV_aux_temp( (now() + interval '1 day')::Date);
PERFORM refresh_X_MRP_ProductInfo_MV_aux_temp( (now() + interval '2 day')::Date);
PERFORM refresh_X_MRP_ProductInfo_MV_aux_temp( (now() + interval '3 day')::Date);
PERFORM refresh_X_MRP_ProductInfo_MV_aux_temp( (now() + interval '4 day')::Date);


RETURN;
END
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
COMMENT ON FUNCTION refresh_X_MRP_ProductInfo_MV()
  IS 'Task 08329: helper function that re-inserts records into X_MRP_ProductInfo_MV, if there are existing records with another date, but no respective record with the given date. This function is called from refresh_X_MRP_ProductInfo_MV.';
