package org.adempiere.process.rpl.requesthandler.spi;

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


import org.adempiere.process.rpl.requesthandler.api.IReplRequestHandlerBL;
import org.adempiere.process.rpl.requesthandler.api.IReplRequestHandlerCtx;
import org.adempiere.process.rpl.requesthandler.api.IReplRequestHandlerResult;
import org.compiere.model.PO;

/**
 * SPI to be extended by request handler implementations.
<<<<<<< HEAD
 * 
=======
 * <p/>
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
 * Please consider extending the adapter: {@link ReplRequestHandlerAdapter}.
 * 
 * @author ts
 * 
 */
public interface IReplRequestHandler
{
	/**
	 * Note that an instance can use {@link IReplRequestHandlerBL#createInitialRequestHandlerResult()} to get and
	 * initial result instance to fill.
	 * 
	 * @param po
	 *            the PO that was just received via replication interface and that therefore represents the request
	 * @param ctx
	 *            context info to be used when processing the request
<<<<<<< HEAD
	 * 
	 * @return
=======
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 */
	IReplRequestHandlerResult handleRequest(PO po, IReplRequestHandlerCtx ctx);
}
