package de.metas.data.export.api.impl;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2015 metas GmbH
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


import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.misc.service.IProcessPA;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_PInstance;
import org.compiere.model.I_AD_PInstance_Para;
import org.compiere.util.Env;
import org.slf4j.Logger;

import de.metas.adempiere.form.IClientUIInstance;
import de.metas.data.export.api.IExporter;
import de.metas.data.export.api.IExporterMonitor;
import de.metas.logging.LogManager;

/**
 * Helper monitor which logs the given parameters by using <code>adProcessId</code> when export started. When export finished, it logs the status.
 * 
 * NOTE: this monitor is used when we don't have an actual ADempiere process but we would like to have the audit informations.
 * 
 * @author tsa
 * 
 */
public class ProcessLoggerExporterMonitor implements IExporterMonitor
{
	private static final Logger logger = LogManager.getLogger(ProcessLoggerExporterMonitor.class);

	private final Properties ctx;
	private final int adProcessId;
	private final Object params;
	private final Class<?> paramsInterfaceClass;

	private I_AD_PInstance pinstance;

	private IClientUIInstance clientUIInstance;

	/**
	 * 
	 * @param ctx
	 * @param adProcessId which process shall be used when creating the {@link I_AD_PInstance} record
	 * @param params object which contains the export parameters
	 * @param paramsInterfaceClass interface class to be used when we are introspecting the export parameters
	 */
	public <T> ProcessLoggerExporterMonitor(final Properties ctx, final int adProcessId, T params, Class<T> paramsInterfaceClass)
	{
		Check.assume(adProcessId > 0, "adProcessId > 0");
		Check.assumeNotNull(params, "params not null");
		Check.assumeNotNull(paramsInterfaceClass, "paramsInterfaceClass not null");

		this.ctx = ctx;
		this.adProcessId = adProcessId;
		this.params = params;
		this.paramsInterfaceClass = paramsInterfaceClass;
	}

	@Override
	public void exportStarted(IExporter exporter)
	{
		pinstance = createPInstance();
	}

	@Override
	public void exportFinished(IExporter exporter)
	{
		final Throwable error = exporter.getError();
		if (error != null)
		{
			// Log the error to console. This is useful if the exporter is running asynchronous and the threads executor service is not logging the exception
			logger.error(error.getLocalizedMessage(), error);
			
			if (clientUIInstance != null)
			{
				clientUIInstance.error(0, "Error", error.getLocalizedMessage());
			}
		}

		if (pinstance != null)
		{
			pinstance.setResult(exporter.getExportedRowCount());
			pinstance.setIsProcessing(false);
			if (error != null)
			{
				pinstance.setErrorMsg(error.getLocalizedMessage());
			}
			InterfaceWrapperHelper.save(pinstance);
		}
	}

	private I_AD_PInstance createPInstance()
	{
		final I_AD_PInstance pinstance = InterfaceWrapperHelper.create(ctx, I_AD_PInstance.class, ITrx.TRXNAME_None);
		pinstance.setAD_Process_ID(adProcessId);
		pinstance.setAD_User_ID(Env.getAD_User_ID(ctx));
		pinstance.setAD_Role_ID(Env.getAD_Role_ID(ctx));
		pinstance.setRecord_ID(0); // mandatory
		pinstance.setIsProcessing(true);
		InterfaceWrapperHelper.save(pinstance);

		int seqNo = 1;
		final BeanInfo beanInfo;
		try
		{
			beanInfo = Introspector.getBeanInfo(paramsInterfaceClass);
		}
		catch (IntrospectionException e)
		{
			throw new AdempiereException(e.getLocalizedMessage(), e);
		}

		for (final PropertyDescriptor pd : beanInfo.getPropertyDescriptors())
		{
			final Object value;
			try
			{
				value = pd.getReadMethod().invoke(params);
			}
			catch (Exception e)
			{
				logger.info(e.getLocalizedMessage(), e);
				continue;
			}

			if (value == null)
			{
				continue;
			}

			final I_AD_PInstance_Para pipa = InterfaceWrapperHelper.create(ctx, I_AD_PInstance_Para.class, ITrx.TRXNAME_None);
			pipa.setAD_PInstance_ID(pinstance.getAD_PInstance_ID());
			pipa.setSeqNo(seqNo);
			pipa.setParameterName(pd.getName());

			if (!Services.get(IProcessPA.class).setPInstanceParaValue(pipa, value))
			{
				continue;
			}

			InterfaceWrapperHelper.save(pipa);

			seqNo++;
		}

		return pinstance;
	}

	public void setClientUIInstance(IClientUIInstance clientUIInstance)
	{
		this.clientUIInstance = clientUIInstance;
	}
}
