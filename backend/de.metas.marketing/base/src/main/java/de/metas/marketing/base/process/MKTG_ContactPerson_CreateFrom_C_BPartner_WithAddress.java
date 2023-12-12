package de.metas.marketing.base.process;

import de.metas.marketing.base.bpartner.DefaultAddressType;
import de.metas.process.Param;
import lombok.NonNull;

/*
 * #%L
 * marketing-base
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

public class MKTG_ContactPerson_CreateFrom_C_BPartner_WithAddress extends MKTG_ContactPerson_CreateFrom_C_BPartner
{
	@Param(mandatory = true, parameterName = "DefaultAddressType")
	private String defaultAddressType;

	@NonNull
	@Override
	protected DefaultAddressType getAddressType()
	{
		return DefaultAddressType.forCode(defaultAddressType);
	}
}
