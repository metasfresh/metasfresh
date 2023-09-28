-- Run mode: SWING_CLIENT

-- Reference: C_DocType SubType
-- Value: PF
-- ValueName: ProFormaSO
-- 2023-09-19T12:22:12.202Z
INSERT INTO AD_Ref_List (AD_Client_ID, AD_Org_ID, AD_Reference_ID, AD_Ref_List_ID, Created, CreatedBy, EntityType, IsActive, Name, Updated, UpdatedBy, Value, ValueName)
VALUES (0, 0, 148, 543560, TO_TIMESTAMP('2023-09-19 14:22:12.047', 'YYYY-MM-DD HH24:MI:SS.US'), 100, 'de.metas.contracts', 'Y', 'ProForma-Auftrag', TO_TIMESTAMP('2023-09-19 14:22:12.047', 'YYYY-MM-DD HH24:MI:SS.US'), 100, 'PF', 'ProFormaSO')
;

-- 2023-09-19T12:22:12.210Z
INSERT INTO AD_Ref_List_Trl (AD_Language, AD_Ref_List_ID, Description, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language,
       t.AD_Ref_List_ID,
       t.Description,
       t.Name,
       'N',
       t.AD_Client_ID,
       t.AD_Org_ID,
       t.Created,
       t.Createdby,
       t.Updated,
       t.UpdatedBy,
       'Y'
FROM AD_Language l,
     AD_Ref_List t
WHERE l.IsActive = 'Y'
  AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Ref_List_ID = 543560
  AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Ref_List_ID = t.AD_Ref_List_ID)
;

-- Reference Item: C_DocType SubType -> PF_ProFormaSO
-- 2023-09-19T12:22:25.695Z
UPDATE AD_Ref_List_Trl
SET IsTranslated='Y', Updated=TO_TIMESTAMP('2023-09-19 14:22:25.695', 'YYYY-MM-DD HH24:MI:SS.US'), UpdatedBy=100
WHERE AD_Language = 'de_CH'
  AND AD_Ref_List_ID = 543560
;

-- Reference Item: C_DocType SubType -> PF_ProFormaSO
-- 2023-09-19T12:22:26.959Z
UPDATE AD_Ref_List_Trl
SET IsTranslated='Y', Updated=TO_TIMESTAMP('2023-09-19 14:22:26.959', 'YYYY-MM-DD HH24:MI:SS.US'), UpdatedBy=100
WHERE AD_Language = 'de_DE'
  AND AD_Ref_List_ID = 543560
;

-- Reference Item: C_DocType SubType -> PF_ProFormaSO
-- 2023-09-19T12:22:43.916Z
UPDATE AD_Ref_List_Trl
SET IsTranslated='Y', Name='ProFormaSO', Updated=TO_TIMESTAMP('2023-09-19 14:22:43.916', 'YYYY-MM-DD HH24:MI:SS.US'), UpdatedBy=100
WHERE AD_Language = 'en_US'
  AND AD_Ref_List_ID = 543560
;

-- 2023-09-21T06:32:03.266Z
INSERT INTO AD_Sequence (AD_Client_ID, AD_Org_ID, AD_Sequence_ID, Created, CreatedBy, CurrentNext, CurrentNextSys, IncrementNo, IsActive, IsAudited, IsAutoSequence, IsTableID, Name, StartNewMonth, StartNewYear, StartNo, Updated, UpdatedBy)
VALUES (1000000, 1000000, 556308, TO_TIMESTAMP('2023-09-21 08:32:03.263', 'YYYY-MM-DD HH24:MI:SS.US'), 100, 1000000, 100, 1, 'Y', 'N', 'N', 'N', 'ProFormaSO', 'N', 'N', 1000000, TO_TIMESTAMP('2023-09-21 08:32:03.263', 'YYYY-MM-DD HH24:MI:SS.US'), 100)
;

-- 2023-09-21T06:32:03.352Z
UPDATE AD_Sequence
SET IsAutoSequence='Y', Updated=TO_TIMESTAMP('2023-09-21 08:32:03.352', 'YYYY-MM-DD HH24:MI:SS.US'), UpdatedBy=100
WHERE AD_Sequence_ID = 556308
;

-- 2023-09-21T06:44:52.269Z
INSERT INTO C_DocType (AD_Client_ID, AD_Org_ID, C_DocType_ID, Created, CreatedBy, DocBaseType, DocumentCopies, EntityType, GL_Category_ID, HasCharges, HasProforma, IsActive, IsCopyDescriptionToDocument, IsCreateCounter, IsDefault, IsDefaultCounterDoc, IsDocNoControlled, IsExcludeFromCommision, IsIndexed, IsInTransit, IsOverwriteDateOnComplete, IsOverwriteSeqOnComplete, IsPickQAConfirm,
                       IsShipConfirm, IsSOTrx, IsSplitWhenDifference, Name, PrintName, Updated, UpdatedBy)
VALUES (1000000, 1000000, 1000045, TO_TIMESTAMP('2023-09-21 08:44:52.245', 'YYYY-MM-DD HH24:MI:SS.US'), 100, 'SOO', 1, 'de.metas.contracts', 1000000, 'N', 'N', 'Y', 'Y', 'Y', 'N', 'N', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'ProForma-Auftrag', 'ProForma-Auftrag', TO_TIMESTAMP('2023-09-21 08:44:52.245', 'YYYY-MM-DD HH24:MI:SS.US'), 100)
;

-- 2023-09-21T06:44:52.332Z
INSERT INTO C_DocType_Trl (AD_Language, C_DocType_ID, Description, DocumentNote, Name, PrintName, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language,
       t.C_DocType_ID,
       t.Description,
       t.DocumentNote,
       t.Name,
       t.PrintName,
       'N',
       t.AD_Client_ID,
       t.AD_Org_ID,
       t.Created,
       t.Createdby,
       t.Updated,
       t.UpdatedBy,
       'Y'
FROM AD_Language l,
     C_DocType t
WHERE l.IsActive = 'Y'
  AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.C_DocType_ID = 1000045
  AND NOT EXISTS (SELECT 1 FROM C_DocType_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.C_DocType_ID = t.C_DocType_ID)
;

-- 2023-09-21T06:44:52.336Z
INSERT INTO AD_Document_Action_Access (AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, C_DocType_ID, AD_Ref_List_ID, AD_Role_ID) (SELECT 1000000,
                                                                                                                                                                    0,
                                                                                                                                                                    'Y',
                                                                                                                                                                    NOW(),
                                                                                                                                                                    100,
                                                                                                                                                                    NOW(),
                                                                                                                                                                    100,
                                                                                                                                                                    doctype.C_DocType_ID,
                                                                                                                                                                    action.AD_Ref_List_ID,
                                                                                                                                                                    rol.AD_Role_ID
                                                                                                                                                             FROM AD_Client client
                                                                                                                                                                      INNER JOIN C_DocType doctype ON (doctype.AD_Client_ID = client.AD_Client_ID)
                                                                                                                                                                      INNER JOIN AD_Ref_List action ON (action.AD_Reference_ID = 135)
                                                                                                                                                                      INNER JOIN AD_Role rol ON (rol.AD_Client_ID = client.AD_Client_ID)
                                                                                                                                                             WHERE client.AD_Client_ID = 1000000
                                                                                                                                                               AND doctype.C_DocType_ID = 1000045
                                                                                                                                                               AND rol.IsManual = 'N')
;

-- 2023-09-21T06:47:00.359Z
UPDATE C_DocType
SET DocSubType='PF', Updated=TO_TIMESTAMP('2023-09-21 08:47:00.359', 'YYYY-MM-DD HH24:MI:SS.US'), UpdatedBy=100
WHERE C_DocType_ID = 1000045
;

-- 2023-09-21T06:48:59.122Z
UPDATE C_DocType
SET IsSOTrx='Y', Updated=TO_TIMESTAMP('2023-09-21 08:48:59.122', 'YYYY-MM-DD HH24:MI:SS.US'), UpdatedBy=100
WHERE C_DocType_ID = 1000045
;

-- 2023-09-21T06:49:36.501Z
UPDATE C_DocType
SET AD_PrintFormat_ID=1000010, Updated=TO_TIMESTAMP('2023-09-21 08:49:36.501', 'YYYY-MM-DD HH24:MI:SS.US'), UpdatedBy=100
WHERE C_DocType_ID = 1000045
;

-- 2023-09-21T06:55:30.272Z
UPDATE C_DocType_Trl
SET IsTranslated='Y', Updated=TO_TIMESTAMP('2023-09-21 08:55:30.272', 'YYYY-MM-DD HH24:MI:SS.US'), UpdatedBy=100
WHERE AD_Language = 'de_CH'
  AND C_DocType_ID = 1000045
;

-- 2023-09-21T06:55:33.734Z
UPDATE C_DocType_Trl
SET IsTranslated='Y', Updated=TO_TIMESTAMP('2023-09-21 08:55:33.734', 'YYYY-MM-DD HH24:MI:SS.US'), UpdatedBy=100
WHERE AD_Language = 'de_DE'
  AND C_DocType_ID = 1000045
;

-- 2023-09-21T06:55:43.744Z
UPDATE C_DocType_Trl
SET Name='ProForma SO', Updated=TO_TIMESTAMP('2023-09-21 08:55:43.744', 'YYYY-MM-DD HH24:MI:SS.US'), UpdatedBy=100
WHERE AD_Language = 'en_US'
  AND C_DocType_ID = 1000045
;

-- 2023-09-21T06:55:50.343Z
UPDATE C_DocType_Trl
SET PrintName='ProForma SO', Updated=TO_TIMESTAMP('2023-09-21 08:55:50.343', 'YYYY-MM-DD HH24:MI:SS.US'), UpdatedBy=100
WHERE AD_Language = 'en_US'
  AND C_DocType_ID = 1000045
;

-- 2023-09-21T06:55:55.232Z
UPDATE C_DocType_Trl
SET IsTranslated='Y', Updated=TO_TIMESTAMP('2023-09-21 08:55:55.232', 'YYYY-MM-DD HH24:MI:SS.US'), UpdatedBy=100
WHERE AD_Language = 'en_US'
  AND C_DocType_ID = 1000045
;

-- 2023-09-21T07:06:47.915Z
UPDATE C_DocType
SET DocNoSequence_ID=556308, Updated=TO_TIMESTAMP('2023-09-21 09:06:47.915', 'YYYY-MM-DD HH24:MI:SS.US'), UpdatedBy=100
WHERE C_DocType_ID = 1000045
;

-- Reference: ModCntr_Log_DocumentType
-- Value: ProFormaSO
-- ValueName: ProFormaSO
-- 2023-09-26T08:10:26.406Z
INSERT INTO AD_Ref_List (AD_Client_ID, AD_Org_ID, AD_Reference_ID, AD_Ref_List_ID, Created, CreatedBy, EntityType, IsActive, Name, Updated, UpdatedBy, Value, ValueName)
VALUES (0, 0, 541770, 543563, TO_TIMESTAMP('2023-09-26 10:10:26.3', 'YYYY-MM-DD HH24:MI:SS.US'), 100, 'de.metas.contracts', 'Y', 'ProForma-Auftrag', TO_TIMESTAMP('2023-09-26 10:10:26.3', 'YYYY-MM-DD HH24:MI:SS.US'), 100, 'ProFormaSO', 'ProFormaSO')
;

-- 2023-09-26T08:10:26.407Z
INSERT INTO AD_Ref_List_Trl (AD_Language, AD_Ref_List_ID, Description, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language,
       t.AD_Ref_List_ID,
       t.Description,
       t.Name,
       'N',
       t.AD_Client_ID,
       t.AD_Org_ID,
       t.Created,
       t.Createdby,
       t.Updated,
       t.UpdatedBy,
       'Y'
FROM AD_Language l,
     AD_Ref_List t
WHERE l.IsActive = 'Y'
  AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Ref_List_ID = 543563
  AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Ref_List_ID = t.AD_Ref_List_ID)
;

-- Reference Item: ModCntr_Log_DocumentType -> ProFormaSO_ProFormaSO
-- 2023-09-26T08:10:32.882Z
UPDATE AD_Ref_List_Trl
SET IsTranslated='Y', Updated=TO_TIMESTAMP('2023-09-26 10:10:32.881', 'YYYY-MM-DD HH24:MI:SS.US'), UpdatedBy=100
WHERE AD_Language = 'de_CH'
  AND AD_Ref_List_ID = 543563
;

-- Reference Item: ModCntr_Log_DocumentType -> ProFormaSO_ProFormaSO
-- 2023-09-26T08:10:37.688Z
UPDATE AD_Ref_List_Trl
SET IsTranslated='Y', Updated=TO_TIMESTAMP('2023-09-26 10:10:37.688', 'YYYY-MM-DD HH24:MI:SS.US'), UpdatedBy=100
WHERE AD_Language = 'de_DE'
  AND AD_Ref_List_ID = 543563
;

-- Reference Item: ModCntr_Log_DocumentType -> ProFormaSO_ProFormaSO
-- 2023-09-26T08:10:51.387Z
UPDATE AD_Ref_List_Trl
SET IsTranslated='Y', Name='ProForma SO', Updated=TO_TIMESTAMP('2023-09-26 10:10:51.387', 'YYYY-MM-DD HH24:MI:SS.US'), UpdatedBy=100
WHERE AD_Language = 'en_US'
  AND AD_Ref_List_ID = 543563
;

