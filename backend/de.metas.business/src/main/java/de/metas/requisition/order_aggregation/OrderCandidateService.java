package de.metas.requisition.order_aggregation;

import de.metas.requisition.RequisitionId;
import de.metas.requisition.RequisitionRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.impl.TypedSqlQuery;
import org.adempiere.ad.trx.api.ITrx;
import org.compiere.model.IQuery;
import org.compiere.model.I_M_Requisition;
import org.compiere.model.I_M_RequisitionLine;
import org.compiere.model.MRequisition;
import org.compiere.util.Env;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class OrderCandidateService
{
	@NonNull private final RequisitionRepository requisitionRepository;

	public Stream<OrderCandidate> streamByRequisitionId(@NonNull final RequisitionId requisitionId)
	{
		final I_M_Requisition requisition = requisitionRepository.getById(requisitionId);
		return requisitionRepository.getLinesByRequisitionId(requisitionId)
				.stream()
				.map(requisitionLine -> new OrderCandidate(requisition, requisitionLine));
	}

	public Stream<OrderCandidate> stream(final OrderCandidateQuery query)
	{
		ArrayList<Object> sqlParams = new ArrayList<>();
		StringBuilder whereClause = new StringBuilder("C_OrderLine_ID IS NULL");
		if (query.getOrgId() != null && query.getOrgId().isRegular())
		{
			whereClause.append(" AND AD_Org_ID=?");
			sqlParams.add(query.getOrgId());
		}
		if (query.getProductId() != null)
		{
			whereClause.append(" AND M_Product_ID=?");
			sqlParams.add(query.getProductId());
		}
		else if (query.getProductCategoryId() != null)
		{
			whereClause.append(" AND EXISTS (SELECT 1 FROM M_Product p WHERE M_RequisitionLine.M_Product_ID=p.M_Product_ID AND p.M_Product_Category_ID=?)");
			sqlParams.add(query.getProductCategoryId());
		}

		if (query.getBpGroupId() != null)
		{
			whereClause.append(" AND (")
					.append("M_RequisitionLine.C_BPartner_ID IS NULL")
					.append(" OR EXISTS (SELECT 1 FROM C_BPartner bp WHERE M_RequisitionLine.C_BPartner_ID=bp.C_BPartner_ID AND bp.C_BP_Group_ID=?)")
					.append(")");
			sqlParams.add(query.getBpGroupId());
		}

		//
		// Requisition Header
		whereClause.append(" AND EXISTS (SELECT 1 FROM M_Requisition r WHERE M_RequisitionLine.M_Requisition_ID=r.M_Requisition_ID")
				.append(" AND r.DocStatus=?");
		sqlParams.add(MRequisition.DOCSTATUS_Completed);
		if (query.getWarehouseId() != null)
		{
			whereClause.append(" AND r.M_Warehouse_ID=?");
			sqlParams.add(query.getWarehouseId());
		}
		if (query.getDateDocFrom() != null)
		{
			whereClause.append(" AND r.DateDoc => ?");
			sqlParams.add(query.getDateDocFrom());
		}
		if (query.getDateDocTo() != null)
		{
			whereClause.append(" AND r.DateDoc <= ?");
			sqlParams.add(query.getDateDocTo());
		}
		if (query.getDateRequiredFrom() != null)
		{
			whereClause.append(" AND r.DateRequired => ?");
			sqlParams.add(query.getDateRequiredFrom());
		}
		if (query.getDateRequiredTo() != null)
		{
			whereClause.append(" AND r.DateRequired <= ?");
			sqlParams.add(query.getDateRequiredTo());
		}
		if (query.getPriorityRule() != null)
		{
			whereClause.append(" AND r.PriorityRule => ?");
			sqlParams.add(query.getPriorityRule());
		}
		if (query.getRequestorId() != null)
		{
			whereClause.append(" AND r.AD_User_ID=?");
			sqlParams.add(query.getRequestorId());
		}
		whereClause.append(")"); // End Requisition Header

		//
		// ORDER BY clause
		StringBuilder orderClause = new StringBuilder();
		if (query.isOrderByRequisitionId())
		{
			orderClause.append("M_Requisition_ID, ");
		}
		if (query.isOrderByRequestor())
		{
			orderClause.append("(SELECT AD_User_ID FROM M_Requisition r WHERE M_RequisitionLine.M_Requisition_ID=r.M_Requisition_ID), ");
		}
		orderClause.append("(SELECT DateRequired FROM M_Requisition r WHERE M_RequisitionLine.M_Requisition_ID=r.M_Requisition_ID),");
		orderClause.append("M_Product_ID, C_Charge_ID, M_AttributeSetInstance_ID");

		final OrderCandidateLoader loader = new OrderCandidateLoader(requisitionRepository);

		return new TypedSqlQuery<>(Env.getCtx(), I_M_RequisitionLine.class, whereClause.toString(), ITrx.TRXNAME_ThreadInherited)
				.setParameters(sqlParams)
				.setOrderBy(orderClause.toString())
				.setClient_ID()
				.setOption(IQuery.OPTION_GuaranteedIteratorRequired, true)
				.iterateAndStream()
				.map(loader::toOrderCandidate);
	}

	@RequiredArgsConstructor
	private static class OrderCandidateLoader
	{
		@NonNull private final RequisitionRepository requisitionRepository;

		private final HashMap<RequisitionId, I_M_Requisition> requisitionsCache = new HashMap<>();

		public OrderCandidate toOrderCandidate(@NonNull final I_M_RequisitionLine requisitionLine)
		{
			final RequisitionId requisitionId = RequisitionId.ofRepoId(requisitionLine.getM_Requisition_ID());
			final I_M_Requisition requisition = getRequisitionById(requisitionId);
			return new OrderCandidate(requisition, requisitionLine);
		}

		private I_M_Requisition getRequisitionById(@NonNull final RequisitionId requisitionId)
		{
			return requisitionsCache.computeIfAbsent(requisitionId, requisitionRepository::getById);
		}

	}
}
