-- WEBUI_M_ReceiptSchedule_ReceiveCUs_WithParam (AD_Process 540765): the quantity parameter is not a quantity per TU (there is no TU when receiving loose CUs).
-- Move it from element "Menge pro TU" (QtyCUsPerTU, 542492) to "Menge CU" (QtyCUsReceived, 585563; created in 5831320). That element is also used by the
-- production receipt dialog (WEBUI_PP_Order_Receipt); QtyCUsPerTU keeps its caption for the dialogs that really ask for a quantity per TU.
-- Caution: AD_Process_Para.ColumnName is not synced from the element, so it is set here as well; the Java process reads QtyCUsReceived and falls back to QtyCUsPerTU.

UPDATE AD_Process_Para
SET AD_Element_ID=585563, ColumnName='QtyCUsReceived', Updated=TO_TIMESTAMP('2026-10-23 16:10:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Process_Para_ID=541163 AND AD_Process_ID=540765;

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585563);
