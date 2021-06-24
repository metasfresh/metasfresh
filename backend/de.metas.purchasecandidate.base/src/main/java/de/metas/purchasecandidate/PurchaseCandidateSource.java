/*
 * #%L
 * de.metas.purchasecandidate.base
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

package de.metas.purchasecandidate;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.purchasecandidate.model.X_C_PurchaseCandidate;
import de.metas.util.Check;
import de.metas.util.lang.ReferenceListAwareEnum;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.Arrays;

public enum PurchaseCandidateSource implements ReferenceListAwareEnum
{
	MaterialDisposition(X_C_PurchaseCandidate.SOURCE_MaterialDisposition),
	SalesOrder(X_C_PurchaseCandidate.SOURCE_SalesOrder),
	Api(X_C_PurchaseCandidate.SOURCE_API);

	String code;
	private static final ImmutableMap<String, PurchaseCandidateSource> typesByCode = Maps.uniqueIndex(Arrays.asList(values()), PurchaseCandidateSource::getCode);

	PurchaseCandidateSource(final String code)
	{
		this.code = code;
	}

	public String getCode()
	{
		return code;
	}

	public static PurchaseCandidateSource ofCode(@NonNull final String code)
	{
		final PurchaseCandidateSource type = typesByCode.get(code);
		if (type == null)
		{
			throw new AdempiereException("No " + PurchaseCandidateSource.class + " found for code: " + code);
		}
		return type;
	}

	@Nullable
	public static PurchaseCandidateSource ofCodeOrNull(@Nullable final String code)
	{
		if (Check.isBlank(code))
		{
			return null;
		}
		return ofCode(code);
	}

}
