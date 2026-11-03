-- me03#29295: Populate IsHUAttributeOverridesASI for existing records (DML only)

UPDATE M_ShipmentSchedule_AttributeConfig SET IsHUAttributeOverridesASI = 'Y' WHERE IsHUAttributeOverridesASI IS NULL;
