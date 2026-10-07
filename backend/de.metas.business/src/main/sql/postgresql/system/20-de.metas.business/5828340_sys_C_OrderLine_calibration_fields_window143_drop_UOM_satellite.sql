-- Sales order line tab (AD_Tab 187): remove the line-UOM satellite widget of "Menge unkalibriert" (AD_UI_ElementField 542569,
-- migration 5828300). The WebUI renders only the first field of a quantity widget, so the satellite was never shown; the
-- line UOM stays visible in the "main" group of the same advanced-edit form.
--
-- Layout exception, documented on purpose: the calibration fields live in their own element group "calibration" (555804,
-- SeqNo 20) after the "main" group, i.e. after the Org/Client elements (SeqNo 210/220 of "main"), instead of placing
-- Org/Client last. This follows the existing layout of this tab, where the "main" group already has 20+ elements after
-- Org/Client, and the precedents AD_Window 162 / AD_Tab 242 and AD_Window 541079 / AD_Tab 543697, which also place an
-- advanced-edit group after the group holding Org/Client. The calibration fields are kept in an own group so they are
-- shown together and only on calibrated lines.
--
-- IDs allocated from idserver.metas.de on 2026-10-07:
--   AD_MigrationScript 5828340 (this script)

DELETE FROM AD_UI_ElementField WHERE AD_UI_ElementField_ID = 542569
;
