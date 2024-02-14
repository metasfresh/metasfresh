package de.metas.manufacturing.issue.plan;

import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.storage.IHUStorageFactory;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.quantity.QuantityUOMConverter;
import de.metas.uom.UomId;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.warehouse.LocatorId;

@ToString(exclude = { "storageFactory", "uomConverter" })
class AllocableHU
{
	private final IHUStorageFactory storageFactory;
	private final QuantityUOMConverter uomConverter;

	@Getter
	@NonNull private final I_M_HU topLevelHU;

	@Getter
	@NonNull private final ProductId productId;

	@Getter
	@NonNull private final LocatorId locatorId;

	private Quantity _storageQtyInHuUom; // lazy
	private Quantity qtyAllocatedInHuUom;

	public AllocableHU(
			@NonNull final IHUStorageFactory storageFactory,
			@NonNull final QuantityUOMConverter uomConverter,
			@NonNull final I_M_HU topLevelHU,
			@NonNull final ProductId productId)
	{
		this.storageFactory = storageFactory;
		this.uomConverter = uomConverter;
		this.topLevelHU = topLevelHU;
		this.productId = productId;

		this.locatorId = IHandlingUnitsBL.extractLocatorId(topLevelHU);
	}

	public HuId getHuId() {return HuId.ofRepoId(getTopLevelHU().getM_HU_ID());}

	public Quantity getQtyAvailableToAllocate(final UomId uomId)
	{
		return uomConverter.convertQuantityTo(getQtyAvailableToAllocateInHuUom(), productId, uomId);
	}

	public Quantity getQtyAvailableToAllocateInHuUom()
	{
		final Quantity qtyStorageInHuUom = getStorageQtyInHuUom();
		return qtyAllocatedInHuUom != null
				? qtyStorageInHuUom.subtract(qtyAllocatedInHuUom)
				: qtyStorageInHuUom;
	}

	private Quantity getStorageQtyInHuUom()
	{
		Quantity storageQtyInHuUom = this._storageQtyInHuUom;
		if (storageQtyInHuUom == null)
		{
			storageQtyInHuUom = this._storageQtyInHuUom = storageFactory.getStorage(topLevelHU).getProductStorage(productId).getQty();
		}
		return storageQtyInHuUom;
	}

	public void addQtyAllocated(@NonNull final Quantity qtyAllocatedToAdd0)
	{
		final Quantity storageQtyInHuUom = getStorageQtyInHuUom();

		final Quantity qtyAllocatedToAddInHuUom = uomConverter.convertQuantityTo(qtyAllocatedToAdd0, productId, storageQtyInHuUom.getUomId());

		final Quantity newQtyAllocatedInHuUom = this.qtyAllocatedInHuUom != null
				? this.qtyAllocatedInHuUom.add(qtyAllocatedToAddInHuUom)
				: qtyAllocatedToAddInHuUom;

		if (newQtyAllocatedInHuUom.isGreaterThan(storageQtyInHuUom))
		{
			throw new AdempiereException("Over-allocating is not allowed")
					.appendParametersToMessage()
					.setParameter("this.qtyAllocated", this.qtyAllocatedInHuUom)
					.setParameter("newQtyAllocated", newQtyAllocatedInHuUom)
					.setParameter("storageQty", storageQtyInHuUom);
		}

		this.qtyAllocatedInHuUom = newQtyAllocatedInHuUom;
	}

	public boolean hasQtyAvailable()
	{
		return getQtyAvailableToAllocateInHuUom().signum() > 0;
	}
}
