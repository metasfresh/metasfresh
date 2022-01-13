-- 2021-07-15T07:20:33.698Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Window_ID=541176
;

-- 2021-07-15T07:20:33.737Z
-- URL zum Konzept
DELETE FROM  AD_Window_Trl WHERE AD_Window_ID=541176
;

-- 2021-07-15T07:20:33.986Z
-- URL zum Konzept
DELETE FROM AD_Window WHERE AD_Window_ID=541176
;

-- 2021-07-15T07:21:08.527Z
-- URL zum Konzept
DELETE FROM  AD_Table_Trl WHERE AD_Table_ID=541748
;

-- 2021-07-15T07:21:08.739Z
-- URL zum Konzept
DELETE FROM AD_Table WHERE AD_Table_ID=541748
;

-- 2021-07-15T07:21:14.698Z
-- URL zum Konzept
DELETE FROM  AD_Element_Trl WHERE AD_Element_ID=579492
;

-- 2021-07-15T07:21:14.915Z
-- URL zum Konzept
DELETE FROM AD_Element WHERE AD_Element_ID=579492
;

delete from ad_sequence where name='PP_Order_RepairService';

drop table if exists PP_Order_RepairService;

drop SEQUENCE if EXISTS pp_order_repairservice_seq;

