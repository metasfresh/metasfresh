package de.metas.aggregation.listeners;

/*
 * #%L
 * de.metas.aggregation
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


import org.adempiere.util.ISingletonService;

import de.metas.aggregation.model.I_C_Aggregation;

/**
 * Aggregation master data listeners service.
 * 
 * Used for registering listeners and listeners firing.
 * 
 * @author tsa
 *
 */
public interface IAggregationListeners extends ISingletonService
{
	void addListener(final IAggregationListener listener);

	void fireAggregationCreated(final I_C_Aggregation aggregation);

	void fireAggregationChanged(final I_C_Aggregation aggregation);

	void fireAggregationDeleted(final I_C_Aggregation aggregation);
}
