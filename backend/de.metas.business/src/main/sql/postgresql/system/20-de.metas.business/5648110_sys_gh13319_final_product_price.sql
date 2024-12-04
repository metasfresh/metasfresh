-- 2022-07-22T09:07:48.930Z
-- URL zum Konzept
INSERT INTO C_DocType (AD_Client_ID, AD_Org_ID, C_DocType_ID, Created, CreatedBy, DocBaseType, DocumentCopies, EntityType, GL_Category_ID, HasCharges, HasProforma, IsActive, IsCopyDescriptionToDocument, IsCreateCounter, IsDefault, IsDefaultCounterDoc, IsDocNoControlled, IsExcludeFromCommision, IsIndexed, IsInTransit, IsOverwriteDateOnComplete, IsOverwriteSeqOnComplete, IsPickQAConfirm,
                       IsShipConfirm, IsSOTrx, IsSplitWhenDifference, Name, PrintName, Updated, UpdatedBy)
VALUES (1000000, 1000000, 541047, TO_TIMESTAMP('2022-07-22 09:07:48', 'YYYY-MM-DD HH24:MI:SS'), 100, 'CRD', 1, 'de.metas.document', 1000000, 'N', 'N', 'Y', 'Y', 'Y', 'N', 'N', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Cost Revaluation', 'Cost Revaluation', TO_TIMESTAMP('2022-07-22 09:07:48', 'YYYY-MM-DD HH24:MI:SS'), 100)
;

-- 2022-07-22T09:07:48.946Z
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
  AND t.C_DocType_ID = 541047
  AND NOT EXISTS(SELECT 1 FROM C_DocType_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.C_DocType_ID = t.C_DocType_ID)
;

-- 2022-07-22T09:07:48.948Z
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
                                                                                                                                                               AND doctype.C_DocType_ID = 541047
                                                                                                                                                               AND rol.IsManual = 'N')
;


UPDATE ad_sequence
SET decimalpattern = NULL, currentnext = 10000000, updated = now()
WHERE ad_sequence_id = 555982
;



-- 2022-07-22T09:35:47.080Z
-- URL zum Konzept
UPDATE C_DocType
SET ad_org_id = 0, DocNoSequence_ID=555982, Updated=TO_TIMESTAMP('2022-07-22 09:35:47', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE C_DocType_ID = 541047
;

-- 2022-07-25T18:04:33.134Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET AccessLevel='1',Updated=TO_TIMESTAMP('2022-07-25 19:04:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=542190
;

-- 2022-07-25T18:04:42.138Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET IsChangeLog='Y',Updated=TO_TIMESTAMP('2022-07-25 19:04:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=542190
;

-- 2022-07-25T18:04:45.956Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET IsEnableRemoteCacheInvalidation='N',Updated=TO_TIMESTAMP('2022-07-25 19:04:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=542190
;

-- 2022-07-25T18:06:38.315Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2022-07-25 19:06:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=702158
;
