package org.adempiere.ad.trx.processor.api;

import org.adempiere.ad.trx.processor.spi.ITrxItemProcessor;
import org.adempiere.exceptions.AdempiereException;

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

/**
 * Exception thrown when there is a configuration issue while creating the {@link ITrxItemProcessor} or {@link ITrxItemProcessorExecutor}.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class TrxItemProcessorConfigException extends AdempiereException
{
	private static final long serialVersionUID = 415994901244958144L;

	public TrxItemProcessorConfigException(final String message)
	{
		super(message);
	}
}
