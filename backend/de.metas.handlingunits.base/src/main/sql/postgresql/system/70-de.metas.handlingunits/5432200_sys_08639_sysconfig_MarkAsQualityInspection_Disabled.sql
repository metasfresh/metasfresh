-- 02.11.2015 12:44
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,540910,'S',TO_TIMESTAMP('2015-11-02 12:44:54','YYYY-MM-DD HH24:MI:SS'),100,'Flag used to disable the "Mark for Quality Inspection" (i.e. Waschprobe) button. See task 08639.','de.metas.handlingunits','Y','de.metas.handlingunits.client.terminal.editor.view.HUEditorPanel.MarkAsQualityInspection.Disabled',TO_TIMESTAMP('2015-11-02 12:44:54','YYYY-MM-DD HH24:MI:SS'),100,'Y')
;

-- NOTE: to be enabled manually
-- UPDATE AD_SysConfig SET Value='N',Updated=TO_TIMESTAMP('2015-11-02 12:45:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=540910;

