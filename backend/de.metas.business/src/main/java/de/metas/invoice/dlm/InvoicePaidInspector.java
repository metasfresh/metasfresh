package de.metas.invoice.dlm;

import org.adempiere.model.InterfaceWrapperHelper;

import de.metas.adempiere.model.I_C_Invoice;
import de.metas.dlm.coordinator.IRecordInspector;
import de.metas.dlm.migrator.IMigratorService;

/*
 * #%L
 * metasfresh-dlm
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
 * Checks if an invoice was already paid. If it is still open, then it can't be archived.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class InvoicePaidInspector implements IRecordInspector
{
	public static InvoicePaidInspector INSTANCE = new InvoicePaidInspector();

	private InvoicePaidInspector()
	{
	}

	@Override
	public int inspectRecord(Object model)
	{
		final I_C_Invoice invoice = InterfaceWrapperHelper.create(model, I_C_Invoice.class);
		if (!invoice.isProcessed())
		{
			return IMigratorService.DLM_Level_LIVE;
		}
		if (!invoice.isPaid())
		{
			return IMigratorService.DLM_Level_LIVE;
		}
		return IMigratorService.DLM_Level_ARCHIVE;
	}

	/**
	 * Returns <code>true</code> if the given model has an <code>Updated</code> column.
	 */
	@Override
	public boolean isApplicableFor(Object model)
	{
		return I_C_Invoice.Table_Name.equals(InterfaceWrapperHelper.getModelTableName(model));
	}
}
