package de.metas.inoutcandidate.spi;

import static org.assertj.core.api.Assertions.assertThat;

import org.compiere.Adempiere;
import org.compiere.model.I_C_OrderLine;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import de.metas.ShutdownListener;
import de.metas.StartupListener;
import de.metas.inoutcandidate.spi.impl.ShipmentScheduleOrderReferenceProvider;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2017 metas GmbH
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

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {

		// needed to register the spring context with the Adempiere main class
		StartupListener.class, ShutdownListener.class,

		// needed so that the spring context can discover those two components. Note that there are other ways too, but this one is very fast
		ShipmentScheduleReferencedLineFactory.class, ShipmentScheduleOrderReferenceProvider.class
})
public class ShipmentScheduleOrderDocFactoryTest
{
	@Test
	public void factoryAndOrderLineImplCanBeDiscoveredAndConfigured()
	{
		final ShipmentScheduleReferencedLineFactory bean = Adempiere.getBean(ShipmentScheduleReferencedLineFactory.class);

		final ShipmentScheduleReferencedLineProvider providerForOrderLineScheds = bean.getProviderForTableName(I_C_OrderLine.Table_Name);
		assertThat(providerForOrderLineScheds).isNotNull();
		assertThat(providerForOrderLineScheds).isInstanceOf(ShipmentScheduleOrderReferenceProvider.class);
	}
}
