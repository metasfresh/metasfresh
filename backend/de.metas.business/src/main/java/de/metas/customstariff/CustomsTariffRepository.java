package de.metas.customstariff;

import com.google.common.annotations.VisibleForTesting;
import de.metas.common.util.Check;
import lombok.NonNull;
import org.compiere.Adempiere;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_M_CustomsTariff;
import org.springframework.stereotype.Repository;

import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;

/*
 * #%L
 * de.metas.business
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
public class CustomsTariffRepository
{

	@VisibleForTesting
	public static CustomsTariffRepository newInstanceForUnitTesting()
	{
		Adempiere.assertUnitTestMode();
		//noinspection DataFlowIssue
		return SpringContextHolder.getBeanOrSupply(CustomsTariffRepository.class, CustomsTariffRepository::new);
	}

	@NonNull
	public CustomsTariff getById(@NonNull final CustomsTariffId id)
	{
		final I_M_CustomsTariff record = loadOutOfTrx(id.getRepoId(), I_M_CustomsTariff.class);
		Check.assumeNotNull(record, "M_CustomsTariff record not found for id={}", id);
		return ofRecord(record);
	}

	@NonNull
	private CustomsTariff ofRecord(@NonNull final I_M_CustomsTariff record)
	{
		return CustomsTariff.builder()
				.id(CustomsTariffId.ofRepoId(record.getM_CustomsTariff_ID()))
				.value(record.getValue())
				.description(record.getDescription())
				.build();
	}
}
