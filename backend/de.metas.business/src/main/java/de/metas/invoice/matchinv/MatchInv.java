package de.metas.invoice.matchinv;

import de.metas.inout.InOutAndLineId;
import de.metas.inout.InOutId;
import de.metas.invoice.InvoiceAndLineId;
import de.metas.invoice.InvoiceId;
import de.metas.lang.SOTrx;
import de.metas.organization.ClientAndOrgId;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.quantity.StockQtyAndUOMQty;
import de.metas.user.UserId;
import de.metas.util.Check;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.service.ClientId;

import javax.annotation.Nullable;
import java.time.Instant;

@Value
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class MatchInv
{
	@NonNull MatchInvId id;

	@NonNull InvoiceAndLineId invoiceAndLineId;
	@NonNull InOutAndLineId inoutLineId;

	@NonNull ClientAndOrgId clientAndOrgId;
	@NonNull SOTrx soTrx;
	@NonNull Instant dateTrx;
	@NonNull Instant dateAcct;
	boolean posted;
	@NonNull UserId updatedByUserId;

	@NonNull ProductId productId;
	@NonNull AttributeSetInstanceId asiId;
	@NonNull StockQtyAndUOMQty qty;

	@NonNull MatchInvType type;

	@Getter(AccessLevel.NONE)
	@Nullable MatchInvCostPart costPart;

	@Builder
	private MatchInv(
			@NonNull final MatchInvId id,
			@NonNull final InvoiceAndLineId invoiceAndLineId,
			@NonNull final InOutAndLineId inoutLineId,
			@NonNull final ClientAndOrgId clientAndOrgId,
			@NonNull final SOTrx soTrx,
			@NonNull final Instant dateTrx,
			@NonNull final Instant dateAcct,
			final boolean posted,
			@NonNull final UserId updatedByUserId,
			@NonNull final ProductId productId,
			@NonNull final AttributeSetInstanceId asiId,
			@NonNull final StockQtyAndUOMQty qty,
			@NonNull final MatchInvType type,
			@Nullable final MatchInvCostPart costPart)
	{
		this.id = id;
		this.invoiceAndLineId = invoiceAndLineId;
		this.inoutLineId = inoutLineId;
		this.clientAndOrgId = clientAndOrgId;
		this.soTrx = soTrx;
		this.dateAcct = dateAcct;
		this.dateTrx = dateTrx;
		this.posted = posted;
		this.updatedByUserId = updatedByUserId;
		this.productId = productId;
		this.asiId = asiId;
		this.qty = qty;
		this.type = type;

		if (type.isCost())
		{
			this.costPart = Check.assumeNotNull(costPart, "costPart shall be provided for Cost matching type");
		}
		else
		{
			this.costPart = null;
		}
	}

	public InvoiceId getInvoiceId() {return invoiceAndLineId.getInvoiceId();}

	public InOutId getInOutId() {return inoutLineId.getInOutId();}

	public ClientId getClientId() {return clientAndOrgId.getClientId();}

	public OrgId getOrgId() {return clientAndOrgId.getOrgId();}

	@NonNull
	public MatchInvCostPart getCostPartNotNull()
	{
		if (type.isCost())
		{
			return Check.assumeNotNull(costPart, "costPart is set");
		}
		else
		{
			throw new AdempiereException("costPart not available when matching type is not Cost");
		}
	}
}
