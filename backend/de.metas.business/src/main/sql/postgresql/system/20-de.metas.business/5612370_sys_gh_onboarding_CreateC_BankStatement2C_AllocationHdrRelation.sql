

-- 2021-11-04T12:50:03.620Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541469,TO_TIMESTAMP('2021-11-04 13:50:03','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','RelType_Source_C_BankStatment_C_AllocationHdr',TO_TIMESTAMP('2021-11-04 13:50:03','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2021-11-04T12:50:03.621Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541469 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2021-11-04T12:50:28.519Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy) VALUES (0,4909,0,541469,392,TO_TIMESTAMP('2021-11-04 13:50:28','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N',TO_TIMESTAMP('2021-11-04 13:50:28','YYYY-MM-DD HH24:MI:SS'),100)
;


-- 2021-11-05T14:40:26.230Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541477,TO_TIMESTAMP('2021-11-05 15:40:23','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','RelType_Source_C_AllocationHdr -> C_BankStatement',TO_TIMESTAMP('2021-11-05 15:40:23','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2021-11-05T14:40:26.232Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541477 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2021-11-05T14:40:51.917Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy) VALUES (0,12310,0,541477,735,TO_TIMESTAMP('2021-11-05 15:40:51','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N',TO_TIMESTAMP('2021-11-05 15:40:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-11-05T14:42:27.398Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541478,TO_TIMESTAMP('2021-11-05 15:42:15','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','RelType_Target_C_AllocationHdr -> C_BankStatement',TO_TIMESTAMP('2021-11-05 15:42:15','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2021-11-05T14:42:27.399Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541478 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2021-11-05T14:45:04.114Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy,WhereClause) VALUES (0,4909,0,541478,392,194,TO_TIMESTAMP('2021-11-05 15:45:04','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N',TO_TIMESTAMP('2021-11-05 15:45:04','YYYY-MM-DD HH24:MI:SS'),100,'c_bankstatement.c_bankstatement_id in (    select cb.c_bankstatement_id       from c_bankstatement cb        join c_bankstatementline cbl on cb.c_bankstatement_id  = cbl.c_bankstatement_id           join c_allocationline cal on cbl.c_payment_id  = cal.c_payment_id        where cal.c_allocationhdr_id=@C_AllocationHdr_ID/-1@ )')
;

-- 2021-11-05T14:53:41.974Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541479,TO_TIMESTAMP('2021-11-05 15:53:41','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','RelType_Target_C_BankStatment->C_AllocationHdr',TO_TIMESTAMP('2021-11-05 15:53:41','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2021-11-05T14:53:41.975Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541479 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2021-11-05T14:54:47.591Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy,WhereClause) VALUES (0,12310,0,541479,735,TO_TIMESTAMP('2021-11-05 15:54:47','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N',TO_TIMESTAMP('2021-11-05 15:54:47','YYYY-MM-DD HH24:MI:SS'),100,' C_AllocationHdr.C_AllocationHdr_ID IN (     select ca.c_allocationhdr_id     from c_allocationhdr ca                inner join c_allocationline cal on ca.c_allocationhdr_id = cal.c_allocationhdr_id              inner join c_bankstatementline bsl on cal.c_payment_id  = bsl.c_payment_id    where bsl.c_bankstatement_id =@C_BankStatement_ID/-1@  ) ')
;

-- 2021-11-05T14:55:59.939Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Source_ID,AD_Reference_Target_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsTableRecordIdTarget,Name,Updated,UpdatedBy) VALUES (0,0,541469,541479,540322,TO_TIMESTAMP('2021-11-05 15:55:54','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','C_BankStatement->C_AllocationHdr',TO_TIMESTAMP('2021-11-05 15:55:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-11-05T14:57:29.914Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET AD_Window_ID=194,Updated=TO_TIMESTAMP('2021-11-05 15:57:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541469
;


-- 2021-11-05T15:09:03.200Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541480,TO_TIMESTAMP('2021-11-05 16:08:52','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','RelType_Source_C_AllocationHdr->C_BankStatement',TO_TIMESTAMP('2021-11-05 16:08:52','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2021-11-05T15:09:03.201Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541480 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2021-11-05T15:09:26.864Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy) VALUES (0,12310,0,541480,735,TO_TIMESTAMP('2021-11-05 16:09:26','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N',TO_TIMESTAMP('2021-11-05 16:09:26','YYYY-MM-DD HH24:MI:SS'),100)
;



-- 2021-11-05T15:13:35.506Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Source_ID,AD_Reference_Target_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsTableRecordIdTarget,Name,Updated,UpdatedBy) VALUES (0,0,541477,541478,540323,TO_TIMESTAMP('2021-11-05 16:13:34','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','C_AllocationHdr->C_BankStatement  ',TO_TIMESTAMP('2021-11-05 16:13:34','YYYY-MM-DD HH24:MI:SS'),100)
;

