
-- set all preocesses to not accept rerun
 UPDATE AD_Process
SET AllowProcessRerun = 'N' ;


-- set the menu processes to accept rerun

UPDATE  AD_Process p
SET AllowProcessRerun = 'Y' 
FROM
(
SELECT p.AD_Process_ID from AD_Menu m
JOIN AD_Process p on m.AD_Process_ID = p.AD_Process_ID
WHERE m.Action IN ('P', 'R')
AND m.IsActive = 'Y' AND p.IsActive = 'Y'
) x
WHERE p.AD_Process_ID = x.AD_Process_ID;


-- Activate Bestellung au Auftrag
-- 19.01.2017 13:25
-- URL zum Konzept
UPDATE AD_Process SET AllowProcessReRun='Y',Updated=TO_TIMESTAMP('2017-01-19 13:25:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=193
;

