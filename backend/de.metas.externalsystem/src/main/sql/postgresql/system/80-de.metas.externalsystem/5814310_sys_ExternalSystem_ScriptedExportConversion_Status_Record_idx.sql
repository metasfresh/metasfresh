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

-- Supports the by-source-record lookups (getActiveBySourceRecord / getLatestBySourceRecord in
-- ExternalSystemExportStatusRepository) that filter on (AD_Table_ID, Record_ID) WITHOUT the config id.
-- These feed the reverse/reactivate/void in-flight guard, run on every such document action. The
-- existing UNIQUE index (config, AD_Table_ID, Record_ID) leads with the config id, so a predicate
-- lacking it cannot seek that index — this index makes the source-record lookup an index seek.
-- Record_ID leads: it is highly selective (one value per source document), whereas AD_Table_ID has
-- only a handful of distinct values — so the more decisive column goes first.
CREATE INDEX IF NOT EXISTS ExtSysScriptedExpConv_Status_Record
    ON public.ExternalSystem_ScriptedExportConversion_Status (Record_ID, AD_Table_ID)
;
