/*
 * #%L
 * de.metas.shipper.gateway.dhl
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

package de.metas.shipper.gateway.dhl.model;

import de.metas.customs.CustomsInvoiceId;
import de.metas.customs.CustomsInvoiceLineId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.math.BigInteger;

@Value
@Builder
public class DhlCustomsDocument
{
	@NonNull
	String exportType; // probably should be "OTHER"
	@NonNull
	String exportTypeDescription; // todo what export type description should i put here????
	//	@NonNull
	//	String placeOfCommittal; // i think it should be the destination city
	@NonNull
	BigDecimal additionalFee; // not sure which column inside customs invoice refers to this

	@NonNull
	String electronicExportNotification; // no clue, but i guess it should always have value "1"

	@NonNull
	String packageDescription;
	//	@NonNull
	//	String countryCode2Origin; // this is the source bpartner country
	@NonNull
	String customsTariffNumber;
	@NonNull
	BigInteger customsAmount; // refers to the qty inside the package
	@NonNull
	BigDecimal netWeightInKg; // must be less than the weight!!
	@NonNull
	BigDecimal customsValue; // not sure which column inside customs invoice refers to this

	// todo this should be changed to NonNull when i figure out how to get the CustomsInvoice
	@Nullable
	CustomsInvoiceId invoiceId;
	@Nullable
	CustomsInvoiceLineId invoiceLineId;

}
