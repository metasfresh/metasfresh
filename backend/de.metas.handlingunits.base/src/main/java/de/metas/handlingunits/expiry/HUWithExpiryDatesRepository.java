package de.metas.handlingunits.expiry;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Iterator;
import java.util.stream.Stream;

import javax.annotation.Nullable;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.impl.CompareQueryFilter.Operator;
import org.adempiere.ad.dao.impl.DateTruncQueryFilterModifier;
import org.adempiere.mm.attributes.api.AttributeConstants;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Repository;

import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHUStatusBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.attribute.HUAttributeConstants;
import de.metas.handlingunits.model.I_M_HU_BestBefore_V;
import de.metas.util.Services;
import lombok.NonNull;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2018 metas GmbH
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

@Repository
public class HUWithExpiryDatesRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
	private final IHUStatusBL huStatusBL = Services.get(IHUStatusBL.class);

	public Stream<HUWithExpiryDates> streamByWarnDateExceeded(@NonNull final LocalDate today)
	{
		final Timestamp todayTS = TimeUtil.asTimestamp(today);

		return queryBL.createQueryBuilder(I_M_HU_BestBefore_V.class)
				.addCompareFilter(I_M_HU_BestBefore_V.COLUMN_HU_ExpiredWarnDate, Operator.LESS_OR_EQUAL, todayTS, DateTruncQueryFilterModifier.DAY)
				.addNotEqualsFilter(I_M_HU_BestBefore_V.COLUMN_HU_Expired, HUAttributeConstants.ATTR_Expired_Value_Expired)
				.orderBy(I_M_HU_BestBefore_V.COLUMN_M_HU_ID)
				.create()
				.iterateAndStream()
				.map(record -> ofRecordOrNull(record));
	}

	private static HUWithExpiryDates ofRecordOrNull(@Nullable final I_M_HU_BestBefore_V record)
	{
		if (record == null)
		{
			return null;
		}

		return HUWithExpiryDates.builder()
				.huId(HuId.ofRepoId(record.getM_HU_ID()))
				.bestBeforeDate(TimeUtil.asLocalDate(record.getHU_BestBeforeDate()))
				.expiryWarnDate(TimeUtil.asLocalDate(record.getHU_ExpiredWarnDate()))
				.build();
	}

	public HUWithExpiryDates getByIdIfWarnDateExceededOrNull(
			@NonNull final HuId huId,
			@Nullable final LocalDate expiryWarnDate)
	{
		final Timestamp timestamp = TimeUtil.asTimestamp(expiryWarnDate);

		final I_M_HU_BestBefore_V recordOrdNull = queryBL.createQueryBuilder(I_M_HU_BestBefore_V.class)
				.addCompareFilter(I_M_HU_BestBefore_V.COLUMN_HU_ExpiredWarnDate, Operator.LESS_OR_EQUAL, timestamp, DateTruncQueryFilterModifier.DAY)
				.addNotEqualsFilter(I_M_HU_BestBefore_V.COLUMN_HU_Expired, HUAttributeConstants.ATTR_Expired_Value_Expired)
				.addEqualsFilter(I_M_HU_BestBefore_V.COLUMN_M_HU_ID, huId)
				.create()
				.firstOnly(I_M_HU_BestBefore_V.class);

		return ofRecordOrNull(recordOrdNull);
	}

	public HUWithExpiryDates getById(@NonNull final HuId huId)
	{
		final I_M_HU_BestBefore_V recordOrdNull = queryBL.createQueryBuilder(I_M_HU_BestBefore_V.class)
				.addEqualsFilter(I_M_HU_BestBefore_V.COLUMN_M_HU_ID, huId)
				.create()
				.firstOnly(I_M_HU_BestBefore_V.class);

		return ofRecordOrNull(recordOrdNull);
	}

	public Iterator<HuId> getAllWithBestBeforeDate()
	{
		return handlingUnitsDAO.createHUQueryBuilder()
				.addOnlyWithAttributeNotNull(AttributeConstants.ATTR_BestBeforeDate)
				.addHUStatusesToInclude(huStatusBL.getQtyOnHandStatuses())
				.createQuery()
				.iterateIds(HuId::ofRepoId);
	}

	public Iterator<HuId> getAllWithEndStorageDate()
	{
		return handlingUnitsDAO.createHUQueryBuilder()
				.addOnlyWithAttributeNotNull(AttributeConstants.ATTR_endStorageDate)
				.addHUStatusesToInclude(huStatusBL.getQtyOnHandStatuses())
				.createQuery()
				.iterateIds(HuId::ofRepoId);
	}
}
