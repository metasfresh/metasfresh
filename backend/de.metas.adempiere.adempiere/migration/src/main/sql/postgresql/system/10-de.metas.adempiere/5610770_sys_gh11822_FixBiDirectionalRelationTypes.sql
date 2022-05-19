--C_FlatRate_Term<->C_Invoice_Candidate
-- 2021-10-27T07:59:14.041Z
-- URL zum Konzept
UPDATE AD_RelationType SET IsActive='Y',Updated=TO_TIMESTAMP('2021-10-27 09:59:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540190
;

--C_InvoiceCandidate -> C_Flatrate_Term
-- 2021-10-27T08:01:31.351Z
-- URL zum Konzept
UPDATE AD_RelationType SET IsActive='Y',Updated=TO_TIMESTAMP('2021-10-27 10:01:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540194
;

--C_InvoiceCandidate -> Invoice (SO ONLY)
-- 2021-10-27T08:03:26.846Z
-- URL zum Konzept
UPDATE AD_RelationType SET IsActive='Y',Updated=TO_TIMESTAMP('2021-10-27 10:03:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540116
;

--C_InvoiceCandidate -> Invoice (PO ONLY)
-- 2021-10-27T08:04:24.950Z
-- URL zum Konzept
UPDATE AD_RelationType SET IsActive='Y',Updated=TO_TIMESTAMP('2021-10-27 10:04:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540120
;

--C_Order(SO) -> C_Order(Quotation)
-- 2021-10-27T08:06:29.748Z
-- URL zum Konzept
UPDATE AD_RelationType SET IsActive='Y',Updated=TO_TIMESTAMP('2021-10-27 10:06:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540261
;

--C_Order(Quotation) -> C_Order(SO)
-- 2021-10-27T08:07:29.622Z
-- URL zum Konzept
UPDATE AD_RelationType SET IsActive='Y',Updated=TO_TIMESTAMP('2021-10-27 10:07:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540260
;

-- 2021-10-20T10:50:20.704Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='exists ( select 1 from C_Order o where o.C_DocType_ID=1000027 and C_Order.C_Order_ID = o.C_Order_ID and @C_BPartner_ID/-1@=o.C_BPartner_ID)',Updated=TO_TIMESTAMP('2021-10-20 13:50:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541184
;

--M_InOut_ShipmentSchedule
-- 2021-10-27T08:51:55.971Z
-- URL zum Konzept
UPDATE AD_RelationType SET IsActive='Y',Updated=TO_TIMESTAMP('2021-10-27 10:51:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540118
;


--M_ShipmentSchedule -> Shipment
-- 2021-10-27T08:50:42.636Z
-- URL zum Konzept
UPDATE AD_RelationType SET IsActive='Y',Updated=TO_TIMESTAMP('2021-10-27 10:50:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540115
;

--M_InventoryLine -> C_Order (PO)
-- 2021-10-27T08:49:17.578Z
-- URL zum Konzept
UPDATE AD_RelationType SET IsActive='Y',Updated=TO_TIMESTAMP('2021-10-27 10:49:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540175
;

--C_Order (PO) -> M_Inventory_Line
-- 2021-10-27T08:47:38.832Z
-- URL zum Konzept
UPDATE AD_RelationType SET IsActive='Y',Updated=TO_TIMESTAMP('2021-10-27 10:47:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540174
;

-- 2021-10-11T07:16:28.388Z
-- URL zum Konzept
UPDATE AD_Ref_Table SET AD_Window_ID=540205,Updated=TO_TIMESTAMP('2021-10-11 09:16:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540711
;

-- C_Order(SO) -> C_Project
-- 2021-10-27T08:09:22.031Z
-- URL zum Konzept
UPDATE AD_RelationType SET IsActive='Y',Updated=TO_TIMESTAMP('2021-10-27 10:09:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540254
;

-- C_Project -> Order (SO)
-- 2021-10-27T08:11:17.541Z
-- URL zum Konzept
DELETE FROM AD_RelationType WHERE AD_RelationType_ID=540255
;

--M_InOut (SO) -> C_Project
-- 2021-10-27T08:12:11.345Z
-- URL zum Konzept
UPDATE AD_RelationType SET IsActive='Y',Updated=TO_TIMESTAMP('2021-10-27 10:12:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540253
;

--C_Project -> M_InOut(SO)
-- 2021-10-27T08:24:16.826Z
-- URL zum Konzept
UPDATE AD_RelationType SET IsActive='Y',Updated=TO_TIMESTAMP('2021-10-27 10:24:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540256
;

--C_Invoice(SO) -> C_Project
-- 2021-10-27T08:13:20.830Z
-- URL zum Konzept
UPDATE AD_RelationType SET IsActive='Y',Updated=TO_TIMESTAMP('2021-10-27 10:13:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540252
;

--C_Project -> C_Invoice(SO)
-- 2021-10-27T08:14:35.852Z
-- URL zum Konzept
UPDATE AD_RelationType SET IsActive='Y',Updated=TO_TIMESTAMP('2021-10-27 10:14:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540257
;

--C_Order (PO) -> C_Project
-- 2021-10-27T08:16:23.787Z
-- URL zum Konzept
UPDATE AD_RelationType SET IsActive='Y',Updated=TO_TIMESTAMP('2021-10-27 10:16:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540267
;

delete from ad_relationtype where name ILIKE '%C_Project -> Order (SO)%'
;

--M_InOut (PO) -> C_Project
-- 2021-10-27T08:25:32.662Z
-- URL zum Konzept
UPDATE AD_RelationType SET IsActive='Y',Updated=TO_TIMESTAMP('2021-10-27 10:25:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540268
;

--C_Project -> M_InOut (PO)
-- 2021-10-27T08:26:40.656Z
-- URL zum Konzept
UPDATE AD_RelationType SET IsActive='Y',Updated=TO_TIMESTAMP('2021-10-27 10:26:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540271
;

--C_Invoice (PO) -> C_Project
-- 2021-10-27T08:27:34.126Z
-- URL zum Konzept
UPDATE AD_RelationType SET IsActive='Y',Updated=TO_TIMESTAMP('2021-10-27 10:27:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540269
;

--C_Project -> C_Invoice (PO)
-- 2021-10-27T08:28:34.798Z
-- URL zum Konzept
UPDATE AD_RelationType SET IsActive='Y',Updated=TO_TIMESTAMP('2021-10-27 10:28:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540272
;

--Fact_Acct -> C_Project
-- 2021-10-27T08:30:57.422Z
-- URL zum Konzept
UPDATE AD_RelationType SET IsActive='Y',Updated=TO_TIMESTAMP('2021-10-27 10:30:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540258
;

--C_Project -> Fact_Acct
-- 2021-10-27T08:29:54.651Z
-- URL zum Konzept
UPDATE AD_RelationType SET IsActive='Y',Updated=TO_TIMESTAMP('2021-10-27 10:29:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540259
;

delete from ad_relationtype where name ILIKE  '%C_Order (SO) -> C_Order (PO)%'
;

delete from ad_relationtype where name ILIKE  '%C_Order (PO) -> C_Order (SO)%'
;

--create new reltype C_Element_Value -> C_Element
-- 2021-10-20T05:51:27.719Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541425,TO_TIMESTAMP('2021-10-20 08:51:27','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','C_Element_Value_Source',TO_TIMESTAMP('2021-10-20 08:51:27','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2021-10-20T05:51:27.728Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541425 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2021-10-20T05:54:07.355Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy,WhereClause) VALUES (0,1137,0,541425,188,540761,TO_TIMESTAMP('2021-10-20 08:54:07','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N',TO_TIMESTAMP('2021-10-20 08:54:07','YYYY-MM-DD HH24:MI:SS'),100,'C_Element_ID = @C_Element_ID@')
;

-- 2021-10-20T05:54:54.646Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541426,TO_TIMESTAMP('2021-10-20 08:54:54','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','C_Element_Target',TO_TIMESTAMP('2021-10-20 08:54:54','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2021-10-20T05:54:54.650Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541426 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2021-10-20T05:57:06.628Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy,WhereClause) VALUES (0,464,0,541426,142,118,TO_TIMESTAMP('2021-10-20 08:57:06','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N',TO_TIMESTAMP('2021-10-20 08:57:06','YYYY-MM-DD HH24:MI:SS'),100,'C_Element.C_Element_ID = @C_Element_ID@')
;

-- 2021-10-20T05:58:47.785Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='(EXISTS (SELECT 1 from C_Element_Value ev where ev.C_Element_ID = @C_Element_ID@))',Updated=TO_TIMESTAMP('2021-10-20 08:58:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541426
;

-- 2021-10-20T05:59:47.787Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='(EXISTS (SELECT 1 from C_Elementvalue ev where ev.C_Element_ID = @C_Element_ID@))',Updated=TO_TIMESTAMP('2021-10-20 08:59:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541426
;

-- 2021-10-20T06:01:26.098Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Source_ID,AD_Reference_Target_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsTableRecordIdTarget,Name,Updated,UpdatedBy) VALUES (0,0,541425,541426,540301,TO_TIMESTAMP('2021-10-20 09:01:25','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','C_ElementValue -> C_Element',TO_TIMESTAMP('2021-10-20 09:01:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-10-20T06:02:46.093Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='',Updated=TO_TIMESTAMP('2021-10-20 09:02:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541425
;

-- 2021-10-20T06:04:07.939Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='C_Element.C_Element_ID=@C_Element_ID@',Updated=TO_TIMESTAMP('2021-10-20 09:04:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541426
;

-- 2021-10-20T06:07:59.886Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Source_ID=541426, AD_Reference_Target_ID=541425,Updated=TO_TIMESTAMP('2021-10-20 09:07:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540301
;

-- 2021-10-20T06:11:12.069Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET AD_Window_ID=540761,Updated=TO_TIMESTAMP('2021-10-20 09:11:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541426
;

-- 2021-10-20T06:11:37.398Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET AD_Window_ID=118,Updated=TO_TIMESTAMP('2021-10-20 09:11:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541425
;

-- 2021-10-20T06:12:44.067Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET AD_Window_ID=540761,Updated=TO_TIMESTAMP('2021-10-20 09:12:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541425
;

-- 2021-10-20T06:13:33.918Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='C_ElementValue.C_Element_ID = @C_Element_ID@',Updated=TO_TIMESTAMP('2021-10-20 09:13:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541425
;

-- 2021-10-20T06:14:21.359Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET AD_Window_ID=NULL,Updated=TO_TIMESTAMP('2021-10-20 09:14:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541426
;

-- 2021-10-20T06:16:02.169Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='C_Element_ID = @C_Element_ID@',Updated=TO_TIMESTAMP('2021-10-20 09:16:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541425
;

-- 2021-10-20T06:16:25.469Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='1=1',Updated=TO_TIMESTAMP('2021-10-20 09:16:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541426
;

-- 2021-10-20T06:16:32.890Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET AD_Window_ID=118,Updated=TO_TIMESTAMP('2021-10-20 09:16:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541426
;

-- 2021-10-20T09:24:50.112Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='(EXISTS (SELECT 1 from C_Element e JOIN C_Elementvalue ev on e.C_Element_ID = ev.C_Element_ID and ev.C_Element_ID = @C_Element_ID/-1@))',Updated=TO_TIMESTAMP('2021-10-20 12:24:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541426
;

-- 2021-10-20T09:42:30.410Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='(EXISTS (SELECT 1 from C_Element e JOIN C_Elementvalue ev on e.C_Element_ID = ev.C_Element_ID and e.C_Element_ID = C_Element.C_Element_ID and ev.C_Elementvalue_ID = @C_Elementvalue_ID/-1@))',Updated=TO_TIMESTAMP('2021-10-20 12:42:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541426
;

-- 2021-10-20T09:42:50.789Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET AD_Key=1125, WhereClause='',Updated=TO_TIMESTAMP('2021-10-20 12:42:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541425
;

-- 2021-10-20T10:32:20.195Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Source_ID=541425, AD_Reference_Target_ID=541426,Updated=TO_TIMESTAMP('2021-10-20 13:32:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540301
;

-- 2021-10-20T10:37:00.578Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET IsActive='Y',Updated=TO_TIMESTAMP('2021-10-20 13:37:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540260
;
-- 2021-10-20T11:56:46.136Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='EXISTS (SELECT 1 from C_Element e JOIN C_Elementvalue ev on e.C_Element_ID = ev.C_Element_ID and e.C_Element_ID = C_Element.C_Element_ID and ev.C_Elementvalue_ID = @C_Elementvalue_ID/-1@)',Updated=TO_TIMESTAMP('2021-10-20 14:56:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541426
;

-- 2021-10-20T12:00:27.699Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='EXISTS (SELECT 1 from C_Element e JOIN C_Elementvalue ev on e.C_Element_ID = ev.C_Element_ID and e.C_Element_ID = C_Element.C_Element_ID and ev.C_Elementvalue_ID = 1002545)',Updated=TO_TIMESTAMP('2021-10-20 15:00:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541426
;

-- 2021-10-20T12:01:33.426Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='EXISTS (SELECT 1 from C_Element e JOIN C_Elementvalue ev on e.C_Element_ID = ev.C_Element_ID and e.C_Element_ID = C_Element.C_Element_ID and ev.C_Elementvalue_ID = @C_ElementValue_ID/-1@)',Updated=TO_TIMESTAMP('2021-10-20 15:01:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541426
;


--C_Project -> Fact_Acct
-- 2021-10-20T12:44:06.246Z
-- URL zum Konzept
UPDATE AD_RelationType SET IsActive='N',Updated=TO_TIMESTAMP('2021-10-20 14:44:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540259
;

-- 2021-10-20T12:47:48.904Z
-- URL zum Konzept
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541427,TO_TIMESTAMP('2021-10-20 14:47:48','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','C_Project_Source',TO_TIMESTAMP('2021-10-20 14:47:48','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2021-10-20T12:47:48.908Z
-- URL zum Konzept
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541427 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2021-10-20T12:48:11.256Z
-- URL zum Konzept
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy) VALUES (0,1349,0,541427,203,TO_TIMESTAMP('2021-10-20 14:48:11','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N',TO_TIMESTAMP('2021-10-20 14:48:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-10-20T12:48:32.332Z
-- URL zum Konzept
UPDATE AD_RelationType SET AD_Reference_Source_ID=541177, IsActive='Y',Updated=TO_TIMESTAMP('2021-10-20 14:48:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540259
;

-- 2021-10-20T12:49:03.996Z
-- URL zum Konzept
UPDATE AD_RelationType SET AD_Reference_Source_ID=541427,Updated=TO_TIMESTAMP('2021-10-20 14:49:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540259
;

-- 2021-10-20T12:49:55.940Z
-- URL zum Konzept
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541428,TO_TIMESTAMP('2021-10-20 14:49:55','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','FactAccount_Target',TO_TIMESTAMP('2021-10-20 14:49:55','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2021-10-20T12:49:55.941Z
-- URL zum Konzept
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541428 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2021-10-20T12:50:22.620Z
-- URL zum Konzept
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy) VALUES (0,570545,0,541428,541485,TO_TIMESTAMP('2021-10-20 14:50:22','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N',TO_TIMESTAMP('2021-10-20 14:50:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-10-20T12:55:52.691Z
-- URL zum Konzept
UPDATE AD_Ref_Table SET WhereClause='EXISTS (SELECT 1 from C"_Project p JOIN Fact_Acct_Transactions_View f on p.C_Project_ID  = f.C_Project_ID and p.C_Project_ID = @C_Project_ID/-1@)',Updated=TO_TIMESTAMP('2021-10-20 14:55:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541428
;

-- 2021-10-20T12:56:42.840Z
-- URL zum Konzept
UPDATE AD_RelationType SET AD_Reference_Target_ID=541428,Updated=TO_TIMESTAMP('2021-10-20 14:56:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540259
;

-- 2021-10-20T12:59:05.401Z
-- URL zum Konzept
UPDATE AD_Ref_Table SET AD_Window_ID=130, WhereClause='EXISTS (SELECT 1 from C_Project p JOIN Fact_Acct_Transactions_View f on p.C_Project_ID  = f.C_Project_ID and p.C_Project_ID = @C_Project_ID/-1@)',Updated=TO_TIMESTAMP('2021-10-20 14:59:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541428
;

-- 2021-10-20T13:00:04.488Z
-- URL zum Konzept
UPDATE AD_Ref_Table SET AD_Window_ID=162,Updated=TO_TIMESTAMP('2021-10-20 15:00:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541428
;

-- 2021-10-20T13:00:37.019Z
-- URL zum Konzept
UPDATE AD_Ref_Table SET AD_Window_ID=130,Updated=TO_TIMESTAMP('2021-10-20 15:00:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541427
;

-- 2021-10-20T13:21:09.660Z
-- URL zum Konzept
UPDATE AD_Ref_Table SET WhereClause='EXISTS (SELECT 1 from C_Project p JOIN Fact_Acct_Transactions_View f on p.C_Project_ID  = f.C_Project_ID and p.C_Project_ID = Fact_Acct_Transactions_View.C_Project_ID and p.C_Project_ID = @C_Project_ID/-1@)',Updated=TO_TIMESTAMP('2021-10-20 15:21:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541428
;

-- C_PaySelection -> C_Payment
-- 2021-10-27T08:32:34.891Z
-- URL zum Konzept
UPDATE AD_RelationType SET IsActive='Y',Updated=TO_TIMESTAMP('2021-10-27 10:32:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540149
;

-- C_Payment -> C_PaySelection
-- 2021-10-20T13:43:34.204Z
-- URL zum Konzept
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541431,TO_TIMESTAMP('2021-10-20 15:43:34','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','C_Payment_Source',TO_TIMESTAMP('2021-10-20 15:43:34','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2021-10-20T13:43:34.207Z
-- URL zum Konzept
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541431 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2021-10-20T13:51:36.155Z
-- URL zum Konzept
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy) VALUES (0,5043,0,541431,335,TO_TIMESTAMP('2021-10-20 15:51:36','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N',TO_TIMESTAMP('2021-10-20 15:51:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-10-20T13:53:01.090Z
-- URL zum Konzept
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Source_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsTableRecordIdTarget,Name,Updated,UpdatedBy) VALUES (0,0,541431,540303,TO_TIMESTAMP('2021-10-20 15:53:00','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','N','C_Payment -> C_PaySelection',TO_TIMESTAMP('2021-10-20 15:53:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-10-20T13:53:47.213Z
-- URL zum Konzept
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541432,TO_TIMESTAMP('2021-10-20 15:53:47','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','C_Pay_Selection_Target',TO_TIMESTAMP('2021-10-20 15:53:47','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2021-10-20T13:53:47.213Z
-- URL zum Konzept
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541432 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2021-10-20T13:54:42.008Z
-- URL zum Konzept
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy) VALUES (0,552607,0,541432,427,206,TO_TIMESTAMP('2021-10-20 15:54:42','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N',TO_TIMESTAMP('2021-10-20 15:54:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-10-20T14:01:38.861Z
-- URL zum Konzept
UPDATE AD_Ref_Table SET WhereClause='EXISTS (SELECT 1 from C_Payment p JOIN C_PaySelectionline pl on p.C_Payment_ID = pl.C_Payment_ID and p.C_Payment_ID = C_PaySelectionline.C_Payment_ID and p.C_Payment_ID = @C_Payment_ID/-1@)',Updated=TO_TIMESTAMP('2021-10-20 16:01:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541432
;

-- 2021-10-20T14:03:05.736Z
-- URL zum Konzept
UPDATE AD_RelationType SET AD_Reference_Target_ID=540877,Updated=TO_TIMESTAMP('2021-10-20 16:03:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540303
;

-- 2021-10-20T14:03:58.566Z
-- URL zum Konzept
UPDATE AD_RelationType SET AD_Reference_Target_ID=541432, EntityType='D',Updated=TO_TIMESTAMP('2021-10-20 16:03:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540303
;

-- 2021-10-20T14:04:16.210Z
-- URL zum Konzept
UPDATE AD_Ref_Table SET AD_Window_ID=195,Updated=TO_TIMESTAMP('2021-10-20 16:04:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541431
;

-- AD_Sequence -> C_DocType
-- 2021-10-27T08:56:07.831Z
-- URL zum Konzept
UPDATE AD_RelationType SET IsActive='Y',Updated=TO_TIMESTAMP('2021-10-27 10:56:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540144
;

-- AD_Table -> AD_Window
-- 2021-10-25T08:05:02.775Z
-- URL zum Konzept
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541436,TO_TIMESTAMP('2021-10-25 10:05:02','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','AD_Table_Source_To_AD_Window',TO_TIMESTAMP('2021-10-25 10:05:02','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2021-10-25T08:05:02.778Z
-- URL zum Konzept
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541436 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2021-10-25T08:06:10.883Z
-- URL zum Konzept
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy) VALUES (0,105,0,541436,100,100,TO_TIMESTAMP('2021-10-25 10:06:10','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N',TO_TIMESTAMP('2021-10-25 10:06:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-10-25T08:06:50.776Z
-- URL zum Konzept
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541437,TO_TIMESTAMP('2021-10-25 10:06:50','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','AD_Window_Target_From_AD_Table',TO_TIMESTAMP('2021-10-25 10:06:50','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2021-10-25T08:06:50.778Z
-- URL zum Konzept
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541437 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2021-10-25T08:27:12.929Z
-- URL zum Konzept
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy,WhereClause) VALUES (0,155,0,541437,105,102,TO_TIMESTAMP('2021-10-25 10:27:12','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N',TO_TIMESTAMP('2021-10-25 10:27:12','YYYY-MM-DD HH24:MI:SS'),100,'exists(SELECT 1 from AD_Window w JOIN AD_Tab tt on tt.AD_Window_ID = w.AD_Window_ID JOIN ad_table t on t.AD_Window_ID = w.AD_Window_ID where w.AD_Window_ID = AD_Window.AD_Window_ID and t.AD_Table_ID = @AD_Table_ID/-1@)')
;

-- 2021-10-25T08:28:32.210Z
-- URL zum Konzept
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Source_ID,AD_Reference_Target_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsTableRecordIdTarget,Name,Updated,UpdatedBy) VALUES (0,0,541436,541437,540305,TO_TIMESTAMP('2021-10-25 10:28:32','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','N','AD_Table -> AD_Window',TO_TIMESTAMP('2021-10-25 10:28:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-10-25T08:28:42.887Z
-- URL zum Konzept
UPDATE AD_Ref_Table SET AD_Key=100,Updated=TO_TIMESTAMP('2021-10-25 10:28:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541436
;

-- 2021-10-25T08:40:13.227Z
-- URL zum Konzept
UPDATE AD_Ref_Table SET WhereClause='exists(SELECT 1 from AD_Window w JOIN AD_Tab tt on tt.AD_Window_ID = w.AD_Window_ID JOIN ad_table t on t.AD_Window_ID = w.AD_Window_ID where w.AD_Window_ID = AD_Window.AD_Window_ID and t.AD_Table_ID = @AD_Table_ID/-1@) or exists(SELECT 1 from AD_Window w JOIN AD_Tab tt on tt.AD_Window_ID = w.AD_Window_ID JOIN ad_table t on t.PO_Window_ID = w.AD_Window_ID where w.AD_Window_ID = AD_Window.AD_Window_ID and t.AD_Table_ID = @AD_Table_ID/-1@)',Updated=TO_TIMESTAMP('2021-10-25 10:40:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541437
;

-- AD_Window -> AD_Table
-- 2021-10-25T09:17:58.438Z
-- URL zum Konzept
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541438,TO_TIMESTAMP('2021-10-25 11:17:58','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','AD_Window_Source_To_AD_Table',TO_TIMESTAMP('2021-10-25 11:17:58','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2021-10-25T09:17:58.441Z
-- URL zum Konzept
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541438 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2021-10-25T09:18:25.831Z
-- URL zum Konzept
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy) VALUES (0,155,0,541438,105,TO_TIMESTAMP('2021-10-25 11:18:25','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N',TO_TIMESTAMP('2021-10-25 11:18:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-10-25T09:19:22.554Z
-- URL zum Konzept
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541439,TO_TIMESTAMP('2021-10-25 11:19:22','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','AD_Table_Target_For_AD_Window',TO_TIMESTAMP('2021-10-25 11:19:22','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2021-10-25T09:19:22.556Z
-- URL zum Konzept
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541439 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2021-10-25T09:20:21.590Z
-- URL zum Konzept
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy,WhereClause) VALUES (0,105,0,541439,100,TO_TIMESTAMP('2021-10-25 11:20:21','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N',TO_TIMESTAMP('2021-10-25 11:20:21','YYYY-MM-DD HH24:MI:SS'),100,'exists( select 1 from ad_table t join ad_tab tt on t.ad_table_id = tt.ad_table_id     join ad_window w on t.ad_window_id = w.ad_window_id     where  t.ad_window_id = ad_table.ad_window_id and w.ad_window_id = @AD_Window_ID/-1@     ) or     exists( select 1 from ad_table t join ad_tab tt on t.ad_table_id = tt.ad_table_id                                      join ad_window w on t.po_window_id = w.ad_window_id             where  t.po_window_id = ad_table.po_window_id and w.ad_window_id = @AD_Window_ID/-1@         )')
;

-- 2021-10-25T09:21:01.568Z
-- URL zum Konzept
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Source_ID,AD_Reference_Target_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsTableRecordIdTarget,Name,Updated,UpdatedBy) VALUES (0,0,541438,541439,540306,TO_TIMESTAMP('2021-10-25 11:21:01','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','AD_Window -> AD_Table',TO_TIMESTAMP('2021-10-25 11:21:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- delete `AD_Window <-> AD_Table`
-- 2021-10-27T08:34:11.204Z
-- URL zum Konzept
DELETE FROM AD_RelationType WHERE AD_RelationType_ID=540027
;

-- C_DunningDoc -> C_Dunning_Candidate
-- 2021-10-25T10:27:22.106Z
-- URL zum Konzept
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541440,TO_TIMESTAMP('2021-10-25 12:27:22','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','C_DunningCanditate_Source_To_C_DunningDoc',TO_TIMESTAMP('2021-10-25 12:27:22','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2021-10-25T10:27:22.108Z
-- URL zum Konzept
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541440 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2021-10-25T10:29:16.009Z
-- URL zum Konzept
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy) VALUES (0,547331,0,541440,540396,TO_TIMESTAMP('2021-10-25 12:29:16','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N',TO_TIMESTAMP('2021-10-25 12:29:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-10-25T10:29:42.944Z
-- URL zum Konzept
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Source_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsTableRecordIdTarget,Name,Updated,UpdatedBy) VALUES (0,0,321,540307,TO_TIMESTAMP('2021-10-25 12:29:42','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','N','C_DunningDoc -> C_Dunning_Candidate',TO_TIMESTAMP('2021-10-25 12:29:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-10-25T10:30:52.138Z
-- URL zum Konzept
UPDATE AD_RelationType SET AD_Reference_Source_ID=541440, EntityType='D',Updated=TO_TIMESTAMP('2021-10-25 12:30:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540307
;

-- 2021-10-25T10:31:46.951Z
-- URL zum Konzept
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541441,TO_TIMESTAMP('2021-10-25 12:31:46','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','C_DunningDoc_Target_For_C_DunningCandidate',TO_TIMESTAMP('2021-10-25 12:31:46','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2021-10-25T10:31:46.953Z
-- URL zum Konzept
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541441 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2021-10-25T10:33:19.703Z
-- URL zum Konzept
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy,WhereClause) VALUES (0,547408,0,541441,540401,TO_TIMESTAMP('2021-10-25 12:33:19','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N',TO_TIMESTAMP('2021-10-25 12:33:19','YYYY-MM-DD HH24:MI:SS'),100,'(select 1 from c_dunning_candidate c join c_dunninglevel l on c.c_dunninglevel_id = l.c_dunninglevel_id     join c_dunningdoc d on d.c_dunningdoc_id = l.c_dunninglevel_id     where c.c_dunning_candidate_id = C_Dunning_Candidate.c_dunning_candidate_id and c_dunningdoc_id = @C_Dunningdoc_ID/-1@)')
;

-- 2021-10-25T10:34:04.302Z
-- URL zum Konzept
UPDATE AD_Ref_Table SET AD_Window_ID=540154,Updated=TO_TIMESTAMP('2021-10-25 12:34:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541441
;

-- 2021-10-25T10:34:41.614Z
-- URL zum Konzept
UPDATE AD_RelationType SET AD_Reference_Target_ID=541012,Updated=TO_TIMESTAMP('2021-10-25 12:34:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540307
;

-- 2021-10-25T10:35:21.839Z
-- URL zum Konzept
UPDATE AD_RelationType SET AD_Reference_Target_ID=541441,Updated=TO_TIMESTAMP('2021-10-25 12:35:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540307
;

-- 2021-10-25T10:35:39.302Z
-- URL zum Konzept
UPDATE AD_Ref_Table SET AD_Window_ID=540154,Updated=TO_TIMESTAMP('2021-10-25 12:35:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541440
;

-- 2021-10-25T10:36:58.302Z
-- URL zum Konzept
UPDATE AD_Reference SET Name='C_DunningDoc_Source_For_C_Dunning_Candidate',Updated=TO_TIMESTAMP('2021-10-25 12:36:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541440
;

-- 2021-10-25T10:37:22.867Z
-- URL zum Konzept
UPDATE AD_Ref_Table SET AD_Key=547408, AD_Table_ID=540401, AD_Window_ID=540155,Updated=TO_TIMESTAMP('2021-10-25 12:37:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541440
;

-- 2021-10-25T10:38:06.900Z
-- URL zum Konzept
UPDATE AD_Reference SET Name='C_DunningCandidate_Target_For_C_DunningDoc',Updated=TO_TIMESTAMP('2021-10-25 12:38:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541441
;

-- 2021-10-25T10:40:48.595Z
-- URL zum Konzept
UPDATE AD_Ref_Table SET AD_Key=547345, AD_Table_ID=540396,Updated=TO_TIMESTAMP('2021-10-25 12:40:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541441
;

-- 2021-10-25T10:42:30.014Z
-- URL zum Konzept
UPDATE AD_Ref_Table SET AD_Key=547331,Updated=TO_TIMESTAMP('2021-10-25 12:42:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541441
;

-- 2021-10-25T10:46:20.495Z
-- URL zum Konzept
UPDATE AD_Ref_Table SET WhereClause='(select 1 from c_dunning_candidate c join c_dunninglevel l on c.c_dunninglevel_id = l.c_dunninglevel_id     join c_dunningdoc d on d.c_dunninglevel_id = l.c_dunninglevel_id     where c.c_dunning_candidate_id = C_Dunning_Candidate.c_dunning_candidate_id and d.c_dunningdoc_id = @C_Dunningdoc_ID/-1@)',Updated=TO_TIMESTAMP('2021-10-25 12:46:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541441
;

-- 2021-10-25T10:49:05.532Z
-- URL zum Konzept
UPDATE AD_Ref_Table SET WhereClause='(select 1 from C_Dunning_Candidate c join C_DunningLevel l on c.C_DunningLevel_ID = l.C_DunningLevel_ID    join C_Dunningdoc d on d.C_DunningLevel_ID = l.C_DunningLevel_ID where c.C_Dunning_Candidate_ID = C_Dunning_Candidate.C_Dunning_Candidate_ID and d.C_Dunningdoc_ID = @C_Dunningdoc_ID/-1@)',Updated=TO_TIMESTAMP('2021-10-25 12:49:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541441
;

-- C_Dunning_Candidate -> C_DunningDoc
-- 2021-10-27T08:38:25.346Z
-- URL zum Konzept
UPDATE AD_RelationType SET IsActive='Y',Updated=TO_TIMESTAMP('2021-10-27 10:38:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540150
;

--C_Bpartner_Org_Masterdata -> C_Bpartner
-- 2021-10-27T08:39:36.411Z
-- URL zum Konzept
UPDATE AD_RelationType SET IsActive='Y',Updated=TO_TIMESTAMP('2021-10-27 10:39:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540265
;

--Transportation Order -> DPD Delivery Order
delete from ad_relationtype  where ad_relationtype_id = 540231
;


--M_PriceListSchema -> M_PriceListSchemaLine
-- 2021-10-27T09:00:41.596Z
-- URL zum Konzept
DELETE FROM AD_RelationType WHERE AD_RelationType_ID=540232
;

-- M_PriceListSchemaLine -> M_PriceListSchema
-- 2021-10-25T12:55:50.194Z
-- URL zum Konzept
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541442,TO_TIMESTAMP('2021-10-25 14:55:50','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','M_PriceListSchemaLine_Source_For_M_PriceListSchema',TO_TIMESTAMP('2021-10-25 14:55:50','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2021-10-25T12:55:50.195Z
-- URL zum Konzept
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541442 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2021-10-25T12:59:20.384Z
-- URL zum Konzept
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy) VALUES (0,6620,0,541442,477,TO_TIMESTAMP('2021-10-25 14:59:20','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N',TO_TIMESTAMP('2021-10-25 14:59:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-10-25T12:59:49.072Z
-- URL zum Konzept
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Source_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsTableRecordIdTarget,Name,Updated,UpdatedBy) VALUES (0,0,541077,540308,TO_TIMESTAMP('2021-10-25 14:59:48','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','M_PriceListSchemaLine -> M_PriceListSchema',TO_TIMESTAMP('2021-10-25 14:59:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-10-25T13:05:46.403Z
-- URL zum Konzept
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541443,TO_TIMESTAMP('2021-10-25 15:05:46','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','M_DiscountSchema_Target_For_M-DiscountSchemaLine',TO_TIMESTAMP('2021-10-25 15:05:46','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2021-10-25T13:05:46.404Z
-- URL zum Konzept
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541443 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2021-10-25T13:06:10.051Z
-- URL zum Konzept
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy) VALUES (0,6581,0,541443,475,TO_TIMESTAMP('2021-10-25 15:06:10','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N',TO_TIMESTAMP('2021-10-25 15:06:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-10-25T13:08:18.327Z
-- URL zum Konzept
UPDATE AD_Ref_Table SET AD_Window_ID=337,Updated=TO_TIMESTAMP('2021-10-25 15:08:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541443
;

-- 2021-10-25T13:12:23.171Z
-- URL zum Konzept
UPDATE AD_Ref_Table SET WhereClause='exists( select 1 from M_DiscountSchemaLine dl join M_Discountschema d on dl.M_Discountschema_ID = d.M_Discountschema_ID     and d.M_Discountschema_ID = M_Discountschema.M_Discountschema_ID and dl.M_Discountschema_ID = @M_DiscountSchemaLine_ID/-1@)',Updated=TO_TIMESTAMP('2021-10-25 15:12:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541443
;

-- 2021-10-25T13:12:54.589Z
-- URL zum Konzept
UPDATE AD_RelationType SET AD_Reference_Source_ID=541442, AD_Reference_Target_ID=249,Updated=TO_TIMESTAMP('2021-10-25 15:12:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540308
;

-- 2021-10-25T13:15:23.371Z
-- URL zum Konzept
UPDATE AD_RelationType SET AD_Reference_Target_ID=540152,Updated=TO_TIMESTAMP('2021-10-25 15:15:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540308
;

-- 2021-10-25T13:16:57.166Z
-- URL zum Konzept
UPDATE AD_RelationType SET AD_Reference_Target_ID=541443,Updated=TO_TIMESTAMP('2021-10-25 15:16:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540308
;

-- 2021-10-25T13:17:14.530Z
-- URL zum Konzept
UPDATE AD_Ref_Table SET AD_Key=6612,Updated=TO_TIMESTAMP('2021-10-25 15:17:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541442
;

-- Async Batch -> C_Queue_WorkPackage
-- 2021-10-27T08:41:35.698Z
-- URL zum Konzept
DELETE FROM AD_RelationType WHERE AD_RelationType_ID=540109
;

-- C_Queue_WorkPackage -> Async Batch
-- 2021-10-26T08:47:08.779Z
-- URL zum Konzept
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541444,TO_TIMESTAMP('2021-10-26 10:47:08','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','C_Queue_WorkPackage_Source_To_AsyncBatch',TO_TIMESTAMP('2021-10-26 10:47:08','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2021-10-26T08:47:08.787Z
-- URL zum Konzept
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541444 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2021-10-26T08:48:13.200Z
-- URL zum Konzept
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy) VALUES (0,547851,0,541444,540425,TO_TIMESTAMP('2021-10-26 10:48:13','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N',TO_TIMESTAMP('2021-10-26 10:48:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-10-26T08:48:30.707Z
-- URL zum Konzept
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Source_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsTableRecordIdTarget,Name,Updated,UpdatedBy) VALUES (0,0,541314,540309,TO_TIMESTAMP('2021-10-26 10:48:30','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','C_Queue_WorkPackage -> Async_Batch',TO_TIMESTAMP('2021-10-26 10:48:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-10-26T08:49:04.136Z
-- URL zum Konzept
UPDATE AD_RelationType SET AD_Reference_Source_ID=540527,Updated=TO_TIMESTAMP('2021-10-26 10:49:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540309
;

-- 2021-10-26T08:50:22.145Z
-- URL zum Konzept
UPDATE AD_RelationType SET AD_Reference_Source_ID=541444,Updated=TO_TIMESTAMP('2021-10-26 10:50:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540309
;

-- 2021-10-26T08:50:59.615Z
-- URL zum Konzept
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541445,TO_TIMESTAMP('2021-10-26 10:50:59','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','Async_Batch_Target_For_C_Queue_Workpackage',TO_TIMESTAMP('2021-10-26 10:50:59','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2021-10-26T08:50:59.618Z
-- URL zum Konzept
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541445 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2021-10-26T08:52:13.957Z
-- URL zum Konzept
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy,WhereClause) VALUES (0,551302,0,541445,540620,TO_TIMESTAMP('2021-10-26 10:52:13','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N',TO_TIMESTAMP('2021-10-26 10:52:13','YYYY-MM-DD HH24:MI:SS'),100,'exists(select 1 from C_Async_Batch b join C_Queue_WorkPackage p on b.C_Async_Batch_ID = p.C_Async_Batch_ID     where b.C_Async_Batch_ID = C_Async_Batch.C_Async_Batch_ID and p.C_Queue_WorkPackage_ID = @C_Queue_WorkPackage_ID/-1@)')
;

-- 2021-10-26T08:52:32.929Z
-- URL zum Konzept
UPDATE AD_RelationType SET AD_Reference_Target_ID=541445,Updated=TO_TIMESTAMP('2021-10-26 10:52:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540309
;

-- C_Order -> M_Material_Tracking
-- 2021-10-27T08:43:12.096Z
-- URL zum Konzept
UPDATE AD_RelationType SET IsActive='Y',Updated=TO_TIMESTAMP('2021-10-27 10:43:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540136
;

-- M_Material_Tracking -> C_Order
-- 2021-10-26T10:49:00.181Z
-- URL zum Konzept
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541446,TO_TIMESTAMP('2021-10-26 12:49:00','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','M_Material_Tracking_Source_To_C_Order',TO_TIMESTAMP('2021-10-26 12:49:00','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2021-10-26T10:49:00.184Z
-- URL zum Konzept
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541446 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2021-10-26T10:49:22.590Z
-- URL zum Konzept
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy) VALUES (0,551110,0,541446,540610,TO_TIMESTAMP('2021-10-26 12:49:22','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N',TO_TIMESTAMP('2021-10-26 12:49:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-10-26T10:49:41.851Z
-- URL zum Konzept
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Source_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsTableRecordIdTarget,Name,Updated,UpdatedBy) VALUES (0,0,351,540310,TO_TIMESTAMP('2021-10-26 12:49:41','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','M_Material_Tracking -> C_Order ',TO_TIMESTAMP('2021-10-26 12:49:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-10-26T10:50:01.724Z
-- URL zum Konzept
UPDATE AD_RelationType SET AD_Reference_Source_ID=541446,Updated=TO_TIMESTAMP('2021-10-26 12:50:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540310
;

-- 2021-10-26T10:50:37.591Z
-- URL zum Konzept
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541447,TO_TIMESTAMP('2021-10-26 12:50:37','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','C_Order_Target_From_M_Material_Tracking',TO_TIMESTAMP('2021-10-26 12:50:37','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2021-10-26T10:50:37.593Z
-- URL zum Konzept
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541447 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2021-10-26T10:52:00.196Z
-- URL zum Konzept
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy,WhereClause) VALUES (0,2161,0,541447,259,181,TO_TIMESTAMP('2021-10-26 12:52:00','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N',TO_TIMESTAMP('2021-10-26 12:52:00','YYYY-MM-DD HH24:MI:SS'),100,'(     select 1     from C_Order o              join C_OrderLine ol on o.C_Order_ID = ol.C_Order_ID and o.isActive = ''Y'' and ol.isactive = ''Y''              join M_Material_Tracking_Ref mtr on  ol.C_OrderLine_ID = mtr.Record_ID and  mtr.AD_Table_ID = ( select ad_Table_ID from AD_Table where tablename = ''C_OrderLine'') and mtr.isActive = ''Y''              join M_Material_Tracking mt on mtr.M_Material_Tracking_ID = mt.M_Material_Tracking_ID and mt.isActive = ''Y''     where             C_Order.C_Order_ID = o.C_Order_ID and         ( o.C_Order_ID = 0 or mt.M_Material_Tracking_ID = @M_Material_Tracking_ID/-1@) )')
;

-- 2021-10-26T10:52:24.643Z
-- URL zum Konzept
UPDATE AD_RelationType SET AD_Reference_Target_ID=541178,Updated=TO_TIMESTAMP('2021-10-26 12:52:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540310
;

-- 2021-10-26T10:52:53.376Z
-- URL zum Konzept
UPDATE AD_RelationType SET AD_Reference_Target_ID=541447,Updated=TO_TIMESTAMP('2021-10-26 12:52:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540310
;

-- 2021-10-26T10:53:10.576Z
-- URL zum Konzept
UPDATE AD_Ref_Table SET AD_Window_ID=540226,Updated=TO_TIMESTAMP('2021-10-26 12:53:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541446
;

-- 2021-10-26T10:56:54.364Z
-- URL zum Konzept
UPDATE AD_Ref_Table SET WhereClause='(     select 1     from C_Order o              join C_OrderLine ol on o.C_Order_ID = ol.C_Order_ID and o.isActive = ''Y'' and ol.isactive = ''Y''              join M_Material_Tracking_Ref mtr on  ol.C_OrderLine_ID = mtr.Record_ID and  mtr.AD_Table_ID = ( select ad_Table_ID from AD_Table where tablename = ''C_OrderLine'') and mtr.isActive = ''Y''              join M_Material_Tracking mt on mtr.M_Material_Tracking_ID = mt.M_Material_Tracking_ID and mt.isActive = ''Y''     where             C_Order.C_Order_ID = o.C_Order_ID and         ( o.C_Order_ID = @C_Order_ID/-1@ or mt.M_Material_Tracking_ID = @M_Material_Tracking_ID/-1@) )',Updated=TO_TIMESTAMP('2021-10-26 12:56:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541447
;

-- 2021-10-26T10:57:45.613Z
-- URL zum Konzept
UPDATE AD_RelationType SET IsTableRecordIdTarget='Y',Updated=TO_TIMESTAMP('2021-10-26 12:57:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540310
;

-- 2021-10-26T11:00:52.223Z
-- URL zum Konzept
UPDATE AD_Ref_Table SET WhereClause='exists (     select 1     from C_Order o              join C_OrderLine ol on o.C_Order_ID = ol.C_Order_ID and o.isActive = ''Y'' and ol.isactive = ''Y''              join M_Material_Tracking_Ref mtr on  ol.C_OrderLine_ID = mtr.Record_ID and  mtr.AD_Table_ID = ( select ad_Table_ID from AD_Table where tablename = ''C_OrderLine'') and mtr.isActive = ''Y''              join M_Material_Tracking mt on mtr.M_Material_Tracking_ID = mt.M_Material_Tracking_ID and mt.isActive = ''Y''     where             C_Order.C_Order_ID = o.C_Order_ID and         ( o.C_Order_ID = @C_Order_ID/-1@ or mt.M_Material_Tracking_ID = @M_Material_Tracking_ID/-1@) )',Updated=TO_TIMESTAMP('2021-10-26 13:00:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541447
;

-- M_InOut -> Material_Tracking
-- 2021-10-27T08:45:33.402Z
-- URL zum Konzept
UPDATE AD_RelationType SET IsActive='Y',Updated=TO_TIMESTAMP('2021-10-27 10:45:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540137
;

-- Material_Tracking -> M_InOut
-- 2021-10-26T11:46:44.226Z
-- URL zum Konzept
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541448,TO_TIMESTAMP('2021-10-26 13:46:44','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','M_MaterialTracking_Source_To_M_InOut',TO_TIMESTAMP('2021-10-26 13:46:44','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2021-10-26T11:46:44.227Z
-- URL zum Konzept
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541448 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2021-10-26T11:47:24.104Z
-- URL zum Konzept
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy) VALUES (0,551110,0,541448,540610,540226,TO_TIMESTAMP('2021-10-26 13:47:24','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N',TO_TIMESTAMP('2021-10-26 13:47:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-10-26T11:48:17.418Z
-- URL zum Konzept
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541449,TO_TIMESTAMP('2021-10-26 13:48:17','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','M_Inout_Target_From_M_Material_Tracking',TO_TIMESTAMP('2021-10-26 13:48:17','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2021-10-26T11:48:17.420Z
-- URL zum Konzept
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541449 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2021-10-26T11:54:08.940Z
-- URL zum Konzept
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541450,TO_TIMESTAMP('2021-10-26 13:54:08','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','M_InnOutTarget_From_M_Material_Tracking',TO_TIMESTAMP('2021-10-26 13:54:08','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2021-10-26T11:54:08.942Z
-- URL zum Konzept
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541450 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2021-10-26T11:55:07.502Z
-- URL zum Konzept
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy,WhereClause) VALUES (0,3521,0,541450,319,TO_TIMESTAMP('2021-10-26 13:55:07','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N',TO_TIMESTAMP('2021-10-26 13:55:07','YYYY-MM-DD HH24:MI:SS'),100,'exists (     select 1     from M_InOut io              join M_InOutLine iol on io.M_InOut_ID = iol.M_InOut_ID and io.isActive = ''Y'' and iol.isactive = ''Y''              join M_Material_Tracking_Ref mtr on  iol.M_InOutLine_ID = mtr.Record_ID and  mtr.AD_Table_ID = ( select ad_Table_ID from AD_Table where tablename = ''M_InOutLine'') and mtr.isActive = ''Y''              join M_Material_Tracking mt on mtr.M_Material_Tracking_ID = mt.M_Material_Tracking_ID and mt.isActive = ''Y''     where             M_Material_Tracking.M_Material_Tracking_ID = mt.M_Material_Tracking_ID and         ( mt.M_Material_Tracking_ID = @M_Material_Tracking_ID/-1@) )')
;

-- 2021-10-26T11:56:48.487Z
-- URL zum Konzept
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy,WhereClause) VALUES (0,3521,0,541449,319,TO_TIMESTAMP('2021-10-26 13:56:48','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N',TO_TIMESTAMP('2021-10-26 13:56:48','YYYY-MM-DD HH24:MI:SS'),100,'  exists (  select 1  from M_InOut io  join M_InOutLine iol on io.M_InOut_ID = iol.M_InOut_ID and io.isActive = ''Y'' and iol.isactive = ''Y''  join M_Material_Tracking_Ref mtr on  iol.M_InOutLine_ID = mtr.Record_ID and  mtr.AD_Table_ID = ( select ad_Table_ID from AD_Table where tablename = ''M_InOutLine'') and mtr.isActive = ''Y''  join M_Material_Tracking mt on mtr.M_Material_Tracking_ID = mt.M_Material_Tracking_ID and mt.isActive = ''Y''  where   M_Material_Tracking.M_Material_Tracking_ID = mt.M_Material_Tracking_ID and   ( io.M_InOut_ID = @M_InOut_ID/-1@ or mt.M_Material_Tracking_ID = @M_Material_Tracking_ID/-1@) ) ')
;

-- 2021-10-26T11:57:57.627Z
-- URL zum Konzept
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Source_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsTableRecordIdTarget,Name,Updated,UpdatedBy) VALUES (0,0,541448,540311,TO_TIMESTAMP('2021-10-26 13:57:57','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','N','M_Material_Tracking -> M_InOut',TO_TIMESTAMP('2021-10-26 13:57:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-10-26T11:59:02.566Z
-- URL zum Konzept
UPDATE AD_Ref_Table SET AD_Window_ID=184,Updated=TO_TIMESTAMP('2021-10-26 13:59:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541449
;

-- 2021-10-26T11:59:06.172Z
-- URL zum Konzept
UPDATE AD_RelationType SET AD_Reference_Target_ID=541449,Updated=TO_TIMESTAMP('2021-10-26 13:59:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540311
;

-- 2021-10-26T11:59:10.980Z
-- URL zum Konzept
UPDATE AD_RelationType SET EntityType='D',Updated=TO_TIMESTAMP('2021-10-26 13:59:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540311
;

-- 2021-10-27T09:23:56.543Z
-- URL zum Konzept
UPDATE AD_Ref_Table SET WhereClause='  exists (  select 1  from M_InOut io  join M_InOutLine iol on io.M_InOut_ID = iol.M_InOut_ID and io.isActive = ''Y'' and iol.isactive = ''Y''  join M_Material_Tracking_Ref mtr on  iol.M_InOutLine_ID = mtr.Record_ID and  mtr.AD_Table_ID = ( select ad_Table_ID from AD_Table where tablename = ''M_InOutLine'') and mtr.isActive = ''Y''  join M_Material_Tracking mt on mtr.M_Material_Tracking_ID = mt.M_Material_Tracking_ID and mt.isActive = ''Y''  where   M_InOut.M_InOut_ID = io.M_InOut_ID and   ( io.M_InOut_ID = @M_InOut_ID/-1@ or mt.M_Material_Tracking_ID = @M_Material_Tracking_ID/-1@) ) ',Updated=TO_TIMESTAMP('2021-10-27 11:23:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541449
;

-- C_Printing_Queue -> C_Print_Job
-- 2021-10-26T12:29:33.567Z
-- URL zum Konzept
UPDATE AD_RelationType SET IsActive='Y',Updated=TO_TIMESTAMP('2021-10-26 14:29:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540207
;

--C_Print_Job -> C_Printing_Queue
-- 2021-10-26T12:54:28.955Z
-- URL zum Konzept
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541451,TO_TIMESTAMP('2021-10-26 14:54:28','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','C_Print_Job_Source',TO_TIMESTAMP('2021-10-26 14:54:28','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2021-10-26T12:54:28.958Z
-- URL zum Konzept
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541451 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2021-10-26T12:55:05.781Z
-- URL zum Konzept
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy) VALUES (0,548068,0,541451,540437,TO_TIMESTAMP('2021-10-26 14:55:05','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N',TO_TIMESTAMP('2021-10-26 14:55:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-10-26T12:56:34.827Z
-- URL zum Konzept
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541452,TO_TIMESTAMP('2021-10-26 14:56:34','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','C_PrintingQueue_Target_From_C_Print_Job',TO_TIMESTAMP('2021-10-26 14:56:34','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2021-10-26T12:56:34.829Z
-- URL zum Konzept
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541452 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2021-10-26T12:59:24.226Z
-- URL zum Konzept
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy,WhereClause) VALUES (0,548048,0,541452,540435,TO_TIMESTAMP('2021-10-26 14:59:24','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N',TO_TIMESTAMP('2021-10-26 14:59:24','YYYY-MM-DD HH24:MI:SS'),100,'exists( select 1 from c_print_job_line pjl join  c_printing_queue que on pjl.c_printing_queue_id = que.c_printing_queue_id where C_Printing_Queue.C_Printing_Queue_ID = que.C_Printing_Queue_ID and  pjl.c_print_job_id = @C_Printing_Queue_ID/-1@)')
;

-- 2021-10-26T13:00:39.855Z
-- URL zum Konzept
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Source_ID,AD_Reference_Target_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsTableRecordIdTarget,Name,Updated,UpdatedBy) VALUES (0,0,541451,541452,540312,TO_TIMESTAMP('2021-10-26 15:00:39','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','C_Print_Job -> C_Printing_Queue',TO_TIMESTAMP('2021-10-26 15:00:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-10-26T13:01:03.770Z
-- URL zum Konzept
UPDATE AD_Ref_Table SET AD_Window_ID=540166,Updated=TO_TIMESTAMP('2021-10-26 15:01:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541451
;

-- 2021-10-26T13:01:31.447Z
-- URL zum Konzept
UPDATE AD_Ref_Table SET AD_Window_ID=540165,Updated=TO_TIMESTAMP('2021-10-26 15:01:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541452
;

-- 2021-10-26T13:04:49.216Z
-- URL zum Konzept
UPDATE AD_Ref_Table SET WhereClause='exists( select 1 from C_Print_Job_Line pjl join C_Printing_Queue que on pjl.C_Printing_Queue_ID = que.C_Printing_Queue_ID where C_Printing_Queue.C_Printing_Queue_ID = que.C_Printing_Queue_ID and  pjl.C_Print_Job_ID = @CC_Print_Job_ID/-1@)',Updated=TO_TIMESTAMP('2021-10-26 15:04:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541452
;

-- 2021-10-26T13:05:31.193Z
-- URL zum Konzept
UPDATE AD_Ref_Table SET WhereClause='exists( select 1 from C_Print_Job_Line pjl join C_Printing_Queue que on pjl.C_Printing_Queue_ID = que.C_Printing_Queue_ID where C_Printing_Queue.C_Printing_Queue_ID = que.C_Printing_Queue_ID and  pjl.C_Print_Job_ID = @C_Print_Job_ID/-1@)',Updated=TO_TIMESTAMP('2021-10-26 15:05:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541452
;

-- PP_Order -> M_Material_Tracking
-- 2021-10-26T13:38:40.507Z
-- URL zum Konzept
UPDATE AD_RelationType SET IsActive='Y',Updated=TO_TIMESTAMP('2021-10-26 15:38:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540135
;

--M_HU -> M_HU_Trx_Line
-- 2021-10-27T09:32:32.623Z
-- URL zum Konzept
UPDATE AD_RelationType SET IsActive='Y',Updated=TO_TIMESTAMP('2021-10-27 11:32:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540208
;

-- 2021-10-27T09:37:42.298Z
-- URL zum Konzept
UPDATE AD_Ref_Table SET AD_Window_ID=540189,Updated=TO_TIMESTAMP('2021-10-27 11:37:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540499
;

--Auftragskand.<=>Auftrag (Belegebene)
-- 2021-10-27T09:52:19.164Z
-- URL zum Konzept
DELETE FROM AD_RelationType WHERE AD_RelationType_ID=540008
;

--c_bankstatement -> esr_import
-- 2021-10-27T10:03:22.382Z
-- URL zum Konzept
DELETE FROM AD_RelationType WHERE AD_RelationType_ID=540133
;

-- esr_import -> c_bankstatement
-- 2021-10-27T12:47:25.868Z
-- URL zum Konzept
UPDATE AD_RelationType SET IsActive='Y',Updated=TO_TIMESTAMP('2021-10-27 14:47:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540250
;

--ExternalConfig -> External Config Log
-- 2021-10-27T13:42:33.557Z
-- URL zum Konzept
UPDATE AD_RelationType SET IsActive='Y',Updated=TO_TIMESTAMP('2021-10-27 15:42:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540279
;

--AD_Role_Included -> AD_Role
-- 2021-10-27T13:52:19.334Z
-- URL zum Konzept
UPDATE AD_RelationType SET IsActive='Y',Updated=TO_TIMESTAMP('2021-10-27 15:52:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540143
;

-- MD_Candidate Parent -> MD_Candidate
-- 2021-10-28T08:23:35.682Z
-- URL zum Konzept
UPDATE AD_RelationType SET IsActive='Y',Updated=TO_TIMESTAMP('2021-10-28 10:23:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540217
;

--RelType C_Element_Value <-> Fact_acct
-- 2021-10-28T08:55:27.928Z
-- URL zum Konzept
UPDATE AD_RelationType SET IsActive='Y',Updated=TO_TIMESTAMP('2021-10-28 10:55:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540234
;

-- 2021-10-28T08:59:43.824Z
-- URL zum Konzept
UPDATE AD_RelationType SET Name='RelType C_Element_Value -> Fact_acct',Updated=TO_TIMESTAMP('2021-10-28 10:59:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540234
;

-- BankStatementLineRef <-> C_AllocationLine
-- 2021-10-28T14:52:14.825Z
-- URL zum Konzept
DELETE FROM AD_RelationType WHERE AD_RelationType_ID=540114
;
