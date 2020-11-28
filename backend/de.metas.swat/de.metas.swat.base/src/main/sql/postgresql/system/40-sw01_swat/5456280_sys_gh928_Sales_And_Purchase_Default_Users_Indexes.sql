
-- 10.02.2017 11:55
-- URL zum Konzept
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,BeforeChangeCode,BeforeChangeCodeType,Created,CreatedBy,EntityType,ErrorMsg,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy,WhereClause) VALUES (0,540395,0,114,'IsSalesContact_Default=''N''','SQLS',TO_TIMESTAMP('2017-02-10 11:55:48','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Es darf nur eine Standard Verkaufskontakt aktiviert sein. Bei Änderung wird bei der vorherigen Standard Verkaufskontakt automatisch der Haken entfernt.','Y','Y','AD_User_IsSalesContact_Default','N',TO_TIMESTAMP('2017-02-10 11:55:48','YYYY-MM-DD HH24:MI:SS'),100,'C_BPartner_ID IS NOT NULL AND IsSalesContact_Default=''Y'' AND IsActive=''Y''')
;

-- 10.02.2017 11:55
-- URL zum Konzept
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Index_Table_ID=540395 AND NOT EXISTS (SELECT * FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 10.02.2017 11:56
-- URL zum Konzept
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,5844,540790,540395,0,TO_TIMESTAMP('2017-02-10 11:56:10','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y',10,TO_TIMESTAMP('2017-02-10 11:56:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 10.02.2017 11:56
-- URL zum Konzept
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,556073,540791,540395,0,TO_TIMESTAMP('2017-02-10 11:56:26','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y',20,TO_TIMESTAMP('2017-02-10 11:56:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 10.02.2017 11:56
-- URL zum Konzept
CREATE UNIQUE INDEX AD_User_IsSalesContact_Default ON AD_User (C_BPartner_ID,IsSalesContact_Default) WHERE C_BPartner_ID IS NOT NULL AND IsSalesContact_Default='Y' AND IsActive='Y'
;

-- 10.02.2017 11:56
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

-- 10.02.2017 11:56
-- URL zum Konzept
DROP TRIGGER IF EXISTS AD_User_IsSalesContact_Default_tg ON AD_User
;

-- 10.02.2017 11:56
-- URL zum Konzept
CREATE TRIGGER AD_User_IsSalesContact_Default_tg BEFORE INSERT OR UPDATE  ON AD_User FOR EACH ROW EXECUTE PROCEDURE AD_User_IsSalesContact_Default_tgfn()
;

-- 10.02.2017 12:06
-- URL zum Konzept
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,BeforeChangeCode,BeforeChangeCodeType,Created,CreatedBy,EntityType,ErrorMsg,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy,WhereClause) VALUES (0,540396,0,114,'IsPurchaseContact_Default=''N''','SQLS',TO_TIMESTAMP('2017-02-10 12:06:03','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Es darf nur eine Standard Einkaufskontakt aktiviert sein. Bei Änderung wird bei der vorherigen Standard Einkaufskontakt automatisch der Haken entfernt.','Y','Y','AD_User_IsPurchaseContact_Default','N',TO_TIMESTAMP('2017-02-10 12:06:03','YYYY-MM-DD HH24:MI:SS'),100,'C_BPartner_ID IS NOT NULL AND IsPurchaseContact_Default=''Y'' AND IsActive=''Y''')
;

-- 10.02.2017 12:06
-- URL zum Konzept
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Index_Table_ID=540396 AND NOT EXISTS (SELECT * FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 10.02.2017 12:06
-- URL zum Konzept
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,5844,540792,540396,0,TO_TIMESTAMP('2017-02-10 12:06:16','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y',10,TO_TIMESTAMP('2017-02-10 12:06:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 10.02.2017 12:06
-- URL zum Konzept
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,556074,540793,540396,0,TO_TIMESTAMP('2017-02-10 12:06:57','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y',20,TO_TIMESTAMP('2017-02-10 12:06:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 10.02.2017 12:07
-- URL zum Konzept
CREATE UNIQUE INDEX AD_User_IsPurchaseContact_Default ON AD_User (C_BPartner_ID,IsPurchaseContact_Default) WHERE C_BPartner_ID IS NOT NULL AND IsPurchaseContact_Default='Y' AND IsActive='Y'
;

-- 10.02.2017 12:07
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

-- 10.02.2017 12:07
-- URL zum Konzept
DROP TRIGGER IF EXISTS AD_User_IsPurchaseContact_Default_tg ON AD_User
;

-- 10.02.2017 12:07
-- URL zum Konzept
CREATE TRIGGER AD_User_IsPurchaseContact_Default_tg BEFORE INSERT OR UPDATE  ON AD_User FOR EACH ROW EXECUTE PROCEDURE AD_User_IsPurchaseContact_Default_tgfn()
;

