-- Data migration: convert the per-attribute boolean flags MobileUI_MFG_Config.IsLotNumberEditable /
-- IsBestBeforeDateEditable into rows of the MobileUI_MFG_Config_Attribute child table introduced by
-- 5821570_sys_mobileMfgReceiveEditableAttributes.sql.
--
-- For every existing MobileUI_MFG_Config row, seed a Lot-number child row (SeqNo 10) and a
-- Best-before-date child row (SeqNo 20), with IsActive = the resolved old boolean (Y -> active /
-- editable, N -> inactive / present-but-disabled). This preserves today's effective behaviour
-- unchanged (vanilla default: both flags are Y, so both rows land active).
--
-- The two M_Attribute codes (Lot-Nummer / HU_BestBeforeDate) are resolved by AD_Column.Value the
-- same way the application itself resolves an AttributeCode (org.adempiere.mm.attributes.api.impl.
-- AttributeDAO#retrieveAttributesMap loads M_Attribute with no AD_Client_ID filter) - client-specific
-- row preferred, falling back to the system (AD_Client_ID=0) row.
--
-- The companion DDL-retire migration (dropping the boolean columns + their AD_Field/AD_UI_Element/
-- AD_Column rows) is a separate script per the data-fix-vs-pure-DDL split:
-- 5821600_sys_mobileMfg_retireEditableAttributeFlagColumns.sql (must run AFTER this one).

-- Pre-flight: every existing MobileUI_MFG_Config row must be able to resolve BOTH well-known
-- M_Attribute codes (client-specific or system-level M_Client_ID=0 fallback). If not, this is an
-- environment missing core master data (these attributes are referenced throughout core Java code
-- via org.adempiere.mm.attributes.api.AttributeConstants), not something this migration should
-- silently paper over.
DO $$
DECLARE
	v_unresolved_count int;
BEGIN
	SELECT count(*) INTO v_unresolved_count
	FROM MobileUI_MFG_Config cfg
	WHERE NOT EXISTS (
			SELECT 1 FROM M_Attribute m
			WHERE m.Value = 'Lot-Nummer' AND m.IsActive = 'Y' AND (m.AD_Client_ID = cfg.AD_Client_ID OR m.AD_Client_ID = 0)
		)
		OR NOT EXISTS (
			SELECT 1 FROM M_Attribute m
			WHERE m.Value = 'HU_BestBeforeDate' AND m.IsActive = 'Y' AND (m.AD_Client_ID = cfg.AD_Client_ID OR m.AD_Client_ID = 0)
		);

	IF v_unresolved_count > 0 THEN
		RAISE EXCEPTION 'MobileUI_MFG_Config editable-attribute migration: % config row(s) cannot resolve the well-known Lot-Nummer / HU_BestBeforeDate M_Attribute (neither client-specific nor AD_Client_ID=0 fallback)', v_unresolved_count;
	END IF;
END $$
;

-- 2026-09-01 11:00:00 / 11:00:01 (one INSERT statement, two UNION ALL branches with distinguishable timestamps)
INSERT INTO MobileUI_MFG_Config_Attribute
	(MobileUI_MFG_Config_Attribute_ID, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive, MobileUI_MFG_Config_ID, M_Attribute_ID, SeqNo)
SELECT
	nextval('mobileui_mfg_config_attribute_seq'),
	cfg.AD_Client_ID,
	cfg.AD_Org_ID,
	TO_TIMESTAMP('2026-09-01 11:00:00', 'YYYY-MM-DD HH24:MI:SS'),
	99,
	TO_TIMESTAMP('2026-09-01 11:00:00', 'YYYY-MM-DD HH24:MI:SS'),
	99,
	cfg.IsLotNumberEditable,
	cfg.MobileUI_MFG_Config_ID,
	COALESCE(
		(SELECT m.M_Attribute_ID FROM M_Attribute m WHERE m.Value = 'Lot-Nummer' AND m.IsActive = 'Y' AND m.AD_Client_ID = cfg.AD_Client_ID ORDER BY m.M_Attribute_ID LIMIT 1),
		(SELECT m.M_Attribute_ID FROM M_Attribute m WHERE m.Value = 'Lot-Nummer' AND m.IsActive = 'Y' AND m.AD_Client_ID = 0 ORDER BY m.M_Attribute_ID LIMIT 1)
	),
	10
FROM MobileUI_MFG_Config cfg
UNION ALL
SELECT
	nextval('mobileui_mfg_config_attribute_seq'),
	cfg.AD_Client_ID,
	cfg.AD_Org_ID,
	TO_TIMESTAMP('2026-09-01 11:00:01', 'YYYY-MM-DD HH24:MI:SS'),
	99,
	TO_TIMESTAMP('2026-09-01 11:00:01', 'YYYY-MM-DD HH24:MI:SS'),
	99,
	cfg.IsBestBeforeDateEditable,
	cfg.MobileUI_MFG_Config_ID,
	COALESCE(
		(SELECT m.M_Attribute_ID FROM M_Attribute m WHERE m.Value = 'HU_BestBeforeDate' AND m.IsActive = 'Y' AND m.AD_Client_ID = cfg.AD_Client_ID ORDER BY m.M_Attribute_ID LIMIT 1),
		(SELECT m.M_Attribute_ID FROM M_Attribute m WHERE m.Value = 'HU_BestBeforeDate' AND m.IsActive = 'Y' AND m.AD_Client_ID = 0 ORDER BY m.M_Attribute_ID LIMIT 1)
	),
	20
FROM MobileUI_MFG_Config cfg
;
