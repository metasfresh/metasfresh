package org.adempiere.process.rpl.requesthandler.api;

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

import org.adempiere.process.rpl.requesthandler.model.I_IMP_RequestHandler;
import org.adempiere.process.rpl.requesthandler.spi.IReplRequestHandler;
import org.adempiere.server.rpl.interfaces.I_EXP_Format;

/**
 * Instances are created by {@link IReplRequestHandlerBL#createCtx(Properties, I_EXP_Format, I_IMP_RequestHandler)} and
 * contain context info for {@link IReplRequestHandler#handleRequest(org.compiere.model.PO, IReplRequestHandlerCtx)}.
 * 
 * @author ts
 * 
 */
public interface IReplRequestHandlerCtx
{
	public Properties getEnvCtxToUse();

	public I_EXP_Format getImportFormat();

	public I_IMP_RequestHandler getRequestHandlerRecord();
}
