package org.adempiere.process.rpl;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

import org.adempiere.server.rpl.exceptions.ExportProcessorException;
import org.compiere.model.MEXPProcessor;
import org.compiere.model.PO;
import org.compiere.util.Trx;
import org.w3c.dom.Document;

import java.util.Properties;

public class ExportProcessor2Wrapper implements IExportProcessor2
{
	private final IExportProcessor exportProcessor;

	public ExportProcessor2Wrapper(IExportProcessor exportProcessor)
	{
		this.exportProcessor = exportProcessor;
	}

	@Override
	public void createInitialParameters(MEXPProcessor processor)
	{
		exportProcessor.createInitialParameters(processor);
	}
	
	@Override
	public void process(Properties ctx, MEXPProcessor expProcessor, Document document, Trx trx) throws ExportProcessorException
	{
		try
		{
			exportProcessor.process(ctx, expProcessor, document, trx);
		}
		catch (Exception e)
		{
			// TODO add AD_Message
			throw new ExportProcessorException(null, e);
		}
	}

	@Override
	public void process(MEXPProcessor expProcessor, Document document, PO po) throws ExportProcessorException
	{
		final Properties ctx = po.getCtx();
		final Trx trx = Trx.get(po.get_TrxName(), false);
		try
		{
			exportProcessor.process(ctx, expProcessor, document, trx);
		}
		catch (final Exception e)
		{
			// TODO add AD_Message
			throw new ExportProcessorException(e.getClass().getSimpleName(), e);
		}
	}
}
