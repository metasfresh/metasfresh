package de.metas.ui.web.process;

import java.io.File;
import java.io.Serializable;

import javax.annotation.concurrent.Immutable;

import org.compiere.util.Util;

import com.google.common.base.MoreObjects;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2016 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@Immutable
@SuppressWarnings("serial")
public class ProcessInstanceResult implements Serializable
{
	public static final Builder builder()
	{
		return new Builder();
	}

	private final int adPInstanceId;
	private final String summary;
	private final boolean error;

	private final String reportFilename;
	private final String reportContentType;
	private final File reportTempFile;

	private ProcessInstanceResult(final Builder builder)
	{
		super();
		adPInstanceId = builder.adPInstanceId;
		summary = builder.summary;
		error = builder.error;

		reportFilename = builder.reportFilename;
		reportContentType = builder.reportContentType;
		reportTempFile = builder.reportTempFile;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("adPInstanceId", adPInstanceId)
				.add("summary", summary)
				.add("error", error)
				.add("reportFilename", reportFilename)
				.add("reportContentType", reportContentType)
				.add("reportTempFile", reportTempFile)
				.toString();
	}

	public int getAD_PInstance_ID()
	{
		return adPInstanceId;
	}

	public String getSummary()
	{
		return summary;
	}
	
	public boolean isSuccess()
	{
		return !isError();
	}

	public boolean isError()
	{
		return error;
	}

	public String getReportFilename()
	{
		return reportFilename;
	}

	public String getReportContentType()
	{
		return reportContentType;
	}
	
	public byte[] getReportData()
	{
		return Util.readBytes(reportTempFile);
	}

	public static final class Builder
	{
		private int adPInstanceId;
		private String summary;
		private boolean error;

		private String reportFilename;
		private String reportContentType;
		private File reportTempFile;

		private Builder()
		{
			super();
		}

		public ProcessInstanceResult build()
		{
			return new ProcessInstanceResult(this);
		}

		public Builder setAD_PInstance_ID(final int adPInstanceId)
		{
			this.adPInstanceId = adPInstanceId;
			return this;
		}

		public Builder setSummary(final String summary)
		{
			this.summary = summary;
			return this;
		}

		public Builder setError(final boolean error)
		{
			this.error = error;
			return this;
		}

		public Builder setReportData(final String reportFileName, final String reportContentType, final File reportTempFile)
		{
			reportFilename = reportFileName;
			this.reportContentType = reportContentType;
			this.reportTempFile = reportTempFile;
			return this;
		}
	}
}
