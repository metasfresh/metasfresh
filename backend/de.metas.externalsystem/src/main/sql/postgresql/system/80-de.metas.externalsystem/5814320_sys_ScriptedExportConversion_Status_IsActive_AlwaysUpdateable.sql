/*
 * #%L
 * de.metas.externalsystem
 * %%
 * Copyright (C) 2026 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

-- Keep the ScriptedExportConversion_Status IsActive field editable even though its status tab is a
-- child of an already-processed shipment (M_InOut). The status tab (AD_Tab 549295) lives under the
-- shipment window; a shipment carrying an export status is always CO/CL (processed). metasfresh's
-- GridField gate (GridField#isEditablePara: "if parent tab is processed or Not Active ... unless
-- IsAlwaysUpdateable") locks every field of a processed-parent child tab REGARDLESS of the field's
-- own AD_Field.IsReadOnly='N' — so the deactivate-a-stuck-in-flight-row escape hatch was not
-- reachable in the WebUI. Setting IsAlwaysUpdateable='Y' on this table's IsActive column is the
-- documented exception the gate honours, making the field editable on the processed parent.
-- AD_Column 592776 = IsActive on ExternalSystem_ScriptedExportConversion_Status (AD_Table 542617).
UPDATE AD_Column
SET    IsAlwaysUpdateable = 'Y',
       Updated = TO_TIMESTAMP('2026-07-16 10:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE  AD_Column_ID = 592776
  AND  IsAlwaysUpdateable <> 'Y'
;
