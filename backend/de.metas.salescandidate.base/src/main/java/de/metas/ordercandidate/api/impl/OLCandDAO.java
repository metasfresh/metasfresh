package de.metas.ordercandidate.api.impl;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2015 metas GmbH
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

import com.google.common.collect.ImmutableMap;
import de.metas.interfaces.I_C_OrderLine;
import de.metas.ordercandidate.api.IOLCandDAO;
import de.metas.ordercandidate.api.PoReferenceLookupKey;
import de.metas.ordercandidate.model.I_C_OLCand;
import de.metas.ordercandidate.model.I_C_Order_Line_Alloc;
import de.metas.organization.OrgId;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.time.LocalDateInterval;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.impl.DateTruncQueryFilterModifier;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.util.TimeUtil;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

public class OLCandDAO implements IOLCandDAO
{
	// makes no sense: we can't assume uniqueness among different external systems.
	// @Override
	// public OptionalInt getOLCandIdByExternalId(@NonNull final String olCandExternalId)
	// {
	// final int olCandId = Services.get(IQueryBL.class)
	// .createQueryBuilder(I_C_OLCand.class)
	// .addEqualsFilter(I_C_OLCand.COLUMN_ExternalId, olCandExternalId)
	// .create()
	// .firstIdOnly();
	//
	// return olCandId > 0 ? OptionalInt.of(olCandId) : OptionalInt.empty();
	// }

	@Override
	public List<I_C_OLCand> retrieveReferencing(final Properties ctx, final String tableName, final int recordId, final String trxName)
	{
		Check.assume(!Check.isEmpty(tableName), "Param 'tableName' is not empty");
		Check.assume(recordId > 0, "Param 'recordId' is > 0");

		final int tableId = Services.get(IADTableDAO.class).retrieveTableId(tableName);

		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_OLCand.class, ctx, trxName)
				.addEqualsFilter(I_C_OLCand.COLUMNNAME_AD_Table_ID, tableId)

				.addEqualsFilter(I_C_OLCand.COLUMNNAME_Record_ID, recordId)

				.addOnlyActiveRecordsFilter()
				.addOnlyContextClient()
				.orderBy(I_C_OLCand.COLUMNNAME_C_OLCand_ID)
				.create()
				.list(I_C_OLCand.class);
	}

	@Override
	public <T extends I_C_OLCand> List<T> retrieveOLCands(final I_C_OrderLine ol, final Class<T> clazz)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(ol);
		final String trxName = InterfaceWrapperHelper.getTrxName(ol);
		final int orderLineId = ol.getC_OrderLine_ID();

		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_Order_Line_Alloc.class, ctx, trxName)
				.addEqualsFilter(I_C_Order_Line_Alloc.COLUMN_C_OrderLine_ID, orderLineId)
				.addOnlyActiveRecordsFilter()
				.andCollect(I_C_Order_Line_Alloc.COLUMN_C_OLCand_ID)
				.addOnlyActiveRecordsFilter()
				.addOnlyContextClient()
				.orderBy(I_C_OLCand.COLUMN_C_OLCand_ID)
				.create()
				.list(clazz);
	}

	@Override
	public List<I_C_Order_Line_Alloc> retrieveAllOlas(final I_C_OrderLine ol)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(ol);
		final String trxName = InterfaceWrapperHelper.getTrxName(ol);
		final int orderLineId = ol.getC_OrderLine_ID();

		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_Order_Line_Alloc.class, ctx, trxName)
				.addEqualsFilter(I_C_Order_Line_Alloc.COLUMN_C_OrderLine_ID, orderLineId)
				// .addOnlyActiveRecordsFilter() // note that we also load records that are inactive or belong to different AD_Clients
				.orderBy(I_C_Order_Line_Alloc.COLUMN_C_Order_Line_Alloc_ID)
				.create()
				.list(I_C_Order_Line_Alloc.class);
	}

	@Override
	public List<I_C_Order_Line_Alloc> retrieveAllOlas(final I_C_OLCand olCand)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(olCand);
		final String trxName = InterfaceWrapperHelper.getTrxName(olCand);
		final int olCandId = olCand.getC_OLCand_ID();

		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_Order_Line_Alloc.class, ctx, trxName)
				.addEqualsFilter(I_C_Order_Line_Alloc.COLUMN_C_OLCand_ID, olCandId)
				// .addOnlyActiveRecordsFilter() // note that we also load records that are inactive or belong to different AD_Clients
				.orderBy(I_C_Order_Line_Alloc.COLUMN_C_Order_Line_Alloc_ID)
				.create()
				.list(I_C_Order_Line_Alloc.class);
	}

	public ImmutableMap<PoReferenceLookupKey, Integer> getNumberOfRecordsWithTheSamePOReference(
			@NonNull final Set<PoReferenceLookupKey> targetKeySet,
			@Nullable final LocalDateInterval searchingTimeWindow)
	{
		final Set<String> poReferences = targetKeySet.stream().map(PoReferenceLookupKey::getPoReference).collect(Collectors.toSet());
		final Set<Integer> orgIdSet = targetKeySet.stream().map(PoReferenceLookupKey::getOrgId).map(OrgId::getRepoId).collect(Collectors.toSet());

		final IQueryBuilder<I_C_OLCand> olCandsQBuilder = Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_OLCand.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_C_OLCand.COLUMNNAME_POReference, poReferences)
				.addInArrayFilter(I_C_OLCand.COLUMNNAME_AD_Org_ID, orgIdSet);

		if (searchingTimeWindow != null)
		{
			olCandsQBuilder.addBetweenFilter(I_C_OLCand.COLUMNNAME_Created, TimeUtil.asTimestamp(searchingTimeWindow.getStartDate()),
					TimeUtil.asTimestamp(searchingTimeWindow.getEndDate()), DateTruncQueryFilterModifier.DAY);
		}

		final List<I_C_OLCand> olCands = olCandsQBuilder.create().list();


		final Map<PoReferenceLookupKey, Integer> nrOfOLCandsByPoReferenceKey = new HashMap<>();

		//initialize map
		//we only care about those PoReferenceLookupKey that were requested
		targetKeySet.forEach(key -> nrOfOLCandsByPoReferenceKey.put(key, 0));

		olCands.forEach(olCand -> {
			final PoReferenceLookupKey poReferenceLookupKey = PoReferenceLookupKey.builder()
					.poReference(olCand.getPOReference())
					.orgId(OrgId.ofRepoId(olCand.getAD_Org_ID()))
					.build();

			final Integer currentCounting = nrOfOLCandsByPoReferenceKey.get(poReferenceLookupKey);

			if (currentCounting != null)
			{
				nrOfOLCandsByPoReferenceKey.put(poReferenceLookupKey, currentCounting + 1);
			}
		});

		return ImmutableMap.copyOf(nrOfOLCandsByPoReferenceKey);
	}
}
