package de.metas.invoicecandidate.jmx;

import java.util.logging.Level;

import org.adempiere.util.jmx.IJMXNameAware;
import org.compiere.util.CLogger;

import de.metas.invoicecandidate.api.InvoiceCandidate_Constants;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class JMXInvoiceCandidates implements JMXInvoiceCandidatesMBean, IJMXNameAware
{
	private final String jmxName;

	public JMXInvoiceCandidates()
	{
		super();
		this.jmxName = InvoiceCandidate_Constants.JMX_BASE_NAME + ":type=General";
	}

	@Override
	public String getJMXName()
	{
		return jmxName;
	}

	@Override
	public String getLogLevel()
	{
		return getLogger().getLevel().getName();
	}

	private final CLogger getLogger()
	{
		return InvoiceCandidate_Constants.getLogger();
	}

	@Override
	public void setLogLevel(String logLevelName)
	{
		final Level levelNew = Level.parse(logLevelName);
		final CLogger logger = getLogger();
		final Level levelOld = logger.getLevel();

		logger.log(levelOld, "Changing log level " + levelOld + " -> " + levelNew);
		logger.setLevel(levelNew);
	}
}
