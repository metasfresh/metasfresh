package de.metas.manufacturing.issue.plan;

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

@ToString(exclude = "storageFactory")
class AllocableHU
{
	private final IHUStorageFactory storageFactory;
	private final QuantityUOMConverter uomConverter;

	@Getter
	private final I_M_HU hu;

	private final ProductId productId;

	@Getter
	private final LocatorId locatorId;

	private Quantity _storageQtyInHuUom;
	private Quantity qtyAllocatedInHuUom;

	public AllocableHU(
			@NonNull final IHUStorageFactory storageFactory,
			@NonNull final QuantityUOMConverter uomConverter,
			@NonNull final I_M_HU hu,
			@NonNull final ProductId productId)
	{
		this.storageFactory = storageFactory;
		this.uomConverter = uomConverter;
		this.hu = hu;
		this.productId = productId;

		this.locatorId = IHandlingUnitsBL.extractLocatorId(hu);
	}

	public Quantity getQtyAvailableToAllocate(final UomId uomId)
	{
		return uomConverter.convertQuantityTo(getQtyAvailableToAllocateInHuUom(), productId, uomId);
	}

	private Quantity getQtyAvailableToAllocateInHuUom()
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
			storageQtyInHuUom = this._storageQtyInHuUom = storageFactory.getStorage(hu).getProductStorage(productId).getQty();
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

}
