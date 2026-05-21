-- Tax Declaration Iter 5 — add DocBaseType 'TXD' (Tax Declaration) + C_DocType row.
-- AD_Reference_ID 183 = "DocBaseType" reference list.
-- DocNoSequence reuses the existing AD_Sequence 'DocumentNo_C_TaxDeclaration' (AD_Sequence_ID=556598).

-- AD_Ref_List: Tax Declaration under AD_Reference 183
INSERT INTO AD_Ref_List (AD_Client_ID, AD_Org_ID, AD_Ref_List_ID, AD_Reference_ID, Created, CreatedBy, EntityType, IsActive, Name, Updated, UpdatedBy, Value, ValueName)
VALUES (0, 0, 544233, 183, TIMESTAMP '2026-05-21 00:00:00', 100, 'D', 'Y', 'Tax Declaration', TIMESTAMP '2026-05-21 00:00:00', 100, 'TXD', 'Tax Declaration')
;

INSERT INTO AD_Ref_List_Trl (AD_Language, AD_Ref_List_ID, Description, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Ref_List t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Ref_List_ID=544233
  AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- C_DocType row using DocBaseType='TXD'
INSERT INTO C_DocType (AD_Client_ID, AD_Org_ID, C_DocType_ID, Created, CreatedBy, DocBaseType, DocumentCopies, EntityType, GL_Category_ID, HasCharges, HasProforma, IsActive, IsCopyDescriptionToDocument, IsCreateCounter, IsDefault, IsDefaultCounterDoc, IsDocNoControlled, IsIndexed, IsInTransit, IsOverwriteDateOnComplete, IsOverwriteSeqOnComplete, IsPickQAConfirm, IsShipConfirm, IsSOTrx, IsSplitWhenDifference, Name, PrintName, Updated, UpdatedBy, DocNoSequence_ID)
VALUES (1000000, 0, 541176, TIMESTAMP '2026-05-21 00:00:00', 100, 'TXD', 1, 'D', 0, 'N', 'N', 'Y', 'Y', 'Y', 'N', 'N', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Tax Declaration', 'Tax Declaration', TIMESTAMP '2026-05-21 00:00:00', 100, 556598)
;

INSERT INTO C_DocType_Trl (AD_Language, C_DocType_ID, Description, DocumentNote, Name, PrintName, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy)
SELECT l.AD_Language, t.C_DocType_ID, t.Description, t.DocumentNote, t.Name, t.PrintName, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy
FROM AD_Language l, C_DocType t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.C_DocType_ID=541176
  AND NOT EXISTS (SELECT 1 FROM C_DocType_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_DocType_ID=t.C_DocType_ID)
;

-- Grant standard document-action access on this DocType to all non-manual roles (mirrors C_Customs_Invoice setup)
INSERT INTO AD_Document_Action_Access (AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, C_DocType_ID, AD_Ref_List_ID, AD_Role_ID)
(SELECT 1000000, 0, 'Y', TIMESTAMP '2026-05-21 00:00:00', 100, TIMESTAMP '2026-05-21 00:00:00', 100, doctype.C_DocType_ID, action.AD_Ref_List_ID, rol.AD_Role_ID
 FROM AD_Client client
 INNER JOIN C_DocType doctype ON (doctype.AD_Client_ID=client.AD_Client_ID)
 INNER JOIN AD_Ref_List action ON (action.AD_Reference_ID=135)
 INNER JOIN AD_Role rol ON (rol.AD_Client_ID=client.AD_Client_ID)
 WHERE client.AD_Client_ID=1000000 AND doctype.C_DocType_ID=541176 AND rol.IsManual='N')
;
