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

-- 2020-08-31T15:54:15.296Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Index_Table SET WhereClause='C_BPartner_ID IS NOT NULL AND IsShipToContact_Default=''Y''',Updated=TO_TIMESTAMP('2020-08-31 18:54:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Table_ID=540408
;

-- 2020-08-31T15:54:27.705Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DROP INDEX IF EXISTS ad_user_isshiptocontact_default
;

-- 2020-08-31T15:54:27.708Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE UNIQUE INDEX AD_User_IsShipToContact_Default ON AD_User (C_BPartner_ID,IsShipToContact_Default) WHERE C_BPartner_ID IS NOT NULL AND IsShipToContact_Default='Y'
;

-- 2020-08-31T15:54:27.720Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE OR REPLACE FUNCTION AD_User_IsShipToContact_Default_tgfn()
    RETURNS TRIGGER AS $AD_User_IsShipToContact_Default_tg$
BEGIN
    IF TG_OP='INSERT' THEN
        UPDATE AD_User SET IsShipToContact_Default='N', Updated=NEW.Updated, UpdatedBy=NEW.UpdatedBy WHERE 1=1  AND C_BPartner_ID=NEW.C_BPartner_ID AND IsShipToContact_Default=NEW.IsShipToContact_Default AND AD_User_ID<>NEW.AD_User_ID AND C_BPartner_ID IS NOT NULL AND IsShipToContact_Default='Y';
    ELSE
        IF OLD.C_BPartner_ID<>NEW.C_BPartner_ID OR OLD.IsShipToContact_Default<>NEW.IsShipToContact_Default THEN
            UPDATE AD_User SET IsShipToContact_Default='N', Updated=NEW.Updated, UpdatedBy=NEW.UpdatedBy WHERE 1=1  AND C_BPartner_ID=NEW.C_BPartner_ID AND IsShipToContact_Default=NEW.IsShipToContact_Default AND AD_User_ID<>NEW.AD_User_ID AND C_BPartner_ID IS NOT NULL AND IsShipToContact_Default='Y';
        END IF;
    END IF;
    RETURN NEW;
END;
$AD_User_IsShipToContact_Default_tg$ LANGUAGE plpgsql;
;

-- 2020-08-31T15:54:27.723Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DROP TRIGGER IF EXISTS AD_User_IsShipToContact_Default_tg ON AD_User
;

-- 2020-08-31T15:54:27.724Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE TRIGGER AD_User_IsShipToContact_Default_tg BEFORE INSERT OR UPDATE  ON AD_User FOR EACH ROW EXECUTE PROCEDURE AD_User_IsShipToContact_Default_tgfn()
;

-- 2020-08-31T15:54:53.332Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Index_Table SET WhereClause='C_BPartner_ID IS NOT NULL AND IsPurchaseContact_Default=''Y''',Updated=TO_TIMESTAMP('2020-08-31 18:54:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Table_ID=540396
;

-- 2020-08-31T15:55:08.870Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DROP INDEX IF EXISTS ad_user_ispurchasecontact_default
;

-- 2020-08-31T15:55:08.871Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE UNIQUE INDEX AD_User_IsPurchaseContact_Default ON AD_User (C_BPartner_ID,IsPurchaseContact_Default) WHERE C_BPartner_ID IS NOT NULL AND IsPurchaseContact_Default='Y'
;

-- 2020-08-31T15:55:08.879Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE OR REPLACE FUNCTION AD_User_IsPurchaseContact_Default_tgfn()
    RETURNS TRIGGER AS $AD_User_IsPurchaseContact_Default_tg$
BEGIN
    IF TG_OP='INSERT' THEN
        UPDATE AD_User SET IsPurchaseContact_Default='N', Updated=NEW.Updated, UpdatedBy=NEW.UpdatedBy WHERE 1=1  AND C_BPartner_ID=NEW.C_BPartner_ID AND IsPurchaseContact_Default=NEW.IsPurchaseContact_Default AND AD_User_ID<>NEW.AD_User_ID AND C_BPartner_ID IS NOT NULL AND IsPurchaseContact_Default='Y';
    ELSE
        IF OLD.C_BPartner_ID<>NEW.C_BPartner_ID OR OLD.IsPurchaseContact_Default<>NEW.IsPurchaseContact_Default THEN
            UPDATE AD_User SET IsPurchaseContact_Default='N', Updated=NEW.Updated, UpdatedBy=NEW.UpdatedBy WHERE 1=1  AND C_BPartner_ID=NEW.C_BPartner_ID AND IsPurchaseContact_Default=NEW.IsPurchaseContact_Default AND AD_User_ID<>NEW.AD_User_ID AND C_BPartner_ID IS NOT NULL AND IsPurchaseContact_Default='Y';
        END IF;
    END IF;
    RETURN NEW;
END;
$AD_User_IsPurchaseContact_Default_tg$ LANGUAGE plpgsql;
;

-- 2020-08-31T15:55:08.882Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DROP TRIGGER IF EXISTS AD_User_IsPurchaseContact_Default_tg ON AD_User
;

-- 2020-08-31T15:55:08.883Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE TRIGGER AD_User_IsPurchaseContact_Default_tg BEFORE INSERT OR UPDATE  ON AD_User FOR EACH ROW EXECUTE PROCEDURE AD_User_IsPurchaseContact_Default_tgfn()
;

-- 2020-08-31T15:55:31.218Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Index_Table SET WhereClause='C_BPartner_ID IS NOT NULL AND IsSalesContact_Default=''Y''',Updated=TO_TIMESTAMP('2020-08-31 18:55:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Table_ID=540395
;

-- 2020-08-31T15:55:50.102Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DROP INDEX IF EXISTS ad_user_issalescontact_default
;

-- 2020-08-31T15:55:50.103Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE UNIQUE INDEX AD_User_IsSalesContact_Default ON AD_User (C_BPartner_ID,IsSalesContact_Default) WHERE C_BPartner_ID IS NOT NULL AND IsSalesContact_Default='Y'
;

-- 2020-08-31T15:55:50.118Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE OR REPLACE FUNCTION AD_User_IsSalesContact_Default_tgfn()
    RETURNS TRIGGER AS $AD_User_IsSalesContact_Default_tg$
BEGIN
    IF TG_OP='INSERT' THEN
        UPDATE AD_User SET IsSalesContact_Default='N', Updated=NEW.Updated, UpdatedBy=NEW.UpdatedBy WHERE 1=1  AND C_BPartner_ID=NEW.C_BPartner_ID AND IsSalesContact_Default=NEW.IsSalesContact_Default AND AD_User_ID<>NEW.AD_User_ID AND C_BPartner_ID IS NOT NULL AND IsSalesContact_Default='Y';
    ELSE
        IF OLD.C_BPartner_ID<>NEW.C_BPartner_ID OR OLD.IsSalesContact_Default<>NEW.IsSalesContact_Default THEN
            UPDATE AD_User SET IsSalesContact_Default='N', Updated=NEW.Updated, UpdatedBy=NEW.UpdatedBy WHERE 1=1  AND C_BPartner_ID=NEW.C_BPartner_ID AND IsSalesContact_Default=NEW.IsSalesContact_Default AND AD_User_ID<>NEW.AD_User_ID AND C_BPartner_ID IS NOT NULL AND IsSalesContact_Default='Y';
        END IF;
    END IF;
    RETURN NEW;
END;
$AD_User_IsSalesContact_Default_tg$ LANGUAGE plpgsql;
;

-- 2020-08-31T15:55:50.120Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DROP TRIGGER IF EXISTS AD_User_IsSalesContact_Default_tg ON AD_User
;

-- 2020-08-31T15:55:50.121Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE TRIGGER AD_User_IsSalesContact_Default_tg BEFORE INSERT OR UPDATE  ON AD_User FOR EACH ROW EXECUTE PROCEDURE AD_User_IsSalesContact_Default_tgfn()
;

-- 2020-08-31T15:56:32.527Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Index_Table SET WhereClause='C_BPartner_ID IS NOT NULL AND IsDefaultContact=''Y''',Updated=TO_TIMESTAMP('2020-08-31 18:56:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Table_ID=540199
;

-- 2020-08-31T15:56:56.525Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DROP INDEX IF EXISTS ad_user_isdefaultcontact
;

-- 2020-08-31T15:56:56.527Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE UNIQUE INDEX AD_User_IsDefaultContact ON AD_User (C_BPartner_ID,IsDefaultContact) WHERE C_BPartner_ID IS NOT NULL AND IsDefaultContact='Y'
;

-- 2020-08-31T15:56:56.541Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE OR REPLACE FUNCTION AD_User_IsDefaultContact_tgfn()
    RETURNS TRIGGER AS $AD_User_IsDefaultContact_tg$
BEGIN
    IF TG_OP='INSERT' THEN
        UPDATE AD_User SET IsDefaultContact='N', Updated=NEW.Updated, UpdatedBy=NEW.UpdatedBy WHERE 1=1  AND C_BPartner_ID=NEW.C_BPartner_ID AND IsDefaultContact=NEW.IsDefaultContact AND AD_User_ID<>NEW.AD_User_ID AND C_BPartner_ID IS NOT NULL AND IsDefaultContact='Y';
    ELSE
        IF OLD.C_BPartner_ID<>NEW.C_BPartner_ID OR OLD.IsDefaultContact<>NEW.IsDefaultContact THEN
            UPDATE AD_User SET IsDefaultContact='N', Updated=NEW.Updated, UpdatedBy=NEW.UpdatedBy WHERE 1=1  AND C_BPartner_ID=NEW.C_BPartner_ID AND IsDefaultContact=NEW.IsDefaultContact AND AD_User_ID<>NEW.AD_User_ID AND C_BPartner_ID IS NOT NULL AND IsDefaultContact='Y';
        END IF;
    END IF;
    RETURN NEW;
END;
$AD_User_IsDefaultContact_tg$ LANGUAGE plpgsql;
;

-- 2020-08-31T15:56:56.545Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DROP TRIGGER IF EXISTS AD_User_IsDefaultContact_tg ON AD_User
;

-- 2020-08-31T15:56:56.546Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE TRIGGER AD_User_IsDefaultContact_tg BEFORE INSERT OR UPDATE  ON AD_User FOR EACH ROW EXECUTE PROCEDURE AD_User_IsDefaultContact_tgfn()
;
