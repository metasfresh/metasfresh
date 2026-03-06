package de.metas.ui.web.material.cockpit.v2.reservation;

import de.metas.handlingunits.QtyTU;
import de.metas.order.OrderLineId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.IQuery;
import org.compiere.model.I_M_QtyReservation;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

import static org.adempiere.model.InterfaceWrapperHelper.deleteRecord;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

@Repository
public class QtyReservationRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	public void createReservation(@NonNull final CreateQtyReservationRequest request)
	{
		final I_M_QtyReservation record = newInstance(I_M_QtyReservation.class);
		record.setC_OrderLine_ID(request.getOrderLineId().getRepoId());
		record.setM_Product_ID(request.getProductId().getRepoId());
		record.setM_Warehouse_ID(request.getWarehouseId().getRepoId());
		if (request.getVendorBPartnerId() != null)
		{
			record.setC_BPartner_Vendor_ID(request.getVendorBPartnerId().getRepoId());
		}
		record.setSupplyType(request.getSupplyType().getCode());
		if (request.getDatePromised() != null)
		{
			record.setDatePromised(TimeUtil.asTimestamp(request.getDatePromised()));
		}
		record.setQtyTU(request.getQtyTU().toBigDecimal());
		record.setQty(request.getQty().toBigDecimal());
		record.setC_UOM_ID(request.getQty().getUomId().getRepoId());
		record.setAttributesKey(request.getAttributesKey() != null ? request.getAttributesKey().getAsString() : null);
		saveRecord(record);

		// return QtyReservationId.ofRepoId(record.getM_QtyReservation_ID());
	}

	public void deleteById(@NonNull final QtyReservationId reservationId)
	{
		final I_M_QtyReservation record = queryBL
				.createQueryBuilder(I_M_QtyReservation.class)
				.addEqualsFilter(I_M_QtyReservation.COLUMNNAME_M_QtyReservation_ID, reservationId.getRepoId())
				.create()
				.firstOnlyNotNull(I_M_QtyReservation.class);

		deleteRecord(record);
	}

	public void deleteByOrderLineId(@NonNull final OrderLineId orderLineId)
	{
		queryBL.createQueryBuilder(I_M_QtyReservation.class)
				.addEqualsFilter(I_M_QtyReservation.COLUMNNAME_C_OrderLine_ID, orderLineId)
				.addOnlyActiveRecordsFilter()
				.create()
				.stream()
				.forEach(InterfaceWrapperHelper::deleteRecord);
	}

	public QtyTU getReservedQtyTU(@NonNull final OrderLineId orderLineId)
	{
		final BigDecimal result = queryBL.createQueryBuilder(I_M_QtyReservation.class)
				.addEqualsFilter(I_M_QtyReservation.COLUMNNAME_C_OrderLine_ID, orderLineId)
				.addOnlyActiveRecordsFilter()
				.create()
				.aggregate(I_M_QtyReservation.COLUMNNAME_QtyTU, IQuery.Aggregate.SUM, BigDecimal.class);

		return result != null ? QtyTU.ofBigDecimal(result) : QtyTU.ZERO;
	}
}
