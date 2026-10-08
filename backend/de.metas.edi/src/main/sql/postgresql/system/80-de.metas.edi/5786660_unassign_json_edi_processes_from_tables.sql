/*
 * #%L
 * de.metas.edi
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

-- Process: C_Doc_Outbound_Log_Selection_Export_JSON(de.metas.edi.process.export.json.C_Doc_Outbound_Log_Selection_Export_JSON)
-- Table: C_Doc_Outbound_Log
-- EntityType: de.metas.esb.edi
-- 2026-02-04T18:08:54.656Z
DELETE FROM AD_Table_Process WHERE AD_Table_Process_ID=541555
;

-- Process: C_Invoice_Selection_Export_JSON(de.metas.edi.process.export.json.C_Invoice_Selection_Export_JSON)
-- Table: C_Invoice
-- EntityType: de.metas.esb.edi
-- 2026-02-04T18:09:15.480Z
DELETE FROM AD_Table_Process WHERE AD_Table_Process_ID=541556
;

-- Process: M_InOut_Selection_Export_JSON(de.metas.edi.process.export.json.M_InOut_Selection_Export_JSON)
-- Table: M_InOut
-- Window: Lieferung(169,D)
-- EntityType: de.metas.esb.edi
-- 2026-02-04T18:09:36.598Z
DELETE FROM AD_Table_Process WHERE AD_Table_Process_ID=541558
;
