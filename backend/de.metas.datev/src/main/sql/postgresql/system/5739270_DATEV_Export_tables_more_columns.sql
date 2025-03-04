-- Column: RV_DATEV_Export_Fact_Acct_Invoice.TaxAmt
-- Column: RV_DATEV_Export_Fact_Acct_Invoice.TaxAmt
-- 2024-11-13T06:33:22.409Z
UPDATE AD_Column SET FieldLength=10,Updated=TO_TIMESTAMP('2024-11-13 08:33:22.409','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=585324
;

-- Column: RV_DATEV_Export_Fact_Acct_Invoice.GrandTotal
-- Column: RV_DATEV_Export_Fact_Acct_Invoice.GrandTotal
-- 2024-11-13T06:33:36.178Z
UPDATE AD_Column SET FieldLength=10,Updated=TO_TIMESTAMP('2024-11-13 08:33:36.178','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=585325
;

-- Column: RV_DATEV_Export_Fact_Acct_Invoice.Amt
-- Column: RV_DATEV_Export_Fact_Acct_Invoice.Amt
-- 2024-11-13T06:33:40.692Z
UPDATE AD_Column SET FieldLength=10,Updated=TO_TIMESTAMP('2024-11-13 08:33:40.692','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=559161
;

-- Column: RV_DATEV_Export_Fact_Acct_Invoice.AmtSource
-- Column: RV_DATEV_Export_Fact_Acct_Invoice.AmtSource
-- 2024-11-15T09:46:09.708Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,589411,2343,0,12,540936,'AmtSource',TO_TIMESTAMP('2024-11-15 11:46:08.858','YYYY-MM-DD HH24:MI:SS.US'),100,'Amount Balance in Source Currency','de.metas.datev',14,'Y','Y','N','N','N','N','N','N','N','N','N','Source Amount',TO_TIMESTAMP('2024-11-15 11:46:08.858','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-11-15T09:46:09.759Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589411 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-11-15T09:46:09.900Z
/* DDL */  select update_Column_Translation_From_AD_Element(2343) 
;

-- 2024-11-15T09:46:12.830Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583370,0,'taxamtsource',TO_TIMESTAMP('2024-11-15 11:46:12.369','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.datev','Y','taxamtsource','taxamtsource',TO_TIMESTAMP('2024-11-15 11:46:12.369','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-11-15T09:46:12.884Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583370 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: RV_DATEV_Export_Fact_Acct_Invoice.taxamtsource
-- Column: RV_DATEV_Export_Fact_Acct_Invoice.taxamtsource
-- 2024-11-15T09:46:14.679Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,589412,583370,0,12,540936,'taxamtsource',TO_TIMESTAMP('2024-11-15 11:46:12.215','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.datev',14,'Y','Y','N','N','N','N','N','N','N','N','N','taxamtsource',TO_TIMESTAMP('2024-11-15 11:46:12.215','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-11-15T09:46:14.729Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589412 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-11-15T09:46:14.833Z
/* DDL */  select update_Column_Translation_From_AD_Element(583370) 
;

-- Column: RV_DATEV_Export_Fact_Acct_Invoice.C_Tax_ID
-- Column: RV_DATEV_Export_Fact_Acct_Invoice.C_Tax_ID
-- 2024-11-15T09:46:18.804Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,589413,213,0,30,540936,'C_Tax_ID',TO_TIMESTAMP('2024-11-15 11:46:17.098','YYYY-MM-DD HH24:MI:SS.US'),100,'Steuerart','de.metas.datev',10,'Steuer bezeichnet die in dieser Dokumentenzeile verwendete Steuerart.','Y','Y','N','N','N','N','N','N','N','N','N','Steuer',TO_TIMESTAMP('2024-11-15 11:46:17.098','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-11-15T09:46:18.855Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589413 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-11-15T09:46:18.962Z
/* DDL */  select update_Column_Translation_From_AD_Element(213) 
;

-- Column: RV_DATEV_Export_Fact_Acct_Invoice.DateTrx
-- Column: RV_DATEV_Export_Fact_Acct_Invoice.DateTrx
-- 2024-11-15T09:46:21.331Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,589414,1297,0,16,540936,'DateTrx',TO_TIMESTAMP('2024-11-15 11:46:20.643','YYYY-MM-DD HH24:MI:SS.US'),100,'Vorgangsdatum','de.metas.datev',29,'Das "Vorgangsdatum" bezeichnet das Datum des Vorgangs.','Y','Y','N','N','N','N','N','N','N','N','N','Vorgangsdatum',TO_TIMESTAMP('2024-11-15 11:46:20.643','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-11-15T09:46:21.381Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589414 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-11-15T09:46:21.487Z
/* DDL */  select update_Column_Translation_From_AD_Element(1297) 
;

-- Column: RV_DATEV_Export_Fact_Acct_Invoice.C_DocType_ID
-- Column: RV_DATEV_Export_Fact_Acct_Invoice.C_DocType_ID
-- 2024-11-15T09:46:24.433Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,589415,196,0,30,540936,'C_DocType_ID',TO_TIMESTAMP('2024-11-15 11:46:22.95','YYYY-MM-DD HH24:MI:SS.US'),100,'Belegart oder Verarbeitungsvorgaben','de.metas.datev',10,'Die Belegart bestimmt den Nummernkreis und die Vorgaben für die Belegverarbeitung.','Y','Y','N','N','N','N','N','N','N','N','N','Belegart',TO_TIMESTAMP('2024-11-15 11:46:22.95','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-11-15T09:46:24.486Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589415 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-11-15T09:46:24.590Z
/* DDL */  select update_Column_Translation_From_AD_Element(196) 
;

-- Column: RV_DATEV_Export_Fact_Acct_Invoice.IsSOTrx
-- Column: RV_DATEV_Export_Fact_Acct_Invoice.IsSOTrx
-- 2024-11-15T09:46:26.661Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,589416,1106,0,14,540936,'IsSOTrx',TO_TIMESTAMP('2024-11-15 11:46:25.998','YYYY-MM-DD HH24:MI:SS.US'),100,'Dies ist eine Verkaufstransaktion','de.metas.datev',2147483647,'The Sales Transaction checkbox indicates if this item is a Sales Transaction.','Y','Y','N','N','N','N','N','N','N','N','N','Verkaufstransaktion',TO_TIMESTAMP('2024-11-15 11:46:25.998','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-11-15T09:46:26.712Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589416 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-11-15T09:46:26.815Z
/* DDL */  select update_Column_Translation_From_AD_Element(1106) 
;

-- Column: RV_DATEV_Export_Fact_Acct_Invoice.C_AcctSchema_ID
-- Column: RV_DATEV_Export_Fact_Acct_Invoice.C_AcctSchema_ID
-- 2024-11-15T09:46:29.704Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,589417,181,0,30,540936,'C_AcctSchema_ID',TO_TIMESTAMP('2024-11-15 11:46:28.347','YYYY-MM-DD HH24:MI:SS.US'),100,'Stammdaten für Buchhaltung','de.metas.datev',10,'Ein Kontenschema definiert eine Ausprägung von Stammdaten für die Buchhaltung wie verwendete Art der Kostenrechnung, Währung und Buchungsperiode.','Y','Y','N','N','N','N','N','N','N','N','N','Buchführungs-Schema',TO_TIMESTAMP('2024-11-15 11:46:28.347','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-11-15T09:46:29.754Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589417 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-11-15T09:46:29.859Z
/* DDL */  select update_Column_Translation_From_AD_Element(181) 
;

-- Column: RV_DATEV_Export_Fact_Acct_Invoice.PostingType
-- Column: RV_DATEV_Export_Fact_Acct_Invoice.PostingType
-- 2024-11-15T09:46:31.977Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,589418,514,0,14,540936,'PostingType',TO_TIMESTAMP('2024-11-15 11:46:31.296','YYYY-MM-DD HH24:MI:SS.US'),100,'Die Art des gebuchten Betrages dieser Transaktion','de.metas.datev',2147483647,'Die "Buchungsart" zeigt die Art des Betrages (Ist, Budget, Reservierung, Statistitisch) der Transaktion an.','Y','Y','N','N','N','N','N','N','N','N','N','Buchungsart',TO_TIMESTAMP('2024-11-15 11:46:31.296','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-11-15T09:46:32.028Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589418 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-11-15T09:46:32.129Z
/* DDL */  select update_Column_Translation_From_AD_Element(514) 
;

-- Column: RV_DATEV_Export_Fact_Acct_Invoice.POReference
-- Column: RV_DATEV_Export_Fact_Acct_Invoice.POReference
-- 2024-11-15T09:46:34.203Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,589419,952,0,14,540936,'POReference',TO_TIMESTAMP('2024-11-15 11:46:33.52','YYYY-MM-DD HH24:MI:SS.US'),100,'Referenz-Nummer des Kunden','de.metas.datev',2147483647,'The business partner order reference is the order reference for this specific transaction; Often Purchase Order numbers are given to print on Invoices for easier reference.  A standard number can be defined in the Business Partner (Customer) window.','Y','Y','N','N','N','N','N','N','N','N','N','Referenz',TO_TIMESTAMP('2024-11-15 11:46:33.52','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-11-15T09:46:34.253Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589419 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-11-15T09:46:34.358Z
/* DDL */  select update_Column_Translation_From_AD_Element(952) 
;

-- 2024-11-15T09:47:56.396Z
UPDATE AD_Element SET ColumnName='TaxAmtSource',Updated=TO_TIMESTAMP('2024-11-15 11:47:56.396','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583370
;

-- 2024-11-15T09:47:56.447Z
UPDATE AD_Column SET ColumnName='TaxAmtSource' WHERE AD_Element_ID=583370
;

-- 2024-11-15T09:47:56.498Z
UPDATE AD_Process_Para SET ColumnName='TaxAmtSource' WHERE AD_Element_ID=583370
;

-- 2024-11-15T09:47:56.702Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583370,'de_DE') 
;

-- Element: TaxAmtSource
-- 2024-11-15T09:51:33.800Z
UPDATE AD_Element_Trl SET Name='Tax Amount Source', PrintName='Tax Amount Source',Updated=TO_TIMESTAMP('2024-11-15 11:51:33.8','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583370 AND AD_Language='de_CH'
;

-- 2024-11-15T09:51:33.905Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583370,'de_CH') 
;

-- Element: TaxAmtSource
-- 2024-11-15T09:51:37.383Z
UPDATE AD_Element_Trl SET Name='Tax Amount Source', PrintName='Tax Amount Source',Updated=TO_TIMESTAMP('2024-11-15 11:51:37.383','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583370 AND AD_Language='de_DE'
;

-- 2024-11-15T09:51:37.488Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583370,'de_DE') 
;

-- 2024-11-15T09:51:37.539Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583370,'de_DE') 
;

-- Element: TaxAmtSource
-- 2024-11-15T09:52:16.033Z
UPDATE AD_Element_Trl SET Description='Tax Amount in source currency', IsTranslated='Y', Name='Tax Amount Source', PrintName='Tax Amount Source',Updated=TO_TIMESTAMP('2024-11-15 11:52:16.033','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583370 AND AD_Language='en_US'
;

-- 2024-11-15T09:52:16.137Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583370,'en_US') 
;

-- Column: RV_DATEV_Export_Fact_Acct_Invoice.GrandTotal
-- Column: RV_DATEV_Export_Fact_Acct_Invoice.GrandTotal
-- 2024-11-15T09:53:16.280Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=585325
;

-- 2024-11-15T09:53:16.590Z
DELETE FROM AD_Column WHERE AD_Column_ID=585325
;

-- Column: RV_DATEV_Export_Fact_Acct_Invoice.DebitOrCreditIndicator
-- Column: RV_DATEV_Export_Fact_Acct_Invoice.DebitOrCreditIndicator
-- 2024-11-15T09:54:08.081Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=582635
;

-- 2024-11-15T09:54:08.387Z
DELETE FROM AD_Column WHERE AD_Column_ID=582635
;

-- Column: RV_DATEV_Export_Fact_Acct_Invoice.Fact_Acct_ID
-- Column: RV_DATEV_Export_Fact_Acct_Invoice.Fact_Acct_ID
-- 2024-11-15T09:54:27.430Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=559171
;

-- 2024-11-15T09:54:27.733Z
DELETE FROM AD_Column WHERE AD_Column_ID=559171
;

-- Column: DATEV_ExportLine.AmtSource
-- Column: DATEV_ExportLine.AmtSource
-- 2024-11-15T09:58:12.824Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589420,2343,0,12,540935,'AmtSource',TO_TIMESTAMP('2024-11-15 11:58:12.083','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Amount Balance in Source Currency','de.metas.datev',0,14,'Y','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Source Amount',0,0,TO_TIMESTAMP('2024-11-15 11:58:12.083','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-11-15T09:58:12.876Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589420 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-11-15T09:58:12.979Z
/* DDL */  select update_Column_Translation_From_AD_Element(2343) 
;

-- Column: DATEV_ExportLine.C_AcctSchema_ID
-- Column: DATEV_ExportLine.C_AcctSchema_ID
-- 2024-11-15T09:58:15.525Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589421,181,0,30,540935,'C_AcctSchema_ID',TO_TIMESTAMP('2024-11-15 11:58:14.856','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Stammdaten für Buchhaltung','de.metas.datev',0,10,'Ein Kontenschema definiert eine Ausprägung von Stammdaten für die Buchhaltung wie verwendete Art der Kostenrechnung, Währung und Buchungsperiode.','Y','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Buchführungs-Schema',0,0,TO_TIMESTAMP('2024-11-15 11:58:14.856','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-11-15T09:58:15.576Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589421 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-11-15T09:58:15.681Z
/* DDL */  select update_Column_Translation_From_AD_Element(181) 
;

-- Column: DATEV_ExportLine.C_DocType_ID
-- Column: DATEV_ExportLine.C_DocType_ID
-- 2024-11-15T09:58:17.989Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589422,196,0,30,540935,'C_DocType_ID',TO_TIMESTAMP('2024-11-15 11:58:17.301','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Belegart oder Verarbeitungsvorgaben','de.metas.datev',0,10,'Die Belegart bestimmt den Nummernkreis und die Vorgaben für die Belegverarbeitung.','Y','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Belegart',0,0,TO_TIMESTAMP('2024-11-15 11:58:17.301','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-11-15T09:58:18.042Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589422 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-11-15T09:58:18.145Z
/* DDL */  select update_Column_Translation_From_AD_Element(196) 
;

-- Column: DATEV_ExportLine.C_Tax_ID
-- Column: DATEV_ExportLine.C_Tax_ID
-- 2024-11-15T09:58:20.474Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589423,213,0,30,540935,'C_Tax_ID',TO_TIMESTAMP('2024-11-15 11:58:19.725','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Steuerart','de.metas.datev',0,10,'Steuer bezeichnet die in dieser Dokumentenzeile verwendete Steuerart.','Y','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Steuer',0,0,TO_TIMESTAMP('2024-11-15 11:58:19.725','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-11-15T09:58:20.525Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589423 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-11-15T09:58:20.630Z
/* DDL */  select update_Column_Translation_From_AD_Element(213) 
;

-- Column: DATEV_ExportLine.DateTrx
-- Column: DATEV_ExportLine.DateTrx
-- 2024-11-15T09:58:22.827Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589424,1297,0,16,540935,'DateTrx',TO_TIMESTAMP('2024-11-15 11:58:22.299','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Vorgangsdatum','de.metas.datev',0,29,'Das "Vorgangsdatum" bezeichnet das Datum des Vorgangs.','Y','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Vorgangsdatum',0,0,TO_TIMESTAMP('2024-11-15 11:58:22.299','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-11-15T09:58:22.878Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589424 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-11-15T09:58:22.981Z
/* DDL */  select update_Column_Translation_From_AD_Element(1297) 
;

-- Column: DATEV_ExportLine.IsSOTrx
-- Column: DATEV_ExportLine.IsSOTrx
-- 2024-11-15T09:58:25.171Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589425,1106,0,14,540935,'IsSOTrx',TO_TIMESTAMP('2024-11-15 11:58:24.638','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Dies ist eine Verkaufstransaktion','de.metas.datev',0,2147483647,'The Sales Transaction checkbox indicates if this item is a Sales Transaction.','Y','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Verkaufstransaktion',0,0,TO_TIMESTAMP('2024-11-15 11:58:24.638','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-11-15T09:58:25.222Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589425 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-11-15T09:58:25.325Z
/* DDL */  select update_Column_Translation_From_AD_Element(1106) 
;

-- Column: DATEV_ExportLine.POReference
-- Column: DATEV_ExportLine.POReference
-- 2024-11-15T09:58:27.604Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589426,952,0,14,540935,'POReference',TO_TIMESTAMP('2024-11-15 11:58:26.779','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Referenz-Nummer des Kunden','de.metas.datev',0,2147483647,'The business partner order reference is the order reference for this specific transaction; Often Purchase Order numbers are given to print on Invoices for easier reference.  A standard number can be defined in the Business Partner (Customer) window.','Y','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Referenz',0,0,TO_TIMESTAMP('2024-11-15 11:58:26.779','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-11-15T09:58:27.657Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589426 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-11-15T09:58:27.760Z
/* DDL */  select update_Column_Translation_From_AD_Element(952) 
;

-- Column: DATEV_ExportLine.PostingType
-- Column: DATEV_ExportLine.PostingType
-- 2024-11-15T09:58:29.864Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589427,514,0,14,540935,'PostingType',TO_TIMESTAMP('2024-11-15 11:58:29.095','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Die Art des gebuchten Betrages dieser Transaktion','de.metas.datev',0,2147483647,'Die "Buchungsart" zeigt die Art des Betrages (Ist, Budget, Reservierung, Statistitisch) der Transaktion an.','Y','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Buchungsart',0,0,TO_TIMESTAMP('2024-11-15 11:58:29.095','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-11-15T09:58:29.914Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589427 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-11-15T09:58:30.018Z
/* DDL */  select update_Column_Translation_From_AD_Element(514) 
;

-- Column: DATEV_ExportLine.TaxAmtSource
-- Column: DATEV_ExportLine.TaxAmtSource
-- 2024-11-15T09:58:32.360Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589428,583370,0,12,540935,'TaxAmtSource',TO_TIMESTAMP('2024-11-15 11:58:31.829','YYYY-MM-DD HH24:MI:SS.US'),100,'N','de.metas.datev',0,14,'Y','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Tax Amount Source',0,0,TO_TIMESTAMP('2024-11-15 11:58:31.829','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-11-15T09:58:32.412Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589428 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-11-15T09:58:32.517Z
/* DDL */  select update_Column_Translation_From_AD_Element(583370) 
;

-- Column: DATEV_ExportLine.VATCode
-- Column: DATEV_ExportLine.VATCode
-- 2024-11-15T09:58:35.248Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589429,542959,0,10,540935,'VATCode',TO_TIMESTAMP('2024-11-15 11:58:34.447','YYYY-MM-DD HH24:MI:SS.US'),100,'N','de.metas.datev',0,10,'Y','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','VAT Code',0,0,TO_TIMESTAMP('2024-11-15 11:58:34.447','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-11-15T09:58:35.298Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589429 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-11-15T09:58:35.400Z
/* DDL */  select update_Column_Translation_From_AD_Element(542959) 
;

-- UI Element: Buchungen Export -> Lines.DebitOrCreditIndicator
-- Column: DATEV_ExportLine.DebitOrCreditIndicator
-- UI Element: Buchungen Export(541616,de.metas.datev) -> Lines(546636,de.metas.datev) -> main -> 10 -> default.DebitOrCreditIndicator
-- Column: DATEV_ExportLine.DebitOrCreditIndicator
-- 2024-11-15T10:01:24.655Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=613070
;

-- 2024-11-15T10:01:24.764Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=707376
;

-- Field: Buchungen Export -> Lines -> Soll-/Haben-Kennzeichen
-- Column: DATEV_ExportLine.DebitOrCreditIndicator
-- Field: Buchungen Export(541616,de.metas.datev) -> Lines(546636,de.metas.datev) -> Soll-/Haben-Kennzeichen
-- Column: DATEV_ExportLine.DebitOrCreditIndicator
-- 2024-11-15T10:01:25.026Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=707376
;

-- 2024-11-15T10:01:25.329Z
DELETE FROM AD_Field WHERE AD_Field_ID=707376
;

-- UI Element: Buchungen Export_OLD -> Lines.DebitOrCreditIndicator
-- Column: DATEV_ExportLine.DebitOrCreditIndicator
-- UI Element: Buchungen Export_OLD(540413,de.metas.datev) -> Lines(541037,de.metas.datev) -> main -> 10 -> default.DebitOrCreditIndicator
-- Column: DATEV_ExportLine.DebitOrCreditIndicator
-- 2024-11-15T10:01:26.305Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=605266
;

-- 2024-11-15T10:01:26.408Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=691474
;

-- Field: Buchungen Export_OLD -> Lines -> Soll-/Haben-Kennzeichen
-- Column: DATEV_ExportLine.DebitOrCreditIndicator
-- Field: Buchungen Export_OLD(540413,de.metas.datev) -> Lines(541037,de.metas.datev) -> Soll-/Haben-Kennzeichen
-- Column: DATEV_ExportLine.DebitOrCreditIndicator
-- 2024-11-15T10:01:26.661Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=691474
;

-- 2024-11-15T10:01:26.969Z
DELETE FROM AD_Field WHERE AD_Field_ID=691474
;

-- 2024-11-15T10:01:27.074Z
/* DDL */ SELECT public.db_alter_table('DATEV_ExportLine','ALTER TABLE DATEV_ExportLine DROP COLUMN IF EXISTS DebitOrCreditIndicator')
;

-- Column: DATEV_ExportLine.DebitOrCreditIndicator
-- Column: DATEV_ExportLine.DebitOrCreditIndicator
-- 2024-11-15T10:01:27.399Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=582636
;

-- 2024-11-15T10:01:27.703Z
DELETE FROM AD_Column WHERE AD_Column_ID=582636
;

-- UI Element: Buchungen Export -> Lines.Accounting Fact
-- Column: DATEV_ExportLine.Fact_Acct_ID
-- UI Element: Buchungen Export(541616,de.metas.datev) -> Lines(546636,de.metas.datev) -> main -> 10 -> default.Accounting Fact
-- Column: DATEV_ExportLine.Fact_Acct_ID
-- 2024-11-15T10:05:16.172Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=613067
;

-- 2024-11-15T10:05:16.274Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=707372
;

-- Field: Buchungen Export -> Lines -> Accounting Fact
-- Column: DATEV_ExportLine.Fact_Acct_ID
-- Field: Buchungen Export(541616,de.metas.datev) -> Lines(546636,de.metas.datev) -> Accounting Fact
-- Column: DATEV_ExportLine.Fact_Acct_ID
-- 2024-11-15T10:05:16.528Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=707372
;

-- 2024-11-15T10:05:16.830Z
DELETE FROM AD_Field WHERE AD_Field_ID=707372
;

-- UI Element: Buchungen Export_OLD -> Lines.Accounting Fact
-- Column: DATEV_ExportLine.Fact_Acct_ID
-- UI Element: Buchungen Export_OLD(540413,de.metas.datev) -> Lines(541037,de.metas.datev) -> main -> 10 -> default.Accounting Fact
-- Column: DATEV_ExportLine.Fact_Acct_ID
-- 2024-11-15T10:05:17.801Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=551048
;

-- 2024-11-15T10:05:17.902Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=562755
;

-- Field: Buchungen Export_OLD -> Lines -> Accounting Fact
-- Column: DATEV_ExportLine.Fact_Acct_ID
-- Field: Buchungen Export_OLD(540413,de.metas.datev) -> Lines(541037,de.metas.datev) -> Accounting Fact
-- Column: DATEV_ExportLine.Fact_Acct_ID
-- 2024-11-15T10:05:18.159Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=562755
;

-- 2024-11-15T10:05:18.463Z
DELETE FROM AD_Field WHERE AD_Field_ID=562755
;

-- 2024-11-15T10:05:18.567Z
/* DDL */ SELECT public.db_alter_table('DATEV_ExportLine','ALTER TABLE DATEV_ExportLine DROP COLUMN IF EXISTS Fact_Acct_ID')
;

-- Column: DATEV_ExportLine.Fact_Acct_ID
-- Column: DATEV_ExportLine.Fact_Acct_ID
-- 2024-11-15T10:05:18.883Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=559158
;

-- 2024-11-15T10:05:19.182Z
DELETE FROM AD_Column WHERE AD_Column_ID=559158
;

-- UI Element: Buchungen Export -> Lines.Summe Gesamt
-- Column: DATEV_ExportLine.GrandTotal
-- UI Element: Buchungen Export(541616,de.metas.datev) -> Lines(546636,de.metas.datev) -> main -> 10 -> default.Summe Gesamt
-- Column: DATEV_ExportLine.GrandTotal
-- 2024-11-15T10:06:20.109Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=614520
;

-- 2024-11-15T10:06:20.213Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=709969
;

-- Field: Buchungen Export -> Lines -> Summe Gesamt
-- Column: DATEV_ExportLine.GrandTotal
-- Field: Buchungen Export(541616,de.metas.datev) -> Lines(546636,de.metas.datev) -> Summe Gesamt
-- Column: DATEV_ExportLine.GrandTotal
-- 2024-11-15T10:06:20.471Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=709969
;

-- 2024-11-15T10:06:20.775Z
DELETE FROM AD_Field WHERE AD_Field_ID=709969
;

-- UI Element: Buchungen Export_OLD -> Lines.Summe Gesamt
-- Column: DATEV_ExportLine.GrandTotal
-- UI Element: Buchungen Export_OLD(540413,de.metas.datev) -> Lines(541037,de.metas.datev) -> main -> 10 -> default.Summe Gesamt
-- Column: DATEV_ExportLine.GrandTotal
-- 2024-11-15T10:06:21.753Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=614518
;

-- 2024-11-15T10:06:21.857Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=709967
;

-- Field: Buchungen Export_OLD -> Lines -> Summe Gesamt
-- Column: DATEV_ExportLine.GrandTotal
-- Field: Buchungen Export_OLD(540413,de.metas.datev) -> Lines(541037,de.metas.datev) -> Summe Gesamt
-- Column: DATEV_ExportLine.GrandTotal
-- 2024-11-15T10:06:22.116Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=709967
;

-- 2024-11-15T10:06:22.420Z
DELETE FROM AD_Field WHERE AD_Field_ID=709967
;

-- 2024-11-15T10:06:22.521Z
/* DDL */ SELECT public.db_alter_table('DATEV_ExportLine','ALTER TABLE DATEV_ExportLine DROP COLUMN IF EXISTS GrandTotal')
;

-- Column: DATEV_ExportLine.GrandTotal
-- Column: DATEV_ExportLine.GrandTotal
-- 2024-11-15T10:06:22.834Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=585327
;

-- 2024-11-15T10:06:23.137Z
DELETE FROM AD_Column WHERE AD_Column_ID=585327
;

