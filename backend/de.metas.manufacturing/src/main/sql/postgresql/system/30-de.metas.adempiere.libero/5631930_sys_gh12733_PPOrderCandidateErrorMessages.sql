-- 2022-03-25T12:45:29.598Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545113,0,TO_TIMESTAMP('2022-03-25 14:45:29','YYYY-MM-DD HH24:MI:SS'),100,'EE01','Y','Invalid selection! The production candidate must be processed but not closed!','E',TO_TIMESTAMP('2022-03-25 14:45:29','YYYY-MM-DD HH24:MI:SS'),100,'org.eevolution.productioncandidate.process.ClosedManufacturingCandidate')
;

-- 2022-03-25T12:45:29.603Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545113 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2022-03-25T12:45:49.893Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='Ungültige Auswahl! Der Produktionskandidat muss bearbeitet, aber nicht geschlossen werden!',Updated=TO_TIMESTAMP('2022-03-25 14:45:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545113
;

-- 2022-03-25T12:45:52.275Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='Ungültige Auswahl! Der Produktionskandidat muss bearbeitet, aber nicht geschlossen werden!',Updated=TO_TIMESTAMP('2022-03-25 14:45:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Message_ID=545113
;

-- 2022-03-25T12:45:54.708Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='Ungültige Auswahl! Der Produktionskandidat muss bearbeitet, aber nicht geschlossen werden!',Updated=TO_TIMESTAMP('2022-03-25 14:45:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545113
;

-- 2022-03-25T12:48:20.821Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET Value='org.eevolution.productioncandidate.process.ClosedManufacturingCandidateSelection',Updated=TO_TIMESTAMP('2022-03-25 14:48:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=545113
;

-- 2022-03-25T13:05:39.877Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545114,0,TO_TIMESTAMP('2022-03-25 15:05:39','YYYY-MM-DD HH24:MI:SS'),100,'EE01','Y','QtyEntered cannot go lower than the QtyProcessed!','E',TO_TIMESTAMP('2022-03-25 15:05:39','YYYY-MM-DD HH24:MI:SS'),100,'org.eevolution.productioncandidate.model.interceptor.QtyEnteredLowerThanQtyProcessed')
;

-- 2022-03-25T13:05:39.879Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545114 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2022-03-25T13:06:01.706Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='QtyEntered kann nicht niedriger sein als QtyProcessed!',Updated=TO_TIMESTAMP('2022-03-25 15:06:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545114
;

-- 2022-03-25T13:06:05.674Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='QtyEntered kann nicht niedriger sein als QtyProcessed!',Updated=TO_TIMESTAMP('2022-03-25 15:06:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Message_ID=545114
;

-- 2022-03-25T13:06:08.209Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='QtyEntered kann nicht niedriger sein als QtyProcessed!',Updated=TO_TIMESTAMP('2022-03-25 15:06:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545114
;

-- 2022-03-25T13:06:48.601Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545115,0,TO_TIMESTAMP('2022-03-25 15:06:48','YYYY-MM-DD HH24:MI:SS'),100,'EE01','Y','QtyToProcess cannot be greater then the actual qty left to be processed!','E',TO_TIMESTAMP('2022-03-25 15:06:48','YYYY-MM-DD HH24:MI:SS'),100,'org.eevolution.productioncandidate.model.interceptor.QtyToProcessGreaterThanQtyLeftToBeProcessed')
;

-- 2022-03-25T13:06:48.602Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545115 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2022-03-25T13:06:58.080Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='QtyToProcess kann nicht größer sein als die tatsächlich noch zu verarbeitende Menge!',Updated=TO_TIMESTAMP('2022-03-25 15:06:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545115
;

-- 2022-03-25T13:07:01.087Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='QtyToProcess kann nicht größer sein als die tatsächlich noch zu verarbeitende Menge!',Updated=TO_TIMESTAMP('2022-03-25 15:07:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Message_ID=545115
;

-- 2022-03-25T13:07:04.178Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='QtyToProcess kann nicht größer sein als die tatsächlich noch zu verarbeitende Menge!',Updated=TO_TIMESTAMP('2022-03-25 15:07:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545115
;

-- 2022-03-25T14:05:27.855Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET MsgText='Ungültige Auswahl! Der Produktionskandidat muss bearbeitet, aber nicht geschlossen werden!',Updated=TO_TIMESTAMP('2022-03-25 16:05:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=545113
;

-- 2022-03-25T14:06:04.059Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET MsgText='QtyEntered kann nicht niedriger sein als QtyProcessed!',Updated=TO_TIMESTAMP('2022-03-25 16:06:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=545114
;

-- 2022-03-25T14:06:27.206Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET MsgText='QtyToProcess kann nicht größer sein als die tatsächlich noch zu verarbeitende Menge!',Updated=TO_TIMESTAMP('2022-03-25 16:06:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=545115
;

-- 2022-03-25T15:45:14.304Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET MsgText='{0} kann nicht niedriger sein als {1}!',Updated=TO_TIMESTAMP('2022-03-25 17:45:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=545114
;

-- 2022-03-25T15:45:26.587Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='{0} kann nicht niedriger sein als {1}!',Updated=TO_TIMESTAMP('2022-03-25 17:45:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545114
;

-- 2022-03-25T15:45:29.254Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='{0} kann nicht niedriger sein als {1}!',Updated=TO_TIMESTAMP('2022-03-25 17:45:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Message_ID=545114
;

-- 2022-03-25T15:45:37.940Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='{0} cannot go lower than the {1}!',Updated=TO_TIMESTAMP('2022-03-25 17:45:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545114
;

-- 2022-03-25T15:45:39.877Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='{0} kann nicht niedriger sein als {1}!',Updated=TO_TIMESTAMP('2022-03-25 17:45:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545114
;

-- 2022-03-25T16:00:46.487Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET MsgText='{0} kann nicht größer sein als die tatsächlich noch zu verarbeitende Menge!',Updated=TO_TIMESTAMP('2022-03-25 18:00:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=545115
;

-- 2022-03-25T16:00:51.842Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='{0} kann nicht größer sein als die tatsächlich noch zu verarbeitende Menge!',Updated=TO_TIMESTAMP('2022-03-25 18:00:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545115
;

-- 2022-03-25T16:00:54.115Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='{0} kann nicht größer sein als die tatsächlich noch zu verarbeitende Menge!',Updated=TO_TIMESTAMP('2022-03-25 18:00:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Message_ID=545115
;

-- 2022-03-25T16:00:56.378Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='{0} cannot be greater then the actual qty left to be processed!',Updated=TO_TIMESTAMP('2022-03-25 18:00:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545115
;

-- 2022-03-25T16:00:58.858Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='{0} kann nicht größer sein als die tatsächlich noch zu verarbeitende Menge!',Updated=TO_TIMESTAMP('2022-03-25 18:00:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545115
;

-- 2022-03-25T17:19:22.540Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Verarbeitete Menge', PrintName='Verarbeitete Menge',Updated=TO_TIMESTAMP('2022-03-25 19:19:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580228 AND AD_Language='nl_NL'
;

-- 2022-03-25T17:19:22.642Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580228,'nl_NL')
;

-- 2022-03-25T17:19:30.338Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Verarbeitete Menge', PrintName='Verarbeitete Menge',Updated=TO_TIMESTAMP('2022-03-25 19:19:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580228 AND AD_Language='de_DE'
;

-- 2022-03-25T17:19:30.339Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580228,'de_DE')
;

-- 2022-03-25T17:19:30.369Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(580228,'de_DE')
;

-- 2022-03-25T17:19:30.371Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='QtyProcessed', Name='Verarbeitete Menge', Description=NULL, Help=NULL WHERE AD_Element_ID=580228
;

-- 2022-03-25T17:19:30.372Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='QtyProcessed', Name='Verarbeitete Menge', Description=NULL, Help=NULL, AD_Element_ID=580228 WHERE UPPER(ColumnName)='QTYPROCESSED' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-03-25T17:19:30.373Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='QtyProcessed', Name='Verarbeitete Menge', Description=NULL, Help=NULL WHERE AD_Element_ID=580228 AND IsCentrallyMaintained='Y'
;

-- 2022-03-25T17:19:30.374Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Verarbeitete Menge', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=580228) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 580228)
;

-- 2022-03-25T17:19:30.396Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Verarbeitete Menge', Name='Verarbeitete Menge' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=580228)
;

-- 2022-03-25T17:19:30.398Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Verarbeitete Menge', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 580228
;

-- 2022-03-25T17:19:30.399Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Verarbeitete Menge', Description=NULL, Help=NULL WHERE AD_Element_ID = 580228
;

-- 2022-03-25T17:19:30.400Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Verarbeitete Menge', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 580228
;

-- 2022-03-25T17:19:32.794Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Verarbeitete Menge', PrintName='Verarbeitete Menge',Updated=TO_TIMESTAMP('2022-03-25 19:19:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580228 AND AD_Language='de_CH'
;

-- 2022-03-25T17:19:32.796Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580228,'de_CH')
;

-- 2022-03-25T17:20:20.120Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Zu verarbeitende Menge', PrintName='Zu verarbeitende Menge',Updated=TO_TIMESTAMP('2022-03-25 19:20:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580229 AND AD_Language='nl_NL'
;

-- 2022-03-25T17:20:20.121Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580229,'nl_NL')
;

-- 2022-03-25T17:20:23.401Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Zu verarbeitende Menge', PrintName='Zu verarbeitende Menge',Updated=TO_TIMESTAMP('2022-03-25 19:20:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580229 AND AD_Language='de_DE'
;

-- 2022-03-25T17:20:23.402Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580229,'de_DE')
;

-- 2022-03-25T17:20:23.412Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(580229,'de_DE')
;

-- 2022-03-25T17:20:23.414Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='QtyToProcess', Name='Zu verarbeitende Menge', Description=NULL, Help=NULL WHERE AD_Element_ID=580229
;

-- 2022-03-25T17:20:23.416Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='QtyToProcess', Name='Zu verarbeitende Menge', Description=NULL, Help=NULL, AD_Element_ID=580229 WHERE UPPER(ColumnName)='QTYTOPROCESS' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-03-25T17:20:23.417Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='QtyToProcess', Name='Zu verarbeitende Menge', Description=NULL, Help=NULL WHERE AD_Element_ID=580229 AND IsCentrallyMaintained='Y'
;

-- 2022-03-25T17:20:23.418Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Zu verarbeitende Menge', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=580229) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 580229)
;

-- 2022-03-25T17:20:23.453Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Zu verarbeitende Menge', Name='Zu verarbeitende Menge' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=580229)
;

-- 2022-03-25T17:20:23.455Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Zu verarbeitende Menge', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 580229
;

-- 2022-03-25T17:20:23.457Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Zu verarbeitende Menge', Description=NULL, Help=NULL WHERE AD_Element_ID = 580229
;

-- 2022-03-25T17:20:23.458Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Zu verarbeitende Menge', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 580229
;

-- 2022-03-25T17:20:25.936Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Zu verarbeitende Menge', PrintName='Zu verarbeitende Menge',Updated=TO_TIMESTAMP('2022-03-25 19:20:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580229 AND AD_Language='de_CH'
;

-- 2022-03-25T17:20:25.937Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580229,'de_CH')
;