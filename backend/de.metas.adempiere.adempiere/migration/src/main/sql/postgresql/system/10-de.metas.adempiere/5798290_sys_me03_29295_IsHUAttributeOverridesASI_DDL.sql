-- me03#29295: Add IsHUAttributeOverridesASI physical column (DDL only)

ALTER TABLE M_ShipmentSchedule_AttributeConfig ADD COLUMN IF NOT EXISTS IsHUAttributeOverridesASI CHAR(1) DEFAULT 'Y';
