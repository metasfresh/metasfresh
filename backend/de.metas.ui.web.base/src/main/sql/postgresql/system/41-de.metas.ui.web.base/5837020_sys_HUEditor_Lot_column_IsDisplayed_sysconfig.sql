-- Seed the SysConfig that gates the "Lot No" column in the HU Editor (desktop WebUI, window 540189).
-- Ships OFF by default for all instances; enable per instance by setting the value to 'Y'.
-- The column exposes the existing M_HU.Lot virtual column (which the HU Editor "Lot-Nr." filter uses);
-- the key follows the HUEditorRow 'Lot' @ViewColumn displayedSysConfigPrefix convention
--   (de.metas.ui.web.handlingunits.field.<fieldName>.IsDisplayed): the same key both displays the
--   column and triggers its value population in SqlHUEditorViewRepository.
INSERT INTO AD_SysConfig (AD_SysConfig_ID,AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,Name,Value,Description,ConfigurationLevel,EntityType)
SELECT 541867 /*From ID Server*/,0,0,'Y',
       TO_TIMESTAMP('2026-11-23 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,
       TO_TIMESTAMP('2026-11-23 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,
       'de.metas.ui.web.handlingunits.field.Lot.IsDisplayed','N',
       'Show the Lot No column in the HU Editor (desktop WebUI, window 540189). Default N (hidden); set to Y per instance to display it.',
       'S','de.metas.ui.web'
WHERE NOT EXISTS (SELECT 1 FROM AD_SysConfig WHERE Name='de.metas.ui.web.handlingunits.field.Lot.IsDisplayed')
;
