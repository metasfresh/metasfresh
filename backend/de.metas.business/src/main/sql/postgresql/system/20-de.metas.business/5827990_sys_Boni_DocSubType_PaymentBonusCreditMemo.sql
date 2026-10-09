-- Boni: payment bonus credit memo ("Zahlungsbonus-Gutschrift").
-- When a customer deducts a bonus while paying a sales invoice, the payment allocation creates an internal sales credit memo
-- of this document sub type with one line per bonus product and allocates it against the invoice.
--   1) DocSubType 'PB' (reference 148 C_DocType SubType)
--   2) the sub type is offered for the base type ARC in the C_DocType window (validation rule 540219)
--   3) the document type of the demo client 1000000
--
-- IDs allocated from idserver.metas.de on 2026-10-06:
--   AD_Ref_List 544376
--   C_DocType   541179

INSERT INTO AD_Ref_List (AD_Client_ID, AD_Org_ID, AD_Ref_List_ID, AD_Reference_ID, Created, CreatedBy, EntityType, IsActive, Name, Description, Updated, UpdatedBy, Value, ValueName)
VALUES (0, 0, 544376 /*From ID Server*/, 148, TO_TIMESTAMP('2026-10-06 11:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100, 'D', 'Y', 'Zahlungsbonus-Gutschrift',
        'Interne Gutschrift über den Bonus, den der Kunde bei der Zahlung abgezogen hat.',
        TO_TIMESTAMP('2026-10-06 11:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100, 'PB', 'PaymentBonusCreditMemo')
;
INSERT INTO AD_Ref_List_Trl (AD_Language, AD_Ref_List_ID, Description, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Ref_List t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND t.AD_Ref_List_ID = 544376
  AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Ref_List_ID = t.AD_Ref_List_ID)
;
UPDATE AD_Ref_List_Trl
SET Name='Payment bonus credit memo', Description='Internal credit memo for the bonus that the customer deducted when paying.', IsTranslated='Y',
    Updated=TO_TIMESTAMP('2026-10-06 11:00:01', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Ref_List_ID = 544376 AND AD_Language = 'en_US'
;
UPDATE AD_Ref_List_Trl
SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-10-06 11:00:02', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Ref_List_ID = 544376 AND AD_Language IN ('de_DE', 'de_CH')
;

-- the C_DocType window offers the sub type for credit memos (ARC), like the refund credit memo 'RC'
UPDATE AD_Val_Rule
SET Code=REPLACE(Code, '(''@DocBaseType@''=''ARC'' AND AD_Ref_List.Value IN (''CQ'', ''CR'',''CS'', ''RI'', ''RC''))',
                       '(''@DocBaseType@''=''ARC'' AND AD_Ref_List.Value IN (''CQ'', ''CR'',''CS'', ''RI'', ''RC'', ''PB''))'),
    Updated=TO_TIMESTAMP('2026-10-06 11:00:03', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Val_Rule_ID = 540219
;

-- not printed: the customer already deducted the bonus, the credit memo only books it
INSERT INTO C_DocType (AD_Client_ID, AD_Org_ID, C_DocType_ID, Created, CreatedBy, DocBaseType, DocSubType, DocumentCopies, EntityType, GL_Category_ID,
                       HasCharges, HasProforma, IsActive, CopyDescriptionAndDocumentNote, IsCreateCounter, IsDefault, IsDefaultCounterDoc, IsDocNoControlled,
                       IsExcludeFromCommision, IsIndexed, IsInTransit, IsOverwriteDateOnComplete, IsOverwriteSeqOnComplete, IsPickQAConfirm, IsShipConfirm,
                       IsSOTrx, IsSplitWhenDifference, Name, PrintName, Description, Updated, UpdatedBy)
VALUES (1000000, 0, 541179 /*From ID Server*/, TO_TIMESTAMP('2026-10-06 11:00:04', 'YYYY-MM-DD HH24:MI:SS'), 100, 'ARC', 'PB', 0, 'D', 1000000,
        'N', 'N', 'Y', 'CD', 'N', 'N', 'N', 'Y',
        'N', 'N', 'N', 'N', 'N', 'N', 'N',
        'Y', 'N', 'Zahlungsbonus-Gutschrift', 'Zahlungsbonus-Gutschrift',
        'Interne Gutschrift über den Bonus, den der Kunde bei der Zahlung abgezogen hat. Sie wird bei der Zahlungszuordnung erstellt und mit der Rechnung verrechnet.',
        TO_TIMESTAMP('2026-10-06 11:00:04', 'YYYY-MM-DD HH24:MI:SS'), 100)
;
-- translations: German for every language, English for en_US
INSERT INTO C_DocType_Trl (AD_Language, C_DocType_ID, Description, DocumentNote, Name, PrintName, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language,
       t.C_DocType_ID,
       CASE
           WHEN l.AD_Language = 'en_US'
               THEN 'Internal credit memo for the bonus that the customer deducted when paying. It is created at the payment allocation and offset against the invoice.'
               ELSE t.Description
       END,
       t.DocumentNote,
       CASE WHEN l.AD_Language = 'en_US' THEN 'Payment bonus credit memo' ELSE t.Name END,
       CASE WHEN l.AD_Language = 'en_US' THEN 'Payment bonus credit memo' ELSE t.PrintName END,
       CASE WHEN l.AD_Language IN ('en_US', 'de_DE', 'de_CH') THEN 'Y' ELSE 'N' END,
       t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, C_DocType t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND t.C_DocType_ID = 541179
  AND NOT EXISTS (SELECT 1 FROM C_DocType_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.C_DocType_ID = t.C_DocType_ID)
;

-- the document actions of the client's roles, like for every other document type
INSERT INTO AD_Document_Action_Access (AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, C_DocType_ID, AD_Ref_List_ID, AD_Role_ID)
SELECT 1000000, 0, 'Y', TO_TIMESTAMP('2026-10-06 11:00:05', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-10-06 11:00:05', 'YYYY-MM-DD HH24:MI:SS'), 100,
       doctype.C_DocType_ID, action.AD_Ref_List_ID, rol.AD_Role_ID
FROM AD_Client client
         INNER JOIN C_DocType doctype ON (doctype.AD_Client_ID = client.AD_Client_ID)
         INNER JOIN AD_Ref_List action ON (action.AD_Reference_ID = 135)
         INNER JOIN AD_Role rol ON (rol.AD_Client_ID = client.AD_Client_ID)
WHERE client.AD_Client_ID = 1000000
  AND doctype.C_DocType_ID = 541179
  AND rol.IsManual = 'N'
;
