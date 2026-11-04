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

-- EPCIS transmission ledger: make the (config, SSCC18) unique index ACTIVE-ONLY (partial).
--
-- WHY: deactivating a ledger row is the sanctioned escape-hatch to re-send an SSCC. The full unique
-- index kept the deactivated row occupying the key, so recordTransmitted could neither reactivate it
-- nor insert a fresh row after a re-send — leaving the SSCC with NO active ledger row. The exactly-once
-- guard (get_epcis_events_json_fn / reverse guard) only matches active rows, so the NEXT send would
-- re-transmit it → duplicate. Making the unique index partial (WHERE isactive='Y') keeps the invariant
-- that matters — at most ONE ACTIVE row per (config, SSCC18) — while letting deactivated rows accumulate
-- as a per-transmission history, so each confirmed (re)send inserts a fresh active row.
DROP INDEX IF EXISTS edi_epcis_transmitted_sscc_uq;
CREATE UNIQUE INDEX edi_epcis_transmitted_sscc_uq
    ON public.edi_epcis_transmitted_sscc (externalsystem_config_scriptedexportconversion_id, sscc18)
    WHERE isactive = 'Y';
