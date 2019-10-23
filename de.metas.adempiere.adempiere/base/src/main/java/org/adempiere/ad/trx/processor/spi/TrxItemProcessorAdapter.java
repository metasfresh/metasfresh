package org.adempiere.ad.trx.processor.spi;

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


import java.util.Properties;

import org.adempiere.ad.trx.processor.api.ITrxItemProcessorContext;
import org.adempiere.util.api.IParams;

/**
 * Implement what you need adapter for {@link ITrxItemProcessor}.
 * 
 * @author tsa
 *
 * @param <IT> input type
 * @param <RT> result type
 */
public abstract class TrxItemProcessorAdapter<IT, RT> implements ITrxItemProcessor<IT, RT>
{
	private ITrxItemProcessorContext processorCtx;

	@Override
	public final void setTrxItemProcessorCtx(ITrxItemProcessorContext processorCtx)
	{
		this.processorCtx = processorCtx;
	}

	protected final ITrxItemProcessorContext getTrxItemProcessorCtx()
	{
		return processorCtx;
	}

	protected final Properties getCtx()
	{
		return getTrxItemProcessorCtx().getCtx();
	}

	protected final String getTrxName()
	{
		return getTrxItemProcessorCtx().getTrxName();
	}
	
	protected final IParams getParams()
	{
		return getTrxItemProcessorCtx().getParams();
	}

	@Override
	public abstract void process(IT item) throws Exception;

	@Override
	public RT getResult()
	{
		// nothing at this level
		return null;
	}

}
