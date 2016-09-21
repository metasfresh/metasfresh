package org.adempiere.ad.validationRule;

import java.util.Set;

import org.compiere.util.NamePair;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/**
 * Predicate used to filter {@link NamePair} items based on given {@link IValidationContext}.
 *
 * For more helping tools, please check {@link NamePairPredicates}.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public interface INamePairPredicate
{
	INamePairPredicate NULL = NamePairPredicates.NULL;

	/**
	 * @return true if the predicate accepts given item
	 */
	boolean accept(IValidationContext evalCtx, NamePair item);

	/**
	 * @return a set of parameters on which this predicate depends. On evaluation time those parameters has to be in the {@link IValidationContext}
	 */
	Set<String> getParameters();
}