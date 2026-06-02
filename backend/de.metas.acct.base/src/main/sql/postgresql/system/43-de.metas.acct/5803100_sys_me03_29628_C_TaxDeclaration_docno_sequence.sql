-- Tax Declaration: add AD_Sequence for DocumentNo auto-assign on C_TaxDeclaration
-- PO framework requires an AD_Sequence named "DocumentNo_<TableName>" when IsUseDocSequence='Y'.

INSERT INTO AD_Sequence (
    AD_Client_ID, AD_Org_ID, AD_Sequence_ID,
    Created, CreatedBy, Updated, UpdatedBy,
    CurrentNext, CurrentNextSys, IncrementNo,
    IsActive, IsAudited, IsAutoSequence, IsTableID,
    Name, StartNo
)
VALUES (
    0, 0, 556598 /*From ID Server*/,
    TIMESTAMP '2026-05-19 00:00:00', 100, TIMESTAMP '2026-05-19 00:00:00', 100,
    1000000, 50000, 1,
    'Y', 'N', 'Y', 'N',
    'DocumentNo_C_TaxDeclaration', 1000000
);
