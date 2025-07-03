-- 2022-11-14T07:57:16.807Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Weighing Specifications', PrintName='Weighing Specifications',Updated=TO_TIMESTAMP('2022-11-14 09:57:16.804','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=581666 AND AD_Language='en_US'
;

-- 2022-11-14T07:57:16.818Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581666,'en_US') 
;

-- 2022-11-14T07:57:28.804Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Wägespezifikationen', PrintName='Wägespezifikationen',Updated=TO_TIMESTAMP('2022-11-14 09:57:28.802','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=581666 AND AD_Language='de_CH'
;

-- 2022-11-14T07:57:28.807Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581666,'de_CH') 
;

-- 2022-11-14T07:57:31.131Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Wägespezifikationen', PrintName='Wägespezifikationen',Updated=TO_TIMESTAMP('2022-11-14 09:57:31.129','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=581666 AND AD_Language='de_DE'
;

-- 2022-11-14T07:57:31.133Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581666,'de_DE') 
;

-- 2022-11-14T07:57:31.141Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581666,'de_DE') 
;

-- 2022-11-14T07:57:31.144Z
UPDATE AD_Column SET ColumnName='PP_Weighting_Spec_ID', Name='Wägespezifikationen', Description=NULL, Help=NULL WHERE AD_Element_ID=581666
;

-- 2022-11-14T07:57:31.146Z
UPDATE AD_Process_Para SET ColumnName='PP_Weighting_Spec_ID', Name='Wägespezifikationen', Description=NULL, Help=NULL, AD_Element_ID=581666 WHERE UPPER(ColumnName)='PP_WEIGHTING_SPEC_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-11-14T07:57:31.154Z
UPDATE AD_Process_Para SET ColumnName='PP_Weighting_Spec_ID', Name='Wägespezifikationen', Description=NULL, Help=NULL WHERE AD_Element_ID=581666 AND IsCentrallyMaintained='Y'
;

-- 2022-11-14T07:57:31.155Z
UPDATE AD_Field SET Name='Wägespezifikationen', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=581666) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 581666)
;

-- 2022-11-14T07:57:31.181Z
UPDATE AD_PrintFormatItem pi SET PrintName='Wägespezifikationen', Name='Wägespezifikationen' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=581666)
;

-- 2022-11-14T07:57:31.183Z
UPDATE AD_Tab SET Name='Wägespezifikationen', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 581666
;

-- 2022-11-14T07:57:31.187Z
UPDATE AD_WINDOW SET Name='Wägespezifikationen', Description=NULL, Help=NULL WHERE AD_Element_ID = 581666
;

-- 2022-11-14T07:57:31.190Z
UPDATE AD_Menu SET   Name = 'Wägespezifikationen', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 581666
;

-- 2022-11-14T07:58:21.723Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Erforderliche Gewichtskontrollen', PrintName='Erforderliche Gewichtskontrollen',Updated=TO_TIMESTAMP('2022-11-14 09:58:21.721','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=581662 AND AD_Language='de_CH'
;

-- 2022-11-14T07:58:21.725Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581662,'de_CH') 
;

-- 2022-11-14T07:58:25.893Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Erforderliche Gewichtskontrollen', PrintName='Erforderliche Gewichtskontrollen',Updated=TO_TIMESTAMP('2022-11-14 09:58:25.891','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=581662 AND AD_Language='de_DE'
;

-- 2022-11-14T07:58:25.896Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581662,'de_DE') 
;

-- 2022-11-14T07:58:25.910Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581662,'de_DE') 
;

-- 2022-11-14T07:58:25.913Z
UPDATE AD_Column SET ColumnName='WeightChecksRequired', Name='Erforderliche Gewichtskontrollen', Description=NULL, Help=NULL WHERE AD_Element_ID=581662
;

-- 2022-11-14T07:58:25.914Z
UPDATE AD_Process_Para SET ColumnName='WeightChecksRequired', Name='Erforderliche Gewichtskontrollen', Description=NULL, Help=NULL, AD_Element_ID=581662 WHERE UPPER(ColumnName)='WEIGHTCHECKSREQUIRED' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-11-14T07:58:25.916Z
UPDATE AD_Process_Para SET ColumnName='WeightChecksRequired', Name='Erforderliche Gewichtskontrollen', Description=NULL, Help=NULL WHERE AD_Element_ID=581662 AND IsCentrallyMaintained='Y'
;

-- 2022-11-14T07:58:25.917Z
UPDATE AD_Field SET Name='Erforderliche Gewichtskontrollen', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=581662) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 581662)
;

-- 2022-11-14T07:58:25.929Z
UPDATE AD_PrintFormatItem pi SET PrintName='Erforderliche Gewichtskontrollen', Name='Erforderliche Gewichtskontrollen' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=581662)
;

-- 2022-11-14T07:58:25.931Z
UPDATE AD_Tab SET Name='Erforderliche Gewichtskontrollen', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 581662
;

-- 2022-11-14T07:58:25.933Z
UPDATE AD_WINDOW SET Name='Erforderliche Gewichtskontrollen', Description=NULL, Help=NULL WHERE AD_Element_ID = 581662
;

-- 2022-11-14T07:58:25.935Z
UPDATE AD_Menu SET   Name = 'Erforderliche Gewichtskontrollen', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 581662
;

-- 2022-11-14T07:58:27.667Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-11-14 09:58:27.665','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=581662 AND AD_Language='en_US'
;

-- 2022-11-14T07:58:27.669Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581662,'en_US') 
;

-- 2022-11-14T07:59:03.069Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Weighing Run', PrintName='Weighing Run',Updated=TO_TIMESTAMP('2022-11-14 09:59:03.067','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=581658 AND AD_Language='en_US'
;

-- 2022-11-14T07:59:03.071Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581658,'en_US') 
;

-- 2022-11-14T07:59:12.208Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Wägelauf', PrintName='Wägelauf',Updated=TO_TIMESTAMP('2022-11-14 09:59:12.206','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=581658 AND AD_Language='de_DE'
;

-- 2022-11-14T07:59:12.210Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581658,'de_DE') 
;

-- 2022-11-14T07:59:12.217Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581658,'de_DE') 
;

-- 2022-11-14T07:59:12.218Z
UPDATE AD_Column SET ColumnName='PP_Order_Weighting_Run_ID', Name='Wägelauf', Description=NULL, Help=NULL WHERE AD_Element_ID=581658
;

-- 2022-11-14T07:59:12.219Z
UPDATE AD_Process_Para SET ColumnName='PP_Order_Weighting_Run_ID', Name='Wägelauf', Description=NULL, Help=NULL, AD_Element_ID=581658 WHERE UPPER(ColumnName)='PP_ORDER_WEIGHTING_RUN_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-11-14T07:59:12.220Z
UPDATE AD_Process_Para SET ColumnName='PP_Order_Weighting_Run_ID', Name='Wägelauf', Description=NULL, Help=NULL WHERE AD_Element_ID=581658 AND IsCentrallyMaintained='Y'
;

-- 2022-11-14T07:59:12.221Z
UPDATE AD_Field SET Name='Wägelauf', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=581658) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 581658)
;

-- 2022-11-14T07:59:12.230Z
UPDATE AD_PrintFormatItem pi SET PrintName='Wägelauf', Name='Wägelauf' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=581658)
;

-- 2022-11-14T07:59:12.231Z
UPDATE AD_Tab SET Name='Wägelauf', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 581658
;

-- 2022-11-14T07:59:12.233Z
UPDATE AD_WINDOW SET Name='Wägelauf', Description=NULL, Help=NULL WHERE AD_Element_ID = 581658
;

-- 2022-11-14T07:59:12.234Z
UPDATE AD_Menu SET   Name = 'Wägelauf', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 581658
;

-- 2022-11-14T07:59:14.819Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Wägelauf', PrintName='Wägelauf',Updated=TO_TIMESTAMP('2022-11-14 09:59:14.817','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=581658 AND AD_Language='de_CH'
;

-- 2022-11-14T07:59:14.820Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581658,'de_CH') 
;

-- 2022-11-14T08:00:19.805Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Weighing Check', PrintName='Weighing Check',Updated=TO_TIMESTAMP('2022-11-14 10:00:19.804','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=581663 AND AD_Language='en_US'
;

-- 2022-11-14T08:00:19.807Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581663,'en_US') 
;

-- 2022-11-14T08:00:24.577Z
UPDATE AD_Element_Trl SET Name='Weighing Check', PrintName='Weighing Check',Updated=TO_TIMESTAMP('2022-11-14 10:00:24.575','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=581663 AND AD_Language='de_CH'
;

-- 2022-11-14T08:00:24.578Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581663,'de_CH') 
;

-- 2022-11-14T08:00:26.996Z
UPDATE AD_Element_Trl SET Name='Weighing Check', PrintName='Weighing Check',Updated=TO_TIMESTAMP('2022-11-14 10:00:26.994','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=581663 AND AD_Language='de_DE'
;

-- 2022-11-14T08:00:26.997Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581663,'de_DE') 
;

-- 2022-11-14T08:00:27.004Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581663,'de_DE') 
;

-- 2022-11-14T08:00:27.006Z
UPDATE AD_Column SET ColumnName='PP_Order_Weighting_RunCheck_ID', Name='Weighing Check', Description=NULL, Help=NULL WHERE AD_Element_ID=581663
;

-- 2022-11-14T08:00:27.007Z
UPDATE AD_Process_Para SET ColumnName='PP_Order_Weighting_RunCheck_ID', Name='Weighing Check', Description=NULL, Help=NULL, AD_Element_ID=581663 WHERE UPPER(ColumnName)='PP_ORDER_WEIGHTING_RUNCHECK_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-11-14T08:00:27.008Z
UPDATE AD_Process_Para SET ColumnName='PP_Order_Weighting_RunCheck_ID', Name='Weighing Check', Description=NULL, Help=NULL WHERE AD_Element_ID=581663 AND IsCentrallyMaintained='Y'
;

-- 2022-11-14T08:00:27.009Z
UPDATE AD_Field SET Name='Weighing Check', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=581663) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 581663)
;

-- 2022-11-14T08:00:27.019Z
UPDATE AD_PrintFormatItem pi SET PrintName='Weighing Check', Name='Weighing Check' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=581663)
;

-- 2022-11-14T08:00:27.020Z
UPDATE AD_Tab SET Name='Weighing Check', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 581663
;

-- 2022-11-14T08:00:27.022Z
UPDATE AD_WINDOW SET Name='Weighing Check', Description=NULL, Help=NULL WHERE AD_Element_ID = 581663
;

-- 2022-11-14T08:00:27.024Z
UPDATE AD_Menu SET   Name = 'Weighing Check', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 581663
;

-- 2022-11-14T08:01:38.273Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Outside weighing tolerance', PrintName='Outside weighing tolerance',Updated=TO_TIMESTAMP('2022-11-14 10:01:38.271','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=581659 AND AD_Language='en_US'
;

-- 2022-11-14T08:01:38.274Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581659,'en_US') 
;

-- 2022-11-14T08:01:54.336Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Außerhalb der Wägetoleranz', PrintName='Außerhalb der Wägetoleranz',Updated=TO_TIMESTAMP('2022-11-14 10:01:54.334','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=581659 AND AD_Language='de_CH'
;

-- 2022-11-14T08:01:54.337Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581659,'de_CH') 
;

-- 2022-11-14T08:01:56.709Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Außerhalb der Wägetoleranz', PrintName='Außerhalb der Wägetoleranz',Updated=TO_TIMESTAMP('2022-11-14 10:01:56.707','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=581659 AND AD_Language='de_DE'
;

-- 2022-11-14T08:01:56.711Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581659,'de_DE') 
;

-- 2022-11-14T08:01:56.718Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581659,'de_DE') 
;

-- 2022-11-14T08:01:56.721Z
UPDATE AD_Column SET ColumnName='IsToleranceExceeded', Name='Außerhalb der Wägetoleranz', Description=NULL, Help=NULL WHERE AD_Element_ID=581659
;

-- 2022-11-14T08:01:56.722Z
UPDATE AD_Process_Para SET ColumnName='IsToleranceExceeded', Name='Außerhalb der Wägetoleranz', Description=NULL, Help=NULL, AD_Element_ID=581659 WHERE UPPER(ColumnName)='ISTOLERANCEEXCEEDED' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-11-14T08:01:56.723Z
UPDATE AD_Process_Para SET ColumnName='IsToleranceExceeded', Name='Außerhalb der Wägetoleranz', Description=NULL, Help=NULL WHERE AD_Element_ID=581659 AND IsCentrallyMaintained='Y'
;

-- 2022-11-14T08:01:56.724Z
UPDATE AD_Field SET Name='Außerhalb der Wägetoleranz', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=581659) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 581659)
;

-- 2022-11-14T08:01:56.734Z
UPDATE AD_PrintFormatItem pi SET PrintName='Außerhalb der Wägetoleranz', Name='Außerhalb der Wägetoleranz' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=581659)
;

-- 2022-11-14T08:01:56.736Z
UPDATE AD_Tab SET Name='Außerhalb der Wägetoleranz', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 581659
;

-- 2022-11-14T08:01:56.737Z
UPDATE AD_WINDOW SET Name='Außerhalb der Wägetoleranz', Description=NULL, Help=NULL WHERE AD_Element_ID = 581659
;

-- 2022-11-14T08:01:56.738Z
UPDATE AD_Menu SET   Name = 'Außerhalb der Wägetoleranz', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 581659
;

-- 2022-11-14T08:02:25.684Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Gewichtskontrolle', PrintName='Gewichtskontrolle',Updated=TO_TIMESTAMP('2022-11-14 10:02:25.682','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=581663 AND AD_Language='de_CH'
;

-- 2022-11-14T08:02:25.686Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581663,'de_CH') 
;

-- 2022-11-14T08:02:28.045Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Gewichtskontrolle', PrintName='Gewichtskontrolle',Updated=TO_TIMESTAMP('2022-11-14 10:02:28.043','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=581663 AND AD_Language='de_DE'
;

-- 2022-11-14T08:02:28.047Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581663,'de_DE') 
;

-- 2022-11-14T08:02:28.054Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581663,'de_DE') 
;

-- 2022-11-14T08:02:28.066Z
UPDATE AD_Column SET ColumnName='PP_Order_Weighting_RunCheck_ID', Name='Gewichtskontrolle', Description=NULL, Help=NULL WHERE AD_Element_ID=581663
;

-- 2022-11-14T08:02:28.067Z
UPDATE AD_Process_Para SET ColumnName='PP_Order_Weighting_RunCheck_ID', Name='Gewichtskontrolle', Description=NULL, Help=NULL, AD_Element_ID=581663 WHERE UPPER(ColumnName)='PP_ORDER_WEIGHTING_RUNCHECK_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-11-14T08:02:28.068Z
UPDATE AD_Process_Para SET ColumnName='PP_Order_Weighting_RunCheck_ID', Name='Gewichtskontrolle', Description=NULL, Help=NULL WHERE AD_Element_ID=581663 AND IsCentrallyMaintained='Y'
;

-- 2022-11-14T08:02:28.069Z
UPDATE AD_Field SET Name='Gewichtskontrolle', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=581663) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 581663)
;

-- 2022-11-14T08:02:28.078Z
UPDATE AD_PrintFormatItem pi SET PrintName='Gewichtskontrolle', Name='Gewichtskontrolle' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=581663)
;

-- 2022-11-14T08:02:28.080Z
UPDATE AD_Tab SET Name='Gewichtskontrolle', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 581663
;

-- 2022-11-14T08:02:28.081Z
UPDATE AD_WINDOW SET Name='Gewichtskontrolle', Description=NULL, Help=NULL WHERE AD_Element_ID = 581663
;

-- 2022-11-14T08:02:28.083Z
UPDATE AD_Menu SET   Name = 'Gewichtskontrolle', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 581663
;

-- 2022-11-14T08:04:24.587Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545210,0,TO_TIMESTAMP('2022-11-14 10:04:24.442','YYYY-MM-DD HH24:MI:SS.US'),100,'EE01','Y','Kann nicht bearbeitet werden, da weniger Gewichtskontrollen als erforderlich durchgeführt wurden.','E',TO_TIMESTAMP('2022-11-14 10:04:24.442','YYYY-MM-DD HH24:MI:SS.US'),100,'manufacturing.order.weighting.LessChecksThanRequired')
;

-- 2022-11-14T08:04:24.590Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545210 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2022-11-14T08:04:28.306Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-11-14 10:04:28.304','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545210
;

-- 2022-11-14T08:04:34.958Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-11-14 10:04:34.956','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545210
;

-- 2022-11-14T08:04:45.175Z
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Cannot process because there were less weight checks performed than required.',Updated=TO_TIMESTAMP('2022-11-14 10:04:45.174','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545210
;

-- 2022-11-14T08:05:50.880Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545211,0,TO_TIMESTAMP('2022-11-14 10:05:50.747','YYYY-MM-DD HH24:MI:SS.US'),100,'EE01','Y','Kann nicht bearbeitet werden, da das Gewicht außerhalb des Toleranzbereichs liegt. or simply Gewicht liegt außerhalb des Toleranzbereichs.','E',TO_TIMESTAMP('2022-11-14 10:05:50.747','YYYY-MM-DD HH24:MI:SS.US'),100,'manufacturing.order.weighting.ToleranceExceeded')
;

-- 2022-11-14T08:05:50.881Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545211 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2022-11-14T08:06:02.158Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-11-14 10:06:02.156','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545211
;

-- 2022-11-14T08:06:12.654Z
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Cannot process because weight is outside tolerance range. or simply Weight is outside tolerance range.',Updated=TO_TIMESTAMP('2022-11-14 10:06:12.652','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545211
;

-- 2022-11-14T08:06:16.752Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-11-14 10:06:16.749','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545211
;

-- 2022-11-14T08:07:39.627Z
UPDATE AD_Process SET Name='Verarbeiten',Updated=TO_TIMESTAMP('2022-11-14 10:07:39.625','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Process_ID=585144
;

-- 2022-11-14T08:07:43.681Z
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='Verarbeiten',Updated=TO_TIMESTAMP('2022-11-14 10:07:43.679','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=585144
;

-- 2022-11-14T08:07:45.867Z
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='Verarbeiten',Updated=TO_TIMESTAMP('2022-11-14 10:07:45.864','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=585144
;

-- 2022-11-14T08:07:47.104Z
UPDATE AD_Process_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-11-14 10:07:47.103','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585144
;

-- 2022-11-14T08:08:59.672Z
UPDATE AD_Process SET Name='Reaktivieren',Updated=TO_TIMESTAMP('2022-11-14 10:08:59.669','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Process_ID=585145
;

-- 2022-11-14T08:09:03.622Z
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='Reaktivieren',Updated=TO_TIMESTAMP('2022-11-14 10:09:03.62','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=585145
;

-- 2022-11-14T08:09:05.637Z
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='Reaktivieren',Updated=TO_TIMESTAMP('2022-11-14 10:09:05.634','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=585145
;

-- 2022-11-14T08:09:07.249Z
UPDATE AD_Process_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-11-14 10:09:07.247','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585145
;

