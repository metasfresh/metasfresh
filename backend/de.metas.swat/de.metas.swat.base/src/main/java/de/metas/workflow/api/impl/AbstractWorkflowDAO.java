package de.metas.workflow.api.impl;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.Properties;

import org.adempiere.model.InterfaceWrapperHelper;

import de.metas.workflow.api.IWorkflowDAO;
import de.metas.workflow.model.I_C_Doc_Responsible;
import lombok.NonNull;

public abstract class AbstractWorkflowDAO implements IWorkflowDAO
{
	protected abstract I_C_Doc_Responsible retrieveDocResponsible(final Properties ctx, final int tableId, final int recordId, final String trxName);

	@Override
	public I_C_Doc_Responsible retrieveDocResponsible(@NonNull final Object doc)
	{

		final Properties ctx = InterfaceWrapperHelper.getCtx(doc);
		final String trxName = InterfaceWrapperHelper.getTrxName(doc);

		final int tableId = InterfaceWrapperHelper.getModelTableId(doc);
		final int recordId = InterfaceWrapperHelper.getId(doc);

		return retrieveDocResponsible(ctx, tableId, recordId, trxName);
	}

}
