package de.metas.ui.web.window.model;

import org.compiere.util.Evaluatee;

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

public interface IDocumentEvaluatee extends Evaluatee
{

	/**
	 * Creates a new evaluatee which has given field in scope.
	 * 
	 * The field in scope is the field for whom we actually do the evaluation.
	 * Documents will not be asked for a field value it the field is in scope.
	 * 
	 * @param fieldNameInScope
	 * @return new evaluatee instance which has the given field in scope
	 */
	IDocumentEvaluatee fieldInScope(String fieldNameInScope);
}
