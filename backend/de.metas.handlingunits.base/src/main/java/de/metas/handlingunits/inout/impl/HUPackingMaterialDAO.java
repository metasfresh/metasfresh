package de.metas.handlingunits.inout.impl;

import de.metas.handlingunits.inout.IHUPackingMaterialDAO;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.I_M_HU_PackingMaterial;
import de.metas.handlingunits.model.I_M_Package_HU;
import de.metas.handlingunits.model.X_M_HU_PI_Item;
import de.metas.mpackage.PackageId;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.shipper.gateway.spi.model.PackageDimensions;
import de.metas.uom.IUOMConversionBL;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.impl.EqualsQueryFilter;
import org.compiere.model.I_M_Product;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;

/**
 * @author cg
 */
public class HUPackingMaterialDAO implements IHUPackingMaterialDAO
{

	private final IQueryBL iQueryBL = Services.get(IQueryBL.class);

	@Override
	public List<I_M_HU_PackingMaterial> retrievePackingMaterials(@Nullable final I_M_HU_PI_Item_Product pip)
	{
		if (pip == null)
		{
			return Collections.emptyList();
		}

		/*note that the pip's M_HU_PI_Item item has type "Material"; we are looking for its "PackingMaterial"-sibling(s)*/
		final int piVersionId = pip.getM_HU_PI_Item().getM_HU_PI_Version_ID();

		return iQueryBL
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
		return iQueryBL.createQueryBuilder(I_M_HU_PackingMaterial.class, product)
				.filter(new EqualsQueryFilter<I_M_HU_PackingMaterial>(I_M_HU_PackingMaterial.COLUMNNAME_M_Product_ID, product.getM_Product_ID()))
				.create()
				.setOnlyActiveRecords(true)
				.firstOnly(I_M_HU_PackingMaterial.class);
	}

	@Nullable
	@Override
	public I_M_HU_PackingMaterial retrievePackingMaterialOrNull(@NonNull final PackageId packageId)
	{
		return iQueryBL
				.createQueryBuilder(I_M_Package_HU.class)
				.addEqualsFilter(I_M_Package_HU.COLUMNNAME_M_Package_ID, packageId)
				//

				.andCollect(I_M_HU.COLUMN_M_HU_ID, I_M_HU.class)
				.andCollectChildren(I_M_HU_Item.COLUMN_M_HU_ID)
				.andCollect(I_M_HU_PackingMaterial.COLUMN_M_HU_PackingMaterial_ID, I_M_HU_PackingMaterial.class)
				.create()
				// first only used here because we wanna see (read: blow up) if there are cases with 2 packing materials for a single package. I don't think this could happen, but wht if it does?
				.firstOnly(I_M_HU_PackingMaterial.class);
	}

	@Nullable
	@Override
	public I_M_HU_PackingMaterial retrieveHUPackingMaterialOrNull(@NonNull final I_M_HU_Item huItem)
	{
		final int packingMaterialId = huItem.getM_HU_PackingMaterial_ID();
		return packingMaterialId > 0 ? loadOutOfTrx(packingMaterialId, I_M_HU_PackingMaterial.class) : null;
	}

	@NonNull
	@Override
	public PackageDimensions retrievePackageDimensions(@NonNull final I_M_HU_PackingMaterial packingMaterial, @NonNull final UomId toUomId)
	{
		final UomId fromUomId = UomId.ofRepoId(packingMaterial.getC_UOM_Dimension_ID());

		final IUOMConversionBL iuomConversionBL = Services.get(IUOMConversionBL.class);
		return PackageDimensions.builder()
				.heightInCM(iuomConversionBL.convert(fromUomId, toUomId, packingMaterial.getHeight()).orElse(BigDecimal.ONE).intValue())
				.lengthInCM(iuomConversionBL.convert(fromUomId, toUomId, packingMaterial.getLength()).orElse(BigDecimal.ONE).intValue())
				.widthInCM(iuomConversionBL.convert(fromUomId, toUomId, packingMaterial.getWidth()).orElse(BigDecimal.ONE).intValue())
				.build();
	}
}
