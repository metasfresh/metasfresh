-- Delete C_OrderLine_OpenMaterialCockpit process (AD_Process_ID=585570)
-- The material cockpit opening logic has been integrated into WEBUI_C_OrderLineSO_Launch_HUEditor
-- which dynamically discovers custom DB functions to decide whether to open the HU Editor or Material Cockpit.

DELETE FROM AD_Table_Process WHERE AD_Process_ID = 585570;
DELETE FROM AD_Process_Trl WHERE AD_Process_ID = 585570;
DELETE FROM AD_Process WHERE AD_Process_ID = 585570;
