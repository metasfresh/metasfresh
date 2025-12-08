/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2020 metas GmbH
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

package org.adempiere.ad.service;

import de.metas.ad_reference.ADRefTable;
import de.metas.util.ISingletonService;
import de.metas.util.collections.BlindIterator;
import org.adempiere.ad.service.impl.LookupDisplayInfo;
import org.adempiere.ad.validationRule.IValidationContext;
import org.adempiere.ad.validationRule.IValidationRule;
import org.compiere.model.MLookupInfo;
import org.compiere.util.KeyNamePair;
import org.compiere.util.NamePair;
import org.compiere.util.ValueNamePair;

/**
 * Field Lookup DAO methods
 *
 * @author tsa
 */
public interface ILookupDAO extends ISingletonService
{
	/**
	 * Contains a set of {@link ValueNamePair} or {@link KeyNamePair} elements.
	 *
	 * @author tsa
	 */
	interface INamePairIterator extends BlindIterator<NamePair>, AutoCloseable
	{
		/**
		 * @return true if this iterator is valid
		 */
		boolean isValid();

		/**
		 * Gets validation key of data in loaded context.
		 */
		Object getValidationKey();

		@Override
		NamePair next();

		/**
		 * @return true if last value was active
		 */
		boolean wasActive();

		/**
		 * @return true if underlying elements are of type{@link KeyNamePair}; if false then elements are of type {@link ValueNamePair}
		 */
		boolean isNumericKey();

		@Override
		void close();
	}

	LookupDisplayInfo getLookupDisplayInfo(ADRefTable tableRefInfo);

	/**
	 * Retrieves all elements of <code>lookupInfo</code> in given <code>validationCtx</code> context
	 */
	INamePairIterator retrieveLookupValues(IValidationContext validationCtx, MLookupInfo lookupInfo);

	/**
	 * Retrieves all elements of <code>lookupInfo</code> in given <code>validationCtx</code> context
	 *
	 * @param additionalValidationRule optional additional validation rule to be applied on top of lookupInfo's validation rule
	 */
	INamePairIterator retrieveLookupValues(IValidationContext validationCtx, MLookupInfo lookupInfo, IValidationRule additionalValidationRule);

	/**
	 * Directly retrieves a data element identified by <code>key</code>.
	 */
	NamePair retrieveLookupValue(IValidationContext validationCtx, MLookupInfo lookupInfo, Object key);

	/**
	 * Creates a validation key to be used when checking if data is valid in a given context
	 */
	Object createValidationKey(IValidationContext validationCtx, MLookupInfo lookupInfo);
}
