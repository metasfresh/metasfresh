/*
 * #%L
 * de-metas-camel-externalsystems-earl
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

package de.metas.camel.externalsystems.pcm.purchaseorder;

import de.metas.common.rest_api.common.JsonExternalId;
import lombok.Data;
import lombok.NonNull;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

@Data
public class ImportOrdersRouteContext
{
	boolean errorInUnknownRow = false;

	/**
	 * Using Linked hash set to preserve order
	 */
	final LinkedHashSet<JsonExternalId> purchaseCandidatesToProcess = new LinkedHashSet<>();

	final Set<JsonExternalId> purchaseCandidatesWithError = new HashSet<>();

	void errorInUnknownRow()
	{
		errorInUnknownRow = true;
	}

	public void addAll(@NonNull final ImportOrdersRouteContext other)
	{
		purchaseCandidatesToProcess.addAll(other.getPurchaseCandidatesToProcess());
		purchaseCandidatesWithError.addAll(other.getPurchaseCandidatesWithError());

		if (other.errorInUnknownRow)
		{
			errorInUnknownRow = true;
		}
	}

	public boolean isDoNotProcessAtAll()
	{
		return errorInUnknownRow || purchaseCandidatesToProcess.isEmpty();
	}
}
