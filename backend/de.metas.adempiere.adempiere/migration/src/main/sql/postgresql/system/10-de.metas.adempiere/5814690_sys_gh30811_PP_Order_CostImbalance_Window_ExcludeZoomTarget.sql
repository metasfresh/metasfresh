-- Two windows already expose PP_Order at TabLevel=0 as general-purpose zoom targets (53009, 540328).
-- Excluding this narrow, DocStatus-filtered monitor keeps it from competing with them in a
-- Related-Documents panel.
UPDATE AD_Window
SET IsExcludeFromZoomTargets='Y', Updated=TO_TIMESTAMP('2026-07-20 14:05:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Window_ID=542175
;
