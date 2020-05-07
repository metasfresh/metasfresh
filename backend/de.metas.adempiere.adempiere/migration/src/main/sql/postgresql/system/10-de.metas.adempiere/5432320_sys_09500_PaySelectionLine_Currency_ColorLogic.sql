

-- 02.11.2015 19:18
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,552811,193,0,19,427,'N','C_Currency_ID',TO_TIMESTAMP('2015-11-02 19:18:42','YYYY-MM-DD HH24:MI:SS'),100,'N','Die Währung für diesen Eintrag','de.metas.banking',10,'Bezeichnet die auf Dokumenten oder Berichten verwendete Währung','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Währung',0,TO_TIMESTAMP('2015-11-02 19:18:42','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 02.11.2015 19:18
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=552811 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 02.11.2015 19:18
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET EntityType='D',Updated=TO_TIMESTAMP('2015-11-02 19:18:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552811
;

-- 02.11.2015 19:20
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnSQL='(select c_currency_id from C_BP_BankAccount where C_BP_BankAccount.C_BP_BankAccount_ID = C_PaySelectionLine.C_BP_BankAccount_ID)', IsUpdateable='N',Updated=TO_TIMESTAMP('2015-11-02 19:20:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552811
;

-- 02.11.2015 19:20
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsLazyLoading='Y',Updated=TO_TIMESTAMP('2015-11-02 19:20:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552811
;

-- 02.11.2015 19:21
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,Updated,UpdatedBy) VALUES (0,552811,556385,0,353,175,TO_TIMESTAMP('2015-11-02 19:21:56','YYYY-MM-DD HH24:MI:SS'),100,NULL,0,'D',NULL,0,'Y','N','Y','Y','N','N','N','N','Y','Währung',91,91,0,TO_TIMESTAMP('2015-11-02 19:21:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 02.11.2015 19:21
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=556385 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 02.11.2015 19:27
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET ColorLogic=' select case when @C_Currency_ID@ = (Select C_Currency_ID from C_BP_BankAccount where c_bankaccount_id = (select C_BankAccount_ID from C_PaySelection where C_PaySelection_DI = @C_PaySelection_ID@))
 then -1 else (select ad_color_id from ad_color where name = ''Red'')',Updated=TO_TIMESTAMP('2015-11-02 19:27:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556385
;


-- 02.11.2015 19:37
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET ColorLogic=' select case when @C_Currency_ID@ = (Select C_Currency_ID from C_BP_BankAccount where c_bankaccount_id = (select C_BankAccount_ID from C_PaySelection where C_PaySelection_DI = @C_PaySelection_ID@))
 then -1 else (select ad_color_id from ad_color where name = ''Rot'')  end as ad_color_id',Updated=TO_TIMESTAMP('2015-11-02 19:37:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556385
;

-- 02.11.2015 19:39
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET ColorLogic=' select case when @C_Currency_ID@ = (Select C_Currency_ID from C_BP_BankAccount where c_bp_bankaccount_id = (select C_BP_BankAccount_ID from C_PaySelection where C_PaySelection_ID = @C_PaySelection_ID@))
 then -1 else (select ad_color_id from ad_color where name = ''Rot'')  end as ad_color_id',Updated=TO_TIMESTAMP('2015-11-02 19:39:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556385
;

