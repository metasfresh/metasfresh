package de.metas.uom.impl;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

import java.math.BigDecimal;

import org.adempiere.ad.dao.IQueryBL;
import org.compiere.model.I_C_UOM_Conversion;
import org.slf4j.Logger;

import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableList;

import de.metas.cache.CCache;
import de.metas.logging.LogManager;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.uom.CreateUOMConversionRequest;
import de.metas.uom.IUOMConversionDAO;
import de.metas.uom.UOMConversion;
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
		final UomId productStockingUomId = Services.get(IProductBL.class).getStockingUOMId(productId);

		final ImmutableList<UOMConversion> conversions = Services.get(IQueryBL.class)
				.createQueryBuilderOutOfTrx(I_C_UOM_Conversion.class)
				.addEqualsFilter(I_C_UOM_Conversion.COLUMNNAME_M_Product_ID, productId)
				.addOnlyActiveRecordsFilter()
				.create()
				.stream()
				.map(record -> toUOMConversionOrNull(record))
				.filter(Predicates.notNull())
				.collect(ImmutableList.toImmutableList());

		return UOMConversionsMap.builder()
				.productId(productId)
				.conversions(ImmutableList.<UOMConversion> builder()
						.add(UOMConversion.one(productStockingUomId)) // default conversion
						.addAll(conversions)
						.build())
				.build();
	}

	@Override
	public UOMConversionsMap getGenericConversions()
	{
		final ImmutableList<UOMConversion> conversions = Services.get(IQueryBL.class)
				.createQueryBuilderOutOfTrx(I_C_UOM_Conversion.class)
				.addEqualsFilter(I_C_UOM_Conversion.COLUMNNAME_M_Product_ID, null)
				.addOnlyActiveRecordsFilter()
				.create()
				.stream(I_C_UOM_Conversion.class)
				.map(record -> toUOMConversionOrNull(record))
				.filter(Predicates.notNull())
				.collect(ImmutableList.toImmutableList());

		return UOMConversionsMap.builder()
				.productId(null)
				.conversions(conversions)
				.build();
	}

	private static UOMConversion toUOMConversionOrNull(final I_C_UOM_Conversion record)
	{
		final BigDecimal multiplyRate = record.getMultiplyRate();
		BigDecimal divideRate = record.getDivideRate();
		if (divideRate.signum() == 0 && multiplyRate.signum() != 0)
		{
			// In case divide rate is not available, calculate divide rate as 1/multiplyRate (precision=12)
			divideRate = BigDecimal.ONE.divide(multiplyRate, 12, BigDecimal.ROUND_HALF_UP);
		}

		if (multiplyRate.signum() == 0)
		{
			logger.warn("Invalid conversion {}: multiplyRate is zero", record);
			return null;
		}
		if (divideRate.signum() == 0)
		{
			logger.warn("Invalid conversion {}: divideRate is zero", record);
			return null;
		}

		return UOMConversion.builder()
				.fromUomId(UomId.ofRepoId(record.getC_UOM_ID()))
				.toUomId(UomId.ofRepoId(record.getC_UOM_To_ID()))
				.multiplyRate(multiplyRate)
				.divideRate(divideRate)
				.build();
	}

	@Override
	public void createUOMConversion(@NonNull final CreateUOMConversionRequest request)
	{
		final I_C_UOM_Conversion record = newInstance(I_C_UOM_Conversion.class);

		record.setM_Product_ID(ProductId.toRepoId(request.getProductId()));
		record.setC_UOM_ID(request.getFromUomId().getRepoId());
		record.setC_UOM_To_ID(request.getToUomId().getRepoId());
		record.setMultiplyRate(request.getMultiplyRate());
		record.setDivideRate(request.getDivideRate());

		saveRecord(record);
	}

}
