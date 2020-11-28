
-- 2017-12-11T16:28:25.924
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnSQL='( select OrderBy_ProductGroup from "de.metas.fresh".OrderBy_ProductGroup v where v.M_Product_ID = Fresh_QtyOnHand_Line.M_Product_ID )',Updated=TO_TIMESTAMP('2017-12-11 16:28:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552538
;

-- 2017-12-11T16:28:39.658
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnSQL='( select OrderBy_ProductName from "de.metas.fresh".OrderBy_ProductGroup v where v.M_Product_ID = Fresh_QtyOnHand_Line.M_Product_ID )',Updated=TO_TIMESTAMP('2017-12-11 16:28:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552539
;
