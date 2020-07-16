/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.inoutcandidate.exportaudit;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.inoutcandidate.model.X_M_ShipmentSchedule_ExportAudit_Line;
import de.metas.order.InvoiceRule;
import de.metas.util.lang.ReferenceListAwareEnum;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.Arrays;

public enum ShipmentScheduleExportStatus implements ReferenceListAwareEnum
{
	Pending(X_M_ShipmentSchedule_ExportAudit_Line.EXPORTSTATUS_PENDING),
	Exported(X_M_ShipmentSchedule_ExportAudit_Line.EXPORTSTATUS_EXPORTED),
	ExportError(X_M_ShipmentSchedule_ExportAudit_Line.EXPORTSTATUS_EXPORT_ERROR),
	ExportedAndForwarded(X_M_ShipmentSchedule_ExportAudit_Line.EXPORTSTATUS_EXPORTED_AND_FORWARDED),
	ExportedAndError(X_M_ShipmentSchedule_ExportAudit_Line.EXPORTSTATUS_EXPORTED_FORWARD_ERROR);

	@Getter
	private final String code;

	ShipmentScheduleExportStatus(@NonNull final String code)
	{
		this.code = code;
	}

	@Nullable
	public static ShipmentScheduleExportStatus ofNullableCode(final String code)
	{
		return code != null ? ofCode(code) : null;
	}

	public static ShipmentScheduleExportStatus ofCode(@NonNull final String code)
	{
		final ShipmentScheduleExportStatus type = typesByCode.get(code);
		if (type == null)
		{
			throw new AdempiereException("No " + InvoiceRule.class + " found for code: " + code);
		}
		return type;
	}

	@Nullable
	public static String toCodeOrNull(final ShipmentScheduleExportStatus type)
	{
		return type != null ? type.getCode() : null;
	}

	private static final ImmutableMap<String, ShipmentScheduleExportStatus> typesByCode = Maps.uniqueIndex(Arrays.asList(values()), ShipmentScheduleExportStatus::getCode);
}
