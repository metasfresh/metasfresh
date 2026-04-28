/*
 * #%L
 * de.metas.serviceprovider.base
 * %%
 * Copyright (C) 2019 metas GmbH
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

package de.metas.serviceprovider.timebooking.importer.failed;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import de.metas.common.util.CoalesceUtil;
import de.metas.externalsystem.ExternalSystem;
import de.metas.externalsystem.ExternalSystemId;
import de.metas.externalsystem.ExternalSystemRepository;
import de.metas.organization.OrgId;
import de.metas.serviceprovider.model.I_S_FailedTimeBooking;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.Adempiere;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.function.Supplier;

@Repository
@RequiredArgsConstructor
public class FailedTimeBookingRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final ExternalSystemRepository externalSystemRepository;

	@VisibleForTesting
	public static FailedTimeBookingRepository newInstanceForUnitTesting()
	{
		Adempiere.assertUnitTestMode();
		return new FailedTimeBookingRepository(new ExternalSystemRepository());
	}

	public FailedTimeBookingId save(@NonNull final FailedTimeBooking failedTimeBooking)
	{
		final Supplier<FailedTimeBookingId> failedIdByExternalIdAndSystemSupplier = () ->
				getOptionalByExternalIdAndSystem(failedTimeBooking.getExternalSystem(), failedTimeBooking.getExternalId())
				.map(FailedTimeBooking::getFailedTimeBookingId)
				.orElse(null);

		final FailedTimeBookingId failedTimeBookingId = CoalesceUtil.coalesceSuppliers(
				failedTimeBooking::getFailedTimeBookingId,
				failedIdByExternalIdAndSystemSupplier);

		final I_S_FailedTimeBooking record = InterfaceWrapperHelper.loadOrNew(failedTimeBookingId, I_S_FailedTimeBooking.class);

		record.setExternalId(failedTimeBooking.getExternalId());
		record.setExternalSystem_ID(failedTimeBooking.getExternalSystem().getId().getRepoId());

		record.setJSONValue(failedTimeBooking.getJsonValue());
		record.setImportErrorMsg(failedTimeBooking.getErrorMsg());

		InterfaceWrapperHelper.saveRecord(record);

		return FailedTimeBookingId.ofRepoId(record.getS_FailedTimeBooking_ID());
	}

	public void delete(@NonNull final FailedTimeBookingId failedTimeBookingId)
	{
		final I_S_FailedTimeBooking record = InterfaceWrapperHelper.load(failedTimeBookingId, I_S_FailedTimeBooking.class);

		InterfaceWrapperHelper.delete(record);
	}

	public Optional<FailedTimeBooking> getOptionalByExternalIdAndSystem(@NonNull final ExternalSystem externalSystem,
																	    @NonNull final String externalId)
	{
		return queryBL.createQueryBuilder(I_S_FailedTimeBooking.class)
				.addEqualsFilter(I_S_FailedTimeBooking.COLUMNNAME_ExternalSystem_ID, externalSystem.getId() )
				.addEqualsFilter(I_S_FailedTimeBooking.COLUMNNAME_ExternalId, externalId)
				.create()
				.firstOnlyOptional(I_S_FailedTimeBooking.class)
				.map(this::buildFailedTimeBooking);
	}

	public ImmutableList<FailedTimeBooking> listBySystem(@NonNull final ExternalSystem externalSystem)
	{
		return queryBL.createQueryBuilder(I_S_FailedTimeBooking.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_S_FailedTimeBooking.COLUMNNAME_ExternalSystem_ID, externalSystem.getId() )
				.create()
				.list()
				.stream()
				.map(this::buildFailedTimeBooking)
				.collect(ImmutableList.toImmutableList());
	}

	private FailedTimeBooking buildFailedTimeBooking(@NonNull final I_S_FailedTimeBooking record)
	{
		final ExternalSystem externalSystem = externalSystemRepository.getById(ExternalSystemId.ofRepoId(record.getExternalSystem_ID()));

		return FailedTimeBooking.builder()
				.orgId(OrgId.ofRepoId(record.getAD_Org_ID()))
				.failedTimeBookingId(FailedTimeBookingId.ofRepoId(record.getS_FailedTimeBooking_ID()))
				.externalId(record.getExternalId())
				.externalSystem(externalSystem)
				.jsonValue(record.getJSONValue())
				.errorMsg(record.getImportErrorMsg())
				.build();
	}

}
