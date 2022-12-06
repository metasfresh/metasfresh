-- Column: ExternalSystem_Config_SAP_SFTP.ExternalSystem_Config_ID
-- 2022-11-18T18:59:32.574Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585086,578728,0,19,542257,'ExternalSystem_Config_ID',TO_TIMESTAMP('2022-11-18 20:59:32','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.externalsystem',0,10,'Y','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','External System Config',0,0,TO_TIMESTAMP('2022-11-18 20:59:32','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-11-18T18:59:32.579Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585086 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-11-18T18:59:32.608Z
/* DDL */  select update_Column_Translation_From_AD_Element(578728) 
;

-- Column: ExternalSystem_Config_SAP_SFTP.ExternalSystemValue
-- 2022-11-18T18:59:33.235Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585087,578788,0,10,542257,'ExternalSystemValue',TO_TIMESTAMP('2022-11-18 20:59:33','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.externalsystem',0,255,'Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','Y','Suchschlüssel',0,0,TO_TIMESTAMP('2022-11-18 20:59:33','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-11-18T18:59:33.237Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585087 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-11-18T18:59:33.239Z
/* DDL */  select update_Column_Translation_From_AD_Element(578788) 
;

-- Column: ExternalSystem_Config_SAP_SFTP.SFTP_CreditLimit_FileName_Pattern
-- 2022-11-18T18:59:33.866Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585088,581657,0,10,542257,'SFTP_CreditLimit_FileName_Pattern',TO_TIMESTAMP('2022-11-18 20:59:33','YYYY-MM-DD HH24:MI:SS'),100,'N','Muster, das zur Identifizierung von Kreditlimit-Dateien auf dem SFTP-Server verwendet wird. (Wenn nicht angegeben, werden alle Dateien berücksichtigt). ','de.metas.externalsystem',0,255,'Y','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','SFTP-Kreditlimit Dateinamen-Muster',0,0,TO_TIMESTAMP('2022-11-18 20:59:33','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-11-18T18:59:33.869Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585088 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-11-18T18:59:33.872Z
/* DDL */  select update_Column_Translation_From_AD_Element(581657) 
;

-- Column: ExternalSystem_Config_SAP_SFTP.SFTP_CreditLimit_TargetDirectory
-- 2022-11-18T18:59:34.496Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585089,581623,0,10,542257,'SFTP_CreditLimit_TargetDirectory',TO_TIMESTAMP('2022-11-18 20:59:34','YYYY-MM-DD HH24:MI:SS'),100,'N','Das Verzeichnis, das zum Abrufen der Kreditlimits vom sftp-Server verwendet wird. (Wenn hier kein Wert angegeben wird, werden die Dateien aus dem Stammverzeichnis des sftp-Servers abgerufen).','de.metas.externalsystem',0,255,'Y','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','SFTP Kreditlimits Zielverzeichnis',0,0,TO_TIMESTAMP('2022-11-18 20:59:34','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-11-18T18:59:34.498Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585089 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-11-18T18:59:34.500Z
/* DDL */  select update_Column_Translation_From_AD_Element(581623) 
;

-- Column: ExternalSystem_Config_SAP_SFTP.ExternalSystemValue
-- 2022-11-18T19:00:01.360Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=585087
;

-- 2022-11-18T19:00:01.375Z
DELETE FROM AD_Column WHERE AD_Column_ID=585087
;

-- Column: ExternalSystem_Config_SAP_SFTP.ExternalSystem_Config_ID
-- 2022-11-18T19:01:51.260Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=585086
;

-- 2022-11-18T19:01:51.264Z
DELETE FROM AD_Column WHERE AD_Column_ID=585086
;

-- UI Element: Externes System(541024,de.metas.externalsystem) -> SAP(546647,de.metas.externalsystem) -> main -> 10 -> creditLimit.SFTP Credit Limit File Name Pattern
-- Column: ExternalSystem_Config_SAP.SFTP_CreditLimit_FileName_Pattern
-- 2022-11-18T19:02:21.287Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=613534
;

-- 2022-11-18T19:02:21.287Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=707991
;

-- Field: Externes System(541024,de.metas.externalsystem) -> SAP(546647,de.metas.externalsystem) -> SFTP-Kreditlimit Dateinamen-Muster
-- Column: ExternalSystem_Config_SAP.SFTP_CreditLimit_FileName_Pattern
-- 2022-11-18T19:02:21.303Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=707991
;

-- 2022-11-18T19:02:21.303Z
DELETE FROM AD_Field WHERE AD_Field_ID=707991
;

-- 2022-11-18T19:02:21.303Z
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_SAP','ALTER TABLE ExternalSystem_Config_SAP DROP COLUMN IF EXISTS SFTP_CreditLimit_FileName_Pattern')
;

-- Column: ExternalSystem_Config_SAP.SFTP_CreditLimit_FileName_Pattern
-- 2022-11-18T19:02:21.326Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=584886
;

-- 2022-11-18T19:02:21.326Z
DELETE FROM AD_Column WHERE AD_Column_ID=584886
;

-- UI Element: Externes System(541024,de.metas.externalsystem) -> SAP(546647,de.metas.externalsystem) -> main -> 10 -> creditLimit.SFTP Kreditlimits Zielverzeichnis
-- Column: ExternalSystem_Config_SAP.SFTP_CreditLimit_TargetDirectory
-- 2022-11-18T19:02:44.887Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=613533
;

-- 2022-11-18T19:02:44.889Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=707881
;

-- Field: Externes System(541024,de.metas.externalsystem) -> SAP(546647,de.metas.externalsystem) -> SFTP Kreditlimits Zielverzeichnis
-- Column: ExternalSystem_Config_SAP.SFTP_CreditLimit_TargetDirectory
-- 2022-11-18T19:02:44.889Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=707881
;

-- 2022-11-18T19:02:44.889Z
DELETE FROM AD_Field WHERE AD_Field_ID=707881
;

-- 2022-11-18T19:02:44.889Z
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_SAP','ALTER TABLE ExternalSystem_Config_SAP DROP COLUMN IF EXISTS SFTP_CreditLimit_TargetDirectory')
;

-- Column: ExternalSystem_Config_SAP.SFTP_CreditLimit_TargetDirectory
-- 2022-11-18T19:02:44.905Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=584828
;

-- 2022-11-18T19:02:44.905Z
DELETE FROM AD_Column WHERE AD_Column_ID=584828
;

-- Column: ExternalSystem_Config_SAP_LocalFile.SFTP_BPartner_FileName_Pattern
-- 2022-11-18T19:03:27.844Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585090,581668,0,10,542258,'SFTP_BPartner_FileName_Pattern',TO_TIMESTAMP('2022-11-18 21:03:27','YYYY-MM-DD HH24:MI:SS'),100,'N','Muster, das zur Identifizierung von Geschäftspartnerdateien auf dem SFTP-Server verwendet wird. (Wenn nicht festgelegt, werden alle Dateien berücksichtigt)','de.metas.externalsystem',0,255,'Y','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','Geschäftspartner Dateinamen-Muster',0,0,TO_TIMESTAMP('2022-11-18 21:03:27','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-11-18T19:03:27.844Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585090 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-11-18T19:03:27.861Z
/* DDL */  select update_Column_Translation_From_AD_Element(581668) 
;

-- Column: ExternalSystem_Config_SAP_LocalFile.SFTP_BPartner_TargetDirectory
-- 2022-11-18T19:03:28.395Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585091,581604,0,10,542258,'SFTP_BPartner_TargetDirectory',TO_TIMESTAMP('2022-11-18 21:03:28','YYYY-MM-DD HH24:MI:SS'),100,'N','Verzeichnis, das zum Abrufen von Geschäftspartnern vom sftp-Server verwendet wird. (Falls nicht festgelegt, werden die Dateien aus dem Stammverzeichnis der sftp-Verbindung gezogen)','de.metas.externalsystem',0,255,'Y','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','Geschäftspartner-Zielverzeichnis',0,0,TO_TIMESTAMP('2022-11-18 21:03:28','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-11-18T19:03:28.411Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585091 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-11-18T19:03:28.411Z
/* DDL */  select update_Column_Translation_From_AD_Element(581604) 
;

-- Column: ExternalSystem_Config_SAP_LocalFile.SFTP_CreditLimit_FileName_Pattern
-- 2022-11-18T19:03:28.979Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585092,581657,0,10,542258,'SFTP_CreditLimit_FileName_Pattern',TO_TIMESTAMP('2022-11-18 21:03:28','YYYY-MM-DD HH24:MI:SS'),100,'N','Muster, das zur Identifizierung von Kreditlimit-Dateien auf dem SFTP-Server verwendet wird. (Wenn nicht angegeben, werden alle Dateien berücksichtigt). ','de.metas.externalsystem',0,255,'Y','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','SFTP-Kreditlimit Dateinamen-Muster',0,0,TO_TIMESTAMP('2022-11-18 21:03:28','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-11-18T19:03:28.987Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585092 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-11-18T19:03:28.987Z
/* DDL */  select update_Column_Translation_From_AD_Element(581657) 
;

-- Column: ExternalSystem_Config_SAP_LocalFile.SFTP_CreditLimit_TargetDirectory
-- 2022-11-18T19:03:29.587Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585093,581623,0,10,542258,'SFTP_CreditLimit_TargetDirectory',TO_TIMESTAMP('2022-11-18 21:03:29','YYYY-MM-DD HH24:MI:SS'),100,'N','Das Verzeichnis, das zum Abrufen der Kreditlimits vom sftp-Server verwendet wird. (Wenn hier kein Wert angegeben wird, werden die Dateien aus dem Stammverzeichnis des sftp-Servers abgerufen).','de.metas.externalsystem',0,255,'Y','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','SFTP Kreditlimits Zielverzeichnis',0,0,TO_TIMESTAMP('2022-11-18 21:03:29','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-11-18T19:03:29.587Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585093 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-11-18T19:03:29.587Z
/* DDL */  select update_Column_Translation_From_AD_Element(581623) 
;

-- Column: ExternalSystem_Config_SAP_LocalFile.SFTP_HostName
-- 2022-11-18T19:03:30.275Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585094,581553,0,10,542258,'SFTP_HostName',TO_TIMESTAMP('2022-11-18 21:03:30','YYYY-MM-DD HH24:MI:SS'),100,'N','SFTP-Hostname','SFTP-Server-Hostname','de.metas.externalsystem',0,255,'Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','Y','Hostname',0,0,TO_TIMESTAMP('2022-11-18 21:03:30','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-11-18T19:03:30.275Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585094 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-11-18T19:03:30.275Z
/* DDL */  select update_Column_Translation_From_AD_Element(581553) 
;

-- Column: ExternalSystem_Config_SAP_LocalFile.SFTP_Password
-- 2022-11-18T19:03:30.891Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585095,581554,0,10,542258,'SFTP_Password',TO_TIMESTAMP('2022-11-18 21:03:30','YYYY-MM-DD HH24:MI:SS'),100,'N','SFTP-Passwort','Das Passwort, das für die Authentifizierung des sftp-Servers verwendet wird.','de.metas.externalsystem',0,255,'Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','Y','Passwort',0,0,TO_TIMESTAMP('2022-11-18 21:03:30','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-11-18T19:03:30.895Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585095 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-11-18T19:03:30.896Z
/* DDL */  select update_Column_Translation_From_AD_Element(581554) 
;

-- Column: ExternalSystem_Config_SAP_LocalFile.SFTP_Port
-- 2022-11-18T19:03:31.531Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585096,581555,0,10,542258,'SFTP_Port',TO_TIMESTAMP('2022-11-18 21:03:31','YYYY-MM-DD HH24:MI:SS'),100,'N','SFTP-Port','SFTP-Port','de.metas.externalsystem',0,14,'Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','Y','Port',0,0,TO_TIMESTAMP('2022-11-18 21:03:31','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-11-18T19:03:31.531Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585096 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-11-18T19:03:31.531Z
/* DDL */  select update_Column_Translation_From_AD_Element(581555) 
;

-- Column: ExternalSystem_Config_SAP_LocalFile.SFTP_Product_FileName_Pattern
-- 2022-11-18T19:03:32.132Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585097,581667,0,10,542258,'SFTP_Product_FileName_Pattern',TO_TIMESTAMP('2022-11-18 21:03:31','YYYY-MM-DD HH24:MI:SS'),100,'N','Muster, das zur Identifizierung von Produktdateien auf dem SFTP-Server verwendet wird. (Wenn nicht festgelegt, werden alle Dateien berücksichtigt)','de.metas.externalsystem',0,255,'Y','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','Produkt Dateinamen-Muster',0,0,TO_TIMESTAMP('2022-11-18 21:03:31','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-11-18T19:03:32.132Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585097 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-11-18T19:03:32.148Z
/* DDL */  select update_Column_Translation_From_AD_Element(581667) 
;

-- Column: ExternalSystem_Config_SAP_LocalFile.SFTP_Product_TargetDirectory
-- 2022-11-18T19:03:32.733Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585098,581603,0,10,542258,'SFTP_Product_TargetDirectory',TO_TIMESTAMP('2022-11-18 21:03:32','YYYY-MM-DD HH24:MI:SS'),100,'N','Verzeichnis, das zum Abrufen von Produkten vom sftp-Server verwendet wird. (Falls nicht festgelegt, werden die Dateien aus dem Stammverzeichnis der sftp-Verbindung gezogen)','de.metas.externalsystem',0,255,'Y','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','Produkt-Zielverzeichnis',0,0,TO_TIMESTAMP('2022-11-18 21:03:32','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-11-18T19:03:32.750Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585098 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-11-18T19:03:32.750Z
/* DDL */  select update_Column_Translation_From_AD_Element(581603) 
;

-- Column: ExternalSystem_Config_SAP_LocalFile.SFTP_Username
-- 2022-11-18T19:03:33.372Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585099,581556,0,10,542258,'SFTP_Username',TO_TIMESTAMP('2022-11-18 21:03:33','YYYY-MM-DD HH24:MI:SS'),100,'N','SFTP-Benutzername','Benutzername, der für die Authentifizierung am sftp-Server verwendet wird.','de.metas.externalsystem',0,255,'Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','Y','Benutzername',0,0,TO_TIMESTAMP('2022-11-18 21:03:33','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-11-18T19:03:33.372Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585099 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-11-18T19:03:33.372Z
/* DDL */  select update_Column_Translation_From_AD_Element(581556) 
;

-- Column: ExternalSystem_Config_SAP_LocalFile.SFTP_Password
-- 2022-11-18T19:03:41.731Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=585095
;

-- 2022-11-18T19:03:41.733Z
DELETE FROM AD_Column WHERE AD_Column_ID=585095
;

-- Column: ExternalSystem_Config_SAP_LocalFile.SFTP_HostName
-- 2022-11-18T19:03:48.153Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=585094
;

-- 2022-11-18T19:03:48.165Z
DELETE FROM AD_Column WHERE AD_Column_ID=585094
;

-- Column: ExternalSystem_Config_SAP_LocalFile.SFTP_Port
-- 2022-11-18T19:04:04.517Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=585096
;

-- 2022-11-18T19:04:04.518Z
DELETE FROM AD_Column WHERE AD_Column_ID=585096
;

-- Column: ExternalSystem_Config_SAP_LocalFile.SFTP_Username
-- 2022-11-18T19:04:18.121Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=585099
;

-- 2022-11-18T19:04:18.122Z
DELETE FROM AD_Column WHERE AD_Column_ID=585099
;

-- Column: ExternalSystem_Config_SAP_LocalFile.SFTP_BPartner_FileName_Pattern
-- 2022-11-18T19:04:42.973Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=585090
;

-- 2022-11-18T19:04:42.980Z
DELETE FROM AD_Column WHERE AD_Column_ID=585090
;

-- Column: ExternalSystem_Config_SAP_LocalFile.SFTP_BPartner_TargetDirectory
-- 2022-11-18T19:04:52.878Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=585091
;

-- 2022-11-18T19:04:52.880Z
DELETE FROM AD_Column WHERE AD_Column_ID=585091
;

-- Column: ExternalSystem_Config_SAP_LocalFile.SFTP_Product_FileName_Pattern
-- 2022-11-18T19:04:59.317Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=585097
;

-- 2022-11-18T19:04:59.318Z
DELETE FROM AD_Column WHERE AD_Column_ID=585097
;

-- Column: ExternalSystem_Config_SAP_LocalFile.SFTP_Product_TargetDirectory
-- 2022-11-18T19:05:05.698Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=585098
;

-- 2022-11-18T19:05:05.700Z
DELETE FROM AD_Column WHERE AD_Column_ID=585098
;

-- Element: SFTP_CreditLimit_FileName_Pattern
-- 2022-11-18T19:16:19.856Z
UPDATE AD_Element_Trl SET Name='Kreditlimit Dateinamen-Muster', PrintName='Kreditlimit Dateinamen-Muster',Updated=TO_TIMESTAMP('2022-11-18 21:16:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581657 AND AD_Language='de_CH'
;

-- 2022-11-18T19:16:19.860Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581657,'de_CH')
;

-- Element: SFTP_CreditLimit_FileName_Pattern
-- 2022-11-18T19:16:24.623Z
UPDATE AD_Element_Trl SET Name='Kreditlimit Dateinamen-Muster', PrintName='Kreditlimit Dateinamen-Muster',Updated=TO_TIMESTAMP('2022-11-18 21:16:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581657 AND AD_Language='de_DE'
;

-- 2022-11-18T19:16:24.624Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581657,'de_DE')
;

-- 2022-11-18T19:16:24.627Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581657,'de_DE')
;

-- Element: SFTP_CreditLimit_FileName_Pattern
-- 2022-11-18T19:16:29.114Z
UPDATE AD_Element_Trl SET Name='Credit Limit File Name Pattern', PrintName='Credit Limit File Name Pattern',Updated=TO_TIMESTAMP('2022-11-18 21:16:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581657 AND AD_Language='en_US'
;

-- 2022-11-18T19:16:29.116Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581657,'en_US')
;

-- Element: SFTP_CreditLimit_FileName_Pattern
-- 2022-11-18T19:16:38.078Z
UPDATE AD_Element_Trl SET Name='Credit Limit File Name Pattern', PrintName='Credit Limit File Name Pattern',Updated=TO_TIMESTAMP('2022-11-18 21:16:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581657 AND AD_Language='nl_NL'
;

-- 2022-11-18T19:16:38.080Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581657,'nl_NL')
;

-- 2022-11-18T19:17:26.312Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581710,0,'LocalFile_CreditLimit_FileName_Pattern',TO_TIMESTAMP('2022-11-18 21:17:26','YYYY-MM-DD HH24:MI:SS'),100,'Muster, das zur Identifizierung von Kreditlimit-Dateien auf dem SFTP-Server verwendet wird. (Wenn nicht angegeben, werden alle Dateien berücksichtigt). ','de.metas.externalsystem','Y','Kreditlimit Dateinamen-Muster','Kreditlimit Dateinamen-Muster',TO_TIMESTAMP('2022-11-18 21:17:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-18T19:17:26.312Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581710 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: LocalFile_CreditLimit_FileName_Pattern
-- 2022-11-18T19:18:01.763Z
UPDATE AD_Element_Trl SET Description='Muster, das zur Identifizierung von Kreditlimit-Dateien auf dem lokalen Rechner verwendet wird. (Wenn nicht angegeben, werden alle Dateien berücksichtigt). ',Updated=TO_TIMESTAMP('2022-11-18 21:18:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581710 AND AD_Language='nl_NL'
;

-- 2022-11-18T19:18:01.765Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581710,'nl_NL')
;

-- Element: LocalFile_CreditLimit_FileName_Pattern
-- 2022-11-18T19:18:10.872Z
UPDATE AD_Element_Trl SET Description='Muster, das zur Identifizierung von Kreditlimit-Dateien auf dem lokalen Rechner verwendet wird. (Wenn nicht angegeben, werden alle Dateien berücksichtigt). ',Updated=TO_TIMESTAMP('2022-11-18 21:18:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581710 AND AD_Language='de_CH'
;

-- 2022-11-18T19:18:10.874Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581710,'de_CH')
;

-- Element: LocalFile_CreditLimit_FileName_Pattern
-- 2022-11-18T19:18:16.308Z
UPDATE AD_Element_Trl SET Description='Muster, das zur Identifizierung von Kreditlimit-Dateien auf dem lokalen Rechner verwendet wird. (Wenn nicht angegeben, werden alle Dateien berücksichtigt). ',Updated=TO_TIMESTAMP('2022-11-18 21:18:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581710 AND AD_Language='de_DE'
;

-- 2022-11-18T19:18:16.309Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581710,'de_DE')
;

-- 2022-11-18T19:18:16.310Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581710,'de_DE')
;

-- Element: LocalFile_CreditLimit_FileName_Pattern
-- 2022-11-18T19:19:15.245Z
UPDATE AD_Element_Trl SET Description='Ant-style pattern used to identify credit limit files on the local machine. (If not set, all files are considered)', Name='Credit Limit File Name Pattern', PrintName='Credit Limit File Name Pattern',Updated=TO_TIMESTAMP('2022-11-18 21:19:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581710 AND AD_Language='en_US'
;

-- 2022-11-18T19:19:15.246Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581710,'en_US')
;

-- Element: LocalFile_CreditLimit_FileName_Pattern
-- 2022-11-18T19:19:59.057Z
UPDATE AD_Element_Trl SET Description='Ant-style pattern used to identify credit limit files on the SFTP-Server. (If not set, all files are considered)', Name='Credit Limit File Name Pattern', PrintName='Credit Limit File Name Pattern',Updated=TO_TIMESTAMP('2022-11-18 21:19:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581710 AND AD_Language='nl_NL'
;

-- 2022-11-18T19:19:59.058Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581710,'nl_NL')
;

-- Column: ExternalSystem_Config_SAP_LocalFile.LocalFile_CreditLimit_FileName_Pattern
-- 2022-11-18T19:20:51.641Z
UPDATE AD_Column SET AD_Element_ID=581710, ColumnName='LocalFile_CreditLimit_FileName_Pattern', Description='Muster, das zur Identifizierung von Kreditlimit-Dateien auf dem lokalen Rechner verwendet wird. (Wenn nicht angegeben, werden alle Dateien berücksichtigt). ', Help=NULL, Name='Kreditlimit Dateinamen-Muster',Updated=TO_TIMESTAMP('2022-11-18 21:20:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585092
;

-- 2022-11-18T19:20:51.644Z
UPDATE AD_Field SET Name='Kreditlimit Dateinamen-Muster', Description='Muster, das zur Identifizierung von Kreditlimit-Dateien auf dem lokalen Rechner verwendet wird. (Wenn nicht angegeben, werden alle Dateien berücksichtigt). ', Help=NULL WHERE AD_Column_ID=585092
;

-- 2022-11-18T19:20:51.645Z
/* DDL */  select update_Column_Translation_From_AD_Element(581710)
;

-- 2022-11-18T19:20:52.373Z
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_SAP_LocalFile','ALTER TABLE public.ExternalSystem_Config_SAP_LocalFile ADD COLUMN LocalFile_CreditLimit_FileName_Pattern VARCHAR(255)')
;

-- 2022-11-18T19:23:45.557Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581711,0,'LocalFile_CreditLimit_TargetDirectory',TO_TIMESTAMP('2022-11-18 21:23:45','YYYY-MM-DD HH24:MI:SS'),100,'Das Verzeichnis, das zum Abrufen der Kreditlimits vom lokalen Rechner verwendet wird. (Wenn hier kein Wert angegeben wird, werden die Dateien aus dem konfigurierten lokalen Stammverzeichnis gezogen).','U','Y','Kreditlimits Zielverzeichnis','Kreditlimits Zielverzeichnis',TO_TIMESTAMP('2022-11-18 21:23:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-18T19:23:45.557Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581711 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2022-11-18T19:24:09.594Z
UPDATE AD_Element SET EntityType='de.metas.externalsystem',Updated=TO_TIMESTAMP('2022-11-18 21:24:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581711
;

-- 2022-11-18T19:24:09.596Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581711,'de_DE')
;

-- Element: SFTP_CreditLimit_TargetDirectory
-- 2022-11-18T19:24:25.219Z
UPDATE AD_Element_Trl SET Name='Kreditlimits Zielverzeichnis',Updated=TO_TIMESTAMP('2022-11-18 21:24:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581623 AND AD_Language='de_CH'
;

-- 2022-11-18T19:24:25.221Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581623,'de_CH')
;

-- Element: SFTP_CreditLimit_TargetDirectory
-- 2022-11-18T19:24:30.169Z
UPDATE AD_Element_Trl SET PrintName='Kreditlimits Zielverzeichnis',Updated=TO_TIMESTAMP('2022-11-18 21:24:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581623 AND AD_Language='de_CH'
;

-- 2022-11-18T19:24:30.172Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581623,'de_CH')
;

-- Element: SFTP_CreditLimit_TargetDirectory
-- 2022-11-18T19:24:42.459Z
UPDATE AD_Element_Trl SET Name='Kreditlimits Zielverzeichnis', PrintName='Kreditlimits Zielverzeichnis',Updated=TO_TIMESTAMP('2022-11-18 21:24:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581623 AND AD_Language='de_DE'
;

-- 2022-11-18T19:24:42.460Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581623,'de_DE')
;

-- 2022-11-18T19:24:42.461Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581623,'de_DE')
;

-- Element: SFTP_CreditLimit_TargetDirectory
-- 2022-11-18T19:24:50.258Z
UPDATE AD_Element_Trl SET Name='Credit Limit TargetDirectory', PrintName='Credit Limit TargetDirectory',Updated=TO_TIMESTAMP('2022-11-18 21:24:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581623 AND AD_Language='en_US'
;

-- 2022-11-18T19:24:50.260Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581623,'en_US')
;

-- Element: SFTP_CreditLimit_TargetDirectory
-- 2022-11-18T19:24:59.825Z
UPDATE AD_Element_Trl SET Name='Credit Limit TargetDirectory', PrintName='Credit Limit TargetDirectory',Updated=TO_TIMESTAMP('2022-11-18 21:24:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581623 AND AD_Language='nl_NL'
;

-- 2022-11-18T19:24:59.826Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581623,'nl_NL')
;

-- Element: SFTP_CreditLimit_TargetDirectory
-- 2022-11-18T19:25:15.421Z
UPDATE AD_Element_Trl SET Description='The directory used to retrieve credit limits from the sftp server. (If no value is specified here, the files are pulled from the root directory of the sftp server).',Updated=TO_TIMESTAMP('2022-11-18 21:25:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581623 AND AD_Language='nl_NL'
;

-- 2022-11-18T19:25:15.423Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581623,'nl_NL')
;

-- Element: LocalFile_CreditLimit_TargetDirectory
-- 2022-11-18T19:25:58.520Z
UPDATE AD_Element_Trl SET Description='The directory used to retrieve credit limits from the sftp server. (If no value is specified here, the files are pulled from the root directory of the sftp server).', Name='Credit Limit TargetDirectory', PrintName='Credit Limit TargetDirectory',Updated=TO_TIMESTAMP('2022-11-18 21:25:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581711 AND AD_Language='en_US'
;

-- 2022-11-18T19:25:58.522Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581711,'en_US')
;

-- Element: LocalFile_CreditLimit_TargetDirectory
-- 2022-11-18T19:26:21.493Z
UPDATE AD_Element_Trl SET Name='Credit Limit TargetDirectory', PrintName='Credit Limit TargetDirectory',Updated=TO_TIMESTAMP('2022-11-18 21:26:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581711 AND AD_Language='nl_NL'
;

-- 2022-11-18T19:26:21.495Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581711,'nl_NL')
;

-- Element: LocalFile_CreditLimit_TargetDirectory
-- 2022-11-18T19:26:54.279Z
UPDATE AD_Element_Trl SET Description='The directory used to retrieve credit limits from the local machine. (If no value is specified here, the files are pulled from the configured local root location).',Updated=TO_TIMESTAMP('2022-11-18 21:26:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581711 AND AD_Language='en_US'
;

-- 2022-11-18T19:26:54.281Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581711,'en_US')
;

-- Element: LocalFile_CreditLimit_TargetDirectory
-- 2022-11-18T19:27:03.935Z
UPDATE AD_Element_Trl SET Description='The directory used to retrieve credit limits from the local machine. (If no value is specified here, the files are pulled from the configured local root location).',Updated=TO_TIMESTAMP('2022-11-18 21:27:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581711 AND AD_Language='nl_NL'
;

-- 2022-11-18T19:27:03.936Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581711,'nl_NL')
;

-- Column: ExternalSystem_Config_SAP_LocalFile.LocalFile_CreditLimit_TargetDirectory
-- 2022-11-18T19:27:34.372Z
UPDATE AD_Column SET AD_Element_ID=581711, ColumnName='LocalFile_CreditLimit_TargetDirectory', Description='Das Verzeichnis, das zum Abrufen der Kreditlimits vom lokalen Rechner verwendet wird. (Wenn hier kein Wert angegeben wird, werden die Dateien aus dem konfigurierten lokalen Stammverzeichnis gezogen).', Help=NULL, Name='Kreditlimits Zielverzeichnis',Updated=TO_TIMESTAMP('2022-11-18 21:27:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585093
;

-- 2022-11-18T19:27:34.373Z
UPDATE AD_Field SET Name='Kreditlimits Zielverzeichnis', Description='Das Verzeichnis, das zum Abrufen der Kreditlimits vom lokalen Rechner verwendet wird. (Wenn hier kein Wert angegeben wird, werden die Dateien aus dem konfigurierten lokalen Stammverzeichnis gezogen).', Help=NULL WHERE AD_Column_ID=585093
;

-- 2022-11-18T19:27:34.374Z
/* DDL */  select update_Column_Translation_From_AD_Element(581711)
;

-- 2022-11-18T19:27:35.018Z
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_SAP_LocalFile','ALTER TABLE public.ExternalSystem_Config_SAP_LocalFile ADD COLUMN LocalFile_CreditLimit_TargetDirectory VARCHAR(255)')
;

-- Element: LocalFile_CreditLimit_FileName_Pattern
-- 2022-11-18T19:28:32.165Z
UPDATE AD_Element_Trl SET Description='Ant-style pattern used to identify credit limit files on the local machine. (If not set, all files are considered)',Updated=TO_TIMESTAMP('2022-11-18 21:28:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581710 AND AD_Language='nl_NL'
;

-- 2022-11-18T19:28:32.166Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581710,'nl_NL')
;

-- Element: SFTP_CreditLimit_FileName_Pattern
-- 2022-11-18T19:29:21.314Z
UPDATE AD_Element_Trl SET Description='Ant-style pattern used to identify credit limit files on the SFTP-Server. (If not set, all files are considered)',Updated=TO_TIMESTAMP('2022-11-18 21:29:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581657 AND AD_Language='nl_NL'
;

-- 2022-11-18T19:29:21.316Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581657,'nl_NL')
;

-- Field: External system config SAP(541631,de.metas.externalsystem) -> SFTP(546672,de.metas.externalsystem) -> Kreditlimits Zielverzeichnis
-- Column: ExternalSystem_Config_SAP_SFTP.SFTP_CreditLimit_TargetDirectory
-- 2022-11-18T19:33:57.177Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,585089,708132,0,546672,0,TO_TIMESTAMP('2022-11-18 21:33:57','YYYY-MM-DD HH24:MI:SS'),100,'Das Verzeichnis, das zum Abrufen der Kreditlimits vom sftp-Server verwendet wird. (Wenn hier kein Wert angegeben wird, werden die Dateien aus dem Stammverzeichnis des sftp-Servers abgerufen).',0,'de.metas.externalsystem',0,'Y','Y','N','N','N','N','N','N','Kreditlimits Zielverzeichnis',0,160,0,1,1,TO_TIMESTAMP('2022-11-18 21:33:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-18T19:33:57.193Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=708132 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-18T19:33:57.199Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581623)
;

-- 2022-11-18T19:33:57.199Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708132
;

-- 2022-11-18T19:33:57.199Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708132)
;

-- Field: External system config SAP(541631,de.metas.externalsystem) -> SFTP(546672,de.metas.externalsystem) -> Kreditlimit Dateinamen-Muster
-- Column: ExternalSystem_Config_SAP_SFTP.SFTP_CreditLimit_FileName_Pattern
-- 2022-11-18T19:34:24.845Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,585088,708133,0,546672,0,TO_TIMESTAMP('2022-11-18 21:34:24','YYYY-MM-DD HH24:MI:SS'),100,'Muster, das zur Identifizierung von Kreditlimit-Dateien auf dem SFTP-Server verwendet wird. (Wenn nicht angegeben, werden alle Dateien berücksichtigt). ',0,'de.metas.externalsystem',0,'Y','Y','N','N','N','N','N','N','Kreditlimit Dateinamen-Muster',0,170,0,1,1,TO_TIMESTAMP('2022-11-18 21:34:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-18T19:34:24.861Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=708133 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-18T19:34:24.861Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581657)
;

-- 2022-11-18T19:34:24.861Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708133
;

-- 2022-11-18T19:34:24.861Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708133)
;

-- UI Element: External system config SAP(541631,de.metas.externalsystem) -> SFTP(546672,de.metas.externalsystem) -> main -> 10 -> main.Kreditlimits Zielverzeichnis
-- Column: ExternalSystem_Config_SAP_SFTP.SFTP_CreditLimit_TargetDirectory
-- 2022-11-18T19:34:59.104Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,708132,0,546672,613535,550021,'F',TO_TIMESTAMP('2022-11-18 21:34:58','YYYY-MM-DD HH24:MI:SS'),100,'Das Verzeichnis, das zum Abrufen der Kreditlimits vom sftp-Server verwendet wird. (Wenn hier kein Wert angegeben wird, werden die Dateien aus dem Stammverzeichnis des sftp-Servers abgerufen).','Y','N','N','Y','N','N','N',0,'Kreditlimits Zielverzeichnis',130,0,0,TO_TIMESTAMP('2022-11-18 21:34:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: External system config SAP(541631,de.metas.externalsystem) -> SFTP(546672,de.metas.externalsystem) -> main -> 10 -> main.Kreditlimit Dateinamen-Muster
-- Column: ExternalSystem_Config_SAP_SFTP.SFTP_CreditLimit_FileName_Pattern
-- 2022-11-18T19:35:11.369Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,708133,0,546672,613536,550021,'F',TO_TIMESTAMP('2022-11-18 21:35:11','YYYY-MM-DD HH24:MI:SS'),100,'Muster, das zur Identifizierung von Kreditlimit-Dateien auf dem SFTP-Server verwendet wird. (Wenn nicht angegeben, werden alle Dateien berücksichtigt). ','Y','N','N','Y','N','N','N',0,'Kreditlimit Dateinamen-Muster',140,0,0,TO_TIMESTAMP('2022-11-18 21:35:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Field: External system config SAP(541631,de.metas.externalsystem) -> Lokale Datei(546673,de.metas.externalsystem) -> Kreditlimits Zielverzeichnis
-- Column: ExternalSystem_Config_SAP_LocalFile.LocalFile_CreditLimit_TargetDirectory
-- 2022-11-18T19:35:56.247Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,585093,708134,0,546673,0,TO_TIMESTAMP('2022-11-18 21:35:56','YYYY-MM-DD HH24:MI:SS'),100,'Das Verzeichnis, das zum Abrufen der Kreditlimits vom lokalen Rechner verwendet wird. (Wenn hier kein Wert angegeben wird, werden die Dateien aus dem konfigurierten lokalen Stammverzeichnis gezogen).',0,'de.metas.externalsystem',0,'Y','Y','Y','N','N','N','N','N','Kreditlimits Zielverzeichnis',0,130,0,1,1,TO_TIMESTAMP('2022-11-18 21:35:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-18T19:35:56.247Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=708134 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-18T19:35:56.247Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581711)
;

-- 2022-11-18T19:35:56.259Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708134
;

-- 2022-11-18T19:35:56.259Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708134)
;

-- Field: External system config SAP(541631,de.metas.externalsystem) -> Lokale Datei(546673,de.metas.externalsystem) -> Kreditlimit Dateinamen-Muster
-- Column: ExternalSystem_Config_SAP_LocalFile.LocalFile_CreditLimit_FileName_Pattern
-- 2022-11-18T19:36:32.815Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,585092,708135,0,546673,0,TO_TIMESTAMP('2022-11-18 21:36:32','YYYY-MM-DD HH24:MI:SS'),100,'Muster, das zur Identifizierung von Kreditlimit-Dateien auf dem lokalen Rechner verwendet wird. (Wenn nicht angegeben, werden alle Dateien berücksichtigt). ',0,'de.metas.externalsystem',0,'Y','Y','N','N','N','N','N','N','Kreditlimit Dateinamen-Muster',0,140,0,1,1,TO_TIMESTAMP('2022-11-18 21:36:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-18T19:36:32.815Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=708135 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-18T19:36:32.815Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581710)
;

-- 2022-11-18T19:36:32.822Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708135
;

-- 2022-11-18T19:36:32.822Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708135)
;

-- UI Element: External system config SAP(541631,de.metas.externalsystem) -> Lokale Datei(546673,de.metas.externalsystem) -> main -> 10 -> main.Kreditlimits Zielverzeichnis
-- Column: ExternalSystem_Config_SAP_LocalFile.LocalFile_CreditLimit_TargetDirectory
-- 2022-11-18T19:37:05.914Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,708134,0,546673,613537,550024,'F',TO_TIMESTAMP('2022-11-18 21:37:05','YYYY-MM-DD HH24:MI:SS'),100,'Das Verzeichnis, das zum Abrufen der Kreditlimits vom lokalen Rechner verwendet wird. (Wenn hier kein Wert angegeben wird, werden die Dateien aus dem konfigurierten lokalen Stammverzeichnis gezogen).','Y','N','N','Y','N','N','N',0,'Kreditlimits Zielverzeichnis',100,0,0,TO_TIMESTAMP('2022-11-18 21:37:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: External system config SAP(541631,de.metas.externalsystem) -> Lokale Datei(546673,de.metas.externalsystem) -> main -> 10 -> main.Kreditlimit Dateinamen-Muster
-- Column: ExternalSystem_Config_SAP_LocalFile.LocalFile_CreditLimit_FileName_Pattern
-- 2022-11-18T19:37:17.572Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,708135,0,546673,613538,550024,'F',TO_TIMESTAMP('2022-11-18 21:37:17','YYYY-MM-DD HH24:MI:SS'),100,'Muster, das zur Identifizierung von Kreditlimit-Dateien auf dem lokalen Rechner verwendet wird. (Wenn nicht angegeben, werden alle Dateien berücksichtigt). ','Y','N','N','Y','N','N','N',0,'Kreditlimit Dateinamen-Muster',110,0,0,TO_TIMESTAMP('2022-11-18 21:37:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: External system config SAP(541631,de.metas.externalsystem) -> Lokale Datei(546673,de.metas.externalsystem) -> main -> 10 -> main.Kreditlimits Zielverzeichnis
-- Column: ExternalSystem_Config_SAP_LocalFile.LocalFile_CreditLimit_TargetDirectory
-- 2022-11-18T19:37:34.667Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2022-11-18 21:37:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613537
;

-- Reference: External_Request SAP
-- Value: startCreditLimitSync
-- ValueName: Start Credit Limits Synchronization
-- 2022-11-18T19:40:58.074Z
UPDATE AD_Ref_List SET Value='startCreditLimitSync',Updated=TO_TIMESTAMP('2022-11-18 21:40:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=543329
;

-- Reference Item: External_Request SAP -> startCreditLimitSync_Start Credit Limits Synchronization
-- 2022-11-18T19:42:33.988Z
UPDATE AD_Ref_List_Trl SET Description='Starts the credit limits sychronization with SAP external system. The credit limit files are fetched from the configured sftp server.', Name='Start Credit Limits Synchronization SFTP',Updated=TO_TIMESTAMP('2022-11-18 21:42:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543329
;

-- Reference Item: External_Request SAP -> startCreditLimitSync_Start Credit Limits Synchronization
-- 2022-11-18T19:42:38.725Z
UPDATE AD_Ref_List_Trl SET Name='Start Credit Limits Synchronization SFTP',Updated=TO_TIMESTAMP('2022-11-18 21:42:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Ref_List_ID=543329
;

-- Reference Item: External_Request SAP -> startCreditLimitSync_Start Credit Limits Synchronization
-- 2022-11-18T19:42:46.789Z
UPDATE AD_Ref_List_Trl SET Description='Starts the credit limits sychronization with SAP external system. The credit limit files are fetched from the configured sftp server.',Updated=TO_TIMESTAMP('2022-11-18 21:42:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Ref_List_ID=543329
;

-- Reference Item: External_Request SAP -> startCreditLimitSync_Start Credit Limits Synchronization
-- 2022-11-18T19:42:58.114Z
UPDATE AD_Ref_List_Trl SET Name='Start der Kreditlimitsynchronisation SFTP',Updated=TO_TIMESTAMP('2022-11-18 21:42:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543329
;

-- Reference Item: External_Request SAP -> startCreditLimitSync_Start Credit Limits Synchronization
-- 2022-11-18T19:43:02.085Z
UPDATE AD_Ref_List_Trl SET Name='Start der Kreditlimitsynchronisation SFTP',Updated=TO_TIMESTAMP('2022-11-18 21:43:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543329
;

-- Reference Item: External_Request SAP -> startCreditLimitSync_Start Credit Limits Synchronization
-- 2022-11-18T19:43:25.018Z
UPDATE AD_Ref_List_Trl SET Description='Startet die Synchronisation der Kreditlimits mit dem externen SAP-System. Die Kreditlimitdateien werden von dem konfigurierten sftp-Server abgerufen.',Updated=TO_TIMESTAMP('2022-11-18 21:43:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543329
;

-- Reference Item: External_Request SAP -> startCreditLimitSync_Start Credit Limits Synchronization
-- 2022-11-18T19:43:30.458Z
UPDATE AD_Ref_List_Trl SET Description='Startet die Synchronisation der Kreditlimits mit dem externen SAP-System. Die Kreditlimitdateien werden von dem konfigurierten sftp-Server abgerufen.',Updated=TO_TIMESTAMP('2022-11-18 21:43:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543329
;

-- Reference: External_Request SAP
-- Value: startCreditLimitSync
-- ValueName: Start Credit Limits Synchronization
-- 2022-11-18T19:44:01.805Z
UPDATE AD_Ref_List SET Name='Start der Kreditlimitsynchronisation SFTP',Updated=TO_TIMESTAMP('2022-11-18 21:44:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=543329
;

-- Reference: External_Request SAP
-- Value: startCreditLimitSync
-- ValueName: Start Credit Limits Synchronization
-- 2022-11-18T19:44:22.146Z
UPDATE AD_Ref_List SET Description='Startet die Kreditlimitsynchronisation mit dem externen SAP-System. Die Kreditlimitdateien werden von dem konfigurierten sftp-Server abgerufen.',Updated=TO_TIMESTAMP('2022-11-18 21:44:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=543329
;

-- Reference: External_Request SAP
-- Value: startCreditLimitSyncSFTP
-- ValueName: Start Credit Limits Synchronization
-- 2022-11-18T19:46:07.067Z
UPDATE AD_Ref_List SET Value='startCreditLimitSyncSFTP',Updated=TO_TIMESTAMP('2022-11-18 21:46:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=543329
;

-- Reference: External_Request SAP
-- Value: startCreditLimitSyncLocalFile
-- ValueName: Start Credit Limits Synchronization Local File
-- 2022-11-18T19:48:27.682Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,543351,541661,TO_TIMESTAMP('2022-11-18 21:48:27','YYYY-MM-DD HH24:MI:SS'),100,'Startet die Kreditlimitsynchronisation mit dem externen SAP-System. Die Kreditlimitdateien werden von vom lokalen Rechner abgerufen.','U','Y','Start der Kreditlimitsynchronisation Local File',TO_TIMESTAMP('2022-11-18 21:48:27','YYYY-MM-DD HH24:MI:SS'),100,'startCreditLimitSyncLocalFile','Start Credit Limits Synchronization Local File')
;

-- 2022-11-18T19:48:27.684Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543351 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference Item: External_Request SAP -> startCreditLimitSyncLocalFile_Start Credit Limits Synchronization Local File
-- 2022-11-18T19:49:22.309Z
UPDATE AD_Ref_List_Trl SET Description='Starts the credit limits sychronization with SAP external system. The credit limit files are fetched from the local machine.', Name='Start Credit Limits Synchronization SFTP',Updated=TO_TIMESTAMP('2022-11-18 21:49:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543351
;

-- Reference Item: External_Request SAP -> startCreditLimitSyncLocalFile_Start Credit Limits Synchronization Local File
-- 2022-11-18T19:49:41.982Z
UPDATE AD_Ref_List_Trl SET Description='Starts the credit limits sychronization with SAP external system. The credit limit files are fetched from the local machine.', Name='Start Credit Limits Synchronization SFTP',Updated=TO_TIMESTAMP('2022-11-18 21:49:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Ref_List_ID=543351
;

-- Reference Item: External_Request SAP -> stopBPartnerSyncSFTP_import
-- 2022-11-18T19:51:13.744Z
UPDATE AD_Ref_List_Trl SET Name='Stop der Geschäftspartner-Synchronisation SFTP',Updated=TO_TIMESTAMP('2022-11-18 21:51:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543317
;

-- Reference Item: External_Request SAP -> stopBPartnerSyncSFTP_import
-- 2022-11-18T19:51:32.184Z
UPDATE AD_Ref_List_Trl SET Name='Stop der Geschäftspartner-Synchronisation SFTP',Updated=TO_TIMESTAMP('2022-11-18 21:51:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543317
;

-- Reference: External_Request SAP
-- Value: stopBPartnerSyncSFTP
-- ValueName: import
-- 2022-11-18T19:51:43.841Z
UPDATE AD_Ref_List SET Name='Stop der Geschäftspartner-Synchronisation SFTP',Updated=TO_TIMESTAMP('2022-11-18 21:51:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=543317
;

-- Reference: External_Request SAP
-- Value: stopProductSyncSFTP
-- ValueName: import
-- 2022-11-18T19:52:52.965Z
UPDATE AD_Ref_List SET Name='Stop der Produktsynchronisation SFTP',Updated=TO_TIMESTAMP('2022-11-18 21:52:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=543301
;

-- Reference Item: External_Request SAP -> stopProductSyncSFTP_import
-- 2022-11-18T19:52:58.817Z
UPDATE AD_Ref_List_Trl SET Name='Stop der Produktsynchronisation SFTP',Updated=TO_TIMESTAMP('2022-11-18 21:52:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543301
;

-- Reference Item: External_Request SAP -> stopProductSyncSFTP_import
-- 2022-11-18T19:53:02.276Z
UPDATE AD_Ref_List_Trl SET Name='Stop der Produktsynchronisation SFTP',Updated=TO_TIMESTAMP('2022-11-18 21:53:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543301
;

-- Reference: External_Request SAP
-- Value: stopCreditLimitsSyncSFTP
-- ValueName: Stop Credit Limits Synchronization
-- 2022-11-18T19:53:59.952Z
UPDATE AD_Ref_List SET Name='Stop der Kreditlimitsynchronisation SFTP', Value='stopCreditLimitsSyncSFTP',Updated=TO_TIMESTAMP('2022-11-18 21:53:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=543330
;

-- Reference Item: External_Request SAP -> stopCreditLimitsSyncSFTP_Stop Credit Limits Synchronization
-- 2022-11-18T19:54:40.388Z
UPDATE AD_Ref_List_Trl SET Description='Stoppt die Synchronisation des Kreditlimits mit dem externen SAP-System vom konfigurierten SFTP-Server.', Name='Stop Kreditlimit-Synchronisation SFTP',Updated=TO_TIMESTAMP('2022-11-18 21:54:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543330
;

-- Reference Item: External_Request SAP -> stopCreditLimitsSyncSFTP_Stop Credit Limits Synchronization
-- 2022-11-18T19:54:46.797Z
UPDATE AD_Ref_List_Trl SET Description='Stoppt die Synchronisation des Kreditlimits mit dem externen SAP-System vom konfigurierten SFTP-Server.', Name='Kreditlimit-Synchronisation stoppen SFTP',Updated=TO_TIMESTAMP('2022-11-18 21:54:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543330
;

-- Reference Item: External_Request SAP -> stopCreditLimitsSyncSFTP_Stop Credit Limits Synchronization
-- 2022-11-18T19:55:09.666Z
UPDATE AD_Ref_List_Trl SET Description='Stops the credit limits synchronization with SAP external system from the configured SFTP server.', Name='Stop Credit Limits Synchronization SFTP',Updated=TO_TIMESTAMP('2022-11-18 21:55:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543330
;

-- Reference Item: External_Request SAP -> stopCreditLimitsSyncSFTP_Stop Credit Limits Synchronization
-- 2022-11-18T19:55:22.543Z
UPDATE AD_Ref_List_Trl SET Description='Stops the credit limits synchronization with SAP external system from the configured SFTP server.', Name='Stop Credit Limits Synchronization SFTP',Updated=TO_TIMESTAMP('2022-11-18 21:55:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Ref_List_ID=543330
;

-- Reference: External_Request SAP
-- Value: stopCreditLimitsSyncSFTP
-- ValueName: Stop Credit Limits Synchronization
-- 2022-11-18T19:55:43.544Z
UPDATE AD_Ref_List SET Description='Stoppt die Kreditlimitsynchronisation mit dem externen SAP-System vom konfigurierten SFTP-Server.',Updated=TO_TIMESTAMP('2022-11-18 21:55:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=543330
;

-- Reference: External_Request SAP
-- Value: stopCreditLimitSyncLocalFile
-- ValueName: Stop Credit Limit Synchronization Local File
-- 2022-11-18T19:56:50.669Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,543352,541661,TO_TIMESTAMP('2022-11-18 21:56:50','YYYY-MM-DD HH24:MI:SS'),100,'Stoppt die Kreditlimitsynchronisation mit dem externen SAP-System vom lokalen Rechner aus.','U','Y','Stop der Kreditlimitsynchronisation Local File',TO_TIMESTAMP('2022-11-18 21:56:50','YYYY-MM-DD HH24:MI:SS'),100,'stopCreditLimitSyncLocalFile','Stop Credit Limit Synchronization Local File')
;

-- 2022-11-18T19:56:50.669Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543352 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference Item: External_Request SAP -> stopCreditLimitSyncLocalFile_Stop Credit Limit Synchronization Local File
-- 2022-11-18T19:57:05.876Z
UPDATE AD_Ref_List_Trl SET Name='Stop der Kreditlimitsynchronisation lokale Datei',Updated=TO_TIMESTAMP('2022-11-18 21:57:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543352
;

-- Reference Item: External_Request SAP -> stopCreditLimitSyncLocalFile_Stop Credit Limit Synchronization Local File
-- 2022-11-18T19:57:09.242Z
UPDATE AD_Ref_List_Trl SET Name='Stop der Kreditlimitsynchronisation lokale Datei',Updated=TO_TIMESTAMP('2022-11-18 21:57:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543352
;

-- Reference: External_Request SAP
-- Value: stopCreditLimitSyncLocalFile
-- ValueName: Stop Credit Limit Synchronization Local File
-- 2022-11-18T19:58:15.789Z
UPDATE AD_Ref_List SET EntityType='de.metas.externalsystem', Name='Stop der Kreditlimitsynchronisation lokale Datei',Updated=TO_TIMESTAMP('2022-11-18 21:58:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=543352
;

-- Reference Item: External_Request SAP -> stopCreditLimitSyncLocalFile_Stop Credit Limit Synchronization Local File
-- 2022-11-18T19:58:53.480Z
UPDATE AD_Ref_List_Trl SET Description='Stops the credit limits synchronization with SAP external system from the local machine.', Name='Stop Credit Limits Synchronization Local File',Updated=TO_TIMESTAMP('2022-11-18 21:58:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543352
;

-- Reference Item: External_Request SAP -> stopCreditLimitSyncLocalFile_Stop Credit Limit Synchronization Local File
-- 2022-11-18T19:59:03.327Z
UPDATE AD_Ref_List_Trl SET Description='Stops the credit limits synchronization with SAP external system from the local machine.', Name='Stop Credit Limits Synchronization Local File',Updated=TO_TIMESTAMP('2022-11-18 21:59:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Ref_List_ID=543352
;

-- Reference: External_Request SAP
-- Value: stopCreditLimitSyncSFTP
-- ValueName: Stop Credit Limits Synchronization
-- 2022-11-18T19:59:39.225Z
UPDATE AD_Ref_List SET Value='stopCreditLimitSyncSFTP',Updated=TO_TIMESTAMP('2022-11-18 21:59:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=543330
;

-- Reference Item: External_Request SAP -> stopCreditLimitSyncSFTP_Stop Credit Limits Synchronization
-- 2022-11-18T19:59:51.653Z
UPDATE AD_Ref_List_Trl SET Name='Stop Credit Limit Synchronization SFTP',Updated=TO_TIMESTAMP('2022-11-18 21:59:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543330
;

-- Reference Item: External_Request SAP -> stopCreditLimitSyncSFTP_Stop Credit Limits Synchronization
-- 2022-11-18T19:59:57.980Z
UPDATE AD_Ref_List_Trl SET Name='Stop Credit Limit Synchronization SFTP',Updated=TO_TIMESTAMP('2022-11-18 21:59:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Ref_List_ID=543330
;

-- Reference Item: External_Request SAP -> stopCreditLimitSyncSFTP_Stop Credit Limits Synchronization
-- 2022-11-18T20:00:02.024Z
UPDATE AD_Ref_List_Trl SET Description='Stops the credit limit synchronization with SAP external system from the configured SFTP server.',Updated=TO_TIMESTAMP('2022-11-18 22:00:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543330
;

-- Reference Item: External_Request SAP -> stopCreditLimitSyncSFTP_Stop Credit Limits Synchronization
-- 2022-11-18T20:00:06.736Z
UPDATE AD_Ref_List_Trl SET Description='Stops the credit limit synchronization with SAP external system from the configured SFTP server.',Updated=TO_TIMESTAMP('2022-11-18 22:00:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Ref_List_ID=543330
;

-- Reference Item: External_Request SAP -> startCreditLimitSyncSFTP_Start Credit Limits Synchronization
-- 2022-11-18T20:00:45.253Z
UPDATE AD_Ref_List_Trl SET Description='Starts the credit limit sychronization with SAP external system. The credit limit files are fetched from the configured sftp server.', Name='Start Credit Limit Synchronization SFTP',Updated=TO_TIMESTAMP('2022-11-18 22:00:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543329
;

-- Reference Item: External_Request SAP -> startCreditLimitSyncSFTP_Start Credit Limits Synchronization
-- 2022-11-18T20:00:53.811Z
UPDATE AD_Ref_List_Trl SET Description='Starts the credit limit sychronization with SAP external system. The credit limit files are fetched from the configured sftp server.', Name='Start Credit Limit Synchronization SFTP',Updated=TO_TIMESTAMP('2022-11-18 22:00:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Ref_List_ID=543329
;

-- Reference Item: External_Request SAP -> startCreditLimitSyncLocalFile_Start Credit Limits Synchronization Local File
-- 2022-11-18T20:01:29.757Z
UPDATE AD_Ref_List_Trl SET Description='Starts the credit limit sychronization with SAP external system. The credit limit files are fetched from the local machine.', Name='Start Credit Limit Synchronization SFTP',Updated=TO_TIMESTAMP('2022-11-18 22:01:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543351
;

-- Reference Item: External_Request SAP -> startCreditLimitSyncLocalFile_Start Credit Limits Synchronization Local File
-- 2022-11-18T20:01:35.476Z
UPDATE AD_Ref_List_Trl SET Description='Starts the credit limit sychronization with SAP external system. The credit limit files are fetched from the local machine.', Name='Start Credit Limit Synchronization SFTP',Updated=TO_TIMESTAMP('2022-11-18 22:01:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Ref_List_ID=543351
;

-- Value: ExternalSystemConfigSAPDuplicateFileLookupDetails
-- 2022-11-18T20:34:16.977Z
UPDATE AD_Message SET Value='ExternalSystemConfigSAPDuplicateFileLookupDetails',Updated=TO_TIMESTAMP('2022-11-18 22:34:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=545212
;