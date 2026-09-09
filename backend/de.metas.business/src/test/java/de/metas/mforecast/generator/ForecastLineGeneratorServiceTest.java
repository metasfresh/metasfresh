package de.metas.mforecast.generator;

import de.metas.mforecast.impl.ForecastId;
import de.metas.organization.OrgId;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.uom.UomId;
import de.metas.util.Services;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.adempiere.warehouse.WarehouseId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.LocalDate;
import com.google.common.collect.ImmutableList;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(AdempiereTestWatcher.class)
class ForecastLineGeneratorServiceTest
{
	private ForecastLineGeneratorRepository repository;
	private ForecastCalculationStrategyFactory strategyFactory;
	private ForecastLineGeneratorService service;

	private static final ForecastId FORECAST_ID = ForecastId.ofRepoId(100);
	private static final OrgId ORG_ID = OrgId.ofRepoId(1000000);
	private static final WarehouseId WAREHOUSE_ID = WarehouseId.ofRepoId(200);
	private static final LocalDate REF_DATE = LocalDate.of(2026, 3, 7);

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();

		// Register IProductBL mock that returns a valid UomId for any product
		final IProductBL productBL = Mockito.mock(IProductBL.class);
		when(productBL.getStockUOMId(any())).thenReturn(UomId.ofRepoId(100));
		Services.registerService(IProductBL.class, productBL);

		repository = Mockito.mock(ForecastLineGeneratorRepository.class);
		strategyFactory = Mockito.mock(ForecastCalculationStrategyFactory.class);
		service = new ForecastLineGeneratorService(repository, strategyFactory);

		when(repository.getById(FORECAST_ID)).thenReturn(GeneratorForecast.builder()
				.id(FORECAST_ID)
				.orgId(ORG_ID)
				.warehouseId(WAREHOUSE_ID)
				.referenceDate(REF_DATE)
				.build());
	}

	@Test
	void noEligibleProducts_returnsZero()
	{
		when(repository.loadEligibleProducts(any(), any(), any(), any()))
				.thenReturn(Collections.emptyList());

		final int count = service.generateLines(FORECAST_ID, defaultRequest());
		assertThat(count).isZero();
		verify(repository, never()).saveForecastLine(any(), any());
	}

	@Test
	void productWithNoMethod_isSkipped()
	{
		when(repository.loadEligibleProducts(any(), any(), any(), any()))
				.thenReturn(ImmutableList.of(createProductPlanning(null)));

		final int count = service.generateLines(FORECAST_ID, defaultRequest());
		assertThat(count).isZero();
	}

	@Test
	void productWithZeroForecastQty_isSkipped()
	{
		when(repository.loadEligibleProducts(any(), any(), any(), any()))
				.thenReturn(ImmutableList.of(createProductPlanning(ForecastCalculationMethod.AVG_52_WEEKS)));

		final ForecastCalculationStrategy strategy = Mockito.mock(ForecastCalculationStrategy.class);
		when(strategy.computeForecastQty(any())).thenReturn(BigDecimal.ZERO);
		when(strategyFactory.getStrategy(any())).thenReturn(strategy);

		final int count = service.generateLines(FORECAST_ID, defaultRequest());
		assertThat(count).isZero();
	}

	@Test
	void productWithPositiveQty_createsLine()
	{
		when(repository.loadEligibleProducts(any(), any(), any(), any()))
				.thenReturn(ImmutableList.of(createProductPlanning(ForecastCalculationMethod.AVG_52_WEEKS)));

		final ForecastCalculationStrategy strategy = Mockito.mock(ForecastCalculationStrategy.class);
		when(strategy.computeForecastQty(any())).thenReturn(new BigDecimal("500"));
		when(strategyFactory.getStrategy(any())).thenReturn(strategy);

		final int count = service.generateLines(FORECAST_ID, defaultRequest());
		assertThat(count).isEqualTo(1);

		final ArgumentCaptor<GeneratedForecastLine> lineCaptor = ArgumentCaptor.forClass(GeneratedForecastLine.class);
		verify(repository).saveForecastLine(eq(FORECAST_ID), lineCaptor.capture());

		final GeneratedForecastLine saved = lineCaptor.getValue();
		assertThat(saved.getQty()).isEqualByComparingTo("500");
		assertThat(saved.getQtyCalculated()).isEqualByComparingTo("500");
		assertThat(saved.getDatePromised()).isEqualTo(REF_DATE);
	}

	@Test
	void deleteExistingLines_calledWhenRequested()
	{
		when(repository.loadEligibleProducts(any(), any(), any(), any()))
				.thenReturn(Collections.emptyList());
		when(repository.deleteExistingLines(FORECAST_ID)).thenReturn(5);

		service.generateLines(FORECAST_ID, ForecastGeneratorRequest.builder()
				.deleteExistingLines(true)
				.build());

		verify(repository).deleteExistingLines(FORECAST_ID);
	}

	@Test
	void deleteExistingLines_notCalledWhenNotRequested()
	{
		when(repository.loadEligibleProducts(any(), any(), any(), any()))
				.thenReturn(Collections.emptyList());

		service.generateLines(FORECAST_ID, defaultRequest());

		verify(repository, never()).deleteExistingLines(any());
	}

	private ForecastGeneratorRequest defaultRequest()
	{
		return ForecastGeneratorRequest.builder().build();
	}

	private ProductPlanningForForecast createProductPlanning(final ForecastCalculationMethod method)
	{
		return ProductPlanningForForecast.builder()
				.productId(ProductId.ofRepoId(1000))
				.warehouseId(WAREHOUSE_ID)
				.attributeSetInstanceId(AttributeSetInstanceId.NONE)
				.forecastCalculationMethod(method)
				.forecastPrecisionUnit(ForecastPrecisionUnit.WEEK)
				.forecastFrequency(8)
				.forecastBufferTime(4)
				.build();
	}
}
