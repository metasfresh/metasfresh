-- 2022-07-12T15:09:00.420367300Z
-- URL zum Konzept
UPDATE AD_Process_Para SET DefaultValue='@SQL=SELECT get_sysconfig_value(''''C_Invoice_Send_To_Current_BillTo_Address_And_User'''', ''''N'''')',Updated=TO_TIMESTAMP('2022-07-12 18:09:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541615
;

-- 2022-07-12T15:23:10.423159500Z
-- URL zum Konzept
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,Description,DisplayLogic,EntityType,FieldLength,Help,IsActive,
IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,577153,0,584953,1000000,20,'IsUpdateLocationAndContactForInvoice',TO_TIMESTAMP('2022-07-12 18:23:10','YYYY-MM-DD HH24:MI:SS'),100,'@SQL=SELECT get_sysconfig_value(''''C_Invoice_Send_To_Current_BillTo_Address_And_User'''', ''''N'''')',' ','1=2','de.metas.invoicecandidate',0,'Wenn dieser Parameter aktiviert ist, erhalten die zu erstellenden Rechnungen die aktuellen Benutzer und Standorte ihrer Geschäftspartner, unabhängig von den Werten in Bill_Location und Bill_User, die in den eingereihten Rechnungskandidaten eingestellt sind. Die Rechnungskandidaten selbst werden nicht verändert. Dennoch werden die Werte von Bill_Location_Override und Bill_User_Override eingehalten, sofern sie in den Rechnungskandidaten gesetzt sind.','Y','N','Y','N','Y','N','Rechnungsadresse und -kontakt aktualisieren',30,TO_TIMESTAMP('2022-07-12 18:23:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-12T15:23:10.476432400Z
-- URL zum Konzept
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=1000000 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;



-- 2022-07-12T17:51:32.561966900Z
-- URL zum Konzept
UPDATE AD_Process_Para SET DefaultValue='@SQL=SELECT get_sysconfig_value(''C_Invoice_Send_To_Current_BillTo_Address_And_User'', ''N'')',Updated=TO_TIMESTAMP('2022-07-12 20:51:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541615
;

-- 2022-07-12T17:51:47.444515600Z
-- URL zum Konzept
UPDATE AD_Process_Para SET DefaultValue='@SQL=SELECT get_sysconfig_value(''C_Invoice_Send_To_Current_BillTo_Address_And_User'', ''N'')',Updated=TO_TIMESTAMP('2022-07-12 20:51:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=1000000
;

-- 2022-07-13T15:11:18.336660900Z
-- URL zum Konzept
UPDATE AD_Process_Para SET DefaultValue='@SQL=SELECT get_sysconfig_value_forOrg(''C_Invoice_Send_To_Current_BillTo_Address_And_User'', ''N'', @AD_Org_ID/-1@)',Updated=TO_TIMESTAMP('2022-07-13 18:11:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541615
;

-- 2022-07-13T15:11:34.722839800Z
-- URL zum Konzept
UPDATE AD_Process_Para SET DefaultValue='@SQL=SELECT get_sysconfig_value_forOrg(''C_Invoice_Send_To_Current_BillTo_Address_And_User'', ''N'', @AD_Org_ID/-1@)',Updated=TO_TIMESTAMP('2022-07-13 18:11:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=1000000
;




