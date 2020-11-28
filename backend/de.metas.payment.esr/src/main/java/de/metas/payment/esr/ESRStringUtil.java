package de.metas.payment.esr;

import lombok.NonNull;
import lombok.experimental.UtilityClass;

/*
 * #%L
 * de.metas.payment.esr
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

@UtilityClass
public class ESRStringUtil
{
	/**
	 * @param referenceNo a correct ESR reference number that merely doesn't have spaces at the right places.
	 */
	public String formatReferenceNumber(@NonNull final String referenceNo)
	{
		final String referenceNoWithoutSpaces = referenceNo.replaceAll(" ", "");
		return new StringBuilder()
				.append(referenceNoWithoutSpaces.substring(0, 2)).append(" ")
				.append(referenceNoWithoutSpaces.substring(2, 7)).append(" ")
				.append(referenceNoWithoutSpaces.substring(7, 12)).append(" ")
				.append(referenceNoWithoutSpaces.substring(12, 17)).append(" ")
				.append(referenceNoWithoutSpaces.substring(17, 22)).append(" ")
				.append(referenceNoWithoutSpaces.substring(22))
				.toString();

	}
}
