-- gh#28337: Add WriteOffType discriminator to C_AllocationLine
-- Distinguishes standard write-off (WO) from bank fee (BF) for posting purposes.

-- 1. AD_Reference (List type) for WriteOffType
INSERT INTO AD_Reference (AD_Reference_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, Name, Description, ValidationType, EntityType, IsOrderByValue)
VALUES (542053, 0, 0, 'Y', TO_TIMESTAMP('2026-02-23 10:00','YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-02-23 10:00','YYYY-MM-DD HH24:MI'), 100,
        'C_AllocationLine WriteOffType', 'Discriminator for write-off amounts on allocation lines', 'L', 'D', 'N');

-- 2. AD_Ref_List: WO = Standard Write-Off
INSERT INTO AD_Ref_List (AD_Ref_List_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, Value, Name, AD_Reference_ID, EntityType)
VALUES (544125, 0, 0, 'Y', TO_TIMESTAMP('2026-02-23 10:00','YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-02-23 10:00','YYYY-MM-DD HH24:MI'), 100,
        'WO', 'Standard Write-Off', 542053, 'D');

-- 3. AD_Ref_List: BF = Bank Fee
INSERT INTO AD_Ref_List (AD_Ref_List_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, Value, Name, AD_Reference_ID, EntityType)
VALUES (544126, 0, 0, 'Y', TO_TIMESTAMP('2026-02-23 10:00','YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-02-23 10:00','YYYY-MM-DD HH24:MI'), 100,
        'BF', 'Bank Fee', 542053, 'D');

-- 4. AD_Element for WriteOffType
INSERT INTO AD_Element (AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, ColumnName, Name, PrintName, EntityType)
VALUES (584561, 0, 0, 'Y', TO_TIMESTAMP('2026-02-23 10:00','YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-02-23 10:00','YYYY-MM-DD HH24:MI'), 100,
        'WriteOffType', 'Write-Off Type', 'Write-Off Type', 'D');

-- 5. AD_Column on C_AllocationLine (AD_Table_ID=390)
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                       AD_Table_ID, AD_Element_ID, AD_Reference_ID, AD_Reference_Value_ID,
                       ColumnName, Name, Description,
                       FieldLength, IsMandatory, IsUpdateable, DefaultValue,
                       EntityType, IsSelectionColumn, SeqNo, PersonalDataCategory, Version)
VALUES (592061, 0, 0, 'Y', TO_TIMESTAMP('2026-02-23 10:00','YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-02-23 10:00','YYYY-MM-DD HH24:MI'), 100,
        390, 584561, 17, 542053,
        'WriteOffType', 'Write-Off Type', 'Discriminates standard write-off (WO) from bank fee (BF)',
        2, 'Y', 'Y', 'WO',
        'D', 'N', 0, 'NP', 0);

-- 6. DDL: Add column to C_AllocationLine
SELECT db_alter_table('C_AllocationLine', 'ALTER TABLE C_AllocationLine ADD COLUMN IF NOT EXISTS WriteOffType VARCHAR(2) DEFAULT ''WO'' NOT NULL');

-- 7. Backfill existing rows (safety net — DEFAULT should handle this, but explicit is better)
UPDATE C_AllocationLine SET WriteOffType = 'WO' WHERE WriteOffType IS NULL;
