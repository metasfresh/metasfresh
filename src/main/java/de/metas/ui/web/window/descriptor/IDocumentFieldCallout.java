package de.metas.ui.web.window.descriptor;

import java.util.Set;

import org.adempiere.ad.callout.api.ICalloutExecutor;
import org.adempiere.ad.callout.api.ICalloutField;
import org.adempiere.ad.callout.api.ICalloutInstance;

/*
 * #%L
 * metasfresh-webui-api
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/**
 * Document field callout: an {@link ICalloutField} implementation which also provides the field names for which this callout shall be triggered.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public interface IDocumentFieldCallout extends ICalloutInstance
{
	@Override
	String getId();

	/**
	 * @return list of field names for which this callout shall be triggered.
	 */
	Set<String> getDependsOnFieldNames();

	@Override
	void execute(ICalloutExecutor executor, ICalloutField field) throws Exception;
}
