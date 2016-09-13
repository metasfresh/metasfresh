package org.adempiere.ad.callout.spi;

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

import org.adempiere.ad.callout.api.ICalloutInstance;
import org.adempiere.ad.modelvalidator.AbstractModuleInterceptor;
import org.adempiere.util.ISingletonService;

/**
 * Instances can be used to register callouts "programatically", i.e. not by database.
 * The preferred way to obtain an instance of this interface is to subclass {@link AbstractModuleInterceptor} and override its {@link AbstractModuleInterceptor#registerCallouts(IProgramaticCalloutProvider) registerCallouts} method.<br>
 * In other words, programatic callouts should be registered per-module and at one place within each module.<br>
 * However, since this interface extends {@link ISingletonService}, an instance can also be obtained via {@link org.adempiere.util.Services#get(Class)}.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public interface IProgramaticCalloutProvider extends ICalloutProvider, ISingletonService
{
	boolean registerCallout(String tableName, String columnName, ICalloutInstance callout);

	boolean registerAnnotatedCallout(Object annotatedCalloutObj);
}
