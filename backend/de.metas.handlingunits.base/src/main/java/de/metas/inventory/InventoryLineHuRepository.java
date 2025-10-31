package de.metas.inventory;

import de.metas.handlingunits.model.I_M_InventoryLine_HU;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.ICompositeQueryUpdater;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.impl.ModelColumnNameValue;
import org.springframework.stereotype.Repository;

@Repository
public class InventoryLineHuRepository
{
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);

	public void setQtyCountToQtyBookForInventoryLinesHU(@NonNull final InventoryId inventoryId)
	{
		final ICompositeQueryUpdater<I_M_InventoryLine_HU> updaterInventoryLineHU = queryBL.createCompositeQueryUpdater(I_M_InventoryLine_HU.class)
				.addSetColumnFromColumn(I_M_InventoryLine_HU.COLUMNNAME_QtyCount, ModelColumnNameValue.forColumnName(I_M_InventoryLine_HU.COLUMNNAME_QtyBook));

		queryBL.createQueryBuilder(I_M_InventoryLine_HU.class)
				.addEqualsFilter(I_M_InventoryLine_HU.COLUMNNAME_M_Inventory_ID, inventoryId)
				.create().update(updaterInventoryLineHU);
	}
}
