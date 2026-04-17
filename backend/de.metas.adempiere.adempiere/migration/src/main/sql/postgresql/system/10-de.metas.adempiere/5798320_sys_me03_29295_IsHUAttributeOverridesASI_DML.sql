-- me03#29295: Populate IsHUAttributeOverridesASI and set NOT NULL (DML, separate from DDL)

UPDATE M_ShipmentSchedule_AttributeConfig SET IsHUAttributeOverridesASI = 'Y' WHERE IsHUAttributeOverridesASI IS NULL;
ALTER TABLE M_ShipmentSchedule_AttributeConfig ALTER COLUMN IsHUAttributeOverridesASI SET NOT NULL;
