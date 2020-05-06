package org.adempiere.mm.attributes.api;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.adempiere.ad.modelvalidator.ModelChangeType;
import org.adempiere.ad.persistence.ModelDynAttributeAccessor;

/**
 * Listens on model (which is also {@link IAttributeSetInstanceAware}).<br>
 * Use {@link IModelAttributeSetInstanceListenerService#registerListener(IModelAttributeSetInstanceListener)} to register your own listener.
 *
 * @author tsa
 *
 */
public interface IModelAttributeSetInstanceListener
{
	/**
	 * Flag used to specify that we don't want automatic ASI update when a model is changed.
	 *
	 * Example use case: you want to create an order line on which you precisely set the ASI and don't want to be changed.
	 */
	ModelDynAttributeAccessor<Object, Boolean> DYNATTR_DisableASIUpdateOnModelChange = new ModelDynAttributeAccessor<>(
			IModelAttributeSetInstanceListener.class.getName() + "#DisableASIUpdateOnModelChange",
			Boolean.class);

	/**
	 * {@link #getSourceColumnNames()} can return this constant to indicate that it does not matter which particular column was changed.
	 */
	List<String> ANY_SOURCE_COLUMN = Collections.unmodifiableList(new ArrayList<String>());

	/**
	 * @return Listened model's Table_Name
	 */
	String getSourceTableName();

	/**
	 * @return listened table's column names; also see {@link #modelChanged(Object)}.
	 */
	List<String> getSourceColumnNames();

	/**
	 * Called by API if the listener model (which is also an {@link IAttributeSetInstanceAware}) was changed and the the model validation engine was fired with
	 * <ul>
	 * <li>{@link ModelChangeType#BEFORE_NEW}
	 * <li>{@link ModelChangeType#BEFORE_CHANGE} and at least one of the changed columns is one of those returned by {@link #getSourceColumnNames()}.
	 * </ul>
	 *
	 * @param model
	 */
	void modelChanged(Object model);
}
