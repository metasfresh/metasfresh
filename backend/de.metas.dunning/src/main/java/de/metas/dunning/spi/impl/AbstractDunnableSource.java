package de.metas.dunning.spi.impl;

/*
 * #%L
 * de.metas.dunning
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


import java.util.Iterator;

import de.metas.organization.IOrgDAO;
import de.metas.util.Services;
import org.slf4j.Logger;

import de.metas.dunning.api.IDunnableDoc;
import de.metas.dunning.api.IDunningContext;
import de.metas.dunning.interfaces.I_C_DunningLevel;
import de.metas.dunning.spi.IDunnableSource;
import de.metas.logging.LogManager;
import de.metas.util.collections.FilterIterator;

public abstract class AbstractDunnableSource implements IDunnableSource
{
	protected final transient Logger logger = LogManager.getLogger(getClass());
	protected final transient IOrgDAO orgDAO = Services.get(IOrgDAO.class);

	protected abstract Iterator<IDunnableDoc> createRawSourceIterator(IDunningContext context);

	@Override
	public final Iterator<IDunnableDoc> iterator(final IDunningContext context)
	{
		final Iterator<IDunnableDoc> sourceIterator = createRawSourceIterator(context);

		final Iterator<IDunnableDoc> filteredSourceIterator = new FilterIterator<>(sourceIterator, value -> isEligible(context, value));

		return filteredSourceIterator;
	}

	/**
	 *
	 * @param dunnableDoc
	 * @return true if dunnableDoc is eligible for creating a candidate
	 */
	protected boolean isEligible(final IDunningContext dunningContext, final IDunnableDoc dunnableDoc)
	{
		if (dunnableDoc.getOpenAmt().signum() == 0)
		{
			logger.info("Skip " + dunnableDoc + " because OpenAmt is ZERO");
			return false;
		}

		if (dunnableDoc.isInDispute())
		{
			logger.info("Skip " + dunnableDoc + " because is in dispute");
			return false;
		}

		final I_C_DunningLevel level = dunningContext.getC_DunningLevel();
		final boolean isDue = dunnableDoc.getDaysDue() >= 0;
		if (isDue)
		{
			if (level.isShowAllDue())
			{
				// Document is due, and we are asked to show all due, so there is no point to check if the days after due is valid
				return true;
			}
		}
		else
		{
			// Document is not due yet => not eligible for dunning
			return false;
		}

		final int daysAfterDue = level.getDaysAfterDue().intValue();
		if (dunnableDoc.getDaysDue() < daysAfterDue)
		{
			logger.info("Skip " + dunnableDoc + " because is not already due (DaysAfterDue:" + daysAfterDue + ")");
			return false;
		}

		return true;
	}
}
