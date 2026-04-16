-- me03#29295: Add IsHUAttributeOverridesASI physical column
-- Part 2: DDL + DML (separate from AD metadata to avoid pending trigger events)

ALTER TABLE M_ShipmentSchedule_AttributeConfig ADD COLUMN IF NOT EXISTS IsHUAttributeOverridesASI CHAR(1) DEFAULT 'Y';
UPDATE M_ShipmentSchedule_AttributeConfig SET IsHUAttributeOverridesASI = 'Y' WHERE IsHUAttributeOverridesASI IS NULL;
ALTER TABLE M_ShipmentSchedule_AttributeConfig ALTER COLUMN IsHUAttributeOverridesASI SET NOT NULL;
