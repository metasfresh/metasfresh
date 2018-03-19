
--
-- remove the old InOutGenerate process
--
DELETE FROM AD_Menu WHERE ad_process_id=118;

-- 2017-09-27T12:20:05.469
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Process_Trl WHERE AD_Process_ID=118
;

-- 2017-09-27T12:20:05.471
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Process WHERE AD_Process_ID=118
;

--
-- Removed M_ShipmentSchedule.QtyDeliverable
--
DELETE FROM ad_ui_element WHERE AD_Field_ID IN (SELECt AD_Field_ID FROM AD_Field WHERE AD_Column_ID IN (select AD_Column_ID FROM AD_Column WHERE AD_Element_ID=501930));
DELETE FROM AD_Field WHERE AD_Column_ID IN (select AD_Column_ID FROM AD_Column WHERE AD_Element_ID=501930);
DELETE FROM AD_Column WHERE AD_Element_ID=501930;
DELETE FROM AD_Element WHERE AD_Element_ID=501930;
SELECT public.db_alter_table('M_ShipmentSchedule','ALTER Table M_ShipmentSchedule DROP COLUMN IF EXISTS QtyDeliverable;');
