package de.metas.contracts.commission.invoicecandidate;

import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.ZERO;
import static org.adempiere.model.InterfaceWrapperHelper.create;
import static org.adempiere.model.InterfaceWrapperHelper.getTableId;
import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;

import java.math.BigDecimal;
import java.util.Iterator;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.service.ClientId;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.IQuery;
import org.compiere.model.X_C_DocType;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;

import de.metas.acct.api.IProductAcctDAO;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.IFlatrateDAO;
import de.metas.contracts.commission.CommissionConstants;
import de.metas.contracts.commission.model.I_C_Commission_Share;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.document.DocTypeId;
import de.metas.document.DocTypeQuery;
import de.metas.document.IDocTypeDAO;
import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.model.X_C_Invoice_Candidate;
import de.metas.invoicecandidate.spi.AbstractInvoiceCandidateHandler;
import de.metas.invoicecandidate.spi.IInvoiceCandidateHandler;
import de.metas.invoicecandidate.spi.InvoiceCandidateGenerateRequest;
import de.metas.invoicecandidate.spi.InvoiceCandidateGenerateResult;
import de.metas.lang.SOTrx;
import de.metas.organization.OrgId;
import de.metas.pricing.IEditablePricingContext;
import de.metas.pricing.IPricingResult;
import de.metas.pricing.PriceListId;
import de.metas.pricing.PricingSystemId;
import de.metas.pricing.service.IPriceListDAO;
import de.metas.pricing.service.IPricingBL;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.product.acct.api.ActivityId;
import de.metas.quantity.Quantitys;
import de.metas.quantity.StockQtyAndUOMQty;
import de.metas.quantity.StockQtyAndUOMQtys;
import de.metas.tax.api.ITaxBL;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.NonNull;

/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2019 metas GmbH
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

public class CommissionShareHandler extends AbstractInvoiceCandidateHandler
{
	private final IFlatrateDAO flatrateDAO = Services.get(IFlatrateDAO.class);
	private final IProductAcctDAO productAcctDAO = Services.get(IProductAcctDAO.class);
	private final IProductBL productBL = Services.get(IProductBL.class);
	private final IBPartnerDAO bPartnerDAO = Services.get(IBPartnerDAO.class);
	private final IPricingBL pricingBL = Services.get(IPricingBL.class);
	private final IPriceListDAO priceListDAO = Services.get(IPriceListDAO.class);
	private final IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);
	private final IInvoiceCandDAO invoiceCandDAO = Services.get(IInvoiceCandDAO.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final ITaxBL taxBL = Services.get(ITaxBL.class);

	@Override
	public Iterator<? extends Object> retrieveAllModelsWithMissingCandidates(int limit_IGNORED)
	{
		return createShareWithMissingICsQuery()
				.iterate(I_C_Commission_Share.class);
	}

	@Override
	public boolean isCreateMissingCandidatesAutomatically(final Object model)
	{
		final I_C_Commission_Share commissionShareRecord = create(model, I_C_Commission_Share.class);

		if (recordHasAnInvoiceCandiate(commissionShareRecord))
		{
			return false;
		}
		return true;
	}

	public boolean recordHasAnInvoiceCandiate(final I_C_Commission_Share commissionShareRecord)
	{
		final boolean recordHasInvoiceCandidate = createICsThatReferenceSharesQueryBuilder()
				.addEqualsFilter(I_C_Invoice_Candidate.COLUMN_Record_ID, commissionShareRecord.getC_Commission_Share_ID())
				.create()
				.match();
		return recordHasInvoiceCandidate;
	}

	public IQuery<I_C_Commission_Share> createShareWithMissingICsQuery()
	{
		final IQuery<I_C_Commission_Share> shareWithMissingCandidateQuery = queryBL
				.createQueryBuilder(I_C_Commission_Share.class)
				.addOnlyActiveRecordsFilter()
				.addNotInSubQueryFilter(I_C_Commission_Share.COLUMN_C_Commission_Share_ID, I_C_Invoice_Candidate.COLUMN_Record_ID, createICsThatReferenceSharesQuery())
				.create();

		return shareWithMissingCandidateQuery;
	}

	private IQuery<I_C_Invoice_Candidate> createICsThatReferenceSharesQuery()
	{
		return createICsThatReferenceSharesQueryBuilder().create();
	}

	public IQueryBuilder<I_C_Invoice_Candidate> createICsThatReferenceSharesQueryBuilder()
	{
		return queryBL.createQueryBuilder(I_C_Invoice_Candidate.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Invoice_Candidate.COLUMN_AD_Table_ID, getTableId(I_C_Commission_Share.class));
	}

	@Override
	public InvoiceCandidateGenerateResult createCandidatesFor(@NonNull final InvoiceCandidateGenerateRequest request)
	{
		final I_C_Commission_Share commissionShareRecord = request.getModel(I_C_Commission_Share.class);
		if (recordHasAnInvoiceCandiate(commissionShareRecord))
		{
			return InvoiceCandidateGenerateResult.of(this); // make sure once again that no IC was created in meanwhile
		}

		final I_C_Invoice_Candidate ic = createInvoiceCandidateRecordFor(commissionShareRecord);
		return InvoiceCandidateGenerateResult.of(this, ic);
	}

	private I_C_Invoice_Candidate createInvoiceCandidateRecordFor(@NonNull final I_C_Commission_Share commissionShareRecord)
	{
		final I_C_Invoice_Candidate icRecord = newInstance(I_C_Invoice_Candidate.class, commissionShareRecord);

		icRecord.setAD_Org_ID(commissionShareRecord.getAD_Org_ID());
		icRecord.setC_ILCandHandler(getHandlerRecord());

		final TableRecordReference commissionShareRef = TableRecordReference.of(commissionShareRecord);
		icRecord.setAD_Table_ID(commissionShareRef.getAD_Table_ID());
		icRecord.setRecord_ID(commissionShareRef.getRecord_ID());

		final I_C_Flatrate_Term flatrateTerm = flatrateDAO.retrieveTerm(FlatrateTermId.ofRepoId(commissionShareRecord.getC_Flatrate_Term_ID()));

		// product
		icRecord.setM_Product_ID(CommissionConstants.COMMISSION_PRODUCT_ID.getRepoId());

		setOrderedData(icRecord, commissionShareRecord);
		setDeliveredData(icRecord);

		icRecord.setQtyToInvoice(ZERO); // to be computed

		final BPartnerId bPartnerId = BPartnerId.ofRepoId(commissionShareRecord.getC_BPartner_SalesRep_ID());
		final BPartnerLocationId commissionToLocationId = BPartnerLocationId.ofRepoId(flatrateTerm.getBill_BPartner_ID(), flatrateTerm.getBill_Location_ID());

		final PricingSystemId pricingSystemId = bPartnerDAO.retrievePricingSystemIdOrNull(bPartnerId, SOTrx.PURCHASE);

		final PriceListId priceListId = priceListDAO.retrievePriceListIdByPricingSyst(pricingSystemId, commissionToLocationId, SOTrx.PURCHASE);

		final IEditablePricingContext pricingContext = pricingBL
				.createInitialContext(
						CommissionConstants.COMMISSION_PRODUCT_ID,
						bPartnerId,
						Quantitys.create(ONE, CommissionConstants.COMMISSION_PRODUCT_ID),
						SOTrx.PURCHASE)
				.setPriceListId(priceListId)
				.setPriceDate(TimeUtil.asLocalDate(icRecord.getDateOrdered()));

		final IPricingResult pricingResult = pricingBL.calculatePrice(pricingContext);
		// TODO throw exception if not calculated

		icRecord.setInvoicableQtyBasedOn(X_C_Invoice_Candidate.INVOICABLEQTYBASEDON_Nominal);
		icRecord.setM_PricingSystem_ID(PricingSystemId.toRepoId(pricingSystemId));
		icRecord.setM_PriceList_Version_ID(pricingResult.getPriceListVersionId().getRepoId());
		icRecord.setC_Currency_ID(pricingResult.getCurrencyId().getRepoId());
		icRecord.setPriceActual(pricingResult.getPriceStd());// compute, taking into account the discount
		icRecord.setPrice_UOM_ID(pricingResult.getPriceUomId().getRepoId());

		icRecord.setPriceEntered(pricingResult.getPriceStd());
		icRecord.setDiscount(pricingResult.getDiscount().toBigDecimal());

		// bill location
		icRecord.setBill_BPartner_ID(bPartnerId.getRepoId());
		icRecord.setBill_Location_ID(commissionToLocationId.getRepoId());
		icRecord.setBill_User_ID(flatrateTerm.getBill_User_ID());

		icRecord.setInvoiceRule(flatrateTerm.getC_Flatrate_Conditions().getInvoiceRule());

		icRecord.setIsSOTrx(false);

		final DocTypeId docTypeId = docTypeDAO.getDocTypeId(
				DocTypeQuery.builder()
						.docBaseType(X_C_DocType.DOCBASETYPE_APInvoice)
						.docSubType(CommissionConstants.COMMISSION_DOC_SUBTYPE_VALUE)
						.adClientId(commissionShareRecord.getAD_Client_ID())
						.adOrgId(commissionShareRecord.getAD_Org_ID())
						.build());
		icRecord.setC_DocTypeInvoice_ID(docTypeId.getRepoId());

		// 07442 activity and tax
		final OrgId orgId = OrgId.ofRepoId(commissionShareRecord.getAD_Org_ID());

		final ActivityId activityId = productAcctDAO.retrieveActivityForAcct(
				ClientId.ofRepoId(commissionShareRecord.getAD_Client_ID()),
				orgId,
				CommissionConstants.COMMISSION_PRODUCT_ID);
		icRecord.setC_Activity_ID(ActivityId.toRepoId(activityId));

		final int taxId = taxBL.getTax(
				Env.getCtx(),
				icRecord, // model
				pricingResult.getTaxCategoryId(),
				CommissionConstants.COMMISSION_PRODUCT_ID.getRepoId(),
				icRecord.getDeliveryDate(),
				orgId,
				(WarehouseId)null,
				commissionToLocationId.getRepoId(),
				false /* isSOTrx */);
		icRecord.setC_Tax_ID(taxId);
		return icRecord;
	}

	@Override
	public void invalidateCandidatesFor(@NonNull final Object model)
	{
		invoiceCandDAO.invalidateCandsThatReference(TableRecordReference.of(model));
	}

	/** @return {@code C_Commission_Share} */
	@Override
	public String getSourceTable()
	{
		return I_C_Commission_Share.Table_Name;
	}

	/** @return {@code false} */
	@Override
	public boolean isUserInChargeUserEditable()
	{
		return false;
	}

	/**
	 * <ul>
	 * <li>QtyEntered := sum of all 3 C_Commission_Share.PointsSum_* columns
	 * <li>C_UOM_ID := {@link #COMMISSION_PRODUCT_ID}'s stock UOM
	 * <li>QtyOrdered := QtyEntered
	 * <li>DateOrdered := C_Commission_Share.Created
	 * <li>C_Order_ID: -1
	 * </ul>
	 */
	@Override
	public void setOrderedData(@NonNull final I_C_Invoice_Candidate ic)
	{
		final I_C_Commission_Share commissionShareRecord = getCommissionShareRecord(ic);
		setOrderedData(ic, commissionShareRecord);
	}

	private void setOrderedData(
			@NonNull final I_C_Invoice_Candidate ic,
			@NonNull final I_C_Commission_Share commissionShareRecord)
	{
		final UomId uomId = productBL.getStockUOMId(ic.getM_Product_ID());

		final BigDecimal allPoints = commissionShareRecord.getPointsSum_Forecasted()
				.add(commissionShareRecord.getPointsSum_Invoiceable())
				.add(commissionShareRecord.getPointsSum_Invoiced());

		final int commissionTriggerIcRecordId = commissionShareRecord.getC_Commission_Instance().getC_Invoice_Candidate_ID();
		final I_C_Invoice_Candidate commissionTriggerIcRecord = loadOutOfTrx(commissionTriggerIcRecordId, I_C_Invoice_Candidate.class);

		ic.setQtyEntered(allPoints);
		ic.setC_UOM_ID(uomId.getRepoId());
		ic.setQtyOrdered(allPoints);
		ic.setDateOrdered(commissionTriggerIcRecord.getDateOrdered());
		ic.setC_Order_ID(-1);
	}

	/**
	 * <ul>
	 * <li>QtyDelivered := Invoiced quantity of the respective {@code C_Commission_Share}
	 * <li>DeliveryDate := (latest) DatenInvoiced of the respective {@code C_Commission_Share}'s invoice candidate's invoice
	 * <li>M_InOut_ID: := -1
	 * </ul>
	 *
	 * @see IInvoiceCandidateHandler#setDeliveredData(I_C_Invoice_Candidate)
	 */
	@Override
	public void setDeliveredData(@NonNull final I_C_Invoice_Candidate ic)
	{
		final I_C_Commission_Share commissionShareRecord = getCommissionShareRecord(ic);

		final BigDecimal delivered = commissionShareRecord.getPointsSum_Invoiceable()
				.add(commissionShareRecord.getPointsSum_Invoiced());

		final StockQtyAndUOMQty stockAndUom = StockQtyAndUOMQtys.createConvert(delivered,
				ProductId.ofRepoId(ic.getM_Product_ID()),
				UomId.ofRepoId(ic.getC_UOM_ID()));

		ic.setQtyDelivered(stockAndUom.getStockQty().toBigDecimal());
		ic.setQtyDeliveredInUOM(stockAndUom.getStockQty().toBigDecimal());

		ic.setDeliveryDate(ic.getDateOrdered());
		ic.setM_InOut_ID(-1);
	}

	private I_C_Commission_Share getCommissionShareRecord(@NonNull final I_C_Invoice_Candidate ic)
	{
		final I_C_Commission_Share commissionShareRecord = TableRecordReference
				.ofReferenced(ic)
				.getModel(I_C_Commission_Share.class);
		return commissionShareRecord;
	}

	@Override
	public void setBPartnerData(@NonNull final I_C_Invoice_Candidate ic)
	{
		final I_C_Commission_Share commissionShareRecord = getCommissionShareRecord(ic);
		ic.setBill_BPartner_ID(commissionShareRecord.getC_BPartner_SalesRep_ID());
	}
}
