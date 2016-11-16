package de.metas.report.engine;

import java.util.List;
import java.util.Properties;

import org.adempiere.ad.security.IUserRolePermissions;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_Process;
import org.compiere.util.Env;

import com.google.common.collect.ImmutableList;

import de.metas.adempiere.report.jasper.OutputType;
import de.metas.process.IADPInstanceDAO;
import de.metas.process.ProcessInfoParameter;

/*
 * #%L
 * de.metas.report.jasper.server.base
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

public final class ReportContext
{
	public static final Builder builder()
	{
		return new Builder();
	}

	private final Properties ctx;
	private final int AD_Process_ID;
	private final I_AD_Process adProcess;
	private final int AD_PInstance_ID;
	private final String AD_Language;
	private OutputType outputType;
	private final int AD_Table_ID;
	private final int Record_ID;
	private final ImmutableList<ProcessInfoParameter> processInfoParameters;

	private ReportContext(final Builder builder)
	{
		super();
		ctx = builder.ctx;

		adProcess = builder.adProcess;
		Check.assumeNotNull(adProcess, "AD_Process not null");
		AD_Process_ID = adProcess.getAD_Process_ID();

		AD_PInstance_ID = builder.AD_PInstance_ID;
		AD_Language = builder.AD_Language;
		outputType = builder.outputType;
		AD_Table_ID = builder.AD_Table_ID;
		Record_ID = builder.Record_ID;
		processInfoParameters = ImmutableList.copyOf(builder.getProcessInfoParameters());
	}

	@Override
	public String toString()
	{
		return "ReportContext ["
				+ "adProcess=" + adProcess
				+ ", AD_PInstance_ID=" + AD_PInstance_ID
				+ ", AD_Language=" + AD_Language
				+ ", outputType=" + outputType
				+ ", AD_Table_ID=" + AD_Table_ID
				+ ", Record_ID=" + Record_ID
				+ ", processInfoParameters=" + processInfoParameters
				+ "]";
	}

	public Properties getCtx()
	{
		return ctx;
	}
	
	public String getReportTemplatePath()
	{
		return getAD_Process().getJasperReport();
	}
	
	public String getSQLStatement()
	{
		return getAD_Process().getSQLStatement();
	}

	public int getAD_Process_ID()
	{
		return AD_Process_ID;
	}

	/**
	 * @return {@link I_AD_Process}; never returns null
	 */
	public I_AD_Process getAD_Process()
	{
		return adProcess;
	}
	
	/**
	 * @param type
	 * @return {@link I_AD_Process}; never returns null
	 */
	public <T extends I_AD_Process> T getAD_Process(final Class<T> type)
	{
		return InterfaceWrapperHelper.create(adProcess, type);
	}

	public int getAD_PInstance_ID()
	{
		return AD_PInstance_ID;
	}

	public String getAD_Language()
	{
		return AD_Language;
	}

	public OutputType getOutputType()
	{
		return outputType;
	}

	public void setOutputType(final OutputType outputType)
	{
		this.outputType = outputType;
	}
	
	public String getTableNameOrNull()
	{
		if (AD_Table_ID <= 0)
		{
			return null;
		}
		return Services.get(IADTableDAO.class).retrieveTableName(AD_Table_ID);
	}

	public int getAD_Table_ID()
	{
		return AD_Table_ID;
	}

	public int getRecord_ID()
	{
		return Record_ID;
	}

	public boolean isApplySecuritySettings()
	{
		final I_AD_Process adProcess = getAD_Process();
		return adProcess == null ? false : adProcess.isApplySecuritySettings();
	}
	
	public IUserRolePermissions getUserRolePermissions()
	{
		return Env.getUserRolePermissions(getCtx());
	}

	public List<ProcessInfoParameter> getProcessInfoParameters()
	{
		return processInfoParameters;
	}

	public static final class Builder
	{
		private Properties ctx;
		private I_AD_Process adProcess;
		private int AD_PInstance_ID;
		private String AD_Language;
		private OutputType outputType;
		private int AD_Table_ID;
		private int Record_ID;

		private Builder()
		{
			super();
		}

		public ReportContext build()
		{
			return new ReportContext(this);
		}

		public Builder setCtx(final Properties ctx)
		{
			this.ctx = ctx;
			return this;
		}

		public Builder setAD_Process(final I_AD_Process adProcess)
		{
			this.adProcess = adProcess;
			return this;
		}

		public Builder setAD_PInstance_ID(final int AD_PInstance_ID)
		{
			this.AD_PInstance_ID = AD_PInstance_ID;
			return this;
		}

		public Builder setAD_Language(final String AD_Language)
		{
			this.AD_Language = AD_Language;
			return this;
		}

		public Builder setOutputType(final OutputType outputType)
		{
			this.outputType = outputType;
			return this;
		}

		public Builder setRecord(final int AD_Table_ID, final int Record_ID)
		{
			this.AD_Table_ID = AD_Table_ID;
			this.Record_ID = Record_ID;
			return this;
		}

		private final List<ProcessInfoParameter> getProcessInfoParameters()
		{
			return Services.get(IADPInstanceDAO.class).retrieveProcessInfoParameters(ctx, AD_PInstance_ID);
		}
	}
}
