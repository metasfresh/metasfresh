-- Process: WEBUI_Picking_PickQtyToComputedHU(de.metas.ui.web.picking.pickingslot.process.WEBUI_Picking_PickQtyToComputedHU)
-- ParameterName: QtyCUsPerTU
-- 2024-12-16T13:01:02.678Z
UPDATE AD_Process_Para SET AD_Element_ID=542492, ColumnName='QtyCUsPerTU', Description='Menge der CUs pro Einzelgebinde (normalerweise TU)', EntityType='de.metas.handlingunits', Help=NULL, Name='Menge CU/TU',Updated=TO_TIMESTAMP('2024-12-16 13:01:02.677000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_Para_ID=542421
;

-- 2024-12-16T13:01:02.679Z
UPDATE AD_Process_Para_Trl trl SET Description='Menge der CUs pro Einzelgebinde (normalerweise TU)',Help=NULL,Name='Menge CU/TU' WHERE AD_Process_Para_ID=542421 AND AD_Language='de_DE'
;

-- Process: WEBUI_Picking_ForcePickToComputedHU(de.metas.ui.web.picking.pickingslot.process.WEBUI_Picking_ForcePickToComputedHU)
-- ParameterName: QtyCUsPerTU
-- 2024-12-16T13:02:01.716Z
UPDATE AD_Process_Para SET AD_Element_ID=542492, ColumnName='QtyCUsPerTU', Description='Menge der CUs pro Einzelgebinde (normalerweise TU)', EntityType='de.metas.handlingunits', Help=NULL, Name='Menge CU/TU',Updated=TO_TIMESTAMP('2024-12-16 13:02:01.716000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_Para_ID=542424
;

-- 2024-12-16T13:02:01.718Z
UPDATE AD_Process_Para_Trl trl SET Description='Menge der CUs pro Einzelgebinde (normalerweise TU)',Help=NULL,Name='Menge CU/TU' WHERE AD_Process_Para_ID=542424 AND AD_Language='de_DE'
;

