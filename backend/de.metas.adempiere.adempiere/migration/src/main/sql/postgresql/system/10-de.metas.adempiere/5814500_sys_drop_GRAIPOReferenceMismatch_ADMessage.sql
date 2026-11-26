-- IDs allocated from idserver.metas.de:
--   AD_MigrationScript 5814500
--
-- Removes the GRAI PO-reference-mismatch picking error message (AD_Message_ID=545772,
-- Value de.metas.handlingunits.picking.GRAIPOReferenceMismatch). The scan-time PO-reference match it
-- backed has been removed: a scanned Migros returnable-asset GRAI is a dummy GRAI generated at shipment
-- (serial derived from the PO reference), so there is nothing authoritative to validate against at scan
-- time. No code references this message anymore.
-- The message was introduced by 5812440_sys_GRAIPOReferenceMismatch_ADMessage.sql, which cannot be un-applied.

DELETE FROM AD_Message_Trl WHERE AD_Message_ID=545772;
DELETE FROM AD_Message WHERE AD_Message_ID=545772 AND Value='de.metas.handlingunits.picking.GRAIPOReferenceMismatch';
