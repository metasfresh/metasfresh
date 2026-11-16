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

-- ScriptedExportConversion_Status: allow ONE ROW PER EXPORT ATTEMPT.
--
-- The status table shipped with a UNIQUE index on (config, AD_Table_ID, Record_ID), forcing a single
-- in-place-upserted row per source record. The intended design is one row per export ATTEMPT: each
-- enqueue / re-send is its own attempt row, and the status tab shows the attempt log newest-first.
-- Dropping the unique index lets each send attempt keep its own row (a per-attempt history); the
-- enqueue path now inserts a fresh row and the transitions (Enqueued / Sent / Error) update the latest
-- attempt, correlated by AD_PInstance_ID. The status tab already orders newest-first (OrderByClause
-- 'Updated DESC').
DROP INDEX IF EXISTS extsysscriptedexpconv_status_uq;
