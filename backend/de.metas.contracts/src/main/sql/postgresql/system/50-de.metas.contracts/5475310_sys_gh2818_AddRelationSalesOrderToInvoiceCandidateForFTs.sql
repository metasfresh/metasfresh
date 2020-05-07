-- 2017-10-24T15:05:43.437
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Source_ID,AD_Reference_Target_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsDirected,Name,Updated,UpdatedBy) VALUES (0,0,540666,540755,540193,TO_TIMESTAMP('2017-10-24 15:05:43','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','Y','C_Order -> C_Invoice_Candidate',TO_TIMESTAMP('2017-10-24 15:05:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-10-24T15:08:45.080
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Target_ID=540266,Updated=TO_TIMESTAMP('2017-10-24 15:08:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540193
;

-- 2017-10-24T15:15:49.091
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540760,TO_TIMESTAMP('2017-10-24 15:15:48','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','N','C_Order->C_Invoice_Candidate',TO_TIMESTAMP('2017-10-24 15:15:48','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2017-10-24T15:15:49.098
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540760 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2017-10-24T15:16:54.826
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Display,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy,WhereClause) VALUES (0,548618,544906,0,540760,540270,540092,TO_TIMESTAMP('2017-10-24 15:16:54','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','N',TO_TIMESTAMP('2017-10-24 15:16:54','YYYY-MM-DD HH24:MI:SS'),100,'C_Invoice_Candidate_ID IN
(
	SELECT C_Invoice_Candidate_ID FROM C_Invoice_Candidate ic
	JOIN C_OrderLine ol on ic.C_OrderLine_Term_ID = ol.C_OrderLine_ID
	JOIN C_Order o on ol.C_Order_ID = o.C_Order_ID
	WHERE @C_Order_ID@ = @C_Order_ID/-1@ 
	AND ic.C_Invoice_Candidate_ID = C_Invoice_Candidate_ID.C_Invoice_Candidate_ID
)')
;

-- 2017-10-24T15:17:06.139
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Target_ID=540760,Updated=TO_TIMESTAMP('2017-10-24 15:17:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540193
;

-- 2017-10-24T15:19:43.120
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='C_Invoice_Candidate_ID IN
(
	SELECT C_Invoice_Candidate_ID FROM C_Invoice_Candidate ic
	JOIN C_OrderLine ol on ic.C_OrderLine_ID = ol.C_OrderLine_ID
	JOIN C_Order o on ol.C_Order_ID = o.C_Order_ID
	WHERE @C_Order_ID@ = @C_Order_ID/-1@ 
	AND ic.C_Invoice_Candidate_ID = C_Invoice_Candidate_ID.C_Invoice_Candidate_ID
)',Updated=TO_TIMESTAMP('2017-10-24 15:19:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540760
;

-- 2017-10-24T15:29:41.974
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='C_Invoice_Candidate_ID IN
(
	SELECT C_Invoice_Candidate_ID FROM C_Invoice_Candidate ic
	JOIN C_OrderLine ol on ic.C_OrderLine_ID = ol.C_OrderLine_ID
	JOIN C_Order o on ol.C_Order_ID = o.C_Order_ID
	WHERE @C_Order_ID@ = @C_Order_ID/-1@ 
	AND ic.C_Invoice_Candidate_ID = C_Invoice_Candidate_ID.C_Invoice_Candidate_ID
)
OR 
C_Invoice_Candidate_ID IN
(
	SELECT C_Invoice_Candidate_ID FROM C_Invoice_Candidate ic
	JOIN C_Flatrate_Term ft on (ft.C_Flatrate_Term_ID = ic.Record_ID AND ic.AD_table_ID = 540320)
	JOIN C_OrderLine ol on ft.C_OrderLine_Term_ID = ol.C_OrderLine_ID
	JOIN C_Order o on ol.C_Order_ID = o.C_Order_ID
	WHERE o.C_Order_ID = @C_Order_ID/-1@ 
	AND ic.C_Invoice_Candidate_ID = C_Invoice_Candidate.C_Invoice_Candidate_ID
)',Updated=TO_TIMESTAMP('2017-10-24 15:29:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540760
;

-- 2017-10-24T15:34:22.943
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='C_Invoice_Candidate_ID IN
(
	SELECT C_Invoice_Candidate_ID FROM C_Invoice_Candidate ic
	JOIN C_OrderLine ol on ic.C_OrderLine_ID = ol.C_OrderLine_ID
	JOIN C_Order o on ol.C_Order_ID = o.C_Order_ID
	WHERE o.C_Order_ID = @C_Order_ID/-1@ 
	AND ic.C_Invoice_Candidate_ID = C_Invoice_Candidate.C_Invoice_Candidate_ID
)
OR 
C_Invoice_Candidate_ID IN
(
	SELECT C_Invoice_Candidate_ID FROM C_Invoice_Candidate ic
	JOIN C_Flatrate_Term ft on (ft.C_Flatrate_Term_ID = ic.Record_ID AND ic.AD_table_ID = 540320)
	JOIN C_OrderLine ol on ft.C_OrderLine_Term_ID = ol.C_OrderLine_ID
	JOIN C_Order o on ol.C_Order_ID = o.C_Order_ID
	WHERE o.C_Order_ID = @C_Order_ID/-1@ 
	AND ic.C_Invoice_Candidate_ID = C_Invoice_Candidate.C_Invoice_Candidate_ID
)',Updated=TO_TIMESTAMP('2017-10-24 15:34:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540760
;

-- 2017-10-24T15:39:39.949
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Reference SET EntityType='de.metas.contracts',Updated=TO_TIMESTAMP('2017-10-24 15:39:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540760
;

-- 2017-10-24T15:39:54.674
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET EntityType='de.metas.contracts',Updated=TO_TIMESTAMP('2017-10-24 15:39:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540193
;

