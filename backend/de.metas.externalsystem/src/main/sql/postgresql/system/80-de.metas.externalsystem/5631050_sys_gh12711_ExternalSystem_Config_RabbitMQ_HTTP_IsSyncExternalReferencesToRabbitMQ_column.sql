-- 2022-03-21T15:39:28.958Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,580720,0,'IsSyncExternalReferencesToRabbitMQ',TO_TIMESTAMP('2022-03-21 17:39:28','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalsystem','Y','Send partner references','Send partner references',TO_TIMESTAMP('2022-03-21 17:39:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-03-21T15:39:28.986Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=580720 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2022-03-21T15:39:47.058Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Wenn angehakt, dann können ausgewählte externe Referenzen zu Geschäftspartnern mit einer Aktion im Externe-Referenz-Fenster initial zu RabbitMQ gesendet werden. Nachdem sie einmal gesendet wurden, werden sie bei Änderungen automatisch erneut gesendet.', Name='Partner-Referenzen senden', PrintName='Partner-Referenzen senden',Updated=TO_TIMESTAMP('2022-03-21 17:39:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580720 AND AD_Language='de_CH'
;

-- 2022-03-21T15:39:47.244Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580720,'de_CH') 
;

-- 2022-03-21T15:39:57.712Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Wenn angehakt, dann können ausgewählte externe Referenzen zu Geschäftspartnern mit einer Aktion im Externe-Referenz-Fenster initial zu RabbitMQ gesendet werden. Nachdem sie einmal gesendet wurden, werden sie bei Änderungen automatisch erneut gesendet.', Name='Partner-Referenzen senden', PrintName='Partner-Referenzen senden',Updated=TO_TIMESTAMP('2022-03-21 17:39:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580720 AND AD_Language='de_DE'
;

-- 2022-03-21T15:39:57.715Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580720,'de_DE') 
;

-- 2022-03-21T15:39:57.728Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(580720,'de_DE') 
;

-- 2022-03-21T15:39:57.731Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsSyncExternalReferencesToRabbitMQ', Name='Partner-Referenzen senden', Description='Wenn angehakt, dann können ausgewählte externe Referenzen zu Geschäftspartnern mit einer Aktion im Externe-Referenz-Fenster initial zu RabbitMQ gesendet werden. Nachdem sie einmal gesendet wurden, werden sie bei Änderungen automatisch erneut gesendet.', Help=NULL WHERE AD_Element_ID=580720
;

-- 2022-03-21T15:39:57.733Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsSyncExternalReferencesToRabbitMQ', Name='Partner-Referenzen senden', Description='Wenn angehakt, dann können ausgewählte externe Referenzen zu Geschäftspartnern mit einer Aktion im Externe-Referenz-Fenster initial zu RabbitMQ gesendet werden. Nachdem sie einmal gesendet wurden, werden sie bei Änderungen automatisch erneut gesendet.', Help=NULL, AD_Element_ID=580720 WHERE UPPER(ColumnName)='ISSYNCEXTERNALREFERENCESTORABBITMQ' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-03-21T15:39:57.741Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsSyncExternalReferencesToRabbitMQ', Name='Partner-Referenzen senden', Description='Wenn angehakt, dann können ausgewählte externe Referenzen zu Geschäftspartnern mit einer Aktion im Externe-Referenz-Fenster initial zu RabbitMQ gesendet werden. Nachdem sie einmal gesendet wurden, werden sie bei Änderungen automatisch erneut gesendet.', Help=NULL WHERE AD_Element_ID=580720 AND IsCentrallyMaintained='Y'
;

-- 2022-03-21T15:39:57.742Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Partner-Referenzen senden', Description='Wenn angehakt, dann können ausgewählte externe Referenzen zu Geschäftspartnern mit einer Aktion im Externe-Referenz-Fenster initial zu RabbitMQ gesendet werden. Nachdem sie einmal gesendet wurden, werden sie bei Änderungen automatisch erneut gesendet.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=580720) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 580720)
;

-- 2022-03-21T15:39:57.884Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Partner-Referenzen senden', Name='Partner-Referenzen senden' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=580720)
;

-- 2022-03-21T15:39:57.888Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Partner-Referenzen senden', Description='Wenn angehakt, dann können ausgewählte externe Referenzen zu Geschäftspartnern mit einer Aktion im Externe-Referenz-Fenster initial zu RabbitMQ gesendet werden. Nachdem sie einmal gesendet wurden, werden sie bei Änderungen automatisch erneut gesendet.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 580720
;

-- 2022-03-21T15:39:57.894Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Partner-Referenzen senden', Description='Wenn angehakt, dann können ausgewählte externe Referenzen zu Geschäftspartnern mit einer Aktion im Externe-Referenz-Fenster initial zu RabbitMQ gesendet werden. Nachdem sie einmal gesendet wurden, werden sie bei Änderungen automatisch erneut gesendet.', Help=NULL WHERE AD_Element_ID = 580720
;

-- 2022-03-21T15:39:57.898Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Partner-Referenzen senden', Description = 'Wenn angehakt, dann können ausgewählte externe Referenzen zu Geschäftspartnern mit einer Aktion im Externe-Referenz-Fenster initial zu RabbitMQ gesendet werden. Nachdem sie einmal gesendet wurden, werden sie bei Änderungen automatisch erneut gesendet.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 580720
;

-- 2022-03-21T15:40:07.354Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='If ticked, then selected external references to business partners can be initially sent to RabbitMQ with an action in the External Reference window. After they have been sent once, they are automatically resent when changes are made.',Updated=TO_TIMESTAMP('2022-03-21 17:40:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580720 AND AD_Language='en_US'
;

-- 2022-03-21T15:40:07.363Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580720,'en_US') 
;

-- 2022-03-21T15:40:21.261Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Wenn angehakt, dann können ausgewählte externe Referenzen zu Geschäftspartnern mit einer Aktion im Externe-Referenz-Fenster initial zu RabbitMQ gesendet werden. Nachdem sie einmal gesendet wurden, werden sie bei Änderungen automatisch erneut gesendet.', Name='Partner-Referenzen senden', PrintName='Partner-Referenzen senden',Updated=TO_TIMESTAMP('2022-03-21 17:40:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580720 AND AD_Language='nl_NL'
;

-- 2022-03-21T15:40:21.263Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580720,'nl_NL') 
;

-- 2022-03-21T15:40:53.656Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,582471,580720,0,20,541803,'IsSyncExternalReferencesToRabbitMQ',TO_TIMESTAMP('2022-03-21 17:40:53','YYYY-MM-DD HH24:MI:SS'),100,'N','Y','Wenn angehakt, dann können ausgewählte externe Referenzen zu Geschäftspartnern mit einer Aktion im Externe-Referenz-Fenster initial zu RabbitMQ gesendet werden. Nachdem sie einmal gesendet wurden, werden sie bei Änderungen automatisch erneut gesendet.','de.metas.externalsystem',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N',0,'Partner-Referenzen senden',0,0,TO_TIMESTAMP('2022-03-21 17:40:53','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-03-21T15:40:53.670Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=582471 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-03-21T15:40:53.689Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(580720) 
;

-- 2022-03-21T15:40:58.747Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_RabbitMQ_HTTP','ALTER TABLE public.ExternalSystem_Config_RabbitMQ_HTTP ADD COLUMN IsSyncExternalReferencesToRabbitMQ CHAR(1) DEFAULT ''Y'' CHECK (IsSyncExternalReferencesToRabbitMQ IN (''Y'',''N'')) NOT NULL')
;

-- 2022-03-21T15:41:39.878Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Wenn ein Geschäftspartner bzw eine externe Referenz von einem Benutzer aus der angegebenen Gruppe erstellt wurde, wird der Datensatz automatisch gesendet.',Updated=TO_TIMESTAMP('2022-03-21 17:41:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580583 AND AD_Language='de_CH'
;

-- 2022-03-21T15:41:39.882Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580583,'de_CH') 
;

-- 2022-03-21T15:41:43.422Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Wenn ein Geschäftspartner bzw eine externe Referenz von einem Benutzer aus der angegebenen Gruppe erstellt wurde, wird der Datensatz automatisch gesendet.',Updated=TO_TIMESTAMP('2022-03-21 17:41:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580583 AND AD_Language='de_DE'
;

-- 2022-03-21T15:41:43.424Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580583,'de_DE') 
;

-- 2022-03-21T15:41:43.465Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(580583,'de_DE') 
;

-- 2022-03-21T15:41:43.467Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsAutoSendWhenCreatedByUserGroup', Name='Erstellt von Nutzergrp. autom. senden', Description='Wenn ein Geschäftspartner bzw eine externe Referenz von einem Benutzer aus der angegebenen Gruppe erstellt wurde, wird der Datensatz automatisch gesendet.', Help=NULL WHERE AD_Element_ID=580583
;

-- 2022-03-21T15:41:43.472Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsAutoSendWhenCreatedByUserGroup', Name='Erstellt von Nutzergrp. autom. senden', Description='Wenn ein Geschäftspartner bzw eine externe Referenz von einem Benutzer aus der angegebenen Gruppe erstellt wurde, wird der Datensatz automatisch gesendet.', Help=NULL, AD_Element_ID=580583 WHERE UPPER(ColumnName)='ISAUTOSENDWHENCREATEDBYUSERGROUP' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-03-21T15:41:43.475Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsAutoSendWhenCreatedByUserGroup', Name='Erstellt von Nutzergrp. autom. senden', Description='Wenn ein Geschäftspartner bzw eine externe Referenz von einem Benutzer aus der angegebenen Gruppe erstellt wurde, wird der Datensatz automatisch gesendet.', Help=NULL WHERE AD_Element_ID=580583 AND IsCentrallyMaintained='Y'
;

-- 2022-03-21T15:41:43.476Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Erstellt von Nutzergrp. autom. senden', Description='Wenn ein Geschäftspartner bzw eine externe Referenz von einem Benutzer aus der angegebenen Gruppe erstellt wurde, wird der Datensatz automatisch gesendet.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=580583) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 580583)
;

-- 2022-03-21T15:41:43.506Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Erstellt von Nutzergrp. autom. senden', Description='Wenn ein Geschäftspartner bzw eine externe Referenz von einem Benutzer aus der angegebenen Gruppe erstellt wurde, wird der Datensatz automatisch gesendet.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 580583
;

-- 2022-03-21T15:41:43.509Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Erstellt von Nutzergrp. autom. senden', Description='Wenn ein Geschäftspartner bzw eine externe Referenz von einem Benutzer aus der angegebenen Gruppe erstellt wurde, wird der Datensatz automatisch gesendet.', Help=NULL WHERE AD_Element_ID = 580583
;

-- 2022-03-21T15:41:43.511Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Erstellt von Nutzergrp. autom. senden', Description = 'Wenn ein Geschäftspartner bzw eine externe Referenz von einem Benutzer aus der angegebenen Gruppe erstellt wurde, wird der Datensatz automatisch gesendet.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 580583
;

-- 2022-03-21T15:41:49.216Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Wenn ein Geschäftspartner bzw eine externe Referenz von einem Benutzer aus der angegebenen Gruppe erstellt wurde, wird der Datensatz automatisch gesendet.',Updated=TO_TIMESTAMP('2022-03-21 17:41:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580583 AND AD_Language='nl_NL'
;

-- 2022-03-21T15:41:49.218Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580583,'nl_NL') 
;

-- 2022-03-21T15:41:57.169Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='If a business partner or external reference was created by a user from the specified group, the record will be sent automatically.',Updated=TO_TIMESTAMP('2022-03-21 17:41:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580583 AND AD_Language='en_US'
;

-- 2022-03-21T15:41:57.170Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580583,'en_US') 
;

-- 2022-03-21T15:43:15.188Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,582471,691418,0,544410,TO_TIMESTAMP('2022-03-21 17:43:15','YYYY-MM-DD HH24:MI:SS'),100,'Wenn angehakt, dann können ausgewählte externe Referenzen zu Geschäftspartnern mit einer Aktion im Externe-Referenz-Fenster initial zu RabbitMQ gesendet werden. Nachdem sie einmal gesendet wurden, werden sie bei Änderungen automatisch erneut gesendet.',1,'de.metas.externalsystem','Y','N','N','N','N','N','N','N','Partner-Referenzen senden',TO_TIMESTAMP('2022-03-21 17:43:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-03-21T15:43:15.198Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=691418 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-03-21T15:43:15.211Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(580720) 
;

-- 2022-03-21T15:43:15.263Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=691418
;

-- 2022-03-21T15:43:15.291Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(691418)
;

-- 2022-03-21T15:43:41.164Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,691418,0,544410,605256,546533,'F',TO_TIMESTAMP('2022-03-21 17:43:41','YYYY-MM-DD HH24:MI:SS'),100,'Wenn angehakt, dann können ausgewählte externe Referenzen zu Geschäftspartnern mit einer Aktion im Externe-Referenz-Fenster initial zu RabbitMQ gesendet werden. Nachdem sie einmal gesendet wurden, werden sie bei Änderungen automatisch erneut gesendet.','Y','N','N','Y','N','N','N',0,'Partner-Referenzen senden',40,0,0,TO_TIMESTAMP('2022-03-21 17:43:41','YYYY-MM-DD HH24:MI:SS'),100)
;

