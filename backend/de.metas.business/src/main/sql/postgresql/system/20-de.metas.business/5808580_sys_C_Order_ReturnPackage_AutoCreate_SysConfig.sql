-- Run mode: SWING_CLIENT
--
-- Ruecknahme Gebinde (return-package) feature, core (EntityType 'D').
--
-- On-switch for the C_Order interceptor that auto-creates the two return-package rows (EUR + H1)
-- on sales-order creation. Default 'N' (off): vanilla / other installs do NOT get the rows; the
-- central order flow stays untouched. The deployment that exposes the feature's UI turns this on
-- via set_sysconfig_value('C_Order.ReturnPackage.AutoCreate','Y') in its own (customer) repo.
--
-- System-level config (ConfigurationLevel 'S'), no _Trl companion → text written directly in German.
--
-- AD_SysConfig 541823 (from central ID server).

-- 2026-06-18 09:00:00
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value)
SELECT 0,0,541823 /*From ID Server*/,'S',TO_TIMESTAMP('2026-06-18 09:00:00','YYYY-MM-DD HH24:MI:SS'),100,
       'Automatisches Anlegen der beiden Rücknahme-Gebinde-Zeilen (Palettentyp EUR und H1) je Kundenauftrag beim Anlegen des Auftrags. Y=aktiv, N=deaktiviert. Standard: N (nur für die Installation aktivieren, die die zugehörige UI ausliefert).',
       'D','Y','C_Order.ReturnPackage.AutoCreate',TO_TIMESTAMP('2026-06-18 09:00:00','YYYY-MM-DD HH24:MI:SS'),100,'N'
WHERE NOT EXISTS (SELECT 1 FROM AD_SysConfig WHERE Name='C_Order.ReturnPackage.AutoCreate')
;
