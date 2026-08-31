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

package de.metas.edi.api.impl;

import de.metas.common.util.time.SystemTime;
import de.metas.esb.edi.model.I_EDI_EPCIS_Transmitted_SSCC;
import de.metas.externalsystem.scriptedexportconversion.ExternalSystemScriptedExportConversionConfigId;
import de.metas.inout.InOutId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.springframework.stereotype.Repository;

/**
 * Repository for {@code EDI_EPCIS_Transmitted_SSCC} — the per-SSCC EPCIS transmission ledger
 * that makes a resend idempotent: {@code get_epcis_events_json_fn} excludes any physical SSCC18
 * that already has an active ledger row for the given receiver config.
 * <p>
 * Repository Tables: EDI_EPCIS_Transmitted_SSCC
 * Repository Cluster: EpcisTransmittedSsccRepository
 */
@Repository
public class EpcisTransmittedSsccRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	/**
	 * Records that the given physical SSCC18 was transmitted to the given receiver config via the
	 * given shipment. Idempotent per ACTIVE transmission: does nothing if an <b>active</b> ledger row
	 * already exists for the {@code (configId, sscc18)} pair (that SSCC is currently transmitted).
	 * Otherwise inserts a fresh active row — so after the deactivate-escape-hatch + a confirmed
	 * re-send, a new active row is written (restoring the exactly-once guard), while the previously
	 * deactivated rows remain as a per-transmission history. The table's partial unique index
	 * (active-only) guarantees at most one active row per {@code (configId, sscc18)}.
	 */
	public void recordTransmittedIfAbsent(
			@NonNull final ExternalSystemScriptedExportConversionConfigId configId,
			@NonNull final InOutId inOutId,
			@NonNull final String sscc18)
	{
		// ACTIVE-only: a deactivated (historical) row must NOT block recording a fresh transmission
		final int existingActiveId = queryBL.createQueryBuilder(I_EDI_EPCIS_Transmitted_SSCC.class)
				.addEqualsFilter(I_EDI_EPCIS_Transmitted_SSCC.COLUMNNAME_ExternalSystem_Config_ScriptedExportConversion_ID, configId.getRepoId())
				.addEqualsFilter(I_EDI_EPCIS_Transmitted_SSCC.COLUMNNAME_SSCC18, sscc18)
				.addOnlyActiveRecordsFilter()
				.create()
				.firstIdOnly();
		if (existingActiveId > 0)
		{
			return;
		}

		final I_EDI_EPCIS_Transmitted_SSCC record = InterfaceWrapperHelper.newInstance(I_EDI_EPCIS_Transmitted_SSCC.class);
		record.setExternalSystem_Config_ScriptedExportConversion_ID(configId.getRepoId());
		record.setM_InOut_ID(inOutId.getRepoId());
		record.setSSCC18(sscc18);
		record.setTransmitted(SystemTime.asTimestamp());
		record.setIsActive(true);
		InterfaceWrapperHelper.saveRecord(record);
	}

	/**
	 * True iff the given shipment has at least one ACTIVE ledger row — i.e. at least one physical SSCC
	 * of that shipment was already transmitted to a receiver and the ledger row was not since
	 * deactivated. Active-only is intentional: deactivating the ledger row (the WebUI shipment-tab
	 * feature) is the sanctioned way to unblock both re-sending the SSCC and reversing/reactivating/
	 * voiding the shipment.
	 */
	public boolean hasActiveTransmittedForInOut(@NonNull final InOutId inOutId)
	{
		return queryBL.createQueryBuilder(I_EDI_EPCIS_Transmitted_SSCC.class)
				.addEqualsFilter(I_EDI_EPCIS_Transmitted_SSCC.COLUMNNAME_M_InOut_ID, inOutId.getRepoId())
				.addOnlyActiveRecordsFilter()
				.create()
				.anyMatch();
	}
}
