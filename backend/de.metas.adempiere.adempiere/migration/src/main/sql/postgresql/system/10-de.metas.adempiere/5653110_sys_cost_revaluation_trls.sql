-- Element: M_CostRevaluation_ID
-- 2022-08-25T14:18:00.798Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Kosten Neubewertung', PrintName='Kosten Neubewertung',Updated=TO_TIMESTAMP('2022-08-25 17:18:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581160 AND AD_Language='de_CH'
;

-- 2022-08-25T14:18:00.890Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581160,'de_CH') 
;

-- Element: M_CostRevaluation_ID
-- 2022-08-25T14:18:06.135Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Kosten Neubewertung', PrintName='Kosten Neubewertung',Updated=TO_TIMESTAMP('2022-08-25 17:18:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581160 AND AD_Language='de_DE'
;

-- 2022-08-25T14:18:06.137Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581160,'de_DE') 
;

-- 2022-08-25T14:18:06.178Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581160,'de_DE') 
;

-- 2022-08-25T14:18:06.185Z
UPDATE AD_Column SET ColumnName='M_CostRevaluation_ID', Name='Kosten Neubewertung', Description=NULL, Help=NULL WHERE AD_Element_ID=581160
;

-- 2022-08-25T14:18:06.188Z
UPDATE AD_Process_Para SET ColumnName='M_CostRevaluation_ID', Name='Kosten Neubewertung', Description=NULL, Help=NULL, AD_Element_ID=581160 WHERE UPPER(ColumnName)='M_COSTREVALUATION_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-08-25T14:18:06.200Z
UPDATE AD_Process_Para SET ColumnName='M_CostRevaluation_ID', Name='Kosten Neubewertung', Description=NULL, Help=NULL WHERE AD_Element_ID=581160 AND IsCentrallyMaintained='Y'
;

-- 2022-08-25T14:18:06.201Z
UPDATE AD_Field SET Name='Kosten Neubewertung', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=581160) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 581160)
;

-- 2022-08-25T14:18:06.464Z
UPDATE AD_PrintFormatItem pi SET PrintName='Kosten Neubewertung', Name='Kosten Neubewertung' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=581160)
;

-- 2022-08-25T14:18:06.468Z
UPDATE AD_Tab SET Name='Kosten Neubewertung', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 581160
;

-- 2022-08-25T14:18:06.471Z
UPDATE AD_WINDOW SET Name='Kosten Neubewertung', Description=NULL, Help=NULL WHERE AD_Element_ID = 581160
;

-- 2022-08-25T14:18:06.472Z
UPDATE AD_Menu SET   Name = 'Kosten Neubewertung', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 581160
;

-- Element: M_CostRevaluation_ID
-- 2022-08-25T14:18:08.732Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-08-25 17:18:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581160 AND AD_Language='en_US'
;

-- 2022-08-25T14:18:08.734Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581160,'en_US') 
;

-- Element: M_CostRevaluationLine_ID
-- 2022-08-25T14:19:17.791Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Kosten Neubewertung Position', PrintName='Kosten Neubewertung Position',Updated=TO_TIMESTAMP('2022-08-25 17:19:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581162 AND AD_Language='de_CH'
;

-- 2022-08-25T14:19:17.792Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581162,'de_CH') 
;

-- Element: M_CostRevaluationLine_ID
-- 2022-08-25T14:19:23.805Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Kosten Neubewertung Position', PrintName='Kosten Neubewertung Position',Updated=TO_TIMESTAMP('2022-08-25 17:19:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581162 AND AD_Language='de_DE'
;

-- 2022-08-25T14:19:23.806Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581162,'de_DE') 
;

-- 2022-08-25T14:19:23.813Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581162,'de_DE') 
;

-- 2022-08-25T14:19:23.814Z
UPDATE AD_Column SET ColumnName='M_CostRevaluationLine_ID', Name='Kosten Neubewertung Position', Description=NULL, Help=NULL WHERE AD_Element_ID=581162
;

-- 2022-08-25T14:19:23.815Z
UPDATE AD_Process_Para SET ColumnName='M_CostRevaluationLine_ID', Name='Kosten Neubewertung Position', Description=NULL, Help=NULL, AD_Element_ID=581162 WHERE UPPER(ColumnName)='M_COSTREVALUATIONLINE_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-08-25T14:19:23.816Z
UPDATE AD_Process_Para SET ColumnName='M_CostRevaluationLine_ID', Name='Kosten Neubewertung Position', Description=NULL, Help=NULL WHERE AD_Element_ID=581162 AND IsCentrallyMaintained='Y'
;

-- 2022-08-25T14:19:23.816Z
UPDATE AD_Field SET Name='Kosten Neubewertung Position', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=581162) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 581162)
;

-- 2022-08-25T14:19:23.834Z
UPDATE AD_PrintFormatItem pi SET PrintName='Kosten Neubewertung Position', Name='Kosten Neubewertung Position' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=581162)
;

-- 2022-08-25T14:19:23.835Z
UPDATE AD_Tab SET Name='Kosten Neubewertung Position', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 581162
;

-- 2022-08-25T14:19:23.837Z
UPDATE AD_WINDOW SET Name='Kosten Neubewertung Position', Description=NULL, Help=NULL WHERE AD_Element_ID = 581162
;

-- 2022-08-25T14:19:23.838Z
UPDATE AD_Menu SET   Name = 'Kosten Neubewertung Position', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 581162
;

-- Element: M_CostRevaluationLine_ID
-- 2022-08-25T14:19:25.931Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-08-25 17:19:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581162 AND AD_Language='en_US'
;

-- 2022-08-25T14:19:25.932Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581162,'en_US') 
;

-- Element: IsRevaluated
-- 2022-08-25T14:20:55.569Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Wert berichtigt', PrintName='Wert berichtigt',Updated=TO_TIMESTAMP('2022-08-25 17:20:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581374 AND AD_Language='de_CH'
;

-- 2022-08-25T14:20:55.571Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581374,'de_CH') 
;

-- Element: IsRevaluated
-- 2022-08-25T14:20:58.880Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Wert berichtigt', PrintName='Wert berichtigt',Updated=TO_TIMESTAMP('2022-08-25 17:20:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581374 AND AD_Language='de_DE'
;

-- 2022-08-25T14:20:58.882Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581374,'de_DE') 
;

-- 2022-08-25T14:20:58.895Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581374,'de_DE') 
;

-- 2022-08-25T14:20:58.898Z
UPDATE AD_Column SET ColumnName='IsRevaluated', Name='Wert berichtigt', Description=NULL, Help=NULL WHERE AD_Element_ID=581374
;

-- 2022-08-25T14:20:58.899Z
UPDATE AD_Process_Para SET ColumnName='IsRevaluated', Name='Wert berichtigt', Description=NULL, Help=NULL, AD_Element_ID=581374 WHERE UPPER(ColumnName)='ISREVALUATED' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-08-25T14:20:58.901Z
UPDATE AD_Process_Para SET ColumnName='IsRevaluated', Name='Wert berichtigt', Description=NULL, Help=NULL WHERE AD_Element_ID=581374 AND IsCentrallyMaintained='Y'
;

-- 2022-08-25T14:20:58.901Z
UPDATE AD_Field SET Name='Wert berichtigt', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=581374) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 581374)
;

-- 2022-08-25T14:20:58.928Z
UPDATE AD_PrintFormatItem pi SET PrintName='Wert berichtigt', Name='Wert berichtigt' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=581374)
;

-- 2022-08-25T14:20:58.930Z
UPDATE AD_Tab SET Name='Wert berichtigt', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 581374
;

-- 2022-08-25T14:20:58.932Z
UPDATE AD_WINDOW SET Name='Wert berichtigt', Description=NULL, Help=NULL WHERE AD_Element_ID = 581374
;

-- 2022-08-25T14:20:58.933Z
UPDATE AD_Menu SET   Name = 'Wert berichtigt', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 581374
;

-- Element: IsRevaluated
-- 2022-08-25T14:21:03.253Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-08-25 17:21:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581374 AND AD_Language='en_US'
;

-- 2022-08-25T14:21:03.254Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581374,'en_US') 
;

-- Element: DeltaAmt
-- 2022-08-25T14:21:26.232Z
UPDATE AD_Element_Trl SET Description='', IsTranslated='Y', Name='Deltabetrag', PrintName='Deltabetrag',Updated=TO_TIMESTAMP('2022-08-25 17:21:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=2840 AND AD_Language='it_CH'
;

-- 2022-08-25T14:21:26.238Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(2840,'it_CH') 
;

-- Element: DeltaAmt
-- 2022-08-25T14:21:32.154Z
UPDATE AD_Element_Trl SET Description='', IsTranslated='Y', Name='Deltabetrag', PrintName='Deltabetrag',Updated=TO_TIMESTAMP('2022-08-25 17:21:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=2840 AND AD_Language='de_CH'
;

-- 2022-08-25T14:21:32.156Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(2840,'de_CH') 
;

-- Element: DeltaAmt
-- 2022-08-25T14:21:41.752Z
UPDATE AD_Element_Trl SET Description='', IsTranslated='Y', Name='Deltabetrag', PrintName='Deltabetrag',Updated=TO_TIMESTAMP('2022-08-25 17:21:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=2840 AND AD_Language='de_DE'
;

-- 2022-08-25T14:21:41.753Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(2840,'de_DE') 
;

-- 2022-08-25T14:21:41.760Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(2840,'de_DE') 
;

-- 2022-08-25T14:21:41.764Z
UPDATE AD_Column SET ColumnName='DeltaAmt', Name='Deltabetrag', Description='', Help=NULL WHERE AD_Element_ID=2840
;

-- 2022-08-25T14:21:41.765Z
UPDATE AD_Process_Para SET ColumnName='DeltaAmt', Name='Deltabetrag', Description='', Help=NULL, AD_Element_ID=2840 WHERE UPPER(ColumnName)='DELTAAMT' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-08-25T14:21:41.766Z
UPDATE AD_Process_Para SET ColumnName='DeltaAmt', Name='Deltabetrag', Description='', Help=NULL WHERE AD_Element_ID=2840 AND IsCentrallyMaintained='Y'
;

-- 2022-08-25T14:21:41.766Z
UPDATE AD_Field SET Name='Deltabetrag', Description='', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=2840) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 2840)
;

-- 2022-08-25T14:21:41.784Z
UPDATE AD_PrintFormatItem pi SET PrintName='Deltabetrag', Name='Deltabetrag' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=2840)
;

-- 2022-08-25T14:21:41.785Z
UPDATE AD_Tab SET Name='Deltabetrag', Description='', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 2840
;

-- 2022-08-25T14:21:41.786Z
UPDATE AD_WINDOW SET Name='Deltabetrag', Description='', Help=NULL WHERE AD_Element_ID = 2840
;

-- 2022-08-25T14:21:41.786Z
UPDATE AD_Menu SET   Name = 'Deltabetrag', Description = '', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 2840
;

-- Process: M_CostRevaluation_Run(de.metas.costrevaluation.process.M_CostRevaluation_Run)
-- 2022-08-25T14:22:35.125Z
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='Wertberichtigung durchführen',Updated=TO_TIMESTAMP('2022-08-25 17:22:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=585097
;

-- Value: M_CostRevaluation_Run
-- Classname: de.metas.costrevaluation.process.M_CostRevaluation_Run
-- 2022-08-25T14:22:37.780Z
UPDATE AD_Process SET Description=NULL, Help=NULL, Name='Wertberichtigung durchführen',Updated=TO_TIMESTAMP('2022-08-25 17:22:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=585097
;

-- Process: M_CostRevaluation_Run(de.metas.costrevaluation.process.M_CostRevaluation_Run)
-- 2022-08-25T14:22:37.739Z
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='Wertberichtigung durchführen',Updated=TO_TIMESTAMP('2022-08-25 17:22:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=585097
;

-- Process: M_CostRevaluation_Run(de.metas.costrevaluation.process.M_CostRevaluation_Run)
-- 2022-08-25T14:22:41.719Z
UPDATE AD_Process_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-08-25 17:22:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585097
;

-- Element: M_CostRevaluation_Detail_ID
-- 2022-08-25T14:23:34.291Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Kosten Wertberichtigung Details', PrintName='Kosten Wertberichtigung Details',Updated=TO_TIMESTAMP('2022-08-25 17:23:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581340 AND AD_Language='de_CH'
;

-- 2022-08-25T14:23:34.291Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581340,'de_CH') 
;

-- Element: M_CostRevaluation_Detail_ID
-- 2022-08-25T14:23:38.746Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Kosten Wertberichtigung Details', PrintName='Kosten Wertberichtigung Details',Updated=TO_TIMESTAMP('2022-08-25 17:23:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581340 AND AD_Language='de_DE'
;

-- 2022-08-25T14:23:38.747Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581340,'de_DE') 
;

-- 2022-08-25T14:23:38.754Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581340,'de_DE') 
;

-- 2022-08-25T14:23:38.755Z
UPDATE AD_Column SET ColumnName='M_CostRevaluation_Detail_ID', Name='Kosten Wertberichtigung Details', Description=NULL, Help=NULL WHERE AD_Element_ID=581340
;

-- 2022-08-25T14:23:38.755Z
UPDATE AD_Process_Para SET ColumnName='M_CostRevaluation_Detail_ID', Name='Kosten Wertberichtigung Details', Description=NULL, Help=NULL, AD_Element_ID=581340 WHERE UPPER(ColumnName)='M_COSTREVALUATION_DETAIL_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-08-25T14:23:38.756Z
UPDATE AD_Process_Para SET ColumnName='M_CostRevaluation_Detail_ID', Name='Kosten Wertberichtigung Details', Description=NULL, Help=NULL WHERE AD_Element_ID=581340 AND IsCentrallyMaintained='Y'
;

-- 2022-08-25T14:23:38.756Z
UPDATE AD_Field SET Name='Kosten Wertberichtigung Details', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=581340) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 581340)
;

-- 2022-08-25T14:23:38.770Z
UPDATE AD_PrintFormatItem pi SET PrintName='Kosten Wertberichtigung Details', Name='Kosten Wertberichtigung Details' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=581340)
;

-- 2022-08-25T14:23:38.771Z
UPDATE AD_Tab SET Name='Kosten Wertberichtigung Details', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 581340
;

-- 2022-08-25T14:23:38.772Z
UPDATE AD_WINDOW SET Name='Kosten Wertberichtigung Details', Description=NULL, Help=NULL WHERE AD_Element_ID = 581340
;

-- 2022-08-25T14:23:38.775Z
UPDATE AD_Menu SET   Name = 'Kosten Wertberichtigung Details', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 581340
;

-- Element: M_CostRevaluation_Detail_ID
-- 2022-08-25T14:23:40.218Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-08-25 17:23:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581340 AND AD_Language='en_US'
;

-- 2022-08-25T14:23:40.218Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581340,'en_US') 
;

-- Value: M_CostRevaluation.DeleteLinesFirstError
-- 2022-08-25T14:25:47.003Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545151,0,TO_TIMESTAMP('2022-08-25 17:25:46','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Bitte zuerst die Positionen löschen.','E',TO_TIMESTAMP('2022-08-25 17:25:46','YYYY-MM-DD HH24:MI:SS'),100,'M_CostRevaluation.DeleteLinesFirstError')
;

-- 2022-08-25T14:25:47.008Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545151 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: M_CostRevaluation.DeleteLinesFirstError
-- 2022-08-25T14:25:51.322Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-08-25 17:25:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545151
;

-- Value: M_CostRevaluation.DeleteLinesFirstError
-- 2022-08-25T14:25:59.214Z
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Please delete the lines first.',Updated=TO_TIMESTAMP('2022-08-25 17:25:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545151
;

-- Value: M_CostRevaluation.DeleteLinesFirstError
-- 2022-08-25T14:26:02.174Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-08-25 17:26:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545151
;

-- Value: CostingMethodHandler.RevaluatingAnotherRevaluationIsNotSupported
-- 2022-08-25T14:32:34.803Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545152,0,TO_TIMESTAMP('2022-08-25 17:32:34','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Evaluating another revaluation is not supported','E',TO_TIMESTAMP('2022-08-25 17:32:34','YYYY-MM-DD HH24:MI:SS'),100,'CostingMethodHandler.RevaluatingAnotherRevaluationIsNotSupported')
;

-- 2022-08-25T14:32:34.806Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545152 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

