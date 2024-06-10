package de.metas.contracts.invoicecandidate;

import de.metas.cache.CCache;
import de.metas.common.util.CoalesceUtil;
import de.metas.contracts.location.ContractLocationHelper;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.X_C_Flatrate_Term;
import de.metas.document.DocTypeId;
import de.metas.document.IDocTypeBL;
import de.metas.document.dimension.Dimension;
import de.metas.document.dimension.DimensionService;
import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.invoicecandidate.location.adapter.InvoiceCandidateLocationAdapterFactory;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.spi.IInvoiceCandidateHandler;
import de.metas.order.IOrderDAO;
import de.metas.order.OrderId;
import de.metas.order.OrderLineId;
import de.metas.organization.OrgId;
import de.metas.product.IProductActivityProvider;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.product.acct.api.ActivityId;
import de.metas.uom.UomId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.SpringContextHolder;
import org.compiere.model.IQuery;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;

import java.math.BigDecimal;

import static org.adempiere.model.InterfaceWrapperHelper.getContextAware;
import static org.adempiere.model.InterfaceWrapperHelper.getTableId;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;

/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2018 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class HandlerTools
{

	public static void invalidateCandidatesForTerm(final Object model)
	{
		final I_C_Flatrate_Term term = InterfaceWrapperHelper.create(model, I_C_Flatrate_Term.class);

		final int tableId = InterfaceWrapperHelper.getTableId(I_C_Flatrate_Term.class);

		final IQuery<I_C_Invoice_Candidate> query = Services.get(IQueryBL.class).createQueryBuilder(I_C_Invoice_Candidate.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Invoice_Candidate.COLUMNNAME_AD_Table_ID, tableId)
				.addEqualsFilter(I_C_Invoice_Candidate.COLUMNNAME_Record_ID, term.getC_Flatrate_Term_ID())
				.create();

		final IInvoiceCandDAO invoiceCandDB = Services.get(IInvoiceCandDAO.class);
		invoiceCandDB.invalidateCandsFor(query);
	}

	public static I_C_Invoice_Candidate createIcAndSetCommonFields(@NonNull final I_C_Flatrate_Term term)
	{
		// Services
		final DimensionService dimensionService = SpringContextHolder.instance.getBean(DimensionService.class);
		final IOrderDAO orderDAO = Services.get(IOrderDAO.class);
		final IDocTypeBL docTypeBL = Services.get(IDocTypeBL.class);

		final I_C_Invoice_Candidate ic = newInstance(I_C_Invoice_Candidate.class);

		ic.setAD_Org_ID(term.getAD_Org_ID());

		ic.setAD_Table_ID(InterfaceWrapperHelper.getTableId(I_C_Flatrate_Term.class));
		ic.setRecord_ID(term.getC_Flatrate_Term_ID());
		
		ic.setM_Product_ID(term.getM_Product_ID());

		ic.setC_Currency_ID(term.getC_Currency_ID());

		ic.setQtyToInvoice(BigDecimal.ZERO); // to be computed

		InvoiceCandidateLocationAdapterFactory
				.billLocationAdapter(ic)
				.setFrom(ContractLocationHelper.extractBillLocation(term));

		ic.setM_PricingSystem_ID(term.getM_PricingSystem_ID());

		// 07442 activity and tax
		final ActivityId activityId = Services.get(IProductActivityProvider.class).getActivityForAcct(
				ClientId.ofRepoId(term.getAD_Client_ID()),
				OrgId.ofRepoId(term.getAD_Org_ID()),
				ProductId.ofRepoId(term.getM_Product_ID()));

		ic.setIsTaxIncluded(term.isTaxIncluded());

		if (term.getC_OrderLine_Term_ID() > 0)
		{
			final I_C_OrderLine orderLine = orderDAO.getOrderLineById(OrderLineId.ofRepoId(term.getC_OrderLine_Term_ID()));
			ic.setC_OrderLine_ID(orderLine.getC_OrderLine_ID());

			final I_C_Order order = orderDAO.getById(OrderId.ofRepoId(orderLine.getC_Order_ID()));
			ic.setC_Order_ID(orderLine.getC_Order_ID());
			ic.setC_OrderSO_ID(orderLine.getC_OrderSO_ID());
			ic.setC_Incoterms_ID(order.getC_Incoterms_ID());
			ic.setIncotermLocation(order.getIncotermLocation());

			//DocType
			final DocTypeId orderDocTypeId = CoalesceUtil.coalesceSuppliers(
					() -> DocTypeId.ofRepoIdOrNull(order.getC_DocType_ID()),
					() -> DocTypeId.ofRepoIdOrNull(order.getC_DocTypeTarget_ID()));

			if(orderDocTypeId != null)
			{
				final I_C_DocType orderDocType = docTypeBL.getById(orderDocTypeId);
				final DocTypeId invoiceDocTypeId = DocTypeId.ofRepoIdOrNull(orderDocType.getC_DocTypeInvoice_ID());
				if (invoiceDocTypeId != null)
				{
					ic.setC_DocTypeInvoice_ID(invoiceDocTypeId.getRepoId());
				}
			}

			final Dimension orderLineDimension = dimensionService.getFromRecord(orderLine);

			if (orderLineDimension.getActivityId() == null)
			{
				dimensionService.updateRecord(ic, orderLineDimension.withActivityId(activityId));
			}
			else
			{
				dimensionService.updateRecord(ic, orderLineDimension);
			}
		}

		return ic;
	}

	public static boolean isCancelledContract(@NonNull final I_C_Flatrate_Term term)
	{
		return X_C_Flatrate_Term.CONTRACTSTATUS_Quit.equals(term.getContractStatus())
				|| X_C_Flatrate_Term.CONTRACTSTATUS_Voided.equals(term.getContractStatus());
	}

	private static final CCache<Integer, I_C_Flatrate_Term> IC_2_TERM = CCache
			.<Integer, I_C_Flatrate_Term>builder()
			.cacheName(I_C_Invoice_Candidate.Table_Name + "#by#" + I_C_Invoice_Candidate.COLUMNNAME_AD_Table_ID + "#" + I_C_Invoice_Candidate.COLUMNNAME_Record_ID)
			.tableName(I_C_Invoice_Candidate.Table_Name)
			.additionalTableNameToResetFor(I_C_Flatrate_Term.Table_Name)
			.build();

	public static I_C_Flatrate_Term retrieveTerm(@NonNull final I_C_Invoice_Candidate ic)
	{
		return IC_2_TERM.getOrLoad(ic.getC_Invoice_Candidate_ID(), () -> retrieveTermForCache(ic));
	}

	private static I_C_Flatrate_Term retrieveTermForCache(@NonNull final I_C_Invoice_Candidate ic)
	{
		final int flatrateTermTableId = getTableId(I_C_Flatrate_Term.class);
		Check.assume(ic.getAD_Table_ID() == flatrateTermTableId, "{} has AD_Table_ID={}", ic, flatrateTermTableId);

		final I_C_Flatrate_Term term = TableRecordReference
				.ofReferenced(ic)
				.getModel(getContextAware(ic), I_C_Flatrate_Term.class);
		return Check.assumeNotNull(term,
								   "The given invoice candidate references a {}; ic={}",
								   I_C_Flatrate_Term.class.getSimpleName(), ic);
	}

	/**
	 * <ul>
	 * <li>QtyDelivered := QtyOrdered
	 * <li>DeliveryDate := DateOrdered
	 * <li>M_InOut_ID: untouched
	 * </ul>
	 *
	 * @see IInvoiceCandidateHandler#setDeliveredData(I_C_Invoice_Candidate)
	 */
	public static void setDeliveredData(@NonNull final I_C_Invoice_Candidate ic)
	{
		// note: we can assume that #setQtyOrdered() was already called
		ic.setQtyDelivered(ic.getQtyOrdered()); // when changing this, make sure to threat ProductType.Service specially
		ic.setQtyDeliveredInUOM(ic.getQtyEntered());

		ic.setDeliveryDate(ic.getDateOrdered());
	}

	public static void setBPartnerData(@NonNull final I_C_Invoice_Candidate ic)
	{
		final I_C_Flatrate_Term term = retrieveTerm(ic);

		InvoiceCandidateLocationAdapterFactory
				.billLocationAdapter(ic)
				.setFrom(ContractLocationHelper.extractBillLocation(term));
	}

	public static UomId retrieveUomId(@NonNull final I_C_Invoice_Candidate icRecord)
	{
		final I_C_Flatrate_Term term = retrieveTerm(icRecord);
		if (term.getC_UOM_ID() > 0)
		{
			return UomId.ofRepoId(term.getC_UOM_ID());
		}
		if (term.getM_Product_ID() > 0)
		{
			final IProductBL productBL = Services.get(IProductBL.class);
			return productBL.getStockUOMId(term.getM_Product_ID());
		}

		throw new AdempiereException("The term of param 'icRecord' needs to have a UOM; C_Invoice_Candidate_ID=" + icRecord.getC_Invoice_Candidate_ID())
				.appendParametersToMessage()
				.setParameter("term", term)
				.setParameter("icRecord", icRecord);
	}
}
