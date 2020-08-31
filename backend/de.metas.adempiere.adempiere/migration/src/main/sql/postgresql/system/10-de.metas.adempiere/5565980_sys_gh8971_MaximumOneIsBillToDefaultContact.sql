-- 2020-08-31T15:08:23.475Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Index_Table SET WhereClause='C_BPartner_ID IS NOT NULL AND IsBillToContact_Default=''Y''',Updated=TO_TIMESTAMP('2020-08-31 18:08:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Table_ID=540407
;

-- 2020-08-31T15:09:14.234Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DROP INDEX IF EXISTS ad_user_isbillcontact_default
;

-- 2020-08-31T15:09:14.239Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE INDEX AD_User_IsBillContact_Default ON AD_User (C_BPartner_ID,IsBillToContact_Default) WHERE C_BPartner_ID IS NOT NULL AND IsBillToContact_Default='Y'
;

-- 2020-08-31T15:09:14.252Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE OR REPLACE FUNCTION AD_User_IsBillContact_Default_tgfn()
    RETURNS TRIGGER AS $AD_User_IsBillContact_Default_tg$
BEGIN
    IF TG_OP='INSERT' THEN
        UPDATE AD_User SET IsBillToContact_Default='N', Updated=NEW.Updated, UpdatedBy=NEW.UpdatedBy WHERE 1=1  AND C_BPartner_ID=NEW.C_BPartner_ID AND IsBillToContact_Default=NEW.IsBillToContact_Default AND AD_User_ID<>NEW.AD_User_ID AND C_BPartner_ID IS NOT NULL AND IsBillToContact_Default='Y';
    ELSE
        IF OLD.C_BPartner_ID<>NEW.C_BPartner_ID OR OLD.IsBillToContact_Default<>NEW.IsBillToContact_Default THEN
            UPDATE AD_User SET IsBillToContact_Default='N', Updated=NEW.Updated, UpdatedBy=NEW.UpdatedBy WHERE 1=1  AND C_BPartner_ID=NEW.C_BPartner_ID AND IsBillToContact_Default=NEW.IsBillToContact_Default AND AD_User_ID<>NEW.AD_User_ID AND C_BPartner_ID IS NOT NULL AND IsBillToContact_Default='Y';
        END IF;
    END IF;
    RETURN NEW;
END;
$AD_User_IsBillContact_Default_tg$ LANGUAGE plpgsql;
;

-- 2020-08-31T15:09:14.256Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DROP TRIGGER IF EXISTS AD_User_IsBillContact_Default_tg ON AD_User
;

-- 2020-08-31T15:09:14.257Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE TRIGGER AD_User_IsBillContact_Default_tg BEFORE INSERT OR UPDATE  ON AD_User FOR EACH ROW EXECUTE PROCEDURE AD_User_IsBillContact_Default_tgfn()
;

-- 2020-08-31T15:09:37.350Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Index_Table SET IsUnique='Y',Updated=TO_TIMESTAMP('2020-08-31 18:09:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Table_ID=540407
;

-- 2020-08-31T15:09:49.462Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DROP INDEX IF EXISTS ad_user_isbillcontact_default
;

-- 2020-08-31T15:09:49.464Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE UNIQUE INDEX AD_User_IsBillContact_Default ON AD_User (C_BPartner_ID,IsBillToContact_Default) WHERE C_BPartner_ID IS NOT NULL AND IsBillToContact_Default='Y'
;

-- 2020-08-31T15:09:49.478Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE OR REPLACE FUNCTION AD_User_IsBillContact_Default_tgfn()
    RETURNS TRIGGER AS $AD_User_IsBillContact_Default_tg$
BEGIN
    IF TG_OP='INSERT' THEN
        UPDATE AD_User SET IsBillToContact_Default='N', Updated=NEW.Updated, UpdatedBy=NEW.UpdatedBy WHERE 1=1  AND C_BPartner_ID=NEW.C_BPartner_ID AND IsBillToContact_Default=NEW.IsBillToContact_Default AND AD_User_ID<>NEW.AD_User_ID AND C_BPartner_ID IS NOT NULL AND IsBillToContact_Default='Y';
    ELSE
        IF OLD.C_BPartner_ID<>NEW.C_BPartner_ID OR OLD.IsBillToContact_Default<>NEW.IsBillToContact_Default THEN
            UPDATE AD_User SET IsBillToContact_Default='N', Updated=NEW.Updated, UpdatedBy=NEW.UpdatedBy WHERE 1=1  AND C_BPartner_ID=NEW.C_BPartner_ID AND IsBillToContact_Default=NEW.IsBillToContact_Default AND AD_User_ID<>NEW.AD_User_ID AND C_BPartner_ID IS NOT NULL AND IsBillToContact_Default='Y';
        END IF;
    END IF;
    RETURN NEW;
END;
$AD_User_IsBillContact_Default_tg$ LANGUAGE plpgsql;
;

-- 2020-08-31T15:09:49.481Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DROP TRIGGER IF EXISTS AD_User_IsBillContact_Default_tg ON AD_User
;

-- 2020-08-31T15:09:49.482Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE TRIGGER AD_User_IsBillContact_Default_tg BEFORE INSERT OR UPDATE  ON AD_User FOR EACH ROW EXECUTE PROCEDURE AD_User_IsBillContact_Default_tgfn()
;
