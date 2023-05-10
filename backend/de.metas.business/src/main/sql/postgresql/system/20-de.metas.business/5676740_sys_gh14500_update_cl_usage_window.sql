-- Table: CreditLimit_Usage_V
-- 2023-02-13T00:32:12.106Z
INSERT INTO AD_Table (AccessLevel,ACTriggerLength,AD_Client_ID,AD_Org_ID,AD_Table_ID,CopyColumnsFromTable,Created,CreatedBy,EntityType,ImportTable,IsActive,IsAutocomplete,IsChangeLog,IsDeleteable,IsDLM,IsEnableRemoteCacheInvalidation,IsHighVolume,IsSecurityEnabled,IsView,LoadSeq,Name,PersonalDataCategory,ReplicationType,TableName,TooltipType,Updated,UpdatedBy,WEBUI_View_PageLength) VALUES ('3',0,0,0,542304,'N',TO_TIMESTAMP('2023-02-13 01:32:12','YYYY-MM-DD HH24:MI:SS'),100,'D','N','Y','N','N','N','N','N','N','N','Y',0,'CreditLimit_Usage_V','NP','L','CreditLimit_Usage_V','DTI',TO_TIMESTAMP('2023-02-13 01:32:12','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-13T00:32:12.108Z
INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Table_ID=542304 AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- 2023-02-13T00:32:29.820Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582057,0,'CreditLimit_Usage_V_ID',TO_TIMESTAMP('2023-02-13 01:32:29','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','CreditLimit_Usage_V','CreditLimit_Usage_V',TO_TIMESTAMP('2023-02-13 01:32:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-13T00:32:29.822Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582057 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: CreditLimit_Usage_V.CreditLimit_Usage_V_ID
-- 2023-02-13T00:32:30.171Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,586025,582057,0,13,542304,'CreditLimit_Usage_V_ID',TO_TIMESTAMP('2023-02-13 01:32:29','YYYY-MM-DD HH24:MI:SS'),100,'D',19,'Y','Y','N','N','N','Y','N','N','N','N','N','CreditLimit_Usage_V',TO_TIMESTAMP('2023-02-13 01:32:29','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-13T00:32:30.173Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586025 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-13T00:32:30.191Z
/* DDL */  select update_Column_Translation_From_AD_Element(582057) 
;

-- Column: CreditLimit_Usage_V.M_Department_ID
-- 2023-02-13T00:32:30.655Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,586026,581944,0,30,542304,'M_Department_ID',TO_TIMESTAMP('2023-02-13 01:32:30','YYYY-MM-DD HH24:MI:SS'),100,'D',10,'Y','Y','N','N','N','N','N','N','N','N','N','Department',TO_TIMESTAMP('2023-02-13 01:32:30','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-13T00:32:30.656Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586026 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-13T00:32:30.657Z
/* DDL */  select update_Column_Translation_From_AD_Element(581944) 
;

-- Column: CreditLimit_Usage_V.Section_Group_Partner_ID
-- 2023-02-13T00:32:31.002Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,586027,581322,0,30,542304,'Section_Group_Partner_ID',TO_TIMESTAMP('2023-02-13 01:32:30','YYYY-MM-DD HH24:MI:SS'),100,'D',10,'Y','Y','N','N','N','N','N','N','N','N','N','Section Group Partner',TO_TIMESTAMP('2023-02-13 01:32:30','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-13T00:32:31.003Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586027 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-13T00:32:31.005Z
/* DDL */  select update_Column_Translation_From_AD_Element(581322) 
;

-- Column: CreditLimit_Usage_V.partner_code
-- 2023-02-13T00:32:31.456Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,586028,582015,0,10,542304,'partner_code',TO_TIMESTAMP('2023-02-13 01:32:31','YYYY-MM-DD HH24:MI:SS'),100,'D',40,'Y','Y','N','N','N','N','N','N','N','N','N','partner_code',TO_TIMESTAMP('2023-02-13 01:32:31','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-13T00:32:31.457Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586028 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-13T00:32:31.459Z
/* DDL */  select update_Column_Translation_From_AD_Element(582015) 
;

-- Column: CreditLimit_Usage_V.C_BPartner_ID
-- 2023-02-13T00:32:32.012Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,586029,187,0,30,542304,'C_BPartner_ID',TO_TIMESTAMP('2023-02-13 01:32:31','YYYY-MM-DD HH24:MI:SS'),100,'','D',10,'','Y','Y','N','N','N','N','N','N','N','N','N','Business Partner',TO_TIMESTAMP('2023-02-13 01:32:31','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-13T00:32:32.013Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586029 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-13T00:32:32.014Z
/* DDL */  select update_Column_Translation_From_AD_Element(187) 
;

-- Column: CreditLimit_Usage_V.M_SectionCode_ID
-- 2023-02-13T00:32:32.494Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,586030,581238,0,30,542304,'M_SectionCode_ID',TO_TIMESTAMP('2023-02-13 01:32:32','YYYY-MM-DD HH24:MI:SS'),100,'D',10,'Y','Y','N','N','N','N','N','N','N','N','N','Section Code',TO_TIMESTAMP('2023-02-13 01:32:32','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-13T00:32:32.495Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586030 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-13T00:32:32.497Z
/* DDL */  select update_Column_Translation_From_AD_Element(581238) 
;

-- Column: CreditLimit_Usage_V.CreditLimit
-- 2023-02-13T00:32:33.008Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,586031,855,0,14,542304,'CreditLimit',TO_TIMESTAMP('2023-02-13 01:32:32','YYYY-MM-DD HH24:MI:SS'),100,'Amount of Credit allowed','D',2147483647,'The Credit Limit field indicates the credit limit for this account.','Y','Y','N','N','N','N','N','N','N','N','N','Credit limit',TO_TIMESTAMP('2023-02-13 01:32:32','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-13T00:32:33.009Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586031 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-13T00:32:33.011Z
/* DDL */  select update_Column_Translation_From_AD_Element(855) 
;

-- Column: CreditLimit_Usage_V.creditlimit_by_department
-- 2023-02-13T00:32:33.469Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,586032,582004,0,22,542304,'creditlimit_by_department',TO_TIMESTAMP('2023-02-13 01:32:33','YYYY-MM-DD HH24:MI:SS'),100,'D',14,'Y','Y','N','N','N','N','N','N','N','N','N','creditlimit_by_department',TO_TIMESTAMP('2023-02-13 01:32:33','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-13T00:32:33.470Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586032 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-13T00:32:33.471Z
/* DDL */  select update_Column_Translation_From_AD_Element(582004) 
;

-- Column: CreditLimit_Usage_V.cl_indicator_order
-- 2023-02-13T00:32:33.942Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,586033,582006,0,14,542304,'cl_indicator_order',TO_TIMESTAMP('2023-02-13 01:32:33','YYYY-MM-DD HH24:MI:SS'),100,'D',2147483647,'Y','Y','N','N','N','N','N','N','N','N','N','cl_indicator_order',TO_TIMESTAMP('2023-02-13 01:32:33','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-13T00:32:33.944Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586033 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-13T00:32:33.945Z
/* DDL */  select update_Column_Translation_From_AD_Element(582006) 
;

-- Column: CreditLimit_Usage_V.cl_indicator_delivery
-- 2023-02-13T00:32:34.392Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,586034,582007,0,14,542304,'cl_indicator_delivery',TO_TIMESTAMP('2023-02-13 01:32:34','YYYY-MM-DD HH24:MI:SS'),100,'D',2147483647,'Y','Y','N','N','N','N','N','N','N','N','N','cl_indicator_delivery',TO_TIMESTAMP('2023-02-13 01:32:34','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-13T00:32:34.393Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586034 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-13T00:32:34.394Z
/* DDL */  select update_Column_Translation_From_AD_Element(582007) 
;

-- Column: CreditLimit_Usage_V.Created
-- 2023-02-13T00:32:34.845Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,586035,245,0,16,542304,'Created',TO_TIMESTAMP('2023-02-13 01:32:34','YYYY-MM-DD HH24:MI:SS'),100,'Date this record was created','D',35,'The Created field indicates the date that this record was created.','Y','N','N','N','N','N','N','N','N','N','N','Created',TO_TIMESTAMP('2023-02-13 01:32:34','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-13T00:32:34.847Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586035 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-13T00:32:34.848Z
/* DDL */  select update_Column_Translation_From_AD_Element(245) 
;

-- Column: CreditLimit_Usage_V.CreatedBy
-- 2023-02-13T00:32:35.437Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,586036,246,0,18,110,542304,'CreatedBy',TO_TIMESTAMP('2023-02-13 01:32:35','YYYY-MM-DD HH24:MI:SS'),100,'User who created this records','D',10,'The Created By field indicates the user who created this record.','Y','N','N','N','N','N','N','N','N','N','N','Created By',TO_TIMESTAMP('2023-02-13 01:32:35','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-13T00:32:35.438Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586036 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-13T00:32:35.439Z
/* DDL */  select update_Column_Translation_From_AD_Element(246) 
;

-- Column: CreditLimit_Usage_V.Updated
-- 2023-02-13T00:32:35.997Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,586037,607,0,16,542304,'Updated',TO_TIMESTAMP('2023-02-13 01:32:35','YYYY-MM-DD HH24:MI:SS'),100,'Date this record was updated','D',35,'The Updated field indicates the date that this record was updated.','Y','N','N','N','N','N','N','N','N','N','N','Updated',TO_TIMESTAMP('2023-02-13 01:32:35','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-13T00:32:35.998Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586037 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-13T00:32:35.999Z
/* DDL */  select update_Column_Translation_From_AD_Element(607) 
;

-- Column: CreditLimit_Usage_V.UpdatedBy
-- 2023-02-13T00:32:36.537Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,586038,608,0,18,110,542304,'UpdatedBy',TO_TIMESTAMP('2023-02-13 01:32:36','YYYY-MM-DD HH24:MI:SS'),100,'User who updated this records','D',10,'The Updated By field indicates the user who updated this record.','Y','N','N','N','N','N','N','N','N','N','N','Updated By',TO_TIMESTAMP('2023-02-13 01:32:36','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-13T00:32:36.538Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586038 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-13T00:32:36.540Z
/* DDL */  select update_Column_Translation_From_AD_Element(608) 
;

-- Column: CreditLimit_Usage_V.IsActive
-- 2023-02-13T00:32:37.110Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,586039,348,0,10,542304,'IsActive',TO_TIMESTAMP('2023-02-13 01:32:37','YYYY-MM-DD HH24:MI:SS'),100,'The record is active in the system','D',2147483647,'There are two methods of making records unavailable in the system: One is to delete the record, the other is to de-activate the record. A de-activated record is not available for selection, but available for reports.
There are two reasons for de-activating and not deleting records:
(1) The system requires the record for audit purposes.
(2) The record is referenced by other records. E.g., you cannot delete a Business Partner, if there are invoices for this partner record existing. You de-activate the Business Partner and prevent that this record is used for future entries.','Y','Y','N','N','N','N','N','N','N','N','N','Active',TO_TIMESTAMP('2023-02-13 01:32:37','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-13T00:32:37.112Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586039 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-13T00:32:37.113Z
/* DDL */  select update_Column_Translation_From_AD_Element(348) 
;

-- Column: CreditLimit_Usage_V.AD_Client_ID
-- 2023-02-13T00:32:37.717Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,586040,102,0,30,542304,'AD_Client_ID',TO_TIMESTAMP('2023-02-13 01:32:37','YYYY-MM-DD HH24:MI:SS'),100,'Client/Tenant for this installation.','D',10,'A Client is a company or a legal entity. You cannot share data between Clients. Tenant is a synonym for Client.','Y','Y','N','N','N','N','N','N','N','N','N','Client',TO_TIMESTAMP('2023-02-13 01:32:37','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-13T00:32:37.719Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586040 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-13T00:32:37.721Z
/* DDL */  select update_Column_Translation_From_AD_Element(102) 
;

-- Column: CreditLimit_Usage_V.AD_Org_ID
-- 2023-02-13T00:32:38.312Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,586041,113,0,30,542304,'AD_Org_ID',TO_TIMESTAMP('2023-02-13 01:32:38','YYYY-MM-DD HH24:MI:SS'),100,'Organisational entity within client','D',10,'An organisation is a unit of your client or legal entity - examples are store, department. You can share data between organisations.','Y','Y','N','N','N','N','N','N','N','N','N','Organisation',TO_TIMESTAMP('2023-02-13 01:32:38','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-13T00:32:38.313Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586041 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-13T00:32:38.315Z
/* DDL */  select update_Column_Translation_From_AD_Element(113) 
;

-- Table: CreditLimit_Usage_V
-- 2023-02-13T00:32:49.792Z
UPDATE AD_Table SET IsEnableRemoteCacheInvalidation='Y',Updated=TO_TIMESTAMP('2023-02-13 01:32:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=542304
;

-- Column: CreditLimit_Usage_V.Section_Group_Partner_ID
-- 2023-02-13T00:33:23.714Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2023-02-13 01:33:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=586027
;

-- Column: CreditLimit_Usage_V.Section_Group_Partner_ID
-- 2023-02-13T00:34:17.155Z
UPDATE AD_Column SET AD_Reference_Value_ID=541640,Updated=TO_TIMESTAMP('2023-02-13 01:34:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=586027
;

-- Window: Credit limit Usage, InternalName=null
-- 2023-02-13T00:35:32.612Z
INSERT INTO AD_Window (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,Description,EntityType,Help,IsActive,IsBetaFunctionality,IsDefault,IsEnableRemoteCacheInvalidation,IsExcludeFromZoomTargets,IsOneInstanceOnly,IsOverrideInMenu,IsSOTrx,Name,Processing,Updated,UpdatedBy,WindowType,WinHeight,WinWidth,ZoomIntoPriority) VALUES (0,543845,0,541679,TO_TIMESTAMP('2023-02-13 01:35:32','YYYY-MM-DD HH24:MI:SS'),100,'Percent of Credit used from the limit','D','','Y','N','N','N','N','N','N','Y','Credit limit Usage','N',TO_TIMESTAMP('2023-02-13 01:35:32','YYYY-MM-DD HH24:MI:SS'),100,'M',0,0,100)
;

-- 2023-02-13T00:35:32.613Z
INSERT INTO AD_Window_Trl (AD_Language,AD_Window_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Window_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Window t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Window_ID=541679 AND NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Window_ID=t.AD_Window_ID)
;

-- 2023-02-13T00:35:32.616Z
/* DDL */  select update_window_translation_from_ad_element(543845) 
;

-- 2023-02-13T00:35:32.623Z
DELETE FROM AD_Element_Link WHERE AD_Window_ID=541679
;

-- 2023-02-13T00:35:32.624Z
/* DDL */ select AD_Element_Link_Create_Missing_Window(541679)
;

-- Window: Credit limit Usage, InternalName=null
-- 2023-02-13T00:35:36.991Z
UPDATE AD_Window SET IsEnableRemoteCacheInvalidation='Y',Updated=TO_TIMESTAMP('2023-02-13 01:35:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Window_ID=541679
;

-- Tab: Credit limit Usage(541679,D) -> CreditLimit_Usage_V
-- Table: CreditLimit_Usage_V
-- 2023-02-13T00:35:56.318Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,582057,0,546816,542304,541679,'Y',TO_TIMESTAMP('2023-02-13 01:35:56','YYYY-MM-DD HH24:MI:SS'),100,'D','N','N','A','CreditLimit_Usage_V','Y','N','Y','Y','N','N','N','N','Y','Y','N','N','Y','Y','N','N','N',0,'CreditLimit_Usage_V','N',10,0,TO_TIMESTAMP('2023-02-13 01:35:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-13T00:35:56.320Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Tab_ID=546816 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2023-02-13T00:35:56.322Z
/* DDL */  select update_tab_translation_from_ad_element(582057) 
;

-- 2023-02-13T00:35:56.328Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(546816)
;

-- Tab: Credit limit Usage(541679,D) -> CreditLimit_Usage_V(546816,D)
-- UI Section: main
-- 2023-02-13T00:36:29.903Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,546816,545443,TO_TIMESTAMP('2023-02-13 01:36:29','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2023-02-13 01:36:29','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2023-02-13T00:36:29.904Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_UI_Section_ID=545443 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- UI Section: Credit limit Usage(541679,D) -> CreditLimit_Usage_V(546816,D) -> main
-- UI Column: 10
-- 2023-02-13T00:36:29.981Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,546646,545443,TO_TIMESTAMP('2023-02-13 01:36:29','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2023-02-13 01:36:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Section: Credit limit Usage(541679,D) -> CreditLimit_Usage_V(546816,D) -> main
-- UI Column: 20
-- 2023-02-13T00:36:30.036Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,546647,545443,TO_TIMESTAMP('2023-02-13 01:36:29','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2023-02-13 01:36:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: Credit limit Usage(541679,D) -> CreditLimit_Usage_V(546816,D) -> main -> 10
-- UI Element Group: default
-- 2023-02-13T00:36:30.104Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,546646,550370,TO_TIMESTAMP('2023-02-13 01:36:30','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2023-02-13 01:36:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Field: Credit limit Usage(541679,D) -> CreditLimit_Usage_V(546816,D) -> CreditLimit_Usage_V
-- Column: CreditLimit_Usage_V.CreditLimit_Usage_V_ID
-- 2023-02-13T00:37:08.533Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586025,712318,0,546816,TO_TIMESTAMP('2023-02-13 01:37:08','YYYY-MM-DD HH24:MI:SS'),100,19,'D','Y','N','N','N','N','N','N','N','CreditLimit_Usage_V',TO_TIMESTAMP('2023-02-13 01:37:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-13T00:37:08.535Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712318 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-13T00:37:08.538Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582057) 
;

-- 2023-02-13T00:37:08.544Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712318
;

-- 2023-02-13T00:37:08.545Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712318)
;

-- Field: Credit limit Usage(541679,D) -> CreditLimit_Usage_V(546816,D) -> Department
-- Column: CreditLimit_Usage_V.M_Department_ID
-- 2023-02-13T00:37:08.599Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586026,712319,0,546816,TO_TIMESTAMP('2023-02-13 01:37:08','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Department',TO_TIMESTAMP('2023-02-13 01:37:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-13T00:37:08.600Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712319 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-13T00:37:08.601Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581944) 
;

-- 2023-02-13T00:37:08.610Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712319
;

-- 2023-02-13T00:37:08.610Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712319)
;

-- Field: Credit limit Usage(541679,D) -> CreditLimit_Usage_V(546816,D) -> Section Group Partner
-- Column: CreditLimit_Usage_V.Section_Group_Partner_ID
-- 2023-02-13T00:37:08.676Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586027,712320,0,546816,TO_TIMESTAMP('2023-02-13 01:37:08','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Section Group Partner',TO_TIMESTAMP('2023-02-13 01:37:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-13T00:37:08.677Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712320 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-13T00:37:08.678Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581322) 
;

-- 2023-02-13T00:37:08.685Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712320
;

-- 2023-02-13T00:37:08.685Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712320)
;

-- Field: Credit limit Usage(541679,D) -> CreditLimit_Usage_V(546816,D) -> partner_code
-- Column: CreditLimit_Usage_V.partner_code
-- 2023-02-13T00:37:08.746Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586028,712321,0,546816,TO_TIMESTAMP('2023-02-13 01:37:08','YYYY-MM-DD HH24:MI:SS'),100,40,'D','Y','N','N','N','N','N','N','N','partner_code',TO_TIMESTAMP('2023-02-13 01:37:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-13T00:37:08.747Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712321 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-13T00:37:08.748Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582015) 
;

-- 2023-02-13T00:37:08.752Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712321
;

-- 2023-02-13T00:37:08.752Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712321)
;

-- Field: Credit limit Usage(541679,D) -> CreditLimit_Usage_V(546816,D) -> Business Partner
-- Column: CreditLimit_Usage_V.C_BPartner_ID
-- 2023-02-13T00:37:08.807Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586029,712322,0,546816,TO_TIMESTAMP('2023-02-13 01:37:08','YYYY-MM-DD HH24:MI:SS'),100,'',10,'D','','Y','N','N','N','N','N','N','N','Business Partner',TO_TIMESTAMP('2023-02-13 01:37:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-13T00:37:08.808Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712322 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-13T00:37:08.809Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(187) 
;

-- 2023-02-13T00:37:08.890Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712322
;

-- 2023-02-13T00:37:08.890Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712322)
;

-- Field: Credit limit Usage(541679,D) -> CreditLimit_Usage_V(546816,D) -> Section Code
-- Column: CreditLimit_Usage_V.M_SectionCode_ID
-- 2023-02-13T00:37:08.950Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586030,712323,0,546816,TO_TIMESTAMP('2023-02-13 01:37:08','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Section Code',TO_TIMESTAMP('2023-02-13 01:37:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-13T00:37:08.951Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712323 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-13T00:37:08.952Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581238) 
;

-- 2023-02-13T00:37:08.993Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712323
;

-- 2023-02-13T00:37:08.993Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712323)
;

-- Field: Credit limit Usage(541679,D) -> CreditLimit_Usage_V(546816,D) -> Credit limit
-- Column: CreditLimit_Usage_V.CreditLimit
-- 2023-02-13T00:37:09.055Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586031,712324,0,546816,TO_TIMESTAMP('2023-02-13 01:37:08','YYYY-MM-DD HH24:MI:SS'),100,'Amount of Credit allowed',2147483647,'D','The Credit Limit field indicates the credit limit for this account.','Y','N','N','N','N','N','N','N','Credit limit',TO_TIMESTAMP('2023-02-13 01:37:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-13T00:37:09.056Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712324 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-13T00:37:09.057Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(855) 
;

-- 2023-02-13T00:37:09.063Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712324
;

-- 2023-02-13T00:37:09.063Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712324)
;

-- Field: Credit limit Usage(541679,D) -> CreditLimit_Usage_V(546816,D) -> creditlimit_by_department
-- Column: CreditLimit_Usage_V.creditlimit_by_department
-- 2023-02-13T00:37:09.114Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586032,712325,0,546816,TO_TIMESTAMP('2023-02-13 01:37:09','YYYY-MM-DD HH24:MI:SS'),100,14,'D','Y','N','N','N','N','N','N','N','creditlimit_by_department',TO_TIMESTAMP('2023-02-13 01:37:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-13T00:37:09.114Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712325 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-13T00:37:09.116Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582004) 
;

-- 2023-02-13T00:37:09.119Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712325
;

-- 2023-02-13T00:37:09.119Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712325)
;

-- Field: Credit limit Usage(541679,D) -> CreditLimit_Usage_V(546816,D) -> cl_indicator_order
-- Column: CreditLimit_Usage_V.cl_indicator_order
-- 2023-02-13T00:37:09.177Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586033,712326,0,546816,TO_TIMESTAMP('2023-02-13 01:37:09','YYYY-MM-DD HH24:MI:SS'),100,2147483647,'D','Y','N','N','N','N','N','N','N','cl_indicator_order',TO_TIMESTAMP('2023-02-13 01:37:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-13T00:37:09.177Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712326 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-13T00:37:09.178Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582006) 
;

-- 2023-02-13T00:37:09.182Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712326
;

-- 2023-02-13T00:37:09.183Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712326)
;

-- Field: Credit limit Usage(541679,D) -> CreditLimit_Usage_V(546816,D) -> cl_indicator_delivery
-- Column: CreditLimit_Usage_V.cl_indicator_delivery
-- 2023-02-13T00:37:09.239Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586034,712327,0,546816,TO_TIMESTAMP('2023-02-13 01:37:09','YYYY-MM-DD HH24:MI:SS'),100,2147483647,'D','Y','N','N','N','N','N','N','N','cl_indicator_delivery',TO_TIMESTAMP('2023-02-13 01:37:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-13T00:37:09.240Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712327 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-13T00:37:09.242Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582007) 
;

-- 2023-02-13T00:37:09.245Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712327
;

-- 2023-02-13T00:37:09.245Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712327)
;

-- Field: Credit limit Usage(541679,D) -> CreditLimit_Usage_V(546816,D) -> Created
-- Column: CreditLimit_Usage_V.Created
-- 2023-02-13T00:37:09.302Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586035,712328,0,546816,TO_TIMESTAMP('2023-02-13 01:37:09','YYYY-MM-DD HH24:MI:SS'),100,'Date this record was created',35,'D','The Created field indicates the date that this record was created.','Y','N','N','N','N','N','N','N','Created',TO_TIMESTAMP('2023-02-13 01:37:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-13T00:37:09.303Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712328 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-13T00:37:09.304Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(245) 
;

-- 2023-02-13T00:37:09.606Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712328
;

-- 2023-02-13T00:37:09.606Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712328)
;

-- Field: Credit limit Usage(541679,D) -> CreditLimit_Usage_V(546816,D) -> Created By
-- Column: CreditLimit_Usage_V.CreatedBy
-- 2023-02-13T00:37:09.679Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586036,712329,0,546816,TO_TIMESTAMP('2023-02-13 01:37:09','YYYY-MM-DD HH24:MI:SS'),100,'User who created this records',10,'D','The Created By field indicates the user who created this record.','Y','N','N','N','N','N','N','N','Created By',TO_TIMESTAMP('2023-02-13 01:37:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-13T00:37:09.680Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712329 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-13T00:37:09.682Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(246) 
;

-- 2023-02-13T00:37:09.826Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712329
;

-- 2023-02-13T00:37:09.826Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712329)
;

-- Field: Credit limit Usage(541679,D) -> CreditLimit_Usage_V(546816,D) -> Updated
-- Column: CreditLimit_Usage_V.Updated
-- 2023-02-13T00:37:09.900Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586037,712330,0,546816,TO_TIMESTAMP('2023-02-13 01:37:09','YYYY-MM-DD HH24:MI:SS'),100,'Date this record was updated',35,'D','The Updated field indicates the date that this record was updated.','Y','N','N','N','N','N','N','N','Updated',TO_TIMESTAMP('2023-02-13 01:37:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-13T00:37:09.901Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712330 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-13T00:37:09.902Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(607) 
;

-- 2023-02-13T00:37:10.009Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712330
;

-- 2023-02-13T00:37:10.010Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712330)
;

-- Field: Credit limit Usage(541679,D) -> CreditLimit_Usage_V(546816,D) -> Updated By
-- Column: CreditLimit_Usage_V.UpdatedBy
-- 2023-02-13T00:37:10.074Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586038,712331,0,546816,TO_TIMESTAMP('2023-02-13 01:37:10','YYYY-MM-DD HH24:MI:SS'),100,'User who updated this records',10,'D','The Updated By field indicates the user who updated this record.','Y','N','N','N','N','N','N','N','Updated By',TO_TIMESTAMP('2023-02-13 01:37:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-13T00:37:10.075Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712331 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-13T00:37:10.076Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(608) 
;

-- 2023-02-13T00:37:10.169Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712331
;

-- 2023-02-13T00:37:10.169Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712331)
;

-- Field: Credit limit Usage(541679,D) -> CreditLimit_Usage_V(546816,D) -> Active
-- Column: CreditLimit_Usage_V.IsActive
-- 2023-02-13T00:37:10.228Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586039,712332,0,546816,TO_TIMESTAMP('2023-02-13 01:37:10','YYYY-MM-DD HH24:MI:SS'),100,'The record is active in the system',2147483647,'D','There are two methods of making records unavailable in the system: One is to delete the record, the other is to de-activate the record. A de-activated record is not available for selection, but available for reports.
There are two reasons for de-activating and not deleting records:
(1) The system requires the record for audit purposes.
(2) The record is referenced by other records. E.g., you cannot delete a Business Partner, if there are invoices for this partner record existing. You de-activate the Business Partner and prevent that this record is used for future entries.','Y','N','N','N','N','N','N','N','Active',TO_TIMESTAMP('2023-02-13 01:37:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-13T00:37:10.228Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712332 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-13T00:37:10.230Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2023-02-13T00:37:10.626Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712332
;

-- 2023-02-13T00:37:10.626Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712332)
;

-- Field: Credit limit Usage(541679,D) -> CreditLimit_Usage_V(546816,D) -> Client
-- Column: CreditLimit_Usage_V.AD_Client_ID
-- 2023-02-13T00:37:10.683Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586040,712333,0,546816,TO_TIMESTAMP('2023-02-13 01:37:10','YYYY-MM-DD HH24:MI:SS'),100,'Client/Tenant for this installation.',10,'D','A Client is a company or a legal entity. You cannot share data between Clients. Tenant is a synonym for Client.','Y','N','N','N','N','N','Y','N','Client',TO_TIMESTAMP('2023-02-13 01:37:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-13T00:37:10.684Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712333 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-13T00:37:10.686Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2023-02-13T00:37:10.874Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712333
;

-- 2023-02-13T00:37:10.874Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712333)
;

-- Field: Credit limit Usage(541679,D) -> CreditLimit_Usage_V(546816,D) -> Organisation
-- Column: CreditLimit_Usage_V.AD_Org_ID
-- 2023-02-13T00:37:10.954Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586041,712334,0,546816,TO_TIMESTAMP('2023-02-13 01:37:10','YYYY-MM-DD HH24:MI:SS'),100,'Organisational entity within client',10,'D','An organisation is a unit of your client or legal entity - examples are store, department. You can share data between organisations.','Y','N','N','N','N','N','N','N','Organisation',TO_TIMESTAMP('2023-02-13 01:37:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-13T00:37:10.955Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712334 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-13T00:37:10.956Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2023-02-13T00:37:11.136Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712334
;

-- 2023-02-13T00:37:11.137Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712334)
;

-- UI Element: Credit limit Usage(541679,D) -> CreditLimit_Usage_V(546816,D) -> main -> 10 -> default.CreditLimit_Usage_V
-- Column: CreditLimit_Usage_V.CreditLimit_Usage_V_ID
-- 2023-02-13T00:37:48.479Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712318,0,546816,550370,615681,'F',TO_TIMESTAMP('2023-02-13 01:37:48','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'CreditLimit_Usage_V',10,0,0,TO_TIMESTAMP('2023-02-13 01:37:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Credit limit Usage(541679,D) -> CreditLimit_Usage_V(546816,D) -> main -> 10 -> default.CreditLimit_Usage_V
-- Column: CreditLimit_Usage_V.CreditLimit_Usage_V_ID
-- 2023-02-13T00:37:50.281Z
UPDATE AD_UI_Element SET IsDisplayed='N',Updated=TO_TIMESTAMP('2023-02-13 01:37:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615681
;

-- UI Element: Credit limit Usage(541679,D) -> CreditLimit_Usage_V(546816,D) -> main -> 10 -> default.CreditLimit_Usage_V
-- Column: CreditLimit_Usage_V.CreditLimit_Usage_V_ID
-- 2023-02-13T00:37:56.923Z
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2023-02-13 01:37:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615681
;

-- UI Element: Credit limit Usage(541679,D) -> CreditLimit_Usage_V(546816,D) -> main -> 10 -> default.Section Group Partner
-- Column: CreditLimit_Usage_V.Section_Group_Partner_ID
-- 2023-02-13T00:38:07.112Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712320,0,546816,550370,615682,'F',TO_TIMESTAMP('2023-02-13 01:38:07','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Section Group Partner',20,0,0,TO_TIMESTAMP('2023-02-13 01:38:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Credit limit Usage(541679,D) -> CreditLimit_Usage_V(546816,D) -> main -> 10 -> default.Business Partner
-- Column: CreditLimit_Usage_V.C_BPartner_ID
-- 2023-02-13T00:38:15.470Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712322,0,546816,550370,615683,'F',TO_TIMESTAMP('2023-02-13 01:38:15','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Business Partner',30,0,0,TO_TIMESTAMP('2023-02-13 01:38:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Credit limit Usage(541679,D) -> CreditLimit_Usage_V(546816,D) -> main -> 10 -> default.Department
-- Column: CreditLimit_Usage_V.M_Department_ID
-- 2023-02-13T00:38:24.414Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712319,0,546816,550370,615684,'F',TO_TIMESTAMP('2023-02-13 01:38:24','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Department',40,0,0,TO_TIMESTAMP('2023-02-13 01:38:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Credit limit Usage(541679,D) -> CreditLimit_Usage_V(546816,D) -> main -> 10 -> default.Credit limit
-- Column: CreditLimit_Usage_V.CreditLimit
-- 2023-02-13T00:38:30.775Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712324,0,546816,550370,615685,'F',TO_TIMESTAMP('2023-02-13 01:38:30','YYYY-MM-DD HH24:MI:SS'),100,'Amount of Credit allowed','The Credit Limit field indicates the credit limit for this account.','Y','N','N','Y','N','N','N',0,'Credit limit',50,0,0,TO_TIMESTAMP('2023-02-13 01:38:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Credit limit Usage(541679,D) -> CreditLimit_Usage_V(546816,D) -> main -> 10 -> default.Section Code
-- Column: CreditLimit_Usage_V.M_SectionCode_ID
-- 2023-02-13T00:38:38.429Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712323,0,546816,550370,615686,'F',TO_TIMESTAMP('2023-02-13 01:38:38','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Section Code',60,0,0,TO_TIMESTAMP('2023-02-13 01:38:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Credit limit Usage(541679,D) -> CreditLimit_Usage_V(546816,D) -> main -> 10 -> default.CreditLimit_Usage_V
-- Column: CreditLimit_Usage_V.CreditLimit_Usage_V_ID
-- 2023-02-13T00:39:06.759Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2023-02-13 01:39:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615681
;

-- UI Element: Credit limit Usage(541679,D) -> CreditLimit_Usage_V(546816,D) -> main -> 10 -> default.Section Group Partner
-- Column: CreditLimit_Usage_V.Section_Group_Partner_ID
-- 2023-02-13T00:39:06.762Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2023-02-13 01:39:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615682
;

-- UI Element: Credit limit Usage(541679,D) -> CreditLimit_Usage_V(546816,D) -> main -> 10 -> default.Business Partner
-- Column: CreditLimit_Usage_V.C_BPartner_ID
-- 2023-02-13T00:39:06.766Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2023-02-13 01:39:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615683
;

-- UI Element: Credit limit Usage(541679,D) -> CreditLimit_Usage_V(546816,D) -> main -> 10 -> default.Department
-- Column: CreditLimit_Usage_V.M_Department_ID
-- 2023-02-13T00:39:06.770Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2023-02-13 01:39:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615684
;

-- UI Element: Credit limit Usage(541679,D) -> CreditLimit_Usage_V(546816,D) -> main -> 10 -> default.Section Code
-- Column: CreditLimit_Usage_V.M_SectionCode_ID
-- 2023-02-13T00:39:06.772Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2023-02-13 01:39:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615686
;

-- UI Element: Credit limit Usage(541679,D) -> CreditLimit_Usage_V(546816,D) -> main -> 10 -> default.Credit limit
-- Column: CreditLimit_Usage_V.CreditLimit
-- 2023-02-13T00:39:06.774Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2023-02-13 01:39:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615685
;

-- Window: Credit Limit Usage, InternalName=Credit Limit Usage
-- 2023-02-13T00:39:39.629Z
UPDATE AD_Window SET IsActive='N',Updated=TO_TIMESTAMP('2023-02-13 01:39:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Window_ID=541673
;

-- Name: Credit Limit Usage
-- Action Type: W
-- Window: Credit Limit Usage(541673,D)
-- 2023-02-13T00:39:39.632Z
UPDATE AD_Menu SET Description=NULL, IsActive='N', Name='Credit Limit Usage',Updated=TO_TIMESTAMP('2023-02-13 01:39:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=542050
;

-- Name: Credit limit Usage
-- Action Type: W
-- Window: Credit limit Usage(541679,D)
-- 2023-02-13T00:40:25.227Z
UPDATE AD_Menu SET AD_Element_ID=543845, AD_Window_ID=541679, Description='Percent of Credit used from the limit', InternalName='CL Usage', Name='Credit limit Usage', WEBUI_NameBrowse=NULL, WEBUI_NameNew=NULL, WEBUI_NameNewBreadcrumb=NULL,Updated=TO_TIMESTAMP('2023-02-13 01:40:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=542050
;

-- 2023-02-13T00:40:25.227Z
UPDATE AD_Menu_Trl trl SET Description='Percent of Credit used from the limit',Name='Credit limit Usage' WHERE AD_Menu_ID=542050 AND AD_Language='en_US'
;

-- 2023-02-13T00:40:25.230Z
/* DDL */  select update_menu_translation_from_ad_element(543845) 
;

-- Name: Credit limit Usage
-- Action Type: W
-- Window: Credit limit Usage(541679,D)
-- 2023-02-13T00:40:28.806Z
UPDATE AD_Menu SET IsActive='Y',Updated=TO_TIMESTAMP('2023-02-13 01:40:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=542050
;

-- Name: Credit limit Usage
-- Action Type: W
-- Window: Credit limit Usage(541679,D)
-- 2023-02-13T00:40:37.594Z
UPDATE AD_Menu SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2023-02-13 01:40:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=542050
;

-- Name: Credit limit Usage
-- Action Type: W
-- Window: Credit limit Usage(541679,D)
-- 2023-02-13T00:42:41.364Z
UPDATE AD_Menu SET IsReadOnly='N',Updated=TO_TIMESTAMP('2023-02-13 01:42:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=542050
;

-- Tab: Credit limit Usage(541679,D) -> CreditLimit_Usage_V
-- Table: CreditLimit_Usage_V
-- 2023-02-13T00:43:21.596Z
UPDATE AD_Tab SET IsGridModeOnly='Y',Updated=TO_TIMESTAMP('2023-02-13 01:43:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=546816
;

-- Field: Credit limit Usage(541679,D) -> CreditLimit_Usage_V(546816,D) -> CreditLimit_Usage_V
-- Column: CreditLimit_Usage_V.CreditLimit_Usage_V_ID
-- 2023-02-13T00:43:33.452Z
UPDATE AD_Field SET IsDisplayedGrid='Y',Updated=TO_TIMESTAMP('2023-02-13 01:43:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=712318
;

-- Field: Credit limit Usage(541679,D) -> CreditLimit_Usage_V(546816,D) -> Department
-- Column: CreditLimit_Usage_V.M_Department_ID
-- 2023-02-13T00:43:38.677Z
UPDATE AD_Field SET IsDisplayed='Y', IsDisplayedGrid='Y',Updated=TO_TIMESTAMP('2023-02-13 01:43:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=712319
;

-- Field: Credit limit Usage(541679,D) -> CreditLimit_Usage_V(546816,D) -> Section Group Partner
-- Column: CreditLimit_Usage_V.Section_Group_Partner_ID
-- 2023-02-13T00:43:41.776Z
UPDATE AD_Field SET IsDisplayed='Y', IsDisplayedGrid='Y',Updated=TO_TIMESTAMP('2023-02-13 01:43:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=712320
;

-- Field: Credit limit Usage(541679,D) -> CreditLimit_Usage_V(546816,D) -> partner_code
-- Column: CreditLimit_Usage_V.partner_code
-- 2023-02-13T00:43:45.569Z
UPDATE AD_Field SET IsDisplayed='Y', IsDisplayedGrid='Y',Updated=TO_TIMESTAMP('2023-02-13 01:43:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=712321
;

-- Field: Credit limit Usage(541679,D) -> CreditLimit_Usage_V(546816,D) -> Business Partner
-- Column: CreditLimit_Usage_V.C_BPartner_ID
-- 2023-02-13T00:43:49.289Z
UPDATE AD_Field SET IsDisplayed='Y', IsDisplayedGrid='Y',Updated=TO_TIMESTAMP('2023-02-13 01:43:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=712322
;

-- Field: Credit limit Usage(541679,D) -> CreditLimit_Usage_V(546816,D) -> Section Code
-- Column: CreditLimit_Usage_V.M_SectionCode_ID
-- 2023-02-13T00:43:52.581Z
UPDATE AD_Field SET IsDisplayed='Y', IsDisplayedGrid='Y',Updated=TO_TIMESTAMP('2023-02-13 01:43:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=712323
;

-- Field: Credit limit Usage(541679,D) -> CreditLimit_Usage_V(546816,D) -> Credit limit
-- Column: CreditLimit_Usage_V.CreditLimit
-- 2023-02-13T00:43:55.993Z
UPDATE AD_Field SET IsDisplayed='Y', IsDisplayedGrid='Y',Updated=TO_TIMESTAMP('2023-02-13 01:43:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=712324
;

-- Field: Credit limit Usage(541679,D) -> CreditLimit_Usage_V(546816,D) -> creditlimit_by_department
-- Column: CreditLimit_Usage_V.creditlimit_by_department
-- 2023-02-13T00:43:59.191Z
UPDATE AD_Field SET IsDisplayed='Y', IsDisplayedGrid='Y',Updated=TO_TIMESTAMP('2023-02-13 01:43:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=712325
;

-- Field: Credit limit Usage(541679,D) -> CreditLimit_Usage_V(546816,D) -> cl_indicator_order
-- Column: CreditLimit_Usage_V.cl_indicator_order
-- 2023-02-13T00:44:02.899Z
UPDATE AD_Field SET IsDisplayed='Y', IsDisplayedGrid='Y',Updated=TO_TIMESTAMP('2023-02-13 01:44:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=712326
;

-- Field: Credit limit Usage(541679,D) -> CreditLimit_Usage_V(546816,D) -> cl_indicator_delivery
-- Column: CreditLimit_Usage_V.cl_indicator_delivery
-- 2023-02-13T00:44:06.332Z
UPDATE AD_Field SET IsDisplayed='Y', IsDisplayedGrid='Y',Updated=TO_TIMESTAMP('2023-02-13 01:44:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=712327
;

-- Field: Credit limit Usage(541679,D) -> CreditLimit_Usage_V(546816,D) -> Active
-- Column: CreditLimit_Usage_V.IsActive
-- 2023-02-13T00:44:15.735Z
UPDATE AD_Field SET IsDisplayed='Y', IsDisplayedGrid='Y',Updated=TO_TIMESTAMP('2023-02-13 01:44:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=712332
;

-- Field: Credit limit Usage(541679,D) -> CreditLimit_Usage_V(546816,D) -> Client
-- Column: CreditLimit_Usage_V.AD_Client_ID
-- 2023-02-13T00:44:20.871Z
UPDATE AD_Field SET IsDisplayed='Y', IsDisplayedGrid='Y',Updated=TO_TIMESTAMP('2023-02-13 01:44:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=712333
;

-- Field: Credit limit Usage(541679,D) -> CreditLimit_Usage_V(546816,D) -> Organisation
-- Column: CreditLimit_Usage_V.AD_Org_ID
-- 2023-02-13T00:44:26.326Z
UPDATE AD_Field SET IsDisplayed='Y', IsDisplayedGrid='Y',Updated=TO_TIMESTAMP('2023-02-13 01:44:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=712334
;

-- Column: CreditLimit_Usage_V.M_Department_ID
-- 2023-02-13T00:45:45.210Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2023-02-13 01:45:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=586026
;

-- Column: CreditLimit_Usage_V.CreditLimit
-- 2023-02-13T00:48:13.246Z
UPDATE AD_Column SET AD_Reference_ID=10, FieldLength=255,Updated=TO_TIMESTAMP('2023-02-13 01:48:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=586031
;

-- Column: CreditLimit_Usage_V.IsActive
-- 2023-02-13T00:48:40.812Z
UPDATE AD_Column SET FieldLength=1,Updated=TO_TIMESTAMP('2023-02-13 01:48:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=586039
;

-- Column: CreditLimit_Usage_V.IsActive
-- 2023-02-13T00:48:51.992Z
UPDATE AD_Column SET AD_Reference_ID=20, DefaultValue='Y', IsMandatory='Y',Updated=TO_TIMESTAMP('2023-02-13 01:48:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=586039
;

-- Column: CreditLimit_Usage_V.M_SectionCode_ID
-- 2023-02-13T00:49:33.354Z
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2023-02-13 01:49:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=586030
;

-- Column: CreditLimit_Usage_V.M_SectionCode_ID
-- 2023-02-13T00:49:39.277Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2023-02-13 01:49:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=586030
;

-- Table: CreditLimit_Usage_V
-- 2023-02-13T00:50:00.019Z
UPDATE AD_Table SET AD_Window_ID=541679,Updated=TO_TIMESTAMP('2023-02-13 01:50:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=542304
;

-- Column: CreditLimit_Usage_V.M_SectionCode_ID
-- 2023-02-13T00:53:23.137Z
UPDATE AD_Column SET AD_Reference_Value_ID=541698,Updated=TO_TIMESTAMP('2023-02-13 01:53:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=586030
;

-- Name: M_Department
-- 2023-02-13T00:54:44.438Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,Description,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541715,TO_TIMESTAMP('2023-02-13 01:54:44','YYYY-MM-DD HH24:MI:SS'),100,'M_Department','D','Y','N','M_Department',TO_TIMESTAMP('2023-02-13 01:54:44','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2023-02-13T00:54:44.439Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Reference_ID=541715 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Field: Credit limit Usage(541679,D) -> CreditLimit_Usage_V(546816,D) -> Department
-- Column: CreditLimit_Usage_V.M_Department_ID
-- 2023-02-13T01:08:29.549Z
UPDATE AD_Field SET SortNo=1.000000000000,Updated=TO_TIMESTAMP('2023-02-13 02:08:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=712319
;

-- UI Element: Credit limit Usage(541679,D) -> CreditLimit_Usage_V(546816,D) -> main -> 10 -> default.CreditLimit_Usage_V
-- Column: CreditLimit_Usage_V.CreditLimit_Usage_V_ID
-- 2023-02-13T01:08:36.849Z
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2023-02-13 02:08:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615681
;

-- UI Element: Credit limit Usage(541679,D) -> CreditLimit_Usage_V(546816,D) -> main -> 10 -> default.Section Group Partner
-- Column: CreditLimit_Usage_V.Section_Group_Partner_ID
-- 2023-02-13T01:08:36.853Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2023-02-13 02:08:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615682
;

-- UI Element: Credit limit Usage(541679,D) -> CreditLimit_Usage_V(546816,D) -> main -> 10 -> default.Business Partner
-- Column: CreditLimit_Usage_V.C_BPartner_ID
-- 2023-02-13T01:08:36.857Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2023-02-13 02:08:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615683
;

-- UI Element: Credit limit Usage(541679,D) -> CreditLimit_Usage_V(546816,D) -> main -> 10 -> default.Department
-- Column: CreditLimit_Usage_V.M_Department_ID
-- 2023-02-13T01:08:36.860Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2023-02-13 02:08:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615684
;

-- UI Element: Credit limit Usage(541679,D) -> CreditLimit_Usage_V(546816,D) -> main -> 10 -> default.Section Code
-- Column: CreditLimit_Usage_V.M_SectionCode_ID
-- 2023-02-13T01:08:36.864Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2023-02-13 02:08:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615686
;

-- UI Element: Credit limit Usage(541679,D) -> CreditLimit_Usage_V(546816,D) -> main -> 10 -> default.Credit limit
-- Column: CreditLimit_Usage_V.CreditLimit
-- 2023-02-13T01:08:36.866Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2023-02-13 02:08:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615685
;

-- UI Element: Credit limit Usage(541679,D) -> CreditLimit_Usage_V(546816,D) -> main -> 10 -> default.partner_code
-- Column: CreditLimit_Usage_V.partner_code
-- 2023-02-13T01:09:22.138Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712321,0,546816,550370,615687,'F',TO_TIMESTAMP('2023-02-13 02:09:22','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'partner_code',70,0,0,TO_TIMESTAMP('2023-02-13 02:09:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Credit limit Usage(541679,D) -> CreditLimit_Usage_V(546816,D) -> main -> 10 -> default.Section Group Partner
-- Column: CreditLimit_Usage_V.Section_Group_Partner_ID
-- 2023-02-13T01:09:36.094Z
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2023-02-13 02:09:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615682
;

-- UI Element: Credit limit Usage(541679,D) -> CreditLimit_Usage_V(546816,D) -> main -> 10 -> default.partner_code
-- Column: CreditLimit_Usage_V.partner_code
-- 2023-02-13T01:09:36.096Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2023-02-13 02:09:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615687
;

-- UI Element: Credit limit Usage(541679,D) -> CreditLimit_Usage_V(546816,D) -> main -> 10 -> default.Business Partner
-- Column: CreditLimit_Usage_V.C_BPartner_ID
-- 2023-02-13T01:09:47.866Z
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2023-02-13 02:09:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615683
;

-- UI Element: Credit limit Usage(541679,D) -> CreditLimit_Usage_V(546816,D) -> main -> 10 -> default.Department
-- Column: CreditLimit_Usage_V.M_Department_ID
-- 2023-02-13T01:09:47.871Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2023-02-13 02:09:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615684
;

-- UI Element: Credit limit Usage(541679,D) -> CreditLimit_Usage_V(546816,D) -> main -> 10 -> default.Section Code
-- Column: CreditLimit_Usage_V.M_SectionCode_ID
-- 2023-02-13T01:09:47.874Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2023-02-13 02:09:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615686
;

-- UI Element: Credit limit Usage(541679,D) -> CreditLimit_Usage_V(546816,D) -> main -> 10 -> default.Credit limit
-- Column: CreditLimit_Usage_V.CreditLimit
-- 2023-02-13T01:09:47.877Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2023-02-13 02:09:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615685
;

-- UI Element: Credit limit Usage(541679,D) -> CreditLimit_Usage_V(546816,D) -> main -> 10 -> default.Section Group Partner
-- Column: CreditLimit_Usage_V.Section_Group_Partner_ID
-- 2023-02-13T01:10:18.253Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2023-02-13 02:10:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615682
;

-- UI Element: Credit limit Usage(541679,D) -> CreditLimit_Usage_V(546816,D) -> main -> 10 -> default.partner_code
-- Column: CreditLimit_Usage_V.partner_code
-- 2023-02-13T01:10:18.255Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2023-02-13 02:10:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615687
;

-- UI Element: Credit limit Usage(541679,D) -> CreditLimit_Usage_V(546816,D) -> main -> 10 -> default.Department
-- Column: CreditLimit_Usage_V.M_Department_ID
-- 2023-02-13T01:10:18.257Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2023-02-13 02:10:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615684
;

-- UI Element: Credit limit Usage(541679,D) -> CreditLimit_Usage_V(546816,D) -> main -> 10 -> default.Section Code
-- Column: CreditLimit_Usage_V.M_SectionCode_ID
-- 2023-02-13T01:10:18.259Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2023-02-13 02:10:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615686
;

-- UI Element: Credit limit Usage(541679,D) -> CreditLimit_Usage_V(546816,D) -> main -> 10 -> default.Credit limit
-- Column: CreditLimit_Usage_V.CreditLimit
-- 2023-02-13T01:10:18.261Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2023-02-13 02:10:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615685
;

-- UI Element: Credit limit Usage(541679,D) -> CreditLimit_Usage_V(546816,D) -> main -> 10 -> default.creditlimit_by_department
-- Column: CreditLimit_Usage_V.creditlimit_by_department
-- 2023-02-13T01:11:36.891Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712325,0,546816,550370,615688,'F',TO_TIMESTAMP('2023-02-13 02:11:36','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'creditlimit_by_department',80,0,0,TO_TIMESTAMP('2023-02-13 02:11:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Credit limit Usage(541679,D) -> CreditLimit_Usage_V(546816,D) -> main -> 10 -> default.cl_indicator_order
-- Column: CreditLimit_Usage_V.cl_indicator_order
-- 2023-02-13T01:11:45.834Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712326,0,546816,550370,615689,'F',TO_TIMESTAMP('2023-02-13 02:11:45','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'cl_indicator_order',90,0,0,TO_TIMESTAMP('2023-02-13 02:11:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Credit limit Usage(541679,D) -> CreditLimit_Usage_V(546816,D) -> main -> 10 -> default.cl_indicator_delivery
-- Column: CreditLimit_Usage_V.cl_indicator_delivery
-- 2023-02-13T01:11:53.453Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712327,0,546816,550370,615690,'F',TO_TIMESTAMP('2023-02-13 02:11:53','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'cl_indicator_delivery',100,0,0,TO_TIMESTAMP('2023-02-13 02:11:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Field: Credit limit Usage(541679,D) -> CreditLimit_Usage_V(546816,D) -> Section Group Partner
-- Column: CreditLimit_Usage_V.Section_Group_Partner_ID
-- 2023-02-13T01:12:48.350Z
UPDATE AD_Field SET SortNo=2.000000000000,Updated=TO_TIMESTAMP('2023-02-13 02:12:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=712320
;

-- Field: Credit limit Usage(541679,D) -> CreditLimit_Usage_V(546816,D) -> Business Partner
-- Column: CreditLimit_Usage_V.C_BPartner_ID
-- 2023-02-13T01:13:02.069Z
UPDATE AD_Field SET SortNo=3.000000000000,Updated=TO_TIMESTAMP('2023-02-13 02:13:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=712322
;

-- UI Element: Credit limit Usage(541679,D) -> CreditLimit_Usage_V(546816,D) -> main -> 10 -> default.creditlimit_by_department
-- Column: CreditLimit_Usage_V.creditlimit_by_department
-- 2023-02-13T01:13:48.725Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2023-02-13 02:13:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615688
;

-- UI Element: Credit limit Usage(541679,D) -> CreditLimit_Usage_V(546816,D) -> main -> 10 -> default.cl_indicator_order
-- Column: CreditLimit_Usage_V.cl_indicator_order
-- 2023-02-13T01:13:48.728Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2023-02-13 02:13:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615689
;

-- UI Element: Credit limit Usage(541679,D) -> CreditLimit_Usage_V(546816,D) -> main -> 10 -> default.cl_indicator_delivery
-- Column: CreditLimit_Usage_V.cl_indicator_delivery
-- 2023-02-13T01:13:48.731Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2023-02-13 02:13:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615690
;

-- UI Element: Credit limit Usage(541679,D) -> CreditLimit_Usage_V(546816,D) -> main -> 10 -> default.Section Group Partner
-- Column: CreditLimit_Usage_V.Section_Group_Partner_ID
-- 2023-02-13T01:13:52.361Z
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2023-02-13 02:13:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615682
;

-- UI Element: Credit limit Usage(541679,D) -> CreditLimit_Usage_V(546816,D) -> main -> 10 -> default.partner_code
-- Column: CreditLimit_Usage_V.partner_code
-- 2023-02-13T01:13:52.365Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2023-02-13 02:13:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615687
;

-- UI Element: Credit limit Usage(541679,D) -> CreditLimit_Usage_V(546816,D) -> main -> 10 -> default.Department
-- Column: CreditLimit_Usage_V.M_Department_ID
-- 2023-02-13T01:13:52.369Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2023-02-13 02:13:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615684
;

-- UI Element: Credit limit Usage(541679,D) -> CreditLimit_Usage_V(546816,D) -> main -> 10 -> default.Section Code
-- Column: CreditLimit_Usage_V.M_SectionCode_ID
-- 2023-02-13T01:13:52.372Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2023-02-13 02:13:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615686
;

-- UI Element: Credit limit Usage(541679,D) -> CreditLimit_Usage_V(546816,D) -> main -> 10 -> default.Credit limit
-- Column: CreditLimit_Usage_V.CreditLimit
-- 2023-02-13T01:13:52.376Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2023-02-13 02:13:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615685
;

-- UI Element: Credit limit Usage(541679,D) -> CreditLimit_Usage_V(546816,D) -> main -> 10 -> default.creditlimit_by_department
-- Column: CreditLimit_Usage_V.creditlimit_by_department
-- 2023-02-13T01:13:52.379Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2023-02-13 02:13:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615688
;

-- UI Element: Credit limit Usage(541679,D) -> CreditLimit_Usage_V(546816,D) -> main -> 10 -> default.cl_indicator_order
-- Column: CreditLimit_Usage_V.cl_indicator_order
-- 2023-02-13T01:13:52.382Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2023-02-13 02:13:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615689
;

-- UI Element: Credit limit Usage(541679,D) -> CreditLimit_Usage_V(546816,D) -> main -> 10 -> default.cl_indicator_delivery
-- Column: CreditLimit_Usage_V.cl_indicator_delivery
-- 2023-02-13T01:13:52.385Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2023-02-13 02:13:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615690
;

