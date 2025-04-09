-- Column: M_ProductPrice.M_PriceList_Version_ID
-- Column: M_ProductPrice.M_PriceList_Version_ID
-- 2025-04-09T09:14:57.483Z
UPDATE AD_Column SET IsExcludeFromZoomTargets='N', IsGenericZoomKeyColumn='Y', IsGenericZoomOrigin='Y', IsUpdateable='N',Updated=TO_TIMESTAMP('2025-04-09 09:14:57.483000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=2760
;

-- Tab: Produkt -> Preis
-- Table: M_ProductPrice
-- Tab: Produkt(140,D) -> Preis
-- Table: M_ProductPrice
-- 2025-04-09T09:19:15.689Z
UPDATE AD_Tab SET IsGenericZoomTarget='Y',Updated=TO_TIMESTAMP('2025-04-09 09:19:15.689000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Tab_ID=183
;

-- Reference: Pricelist Version newest to oldest
-- Table: M_PriceList_Version
-- Key: M_PriceList_Version.M_PriceList_Version_ID
-- 2025-04-09T09:24:09.306Z
UPDATE AD_Ref_Table SET AD_Window_ID=540321,Updated=TO_TIMESTAMP('2025-04-09 09:24:09.305000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Reference_ID=541033
;

