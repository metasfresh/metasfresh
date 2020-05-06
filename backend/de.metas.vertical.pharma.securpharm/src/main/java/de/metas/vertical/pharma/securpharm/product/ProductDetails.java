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

import org.adempiere.exceptions.AdempiereException;

import de.metas.vertical.pharma.securpharm.client.schema.JsonExpirationDate;
import de.metas.vertical.pharma.securpharm.client.schema.JsonProductPackageState;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.experimental.NonFinal;

/** Secur Pharm Product Details */
@Value
@Builder
public class ProductDetails
{
	@NonNull
	private String productCode;
	private ProductCodeType productCodeType;

	@NonNull
	private String lot;
	@NonNull
	private String serialNumber;

	@NonNull
	private JsonExpirationDate expirationDate;

	@NonNull
	private JsonProductPackageState activeStatus;
	private String inactiveReason;

	//
	@NonFinal
	private boolean decommissioned;
	@NonFinal
	private String decommissionedServerTransactionId;

	public boolean isActive()
	{
		return getActiveStatus() == JsonProductPackageState.ACTIVE;
	}

	public boolean isFraud()
	{
		return getActiveStatus() == JsonProductPackageState.FRAUD;
	}

	public void productDecommissioned(@NonNull final String decommissionedServerTransactionId)
	{
		if (decommissioned)
		{
			throw new AdempiereException("Product already decommissioned: " + this);
		}

		this.decommissioned = true;
		this.decommissionedServerTransactionId = decommissionedServerTransactionId;
	}

	public void productDecommissionUndo(@NonNull final String undoDecommissionedServerTransactionId)
	{
		if (!decommissioned)
		{
			throw new AdempiereException("Product is not decommissioned: " + this);
		}

		this.decommissioned = false;
		this.decommissionedServerTransactionId = undoDecommissionedServerTransactionId;
	}

}
