-- 01.09.2015 11:10
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Source_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsDirected,IsExplicit,Name,Updated,UpdatedBy) VALUES (0,0,540158,540115,TO_TIMESTAMP('2015-09-01 11:10:26','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','Y','N','M_ShipmentSchedule -> Shipment',TO_TIMESTAMP('2015-09-01 11:10:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 01.09.2015 11:10
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540553,TO_TIMESTAMP('2015-09-01 11:10:52','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','N','M_ShipmentSchedule',TO_TIMESTAMP('2015-09-01 11:10:52','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 01.09.2015 11:10
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540553 AND NOT EXISTS (SELECT * FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 01.09.2015 11:11
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy) VALUES (0,500232,0,540553,500221,TO_TIMESTAMP('2015-09-01 11:11:14','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','N',TO_TIMESTAMP('2015-09-01 11:11:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 01.09.2015 11:14
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Source_ID=540553,Updated=TO_TIMESTAMP('2015-09-01 11:14:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540115
;

-- 01.09.2015 11:16
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Target_ID=540299,Updated=TO_TIMESTAMP('2015-09-01 11:16:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540115
;

-- 01.09.2015 11:19
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540554,TO_TIMESTAMP('2015-09-01 11:19:11','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','N','RelType M_ShipmentSchedule->M_InOut',TO_TIMESTAMP('2015-09-01 11:19:11','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 01.09.2015 11:19
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540554 AND NOT EXISTS (SELECT * FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 01.09.2015 11:30
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Display,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,OrderByClause,Updated,UpdatedBy) VALUES (0,3791,3521,0,540554,319,TO_TIMESTAMP('2015-09-01 11:30:43','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','N','DocumentNo',TO_TIMESTAMP('2015-09-01 11:30:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 01.09.2015 11:57
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='
M_InOut.M_InOut_ID 
	IN(
		SELECT iol.M_InOut_ID 
		FROM M_InOutLine iol 
		INNER JOIN M_ShipmentSchedule_QtyPicked sqp ON iol.M_InOutLine_ID = sqp.M_InOutLine_ID 
		WHERE sqp.M_ShipmentSchedule_ID = @M_ShipmentSchedule_ID@
	)',Updated=TO_TIMESTAMP('2015-09-01 11:57:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540554
;

-- 01.09.2015 11:58
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Target_ID=540554,Updated=TO_TIMESTAMP('2015-09-01 11:58:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540115
;

-- 01.09.2015 11:58
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET Role_Source='Shipment',Updated=TO_TIMESTAMP('2015-09-01 11:58:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540115
;

-- 01.09.2015 12:16
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540555,TO_TIMESTAMP('2015-09-01 12:16:44','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','N','Rechnungsdisposition - Invoice Candidate',TO_TIMESTAMP('2015-09-01 12:16:44','YYYY-MM-DD HH24:MI:SS'),100,'D')
;

-- 01.09.2015 12:16
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540555 AND NOT EXISTS (SELECT * FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 01.09.2015 12:21
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Source_ID,AD_Reference_Target_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsDirected,IsExplicit,Name,Role_Source,Updated,UpdatedBy) VALUES (0,0,540266,540554,540116,TO_TIMESTAMP('2015-09-01 12:21:45','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','Y','N','C_InvoiceCandidate -> Invoice','Invoice',TO_TIMESTAMP('2015-09-01 12:21:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 01.09.2015 12:22
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Reference_Trl WHERE AD_Reference_ID=540555
;

-- 01.09.2015 12:22
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Reference WHERE AD_Reference_ID=540555
;

-- 01.09.2015 12:23
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540556,TO_TIMESTAMP('2015-09-01 12:23:21','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','N','RelType C_Invoice_Candidate->C_Invoice',TO_TIMESTAMP('2015-09-01 12:23:21','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 01.09.2015 12:23
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540556 AND NOT EXISTS (SELECT * FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 01.09.2015 12:24
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Display,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,OrderByClause,Updated,UpdatedBy,WhereClause) VALUES (0,3492,3484,0,540556,318,TO_TIMESTAMP('2015-09-01 12:24:24','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','N','DocumentNo',TO_TIMESTAMP('2015-09-01 12:24:24','YYYY-MM-DD HH24:MI:SS'),100,'
C_Invoice.C_Invoice_ID 
	IN(
		SELECT il.C_Invoice_ID 
		FROM C_InvoiceLine il 
		INNER JOIN C_Invoice_Line_Alloc ila ON il.C_InvoiceLine_ID = ila.C_InvoiceLine_ID 
		WHERE ila.C_InvoiceCandidate_ID = @C_InvoiceCandidate_ID@
	)')
;

-- 01.09.2015 12:25
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Target_ID=540556,Updated=TO_TIMESTAMP('2015-09-01 12:25:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540116
;

-- 01.09.2015 12:29
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='
C_Invoice.C_Invoice_ID 
	IN(
		SELECT il.C_Invoice_ID 
		FROM C_InvoiceLine il 
		INNER JOIN C_Invoice_Line_Alloc ila ON il.C_InvoiceLine_ID = ila.C_InvoiceLine_ID 
		WHERE ila.C_Invoice_Candidate_ID = @C_Invoice_Candidate_ID@
	)',Updated=TO_TIMESTAMP('2015-09-01 12:29:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540556
;





-- 01.09.2015 13:13
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540557,TO_TIMESTAMP('2015-09-01 13:13:40','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','N','RelType M_InOut -> M_ShipmentSchedule',TO_TIMESTAMP('2015-09-01 13:13:40','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 01.09.2015 13:13
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540557 AND NOT EXISTS (SELECT * FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 01.09.2015 13:14
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy) VALUES (0,500232,0,540557,500221,TO_TIMESTAMP('2015-09-01 13:14:15','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','N',TO_TIMESTAMP('2015-09-01 13:14:15','YYYY-MM-DD HH24:MI:SS'),100)
;



-- 01.09.2015 13:23
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='
	
	M_ShipmentSchedule.M_ShipmentSchedule_ID 
	IN(
		SELECT ss.M_ShipmentSchedule_ID 
		FROM M_ShipmentSchedule ss 
		INNER JOIN M_ShipmentSchedule_QtyPicked sqp ON ss.M_ShipmentSchedule_ID = sqp.M_ShipmentSchedule_ID 
		INNER JOIN M_InOutLine iol ON sqp.M_InOutLine_ID = iol.M_InOutLine_ID
		WHERE iol.M_InOut_ID = @M_InOut_ID@
	)',Updated=TO_TIMESTAMP('2015-09-01 13:23:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540557
;

-- 01.09.2015 13:24
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Source_ID=540557,Updated=TO_TIMESTAMP('2015-09-01 13:24:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540115
;

-- 01.09.2015 13:25
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET Role_Target='Shipment',Updated=TO_TIMESTAMP('2015-09-01 13:25:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540115
;

-- 01.09.2015 13:26
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET Role_Source='ShipmentSchedule',Updated=TO_TIMESTAMP('2015-09-01 13:26:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540115
;




-- 01.09.2015 14:34
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='M_ShipmentSchedule_ID 
	IN(
		SELECT sqp.M_ShipmentSchedule_ID 
		FROM M_ShipmentSchedule_QtyPicked sqp 
		INNER JOIN M_InOutLine iol ON sqp.M_InOutLine_ID = iol.M_InOutLine_ID
		WHERE iol.M_InOut_ID = @M_InOut_ID@ )',Updated=TO_TIMESTAMP('2015-09-01 14:34:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540557
;

-- 01.09.2015 14:35
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='M_ShipmentSchedule_ID 
	IN(
		SELECT sqp.M_ShipmentSchedule_ID 
		FROM M_ShipmentSchedule_QtyPicked sqp 
		INNER JOIN M_InOutLine iol ON sqp.M_InOutLine_ID = iol.M_InOutLine_ID
		WHERE iol.M_InOut_ID = @M_InOut_ID@ )',Updated=TO_TIMESTAMP('2015-09-01 14:35:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540557
;






-- 01.09.2015 17:00
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET IsDirected='N',Updated=TO_TIMESTAMP('2015-09-01 17:00:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540115
;

-- 01.09.2015 17:11
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET IsDirected='Y',Updated=TO_TIMESTAMP('2015-09-01 17:11:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540115
;

-- 01.09.2015 17:15
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Source_ID=540058,Updated=TO_TIMESTAMP('2015-09-01 17:15:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540115
;

-- 01.09.2015 17:15
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Source_ID=540557,Updated=TO_TIMESTAMP('2015-09-01 17:15:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540115
;

-- 01.09.2015 17:16
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='M_InOut_ID 
	IN(
		SELECT iol.M_InOut_ID 
		FROM M_InOutLine iol 
		INNER JOIN M_ShipmentSchedule_QtyPicked sqp ON iol.M_InOutLine_ID = sqp.M_InOutLine_ID 
		WHERE sqp.M_ShipmentSchedule_ID = @M_ShipmentSchedule_ID@
	)',Updated=TO_TIMESTAMP('2015-09-01 17:16:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540554
;

-- 01.09.2015 17:16
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET IsDirected='N',Updated=TO_TIMESTAMP('2015-09-01 17:16:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540115
;

-- 01.09.2015 17:28
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Source_ID=540554, AD_Reference_Target_ID=540557,Updated=TO_TIMESTAMP('2015-09-01 17:28:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540115
;










-- 01.09.2015 17:42
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET Role_Source='Shipment', Role_Target='ShipmentSchedule',Updated=TO_TIMESTAMP('2015-09-01 17:42:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540115
;

-- 01.09.2015 17:59
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='
	EXISTS 
	(
		SELECT 1 FROM M_ShipmentSchedule ss 
		INNER JOIN M_ShipmentSchedule_QtyPicked sqp ON sqp.M_ShipmentSchedule_ID = ss.M_ShipmentSchedule_ID
		JOIN M_InOutLine iol ON sqp.M_InOutLine_ID = iol.M_InOutLine_ID 
		WHERE M_InOut.M_InOut_ID = iol.M_InOut_ID
			AND ss.M_ShipmentSchedule_ID = @M_ShipmentSchedule_ID@
	)',Updated=TO_TIMESTAMP('2015-09-01 17:59:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540554
;

-- 01.09.2015 18:03
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='	
	EXISTS 
	(
		SELECT 1 FROM M_InOut io
		JOIN M_InOutLine iol ON io.M_InOut_ID = iol.M_InOut_ID 
		JOIN M_ShipmentSchedule_QtyPicked sqp ON iol.M_InOutLine_ID = sqp.M_InOutLine_ID
		
		WHERE M_ShipmentSchedule.M_ShipmentSchedule_ID = sqp.M_ShipmentSchedule_ID
			AND io.M_InOut_ID = @M_InOut_ID@
	)',Updated=TO_TIMESTAMP('2015-09-01 18:03:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540557
;

-- 01.09.2015 18:06
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Source_ID=540557, AD_Reference_Target_ID=540554,Updated=TO_TIMESTAMP('2015-09-01 18:06:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540115
;

-- 01.09.2015 18:07
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='	
	EXISTS 
	(
		SELECT 1 FROM M_InOut io
		JOIN M_InOutLine iol ON io.M_InOut_ID = iol.M_InOut_ID ;
		JOIN M_ShipmentSchedule_QtyPicked sqp ON iol.M_InOutLine_ID = sqp.M_InOutLine_ID
		
		WHERE M_ShipmentSchedule.M_ShipmentSchedule_ID = sqp.M_ShipmentSchedule_ID
			AND io.M_InOut_ID = @M_InOut_ID@
	)',Updated=TO_TIMESTAMP('2015-09-01 18:07:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540557
;

-- 01.09.2015 18:08
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='
	EXISTS 
	(
		SELECT 1 FROM M_ShipmentSchedule ss ;
		INNER JOIN M_ShipmentSchedule_QtyPicked sqp ON sqp.M_ShipmentSchedule_ID = ss.M_ShipmentSchedule_ID
		JOIN M_InOutLine iol ON sqp.M_InOutLine_ID = iol.M_InOutLine_ID 
		WHERE M_InOut.M_InOut_ID = iol.M_InOut_ID
			AND ss.M_ShipmentSchedule_ID = @M_ShipmentSchedule_ID@
	)',Updated=TO_TIMESTAMP('2015-09-01 18:08:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540554
;

-- 01.09.2015 18:14
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='EXISTS 
	(
		SELECT 1 FROM M_InOut io
		JOIN M_InOutLine iol ON io.M_InOut_ID = iol.M_InOut_ID ;
		JOIN M_ShipmentSchedule_QtyPicked sqp ON iol.M_InOutLine_ID = sqp.M_InOutLine_ID
		
		WHERE M_ShipmentSchedule.M_ShipmentSchedule_ID = sqp.M_ShipmentSchedule_ID
			AND sqp.M_ShipmentSchedule_ID = @M_ShipmentSchedule_ID@
			
	)',Updated=TO_TIMESTAMP('2015-09-01 18:14:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540557
;

-- 01.09.2015 18:15
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='EXISTS 
	(
		SELECT 1 FROM M_InOut io
		JOIN M_InOutLine iol ON io.M_InOut_ID = iol.M_InOut_ID 
		JOIN M_ShipmentSchedule_QtyPicked sqp ON iol.M_InOutLine_ID = sqp.M_InOutLine_ID
		
		WHERE M_ShipmentSchedule.M_ShipmentSchedule_ID = sqp.M_ShipmentSchedule_ID
			AND sqp.M_ShipmentSchedule_ID = @M_ShipmentSchedule_ID@
			
	)',Updated=TO_TIMESTAMP('2015-09-01 18:15:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540557
;

-- 01.09.2015 18:15
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='
	EXISTS 
	(
		SELECT 1 FROM M_ShipmentSchedule ss 
		INNER JOIN M_ShipmentSchedule_QtyPicked sqp ON sqp.M_ShipmentSchedule_ID = ss.M_ShipmentSchedule_ID
		JOIN M_InOutLine iol ON sqp.M_InOutLine_ID = iol.M_InOutLine_ID 
		WHERE M_InOut.M_InOut_ID = iol.M_InOut_ID
			AND ss.M_ShipmentSchedule_ID = @M_ShipmentSchedule_ID@
	)',Updated=TO_TIMESTAMP('2015-09-01 18:15:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540554
;

-- 01.09.2015 18:16
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET Role_Source='ShipmentSchedule', Role_Target='Shipment',Updated=TO_TIMESTAMP('2015-09-01 18:16:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540115
;

-- 01.09.2015 18:17
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='	EXISTS 
	(
		SELECT 1 FROM M_ShipmentSchedule ss 
		INNER JOIN M_ShipmentSchedule_QtyPicked sqp ON sqp.M_ShipmentSchedule_ID = ss.M_ShipmentSchedule_ID
		JOIN M_InOutLine iol ON sqp.M_InOutLine_ID = iol.M_InOutLine_ID 
		WHERE M_InOut.M_InOut_ID = iol.M_InOut_ID
			AND iol.M_InOut_ID = @M_InOut_ID@
	)',Updated=TO_TIMESTAMP('2015-09-01 18:17:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540554
;

-- 01.09.2015 18:18
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='
	EXISTS 
	(
		SELECT 1 FROM M_ShipmentSchedule ss 
		INNER JOIN M_ShipmentSchedule_QtyPicked sqp ON sqp.M_ShipmentSchedule_ID = ss.M_ShipmentSchedule_ID
		JOIN M_InOutLine iol ON sqp.M_InOutLine_ID = iol.M_InOutLine_ID 
		WHERE M_InOut.M_InOut_ID = iol.M_InOut_ID
			AND ss.M_ShipmentSchedule_ID = @M_ShipmentSchedule_ID@
	)',Updated=TO_TIMESTAMP('2015-09-01 18:18:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540554
;
-- 01.09.2015 18:23
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='EXISTS 
	(
		SELECT 1 FROM M_InOut io
		JOIN M_InOutLine iol ON io.M_InOut_ID = iol.M_InOut_ID 
		JOIN M_ShipmentSchedule_QtyPicked sqp ON iol.M_InOutLine_ID = sqp.M_InOutLine_ID
		
		WHERE M_ShipmentSchedule.M_ShipmentSchedule_ID = sqp.M_ShipmentSchedule_ID
			AND sqp.M_ShipmentSchedule_ID = @M_ShipmentSchedule_ID@
			
	)',Updated=TO_TIMESTAMP('2015-09-01 18:23:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540554
;

-- 01.09.2015 18:23
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause=NULL,Updated=TO_TIMESTAMP('2015-09-01 18:23:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540557
;

-- 01.09.2015 18:24
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Source_ID=540553,Updated=TO_TIMESTAMP('2015-09-01 18:24:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540115
;

-- 01.09.2015 18:25
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Source_ID=540554, AD_Reference_Target_ID=337,Updated=TO_TIMESTAMP('2015-09-01 18:25:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540115
;

-- 01.09.2015 18:27
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET IsDirected='Y',Updated=TO_TIMESTAMP('2015-09-01 18:27:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540115
;

-- 01.09.2015 18:28
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Source_ID=540553, AD_Reference_Target_ID=540554,Updated=TO_TIMESTAMP('2015-09-01 18:28:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540115
;

-- 01.09.2015 18:29
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='EXISTS 
	(
		SELECT 1 FROM M_InOut io
		JOIN M_InOutLine iol ON io.M_InOut_ID = iol.M_InOut_ID 
		JOIN M_ShipmentSchedule_QtyPicked sqp ON iol.M_InOutLine_ID = sqp.M_InOutLine_ID
		
		WHERE  sqp.M_ShipmentSchedule_ID = @M_ShipmentSchedule_ID@
			
	)',Updated=TO_TIMESTAMP('2015-09-01 18:29:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540554
;

-- 01.09.2015 18:31
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='M_InOut.M_InOut_ID 
	IN(
		SELECT iol.M_InOut_ID 
		FROM M_InOutLine iol 
		INNER JOIN M_ShipmentSchedule_QtyPicked sqp ON iol.M_InOutLine_ID = sqp.M_InOutLine_ID 
		WHERE sqp.M_ShipmentSchedule_ID = @M_ShipmentSchedule_ID@
	)',Updated=TO_TIMESTAMP('2015-09-01 18:31:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540554
;

-- 01.09.2015 18:31
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsDirected,IsExplicit,Name,Updated,UpdatedBy) VALUES (0,0,540117,TO_TIMESTAMP('2015-09-01 18:31:55','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','Y','N','M_InOut -> ShipmentSchedule',TO_TIMESTAMP('2015-09-01 18:31:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 01.09.2015 18:31
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET IsDirected='N',Updated=TO_TIMESTAMP('2015-09-01 18:31:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540115
;

-- 01.09.2015 18:33
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Source_ID=337, AD_Reference_Target_ID=540557, IsDirected='N', Role_Source='Shipment', Role_Target='ShipmentSchedule',Updated=TO_TIMESTAMP('2015-09-01 18:33:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540117
;

-- 01.09.2015 18:34
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_RelationType WHERE AD_RelationType_ID=540117
;

-- 01.09.2015 18:34
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Source_ID,AD_Reference_Target_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsDirected,IsExplicit,Name,Role_Source,Role_Target,Updated,UpdatedBy) VALUES (0,0,540553,540554,540118,TO_TIMESTAMP('2015-09-01 18:34:38','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','N','N','M_InOut_ShipmentSchedule','ShipmentSchedule','Shipment',TO_TIMESTAMP('2015-09-01 18:34:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 01.09.2015 18:35
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Source_ID=540299, AD_Reference_Target_ID=540557, Role_Source='Shipment', Role_Target='ShipmentSchedule',Updated=TO_TIMESTAMP('2015-09-01 18:35:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540118
;

-- 01.09.2015 18:38
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='EXISTS 
	(
	SELECT 1 FROM M_InOutLine iol 
		INNER JOIN M_ShipmentSchedule_QtyPicked sqp ON iol.M_InOutLine_ID = sqp.M_InOutLine_ID 
		WHERE .M_InOut_ID = @M_InOut_ID@
	)',Updated=TO_TIMESTAMP('2015-09-01 18:38:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540557
;

-- 01.09.2015 18:39
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='EXISTS 
	(
	SELECT 1 FROM M_InOutLine iol 
		INNER JOIN M_ShipmentSchedule_QtyPicked sqp ON iol.M_InOutLine_ID = sqp.M_InOutLine_ID 
		WHERE iol.M_InOut_ID = @M_InOut_ID@
	)',Updated=TO_TIMESTAMP('2015-09-01 18:39:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540557
;

-- 01.09.2015 18:40
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='
	EXISTS 
	(
	SELECT 1 FROM M_InOutLine iol 
		INNER JOIN M_ShipmentSchedule_QtyPicked sqp ON iol.M_InOutLine_ID = sqp.M_InOutLine_ID 
		INNER JOIN M_ShipmentSchedule ss on sqp.M_ShipmentSchedule = ss.M_ShipmentSchedule
		WHERE iol.M_InOut_ID = @M_InOut_ID@
	)',Updated=TO_TIMESTAMP('2015-09-01 18:40:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540557
;

-- 01.09.2015 18:41
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='
	EXISTS 
	(
	SELECT 1 FROM M_InOutLine iol 
		INNER JOIN M_ShipmentSchedule_QtyPicked sqp ON iol.M_InOutLine_ID = sqp.M_InOutLine_ID 
		INNER JOIN M_ShipmentSchedule ss on sqp.M_ShipmentSchedule_ID = ss.M_ShipmentSchedule_ID
		WHERE iol.M_InOut_ID = @M_InOut_ID@
	)',Updated=TO_TIMESTAMP('2015-09-01 18:41:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540557
;

-- 01.09.2015 18:41
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='
	EXISTS 
	(
	SELECT 1 FROM M_InOutLine iol 
		INNER JOIN M_ShipmentSchedule_QtyPicked sqp ON iol.M_InOutLine_ID = sqp.M_InOutLine_ID 
		INNER JOIN M_ShipmentSchedule ss on sqp.M_ShipmentSchedule_ID = ss.M_ShipmentSchedule_ID
		WHERE sqp.M_InOut_ID = @M_InOut_ID@
	)',Updated=TO_TIMESTAMP('2015-09-01 18:41:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540557
;

-- 01.09.2015 18:43
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='
	EXISTS 
	(
	SELECT 1 FROM  M_ShipmentSchedule ss
	INNER JOIN M_ShipmentSchedule_QtyPicked sqp ON  ss.M_ShipmentSchedule_ID = sqp.M_ShipmentSchedule_ID
	INNER JOIN M_InOutLine iol ON sqp.M_InOutLine_ID = iol.M_InOutLine_ID
	
		WHERE iol.M_InOut_ID = @M_InOut_ID@
	)',Updated=TO_TIMESTAMP('2015-09-01 18:43:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540557
;

-- 01.09.2015 18:50
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540558,TO_TIMESTAMP('2015-09-01 18:50:12','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','N','M_ShipmentScheduleSourceForLieferung',TO_TIMESTAMP('2015-09-01 18:50:12','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 01.09.2015 18:50
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540558 AND NOT EXISTS (SELECT * FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 01.09.2015 18:50
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy,WhereClause) VALUES (0,500232,0,540558,500221,TO_TIMESTAMP('2015-09-01 18:50:33','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','N',TO_TIMESTAMP('2015-09-01 18:50:33','YYYY-MM-DD HH24:MI:SS'),100,'	EXISTS 
	(
		SELECT 1 FROM M_ShipmentSchedule ss 
		INNER JOIN M_ShipmentSchedule_QtyPicked sqp ON sqp.M_ShipmentSchedule_ID = ss.M_ShipmentSchedule_ID
		JOIN M_InOutLine iol ON sqp.M_InOutLine_ID = iol.M_InOutLine_ID 
		WHERE M_InOut.M_InOut_ID = iol.M_InOut_ID
			AND ss.M_ShipmentSchedule_ID = @M_ShipmentSchedule_ID@
	)')
;

-- 01.09.2015 18:51
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Source_ID=540558,Updated=TO_TIMESTAMP('2015-09-01 18:51:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540115
;

-- 01.09.2015 18:51
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='EXISTS 
	(
		SELECT 1 FROM M_InOut io
		JOIN M_InOutLine iol ON io.M_InOut_ID = iol.M_InOut_ID 
		JOIN M_ShipmentSchedule_QtyPicked sqp ON iol.M_InOutLine_ID = sqp.M_InOutLine_ID
		
		WHERE M_ShipmentSchedule.M_ShipmentSchedule_ID = sqp.M_ShipmentSchedule_ID
			AND sqp.M_ShipmentSchedule_ID = @M_ShipmentSchedule_ID@
			
	)',Updated=TO_TIMESTAMP('2015-09-01 18:51:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540554
;

-- 01.09.2015 18:58
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='	EXISTS 
	(
		SELECT 1 FROM M_ShipmentSchedule ss 
		INNER JOIN M_ShipmentSchedule_QtyPicked sqp ON sqp.M_ShipmentSchedule_ID = ss.M_ShipmentSchedule_ID
		JOIN M_InOutLine iol ON sqp.M_InOutLine_ID = iol.M_InOutLine_ID 
		WHERE ss.M_ShipmentSchedule_ID = @M_ShipmentSchedule_ID@
	)',Updated=TO_TIMESTAMP('2015-09-01 18:58:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540558
;

-- 01.09.2015 19:00
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='EXISTS 
	(
		SELECT 1 FROM M_InOut io
		JOIN M_InOutLine iol ON io.M_InOut_ID = iol.M_InOut_ID 
		JOIN M_ShipmentSchedule_QtyPicked sqp ON iol.M_InOutLine_ID = sqp.M_InOutLine_ID
		JOIN M_ShipmentSchedule ss on sqp.M_ShipmentSchedule_ID = ss.M_ShipmentSchedule
		
		WHERE M ss.M_ShipmentSchedule_ID = @M_ShipmentSchedule_ID@
			
	)',Updated=TO_TIMESTAMP('2015-09-01 19:00:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540554
;

-- 01.09.2015 19:02
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='EXISTS 
	(
		SELECT 1 FROM M_InOut io
		JOIN M_InOutLine iol ON io.M_InOut_ID = iol.M_InOut_ID 
		JOIN M_ShipmentSchedule_QtyPicked sqp ON iol.M_InOutLine_ID = sqp.M_InOutLine_ID
		JOIN M_ShipmentSchedule ss on sqp.M_ShipmentSchedule_ID = ss.M_ShipmentSchedule_ID
		
		WHERE M ss.M_ShipmentSchedule_ID = @M_ShipmentSchedule_ID@
			
	)',Updated=TO_TIMESTAMP('2015-09-01 19:02:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540554
;

-- 01.09.2015 19:02
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='EXISTS 
	(
		SELECT 1 FROM M_InOut io
		JOIN M_InOutLine iol ON io.M_InOut_ID = iol.M_InOut_ID 
		JOIN M_ShipmentSchedule_QtyPicked sqp ON iol.M_InOutLine_ID = sqp.M_InOutLine_ID
		JOIN M_ShipmentSchedule ss on sqp.M_ShipmentSchedule_ID = ss.M_ShipmentSchedule_ID
		
		WHERE  ss.M_ShipmentSchedule_ID = @M_ShipmentSchedule_ID@
			
	)',Updated=TO_TIMESTAMP('2015-09-01 19:02:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540554
;

-- 01.09.2015 19:05
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='	EXISTS 
	(
		SELECT 1 FROM M_ShipmentSchedule ss 
		INNER JOIN M_ShipmentSchedule_QtyPicked sqp ON sqp.M_ShipmentSchedule_ID = ss.M_ShipmentSchedule_ID
		JOIN M_InOutLine iol ON sqp.M_InOutLine_ID = iol.M_InOutLine_ID 
		JOIN M_InOut io ON iol.M_InOut_ID = io.M_InOut_ID
		WHERE ss.M_ShipmentSchedule_ID = @M_ShipmentSchedule_ID@
	)',Updated=TO_TIMESTAMP('2015-09-01 19:05:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540558
;

-- 01.09.2015 19:08
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='M_ShipmentSchedule.M_ShipmentSchedule_ID =   @M_ShipmentSchedule_ID@
	',Updated=TO_TIMESTAMP('2015-09-01 19:08:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540558
;

-- 01.09.2015 19:10
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='EXISTS 
	(
		SELECT 1 FROM M_InOut io
		JOIN M_InOutLine iol ON io.M_InOut_ID = iol.M_InOut_ID 
		JOIN M_ShipmentSchedule_QtyPicked sqp ON iol.M_InOutLine_ID = sqp.M_InOutLine_ID
		JOIN M_ShipmentSchedule ss on sqp.M_ShipmentSchedule_ID = ss.M_ShipmentSchedule_ID
		
		WHERE  ss.M_ShipmentSchedule_ID = @M_ShipmentSchedule_ID@
			AND M_InOut.M_InOut_ID = io.M_InOut_ID
			
	)',Updated=TO_TIMESTAMP('2015-09-01 19:10:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540554
;

-- 01.09.2015 19:11
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='
	EXISTS 
	(
	SELECT 1 FROM  M_ShipmentSchedule ss
	INNER JOIN M_ShipmentSchedule_QtyPicked sqp ON  ss.M_ShipmentSchedule_ID = sqp.M_ShipmentSchedule_ID
	INNER JOIN M_InOutLine iol ON sqp.M_InOutLine_ID = iol.M_InOutLine_ID
	
		WHERE iol.M_InOut_ID = @M_InOut_ID@
		AND M_ShipmentSchedule.M_ShipmentSchedule_ID =  ss.M_ShipmentSchedule_ID
	)',Updated=TO_TIMESTAMP('2015-09-01 19:11:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540557
;





-- 01.09.2015 19:14
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Source_ID=540553,Updated=TO_TIMESTAMP('2015-09-01 19:14:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540115
;

-- 01.09.2015 19:14
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Reference_Trl WHERE AD_Reference_ID=540558
;

-- 01.09.2015 19:14
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Reference WHERE AD_Reference_ID=540558
;






-- 01.09.2015 19:16
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='	
	
	EXISTS 
	(
		SELECT 1 FROM C_Invoice i
		INNER JOIN C_InvoiceLine il ON i.C_Invoice_ID = il.C_Invoice_ID
		INNER JOIN C_Invoice_Line_Alloc ila ON il.C_InvoiceLine_ID = ila.C_InvoiceLine_ID 
		WHERE ila.C_Invoice_Candidate_ID = @C_Invoice_Candidate_ID@
			AND C_Invoice.C_Invoice_ID = i.C_Invoice_ID
	)',Updated=TO_TIMESTAMP('2015-09-01 19:16:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540556
;

-- 01.09.2015 19:17
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Source_ID,AD_Reference_Target_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsDirected,IsExplicit,Name,Role_Source,Updated,UpdatedBy) VALUES (0,0,540266,540556,540119,TO_TIMESTAMP('2015-09-01 19:17:12','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','Y','N','C_Invoice -> InvoiceCandidate','Invoice',TO_TIMESTAMP('2015-09-01 19:17:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 01.09.2015 19:17
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET IsDirected='N',Updated=TO_TIMESTAMP('2015-09-01 19:17:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540116
;

-- 01.09.2015 19:19
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540559,TO_TIMESTAMP('2015-09-01 19:19:15','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','N','RelType C_Invoice->C_Invoice_Candidate',TO_TIMESTAMP('2015-09-01 19:19:15','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 01.09.2015 19:19
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540559 AND NOT EXISTS (SELECT * FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 01.09.2015 19:19
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy) VALUES (0,544906,0,540559,540270,TO_TIMESTAMP('2015-09-01 19:19:46','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','N',TO_TIMESTAMP('2015-09-01 19:19:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 01.09.2015 19:22
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='
	
	EXISTS 
	(
		SELECT 1 FROM C_Invoice_Candidate ic
		INNER JOIN C_Invoice_Line_Alloc ila ON ic.C_InvoiceLine_ID = ila.C_InvoiceLine_ID 
		INNER JOIN C_InvoiceLine il ON ila.C_InvoiceLine_ID = il.C_InvoiceLine_ID
		INNER JOIN C_Invoice i = il.C_Invoice_ID = i.C_Invoice_ID

		WHERE ila.C_Invoice_Candidate_ID = @C_Invoice_Candidate_ID@
			AND C_Invoice.C_Invoice_ID = i.C_Invoice_ID
	)',Updated=TO_TIMESTAMP('2015-09-01 19:22:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540559
;

-- 01.09.2015 19:22
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='
	
	EXISTS 
	(
		SELECT 1 FROM C_Invoice_Candidate ic
		INNER JOIN C_Invoice_Line_Alloc ila ON ic.C_InvoiceLine_ID = ila.C_InvoiceLine_ID 
		INNER JOIN C_InvoiceLine il ON ila.C_InvoiceLine_ID = il.C_InvoiceLine_ID
		INNER JOIN C_Invoice i  ON il.C_Invoice_ID = i.C_Invoice_ID

		WHERE ic.C_Invoice_Candidate_ID = @C_Invoice_Candidate_ID@
			AND C_Invoice.C_Invoice_ID = i.C_Invoice_ID
	)',Updated=TO_TIMESTAMP('2015-09-01 19:22:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540559
;

-- 01.09.2015 19:24
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='
	
	EXISTS 
	(
		SELECT 1 FROM C_Invoice_Candidate ic
		INNER JOIN C_Invoice_Line_Alloc ila ON ic.C_InvoiceLine_ID = ila.C_InvoiceLine_ID 
		INNER JOIN C_InvoiceLine il ON ila.C_InvoiceLine_ID = il.C_InvoiceLine_ID
		INNER JOIN C_Invoice i  ON il.C_Invoice_ID = i.C_Invoice_ID

		WHERE i.C_Invoice_D = @C_Invoice_ID@
			AND C_InvoiceCandidate.C_InvoiceCandidate_ID = ic._InvoiceCandidate_ID
	)',Updated=TO_TIMESTAMP('2015-09-01 19:24:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540559
;

-- 01.09.2015 19:24
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='
	
	EXISTS 
	(
		SELECT 1 FROM C_Invoice_Candidate ic
		INNER JOIN C_Invoice_Line_Alloc ila ON ic.C_InvoiceLine_ID = ila.C_InvoiceLine_ID 
		INNER JOIN C_InvoiceLine il ON ila.C_InvoiceLine_ID = il.C_InvoiceLine_ID
		INNER JOIN C_Invoice i  ON il.C_Invoice_ID = i.C_Invoice_ID

		WHERE i.C_Invoice_ID = @C_Invoice_ID@
			AND C_InvoiceCandidate.C_InvoiceCandidate_ID = ic._InvoiceCandidate_ID
	)',Updated=TO_TIMESTAMP('2015-09-01 19:24:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540559
;

-- 01.09.2015 19:25
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Source_ID=336, AD_Reference_Target_ID=540559, IsDirected='N', Role_Source=NULL,Updated=TO_TIMESTAMP('2015-09-01 19:25:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540119
;

-- 01.09.2015 19:26
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='
	
	EXISTS 
	(
		SELECT 1 FROM C_Invoice_Candidate ic
		INNER JOIN C_Invoice_Line_Alloc ila ON ic.C_Invoice_Candidate_ID = ila.ic.C_Invoice_Candidate_ID
		INNER JOIN C_InvoiceLine il ON ila.C_InvoiceLine_ID = il.C_InvoiceLine_ID
		INNER JOIN C_Invoice i  ON il.C_Invoice_ID = i.C_Invoice_ID

		WHERE i.C_Invoice_ID = @C_Invoice_ID@
			AND C_InvoiceCandidate.C_InvoiceCandidate_ID = ic._InvoiceCandidate_ID
	)',Updated=TO_TIMESTAMP('2015-09-01 19:26:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540559
;

-- 01.09.2015 19:26
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='
	
	EXISTS 
	(
		SELECT 1 FROM C_Invoice_Candidate ic
		INNER JOIN C_Invoice_Line_Alloc ila ON ic.C_Invoice_Candidate_ID = ila.C_Invoice_Candidate_ID
		INNER JOIN C_InvoiceLine il ON ila.C_InvoiceLine_ID = il.C_InvoiceLine_ID
		INNER JOIN C_Invoice i  ON il.C_Invoice_ID = i.C_Invoice_ID

		WHERE i.C_Invoice_ID = @C_Invoice_ID@
			AND C_InvoiceCandidate.C_InvoiceCandidate_ID = ic._InvoiceCandidate_ID
	)',Updated=TO_TIMESTAMP('2015-09-01 19:26:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540559
;

-- 01.09.2015 19:28
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='
	
	EXISTS 
	(
		SELECT 1 FROM C_Invoice_Candidate ic
		INNER JOIN C_Invoice_Line_Alloc ila ON ic.C_Invoice_Candidate_ID = ila.C_Invoice_Candidate_ID
		INNER JOIN C_InvoiceLine il ON ila.C_InvoiceLine_ID = il.C_InvoiceLine_ID
		INNER JOIN C_Invoice i  ON il.C_Invoice_ID = i.C_Invoice_ID

		WHERE i.C_Invoice_ID = @C_Invoice_ID@
			AND C_Invoice_Candidate.C_InvoiceCandidate_ID = ic._InvoiceCandidate_ID
	)',Updated=TO_TIMESTAMP('2015-09-01 19:28:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540559
;

-- 01.09.2015 19:28
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='
	
	EXISTS 
	(
		SELECT 1 FROM C_Invoice_Candidate ic
		INNER JOIN C_Invoice_Line_Alloc ila ON ic.C_Invoice_Candidate_ID = ila.C_Invoice_Candidate_ID
		INNER JOIN C_InvoiceLine il ON ila.C_InvoiceLine_ID = il.C_InvoiceLine_ID
		INNER JOIN C_Invoice i  ON il.C_Invoice_ID = i.C_Invoice_ID

		WHERE i.C_Invoice_ID = @C_Invoice_ID@
			AND C_Invoice_Candidate.C_Invoice_Candidate_ID = ic._Invoice_Candidate_ID
	)',Updated=TO_TIMESTAMP('2015-09-01 19:28:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540559
;

-- 01.09.2015 19:29
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='
	
	EXISTS 
	(
		SELECT 1 FROM C_Invoice_Candidate ic
		INNER JOIN C_Invoice_Line_Alloc ila ON ic.C_Invoice_Candidate_ID = ila.C_Invoice_Candidate_ID
		INNER JOIN C_InvoiceLine il ON ila.C_InvoiceLine_ID = il.C_InvoiceLine_ID
		INNER JOIN C_Invoice i  ON il.C_Invoice_ID = i.C_Invoice_ID

		WHERE i.C_Invoice_ID = @C_Invoice_ID@
			AND C_Invoice_Candidate.C_Invoice_Candidate_ID = ic.C_Invoice_Candidate_ID
	)',Updated=TO_TIMESTAMP('2015-09-01 19:29:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540559
;

