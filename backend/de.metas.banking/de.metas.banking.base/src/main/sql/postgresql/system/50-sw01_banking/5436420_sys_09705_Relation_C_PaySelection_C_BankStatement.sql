-- 08.01.2016 11:17
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsDirected,IsExplicit,Name,Updated,UpdatedBy) VALUES (0,0,540147,TO_TIMESTAMP('2016-01-08 11:17:55','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.banking','Y','N','N','C_PaySelection -> C_BankStatement',TO_TIMESTAMP('2016-01-08 11:17:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 08.01.2016 11:19
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540624,TO_TIMESTAMP('2016-01-08 11:19:03','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.banking','Y','N','C_PaySelection -> C_BankStatement',TO_TIMESTAMP('2016-01-08 11:19:03','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 08.01.2016 11:19
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540624 AND NOT EXISTS (SELECT * FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 08.01.2016 13:03
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy,WhereClause) VALUES (0,5609,0,540624,426,TO_TIMESTAMP('2016-01-08 13:03:11','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.banking','Y','N',TO_TIMESTAMP('2016-01-08 13:03:11','YYYY-MM-DD HH24:MI:SS'),100,'exists
(
	select 1
	from C_PaySelection ps
	join C_PaySelectionLine psl on ps.C_PaySelection_ID = psl.C_PaySelection_ID
	join C_BankStatementLine bsl on psl.C_BankStatementLine_ID = bsl.C_BankStatementLine_ID
	join C_BankStatement bs on bsl.C_BankStatement_ID = bs.C_BankStatement_ID
	where
		C_PaySelection.C_PaySelection_ID = ps.C_PaySelection_ID and
		(ps.C_PaySelection_ID = @C_PaySelection/-1@ or bs.C_BankStatement_ID = @C_BankStatement_ID/-1@)
)')
;

-- 08.01.2016 13:03
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Source_ID=540624,Updated=TO_TIMESTAMP('2016-01-08 13:03:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540147
;

-- 08.01.2016 13:04
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540625,TO_TIMESTAMP('2016-01-08 13:04:27','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.banking','Y','N','RelType C_PaySelection -> C_BankStatement',TO_TIMESTAMP('2016-01-08 13:04:27','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 08.01.2016 13:04
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540625 AND NOT EXISTS (SELECT * FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 08.01.2016 13:04
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy) VALUES (0,4909,0,540625,392,TO_TIMESTAMP('2016-01-08 13:04:54','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.banking','Y','N',TO_TIMESTAMP('2016-01-08 13:04:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 08.01.2016 13:05
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='
exists
(
	select 1
	from C_PaySelection ps
	join C_PaySelectionLine psl on ps.C_PaySelection_ID = psl.C_PaySelection_ID
	join C_BankStatementLine bsl on psl.C_BankStatementLine_ID = bsl.C_BankStatementLine_ID
	join C_BankStatement bs on bsl.C_BankStatement_ID = bs.C_BankStatement_ID
	where
		C_BankStatement.C_BankStatement_ID = bs.C_BankStatement_ID and
		(ps.C_PaySelection_ID = @C_PaySelection/-1@ or bs.C_BankStatement_ID = @C_BankStatement_ID/-1@)
)
',Updated=TO_TIMESTAMP('2016-01-08 13:05:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540625
;

-- 08.01.2016 13:06
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET AD_Window_ID=194,Updated=TO_TIMESTAMP('2016-01-08 13:06:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540625
;

-- 08.01.2016 13:06
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET AD_Window_ID=206,Updated=TO_TIMESTAMP('2016-01-08 13:06:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540624
;

-- 08.01.2016 13:06
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Target_ID=540625,Updated=TO_TIMESTAMP('2016-01-08 13:06:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540147
;

-- 08.01.2016 13:36
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='exists
(
	select 1
	from C_PaySelection ps
	join C_PaySelectionLine psl on ps.C_PaySelection_ID = psl.C_PaySelection_ID
	join C_BankStatementLine bsl on psl.C_BankStatementLine_ID = bsl.C_BankStatementLine_ID
	join C_BankStatement bs on bsl.C_BankStatement_ID = bs.C_BankStatement_ID
	where
		C_PaySelection.C_PaySelection_ID = ps.C_PaySelection_ID and
		(ps.C_PaySelection_ID = @C_PaySelection_ID/-1@ or bs.C_BankStatement_ID = @C_BankStatement_ID/-1@)
)',Updated=TO_TIMESTAMP('2016-01-08 13:36:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540624
;

-- 08.01.2016 13:37
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='
exists
(
	select 1
	from C_PaySelection ps
	join C_PaySelectionLine psl on ps.C_PaySelection_ID = psl.C_PaySelection_ID
	join C_BankStatementLine bsl on psl.C_BankStatementLine_ID = bsl.C_BankStatementLine_ID
	join C_BankStatement bs on bsl.C_BankStatement_ID = bs.C_BankStatement_ID
	where
		C_BankStatement.C_BankStatement_ID = bs.C_BankStatement_ID and
		(ps.C_PaySelection_ID = @C_PaySelection_ID/-1@ or bs.C_BankStatement_ID = @C_BankStatement_ID/-1@)
)
',Updated=TO_TIMESTAMP('2016-01-08 13:37:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540625
;


