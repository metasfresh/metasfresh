package de.metas.shipper.gateway.derkurier.misc;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;

import de.metas.shipper.gateway.derkurier.DerKurierTestTools;
import de.metas.shipper.gateway.spi.model.DeliveryOrder;

/*
 * #%L
 * de.metas.shipper.gateway.derkurier
 * %%
 * Copyright (C) 2018 metas GmbH
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

public class ConvertersTest
{
	@Test
	public void createCsv()
	{
		final DeliveryOrder deliveryOrder = DerKurierTestTools.createTestDeliveryOrder();

		final List<String> csv = new Converters().createCsv(deliveryOrder);
		assertThat(csv).hasSize(2);
		assertThat(csv.get(0)).isEqualTo("2018-01-08;customerNumber-12345;to company;;DE;54321;Köln;street 1 - street 2;1;030;2018-01-09;09:00;17:30;1;1;;;;parcelnumber1;some info for customer;1234;;;;;;;5;");
		assertThat(csv.get(1)).isEqualTo("2018-01-08;customerNumber-12345;to company;;DE;54321;Köln;street 1 - street 2;1;030;2018-01-09;09:00;17:30;2;2;;;;parcelnumber2;some info for customer;1234;;;;;;;1;");
	}
}
