-- 2026-06-18T00:00:00.000Z
-- Remove the now-unused AD_Message de.metas.picking.CarrierAdvise_MultipleShippersOnHU (AD_Message_ID 545756).
-- The E3 "multiple advise-enabled shippers on one HU" guard was removed (the shipper is legitimately
-- header-level); this message is no longer referenced by any code or feature. It was added on this branch by
-- migration 5807750_sys_CarrierAdvise_completion_messages.sql (which is immutable once committed), so the
-- removal ships as this new migration. Delete the _Trl rows first, then the base AD_Message row.

DELETE FROM AD_Message_Trl WHERE AD_Message_ID=545756
;

DELETE FROM AD_Message WHERE AD_Message_ID=545756
;
