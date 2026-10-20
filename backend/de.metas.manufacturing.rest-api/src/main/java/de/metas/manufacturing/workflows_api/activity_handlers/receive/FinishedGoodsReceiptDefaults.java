package de.metas.manufacturing.workflows_api.activity_handlers.receive;

import de.metas.common.util.time.SystemTime;
import de.metas.handlingunits.picking.config.mobileui.PickAttribute;
import de.metas.handlingunits.pporder.api.IHUPPOrderBL;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.mm.attributes.api.IAttributesBL;
import org.eevolution.api.PPOrderId;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.util.Set;

/**
 * Default best-before date (MHD) and lot number pre-filled on the mobile finished-goods receipt of one manufacturing order,
 * for a receipt today in the order's org time zone.
 * <p>
 * A default is only computed for an attribute the operator can edit on the receipt.
 * Nothing is written to the database: the lot number is a preview that does not consume the lot-number sequence.
 */
final class FinishedGoodsReceiptDefaults
{
	private final IAttributesBL attributesBL = Services.get(IAttributesBL.class);
	private final IHUPPOrderBL huPPOrderBL = Services.get(IHUPPOrderBL.class);
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);

	private final boolean isBestBeforeDateEditable;
	@NonNull private final LocalDate receiptDate;
	@Nullable private final String lotNo;

	private FinishedGoodsReceiptDefaults(@NonNull final PPOrderId ppOrderId, @NonNull final Set<PickAttribute> editableAttributes)
	{
		final OrgId orgId = OrgId.ofRepoIdOrAny(huPPOrderBL.getById(ppOrderId).getAD_Org_ID());

		this.isBestBeforeDateEditable = editableAttributes.contains(PickAttribute.BestBeforeDate);
		// same day as the receipt itself uses for its lot number and production date (movement date in the org time zone)
		this.receiptDate = SystemTime.asLocalDate(orgDAO.getTimeZone(orgId));
		this.lotNo = editableAttributes.contains(PickAttribute.LotNo)
				? huPPOrderBL.previewLotNumberFromSequence(ppOrderId, receiptDate).orElse(null)
				: null;
	}

	static FinishedGoodsReceiptDefaults of(@NonNull final PPOrderId ppOrderId, @NonNull final Set<PickAttribute> editableAttributes)
	{
		return new FinishedGoodsReceiptDefaults(ppOrderId, editableAttributes);
	}

	/**
	 * @return receipt date + the product's {@code BestBeforeDays}, or {@code null} if the best-before date is not editable or the product has no {@code BestBeforeDays}
	 */
	@Nullable
	LocalDate getBestBeforeDate(@NonNull final ProductId productId)
	{
		return isBestBeforeDateEditable
				? attributesBL.computeBestBeforeDefault(productId, receiptDate).orElse(null)
				: null;
	}

	/**
	 * @return the lot number the order's BOM lot-number sequence yields for the receipt date, or {@code null} if the lot number is not editable,
	 * there is no such sequence, or its value cannot be previewed (see {@link IHUPPOrderBL#previewLotNumberFromSequence})
	 */
	@Nullable
	String getLotNo() {return lotNo;}
}
