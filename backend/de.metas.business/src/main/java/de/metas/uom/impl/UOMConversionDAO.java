package de.metas.uom.impl;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

import java.math.BigDecimal;

import org.adempiere.ad.dao.IQueryBL;
import org.compiere.model.I_C_UOM_Conversion;
import org.slf4j.Logger;

import java.util.Objects;
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
import de.metas.util.Services;
import lombok.NonNull;

public class UOMConversionDAO implements IUOMConversionDAO
{
	private static final Logger logger = LogManager.getLogger(UOMConversionDAO.class);

	private final CCache<ProductId, UOMConversionsMap> productConversionsCache = CCache.<ProductId, UOMConversionsMap> builder()
			.tableName(I_C_UOM_Conversion.Table_Name)
			.build();

	@Override
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
				.map(record -> toUOMConversionOrNull(record))
				.filter(Objects::nonNull)
				.collect(ImmutableList.toImmutableList());

		return UOMConversionsMap.builder()
				.productId(productId)
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
				.map(record -> toUOMConversionOrNull(record))
				.filter(Objects::nonNull)
				.collect(ImmutableList.toImmutableList());

		return UOMConversionsMap.builder()
				.productId(null)
				.rates(rates)
				.build();
	}

	private static UOMConversionRate toUOMConversionOrNull(@NonNull final I_C_UOM_Conversion record)
	{
		final BigDecimal fromToMultiplier = record.getMultiplyRate();
		BigDecimal toFromMultiplier = record.getDivideRate();
		if (toFromMultiplier.signum() == 0 && fromToMultiplier.signum() != 0)
		{
			// In case divide rate is not available, calculate divide rate as 1/multiplyRate (precision=12)
			toFromMultiplier = BigDecimal.ONE.divide(fromToMultiplier, 12, BigDecimal.ROUND_HALF_UP);
		}

		if (fromToMultiplier.signum() == 0)
		{
			logger.warn("Invalid conversion {}: multiplyRate is zero", record);
			return null;
		}
		if (toFromMultiplier.signum() == 0)
		{
			logger.warn("Invalid conversion {}: divideRate is zero", record);
			return null;
		}

		return UOMConversionRate.builder()
				.fromUomId(UomId.ofRepoId(record.getC_UOM_ID()))
				.toUomId(UomId.ofRepoId(record.getC_UOM_To_ID()))
				.fromToMultiplier(fromToMultiplier)
				.toFromMultiplier(toFromMultiplier)
				.catchUOMForProduct(record.isCatchUOMForProduct())
				.build();
	}

	@Override
	public void createUOMConversion(@NonNull final CreateUOMConversionRequest request)
	{
		final I_C_UOM_Conversion record = newInstance(I_C_UOM_Conversion.class);

		record.setM_Product_ID(ProductId.toRepoId(request.getProductId()));
		record.setC_UOM_ID(request.getFromUomId().getRepoId());
		record.setC_UOM_To_ID(request.getToUomId().getRepoId());
		record.setMultiplyRate(request.getFromToMultiplier());
		record.setDivideRate(request.getToFromMultiplier());
		record.setIsCatchUOMForProduct(request.isCatchUOMForProduct());

		saveRecord(record);
	}
}
