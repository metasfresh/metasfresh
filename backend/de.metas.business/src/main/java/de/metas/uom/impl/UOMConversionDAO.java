package de.metas.uom.impl;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import de.metas.cache.CCache;
import de.metas.logging.LogManager;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.uom.CreateUOMConversionRequest;
import de.metas.uom.IUOMConversionDAO;
import de.metas.uom.UOMConversionRate;
import de.metas.uom.UOMConversionsMap;
import de.metas.uom.UomId;
import de.metas.uom.UpdateUOMConversionRequest;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.compiere.model.I_C_UOM_Conversion;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.Objects;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

public class UOMConversionDAO implements IUOMConversionDAO
{
	private static final Logger logger = LogManager.getLogger(UOMConversionDAO.class);

	private final CCache<ProductId, UOMConversionsMap> productConversionsCache = CCache.<ProductId, UOMConversionsMap> builder()
			.tableName(I_C_UOM_Conversion.Table_Name)
			.build();

	@Override
	@NonNull
	public UOMConversionsMap getProductConversions(@NonNull final ProductId productId)
	{
		return productConversionsCache.getOrLoad(productId, this::retrieveProductConversions);
	}

	private UOMConversionsMap retrieveProductConversions(@NonNull final ProductId productId)
	{
		final UomId productStockingUomId = Services.get(IProductBL.class).getStockUOMId(productId);

		final ImmutableList<UOMConversionRate> rates = Services.get(IQueryBL.class)
				.createQueryBuilderOutOfTrx(I_C_UOM_Conversion.class)
				.addEqualsFilter(I_C_UOM_Conversion.COLUMNNAME_M_Product_ID, productId)
				.addOnlyActiveRecordsFilter()
				.create()
				.stream()
				.map(UOMConversionDAO::toUOMConversionOrNull)
				.filter(Objects::nonNull)
				.collect(ImmutableList.toImmutableList());

		final boolean hasRatesForNonStockingUOMs = !rates.isEmpty();
		
		return UOMConversionsMap.builder()
				.productId(productId)
				.hasRatesForNonStockingUOMs(hasRatesForNonStockingUOMs)
				.rates(ImmutableList.<UOMConversionRate> builder()
						.add(UOMConversionRate.one(productStockingUomId)) // default conversion
						.addAll(rates)
						.build())
				.build();
	}

	@Override
	public UOMConversionsMap getGenericConversions()
	{
		final ImmutableList<UOMConversionRate> rates = Services.get(IQueryBL.class)
				.createQueryBuilderOutOfTrx(I_C_UOM_Conversion.class)
				.addEqualsFilter(I_C_UOM_Conversion.COLUMNNAME_M_Product_ID, null)
				.addOnlyActiveRecordsFilter()
				.create()
				.stream(I_C_UOM_Conversion.class)
				.map(UOMConversionDAO::toUOMConversionOrNull)
				.filter(Objects::nonNull)
				.collect(ImmutableList.toImmutableList());

		return UOMConversionsMap.builder()
				.productId(null)
				.rates(rates)
				.build();
	}

	@Nullable
	@VisibleForTesting
	static UOMConversionRate toUOMConversionOrNull(@NonNull final I_C_UOM_Conversion record)
	{
		final BigDecimal fromToMultiplier = record.getMultiplyRate();
		if (fromToMultiplier.signum() == 0)
		{
			logger.warn("Invalid conversion {}: multiplyRate is zero", record);
			return null;
		}

		// NOTE: Don't use C_UOM_Conversion.DivideRate because that's always calculated as 1/MultiplyRate, rounded HALF_UP at 12 decimals.
		// This approach can introduce calculation errors in some cases, so better let the API calculate it when needed, using the correct precision.

		return UOMConversionRate.builder()
				.fromUomId(UomId.ofRepoId(record.getC_UOM_ID()))
				.toUomId(UomId.ofRepoId(record.getC_UOM_To_ID()))
				.fromToMultiplier(fromToMultiplier)
				.catchUOMForProduct(record.isCatchUOMForProduct())
				.build();
	}

	@Override
	public void createUOMConversion(@NonNull final CreateUOMConversionRequest request)
	{
		final BigDecimal fromToMultiplier = request.getFromToMultiplier();
		final BigDecimal toFromMultiplier = UOMConversionRate.computeInvertedMultiplier(fromToMultiplier);

		final I_C_UOM_Conversion record = newInstance(I_C_UOM_Conversion.class);

		record.setM_Product_ID(ProductId.toRepoId(request.getProductId()));
		record.setC_UOM_ID(request.getFromUomId().getRepoId());
		record.setC_UOM_To_ID(request.getToUomId().getRepoId());
		record.setMultiplyRate(fromToMultiplier);
		record.setDivideRate(toFromMultiplier);
		record.setIsCatchUOMForProduct(request.isCatchUOMForProduct());

		saveRecord(record);
	}

	@Override
	public void updateUOMConversion(@NonNull final UpdateUOMConversionRequest request)
	{
		final I_C_UOM_Conversion record = Services.get(IQueryBL.class).createQueryBuilder(I_C_UOM_Conversion.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_UOM_Conversion.COLUMNNAME_M_Product_ID, request.getProductId())
				.addEqualsFilter(I_C_UOM_Conversion.COLUMNNAME_C_UOM_ID, request.getFromUomId())
				.addEqualsFilter(I_C_UOM_Conversion.COLUMNNAME_C_UOM_To_ID, request.getToUomId())
				.create()
				.firstOnlyNotNull(I_C_UOM_Conversion.class);// we have a unique-constraint

		final BigDecimal fromToMultiplier = request.getFromToMultiplier();
		final BigDecimal toFromMultiplier = UOMConversionRate.computeInvertedMultiplier(fromToMultiplier);
		
		record.setMultiplyRate(fromToMultiplier);
		record.setDivideRate(toFromMultiplier);
		record.setIsCatchUOMForProduct(request.isCatchUOMForProduct());

		saveRecord(record);
	}
}
