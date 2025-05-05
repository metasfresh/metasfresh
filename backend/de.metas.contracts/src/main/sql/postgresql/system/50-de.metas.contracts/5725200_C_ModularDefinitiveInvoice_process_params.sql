-- Run mode: SWING_CLIENT

-- Process: C_ModularDefinitiveInvoice(de.metas.contracts.definitive.process.C_ModularDefinitiveInvoice)
-- ParameterName: OnlyApprovedForInvoicing
-- 2024-05-31T10:23:36.704Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,585396,542834,20,'OnlyApprovedForInvoicing',TO_TIMESTAMP('2024-05-31 13:23:36.415','YYYY-MM-DD HH24:MI:SS.US'),100,'N','de.metas.contracts',0,'Wenn ausgewählt, werden nur die zur Fakturierung freigegebenen Rechnungskandidaten fakturiert.','Y','N','Y','N','Y','N','Nur für Fakturierung freigegeben',10,TO_TIMESTAMP('2024-05-31 13:23:36.415','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-05-31T10:23:36.708Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542834 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2024-05-31T10:23:36.729Z
DELETE FROM AD_Process_Para_Trl WHERE AD_Process_Para_ID = 542834
;

-- 2024-05-31T10:23:36.731Z
INSERT INTO AD_Process_Para_Trl (AD_Process_Para_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 542834, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Process_Para_Trl WHERE AD_Process_Para_ID = 542807
;

-- Process: C_ModularDefinitiveInvoice(de.metas.contracts.definitive.process.C_ModularDefinitiveInvoice)
-- ParameterName: IgnoreInvoiceSchedule
-- 2024-05-31T10:23:36.858Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,541910,0,585396,542835,20,'IgnoreInvoiceSchedule',TO_TIMESTAMP('2024-05-31 13:23:36.732','YYYY-MM-DD HH24:MI:SS.US'),100,'N','de.metas.contracts',0,'Y','N','Y','N','Y','N','Rechnungs-Terminplan ignorieren',20,TO_TIMESTAMP('2024-05-31 13:23:36.732','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-05-31T10:23:36.859Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542835 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2024-05-31T10:23:36.893Z
/* DDL */  select update_Process_Para_Translation_From_AD_Element(541910)
;

-- 2024-05-31T10:23:36.896Z
DELETE FROM AD_Process_Para_Trl WHERE AD_Process_Para_ID = 542835
;

-- 2024-05-31T10:23:36.897Z
INSERT INTO AD_Process_Para_Trl (AD_Process_Para_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 542835, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Process_Para_Trl WHERE AD_Process_Para_ID = 542808
;

-- Process: C_ModularDefinitiveInvoice(de.metas.contracts.definitive.process.C_ModularDefinitiveInvoice)
-- ParameterName: IsUpdateLocationAndContactForInvoice
-- 2024-05-31T10:23:37.003Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,577153,0,585396,542836,20,'IsUpdateLocationAndContactForInvoice',TO_TIMESTAMP('2024-05-31 13:23:36.898','YYYY-MM-DD HH24:MI:SS.US'),100,'@SQL=SELECT get_sysconfig_value_forOrg(''C_Invoice_Send_To_Current_BillTo_Address_And_User'', ''N'', @AD_Org_ID/-1@)',' ','de.metas.contracts',0,'Wenn dieser Parameter aktiviert ist, erhalten die zu erstellenden Rechnungen die aktuellen Benutzer und Standorte ihrer Geschäftspartner, unabhängig von den Werten in Bill_Location und Bill_User, die in den eingereihten Rechnungskandidaten eingestellt sind. Die Rechnungskandidaten selbst werden nicht verändert. Dennoch werden die Werte von Bill_Location_Override und Bill_User_Override eingehalten, sofern sie in den Rechnungskandidaten gesetzt sind.','Y','N','Y','N','Y','N','Rechnungsadresse und -kontakt aktualisieren',30,TO_TIMESTAMP('2024-05-31 13:23:36.898','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-05-31T10:23:37.004Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542836 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2024-05-31T10:23:37.005Z
/* DDL */  select update_Process_Para_Translation_From_AD_Element(577153)
;

-- 2024-05-31T10:23:37.007Z
DELETE FROM AD_Process_Para_Trl WHERE AD_Process_Para_ID = 542836
;

-- 2024-05-31T10:23:37.008Z
INSERT INTO AD_Process_Para_Trl (AD_Process_Para_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 542836, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Process_Para_Trl WHERE AD_Process_Para_ID = 542809
;

-- Process: C_ModularDefinitiveInvoice(de.metas.contracts.definitive.process.C_ModularDefinitiveInvoice)
-- ParameterName: IsCompleteInvoices
-- 2024-05-31T10:23:37.111Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,Description,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,581600,0,585396,542837,20,'IsCompleteInvoices',TO_TIMESTAMP('2024-05-31 13:23:37.009','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','Legt fest, ob die neu erstellten Rechungen fertig gestellt oder nur vorbereitet werden sollen.','de.metas.contracts',0,'Y','N','Y','N','Y','N','Rechnungen fertigstellen',40,TO_TIMESTAMP('2024-05-31 13:23:37.009','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-05-31T10:23:37.113Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542837 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2024-05-31T10:23:37.114Z
/* DDL */  select update_Process_Para_Translation_From_AD_Element(581600)
;

-- 2024-05-31T10:23:37.115Z
DELETE FROM AD_Process_Para_Trl WHERE AD_Process_Para_ID = 542837
;

-- 2024-05-31T10:23:37.116Z
INSERT INTO AD_Process_Para_Trl (AD_Process_Para_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 542837, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Process_Para_Trl WHERE AD_Process_Para_ID = 542810
;

-- Process: C_ModularDefinitiveInvoice(de.metas.contracts.definitive.process.C_ModularDefinitiveInvoice)
-- ParameterName: DateInvoiced
-- 2024-05-31T10:23:37.227Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,267,0,585396,542838,15,'DateInvoiced',TO_TIMESTAMP('2024-05-31 13:23:37.117','YYYY-MM-DD HH24:MI:SS.US'),100,'Datum auf der Rechnung','de.metas.contracts',0,'"Rechnungsdatum" bezeichnet das auf der Rechnung verwendete Datum.','Y','N','Y','N','N','N','Rechnungsdatum',50,TO_TIMESTAMP('2024-05-31 13:23:37.117','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-05-31T10:23:37.228Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542838 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2024-05-31T10:23:37.229Z
/* DDL */  select update_Process_Para_Translation_From_AD_Element(267)
;

-- 2024-05-31T10:23:37.231Z
DELETE FROM AD_Process_Para_Trl WHERE AD_Process_Para_ID = 542838
;

-- 2024-05-31T10:23:37.231Z
INSERT INTO AD_Process_Para_Trl (AD_Process_Para_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 542838, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Process_Para_Trl WHERE AD_Process_Para_ID = 542811
;

-- Process: C_ModularDefinitiveInvoice(de.metas.contracts.definitive.process.C_ModularDefinitiveInvoice)
-- ParameterName: DateAcct
-- 2024-05-31T10:23:37.327Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,263,0,585396,542839,15,'DateAcct',TO_TIMESTAMP('2024-05-31 13:23:37.232','YYYY-MM-DD HH24:MI:SS.US'),100,'Accounting Date','de.metas.contracts',0,'The Accounting Date indicates the date to be used on the General Ledger account entries generated from this document. It is also used for any currency conversion.','Y','N','Y','N','N','N','Buchungsdatum',60,TO_TIMESTAMP('2024-05-31 13:23:37.232','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-05-31T10:23:37.328Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542839 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2024-05-31T10:23:37.329Z
/* DDL */  select update_Process_Para_Translation_From_AD_Element(263)
;

-- 2024-05-31T10:23:37.331Z
DELETE FROM AD_Process_Para_Trl WHERE AD_Process_Para_ID = 542839
;

-- 2024-05-31T10:23:37.331Z
INSERT INTO AD_Process_Para_Trl (AD_Process_Para_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 542839, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Process_Para_Trl WHERE AD_Process_Para_ID = 542812
;

-- Process: C_ModularDefinitiveInvoice(de.metas.contracts.definitive.process.C_ModularDefinitiveInvoice)
-- ParameterName: POReference
-- 2024-05-31T10:23:37.436Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,952,0,585396,542840,10,'POReference',TO_TIMESTAMP('2024-05-31 13:23:37.332','YYYY-MM-DD HH24:MI:SS.US'),100,'Referenz-Nummer des Kunden','de.metas.contracts',0,'The business partner order reference is the order reference for this specific transaction; Often Purchase Order numbers are given to print on Invoices for easier reference.  A standard number can be defined in the Business Partner (Customer) window.','Y','N','Y','N','N','N','Referenz',70,TO_TIMESTAMP('2024-05-31 13:23:37.332','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-05-31T10:23:37.437Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542840 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2024-05-31T10:23:37.438Z
/* DDL */  select update_Process_Para_Translation_From_AD_Element(952)
;

-- 2024-05-31T10:23:37.441Z
DELETE FROM AD_Process_Para_Trl WHERE AD_Process_Para_ID = 542840
;

-- 2024-05-31T10:23:37.441Z
INSERT INTO AD_Process_Para_Trl (AD_Process_Para_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 542840, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Process_Para_Trl WHERE AD_Process_Para_ID = 542813
;

-- Process: C_ModularDefinitiveInvoice(de.metas.contracts.definitive.process.C_ModularDefinitiveInvoice)
-- ParameterName: Check_NetAmtToInvoice
-- 2024-05-31T10:23:37.544Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,DisplayLogic,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,542769,0,585396,542841,12,'Check_NetAmtToInvoice',TO_TIMESTAMP('2024-05-31 13:23:37.442','YYYY-MM-DD HH24:MI:SS.US'),100,'0','1=0','de.metas.contracts',0,'Y','N','Y','N','N','N','Rechungsnetto zur Prüfung',80,TO_TIMESTAMP('2024-05-31 13:23:37.442','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-05-31T10:23:37.545Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542841 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2024-05-31T10:23:37.547Z
/* DDL */  select update_Process_Para_Translation_From_AD_Element(542769)
;

-- 2024-05-31T10:23:37.549Z
DELETE FROM AD_Process_Para_Trl WHERE AD_Process_Para_ID = 542841
;

-- 2024-05-31T10:23:37.550Z
INSERT INTO AD_Process_Para_Trl (AD_Process_Para_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 542841, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Process_Para_Trl WHERE AD_Process_Para_ID = 542814
;

-- Process: C_ModularDefinitiveInvoice(de.metas.contracts.definitive.process.C_ModularDefinitiveInvoice)
-- ParameterName: OverrideDueDate
-- 2024-05-31T10:23:37.642Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,582059,0,585396,542842,15,'OverrideDueDate',TO_TIMESTAMP('2024-05-31 13:23:37.551','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts',0,'Y','N','Y','N','N','N','Fälligkeitsdatum abw.',90,TO_TIMESTAMP('2024-05-31 13:23:37.551','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-05-31T10:23:37.643Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542842 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2024-05-31T10:23:37.644Z
/* DDL */  select update_Process_Para_Translation_From_AD_Element(582059)
;

-- 2024-05-31T10:23:37.646Z
DELETE FROM AD_Process_Para_Trl WHERE AD_Process_Para_ID = 542842
;

-- 2024-05-31T10:23:37.647Z
INSERT INTO AD_Process_Para_Trl (AD_Process_Para_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 542842, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Process_Para_Trl WHERE AD_Process_Para_ID = 542815
;

-- Process: C_ModularDefinitiveInvoice(de.metas.contracts.definitive.process.C_ModularDefinitiveInvoice)
-- Table: C_Flatrate_Term
-- EntityType: de.metas.contracts
-- 2024-05-31T10:24:09.440Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585396,540320,541492,TO_TIMESTAMP('2024-05-31 13:24:09.281','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y',TO_TIMESTAMP('2024-05-31 13:24:09.281','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','Y','N','N')
;

