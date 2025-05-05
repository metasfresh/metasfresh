package de.metas.document.engine;

import com.google.common.collect.ImmutableSet;
import org.springframework.stereotype.Component;

import java.util.Set;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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
 * Customize the available DocActions for given document.
 * Just annotate implementations with spring's {@link Component} they will be automatically discovered.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public interface IDocActionOptionsCustomizer
{
	/**
	 * Gets table name on which this customizer applies.
	 * Called by API at registration time.
	 */
	String getAppliesToTableName();

	void customizeValidActions(DocActionOptionsContext optionsCtx);

	default Set<String> getParameters() { return ImmutableSet.of(); }
}
