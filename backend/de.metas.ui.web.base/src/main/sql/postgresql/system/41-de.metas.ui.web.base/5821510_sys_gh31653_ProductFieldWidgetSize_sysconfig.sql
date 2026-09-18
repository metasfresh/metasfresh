-- gh#31653: order-line quick-input Produkt field width sysconfig (default: empty = unchanged).
-- Guarded by "WHERE NOT EXISTS": the fm206 (customer) migration 5824210 also creates this exact
-- row (same AD_SysConfig_ID=541854) as a fallback, because fm206's CI builds against RELEASED
-- core and may not yet contain this migration. Whichever of the two migrations runs first wins;
-- the other is then a no-op, so exactly one row ever exists regardless of run order.
INSERT INTO AD_SysConfig
  (AD_SysConfig_ID /*From ID Server*/, AD_Client_ID, AD_Org_ID, IsActive, ConfigurationLevel, EntityType,
   Name, Value, Description, CreatedBy, UpdatedBy, Created, Updated)
SELECT
   541854, 0, 0, 'Y', 'S', 'de.metas.ui.web',
   'webui.quickinput.ProductFieldWidgetSize',
   '',
   'Widget size (width) of the Product field in the order-line quick-input (Schnellerfassung / Batch Entry) panel of sales and purchase orders. Value: a WidgetSize code - S, M, L, XL or XXL; empty (or "-") = default width (unchanged). Recommended: L (approx +50% wider) so long product names are readable in the lookup dropdown. LIMITATIONS: (1) Takes effect ONLY when the Product field renders as a single-field Lookup - i.e. when the packing-instructions field (webui.quickinput.EnablePackingInstructionsField) is disabled. When packing instructions (or LU fields) are enabled, the Product field is combined with them into one Composed widget and this setting has NO effect. (2) For a Lookup field only S and L are styled; M, XL, XXL currently render at the default width.',
   100, 100, now(), now()
WHERE NOT EXISTS (SELECT 1 FROM AD_SysConfig WHERE AD_SysConfig_ID = 541854)
;
