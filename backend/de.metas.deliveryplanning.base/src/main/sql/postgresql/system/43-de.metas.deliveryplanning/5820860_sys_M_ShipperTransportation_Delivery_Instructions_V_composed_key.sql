-- M_ShipperTransportation_Delivery_Instructions_V (AD_Table 542287): composed row identity.
--
-- The view returns one row per active allocation of the instruction, and M_ShipperTransportation_ID
-- (585628) is identical across them -- as the single IsKey column it collides every row of one
-- instruction onto the same WebUI row id. A second IsKey='Y' is impossible (the partial unique index
-- ad_column_iskey allows one per table), so the row id is composed from the two IsParent='Y' columns
-- instead: 585628 plus M_Delivery_Planning_ID (585629), unique per active allocation via
-- M_Delivery_Planning_Alloc_Planning_UQ.
UPDATE AD_Column SET IsKey='N', IsParent='Y',
  Updated=TO_TIMESTAMP('2026-08-27 15:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Column_ID=585628
;

UPDATE AD_Column SET IsParent='Y',
  Updated=TO_TIMESTAMP('2026-08-27 15:00:01','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Column_ID=585629
;
