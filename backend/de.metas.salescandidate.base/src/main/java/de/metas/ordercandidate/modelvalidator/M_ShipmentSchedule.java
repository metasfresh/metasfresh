/*
 * #%L
 * de.metas.salescandidate.base
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.ordercandidate.modelvalidator;

import com.google.common.collect.ImmutableSet;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.ordercandidate.api.IOLCandDAO;
import de.metas.ordercandidate.api.PoReferenceLookupKey;
import de.metas.organization.OrgId;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.time.LocalDateInterval;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.impl.DateTruncQueryFilterModifier;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ISysConfigBL;
import org.compiere.model.ModelValidator;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.util.Map;

@Interceptor(I_M_ShipmentSchedule.class)
@Component
public class M_ShipmentSchedule
{
	private static final String AD_SYS_CONFIG_NR_OF_LINES_WITH_SAME_PO_REFERENCE_DAYS_OFFSET = "shipmentSchedule.recomputeNrOfOLCandsWithSamePORef.daysOffset";

	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
	private final IOLCandDAO olCandDAO = Services.get(IOLCandDAO.class);

	@ModelChange(timings = ModelValidator.TYPE_AFTER_CHANGE, ifColumnsChanged = I_M_ShipmentSchedule.COLUMNNAME_POReference)
	public void updateNrOfOLCandsWithSamePoReference(@NonNull final I_M_ShipmentSchedule shipmentSchedule)
	{
		final ImmutableSet.Builder<PoReferenceLookupKey> poReferenceLookupKeySetBuilder = ImmutableSet.builder();

		final OrgId orgId = OrgId.ofRepoId(shipmentSchedule.getAD_Org_ID());

		final I_M_ShipmentSchedule oldRecord = InterfaceWrapperHelper.createOld(shipmentSchedule, I_M_ShipmentSchedule.class);

		if (oldRecord != null && Check.isNotBlank(oldRecord.getPOReference()))
		{
			final PoReferenceLookupKey poReferenceLookupKey = PoReferenceLookupKey.builder()
					.orgId(orgId)
					.poReference(oldRecord.getPOReference())
					.build();

			poReferenceLookupKeySetBuilder.add(poReferenceLookupKey);
		}

		if (Check.isNotBlank(shipmentSchedule.getPOReference()))
		{
			final PoReferenceLookupKey poReferenceLookupKey = PoReferenceLookupKey.builder()
					.orgId(orgId)
					.poReference(shipmentSchedule.getPOReference())
					.build();

			poReferenceLookupKeySetBuilder.add(poReferenceLookupKey);
		}

		final ImmutableSet<PoReferenceLookupKey> lookupKeySet = poReferenceLookupKeySetBuilder.build();

		if (lookupKeySet.isEmpty())
		{
			return;//nothing to do
		}

		final LocalDate shipmentScheduleCreated = TimeUtil.asLocalDate(shipmentSchedule.getCreated());

		final LocalDateInterval queryTimeWindow = getSearchingDateInterval(shipmentScheduleCreated);

		final Map<PoReferenceLookupKey, Integer> poReferenceKey2NrOfOLCands = olCandDAO
				.getNumberOfRecordsWithTheSamePOReference(lookupKeySet, queryTimeWindow);

		poReferenceKey2NrOfOLCands.forEach((key, value)
				-> updateNrOfOLCandsWithSamePOReference(key.getPoReference(), key.getOrgId(), value, queryTimeWindow));
	}

	private void updateNrOfOLCandsWithSamePOReference(@NonNull final String poReference,
			@NonNull final OrgId orgId,
			final int nrOfOLCandsWithSamePOReference,
			@Nullable final LocalDateInterval queryTimeWindow)
	{

		final IQueryBuilder<I_M_ShipmentSchedule> shipmentScheduleSToUpdate = queryBL.createQueryBuilder(I_M_ShipmentSchedule.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_ShipmentSchedule.COLUMNNAME_AD_Org_ID, orgId)
				.addEqualsFilter(I_M_ShipmentSchedule.COLUMNNAME_POReference, poReference);

		if (queryTimeWindow != null)
		{
			shipmentScheduleSToUpdate.addBetweenFilter(I_M_ShipmentSchedule.COLUMNNAME_Created,
					TimeUtil.asTimestamp(queryTimeWindow.getStartDate()), TimeUtil.asTimestamp(queryTimeWindow.getEndDate()), DateTruncQueryFilterModifier.DAY);
		}

		shipmentScheduleSToUpdate.create().list().forEach(shipmentSchedule -> {
			shipmentSchedule.setNrOfOLCandsWithSamePOReference(nrOfOLCandsWithSamePOReference);

			InterfaceWrapperHelper.save(shipmentSchedule);
		});
	}

	@Nullable
	private LocalDateInterval getSearchingDateInterval(@NonNull final LocalDate createdDate)
	{
		final int daysOffset = sysConfigBL.getIntValue(AD_SYS_CONFIG_NR_OF_LINES_WITH_SAME_PO_REFERENCE_DAYS_OFFSET, -1);

		if (daysOffset < 0)
		{
			return null;
		}

		return LocalDateInterval.builder()
				.startDate(createdDate.minusDays(daysOffset))
				.endDate(createdDate.plusDays(daysOffset))
				.build();
	}
}
