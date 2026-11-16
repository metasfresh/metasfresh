-- gh#29852 — HU Consolidation: reword the "scanned unit not at the picking slot" error.
-- Message de.metas.hu_consolidation.LuNotAtPickingSlot (AD_Message_ID 545699, added by gh29689) is
-- thrown when a scanned GRAI resolves to a top-level HU that is not in the open picking slot. In HU
-- Consolidation that scanned unit is a TU (only TU->LU consolidation is supported; the slot holds
-- TUs), so the inherited "LU" wording is misleading. Reword to the neutral "HU" term, matching the
-- HU-worded distribution/inventory scan messages in the same set. The sibling LuExpectedAtTarget
-- (which IS about the target pallet) correctly keeps "LU".

UPDATE AD_Message
SET MsgText='Die HU befindet sich nicht am Kommissionierplatz',
    Updated=TO_TIMESTAMP('2026-06-25 21:30:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Message_ID=545699;

-- English override
UPDATE AD_Message_Trl
SET MsgText='The HU is not at the picking slot', IsTranslated='Y',
    Updated=TO_TIMESTAMP('2026-06-25 21:30:01','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Message_ID=545699 AND AD_Language='en_US';

-- German-holding translation rows (de_DE, de_CH, and any seeded copy) follow the base text;
-- preserve each row's existing IsTranslated flag.
UPDATE AD_Message_Trl
SET MsgText='Die HU befindet sich nicht am Kommissionierplatz',
    Updated=TO_TIMESTAMP('2026-06-25 21:30:02','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Message_ID=545699 AND AD_Language<>'en_US';
