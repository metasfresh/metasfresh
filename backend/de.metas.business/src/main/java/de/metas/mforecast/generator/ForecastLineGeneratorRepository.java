package de.metas.mforecast.generator;

import com.google.common.collect.ImmutableList;
import de.metas.common.util.CoalesceUtil;
import de.metas.material.planning.ProductPlanningBestMatchComparator;
import de.metas.mforecast.IForecastDAO;
import de.metas.mforecast.impl.ForecastId;
import de.metas.organization.OrgId;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_M_Forecast;
import org.compiere.model.I_M_ForecastLine;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Product_Category;
import org.compiere.util.TimeUtil;
import org.eevolution.model.I_PP_Product_Planning;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

@Repository
public class ForecastLineGeneratorRepository
{
	@NonNull private final IForecastDAO forecastDAO = Services.get(IForecastDAO.class);
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);
	@NonNull private final IProductBL productBL = Services.get(IProductBL.class);

	@NonNull
	public GeneratorForecast getById(@NonNull final ForecastId forecastId)
	{
		final I_M_Forecast record = forecastDAO.getById(forecastId);
		return GeneratorForecast.builder()
				.id(forecastId)
				.orgId(OrgId.ofRepoId(record.getAD_Org_ID()))
				.warehouseId(WarehouseId.ofRepoId(record.getM_Warehouse_ID()))
				.referenceDate(TimeUtil.asLocalDate(record.getDatePromised()))
				.build();
	}

	public int deleteExistingLines(@NonNull final ForecastId forecastId)
	{
		final List<I_M_ForecastLine> existingLines = forecastDAO.retrieveLinesByForecastId(forecastId);
		for (final I_M_ForecastLine line : existingLines)
		{
			InterfaceWrapperHelper.delete(line);
		}
		return existingLines.size();
	}

	public void saveForecastLine(@NonNull final ForecastId forecastId, @NonNull final GeneratedForecastLine line)
	{
		final I_M_ForecastLine record = InterfaceWrapperHelper.newInstance(I_M_ForecastLine.class);
		record.setM_Forecast_ID(forecastId.getRepoId());
		record.setM_Product_ID(line.getProductId().getRepoId());
		record.setM_Warehouse_ID(line.getWarehouseId().getRepoId());
		record.setM_AttributeSetInstance_ID(line.getAttributeSetInstanceId().getRepoId());
		record.setC_UOM_ID(line.getUomId().getRepoId());
		record.setQty(line.getQty());
		record.setQtyCalculated(line.getQtyCalculated());
		record.setDatePromised(TimeUtil.asTimestamp(line.getDatePromised()));
		saveRecord(record);
	}

	/**
	 * Loads all eligible products for forecast generation in a single query.
	 * Fixes the N+1 problem (previously queried PP_Product_Planning per product)
	 * and the warehouse bug (previously ignored the forecast's warehouse).
	 * <p>
	 * Filtering (done in SQL):
	 * - PP_Product_Planning: active, matching org (or wildcard), matching warehouse (or null),
	 *   not excluded from forecast, has product assigned
	 * - Optional: product filter from request
	 * <p>
	 * Filtering (done in memory):
	 * - M_Product: not discontinued at referenceDate
	 * - M_Product_Category: not excluded from forecast
	 * - Optional: product category filter from request
	 * <p>
	 * Best-match selection (done in memory):
	 * Groups results by productId, picks the best match per group using
	 * {@link ProductPlanningBestMatchComparator}.
	 */
	@NonNull
	public List<ProductPlanningForForecast> loadEligibleProducts(
			@NonNull final OrgId orgId,
			@NonNull final ForecastGeneratorRequest request,
			@NonNull final LocalDate referenceDate,
			@NonNull final WarehouseId forecastWarehouseId)
	{
		// Single query: load all matching PP_Product_Planning records
		final IQueryBuilder<I_PP_Product_Planning> queryBuilder = queryBL.createQueryBuilder(I_PP_Product_Planning.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_PP_Product_Planning.COLUMNNAME_AD_Org_ID, orgId, OrgId.ANY, null)
				.addInArrayFilter(I_PP_Product_Planning.COLUMNNAME_M_Warehouse_ID, forecastWarehouseId, null)
				.addEqualsFilter(I_PP_Product_Planning.COLUMNNAME_IsExcludeFromForecast, false)
				.addNotNull(I_PP_Product_Planning.COLUMNNAME_M_Product_ID);

		if (request.getProductId() != null)
		{
			queryBuilder.addEqualsFilter(I_PP_Product_Planning.COLUMNNAME_M_Product_ID, request.getProductId());
		}

		final List<I_PP_Product_Planning> allRecords = queryBuilder.create().list();

		// Group by product, pick best match per product, filter by product-level criteria
		final Map<Integer, List<I_PP_Product_Planning>> byProduct = allRecords.stream()
				.collect(Collectors.groupingBy(I_PP_Product_Planning::getM_Product_ID));

		final ImmutableList.Builder<ProductPlanningForForecast> result = ImmutableList.builder();
		for (final List<I_PP_Product_Planning> group : byProduct.values())
		{
			final I_PP_Product_Planning bestMatch = group.stream()
					.min(ProductPlanningBestMatchComparator.INSTANCE)
					.orElseThrow(() -> new IllegalStateException("No records in group"));

			final ProductId productId = ProductId.ofRepoId(bestMatch.getM_Product_ID());
			final I_M_Product product = InterfaceWrapperHelper.load(productId, I_M_Product.class);

			// Filter: discontinued products
			if (productBL.isDiscontinuedAt(product, referenceDate))
			{
				continue;
			}

			// Filter: product category exclusion
			if (isProductCategoryExcluded(product))
			{
				continue;
			}

			// Filter: product category from request
			if (request.getProductCategoryId() != null
					&& product.getM_Product_Category_ID() != request.getProductCategoryId().getRepoId())
			{
				continue;
			}

			final WarehouseId warehouseId = CoalesceUtil.coalesce(
					WarehouseId.ofRepoIdOrNull(bestMatch.getM_Warehouse_ID()),
					forecastWarehouseId);

			result.add(ProductPlanningForForecast.builder()
					.productId(productId)
					.warehouseId(warehouseId)
					.attributeSetInstanceId(CoalesceUtil.coalesceNotNull(
							AttributeSetInstanceId.ofRepoIdOrNone(bestMatch.getM_AttributeSetInstance_ID()),
							AttributeSetInstanceId.NONE))
					.forecastCalculationMethod(ForecastCalculationMethod.ofNullableCode(bestMatch.getForecast_CalculationMethod()))
					.forecastPrecisionUnit(ForecastPrecisionUnit.ofNullableCode(bestMatch.getForecast_PrecisionUnit()))
					.forecastFrequency(bestMatch.getForecast_Frequency())
					.forecastBufferTime(bestMatch.getForecast_BufferTime())
					.deliveryTimePromised(bestMatch.getDeliveryTime_Promised() != null && bestMatch.getDeliveryTime_Promised().signum() > 0
							? bestMatch.getDeliveryTime_Promised()
							: null)
					.build());
		}

		return result.build();
	}

	private boolean isProductCategoryExcluded(@NonNull final I_M_Product product)
	{
		final I_M_Product_Category category = InterfaceWrapperHelper.load(
				product.getM_Product_Category_ID(), I_M_Product_Category.class);
		return category != null && category.isExcludeFromForecast();
	}
}
