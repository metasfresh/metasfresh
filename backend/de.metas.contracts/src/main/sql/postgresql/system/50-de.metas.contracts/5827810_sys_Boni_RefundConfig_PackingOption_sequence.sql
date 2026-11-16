-- Follow-up to 5827800: table sequence for C_Flatrate_RefundConfig_PackingOption (same pattern as the other new module tables),
-- and the M_Product_Category_ID column description taken from its element.
--
-- IDs allocated from idserver.metas.de on 2026-10-05:
--   AD_Sequence 556658 (C_Flatrate_RefundConfig_PackingOption)

INSERT INTO AD_Sequence (AD_Client_ID, AD_Org_ID, AD_Sequence_ID, Created, CreatedBy, CurrentNext, CurrentNextSys, Description,
                         IncrementNo, IsActive, IsAudited, IsAutoSequence, IsTableID, Name, StartNo, Updated, UpdatedBy)
VALUES (0, 0, 556658 /*From ID Server*/, TO_TIMESTAMP('2026-10-05 11:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100, 1000000, 50000,
        'Table C_Flatrate_RefundConfig_PackingOption', 1, 'Y', 'N', 'Y', 'Y', 'C_Flatrate_RefundConfig_PackingOption', 1000000,
        TO_TIMESTAMP('2026-10-05 11:00:01', 'YYYY-MM-DD HH24:MI:SS'), 100)
;

CREATE SEQUENCE IF NOT EXISTS C_FLATRATE_REFUNDCONFIG_PACKINGOPTION_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 START 1000000
;

UPDATE AD_Column
SET Description = (SELECT e.Description FROM AD_Element e WHERE e.AD_Element_ID = AD_Column.AD_Element_ID),
    Updated     = TO_TIMESTAMP('2026-10-05 11:00:02', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Column_ID = 593707
;
