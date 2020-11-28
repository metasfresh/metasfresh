-- 2018-05-24T13:56:10.775
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@DocBaseType@=''SOO'' | @DocBaseType@=''POO'' | @DocBaseType@=''ARI'' | @DocBaseType@=''ARC'' | @DocBaseType@=''MOP'' | @DocBaseType@=''MMR'' | @DocBaseType@=''MMS'' | @DocBaseType@=''API'' | @DocBaseType@=''MMI'' | @DocBaseType@ = ''APC''',Updated=TO_TIMESTAMP('2018-05-24 13:56:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=2581
;


-- 2018-05-24T14:31:24.627
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541647,148,TO_TIMESTAMP('2018-05-24 14:31:24','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.invoice','Y','RefundInvoice',TO_TIMESTAMP('2018-05-24 14:31:24','YYYY-MM-DD HH24:MI:SS'),100,'RI','RefundInvoice')
;

-- 2018-05-24T14:31:24.639
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Ref_List_ID=541647 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2018-05-24T14:32:12.140
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List SET Name='Rückvergütungsrechnung',Updated=TO_TIMESTAMP('2018-05-24 14:32:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=541647
;

-- 2018-05-24T14:32:20.993
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-05-24 14:32:20','YYYY-MM-DD HH24:MI:SS'),Name='Refund Invoice' WHERE AD_Ref_List_ID=541647 AND AD_Language='en_US'
;

-- 2018-05-24T14:32:23.346
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-05-24 14:32:23','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Ref_List_ID=541647 AND AD_Language='en_US'
;

-- 2018-05-24T14:32:29.073
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-05-24 14:32:29','YYYY-MM-DD HH24:MI:SS'),Name='Rückvergütungsrechnung' WHERE AD_Ref_List_ID=541647 AND AD_Language='nl_NL'
;

-- 2018-05-24T14:32:34.273
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-05-24 14:32:34','YYYY-MM-DD HH24:MI:SS'),Name='Rückvergütungsrechnung' WHERE AD_Ref_List_ID=541647 AND AD_Language='de_CH'
;

-- 2018-05-24T14:33:03.031
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541648,148,TO_TIMESTAMP('2018-05-24 14:33:02','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.invoice','Y','Rückvergütungsgutschrift',TO_TIMESTAMP('2018-05-24 14:33:02','YYYY-MM-DD HH24:MI:SS'),100,'RC','Rückvergütungsgutschrift')
;

-- 2018-05-24T14:33:03.034
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Ref_List_ID=541648 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2018-05-24T14:33:30.017
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-05-24 14:33:30','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Refund Creditmemo' WHERE AD_Ref_List_ID=541648 AND AD_Language='en_US'
;

-- 2018-05-24T14:33:42.026
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List SET ValueName='Rückvergütungsrechnung',Updated=TO_TIMESTAMP('2018-05-24 14:33:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=541647
;




-- 2018-05-24T15:32:58.469
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code=' (''@DocBaseType@''=''ARI'' AND AD_Ref_List.Value IN (''AQ'', ''AP''))
 OR (''@DocBaseType@''=''ARC'' AND AD_Ref_List.Value IN (''CQ'', ''CR'',''CS'', ''RI'', ''RC''))
 OR (''@DocBaseType@'' IN(''API'', ''MOP'') AND AD_Ref_List.Value IN (''QI'', ''VI''))
OR (''@DocBaseType@'' = ''MMI'' AND AD_Ref_List.Value = ''MD'')
 OR (''@DocBaseType@'' NOT IN (''API'', ''ARI'', ''ARC'', ''MOP'') AND AD_Ref_List.Value NOT IN (''AQ'', ''AP'', ''CQ'', ''CR'', ''QI'')) /* fallback for the rest of the entries */
',Updated=TO_TIMESTAMP('2018-05-24 15:32:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540219
;


