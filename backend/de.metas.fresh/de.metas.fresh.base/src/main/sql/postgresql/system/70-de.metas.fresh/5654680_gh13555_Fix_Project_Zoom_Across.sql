-- Window: Projekt (webUI), InternalName=Projekt (webUI)
-- 2022-09-06T09:59:29.630Z
UPDATE AD_Window SET IsEnableRemoteCacheInvalidation='Y', IsExcludeFromZoomTargets='Y',Updated=TO_TIMESTAMP('2022-09-06 10:59:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Window_ID=540668
;

UPDATE ad_window SET zoomintopriority = 1 WHERE ad_window_id=540668
;

-- Table: C_Project
-- 2022-09-06T14:33:57.260Z
UPDATE AD_Table
SET AD_Window_ID=540668, Updated=TO_TIMESTAMP('2022-09-06 15:33:56', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Table_ID = 203
  AND EXISTS(SELECT 1 FROM ad_window WHERE ad_window_id = 540668)
;

-- Table: C_Project
-- 2022-09-06T14:48:33.627Z
UPDATE AD_Table
SET PO_Window_ID=540668, Updated=TO_TIMESTAMP('2022-09-06 15:48:33', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Table_ID = 203
  AND EXISTS(SELECT 1 FROM ad_window WHERE ad_window_id = 540668)
;

-- Window: Projekt (Verkauf), InternalName=286
-- 2022-09-06T14:58:40.334Z
UPDATE AD_Window SET IsExcludeFromZoomTargets='Y',Updated=TO_TIMESTAMP('2022-09-06 15:58:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Window_ID=286
;

-- Window: Seminar, InternalName=Seminar
-- 2022-09-06T15:00:10.527Z
UPDATE AD_Window SET IsExcludeFromZoomTargets='Y',Updated=TO_TIMESTAMP('2022-09-06 16:00:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Window_ID=540680
;

