package de.metas.ui.web.process;

import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.compiere.model.I_AD_PInstance;
import org.compiere.process.ProcessInfo;
import org.compiere.util.Env;

import de.metas.process.ProcessCtl;
import de.metas.ui.web.process.descriptor.ProcessDescriptor;
import de.metas.ui.web.window.datatypes.LookupValuesList;
import de.metas.ui.web.window.model.Document;
import de.metas.ui.web.window.model.Document.CopyMode;
import de.metas.ui.web.window.model.IDocumentChangesCollector.ReasonSupplier;

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

public class ProcessInstance
{

	private final ProcessDescriptor processDescriptor;
	private final int adPInstanceId;
	private final Document parameters;

	public ProcessInstance(final ProcessDescriptor processDescriptor, final int adPInstanceId, final Document parameters)
	{
		super();
		this.processDescriptor = processDescriptor;
		this.adPInstanceId = adPInstanceId;
		this.parameters = parameters;
	}

	public void destroy()
	{
		// TODO Auto-generated method stub

	}

	public ProcessDescriptor getDescriptor()
	{
		return processDescriptor;
	}

	public int getAD_PInstance_ID()
	{
		return adPInstanceId;
	}

	public Document getParameters()
	{
		return parameters;
	}

	public ProcessInstance copy(final CopyMode copyMode)
	{
		return new ProcessInstance(processDescriptor, adPInstanceId, parameters.copy(copyMode));
	}

	public LookupValuesList getParameterLookupValues(final String parameterName)
	{
		return parameters.getFieldLookupValues(parameterName);
	}

	public LookupValuesList getParameterLookupValuesForQuery(final String parameterName, final String query)
	{
		return parameters.getFieldLookupValuesForQuery(parameterName, query);
	}

	public void processParameterValueChange(String parameterName, Object value, ReasonSupplier reason)
	{
		parameters.processValueChange(parameterName, value, reason);
	}

	public void startProcess()
	{
		// TODO: make sure it's saved in database
		
		final ProcessInfo pi = createProcessInfo();

		ProcessCtl.builder()
				.setProcessInfo(pi)
				// .setWindowNo(windowNo)
				// .setAsyncParent(asyncParent)
				.executeSync();

		// TODO
		throw new UnsupportedOperationException();
	}

	private final ProcessInfo createProcessInfo()
	{
		final Properties ctx = Env.getCtx(); // We assume the right context was already used when the process was loaded

		final int adPInstanceId = getAD_PInstance_ID();
		final I_AD_PInstance adPInstance = InterfaceWrapperHelper.create(ctx, adPInstanceId, I_AD_PInstance.class, ITrx.TRXNAME_None);
		Check.assumeNotNull(adPInstance, "Parameter pInstance is not null");

		final ProcessDescriptor processDescriptor = getDescriptor();
		final String adLanguage = Env.getAD_Language(ctx);
		final String name = processDescriptor.getCaption(adLanguage);
		final int adProcessId = adPInstance.getAD_Process_ID();
		final int AD_Table_ID = adPInstance.getAD_Table_ID();
		final int Record_ID = adPInstance.getRecord_ID();
		final String classname = processDescriptor.getProcessClassname();
		final ProcessInfo pi = ProcessInfo.builder()
				.setCtx(ctx)
				.setAD_Process_ID(adProcessId)
				.setTitle(name)
				.setClassname(classname)
				.setRecord(AD_Table_ID, Record_ID)
				.build();
		pi.setAD_User_ID(Env.getAD_User_ID(ctx));
		pi.setAD_Client_ID(Env.getAD_Client_ID(ctx));
		pi.setAD_Org_ID(Env.getAD_Org_ID(ctx));
		pi.setAD_PInstance_ID(adPInstanceId);
		// pi.setParameter(parameter); // NOTE: don't set it; let it to be loaded

		return pi;
	}

}
