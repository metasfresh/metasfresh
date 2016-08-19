------------- Relation C_Order->C_OlCand--------------------

-- Aug 19, 2016 4:57 PM
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='exists
(
	select 1 from C_Order o
	join C_OrderLine ol on o.C_Order_ID = ol.C_Order_ID
	join C_Order_Line_Alloc ola on ol.C_OrderLine_ID = ola.C_OrderLine_ID
	join C_OLCand olc on ola.C_OLCand_ID = olc.C_Olcand_ID 
	
	where
	C_Order.C_Order_ID = o.C_Order_ID
	and
	olc.C_OLCand_ID = @C_OLCand_ID/-1@
)',Updated=TO_TIMESTAMP('2016-08-19 16:57:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540618
;

-- Aug 19, 2016 4:57 PM
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='exists
(
	select 1 from C_Order o
	join C_OrderLine ol on o.C_Order_ID = ol.C_Order_ID
	join C_Order_Line_Alloc ola on ol.C_OrderLine_ID = ola.C_OrderLine_ID
	join C_OLCand olc on ola.C_OLCand_ID = olc.C_Olcand_ID 
	
	where
	C_OLCand.C_OLCand_ID = olc.C_OLCand_ID
	and
	o.C_Order_ID = @C_Order_ID/-1@ 
)',Updated=TO_TIMESTAMP('2016-08-19 16:57:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540619
;

-- Aug 19, 2016 5:45 PM
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause=NULL,Updated=TO_TIMESTAMP('2016-08-19 17:45:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540618
;

-- Aug 19, 2016 6:05 PM
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET IsDirected='Y',Updated=TO_TIMESTAMP('2016-08-19 18:05:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540145
;

-- Aug 19, 2016 6:06 PM
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='exists
(
	select 1 from C_Order o
	join C_OrderLine ol on o.C_Order_ID = ol.C_Order_ID
	join C_Order_Line_Alloc ola on ol.C_OrderLine_ID = ola.C_OrderLine_ID
	join C_OLCand olc on ola.C_OLCand_ID = olc.C_Olcand_ID 
	
	where
	C_Order.C_Order_ID = o.C_Order_ID
	and
	olc.C_OLCand_ID = @C_OLCand_ID/-1@
)',Updated=TO_TIMESTAMP('2016-08-19 18:06:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540618
;

-- Aug 19, 2016 6:08 PM
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Target_ID=NULL,Updated=TO_TIMESTAMP('2016-08-19 18:08:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540145
;

-- Aug 19, 2016 6:09 PM
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Target_ID=540619,Updated=TO_TIMESTAMP('2016-08-19 18:09:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540145
;

-- Aug 19, 2016 6:09 PM
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Source_ID=NULL,Updated=TO_TIMESTAMP('2016-08-19 18:09:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540145
;

-- Aug 19, 2016 6:10 PM
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Source_ID=290,Updated=TO_TIMESTAMP('2016-08-19 18:10:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540145
;

-- Aug 19, 2016 6:12 PM
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Source_ID,AD_Reference_Target_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsDirected,IsExplicit,Name,Updated,UpdatedBy) VALUES (0,0,540618,540201,540154,TO_TIMESTAMP('2016-08-19 18:12:06','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.ordercandidate','Y','Y','N','C_OLCand->C_Order',TO_TIMESTAMP('2016-08-19 18:12:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Aug 19, 2016 6:13 PM
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Source_ID=540201, AD_Reference_Target_ID=540618,Updated=TO_TIMESTAMP('2016-08-19 18:13:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540154
;



-- Aug 19, 2016 7:04 PM
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='exists
(
	select 1 from C_OrderLine ol
	join C_Order_Line_Alloc ola on ol.C_OrderLine_ID = ola.C_OrderLine_ID and ol.C_Order_ID = @C_Order_ID/-1@ 
	join C_OLCand olc on ola.C_OLCand_ID = olc.C_Olcand_ID 
	where
	C_OLCand.C_OLCand_ID = olc.C_OLCand_ID
)',Updated=TO_TIMESTAMP('2016-08-19 19:04:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540619
;

-- Aug 19, 2016 7:13 PM
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='exists
(
	select 1 from C_OrderLine ol 
	join C_Order_Line_Alloc ola on ol.C_OrderLine_ID = ola.C_OrderLine_ID and ola.C_OLCand_ID = @C_OLCand_ID/-1@
	join C_OLCand olc on ola.C_OLCand_ID = olc.C_Olcand_ID 
	where  C_Order.C_Order_ID = ol.C_Order_ID
)',Updated=TO_TIMESTAMP('2016-08-19 19:13:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540618
;



-----------------------------Relation C_Invoice -> M_InOut (PO) -------------------------



-- Aug 19, 2016 7:36 PM
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='EXISTS
(
SELECT 1
	FROM C_Invoice i
	JOIN C_InvoiceLine il on i.C_Invoice_ID = il.C_Invoice_ID and i.C_Invoice_ID = @C_Invoice_ID/-1@ 
	JOIN M_InOutLine iol on il.M_InOutline_ID = iol.M_InOutline_ID
	JOIN M_InOut io on iol.M_InOut_ID = io.M_InOut_ID

	WHERE 
		M_InOut.M_InOut_ID = io.M_InOut_ID
		AND i.IsSOTrx = M_InOut.IsSOTrx
		AND i.IsSOTrx = ''N''
)',Updated=TO_TIMESTAMP('2016-08-19 19:36:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540586
;

-- Aug 19, 2016 7:37 PM
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Source_ID=540569,Updated=TO_TIMESTAMP('2016-08-19 19:37:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540131
;

-- Aug 19, 2016 7:38 PM
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET IsDirected='Y',Updated=TO_TIMESTAMP('2016-08-19 19:38:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540131
;



------------------ relation M_InOut -> Invoice (SO)-----------


-- Aug 19, 2016 7:53 PM
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Source_ID=540101,Updated=TO_TIMESTAMP('2016-08-19 19:53:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540130
;

-- Aug 19, 2016 7:58 PM
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='EXISTS
(
SELECT 1
	FROM C_Invoice i
	JOIN C_InvoiceLine il on i.C_Invoice_ID = il.C_Invoice_ID and i.C_Invoice_ID= @C_Invoice_ID/-1@
	JOIN M_InOutLine iol on il.M_InOutline_ID = iol.M_InOutline_ID
	JOIN M_InOut io on iol.M_InOut_ID = io.M_InOut_ID

	WHERE 
		
		M_InOut.M_InOut_ID = iol.M_InOut_ID
		AND i.IsSOTrx = M_InOut.IsSOTrx
		AND i.IsSOTrx = ''Y''
)',Updated=TO_TIMESTAMP('2016-08-19 19:58:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540584
;

