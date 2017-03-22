-- 15.02.2017 09:16
-- URL zum Konzept
UPDATE AD_Column SET ReadOnlyLogic='IsPurchaseContact = ''N''',Updated=TO_TIMESTAMP('2017-02-15 09:16:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=556074
;

-- 15.02.2017 09:16
-- URL zum Konzept
UPDATE AD_Column SET ReadOnlyLogic='IsSalesContact = ''N''',Updated=TO_TIMESTAMP('2017-02-15 09:16:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=556073
;

-- 15.02.2017 09:17
-- URL zum Konzept
UPDATE AD_Field SET DisplayLogic='IsPurchaseContact = ''Y''',Updated=TO_TIMESTAMP('2017-02-15 09:17:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557547
;

-- 15.02.2017 09:18
-- URL zum Konzept
UPDATE AD_Field SET DisplayLogic='@IsPurchaseContact@ = ''Y''',Updated=TO_TIMESTAMP('2017-02-15 09:18:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557547
;

-- 15.02.2017 09:18
-- URL zum Konzept
UPDATE AD_Field SET DisplayLogic='@IsSalesContact@ = ''Y'' ',Updated=TO_TIMESTAMP('2017-02-15 09:18:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557546
;

-- 15.02.2017 09:19
-- URL zum Konzept
UPDATE AD_Column SET ReadOnlyLogic='@IsSalesContact@ = ''N''',Updated=TO_TIMESTAMP('2017-02-15 09:19:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=556073
;

-- 15.02.2017 09:19
-- URL zum Konzept
UPDATE AD_Column SET ReadOnlyLogic='@IsPurchaseContact@ = ''N''',Updated=TO_TIMESTAMP('2017-02-15 09:19:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=556074
;

-- 15.02.2017 09:46
-- URL zum Konzept
UPDATE AD_Index_Table SET BeforeChangeWarning='Möchten sie wirklich die Standard Verkaufskontakt ändern?',Updated=TO_TIMESTAMP('2017-02-15 09:46:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Table_ID=540395
;

-- 15.02.2017 09:47
-- URL zum Konzept
UPDATE AD_Index_Table SET BeforeChangeWarning='Möchten sie wirklich die Standard Einkaufskontakt ändern?',Updated=TO_TIMESTAMP('2017-02-15 09:47:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Table_ID=540396
;

-- 15.02.2017 09:47
-- URL zum Konzept
DROP INDEX IF EXISTS ad_user_ispurchasecontact_default
;

-- 15.02.2017 09:47
-- URL zum Konzept
CREATE UNIQUE INDEX AD_User_IsPurchaseContact_Default ON AD_User (C_BPartner_ID,IsPurchaseContact_Default) WHERE C_BPartner_ID IS NOT NULL AND IsPurchaseContact_Default='Y' AND IsActive='Y'
;

-- 15.02.2017 09:47
-- URL zum Konzept
CREATE OR REPLACE FUNCTION AD_User_IsPurchaseContact_Default_tgfn()
 RETURNS TRIGGER AS $AD_User_IsPurchaseContact_Default_tg$
 BEGIN
 IF TG_OP='INSERT' THEN
UPDATE AD_User SET IsPurchaseContact_Default='N', Updated=NEW.Updated, UpdatedBy=NEW.UpdatedBy WHERE 1=1  AND C_BPartner_ID=NEW.C_BPartner_ID AND IsPurchaseContact_Default=NEW.IsPurchaseContact_Default AND AD_User_ID<>NEW.AD_User_ID AND C_BPartner_ID IS NOT NULL AND IsPurchaseContact_Default='Y' AND IsActive='Y';
 ELSE
IF OLD.C_BPartner_ID<>NEW.C_BPartner_ID OR OLD.IsPurchaseContact_Default<>NEW.IsPurchaseContact_Default THEN
UPDATE AD_User SET IsPurchaseContact_Default='N', Updated=NEW.Updated, UpdatedBy=NEW.UpdatedBy WHERE 1=1  AND C_BPartner_ID=NEW.C_BPartner_ID AND IsPurchaseContact_Default=NEW.IsPurchaseContact_Default AND AD_User_ID<>NEW.AD_User_ID AND C_BPartner_ID IS NOT NULL AND IsPurchaseContact_Default='Y' AND IsActive='Y';
 END IF;
 END IF;
 RETURN NEW;
 END;
 $AD_User_IsPurchaseContact_Default_tg$ LANGUAGE plpgsql;
;

-- 15.02.2017 09:47
-- URL zum Konzept
DROP TRIGGER IF EXISTS AD_User_IsPurchaseContact_Default_tg ON AD_User
;

-- 15.02.2017 09:47
-- URL zum Konzept
CREATE TRIGGER AD_User_IsPurchaseContact_Default_tg BEFORE INSERT OR UPDATE  ON AD_User FOR EACH ROW EXECUTE PROCEDURE AD_User_IsPurchaseContact_Default_tgfn()
;

-- 15.02.2017 09:47
-- URL zum Konzept
DROP INDEX IF EXISTS ad_user_issalescontact_default
;

-- 15.02.2017 09:47
-- URL zum Konzept
CREATE UNIQUE INDEX AD_User_IsSalesContact_Default ON AD_User (C_BPartner_ID,IsSalesContact_Default) WHERE C_BPartner_ID IS NOT NULL AND IsSalesContact_Default='Y' AND IsActive='Y'
;

-- 15.02.2017 09:47
-- URL zum Konzept
CREATE OR REPLACE FUNCTION AD_User_IsSalesContact_Default_tgfn()
 RETURNS TRIGGER AS $AD_User_IsSalesContact_Default_tg$
 BEGIN
 IF TG_OP='INSERT' THEN
UPDATE AD_User SET IsSalesContact_Default='N', Updated=NEW.Updated, UpdatedBy=NEW.UpdatedBy WHERE 1=1  AND C_BPartner_ID=NEW.C_BPartner_ID AND IsSalesContact_Default=NEW.IsSalesContact_Default AND AD_User_ID<>NEW.AD_User_ID AND C_BPartner_ID IS NOT NULL AND IsSalesContact_Default='Y' AND IsActive='Y';
 ELSE
IF OLD.C_BPartner_ID<>NEW.C_BPartner_ID OR OLD.IsSalesContact_Default<>NEW.IsSalesContact_Default THEN
UPDATE AD_User SET IsSalesContact_Default='N', Updated=NEW.Updated, UpdatedBy=NEW.UpdatedBy WHERE 1=1  AND C_BPartner_ID=NEW.C_BPartner_ID AND IsSalesContact_Default=NEW.IsSalesContact_Default AND AD_User_ID<>NEW.AD_User_ID AND C_BPartner_ID IS NOT NULL AND IsSalesContact_Default='Y' AND IsActive='Y';
 END IF;
 END IF;
 RETURN NEW;
 END;
 $AD_User_IsSalesContact_Default_tg$ LANGUAGE plpgsql;
;

-- 15.02.2017 09:47
-- URL zum Konzept
DROP TRIGGER IF EXISTS AD_User_IsSalesContact_Default_tg ON AD_User
;

-- 15.02.2017 09:47
-- URL zum Konzept
CREATE TRIGGER AD_User_IsSalesContact_Default_tg BEFORE INSERT OR UPDATE  ON AD_User FOR EACH ROW EXECUTE PROCEDURE AD_User_IsSalesContact_Default_tgfn()
;

