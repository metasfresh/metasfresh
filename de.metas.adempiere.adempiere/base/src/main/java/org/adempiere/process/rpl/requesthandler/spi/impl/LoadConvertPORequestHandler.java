package org.adempiere.process.rpl.requesthandler.spi.impl;

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


import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.process.rpl.requesthandler.api.IReplRequestHandlerCtx;
import org.compiere.model.PO;

import de.metas.util.Check;
import de.metas.util.collections.Converter;

/**
 * An extension of {@link LoadPORequestHandler} which takes an {@link Converter} for creating the response based on request
 * 
 * @author tsa
 * 
 * @param <IT> request model interface class
 * @param <OT> response model interface class
 */
public class LoadConvertPORequestHandler<IT, OT> extends LoadPORequestHandler
{
	private final Class<IT> requestModelClass;
	private final Converter<OT, IT> converter;

	public LoadConvertPORequestHandler(final Class<IT> requestModelClass, final Converter<OT, IT> converter)
	{
		Check.assumeNotNull(requestModelClass, "requestModelClass not null");
		Check.assumeNotNull(converter, "converter not null");
		this.requestModelClass = requestModelClass;
		this.converter = converter;
	}

	@Override
	protected PO createResponse(final IReplRequestHandlerCtx ctx, final PO requestPO)
	{
		Check.assume(requestPO != null, "requestPO not null");

		final IT requestModel = InterfaceWrapperHelper.create(requestPO, requestModelClass);

		final OT responseModel = converter.convert(requestModel);

		final PO responsePO = InterfaceWrapperHelper.getPO(responseModel);
		return responsePO;
	}
}
