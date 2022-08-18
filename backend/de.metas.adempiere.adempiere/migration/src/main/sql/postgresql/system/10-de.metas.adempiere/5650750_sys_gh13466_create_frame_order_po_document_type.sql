-- 2022-08-12T09:27:56.864Z
-- URL zum Konzept
INSERT INTO C_DocType (AD_Client_ID, AD_Org_ID, C_DocType_ID, Created, CreatedBy, DocBaseType, DocumentCopies, EntityType, GL_Category_ID, HasCharges, HasProforma, IsActive, IsCopyDescriptionToDocument, IsCreateCounter, IsDefault, IsDefaultCounterDoc, IsDocNoControlled, IsExcludeFromCommision, IsIndexed, IsInTransit, IsOverwriteDateOnComplete, IsOverwriteSeqOnComplete, IsPickQAConfirm,
                       IsShipConfirm, IsSOTrx, IsSplitWhenDifference, Name, PrintName, Updated, UpdatedBy)
VALUES (1000000, 1000000, 541054, TO_TIMESTAMP('2022-08-12 11:27:56', 'YYYY-MM-DD HH24:MI:SS'), 100, 'POO', 1, 'D', 1000001, 'N', 'N', 'Y', 'Y', 'Y', 'N', 'N', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Frame Order PO', 'Frame Order PO', TO_TIMESTAMP('2022-08-12 11:27:56', 'YYYY-MM-DD HH24:MI:SS'), 100)
;

-- 2022-08-12T09:27:56.875Z
-- URL zum Konzept
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
  AND (l.IsSystemLanguage = 'Y')
  AND t.C_DocType_ID = 541054
  AND NOT EXISTS(SELECT 1 FROM C_DocType_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.C_DocType_ID = t.C_DocType_ID)
;

-- 2022-08-12T09:27:56.876Z
-- URL zum Konzept
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
                                                                                                                                                               AND doctype.C_DocType_ID = 541054
                                                                                                                                                               AND rol.IsManual = 'N')
;

-- 2022-08-12T09:28:26.762Z
-- URL zum Konzept
UPDATE C_DocType
SET DocNoSequence_ID=545465, Updated=TO_TIMESTAMP('2022-08-12 11:28:26', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE C_DocType_ID = 541054
;

-- 2022-08-12T09:28:50.911Z
-- URL zum Konzept
UPDATE C_DocType
SET AD_Org_ID=0, Updated=TO_TIMESTAMP('2022-08-12 11:28:50', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE C_DocType_ID = 541054
;

-- 2022-08-12T11:52:13.298Z
-- URL zum Konzept
INSERT INTO AD_Sequence (AD_Client_ID, AD_Org_ID, AD_Sequence_ID, Created, CreatedBy, CurrentNext, CurrentNextSys, Description, IncrementNo, IsActive, IsAudited, IsAutoSequence, IsTableID, Name, StartNewYear, StartNo, Updated, UpdatedBy)
VALUES (1000000, 0, 555996, TO_TIMESTAMP('2022-08-12 13:52:13', 'YYYY-MM-DD HH24:MI:SS'), 100, 1000000, 100, 'Frame Order PO Trx', 1, 'Y', 'N', 'Y', 'N', 'Frame Order PO Trx', 'N', 1000000, TO_TIMESTAMP('2022-08-12 13:52:13', 'YYYY-MM-DD HH24:MI:SS'), 100)
;

-- 2022-08-12T11:52:41.103Z
-- URL zum Konzept
UPDATE C_DocType
SET DocNoSequence_ID=555996, Updated=TO_TIMESTAMP('2022-08-12 13:52:41', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE C_DocType_ID = 541054
;
