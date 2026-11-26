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

-- M_InOut.EPCIS_ExportStatus virtual column — roll up the PER-CONFIG LATEST attempt, not the
-- worst-status-across-all-active-rows.
--
-- The status table now keeps one row PER EXPORT ATTEMPT (the single-row unique index was dropped),
-- so a source record can carry several active rows for the same config: e.g. an errored first
-- attempt (E) AND a later successful re-send (S), both active (the attempt history is intentionally
-- kept so the status tab shows every attempt newest-first). The original ColumnSql ranked the WORST
-- status across ALL active rows, so the errored first attempt (rank 1) permanently outranked the
-- later Sent attempt (rank 3) — the roll-up got stuck at Error forever after any failure.
--
-- The corrected SQL first reduces to the LATEST attempt per config (DISTINCT ON config, newest
-- Status_ID), then rolls up the worst status across those per-config-latest values — matching the
-- Java ExternalSystemExportStatusService.computeRollUp() precedence (Error/Invalid > in-flight > Sent).
UPDATE AD_Column SET ColumnSql=
'(select rolled.ExportStatus from (
   select distinct on (s.ExternalSystem_Config_ScriptedExportConversion_ID)
          s.ExportStatus, s.Updated
   from ExternalSystem_ScriptedExportConversion_Status s
   where s.AD_Table_ID=319 and s.Record_ID=M_InOut.M_InOut_ID and s.IsActive=''Y''
   order by s.ExternalSystem_Config_ScriptedExportConversion_ID,
            s.ExternalSystem_ScriptedExportConversion_Status_ID desc
 ) rolled
 order by case rolled.ExportStatus when ''E'' then 1 when ''I'' then 1 when ''P'' then 2 when ''U'' then 2 when ''D'' then 2 when ''S'' then 3 when ''N'' then 3 else 4 end,
          rolled.Updated desc
 limit 1)',
Updated=TO_TIMESTAMP('2026-07-16 12:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Column_ID=592790;
