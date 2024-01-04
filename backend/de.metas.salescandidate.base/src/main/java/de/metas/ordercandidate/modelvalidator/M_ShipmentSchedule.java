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
import de.metas.material.cockpit.availableforsales.AvailableForSalesConfig;
import de.metas.material.cockpit.availableforsales.AvailableForSalesConfigRepo;
import de.metas.material.cockpit.availableforsales.AvailableForSalesService;
import de.metas.material.cockpit.availableforsales.EnqueueAvailableForSalesRequest;
import de.metas.material.cockpit.availableforsales.interceptor.AvailableForSalesUtil;
import de.metas.material.event.commons.AttributesKey;
import de.metas.ordercandidate.api.IOLCandDAO;
import de.metas.ordercandidate.api.PoReferenceLookupKey;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.time.LocalDateInterval;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.impl.DateTruncQueryFilterModifier;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.keys.AttributesKeys;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.service.ISysConfigBL;
import org.compiere.model.ModelValidator;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.util.Map;
import java.util.Properties;

@Interceptor(I_M_ShipmentSchedule.class)
@Component
public class M_ShipmentSchedule
{
	private static final String AD_SYS_CONFIG_NR_OF_LINES_WITH_SAME_PO_REFERENCE_DAYS_OFFSET = "shipmentSchedule.recomputeNrOfOLCandsWithSamePORef.daysOffset";

	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
	private final IOLCandDAO olCandDAO = Services.get(IOLCandDAO.class);
	private final ITrxManager trxManager = Services.get(ITrxManager.class);

	private final AvailableForSalesService availableForSalesService;
	private final AvailableForSalesConfigRepo availableForSalesConfigRepo;
	private final AvailableForSalesUtil availableForSalesUtil;

	public M_ShipmentSchedule(
			@NonNull final AvailableForSalesService availableForSalesService,
			@NonNull final AvailableForSalesConfigRepo availableForSalesConfigRepo,
			@NonNull final AvailableForSalesUtil availableForSalesUtil)
	{
		this.availableForSalesService = availableForSalesService;
		this.availableForSalesConfigRepo = availableForSalesConfigRepo;
		this.availableForSalesUtil = availableForSalesUtil;
	}

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

	@ModelChange( //
			timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE }, //
			ifColumnsChanged = { I_M_ShipmentSchedule.COLUMNNAME_QtyReserved,
					I_M_ShipmentSchedule.COLUMNNAME_M_Product_ID,
					I_M_ShipmentSchedule.COLUMNNAME_M_AttributeSetInstance_ID,
					I_M_ShipmentSchedule.COLUMNNAME_PreparationDate_Override,
					I_M_ShipmentSchedule.COLUMNNAME_PreparationDate,
					I_M_ShipmentSchedule.COLUMNNAME_C_OrderLine_ID,
					I_M_ShipmentSchedule.COLUMNNAME_IsClosed})
	public void triggerSyncAvailableForSales(@NonNull final I_M_ShipmentSchedule shipmentScheduleRecord)
	{
		final AvailableForSalesConfig config = availableForSalesConfigRepo.getConfig(
				AvailableForSalesConfigRepo.ConfigQuery.builder()
						.clientId(ClientId.ofRepoId(shipmentScheduleRecord.getAD_Client_ID()))
						.orgId(OrgId.ofRepoId(shipmentScheduleRecord.getAD_Org_ID()))
						.build());

		if (!config.isFeatureEnabled())
		{
			return; // nothing to do
		}

		syncAvailableForSalesForShipmentSchedule(shipmentScheduleRecord, config);

		if (!InterfaceWrapperHelper.isNew(shipmentScheduleRecord))
		{
			final I_M_ShipmentSchedule shipmentScheduleOld = InterfaceWrapperHelper.createOld(shipmentScheduleRecord, I_M_ShipmentSchedule.class);

			final AvailableForSalesConfig configOld = availableForSalesConfigRepo.getConfig(
					AvailableForSalesConfigRepo.ConfigQuery.builder()
							.clientId(ClientId.ofRepoId(shipmentScheduleOld.getAD_Client_ID()))
							.orgId(OrgId.ofRepoId(shipmentScheduleOld.getAD_Org_ID()))
							.build());

			if (isExtraSyncNeededForOldShipmentSchedule(shipmentScheduleRecord, shipmentScheduleOld))
			{
				syncAvailableForSalesForShipmentSchedule(shipmentScheduleOld, configOld);
			}
		}
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

	private boolean isExtraSyncNeededForOldShipmentSchedule(
			@NonNull final I_M_ShipmentSchedule currentShipmentScheduleRecord,
			@NonNull final I_M_ShipmentSchedule oldShipmentScheduleRecord)
	{
		return oldShipmentScheduleRecord.getM_Product_ID() != currentShipmentScheduleRecord.getM_Product_ID()
				|| oldShipmentScheduleRecord.getM_AttributeSetInstance_ID() != currentShipmentScheduleRecord.getM_AttributeSetInstance_ID()
				|| oldShipmentScheduleRecord.getAD_Org_ID() != currentShipmentScheduleRecord.getAD_Org_ID();
	}

	private void syncAvailableForSalesForShipmentSchedule(
			@NonNull final I_M_ShipmentSchedule shipmentScheduleRecord,
			@NonNull final AvailableForSalesConfig config)
	{
		final Properties ctx = Env.copyCtx(InterfaceWrapperHelper.getCtx(shipmentScheduleRecord));
		final ProductId productId = ProductId.ofRepoId(shipmentScheduleRecord.getM_Product_ID());
		final OrgId orgId = OrgId.ofRepoId(shipmentScheduleRecord.getAD_Org_ID());

		final AttributesKey storageAttributesKey = AttributesKeys
				.createAttributesKeyFromASIStorageAttributes(AttributeSetInstanceId.ofRepoIdOrNone(shipmentScheduleRecord.getM_AttributeSetInstance_ID()))
				.orElse(AttributesKey.NONE);

		final EnqueueAvailableForSalesRequest enqueueAvailableForSalesRequest = availableForSalesUtil.
				createRequestWithPreparationDateNow(ctx, config, productId, orgId, storageAttributesKey);

		trxManager.runAfterCommit(() -> availableForSalesService.enqueueAvailableForSalesRequest(enqueueAvailableForSalesRequest));
	}
}
