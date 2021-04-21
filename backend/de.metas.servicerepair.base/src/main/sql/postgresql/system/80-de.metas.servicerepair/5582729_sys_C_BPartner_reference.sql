-- NOTE: copied from 5577730_sys_gh10595_Add_RemittanceAdvice_tabels.sql line 573

DO
$$
    BEGIN
        IF exists(SELECT 1 from ad_reference where ad_reference_id=541252)
        THEN
            RAISE NOTICE 'AD_Reference_ID=541252 already exists. Skip creating it!';
        ELSE




-- 2021-01-28T12:01:52.823Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541252,TO_TIMESTAMP('2021-01-28 14:01:52','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','C_BPartner',TO_TIMESTAMP('2021-01-28 14:01:52','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2021-01-28T12:01:52.826Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541252 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2021-01-28T12:02:52.034Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy) VALUES (0,2893,0,541252,291,123,TO_TIMESTAMP('2021-01-28 14:02:52','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N',TO_TIMESTAMP('2021-01-28 14:02:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-01-28T12:05:04.444Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='C_BPartner.isActive=''Y''',Updated=TO_TIMESTAMP('2021-01-28 14:05:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541252
;


        END IF;
    END
$$
;
