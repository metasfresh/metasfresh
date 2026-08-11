-- Repoint the 'Sprung zu Prognose' launcher (AD_Process 585515) from the standard-window jump to the
-- product-scoped forecast-overlay custom view, so the overlay can show a per-product Menge column.
-- Value is repointed together with Classname: the old value named the process class
-- de.metas.material.process.QtyDemand_QtySupply_V_to_Forecast, which this change deletes, so leaving it
-- would leave the AD row naming a class that no longer exists. Nothing looks this process up by Value.
UPDATE AD_Process
SET Classname='de.metas.ui.web.material.cockpit.forecast.process.WEBUI_QtyDemandQtySupply_Forecast_Launcher',
    Value='WEBUI_QtyDemandQtySupply_Forecast_Launcher',
    Updated=TO_TIMESTAMP('2026-08-10 10:00:00','YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy=100
WHERE AD_Process_ID=585515;
