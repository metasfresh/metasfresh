--
-- C_BPartner.C_BP_Group_ID
--
-- 2019-05-15T21:57:29.140
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,IsActive,Created,CreatedBy,IsOrderByValue,Updated,UpdatedBy,AD_Reference_ID,ValidationType,Name,AD_Org_ID,EntityType) VALUES (0,'Y',TO_TIMESTAMP('2019-05-15 21:57:28','YYYY-MM-DD HH24:MI:SS'),100,'N',TO_TIMESTAMP('2019-05-15 21:57:28','YYYY-MM-DD HH24:MI:SS'),100,540996,'T','C_BP_Group_Default_First',0,'D')
;

-- 2019-05-15T21:57:29.143
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Help,Name,Description, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Reference_ID, t.Help,t.Name,t.Description, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Reference_ID=540996 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2019-05-15T21:59:00.286
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Reference_ID,AD_Key,OrderByClause,AD_Client_ID,IsActive,Created,CreatedBy,Updated,IsValueDisplayed,UpdatedBy,AD_Table_ID,AD_Org_ID,EntityType) VALUES (540996,4961,'C_BP_Group.IsDefault DESC',0,'Y',TO_TIMESTAMP('2019-05-15 21:59:00','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2019-05-15 21:59:00','YYYY-MM-DD HH24:MI:SS'),'N',100,394,0,'D')
;

-- 2019-05-15T22:03:40.154
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_ID=18, AD_Reference_Value_ID=540996, IsAutoApplyValidationRule='Y',Updated=TO_TIMESTAMP('2019-05-15 22:03:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=4940
;

-- 2019-05-15T22:09:32.047
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Val_Rule (Code,AD_Client_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,AD_Val_Rule_ID,AD_Org_ID,EntityType,Type,Name) VALUES ('true
',0,'Y',TO_TIMESTAMP('2019-05-15 22:09:31','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2019-05-15 22:09:31','YYYY-MM-DD HH24:MI:SS'),100,540439,0,'D','S','C_BP_Group_ID any')
;

-- 2019-05-15T22:09:42.515
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Val_Rule_ID=540439,Updated=TO_TIMESTAMP('2019-05-15 22:09:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=4940
;

--
-- M_Product.C_UOM_ID
--
-- 2019-05-15T22:16:37.991
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET OrderByClause='C_UOM.IsDefault DESC',Updated=TO_TIMESTAMP('2019-05-15 22:16:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=114
;

-- 2019-05-15T22:16:56.285
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Reference SET Name='C_UOM_Default_First',Updated=TO_TIMESTAMP('2019-05-15 22:16:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=114
;

-- 2019-05-15T22:17:43.459
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Val_Rule (Code,AD_Client_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,AD_Val_Rule_ID,AD_Org_ID,EntityType,Type,Name) VALUES ('true',0,'Y',TO_TIMESTAMP('2019-05-15 22:17:43','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2019-05-15 22:17:43','YYYY-MM-DD HH24:MI:SS'),100,540440,0,'D','S','C_UOM any')
;

-- 2019-05-15T22:17:57.952
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Val_Rule_ID=540440, AD_Reference_ID=18, AD_Reference_Value_ID=114, IsAutoApplyValidationRule='Y',Updated=TO_TIMESTAMP('2019-05-15 22:17:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=1760
;
