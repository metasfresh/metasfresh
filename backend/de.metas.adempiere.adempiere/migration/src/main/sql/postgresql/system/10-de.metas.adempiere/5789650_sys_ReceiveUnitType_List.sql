-- ReceiveUnitType: convert from plain String to List reference

-- 2026-02-22T14:00:00
-- AD_Reference: ReceiveUnitType (List)
INSERT INTO AD_Reference (AD_Client_ID,IsActive,Created,CreatedBy,IsOrderByValue,Updated,UpdatedBy,AD_Reference_ID,ValidationType,Name,AD_Org_ID,EntityType)
VALUES (0,'Y',TO_TIMESTAMP('2026-02-22 14:00','YYYY-MM-DD HH24:MI'),0,'N',TO_TIMESTAMP('2026-02-22 14:00','YYYY-MM-DD HH24:MI'),0,542051,'L','ReceiveUnitType',0,'D')
;

-- 2026-02-22T14:00:00.100
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Help,Name,Description, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy)
SELECT l.AD_Language, t.AD_Reference_ID, t.Help,t.Name,t.Description, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy
FROM AD_Language l, AD_Reference t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Reference_ID=542051
AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

--
-- AD_Ref_List: CU
--
-- 2026-02-22T14:00:01
INSERT INTO AD_Ref_List (AD_Reference_ID,AD_Client_ID,IsActive,Created,CreatedBy,Name,Updated,UpdatedBy,AD_Ref_List_ID,ValueName,Value,AD_Org_ID,Description,EntityType)
VALUES (542051,0,'Y',TO_TIMESTAMP('2026-02-22 14:00','YYYY-MM-DD HH24:MI'),0,'CU',TO_TIMESTAMP('2026-02-22 14:00','YYYY-MM-DD HH24:MI'),0,544120,'CU','CU',0,NULL,'D')
;

-- 2026-02-22T14:00:01.100
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Name,Description, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy)
SELECT l.AD_Language, t.AD_Ref_List_ID, t.Name,t.Description, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy
FROM AD_Language l, AD_Ref_List t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Ref_List_ID=544120
AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2026-02-22T14:00:01.200
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='CU', Description=NULL, Updated=TO_TIMESTAMP('2026-02-22 14:00','YYYY-MM-DD HH24:MI'), UpdatedBy=0
WHERE AD_Language='en_US' AND AD_Ref_List_ID=544120
;

--
-- AD_Ref_List: TU
--
-- 2026-02-22T14:00:02
INSERT INTO AD_Ref_List (AD_Reference_ID,AD_Client_ID,IsActive,Created,CreatedBy,Name,Updated,UpdatedBy,AD_Ref_List_ID,ValueName,Value,AD_Org_ID,Description,EntityType)
VALUES (542051,0,'Y',TO_TIMESTAMP('2026-02-22 14:00','YYYY-MM-DD HH24:MI'),0,'TU',TO_TIMESTAMP('2026-02-22 14:00','YYYY-MM-DD HH24:MI'),0,544121,'TU','TU',0,NULL,'D')
;

-- 2026-02-22T14:00:02.100
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Name,Description, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy)
SELECT l.AD_Language, t.AD_Ref_List_ID, t.Name,t.Description, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy
FROM AD_Language l, AD_Ref_List t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Ref_List_ID=544121
AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2026-02-22T14:00:02.200
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='TU', Description=NULL, Updated=TO_TIMESTAMP('2026-02-22 14:00','YYYY-MM-DD HH24:MI'), UpdatedBy=0
WHERE AD_Language='en_US' AND AD_Ref_List_ID=544121
;

--
-- Update AD_Column: MobileUI_MFG_Config.ReceiveUnitType -> List reference, NOT NULL, default CU
--
-- 2026-02-22T14:00:03
UPDATE AD_Column
SET AD_Reference_ID=17, AD_Reference_Value_ID=542051, IsMandatory='Y', DefaultValue='CU', Updated=TO_TIMESTAMP('2026-02-22 14:00','YYYY-MM-DD HH24:MI'), UpdatedBy=0
WHERE AD_Column_ID=592054
;

-- Populate existing rows before making NOT NULL
-- 2026-02-22T14:00:03.100
UPDATE MobileUI_MFG_Config SET ReceiveUnitType='CU' WHERE ReceiveUnitType IS NULL
;

-- 2026-02-22T14:00:03.200
INSERT INTO t_alter_column VALUES('MobileUI_MFG_Config','ReceiveUnitType','VARCHAR(2)','NOT NULL','CU')
;

--
-- Update AD_Column: MobileUI_UserProfile_MFG.ReceiveUnitType -> List reference, nullable
--
-- 2026-02-22T14:00:04
UPDATE AD_Column
SET AD_Reference_ID=17, AD_Reference_Value_ID=542051, Updated=TO_TIMESTAMP('2026-02-22 14:00','YYYY-MM-DD HH24:MI'), UpdatedBy=0
WHERE AD_Column_ID=592055
;

--
-- Check constraints
--
-- 2026-02-22T14:00:05
ALTER TABLE MobileUI_MFG_Config ADD CONSTRAINT ReceiveUnitType_check CHECK (ReceiveUnitType IN ('CU','TU'))
;

-- 2026-02-22T14:00:05.100
ALTER TABLE MobileUI_UserProfile_MFG ADD CONSTRAINT ReceiveUnitType_check CHECK (ReceiveUnitType IS NULL OR ReceiveUnitType IN ('CU','TU'))
;
