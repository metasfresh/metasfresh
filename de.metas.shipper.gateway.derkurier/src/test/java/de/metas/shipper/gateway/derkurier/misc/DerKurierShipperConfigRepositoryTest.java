package de.metas.shipper.gateway.derkurier.misc;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;

import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_AD_Sequence;
import org.junit.Before;
import org.junit.Test;

import de.metas.shipper.gateway.derkurier.model.I_DerKurier_Shipper_Config;

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

public class DerKurierShipperConfigRepositoryTest
{
	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
	}

	@Test
	public void retrieveConfigForShipperId()
	{
		final I_AD_Sequence sequenceRecord = newInstance(I_AD_Sequence.class);
		sequenceRecord.setName("mysequencename");
		save(sequenceRecord);

		final I_DerKurier_Shipper_Config configRecord = newInstance(I_DerKurier_Shipper_Config.class);
		configRecord.setDK_CustomerNumber("1234");
		configRecord.setAD_Sequence(sequenceRecord);
		configRecord.setAPIServerBaseURL("https://testurl");
		configRecord.setM_Shipper_ID(20);
		save(configRecord);

		final DerKurierShipperConfig config = new DerKurierShipperConfigRepository().retrieveConfigForShipperId(20);
		assertThat(config.getCustomerNumber()).isEqualTo("1234");
		assertThat(config.getRestApiBaseUrl()).isEqualTo("https://testurl");

		final ParcelNumberGenerator parcelNumberGenerator = config.getParcelNumberGenerator();
		assertThat(parcelNumberGenerator.getAdSequenceId()).isEqualTo(sequenceRecord.getAD_Sequence_ID());
	}

}
