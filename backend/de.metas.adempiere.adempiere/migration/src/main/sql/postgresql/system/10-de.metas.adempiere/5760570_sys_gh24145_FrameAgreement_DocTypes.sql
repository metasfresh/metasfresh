
DO $$
    BEGIN
        IF NOT EXISTS(select * from C_DocType where Name='Rahmenauftrag') THEN

-- Run mode: WEBUI

-- 2025-07-10T08:33:48.642Z
INSERT INTO C_DocType (AD_Client_ID,AD_Org_ID,C_DocType_ID,Created,CreatedBy,DocBaseType,DocNoSequence_ID,DocumentCopies,EntityType,GL_Category_ID,HasCharges,HasProforma,IsActive,IsCreateCounter,IsDefault,IsDefaultCounterDoc,IsDocNoControlled,IsExcludeFromCommision,IsIndexed,IsInTransit,IsOverwriteDateOnComplete,IsOverwriteSeqOnComplete,IsPickQAConfirm,IsShipConfirm,IsSOTrx,IsSplitWhenDifference,Name,PrintName,Updated,UpdatedBy) VALUES (1000000,1000000,541133,TO_TIMESTAMP('2025-07-10 08:33:48.628000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'SOO',545479,1,'D',1000001,'N','N','Y','Y','N','N','Y','N','N','N','N','N','N','N','Y','N','Rahmenauftrag','Rahmenauftrag',TO_TIMESTAMP('2025-07-10 08:33:48.628000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-07-10T08:33:48.701Z
INSERT INTO C_DocType_Trl (AD_Language,C_DocType_ID, Description,DocumentNote,Name,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.C_DocType_ID, t.Description,t.DocumentNote,t.Name,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, C_DocType t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.C_DocType_ID=541133 AND NOT EXISTS (SELECT 1 FROM C_DocType_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_DocType_ID=t.C_DocType_ID)
;

-- 2025-07-10T08:33:48.704Z
INSERT INTO AD_Document_Action_Access (AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,C_DocType_ID , AD_Ref_List_ID, AD_Role_ID) (SELECT 1000000,0,'Y', now(),100, now(),100, doctype.C_DocType_ID, action.AD_Ref_List_ID, rol.AD_Role_ID FROM AD_Client client INNER JOIN C_DocType doctype ON (doctype.AD_Client_ID=client.AD_Client_ID) INNER JOIN AD_Ref_List action ON (action.AD_Reference_ID=135) INNER JOIN AD_Role rol ON (rol.AD_Client_ID=client.AD_Client_ID) WHERE client.AD_Client_ID=1000000 AND doctype.C_DocType_ID=541133 AND rol.IsManual='N')
;

-- 2025-07-10T08:34:01.055Z
UPDATE C_DocType SET DocSubType='CAO',Updated=TO_TIMESTAMP('2025-07-10 08:34:01.055000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE C_DocType_ID=541133
;

-- 2025-07-10T08:34:04.003Z
UPDATE C_DocType SET DocumentCopies=2,Updated=TO_TIMESTAMP('2025-07-10 08:34:04.003000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE C_DocType_ID=541133
;

-- 2025-07-10T08:34:14.199Z
UPDATE C_DocType SET AD_PrintFormat_ID=1000010,Updated=TO_TIMESTAMP('2025-07-10 08:34:14.199000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE C_DocType_ID=541133
;

-- 2025-07-10T08:35:03.091Z
UPDATE C_DocType_Trl SET Name='Frame Order SO',Updated=TO_TIMESTAMP('2025-07-10 08:35:03.090000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND C_DocType_ID=541133
;

-- 2025-07-10T08:35:03.093Z
UPDATE C_DocType base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM C_DocType_Trl trl  WHERE trl.C_DocType_ID=base.C_DocType_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-07-10T08:35:03.876Z
UPDATE C_DocType_Trl SET PrintName='Frame Order SO',Updated=TO_TIMESTAMP('2025-07-10 08:35:03.876000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND C_DocType_ID=541133
;

-- 2025-07-10T08:35:03.877Z
UPDATE C_DocType base SET PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM C_DocType_Trl trl  WHERE trl.C_DocType_ID=base.C_DocType_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

        END IF;
    END
$$;

DO $$
    BEGIN
        IF NOT EXISTS(select * from C_DocType where Name='Rahmenbestellung') THEN

-- 2025-07-10T08:36:34.822Z
INSERT INTO C_DocType (AD_Client_ID,AD_Org_ID,C_DocType_ID,Created,CreatedBy,DocBaseType,DocNoSequence_ID,DocumentCopies,EntityType,GL_Category_ID,HasCharges,HasProforma,IsActive,IsCreateCounter,IsDefault,IsDefaultCounterDoc,IsDocNoControlled,IsExcludeFromCommision,IsIndexed,IsInTransit,IsOverwriteDateOnComplete,IsOverwriteSeqOnComplete,IsPickQAConfirm,IsShipConfirm,IsSOTrx,IsSplitWhenDifference,Name,PrintName,Updated,UpdatedBy) VALUES (1000000,1000000,541134,TO_TIMESTAMP('2025-07-10 08:36:34.818000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'POO',545465,1,'de.metas.contracts',1000001,'N','N','Y','Y','N','N','Y','N','N','N','N','N','N','N','N','N','Rahmenbestellung','Rahmenbestellung',TO_TIMESTAMP('2025-07-10 08:36:34.818000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-07-10T08:36:34.831Z
INSERT INTO C_DocType_Trl (AD_Language,C_DocType_ID, Description,DocumentNote,Name,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.C_DocType_ID, t.Description,t.DocumentNote,t.Name,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, C_DocType t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.C_DocType_ID=541134 AND NOT EXISTS (SELECT 1 FROM C_DocType_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_DocType_ID=t.C_DocType_ID)
;

-- 2025-07-10T08:36:34.837Z
INSERT INTO AD_Document_Action_Access (AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,C_DocType_ID , AD_Ref_List_ID, AD_Role_ID) (SELECT 1000000,0,'Y', now(),100, now(),100, doctype.C_DocType_ID, action.AD_Ref_List_ID, rol.AD_Role_ID FROM AD_Client client INNER JOIN C_DocType doctype ON (doctype.AD_Client_ID=client.AD_Client_ID) INNER JOIN AD_Ref_List action ON (action.AD_Reference_ID=135) INNER JOIN AD_Role rol ON (rol.AD_Client_ID=client.AD_Client_ID) WHERE client.AD_Client_ID=1000000 AND doctype.C_DocType_ID=541134 AND rol.IsManual='N')
;

-- 2025-07-10T08:36:46.845Z
UPDATE C_DocType SET DocSubType='FA',Updated=TO_TIMESTAMP('2025-07-10 08:36:46.845000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE C_DocType_ID=541134
;

-- 2025-07-10T08:36:49.338Z
UPDATE C_DocType SET DocumentCopies=2,Updated=TO_TIMESTAMP('2025-07-10 08:36:49.338000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE C_DocType_ID=541134
;

-- 2025-07-10T08:37:00.855Z
UPDATE C_DocType SET AD_PrintFormat_ID=1000011,Updated=TO_TIMESTAMP('2025-07-10 08:37:00.855000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE C_DocType_ID=541134
;

-- 2025-07-10T08:37:35.501Z
UPDATE C_DocType_Trl SET Name='Frame Agreement PO',Updated=TO_TIMESTAMP('2025-07-10 08:37:35.501000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND C_DocType_ID=541134
;

-- 2025-07-10T08:37:35.502Z
UPDATE C_DocType base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM C_DocType_Trl trl  WHERE trl.C_DocType_ID=base.C_DocType_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-07-10T08:37:36.114Z
UPDATE C_DocType_Trl SET PrintName='Frame Agreement PO',Updated=TO_TIMESTAMP('2025-07-10 08:37:36.114000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND C_DocType_ID=541134
;

-- 2025-07-10T08:37:36.115Z
UPDATE C_DocType base SET PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM C_DocType_Trl trl  WHERE trl.C_DocType_ID=base.C_DocType_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

        END IF;
    END
$$;
