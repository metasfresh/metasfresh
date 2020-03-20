package de.metas.report.server;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import com.google.common.io.BaseEncoding;
import lombok.Builder;
import lombok.NonNull;
import lombok.ToString;
import lombok.Value;

/*
 * #%L
 * de.metas.report.jasper.commons
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

@Value
@Builder
@ToString(exclude = "reportContent")
@JsonDeserialize(builder = ReportResult.ReportResultBuilder.class)
public class ReportResult
{
	String reportFilename;

	@NonNull
	OutputType outputType;

	String reportContentBase64;

	public byte[] getReportContent() {
		return BaseEncoding.base64().decode(reportContentBase64);
	}

	@JsonPOJOBuilder(withPrefix = "")
	public static class ReportResultBuilder
	{
	}
}
