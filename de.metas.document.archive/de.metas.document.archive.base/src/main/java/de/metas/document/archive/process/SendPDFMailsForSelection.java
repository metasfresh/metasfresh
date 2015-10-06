package de.metas.document.archive.process;

/*
 * #%L
 * de.metas.document.archive.base
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


import de.metas.async.spi.IWorkpackageProcessor;
import de.metas.document.archive.async.spi.impl.MailWorkpackageProcessor;
import de.metas.document.archive.model.I_C_Doc_Outbound_Log;
import de.metas.document.archive.model.I_C_Doc_Outbound_Log_Line;

/**
 * Send PDF mails for selected entries.
 *
 * @author al
 */
public class SendPDFMailsForSelection extends AbstractSendDocumentsForSelection
{
	@Override
	protected final Class<? extends IWorkpackageProcessor> getWorkpackageProcessor()
	{
		return MailWorkpackageProcessor.class;
	}

	@Override
	protected final boolean isSentAtLeastOnce(final I_C_Doc_Outbound_Log_Line docOutboundLine)
	{
		final I_C_Doc_Outbound_Log log = docOutboundLine.getC_Doc_Outbound_Log();
		return log.getEMailCount() > 0;
	}
}
