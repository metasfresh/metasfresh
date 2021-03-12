/**
 *
 */
package de.metas.purchasecandidate.interceptor;

import java.time.DayOfWeek;
import java.time.Duration;
import java.util.Optional;

import de.metas.common.util.time.SystemTime;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.Adempiere;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_I_BPartner;

import com.google.common.collect.ImmutableSet;

import de.metas.bpartner.BPartnerId;
import de.metas.impexp.processing.IImportInterceptor;
import de.metas.impexp.processing.IImportProcess;
import de.metas.purchasecandidate.BPPurchaseSchedule;
import de.metas.purchasecandidate.BPPurchaseScheduleRepository;
import de.metas.util.time.generator.Frequency;
import de.metas.util.time.generator.FrequencyType;
import lombok.NonNull;

/*
 * #%L
 * de.metas.purchasecandidate.base
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

/**
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class BPPurchaseScheduleImportPartnerInterceptor implements IImportInterceptor
{
	public static final BPPurchaseScheduleImportPartnerInterceptor instance = new BPPurchaseScheduleImportPartnerInterceptor();

	private BPPurchaseScheduleImportPartnerInterceptor()
	{

	}

	@Override
	public void onImport(IImportProcess<?> process, Object importModel, Object targetModel, int timing)
	{
		if (timing != IImportInterceptor.TIMING_AFTER_IMPORT)
		{
			return;
		}

		final I_I_BPartner ibpartner = InterfaceWrapperHelper.create(importModel, I_I_BPartner.class);

		if (targetModel instanceof I_C_BPartner)
		{
			final I_C_BPartner bpartner = InterfaceWrapperHelper.create(targetModel, I_C_BPartner.class);
			createBPPurchaseScheduleIfNeeded(ibpartner, bpartner);
		}

	}

	private final void createBPPurchaseScheduleIfNeeded(@NonNull final I_I_BPartner importRecord, @NonNull final I_C_BPartner bpartner)
	{
		if (importRecord.getLeadTimeOffset() > 0)
		{
			final Frequency frequency = Frequency.builder()
					.type(FrequencyType.Monthly)
					.everyNthMonth(1)
					.onlyDayOfMonth(1)
					.onlyDaysOfWeek(ImmutableSet.of(DayOfWeek.MONDAY))
					.build();

			final BPPurchaseScheduleRepository bpPurchaseScheduleRepo = Adempiere.getBean(BPPurchaseScheduleRepository.class);
			final Optional<BPPurchaseSchedule> bpPurchaseSchedule = bpPurchaseScheduleRepo.getByBPartnerIdAndValidFrom(BPartnerId.ofRepoId(bpartner.getC_BPartner_ID()), SystemTime.asLocalDate());

			if (bpPurchaseSchedule.isPresent())
			{
				bpPurchaseScheduleRepo.changeLeadTimeOffset(bpPurchaseSchedule.get(), Duration.ofDays(importRecord.getLeadTimeOffset()));
			}
			else
			{
				final BPPurchaseSchedule schedule = BPPurchaseSchedule.builder()
						.bpartnerId(BPartnerId.ofRepoId(bpartner.getC_BPartner_ID()))
						.frequency(frequency)
						.leadTimeOffset(Duration.ofDays(importRecord.getLeadTimeOffset()))
						.reminderTime(Duration.ofHours(1))
						.validFrom(de.metas.common.util.time.SystemTime.asLocalDate())
						.build();
				bpPurchaseScheduleRepo.save(schedule);
			}
		}
	}
}
