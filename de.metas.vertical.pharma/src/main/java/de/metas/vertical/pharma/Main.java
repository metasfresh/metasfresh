package de.metas.vertical.pharma;

import org.adempiere.impexp.impl.PharmaImportInterceptor;
import org.compiere.model.MClient;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.PO;

import de.metas.vertical.pharma.model.I_C_BPartner;

/*
 * #%L
 * de.metas.procurement.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/**
 * Module activator
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class Main implements ModelValidator
{


	@Override
	public void initialize(ModelValidationEngine engine, MClient client)
	{
		engine.addImportValidate(I_C_BPartner.Table_Name, PharmaImportInterceptor.instance);
	}

	@Override
	public int getAD_Client_ID()
	{
		return 0;
	}

	@Override
	public String login(int AD_Org_ID, int AD_Role_ID, int AD_User_ID)
	{
		return null;
	}

	@Override
	public String modelChange(PO po, int type) throws Exception
	{
		return null;
	}

	@Override
	public String docValidate(PO po, int timing) throws Exception
	{
		return null;
	}
}
