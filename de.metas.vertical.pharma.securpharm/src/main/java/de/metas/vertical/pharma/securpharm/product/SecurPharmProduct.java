/*
 *
 * * #%L
 * * %%
 * * Copyright (C) <current year> metas GmbH
 * * %%
 * * This program is free software: you can redistribute it and/or modify
 * * it under the terms of the GNU General Public License as
 * * published by the Free Software Foundation, either version 2 of the
 * * License, or (at your option) any later version.
 * *
 * * This program is distributed in the hope that it will be useful,
 * * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * * GNU General Public License for more details.
 * *
 * * You should have received a copy of the GNU General Public
 * * License along with this program. If not, see
 * * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * * #L%
 *
 */

package de.metas.vertical.pharma.securpharm.product;

import javax.annotation.Nullable;

import de.metas.handlingunits.HuId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Setter;
import lombok.Value;
import lombok.experimental.NonFinal;

/**
 * Contains the product informations we acquired from server.
 */
@Value
@Builder
public class SecurPharmProduct
{
	boolean error;

	String resultCode;
	String resultMessage;

	@Nullable
	ProductDetails productDetails;

	@NonNull
	HuId huId;

	@Nullable
	@NonFinal
	@Setter
	SecurPharmProductId id;

	public boolean isActive()
	{
		return getProductDetails().isActive();
	}

	public boolean isFraud()
	{
		return getProductDetails().isFraud();
	}

	public boolean isDecommissioned()
	{
		return getProductDetails().isDecommissioned();
	}

	public String getDecommissionServerTransactionId()
	{
		return getProductDetails().getDecommissionedServerTransactionId();
	}

	public void productDecommissioned(@NonNull final String decommissionedServerTransactionId)
	{
		getProductDetails().productDecommissioned(decommissionedServerTransactionId);
	}

	public void productDecommissionUndo(@NonNull final String undoDecommissionedServerTransactionId)
	{
		getProductDetails().productDecommissionUndo(undoDecommissionedServerTransactionId);
	}
}
