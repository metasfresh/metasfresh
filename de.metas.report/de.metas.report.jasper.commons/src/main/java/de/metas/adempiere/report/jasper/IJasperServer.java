package de.metas.adempiere.report.jasper;

/*
 * #%L
 * de.metas.report.jasper.commons
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


public interface IJasperServer
{
	OutputType DEFAULT_OutputType = OutputType.PDF;

	public byte[] report(int AD_Process_ID, int AD_PInstance_ID, String adLanguage, OutputType outputType);

	/**
	 * Method to restart the cache for Jasper servlets.
	 */
	public void cacheReset();
}
