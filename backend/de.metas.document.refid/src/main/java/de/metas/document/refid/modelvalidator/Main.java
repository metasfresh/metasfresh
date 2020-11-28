package de.metas.document.refid.modelvalidator;

/*
 * #%L
 * de.metas.document.refid
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


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.MClient;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.PO;

import de.metas.document.refid.api.IReferenceNoBL;
import de.metas.document.refid.api.IReferenceNoDAO;
import de.metas.document.refid.api.IReferenceNoGeneratorInstance;
import de.metas.document.refid.model.I_C_ReferenceNo_Type;
import de.metas.document.refid.workflow.TrackingWFExecutionListener;
import de.metas.util.Services;
import de.metas.workflow.api.IWFExecutionFactory;

public class Main implements ModelValidator
{
	private ModelValidationEngine engine;
	private int m_AD_Client_ID = -1;
	private final List<ReferenceNoGeneratorInstanceValidator> docValidators = new ArrayList<>();

	@Override
	public void initialize(ModelValidationEngine engine, MClient client)
	{
		this.engine = engine;
		if (client != null)
		{
			m_AD_Client_ID = client.getAD_Client_ID();
		}

		engine.addModelValidator(new C_ReferenceNo_Type(this), client);
		engine.addModelValidator(new C_ReferenceNo_Type_Table(this), client);
		engine.addModelValidator(new C_ReferenceNo(), client);

		//
		// Register all referenceNo generator instance interceptors
		final List<I_C_ReferenceNo_Type> typeRecords = Services.get(IReferenceNoDAO.class).retrieveReferenceNoTypes();
		for (final I_C_ReferenceNo_Type typeRecord : typeRecords)
		{
			registerInstanceValidator(typeRecord);
		}

		//
		// Register Workflow execution/identification tracker
		Services.get(IWFExecutionFactory.class).registerListener(new TrackingWFExecutionListener());
	}

	@Override
	public int getAD_Client_ID()
	{
		return m_AD_Client_ID;
	}

	@Override
	public String login(int AD_Org_ID, int AD_Role_ID, int AD_User_ID)
	{
		// nothing
		return null;
	}

	@Override
	public String modelChange(PO po, int type) throws Exception
	{
		// nothing
		return null;
	}

	@Override
	public String docValidate(PO po, int timing)
	{
		// nothing
		return null;
	}

	private void registerInstanceValidator(I_C_ReferenceNo_Type type)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(type);

		final IReferenceNoGeneratorInstance instance = Services.get(IReferenceNoBL.class).getReferenceNoGeneratorInstance(ctx, type);
		if (instance == null)
		{
			return;
		}

		final ReferenceNoGeneratorInstanceValidator validator = new ReferenceNoGeneratorInstanceValidator(instance);
		engine.addModelValidator(validator);
		docValidators.add(validator);
	}

	private void unregisterInstanceValidator(final I_C_ReferenceNo_Type type)
	{
		final Iterator<ReferenceNoGeneratorInstanceValidator> it = docValidators.iterator();
		while (it.hasNext())
		{
			final ReferenceNoGeneratorInstanceValidator validator = it.next();
			if (validator.getInstance().getType().getC_ReferenceNo_Type_ID() == type.getC_ReferenceNo_Type_ID())
			{
				validator.unregister();
				it.remove();
			}
		}
	}

	public void updateInstanceValidator(final I_C_ReferenceNo_Type type)
	{
		unregisterInstanceValidator(type);
		registerInstanceValidator(type);
	}
}
