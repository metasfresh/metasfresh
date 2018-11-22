/**
 *
 */
package de.metas.handlingunits.inout.impl;

import javax.annotation.Nullable;

import java.util.Collections;
import java.util.List;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.impl.EqualsQueryFilter;
import org.compiere.model.I_M_Product;

import de.metas.handlingunits.inout.IHUPackingMaterialDAO;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.I_M_HU_PackingMaterial;
import de.metas.handlingunits.model.X_M_HU_PI_Item;
import de.metas.util.Services;

/**
 * @author cg
 *
 */
public class HUPackingMaterialDAO implements IHUPackingMaterialDAO
{
	@Override
	public List<I_M_HU_PackingMaterial> retrievePackingMaterials(@Nullable final I_M_HU_PI_Item_Product pip)
	{
		if (pip == null)
		{
			return Collections.emptyList();
		}

		/*note that the pip's M_HU_PI_Item item has type "Material"; we are looking for its "PackingMaterial"-sibling(s)*/
		final int piVersionId = pip.getM_HU_PI_Item().getM_HU_PI_Version_ID();

		return Services
				.get(IQueryBL.class)
				.createQueryBuilder(I_M_HU_PI_Item.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_HU_PI_Item.COLUMN_M_HU_PI_Version_ID, piVersionId)
				.addEqualsFilter(I_M_HU_PI_Item.COLUMN_ItemType, X_M_HU_PI_Item.ITEMTYPE_PackingMaterial)
				.andCollect(I_M_HU_PI_Item.COLUMN_M_HU_PackingMaterial_ID)
				.addOnlyActiveRecordsFilter()
				.create()
				.list();
	}

	@Override
	public I_M_HU_PackingMaterial retrivePackingMaterialOfProduct(final I_M_Product product)
	{
		return Services.get(IQueryBL.class).createQueryBuilder(I_M_HU_PackingMaterial.class, product)
				.filter(new EqualsQueryFilter<I_M_HU_PackingMaterial>(I_M_HU_PackingMaterial.COLUMNNAME_M_Product_ID, product.getM_Product_ID()))
				.create()
				.setOnlyActiveRecords(true)
				.firstOnly(I_M_HU_PackingMaterial.class);
	}
}
