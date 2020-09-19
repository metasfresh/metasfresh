package de.metas.material.interceptor;

import com.google.common.collect.ImmutableList;
import de.metas.adempiere.model.I_M_Product;
import de.metas.bpartner.BPartnerId;
import de.metas.document.engine.IDocument;
import de.metas.material.event.ModelProductDescriptorExtractor;
import de.metas.material.event.commons.MaterialDescriptor;
import de.metas.material.event.forecast.Forecast;
import de.metas.material.event.forecast.ForecastCreatedEvent;
import de.metas.material.event.forecast.ForecastLine;
import org.adempiere.ad.modelvalidator.DocTimingType;
import org.adempiere.mm.attributes.api.impl.ModelProductDescriptorExtractorUsingAttributeSetInstanceFactory;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.compiere.model.I_M_Forecast;
import org.compiere.model.I_M_ForecastLine;
import org.compiere.model.I_M_Warehouse;
import org.compiere.util.TimeUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.math.BigDecimal;
import java.util.List;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;
import static org.compiere.util.TimeUtil.asInstant;

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
@ExtendWith(AdempiereTestWatcher.class)
public class M_ForecastPostMaterialEventEventCreatorTest
{
	private static final BPartnerId BPARTNER_ID_OF_FORECAST = BPartnerId.ofRepoId(50);
	private static final BPartnerId BPARTNER_ID_OF_FIRST_FORECAST_LINE = BPartnerId.ofRepoId(51);

	private M_ForecastEventCreator forecastEventCreator;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();

		final ModelProductDescriptorExtractor productDescriptorFactory = new ModelProductDescriptorExtractorUsingAttributeSetInstanceFactory();
		forecastEventCreator = new M_ForecastEventCreator(productDescriptorFactory);
	}

	@Test
	public void createEventWithLinesAndTiming()
	{
		final I_M_Forecast forecastModel = newInstance(I_M_Forecast.class);
		forecastModel.setDocStatus(IDocument.STATUS_NotApproved); //
		forecastModel.setC_BPartner_ID(BPARTNER_ID_OF_FORECAST.getRepoId());
		save(forecastModel);

		final I_M_ForecastLine forecastLineRecord1;
		{
			final I_M_Warehouse warehouse1 = newInstance(I_M_Warehouse.class);
			save(warehouse1);
			final I_M_Product product1 = newInstance(I_M_Product.class);
			save(product1);

			forecastLineRecord1 = newInstance(I_M_ForecastLine.class);
			forecastLineRecord1.setM_Forecast(forecastModel);
			forecastLineRecord1.setC_BPartner_ID(BPARTNER_ID_OF_FIRST_FORECAST_LINE.getRepoId());
			forecastLineRecord1.setDatePromised(TimeUtil.parseTimestamp("2017-10-21"));
			forecastLineRecord1.setQty(new BigDecimal("21"));
			forecastLineRecord1.setM_Product(product1);
			forecastLineRecord1.setM_Warehouse(warehouse1);
			save(forecastLineRecord1);
		}

		final I_M_ForecastLine forecastLineRecord2;
		{
			final I_M_Warehouse warehouse2 = newInstance(I_M_Warehouse.class);
			save(warehouse2);
			final I_M_Product product2 = newInstance(I_M_Product.class);
			save(product2);
			forecastLineRecord2 = newInstance(I_M_ForecastLine.class);
			forecastLineRecord2.setM_Forecast(forecastModel);
			forecastLineRecord2.setDatePromised(TimeUtil.parseTimestamp("2017-10-22"));
			forecastLineRecord2.setQty(new BigDecimal("22"));
			forecastLineRecord2.setM_Product(product2);
			forecastLineRecord2.setM_Warehouse(warehouse2);
			save(forecastLineRecord2);
		}

		final ForecastCreatedEvent result = forecastEventCreator.createEventWithLinesAndTiming(
				ImmutableList.of(forecastLineRecord1, forecastLineRecord2),
				DocTimingType.AFTER_COMPLETE);
		assertThat(result).isNotNull();

		final Forecast forecast = result.getForecast();
		assertThat(forecast).isNotNull();
		assertThat(forecast.getDocStatus()).isEqualTo(IDocument.STATUS_Completed);
		assertThat(forecast.getForecastId()).isEqualTo(forecastModel.getM_Forecast_ID());

		final List<ForecastLine> forecastLines = forecast.getForecastLines();
		assertThat(forecastLines).hasSize(2);

		assertThat(forecastLines).anySatisfy(forecastLine -> {
			verifyEventPojoIsInSyncWithRecord(forecastLine, forecastLineRecord1);
			assertThat(forecastLine.getMaterialDescriptor().getCustomerId()).isEqualTo(BPARTNER_ID_OF_FIRST_FORECAST_LINE);
		});

		assertThat(forecastLines).anySatisfy(forecastLine -> {
			verifyEventPojoIsInSyncWithRecord(forecastLine, forecastLineRecord2);
			assertThat(forecastLine.getMaterialDescriptor().getCustomerId())
					.as("The 2nd focrecastLine has no own C_BPartner_ID, the header's ID shall be inherited")
					.isEqualTo(BPARTNER_ID_OF_FORECAST);
		});

	}

	private void verifyEventPojoIsInSyncWithRecord(
			final ForecastLine forecastLineEventPojo,
			final I_M_ForecastLine forecastLineRecord)
	{
		assertThat(forecastLineEventPojo.getForecastLineId()).isEqualTo(forecastLineRecord.getM_ForecastLine_ID());
		final MaterialDescriptor materialDescriptor = forecastLineEventPojo.getMaterialDescriptor();

		assertThat(materialDescriptor.getDate()).isEqualTo(asInstant(forecastLineRecord.getDatePromised()));
		assertThat(materialDescriptor.getProductId()).isEqualTo(forecastLineRecord.getM_Product_ID());
		assertThat(materialDescriptor.getQuantity()).isEqualTo(forecastLineRecord.getQty());
		assertThat(materialDescriptor.getWarehouseId().getRepoId()).isEqualTo(forecastLineRecord.getM_Warehouse_ID());
	}

}
