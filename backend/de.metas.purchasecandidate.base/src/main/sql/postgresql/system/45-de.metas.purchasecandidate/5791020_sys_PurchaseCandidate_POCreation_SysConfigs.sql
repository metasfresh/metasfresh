-- SysConfig entries for C_PurchaseCandidates_GeneratePurchaseOrdersForSalesOrder debouncer
-- These control how long the system waits before creating purchase orders from sales order candidates.

-- AD_SysConfig_ID=541797: Quiet period (ms) — time to wait after the last candidate arrives before processing
INSERT INTO AD_SysConfig (AD_SysConfig_ID, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive,
                          EntityType, Name, Value, Description, ConfigurationLevel)
VALUES (541797, 0, 0,
        TO_TIMESTAMP('2026-03-04 18:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
        TO_TIMESTAMP('2026-03-04 18:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
        'Y', 'de.metas.purchasecandidate',
        'de.metas.purchasecandidate.POCreation.QuietPeriodMillis',
        '10000',
        'Debounce quiet period in ms. After the last purchase candidate is created for a sales order, the system waits this long before creating purchase orders. If a new candidate arrives within this period, the timer resets.',
        'S')
;

-- AD_SysConfig_ID=541798: Max wait (ms) — absolute maximum time from when the first candidate is created
INSERT INTO AD_SysConfig (AD_SysConfig_ID, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive,
                          EntityType, Name, Value, Description, ConfigurationLevel)
VALUES (541798, 0, 0,
        TO_TIMESTAMP('2026-03-04 18:00:01', 'YYYY-MM-DD HH24:MI:SS'), 100,
        TO_TIMESTAMP('2026-03-04 18:00:01', 'YYYY-MM-DD HH24:MI:SS'), 100,
        'Y', 'de.metas.purchasecandidate',
        'de.metas.purchasecandidate.POCreation.MaxWaitMillis',
        '60000',
        'Maximum total wait time in ms from when the first candidate is created. Ensures purchase orders are created even if candidates keep arriving. After this time, all collected candidates are processed regardless of the quiet period.',
        'S')
;
