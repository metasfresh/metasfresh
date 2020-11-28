-- 2019-10-22T20:37:25.841Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Beauftragte Punktzahl', PrintName='Beauftragte Punktzahl',Updated=TO_TIMESTAMP('2019-10-22 22:37:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577081 AND AD_Language='de_CH'
;

-- 2019-10-22T20:37:25.843Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577081,'de_CH') 
;

-- 2019-10-22T20:37:33.190Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Beauftragte Punktzahl', PrintName='Beauftragte Punktzahl',Updated=TO_TIMESTAMP('2019-10-22 22:37:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577081 AND AD_Language='de_DE'
;

-- 2019-10-22T20:37:33.192Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577081,'de_DE') 
;

-- 2019-10-22T20:37:33.198Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577081,'de_DE') 
;

-- 2019-10-22T20:37:33.199Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='PointsSum_Forecasted', Name='Beauftragte Punktzahl', Description=NULL, Help=NULL WHERE AD_Element_ID=577081
;

-- 2019-10-22T20:37:33.200Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='PointsSum_Forecasted', Name='Beauftragte Punktzahl', Description=NULL, Help=NULL, AD_Element_ID=577081 WHERE UPPER(ColumnName)='POINTSSUM_FORECASTED' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-10-22T20:37:33.201Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='PointsSum_Forecasted', Name='Beauftragte Punktzahl', Description=NULL, Help=NULL WHERE AD_Element_ID=577081 AND IsCentrallyMaintained='Y'
;

-- 2019-10-22T20:37:33.202Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Beauftragte Punktzahl', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577081) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577081)
;

-- 2019-10-22T20:37:33.212Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Beauftragte Punktzahl', Name='Beauftragte Punktzahl' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577081)
;

-- 2019-10-22T20:37:33.213Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Beauftragte Punktzahl', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577081
;

-- 2019-10-22T20:37:33.215Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Beauftragte Punktzahl', Description=NULL, Help=NULL WHERE AD_Element_ID = 577081
;

-- 2019-10-22T20:37:33.216Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Beauftragte Punktzahl', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577081
;

-- 2019-10-22T20:37:49.301Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Ordered points', PrintName='Ordered points',Updated=TO_TIMESTAMP('2019-10-22 22:37:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577081 AND AD_Language='en_US'
;

-- 2019-10-22T20:37:49.302Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577081,'en_US') 
;

-- 2019-10-22T20:38:43.538Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Beauftragte Basispunktzahl', PrintName='Beauftragte Basispunktzahl',Updated=TO_TIMESTAMP('2019-10-22 22:38:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577086 AND AD_Language='de_CH'
;

-- 2019-10-22T20:38:43.539Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577086,'de_CH') 
;

-- 2019-10-22T20:38:52.090Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Beauftragte Basispunktzahl', PrintName='Beauftragte Basispunktzahl',Updated=TO_TIMESTAMP('2019-10-22 22:38:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577086 AND AD_Language='de_DE'
;

-- 2019-10-22T20:38:52.091Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577086,'de_DE') 
;

-- 2019-10-22T20:38:52.096Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577086,'de_DE') 
;

-- 2019-10-22T20:38:52.097Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='PointsBase_Forecasted', Name='Beauftragte Basispunktzahl', Description=NULL, Help=NULL WHERE AD_Element_ID=577086
;

-- 2019-10-22T20:38:52.098Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='PointsBase_Forecasted', Name='Beauftragte Basispunktzahl', Description=NULL, Help=NULL, AD_Element_ID=577086 WHERE UPPER(ColumnName)='POINTSBASE_FORECASTED' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-10-22T20:38:52.099Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='PointsBase_Forecasted', Name='Beauftragte Basispunktzahl', Description=NULL, Help=NULL WHERE AD_Element_ID=577086 AND IsCentrallyMaintained='Y'
;

-- 2019-10-22T20:38:52.100Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Beauftragte Basispunktzahl', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577086) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577086)
;

-- 2019-10-22T20:38:52.110Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Beauftragte Basispunktzahl', Name='Beauftragte Basispunktzahl' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577086)
;

-- 2019-10-22T20:38:52.111Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Beauftragte Basispunktzahl', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577086
;

-- 2019-10-22T20:38:52.113Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Beauftragte Basispunktzahl', Description=NULL, Help=NULL WHERE AD_Element_ID = 577086
;

-- 2019-10-22T20:38:52.114Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Beauftragte Basispunktzahl', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577086
;

-- 2019-10-22T20:39:03.304Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Ordered based points', PrintName='Ordered based points',Updated=TO_TIMESTAMP('2019-10-22 22:39:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577086 AND AD_Language='en_US'
;

-- 2019-10-22T20:39:03.305Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577086,'en_US') 
;

-- 2019-10-22T20:39:22.234Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Gelieferte Basispunktzahl', PrintName='Gelieferte Basispunktzahl',Updated=TO_TIMESTAMP('2019-10-22 22:39:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577088 AND AD_Language='de_CH'
;

-- 2019-10-22T20:39:22.235Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577088,'de_CH') 
;

-- 2019-10-22T20:39:26.433Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Gelieferte Basispunktzahl',Updated=TO_TIMESTAMP('2019-10-22 22:39:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577088 AND AD_Language='de_DE'
;

-- 2019-10-22T20:39:26.434Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577088,'de_DE') 
;

-- 2019-10-22T20:39:26.440Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577088,'de_DE') 
;

-- 2019-10-22T20:39:26.441Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='PointsBase_Invoiceable', Name='Gelieferte Basispunktzahl', Description=NULL, Help=NULL WHERE AD_Element_ID=577088
;

-- 2019-10-22T20:39:26.442Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='PointsBase_Invoiceable', Name='Gelieferte Basispunktzahl', Description=NULL, Help=NULL, AD_Element_ID=577088 WHERE UPPER(ColumnName)='POINTSBASE_INVOICEABLE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-10-22T20:39:26.443Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='PointsBase_Invoiceable', Name='Gelieferte Basispunktzahl', Description=NULL, Help=NULL WHERE AD_Element_ID=577088 AND IsCentrallyMaintained='Y'
;

-- 2019-10-22T20:39:26.444Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Gelieferte Basispunktzahl', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577088) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577088)
;

-- 2019-10-22T20:39:26.454Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Abzurechn. Basispunktzahl', Name='Gelieferte Basispunktzahl' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577088)
;

-- 2019-10-22T20:39:26.456Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Gelieferte Basispunktzahl', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577088
;

-- 2019-10-22T20:39:26.457Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Gelieferte Basispunktzahl', Description=NULL, Help=NULL WHERE AD_Element_ID = 577088
;

-- 2019-10-22T20:39:26.458Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Gelieferte Basispunktzahl', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577088
;

-- 2019-10-22T20:39:31.682Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='Gelieferte Basispunktzahl',Updated=TO_TIMESTAMP('2019-10-22 22:39:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577088 AND AD_Language='de_DE'
;

-- 2019-10-22T20:39:31.683Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577088,'de_DE') 
;

-- 2019-10-22T20:39:31.689Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577088,'de_DE') 
;

-- 2019-10-22T20:39:31.690Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Gelieferte Basispunktzahl', Name='Gelieferte Basispunktzahl' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577088)
;

-- 2019-10-22T20:39:39.222Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Delivered base points', PrintName='Delivered base points',Updated=TO_TIMESTAMP('2019-10-22 22:39:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577088 AND AD_Language='en_US'
;

-- 2019-10-22T20:39:39.223Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577088,'en_US') 
;

-- 2019-10-22T20:40:30.905Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Fakturierte Basispunktzahl', PrintName='Fakturierte Basispunktzahl',Updated=TO_TIMESTAMP('2019-10-22 22:40:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577087 AND AD_Language='de_CH'
;

-- 2019-10-22T20:40:30.907Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577087,'de_CH') 
;

-- 2019-10-22T20:40:37.274Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Fakturierte Basispunktzahl', PrintName='Fakturierte Basispunktzahl',Updated=TO_TIMESTAMP('2019-10-22 22:40:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577087 AND AD_Language='de_DE'
;

-- 2019-10-22T20:40:37.275Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577087,'de_DE') 
;

-- 2019-10-22T20:40:37.282Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577087,'de_DE') 
;

-- 2019-10-22T20:40:37.283Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='PointsBase_Invoiced', Name='Fakturierte Basispunktzahl', Description=NULL, Help=NULL WHERE AD_Element_ID=577087
;

-- 2019-10-22T20:40:37.284Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='PointsBase_Invoiced', Name='Fakturierte Basispunktzahl', Description=NULL, Help=NULL, AD_Element_ID=577087 WHERE UPPER(ColumnName)='POINTSBASE_INVOICED' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-10-22T20:40:37.285Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='PointsBase_Invoiced', Name='Fakturierte Basispunktzahl', Description=NULL, Help=NULL WHERE AD_Element_ID=577087 AND IsCentrallyMaintained='Y'
;

-- 2019-10-22T20:40:37.285Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Fakturierte Basispunktzahl', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577087) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577087)
;

-- 2019-10-22T20:40:37.296Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Fakturierte Basispunktzahl', Name='Fakturierte Basispunktzahl' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577087)
;

-- 2019-10-22T20:40:37.297Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Fakturierte Basispunktzahl', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577087
;

-- 2019-10-22T20:40:37.299Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Fakturierte Basispunktzahl', Description=NULL, Help=NULL WHERE AD_Element_ID = 577087
;

-- 2019-10-22T20:40:37.300Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Fakturierte Basispunktzahl', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577087
;

-- 2019-10-22T20:42:08.204Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Fakturierbare Basispunktzahl', PrintName='Fakturierbare Basispunktzahl',Updated=TO_TIMESTAMP('2019-10-22 22:42:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577088 AND AD_Language='de_CH'
;

-- 2019-10-22T20:42:08.205Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577088,'de_CH') 
;

-- 2019-10-22T20:42:15.586Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Fakturierbare Basispunktzahl', PrintName='Fakturierbare Basispunktzahl',Updated=TO_TIMESTAMP('2019-10-22 22:42:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577088 AND AD_Language='de_DE'
;

-- 2019-10-22T20:42:15.587Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577088,'de_DE') 
;

-- 2019-10-22T20:42:15.592Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577088,'de_DE') 
;

-- 2019-10-22T20:42:15.593Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='PointsBase_Invoiceable', Name='Fakturierbare Basispunktzahl', Description=NULL, Help=NULL WHERE AD_Element_ID=577088
;

-- 2019-10-22T20:42:15.594Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='PointsBase_Invoiceable', Name='Fakturierbare Basispunktzahl', Description=NULL, Help=NULL, AD_Element_ID=577088 WHERE UPPER(ColumnName)='POINTSBASE_INVOICEABLE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-10-22T20:42:15.595Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='PointsBase_Invoiceable', Name='Fakturierbare Basispunktzahl', Description=NULL, Help=NULL WHERE AD_Element_ID=577088 AND IsCentrallyMaintained='Y'
;

-- 2019-10-22T20:42:15.596Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Fakturierbare Basispunktzahl', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577088) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577088)
;

-- 2019-10-22T20:42:15.607Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Fakturierbare Basispunktzahl', Name='Fakturierbare Basispunktzahl' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577088)
;

-- 2019-10-22T20:42:15.608Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Fakturierbare Basispunktzahl', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577088
;

-- 2019-10-22T20:42:15.611Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Fakturierbare Basispunktzahl', Description=NULL, Help=NULL WHERE AD_Element_ID = 577088
;

-- 2019-10-22T20:42:15.612Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Fakturierbare Basispunktzahl', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577088
;

-- 2019-10-22T20:42:29.620Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Invoicable base points', PrintName='Invoicable base points',Updated=TO_TIMESTAMP('2019-10-22 22:42:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577088 AND AD_Language='en_US'
;

-- 2019-10-22T20:42:29.621Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577088,'en_US') 
;

-- 2019-10-22T21:17:25.267Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,544940,0,TO_TIMESTAMP('2019-10-22 23:17:25','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.contracts.commission','Y','Beim (Rechnungs-)Partner des Auftrags ist hinterlegt, dass ein Vertriebspartner angegeben werden muss.','E',TO_TIMESTAMP('2019-10-22 23:17:25','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.contracts.commission.salesOrder.MissingSalesPartner')
;

-- 2019-10-22T21:17:25.268Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Message_ID=544940 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2019-10-22T21:17:28.895Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-10-22 23:17:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=544940
;

-- 2019-10-22T21:18:19.920Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='The order''s (bill-)partner is flagged to require a sales partner to be specified',Updated=TO_TIMESTAMP('2019-10-22 23:18:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=544940
;

-- 2019-10-22T21:21:24.618Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Message_Trl WHERE AD_Message_ID=544939
;

-- 2019-10-22T21:21:24.622Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Message WHERE AD_Message_ID=544939
;

-- 2019-10-22T21:25:49.064Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='', Help='', IsTranslated='Y', Name='Übergeordneter Partner', PrintName='Übergeordneter Partner',Updated=TO_TIMESTAMP('2019-10-22 23:25:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=2031 AND AD_Language='de_CH'
;

-- 2019-10-22T21:25:49.066Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(2031,'de_CH') 
;

-- 2019-10-22T21:25:59.312Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='', Help='',Updated=TO_TIMESTAMP('2019-10-22 23:25:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=2031 AND AD_Language='en_US'
;

-- 2019-10-22T21:25:59.314Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(2031,'en_US') 
;

-- 2019-10-22T21:26:09.676Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='', Help='', IsTranslated='Y', Name='Übergeordneter Partner', PrintName='Übergeordneter Partner',Updated=TO_TIMESTAMP('2019-10-22 23:26:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=2031 AND AD_Language='de_DE'
;

-- 2019-10-22T21:26:09.677Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(2031,'de_DE') 
;

-- 2019-10-22T21:26:09.682Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(2031,'de_DE') 
;

-- 2019-10-22T21:26:09.684Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='BPartner_Parent_ID', Name='Übergeordneter Partner', Description='', Help='' WHERE AD_Element_ID=2031
;

-- 2019-10-22T21:26:09.685Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='BPartner_Parent_ID', Name='Übergeordneter Partner', Description='', Help='', AD_Element_ID=2031 WHERE UPPER(ColumnName)='BPARTNER_PARENT_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-10-22T21:26:09.686Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='BPartner_Parent_ID', Name='Übergeordneter Partner', Description='', Help='' WHERE AD_Element_ID=2031 AND IsCentrallyMaintained='Y'
;

-- 2019-10-22T21:26:09.687Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Übergeordneter Partner', Description='', Help='' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=2031) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 2031)
;

-- 2019-10-22T21:26:09.698Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Übergeordneter Partner', Name='Übergeordneter Partner' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=2031)
;

-- 2019-10-22T21:26:09.700Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Übergeordneter Partner', Description='', Help='', CommitWarning = NULL WHERE AD_Element_ID = 2031
;

-- 2019-10-22T21:26:09.701Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Übergeordneter Partner', Description='', Help='' WHERE AD_Element_ID = 2031
;

-- 2019-10-22T21:26:09.702Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Übergeordneter Partner', Description = '', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 2031
;

-- 2019-10-22T21:26:47.157Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', IsDisplayedGrid='Y',Updated=TO_TIMESTAMP('2019-10-22 23:26:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=9617
;

-- 2019-10-22T21:27:27.309Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET Name='BPartner_Parent_ID',Updated=TO_TIMESTAMP('2019-10-22 23:27:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=559448
;

-- 2019-10-22T21:28:26.783Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,1000003,543120,TO_TIMESTAMP('2019-10-22 23:28:26','YYYY-MM-DD HH24:MI:SS'),100,'Y','commission',12,TO_TIMESTAMP('2019-10-22 23:28:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-22T21:28:53.743Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=543120, SeqNo=10,Updated=TO_TIMESTAMP('2019-10-22 23:28:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=562102
;

-- 2019-10-22T21:30:42.609Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,568800,590599,0,220,0,TO_TIMESTAMP('2019-10-22 23:30:42','YYYY-MM-DD HH24:MI:SS'),100,'Legt für den Rechnungspartner fest, ob bei einer Beauftragung der Vertriebspartner angegeben werden muss.',0,'D',0,'Y','Y','Y','N','N','N','N','N','Nur mit Vertriebspartner',360,350,0,1,1,TO_TIMESTAMP('2019-10-22 23:30:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-22T21:30:42.612Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=590599 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-10-22T21:30:42.616Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577106) 
;

-- 2019-10-22T21:30:42.623Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=590599
;

-- 2019-10-22T21:30:42.624Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(590599)
;

-- 2019-10-22T21:30:57.686Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,568799,590600,0,220,0,TO_TIMESTAMP('2019-10-22 23:30:57','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','Y','Y','N','N','N','N','N','Zugeordneter Vertriebspartner',370,360,0,1,1,TO_TIMESTAMP('2019-10-22 23:30:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-22T21:30:57.688Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=590600 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-10-22T21:30:57.689Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(541357) 
;

-- 2019-10-22T21:30:57.691Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=590600
;

-- 2019-10-22T21:30:57.692Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(590600)
;

-- 2019-10-22T21:31:32.446Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,590599,0,220,543120,563587,'F',TO_TIMESTAMP('2019-10-22 23:31:32','YYYY-MM-DD HH24:MI:SS'),100,'Legt für den Rechnungspartner fest, ob bei einer Beauftragung der Vertriebspartner angegeben werden muss.','Y','N','N','Y','N','N','N',0,'IsSalesPartnerRequired',20,0,0,TO_TIMESTAMP('2019-10-22 23:31:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-22T21:31:53.503Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,590600,0,220,543120,563588,'F',TO_TIMESTAMP('2019-10-22 23:31:53','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'C_BPartner_SalesRep_ID',30,0,0,TO_TIMESTAMP('2019-10-22 23:31:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-22T21:33:23.855Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Legt für den Rechnungspartner fest, ob bei einer Beauftragung immer ein Vertriebspartner angegeben werden muss.', Name='Auftrag nur mit Vertriebspartner', PrintName='Auftrag nur mit Vertriebspartner',Updated=TO_TIMESTAMP('2019-10-22 23:33:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577106 AND AD_Language='de_CH'
;

-- 2019-10-22T21:33:23.856Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577106,'de_CH') 
;

-- 2019-10-22T21:33:27.883Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Legt für den Rechnungspartner fest, ob bei einer Beauftragung immer ein Vertriebspartner angegeben werden muss.',Updated=TO_TIMESTAMP('2019-10-22 23:33:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577106 AND AD_Language='de_DE'
;

-- 2019-10-22T21:33:27.884Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577106,'de_DE') 
;

-- 2019-10-22T21:33:27.889Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577106,'de_DE') 
;

-- 2019-10-22T21:33:27.890Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsSalesPartnerRequired', Name='Nur mit Vertriebspartner', Description='Legt für den Rechnungspartner fest, ob bei einer Beauftragung immer ein Vertriebspartner angegeben werden muss.', Help=NULL WHERE AD_Element_ID=577106
;

-- 2019-10-22T21:33:27.891Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsSalesPartnerRequired', Name='Nur mit Vertriebspartner', Description='Legt für den Rechnungspartner fest, ob bei einer Beauftragung immer ein Vertriebspartner angegeben werden muss.', Help=NULL, AD_Element_ID=577106 WHERE UPPER(ColumnName)='ISSALESPARTNERREQUIRED' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-10-22T21:33:27.892Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsSalesPartnerRequired', Name='Nur mit Vertriebspartner', Description='Legt für den Rechnungspartner fest, ob bei einer Beauftragung immer ein Vertriebspartner angegeben werden muss.', Help=NULL WHERE AD_Element_ID=577106 AND IsCentrallyMaintained='Y'
;

-- 2019-10-22T21:33:27.892Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Nur mit Vertriebspartner', Description='Legt für den Rechnungspartner fest, ob bei einer Beauftragung immer ein Vertriebspartner angegeben werden muss.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577106) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577106)
;

-- 2019-10-22T21:33:27.903Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Nur mit Vertriebspartner', Description='Legt für den Rechnungspartner fest, ob bei einer Beauftragung immer ein Vertriebspartner angegeben werden muss.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577106
;

-- 2019-10-22T21:33:27.904Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Nur mit Vertriebspartner', Description='Legt für den Rechnungspartner fest, ob bei einer Beauftragung immer ein Vertriebspartner angegeben werden muss.', Help=NULL WHERE AD_Element_ID = 577106
;

-- 2019-10-22T21:33:27.905Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Nur mit Vertriebspartner', Description = 'Legt für den Rechnungspartner fest, ob bei einer Beauftragung immer ein Vertriebspartner angegeben werden muss.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577106
;

-- 2019-10-22T21:33:40.220Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Auftrag nur mit Vertriebspartner', PrintName='Auftrag nur mit Vertriebspartner',Updated=TO_TIMESTAMP('2019-10-22 23:33:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577106 AND AD_Language='de_DE'
;

-- 2019-10-22T21:33:40.221Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577106,'de_DE') 
;

-- 2019-10-22T21:33:40.232Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577106,'de_DE') 
;

-- 2019-10-22T21:33:40.233Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsSalesPartnerRequired', Name='Auftrag nur mit Vertriebspartner', Description='Legt für den Rechnungspartner fest, ob bei einer Beauftragung immer ein Vertriebspartner angegeben werden muss.', Help=NULL WHERE AD_Element_ID=577106
;

-- 2019-10-22T21:33:40.234Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsSalesPartnerRequired', Name='Auftrag nur mit Vertriebspartner', Description='Legt für den Rechnungspartner fest, ob bei einer Beauftragung immer ein Vertriebspartner angegeben werden muss.', Help=NULL, AD_Element_ID=577106 WHERE UPPER(ColumnName)='ISSALESPARTNERREQUIRED' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-10-22T21:33:40.236Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsSalesPartnerRequired', Name='Auftrag nur mit Vertriebspartner', Description='Legt für den Rechnungspartner fest, ob bei einer Beauftragung immer ein Vertriebspartner angegeben werden muss.', Help=NULL WHERE AD_Element_ID=577106 AND IsCentrallyMaintained='Y'
;

-- 2019-10-22T21:33:40.237Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Auftrag nur mit Vertriebspartner', Description='Legt für den Rechnungspartner fest, ob bei einer Beauftragung immer ein Vertriebspartner angegeben werden muss.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577106) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577106)
;

-- 2019-10-22T21:33:40.248Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Auftrag nur mit Vertriebspartner', Name='Auftrag nur mit Vertriebspartner' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577106)
;

-- 2019-10-22T21:33:40.249Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Auftrag nur mit Vertriebspartner', Description='Legt für den Rechnungspartner fest, ob bei einer Beauftragung immer ein Vertriebspartner angegeben werden muss.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577106
;

-- 2019-10-22T21:33:40.250Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Auftrag nur mit Vertriebspartner', Description='Legt für den Rechnungspartner fest, ob bei einer Beauftragung immer ein Vertriebspartner angegeben werden muss.', Help=NULL WHERE AD_Element_ID = 577106
;

-- 2019-10-22T21:33:40.251Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Auftrag nur mit Vertriebspartner', Description = 'Legt für den Rechnungspartner fest, ob bei einer Beauftragung immer ein Vertriebspartner angegeben werden muss.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577106
;

-- 2019-10-22T21:34:57.934Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsCalculated='Y',Updated=TO_TIMESTAMP('2019-10-22 23:34:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=563299
;

-- 2019-10-22T21:39:59.379Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,577239,0,'SalesPartnerCode',TO_TIMESTAMP('2019-10-22 23:39:59','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.contracts.commission','Y','Vertriebspartnercode','Vertriebspartnercode',TO_TIMESTAMP('2019-10-22 23:39:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-22T21:39:59.380Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=577239 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-10-22T21:40:03.996Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-10-22 23:40:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577239 AND AD_Language='de_CH'
;

-- 2019-10-22T21:40:03.997Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577239,'de_CH') 
;

-- 2019-10-22T21:40:06.141Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-10-22 23:40:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577239 AND AD_Language='de_DE'
;

-- 2019-10-22T21:40:06.142Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577239,'de_DE') 
;

-- 2019-10-22T21:40:06.148Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577239,'de_DE') 
;

-- 2019-10-22T21:40:26.560Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Sales partner code', PrintName='Sales partner code',Updated=TO_TIMESTAMP('2019-10-22 23:40:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577239 AND AD_Language='en_US'
;

-- 2019-10-22T21:40:26.562Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577239,'en_US') 
;

-- 2019-10-22T21:40:45.267Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,569255,577239,0,10,291,'SalesPartnerCode',TO_TIMESTAMP('2019-10-22 23:40:45','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.contracts.commission',100,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Vertriebspartnercode',0,0,TO_TIMESTAMP('2019-10-22 23:40:45','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-10-22T21:40:45.269Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=569255 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-10-22T21:40:45.271Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(577239) 
;

-- 2019-10-22T21:41:31.311Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET SeqNo=10,Updated=TO_TIMESTAMP('2019-10-22 23:41:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=2902
;

-- 2019-10-22T21:41:42.762Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET SeqNo=20,Updated=TO_TIMESTAMP('2019-10-22 23:41:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=2902
;

-- 2019-10-22T21:41:47.719Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET SeqNo=10,Updated=TO_TIMESTAMP('2019-10-22 23:41:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=569255
;

-- 2019-10-22T21:42:00.997Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET SeqNo=30,Updated=TO_TIMESTAMP('2019-10-22 23:42:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=2901
;

-- 2019-10-22T21:42:27.315Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsAutocomplete='Y',Updated=TO_TIMESTAMP('2019-10-22 23:42:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=8768
;

-- 2019-10-22T21:42:47.722Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_ID=30, IsAutocomplete='Y',Updated=TO_TIMESTAMP('2019-10-22 23:42:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=568799
;

-- 2019-10-22T21:46:40.012Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,569255,590601,0,220,0,TO_TIMESTAMP('2019-10-22 23:46:39','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','Y','Y','N','N','N','N','N','Vertriebspartnercode',380,370,0,1,1,TO_TIMESTAMP('2019-10-22 23:46:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-22T21:46:40.013Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=590601 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-10-22T21:46:40.015Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577239) 
;

-- 2019-10-22T21:46:40.017Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=590601
;

-- 2019-10-22T21:46:40.018Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(590601)
;

-- 2019-10-22T21:47:09.163Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,590601,0,220,543120,563589,'F',TO_TIMESTAMP('2019-10-22 23:47:09','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'SalesPartnerCode',40,0,0,TO_TIMESTAMP('2019-10-22 23:47:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-22T21:47:55.341Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@IsSalesRep/''N''@=''Y''',Updated=TO_TIMESTAMP('2019-10-22 23:47:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=590601
;

-- 2019-10-22T21:48:13.989Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_BPartner','ALTER TABLE public.C_BPartner ADD COLUMN SalesPartnerCode VARCHAR(100)')
;

-- 2019-10-22T21:49:36.169Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=15,Updated=TO_TIMESTAMP('2019-10-22 23:49:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=563589
;

-- 2019-10-22T21:54:16.735Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET AD_Display=NULL, IsValueDisplayed='N',Updated=TO_TIMESTAMP('2019-10-22 23:54:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=138
;

-- 2019-10-22T21:54:59.177Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET AD_Display=2902, IsValueDisplayed='Y',Updated=TO_TIMESTAMP('2019-10-22 23:54:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=138
;

-- 2019-10-22T21:55:52.084Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,569256,577106,0,20,259,'IsSalesPartnerRequired',TO_TIMESTAMP('2019-10-22 23:55:51','YYYY-MM-DD HH24:MI:SS'),100,'N','N','Legt für den Rechnungspartner fest, ob bei einer Beauftragung immer ein Vertriebspartner angegeben werden muss.','de.metas.contracts.commission',1,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','Auftrag nur mit Vertriebspartner',0,0,TO_TIMESTAMP('2019-10-22 23:55:51','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-10-22T21:55:52.085Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=569256 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-10-22T21:55:52.087Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(577106) 
;

-- 2019-10-22T21:55:59.630Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_Order','ALTER TABLE public.C_Order ADD COLUMN IsSalesPartnerRequired CHAR(1) DEFAULT ''N'' CHECK (IsSalesPartnerRequired IN (''Y'',''N'')) NOT NULL')
;

-- 2019-10-22T21:56:27.137Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,569257,577239,0,10,259,'SalesPartnerCode',TO_TIMESTAMP('2019-10-22 23:56:27','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.contracts.commission',100,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Vertriebspartnercode',0,0,TO_TIMESTAMP('2019-10-22 23:56:27','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-10-22T21:56:27.139Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=569257 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-10-22T21:56:27.141Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(577239) 
;

-- 2019-10-22T21:56:27.366Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_Order','ALTER TABLE public.C_Order ADD COLUMN SalesPartnerCode VARCHAR(100)')
;

-- 2019-10-22T21:56:54.505Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,569257,590602,0,186,0,TO_TIMESTAMP('2019-10-22 23:56:54','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','Y','Y','N','N','N','N','N','Vertriebspartnercode',670,650,0,1,1,TO_TIMESTAMP('2019-10-22 23:56:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-22T21:56:54.506Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=590602 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-10-22T21:56:54.508Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577239) 
;

-- 2019-10-22T21:56:54.510Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=590602
;

-- 2019-10-22T21:56:54.511Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(590602)
;

-- 2019-10-22T21:57:07.743Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,569256,590603,0,186,0,TO_TIMESTAMP('2019-10-22 23:57:07','YYYY-MM-DD HH24:MI:SS'),100,'Legt für den Rechnungspartner fest, ob bei einer Beauftragung immer ein Vertriebspartner angegeben werden muss.',0,'D',0,'Y','Y','Y','N','N','N','N','N','Auftrag nur mit Vertriebspartner',680,660,0,1,1,TO_TIMESTAMP('2019-10-22 23:57:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-22T21:57:07.744Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=590603 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-10-22T21:57:07.745Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577106) 
;

-- 2019-10-22T21:57:07.747Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=590603
;

-- 2019-10-22T21:57:07.748Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(590603)
;

-- 2019-10-22T21:58:02.061Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540011,543121,TO_TIMESTAMP('2019-10-22 23:58:01','YYYY-MM-DD HH24:MI:SS'),100,'Y','Commission',20,TO_TIMESTAMP('2019-10-22 23:58:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-22T21:58:33.063Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,590603,0,186,543121,563590,'F',TO_TIMESTAMP('2019-10-22 23:58:32','YYYY-MM-DD HH24:MI:SS'),100,'Legt für den Rechnungspartner fest, ob bei einer Beauftragung immer ein Vertriebspartner angegeben werden muss.','Y','N','N','Y','N','N','N',0,'IsSalesPartnerRequired',10,0,0,TO_TIMESTAMP('2019-10-22 23:58:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-22T21:58:43.742Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2019-10-22 23:58:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=590603
;

-- 2019-10-22T21:59:07.655Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,590602,0,186,543121,563591,'F',TO_TIMESTAMP('2019-10-22 23:59:07','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'SalesPartnerCode',20,0,0,TO_TIMESTAMP('2019-10-22 23:59:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-22T21:59:23.374Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=543121, SeqNo=30,Updated=TO_TIMESTAMP('2019-10-22 23:59:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=562100
;

-- 2019-10-22T22:02:36.454Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET EntityType='D',Updated=TO_TIMESTAMP('2019-10-23 00:02:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=569257
;

-- 2019-10-22T22:15:19.589Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET EntityType='D',Updated=TO_TIMESTAMP('2019-10-23 00:15:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=569255
;

-- 2019-10-22T22:15:27.202Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET EntityType='D',Updated=TO_TIMESTAMP('2019-10-23 00:15:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577239
;

-- 2019-10-22T22:21:20.204Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET EntityType='D',Updated=TO_TIMESTAMP('2019-10-23 00:21:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=569256
;

-- 2019-10-22T22:21:27.196Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET EntityType='D',Updated=TO_TIMESTAMP('2019-10-23 00:21:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577106
;

