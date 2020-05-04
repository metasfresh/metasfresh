package de.metas.inoutcandidate.spi;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_M_Attribute;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.google.common.collect.ImmutableList;

import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.inoutcandidate.spi.ShipmentScheduleHandler.AttributeConfig;
import de.metas.inoutcandidate.spi.ShipmentScheduleHandler.AttributeConfig.AttributeConfigBuilder;

/*
 * #%L
 * de.metas.swat.base
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

public class ShipmentScheduleHandlerTest
{
	private ShipmentScheduleHandler shipmentScheduleHandler;

	private I_M_Attribute attribute;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();

		attribute = newInstance(I_M_Attribute.class);
		save(attribute);

		shipmentScheduleHandler = Mockito.spy(ShipmentScheduleHandler.class);
	}

	@Test
	public void attributeShallBePartOfShipmentLine_when_empty_config_list_then_false()
	{
		final I_M_ShipmentSchedule shipmentSchedule = newInstance(I_M_ShipmentSchedule.class);

		Mockito.when(shipmentScheduleHandler.getAttributeConfigs())
				.thenReturn(ImmutableList.of());

		final boolean result = shipmentScheduleHandler.attributeShallBePartOfShipmentLine(shipmentSchedule, attribute);
		assertThat(result).isFalse();
	}

	@Test
	public void findMatchingAttributeConfig_attribute_precedes_wildcard()
	{
		final AttributeConfig emptyWildcardConfig = AttributeConfig.builder().build();
		final AttributeConfig attributeConfig = AttributeConfig.builder()
				.attributeId(AttributeId.ofRepoId(attribute.getM_Attribute_ID()))
				.build();

		assertConfigReturnedForList(attributeConfig, ImmutableList.of(emptyWildcardConfig, attributeConfig));
		assertConfigReturnedForList(attributeConfig, ImmutableList.of(attributeConfig, emptyWildcardConfig));
	}

	@Test
	public void findMatchingAttributeConfig_specific_org_precedes_null_org()
	{
		final AttributeConfigBuilder builder = AttributeConfig.builder()
				.attributeId(AttributeId.ofRepoId(attribute.getM_Attribute_ID()));

		final AttributeConfig starOrgConfig = builder.orgId(0).build();
		final AttributeConfig specificOrgConfig = builder.orgId(10).build();

		assertConfigReturnedForList(specificOrgConfig, ImmutableList.of(starOrgConfig, specificOrgConfig));
	}

	private void assertConfigReturnedForList(
			final AttributeConfig attributeConfigConfig,
			final ImmutableList<AttributeConfig> list)
	{
		Mockito.when(shipmentScheduleHandler.getAttributeConfigs())
				.thenReturn(list);

		// invoke the method under test
		final Optional<AttributeConfig> matchingAttributeConfig = shipmentScheduleHandler.findMatchingAttributeConfig(10, attribute);

		assertThat(matchingAttributeConfig).isPresent();
		assertThat(matchingAttributeConfig.get()).isSameAs(attributeConfigConfig);
	}

}
