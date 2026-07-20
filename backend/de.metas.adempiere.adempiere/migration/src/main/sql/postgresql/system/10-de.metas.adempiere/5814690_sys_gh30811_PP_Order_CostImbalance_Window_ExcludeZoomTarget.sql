-- The PP_Order cost-imbalance monitor window (542175) is a narrow, read-only,
-- DocStatus-filtered reporting view over PP_Order. Two other windows already
-- expose PP_Order at TabLevel=0 as general-purpose zoom targets (53009, 540328);
-- excluding this monitor from auto-discovered zoom targets avoids it competing
-- with those in a Related-Documents panel for anything with an FK into PP_Order.
UPDATE AD_Window
SET IsExcludeFromZoomTargets='Y', Updated=TO_TIMESTAMP('2026-07-20 14:05:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Window_ID=542175
;
