-- 2022-07-28T13:25:29.526Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541614,TO_TIMESTAMP('2022-07-28 15:25:29','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','C_BP_BankAccount_OwnBankAccounts',TO_TIMESTAMP('2022-07-28 15:25:29','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2022-07-28T13:25:29.529Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541614 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2022-07-28T13:41:05.828Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy,WhereClause) VALUES (0,3094,0,541614,298,TO_TIMESTAMP('2022-07-28 15:41:05','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N',TO_TIMESTAMP('2022-07-28 15:41:05','YYYY-MM-DD HH24:MI:SS'),100,'EXISTS (select 1 from C_BPartner bp where bp.C_BPartner_ID=C_BP_BankAccount.C_BPartner_ID AND AD_OrgBP_ID is not null)')
;

-- 2022-07-28T13:43:21.961Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_ID=18, AD_Reference_Value_ID=541614, IsExcludeFromZoomTargets='Y',Updated=TO_TIMESTAMP('2022-07-28 15:43:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=5298
;

-- 2022-07-28T14:20:10.266Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='EXISTS(SELECT 1 from C_BPartner bp where bp.C_BPartner_ID = C_BP_BankAccount.C_BPartner_ID AND C_BP_BankAccount.isactive = ''Y'' AND C_BP_BankAccount.ad_org_id != 0 AND bp.AD_OrgBP_ID IS NOT NULL)',Updated=TO_TIMESTAMP('2022-07-28 16:20:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541614
;

-- 2022-07-28T14:25:13.254Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_Value_ID=541614, IsExcludeFromZoomTargets='Y',Updated=TO_TIMESTAMP('2022-07-28 16:25:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=4917
;
