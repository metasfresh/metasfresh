/*
 * #%L
 * de-metas-camel-edi
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

package de.metas.edi.esb.remadvimport.ecosio;

import at.erpel.schemas._1p0.documents.extensions.edifact.AdjustmentType;
import at.erpel.schemas._1p0.documents.extensions.edifact.ItemType;
import at.erpel.schemas._1p0.documents.extensions.edifact.ObjectFactory;
import at.erpel.schemas._1p0.documents.extensions.edifact.REMADVListLineItemExtensionType;
import at.erpel.schemas._1p0.documents.extensions.edifact.TaxType;
import at.erpel.schemas._1p0.documents.extensions.edifact.VATType;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class JsonRemittanceAdviceLineProducerTest
{
	private final ObjectFactory objectFactory = new ObjectFactory();

	@Test
	void getServiceFeeVATRate()
	{		
		final REMADVListLineItemExtensionType remadvListLineItemExtensionType = objectFactory.createREMADVListLineItemExtensionType();
		final REMADVListLineItemExtensionType.MonetaryAmounts monetaryAmounts = objectFactory.createREMADVListLineItemExtensionTypeMonetaryAmounts();

		// we only take into account the rates from reasonCodes 67 and 90
		monetaryAmounts.getAdjustment().add(createAdjustmentType("19", "2.5"));
		
		// we assume that reasonCodes 67 and 90 always have a common vat rate
		monetaryAmounts.getAdjustment().add(createAdjustmentType("67", "7.7"));
		monetaryAmounts.getAdjustment().add(createAdjustmentType("90", "7.7"));

		remadvListLineItemExtensionType.setMonetaryAmounts(monetaryAmounts);

		final Optional<BigDecimal> serviceFeeVATRate = JsonRemittanceAdviceLineProducer.of(remadvListLineItemExtensionType).getServiceFeeVATRate(monetaryAmounts);

		assertThat(serviceFeeVATRate).isPresent();
		assertThat(serviceFeeVATRate.get()).isEqualByComparingTo("7.7");
	}

	private AdjustmentType createAdjustmentType(final String reasonCode, final String taxRate)
	{
		final AdjustmentType adjustmentType = objectFactory.createAdjustmentType();
		adjustmentType.setReasonCode(reasonCode);
		final TaxType taxTypeToIgnore = objectFactory.createTaxType();
		final VATType vatType = objectFactory.createVATType();
		final ItemType itemType = objectFactory.createItemType();
		itemType.setTaxRate(new BigDecimal(taxRate));
		vatType.getItem().add(itemType);
		taxTypeToIgnore.setVAT(vatType);
		adjustmentType.setTax(taxTypeToIgnore);

		return adjustmentType;
	}
}