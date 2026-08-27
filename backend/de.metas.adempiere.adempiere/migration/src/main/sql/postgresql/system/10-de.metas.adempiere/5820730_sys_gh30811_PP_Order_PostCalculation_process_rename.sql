-- gh30811 Manufacturing costing — rename the PP_Order action introduced by
-- 5817980_sys_gh30811_PP_Order_Distribute_process.sql: the process is a post-calculation of the
-- manufacturing order (it recomputes the order's costs and discharges the WIP residual), so both the
-- Java class and the AD_Process identity move from "Distribute" to "PostCalculation".
-- AD_Process owns its Name/Description/Help itself (no AD_Element behind it), so the base row and the
-- AD_Process_Trl rows have to be updated directly. The process has no AD_Menu entry (it is surfaced
-- via AD_Table_Process only), so there is no menu caption to re-sync.

-- AD_Process: new Java class, Value and German base Name.
UPDATE AD_Process
SET Classname='org.eevolution.process.PP_Order_PostCalculation',
    Value='PP_Order_PostCalculation',
    Name='Nachberechnung',
    Updated=TO_TIMESTAMP('2026-08-27 09:00:00','YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy=100
WHERE AD_Process_ID=585649
;

-- 5817980 seeded AD_Process_Trl for the non-base system languages only; the base language (de_DE) was
-- left without a row. Seed every active system language incl. the base, matching sibling script
-- 5817970_sys_gh30811_PP_Cost_Collector_CostDifferenceDistribution_RefList.sql.
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID,Description,Help,Name,IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy)
SELECT l.AD_Language, t.AD_Process_ID, t.Description, t.Help, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Process t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Process_ID=585649
  AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Carry the new German base Name into the already-existing _Trl rows (de_CH keeps the German text,
-- fr_CH keeps it as an untranslated fallback). en_US is overridden below.
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
