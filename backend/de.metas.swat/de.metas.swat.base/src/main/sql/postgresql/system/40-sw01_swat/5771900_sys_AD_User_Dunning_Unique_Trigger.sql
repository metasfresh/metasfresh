
DROP INDEX IF EXISTS c_bpartner_unique_dunning;







-- 2025-10-01T10:54:00.587Z
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,BeforeChangeCode,BeforeChangeCodeType,Created,CreatedBy,EntityType,ErrorMsg,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy,WhereClause) VALUES (0,540827,0,114,'IsDunningContact=''N''','SQLS',TO_TIMESTAMP('2025-10-01 10:53:59.459000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.swat','Es darf nur ein Mahnungskontakt aktiviert sein. Bei Änderung wird beim vorherigen Mahnungskontakt automatisch der Haken entfernt.','Y','Y','AD_User_IsDunningContact','N',TO_TIMESTAMP('2025-10-01 10:53:59.459000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'C_BPartner_ID IS NOT NULL AND IsDunningContact=''Y''')
;

-- 2025-10-01T10:54:00.594Z
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Index_Table_ID=540827 AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 2025-10-01T10:54:26.914Z
UPDATE AD_Index_Table_Trl SET ErrorMsg='Only one dunning contact may be activated. If a change is made, the checkmark will automatically be removed from the previous dunning contact.',Updated=TO_TIMESTAMP('2025-10-01 10:54:26.912000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Index_Table_ID=540827 AND AD_Language='en_US'
;

-- 2025-10-01T10:54:26.915Z
UPDATE AD_Index_Table base SET ErrorMsg=trl.ErrorMsg, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Index_Table_Trl trl  WHERE trl.AD_Index_Table_ID=base.AD_Index_Table_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-10-01T10:55:09.893Z
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,5844,541471,540827,0,TO_TIMESTAMP('2025-10-01 10:55:09.728000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.swat','Y',10,TO_TIMESTAMP('2025-10-01 10:55:09.728000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-01T10:55:20.571Z
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,586757,541472,540827,0,TO_TIMESTAMP('2025-10-01 10:55:20.450000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.swat','Y',20,TO_TIMESTAMP('2025-10-01 10:55:20.450000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-01T10:56:22.166Z
CREATE UNIQUE INDEX AD_User_IsDunningContact ON AD_User (C_BPartner_ID,IsDunningContact) WHERE C_BPartner_ID IS NOT NULL AND IsDunningContact='Y'
;

-- 2025-10-01T10:56:22.185Z
CREATE OR REPLACE FUNCTION AD_User_IsDunningContact_tgfn()
 RETURNS TRIGGER AS $AD_User_IsDunningContact_tg$
 BEGIN
 IF TG_OP='INSERT' THEN
UPDATE AD_User SET IsDunningContact='N', Updated=NEW.Updated, UpdatedBy=NEW.UpdatedBy WHERE 1=1  AND C_BPartner_ID=NEW.C_BPartner_ID AND IsDunningContact=NEW.IsDunningContact AND AD_User_ID<>NEW.AD_User_ID AND C_BPartner_ID IS NOT NULL AND IsDunningContact='Y';
 ELSE
IF OLD.C_BPartner_ID<>NEW.C_BPartner_ID OR OLD.IsDunningContact<>NEW.IsDunningContact THEN
UPDATE AD_User SET IsDunningContact='N', Updated=NEW.Updated, UpdatedBy=NEW.UpdatedBy WHERE 1=1  AND C_BPartner_ID=NEW.C_BPartner_ID AND IsDunningContact=NEW.IsDunningContact AND AD_User_ID<>NEW.AD_User_ID AND C_BPartner_ID IS NOT NULL AND IsDunningContact='Y';
 END IF;
 END IF;
 RETURN NEW;
 END;
 $AD_User_IsDunningContact_tg$ LANGUAGE plpgsql;
;

-- 2025-10-01T10:56:22.200Z
DROP TRIGGER IF EXISTS AD_User_IsDunningContact_tg ON AD_User
;

-- 2025-10-01T10:56:22.201Z
CREATE TRIGGER AD_User_IsDunningContact_tg BEFORE INSERT OR UPDATE  ON AD_User FOR EACH ROW EXECUTE PROCEDURE AD_User_IsDunningContact_tgfn()
;

