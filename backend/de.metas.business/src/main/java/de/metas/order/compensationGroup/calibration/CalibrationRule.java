package de.metas.order.compensationGroup.calibration;

import de.metas.bpartner.BPGroupId;
import de.metas.bpartner.BPartnerId;
import de.metas.i18n.AdMessageKey;
import de.metas.order.compensationGroup.GroupTemplateId;
import de.metas.organization.OrgId;
import de.metas.product.ProductCategoryId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.util.lang.Percent;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.math.RoundingMode;

@Value
public class CalibrationRule
{
	private static final AdMessageKey MSG_BPartnerOrGroupRequired = AdMessageKey.of("C_CompensationGroup_CalibrationRule_BPartnerOrGroupRequired");
	private static final AdMessageKey MSG_NegativeFactor = AdMessageKey.of("C_CompensationGroup_CalibrationRule_NegativeFactor");

	/**
	 * null while the rule is not saved yet
	 */
	@Nullable CalibrationRuleId id;
	/**
	 * {@link OrgId#ANY} applies to every organization
	 */
	@NonNull OrgId orgId;
	int seqNo;
	@Nullable BPartnerId bpartnerId;
	@Nullable BPGroupId bpGroupId;
	@Nullable ProductId productId;
	@Nullable ProductCategoryId productCategoryId;
	@Nullable GroupTemplateId schemaId;
	/** 100-based percent the base qty is scaled by: 80 = 80 %, 100 = unchanged, 0 = zero */
	@NonNull Percent factor;

	@Builder
	private CalibrationRule(
			@Nullable final CalibrationRuleId id,
			@NonNull final OrgId orgId,
			final int seqNo,
			@Nullable final BPartnerId bpartnerId,
			@Nullable final BPGroupId bpGroupId,
			@Nullable final ProductId productId,
			@Nullable final ProductCategoryId productCategoryId,
			@Nullable final GroupTemplateId schemaId,
			@NonNull final Percent factor)
	{
		if (bpartnerId == null && bpGroupId == null)
		{
			throw new AdempiereException(MSG_BPartnerOrGroupRequired).markAsUserValidationError();
		}
		if (factor.signum() < 0)
		{
			throw new AdempiereException(MSG_NegativeFactor).markAsUserValidationError();
		}

		this.id = id;
		this.orgId = orgId;
		this.seqNo = seqNo;
		this.bpartnerId = bpartnerId;
		this.bpGroupId = bpGroupId;
		this.productId = productId;
		this.productCategoryId = productCategoryId;
		this.schemaId = schemaId;
		this.factor = factor;
	}

	public boolean appliesTo(@NonNull final CalibrationMatchKey key)
	{
		return (orgId.isAny() || OrgId.equals(orgId, key.getOrgId()))
				&& (bpartnerId == null || BPartnerId.equals(bpartnerId, key.getBpartnerId()))
				&& (bpGroupId == null || BPGroupId.equals(bpGroupId, key.getBpGroupId()))
				&& (productId == null || ProductId.equals(productId, key.getProductId()))
				&& (productCategoryId == null || ProductCategoryId.equals(productCategoryId, key.getProductCategoryId()))
				&& (schemaId == null || GroupTemplateId.equals(schemaId, key.getGroupTemplateId()));
	}

	/**
	 * @param qtyEnteredUncalibrated the base qty (template qty x menu qty), already rounded to its UOM precision
	 * @return the base qty x {@link #factor} (a 100-based percent: 80 = 80 %, 100 = unchanged, 0 = zero), rounded half-up to the UOM precision
	 */
	public Quantity computeQtyCalibrated(@NonNull final Quantity qtyEnteredUncalibrated)
	{
		// half-up, like MOrderLine.setQtyEntered; the UOM's own rounding mode (UP) would round 0.121 to 0.13
		return qtyEnteredUncalibrated.multiply(getFactor(), RoundingMode.HALF_UP);
	}
}
