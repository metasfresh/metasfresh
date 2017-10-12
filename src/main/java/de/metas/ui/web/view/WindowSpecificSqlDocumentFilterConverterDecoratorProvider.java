package de.metas.ui.web.view;

import org.springframework.stereotype.Component;

import de.metas.ui.web.document.filter.sql.SqlDocumentFilterConverterDecoratorProvider;
import de.metas.ui.web.window.datatypes.WindowId;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2017 metas GmbH
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
 * Implementors shall be discovered annotated with {@link Component}, discovered by spring and be autowired into {@link SqlViewFactory}.
 * <p>
 * When a view is created for the implementor's {@link WindowId}, the respective filter results are then augmented<br>
 * using the converter that is provided by the impementor's {@link SqlDocumentFilterConverterDecoratorProvider}.
 * 
 * @author metas-dev <dev@metasfresh.com>
 * @task Extend framework to allow modification of standard filter results https://github.com/metasfresh/metasfresh-webui-api/issues/628
 */

public interface WindowSpecificSqlDocumentFilterConverterDecoratorProvider
{
	WindowId getWindowId();

	SqlDocumentFilterConverterDecoratorProvider getConverter();
}
