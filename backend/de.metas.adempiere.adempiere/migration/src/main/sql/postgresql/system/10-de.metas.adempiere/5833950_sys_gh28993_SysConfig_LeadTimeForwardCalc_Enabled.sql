-- Forward opt-in for the shipper-lead-time DatePromised calculation (DatePromised = workday(DateOrdered, +LeadTimeDays)).
-- Default N (system level); an instance/org enables it via an AD_SysConfig override.
-- The backward PreparationDate derivation is NOT gated by this flag (it is opt-in by M_Shipper.LeadTimeDays presence).
--
-- IDs allocated from idserver.metas.de on 2026-11-09:
--   AD_SysConfig 541865

INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value)
VALUES (0,0,541865 /*From ID Server*/,'O',TO_TIMESTAMP('2026-11-09 22:00:00','YYYY-MM-DD HH24:MI:SS'),100,'If Y, a sales order''s DatePromised is calculated forward as workday(DateOrdered, M_Shipper.LeadTimeDays) when the shipper has a lead time and IsFixedDatePromised is N.','D','Y','de.metas.tourplanning.LeadTimeForwardCalc.Enabled',TO_TIMESTAMP('2026-11-09 22:00:00','YYYY-MM-DD HH24:MI:SS'),100,'N')
;
