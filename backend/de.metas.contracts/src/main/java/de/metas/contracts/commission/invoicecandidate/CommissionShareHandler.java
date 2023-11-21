package de.metas.contracts.commission.invoicecandidate;

import de.metas.acct.api.IProductAcctDAO;
import de.metas.bpartner.BPartnerContactId;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationAndCaptureId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.IFlatrateDAO;
import de.metas.contracts.commission.CommissionConstants;
import de.metas.contracts.commission.model.I_C_Commission_Share;
import de.metas.contracts.location.ContractLocationHelper;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.document.DocTypeId;
import de.metas.document.DocTypeQuery;
import de.metas.document.IDocTypeDAO;
import de.metas.document.location.DocumentLocation;
import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.invoicecandidate.location.adapter.InvoiceCandidateLocationAdapterFactory;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.spi.AbstractInvoiceCandidateHandler;
import de.metas.invoicecandidate.spi.IInvoiceCandidateHandler;
import de.metas.invoicecandidate.spi.InvoiceCandidateGenerateRequest;
import de.metas.invoicecandidate.spi.InvoiceCandidateGenerateResult;
import de.metas.lang.SOTrx;
import de.metas.location.LocationId;
import de.metas.logging.LogManager;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.pricing.IEditablePricingContext;
import de.metas.pricing.IPricingResult;
import de.metas.pricing.InvoicableQtyBasedOn;
import de.metas.pricing.PriceListId;
import de.metas.pricing.PricingSystemId;
import de.metas.pricing.service.IPriceListDAO;
import de.metas.pricing.service.IPricingBL;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.product.acct.api.ActivityId;
import de.metas.quantity.Quantitys;
import de.metas.tax.api.ITaxDAO;
import de.metas.tax.api.Tax;
import de.metas.tax.api.TaxNotFoundException;
import de.metas.tax.api.TaxQuery;
import de.metas.uom.UomId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.QueryLimit;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ClientId;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.IQuery;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.util.TimeUtil;
import org.slf4j.Logger;

import java.math.BigDecimal;
import java.time.ZoneId;
import java.util.Iterator;

import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.ZERO;
import static org.adempiere.model.InterfaceWrapperHelper.create;
import static org.adempiere.model.InterfaceWrapperHelper.getTableId;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;

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

/**
 * Creates an maintains commission settlement invoice candidates.
 */
public class CommissionShareHandler extends AbstractInvoiceCandidateHandler
{
	protected transient final Logger log = LogManager.getLogger(getClass());
	private final transient IFlatrateDAO flatrateDAO = Services.get(IFlatrateDAO.class);
	private final transient IProductAcctDAO productAcctDAO = Services.get(IProductAcctDAO.class);
	private final transient IProductBL productBL = Services.get(IProductBL.class);
	private final transient IBPartnerDAO bPartnerDAO = Services.get(IBPartnerDAO.class);
	private final transient IPricingBL pricingBL = Services.get(IPricingBL.class);
	private final transient IPriceListDAO priceListDAO = Services.get(IPriceListDAO.class);
	private final transient IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);
	private final transient IInvoiceCandDAO invoiceCandDAO = Services.get(IInvoiceCandDAO.class);
	private final transient IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	private final transient IQueryBL queryBL = Services.get(IQueryBL.class);
	private final transient ITaxDAO taxDAO = Services.get(ITaxDAO.class);

	@Override
	public Iterator<?> retrieveAllModelsWithMissingCandidates(final QueryLimit limit_IGNORED)
	{
		return createShareWithMissingICsQuery()
				.iterate(I_C_Commission_Share.class);
	}

	@Override
	public CandidatesAutoCreateMode getSpecificCandidatesAutoCreateMode(@NonNull final Object model)
	{
		final I_C_Commission_Share commissionShareRecord = create(model, I_C_Commission_Share.class);

		final boolean invoiceCandidateIsMissed = !recordHasAnInvoiceCandiate(commissionShareRecord);
		return invoiceCandidateIsMissed ? CandidatesAutoCreateMode.CREATE_CANDIDATES : CandidatesAutoCreateMode.DONT;
	}

	public boolean recordHasAnInvoiceCandiate(@NonNull final I_C_Commission_Share commissionShareRecord)
	{
		return createICsThatReferenceSharesQueryBuilder()
				.addEqualsFilter(I_C_Invoice_Candidate.COLUMN_Record_ID, commissionShareRecord.getC_Commission_Share_ID())
				.create()
				.anyMatch();
	}

	private IQuery<I_C_Commission_Share> createShareWithMissingICsQuery()
	{
		return queryBL
				.createQueryBuilder(I_C_Commission_Share.class)
				.addOnlyActiveRecordsFilter()
				.addNotInSubQueryFilter(I_C_Commission_Share.COLUMN_C_Commission_Share_ID, I_C_Invoice_Candidate.COLUMN_Record_ID, createICsThatReferenceSharesQuery())
				.create();
	}

	private IQuery<I_C_Invoice_Candidate> createICsThatReferenceSharesQuery()
	{
		return createICsThatReferenceSharesQueryBuilder().create();
	}

	public IQueryBuilder<I_C_Invoice_Candidate> createICsThatReferenceSharesQueryBuilder()
	{
		return queryBL.createQueryBuilder(I_C_Invoice_Candidate.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Invoice_Candidate.COLUMNNAME_AD_Table_ID, getTableId(I_C_Commission_Share.class));
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

		final OrgId orgId = OrgId.ofRepoId(commissionShareRecord.getAD_Org_ID());
		icRecord.setAD_Org_ID(orgId.getRepoId());
		icRecord.setC_ILCandHandler(getHandlerRecord());

		final TableRecordReference commissionShareRef = TableRecordReference.of(commissionShareRecord);
		icRecord.setAD_Table_ID(commissionShareRef.getAD_Table_ID());
		icRecord.setRecord_ID(commissionShareRef.getRecord_ID());

		final I_C_Flatrate_Term flatrateTerm = flatrateDAO.retrieveTerm(FlatrateTermId.ofRepoId(commissionShareRecord.getC_Flatrate_Term_ID()));

		// product
		final ProductId commissionProductId = ProductId.ofRepoId(commissionShareRecord.getCommission_Product_ID());
		icRecord.setM_Product_ID(commissionProductId.getRepoId());

		setOrderedData(icRecord, commissionShareRecord);
		setDeliveredData(icRecord);

		icRecord.setQtyToInvoice(ZERO); // to be computed

		final SOTrx soTrx = SOTrx.ofBoolean(commissionShareRecord.isSOTrx());

		final BPartnerId bPartnerId = soTrx.isSales()
				? BPartnerId.ofRepoId(commissionShareRecord.getC_BPartner_Payer_ID())
				: BPartnerId.ofRepoId(commissionShareRecord.getC_BPartner_SalesRep_ID());

		Check.assume(bPartnerId.getRepoId() == flatrateTerm.getBill_BPartner_ID(),
					 "IC.BPartnerID must be the contract owner! IC.BPartnerID: {}, flatRateTerm.Bill_BPartnerId: {}, commissionShareId: {}",
					 bPartnerId, flatrateTerm.getBill_BPartner_ID(), commissionShareRecord.getC_Commission_Share_ID());

		final BPartnerLocationAndCaptureId commissionToLocationId = ContractLocationHelper.extractBillToLocationId(flatrateTerm);

		final PricingSystemId pricingSystemId = bPartnerDAO.retrievePricingSystemIdOrNull(bPartnerId, soTrx);

		final PriceListId priceListId = priceListDAO.retrievePriceListIdByPricingSyst(pricingSystemId, commissionToLocationId, soTrx);
		final ZoneId timeZone = orgDAO.getTimeZone(orgId);

		final IEditablePricingContext pricingContext = pricingBL
				.createInitialContext(
						orgId,
						commissionProductId,
						bPartnerId,
						Quantitys.create(ONE, commissionProductId),
						soTrx)
				.setPriceListId(priceListId)
				.setPriceDate(TimeUtil.asLocalDate(icRecord.getDateOrdered(), timeZone))
				.setFailIfNotCalculated();
		final IPricingResult pricingResult = pricingBL.calculatePrice(pricingContext);

		icRecord.setInvoicableQtyBasedOn(InvoicableQtyBasedOn.NominalWeight.getCode());
		icRecord.setM_PricingSystem_ID(PricingSystemId.toRepoId(pricingSystemId));
		icRecord.setM_PriceList_Version_ID(pricingResult.getPriceListVersionId().getRepoId());
		icRecord.setC_Currency_ID(pricingResult.getCurrencyId().getRepoId());
		icRecord.setPriceActual(pricingResult.getPriceStd());// compute, taking into account the discount
		icRecord.setPrice_UOM_ID(pricingResult.getPriceUomId().getRepoId());

		icRecord.setPriceEntered(pricingResult.getPriceStd());
		icRecord.setDiscount(pricingResult.getDiscount().toBigDecimal());

		// bill location
		InvoiceCandidateLocationAdapterFactory
				.billLocationAdapter(icRecord)
				.setFrom(DocumentLocation.builder()
								 .bpartnerId(bPartnerId)
								 .bpartnerLocationId(commissionToLocationId.getBpartnerLocationId())
								 .locationId(commissionToLocationId.getLocationCaptureId())
								 .contactId(BPartnerContactId.ofRepoIdOrNull(bPartnerId, flatrateTerm.getBill_User_ID()))
								 .build());

		icRecord.setInvoiceRule(flatrateTerm.getC_Flatrate_Conditions().getInvoiceRule());

		icRecord.setIsSOTrx(soTrx.toBoolean());

		final DocTypeId docTypeId = getDoctypeId(commissionShareRecord);
		icRecord.setC_DocTypeInvoice_ID(docTypeId.getRepoId());

		// 07442 activity and tax
		final ActivityId activityId = productAcctDAO.retrieveActivityForAcct(
				ClientId.ofRepoId(commissionShareRecord.getAD_Client_ID()),
				orgId,
				commissionProductId);
		icRecord.setC_Activity_ID(ActivityId.toRepoId(activityId));

		final Tax tax = taxDAO.getBy(TaxQuery.builder()
											 .orgId(orgId)
											 .bPartnerLocationId(commissionToLocationId)
											 .dateOfInterest(icRecord.getDeliveryDate())
											 .soTrx(soTrx)
											 .taxCategoryId(pricingResult.getTaxCategoryId())
											 .build());
		if (tax == null)
		{
			final I_C_BPartner_Location bpLocation = Services.get(IBPartnerDAO.class).getBPartnerLocationByIdEvenInactive(commissionToLocationId.getBpartnerLocationId());
			throw TaxNotFoundException.builder()
					.taxCategoryId(pricingResult.getTaxCategoryId())
					.isSOTrx(soTrx.toBoolean())
					.billDate(icRecord.getDeliveryDate())
					.orgId(orgId)
					.billToC_Location_ID(LocationId.ofRepoId(bpLocation.getC_Location_ID()))
					.build()
					.appendParametersToMessage()
					.setParameter("C_Commission_Share_ID", commissionShareRecord.getC_Commission_Share_ID());
		}
		icRecord.setC_Tax_ID(tax.getTaxId().getRepoId());
		icRecord.setIsSimulation(commissionShareRecord.isSimulation());

		return icRecord;
	}

	@Override
	public void invalidateCandidatesFor(@NonNull final Object model)
	{
		invoiceCandDAO.invalidateCandsThatReference(TableRecordReference.of(model));
	}

	/**
	 * @return {@code C_Commission_Share}
	 */
	@Override
	public String getSourceTable()
	{
		return I_C_Commission_Share.Table_Name;
	}

	/**
	 * @return {@code false}
	 */
	@Override
	public boolean isUserInChargeUserEditable()
	{
		return false;
	}

	/**
	 * <ul>
	 * <li>QtyEntered := sum of all 3 C_Commission_Share.PointsSum_* columns
	 * <li>C_UOM_ID := the commission product's stock UOM
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

		// Right now, only invoiced sales transactions are commission-worthy.
		// We can later add a tick-box in the commission settings to make this configurable.
		// Also note that for non-item products (which is usually the case for commission products), we have QtyInvoicable := QtyOrdered,
		// which is why we don't include the forecasted and invoiceable quantities in the ordered qty
		final BigDecimal ordered = commissionShareRecord.getPointsSum_Invoiced()
				// .add(commissionShareRecord.getPointsSum_Invoiceable())
				// .add(commissionShareRecord.getPointsSum_Forecasted())
				;

		ic.setQtyEntered(ordered);
		ic.setC_UOM_ID(uomId.getRepoId());
		ic.setQtyOrdered(ordered); // we use the commission product's stock uom, so no uom conversion is needed
		ic.setDateOrdered(commissionShareRecord.getC_Commission_Instance().getCommissionDate());
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

		// Right now, only invoiced sales transactions are commission-worthy.
		// We can later add a tick-box in the commission settings to make this configurable.
		final BigDecimal delivered = commissionShareRecord.getPointsSum_Invoiced()
				// .add(commissionShareRecord.getPointsSum_Invoiceable())
				;

		ic.setQtyDelivered(delivered);
		ic.setQtyDeliveredInUOM(delivered);  // we use the commission product's stock uom, so no uom conversion is needed

		ic.setDeliveryDate(ic.getDateOrdered());
		ic.setM_InOut_ID(-1);
	}

	private I_C_Commission_Share getCommissionShareRecord(@NonNull final I_C_Invoice_Candidate ic)
	{
		return TableRecordReference
				.ofReferenced(ic)
				.getModel(I_C_Commission_Share.class);
	}

	@Override
	public void setBPartnerData(@NonNull final I_C_Invoice_Candidate ic)
	{
		final I_C_Commission_Share commissionShareRecord = getCommissionShareRecord(ic);
		final SOTrx soTrx = SOTrx.ofBoolean(commissionShareRecord.isSOTrx());
		ic.setBill_BPartner_ID(soTrx.isSales()
									   ? commissionShareRecord.getC_BPartner_Payer_ID()
									   : commissionShareRecord.getC_BPartner_SalesRep_ID());
	}

	@Override
	public String toString()
	{
		return "CommissionShareHandler";
	}

	@NonNull
	private DocTypeId getDoctypeId(@NonNull final I_C_Commission_Share shareRecord)
	{
		final CommissionConstants.CommissionDocType commissionDocType = getCommissionDocType(shareRecord);

		return docTypeDAO.getDocTypeId(
				DocTypeQuery.builder()
						.docBaseType(commissionDocType.getDocBaseType())
						.docSubType(commissionDocType.getDocSubType())
						.adClientId(shareRecord.getAD_Client_ID())
						.adOrgId(shareRecord.getAD_Org_ID())
						.build());
	}

	@NonNull
	private CommissionConstants.CommissionDocType getCommissionDocType(@NonNull final I_C_Commission_Share shareRecord)
	{
		if (!shareRecord.isSOTrx())
		{
			// note that SOTrx is about the share record's settlement.
			// I.e. if the sales-rep receives money from the commission, then it's a purchase order trx
			return CommissionConstants.CommissionDocType.COMMISSION;
		}
		else if (shareRecord.getC_LicenseFeeSettingsLine_ID() > 0)
		{
			return CommissionConstants.CommissionDocType.LICENSE_COMMISSION;
		}
		else if (shareRecord.getC_MediatedCommissionSettingsLine_ID() > 0)
		{
			return CommissionConstants.CommissionDocType.MEDIATED_COMMISSION;
		}

		throw new AdempiereException("Unhandled commission type! ")
				.appendParametersToMessage()
				.setParameter("C_CommissionShare_ID", shareRecord.getC_Commission_Share_ID());
	}
}
