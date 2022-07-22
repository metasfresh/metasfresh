-- 2021-12-10T14:29:27.990Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541527,TO_TIMESTAMP('2021-12-10 16:29:27','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','Location',TO_TIMESTAMP('2021-12-10 16:29:27','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2021-12-10T14:29:27.997Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541527 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2021-12-10T14:30:51.948Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy,WhereClause) VALUES (0,3434,0,541527,293,TO_TIMESTAMP('2021-12-10 16:30:51','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N',TO_TIMESTAMP('2021-12-10 16:30:51','YYYY-MM-DD HH24:MI:SS'),100,'c_bpartner_location_id in (     select bplocation.c_bpartner_location_id     from c_bpartner_location bplocation inner join ad_user bpConatact on bplocation.c_bpartner_id = bpConatact.c_bpartner_id     where           (bpConatact.isbilltocontact_default <> ''Y'' OR (bpConatact.isbilltocontact_default = ''Y'' and bplocation.isbillto = ''Y''))        AND (bpConatact.isshiptocontact_default <> ''Y'' OR (bpConatact.isshiptocontact_default = ''Y'' and bplocation.isshipto = ''Y''))       AND bpConatact.ad_user_id = @AD_User_ID@')
;

-- 2021-12-10T14:32:07.458Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_ID=18, AD_Reference_Value_ID=541527, IsExcludeFromZoomTargets='Y',Updated=TO_TIMESTAMP('2021-12-10 16:32:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=8746
;

-- 2021-12-10T14:34:44.780Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='c_bpartner_location_id in (select bplocation.c_bpartner_location_id   from c_bpartner_location bplocation inner join ad_user bpConatact on bplocation.c_bpartner_id = bpConatact.c_bpartner_id  where  (bpConatact.isbilltocontact_default <> ''Y'' OR (bpConatact.isbilltocontact_default = ''Y'' and bplocation.isbillto = ''Y''))  AND (bpConatact.isshiptocontact_default <> ''Y'' OR (bpConatact.isshiptocontact_default = ''Y'' and bplocation.isshipto = ''Y''))  AND bpConatact.ad_user_id = @AD_User_ID@',Updated=TO_TIMESTAMP('2021-12-10 16:34:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541527
;

-- 2021-12-10T14:34:47.139Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET IsValueDisplayed='Y',Updated=TO_TIMESTAMP('2021-12-10 16:34:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541527
;

-- 2021-12-10T14:37:52.426Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='c_bpartner_location_id in (select bplocation.c_bpartner_location_id from c_bpartner_location bplocation inner join AD_User bpContact on bplocation.c_bpartner_id = bpContact.c_bpartner_id  where  (bpContact.isbilltocontact_default <> ''Y'' OR (bpContact.isbilltocontact_default = ''Y'' and bplocation.isbillto = ''Y''))  AND (bpContact.isshiptocontact_default <> ''Y'' OR (bpContact.isshiptocontact_default = ''Y'' and bplocation.isshipto = ''Y''))  AND bpContact.ad_user_id = @AD_User_ID@',Updated=TO_TIMESTAMP('2021-12-10 16:37:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541527
;

-- 2021-12-10T14:41:02.353Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='c_bpartner_location_id in (select bplocation.c_bpartner_location_id from c_bpartner_location bplocation inner join AD_User bpContact on bplocation.c_bpartner_id = bpContact.c_bpartner_id  where  (bpContact.isbilltocontact_default <> ''Y'' OR (bpContact.isbilltocontact_default = ''Y'' and bplocation.isbillto = ''Y''))  AND (bpContact.isshiptocontact_default <> ''Y'' OR (bpContact.isshiptocontact_default = ''Y'' and bplocation.isshipto = ''Y''))  AND bpContact.ad_user_id = @AD_User_ID@)',Updated=TO_TIMESTAMP('2021-12-10 16:41:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541527
;

-- 2021-12-10T14:43:07.113Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Reference SET Name='ContactLocation',Updated=TO_TIMESTAMP('2021-12-10 16:43:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541527
;

-- 2021-12-10T14:43:58.140Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Reference_Trl SET Name='ContactLocation',Updated=TO_TIMESTAMP('2021-12-10 16:43:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Reference_ID=541527
;

-- 2021-12-10T14:43:59.996Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Reference_Trl SET Name='ContactLocation',Updated=TO_TIMESTAMP('2021-12-10 16:43:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Reference_ID=541527
;

-- 2021-12-10T14:44:01.507Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Reference_Trl SET Name='ContactLocation',Updated=TO_TIMESTAMP('2021-12-10 16:44:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Reference_ID=541527
;

-- 2021-12-10T14:44:03.737Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Reference_Trl SET Name='ContactLocation',Updated=TO_TIMESTAMP('2021-12-10 16:44:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Reference_ID=541527
;

-- 2021-12-10T14:45:12.329Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('ad_user','C_BPartner_Location_ID','NUMERIC(10)',null,null)
;

-- 2021-12-10T15:00:22.212Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsAutoApplyValidationRule='Y',Updated=TO_TIMESTAMP('2021-12-10 17:00:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=8746
;

-- 2021-12-10T15:03:43.900Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='c_bpartner_location_id in (     select bplocation.c_bpartner_location_id     from c_bpartner_location bplocation inner join ad_user bpConatact on bplocation.c_bpartner_id = bpConatact.c_bpartner_id     where           (bpConatact.isbilltocontact_default <> ''Y'' OR (bpConatact.isbilltocontact_default = ''Y'' and bplocation.isbillto = ''Y''))        AND (@isshiptocontact_default@ <> ''Y'' OR (@isshiptocontact_default@ = ''Y'' and bplocation.isshipto = ''Y''))       AND bpConatact.ad_user_id = @AD_User_ID@)',Updated=TO_TIMESTAMP('2021-12-10 17:03:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541527
;

-- 2021-12-10T15:05:35.824Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='c_bpartner_location_id in (     select bplocation.c_bpartner_location_id     from c_bpartner_location bplocation inner join ad_user bpConatact on bplocation.c_bpartner_id = bpConatact.c_bpartner_id     where           (bpConatact.isbilltocontact_default <> ''Y'' OR (bpConatact.isbilltocontact_default = ''Y'' and bplocation.isbillto = ''Y''))        AND (@IsShipToContact_Default@ <> ''Y'' OR (@IsShipToContact_Default@ = ''Y'' and bplocation.isshipto = ''Y''))       AND bpConatact.ad_user_id = @AD_User_ID@)',Updated=TO_TIMESTAMP('2021-12-10 17:05:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541527
;

-- 2021-12-10T15:07:15.588Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='c_bpartner_location_id in (     select bplocation.c_bpartner_location_id     from c_bpartner_location bplocation inner join ad_user bpConatact on bplocation.c_bpartner_id = bpConatact.c_bpartner_id     where           (bpConatact.isbilltocontact_default <> ''Y'' OR (bpConatact.isbilltocontact_default = ''Y'' and bplocation.isbillto = ''Y''))        AND (''@IsShipToContact_Default@'' <> ''Y'' OR (''@IsShipToContact_Default@'' = ''Y'' and bplocation.isshipto = ''Y''))       AND bpConatact.ad_user_id = @AD_User_ID@)',Updated=TO_TIMESTAMP('2021-12-10 17:07:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541527
;

-- 2021-12-10T15:09:26.997Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='c_bpartner_location_id in (     select bplocation.c_bpartner_location_id     from c_bpartner_location bplocation inner join ad_user bpConatact on bplocation.c_bpartner_id = bpConatact.c_bpartner_id     where           (''@IsBillToContact_Default@'' <> ''Y'' OR (''@IsBillToContact_Default@'' = ''Y'' and bplocation.isbillto = ''Y''))        AND (''@IsShipToContact_Default@'' <> ''Y'' OR (''@IsShipToContact_Default@'' = ''Y'' and bplocation.isshipto = ''Y''))       AND bpConatact.ad_user_id = @AD_User_ID@)',Updated=TO_TIMESTAMP('2021-12-10 17:09:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541527
;

-- 2021-12-10T16:22:48.639Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET IsValueDisplayed='N', WhereClause='c_bpartner_location_id in (     select bplocation.c_bpartner_location_id     from c_bpartner_location bplocation inner join ad_user bpConatact on bplocation.c_bpartner_id = bpConatact.c_bpartner_id     where           (               ( (''@IsBillToContact_Default@'' <> ''Y'' OR (''@IsBillToContact_Default@'' = ''Y'' and bplocation.isbillto = ''Y''))                 AND                 (''@IsShipToContact_Default@'' <> ''Y'' OR (''@IsShipToContact_Default@'' = ''Y'' and bplocation.isshipto = ''Y''))               )               OR               (''@IsBillToContact_Default@'' = ''Y'' AND ''@IsShipToContact_Default@'' = ''Y'' AND (bplocation.isshipto = ''Y'' OR bplocation.isbillto = ''Y''))           )       AND bpConatact.ad_user_id = @AD_User_ID@)',Updated=TO_TIMESTAMP('2021-12-10 18:22:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541527
;

