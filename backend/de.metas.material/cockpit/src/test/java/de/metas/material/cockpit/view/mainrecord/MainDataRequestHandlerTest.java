package de.metas.material.cockpit.view.mainrecord;

import static org.adempiere.model.InterfaceWrapperHelper.isNew;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;

import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.metas.material.cockpit.model.I_MD_Cockpit;
import de.metas.material.cockpit.view.MainDataRecordIdentifier;
import de.metas.material.event.commons.ProductDescriptor;

/*
 * #%L
 * metasfresh-material-cockpit
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

public class MainDataRequestHandlerTest
{

	private static final Instant NOW = Instant.now();

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
	}

	@Test
	public void retrieveOrCreateDataRecord()
	{
		final ProductDescriptor productDescriptor = ProductDescriptor.completeForProductIdAndEmptyAttribute(30);
		final MainDataRecordIdentifier identifier = MainDataRecordIdentifier.builder()
				.date(NOW)
				.productDescriptor(productDescriptor).build();

		final I_MD_Cockpit result = MainDataRequestHandler.retrieveOrCreateDataRecord(identifier);
		assertThat(isNew(result)).isTrue();
		saveRecord(result);

		final I_MD_Cockpit result2 = MainDataRequestHandler.retrieveOrCreateDataRecord(identifier);
		assertThat(isNew(result2)).isFalse();
		assertThat(result2.getMD_Cockpit_ID()).isEqualTo(result.getMD_Cockpit_ID());
	}
}
