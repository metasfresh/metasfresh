-- 2026-04-21
-- Remove the temporary kill-switch SysConfig added to disable the C_Order interceptor
-- `scheduleCreatePurchaseOrderFromPurchaseCandidates` as a workaround for
-- https://github.com/metasfresh/me03/issues/29155 / https://github.com/metasfresh/mf15/issues/4039.
-- The underlying bug (isDocComplete hard-coded to true on sales-order re-completion) is fixed in
-- this same PR, so the kill-switch is no longer needed and should be removed everywhere.
DELETE FROM AD_SysConfig
WHERE Name = 'InterceptorEnabled_de.metas.purchasecandidate.interceptor.C_Order#scheduleCreatePurchaseOrderFromPurchaseCandidates'
;
