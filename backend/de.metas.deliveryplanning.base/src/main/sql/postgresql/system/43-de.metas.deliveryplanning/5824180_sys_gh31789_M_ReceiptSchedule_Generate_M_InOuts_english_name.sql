-- ---------------------------------------------------------------------------------------------
-- AD_Process 540557 "Wareneingangsdispo zu Wareneingang"
-- (de.metas.inoutcandidate.process.M_ReceiptSchedule_Generate_M_InOuts) has en_US and en_GB rows,
-- but both hold the GERMAN base text: they were seeded from the base name and never translated
-- (IsTranslated='N' on every row). An English-speaking user therefore reads German for this action
-- on the receipt-schedule windows.
--
-- This is the same defect the fr_CH convention in
-- 5820520_sys_M_Delivery_Planning_GenerateDeliveryInstruction_IsComplete.sql was written against,
-- and its reasoning transfers verbatim: a seeded German copy in a non-German row "is unusable, not
-- merely untranslated". English is a real target language here, so the row gets correct English and
-- IsTranslated='Y' - unlike fr_CH, where English text is a readable stand-in and stays 'N' so a
-- translator can still find it.
--
-- Scope is deliberately the translation only. 540557's BEHAVIOUR is not touched by this change set:
-- the two other scripts here that name it (5822340, 5822620) reference it in comments as naming
-- precedent and modify nothing.
--
-- NOTE the consequence, which is not introduced here but is made visible by it: AD_Process 585667,
-- this change set's own multi-row receive on window 542190, already carries the en_US name
-- "Generate Material Receipts". Once 540557 reads correctly, the two share a caption in English as
-- they already do in German. They are NOT the same operation - 585667 is selection-scoped, packs per
-- the row's own configuration and can group several lines onto one receipt, while 540557 scopes by
-- query and builds one receipt per schedule. Whether 585667 should be renamed to say so is an open
-- decision for the owner and is deliberately NOT pre-empted here: giving the CORE process anything
-- other than its accurate English name to dodge a collision would be the wrong half to bend.
-- ---------------------------------------------------------------------------------------------

UPDATE AD_Process_Trl
   SET Name = 'Generate Material Receipts',
       IsTranslated = 'Y',
       Updated = TO_TIMESTAMP('2026-09-11 12:00:00', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
 WHERE AD_Process_ID = 540557
   AND AD_Language = 'en_US'
;

UPDATE AD_Process_Trl
   SET Name = 'Generate Material Receipts',
       IsTranslated = 'Y',
       Updated = TO_TIMESTAMP('2026-09-11 12:00:01', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
 WHERE AD_Process_ID = 540557
   AND AD_Language = 'en_GB'
;
