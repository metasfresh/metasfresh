-- 2020-08-31T14:22:21.636Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,BeforeChangeCode,BeforeChangeCodeType,BeforeChangeWarning,Created,CreatedBy,EntityType,ErrorMsg,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy,WhereClause) VALUES (0,540550,0,114,'IsBillToContact_Default=''N''','SQLS','Möchten sie wirklich die default BillTo ändern?',TO_TIMESTAMP('2020-08-31 17:22:21','YYYY-MM-DD HH24:MI:SS'),100,'D','Only one default BillTo contact may be activated. If changed, the previous standard sales contact automatically removes the hook.','Y','Y','AD_User_IsBillContact_Default_Non_Active','N',TO_TIMESTAMP('2020-08-31 17:22:21','YYYY-MM-DD HH24:MI:SS'),100,'C_BPartner_ID IS NOT NULL AND IsBillToContact_Default=''N''')
;

-- 2020-08-31T14:22:21.640Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Index_Table_ID=540550 AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 2020-08-31T14:22:37.186Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,5844,541037,540550,0,TO_TIMESTAMP('2020-08-31 17:22:36','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',10,TO_TIMESTAMP('2020-08-31 17:22:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-08-31T14:23:02.145Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,557164,541038,540550,0,TO_TIMESTAMP('2020-08-31 17:23:01','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',20,TO_TIMESTAMP('2020-08-31 17:23:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-08-31T14:23:38.104Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Index_Table SET IsUnique='N',Updated=TO_TIMESTAMP('2020-08-31 17:23:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Table_ID=540550
;

-- 2020-08-31T14:23:59.703Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Index_Table SET WhereClause='C_BPartner_ID IS NOT NULL AND IsBillToContact_Default=''Y'' AND IsActive=''N''',Updated=TO_TIMESTAMP('2020-08-31 17:23:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Table_ID=540550
;

-- 2020-08-31T14:24:14.637Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Index_Table SET IsUnique='N', WhereClause='C_BPartner_ID IS NOT NULL AND IsBillToContact_Default=''Y'' AND IsActive=''Y''',Updated=TO_TIMESTAMP('2020-08-31 17:24:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Table_ID=540407
;

-- 2020-08-31T14:24:29.092Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DROP INDEX IF EXISTS ad_user_isbillcontact_default
;

-- 2020-08-31T14:24:29.094Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE INDEX AD_User_IsBillContact_Default ON AD_User (C_BPartner_ID,IsBillToContact_Default) WHERE C_BPartner_ID IS NOT NULL AND IsBillToContact_Default='Y' AND IsActive='Y'
;

-- 2020-08-31T14:24:29.108Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE OR REPLACE FUNCTION AD_User_IsBillContact_Default_tgfn()
 RETURNS TRIGGER AS $AD_User_IsBillContact_Default_tg$
 BEGIN
 IF TG_OP='INSERT' THEN
UPDATE AD_User SET IsBillToContact_Default='N', Updated=NEW.Updated, UpdatedBy=NEW.UpdatedBy WHERE 1=1  AND C_BPartner_ID=NEW.C_BPartner_ID AND IsBillToContact_Default=NEW.IsBillToContact_Default AND AD_User_ID<>NEW.AD_User_ID AND C_BPartner_ID IS NOT NULL AND IsBillToContact_Default='Y' AND IsActive='Y';
 ELSE
IF OLD.C_BPartner_ID<>NEW.C_BPartner_ID OR OLD.IsBillToContact_Default<>NEW.IsBillToContact_Default THEN
UPDATE AD_User SET IsBillToContact_Default='N', Updated=NEW.Updated, UpdatedBy=NEW.UpdatedBy WHERE 1=1  AND C_BPartner_ID=NEW.C_BPartner_ID AND IsBillToContact_Default=NEW.IsBillToContact_Default AND AD_User_ID<>NEW.AD_User_ID AND C_BPartner_ID IS NOT NULL AND IsBillToContact_Default='Y' AND IsActive='Y';
 END IF;
 END IF;
 RETURN NEW;
 END;
 $AD_User_IsBillContact_Default_tg$ LANGUAGE plpgsql;
;

-- 2020-08-31T14:24:29.112Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DROP TRIGGER IF EXISTS AD_User_IsBillContact_Default_tg ON AD_User
;

-- 2020-08-31T14:24:29.112Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE TRIGGER AD_User_IsBillContact_Default_tg BEFORE INSERT OR UPDATE  ON AD_User FOR EACH ROW EXECUTE PROCEDURE AD_User_IsBillContact_Default_tgfn()
;

-- 2020-08-31T14:25:01.273Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE INDEX AD_User_IsBillContact_Default_Non_Active ON AD_User (C_BPartner_ID,IsBillToContact_Default) WHERE C_BPartner_ID IS NOT NULL AND IsBillToContact_Default='Y' AND IsActive='N'
;

-- 2020-08-31T14:25:01.280Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE OR REPLACE FUNCTION AD_User_IsBillContact_Default_Non_Active_tgfn()
 RETURNS TRIGGER AS $AD_User_IsBillContact_Default_Non_Active_tg$
 BEGIN
 IF TG_OP='INSERT' THEN
UPDATE AD_User SET IsBillToContact_Default='N', Updated=NEW.Updated, UpdatedBy=NEW.UpdatedBy WHERE 1=1  AND C_BPartner_ID=NEW.C_BPartner_ID AND IsBillToContact_Default=NEW.IsBillToContact_Default AND AD_User_ID<>NEW.AD_User_ID AND C_BPartner_ID IS NOT NULL AND IsBillToContact_Default='Y' AND IsActive='N';
 ELSE
IF OLD.C_BPartner_ID<>NEW.C_BPartner_ID OR OLD.IsBillToContact_Default<>NEW.IsBillToContact_Default THEN
UPDATE AD_User SET IsBillToContact_Default='N', Updated=NEW.Updated, UpdatedBy=NEW.UpdatedBy WHERE 1=1  AND C_BPartner_ID=NEW.C_BPartner_ID AND IsBillToContact_Default=NEW.IsBillToContact_Default AND AD_User_ID<>NEW.AD_User_ID AND C_BPartner_ID IS NOT NULL AND IsBillToContact_Default='Y' AND IsActive='N';
 END IF;
 END IF;
 RETURN NEW;
 END;
 $AD_User_IsBillContact_Default_Non_Active_tg$ LANGUAGE plpgsql;
;

-- 2020-08-31T14:25:01.286Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DROP TRIGGER IF EXISTS AD_User_IsBillContact_Default_Non_Active_tg ON AD_User
;

-- 2020-08-31T14:25:01.286Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE TRIGGER AD_User_IsBillContact_Default_Non_Active_tg BEFORE INSERT OR UPDATE  ON AD_User FOR EACH ROW EXECUTE PROCEDURE AD_User_IsBillContact_Default_Non_Active_tgfn()
;

