package de.metas.impexp.excel;

import java.io.File;
import java.util.Set;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ISysConfigBL;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.io.Files;

import de.metas.util.Services;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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
public class ExcelFormats
{
	public static final ExcelFormat EXCEL_OPEN_XML = new ExcelOpenXMLFormat();
	public static final ExcelFormat EXCEL97 = new Excel97Format();
	private static final ImmutableList<ExcelFormat> ALL_FORMATS = ImmutableList.of(EXCEL_OPEN_XML, EXCEL97);

	private static final String SYSCONFIG_DefaultExcelFileExtension = "de.metas.excel.DefaultFileExtension";

	public static String getDefaultFileExtension()
	{
		final ISysConfigBL sysconfigs = Services.get(ISysConfigBL.class);
		return sysconfigs.getValue(SYSCONFIG_DefaultExcelFileExtension, ExcelOpenXMLFormat.FILE_EXTENSION);
	}

	public static ExcelFormat getDefaultFormat()
	{
		return getFormatByFileExtension(getDefaultFileExtension());
	}

	public static Set<String> getFileExtensionsDefaultFirst()
	{
		return ImmutableSet.<String> builder()
				.add(getDefaultFileExtension()) // default one first
				.addAll(getFileExtensions())
				.build();
	}

	public static Set<String> getFileExtensions()
	{
		return ALL_FORMATS.stream()
				.map(ExcelFormat::getFileExtension)
				.collect(ImmutableSet.toImmutableSet());
	}

	public static ExcelFormat getFormatByFile(@NonNull final File file)
	{
		final String fileExtension = Files.getFileExtension(file.getPath());
		return getFormatByFileExtension(fileExtension);
	}

	public static ExcelFormat getFormatByFileExtension(@NonNull final String fileExtension)
	{
		return ALL_FORMATS
				.stream()
				.filter(format -> fileExtension.equals(format.getFileExtension()))
				.findFirst()
				.orElseThrow(() -> new AdempiereException(
						"No " + ExcelFormat.class.getSimpleName() + " found for file extension '" + fileExtension + "'."
								+ "\n Supported extensions are: " + getFileExtensions()));
	}
}
