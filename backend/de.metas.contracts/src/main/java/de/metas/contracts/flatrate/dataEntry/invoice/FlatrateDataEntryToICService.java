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

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import de.metas.bpartner.BPartnerLocationAndCaptureId;
import de.metas.bpartner.department.BPartnerDepartment;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.common.util.CoalesceUtil;
import de.metas.common.util.pair.IPair;
import de.metas.common.util.pair.ImmutablePair;
import de.metas.contracts.FlatrateTerm;
import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.FlatrateTermRepo;
import de.metas.contracts.flatrate.dataEntry.FlatrateDataEntry;
import de.metas.contracts.flatrate.dataEntry.FlatrateDataEntryDetail;
import de.metas.contracts.flatrate.dataEntry.FlatrateDataEntryId;
import de.metas.contracts.flatrate.dataEntry.FlatrateDataEntryRepo;
import de.metas.contracts.model.I_C_Flatrate_DataEntry;
import de.metas.document.engine.DocStatus;
import de.metas.invoicecandidate.location.adapter.InvoiceCandidateLocationAdapterFactory;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.model.I_C_Invoice_Detail;
import de.metas.lang.SOTrx;
import de.metas.location.CountryId;
import de.metas.material.event.commons.AttributesKey;
import de.metas.pricing.PriceListVersionId;
import de.metas.pricing.PricingSystemId;
import de.metas.pricing.service.IPriceListBL;
import de.metas.pricing.service.ProductPrices;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.tax.api.Tax;
import de.metas.uom.UomId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.Data;
import lombok.NonNull;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.mm.attributes.keys.AttributesKeys;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.I_M_PriceList_Version;
import org.compiere.model.I_M_ProductPrice;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FlatrateDataEntryToICService
{

	final IBPartnerDAO bPartnerDAO = Services.get(IBPartnerDAO.class);
	private final IAttributeDAO attributeDAO = Services.get(IAttributeDAO.class);
	private final IADTableDAO tableDAO = Services.get(IADTableDAO.class);
	private final IPriceListBL priceListBL = Services.get(IPriceListBL.class);

	private final FlatrateTermRepo flatrateTermRepo;
	private final FlatrateDataEntryRepo flatrateDataEntryRepo;

	public FlatrateDataEntryToICService(
			@NonNull final FlatrateTermRepo flatrateTermRepo,
			@NonNull final FlatrateDataEntryRepo flatrateDataEntryRepo)
	{
		this.flatrateTermRepo = flatrateTermRepo;
		this.flatrateDataEntryRepo = flatrateDataEntryRepo;
	}

	/*package */ List<I_C_Invoice_Candidate> createICsFor(
			@NonNull final I_C_Flatrate_DataEntry entryRecord,
			@NonNull final FlatrateDataEntryHandler flatrateDataEntryHandler)
	{
		final DocStatus docStatus = DocStatus.ofCode(entryRecord.getDocStatus());
		if (!docStatus.isCompleted())
		{
			return ImmutableList.of(); // nothing to do
		}

		final FlatrateTermId flatrateTermId = FlatrateTermId.ofRepoId(entryRecord.getC_Flatrate_Term_ID());
		final FlatrateDataEntryId flatrateDataEntryId = FlatrateDataEntryId.ofRepoId(flatrateTermId, entryRecord.getC_Flatrate_DataEntry_ID());

		final ICsCreationContext context = createExecutionContext(flatrateTermId, flatrateDataEntryHandler);

		final FlatrateDataEntry entry = flatrateDataEntryRepo.getById(flatrateDataEntryId);
		
		final ImmutableMap<AttributeSetInstanceId, IPair<Quantity, List<FlatrateDataEntryDetail>>> priceRelevantASIs = createPriceRelevantASIs(entry, context);

		return createInvoiceCandidates(priceRelevantASIs, entry, context);
	}

	private ICsCreationContext createExecutionContext(
			@NonNull final FlatrateTermId flatrateTermId,
			@NonNull final FlatrateDataEntryHandler flatrateDataEntryHandler)
	{
		final FlatrateTerm flatrateTerm = flatrateTermRepo.getById(flatrateTermId);
		final CountryId countryId = bPartnerDAO.getCountryId(flatrateTerm.getBillPartnerLocationAndCaptureId().getBpartnerLocationId());

		final PricingSystemId pricingSystemId = CoalesceUtil.coalesceSuppliersNotNull(
				flatrateTerm::getPricingSystemId,
				() -> bPartnerDAO.retrievePricingSystemIdOrNull(flatrateTerm.getBillPartnerLocationAndCaptureId().getBpartnerId(), SOTrx.SALES));

		final ProductId productId = Check.assumeNotNull(flatrateTerm.getProductId(), "C_FlatrateTerm_ID={} needs to have an M_Product", flatrateTermId.getRepoId());

		return new ICsCreationContext(flatrateTerm, countryId, pricingSystemId, productId, flatrateDataEntryHandler);
	}

	/**
	 * Iterates the {@link AttributeSetInstanceId}s of the given entry's details and checks which ASI-IDs have product-prices.
	 */
	private ImmutableMap<AttributeSetInstanceId, IPair<Quantity, List<FlatrateDataEntryDetail>>> createPriceRelevantASIs(
			@NonNull final FlatrateDataEntry entry,
			@NonNull final ICsCreationContext context)
	{
		final I_M_PriceList_Version priceListVersion = Check.assumeNotNull(priceListBL.getCurrentPriceListVersionOrNull(
				context.getPricingSystemId(),
				context.getCountryId(),
				entry.getPeriod().getEndDate(),
				SOTrx.SALES,
				null), "Missing M_PriceList_Version for C_Flatrate_DataEntry_ID={}", entry.getId().getRepoId());

		// needed later thwn we create the IC
		context.setPriceListVersionId(PriceListVersionId.ofRepoId(priceListVersion.getM_PriceList_Version_ID()));

		// Make sure that between our various departments, we don't have multiple equal ASIs
		// Also sum up the details' quantites, grouped per attribute-keys
		final Map<AttributesKey, List<FlatrateDataEntryDetail>> dataEntryKey2Details = new HashMap<>();
		final Map<AttributesKey, BigDecimal> dataEntryKey2Qtys = new HashMap<>();
		for (final FlatrateDataEntryDetail detail : entry.getDetails())
		{
			final AttributesKey dataEntryKey = AttributesKeys.createAttributesKeyFromASIAllAttributes(detail.getAsiId()).orElse(AttributesKey.NONE);
			final List<FlatrateDataEntryDetail> detailsForKey;
			if (dataEntryKey2Details.containsKey(dataEntryKey))
			{
				detailsForKey = dataEntryKey2Details.get(dataEntryKey);
			}
			else
			{
				detailsForKey = new ArrayList<>();
				dataEntryKey2Details.put(dataEntryKey, detailsForKey);
			}
			detailsForKey.add(detail);

			final BigDecimal qty = Quantitys.toBigDecimalOrZero(detail.getQuantity());
			dataEntryKey2Qtys.compute(dataEntryKey, (key, oldValue) -> oldValue == null ? qty : oldValue.add(qty));
		}

		// Now find a product-price for each detail's attribute-key and re-aggregate the quantities, this time grouped by the product-prices' attribute-keys
		final Map<AttributesKey, IPair<AttributeSetInstanceId, List<FlatrateDataEntryDetail>>> ppKey2AsiIdsWithDetails = new HashMap<>();
		final Map<AttributesKey, BigDecimal> ppKey2Qtys = new HashMap<>();

		for (final Map.Entry<AttributesKey, List<FlatrateDataEntryDetail>> dataEntryKey2Detail : dataEntryKey2Details.entrySet())
		{
			final List<FlatrateDataEntryDetail> details = dataEntryKey2Detail.getValue(); //
			final AttributeSetInstanceId asiId = details.get(0).getAsiId(); // all detail's ASI-IDs are equal for our purpose.

			final I_M_AttributeSetInstance attributeSetInstanceRecord = attributeDAO.getAttributeSetInstanceById(asiId);
			final I_M_ProductPrice productPriceRecord = ProductPrices.newQuery(context.getPriceListVersionId())
					.setProductId(context.getProductId())
					.onlyValidPrices(true)
					.matching(ImmutableList.of())
					.notStrictlyMatchingAttributes(attributeSetInstanceRecord)
					.firstMatching();
			if (productPriceRecord == null)
			{
				continue;
			}

			final AttributeSetInstanceId ppAsiId = AttributeSetInstanceId.ofRepoId(productPriceRecord.getM_AttributeSetInstance_ID());
			final AttributesKey ppKey = AttributesKeys.createAttributesKeyFromASIAllAttributes(ppAsiId).orElse(AttributesKey.NONE);
			final List<FlatrateDataEntryDetail> detailsForPpKey;
			if (ppKey2AsiIdsWithDetails.containsKey(ppKey))
			{
				detailsForPpKey = ppKey2AsiIdsWithDetails.get(ppKey).getRight();
			}
			else
			{
				detailsForPpKey = new ArrayList<>();
				ppKey2AsiIdsWithDetails.put(ppKey, ImmutablePair.of(ppAsiId, detailsForPpKey));
			}
			detailsForPpKey.addAll(details);

			final AttributesKey dataEntryKey = dataEntryKey2Detail.getKey();
			final BigDecimal ppQty = dataEntryKey2Qtys.get(dataEntryKey);
			ppKey2Qtys.compute(ppKey, (key, oldValue) -> oldValue == null ? ppQty : oldValue.add(ppQty));
		}

		// finally create out result which consists of copies of the product-price ASI-IDs and the aggregated quantites from the entry's details.
		final ImmutableMap.Builder<AttributeSetInstanceId, IPair<Quantity, List<FlatrateDataEntryDetail>>> result = ImmutableMap.builder();
		for (final AttributesKey ppKey : ppKey2AsiIdsWithDetails.keySet())
		{
			final BigDecimal qty = ppKey2Qtys.get(ppKey);
			final IPair<AttributeSetInstanceId, List<FlatrateDataEntryDetail>> asiIdWithDetails = ppKey2AsiIdsWithDetails.get(ppKey);

			final AttributeSetInstanceId resultKey = asiIdWithDetails.getLeft();
			final ImmutablePair<Quantity, List<FlatrateDataEntryDetail>> resultValue = ImmutablePair.of(Quantitys.of(qty, entry.getUomId()), asiIdWithDetails.getRight());
			result.put(resultKey, resultValue);
		}
		return result.build();
	}

	private ImmutableList<I_C_Invoice_Candidate> createInvoiceCandidates(
			@NonNull final ImmutableMap<AttributeSetInstanceId, IPair<Quantity, List<FlatrateDataEntryDetail>>> priceRelevantASIs,
			@NonNull final FlatrateDataEntry entry,
			@NonNull final ICsCreationContext context)
	{
		final ImmutableList.Builder<I_C_Invoice_Candidate> result = ImmutableList.builder();

		for (final AttributeSetInstanceId asiId : priceRelevantASIs.keySet())
		{
			final IPair<Quantity, List<FlatrateDataEntryDetail>> quantityListIPair = priceRelevantASIs.get(asiId);

			final I_C_Invoice_Candidate newRecord = createAndSaveCandidateRecord(
					entry,
					asiId,
					quantityListIPair.getLeft(),
					context);

			createAndSaveDetailRecords(newRecord,
									   entry,
									   quantityListIPair.getRight(),
									   context);
			result.add(newRecord);
		}
		return result.build();
	}
	
	private I_C_Invoice_Candidate createAndSaveCandidateRecord(
			@NonNull final FlatrateDataEntry entry,
			@NonNull final AttributeSetInstanceId asiId,
			@NonNull final Quantity quantity,
			@NonNull final ICsCreationContext context)
	{
		final I_C_Invoice_Candidate newCand = InterfaceWrapperHelper.newInstance(I_C_Invoice_Candidate.class);

		final FlatrateTerm flatrateTerm = context.getFlatrateTerm();

		newCand.setAD_Org_ID(flatrateTerm.getOrgId().getRepoId());

		newCand.setM_PricingSystem_ID(context.getPricingSystemId().getRepoId());
		newCand.setM_PriceList_Version_ID(context.getPriceListVersionId().getRepoId());

		newCand.setC_Flatrate_Term_ID(flatrateTerm.getId().getRepoId());

		newCand.setM_Product_ID(context.getProductId().getRepoId());
		newCand.setM_AttributeSetInstance_ID(asiId.getRepoId());

		newCand.setQtyOrdered(quantity.toBigDecimal());
		newCand.setQtyEntered(quantity.toBigDecimal());
		newCand.setC_UOM_ID(UomId.toRepoId(quantity.getUomId()));
		newCand.setDateOrdered(TimeUtil.asTimestamp(entry.getPeriod().getEndDate()));

		newCand.setQtyToInvoice(BigDecimal.ZERO); // to be computed
		newCand.setC_Tax_ID(Tax.C_TAX_ID_NO_TAX_FOUND); // to be computed

		// pricing-data to be computed by the ILCandHandler

		newCand.setInvoiceRule(flatrateTerm.getInvoiceRule().getCode());

		newCand.setAD_Table_ID(tableDAO.retrieveTableId(I_C_Flatrate_DataEntry.Table_Name));
		newCand.setRecord_ID(FlatrateDataEntryId.toRepoId(entry.getId()));

		InvoiceCandidateLocationAdapterFactory
				.billLocationAdapter(newCand)
				.setFrom(flatrateTerm.getBillLocation());

		// needed already here, because we need its ID to be referenced by the invoice-details
		newCand.setC_ILCandHandler(context.getFlatrateDataEntryHandler().getHandlerRecord());
		InterfaceWrapperHelper.saveRecord(newCand);

		return newCand;
	}

	private void createAndSaveDetailRecords(
			@NonNull final I_C_Invoice_Candidate newInvoiceCandidate,
			@NonNull final FlatrateDataEntry entry,
			@NonNull final List<FlatrateDataEntryDetail> matchingDetails,
			@NonNull final ICsCreationContext context)
	{

		for (final FlatrateDataEntryDetail detail : matchingDetails)
		{
			final I_C_Invoice_Detail newDetailRecord = InterfaceWrapperHelper.newInstance(I_C_Invoice_Detail.class);
			newDetailRecord.setC_Invoice_Candidate_ID(newInvoiceCandidate.getC_Invoice_Candidate_ID());
			newDetailRecord.setC_Period_ID(entry.getPeriod().getId().getRepoId());
			newDetailRecord.setSeqNo(detail.getSeqNo());
			newDetailRecord.setIsPrinted(true);
			newDetailRecord.setIsPrintBefore(false);

			final Quantity detailQuantity = detail.getQuantity();
			if (detailQuantity != null)
			{
				newDetailRecord.setQty(detailQuantity.toBigDecimal());
				newDetailRecord.setC_UOM_ID(detailQuantity.getUomId().getRepoId());
			}
			else
			{
				newDetailRecord.setQty(null);
				newDetailRecord.setC_UOM_ID(entry.getUomId().getRepoId());
			}

			newDetailRecord.setM_Product_ID(newInvoiceCandidate.getM_Product_ID());
			newDetailRecord.setM_AttributeSetInstance_ID(detail.getAsiId().getRepoId());

			final BPartnerDepartment bPartnerDepartment = detail.getBPartnerDepartment();
			newDetailRecord.setLabel(bPartnerDepartment.getSearchKey());
			newDetailRecord.setDescription(bPartnerDepartment.getSearchKey() + " - " + bPartnerDepartment.getName());

			final FlatrateTerm flatrateTerm = context.getFlatrateTerm();
			final BPartnerLocationAndCaptureId dropShipLocation = flatrateTerm.getDropshipPartnerLocationAndCaptureId();
			if (dropShipLocation != null)
			{
				newDetailRecord.setDropShip_Location_ID(dropShipLocation.getBpartnerLocationId().getRepoId());
				newDetailRecord.setDropShip_BPartner_ID(dropShipLocation.getBpartnerId().getRepoId());
			}
			InterfaceWrapperHelper.saveRecord(newDetailRecord);
		}
	}

	@Data
	private static class ICsCreationContext
	{
		@NonNull
		final FlatrateTerm flatrateTerm;
		@NonNull
		final CountryId countryId;
		@NonNull
		final PricingSystemId pricingSystemId;
		@NonNull
		final ProductId productId;
		@NonNull
		final FlatrateDataEntryHandler flatrateDataEntryHandler;

		PriceListVersionId priceListVersionId;
	}
}
