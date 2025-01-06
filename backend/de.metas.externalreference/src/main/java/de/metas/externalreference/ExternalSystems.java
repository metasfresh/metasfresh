/*
 * #%L
 * de.metas.externalreference
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.externalreference;

import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class ExternalSystems
{
	public ExternalSystems()
	{
		registerExternalSystem(NullExternalSystem.NULL);
		registerExternalSystem(AlbertaExternalSystem.ALBERTA);
		registerExternalSystem(Shopware6ExternalSystem.SHOPWARE6);
		registerExternalSystem(OtherExternalSystem.OTHER);
		registerExternalSystem(EbayExternalSystem.EBAY);
		registerExternalSystem(WoocommerceExternalSystem.WOOCOMMERCE);
		registerExternalSystem(GRSSignumExternalSystem.GRSSIGNUM);
		registerExternalSystem(LeichUndMehlExternalSystem.LEICHUNDMEHL);
		registerExternalSystem(SAPExternalSystem.SAP);
		registerExternalSystem(PCMExternalSystem.PROCAREMANAGEMENT);
	}

	private final Map<String, IExternalSystem> systemsByCode = new HashMap<>();

	public void registerExternalSystem(@NonNull final IExternalSystem system)
	{
		systemsByCode.put(system.getCode(), system);
	}

	public Optional<IExternalSystem> ofCode(final String code)
	{
		return Optional.ofNullable(systemsByCode.get(code));
	}
}
