-- UI Element: Externes System(541024,de.metas.externalsystem) -> SAP(546647,de.metas.externalsystem) -> main -> 10 -> main.SFTP Credit Limit File Name Pattern
-- Column: ExternalSystem_Config_SAP.SFTP_CreditLimit_FileName_Pattern
-- 2022-11-18T07:38:32.612Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=613412
;

-- UI Element: Externes System(541024,de.metas.externalsystem) -> SAP(546647,de.metas.externalsystem) -> main -> 10 -> main.SFTP Credit Limit TargetDirectory
-- Column: ExternalSystem_Config_SAP.SFTP_CreditLimit_TargetDirectory
-- 2022-11-18T07:38:36.497Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=613321
;

-- UI Column: Externes System(541024,de.metas.externalsystem) -> SAP(546647,de.metas.externalsystem) -> main -> 10
-- UI Element Group: creditLimit
-- 2022-11-18T07:38:49.973Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546411,550035,TO_TIMESTAMP('2022-11-18 09:38:49','YYYY-MM-DD HH24:MI:SS'),100,'Y','creditLimit',40,TO_TIMESTAMP('2022-11-18 09:38:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Externes System(541024,de.metas.externalsystem) -> SAP(546647,de.metas.externalsystem) -> main -> 10 -> creditLimit.SFTP Kreditlimits Zielverzeichnis
-- Column: ExternalSystem_Config_SAP.SFTP_CreditLimit_TargetDirectory
-- 2022-11-18T07:39:20.363Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,707881,0,546647,613533,550035,'F',TO_TIMESTAMP('2022-11-18 09:39:20','YYYY-MM-DD HH24:MI:SS'),100,'Das Verzeichnis, das zum Abrufen der Kreditlimits vom sftp-Server verwendet wird. (Wenn hier kein Wert angegeben wird, werden die Dateien aus dem Stammverzeichnis des sftp-Servers abgerufen).','Y','N','N','Y','N','N','N',0,'SFTP Kreditlimits Zielverzeichnis',10,0,0,TO_TIMESTAMP('2022-11-18 09:39:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Externes System(541024,de.metas.externalsystem) -> SAP(546647,de.metas.externalsystem) -> main -> 10 -> creditLimit.SFTP Credit Limit File Name Pattern
-- Column: ExternalSystem_Config_SAP.SFTP_CreditLimit_FileName_Pattern
-- 2022-11-18T07:39:40.433Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,707991,0,546647,613534,550035,'F',TO_TIMESTAMP('2022-11-18 09:39:40','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'SFTP Credit Limit File Name Pattern',20,0,0,TO_TIMESTAMP('2022-11-18 09:39:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Element: SFTP_CreditLimit_FileName_Pattern
-- 2022-11-18T07:44:48.152Z
UPDATE AD_Element_Trl SET Name='SFTP-Kreditlimit Dateinamen-Muster', PrintName='SFTP-Kreditlimit Dateinamen-Muster',Updated=TO_TIMESTAMP('2022-11-18 09:44:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581657 AND AD_Language='de_CH'
;

-- 2022-11-18T07:44:48.173Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581657,'de_CH') 
;

-- Element: SFTP_CreditLimit_FileName_Pattern
-- 2022-11-18T07:48:03.998Z
UPDATE AD_Element_Trl SET Description='Muster, das zur Identifizierung von Kreditlimit-Dateien auf dem SFTP-Server verwendet wird. (Wenn nicht angegeben, werden alle Dateien berücksichtigt). ', Name='SFTP-Kreditlimit Dateinamen-Muster', PrintName='SFTP-Kreditlimit Dateinamen-Muster',Updated=TO_TIMESTAMP('2022-11-18 09:48:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581657 AND AD_Language='de_DE'
;

-- 2022-11-18T07:48:04.001Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581657,'de_DE') 
;

-- 2022-11-18T07:48:04.003Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581657,'de_DE') 
;

-- Element: SFTP_CreditLimit_FileName_Pattern
-- 2022-11-18T07:48:06.671Z
UPDATE AD_Element_Trl SET Description='Muster, das zur Identifizierung von Kreditlimit-Dateien auf dem SFTP-Server verwendet wird. (Wenn nicht angegeben, werden alle Dateien berücksichtigt). ',Updated=TO_TIMESTAMP('2022-11-18 09:48:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581657 AND AD_Language='de_CH'
;

-- 2022-11-18T07:48:06.673Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581657,'de_CH') 
;

-- Reference: External_Request SAP
-- Value: stopCreditLimitsSync
-- ValueName: Stop Credit Limits Synchronization
-- 2022-11-18T07:59:45.870Z
UPDATE AD_Ref_List SET Description='Stoppt die Kreditlimits-Synchronisation mit dem externen SAP-System',Updated=TO_TIMESTAMP('2022-11-18 09:59:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=543330
;

-- Reference: External_Request SAP
-- Value: startCreditLimitsSync
-- ValueName: Start Credit Limits Synchronization
-- 2022-11-18T08:00:22.128Z
UPDATE AD_Ref_List SET Description='Startet die Kreditlimitsynchronisation mit dem externen SAP-System',Updated=TO_TIMESTAMP('2022-11-18 10:00:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=543329
;

-- Reference: External_Request SAP
-- Value: stopCreditLimitsSync
-- ValueName: Stop Credit Limits Synchronization
-- 2022-11-18T08:00:56.483Z
UPDATE AD_Ref_List SET Description='Stoppt die Kreditlimitsynchronisation mit dem externen SAP-System',Updated=TO_TIMESTAMP('2022-11-18 10:00:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=543330
;

-- Reference Item: External_Request SAP -> stopBPartnerSync_import
-- 2022-11-18T08:02:44.558Z
UPDATE AD_Ref_List_Trl SET Name='Stop der Geschäftspartner-Synchronisation',Updated=TO_TIMESTAMP('2022-11-18 10:02:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543317
;

-- Reference Item: External_Request SAP -> stopBPartnerSync_import
-- 2022-11-18T08:02:55.712Z
UPDATE AD_Ref_List_Trl SET Name='Stop der Geschäftspartner-Synchronisation',Updated=TO_TIMESTAMP('2022-11-18 10:02:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543317
;

-- Reference: External_Request SAP
-- Value: stopBPartnerSync
-- ValueName: import
-- 2022-11-18T08:03:18.289Z
UPDATE AD_Ref_List SET Name='Stop der Geschäftspartner-Synchronisation',Updated=TO_TIMESTAMP('2022-11-18 10:03:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=543317
;

-- Reference: External_Request SAP
-- Value: stopCreditLimitsSync
-- ValueName: Stop Credit Limits Synchronization
-- 2022-11-18T08:03:42.610Z
UPDATE AD_Ref_List SET Name='Stop der Kreditlimit-Synchronisation',Updated=TO_TIMESTAMP('2022-11-18 10:03:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=543330
;

-- Reference Item: External_Request SAP -> stopProductsSync_import
-- 2022-11-18T08:04:51.938Z
UPDATE AD_Ref_List_Trl SET Name='Stop der Produktsynchronisation',Updated=TO_TIMESTAMP('2022-11-18 10:04:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543301
;

-- Reference Item: External_Request SAP -> stopProductsSync_import
-- 2022-11-18T08:04:53.885Z
UPDATE AD_Ref_List_Trl SET Name='Stop der Produktsynchronisation',Updated=TO_TIMESTAMP('2022-11-18 10:04:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543301
;

-- Reference: External_Request SAP
-- Value: stopProductsSync
-- ValueName: import
-- 2022-11-18T08:05:03.208Z
UPDATE AD_Ref_List SET Name='Stop der Produktsynchronisation',Updated=TO_TIMESTAMP('2022-11-18 10:05:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=543301
;

-- Reference: External_Request SAP
-- Value: stopCreditLimitsSync
-- ValueName: Stop Credit Limits Synchronization
-- 2022-11-18T08:05:12.264Z
UPDATE AD_Ref_List SET Name='Stop der Kreditlimitsynchronisation',Updated=TO_TIMESTAMP('2022-11-18 10:05:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=543330
;

-- Element: SFTP_CreditLimit_FileName_Pattern
-- 2022-11-18T08:18:53.797Z
UPDATE AD_Element_Trl SET Description='Ant-style pattern used to identify credit limit files on the SFTP-Server. (If not set, all files are considered)',Updated=TO_TIMESTAMP('2022-11-18 10:18:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581657 AND AD_Language='en_US'
;

-- 2022-11-18T08:18:53.799Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581657,'en_US')
;

-- Value: ExternalSystemConfigSAPSameSFTPExtraDetails
-- 2022-11-18T12:11:44.804Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545212,0,TO_TIMESTAMP('2022-11-18 14:11:44','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalsystem','Y','Cannot enable route "{0}" for ExternalSystem_Config_ID = {1} due to identical target directory & file name details for at least two of the supported types.','I',TO_TIMESTAMP('2022-11-18 14:11:44','YYYY-MM-DD HH24:MI:SS'),100,'ExternalSystemConfigSAPSameSFTPExtraDetails')
;

-- 2022-11-18T12:11:44.814Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545212 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: ExternalSystemConfigSAPDuplicateSFTPFileLookupDetails
-- 2022-11-18T12:12:04.794Z
UPDATE AD_Message SET Value='ExternalSystemConfigSAPDuplicateSFTPFileLookupDetails',Updated=TO_TIMESTAMP('2022-11-18 14:12:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=545212
;

-- Value: ExternalSystemConfigSAPIdenticalSFTPExtraDetails
-- 2022-11-18T15:03:46.670Z
UPDATE AD_Message SET MsgText='Cannot start route "{0}" for ExternalSystem_Config_ID = {1} due to target directory & file name pattern of the selected service being duplicated.',Updated=TO_TIMESTAMP('2022-11-18 17:03:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=545212
;

-- Value: ExternalSystemConfigSAPDuplicateSFTPFileLookupDetails
-- 2022-11-18T15:09:36.977Z
UPDATE AD_Message_Trl SET MsgText='Die Route "{0}" für ExternalSystem_Config_ID = {1} kann nicht gestartet werden, da das Zielverzeichnis und das Dateinamensmuster des ausgewählten Dienstes dupliziert sind. ',Updated=TO_TIMESTAMP('2022-11-18 17:09:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545212
;

-- Value: ExternalSystemConfigSAPDuplicateSFTPFileLookupDetails
-- 2022-11-18T15:09:42.342Z
UPDATE AD_Message_Trl SET MsgText='Die Route "{0}" für ExternalSystem_Config_ID = {1} kann nicht gestartet werden, da das Zielverzeichnis und das Dateinamensmuster des ausgewählten Dienstes dupliziert sind. ',Updated=TO_TIMESTAMP('2022-11-18 17:09:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545212
;

-- Value: ExternalSystemConfigSAPDuplicateSFTPFileLookupDetails
-- 2022-11-18T15:11:22.434Z
UPDATE AD_Message SET MsgText='Die Route "{0}" für ExternalSystem_Config_ID = {1} kann nicht gestartet werden, da das Zielverzeichnis und das Dateinamensmuster des ausgewählten Dienstes dupliziert sind.',Updated=TO_TIMESTAMP('2022-11-18 17:11:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=545212
;

-- Value: ExternalSystemConfigSAPDuplicateSFTPFileLookupDetails
-- 2022-11-18T15:16:42.862Z
UPDATE AD_Message_Trl SET MsgText='Cannot start route "{0}" for ExternalSystem_Config_ID = {1} due to target directory & file name pattern of the selected service being duplicated.',Updated=TO_TIMESTAMP('2022-11-18 17:16:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Message_ID=545212
;

-- Value: ExternalSystemConfigSAPDuplicateSFTPFileLookupDetails
-- 2022-11-18T15:16:47.934Z
UPDATE AD_Message_Trl SET MsgText='Cannot start route "{0}" for ExternalSystem_Config_ID = {1} due to target directory & file name pattern of the selected service being duplicated.',Updated=TO_TIMESTAMP('2022-11-18 17:16:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545212
;



