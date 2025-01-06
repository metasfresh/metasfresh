package de.metas.requisition.order_aggregation;

import com.google.common.base.MoreObjects;
import de.metas.bpartner.BPartnerId;
import de.metas.document.engine.DocStatus;
import de.metas.order.OrderLineId;
import de.metas.organization.OrgId;
import de.metas.pricing.PriceListId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.requisition.RequisitionId;
import de.metas.uom.UomId;
import de.metas.user.UserId;
import lombok.NonNull;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_Requisition;
import org.compiere.model.I_M_RequisitionLine;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;

public class OrderCandidate
{
	@NonNull private final I_M_Requisition requisition;
	@NonNull private final I_M_RequisitionLine requisitionLine;

	public OrderCandidate(@NonNull final I_M_Requisition requisition, @NonNull final I_M_RequisitionLine requisitionLine)
	{
		this.requisition = requisition;
		this.requisitionLine = requisitionLine;
	}

	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("line", requisitionLine.getLine())
				.add("description", requisitionLine.getDescription())
				.add("lineNetAmt", requisitionLine.getLineNetAmt())
				.toString();
	}

	public OrgId getOrgId() {return OrgId.ofRepoId(requisition.getAD_Org_ID());}

	public Instant getDateRequired() {return requisition.getDateRequired().toInstant();}

	@Nullable
	public PriceListId getPriceListId() {return PriceListId.ofRepoIdOrNull(requisition.getM_PriceList_ID());}

	public RequisitionId getRequisitionId() {return RequisitionId.ofRepoId(requisition.getM_Requisition_ID());}

	public String getRequisitionDocumentNo() {return requisition.getDocumentNo();}

	public DocStatus getRequisitionDocStatus() {return DocStatus.ofNullableCodeOrUnknown(requisition.getDocStatus());}

	public Optional<UserId> getRequestorId() {return Optional.ofNullable(UserId.ofRepoIdOrNullIfSystem(requisition.getAD_User_ID()));}

	@Nullable
	public ProductId getProductId() {return ProductId.ofRepoIdOrNull(requisitionLine.getM_Product_ID());}

	@NonNull
	public AttributeSetInstanceId getAttributeSetInstanceId() {return AttributeSetInstanceId.ofRepoIdOrNone(requisitionLine.getM_AttributeSetInstance_ID());}

	public int getC_Charge_ID() {return requisitionLine.getC_Charge_ID();}

	@Nullable
	public BPartnerId getPresetVendorId() {return BPartnerId.ofRepoIdOrNull(requisitionLine.getC_BPartner_ID());}

	public Quantity getQty()
	{
		return Quantitys.of(requisitionLine.getQty(), UomId.ofRepoId(requisitionLine.getC_UOM_ID()));
	}

	public BigDecimal getPriceActual() {return requisitionLine.getPriceActual();}

	public void markAsAggregated(@NonNull final OrderLineId orderLineId)
	{
		requisitionLine.setC_OrderLine_ID(orderLineId.getRepoId());
		InterfaceWrapperHelper.save(requisitionLine);
	}

	public boolean isAlreadyAggregated()
	{
		return OrderLineId.ofRepoIdOrNull(requisitionLine.getC_OrderLine_ID()) != null;
	}
}
