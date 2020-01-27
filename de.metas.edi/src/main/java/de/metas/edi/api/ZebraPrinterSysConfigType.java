/*
 * #%L
 * de.metas.edi
 * %%
 * Copyright (C) 2019 metas GmbH
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

package de.metas.edi.api;

import com.google.common.collect.ImmutableList;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
@Getter
public enum ZebraPrinterSysConfigType
{
	SQL_SELECT("de.metas.handlingunit.sscc18Label.zebra.sql-select", Boolean.TRUE),
	HEADER_LINE_1("de.metas.handlingunit.sscc18Label.zebra.header.line-1", Boolean.TRUE),
	HEADER_LINE_2("de.metas.handlingunit.sscc18Label.zebra.header.line-2", Boolean.TRUE),
	FILE_ENCODING("de.metas.handlingunit.sscc18Label.zebra.encoding", Boolean.TRUE);

	private final String sysConfigName;
	private final boolean mandatory;

	public static List<ZebraPrinterSysConfigType> listMandatoryConfigs()
	{
		return Arrays.stream(ZebraPrinterSysConfigType.values())
				.filter(ZebraPrinterSysConfigType::isMandatory)
				.collect(ImmutableList.toImmutableList());
	}
}
