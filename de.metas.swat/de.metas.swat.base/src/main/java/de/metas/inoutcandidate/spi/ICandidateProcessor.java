package de.metas.inoutcandidate.spi;

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


import java.util.Properties;

import org.adempiere.inout.util.IShipmentCandidates;

import de.metas.inoutcandidate.api.IShipmentScheduleBL;

/**
 * Implementations are called by
 * {@link IShipmentScheduleBL#updateSchedules(Properties, java.util.List, String)}
 * between the first and second allocation run. It's task is to set the status of the candidates from the first run.
 * 
 * @author ts
 * 
 */
public interface ICandidateProcessor
{
	/**
	 * 
	 * @param candidates
	 * @param trxName
	 * @return the number of inout line candidates that have been removed
	 */
	int processCandidates(Properties ctx, IShipmentCandidates candidates, String trxName);
}
