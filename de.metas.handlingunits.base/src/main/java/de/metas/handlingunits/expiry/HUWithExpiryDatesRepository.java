package de.metas.handlingunits.expiry;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.stream.Stream;

import javax.annotation.Nullable;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.impl.CompareQueryFilter.Operator;
import org.adempiere.ad.dao.impl.DateTruncQueryFilterModifier;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.IContextAware;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Repository;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.attribute.HUAttributeConstants;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.attribute.storage.IAttributeStorageFactory;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_BestBefore_V;
import de.metas.handlingunits.model.X_M_HU;
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
	public Stream<HUWithExpiryDates> getByWarnDateExceeded(@NonNull final LocalDateTime expiredWarnDate)
	{
		final Timestamp timestamp = TimeUtil.asTimestamp(expiredWarnDate);

		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_HU_BestBefore_V.class)
				.addCompareFilter(I_M_HU_BestBefore_V.COLUMN_HU_ExpiredWarnDate, Operator.LESS_OR_EQUAL, timestamp, DateTruncQueryFilterModifier.DAY)
				.addNotEqualsFilter(I_M_HU_BestBefore_V.COLUMN_HU_Expired, HUAttributeConstants.ATTR_Expired_Value_Expired)
				.orderBy(I_M_HU_BestBefore_V.COLUMN_M_HU_ID)
				.create()
				.iterateAndStream()
				.map(this::ofRecordOrNull);
	}

	private HUWithExpiryDates ofRecordOrNull(@Nullable final I_M_HU_BestBefore_V record)
	{
		if (record == null)
		{
			return null;
		}

		return HUWithExpiryDates
				.builder()
				.huId(HuId.ofRepoId(record.getM_HU_ID()))
				.bestBeforeDate(TimeUtil.asLocalDateTime(record.getHU_BestBeforeDate()))
				.expiryWarnDate(TimeUtil.asLocalDateTime(record.getHU_ExpiredWarnDate()))
				.build();
	}

	public HUWithExpiryDates getByIdIfWarnDateExceededOrNull(
			@Nullable final HuId huId,
			@Nullable final LocalDateTime expiryWarnDate)
	{
		final Timestamp timestamp = TimeUtil.asTimestamp(expiryWarnDate);

		I_M_HU_BestBefore_V recordOrdNull = Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_HU_BestBefore_V.class)
				.addCompareFilter(I_M_HU_BestBefore_V.COLUMN_HU_ExpiredWarnDate, Operator.LESS_OR_EQUAL, timestamp, DateTruncQueryFilterModifier.DAY)
				.addNotEqualsFilter(I_M_HU_BestBefore_V.COLUMN_HU_Expired, HUAttributeConstants.ATTR_Expired_Value_Expired)
				.addEqualsFilter(I_M_HU_BestBefore_V.COLUMN_M_HU_ID, huId)
				.create()
				.firstOnly(I_M_HU_BestBefore_V.class);

		return ofRecordOrNull(recordOrdNull);
	}

	public ImmutableSet<HuId> getAllWithBestBeforeDate()
	{
		final ImmutableList<String> validHuStatuses = ImmutableList.<String> builder()
				.add(X_M_HU.HUSTATUS_Active)
				.build();

		return Services.get(IHandlingUnitsDAO.class)
				.createHUQueryBuilder()
				.addOnlyWithAttributeNotNull(HUAttributeConstants.ATTR_BestBeforeDate)
				.addHUStatusesToInclude(validHuStatuses)
				.createQueryBuilder()
				.create()
				.listIds(HuId::ofRepoId);
	}

	public LocalDate getBestBeforeDate(final HuId huId)
	{
		IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
		final I_M_HU hu = handlingUnitsDAO.getById(huId);
		final IContextAware ctxAware = InterfaceWrapperHelper.getContextAware(hu);

		final IHUContext huContext = Services.get(IHandlingUnitsBL.class).createMutableHUContext(ctxAware);

		final IAttributeStorage attributeStorage = getAttributeStorage(huContext, hu);

		final LocalDate bestBeforeDate = attributeStorage.getValueAsLocalDate(HUAttributeConstants.ATTR_BestBeforeDate);

		return bestBeforeDate;

	}

	private final IAttributeStorage getAttributeStorage(final IHUContext huContext, final I_M_HU hu)
	{
		final IAttributeStorageFactory attributeStorageFactory = huContext.getHUAttributeStorageFactory();
		final IAttributeStorage attributeStorage = attributeStorageFactory.getAttributeStorage(hu);
		return attributeStorage;
	}
}
