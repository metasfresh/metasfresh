/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2024 metas GmbH
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

package de.metas.contracts.flatrate.dataEntry.invoice;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationAndCaptureId;
import de.metas.common.util.CoalesceUtil;
import de.metas.contracts.IFlatrateDAO;
import de.metas.contracts.invoicecandidate.HandlerTools;
import de.metas.contracts.location.ContractLocationHelper;
import de.metas.contracts.model.I_C_Flatrate_Conditions;
import de.metas.contracts.model.I_C_Flatrate_DataEntry;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.X_C_Flatrate_Conditions;
import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.spi.AbstractInvoiceCandidateHandler;
import de.metas.invoicecandidate.spi.InvoiceCandidateGenerateRequest;
import de.metas.invoicecandidate.spi.InvoiceCandidateGenerateResult;
import de.metas.lang.SOTrx;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.pricing.IPricingContext;
import de.metas.pricing.IPricingResult;
import de.metas.pricing.PriceListVersionId;
import de.metas.pricing.service.IPricingBL;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.tax.api.ITaxBL;
import de.metas.tax.api.TaxId;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.QueryLimit;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.SpringContextHolder;
import org.compiere.util.TimeUtil;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import static org.adempiere.model.InterfaceWrapperHelper.create;

public class FlatrateDataEntryHandler extends AbstractInvoiceCandidateHandler
{
	private final IInvoiceCandDAO invoiceCandDAO = Services.get(IInvoiceCandDAO.class);
	private final IPricingBL pricingBL = Services.get(IPricingBL.class);
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	private final IFlatrateDAO flatrateDAO = Services.get(IFlatrateDAO.class);

	/**
	 * Return "CREATE_CANDIDATES" because not all ICs are created by the model interceptor {@link de.metas.contracts.interceptor.C_Flatrate_DataEntry} when the {@code C_Flatrate_DataEntry} is completed.
	 * For those that are, we will still return "DON'T" at {@link #getSpecificCandidatesAutoCreateMode(Object)}.
	 */
	@Override
	public CandidatesAutoCreateMode getGeneralCandidatesAutoCreateMode()
	{
		return CandidatesAutoCreateMode.CREATE_CANDIDATES;
	}

	@Override
	public CandidatesAutoCreateMode getSpecificCandidatesAutoCreateMode(@NonNull final Object model)
	{
		final I_C_Flatrate_DataEntry flatrateDataEntryRecord = (I_C_Flatrate_DataEntry)model;
		final I_C_Flatrate_Term termRecord = flatrateDataEntryRecord.getC_Flatrate_Term();
		final I_C_Flatrate_Conditions fcRecord = termRecord.getC_Flatrate_Conditions();

		final boolean isReportedQtyDataEntry = fcRecord.getType_Flatrate().equals(X_C_Flatrate_Conditions.TYPE_FLATRATE_ReportedQuantity);

		return isReportedQtyDataEntry ? CandidatesAutoCreateMode.CREATE_CANDIDATES : CandidatesAutoCreateMode.DONT;
	}

	/**
	 * @return empty iterator
	 */
	@Override
	public Iterator<I_C_Flatrate_DataEntry> retrieveAllModelsWithMissingCandidates(@NonNull final QueryLimit limit_IGNORED)
	{
		return Collections.emptyIterator();
	}

	@Override
	public InvoiceCandidateGenerateResult createCandidatesFor(@NonNull final InvoiceCandidateGenerateRequest request)
	{
		final FlatrateDataEntryToICService flatrateDataEntryToICService = SpringContextHolder.instance.getBean(FlatrateDataEntryToICService.class);
		final I_C_Flatrate_DataEntry flatrateEntryRecord = request.getModel(I_C_Flatrate_DataEntry.class);

		final List<I_C_Invoice_Candidate> createdICs = flatrateDataEntryToICService.createICsFor(flatrateEntryRecord, this);
		return InvoiceCandidateGenerateResult.of(this, createdICs);
	}

	/**
	 * Implementation invalidates the C_Flatrate_DataEntry's C_Invoice_Candidate and C_Invoice_Candidate_Corr (if set).
	 */
	@Override
	public void invalidateCandidatesFor(@NonNull final Object dataEntryObj)
	{
		final I_C_Flatrate_DataEntry dataEntry = create(dataEntryObj, I_C_Flatrate_DataEntry.class);
		if (dataEntry.getC_Invoice_Candidate_ID() > 0)
		{
			final InvoiceCandidateId id = InvoiceCandidateId.ofRepoId(dataEntry.getC_Invoice_Candidate_ID());
			invoiceCandDAO.invalidateCandFor(id);
		}
		if (dataEntry.getC_Invoice_Candidate_Corr_ID() > 0)
		{
			final InvoiceCandidateId id = InvoiceCandidateId.ofRepoId(dataEntry.getC_Invoice_Candidate_Corr_ID());
			invoiceCandDAO.invalidateCandFor(id);
		}
		invoiceCandDAO.invalidateCandsThatReference(TableRecordReference.of(I_C_Flatrate_DataEntry.Table_Name, dataEntry.getC_Flatrate_DataEntry_ID()));
	}

	/**
	 * Returns "C_Flatrate_DataEntry".
	 */
	@Override
	public String getSourceTable()
	{
		return I_C_Flatrate_DataEntry.Table_Name;
	}

	/**
	 * Implementation returns the user that is set as "user in charge" in the flatrate term of the {@link I_C_Flatrate_DataEntry} that references the given invoice candidate.
	 * <p>
	 * Note: It is assumed that there is a flatrate data entry referencing the given ic
	 */
	@Override
	public int getAD_User_InCharge_ID(@NonNull final I_C_Invoice_Candidate ic)
	{
		final I_C_Flatrate_Term flatrateTerm = flatrateDAO.getById(ic.getC_Flatrate_Term_ID());
		return flatrateTerm.getAD_User_InCharge_ID();
	}

	/**
	 * Returns false, because the user in charge is taken from the flatrate term.
	 */
	@Override
	public boolean isUserInChargeUserEditable()
	{
		return false;
	}

	/**
	 * Does nothing.
	 */
	@Override
	public void setOrderedData(@NonNull final I_C_Invoice_Candidate ic)
	{
		// nothing to do
	}

	/**
	 * @see HandlerTools#setDeliveredData(I_C_Invoice_Candidate)
	 */
	@Override
	public void setDeliveredData(final I_C_Invoice_Candidate ic)
	{
		HandlerTools.setDeliveredData(ic);
	}

	@Override
	public void setBPartnerData(final I_C_Invoice_Candidate ic)
	{
		// nothing to do
	}

	@Override
	public PriceAndTax calculatePriceAndTax(@NonNull final I_C_Invoice_Candidate icRecord)
	{
		final I_C_Flatrate_DataEntry flatrateDataEntryRecord = TableRecordReference.ofReferenced(icRecord).getModel(I_C_Flatrate_DataEntry.class);
		final I_C_Flatrate_Term termRecord = flatrateDAO.getById(flatrateDataEntryRecord.getC_Flatrate_Term_ID());
		final I_C_Flatrate_Conditions fcRecord = flatrateDAO.getConditionsById(termRecord.getC_Flatrate_Conditions_ID());

		final boolean isReportedQtyDataEntry = fcRecord.getType_Flatrate().equals(X_C_Flatrate_Conditions.TYPE_FLATRATE_ReportedQuantity);
		if (!isReportedQtyDataEntry)
		{
			return PriceAndTax.NONE;
		}

		final Timestamp shipDate = flatrateDataEntryRecord.getC_Period().getEndDate();

		final IPricingContext pricingContext = createPricingContext(icRecord, shipDate);
		final IPricingResult pricingResult = pricingBL.calculatePrice(pricingContext);

		final BPartnerLocationAndCaptureId shipToLocationId = CoalesceUtil.coalesceSuppliers(
				() -> ContractLocationHelper.extractDropshipLocationId(termRecord),
				() -> ContractLocationHelper.extractBillToLocationId(termRecord));

		final TaxId taxId = Services.get(ITaxBL.class).getTaxNotNull(
				icRecord,
				pricingResult.getTaxCategoryId(),
				ProductId.toRepoId(pricingResult.getProductId()),
				shipDate,
				pricingContext.getOrgId(),
				null,
				shipToLocationId, // shipC_BPartner_Location_ID
				SOTrx.ofBoolean(icRecord.isSOTrx()),
				null);

		return PriceAndTax.builder()
				.priceUOMId(pricingResult.getPriceUomId())
				.priceActual(pricingResult.getPriceStd())
				.priceEntered(pricingResult.getPriceStd())
				.discount(pricingResult.getDiscount())
				.taxIncluded(pricingResult.isTaxIncluded())
				.invoicableQtyBasedOn(pricingResult.getInvoicableQtyBasedOn())
				.currencyId(pricingResult.getCurrencyId())
				.pricingSystemId(pricingResult.getPricingSystemId())
				.priceListVersionId(pricingResult.getPriceListVersionId())
				.taxCategoryId(pricingResult.getTaxCategoryId())
				.taxId(taxId)
				.taxIncluded(pricingResult.isTaxIncluded())
				.build();
	}

	private IPricingContext createPricingContext(
			@NonNull final I_C_Invoice_Candidate icRecord,
			@NonNull final Timestamp shipDate)
	{
		final OrgId orgId = OrgId.ofRepoId(icRecord.getAD_Org_ID());
		final BPartnerId bPartnerId = BPartnerId.ofRepoId(icRecord.getBill_BPartner_ID());
		final Quantity quantity = Quantitys.of(icRecord.getQtyOrdered(), UomId.ofRepoId(icRecord.getC_UOM_ID()));
		final SOTrx soTrx = SOTrx.ofBoolean(icRecord.isSOTrx());

		// both might be not mandatory in general, but if this is our IC, then it has a product.
		final ProductId productId = ProductId.ofRepoId(icRecord.getM_Product_ID());
		final PriceListVersionId priceListVersionId = PriceListVersionId.ofRepoId(icRecord.getM_PriceList_Version_ID());

		final ZoneId tz = orgDAO.getTimeZone(orgId);
		final LocalDate priceDate = TimeUtil.asLocalDate(shipDate, tz);

		return pricingBL.createInitialContext(orgId, productId, bPartnerId, quantity, soTrx)
				.setPriceListVersionId(priceListVersionId)
				.setPriceDate(priceDate)
				.setFailIfNotCalculated();
	}

}
