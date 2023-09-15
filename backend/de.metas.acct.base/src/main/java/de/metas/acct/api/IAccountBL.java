package de.metas.acct.api;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import de.metas.util.ISingletonService;
import lombok.NonNull;
import org.compiere.model.I_C_ValidCombination;

import java.util.Set;

/**
 * Business logic used to manipulate accounts (i.e. {@link I_C_ValidCombination}s)
 *
 * @author tsa
 */
public interface IAccountBL extends ISingletonService
{
	void updateValueDescriptionByElementType(@NonNull AcctSchemaElementType elementType, int value);

	void updateValueDescriptionByElementTypes(@NonNull Set<AcctSchemaElementType> elementTypes, int value);

	void updateValueDescriptionByAcctSchemaId(@NonNull AcctSchemaId acctSchemaId);

	/**
	 * Build and set {@link I_C_ValidCombination#COLUMNNAME_Combination}, {@link I_C_ValidCombination#COLUMNNAME_Description} and {@link I_C_ValidCombination#COLUMNNAME_IsFullyQualified}.
	 */
	void updateValueDescription(I_C_ValidCombination account);

	/**
	 * Validate account
	 */
	void validate(I_C_ValidCombination account);

	void createIfMissing(@NonNull AccountDimension dimension);
}
