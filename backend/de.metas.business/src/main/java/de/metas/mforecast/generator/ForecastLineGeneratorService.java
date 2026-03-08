package de.metas.mforecast.generator;

import com.google.common.collect.ImmutableList;
import de.metas.common.util.CoalesceUtil;
import de.metas.material.planning.IProductPlanningDAO;
import de.metas.material.planning.ProductPlanning;
import de.metas.mforecast.IForecastDAO;
import de.metas.mforecast.impl.ForecastId;
import de.metas.organization.OrgId;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_Forecast;
import org.compiere.model.I_M_ForecastLine;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Product_Category;
import org.compiere.util.TimeUtil;
import org.eevolution.model.I_PP_Product_Planning;
import org.springframework.stereotype.Service;


import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

@Service
public class ForecastLineGeneratorService
{
	@NonNull private final ForecastCalculationStrategyFactory strategyFactory;
	@NonNull private final IForecastDAO forecastDAO = Services.get(IForecastDAO.class);
	@NonNull private final IProductPlanningDAO productPlanningDAO = Services.get(IProductPlanningDAO.class);
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);
	@NonNull private final IProductBL productBL = Services.get(IProductBL.class);

	public ForecastLineGeneratorService(@NonNull final ForecastCalculationStrategyFactory strategyFactory)
	{
		this.strategyFactory = strategyFactory;
	}

	public int generateLines(@NonNull final ForecastId forecastId, @NonNull final ForecastGeneratorRequest request)
	{
		final I_M_Forecast forecast = forecastDAO.getById(forecastId);

		if (request.isDeleteExistingLines())
		{
			deleteExistingLines(forecastId);
		}

		final OrgId orgId = OrgId.ofRepoId(forecast.getAD_Org_ID());
		final LocalDate referenceDate = TimeUtil.asLocalDate(forecast.getDatePromised());
		final List<ProductPlanningForForecast> products = loadEligibleProducts(forecast, request, referenceDate);

		int count = 0;
		for (final ProductPlanningForForecast pp : products)
		{
			final ForecastCalculationMethod method = request.getCalculationMethodOverride()
					.orElse(pp.getForecastCalculationMethod());

			if (method == null)
			{
				continue; // no comparison method configured, skip
			}

			final ForecastPrecisionUnit precisionUnit = CoalesceUtil.coalesce(pp.getForecastPrecisionUnit(), ForecastPrecisionUnit.WEEK);

			final int horizonUnits = computeForecastHorizon(pp, precisionUnit);
			if (horizonUnits <= 0)
			{
				continue;
			}

			final ForecastCalculationStrategy strategy = strategyFactory.getStrategy(method);

			final ForecastCalculationContext ctx = ForecastCalculationContext.builder()
					.productId(pp.getProductId())
					.referenceDate(referenceDate)
					.precisionUnit(precisionUnit)
					.forecastHorizonInPrecisionUnits(horizonUnits)
					.orgId(orgId)
					.build();

			final BigDecimal forecastQty = strategy.computeForecastQty(ctx);

			if (forecastQty.signum() > 0)
			{
				createForecastLine(forecast, pp, forecastQty);
				count++;
			}
		}
		return count;
	}

	private void deleteExistingLines(@NonNull final ForecastId forecastId)
	{
		final List<I_M_ForecastLine> existingLines = forecastDAO.retrieveLinesByForecastId(forecastId);
		for (final I_M_ForecastLine line : existingLines)
		{
			InterfaceWrapperHelper.delete(line);
		}
	}

	private List<ProductPlanningForForecast> loadEligibleProducts(
			@NonNull final I_M_Forecast forecast,
			@NonNull final ForecastGeneratorRequest request,
			@NonNull final LocalDate referenceDate)
	{
		final ImmutableList.Builder<ProductPlanningForForecast> result = ImmutableList.builder();
		final OrgId orgId = OrgId.ofRepoId(forecast.getAD_Org_ID());

		// Collect distinct product IDs that have forecast-eligible PP_Product_Planning records
		final List<ProductId> productIds = collectEligibleProductIds(orgId, request, referenceDate);

		for (final ProductId productId : productIds)
		{
			// Use the standard best-match logic (lowest SeqNo, ASI matching, org/warehouse fallback)
			final Optional<ProductPlanning> bestMatch = productPlanningDAO.find(
					IProductPlanningDAO.ProductPlanningQuery.builder()
							.orgId(orgId)
							.productId(productId)
							.build());

			if (!bestMatch.isPresent())
			{
				continue;
			}

			final ProductPlanning pp = bestMatch.get();

			if (pp.isExcludeFromForecast())
			{
				continue;
			}

			final WarehouseId warehouseId = CoalesceUtil.coalesce(
					pp.getWarehouseId(),
					WarehouseId.ofRepoIdOrNull(forecast.getM_Warehouse_ID()));
			if (warehouseId == null)
			{
				continue;
			}

			result.add(ProductPlanningForForecast.builder()
					.productId(productId)
					.warehouseId(warehouseId)
					.attributeSetInstanceId(CoalesceUtil.coalesce(pp.getAttributeSetInstanceId(), AttributeSetInstanceId.NONE))
					.forecastCalculationMethod(pp.getForecastCalculationMethod())
					.forecastPrecisionUnit(pp.getForecastPrecisionUnit())
					.forecastFrequency(pp.getForecastFrequency())
					.forecastBufferTime(pp.getForecastBufferTime())
					.deliveryTimePromised(pp.getLeadTimeDays() > 0 ? BigDecimal.valueOf(pp.getLeadTimeDays()) : null)
					.build());
		}

		return result.build();
	}

	private List<ProductId> collectEligibleProductIds(
			@NonNull final OrgId orgId,
			@NonNull final ForecastGeneratorRequest request,
			@NonNull final LocalDate referenceDate)
	{
		final IQueryBuilder<I_PP_Product_Planning> queryBuilder = queryBL.createQueryBuilder(I_PP_Product_Planning.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_PP_Product_Planning.COLUMNNAME_AD_Org_ID, orgId)
				.addEqualsFilter(I_PP_Product_Planning.COLUMNNAME_IsExcludeFromForecast, false);

		if (request.getProductId() != null)
		{
			queryBuilder.addEqualsFilter(I_PP_Product_Planning.COLUMNNAME_M_Product_ID, request.getProductId());
		}

		final List<Integer> productRepoIds = queryBuilder.create()
				.listDistinct(I_PP_Product_Planning.COLUMNNAME_M_Product_ID, Integer.class);

		final ImmutableList.Builder<ProductId> result = ImmutableList.builder();
		for (final int productRepoId : productRepoIds)
		{
			final ProductId productId = ProductId.ofRepoId(productRepoId);
			final I_M_Product product = InterfaceWrapperHelper.load(productId, I_M_Product.class);

			if (productBL.isDiscontinuedAt(product, referenceDate))
			{
				continue;
			}

			if (request.getProductCategoryId() != null
					&& product.getM_Product_Category_ID() != request.getProductCategoryId().getRepoId())
			{
				continue;
			}

			if (isProductCategoryExcluded(product))
			{
				continue;
			}

			result.add(productId);
		}

		return result.build();
	}

	private boolean isProductCategoryExcluded(@NonNull final I_M_Product product)
	{
		final I_M_Product_Category category = InterfaceWrapperHelper.load(product.getM_Product_Category_ID(), I_M_Product_Category.class);
		if (category == null)
		{
			return false;
		}
		return category.isExcludeFromForecast();
	}

	@Nullable
	private static Integer extractForecastIntOrNull(@Nullable final BigDecimal value)
	{
		if (value == null || value.signum() <= 0)
		{
			return null;
		}
		return value.intValue();
	}

	private int computeForecastHorizon(
			@NonNull final ProductPlanningForForecast pp,
			@NonNull final ForecastPrecisionUnit precisionUnit)
	{
		// Use forecast frequency if set, else derive from lead time
		int frequency;
		if (pp.getForecastFrequency() != null && pp.getForecastFrequency() > 0)
		{
			frequency = pp.getForecastFrequency();
		}
		else if (pp.getDeliveryTimePromised() != null && pp.getDeliveryTimePromised().signum() > 0)
		{
			// Convert lead time (days) to precision units
			frequency = daysToUnits(pp.getDeliveryTimePromised().intValue(), precisionUnit);
		}
		else
		{
			frequency = 1; // default minimum
		}

		final int buffer = pp.getForecastBufferTime() != null ? pp.getForecastBufferTime() : 0;

		return frequency + buffer;
	}

	private static int daysToUnits(final int days, @NonNull final ForecastPrecisionUnit unit)
	{
		switch (unit)
		{
			case WEEK:
				return Math.max(1, (int) Math.ceil(days / 7.0));
			case MONTH:
				return Math.max(1, (int) Math.ceil(days / 30.0));
			default:
				throw new AdempiereException("Unknown precision unit: " + unit);
		}
	}

	private void createForecastLine(
			@NonNull final I_M_Forecast forecast,
			@NonNull final ProductPlanningForForecast pp,
			@NonNull final BigDecimal qty)
	{
		final I_M_ForecastLine line = InterfaceWrapperHelper.newInstance(I_M_ForecastLine.class);
		line.setM_Forecast_ID(forecast.getM_Forecast_ID());
		line.setM_Product_ID(pp.getProductId().getRepoId());
		line.setM_Warehouse_ID(pp.getWarehouseId().getRepoId());
		line.setM_AttributeSetInstance_ID(pp.getAttributeSetInstanceId().getRepoId());
		line.setQty(qty);
		line.setQtyCalculated(qty);
		line.setDatePromised(forecast.getDatePromised());

		final UomId stockUomId = productBL.getStockUOMId(pp.getProductId());
		line.setC_UOM_ID(stockUomId.getRepoId());

		saveRecord(line);
	}
}
