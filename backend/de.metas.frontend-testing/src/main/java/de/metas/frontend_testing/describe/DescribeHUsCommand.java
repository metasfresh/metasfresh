package de.metas.frontend_testing.describe;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.model.I_M_InventoryLine_HU;
import de.metas.handlingunits.storage.IHUProductStorage;
import de.metas.product.IProductBL;
import de.metas.quantity.Quantity;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;

import java.util.Comparator;
import java.util.List;
import java.util.TreeMap;

/**
 * Read-only: describes the given HUs (unit type, top-level HU, storages, included TUs and the LU items they hang on),
 * so that a frontend test can compare HUs whose ids it learns only at runtime (e.g. pallets built by a WebUI action or by completing an inventory).
 */
public class DescribeHUsCommand
{
	@NonNull private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	@NonNull private final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
	@NonNull private final IProductBL productBL = Services.get(IProductBL.class);
	@NonNull private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@NonNull private final JsonDescribeHUsRequest request;

	@Builder
	private DescribeHUsCommand(@NonNull final JsonDescribeHUsRequest request)
	{
		this.request = request;
	}

	public JsonDescribeHUsResponse execute()
	{
		final JsonDescribeHUsResponse.JsonDescribeHUsResponseBuilder response = JsonDescribeHUsResponse.builder();
		for (final Integer huRepoId : request.getHuIds())
		{
			response.hu(describe(HuId.ofRepoId(huRepoId)));
		}

		final Integer inventoryRepoId = request.getInventoryId();
		if (inventoryRepoId != null)
		{
			getInventoryLineHUIds(inventoryRepoId).forEach(huId -> response.hu(describe(huId)));
		}

		return response.build();
	}

	private List<HuId> getInventoryLineHUIds(final int inventoryRepoId)
	{
		return queryBL.createQueryBuilder(I_M_InventoryLine_HU.class)
				.addEqualsFilter(I_M_InventoryLine_HU.COLUMNNAME_M_Inventory_ID, inventoryRepoId)
				.addNotNull(I_M_InventoryLine_HU.COLUMNNAME_M_HU_ID)
				.orderBy(I_M_InventoryLine_HU.COLUMNNAME_M_InventoryLine_HU_ID)
				.create()
				.stream()
				.map(lineHU -> HuId.ofRepoId(lineHU.getM_HU_ID()))
				.distinct()
				.collect(ImmutableList.toImmutableList());
	}

	private JsonDescribeHUsResponse.HU describe(@NonNull final HuId huId)
	{
		if (!handlingUnitsDAO.existsById(huId))
		{
			throw new AdempiereException("No HU found for M_HU_ID=" + huId.getRepoId());
		}
		final I_M_HU hu = handlingUnitsDAO.getById(huId);

		final List<I_M_HU> includedTUs = handlingUnitsDAO.retrieveIncludedHUs(hu)
				.stream()
				.filter(handlingUnitsBL::isTransportUnitOrAggregate)
				.collect(ImmutableList.toImmutableList());

		return JsonDescribeHUsResponse.HU.builder()
				.huId(hu.getM_HU_ID())
				.topLevelHuId(handlingUnitsBL.getTopLevelParent(hu).getM_HU_ID())
				.huUnitType(handlingUnitsBL.getHU_UnitType(hu))
				.storages(getStorages(hu))
				.includedTUsCount(includedTUs.stream()
						.mapToInt(includedHU -> handlingUnitsBL.getTUsCount(includedHU).toInt())
						.sum())
				.tuParentPIItemQtys(includedTUs.stream()
						.map(handlingUnitsDAO::retrieveParentItem)
						.filter(parentItem -> parentItem != null && parentItem.getM_HU_PI_Item_ID() > 0)
						.map(DescribeHUsCommand::getPIItemQty)
						.distinct()
						.sorted()
						.collect(ImmutableList.toImmutableList()))
				.build();
	}

	private ImmutableMap<String, String> getStorages(@NonNull final I_M_HU hu)
	{
		final TreeMap<String, String> storages = new TreeMap<>(Comparator.naturalOrder());
		handlingUnitsBL.getStorageFactory()
				.getStorage(hu)
				.streamProductStorages()
				.forEach(productStorage -> storages.put(
						productBL.getProductValue(productStorage.getProductId()),
						toQtyAndUOMString(productStorage)));
		return ImmutableMap.copyOf(storages);
	}

	private String toQtyAndUOMString(@NonNull final IHUProductStorage productStorage)
	{
		final Quantity qty = productStorage.getQty();
		return qty.toBigDecimal().stripTrailingZeros().toPlainString()
				+ " " + uomDAO.getX12DE355ById(UomId.ofRepoId(qty.getUomId().getRepoId())).getCode();
	}

	private static int getPIItemQty(@NonNull final I_M_HU_Item parentItem)
	{
		return parentItem.getM_HU_PI_Item().getQty().intValueExact();
	}
}
