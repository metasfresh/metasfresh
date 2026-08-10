-- Repoint the 'Sprung zu Prognose' launcher (AD_Process 585515) from the standard-window jump to the
-- product-scoped forecast-overlay custom view, so the overlay can show a per-product Menge column.
UPDATE AD_Process
SET Classname='de.metas.ui.web.material.cockpit.forecast.process.WEBUI_QtyDemandQtySupply_Forecast_Launcher',
    Updated=TO_TIMESTAMP('2026-08-10 10:00:00','YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy=100
WHERE AD_Process_ID=585515;
