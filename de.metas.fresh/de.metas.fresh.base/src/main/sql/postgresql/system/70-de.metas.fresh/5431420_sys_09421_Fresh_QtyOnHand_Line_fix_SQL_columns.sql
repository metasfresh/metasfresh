
-- 27.10.2015 11:00
-- URL zum Konzept
UPDATE AD_Column SET ColumnSQL='( select OrderBy_ProductName from "de.metas.sp80".OrderBy_ProductGroup v where v.M_Product_ID = Fresh_QtyOnHand_Line.M_Product_ID )',Updated=TO_TIMESTAMP('2015-10-27 11:00:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552539
;

-- 27.10.2015 11:00
-- URL zum Konzept
UPDATE AD_Column SET ColumnSQL='( select OrderBy_ProductGroup from "de.metas.sp80".OrderBy_ProductGroup v where v.M_Product_ID = Fresh_QtyOnHand_Line.M_Product_ID )',Updated=TO_TIMESTAMP('2015-10-27 11:00:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552538
;

