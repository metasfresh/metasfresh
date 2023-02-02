-- Table: C_BPartner_CreditLimit_Departments_V
-- 2023-02-01T19:35:23.187Z
INSERT INTO AD_Table (AccessLevel,ACTriggerLength,AD_Client_ID,AD_Org_ID,AD_Table_ID,CopyColumnsFromTable,Created,CreatedBy,EntityType,ImportTable,IsActive,IsAutocomplete,IsChangeLog,IsDeleteable,IsDLM,IsEnableRemoteCacheInvalidation,IsHighVolume,IsSecurityEnabled,IsView,LoadSeq,Name,PersonalDataCategory,ReplicationType,TableName,TooltipType,Updated,UpdatedBy,WEBUI_View_PageLength) VALUES ('3',0,0,0,542288,'N',TO_TIMESTAMP('2023-02-01 21:35:22','YYYY-MM-DD HH24:MI:SS'),100,'D','N','Y','N','N','Y','N','N','N','N','N',0,'Credit Limit (Departments)','NP','L','C_BPartner_CreditLimit_Departments_V','DTI',TO_TIMESTAMP('2023-02-01 21:35:22','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-01T19:35:23.190Z
INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Table_ID=542288 AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- 2023-02-01T19:35:23.318Z
INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,Description,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,StartNewYear,StartNo,Updated,UpdatedBy) VALUES (0,0,556200,TO_TIMESTAMP('2023-02-01 21:35:23','YYYY-MM-DD HH24:MI:SS'),100,1000000,50000,'Table C_BPartner_CreditLimit_Departments_V',1,'Y','N','Y','Y','C_BPartner_CreditLimit_Departments_V','N',1000000,TO_TIMESTAMP('2023-02-01 21:35:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-01T19:35:23.330Z
CREATE SEQUENCE C_BPARTNER_CREDITLIMIT_DEPARTMENTS_V_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 START 1000000
;

-- Table: C_BPartner_CreditLimit_Departments_V
-- 2023-02-01T19:35:51.962Z
UPDATE AD_Table SET IsDeleteable='N', IsView='Y',Updated=TO_TIMESTAMP('2023-02-01 21:35:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=542288
;

-- Column: C_BPartner_CreditLimit_Departments_V.C_BPartner_ID
-- 2023-02-01T19:37:08.031Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,585678,187,0,30,542288,'C_BPartner_ID',TO_TIMESTAMP('2023-02-01 21:37:07','YYYY-MM-DD HH24:MI:SS'),100,'Bezeichnet einen Geschäftspartner','D',10,'Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.','Y','Y','N','N','N','N','N','N','N','N','N','Geschäftspartner',TO_TIMESTAMP('2023-02-01 21:37:07','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-01T19:37:08.034Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585678 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-01T19:37:08.040Z
/* DDL */  select update_Column_Translation_From_AD_Element(187) 
;

-- Column: C_BPartner_CreditLimit_Departments_V.M_SectionCode_ID
-- 2023-02-01T19:37:08.892Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,585679,581238,0,30,542288,'M_SectionCode_ID',TO_TIMESTAMP('2023-02-01 21:37:08','YYYY-MM-DD HH24:MI:SS'),100,'D',10,'Y','Y','N','N','N','N','N','N','N','N','N','Sektionskennung',TO_TIMESTAMP('2023-02-01 21:37:08','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-01T19:37:08.893Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585679 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-01T19:37:08.896Z
/* DDL */  select update_Column_Translation_From_AD_Element(581238) 
;

-- Column: C_BPartner_CreditLimit_Departments_V.M_Department_ID
-- 2023-02-01T19:37:09.628Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,585680,581944,0,30,542288,'M_Department_ID',TO_TIMESTAMP('2023-02-01 21:37:09','YYYY-MM-DD HH24:MI:SS'),100,'D',10,'Y','Y','N','N','N','N','N','N','N','N','N','Department',TO_TIMESTAMP('2023-02-01 21:37:09','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-01T19:37:09.630Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585680 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-01T19:37:09.634Z
/* DDL */  select update_Column_Translation_From_AD_Element(581944) 
;

-- Column: C_BPartner_CreditLimit_Departments_V.C_Currency_ID
-- 2023-02-01T19:37:10.380Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,585681,193,0,30,542288,'C_Currency_ID',TO_TIMESTAMP('2023-02-01 21:37:10','YYYY-MM-DD HH24:MI:SS'),100,'Die Währung für diesen Eintrag','D',10,'Bezeichnet die auf Dokumenten oder Berichten verwendete Währung','Y','Y','N','N','N','N','N','N','N','N','N','Währung',TO_TIMESTAMP('2023-02-01 21:37:10','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-01T19:37:10.382Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585681 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-01T19:37:10.385Z
/* DDL */  select update_Column_Translation_From_AD_Element(193) 
;

-- Column: C_BPartner_CreditLimit_Departments_V.SO_CreditUsed
-- 2023-02-01T19:37:11.142Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,585682,554,0,22,542288,'SO_CreditUsed',TO_TIMESTAMP('2023-02-01 21:37:11','YYYY-MM-DD HH24:MI:SS'),100,'Gegenwärtiger Aussenstand','D',14,'The Credit Used indicates the total amount of open or unpaid invoices in primary accounting currency for the Business Partner. Credit Management is based on the Total Open Amount, which includes Vendor activities.','Y','Y','N','N','N','N','N','N','N','N','N','Kredit gewährt',TO_TIMESTAMP('2023-02-01 21:37:11','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-01T19:37:11.143Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585682 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-01T19:37:11.316Z
/* DDL */  select update_Column_Translation_From_AD_Element(554) 
;

-- 2023-02-01T19:37:11.888Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581982,0,'m_department_order_openamt',TO_TIMESTAMP('2023-02-01 21:37:11','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','m_department_order_openamt','m_department_order_openamt',TO_TIMESTAMP('2023-02-01 21:37:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-01T19:37:11.889Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581982 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: C_BPartner_CreditLimit_Departments_V.m_department_order_openamt
-- 2023-02-01T19:37:12.386Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,585683,581982,0,12,542288,'m_department_order_openamt',TO_TIMESTAMP('2023-02-01 21:37:11','YYYY-MM-DD HH24:MI:SS'),100,'D',14,'Y','Y','N','N','N','N','N','N','N','N','N','m_department_order_openamt',TO_TIMESTAMP('2023-02-01 21:37:11','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-01T19:37:12.387Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585683 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-01T19:37:12.550Z
/* DDL */  select update_Column_Translation_From_AD_Element(581982) 
;

-- 2023-02-01T19:37:13.131Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581983,0,'delivery_crediytused',TO_TIMESTAMP('2023-02-01 21:37:13','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','delivery_crediytused','delivery_crediytused',TO_TIMESTAMP('2023-02-01 21:37:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-01T19:37:13.132Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581983 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: C_BPartner_CreditLimit_Departments_V.delivery_crediytused
-- 2023-02-01T19:37:13.595Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,585684,581983,0,22,542288,'delivery_crediytused',TO_TIMESTAMP('2023-02-01 21:37:13','YYYY-MM-DD HH24:MI:SS'),100,'D',14,'Y','Y','N','N','N','N','N','N','N','N','N','delivery_crediytused',TO_TIMESTAMP('2023-02-01 21:37:13','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-01T19:37:13.597Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585684 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-01T19:37:13.814Z
/* DDL */  select update_Column_Translation_From_AD_Element(581983) 
;

-- 2023-02-01T19:37:14.320Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581984,0,'totalordervalue',TO_TIMESTAMP('2023-02-01 21:37:14','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','totalordervalue','totalordervalue',TO_TIMESTAMP('2023-02-01 21:37:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-01T19:37:14.321Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581984 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: C_BPartner_CreditLimit_Departments_V.totalordervalue
-- 2023-02-01T19:37:14.888Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,585685,581984,0,22,542288,'totalordervalue',TO_TIMESTAMP('2023-02-01 21:37:14','YYYY-MM-DD HH24:MI:SS'),100,'D',14,'Y','Y','N','N','N','N','N','N','N','N','N','totalordervalue',TO_TIMESTAMP('2023-02-01 21:37:14','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-01T19:37:14.889Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585685 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-01T19:37:15.088Z
/* DDL */  select update_Column_Translation_From_AD_Element(581984) 
;

-- 2023-02-01T19:37:15.599Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581985,0,'openorderamt',TO_TIMESTAMP('2023-02-01 21:37:15','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','openorderamt','openorderamt',TO_TIMESTAMP('2023-02-01 21:37:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-01T19:37:15.601Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581985 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: C_BPartner_CreditLimit_Departments_V.openorderamt
-- 2023-02-01T19:37:16.174Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,585686,581985,0,12,542288,'openorderamt',TO_TIMESTAMP('2023-02-01 21:37:15','YYYY-MM-DD HH24:MI:SS'),100,'D',14,'Y','Y','N','N','N','N','N','N','N','N','N','openorderamt',TO_TIMESTAMP('2023-02-01 21:37:15','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-01T19:37:16.175Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585686 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-01T19:37:16.375Z
/* DDL */  select update_Column_Translation_From_AD_Element(581985) 
;

-- Column: C_BPartner_CreditLimit_Departments_V.CreditLimit
-- 2023-02-01T19:37:16.964Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,585687,855,0,22,542288,'CreditLimit',TO_TIMESTAMP('2023-02-01 21:37:16','YYYY-MM-DD HH24:MI:SS'),100,'Amount of Credit allowed','D',14,'The Credit Limit field indicates the credit limit for this account.','Y','Y','N','N','N','N','N','N','N','N','N','Credit limit',TO_TIMESTAMP('2023-02-01 21:37:16','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-01T19:37:16.965Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585687 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-01T19:37:17.166Z
/* DDL */  select update_Column_Translation_From_AD_Element(855) 
;

-- 2023-02-01T19:41:20.160Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581986,0,'c_bpartner_creditlimit_departments_v',TO_TIMESTAMP('2023-02-01 21:41:20','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','c_bpartner_creditlimit_departments_v','c_bpartner_creditlimit_departments_v',TO_TIMESTAMP('2023-02-01 21:41:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-01T19:41:20.162Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581986 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: C_BPartner_CreditLimit_Departments_V.c_bpartner_creditlimit_departments_v
-- 2023-02-01T19:41:20.692Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,585688,581986,0,11,542288,'c_bpartner_creditlimit_departments_v',TO_TIMESTAMP('2023-02-01 21:41:20','YYYY-MM-DD HH24:MI:SS'),100,'D',10,'Y','Y','N','N','N','N','N','N','N','N','N','c_bpartner_creditlimit_departments_v',TO_TIMESTAMP('2023-02-01 21:41:20','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-01T19:41:20.694Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585688 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-01T19:41:20.968Z
/* DDL */  select update_Column_Translation_From_AD_Element(581986) 
;

-- Column: C_BPartner_CreditLimit_Departments_V.Created
-- 2023-02-01T19:41:21.513Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,585689,245,0,16,542288,'Created',TO_TIMESTAMP('2023-02-01 21:41:21','YYYY-MM-DD HH24:MI:SS'),100,'Datum, an dem dieser Eintrag erstellt wurde','D',35,'Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','N','N','N','N','N','N','N','N','N','N','Erstellt',TO_TIMESTAMP('2023-02-01 21:41:21','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-01T19:41:21.514Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585689 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-01T19:41:21.708Z
/* DDL */  select update_Column_Translation_From_AD_Element(245) 
;

-- Column: C_BPartner_CreditLimit_Departments_V.Updated
-- 2023-02-01T19:41:22.521Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,585690,607,0,16,542288,'Updated',TO_TIMESTAMP('2023-02-01 21:41:22','YYYY-MM-DD HH24:MI:SS'),100,'Datum, an dem dieser Eintrag aktualisiert wurde','D',35,'Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Y','N','N','N','N','N','N','N','N','N','N','Aktualisiert',TO_TIMESTAMP('2023-02-01 21:41:22','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-01T19:41:22.529Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585690 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-01T19:41:23.556Z
/* DDL */  select update_Column_Translation_From_AD_Element(607) 
;

-- Column: C_BPartner_CreditLimit_Departments_V.CreatedBy
-- 2023-02-01T19:41:27.982Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,585691,246,0,18,110,542288,'CreatedBy',TO_TIMESTAMP('2023-02-01 21:41:27','YYYY-MM-DD HH24:MI:SS'),100,'Nutzer, der diesen Eintrag erstellt hat','D',10,'Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','N','N','N','N','N','N','N','N','N','N','Erstellt durch',TO_TIMESTAMP('2023-02-01 21:41:27','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-01T19:41:27.984Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585691 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-01T19:41:27.987Z
/* DDL */  select update_Column_Translation_From_AD_Element(246) 
;

-- Column: C_BPartner_CreditLimit_Departments_V.UpdatedBy
-- 2023-02-01T19:41:28.625Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,585692,608,0,18,110,542288,'UpdatedBy',TO_TIMESTAMP('2023-02-01 21:41:28','YYYY-MM-DD HH24:MI:SS'),100,'Nutzer, der diesen Eintrag aktualisiert hat','D',10,'Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','Y','N','N','N','N','N','N','N','N','N','N','Aktualisiert durch',TO_TIMESTAMP('2023-02-01 21:41:28','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-01T19:41:28.626Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585692 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-01T19:41:28.891Z
/* DDL */  select update_Column_Translation_From_AD_Element(608) 
;

-- Column: C_BPartner_CreditLimit_Departments_V.IsActive
-- 2023-02-01T19:41:29.540Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,585693,348,0,10,542288,'IsActive',TO_TIMESTAMP('2023-02-01 21:41:29','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','D',2147483647,'Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','Y','N','N','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2023-02-01 21:41:29','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-01T19:41:29.541Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585693 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-01T19:41:29.734Z
/* DDL */  select update_Column_Translation_From_AD_Element(348) 
;

-- Column: C_BPartner_CreditLimit_Departments_V.AD_Client_ID
-- 2023-02-01T19:41:30.593Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,585694,102,0,30,542288,'AD_Client_ID',TO_TIMESTAMP('2023-02-01 21:41:30','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','D',10,'Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','Y','N','N','N','N','N','N','N','N','N','Mandant',TO_TIMESTAMP('2023-02-01 21:41:30','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-01T19:41:30.594Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585694 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-01T19:41:30.597Z
/* DDL */  select update_Column_Translation_From_AD_Element(102) 
;

-- Column: C_BPartner_CreditLimit_Departments_V.AD_Org_ID
-- 2023-02-01T19:41:31.805Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,585695,113,0,30,542288,'AD_Org_ID',TO_TIMESTAMP('2023-02-01 21:41:31','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','D',10,'Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','Y','N','N','N','N','N','N','N','N','N','Sektion',TO_TIMESTAMP('2023-02-01 21:41:31','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-01T19:41:31.806Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585695 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-01T19:41:31.809Z
/* DDL */  select update_Column_Translation_From_AD_Element(113) 
;

-- 2023-02-01T19:42:05.261Z
UPDATE AD_Element SET ColumnName='OpenOrderAmt',Updated=TO_TIMESTAMP('2023-02-01 21:42:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581985
;

-- 2023-02-01T19:42:05.263Z
UPDATE AD_Column SET ColumnName='OpenOrderAmt' WHERE AD_Element_ID=581985
;

-- 2023-02-01T19:42:05.264Z
UPDATE AD_Process_Para SET ColumnName='OpenOrderAmt' WHERE AD_Element_ID=581985
;

-- 2023-02-01T19:42:05.268Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581985,'de_DE') 
;





-- Column: C_BPartner_CreditLimit_Departments_V.AD_Client_ID
-- 2023-02-01T19:47:27.054Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=585694
;

-- 2023-02-01T19:47:27.060Z
DELETE FROM AD_Column WHERE AD_Column_ID=585694
;

-- Column: C_BPartner_CreditLimit_Departments_V.AD_Org_ID
-- 2023-02-01T19:47:27.731Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=585695
;

-- 2023-02-01T19:47:27.736Z
DELETE FROM AD_Column WHERE AD_Column_ID=585695
;

-- Column: C_BPartner_CreditLimit_Departments_V.c_bpartner_creditlimit_departments_v
-- 2023-02-01T19:47:28.509Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=585688
;

-- 2023-02-01T19:47:28.514Z
DELETE FROM AD_Column WHERE AD_Column_ID=585688
;

-- Column: C_BPartner_CreditLimit_Departments_V.C_BPartner_ID
-- 2023-02-01T19:47:29.336Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=585678
;

-- 2023-02-01T19:47:29.342Z
DELETE FROM AD_Column WHERE AD_Column_ID=585678
;

-- Column: C_BPartner_CreditLimit_Departments_V.C_Currency_ID
-- 2023-02-01T19:47:30.325Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=585681
;

-- 2023-02-01T19:47:30.332Z
DELETE FROM AD_Column WHERE AD_Column_ID=585681
;

-- Column: C_BPartner_CreditLimit_Departments_V.Created
-- 2023-02-01T19:47:31.329Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=585689
;

-- 2023-02-01T19:47:31.336Z
DELETE FROM AD_Column WHERE AD_Column_ID=585689
;

-- Column: C_BPartner_CreditLimit_Departments_V.CreatedBy
-- 2023-02-01T19:47:32.135Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=585691
;

-- 2023-02-01T19:47:32.141Z
DELETE FROM AD_Column WHERE AD_Column_ID=585691
;

-- Column: C_BPartner_CreditLimit_Departments_V.CreditLimit
-- 2023-02-01T19:47:32.950Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=585687
;

-- 2023-02-01T19:47:32.961Z
DELETE FROM AD_Column WHERE AD_Column_ID=585687
;

-- Column: C_BPartner_CreditLimit_Departments_V.delivery_crediytused
-- 2023-02-01T19:47:37.684Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=585684
;

-- 2023-02-01T19:47:37.717Z
DELETE FROM AD_Column WHERE AD_Column_ID=585684
;

-- Column: C_BPartner_CreditLimit_Departments_V.IsActive
-- 2023-02-01T19:47:39.801Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=585693
;

-- 2023-02-01T19:47:39.807Z
DELETE FROM AD_Column WHERE AD_Column_ID=585693
;

-- Column: C_BPartner_CreditLimit_Departments_V.M_Department_ID
-- 2023-02-01T19:47:41.766Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=585680
;

-- 2023-02-01T19:47:41.772Z
DELETE FROM AD_Column WHERE AD_Column_ID=585680
;

-- Column: C_BPartner_CreditLimit_Departments_V.m_department_order_openamt
-- 2023-02-01T19:47:42.774Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=585683
;

-- 2023-02-01T19:47:42.781Z
DELETE FROM AD_Column WHERE AD_Column_ID=585683
;

-- Column: C_BPartner_CreditLimit_Departments_V.M_SectionCode_ID
-- 2023-02-01T19:47:43.897Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=585679
;

-- 2023-02-01T19:47:43.903Z
DELETE FROM AD_Column WHERE AD_Column_ID=585679
;

-- Column: C_BPartner_CreditLimit_Departments_V.OpenOrderAmt
-- 2023-02-01T19:47:44.679Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=585686
;

-- 2023-02-01T19:47:44.685Z
DELETE FROM AD_Column WHERE AD_Column_ID=585686
;

-- Column: C_BPartner_CreditLimit_Departments_V.SO_CreditUsed
-- 2023-02-01T19:47:45.540Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=585682
;

-- 2023-02-01T19:47:45.545Z
DELETE FROM AD_Column WHERE AD_Column_ID=585682
;

-- Column: C_BPartner_CreditLimit_Departments_V.totalordervalue
-- 2023-02-01T19:47:46.265Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=585685
;

-- 2023-02-01T19:47:46.271Z
DELETE FROM AD_Column WHERE AD_Column_ID=585685
;

-- Column: C_BPartner_CreditLimit_Departments_V.Updated
-- 2023-02-01T19:47:47.081Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=585690
;

-- 2023-02-01T19:47:47.086Z
DELETE FROM AD_Column WHERE AD_Column_ID=585690
;

-- Column: C_BPartner_CreditLimit_Departments_V.UpdatedBy
-- 2023-02-01T19:47:48.266Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=585692
;

-- 2023-02-01T19:47:48.301Z
DELETE FROM AD_Column WHERE AD_Column_ID=585692
;

-- Column: C_BPartner_CreditLimit_Departments_V.C_BPartner_ID
-- 2023-02-01T19:49:28.036Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,585696,187,0,30,542288,'C_BPartner_ID',TO_TIMESTAMP('2023-02-01 21:49:27','YYYY-MM-DD HH24:MI:SS'),100,'Bezeichnet einen Geschäftspartner','D',10,'Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.','Y','Y','N','N','N','N','N','N','N','N','N','Geschäftspartner',TO_TIMESTAMP('2023-02-01 21:49:27','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-01T19:49:28.038Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585696 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-01T19:49:28.042Z
/* DDL */  select update_Column_Translation_From_AD_Element(187) 
;

-- Column: C_BPartner_CreditLimit_Departments_V.M_SectionCode_ID
-- 2023-02-01T19:49:29.506Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,585697,581238,0,30,542288,'M_SectionCode_ID',TO_TIMESTAMP('2023-02-01 21:49:29','YYYY-MM-DD HH24:MI:SS'),100,'D',10,'Y','Y','N','N','N','N','N','N','N','N','N','Sektionskennung',TO_TIMESTAMP('2023-02-01 21:49:29','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-01T19:49:29.508Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585697 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-01T19:49:29.724Z
/* DDL */  select update_Column_Translation_From_AD_Element(581238) 
;

-- Column: C_BPartner_CreditLimit_Departments_V.M_Department_ID
-- 2023-02-01T19:49:30.575Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,585698,581944,0,30,542288,'M_Department_ID',TO_TIMESTAMP('2023-02-01 21:49:30','YYYY-MM-DD HH24:MI:SS'),100,'D',10,'Y','Y','N','N','N','N','N','N','N','N','N','Department',TO_TIMESTAMP('2023-02-01 21:49:30','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-01T19:49:30.583Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585698 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-01T19:49:32.040Z
/* DDL */  select update_Column_Translation_From_AD_Element(581944) 
;

-- Column: C_BPartner_CreditLimit_Departments_V.C_Currency_ID
-- 2023-02-01T19:49:34.515Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,585699,193,0,30,542288,'C_Currency_ID',TO_TIMESTAMP('2023-02-01 21:49:34','YYYY-MM-DD HH24:MI:SS'),100,'Die Währung für diesen Eintrag','D',10,'Bezeichnet die auf Dokumenten oder Berichten verwendete Währung','Y','Y','N','N','N','N','N','N','N','N','N','Währung',TO_TIMESTAMP('2023-02-01 21:49:34','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-01T19:49:34.516Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585699 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-01T19:49:34.712Z
/* DDL */  select update_Column_Translation_From_AD_Element(193) 
;

-- Column: C_BPartner_CreditLimit_Departments_V.SO_CreditUsed
-- 2023-02-01T19:49:35.216Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,585700,554,0,22,542288,'SO_CreditUsed',TO_TIMESTAMP('2023-02-01 21:49:35','YYYY-MM-DD HH24:MI:SS'),100,'Gegenwärtiger Aussenstand','D',14,'The Credit Used indicates the total amount of open or unpaid invoices in primary accounting currency for the Business Partner. Credit Management is based on the Total Open Amount, which includes Vendor activities.','Y','Y','N','N','N','N','N','N','N','N','N','Kredit gewährt',TO_TIMESTAMP('2023-02-01 21:49:35','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-01T19:49:35.218Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585700 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-01T19:49:35.484Z
/* DDL */  select update_Column_Translation_From_AD_Element(554) 
;

-- Column: C_BPartner_CreditLimit_Departments_V.m_department_order_openamt
-- 2023-02-01T19:49:36.002Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,585701,581982,0,12,542288,'m_department_order_openamt',TO_TIMESTAMP('2023-02-01 21:49:35','YYYY-MM-DD HH24:MI:SS'),100,'D',14,'Y','Y','N','N','N','N','N','N','N','N','N','m_department_order_openamt',TO_TIMESTAMP('2023-02-01 21:49:35','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-01T19:49:36.004Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585701 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-01T19:49:36.201Z
/* DDL */  select update_Column_Translation_From_AD_Element(581982) 
;

-- Column: C_BPartner_CreditLimit_Departments_V.Delivery_CreditUsed
-- 2023-02-01T19:49:37.626Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,585702,581934,0,22,542288,'Delivery_CreditUsed',TO_TIMESTAMP('2023-02-01 21:49:37','YYYY-MM-DD HH24:MI:SS'),100,'Specifies the amount of money that was already spent from the credit for completed delivery instructions. ','D',14,'Specifies the amount of money that was already spent from the credit for completed delivery instructions. ','Y','Y','N','N','N','N','N','N','N','N','N','Delivery Credit Used',TO_TIMESTAMP('2023-02-01 21:49:37','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-01T19:49:37.628Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585702 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-01T19:49:37.822Z
/* DDL */  select update_Column_Translation_From_AD_Element(581934) 
;

-- Column: C_BPartner_CreditLimit_Departments_V.totalordervalue
-- 2023-02-01T19:49:38.373Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,585703,581984,0,22,542288,'totalordervalue',TO_TIMESTAMP('2023-02-01 21:49:38','YYYY-MM-DD HH24:MI:SS'),100,'D',14,'Y','Y','N','N','N','N','N','N','N','N','N','totalordervalue',TO_TIMESTAMP('2023-02-01 21:49:38','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-01T19:49:38.374Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585703 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-01T19:49:38.582Z
/* DDL */  select update_Column_Translation_From_AD_Element(581984) 
;

-- Column: C_BPartner_CreditLimit_Departments_V.OpenItems
-- 2023-02-01T19:49:39.093Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,585704,543858,0,22,542288,'OpenItems',TO_TIMESTAMP('2023-02-01 21:49:38','YYYY-MM-DD HH24:MI:SS'),100,'','D',14,'','Y','Y','N','N','N','N','N','N','N','N','N','Offene Posten',TO_TIMESTAMP('2023-02-01 21:49:38','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-01T19:49:39.094Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585704 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-01T19:49:39.285Z
/* DDL */  select update_Column_Translation_From_AD_Element(543858) 
;

-- Column: C_BPartner_CreditLimit_Departments_V.CreditLimit
-- 2023-02-01T19:49:39.869Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,585705,855,0,22,542288,'CreditLimit',TO_TIMESTAMP('2023-02-01 21:49:39','YYYY-MM-DD HH24:MI:SS'),100,'Amount of Credit allowed','D',14,'The Credit Limit field indicates the credit limit for this account.','Y','Y','N','N','N','N','N','N','N','N','N','Credit limit',TO_TIMESTAMP('2023-02-01 21:49:39','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-01T19:49:39.871Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585705 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-01T19:49:40.054Z
/* DDL */  select update_Column_Translation_From_AD_Element(855) 
;

-- Column: C_BPartner_CreditLimit_Departments_V.c_bpartner_creditlimit_departments_v
-- 2023-02-01T19:49:40.656Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,585706,581986,0,11,542288,'c_bpartner_creditlimit_departments_v',TO_TIMESTAMP('2023-02-01 21:49:40','YYYY-MM-DD HH24:MI:SS'),100,'D',10,'Y','Y','N','N','N','N','N','N','N','N','N','c_bpartner_creditlimit_departments_v',TO_TIMESTAMP('2023-02-01 21:49:40','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-01T19:49:40.658Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585706 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-01T19:49:40.908Z
/* DDL */  select update_Column_Translation_From_AD_Element(581986) 
;

-- Column: C_BPartner_CreditLimit_Departments_V.Created
-- 2023-02-01T19:49:41.393Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,585707,245,0,16,542288,'Created',TO_TIMESTAMP('2023-02-01 21:49:41','YYYY-MM-DD HH24:MI:SS'),100,'Datum, an dem dieser Eintrag erstellt wurde','D',35,'Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','N','N','N','N','N','N','N','N','N','N','Erstellt',TO_TIMESTAMP('2023-02-01 21:49:41','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-01T19:49:41.395Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585707 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-01T19:49:41.664Z
/* DDL */  select update_Column_Translation_From_AD_Element(245) 
;

-- Column: C_BPartner_CreditLimit_Departments_V.Updated
-- 2023-02-01T19:49:42.383Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,585708,607,0,16,542288,'Updated',TO_TIMESTAMP('2023-02-01 21:49:42','YYYY-MM-DD HH24:MI:SS'),100,'Datum, an dem dieser Eintrag aktualisiert wurde','D',35,'Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Y','N','N','N','N','N','N','N','N','N','N','Aktualisiert',TO_TIMESTAMP('2023-02-01 21:49:42','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-01T19:49:42.385Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585708 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-01T19:49:42.677Z
/* DDL */  select update_Column_Translation_From_AD_Element(607) 
;

-- Column: C_BPartner_CreditLimit_Departments_V.CreatedBy
-- 2023-02-01T19:49:43.383Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,585709,246,0,18,110,542288,'CreatedBy',TO_TIMESTAMP('2023-02-01 21:49:43','YYYY-MM-DD HH24:MI:SS'),100,'Nutzer, der diesen Eintrag erstellt hat','D',10,'Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','N','N','N','N','N','N','N','N','N','N','Erstellt durch',TO_TIMESTAMP('2023-02-01 21:49:43','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-01T19:49:43.385Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585709 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-01T19:49:43.583Z
/* DDL */  select update_Column_Translation_From_AD_Element(246) 
;

-- Column: C_BPartner_CreditLimit_Departments_V.UpdatedBy
-- 2023-02-01T19:49:44.258Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,585710,608,0,18,110,542288,'UpdatedBy',TO_TIMESTAMP('2023-02-01 21:49:44','YYYY-MM-DD HH24:MI:SS'),100,'Nutzer, der diesen Eintrag aktualisiert hat','D',10,'Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','Y','N','N','N','N','N','N','N','N','N','N','Aktualisiert durch',TO_TIMESTAMP('2023-02-01 21:49:44','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-01T19:49:44.259Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585710 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-01T19:49:44.513Z
/* DDL */  select update_Column_Translation_From_AD_Element(608) 
;

-- Column: C_BPartner_CreditLimit_Departments_V.IsActive
-- 2023-02-01T19:49:45.257Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,585711,348,0,10,542288,'IsActive',TO_TIMESTAMP('2023-02-01 21:49:45','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','D',2147483647,'Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','Y','N','N','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2023-02-01 21:49:45','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-01T19:49:45.259Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585711 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-01T19:49:45.520Z
/* DDL */  select update_Column_Translation_From_AD_Element(348) 
;

-- Column: C_BPartner_CreditLimit_Departments_V.AD_Client_ID
-- 2023-02-01T19:49:46.231Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,585712,102,0,30,542288,'AD_Client_ID',TO_TIMESTAMP('2023-02-01 21:49:46','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','D',10,'Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','Y','N','N','N','N','N','N','N','N','N','Mandant',TO_TIMESTAMP('2023-02-01 21:49:46','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-01T19:49:46.233Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585712 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-01T19:49:46.522Z
/* DDL */  select update_Column_Translation_From_AD_Element(102) 
;

-- Column: C_BPartner_CreditLimit_Departments_V.AD_Org_ID
-- 2023-02-01T19:49:47.220Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,585713,113,0,30,542288,'AD_Org_ID',TO_TIMESTAMP('2023-02-01 21:49:47','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','D',10,'Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','Y','N','N','N','N','N','N','N','N','N','Sektion',TO_TIMESTAMP('2023-02-01 21:49:47','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-01T19:49:47.223Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585713 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-01T19:49:47.524Z
/* DDL */  select update_Column_Translation_From_AD_Element(113) 
;

-- Element: m_department_order_openamt
-- 2023-02-01T19:50:39.659Z
UPDATE AD_Element_Trl SET Name='Order Open Amount (Department)', PrintName='Order Open Amount (Department)',Updated=TO_TIMESTAMP('2023-02-01 21:50:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581982 AND AD_Language='de_CH'
;

-- 2023-02-01T19:50:39.661Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581982,'de_CH') 
;

-- Element: m_department_order_openamt
-- 2023-02-01T19:50:42.922Z
UPDATE AD_Element_Trl SET Name='Order Open Amount (Department)', PrintName='Order Open Amount (Department)',Updated=TO_TIMESTAMP('2023-02-01 21:50:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581982 AND AD_Language='de_DE'
;

-- 2023-02-01T19:50:42.923Z
UPDATE AD_Element SET Name='Order Open Amount (Department)', PrintName='Order Open Amount (Department)' WHERE AD_Element_ID=581982
;

-- 2023-02-01T19:50:43.357Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581982,'de_DE') 
;

-- 2023-02-01T19:50:43.358Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581982,'de_DE') 
;

-- Element: m_department_order_openamt
-- 2023-02-01T19:50:46.284Z
UPDATE AD_Element_Trl SET Name='Order Open Amount (Department)', PrintName='Order Open Amount (Department)',Updated=TO_TIMESTAMP('2023-02-01 21:50:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581982 AND AD_Language='en_US'
;

-- 2023-02-01T19:50:46.286Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581982,'en_US') 
;

-- Element: m_department_order_openamt
-- 2023-02-01T19:50:51.109Z
UPDATE AD_Element_Trl SET Name='Order Open Amount (Department)', PrintName='Order Open Amount (Department)',Updated=TO_TIMESTAMP('2023-02-01 21:50:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581982 AND AD_Language='fr_CH'
;

-- 2023-02-01T19:50:51.124Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581982,'fr_CH') 
;

-- Element: m_department_order_openamt
-- 2023-02-01T19:50:57.144Z
UPDATE AD_Element_Trl SET Name='Order Open Amount (Department)', PrintName='Order Open Amount (Department)',Updated=TO_TIMESTAMP('2023-02-01 21:50:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581982 AND AD_Language='nl_NL'
;

-- 2023-02-01T19:50:57.182Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581982,'nl_NL') 
;

-- 2023-02-01T19:51:15.007Z
UPDATE AD_Element SET ColumnName='M_Department_Order_OpenAmt',Updated=TO_TIMESTAMP('2023-02-01 21:51:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581982
;

-- 2023-02-01T19:51:15.008Z
UPDATE AD_Column SET ColumnName='M_Department_Order_OpenAmt' WHERE AD_Element_ID=581982
;

-- 2023-02-01T19:51:15.009Z
UPDATE AD_Process_Para SET ColumnName='M_Department_Order_OpenAmt' WHERE AD_Element_ID=581982
;

-- 2023-02-01T19:51:15.012Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581982,'de_DE') 
;

-- 2023-02-01T19:51:57.560Z
UPDATE AD_Element SET ColumnName='TotalOrderValue',Updated=TO_TIMESTAMP('2023-02-01 21:51:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581984
;

-- 2023-02-01T19:51:57.568Z
UPDATE AD_Column SET ColumnName='TotalOrderValue' WHERE AD_Element_ID=581984
;

-- 2023-02-01T19:51:57.578Z
UPDATE AD_Process_Para SET ColumnName='TotalOrderValue' WHERE AD_Element_ID=581984
;

-- 2023-02-01T19:51:57.605Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581984,'de_DE') 
;

-- Element: TotalOrderValue
-- 2023-02-01T19:52:13.253Z
UPDATE AD_Element_Trl SET Name='Total Order Value', PrintName='Total Order Value',Updated=TO_TIMESTAMP('2023-02-01 21:52:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581984 AND AD_Language='de_CH'
;

-- 2023-02-01T19:52:13.256Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581984,'de_CH') 
;

-- Element: TotalOrderValue
-- 2023-02-01T19:52:16.270Z
UPDATE AD_Element_Trl SET Name='Total Order Value', PrintName='Total Order Value',Updated=TO_TIMESTAMP('2023-02-01 21:52:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581984 AND AD_Language='de_DE'
;

-- 2023-02-01T19:52:16.277Z
UPDATE AD_Element SET Name='Total Order Value', PrintName='Total Order Value' WHERE AD_Element_ID=581984
;

-- 2023-02-01T19:52:18.669Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581984,'de_DE') 
;

-- 2023-02-01T19:52:18.678Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581984,'de_DE') 
;

-- Element: TotalOrderValue
-- 2023-02-01T19:52:22.185Z
UPDATE AD_Element_Trl SET Name='Total Order Value', PrintName='Total Order Value',Updated=TO_TIMESTAMP('2023-02-01 21:52:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581984 AND AD_Language='en_US'
;

-- 2023-02-01T19:52:22.198Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581984,'en_US') 
;

-- Element: TotalOrderValue
-- 2023-02-01T19:52:24.899Z
UPDATE AD_Element_Trl SET Name='Total Order Value', PrintName='Total Order Value',Updated=TO_TIMESTAMP('2023-02-01 21:52:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581984 AND AD_Language='fr_CH'
;

-- 2023-02-01T19:52:24.902Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581984,'fr_CH') 
;

-- Element: TotalOrderValue
-- 2023-02-01T19:52:28.519Z
UPDATE AD_Element_Trl SET Name='Total Order Value', PrintName='Total Order Value',Updated=TO_TIMESTAMP('2023-02-01 21:52:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581984 AND AD_Language='nl_NL'
;

-- 2023-02-01T19:52:28.522Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581984,'nl_NL') 
;

-- 2023-02-01T19:53:03.806Z
UPDATE AD_Element SET ColumnName='C_BPartner_CreditLimit_Departments_v',Updated=TO_TIMESTAMP('2023-02-01 21:53:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581986
;

-- 2023-02-01T19:53:03.815Z
UPDATE AD_Column SET ColumnName='C_BPartner_CreditLimit_Departments_v' WHERE AD_Element_ID=581986
;

-- 2023-02-01T19:53:03.824Z
UPDATE AD_Process_Para SET ColumnName='C_BPartner_CreditLimit_Departments_v' WHERE AD_Element_ID=581986
;

-- 2023-02-01T19:53:03.846Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581986,'de_DE') 
;

-- Element: C_BPartner_CreditLimit_Departments_v
-- 2023-02-01T19:53:23.394Z
UPDATE AD_Element_Trl SET Name='Credit Limit (Departments)', PrintName='Credit Limit (Departments)',Updated=TO_TIMESTAMP('2023-02-01 21:53:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581986 AND AD_Language='de_CH'
;

-- 2023-02-01T19:53:23.396Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581986,'de_CH') 
;

-- Element: C_BPartner_CreditLimit_Departments_v
-- 2023-02-01T19:53:26.246Z
UPDATE AD_Element_Trl SET Name='Credit Limit (Departments)', PrintName='Credit Limit (Departments)',Updated=TO_TIMESTAMP('2023-02-01 21:53:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581986 AND AD_Language='de_DE'
;

-- 2023-02-01T19:53:26.252Z
UPDATE AD_Element SET Name='Credit Limit (Departments)', PrintName='Credit Limit (Departments)' WHERE AD_Element_ID=581986
;

-- 2023-02-01T19:53:28.774Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581986,'de_DE') 
;

-- 2023-02-01T19:53:28.784Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581986,'de_DE') 
;

-- Element: C_BPartner_CreditLimit_Departments_v
-- 2023-02-01T19:53:32.111Z
UPDATE AD_Element_Trl SET Name='Credit Limit (Departments)', PrintName='Credit Limit (Departments)',Updated=TO_TIMESTAMP('2023-02-01 21:53:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581986 AND AD_Language='en_US'
;

-- 2023-02-01T19:53:32.126Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581986,'en_US') 
;

-- Element: C_BPartner_CreditLimit_Departments_v
-- 2023-02-01T19:53:35.345Z
UPDATE AD_Element_Trl SET Name='Credit Limit (Departments)', PrintName='Credit Limit (Departments)',Updated=TO_TIMESTAMP('2023-02-01 21:53:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581986 AND AD_Language='fr_CH'
;

-- 2023-02-01T19:53:35.347Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581986,'fr_CH') 
;

-- Element: C_BPartner_CreditLimit_Departments_v
-- 2023-02-01T19:53:38.790Z
UPDATE AD_Element_Trl SET Name='Credit Limit (Departments)', PrintName='Credit Limit (Departments)',Updated=TO_TIMESTAMP('2023-02-01 21:53:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581986 AND AD_Language='nl_NL'
;

-- 2023-02-01T19:53:38.792Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581986,'nl_NL') 
;



-- Column: C_BPartner_CreditLimit_Departments_V.C_BPartner_CreditLimit_Departments_v
-- 2023-02-01T23:39:26.783Z
UPDATE AD_Column SET IsKey='Y', IsUpdateable='N',Updated=TO_TIMESTAMP('2023-02-02 01:39:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585706
;



-- 2023-02-01T23:45:14.374Z
UPDATE AD_Element SET ColumnName='C_BPartner_CreditLimit_Departments_v_ID',Updated=TO_TIMESTAMP('2023-02-02 01:45:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581986
;

-- 2023-02-01T23:45:14.375Z
UPDATE AD_Column SET ColumnName='C_BPartner_CreditLimit_Departments_v_ID' WHERE AD_Element_ID=581986
;

-- 2023-02-01T23:45:14.377Z
UPDATE AD_Process_Para SET ColumnName='C_BPartner_CreditLimit_Departments_v_ID' WHERE AD_Element_ID=581986
;

-- 2023-02-01T23:45:14.407Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581986,'de_DE') 
;

-- Column: C_BPartner_CreditLimit_Departments_V.C_BPartner_CreditLimit_Departments_v_ID
-- 2023-02-01T23:46:57.277Z
UPDATE AD_Column SET AD_Reference_ID=13, IsUpdateable='N',Updated=TO_TIMESTAMP('2023-02-02 01:46:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585706
;

-- Column: C_BPartner_CreditLimit_Departments_V.C_BPartner_CreditLimit_Departments_v_ID
-- 2023-02-01T23:47:00.906Z
UPDATE AD_Column SET IsMandatory='Y', IsUpdateable='N',Updated=TO_TIMESTAMP('2023-02-02 01:47:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585706
;

-- Column: C_BPartner_CreditLimit_Departments_V.IsActive
-- 2023-02-01T23:53:21.743Z
UPDATE AD_Column SET AD_Reference_ID=20, DefaultValue='Y', FieldLength=1, IsMandatory='Y',Updated=TO_TIMESTAMP('2023-02-02 01:53:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585711
;

-- Column: C_BPartner_CreditLimit_Departments_V.M_Department_ID
-- 2023-02-02T00:15:58.288Z
UPDATE AD_Column SET IsIdentifier='Y',Updated=TO_TIMESTAMP('2023-02-02 02:15:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585698
;

-- Column: C_BPartner_CreditLimit_Departments_V.Section_Group_Partner_ID
-- 2023-02-02T00:16:10.544Z
UPDATE AD_Column SET IsIdentifier='Y',Updated=TO_TIMESTAMP('2023-02-02 02:16:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585714
;

-- Column: C_BPartner_CreditLimit_Departments_V.M_Department_ID
-- 2023-02-02T00:16:24.367Z
UPDATE AD_Column SET SeqNo=1,Updated=TO_TIMESTAMP('2023-02-02 02:16:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585698
;








-- Column: C_BPartner_CreditLimit_Departments_V.Section_Group_Partner_ID
-- 2023-02-02T10:13:45.444Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2023-02-02 12:13:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585714
;

-- Column: C_BPartner_CreditLimit_Departments_V.C_BPartner_ID
-- 2023-02-02T10:14:09.382Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2023-02-02 12:14:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585696
;

-- Column: C_BPartner_CreditLimit_Departments_V.M_Department_ID
-- 2023-02-02T10:14:21.944Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2023-02-02 12:14:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585698
;




-- Column: C_BPartner_CreditLimit_Departments_V.M_Department_Order_OpenAmt
-- 2023-02-02T11:33:23.468Z
UPDATE AD_Column SET AD_Reference_ID=22,Updated=TO_TIMESTAMP('2023-02-02 13:33:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585701
;

