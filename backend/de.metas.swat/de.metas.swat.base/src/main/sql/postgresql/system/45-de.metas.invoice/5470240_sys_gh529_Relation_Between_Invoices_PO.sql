-- 2017-08-30T14:42:16.474
-- URL zum Konzept
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Source_ID,AD_Reference_Target_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsDirected,Name,Updated,UpdatedBy) VALUES (0,0,540739,540740,540186,TO_TIMESTAMP('2017-08-30 14:42:16','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','Y','C_Invoice -> Referenced Invoice PO',TO_TIMESTAMP('2017-08-30 14:42:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-30T14:42:25.386
-- URL zum Konzept
UPDATE AD_Reference SET Name='C_Invoice Reference target for C_Invoice PO',Updated=TO_TIMESTAMP('2017-08-30 14:42:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540740
;

-- 2017-08-30T14:42:38.684
-- URL zum Konzept
UPDATE AD_Reference SET Name='C_Invoice Reference target for C_Invoice SO',Updated=TO_TIMESTAMP('2017-08-30 14:42:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540740
;

-- 2017-08-30T14:43:02.940
-- URL zum Konzept
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540742,TO_TIMESTAMP('2017-08-30 14:43:02','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.invoice','Y','N','C_Invoice Reference target for C_Invoice PO',TO_TIMESTAMP('2017-08-30 14:43:02','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2017-08-30T14:43:02.941
-- URL zum Konzept
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540742 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2017-08-30T14:43:31.955
-- URL zum Konzept
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy,WhereClause) VALUES (0,3484,0,540742,318,183,TO_TIMESTAMP('2017-08-30 14:43:31','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.invoice','Y','N',TO_TIMESTAMP('2017-08-30 14:43:31','YYYY-MM-DD HH24:MI:SS'),100,'
exists (
	select 1 from C_Invoice i
	join C_Invoice i2 on i2.C_Invoice_ID = i.Ref_Invoice_ID
	
	where i2.C_Invoice_ID = @C_Invoice_ID/-1@  and C_Invoice.C_Invoice_ID = i.C_Invoice_ID
)')
;

-- 2017-08-30T14:43:52.315
-- URL zum Konzept
UPDATE AD_RelationType SET AD_Reference_Target_ID=540742,Updated=TO_TIMESTAMP('2017-08-30 14:43:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540186
;

-- 2017-08-30T14:44:45.312
-- URL zum Konzept
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsDirected,Name,Updated,UpdatedBy) VALUES (0,0,540187,TO_TIMESTAMP('2017-08-30 14:44:45','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','Y','Referenced Invoice -> Invoice PO',TO_TIMESTAMP('2017-08-30 14:44:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-30T14:45:14.596
-- URL zum Konzept
UPDATE AD_Reference SET Name='C_Invoice target for Reference Invoice SO',Updated=TO_TIMESTAMP('2017-08-30 14:45:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540741
;

-- 2017-08-30T14:45:27.885
-- URL zum Konzept
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540743,TO_TIMESTAMP('2017-08-30 14:45:27','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.invoice','Y','N','C_Invoice target for Reference Invoice PO',TO_TIMESTAMP('2017-08-30 14:45:27','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2017-08-30T14:45:27.889
-- URL zum Konzept
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540743 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2017-08-30T14:45:50.450
-- URL zum Konzept
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Display,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy) VALUES (0,3484,3484,0,540743,318,TO_TIMESTAMP('2017-08-30 14:45:50','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.invoice','Y','N',TO_TIMESTAMP('2017-08-30 14:45:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-30T14:46:01.572
-- URL zum Konzept
UPDATE AD_Ref_Table SET AD_Window_ID=183, WhereClause='

exists (
	select 1 from C_Invoice i
	join C_Invoice i2 on i2.Ref_Invoice_ID = i.C_Invoice_ID
	
	where i2.C_Invoice_ID = @C_Invoice_ID/-1@  and C_Invoice.C_Invoice_ID = i.C_Invoice_ID
)',Updated=TO_TIMESTAMP('2017-08-30 14:46:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540743
;

-- 2017-08-30T14:46:15.003
-- URL zum Konzept
UPDATE AD_RelationType SET AD_Reference_Source_ID=540739, AD_Reference_Target_ID=540743,Updated=TO_TIMESTAMP('2017-08-30 14:46:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540187
;

-- 2017-08-30T14:48:11.555
-- URL zum Konzept
UPDATE AD_Reference SET Name='C_Invoice Source SO',Updated=TO_TIMESTAMP('2017-08-30 14:48:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540739
;

-- 2017-08-30T14:48:24.311
-- URL zum Konzept
UPDATE AD_Ref_Table SET WhereClause='IsSOTrx = ''Y''',Updated=TO_TIMESTAMP('2017-08-30 14:48:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540739
;

-- 2017-08-30T14:48:53.429
-- URL zum Konzept
UPDATE AD_Ref_Table SET WhereClause='

exists (
	select 1 from C_Invoice i
	join C_Invoice i2 on i2.Ref_Invoice_ID = i.C_Invoice_ID
	
	where i2.C_Invoice_ID = @C_Invoice_ID/-1@  and C_Invoice.C_Invoice_ID = i.C_Invoice_ID
and i.IsSOTrx = ''Y'' and i2.IsSOTrx = ''Y''
)',Updated=TO_TIMESTAMP('2017-08-30 14:48:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540741
;

-- 2017-08-30T14:49:14.658
-- URL zum Konzept
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540744,TO_TIMESTAMP('2017-08-30 14:49:14','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.invoice','Y','N','C_Invoice SourcePO',TO_TIMESTAMP('2017-08-30 14:49:14','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2017-08-30T14:49:14.662
-- URL zum Konzept
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540744 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2017-08-30T14:49:47.196
-- URL zum Konzept
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy,WhereClause) VALUES (0,3484,0,540744,318,183,TO_TIMESTAMP('2017-08-30 14:49:47','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.invoice','Y','N',TO_TIMESTAMP('2017-08-30 14:49:47','YYYY-MM-DD HH24:MI:SS'),100,'IsSOTrx = ''N''')
;

-- 2017-08-30T14:50:22.252
-- URL zum Konzept
UPDATE AD_Ref_Table SET WhereClause='

exists (
	select 1 from C_Invoice i
	join C_Invoice i2 on i2.Ref_Invoice_ID = i.C_Invoice_ID
	
	where i2.C_Invoice_ID = @C_Invoice_ID/-1@  and C_Invoice.C_Invoice_ID = i.C_Invoice_ID

)',Updated=TO_TIMESTAMP('2017-08-30 14:50:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540741
;

-- 2017-08-30T14:57:33.651
-- URL zum Konzept
UPDATE AD_RelationType SET AD_Reference_Source_ID=540744,Updated=TO_TIMESTAMP('2017-08-30 14:57:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540186
;

-- 2017-08-30T14:57:46.850
-- URL zum Konzept
UPDATE AD_RelationType SET AD_Reference_Source_ID=540744,Updated=TO_TIMESTAMP('2017-08-30 14:57:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540187
;

-- 2021-11-10T08:42:25.539Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='C_Invoice.IsSOTrx = ''N''
  AND C_Invoice.isActive = ''Y''
  AND C_Invoice.C_DocType_ID IN (SELECT C_DocType_ID FROM C_DocType WHERE DocBaseType = ''API'' AND DocSubType IS NULL)
  AND AD_Org_ID =@AD_Org_ID/-1@
  AND NOT EXISTS(SELECT *
                 FROM C_Invoice_Relation rel
                 WHERE C_Invoice.C_Invoice_ID = rel.C_Invoice_From_ID
                   AND rel.C_Invoice_To_ID = @C_Invoice_ID/-1@
                   AND rel.C_Invoice_Relation_Type = ''POtoSO'')',Updated=TO_TIMESTAMP('2021-11-10 10:42:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540565
;

-- 2021-11-11T07:15:07.647Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET Name='C_Invoice (PO) -> C_Invoice (SO) via C_Invoice_Relation',Updated=TO_TIMESTAMP('2021-11-11 09:15:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540324
;

-- 2021-11-11T07:19:04.850Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='EXISTS           (               SELECT 1               from C_Invoice_Relation ir                        JOIN C_Invoice poInv on ir.c_invoice_from_id = poInv.c_invoice_id                 AND poInv.c_invoice_id = @C_Invoice_ID/-1@                 AND ir.c_invoice_from_id=c_invoice.c_invoice_id           )',Updated=TO_TIMESTAMP('2021-11-11 09:19:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541481
;

-- 2021-11-11T07:19:13.138Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET AD_Display=NULL,Updated=TO_TIMESTAMP('2021-11-11 09:19:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541481
;

-- 2021-11-11T07:19:52.960Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Source_ID,AD_Reference_Target_ID,AD_RelationType_ID,Created,CreatedBy,Description,EntityType,IsActive,IsTableRecordIdTarget,Name,Role_Source,Role_Target,Updated,UpdatedBy) VALUES (0,0,336,541481,540332,TO_TIMESTAMP('2021-11-11 09:19:52','YYYY-MM-DD HH24:MI:SS'),100,'Reverse lookup via C_Invoice_Relation(PurchaseToSales)','D','Y','N','C_Invoice (SO) -> C_Invoice (PO) via C_Invoice_Relation','Invoice','Invoice',TO_TIMESTAMP('2021-11-11 09:19:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-11-11T07:26:24.640Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='EXISTS           (               SELECT 1               from C_Invoice_Relation ir                        JOIN C_Invoice poInv on ir.c_invoice_from_id = poInv.c_invoice_id                 AND poInv.c_invoice_id = @C_Invoice_ID/-1@                 AND ir.c_invoice_to_id=c_invoice.c_invoice_id           )',Updated=TO_TIMESTAMP('2021-11-11 09:26:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541481
;

-- 2021-11-11T07:29:47.255Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Reference SET Description='From -> To',Updated=TO_TIMESTAMP('2021-11-11 09:29:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541481
;

-- 2021-11-11T07:30:09.618Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,Description,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541496,TO_TIMESTAMP('2021-11-11 09:30:09','YYYY-MM-DD HH24:MI:SS'),100,'From <- To','D','Y','N','C_Invoice via C_Invoice_Relation(reverse lookup)',TO_TIMESTAMP('2021-11-11 09:30:09','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2021-11-11T07:30:09.619Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541496 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2021-11-11T07:33:29.757Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy,WhereClause) VALUES (0,3484,0,541496,318,TO_TIMESTAMP('2021-11-11 09:33:29','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N',TO_TIMESTAMP('2021-11-11 09:33:29','YYYY-MM-DD HH24:MI:SS'),100,'EXISTS(SELECT 1 from C_Invoice_Relation ir JOIN C_Invoice soInv on ir.c_invoice_from_id @C_Invoice_ID/-1@ AND ir.c_invoice_to_id = c_invoice.c_invoice_id)')
;

-- 2021-11-11T07:34:46.286Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='EXISTS(SELECT 1 from C_Invoice_Relation ir where ir.c_invoice_from_id @ C_Invoice_ID / -1@ AND ir.c_invoice_to_id = c_invoice.c_invoice_id)',Updated=TO_TIMESTAMP('2021-11-11 09:34:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541496
;

-- 2021-11-11T07:36:18.269Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='EXISTS(SELECT 1 from C_Invoice_Relation ir where ir.c_invoice_from_id = @C_Invoice_ID/-1@ AND ir.c_invoice_to_id = c_invoice.c_invoice_id)',Updated=TO_TIMESTAMP('2021-11-11 09:36:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541481
;

-- 2021-11-11T07:36:55.352Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Reference SET Description='C_Invoice_To_ID - > C_Invoice_From_ID',Updated=TO_TIMESTAMP('2021-11-11 09:36:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541496
;

-- 2021-11-11T07:37:08.269Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Reference SET Description='C_Invoice_To_ID - > C_Invoice_From_ID (reverse lookup)',Updated=TO_TIMESTAMP('2021-11-11 09:37:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541496
;

-- 2021-11-11T07:37:29.908Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='EXISTS(SELECT 1 from C_Invoice_Relation ir where ir.c_invoice_to_id = @C_Invoice_ID/-1@ AND ir.c_invoice_from_id = c_invoice.c_invoice_id)',Updated=TO_TIMESTAMP('2021-11-11 09:37:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541496
;

-- 2021-11-11T07:38:02.831Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Reference SET Description='C_Invoice_From_ID -> C_Invoice_To_ID',Updated=TO_TIMESTAMP('2021-11-11 09:38:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541481
;

-- 2021-11-11T07:42:43.726Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Target_ID=541496,Updated=TO_TIMESTAMP('2021-11-11 09:42:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540332
;

-- 2021-11-11T07:54:02.512Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET Role_Source='', Role_Target='',Updated=TO_TIMESTAMP('2021-11-11 09:54:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540332
;

-- 2021-11-11T10:31:11.416Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET AD_Window_ID=183,Updated=TO_TIMESTAMP('2021-11-11 12:31:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541496
;

-- 2021-11-11T11:39:52.544Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET DefaultValue='@SQL=SELECT c_invoice_from_id FROM c_invoice_relation WHERE c_invoice_to_id=@C_Invoice_ID/-1@ AND c_invoice_relation_type=''POtoSO''',Updated=TO_TIMESTAMP('2021-11-11 13:39:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542137
;


--TRLs
-- 2021-11-12T12:21:21.927Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='', Name='Rechnung', PrintName='Rechnung',Updated=TO_TIMESTAMP('2021-11-12 14:21:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580170 AND AD_Language='de_CH'
;

-- 2021-11-12T12:21:21.956Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580170,'de_CH')
;

-- 2021-11-12T12:21:31.416Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='', Name='Rechnung', PrintName='Rechnung',Updated=TO_TIMESTAMP('2021-11-12 14:21:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580170 AND AD_Language='de_DE'
;

-- 2021-11-12T12:21:31.417Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580170,'de_DE')
;

-- 2021-11-12T12:21:31.424Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(580170,'de_DE')
;

-- 2021-11-12T12:21:31.427Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='C_Invoice_To_ID', Name='Rechnung', Description='', Help=NULL WHERE AD_Element_ID=580170
;

-- 2021-11-12T12:21:31.428Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_Invoice_To_ID', Name='Rechnung', Description='', Help=NULL, AD_Element_ID=580170 WHERE UPPER(ColumnName)='C_INVOICE_TO_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-11-12T12:21:31.429Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_Invoice_To_ID', Name='Rechnung', Description='', Help=NULL WHERE AD_Element_ID=580170 AND IsCentrallyMaintained='Y'
;

-- 2021-11-12T12:21:31.430Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Rechnung', Description='', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=580170) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 580170)
;

-- 2021-11-12T12:21:31.437Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Rechnung', Name='Rechnung' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=580170)
;

-- 2021-11-12T12:21:31.438Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Rechnung', Description='', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 580170
;

-- 2021-11-12T12:21:31.440Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Rechnung', Description='', Help=NULL WHERE AD_Element_ID = 580170
;

-- 2021-11-12T12:21:31.441Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Rechnung', Description = '', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 580170
;

-- 2021-11-12T12:21:41.198Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='', Name='Rechnung', PrintName='Rechnung',Updated=TO_TIMESTAMP('2021-11-12 14:21:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580170 AND AD_Language='nl_NL'
;

-- 2021-11-12T12:21:41.199Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580170,'nl_NL')
;

-- 2021-11-12T12:21:50.582Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='', Name='Invoice', PrintName='Invoice',Updated=TO_TIMESTAMP('2021-11-12 14:21:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580170 AND AD_Language='en_US'
;

-- 2021-11-12T12:21:50.583Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580170,'en_US')
;

-- 2021-11-12T12:42:06.048Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Anzahl Debitoren Rechnungen', PrintName='Anzahl Debitoren Rechnungen',Updated=TO_TIMESTAMP('2021-11-12 14:42:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580172 AND AD_Language='de_CH'
;

-- 2021-11-12T12:42:06.049Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580172,'de_CH')
;

-- 2021-11-12T12:42:08.317Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Anzahl Debitoren Rechnungen', PrintName='Anzahl Debitoren Rechnungen',Updated=TO_TIMESTAMP('2021-11-12 14:42:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580172 AND AD_Language='de_DE'
;

-- 2021-11-12T12:42:08.319Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580172,'de_DE')
;

-- 2021-11-12T12:42:08.332Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(580172,'de_DE')
;

-- 2021-11-12T12:42:08.333Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='Sales_Invoice_Count', Name='Sales invoice count', Description='Anzahl Debitoren Rechnungen', Help=NULL WHERE AD_Element_ID=580172
;

-- 2021-11-12T12:42:08.334Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Sales_Invoice_Count', Name='Sales invoice count', Description='Anzahl Debitoren Rechnungen', Help=NULL, AD_Element_ID=580172 WHERE UPPER(ColumnName)='SALES_INVOICE_COUNT' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-11-12T12:42:08.335Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Sales_Invoice_Count', Name='Sales invoice count', Description='Anzahl Debitoren Rechnungen', Help=NULL WHERE AD_Element_ID=580172 AND IsCentrallyMaintained='Y'
;

-- 2021-11-12T12:42:08.335Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Sales invoice count', Description='Anzahl Debitoren Rechnungen', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=580172) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 580172)
;

-- 2021-11-12T12:42:08.343Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Anzahl Debitoren Rechnungen', Name='Sales invoice count' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=580172)
;

-- 2021-11-12T12:42:08.344Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Sales invoice count', Description='Anzahl Debitoren Rechnungen', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 580172
;

-- 2021-11-12T12:42:08.345Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Sales invoice count', Description='Anzahl Debitoren Rechnungen', Help=NULL WHERE AD_Element_ID = 580172
;

-- 2021-11-12T12:42:08.346Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Sales invoice count', Description = 'Anzahl Debitoren Rechnungen', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 580172
;

-- 2021-11-12T12:42:33.261Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Anzahl zugeordneter debitoren Rechnungen', Name='Anzahl zugeordneter debitoren Rechnungen', PrintName='Anzahl zugeordneter debitoren Rechnungen',Updated=TO_TIMESTAMP('2021-11-12 14:42:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580172 AND AD_Language='nl_NL'
;

-- 2021-11-12T12:42:33.262Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580172,'nl_NL')
;

-- 2021-11-12T12:42:34.039Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Anzahl zugeordneter debitoren Rechnungen',Updated=TO_TIMESTAMP('2021-11-12 14:42:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580172 AND AD_Language='de_DE'
;

-- 2021-11-12T12:42:34.040Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580172,'de_DE')
;

-- 2021-11-12T12:42:34.052Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(580172,'de_DE')
;

-- 2021-11-12T12:42:34.053Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='Sales_Invoice_Count', Name='Anzahl zugeordneter debitoren Rechnungen', Description='Anzahl Debitoren Rechnungen', Help=NULL WHERE AD_Element_ID=580172
;

-- 2021-11-12T12:42:34.054Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Sales_Invoice_Count', Name='Anzahl zugeordneter debitoren Rechnungen', Description='Anzahl Debitoren Rechnungen', Help=NULL, AD_Element_ID=580172 WHERE UPPER(ColumnName)='SALES_INVOICE_COUNT' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-11-12T12:42:34.055Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Sales_Invoice_Count', Name='Anzahl zugeordneter debitoren Rechnungen', Description='Anzahl Debitoren Rechnungen', Help=NULL WHERE AD_Element_ID=580172 AND IsCentrallyMaintained='Y'
;

-- 2021-11-12T12:42:34.056Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Anzahl zugeordneter debitoren Rechnungen', Description='Anzahl Debitoren Rechnungen', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=580172) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 580172)
;

-- 2021-11-12T12:42:34.063Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Anzahl Debitoren Rechnungen', Name='Anzahl zugeordneter debitoren Rechnungen' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=580172)
;

-- 2021-11-12T12:42:34.064Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Anzahl zugeordneter debitoren Rechnungen', Description='Anzahl Debitoren Rechnungen', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 580172
;

-- 2021-11-12T12:42:34.065Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Anzahl zugeordneter debitoren Rechnungen', Description='Anzahl Debitoren Rechnungen', Help=NULL WHERE AD_Element_ID = 580172
;

-- 2021-11-12T12:42:34.066Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Anzahl zugeordneter debitoren Rechnungen', Description = 'Anzahl Debitoren Rechnungen', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 580172
;

-- 2021-11-12T12:43:17.666Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Anzahl zugeordneter debitoren Rechnungen', Name='Anzahl Debitoren Rechnungen ',Updated=TO_TIMESTAMP('2021-11-12 14:43:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580172 AND AD_Language='de_CH'
;

-- 2021-11-12T12:43:17.667Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580172,'de_CH')
;

-- 2021-11-12T12:43:20.925Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Anzahl Debitoren Rechnungen',Updated=TO_TIMESTAMP('2021-11-12 14:43:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580172 AND AD_Language='de_DE'
;

-- 2021-11-12T12:43:20.926Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580172,'de_DE')
;

-- 2021-11-12T12:43:20.931Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(580172,'de_DE')
;

-- 2021-11-12T12:43:20.932Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='Sales_Invoice_Count', Name='Anzahl Debitoren Rechnungen', Description='Anzahl Debitoren Rechnungen', Help=NULL WHERE AD_Element_ID=580172
;

-- 2021-11-12T12:43:20.932Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Sales_Invoice_Count', Name='Anzahl Debitoren Rechnungen', Description='Anzahl Debitoren Rechnungen', Help=NULL, AD_Element_ID=580172 WHERE UPPER(ColumnName)='SALES_INVOICE_COUNT' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-11-12T12:43:20.933Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Sales_Invoice_Count', Name='Anzahl Debitoren Rechnungen', Description='Anzahl Debitoren Rechnungen', Help=NULL WHERE AD_Element_ID=580172 AND IsCentrallyMaintained='Y'
;

-- 2021-11-12T12:43:20.934Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Anzahl Debitoren Rechnungen', Description='Anzahl Debitoren Rechnungen', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=580172) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 580172)
;

-- 2021-11-12T12:43:20.941Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Anzahl Debitoren Rechnungen', Name='Anzahl Debitoren Rechnungen' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=580172)
;

-- 2021-11-12T12:43:20.942Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Anzahl Debitoren Rechnungen', Description='Anzahl Debitoren Rechnungen', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 580172
;

-- 2021-11-12T12:43:20.943Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Anzahl Debitoren Rechnungen', Description='Anzahl Debitoren Rechnungen', Help=NULL WHERE AD_Element_ID = 580172
;

-- 2021-11-12T12:43:20.944Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Anzahl Debitoren Rechnungen', Description = 'Anzahl Debitoren Rechnungen', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 580172
;

-- 2021-11-12T12:43:25.219Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Anzahl Debitoren Rechnungen',Updated=TO_TIMESTAMP('2021-11-12 14:43:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580172 AND AD_Language='de_CH'
;

-- 2021-11-12T12:43:25.220Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580172,'de_CH')
;

-- 2021-11-12T12:43:40.864Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Anzahl Debitoren Rechnungen ', PrintName='Anzahl Debitoren Rechnungen ',Updated=TO_TIMESTAMP('2021-11-12 14:43:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580172 AND AD_Language='nl_NL'
;

-- 2021-11-12T12:43:40.865Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580172,'nl_NL')
;

-- 2021-11-12T12:43:50.542Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Anzahl zugeordneter debitoren Rechnungen',Updated=TO_TIMESTAMP('2021-11-12 14:43:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580172 AND AD_Language='de_DE'
;

-- 2021-11-12T12:43:50.543Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580172,'de_DE')
;

-- 2021-11-12T12:43:50.554Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(580172,'de_DE')
;

-- 2021-11-12T12:43:50.555Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='Sales_Invoice_Count', Name='Anzahl Debitoren Rechnungen', Description='Anzahl zugeordneter debitoren Rechnungen', Help=NULL WHERE AD_Element_ID=580172
;

-- 2021-11-12T12:43:50.556Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Sales_Invoice_Count', Name='Anzahl Debitoren Rechnungen', Description='Anzahl zugeordneter debitoren Rechnungen', Help=NULL, AD_Element_ID=580172 WHERE UPPER(ColumnName)='SALES_INVOICE_COUNT' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-11-12T12:43:50.556Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Sales_Invoice_Count', Name='Anzahl Debitoren Rechnungen', Description='Anzahl zugeordneter debitoren Rechnungen', Help=NULL WHERE AD_Element_ID=580172 AND IsCentrallyMaintained='Y'
;

-- 2021-11-12T12:43:50.557Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Anzahl Debitoren Rechnungen', Description='Anzahl zugeordneter debitoren Rechnungen', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=580172) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 580172)
;

-- 2021-11-12T12:43:50.564Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Anzahl Debitoren Rechnungen', Description='Anzahl zugeordneter debitoren Rechnungen', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 580172
;

-- 2021-11-12T12:43:50.565Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Anzahl Debitoren Rechnungen', Description='Anzahl zugeordneter debitoren Rechnungen', Help=NULL WHERE AD_Element_ID = 580172
;

-- 2021-11-12T12:43:50.566Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Anzahl Debitoren Rechnungen', Description = 'Anzahl zugeordneter debitoren Rechnungen', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 580172
;

-- 2021-11-12T12:44:11.543Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Number of associated debtor invoices', Name='Debtor invoice count', PrintName='Debtor invoice count',Updated=TO_TIMESTAMP('2021-11-12 14:44:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580172 AND AD_Language='en_US'
;

-- 2021-11-12T12:44:11.544Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580172,'en_US')
;

-- 2021-11-12T12:46:19.982Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Description='', Name='Assign debtor invoice',Updated=TO_TIMESTAMP('2021-11-12 14:46:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=584937
;

-- 2021-11-12T12:46:29.135Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Description='', Help=NULL, Name='Debitoren Rechnung zuordnen',Updated=TO_TIMESTAMP('2021-11-12 14:46:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584937
;

-- 2021-11-12T12:46:29.099Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Description='', Name='Debitoren Rechnung zuordnen',Updated=TO_TIMESTAMP('2021-11-12 14:46:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=584937
;

-- 2021-11-12T12:46:32.849Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Description='', Name='Debitoren Rechnung zuordnen',Updated=TO_TIMESTAMP('2021-11-12 14:46:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=584937
;

-- 2021-11-12T12:47:48.559Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Description='', Name='Debitoren Rechnung Zuordnen aufheben',Updated=TO_TIMESTAMP('2021-11-12 14:47:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=584938
;

-- 2021-11-12T12:47:52.914Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Description='', Help=NULL, Name='Debitoren Rechnung Zuordnen aufheben',Updated=TO_TIMESTAMP('2021-11-12 14:47:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584938
;

-- 2021-11-12T12:47:52.911Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Description='', Name='Debitoren Rechnung Zuordnen aufheben',Updated=TO_TIMESTAMP('2021-11-12 14:47:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=584938
;

-- 2021-11-12T12:48:11.134Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Description='',Updated=TO_TIMESTAMP('2021-11-12 14:48:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=584938
;

-- 2021-11-12T12:48:13.555Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Description='',Updated=TO_TIMESTAMP('2021-11-12 14:48:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Process_ID=584938
;
