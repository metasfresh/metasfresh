-- Insert AD_Elements
INSERT INTO AD_Element (ad_element_id, ad_client_id, ad_org_id, isactive, created, createdby, updated, updatedby, columnname, entitytype, name, printname, description, help, po_name, po_printname, po_description, po_help, widgetsize, commitwarning, webui_namebrowse, webui_namenewbreadcrumb, webui_namenew) VALUES (582004, 0, 0, 'Y', '2023-02-02 20:48:07.000000 +01:00', 100, '2023-02-02 20:48:07.000000 +01:00', 100, 'creditlimit_by_department', 'D', 'creditlimit_by_department', 'creditlimit_by_department', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO AD_Element (ad_element_id, ad_client_id, ad_org_id, isactive, created, createdby, updated, updatedby, columnname, entitytype, name, printname, description, help, po_name, po_printname, po_description, po_help, widgetsize, commitwarning, webui_namebrowse, webui_namenewbreadcrumb, webui_namenew) VALUES (582005, 0, 0, 'Y', '2023-02-02 20:48:08.000000 +01:00', 100, '2023-02-02 20:48:08.000000 +01:00', 100, 'creditlimit_by_sectioncode', 'D', 'creditlimit_by_sectioncode', 'creditlimit_by_sectioncode', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO AD_Element (ad_element_id, ad_client_id, ad_org_id, isactive, created, createdby, updated, updatedby, columnname, entitytype, name, printname, description, help, po_name, po_printname, po_description, po_help, widgetsize, commitwarning, webui_namebrowse, webui_namenewbreadcrumb, webui_namenew) VALUES (582006, 0, 0, 'Y', '2023-02-02 20:48:09.000000 +01:00', 100, '2023-02-02 20:48:09.000000 +01:00', 100, 'cl_indicator_order', 'D', 'cl_indicator_order', 'cl_indicator_order', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO AD_Element (ad_element_id, ad_client_id, ad_org_id, isactive, created, createdby, updated, updatedby, columnname, entitytype, name, printname, description, help, po_name, po_printname, po_description, po_help, widgetsize, commitwarning, webui_namebrowse, webui_namenewbreadcrumb, webui_namenew) VALUES (582007, 0, 0, 'Y', '2023-02-02 20:48:10.000000 +01:00', 100, '2023-02-02 20:48:10.000000 +01:00', 100, 'cl_indicator_delivery', 'D', 'cl_indicator_delivery', 'cl_indicator_delivery', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO AD_Element (ad_element_id, ad_client_id, ad_org_id, isactive, created, createdby, updated, updatedby, columnname, entitytype, name, printname, description, help, po_name, po_printname, po_description, po_help, widgetsize, commitwarning, webui_namebrowse, webui_namenewbreadcrumb, webui_namenew) VALUES (582009, 0, 0, 'Y', '2023-02-02 21:29:23.000000 +01:00', 100, '2023-02-03 15:15:09.000000 +01:00', 100, 'C_BPartner_Creditlimit_Usage_V_ID', 'D', 'CL Usage', 'CL Usage', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO AD_Element (ad_element_id, ad_client_id, ad_org_id, isactive, created, createdby, updated, updatedby, columnname, entitytype, name, printname, description, help, po_name, po_printname, po_description, po_help, widgetsize, commitwarning, webui_namebrowse, webui_namenewbreadcrumb, webui_namenew) VALUES (582015, 0, 0, 'Y', '2023-02-03 17:07:10.000000 +01:00', 100, '2023-02-03 17:07:10.000000 +01:00', 100, 'partner_code', 'D', 'partner_code', 'partner_code', null, null, null, null, null, null, null, null, null, null, null);

INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID, CommitWarning, Description, Help, Name, PO_Description, PO_Help, PO_Name, PO_PrintName, PrintName, WEBUI_NameBrowse, WEBUI_NameNew, WEBUI_NameNewBreadcrumb, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy)
SELECT l.AD_Language,
       t.AD_Element_ID,
       t.CommitWarning,
       t.Description,
       t.Help,
       t.Name,
       t.PO_Description,
       t.PO_Help,
       t.PO_Name,
       t.PO_PrintName,
       t.PrintName,
       t.WEBUI_NameBrowse,
       t.WEBUI_NameNew,
       t.WEBUI_NameNewBreadcrumb,
       'N',
       t.AD_Client_ID,
       t.AD_Org_ID,
       t.Created,
       t.Createdby,
       t.Updated,
       t.UpdatedBy
FROM AD_Language l,
     AD_Element t
WHERE l.IsActive = 'Y'
  AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Element_ID IN (582015, 582009, 582007, 582006,  582005, 582004)
  AND NOT EXISTS(SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Element_ID = t.AD_Element_ID)
;

-- Table: C_BPartner_Creditlimit_Usage_V
-- 2023-02-03T16:06:57.039Z
INSERT INTO AD_Table (AccessLevel,ACTriggerLength,AD_Client_ID,AD_Org_ID,AD_Table_ID,CopyColumnsFromTable,Created,CreatedBy,Description,EntityType,ImportTable,IsActive,IsAutocomplete,IsChangeLog,IsDeleteable,IsDLM,IsEnableRemoteCacheInvalidation,IsHighVolume,IsSecurityEnabled,IsView,LoadSeq,Name,PersonalDataCategory,ReplicationType,TableName,TooltipType,Updated,UpdatedBy,WEBUI_View_PageLength) VALUES ('3',0,0,0,542293,'N',TO_TIMESTAMP('2023-02-03 17:06:56','YYYY-MM-DD HH24:MI:SS'),100,'Business Partner Credit Limit Usage','U','N','Y','N','N','N','N','Y','N','N','Y',0,'Business Partner Credit Limit Usage','NP','L','C_BPartner_Creditlimit_Usage_V','DTI',TO_TIMESTAMP('2023-02-03 17:06:56','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- Column: C_BPartner_Creditlimit_Usage_V.C_BPartner_Creditlimit_Usage_V_ID
-- 2023-02-03T16:07:09.850Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,585843,582009,0,13,542293,'C_BPartner_Creditlimit_Usage_V_ID',TO_TIMESTAMP('2023-02-03 17:07:09','YYYY-MM-DD HH24:MI:SS'),100,'D',10,'Y','Y','N','N','N','Y','N','N','N','N','N','Business Partner Credit Limit Usage',TO_TIMESTAMP('2023-02-03 17:07:09','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-03T16:07:09.852Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585843 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-03T16:07:09.872Z
/* DDL */  select update_Column_Translation_From_AD_Element(582009) 
;

-- Column: C_BPartner_Creditlimit_Usage_V.C_BPartner_ID
-- 2023-02-03T16:07:10.385Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,585844,187,0,30,542293,'C_BPartner_ID',TO_TIMESTAMP('2023-02-03 17:07:10','YYYY-MM-DD HH24:MI:SS'),100,'','D',10,'','Y','Y','N','N','N','N','N','N','N','N','N','Business Partner',TO_TIMESTAMP('2023-02-03 17:07:10','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-03T16:07:10.387Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585844 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-03T16:07:10.388Z
/* DDL */  select update_Column_Translation_From_AD_Element(187) 
;

-- Column: C_BPartner_Creditlimit_Usage_V.partner_code
-- 2023-02-03T16:07:11.092Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,585845,582015,0,10,542293,'partner_code',TO_TIMESTAMP('2023-02-03 17:07:10','YYYY-MM-DD HH24:MI:SS'),100,'D',40,'Y','Y','N','N','N','N','N','N','N','N','N','partner_code',TO_TIMESTAMP('2023-02-03 17:07:10','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-03T16:07:11.093Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585845 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-03T16:07:11.095Z
/* DDL */  select update_Column_Translation_From_AD_Element(582015) 
;

-- Column: C_BPartner_Creditlimit_Usage_V.M_Department_ID
-- 2023-02-03T16:07:11.678Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,585846,581944,0,30,542293,'M_Department_ID',TO_TIMESTAMP('2023-02-03 17:07:11','YYYY-MM-DD HH24:MI:SS'),100,'D',10,'Y','Y','N','N','N','N','N','N','N','N','N','Department',TO_TIMESTAMP('2023-02-03 17:07:11','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-03T16:07:11.680Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585846 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-03T16:07:11.681Z
/* DDL */  select update_Column_Translation_From_AD_Element(581944) 
;

-- Column: C_BPartner_Creditlimit_Usage_V.M_SectionCode_ID
-- 2023-02-03T16:07:12.187Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,585847,581238,0,30,542293,'M_SectionCode_ID',TO_TIMESTAMP('2023-02-03 17:07:11','YYYY-MM-DD HH24:MI:SS'),100,'D',10,'Y','Y','N','N','N','N','N','N','N','N','N','Section Code',TO_TIMESTAMP('2023-02-03 17:07:11','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-03T16:07:12.189Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585847 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-03T16:07:12.190Z
/* DDL */  select update_Column_Translation_From_AD_Element(581238) 
;

-- Column: C_BPartner_Creditlimit_Usage_V.CreditLimit
-- 2023-02-03T16:07:12.953Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,585848,855,0,22,542293,'CreditLimit',TO_TIMESTAMP('2023-02-03 17:07:12','YYYY-MM-DD HH24:MI:SS'),100,'Amount of Credit allowed','D',14,'The Credit Limit field indicates the credit limit for this account.','Y','Y','N','N','N','N','N','N','N','N','N','Credit limit',TO_TIMESTAMP('2023-02-03 17:07:12','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-03T16:07:12.956Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585848 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-03T16:07:12.957Z
/* DDL */  select update_Column_Translation_From_AD_Element(855) 
;

-- Column: C_BPartner_Creditlimit_Usage_V.creditlimit_by_department
-- 2023-02-03T16:07:13.954Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,585849,582004,0,22,542293,'creditlimit_by_department',TO_TIMESTAMP('2023-02-03 17:07:13','YYYY-MM-DD HH24:MI:SS'),100,'D',14,'Y','Y','N','N','N','N','N','N','N','N','N','creditlimit_by_department',TO_TIMESTAMP('2023-02-03 17:07:13','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-03T16:07:13.957Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585849 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-03T16:07:13.958Z
/* DDL */  select update_Column_Translation_From_AD_Element(582004) 
;

-- Column: C_BPartner_Creditlimit_Usage_V.creditlimit_by_sectioncode
-- 2023-02-03T16:07:14.975Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,585850,582005,0,22,542293,'creditlimit_by_sectioncode',TO_TIMESTAMP('2023-02-03 17:07:14','YYYY-MM-DD HH24:MI:SS'),100,'D',14,'Y','Y','N','N','N','N','N','N','N','N','N','creditlimit_by_sectioncode',TO_TIMESTAMP('2023-02-03 17:07:14','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-03T16:07:14.977Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585850 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-03T16:07:14.979Z
/* DDL */  select update_Column_Translation_From_AD_Element(582005) 
;

-- Column: C_BPartner_Creditlimit_Usage_V.cl_indicator_order
-- 2023-02-03T16:07:18.774Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,585851,582006,0,14,542293,'cl_indicator_order',TO_TIMESTAMP('2023-02-03 17:07:18','YYYY-MM-DD HH24:MI:SS'),100,'D',2147483647,'Y','Y','N','N','N','N','N','N','N','N','N','cl_indicator_order',TO_TIMESTAMP('2023-02-03 17:07:18','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-03T16:07:18.775Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585851 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-03T16:07:18.776Z
/* DDL */  select update_Column_Translation_From_AD_Element(582006) 
;

-- Column: C_BPartner_Creditlimit_Usage_V.cl_indicator_delivery
-- 2023-02-03T16:07:19.240Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,585852,582007,0,14,542293,'cl_indicator_delivery',TO_TIMESTAMP('2023-02-03 17:07:19','YYYY-MM-DD HH24:MI:SS'),100,'D',2147483647,'Y','Y','N','N','N','N','N','N','N','N','N','cl_indicator_delivery',TO_TIMESTAMP('2023-02-03 17:07:19','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-03T16:07:19.241Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585852 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-03T16:07:19.243Z
/* DDL */  select update_Column_Translation_From_AD_Element(582007) 
;

-- Column: C_BPartner_Creditlimit_Usage_V.Created
-- 2023-02-03T16:07:19.695Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,585853,245,0,16,542293,'Created',TO_TIMESTAMP('2023-02-03 17:07:19','YYYY-MM-DD HH24:MI:SS'),100,'Date this record was created','D',35,'The Created field indicates the date that this record was created.','Y','N','N','N','N','N','N','N','N','N','N','Created',TO_TIMESTAMP('2023-02-03 17:07:19','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-03T16:07:19.696Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585853 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-03T16:07:19.698Z
/* DDL */  select update_Column_Translation_From_AD_Element(245) 
;

-- Column: C_BPartner_Creditlimit_Usage_V.Updated
-- 2023-02-03T16:07:20.407Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,585854,607,0,16,542293,'Updated',TO_TIMESTAMP('2023-02-03 17:07:20','YYYY-MM-DD HH24:MI:SS'),100,'Date this record was updated','D',35,'The Updated field indicates the date that this record was updated.','Y','N','N','N','N','N','N','N','N','N','N','Updated',TO_TIMESTAMP('2023-02-03 17:07:20','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-03T16:07:20.408Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585854 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-03T16:07:20.410Z
/* DDL */  select update_Column_Translation_From_AD_Element(607) 
;

-- Column: C_BPartner_Creditlimit_Usage_V.CreatedBy
-- 2023-02-03T16:07:20.990Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,585855,246,0,18,110,542293,'CreatedBy',TO_TIMESTAMP('2023-02-03 17:07:20','YYYY-MM-DD HH24:MI:SS'),100,'User who created this records','D',10,'The Created By field indicates the user who created this record.','Y','N','N','N','N','N','N','N','N','N','N','Created By',TO_TIMESTAMP('2023-02-03 17:07:20','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-03T16:07:20.991Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585855 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-03T16:07:20.993Z
/* DDL */  select update_Column_Translation_From_AD_Element(246) 
;

-- Column: C_BPartner_Creditlimit_Usage_V.UpdatedBy
-- 2023-02-03T16:07:21.557Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,585856,608,0,18,110,542293,'UpdatedBy',TO_TIMESTAMP('2023-02-03 17:07:21','YYYY-MM-DD HH24:MI:SS'),100,'User who updated this records','D',10,'The Updated By field indicates the user who updated this record.','Y','N','N','N','N','N','N','N','N','N','N','Updated By',TO_TIMESTAMP('2023-02-03 17:07:21','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-03T16:07:21.559Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585856 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-03T16:07:21.560Z
/* DDL */  select update_Column_Translation_From_AD_Element(608) 
;

-- Column: C_BPartner_Creditlimit_Usage_V.IsActive
-- 2023-02-03T16:07:22.092Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,585857,348,0,10,542293,'IsActive',TO_TIMESTAMP('2023-02-03 17:07:22','YYYY-MM-DD HH24:MI:SS'),100,'The record is active in the system','D',2147483647,'There are two methods of making records unavailable in the system: One is to delete the record, the other is to de-activate the record. A de-activated record is not available for selection, but available for reports.
There are two reasons for de-activating and not deleting records:
(1) The system requires the record for audit purposes.
(2) The record is referenced by other records. E.g., you cannot delete a Business Partner, if there are invoices for this partner record existing. You de-activate the Business Partner and prevent that this record is used for future entries.','Y','Y','N','N','N','N','N','N','N','N','N','Active',TO_TIMESTAMP('2023-02-03 17:07:22','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-03T16:07:22.093Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585857 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-03T16:07:22.094Z
/* DDL */  select update_Column_Translation_From_AD_Element(348) 
;

-- Column: C_BPartner_Creditlimit_Usage_V.AD_Client_ID
-- 2023-02-03T16:07:22.698Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,585858,102,0,30,542293,'AD_Client_ID',TO_TIMESTAMP('2023-02-03 17:07:22','YYYY-MM-DD HH24:MI:SS'),100,'Client/Tenant for this installation.','D',10,'A Client is a company or a legal entity. You cannot share data between Clients. Tenant is a synonym for Client.','Y','Y','N','N','N','N','N','N','N','N','N','Client',TO_TIMESTAMP('2023-02-03 17:07:22','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-03T16:07:22.699Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585858 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-03T16:07:22.701Z
/* DDL */  select update_Column_Translation_From_AD_Element(102) 
;

-- Column: C_BPartner_Creditlimit_Usage_V.AD_Org_ID
-- 2023-02-03T16:07:23.275Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,585859,113,0,30,542293,'AD_Org_ID',TO_TIMESTAMP('2023-02-03 17:07:23','YYYY-MM-DD HH24:MI:SS'),100,'Organisational entity within client','D',10,'An organisation is a unit of your client or legal entity - examples are store, department. You can share data between organisations.','Y','Y','N','N','N','N','N','N','N','N','N','Organisation',TO_TIMESTAMP('2023-02-03 17:07:23','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-03T16:07:23.277Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585859 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-03T16:07:23.278Z
/* DDL */  select update_Column_Translation_From_AD_Element(113) 
;

-- Column: C_BPartner_Creditlimit_Usage_V.C_BPartner_ID
-- 2023-02-03T16:08:13.853Z
UPDATE AD_Column SET IsIdentifier='Y', SeqNo=10,Updated=TO_TIMESTAMP('2023-02-03 17:08:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585844
;

-- Column: C_BPartner_Creditlimit_Usage_V.C_BPartner_ID
-- 2023-02-03T16:08:40.174Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2023-02-03 17:08:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585844
;

-- Column: C_BPartner_Creditlimit_Usage_V.M_Department_ID
-- 2023-02-03T16:09:13.978Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2023-02-03 17:09:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585846
;

-- Column: C_BPartner_Creditlimit_Usage_V.M_SectionCode_ID
-- 2023-02-03T16:09:26.221Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2023-02-03 17:09:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585847
;

-- Column: C_BPartner_Creditlimit_Usage_V.M_SectionCode_ID
-- 2023-02-03T16:09:58.434Z
UPDATE AD_Column SET IsShowFilterInline='Y',Updated=TO_TIMESTAMP('2023-02-03 17:09:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585847
;

-- Column: C_BPartner_Creditlimit_Usage_V.M_Department_ID
-- 2023-02-03T16:10:06.068Z
UPDATE AD_Column SET IsShowFilterInline='Y',Updated=TO_TIMESTAMP('2023-02-03 17:10:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585846
;

-- Column: C_BPartner_Creditlimit_Usage_V.C_BPartner_ID
-- 2023-02-03T16:10:17.773Z
UPDATE AD_Column SET IsShowFilterInline='Y',Updated=TO_TIMESTAMP('2023-02-03 17:10:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585844
;

-- Table: C_BPartner_Creditlimit_Usage_V
-- 2023-02-03T16:10:45.067Z
UPDATE AD_Table SET EntityType='D',Updated=TO_TIMESTAMP('2023-02-03 17:10:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=542293
;

-- 2023-02-03T16:11:27.908Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582017,0,TO_TIMESTAMP('2023-02-03 17:11:27','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Credit Limit Usage','Credit Limit Usage',TO_TIMESTAMP('2023-02-03 17:11:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-03T16:11:27.910Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582017 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Window: Credit Limit Usage, InternalName=null
-- 2023-02-03T16:12:13.448Z
INSERT INTO AD_Window (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsBetaFunctionality,IsDefault,IsEnableRemoteCacheInvalidation,IsExcludeFromZoomTargets,IsOneInstanceOnly,IsOverrideInMenu,IsSOTrx,Name,Processing,Updated,UpdatedBy,WindowType,WinHeight,WinWidth,ZoomIntoPriority) VALUES (0,582017,0,541673,TO_TIMESTAMP('2023-02-03 17:12:13','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N','Y','N','N','N','Y','Credit Limit Usage','N',TO_TIMESTAMP('2023-02-03 17:12:13','YYYY-MM-DD HH24:MI:SS'),100,'Q',0,0,100)
;

-- 2023-02-03T16:12:13.449Z
INSERT INTO AD_Window_Trl (AD_Language,AD_Window_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Window_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Window t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Window_ID=541673 AND NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Window_ID=t.AD_Window_ID)
;

-- 2023-02-03T16:12:13.451Z
/* DDL */  select update_window_translation_from_ad_element(582017) 
;

-- 2023-02-03T16:12:13.457Z
DELETE FROM AD_Element_Link WHERE AD_Window_ID=541673
;

-- 2023-02-03T16:12:13.457Z
/* DDL */ select AD_Element_Link_Create_Missing_Window(541673)
;

-- Tab: Credit Limit Usage(541673,D) -> Business Partner Credit Limit Usage
-- Table: C_BPartner_Creditlimit_Usage_V
-- 2023-02-03T16:13:02.762Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,582009,0,546804,542293,541673,'Y',TO_TIMESTAMP('2023-02-03 17:13:02','YYYY-MM-DD HH24:MI:SS'),100,'D','N','N','A','C_BPartner_Creditlimit_Usage_V','Y','N','Y','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N',0,'Business Partner Credit Limit Usage','N',10,0,TO_TIMESTAMP('2023-02-03 17:13:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-03T16:13:02.763Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Tab_ID=546804 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2023-02-03T16:13:02.765Z
/* DDL */  select update_tab_translation_from_ad_element(582009) 
;

-- 2023-02-03T16:13:02.771Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(546804)
;

-- Tab: Credit Limit Usage(541673,D) -> Business Partner Credit Limit Usage
-- Table: C_BPartner_Creditlimit_Usage_V
-- 2023-02-03T16:13:20.858Z
UPDATE AD_Tab SET IsGridModeOnly='Y',Updated=TO_TIMESTAMP('2023-02-03 17:13:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=546804
;

-- Tab: Credit Limit Usage(541673,D) -> Business Partner Credit Limit Usage
-- Table: C_BPartner_Creditlimit_Usage_V
-- 2023-02-03T16:13:24.411Z
UPDATE AD_Tab SET IsCheckParentsChanged='N',Updated=TO_TIMESTAMP('2023-02-03 17:13:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=546804
;

-- Tab: Credit Limit Usage(541673,D) -> Business Partner Credit Limit Usage
-- Table: C_BPartner_Creditlimit_Usage_V
-- 2023-02-03T16:13:31.703Z
UPDATE AD_Tab SET IsInsertRecord='N', IsReadOnly='Y',Updated=TO_TIMESTAMP('2023-02-03 17:13:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=546804
;

-- Field: Credit Limit Usage(541673,D) -> Business Partner Credit Limit Usage(546804,D) -> Business Partner Credit Limit Usage
-- Column: C_BPartner_Creditlimit_Usage_V.C_BPartner_Creditlimit_Usage_V_ID
-- 2023-02-03T16:13:43.366Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585843,712129,0,546804,TO_TIMESTAMP('2023-02-03 17:13:43','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Business Partner Credit Limit Usage',TO_TIMESTAMP('2023-02-03 17:13:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-03T16:13:43.368Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712129 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-03T16:13:43.370Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582009) 
;

-- 2023-02-03T16:13:43.374Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712129
;

-- 2023-02-03T16:13:43.375Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712129)
;

-- Field: Credit Limit Usage(541673,D) -> Business Partner Credit Limit Usage(546804,D) -> Business Partner
-- Column: C_BPartner_Creditlimit_Usage_V.C_BPartner_ID
-- 2023-02-03T16:13:43.434Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585844,712130,0,546804,TO_TIMESTAMP('2023-02-03 17:13:43','YYYY-MM-DD HH24:MI:SS'),100,'',10,'D','','Y','Y','N','N','N','N','N','Business Partner',TO_TIMESTAMP('2023-02-03 17:13:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-03T16:13:43.435Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712130 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-03T16:13:43.436Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(187) 
;

-- 2023-02-03T16:13:43.467Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712130
;

-- 2023-02-03T16:13:43.467Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712130)
;

-- Field: Credit Limit Usage(541673,D) -> Business Partner Credit Limit Usage(546804,D) -> partner_code
-- Column: C_BPartner_Creditlimit_Usage_V.partner_code
-- 2023-02-03T16:13:43.536Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585845,712131,0,546804,TO_TIMESTAMP('2023-02-03 17:13:43','YYYY-MM-DD HH24:MI:SS'),100,40,'D','Y','Y','N','N','N','N','N','partner_code',TO_TIMESTAMP('2023-02-03 17:13:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-03T16:13:43.537Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712131 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-03T16:13:43.538Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582015) 
;

-- 2023-02-03T16:13:43.542Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712131
;

-- 2023-02-03T16:13:43.543Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712131)
;

-- Field: Credit Limit Usage(541673,D) -> Business Partner Credit Limit Usage(546804,D) -> Department
-- Column: C_BPartner_Creditlimit_Usage_V.M_Department_ID
-- 2023-02-03T16:13:43.597Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585846,712132,0,546804,TO_TIMESTAMP('2023-02-03 17:13:43','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','Y','N','N','N','N','N','Department',TO_TIMESTAMP('2023-02-03 17:13:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-03T16:13:43.598Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712132 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-03T16:13:43.599Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581944) 
;

-- 2023-02-03T16:13:43.604Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712132
;

-- 2023-02-03T16:13:43.604Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712132)
;

-- Field: Credit Limit Usage(541673,D) -> Business Partner Credit Limit Usage(546804,D) -> Section Code
-- Column: C_BPartner_Creditlimit_Usage_V.M_SectionCode_ID
-- 2023-02-03T16:13:43.659Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585847,712133,0,546804,TO_TIMESTAMP('2023-02-03 17:13:43','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','Y','N','N','N','N','N','Section Code',TO_TIMESTAMP('2023-02-03 17:13:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-03T16:13:43.660Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712133 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-03T16:13:43.661Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581238) 
;

-- 2023-02-03T16:13:43.677Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712133
;

-- 2023-02-03T16:13:43.677Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712133)
;

-- Field: Credit Limit Usage(541673,D) -> Business Partner Credit Limit Usage(546804,D) -> Credit limit
-- Column: C_BPartner_Creditlimit_Usage_V.CreditLimit
-- 2023-02-03T16:13:43.726Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585848,712134,0,546804,TO_TIMESTAMP('2023-02-03 17:13:43','YYYY-MM-DD HH24:MI:SS'),100,'Amount of Credit allowed',14,'D','The Credit Limit field indicates the credit limit for this account.','Y','Y','N','N','N','N','N','Credit limit',TO_TIMESTAMP('2023-02-03 17:13:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-03T16:13:43.727Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712134 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-03T16:13:43.729Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(855) 
;

-- 2023-02-03T16:13:43.738Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712134
;

-- 2023-02-03T16:13:43.738Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712134)
;

-- Field: Credit Limit Usage(541673,D) -> Business Partner Credit Limit Usage(546804,D) -> creditlimit_by_department
-- Column: C_BPartner_Creditlimit_Usage_V.creditlimit_by_department
-- 2023-02-03T16:13:43.792Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585849,712135,0,546804,TO_TIMESTAMP('2023-02-03 17:13:43','YYYY-MM-DD HH24:MI:SS'),100,14,'D','Y','Y','N','N','N','N','N','creditlimit_by_department',TO_TIMESTAMP('2023-02-03 17:13:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-03T16:13:43.793Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712135 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-03T16:13:43.794Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582004) 
;

-- 2023-02-03T16:13:43.798Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712135
;

-- 2023-02-03T16:13:43.798Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712135)
;

-- Field: Credit Limit Usage(541673,D) -> Business Partner Credit Limit Usage(546804,D) -> creditlimit_by_sectioncode
-- Column: C_BPartner_Creditlimit_Usage_V.creditlimit_by_sectioncode
-- 2023-02-03T16:13:43.852Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585850,712136,0,546804,TO_TIMESTAMP('2023-02-03 17:13:43','YYYY-MM-DD HH24:MI:SS'),100,14,'D','Y','Y','N','N','N','N','N','creditlimit_by_sectioncode',TO_TIMESTAMP('2023-02-03 17:13:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-03T16:13:43.853Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712136 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-03T16:13:43.854Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582005) 
;

-- 2023-02-03T16:13:43.857Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712136
;

-- 2023-02-03T16:13:43.858Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712136)
;

-- Field: Credit Limit Usage(541673,D) -> Business Partner Credit Limit Usage(546804,D) -> cl_indicator_order
-- Column: C_BPartner_Creditlimit_Usage_V.cl_indicator_order
-- 2023-02-03T16:13:43.906Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585851,712137,0,546804,TO_TIMESTAMP('2023-02-03 17:13:43','YYYY-MM-DD HH24:MI:SS'),100,2147483647,'D','Y','Y','N','N','N','N','N','cl_indicator_order',TO_TIMESTAMP('2023-02-03 17:13:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-03T16:13:43.907Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712137 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-03T16:13:43.908Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582006) 
;

-- 2023-02-03T16:13:43.912Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712137
;

-- 2023-02-03T16:13:43.912Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712137)
;

-- Field: Credit Limit Usage(541673,D) -> Business Partner Credit Limit Usage(546804,D) -> cl_indicator_delivery
-- Column: C_BPartner_Creditlimit_Usage_V.cl_indicator_delivery
-- 2023-02-03T16:13:43.967Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585852,712138,0,546804,TO_TIMESTAMP('2023-02-03 17:13:43','YYYY-MM-DD HH24:MI:SS'),100,2147483647,'D','Y','Y','N','N','N','N','N','cl_indicator_delivery',TO_TIMESTAMP('2023-02-03 17:13:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-03T16:13:43.968Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712138 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-03T16:13:43.970Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582007) 
;

-- 2023-02-03T16:13:43.974Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712138
;

-- 2023-02-03T16:13:43.974Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712138)
;

-- Field: Credit Limit Usage(541673,D) -> Business Partner Credit Limit Usage(546804,D) -> Created
-- Column: C_BPartner_Creditlimit_Usage_V.Created
-- 2023-02-03T16:13:44.021Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585853,712139,0,546804,TO_TIMESTAMP('2023-02-03 17:13:43','YYYY-MM-DD HH24:MI:SS'),100,'Date this record was created',35,'D','The Created field indicates the date that this record was created.','Y','Y','N','N','N','N','N','Created',TO_TIMESTAMP('2023-02-03 17:13:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-03T16:13:44.022Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712139 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-03T16:13:44.023Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(245) 
;

-- 2023-02-03T16:13:44.210Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712139
;

-- 2023-02-03T16:13:44.210Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712139)
;

-- Field: Credit Limit Usage(541673,D) -> Business Partner Credit Limit Usage(546804,D) -> Updated
-- Column: C_BPartner_Creditlimit_Usage_V.Updated
-- 2023-02-03T16:13:44.270Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585854,712140,0,546804,TO_TIMESTAMP('2023-02-03 17:13:44','YYYY-MM-DD HH24:MI:SS'),100,'Date this record was updated',35,'D','The Updated field indicates the date that this record was updated.','Y','Y','N','N','N','N','N','Updated',TO_TIMESTAMP('2023-02-03 17:13:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-03T16:13:44.271Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712140 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-03T16:13:44.271Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(607) 
;

-- 2023-02-03T16:13:44.370Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712140
;

-- 2023-02-03T16:13:44.371Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712140)
;

-- Field: Credit Limit Usage(541673,D) -> Business Partner Credit Limit Usage(546804,D) -> Created By
-- Column: C_BPartner_Creditlimit_Usage_V.CreatedBy
-- 2023-02-03T16:13:44.429Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585855,712141,0,546804,TO_TIMESTAMP('2023-02-03 17:13:44','YYYY-MM-DD HH24:MI:SS'),100,'User who created this records',10,'D','The Created By field indicates the user who created this record.','Y','Y','N','N','N','N','N','Created By',TO_TIMESTAMP('2023-02-03 17:13:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-03T16:13:44.430Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712141 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-03T16:13:44.432Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(246) 
;

-- 2023-02-03T16:13:44.500Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712141
;

-- 2023-02-03T16:13:44.500Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712141)
;

-- Field: Credit Limit Usage(541673,D) -> Business Partner Credit Limit Usage(546804,D) -> Updated By
-- Column: C_BPartner_Creditlimit_Usage_V.UpdatedBy
-- 2023-02-03T16:13:44.564Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585856,712142,0,546804,TO_TIMESTAMP('2023-02-03 17:13:44','YYYY-MM-DD HH24:MI:SS'),100,'User who updated this records',10,'D','The Updated By field indicates the user who updated this record.','Y','Y','N','N','N','N','N','Updated By',TO_TIMESTAMP('2023-02-03 17:13:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-03T16:13:44.564Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712142 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-03T16:13:44.565Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(608) 
;

-- 2023-02-03T16:13:44.643Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712142
;

-- 2023-02-03T16:13:44.643Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712142)
;

-- Field: Credit Limit Usage(541673,D) -> Business Partner Credit Limit Usage(546804,D) -> Active
-- Column: C_BPartner_Creditlimit_Usage_V.IsActive
-- 2023-02-03T16:13:44.697Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585857,712143,0,546804,TO_TIMESTAMP('2023-02-03 17:13:44','YYYY-MM-DD HH24:MI:SS'),100,'The record is active in the system',2147483647,'D','There are two methods of making records unavailable in the system: One is to delete the record, the other is to de-activate the record. A de-activated record is not available for selection, but available for reports.
There are two reasons for de-activating and not deleting records:
(1) The system requires the record for audit purposes.
(2) The record is referenced by other records. E.g., you cannot delete a Business Partner, if there are invoices for this partner record existing. You de-activate the Business Partner and prevent that this record is used for future entries.','Y','Y','N','N','N','N','N','Active',TO_TIMESTAMP('2023-02-03 17:13:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-03T16:13:44.698Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712143 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-03T16:13:44.699Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2023-02-03T16:13:45.078Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712143
;

-- 2023-02-03T16:13:45.078Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712143)
;

-- Field: Credit Limit Usage(541673,D) -> Business Partner Credit Limit Usage(546804,D) -> Client
-- Column: C_BPartner_Creditlimit_Usage_V.AD_Client_ID
-- 2023-02-03T16:13:45.146Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585858,712144,0,546804,TO_TIMESTAMP('2023-02-03 17:13:45','YYYY-MM-DD HH24:MI:SS'),100,'Client/Tenant for this installation.',10,'D','A Client is a company or a legal entity. You cannot share data between Clients. Tenant is a synonym for Client.','Y','Y','N','N','N','Y','N','Client',TO_TIMESTAMP('2023-02-03 17:13:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-03T16:13:45.147Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712144 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-03T16:13:45.149Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2023-02-03T16:13:45.366Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712144
;

-- 2023-02-03T16:13:45.366Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712144)
;

-- Field: Credit Limit Usage(541673,D) -> Business Partner Credit Limit Usage(546804,D) -> Organisation
-- Column: C_BPartner_Creditlimit_Usage_V.AD_Org_ID
-- 2023-02-03T16:13:45.424Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585859,712145,0,546804,TO_TIMESTAMP('2023-02-03 17:13:45','YYYY-MM-DD HH24:MI:SS'),100,'Organisational entity within client',10,'D','An organisation is a unit of your client or legal entity - examples are store, department. You can share data between organisations.','Y','Y','N','N','N','N','N','Organisation',TO_TIMESTAMP('2023-02-03 17:13:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-03T16:13:45.425Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712145 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-03T16:13:45.426Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2023-02-03T16:13:45.616Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712145
;

-- 2023-02-03T16:13:45.616Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712145)
;

-- Tab: Credit Limit Usage(541673,D) -> Business Partner Credit Limit Usage(546804,D)
-- UI Section: main
-- 2023-02-03T16:15:37.671Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,546804,545431,TO_TIMESTAMP('2023-02-03 17:15:37','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2023-02-03 17:15:37','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2023-02-03T16:15:37.672Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_UI_Section_ID=545431 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- UI Section: Credit Limit Usage(541673,D) -> Business Partner Credit Limit Usage(546804,D) -> main
-- UI Column: 10
-- 2023-02-03T16:15:45.713Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,546626,545431,TO_TIMESTAMP('2023-02-03 17:15:45','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2023-02-03 17:15:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: Credit Limit Usage(541673,D) -> Business Partner Credit Limit Usage(546804,D) -> main -> 10
-- UI Element Group: main
-- 2023-02-03T16:16:04.262Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546626,550338,TO_TIMESTAMP('2023-02-03 17:16:04','YYYY-MM-DD HH24:MI:SS'),100,'Y','main',10,TO_TIMESTAMP('2023-02-03 17:16:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Credit Limit Usage(541673,D) -> Business Partner Credit Limit Usage(546804,D) -> main -> 10 -> main.partner_code
-- Column: C_BPartner_Creditlimit_Usage_V.partner_code
-- 2023-02-03T16:16:23.562Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,712131,0,546804,550338,615562,'F',TO_TIMESTAMP('2023-02-03 17:16:23','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'partner_code',10,0,0,TO_TIMESTAMP('2023-02-03 17:16:23','YYYY-MM-DD HH24:MI:SS'),100,'S')
;

-- UI Element: Credit Limit Usage(541673,D) -> Business Partner Credit Limit Usage(546804,D) -> main -> 10 -> main.Department
-- Column: C_BPartner_Creditlimit_Usage_V.M_Department_ID
-- 2023-02-03T16:16:37.380Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712132,0,546804,550338,615563,'F',TO_TIMESTAMP('2023-02-03 17:16:37','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Department',20,0,0,TO_TIMESTAMP('2023-02-03 17:16:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Credit Limit Usage(541673,D) -> Business Partner Credit Limit Usage(546804,D) -> main -> 10 -> main.Section Code
-- Column: C_BPartner_Creditlimit_Usage_V.M_SectionCode_ID
-- 2023-02-03T16:17:12.569Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712133,0,546804,550338,615564,'F',TO_TIMESTAMP('2023-02-03 17:17:12','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Section Code',30,0,0,TO_TIMESTAMP('2023-02-03 17:17:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Credit Limit Usage(541673,D) -> Business Partner Credit Limit Usage(546804,D) -> main -> 10 -> main.creditlimit_by_department
-- Column: C_BPartner_Creditlimit_Usage_V.creditlimit_by_department
-- 2023-02-03T16:17:42.972Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712135,0,546804,550338,615565,'F',TO_TIMESTAMP('2023-02-03 17:17:42','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'creditlimit_by_department',40,0,0,TO_TIMESTAMP('2023-02-03 17:17:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Credit Limit Usage(541673,D) -> Business Partner Credit Limit Usage(546804,D) -> main -> 10 -> main.Credit limit
-- Column: C_BPartner_Creditlimit_Usage_V.CreditLimit
-- 2023-02-03T16:18:10.767Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712134,0,546804,550338,615566,'F',TO_TIMESTAMP('2023-02-03 17:18:10','YYYY-MM-DD HH24:MI:SS'),100,'Amount of Credit allowed','The Credit Limit field indicates the credit limit for this account.','Y','N','N','Y','N','N','N',0,'Credit limit',50,0,0,TO_TIMESTAMP('2023-02-03 17:18:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Credit Limit Usage(541673,D) -> Business Partner Credit Limit Usage(546804,D) -> main -> 10 -> main.cl_indicator_order
-- Column: C_BPartner_Creditlimit_Usage_V.cl_indicator_order
-- 2023-02-03T16:18:22.156Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712137,0,546804,550338,615567,'F',TO_TIMESTAMP('2023-02-03 17:18:22','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'cl_indicator_order',60,0,0,TO_TIMESTAMP('2023-02-03 17:18:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Credit Limit Usage(541673,D) -> Business Partner Credit Limit Usage(546804,D) -> main -> 10 -> main.cl_indicator_delivery
-- Column: C_BPartner_Creditlimit_Usage_V.cl_indicator_delivery
-- 2023-02-03T16:18:30.865Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712138,0,546804,550338,615568,'F',TO_TIMESTAMP('2023-02-03 17:18:30','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'cl_indicator_delivery',70,0,0,TO_TIMESTAMP('2023-02-03 17:18:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Credit Limit Usage(541673,D) -> Business Partner Credit Limit Usage(546804,D) -> main -> 10 -> main.partner_code
-- Column: C_BPartner_Creditlimit_Usage_V.partner_code
-- 2023-02-03T16:19:11.025Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2023-02-03 17:19:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615562
;

-- UI Element: Credit Limit Usage(541673,D) -> Business Partner Credit Limit Usage(546804,D) -> main -> 10 -> main.Department
-- Column: C_BPartner_Creditlimit_Usage_V.M_Department_ID
-- 2023-02-03T16:19:11.030Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2023-02-03 17:19:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615563
;

-- UI Element: Credit Limit Usage(541673,D) -> Business Partner Credit Limit Usage(546804,D) -> main -> 10 -> main.Section Code
-- Column: C_BPartner_Creditlimit_Usage_V.M_SectionCode_ID
-- 2023-02-03T16:19:11.032Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2023-02-03 17:19:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615564
;

-- UI Element: Credit Limit Usage(541673,D) -> Business Partner Credit Limit Usage(546804,D) -> main -> 10 -> main.creditlimit_by_department
-- Column: C_BPartner_Creditlimit_Usage_V.creditlimit_by_department
-- 2023-02-03T16:19:11.036Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2023-02-03 17:19:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615565
;

-- UI Element: Credit Limit Usage(541673,D) -> Business Partner Credit Limit Usage(546804,D) -> main -> 10 -> main.Credit limit
-- Column: C_BPartner_Creditlimit_Usage_V.CreditLimit
-- 2023-02-03T16:19:11.040Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2023-02-03 17:19:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615566
;

-- UI Element: Credit Limit Usage(541673,D) -> Business Partner Credit Limit Usage(546804,D) -> main -> 10 -> main.cl_indicator_order
-- Column: C_BPartner_Creditlimit_Usage_V.cl_indicator_order
-- 2023-02-03T16:19:11.043Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2023-02-03 17:19:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615567
;

-- UI Element: Credit Limit Usage(541673,D) -> Business Partner Credit Limit Usage(546804,D) -> main -> 10 -> main.cl_indicator_delivery
-- Column: C_BPartner_Creditlimit_Usage_V.cl_indicator_delivery
-- 2023-02-03T16:19:11.044Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2023-02-03 17:19:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615568
;

-- Name: Credit Limit Usage
-- Action Type: W
-- Window: Credit Limit Usage(541673,D)
-- 2023-02-03T16:22:28.786Z
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('W',0,582017,542050,0,541673,TO_TIMESTAMP('2023-02-03 17:22:28','YYYY-MM-DD HH24:MI:SS'),100,'D','Credit Limit Usage','Y','N','N','Y','N','Credit Limit Usage',TO_TIMESTAMP('2023-02-03 17:22:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-03T16:22:28.788Z
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Menu_ID=542050 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2023-02-03T16:22:28.790Z
INSERT INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 542050, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=542050)
;

-- 2023-02-03T16:22:28.795Z
/* DDL */  select update_menu_translation_from_ad_element(582017) 
;

-- Reordering children of `Logistics`
-- Node name: `Tour`
-- 2023-02-03T16:22:29.355Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540796 AND AD_Tree_ID=10
;

-- Node name: `Tourversion`
-- 2023-02-03T16:22:29.355Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540798 AND AD_Tree_ID=10
;

-- Node name: `Delivery Days`
-- 2023-02-03T16:22:29.356Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540797 AND AD_Tree_ID=10
;

-- Node name: `Distribution Order`
-- 2023-02-03T16:22:29.356Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540829 AND AD_Tree_ID=10
;

-- Node name: `Distributions Editor`
-- 2023-02-03T16:22:29.356Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540973 AND AD_Tree_ID=10
;

-- Node name: `Packing Instruction`
-- 2023-02-03T16:22:29.357Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540830 AND AD_Tree_ID=10
;

-- Node name: `Packing Instruction Version`
-- 2023-02-03T16:22:29.357Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540831 AND AD_Tree_ID=10
;

-- Node name: `CU-TU Allocation`
-- 2023-02-03T16:22:29.358Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541375 AND AD_Tree_ID=10
;

-- Node name: `Packing Material`
-- 2023-02-03T16:22:29.358Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540844 AND AD_Tree_ID=10
;

-- Node name: `Handling Unit`
-- 2023-02-03T16:22:29.358Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540846 AND AD_Tree_ID=10
;

-- Node name: `Packaging code`
-- 2023-02-03T16:22:29.359Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541384 AND AD_Tree_ID=10
;

-- Node name: `Handling Unit Transaction`
-- 2023-02-03T16:22:29.359Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540977 AND AD_Tree_ID=10
;

-- Node name: `Handling Unit (HU) Tracing`
-- 2023-02-03T16:22:29.360Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540900 AND AD_Tree_ID=10
;

-- Node name: `Delivery Planning`
-- 2023-02-03T16:22:29.360Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542021 AND AD_Tree_ID=10
;

-- Node name: `Delivery Instruction`
-- 2023-02-03T16:22:29.361Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542032 AND AD_Tree_ID=10
;

-- Node name: `Transport Disposition`
-- 2023-02-03T16:22:29.361Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540856 AND AD_Tree_ID=10
;

-- Node name: `Transport Delivery`
-- 2023-02-03T16:22:29.362Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540857 AND AD_Tree_ID=10
;

-- Node name: `Material Transactions`
-- 2023-02-03T16:22:29.362Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540860 AND AD_Tree_ID=10
;

-- Node name: `Transportation Order`
-- 2023-02-03T16:22:29.362Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540866 AND AD_Tree_ID=10
;

-- Node name: `Package`
-- 2023-02-03T16:22:29.363Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541057 AND AD_Tree_ID=10
;

-- Node name: `Internal Use`
-- 2023-02-03T16:22:29.363Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540918 AND AD_Tree_ID=10
;

-- Node name: `GO! Delivery Orders`
-- 2023-02-03T16:22:29.363Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541011 AND AD_Tree_ID=10
;

-- Node name: `Der Kurier Delivery Orders`
-- 2023-02-03T16:22:29.363Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=22, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541083 AND AD_Tree_ID=10
;

-- Node name: `DHL Delivery Order`
-- 2023-02-03T16:22:29.364Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=23, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541388 AND AD_Tree_ID=10
;

-- Node name: `DPD Delivery Order`
-- 2023-02-03T16:22:29.364Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=24, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541394 AND AD_Tree_ID=10
;

-- Node name: `Actions`
-- 2023-02-03T16:22:29.364Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=25, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000057 AND AD_Tree_ID=10
;

-- Node name: `Reports`
-- 2023-02-03T16:22:29.364Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=26, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000065 AND AD_Tree_ID=10
;

-- Node name: `Settings`
-- 2023-02-03T16:22:29.365Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=27, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000075 AND AD_Tree_ID=10
;

-- Node name: `HU Reservierung`
-- 2023-02-03T16:22:29.365Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=28, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541117 AND AD_Tree_ID=10
;

-- Node name: `Service Handling Units`
-- 2023-02-03T16:22:29.365Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=29, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541572 AND AD_Tree_ID=10
;

-- Node name: `HU QR Code`
-- 2023-02-03T16:22:29.365Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=30, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541905 AND AD_Tree_ID=10
;

-- Node name: `Means of Transportation`
-- 2023-02-03T16:22:29.365Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=31, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542024 AND AD_Tree_ID=10
;

-- Node name: `Department`
-- 2023-02-03T16:22:29.366Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=32, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542041 AND AD_Tree_ID=10
;

-- Node name: `Credit Limit (Departments)`
-- 2023-02-03T16:22:29.366Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=33, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542045 AND AD_Tree_ID=10
;

-- Node name: `Credit Limit Usage`
-- 2023-02-03T16:22:29.366Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=34, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542050 AND AD_Tree_ID=10
;

-- Window: Credit Limit Usage, InternalName=Credit Limit Usage
-- 2023-02-03T16:22:47.032Z
UPDATE AD_Window SET InternalName='Credit Limit Usage',Updated=TO_TIMESTAMP('2023-02-03 17:22:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Window_ID=541673
;

-- Column: C_BPartner_Creditlimit_Usage_V.CreditLimit
-- 2023-02-03T16:25:10.882Z
UPDATE AD_Column SET AD_Reference_ID=12,Updated=TO_TIMESTAMP('2023-02-03 17:25:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585848
;

-- Column: C_BPartner_Creditlimit_Usage_V.creditlimit_by_department
-- 2023-02-03T16:25:27.797Z
UPDATE AD_Column SET AD_Reference_ID=12,Updated=TO_TIMESTAMP('2023-02-03 17:25:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585849
;

-- Column: C_BPartner_Creditlimit_Usage_V.creditlimit_by_sectioncode
-- 2023-02-03T16:25:48.758Z
UPDATE AD_Column SET AD_Reference_ID=12,Updated=TO_TIMESTAMP('2023-02-03 17:25:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585850
;

-- Field: Credit Limit Usage(541673,D) -> Business Partner Credit Limit Usage(546804,D) -> Business Partner
-- Column: C_BPartner_Creditlimit_Usage_V.partner_code
-- 2023-02-03T16:28:57.099Z
UPDATE AD_Field SET AD_Name_ID=574089, Description='Maintain Business Partners', Help='The Business Partner window allows you do define any party with whom you transact.  This includes customers, vendors and employees.  Prior to entering or importing products, you must define your vendors.  Prior to generating Orders you must define your customers.  This window holds all information about your business partner and the values entered will be used to generate all document transactions', Name='Business Partner',Updated=TO_TIMESTAMP('2023-02-03 17:28:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=712131
;

-- 2023-02-03T16:28:57.100Z
UPDATE AD_Field_Trl trl SET Description='Maintain Business Partners',Help='The Business Partner window allows you do define any party with whom you transact.  This includes customers, vendors and employees.  Prior to entering or importing products, you must define your vendors.  Prior to generating Orders you must define your customers.  This window holds all information about your business partner and the values entered will be used to generate all document transactions',Name='Business Partner' WHERE AD_Field_ID=712131 AND AD_Language='en_US'
;

-- 2023-02-03T16:28:57.102Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(574089) 
;

-- 2023-02-03T16:28:57.105Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712131
;

-- 2023-02-03T16:28:57.106Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712131)
;

-- 2023-02-03T16:30:50.995Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582018,0,TO_TIMESTAMP('2023-02-03 17:30:50','YYYY-MM-DD HH24:MI:SS'),100,'Totql Credit Limit By  Department','D','Y','Credit Limit Department','Credit Limit Department',TO_TIMESTAMP('2023-02-03 17:30:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-03T16:30:50.997Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582018 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Field: Credit Limit Usage(541673,D) -> Business Partner Credit Limit Usage(546804,D) -> Credit Limit Department
-- Column: C_BPartner_Creditlimit_Usage_V.creditlimit_by_department
-- 2023-02-03T16:31:18.619Z
UPDATE AD_Field SET AD_Name_ID=582018, Description='Totql Credit Limit By  Department', Help=NULL, Name='Credit Limit Department',Updated=TO_TIMESTAMP('2023-02-03 17:31:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=712135
;

-- 2023-02-03T16:31:18.620Z
UPDATE AD_Field_Trl trl SET Description='Totql Credit Limit By  Department',Name='Credit Limit Department' WHERE AD_Field_ID=712135 AND AD_Language='en_US'
;

-- 2023-02-03T16:31:18.622Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582018) 
;

-- 2023-02-03T16:31:18.625Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712135
;

-- 2023-02-03T16:31:18.625Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712135)
;

-- 2023-02-03T16:33:07.195Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582019,0,TO_TIMESTAMP('2023-02-03 17:33:07','YYYY-MM-DD HH24:MI:SS'),100,'Credit limit section code (Credit Limit of this Section Partner)','D','Y','Credit Limit Section Code','Credit Limit Section Code',TO_TIMESTAMP('2023-02-03 17:33:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-03T16:33:07.196Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582019 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Field: Credit Limit Usage(541673,D) -> Business Partner Credit Limit Usage(546804,D) -> Credit Limit Section Code
-- Column: C_BPartner_Creditlimit_Usage_V.CreditLimit
-- 2023-02-03T16:33:15.167Z
UPDATE AD_Field SET AD_Name_ID=582019, Description='Credit limit section code (Credit Limit of this Section Partner)', Help=NULL, Name='Credit Limit Section Code',Updated=TO_TIMESTAMP('2023-02-03 17:33:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=712134
;

-- 2023-02-03T16:33:15.168Z
UPDATE AD_Field_Trl trl SET Description='Credit limit section code (Credit Limit of this Section Partner)',Help=NULL,Name='Credit Limit Section Code' WHERE AD_Field_ID=712134 AND AD_Language='en_US'
;

-- 2023-02-03T16:33:15.170Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582019) 
;

-- 2023-02-03T16:33:15.174Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712134
;

-- 2023-02-03T16:33:15.174Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712134)
;

-- 2023-02-03T16:35:06.637Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582020,0,TO_TIMESTAMP('2023-02-03 17:35:06','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Credit Limit Usage % (Order Based)','Credit Limit Usage % (Order Based)',TO_TIMESTAMP('2023-02-03 17:35:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-03T16:35:06.638Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582020 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Field: Credit Limit Usage(541673,D) -> Business Partner Credit Limit Usage(546804,D) -> Credit Limit Usage % (Order Based)
-- Column: C_BPartner_Creditlimit_Usage_V.cl_indicator_order
-- 2023-02-03T16:35:17.934Z
UPDATE AD_Field SET AD_Name_ID=582020, Description=NULL, Help=NULL, Name='Credit Limit Usage % (Order Based)',Updated=TO_TIMESTAMP('2023-02-03 17:35:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=712137
;

-- 2023-02-03T16:35:17.935Z
UPDATE AD_Field_Trl trl SET Name='Credit Limit Usage % (Order Based)' WHERE AD_Field_ID=712137 AND AD_Language='en_US'
;

-- 2023-02-03T16:35:17.937Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582020) 
;

-- 2023-02-03T16:35:17.940Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712137
;

-- 2023-02-03T16:35:17.940Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712137)
;

-- 2023-02-03T16:36:09.674Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582021,0,TO_TIMESTAMP('2023-02-03 17:36:09','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Credit Limit Usage % (Delivery Based)','Credit Limit Usage % (Delivery Based)',TO_TIMESTAMP('2023-02-03 17:36:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-03T16:36:09.675Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582021 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Field: Credit Limit Usage(541673,D) -> Business Partner Credit Limit Usage(546804,D) -> Credit Limit Usage % (Delivery Based)
-- Column: C_BPartner_Creditlimit_Usage_V.cl_indicator_delivery
-- 2023-02-03T16:36:19.599Z
UPDATE AD_Field SET AD_Name_ID=582021, Description=NULL, Help=NULL, Name='Credit Limit Usage % (Delivery Based)',Updated=TO_TIMESTAMP('2023-02-03 17:36:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=712138
;

-- 2023-02-03T16:36:19.600Z
UPDATE AD_Field_Trl trl SET Name='Credit Limit Usage % (Delivery Based)' WHERE AD_Field_ID=712138 AND AD_Language='en_US'
;

-- 2023-02-03T16:36:19.602Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582021) 
;

-- 2023-02-03T16:36:19.604Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712138
;

-- 2023-02-03T16:36:19.605Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712138)
;

-- Column: C_BPartner_Creditlimit_Usage_V.C_BPartner_ID
-- 2023-02-03T16:38:18.695Z
UPDATE AD_Column SET IsShowFilterInline='N',Updated=TO_TIMESTAMP('2023-02-03 17:38:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585844
;

-- Column: C_BPartner_Creditlimit_Usage_V.M_SectionCode_ID
-- 2023-02-03T16:38:34.368Z
UPDATE AD_Column SET IsShowFilterInline='N',Updated=TO_TIMESTAMP('2023-02-03 17:38:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585847
;

-- Column: C_BPartner_Creditlimit_Usage_V.M_Department_ID
-- 2023-02-03T16:38:42.730Z
UPDATE AD_Column SET IsShowFilterInline='N',Updated=TO_TIMESTAMP('2023-02-03 17:38:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585846
;

-- Tab: Business Partner_OLD(123,D) -> Business Partner Credit Limit Usage
-- Table: C_BPartner_Creditlimit_Usage_V
-- 2023-02-03T16:59:33.072Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,582009,0,546805,542293,123,'Y',TO_TIMESTAMP('2023-02-03 17:59:33','YYYY-MM-DD HH24:MI:SS'),100,'D','N','N','A','C_BPartner_Creditlimit_Usage_V','Y','N','Y','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N',0,'Business Partner Credit Limit Usage','N',290,0,TO_TIMESTAMP('2023-02-03 17:59:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-03T16:59:33.074Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Tab_ID=546805 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2023-02-03T16:59:33.075Z
/* DDL */  select update_tab_translation_from_ad_element(582009) 
;

-- 2023-02-03T16:59:33.080Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(546805)
;

-- Tab: Business Partner_OLD(123,D) -> Business Partner Credit Limit Usage
-- Table: C_BPartner_Creditlimit_Usage_V
-- 2023-02-03T16:59:52.310Z
UPDATE AD_Tab SET IsGridModeOnly='Y',Updated=TO_TIMESTAMP('2023-02-03 17:59:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=546805
;

-- Field: Business Partner_OLD(123,D) -> Business Partner Credit Limit Usage(546805,D) -> Business Partner Credit Limit Usage
-- Column: C_BPartner_Creditlimit_Usage_V.C_BPartner_Creditlimit_Usage_V_ID
-- 2023-02-03T17:00:21.099Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585843,712146,0,546805,TO_TIMESTAMP('2023-02-03 18:00:21','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Business Partner Credit Limit Usage',TO_TIMESTAMP('2023-02-03 18:00:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-03T17:00:21.100Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712146 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-03T17:00:21.102Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582009) 
;

-- 2023-02-03T17:00:21.106Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712146
;

-- 2023-02-03T17:00:21.107Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712146)
;

-- Field: Business Partner_OLD(123,D) -> Business Partner Credit Limit Usage(546805,D) -> Business Partner
-- Column: C_BPartner_Creditlimit_Usage_V.C_BPartner_ID
-- 2023-02-03T17:00:21.164Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585844,712147,0,546805,TO_TIMESTAMP('2023-02-03 18:00:21','YYYY-MM-DD HH24:MI:SS'),100,'',10,'D','','Y','Y','N','N','N','N','N','Business Partner',TO_TIMESTAMP('2023-02-03 18:00:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-03T17:00:21.165Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712147 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-03T17:00:21.166Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(187) 
;

-- 2023-02-03T17:00:21.190Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712147
;

-- 2023-02-03T17:00:21.190Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712147)
;

-- Field: Business Partner_OLD(123,D) -> Business Partner Credit Limit Usage(546805,D) -> partner_code
-- Column: C_BPartner_Creditlimit_Usage_V.partner_code
-- 2023-02-03T17:00:21.244Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585845,712148,0,546805,TO_TIMESTAMP('2023-02-03 18:00:21','YYYY-MM-DD HH24:MI:SS'),100,40,'D','Y','Y','N','N','N','N','N','partner_code',TO_TIMESTAMP('2023-02-03 18:00:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-03T17:00:21.245Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712148 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-03T17:00:21.246Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582015) 
;

-- 2023-02-03T17:00:21.250Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712148
;

-- 2023-02-03T17:00:21.250Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712148)
;

-- Field: Business Partner_OLD(123,D) -> Business Partner Credit Limit Usage(546805,D) -> Department
-- Column: C_BPartner_Creditlimit_Usage_V.M_Department_ID
-- 2023-02-03T17:00:21.303Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585846,712149,0,546805,TO_TIMESTAMP('2023-02-03 18:00:21','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','Y','N','N','N','N','N','Department',TO_TIMESTAMP('2023-02-03 18:00:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-03T17:00:21.304Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712149 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-03T17:00:21.305Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581944) 
;

-- 2023-02-03T17:00:21.309Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712149
;

-- 2023-02-03T17:00:21.309Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712149)
;

-- Field: Business Partner_OLD(123,D) -> Business Partner Credit Limit Usage(546805,D) -> Section Code
-- Column: C_BPartner_Creditlimit_Usage_V.M_SectionCode_ID
-- 2023-02-03T17:00:21.358Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585847,712150,0,546805,TO_TIMESTAMP('2023-02-03 18:00:21','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','Y','N','N','N','N','N','Section Code',TO_TIMESTAMP('2023-02-03 18:00:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-03T17:00:21.359Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712150 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-03T17:00:21.360Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581238) 
;

-- 2023-02-03T17:00:21.370Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712150
;

-- 2023-02-03T17:00:21.370Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712150)
;

-- Field: Business Partner_OLD(123,D) -> Business Partner Credit Limit Usage(546805,D) -> Credit limit
-- Column: C_BPartner_Creditlimit_Usage_V.CreditLimit
-- 2023-02-03T17:00:21.429Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585848,712151,0,546805,TO_TIMESTAMP('2023-02-03 18:00:21','YYYY-MM-DD HH24:MI:SS'),100,'Amount of Credit allowed',14,'D','The Credit Limit field indicates the credit limit for this account.','Y','Y','N','N','N','N','N','Credit limit',TO_TIMESTAMP('2023-02-03 18:00:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-03T17:00:21.430Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712151 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-03T17:00:21.431Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(855) 
;

-- 2023-02-03T17:00:21.439Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712151
;

-- 2023-02-03T17:00:21.440Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712151)
;

-- Field: Business Partner_OLD(123,D) -> Business Partner Credit Limit Usage(546805,D) -> creditlimit_by_department
-- Column: C_BPartner_Creditlimit_Usage_V.creditlimit_by_department
-- 2023-02-03T17:00:21.488Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585849,712152,0,546805,TO_TIMESTAMP('2023-02-03 18:00:21','YYYY-MM-DD HH24:MI:SS'),100,14,'D','Y','Y','N','N','N','N','N','creditlimit_by_department',TO_TIMESTAMP('2023-02-03 18:00:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-03T17:00:21.489Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712152 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-03T17:00:21.490Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582004) 
;

-- 2023-02-03T17:00:21.493Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712152
;

-- 2023-02-03T17:00:21.493Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712152)
;

-- Field: Business Partner_OLD(123,D) -> Business Partner Credit Limit Usage(546805,D) -> creditlimit_by_sectioncode
-- Column: C_BPartner_Creditlimit_Usage_V.creditlimit_by_sectioncode
-- 2023-02-03T17:00:21.553Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585850,712153,0,546805,TO_TIMESTAMP('2023-02-03 18:00:21','YYYY-MM-DD HH24:MI:SS'),100,14,'D','Y','Y','N','N','N','N','N','creditlimit_by_sectioncode',TO_TIMESTAMP('2023-02-03 18:00:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-03T17:00:21.553Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712153 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-03T17:00:21.554Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582005) 
;

-- 2023-02-03T17:00:21.559Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712153
;

-- 2023-02-03T17:00:21.559Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712153)
;

-- Field: Business Partner_OLD(123,D) -> Business Partner Credit Limit Usage(546805,D) -> cl_indicator_order
-- Column: C_BPartner_Creditlimit_Usage_V.cl_indicator_order
-- 2023-02-03T17:00:21.620Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585851,712154,0,546805,TO_TIMESTAMP('2023-02-03 18:00:21','YYYY-MM-DD HH24:MI:SS'),100,2147483647,'D','Y','Y','N','N','N','N','N','cl_indicator_order',TO_TIMESTAMP('2023-02-03 18:00:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-03T17:00:21.621Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712154 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-03T17:00:21.622Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582006) 
;

-- 2023-02-03T17:00:21.625Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712154
;

-- 2023-02-03T17:00:21.625Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712154)
;

-- Field: Business Partner_OLD(123,D) -> Business Partner Credit Limit Usage(546805,D) -> cl_indicator_delivery
-- Column: C_BPartner_Creditlimit_Usage_V.cl_indicator_delivery
-- 2023-02-03T17:00:21.677Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585852,712155,0,546805,TO_TIMESTAMP('2023-02-03 18:00:21','YYYY-MM-DD HH24:MI:SS'),100,2147483647,'D','Y','Y','N','N','N','N','N','cl_indicator_delivery',TO_TIMESTAMP('2023-02-03 18:00:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-03T17:00:21.678Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712155 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-03T17:00:21.679Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582007) 
;

-- 2023-02-03T17:00:21.682Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712155
;

-- 2023-02-03T17:00:21.682Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712155)
;

-- Field: Business Partner_OLD(123,D) -> Business Partner Credit Limit Usage(546805,D) -> Created
-- Column: C_BPartner_Creditlimit_Usage_V.Created
-- 2023-02-03T17:00:21.730Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585853,712156,0,546805,TO_TIMESTAMP('2023-02-03 18:00:21','YYYY-MM-DD HH24:MI:SS'),100,'Date this record was created',35,'D','The Created field indicates the date that this record was created.','Y','Y','N','N','N','N','N','Created',TO_TIMESTAMP('2023-02-03 18:00:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-03T17:00:21.730Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712156 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-03T17:00:21.731Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(245) 
;

-- 2023-02-03T17:00:21.904Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712156
;

-- 2023-02-03T17:00:21.904Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712156)
;

-- Field: Business Partner_OLD(123,D) -> Business Partner Credit Limit Usage(546805,D) -> Updated
-- Column: C_BPartner_Creditlimit_Usage_V.Updated
-- 2023-02-03T17:00:21.964Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585854,712157,0,546805,TO_TIMESTAMP('2023-02-03 18:00:21','YYYY-MM-DD HH24:MI:SS'),100,'Date this record was updated',35,'D','The Updated field indicates the date that this record was updated.','Y','Y','N','N','N','N','N','Updated',TO_TIMESTAMP('2023-02-03 18:00:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-03T17:00:21.965Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712157 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-03T17:00:21.965Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(607) 
;

-- 2023-02-03T17:00:22.033Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712157
;

-- 2023-02-03T17:00:22.033Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712157)
;

-- Field: Business Partner_OLD(123,D) -> Business Partner Credit Limit Usage(546805,D) -> Created By
-- Column: C_BPartner_Creditlimit_Usage_V.CreatedBy
-- 2023-02-03T17:00:22.095Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585855,712158,0,546805,TO_TIMESTAMP('2023-02-03 18:00:22','YYYY-MM-DD HH24:MI:SS'),100,'User who created this records',10,'D','The Created By field indicates the user who created this record.','Y','Y','N','N','N','N','N','Created By',TO_TIMESTAMP('2023-02-03 18:00:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-03T17:00:22.096Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712158 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-03T17:00:22.097Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(246) 
;

-- 2023-02-03T17:00:22.150Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712158
;

-- 2023-02-03T17:00:22.150Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712158)
;

-- Field: Business Partner_OLD(123,D) -> Business Partner Credit Limit Usage(546805,D) -> Updated By
-- Column: C_BPartner_Creditlimit_Usage_V.UpdatedBy
-- 2023-02-03T17:00:22.205Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585856,712159,0,546805,TO_TIMESTAMP('2023-02-03 18:00:22','YYYY-MM-DD HH24:MI:SS'),100,'User who updated this records',10,'D','The Updated By field indicates the user who updated this record.','Y','Y','N','N','N','N','N','Updated By',TO_TIMESTAMP('2023-02-03 18:00:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-03T17:00:22.206Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712159 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-03T17:00:22.207Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(608) 
;

-- 2023-02-03T17:00:22.271Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712159
;

-- 2023-02-03T17:00:22.271Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712159)
;

-- Field: Business Partner_OLD(123,D) -> Business Partner Credit Limit Usage(546805,D) -> Active
-- Column: C_BPartner_Creditlimit_Usage_V.IsActive
-- 2023-02-03T17:00:22.328Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585857,712160,0,546805,TO_TIMESTAMP('2023-02-03 18:00:22','YYYY-MM-DD HH24:MI:SS'),100,'The record is active in the system',2147483647,'D','There are two methods of making records unavailable in the system: One is to delete the record, the other is to de-activate the record. A de-activated record is not available for selection, but available for reports.
There are two reasons for de-activating and not deleting records:
(1) The system requires the record for audit purposes.
(2) The record is referenced by other records. E.g., you cannot delete a Business Partner, if there are invoices for this partner record existing. You de-activate the Business Partner and prevent that this record is used for future entries.','Y','Y','N','N','N','N','N','Active',TO_TIMESTAMP('2023-02-03 18:00:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-03T17:00:22.329Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712160 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-03T17:00:22.331Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2023-02-03T17:00:22.604Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712160
;

-- 2023-02-03T17:00:22.604Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712160)
;

-- Field: Business Partner_OLD(123,D) -> Business Partner Credit Limit Usage(546805,D) -> Client
-- Column: C_BPartner_Creditlimit_Usage_V.AD_Client_ID
-- 2023-02-03T17:00:22.688Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585858,712161,0,546805,TO_TIMESTAMP('2023-02-03 18:00:22','YYYY-MM-DD HH24:MI:SS'),100,'Client/Tenant for this installation.',10,'D','A Client is a company or a legal entity. You cannot share data between Clients. Tenant is a synonym for Client.','Y','Y','N','N','N','Y','N','Client',TO_TIMESTAMP('2023-02-03 18:00:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-03T17:00:22.689Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712161 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-03T17:00:22.690Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2023-02-03T17:00:22.864Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712161
;

-- 2023-02-03T17:00:22.864Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712161)
;

-- Field: Business Partner_OLD(123,D) -> Business Partner Credit Limit Usage(546805,D) -> Organisation
-- Column: C_BPartner_Creditlimit_Usage_V.AD_Org_ID
-- 2023-02-03T17:00:22.926Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585859,712162,0,546805,TO_TIMESTAMP('2023-02-03 18:00:22','YYYY-MM-DD HH24:MI:SS'),100,'Organisational entity within client',10,'D','An organisation is a unit of your client or legal entity - examples are store, department. You can share data between organisations.','Y','Y','N','N','N','N','N','Organisation',TO_TIMESTAMP('2023-02-03 18:00:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-03T17:00:22.927Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712162 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-03T17:00:22.928Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2023-02-03T17:00:23.137Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712162
;

-- 2023-02-03T17:00:23.138Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712162)
;

-- Field: Business Partner_OLD(123,D) -> Business Partner Credit Limit Usage(546805,D) -> Credit Limit Section Code
-- Column: C_BPartner_Creditlimit_Usage_V.CreditLimit
-- 2023-02-03T17:02:14.260Z
UPDATE AD_Field SET AD_Name_ID=582019, Description='Credit limit section code (Credit Limit of this Section Partner)', Help=NULL, Name='Credit Limit Section Code',Updated=TO_TIMESTAMP('2023-02-03 18:02:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=712151
;

-- 2023-02-03T17:02:14.261Z
UPDATE AD_Field_Trl trl SET Description='Credit limit section code (Credit Limit of this Section Partner)',Help=NULL,Name='Credit Limit Section Code' WHERE AD_Field_ID=712151 AND AD_Language='en_US'
;

-- 2023-02-03T17:02:14.263Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582019) 
;

-- 2023-02-03T17:02:14.266Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712151
;

-- 2023-02-03T17:02:14.266Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712151)
;

-- Field: Business Partner_OLD(123,D) -> Business Partner Credit Limit Usage(546805,D) -> Credit Limit Usage % (Order Based)
-- Column: C_BPartner_Creditlimit_Usage_V.cl_indicator_order
-- 2023-02-03T17:02:55.795Z
UPDATE AD_Field SET AD_Name_ID=582020, Description=NULL, Help=NULL, Name='Credit Limit Usage % (Order Based)',Updated=TO_TIMESTAMP('2023-02-03 18:02:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=712154
;

-- 2023-02-03T17:02:55.796Z
UPDATE AD_Field_Trl trl SET Name='Credit Limit Usage % (Order Based)' WHERE AD_Field_ID=712154 AND AD_Language='en_US'
;

-- 2023-02-03T17:02:55.798Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582020) 
;

-- 2023-02-03T17:02:55.803Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712154
;

-- 2023-02-03T17:02:55.803Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712154)
;

-- Field: Business Partner_OLD(123,D) -> Business Partner Credit Limit Usage(546805,D) -> Credit Limit Usage % (Delivery Based)
-- Column: C_BPartner_Creditlimit_Usage_V.cl_indicator_delivery
-- 2023-02-03T17:03:18.274Z
UPDATE AD_Field SET AD_Name_ID=582021, Description=NULL, Help=NULL, Name='Credit Limit Usage % (Delivery Based)',Updated=TO_TIMESTAMP('2023-02-03 18:03:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=712155
;

-- 2023-02-03T17:03:18.275Z
UPDATE AD_Field_Trl trl SET Name='Credit Limit Usage % (Delivery Based)' WHERE AD_Field_ID=712155 AND AD_Language='en_US'
;

-- 2023-02-03T17:03:18.276Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582021) 
;

-- 2023-02-03T17:03:18.283Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712155
;

-- 2023-02-03T17:03:18.283Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712155)
;

-- Field: Business Partner_OLD(123,D) -> Business Partner Credit Limit Usage(546805,D) -> Credit Limit Department
-- Column: C_BPartner_Creditlimit_Usage_V.creditlimit_by_department
-- 2023-02-03T17:04:16.532Z
UPDATE AD_Field SET AD_Name_ID=582018, Description='Totql Credit Limit By  Department', Help=NULL, Name='Credit Limit Department',Updated=TO_TIMESTAMP('2023-02-03 18:04:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=712152
;

-- 2023-02-03T17:04:16.532Z
UPDATE AD_Field_Trl trl SET Description='Totql Credit Limit By  Department',Name='Credit Limit Department' WHERE AD_Field_ID=712152 AND AD_Language='en_US'
;

-- 2023-02-03T17:04:16.534Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582018) 
;

-- 2023-02-03T17:04:16.539Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712152
;

-- 2023-02-03T17:04:16.539Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712152)
;

-- Field: Business Partner_OLD(123,D) -> Business Partner Credit Limit Usage(546805,D) -> Business Partner
-- Column: C_BPartner_Creditlimit_Usage_V.partner_code
-- 2023-02-03T17:05:43.885Z
UPDATE AD_Field SET AD_Name_ID=572850, Description='Business Partner specific Information of a Product', Help='Note that some information is for reference only!  The ', Name='Business Partner',Updated=TO_TIMESTAMP('2023-02-03 18:05:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=712148
;

-- 2023-02-03T17:05:43.886Z
UPDATE AD_Field_Trl trl SET Description='Business Partner specific Information of a Product',Help='Note that some information is for reference only!  The ',Name='Business Partner' WHERE AD_Field_ID=712148 AND AD_Language='en_US'
;

-- 2023-02-03T17:05:43.887Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(572850) 
;

-- 2023-02-03T17:05:43.890Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712148
;

-- 2023-02-03T17:05:43.891Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712148)
;

-- Tab: Business Partner_OLD(123,D) -> Business Partner Credit Limit Usage
-- Table: C_BPartner_Creditlimit_Usage_V
-- 2023-02-03T17:06:50.513Z
UPDATE AD_Tab SET TabLevel=1,Updated=TO_TIMESTAMP('2023-02-03 18:06:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=546805
;

-- Tab: Business Partner_OLD(123,D) -> Business Partner Credit Limit Usage
-- Table: C_BPartner_Creditlimit_Usage_V
-- 2023-02-03T17:07:06.502Z
UPDATE AD_Tab SET IsInsertRecord='N',Updated=TO_TIMESTAMP('2023-02-03 18:07:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=546805
;

-- Tab: Business Partner_OLD(123,D) -> Business Partner Credit Limit Usage
-- Table: C_BPartner_Creditlimit_Usage_V
-- 2023-02-03T17:07:19.237Z
UPDATE AD_Tab SET Parent_Column_ID=563682,Updated=TO_TIMESTAMP('2023-02-03 18:07:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=546805
;

-- Tab: Business Partner_OLD(123,D) -> Business Partner Credit Limit Usage
-- Table: C_BPartner_Creditlimit_Usage_V
-- 2023-02-03T17:09:48.513Z
UPDATE AD_Tab SET Parent_Column_ID=NULL, SeqNo=85,Updated=TO_TIMESTAMP('2023-02-03 18:09:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=546805
;

-- Column: C_BPartner_Creditlimit_Usage_V.C_BPartner_ID
-- 2023-02-03T17:15:55.531Z
UPDATE AD_Column SET IsParent='Y', IsUpdateable='N',Updated=TO_TIMESTAMP('2023-02-03 18:15:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585844
;

-- Field: Business Partner_OLD(123,D) -> Business Partner Credit Limit Usage(546805,D) -> Client
-- Column: C_BPartner_Creditlimit_Usage_V.AD_Client_ID
-- 2023-02-03T17:17:11.018Z
UPDATE AD_Field SET IsDisplayed='N', IsDisplayedGrid='N',Updated=TO_TIMESTAMP('2023-02-03 18:17:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=712161
;

-- Field: Business Partner_OLD(123,D) -> Business Partner Credit Limit Usage(546805,D) -> Active
-- Column: C_BPartner_Creditlimit_Usage_V.IsActive
-- 2023-02-03T17:17:16.103Z
UPDATE AD_Field SET IsDisplayed='N', IsDisplayedGrid='N',Updated=TO_TIMESTAMP('2023-02-03 18:17:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=712160
;

-- Field: Business Partner_OLD(123,D) -> Business Partner Credit Limit Usage(546805,D) -> Updated By
-- Column: C_BPartner_Creditlimit_Usage_V.UpdatedBy
-- 2023-02-03T17:17:20.613Z
UPDATE AD_Field SET IsDisplayed='N',Updated=TO_TIMESTAMP('2023-02-03 18:17:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=712159
;

-- Field: Business Partner_OLD(123,D) -> Business Partner Credit Limit Usage(546805,D) -> Created By
-- Column: C_BPartner_Creditlimit_Usage_V.CreatedBy
-- 2023-02-03T17:17:25.091Z
UPDATE AD_Field SET IsDisplayed='N',Updated=TO_TIMESTAMP('2023-02-03 18:17:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=712158
;

-- Field: Business Partner_OLD(123,D) -> Business Partner Credit Limit Usage(546805,D) -> Updated
-- Column: C_BPartner_Creditlimit_Usage_V.Updated
-- 2023-02-03T17:17:28.980Z
UPDATE AD_Field SET IsDisplayed='N', IsDisplayedGrid='N',Updated=TO_TIMESTAMP('2023-02-03 18:17:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=712157
;

-- Field: Business Partner_OLD(123,D) -> Business Partner Credit Limit Usage(546805,D) -> Created
-- Column: C_BPartner_Creditlimit_Usage_V.Created
-- 2023-02-03T17:17:33.104Z
UPDATE AD_Field SET IsDisplayed='N', IsDisplayedGrid='N',Updated=TO_TIMESTAMP('2023-02-03 18:17:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=712156
;

-- Tab: Business Partner_OLD(123,D) -> Business Partner Credit Limit Usage(546805,D)
-- UI Section: grid
-- 2023-02-03T17:19:44.422Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,546805,545432,TO_TIMESTAMP('2023-02-03 18:19:44','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2023-02-03 18:19:44','YYYY-MM-DD HH24:MI:SS'),100,'grid')
;

-- 2023-02-03T17:19:44.422Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_UI_Section_ID=545432 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- UI Section: Business Partner_OLD(123,D) -> Business Partner Credit Limit Usage(546805,D) -> grid
-- UI Column: 10
-- 2023-02-03T17:19:52.637Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,546627,545432,TO_TIMESTAMP('2023-02-03 18:19:52','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2023-02-03 18:19:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: Business Partner_OLD(123,D) -> Business Partner Credit Limit Usage(546805,D) -> grid -> 10
-- UI Element Group: grid
-- 2023-02-03T17:20:00.538Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546627,550339,TO_TIMESTAMP('2023-02-03 18:20:00','YYYY-MM-DD HH24:MI:SS'),100,'Y','grid',10,TO_TIMESTAMP('2023-02-03 18:20:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Business Partner_OLD(123,D) -> Business Partner Credit Limit Usage(546805,D) -> grid -> 10 -> grid.Business Partner
-- Column: C_BPartner_Creditlimit_Usage_V.partner_code
-- 2023-02-03T17:20:29.505Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712148,0,546805,550339,615569,'F',TO_TIMESTAMP('2023-02-03 18:20:29','YYYY-MM-DD HH24:MI:SS'),100,'Business Partner specific Information of a Product','Note that some information is for reference only!  The ','Y','N','N','Y','N','N','N',0,'Business Partner',10,0,0,TO_TIMESTAMP('2023-02-03 18:20:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Business Partner_OLD(123,D) -> Business Partner Credit Limit Usage(546805,D) -> grid -> 10 -> grid.Department
-- Column: C_BPartner_Creditlimit_Usage_V.M_Department_ID
-- 2023-02-03T17:20:36.752Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712149,0,546805,550339,615570,'F',TO_TIMESTAMP('2023-02-03 18:20:36','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Department',20,0,0,TO_TIMESTAMP('2023-02-03 18:20:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Business Partner_OLD(123,D) -> Business Partner Credit Limit Usage(546805,D) -> grid -> 10 -> grid.Section Code
-- Column: C_BPartner_Creditlimit_Usage_V.M_SectionCode_ID
-- 2023-02-03T17:20:43.530Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712150,0,546805,550339,615571,'F',TO_TIMESTAMP('2023-02-03 18:20:43','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Section Code',30,0,0,TO_TIMESTAMP('2023-02-03 18:20:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Business Partner_OLD(123,D) -> Business Partner Credit Limit Usage(546805,D) -> grid -> 10 -> grid.Credit Limit Department
-- Column: C_BPartner_Creditlimit_Usage_V.creditlimit_by_department
-- 2023-02-03T17:21:06.016Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712152,0,546805,550339,615572,'F',TO_TIMESTAMP('2023-02-03 18:21:05','YYYY-MM-DD HH24:MI:SS'),100,'Totql Credit Limit By  Department','Y','N','N','Y','N','N','N',0,'Credit Limit Department',40,0,0,TO_TIMESTAMP('2023-02-03 18:21:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Business Partner_OLD(123,D) -> Business Partner Credit Limit Usage(546805,D) -> grid -> 10 -> grid.Credit Limit Section Code
-- Column: C_BPartner_Creditlimit_Usage_V.CreditLimit
-- 2023-02-03T17:21:22.012Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712151,0,546805,550339,615573,'F',TO_TIMESTAMP('2023-02-03 18:21:21','YYYY-MM-DD HH24:MI:SS'),100,'Credit limit section code (Credit Limit of this Section Partner)','Y','N','N','Y','N','N','N',0,'Credit Limit Section Code',50,0,0,TO_TIMESTAMP('2023-02-03 18:21:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Business Partner_OLD(123,D) -> Business Partner Credit Limit Usage(546805,D) -> grid -> 10 -> grid.Credit Limit Usage % (Order Based)
-- Column: C_BPartner_Creditlimit_Usage_V.cl_indicator_order
-- 2023-02-03T17:22:25.037Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712154,0,546805,550339,615574,'F',TO_TIMESTAMP('2023-02-03 18:22:24','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Credit Limit Usage % (Order Based)',60,0,0,TO_TIMESTAMP('2023-02-03 18:22:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Business Partner_OLD(123,D) -> Business Partner Credit Limit Usage(546805,D) -> grid -> 10 -> grid.Credit Limit Usage % (Delivery Based)
-- Column: C_BPartner_Creditlimit_Usage_V.cl_indicator_delivery
-- 2023-02-03T17:22:31.493Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712155,0,546805,550339,615575,'F',TO_TIMESTAMP('2023-02-03 18:22:31','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Credit Limit Usage % (Delivery Based)',70,0,0,TO_TIMESTAMP('2023-02-03 18:22:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Business Partner_OLD(123,D) -> Business Partner Credit Limit Usage(546805,D) -> grid -> 10 -> grid.Business Partner
-- Column: C_BPartner_Creditlimit_Usage_V.partner_code
-- 2023-02-03T17:23:01.507Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2023-02-03 18:23:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615569
;

-- UI Element: Business Partner_OLD(123,D) -> Business Partner Credit Limit Usage(546805,D) -> grid -> 10 -> grid.Department
-- Column: C_BPartner_Creditlimit_Usage_V.M_Department_ID
-- 2023-02-03T17:23:01.511Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2023-02-03 18:23:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615570
;

-- UI Element: Business Partner_OLD(123,D) -> Business Partner Credit Limit Usage(546805,D) -> grid -> 10 -> grid.Section Code
-- Column: C_BPartner_Creditlimit_Usage_V.M_SectionCode_ID
-- 2023-02-03T17:23:01.514Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2023-02-03 18:23:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615571
;

-- UI Element: Business Partner_OLD(123,D) -> Business Partner Credit Limit Usage(546805,D) -> grid -> 10 -> grid.Credit Limit Department
-- Column: C_BPartner_Creditlimit_Usage_V.creditlimit_by_department
-- 2023-02-03T17:23:01.517Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2023-02-03 18:23:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615572
;

-- UI Element: Business Partner_OLD(123,D) -> Business Partner Credit Limit Usage(546805,D) -> grid -> 10 -> grid.Credit Limit Section Code
-- Column: C_BPartner_Creditlimit_Usage_V.CreditLimit
-- 2023-02-03T17:23:01.519Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2023-02-03 18:23:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615573
;

-- UI Element: Business Partner_OLD(123,D) -> Business Partner Credit Limit Usage(546805,D) -> grid -> 10 -> grid.Credit Limit Usage % (Order Based)
-- Column: C_BPartner_Creditlimit_Usage_V.cl_indicator_order
-- 2023-02-03T17:23:01.521Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2023-02-03 18:23:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615574
;

-- UI Element: Business Partner_OLD(123,D) -> Business Partner Credit Limit Usage(546805,D) -> grid -> 10 -> grid.Credit Limit Usage % (Delivery Based)
-- Column: C_BPartner_Creditlimit_Usage_V.cl_indicator_delivery
-- 2023-02-03T17:23:01.524Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2023-02-03 18:23:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615575
;

