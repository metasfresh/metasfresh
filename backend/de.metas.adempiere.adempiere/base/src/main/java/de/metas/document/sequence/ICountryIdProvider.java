/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.document.sequence;

import de.metas.document.DocBaseAndSubType;
import de.metas.location.CountryId;
import lombok.NonNull;
import lombok.Value;
import org.compiere.util.Evaluatee;

import javax.annotation.Nullable;
import java.util.Optional;

public interface ICountryIdProvider
{
	@Value
	class ProviderResult
	{
		public static final ICountryIdProvider.ProviderResult EMPTY = new ICountryIdProvider.ProviderResult(null);

		public static ICountryIdProvider.ProviderResult of(@NonNull final CountryId countryId)
		{
			return new ICountryIdProvider.ProviderResult(countryId);
		}

		Optional<CountryId> countryId;

		public boolean hasCountryId()
		{
			return countryId.isPresent();
		}

		public CountryId getCountryIdOrNull()
		{
			return countryId.orElse(null);
		}

		private ProviderResult(@Nullable final CountryId countryId)
		{
			this.countryId = Optional.ofNullable(countryId);
		}
	}

	ICountryIdProvider.ProviderResult computeValueInfo(Evaluatee eval);
	ICountryIdProvider.ProviderResult computeValueInfo(Object documentModel);

	boolean isHandled(DocBaseAndSubType docBaseAndSubType);
}
