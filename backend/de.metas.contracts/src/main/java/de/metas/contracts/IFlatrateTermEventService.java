package de.metas.contracts;

import org.adempiere.util.ISingletonService;

import de.metas.contracts.model.I_C_Flatrate_Conditions;
import de.metas.contracts.spi.IFlatrateTermEventListener;

/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2016 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/**
 * Service used to register and get {@link IFlatrateTermEventListener}s.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public interface IFlatrateTermEventService extends ISingletonService
{
	/**
	 * 
	 * @param listener
	 * @param typeConditions one of the possible {@link I_C_Flatrate_Conditions#getType_Conditions()}.
	 */
	void registerEventListenerForConditionsType(IFlatrateTermEventListener listener, String typeConditions);

	/**
	 * 
	 * @param typeConditions one of the possible {@link I_C_Flatrate_Conditions#getType_Conditions()}.
	 * @return
	 */
	IFlatrateTermEventListener getHandler(String typeConditions);

}
