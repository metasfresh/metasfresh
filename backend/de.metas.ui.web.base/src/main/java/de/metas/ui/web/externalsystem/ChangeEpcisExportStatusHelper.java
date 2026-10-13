/*
 * #%L
 * de.metas.ui.web.base
 * %%
 * Copyright (C) 2026 metas GmbH
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

package de.metas.ui.web.externalsystem;

import com.google.common.collect.ImmutableList;
import de.metas.ad_reference.ADReferenceService;
import de.metas.externalsystem.ExternalSystemExportStatus;
import de.metas.process.IProcessDefaultParametersProvider;
import de.metas.ui.web.window.datatypes.LookupValue;
import de.metas.ui.web.window.datatypes.LookupValuesList;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Shared logic for the "Change EPCIS Export Status" WebUI process (SingleView + GridView).
 * Mirrors {@code de.metas.ui.web.edi_desadv.ChangeEDI_ExportStatusHelper}, but for the EPCIS
 * scripted-export status ref-list.
 */
@UtilityClass
public class ChangeEpcisExportStatusHelper
{
	@NonNull private final ADReferenceService adReferenceService = ADReferenceService.get();

	private final int AD_Reference_ID = ExternalSystemExportStatus.AD_Reference_ID;

	/**
	 * Allowed manual target statuses per current status:
	 * <ul>
	 *   <li>{@code Pending} ↔ {@code DontSend}</li>
	 *   <li>{@code Error} / {@code Sent} / {@code Invalid} → both</li>
	 *   <li>in-flight ({@code Enqueued} / {@code SendingStarted}) → both</li>
	 * </ul>
	 */
	public List<ExternalSystemExportStatus> getAvailableTargetExportStatuses(@Nullable final ExternalSystemExportStatus fromStatus)
	{
		if (fromStatus == null)
		{
			return ImmutableList.of();
		}
		switch (fromStatus)
		{
			case Pending:
				return ImmutableList.of(ExternalSystemExportStatus.DontSend);
			case DontSend:
				return ImmutableList.of(ExternalSystemExportStatus.Pending);
			case Error:
			case Sent:
			case Invalid:
			case Enqueued:
			case SendingStarted:
				return ImmutableList.of(ExternalSystemExportStatus.Pending, ExternalSystemExportStatus.DontSend);
			default:
				return ImmutableList.of();
		}
	}

	public LookupValuesList computeTargetExportStatusLookupValues(@Nullable final ExternalSystemExportStatus fromStatus)
	{
		return getAvailableTargetExportStatuses(fromStatus).stream()
				.map(s -> LookupValue.StringLookupValue.of(s.getCode(), adReferenceService.retrieveListNameTranslatableString(AD_Reference_ID, s.getCode())))
				.collect(LookupValuesList.collect());
	}

	public Object computeParameterDefaultValue(@Nullable final ExternalSystemExportStatus fromStatus)
	{
		final List<ExternalSystemExportStatus> availableTargetStatuses = getAvailableTargetExportStatuses(fromStatus);
		if (!availableTargetStatuses.isEmpty())
		{
			final String code = availableTargetStatuses.get(0).getCode();
			return LookupValue.StringLookupValue.of(code, adReferenceService.retrieveListNameTranslatableString(AD_Reference_ID, code));
		}
		return IProcessDefaultParametersProvider.DEFAULT_VALUE_NOTAVAILABLE;
	}
}
