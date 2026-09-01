-- Rename the PP_Order action added by 5817980 from "Distribute" to "PostCalculation": the process is a
-- post-calculation of the manufacturing order, not merely a distribution.
-- AD_Process owns its Name/Description/Help itself (there is no AD_Element behind it), so the base row
-- and the AD_Process_Trl rows have to be updated directly.

UPDATE AD_Process
SET Classname='org.eevolution.process.PP_Order_PostCalculation',
    Value='PP_Order_PostCalculation',
    Name='Nachberechnung',
    Updated=TO_TIMESTAMP('2026-08-27 09:00:00','YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy=100
WHERE AD_Process_ID=585649
;

-- 5817980 seeded the non-base system languages only; seed every active one incl. the base.
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID,Description,Help,Name,IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy)
SELECT l.AD_Language, t.AD_Process_ID, t.Description, t.Help, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Process t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Process_ID=585649
  AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Carry the new German base Name into the already-existing _Trl rows; en_US is overridden below.
UPDATE AD_Process_Trl SET Name='Nachberechnung', Updated=TO_TIMESTAMP('2026-08-27 09:00:10','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Process_ID=585649 AND AD_Language<>'en_US'
;

-- en_US override: English label
UPDATE AD_Process_Trl SET Name='Post calculation', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-08-27 09:00:11','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language='en_US' AND AD_Process_ID=585649
;

-- de_DE / de_CH: mark as actively translated (same German text as the base)
UPDATE AD_Process_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-08-27 09:00:12','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language='de_CH' AND AD_Process_ID=585649
;

UPDATE AD_Process_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-08-27 09:00:13','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language='de_DE' AND AD_Process_ID=585649
;
