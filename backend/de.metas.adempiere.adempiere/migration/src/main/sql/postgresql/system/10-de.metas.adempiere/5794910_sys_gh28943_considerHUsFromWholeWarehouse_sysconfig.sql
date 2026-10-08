-- gh#28943: Add/update SysConfig description for considerHUsFromTheWholeWarehouse

UPDATE AD_SysConfig
SET Description='When Y, the manufacturing issue plan considers HUs from all locators in the warehouse, not just the BOM line locator. This is useful when raw materials may be stored in different locators within the same warehouse.',
    Updated='2026-03-19 16:00',
    UpdatedBy=100
WHERE Name='de.metas.manufacturing.issue.plan.PPOrderIssuePlanCreateCommand.considerHUsFromTheWholeWarehouse';

-- If the SysConfig doesn't exist yet, create it with default N
INSERT INTO AD_SysConfig (AD_SysConfig_ID, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive, Name, Value, Description, ConfigurationLevel, EntityType)
SELECT 541801 /*From ID Server*/,
       0, 0,
       '2026-03-19 16:00', 100,
       '2026-03-19 16:00', 100,
       'Y',
       'de.metas.manufacturing.issue.plan.PPOrderIssuePlanCreateCommand.considerHUsFromTheWholeWarehouse',
       'N',
       'When Y, the manufacturing issue plan considers HUs from all locators in the warehouse, not just the BOM line locator. This is useful when raw materials may be stored in different locators within the same warehouse.',
       'S',
       'D'
WHERE NOT EXISTS (SELECT 1 FROM AD_SysConfig WHERE Name='de.metas.manufacturing.issue.plan.PPOrderIssuePlanCreateCommand.considerHUsFromTheWholeWarehouse');
