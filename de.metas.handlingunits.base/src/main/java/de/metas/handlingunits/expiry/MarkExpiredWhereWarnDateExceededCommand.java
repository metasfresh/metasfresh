package de.metas.handlingunits.expiry;

import java.time.LocalDate;
import java.util.function.Consumer;

import org.adempiere.exceptions.AdempiereException;
import org.slf4j.Logger;

import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.attribute.HUAttributeConstants;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.hutransaction.IHUTrxBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.logging.LogManager;
import de.metas.util.Loggables;
import lombok.Builder;
import lombok.NonNull;

/*
 * #%L
 * de.metas.handlingunits.base
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

final class MarkExpiredWhereWarnDateExceededCommand
{
	// services
	private static final Logger logger = LogManager.getLogger(MarkExpiredWhereWarnDateExceededCommand.class);
	private final HUWithExpiryDatesRepository huWithExpiryDatesRepository;
	private final IHandlingUnitsBL handlingUnitsBL;
	private final IHUTrxBL huTrxBL;

	// parameters
	private final LocalDate today;

	// status
	private int countChecked;
	private int countUpdated;

	@Builder
	private MarkExpiredWhereWarnDateExceededCommand(
			@NonNull final HUWithExpiryDatesRepository huWithExpiryDatesRepository,
			@NonNull final IHandlingUnitsBL handlingUnitsBL,
			@NonNull final IHUTrxBL huTrxBL,
			//
			@NonNull final LocalDate today)
	{
		this.huWithExpiryDatesRepository = huWithExpiryDatesRepository;
		this.handlingUnitsBL = handlingUnitsBL;
		this.huTrxBL = huTrxBL;

		this.today = today;
	}

	public static class MarkExpiredWhereWarnDateExceededCommandBuilder
	{
		public MarkExpiredWhereWarnDateExceededResult execute()
		{
			return build().execute();
		}
	}

	public MarkExpiredWhereWarnDateExceededResult execute()
	{
		huWithExpiryDatesRepository.streamByWarnDateExceeded(today)
				.map(HUWithExpiryDates::getHuId)
				.forEach(this::markExpiredInOwnTrx);

		return MarkExpiredWhereWarnDateExceededResult.builder()
				.countChecked(countChecked)
				.countUpdated(countUpdated)
				.build();
	}

	private void markExpiredInOwnTrx(@NonNull final HuId huId)
	{
		huTrxBL.process((Consumer<IHUContext>)huContext -> markExpiredUsingHUContext(huId, huContext));
	}

	private void markExpiredUsingHUContext(
			@NonNull final HuId huId,
			@NonNull final IHUContext huContext)
	{
		try
		{
			countChecked++;

			final IAttributeStorage huAttributes = getHUAttributes(huId, huContext);
			final String expiredOld = huAttributes.getValueAsString(HUAttributeConstants.ATTR_Expired);
			if (HUAttributeConstants.ATTR_Expired_Value_Expired.equals(expiredOld))
			{
				Loggables.addLog("Already marked as Expired: M_HU_ID={}", huId);
				return;
			}

			huAttributes.setSaveOnChange(true);
			huAttributes.setValue(HUAttributeConstants.ATTR_Expired, HUAttributeConstants.ATTR_Expired_Value_Expired);

			countUpdated++;
			Loggables.addLog("Successfully processed M_HU_ID={}", huId);
		}
		catch (final AdempiereException ex)
		{
			Loggables.addLog("!!! Failed processing M_HU_ID={}: {} !!!", huId, ex.getLocalizedMessage());
			logger.warn("Failed processing M_HU_ID={}. Skipped", huId, ex);
		}
	}

	private IAttributeStorage getHUAttributes(@NonNull final HuId huId, @NonNull final IHUContext huContext)
	{
		final I_M_HU hu = handlingUnitsBL.getById(huId);

		return huContext
				.getHUAttributeStorageFactory()
				.getAttributeStorage(hu);
	}

}
