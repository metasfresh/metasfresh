-- QtyDemand_QtySupply_V: API view of the Material Cockpit V2.
-- Created by after_migration_M_MaterialCockpit_rebuild() as a plain SELECT * on M_MaterialCockpit_Base_V,
-- or on the customer override view '%_MaterialCockpit_V' if one exists.

DROP VIEW IF EXISTS QtyDemand_QtySupply_V
;

CREATE OR REPLACE VIEW QtyDemand_QtySupply_V AS
SELECT *
FROM M_MaterialCockpit_Base_V
;
