-- Run mode: SWING_CLIENT

-- Reference: C_DocType SubType
-- Value: OOC
-- ValueName: Order on Commission
-- 2025-09-15T08:59:22.754Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,148,543978,TO_TIMESTAMP('2025-09-15 08:59:22.361000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.swat','Y','Order on Commission',TO_TIMESTAMP('2025-09-15 08:59:22.361000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'OOC','Order on Commission')
;

-- 2025-09-15T08:59:22.759Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543978 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;


-- 2025-09-15T08:50:21.600Z
INSERT INTO C_DocType (AD_Client_ID,AD_Org_ID,C_DocType_ID,Created,CreatedBy,DocBaseType,DocumentCopies,EntityType,GL_Category_ID,HasCharges,HasProforma,IsActive,IsCreateCounter,IsDefault,IsDefaultCounterDoc,IsDocNoControlled,IsExcludeFromCommision,IsIndexed,IsInTransit,IsOverwriteDateOnComplete,IsOverwriteSeqOnComplete,IsPickQAConfirm,IsShipConfirm,IsSOTrx,IsSplitWhenDifference,Name,PrintName,Updated,UpdatedBy) VALUES (1000000,1000000,541135,TO_TIMESTAMP('2025-09-15 08:50:21.596000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'SOO',1,'de.metas.swat',0,'N','N','Y','Y','N','N','Y','N','N','N','N','N','N','N','N','N','Auftrag auf Kommission','Auftrag auf Kommission',TO_TIMESTAMP('2025-09-15 08:50:21.596000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-15T08:50:21.607Z
INSERT INTO C_DocType_Trl (AD_Language,C_DocType_ID, Description,DocumentNote,Name,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.C_DocType_ID, t.Description,t.DocumentNote,t.Name,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, C_DocType t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.C_DocType_ID=541135 AND NOT EXISTS (SELECT 1 FROM C_DocType_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_DocType_ID=t.C_DocType_ID)
;

-- 2025-09-15T08:50:21.608Z
INSERT INTO AD_Document_Action_Access (AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,C_DocType_ID , AD_Ref_List_ID, AD_Role_ID) (SELECT 1000000,0,'Y', now(),100, now(),100, doctype.C_DocType_ID, action.AD_Ref_List_ID, rol.AD_Role_ID FROM AD_Client client INNER JOIN C_DocType doctype ON (doctype.AD_Client_ID=client.AD_Client_ID) INNER JOIN AD_Ref_List action ON (action.AD_Reference_ID=135) INNER JOIN AD_Role rol ON (rol.AD_Client_ID=client.AD_Client_ID) WHERE client.AD_Client_ID=1000000 AND doctype.C_DocType_ID=541135 AND rol.IsManual='N')
;



-- Reference Item: C_DocType SubType -> OOC_Order on Commission
-- 2025-09-15T09:05:46.885Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Auftrag auf Kommission',Updated=TO_TIMESTAMP('2025-09-15 09:05:46.885000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543978
;

-- 2025-09-15T09:05:46.887Z
UPDATE AD_Ref_List base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Ref_List_Trl trl  WHERE trl.AD_Ref_List_ID=base.AD_Ref_List_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- Reference Item: C_DocType SubType -> OOC_Order on Commission
-- 2025-09-15T09:05:52.452Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Auftrag auf Kommission',Updated=TO_TIMESTAMP('2025-09-15 09:05:52.452000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543978
;

-- 2025-09-15T09:05:52.454Z
UPDATE AD_Ref_List base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Ref_List_Trl trl  WHERE trl.AD_Ref_List_ID=base.AD_Ref_List_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;





-- 2025-09-15T09:17:20.623Z
UPDATE C_DocType SET IsSOTrx='Y',Updated=TO_TIMESTAMP('2025-09-15 09:17:20.623000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE C_DocType_ID=541135
;

-- 2025-09-15T09:17:31.469Z
UPDATE C_DocType SET DocNoSequence_ID=545479,Updated=TO_TIMESTAMP('2025-09-15 09:17:31.469000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE C_DocType_ID=541135
;

-- 2025-09-15T09:17:51.998Z
UPDATE C_DocType SET AD_PrintFormat_ID=1000010,Updated=TO_TIMESTAMP('2025-09-15 09:17:51.998000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE C_DocType_ID=541135
;











-- 2025-09-15T09:08:48.080Z
UPDATE C_DocType SET DocSubType='OOC',Updated=TO_TIMESTAMP('2025-09-15 09:08:48.080000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE C_DocType_ID=541135
;




-- 2025-09-15T09:11:29.111Z
UPDATE C_DocType SET C_DocTypeInvoice_ID=1000002,Updated=TO_TIMESTAMP('2025-09-15 09:11:29.111000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE C_DocType_ID=541135
;






-- 2025-09-15T09:21:51.168Z
INSERT INTO C_DocType (AD_Client_ID,AD_Org_ID,C_DocType_ID,Created,CreatedBy,DocBaseType,DocumentCopies,EntityType,GL_Category_ID,HasCharges,HasProforma,IsActive,IsCreateCounter,IsDefault,IsDefaultCounterDoc,IsDocNoControlled,IsExcludeFromCommision,IsIndexed,IsInTransit,IsOverwriteDateOnComplete,IsOverwriteSeqOnComplete,IsPickQAConfirm,IsShipConfirm,IsSOTrx,IsSplitWhenDifference,Name,PrintName,Updated,UpdatedBy) VALUES (1000000,1000000,541136,TO_TIMESTAMP('2025-09-15 09:21:51.166000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MMS',1,'de.metas.swat',1000001,'N','N','Y','Y','N','N','Y','N','N','N','N','N','N','N','N','N','Lieferung auf Kommission','Lieferung auf Kommission',TO_TIMESTAMP('2025-09-15 09:21:51.166000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-15T09:21:51.172Z
INSERT INTO C_DocType_Trl (AD_Language,C_DocType_ID, Description,DocumentNote,Name,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.C_DocType_ID, t.Description,t.DocumentNote,t.Name,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, C_DocType t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.C_DocType_ID=541136 AND NOT EXISTS (SELECT 1 FROM C_DocType_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_DocType_ID=t.C_DocType_ID)
;

-- 2025-09-15T09:21:51.173Z
INSERT INTO AD_Document_Action_Access (AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,C_DocType_ID , AD_Ref_List_ID, AD_Role_ID) (SELECT 1000000,0,'Y', now(),100, now(),100, doctype.C_DocType_ID, action.AD_Ref_List_ID, rol.AD_Role_ID FROM AD_Client client INNER JOIN C_DocType doctype ON (doctype.AD_Client_ID=client.AD_Client_ID) INNER JOIN AD_Ref_List action ON (action.AD_Reference_ID=135) INNER JOIN AD_Role rol ON (rol.AD_Client_ID=client.AD_Client_ID) WHERE client.AD_Client_ID=1000000 AND doctype.C_DocType_ID=541136 AND rol.IsManual='N')
;

-- 2025-09-15T09:21:54.756Z
UPDATE C_DocType SET DocSubType='OOC',Updated=TO_TIMESTAMP('2025-09-15 09:21:54.756000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE C_DocType_ID=541136
;

-- 2025-09-15T09:22:00.066Z
UPDATE C_DocType SET IsSOTrx='Y',Updated=TO_TIMESTAMP('2025-09-15 09:22:00.066000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE C_DocType_ID=541136
;

-- 2025-09-15T09:22:07.825Z
UPDATE C_DocType SET DocNoSequence_ID=545461,Updated=TO_TIMESTAMP('2025-09-15 09:22:07.824000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE C_DocType_ID=541136
;

-- 2025-09-15T09:22:11.492Z
UPDATE C_DocType SET IsCreateCounter='N',Updated=TO_TIMESTAMP('2025-09-15 09:22:11.492000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE C_DocType_ID=541136
;

-- 2025-09-15T09:22:18.548Z
UPDATE C_DocType SET AD_PrintFormat_ID=1000013,Updated=TO_TIMESTAMP('2025-09-15 09:22:18.548000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE C_DocType_ID=541136
;








-- 2025-09-16T10:16:36.202Z
UPDATE C_DocType SET C_DocTypeShipment_ID=541136,Updated=TO_TIMESTAMP('2025-09-16 10:16:36.196000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE C_DocType_ID=541135
;




-- 2025-09-16T12:28:21.207Z
UPDATE C_DocType_Trl SET Name='Order on Commission',Updated=TO_TIMESTAMP('2025-09-16 12:28:21.182000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND C_DocType_ID=541135
;

-- 2025-09-16T12:28:21.223Z
UPDATE C_DocType base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM C_DocType_Trl trl  WHERE trl.C_DocType_ID=base.C_DocType_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-09-16T12:28:22.879Z
UPDATE C_DocType_Trl SET PrintName='Order on Commission',Updated=TO_TIMESTAMP('2025-09-16 12:28:22.878000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND C_DocType_ID=541135
;

-- 2025-09-16T12:28:22.880Z
UPDATE C_DocType base SET PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM C_DocType_Trl trl  WHERE trl.C_DocType_ID=base.C_DocType_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;



-- 2025-09-16T12:55:58.417Z
UPDATE C_DocType_Trl SET Name='Delivery on Commission',Updated=TO_TIMESTAMP('2025-09-16 12:55:58.417000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND C_DocType_ID=541136
;

-- 2025-09-16T12:55:58.423Z
UPDATE C_DocType base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM C_DocType_Trl trl  WHERE trl.C_DocType_ID=base.C_DocType_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-09-16T12:55:59.949Z
UPDATE C_DocType_Trl SET PrintName='Delivery on Commission',Updated=TO_TIMESTAMP('2025-09-16 12:55:59.949000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND C_DocType_ID=541136
;

-- 2025-09-16T12:55:59.950Z
UPDATE C_DocType base SET PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM C_DocType_Trl trl  WHERE trl.C_DocType_ID=base.C_DocType_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;