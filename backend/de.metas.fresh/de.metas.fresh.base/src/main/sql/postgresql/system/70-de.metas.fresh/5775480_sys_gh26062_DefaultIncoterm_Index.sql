-- Run mode: SWING_CLIENT

-- 2025-11-03T10:19:18.128Z
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,BeforeChangeCode,BeforeChangeCodeType,Created,CreatedBy,EntityType,ErrorMsg,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy,WhereClause) VALUES (0,540836,0,541870,'IsDefault=''N''','SQLS',TO_TIMESTAMP('2025-11-03 10:19:17.939000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Es kann jeweils nur ein Incoterm als Standard festgelegt werden. Wenn ein neuer Incoterm als Standard festgelegt wird, wird der bisherige Standard-Incoterm automatisch nicht mehr als Standard markiert.','Y','Y','IsDefaultIncoterm','N',TO_TIMESTAMP('2025-11-03 10:19:17.939000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'IsDefault=''Y''')
;

-- 2025-11-04T18:21:09.586Z
UPDATE AD_Index_Table SET WhereClause='IsDefault=''Y'' AND IsActive=''Y''',Updated=TO_TIMESTAMP('2025-11-04 18:21:09.582000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Index_Table_ID=540836
;

-- 2025-11-03T10:19:18.131Z
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Index_Table_ID=540836 AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 2025-11-03T10:19:32.104Z
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,577173,541481,540836,0,TO_TIMESTAMP('2025-11-03 10:19:31.932000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'U','Y',10,TO_TIMESTAMP('2025-11-03 10:19:31.932000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-11-03T10:19:40.505Z
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,591464,541482,540836,0,TO_TIMESTAMP('2025-11-03 10:19:40.315000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'U','Y',20,TO_TIMESTAMP('2025-11-03 10:19:40.315000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-11-03T10:20:57.828Z
DROP INDEX IF EXISTS isdefaultincoterm
;

-- 2025-11-03T10:20:57.830Z
CREATE UNIQUE INDEX IsDefaultIncoterm ON C_Incoterms (AD_Org_ID,IsDefault) WHERE IsDefault='Y'
;

-- 2025-11-03T10:20:57.833Z
-- 2025-11-04T18:21:13.643Z
CREATE OR REPLACE FUNCTION IsDefaultIncoterm_tgfn()
    RETURNS TRIGGER AS $IsDefaultIncoterm_tg$
BEGIN
    IF TG_OP='INSERT' THEN
        UPDATE C_Incoterms SET IsDefault='N', Updated=NEW.Updated, UpdatedBy=NEW.UpdatedBy WHERE 1=1  AND AD_Org_ID=NEW.AD_Org_ID AND IsDefault=NEW.IsDefault AND C_Incoterms_ID<>NEW.C_Incoterms_ID AND IsDefault='Y' AND IsActive='Y';
    ELSE
        IF OLD.AD_Org_ID<>NEW.AD_Org_ID OR OLD.IsDefault<>NEW.IsDefault THEN
            UPDATE C_Incoterms SET IsDefault='N', Updated=NEW.Updated, UpdatedBy=NEW.UpdatedBy WHERE 1=1  AND AD_Org_ID=NEW.AD_Org_ID AND IsDefault=NEW.IsDefault AND C_Incoterms_ID<>NEW.C_Incoterms_ID AND IsDefault='Y' AND IsActive='Y';
        END IF;
    END IF;
    RETURN NEW;
END;
$IsDefaultIncoterm_tg$ LANGUAGE plpgsql;
;

-- 2025-11-03T10:20:57.835Z
DROP TRIGGER IF EXISTS IsDefaultIncoterm_tg ON C_Incoterms
;

-- 2025-11-03T10:20:57.837Z
CREATE TRIGGER IsDefaultIncoterm_tg BEFORE INSERT OR UPDATE  ON C_Incoterms FOR EACH ROW EXECUTE PROCEDURE IsDefaultIncoterm_tgfn()
;

-- 2025-11-03T10:21:36.171Z
UPDATE AD_Index_Table_Trl SET ErrorMsg='Only one Incoterm can be set as the default at any given time. When a new Incoterms is set as the default, the previous default Incoterms is automatically unmarked.', IsTranslated='Y',Updated=TO_TIMESTAMP('2025-11-03 10:21:36.168000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Index_Table_ID=540836 AND AD_Language='en_US'
;

-- 2025-11-03T10:21:36.173Z
UPDATE AD_Index_Table base SET ErrorMsg=trl.ErrorMsg, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Index_Table_Trl trl  WHERE trl.AD_Index_Table_ID=base.AD_Index_Table_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

