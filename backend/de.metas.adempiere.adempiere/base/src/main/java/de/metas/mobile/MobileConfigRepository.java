/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2024 metas GmbH
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

package de.metas.mobile;

import de.metas.cache.CCache;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_MobileConfiguration;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class MobileConfigRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final CCache<Integer, Optional<MobileConfig>> cache = CCache.<Integer, Optional<MobileConfig>>builder()
			.tableName(I_MobileConfiguration.Table_Name)
			.initialCapacity(1)
			.build();

	@NonNull
	public Optional<MobileConfig> getConfig()
	{
		return cache.getOrLoad(0, this::retrieveConfig);
	}

	@NonNull
	private Optional<MobileConfig> retrieveConfig()
	{
		return retrieveRecord().map(MobileConfigRepository::ofRecord);
	}

	@NonNull
	private Optional<I_MobileConfiguration> retrieveRecord()
	{
		return queryBL.createQueryBuilder(I_MobileConfiguration.class)
				.addOnlyActiveRecordsFilter()
				.create()
				.firstOnlyOptional(I_MobileConfiguration.class);
	}

	public void save(@NonNull final MobileConfig mobileConfig)
	{
		final I_MobileConfiguration record = retrieveRecord().orElseGet(() -> InterfaceWrapperHelper.newInstance(I_MobileConfiguration.class));
		updateRecord(record, mobileConfig);
		InterfaceWrapperHelper.saveRecord(record);
	}

	@NonNull
	private static MobileConfig ofRecord(@NonNull final I_MobileConfiguration mobileConfiguration)
	{
		return MobileConfig.builder()
				.defaultAuthMethod(MobileAuthMethod.ofCode(mobileConfiguration.getDefaultAuthenticationMethod()))
				.build();
	}

	private static void updateRecord(@NonNull final I_MobileConfiguration record, @NonNull final MobileConfig from)
	{
		record.setDefaultAuthenticationMethod(from.getDefaultAuthMethod().getCode());
	}
}
