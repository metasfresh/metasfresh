/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.contracts.bpartner.service;

import de.metas.bpartner.composite.BPartnerContact;
import lombok.Builder;
import lombok.Value;

import javax.annotation.Nullable;

@Value
@Builder
public class DefaultContacts
{
	@Nullable
	BPartnerContact defaultContact;
	boolean foundDefaultContact;

	@Nullable
	BPartnerContact billToDefaultContact;
	boolean foundBillToDefaultContact;

	@Nullable
	BPartnerContact shipToDefaultContact;
	boolean foundShipToDefaultContact;

	@Nullable
	BPartnerContact salesDefaultContact;
	boolean foundSalesDefaultContact;

	@Nullable
	BPartnerContact purchaseDefaultContact;
	boolean foundPurchaseDefaultContact;

}
