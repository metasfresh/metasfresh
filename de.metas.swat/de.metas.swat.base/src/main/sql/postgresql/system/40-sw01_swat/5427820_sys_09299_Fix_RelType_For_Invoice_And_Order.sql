-- 22.09.2015 11:33
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540575,TO_TIMESTAMP('2015-09-22 11:33:08','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','N','C_Invoice PO Drafts Included',TO_TIMESTAMP('2015-09-22 11:33:08','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 22.09.2015 11:33
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540575 AND NOT EXISTS (SELECT * FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 22.09.2015 11:34
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Display,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy,WhereClause) VALUES (0,3492,3484,0,540575,318,TO_TIMESTAMP('2015-09-22 11:34:07','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','N',TO_TIMESTAMP('2015-09-22 11:34:07','YYYY-MM-DD HH24:MI:SS'),100,'


C_Invoice.IsSOTrx=''N'' 
AND
EXISTS
(
	SELECT 1
	FROM C_Order o
	JOIN C_OrderLine ol on o.C_Order_ID = ol.C_Order_ID
	JOIN C_InvoiceLine il on ol.C_OrderLine_ID = il.C_OrderLine_ID

	WHERE 
	(C_Invoice.C_Invoice_ID = @C_Invoice_ID/-1@
	OR
	o.C_Order_ID = @C_Order_ID/-1@
	)
		AND C_Invoice.IsSOTrx = o.IsSOTrx
		AND C_Invoice.IsSOTrx = ''N''
		AND il.C_Invoice_ID = C_Invoice.C_Invoice_ID
)
')
;

-- 22.09.2015 11:34
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Source_ID=540575,Updated=TO_TIMESTAMP('2015-09-22 11:34:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540125
;

-- 22.09.2015 11:35
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='

EXISTS
(
	SELECT 1
	FROM C_Invoice i
	JOIN C_InvoiceLine il on i.C_Invoice_ID = il.C_Invoice_ID
	JOIN C_OrderLine ol on il.C_OrderLine_ID = ol.C_OrderLine_ID

	WHERE 
		
		C_Order.C_Order_ID = ol.C_Order_ID
		AND 
		(
		i.C_Invoice_ID = @C_Invoice_ID/-1@
		OR
		C_Order.C_Order_ID = @C_Order_ID/-1@
		)
		AND i.IsSOTrx = C_Order.IsSOTrx
		AND i.IsSOTrx = ''N''
	
)',Updated=TO_TIMESTAMP('2015-09-22 11:35:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540568
;

-- 22.09.2015 11:37
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,Description,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540576,TO_TIMESTAMP('2015-09-22 11:37:08','YYYY-MM-DD HH24:MI:SS'),100,'invalid condition as workaround for the relation to work','de.metas.swat','Y','N','C_Order PO DRAFT INCLUDED',TO_TIMESTAMP('2015-09-22 11:37:08','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 22.09.2015 11:37
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540576 AND NOT EXISTS (SELECT * FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 22.09.2015 11:37
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET Description=NULL,Updated=TO_TIMESTAMP('2015-09-22 11:37:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540122
;

-- 22.09.2015 11:38
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Display,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy,WhereClause) VALUES (0,2169,2161,0,540576,259,TO_TIMESTAMP('2015-09-22 11:38:05','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','N',TO_TIMESTAMP('2015-09-22 11:38:05','YYYY-MM-DD HH24:MI:SS'),100,' 1=2')
;

-- 22.09.2015 11:38
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='ISSOTRX = ''N''',Updated=TO_TIMESTAMP('2015-09-22 11:38:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540566
;

-- 22.09.2015 11:46
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Source_ID=540576,Updated=TO_TIMESTAMP('2015-09-22 11:46:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540126
;

-- 22.09.2015 11:56
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET Role_Source=NULL, Role_Target=NULL,Updated=TO_TIMESTAMP('2015-09-22 11:56:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540122
;

-- 22.09.2015 12:16
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET IsDirected='Y',Updated=TO_TIMESTAMP('2015-09-22 12:16:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540126
;

-- 22.09.2015 12:17
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET IsActive='N',Updated=TO_TIMESTAMP('2015-09-22 12:17:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540122
;

-- 22.09.2015 12:17
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET IsActive='N',Updated=TO_TIMESTAMP('2015-09-22 12:17:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540126
;

-- 22.09.2015 12:17
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET IsActive='Y',Updated=TO_TIMESTAMP('2015-09-22 12:17:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540126
;

-- 22.09.2015 12:17
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET IsActive='Y',Updated=TO_TIMESTAMP('2015-09-22 12:17:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540122
;

-- 22.09.2015 12:18
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET IsActive='N',Updated=TO_TIMESTAMP('2015-09-22 12:18:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540126
;

-- 22.09.2015 12:19
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Preference WHERE AD_Client_ID=0 AND AD_Org_ID=0 AND AD_User_ID=100 AND AD_Window_ID=53102 AND Attribute='Name'
;

-- 22.09.2015 12:19
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Preference (AD_Preference_ID, AD_Client_ID, AD_Org_ID, IsActive, Created,CreatedBy,Updated,UpdatedBy,AD_Window_ID, AD_User_ID, Attribute, Value) VALUES (540064,0,0, 'Y',now(),100,now(),100, 53102,100,'Name','C_Invoice -> Order (SO)')
;

-- 22.09.2015 12:20
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='ISSOTRX = @ISSOTRX@',Updated=TO_TIMESTAMP('2015-09-22 12:20:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540565
;

-- 22.09.2015 12:20
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET IsActive='Y',Updated=TO_TIMESTAMP('2015-09-22 12:20:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540126
;

-- 22.09.2015 12:20
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='ISSOTRX = @ISSOTRX@',Updated=TO_TIMESTAMP('2015-09-22 12:20:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540566
;

-- 22.09.2015 13:09
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='IsSOTrx=@IsSOTrx@',Updated=TO_TIMESTAMP('2015-09-22 13:09:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540250
;

-- 22.09.2015 13:10
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='ISSOTRX =C_Order.IsSoTrx',Updated=TO_TIMESTAMP('2015-09-22 13:10:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540566
;

-- 22.09.2015 13:11
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='ISSOTRX =C_Order.issotrx',Updated=TO_TIMESTAMP('2015-09-22 13:11:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540565
;

-- 22.09.2015 13:11
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='c_invoice.ISSOTRX =C_Order.issotrx',Updated=TO_TIMESTAMP('2015-09-22 13:11:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540565
;

-- 22.09.2015 13:11
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='c_invoice.ISSOTRX =C_Order.IsSoTrx',Updated=TO_TIMESTAMP('2015-09-22 13:11:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540566
;

-- 22.09.2015 13:12
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='C_order.issotrx = c_invoice.issotrx',Updated=TO_TIMESTAMP('2015-09-22 13:12:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540250
;

-- 22.09.2015 13:13
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='C_order.issotrx =@IsSoTrx@',Updated=TO_TIMESTAMP('2015-09-22 13:13:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540250
;

-- 22.09.2015 13:13
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='c_invoice.ISSOTRX =@IsSoTrx@',Updated=TO_TIMESTAMP('2015-09-22 13:13:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540565
;

-- 22.09.2015 13:13
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='c_invoice.ISSOTRX =@IsSoTrx@',Updated=TO_TIMESTAMP('2015-09-22 13:13:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540566
;

-- 22.09.2015 13:17
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET AD_Window_ID=143,Updated=TO_TIMESTAMP('2015-09-22 13:17:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540570
;

-- 22.09.2015 13:18
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET AD_Window_ID=167,Updated=TO_TIMESTAMP('2015-09-22 13:18:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540571
;

-- 22.09.2015 13:18
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET AD_Window_ID=183,Updated=TO_TIMESTAMP('2015-09-22 13:18:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540575
;

-- 22.09.2015 13:18
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET AD_Window_ID=181,Updated=TO_TIMESTAMP('2015-09-22 13:18:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540576
;

