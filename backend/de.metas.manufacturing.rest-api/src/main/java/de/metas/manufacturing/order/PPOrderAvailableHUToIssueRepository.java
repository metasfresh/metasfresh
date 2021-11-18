package de.metas.manufacturing.order;

import de.metas.handlingunits.HuId;
import de.metas.handlingunits.model.I_PP_Order_AvailableHUToIssue;
import de.metas.product.ProductId;
import de.metas.quantity.Quantitys;
import de.metas.uom.UomId;
import de.metas.util.GuavaCollectors;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.warehouse.LocatorId;
import org.eevolution.api.PPOrderId;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.stream.Stream;

@Repository
public class PPOrderAvailableHUToIssueRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	public PPOrderAvailableHUToIssueMap getByOrderId(@NonNull final PPOrderId ppOrderId)
	{
		return streamRecordsByPPOrderId(ppOrderId)
				.map(PPOrderAvailableHUToIssueRepository::toPPOrderAvailableHUToIssue)
				.collect(PPOrderAvailableHUToIssueMap.collect());
	}

	private Stream<I_PP_Order_AvailableHUToIssue> streamRecordsByPPOrderId(final @NonNull PPOrderId ppOrderId)
	{
		return queryBL.createQueryBuilder(I_PP_Order_AvailableHUToIssue.class)
				.addEqualsFilter(I_PP_Order_AvailableHUToIssue.COLUMNNAME_PP_Order_ID, ppOrderId)
				.addOnlyActiveRecordsFilter()
				.create()
				.stream();
	}

	public void save(@NonNull PPOrderAvailableHUToIssueMap map, @NonNull PPOrderId ppOrderId)
	{
		final HashMap<PPOrderAvailableHUToIssueKey, I_PP_Order_AvailableHUToIssue> existingRecords = streamRecordsByPPOrderId(ppOrderId)
				.collect(GuavaCollectors.toHashMapByKey(PPOrderAvailableHUToIssueRepository::extractKey));

		for (final PPOrderAvailableHUToIssue item : map.toList())
		{
			final PPOrderAvailableHUToIssueKey key = PPOrderAvailableHUToIssueKey.of(item.getHuId(), item.getProductId());
			I_PP_Order_AvailableHUToIssue record = existingRecords.remove(key);
			if (record == null)
			{
				record = InterfaceWrapperHelper.newInstance(I_PP_Order_AvailableHUToIssue.class);
				record.setPP_Order_ID(ppOrderId.getRepoId());
				record.setM_Product_ID(item.getProductId().getRepoId());
				record.setM_HU_ID(item.getHuId().getRepoId());
				record.setM_Warehouse_ID(item.getLocatorId().getWarehouseId().getRepoId());
				record.setM_Locator_ID(item.getLocatorId().getRepoId());
			}

			record.setC_UOM_ID(item.getAvailableQty().getUomId().getRepoId());
			record.setQtyAvailable(item.getAvailableQty().toBigDecimal());

			InterfaceWrapperHelper.save(record);
		}

		InterfaceWrapperHelper.deleteAll(existingRecords.values());
	}

	private static PPOrderAvailableHUToIssue toPPOrderAvailableHUToIssue(@NonNull final I_PP_Order_AvailableHUToIssue record)
	{
		return PPOrderAvailableHUToIssue.builder()
				.locatorId(LocatorId.ofRepoId(record.getM_Warehouse_ID(), record.getM_Locator_ID()))
				.huId(HuId.ofRepoId(record.getM_HU_ID()))
				.productId(ProductId.ofRepoId(record.getM_Product_ID()))
				.availableQty(Quantitys.create(record.getQtyAvailable(), UomId.ofRepoId(record.getC_UOM_ID())))
				.build();
	}

	private static PPOrderAvailableHUToIssueKey extractKey(final I_PP_Order_AvailableHUToIssue record)
	{
		return PPOrderAvailableHUToIssueKey.of(
				HuId.ofRepoId(record.getM_HU_ID()),
				ProductId.ofRepoId(record.getM_Product_ID()));
	}
}
