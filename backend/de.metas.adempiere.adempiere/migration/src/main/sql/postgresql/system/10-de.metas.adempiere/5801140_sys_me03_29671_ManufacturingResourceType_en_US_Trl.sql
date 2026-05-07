-- me03#29671 follow-up: AD_Ref_List values for AD_Reference 53223
-- (ManufacturingResourceType) — sync the en_US Trl Names + Descriptions to the
-- locked terminology. Earlier migrations updated AD_Ref_List.Name (base) and
-- AD_Ref_List.Description (base) but did not touch the en_US Trl rows, which is
-- what an en_US user actually sees in the WebUI.

-- Names (one-line per row) — fix "Work Station" → "Workstation"; ensure the
-- other four match the locked terms even if already correct.
UPDATE AD_Ref_List_Trl SET Name='External System',  IsTranslated='Y', Updated='2026-05-06 17:45', UpdatedBy=100
WHERE AD_Ref_List_ID=543704 AND AD_Language='en_US';
UPDATE AD_Ref_List_Trl SET Name='Production Line',  IsTranslated='Y', Updated='2026-05-06 17:45', UpdatedBy=100
WHERE AD_Ref_List_ID=53244  AND AD_Language='en_US';
UPDATE AD_Ref_List_Trl SET Name='Plant',            IsTranslated='Y', Updated='2026-05-06 17:45', UpdatedBy=100
WHERE AD_Ref_List_ID=53245  AND AD_Language='en_US';
UPDATE AD_Ref_List_Trl SET Name='Work Center',      IsTranslated='Y', Updated='2026-05-06 17:45', UpdatedBy=100
WHERE AD_Ref_List_ID=53246  AND AD_Language='en_US';
UPDATE AD_Ref_List_Trl SET Name='Workstation',      IsTranslated='Y', Updated='2026-05-06 17:45', UpdatedBy=100
WHERE AD_Ref_List_ID=53247  AND AD_Language='en_US';

-- Descriptions
UPDATE AD_Ref_List_Trl
SET Description='External System: External system integration (e.g. Shopware, Alberta, external controller). Configured via ExternalSystem_Config_ID.',
    IsTranslated='Y', Updated='2026-05-06 17:45', UpdatedBy=100
WHERE AD_Ref_List_ID=543704 AND AD_Language='en_US';

UPDATE AD_Ref_List_Trl
SET Description='Production Line: Production line within a Work Center.',
    IsTranslated='Y', Updated='2026-05-06 17:45', UpdatedBy=100
WHERE AD_Ref_List_ID=53244 AND AD_Language='en_US';

UPDATE AD_Ref_List_Trl
SET Description='Plant: Manufacturing site. PP_Order.S_Resource_ID points at a Plant.',
    IsTranslated='Y', Updated='2026-05-06 17:45', UpdatedBy=100
WHERE AD_Ref_List_ID=53245 AND AD_Language='en_US';

UPDATE AD_Ref_List_Trl
SET Description='Work Center: Aggregate or department within a Plant. Groups multiple Workstations under one logical unit.',
    IsTranslated='Y', Updated='2026-05-06 17:45', UpdatedBy=100
WHERE AD_Ref_List_ID=53246 AND AD_Language='en_US';

UPDATE AD_Ref_List_Trl
SET Description='Workstation: Physical scannable device within a Workplace (printer, scale, machine). Operators identify themselves by scanning the Workstation''s QR code.',
    IsTranslated='Y', Updated='2026-05-06 17:45', UpdatedBy=100
WHERE AD_Ref_List_ID=53247 AND AD_Language='en_US';
