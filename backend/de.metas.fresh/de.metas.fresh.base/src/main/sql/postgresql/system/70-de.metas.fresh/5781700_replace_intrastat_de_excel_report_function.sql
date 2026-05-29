/*
 * #%L
 * de.metas.fresh.base
 * %%
 * Copyright (C) 2025 metas GmbH
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

-- Value: Report for Intratrade DE Excel
-- Classname: de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess
-- 2025-12-17T12:06:28.915Z
UPDATE AD_Process SET SQLStatement='select * from de_metas_endcustomer_fresh_reports.IntraTradeShipmentsDE(@C_Period_ID@, @AD_Org_ID@)',Updated=TO_TIMESTAMP('2025-12-17 12:06:28.912000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_ID=584748
;
