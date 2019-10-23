package de.metas.contracts.commission;

import de.metas.product.ProductId;
import lombok.experimental.UtilityClass;

/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2019 metas GmbH
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

@UtilityClass
public class CommissionConstants
{
	public static final ProductId COMMISSION_PRODUCT_ID = ProductId.ofRepoId(540420);

	public static final String COMMISSION_DOC_SUBTYPE_VALUE = "CA";
	public static final String COMMISSION_DOC_SUBTYPE_VALUENAME = "CommissionSettlement";

	/**
	 * Please keep in sysnc with {@code AD_Ref_List_ID=542010} and various display logic expressions.
	 */
	public static final String TYPE_CONDITIONS_COMMISSION = "Commission";
}
