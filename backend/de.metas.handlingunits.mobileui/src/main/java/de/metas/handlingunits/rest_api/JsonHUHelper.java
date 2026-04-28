/*
 * #%L
 * de.metas.manufacturing.rest-api
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.handlingunits.rest_api;

import de.metas.common.handlingunits.JsonClearanceStatus;
import de.metas.handlingunits.ClearanceStatus;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.adempiere.exceptions.AdempiereException;

@UtilityClass
public class JsonHUHelper
{
	@NonNull
	public static JsonClearanceStatus toJsonClearanceStatus(@NonNull final ClearanceStatus clearanceStatus)
	{
		switch (clearanceStatus)
		{
			case Cleared:
				return JsonClearanceStatus.Cleared;
			case Locked:
				return JsonClearanceStatus.Locked;
			case Quarantined:
				return JsonClearanceStatus.Quarantined;
			case TestPending:
				return JsonClearanceStatus.TestPending;
			default:
				throw new AdempiereException("Unknown JsonClearanceStatus:")
						.appendParametersToMessage()
						.setParameter("clearanceStatus", clearanceStatus);
		}
	}

	@NonNull
	public static ClearanceStatus fromJsonClearanceStatus(@NonNull final JsonClearanceStatus jsonClearanceStatus)
	{
		switch (jsonClearanceStatus)
		{
			case Cleared:
				return ClearanceStatus.Cleared;
			case Locked:
				return ClearanceStatus.Locked;
			case Quarantined:
				return ClearanceStatus.Quarantined;
			case TestPending:
				return ClearanceStatus.TestPending;
			default:
				throw new AdempiereException("Unknown ClearanceStatus:")
						.appendParametersToMessage()
						.setParameter("jsonClearanceStatus", jsonClearanceStatus);
		}
	}
}
