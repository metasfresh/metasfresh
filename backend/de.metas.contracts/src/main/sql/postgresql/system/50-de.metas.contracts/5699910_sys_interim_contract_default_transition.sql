-- 2023-08-23T17:14:34.865196500Z
INSERT INTO C_Flatrate_Transition (AD_Client_ID, AD_Org_ID, C_Calendar_Contract_ID, C_Flatrate_Transition_ID, Created, CreatedBy, DocAction, DocStatus, EndsWithCalendarYear, IsActive, IsAutoCompleteNewTerm, IsNotifyUserInCharge, Name, Processed, Processing, TermDuration, TermDurationUnit, TermOfNotice, TermOfNoticeUnit, Updated, UpdatedBy)
VALUES (1000000, 1000000, 1000000, 540039, TO_TIMESTAMP('2023-08-23 19:14:34.858', 'YYYY-MM-DD HH24:MI:SS.US'), 100, 'CO', 'DR', 'N', 'Y', 'N', 'N', 'Default value for interim contract', 'N', 'N', 0, 'day', 0, 'day', TO_TIMESTAMP('2023-08-23 19:14:34.858', 'YYYY-MM-DD HH24:MI:SS.US'), 100)
;

-- 2023-08-23T17:15:38.094201500Z
UPDATE C_Flatrate_Transition
SET DocStatus='IP', Updated=TO_TIMESTAMP('2023-08-23 19:15:38.094', 'YYYY-MM-DD HH24:MI:SS.US'), UpdatedBy=100
WHERE C_Flatrate_Transition_ID = 540039
;

-- 2023-08-23T17:15:38.108842600Z
UPDATE C_Flatrate_Transition
SET DocAction='RE', DocStatus='CO', Processed='Y', Updated=TO_TIMESTAMP('2023-08-23 19:15:38.108', 'YYYY-MM-DD HH24:MI:SS.US'), UpdatedBy=100
WHERE C_Flatrate_Transition_ID = 540039
;

-- SysConfig Name: C_Flatrate_Conditions.INTERIM_CONTRACT_TRANSITION_DEFAULT_VALUE
-- SysConfig Value: 540039
-- 2023-08-23T17:18:48.533378Z
INSERT INTO AD_SysConfig (AD_Client_ID, AD_Org_ID, AD_SysConfig_ID, ConfigurationLevel, Created, CreatedBy, Description, EntityType, IsActive, Name, Updated, UpdatedBy, Value)
VALUES (0, 0, 541649, 'O', TO_TIMESTAMP('2023-08-23 19:18:48.34', 'YYYY-MM-DD HH24:MI:SS.US'), 100, 'Default value used for C_Flatrate_Conditions.C_Flatrate_Transition when adding a interim contract.', 'U', 'Y', 'C_Flatrate_Conditions.INTERIM_CONTRACT_TRANSITION_DEFAULT_VALUE', TO_TIMESTAMP('2023-08-23 19:18:48.34', 'YYYY-MM-DD HH24:MI:SS.US'), 100, '540039')
;
