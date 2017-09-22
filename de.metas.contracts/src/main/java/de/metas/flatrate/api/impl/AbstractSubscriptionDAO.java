package de.metas.flatrate.api.impl;

import static de.metas.flatrate.model.X_C_SubscriptionProgress.STATUS_Ausgefuehrt;
import static de.metas.flatrate.model.X_C_SubscriptionProgress.STATUS_Ausgeliefert;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import org.adempiere.ad.dao.ICompositeQueryUpdater;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.impl.CompareQueryFilter.Operator;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.slf4j.Logger;

import com.google.common.collect.ImmutableList;

import de.metas.contracts.subscription.ISubscriptionDAO;
import de.metas.contracts.subscription.model.I_C_OrderLine;
import de.metas.flatrate.model.I_C_Flatrate_Term;
import de.metas.flatrate.model.I_C_SubscriptionProgress;
import de.metas.logging.LogManager;
import lombok.NonNull;

/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2017 metas GmbH
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

public abstract class AbstractSubscriptionDAO implements ISubscriptionDAO
{
	private static final Logger logger = LogManager.getLogger(AbstractSubscriptionDAO.class);

	@Override
	public final boolean existsTermForOl(final I_C_OrderLine ol)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_Flatrate_Term.class, ol)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Flatrate_Term.COLUMN_C_OrderLine_Term_ID, ol.getC_OrderLine_ID())
				.create()
				.match();
	}

	@Override
	public final List<I_C_SubscriptionProgress> retrieveSubscriptionProgress(final I_C_Flatrate_Term term)
	{
		Check.assumeNotNull(term, "Param 'term' not null");

		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_SubscriptionProgress.class, term)
				.addOnlyActiveRecordsFilter()
				.addOnlyContextClient()
				.addEqualsFilter(I_C_SubscriptionProgress.COLUMNNAME_C_Flatrate_Term_ID, term.getC_Flatrate_Term_ID())
				.orderBy().addColumn(I_C_SubscriptionProgress.COLUMNNAME_SeqNo).endOrderBy()
				.create()
				.list(I_C_SubscriptionProgress.class);
	}

	@Override
	public final I_C_SubscriptionProgress insertNewDelivery(@NonNull final I_C_SubscriptionProgress predecessor)
	{
		increaseSeqNosOfSuccessors(predecessor);

		final I_C_SubscriptionProgress sdNew = insertNewRecordAfter(predecessor);
		return sdNew;
	}

	private void increaseSeqNosOfSuccessors(final I_C_SubscriptionProgress predecessor)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);
		final ICompositeQueryUpdater<I_C_SubscriptionProgress> updater = queryBL.createCompositeQueryUpdater(I_C_SubscriptionProgress.class)
				.addAddValueToColumn(I_C_SubscriptionProgress.COLUMNNAME_SeqNo, BigDecimal.ONE);

		final int renumberedRecords = queryBL.createQueryBuilder(I_C_SubscriptionProgress.class)
				.addEqualsFilter(I_C_SubscriptionProgress.COLUMN_C_Flatrate_Term_ID, predecessor.getC_Flatrate_Term_ID())
				.addCompareFilter(I_C_SubscriptionProgress.COLUMN_SeqNo, Operator.GREATER, predecessor.getSeqNo())
				.create()
				.updateDirectly(updater);

		logger.info("Renumbered {} existing I_C_SubscriptionProgress entries with C_Flatrate_Term_ID={} and SeqNo>{}", renumberedRecords, predecessor.getC_Flatrate_Term_ID(), predecessor.getSeqNo());
	}

	private I_C_SubscriptionProgress insertNewRecordAfter(final I_C_SubscriptionProgress predecessor)
	{
		final I_C_SubscriptionProgress sdNew = newInstance(I_C_SubscriptionProgress.class);

		sdNew.setC_Flatrate_Term_ID(predecessor.getC_Flatrate_Term_ID());

		sdNew.setQty(predecessor.getQty());
		sdNew.setStatus(predecessor.getStatus());
		sdNew.setM_ShipmentSchedule_ID(predecessor.getM_ShipmentSchedule_ID());
		sdNew.setEventDate(predecessor.getEventDate());
		sdNew.setSeqNo(predecessor.getSeqNo() + 1);

		sdNew.setDropShip_BPartner_ID(predecessor.getDropShip_BPartner_ID());
		sdNew.setDropShip_Location_ID(predecessor.getDropShip_Location_ID());
		sdNew.setDropShip_User_ID(predecessor.getDropShip_User_ID());

		save(sdNew);
		logger.info("Created new C_SubscriptionProgress={}", sdNew);
		return sdNew;
	}

	@Override
	public final I_C_SubscriptionProgress retrieveNextSP(final I_C_Flatrate_Term control, final Timestamp date, final int seqNo)
	{
		logger.debug("Parameters: date=" + date + ", seqNo=" + seqNo + ", control=" + control);

		final I_C_SubscriptionProgress result = Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_SubscriptionProgress.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_SubscriptionProgress.COLUMN_C_Flatrate_Term_ID, control.getC_Flatrate_Term_ID())
				.addCompareFilter(I_C_SubscriptionProgress.COLUMN_EventDate, Operator.GREATER_OR_EQUAL, date)
				.addCompareFilter(I_C_SubscriptionProgress.COLUMN_SeqNo, Operator.GREATER_OR_EQUAL, seqNo)
				.addNotInArrayFilter(I_C_SubscriptionProgress.COLUMN_Status, ImmutableList.of(STATUS_Ausgefuehrt, STATUS_Ausgeliefert))
				.orderBy().addColumn(I_C_SubscriptionProgress.COLUMN_SeqNo).endOrderBy()
				.create()
				.first();

		if (result != null)
		{
			return result;
		}

		return retrieveLastSP(control.getC_Flatrate_Term_ID(), seqNo);
	}

	public final I_C_SubscriptionProgress retrieveLastSP(
			final int termId,
			final int seqNo)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_SubscriptionProgress.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_SubscriptionProgress.COLUMN_C_Flatrate_Term_ID, termId)
				.addCompareFilter(I_C_SubscriptionProgress.COLUMN_SeqNo, Operator.GREATER_OR_EQUAL, seqNo)
				.orderBy().addColumn(I_C_SubscriptionProgress.COLUMNNAME_SeqNo, false).endOrderBy()
				.create()
				.first();
	}
}
