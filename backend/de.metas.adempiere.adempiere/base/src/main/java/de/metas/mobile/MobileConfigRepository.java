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

import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.compiere.model.I_MobileConfiguration;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class MobileConfigRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@NonNull
	public Optional<MobileConfig> getConfig()
	{
		return queryBL.createQueryBuilder(I_MobileConfiguration.class)
				.addOnlyActiveRecordsFilter()
				.create()
				.firstOnlyOptional(I_MobileConfiguration.class)
				.map(MobileConfigRepository::ofRecord);
	}

	@NonNull
	private static MobileConfig ofRecord(@NonNull final I_MobileConfiguration mobileConfiguration)
	{
		return MobileConfig.builder()
				.defaultAuthMethod(MobileAuthMethod.ofCode(mobileConfiguration.getDefaultAuthenticationMethod()))
				.build();
	}
}
