
-- 2020-04-06T13:03:49.075Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET IsDirected='Y',Updated=TO_TIMESTAMP('2020-04-06 16:03:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540147
;

-- 2020-04-06T13:05:44.079Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,IsActive,Created,CreatedBy,IsOrderByValue,Updated,UpdatedBy,AD_Reference_ID,ValidationType,Name,AD_Org_ID,EntityType) VALUES (0,'Y',TO_TIMESTAMP('2020-04-06 16:05:43','YYYY-MM-DD HH24:MI:SS'),100,'N',TO_TIMESTAMP('2020-04-06 16:05:43','YYYY-MM-DD HH24:MI:SS'),100,541125,'T','C_BankStatement -> C_PaySelection',0,'D')
;

-- 2020-04-06T13:05:44.081Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Help,Name,Description, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Reference_ID, t.Help,t.Name,t.Description, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Reference_ID=541125 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2020-04-06T13:06:46.468Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Reference_ID,AD_Key,AD_Client_ID,IsActive,Created,CreatedBy,Updated,IsValueDisplayed,AD_Window_ID,UpdatedBy,AD_Table_ID,AD_Org_ID,EntityType) VALUES (541125,4909,0,'Y',TO_TIMESTAMP('2020-04-06 16:06:46','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2020-04-06 16:06:46','YYYY-MM-DD HH24:MI:SS'),'N',194,100,392,0,'D')
;

-- 2020-04-06T13:08:07.520Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,IsActive,Created,CreatedBy,IsOrderByValue,Updated,UpdatedBy,AD_Reference_ID,ValidationType,Name,AD_Org_ID,EntityType) VALUES (0,'Y',TO_TIMESTAMP('2020-04-06 16:08:07','YYYY-MM-DD HH24:MI:SS'),100,'N',TO_TIMESTAMP('2020-04-06 16:08:07','YYYY-MM-DD HH24:MI:SS'),100,541126,'T','RelType C_BankStatement -> C_PaySelection',0,'D')
;

-- 2020-04-06T13:08:07.521Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Help,Name,Description, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Reference_ID, t.Help,t.Name,t.Description, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Reference_ID=541126 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2020-04-06T13:12:15.744Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Reference_ID,AD_Key,WhereClause,AD_Client_ID,IsActive,Created,CreatedBy,Updated,IsValueDisplayed,AD_Window_ID,UpdatedBy,AD_Table_ID,AD_Org_ID,EntityType) VALUES (541126,5609,'exists (     select 1     from C_PaySelection ps     join C_PaySelectionLine psl on ps.C_PaySelection_ID = psl.C_PaySelection_ID     join C_BankStatementLine bsl on psl.C_BankStatementLine_ID = bsl.C_BankStatementLine_ID     join C_BankStatement bs on bsl.C_BankStatement_ID = bs.C_BankStatement_ID     where         C_PaySelection.C_BankStatement_ID = bs.C_BankStatement_ID and         (ps.C_PaySelection_ID = @C_PaySelection_ID/-1@ or bs.C_BankStatement_ID = @C_BankStatement_ID/-1@) ) ',0,'Y',TO_TIMESTAMP('2020-04-06 16:12:15','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2020-04-06 16:12:15','YYYY-MM-DD HH24:MI:SS'),'N',206,100,426,0,'D')
;

-- 2020-04-06T13:12:46.461Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='exists (     select 1      ) ',Updated=TO_TIMESTAMP('2020-04-06 16:12:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541126
;

-- 2020-04-06T13:13:46.971Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_RelationType (Created,CreatedBy,IsActive,AD_Client_ID,Updated,UpdatedBy,AD_RelationType_ID,AD_Reference_Target_ID,IsTableRecordIdTarget,IsDirected,AD_Reference_Source_ID,Name,AD_Org_ID,EntityType) VALUES (TO_TIMESTAMP('2020-04-06 16:13:46','YYYY-MM-DD HH24:MI:SS'),100,'Y',0,TO_TIMESTAMP('2020-04-06 16:13:46','YYYY-MM-DD HH24:MI:SS'),100,540241,541126,'N','Y',541125,'C_BankStatement -> C_PaySelection',0,'de.metas.swat')
;

-- 2020-04-06T13:14:09.116Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='exists (     select 1     from C_PaySelection ps     join C_PaySelectionLine psl on ps.C_PaySelection_ID = psl.C_PaySelection_ID     join C_BankStatementLine bsl on psl.C_BankStatementLine_ID = bsl.C_BankStatementLine_ID     join C_BankStatement bs on bsl.C_BankStatement_ID = bs.C_BankStatement_ID     where         C_PaySelection.C_BankStatement_ID = bs.C_BankStatement_ID and         (ps.C_PaySelection_ID = @C_PaySelection_ID/-1@ or bs.C_BankStatement_ID = @C_BankStatement_ID/-1@) ) ',Updated=TO_TIMESTAMP('2020-04-06 16:14:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541126
;

-- 2020-04-06T13:15:45.779Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='exists (     select 1     from C_PaySelection ps     join C_PaySelectionLine psl on ps.C_PaySelection_ID = psl.C_PaySelection_ID     join C_BankStatementLine bsl on psl.C_BankStatementLine_ID = bsl.C_BankStatementLine_ID     join C_BankStatement bs on bsl.C_BankStatement_ID = bs.C_BankStatement_ID     where         C_PaySelection.C_PaySelection_ID = ps.C_PaySelection_ID and         (ps.C_PaySelection_ID = @C_PaySelection_ID/-1@ or bs.C_BankStatement_ID = @C_BankStatement_ID/-1@) ) ',Updated=TO_TIMESTAMP('2020-04-06 16:15:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541126
;

