-- Run mode: SWING_CLIENT

-- Process: CreateFinalInvoice(de.metas.contracts.finalinvoice.process.C_ModularFinalInvoice)
-- ParameterName: OnlyApprovedForInvoicing
-- 2024-04-18T12:36:04.250Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,585375,542807,20,'OnlyApprovedForInvoicing',TO_TIMESTAMP('2024-04-18 15:36:03.88','YYYY-MM-DD HH24:MI:SS.US'),100,'N','de.metas.contracts',0,'Wenn ausgewählt, werden nur die zur Fakturierung freigegebenen Rechnungskandidaten fakturiert.','Y','N','Y','N','Y','N','Nur für Fakturierung freigegeben',10,TO_TIMESTAMP('2024-04-18 15:36:03.88','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-04-18T12:36:04.265Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542807 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2024-04-18T12:36:04.318Z
DELETE FROM AD_Process_Para_Trl WHERE AD_Process_Para_ID = 542807
;

-- 2024-04-18T12:36:04.319Z
INSERT INTO AD_Process_Para_Trl (AD_Process_Para_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 542807, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Process_Para_Trl WHERE AD_Process_Para_ID = 542690
;

-- Process: CreateFinalInvoice(de.metas.contracts.finalinvoice.process.C_ModularFinalInvoice)
-- ParameterName: IgnoreInvoiceSchedule
-- 2024-04-18T12:36:04.425Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,541910,0,585375,542808,20,'IgnoreInvoiceSchedule',TO_TIMESTAMP('2024-04-18 15:36:04.322','YYYY-MM-DD HH24:MI:SS.US'),100,'N','de.metas.contracts',0,'Y','N','Y','N','Y','N','Rechnungs-Terminplan ingorieren',20,TO_TIMESTAMP('2024-04-18 15:36:04.322','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-04-18T12:36:04.427Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542808 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2024-04-18T12:36:04.497Z
/* DDL */  select update_Process_Para_Translation_From_AD_Element(541910)
;

-- 2024-04-18T12:36:04.505Z
DELETE FROM AD_Process_Para_Trl WHERE AD_Process_Para_ID = 542808
;

-- 2024-04-18T12:36:04.506Z
INSERT INTO AD_Process_Para_Trl (AD_Process_Para_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 542808, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Process_Para_Trl WHERE AD_Process_Para_ID = 542691
;

-- Process: CreateFinalInvoice(de.metas.contracts.finalinvoice.process.C_ModularFinalInvoice)
-- ParameterName: IsUpdateLocationAndContactForInvoice
-- 2024-04-18T12:36:04.591Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,577153,0,585375,542809,20,'IsUpdateLocationAndContactForInvoice',TO_TIMESTAMP('2024-04-18 15:36:04.508','YYYY-MM-DD HH24:MI:SS.US'),100,'@SQL=SELECT get_sysconfig_value_forOrg(''C_Invoice_Send_To_Current_BillTo_Address_And_User'', ''N'', @AD_Org_ID/-1@)',' ','de.metas.contracts',0,'Wenn dieser Parameter aktiviert ist, erhalten die zu erstellenden Rechnungen die aktuellen Benutzer und Standorte ihrer Geschäftspartner, unabhängig von den Werten in Bill_Location und Bill_User, die in den eingereihten Rechnungskandidaten eingestellt sind. Die Rechnungskandidaten selbst werden nicht verändert. Dennoch werden die Werte von Bill_Location_Override und Bill_User_Override eingehalten, sofern sie in den Rechnungskandidaten gesetzt sind.','Y','N','Y','N','Y','N','Rechnungsadresse und -kontakt aktualisieren',30,TO_TIMESTAMP('2024-04-18 15:36:04.508','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-04-18T12:36:04.593Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542809 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2024-04-18T12:36:04.594Z
/* DDL */  select update_Process_Para_Translation_From_AD_Element(577153)
;

-- 2024-04-18T12:36:04.598Z
DELETE FROM AD_Process_Para_Trl WHERE AD_Process_Para_ID = 542809
;

-- 2024-04-18T12:36:04.598Z
INSERT INTO AD_Process_Para_Trl (AD_Process_Para_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 542809, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Process_Para_Trl WHERE AD_Process_Para_ID = 542692
;

-- Process: CreateFinalInvoice(de.metas.contracts.finalinvoice.process.C_ModularFinalInvoice)
-- ParameterName: IsCompleteInvoices
-- 2024-04-18T12:36:04.696Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,Description,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,581600,0,585375,542810,20,'IsCompleteInvoices',TO_TIMESTAMP('2024-04-18 15:36:04.6','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','Legt fest, ob die neu erstellten Rechungen fertig gestellt oder nur vorbereitet werden sollen.','de.metas.contracts',0,'Y','N','Y','N','Y','N','Rechnungen fertigstellen',40,TO_TIMESTAMP('2024-04-18 15:36:04.6','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-04-18T12:36:04.698Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542810 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2024-04-18T12:36:04.700Z
/* DDL */  select update_Process_Para_Translation_From_AD_Element(581600)
;

-- 2024-04-18T12:36:04.705Z
DELETE FROM AD_Process_Para_Trl WHERE AD_Process_Para_ID = 542810
;

-- 2024-04-18T12:36:04.706Z
INSERT INTO AD_Process_Para_Trl (AD_Process_Para_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 542810, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Process_Para_Trl WHERE AD_Process_Para_ID = 542693
;

-- Process: CreateFinalInvoice(de.metas.contracts.finalinvoice.process.C_ModularFinalInvoice)
-- ParameterName: DateInvoiced
-- 2024-04-18T12:36:04.802Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,267,0,585375,542811,15,'DateInvoiced',TO_TIMESTAMP('2024-04-18 15:36:04.708','YYYY-MM-DD HH24:MI:SS.US'),100,'Datum auf der Rechnung','de.metas.contracts',0,'"Rechnungsdatum" bezeichnet das auf der Rechnung verwendete Datum.','Y','N','Y','N','N','N','Rechnungsdatum',50,TO_TIMESTAMP('2024-04-18 15:36:04.708','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-04-18T12:36:04.804Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542811 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2024-04-18T12:36:04.806Z
/* DDL */  select update_Process_Para_Translation_From_AD_Element(267)
;

-- 2024-04-18T12:36:04.810Z
DELETE FROM AD_Process_Para_Trl WHERE AD_Process_Para_ID = 542811
;

-- 2024-04-18T12:36:04.810Z
INSERT INTO AD_Process_Para_Trl (AD_Process_Para_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 542811, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Process_Para_Trl WHERE AD_Process_Para_ID = 542694
;

-- Process: CreateFinalInvoice(de.metas.contracts.finalinvoice.process.C_ModularFinalInvoice)
-- ParameterName: DateAcct
-- 2024-04-18T12:36:04.912Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,263,0,585375,542812,15,'DateAcct',TO_TIMESTAMP('2024-04-18 15:36:04.812','YYYY-MM-DD HH24:MI:SS.US'),100,'Accounting Date','de.metas.contracts',0,'The Accounting Date indicates the date to be used on the General Ledger account entries generated from this document. It is also used for any currency conversion.','Y','N','Y','N','N','N','Buchungsdatum',60,TO_TIMESTAMP('2024-04-18 15:36:04.812','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-04-18T12:36:04.914Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542812 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2024-04-18T12:36:04.915Z
/* DDL */  select update_Process_Para_Translation_From_AD_Element(263)
;

-- 2024-04-18T12:36:04.919Z
DELETE FROM AD_Process_Para_Trl WHERE AD_Process_Para_ID = 542812
;

-- 2024-04-18T12:36:04.919Z
INSERT INTO AD_Process_Para_Trl (AD_Process_Para_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 542812, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Process_Para_Trl WHERE AD_Process_Para_ID = 542695
;

-- Process: CreateFinalInvoice(de.metas.contracts.finalinvoice.process.C_ModularFinalInvoice)
-- ParameterName: POReference
-- 2024-04-18T12:36:05.001Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,952,0,585375,542813,10,'POReference',TO_TIMESTAMP('2024-04-18 15:36:04.921','YYYY-MM-DD HH24:MI:SS.US'),100,'Referenz-Nummer des Kunden','de.metas.contracts',0,'The business partner order reference is the order reference for this specific transaction; Often Purchase Order numbers are given to print on Invoices for easier reference.  A standard number can be defined in the Business Partner (Customer) window.','Y','N','Y','N','N','N','Referenz',70,TO_TIMESTAMP('2024-04-18 15:36:04.921','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-04-18T12:36:05.002Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542813 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2024-04-18T12:36:05.004Z
/* DDL */  select update_Process_Para_Translation_From_AD_Element(952)
;

-- 2024-04-18T12:36:05.009Z
DELETE FROM AD_Process_Para_Trl WHERE AD_Process_Para_ID = 542813
;

-- 2024-04-18T12:36:05.010Z
INSERT INTO AD_Process_Para_Trl (AD_Process_Para_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 542813, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Process_Para_Trl WHERE AD_Process_Para_ID = 542696
;

-- Process: CreateFinalInvoice(de.metas.contracts.finalinvoice.process.C_ModularFinalInvoice)
-- ParameterName: Check_NetAmtToInvoice
-- 2024-04-18T12:36:05.100Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,DisplayLogic,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,542769,0,585375,542814,12,'Check_NetAmtToInvoice',TO_TIMESTAMP('2024-04-18 15:36:05.011','YYYY-MM-DD HH24:MI:SS.US'),100,'0','1=0','de.metas.contracts',0,'Y','N','Y','N','N','N','Rechungsnetto zur Prüfung',80,TO_TIMESTAMP('2024-04-18 15:36:05.011','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-04-18T12:36:05.102Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542814 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2024-04-18T12:36:05.103Z
/* DDL */  select update_Process_Para_Translation_From_AD_Element(542769)
;

-- 2024-04-18T12:36:05.107Z
DELETE FROM AD_Process_Para_Trl WHERE AD_Process_Para_ID = 542814
;

-- 2024-04-18T12:36:05.108Z
INSERT INTO AD_Process_Para_Trl (AD_Process_Para_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 542814, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Process_Para_Trl WHERE AD_Process_Para_ID = 542697
;

-- Process: CreateFinalInvoice(de.metas.contracts.finalinvoice.process.C_ModularFinalInvoice)
-- ParameterName: OverrideDueDate
-- 2024-04-18T12:36:05.208Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,582059,0,585375,542815,15,'OverrideDueDate',TO_TIMESTAMP('2024-04-18 15:36:05.11','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts',0,'Y','N','Y','N','N','N','Fälligkeitsdatum abw.',90,TO_TIMESTAMP('2024-04-18 15:36:05.11','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-04-18T12:36:05.210Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542815 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2024-04-18T12:36:05.212Z
/* DDL */  select update_Process_Para_Translation_From_AD_Element(582059)
;

-- 2024-04-18T12:36:05.216Z
DELETE FROM AD_Process_Para_Trl WHERE AD_Process_Para_ID = 542815
;

-- 2024-04-18T12:36:05.217Z
INSERT INTO AD_Process_Para_Trl (AD_Process_Para_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 542815, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Process_Para_Trl WHERE AD_Process_Para_ID = 542698
;

