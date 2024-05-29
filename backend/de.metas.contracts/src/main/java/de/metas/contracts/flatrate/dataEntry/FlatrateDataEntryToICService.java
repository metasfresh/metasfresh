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

package de.metas.contracts.flatrate.dataEntry;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.common.util.CoalesceUtil;
import de.metas.contracts.FlatrateTerm;
import de.metas.contracts.FlatrateTermRepo;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.lang.SOTrx;
import de.metas.location.CountryId;
import de.metas.material.event.commons.AttributesKey;
import de.metas.pricing.PricingSystemId;
import de.metas.pricing.service.IPriceListBL;
import de.metas.pricing.service.ProductPrices;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.api.AttributesKeys;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.I_M_PriceList_Version;
import org.compiere.model.I_M_ProductPrice;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FlatrateDataEntryToICService
{
	private final FlatrateTermRepo flatrateTermRepo;

	final IBPartnerDAO bPartnerDAO = Services.get(IBPartnerDAO.class);
	private final IAttributeDAO attributeDAO = Services.get(IAttributeDAO.class);

	public FlatrateDataEntryToICService(@NonNull final FlatrateTermRepo flatrateTermRepo)
	{
		this.flatrateTermRepo = flatrateTermRepo;
	}

	public List<I_C_Invoice_Candidate> createICsFor(@NonNull final FlatrateDataEntry entry)
	{
		final FlatrateTerm flatrateTerm = flatrateTermRepo.getById(entry.getId().getFlatrateTermId());
		
		final CountryId countryId = bPartnerDAO.getCountryId(flatrateTerm.getBillPartnerLocationAndCaptureId().getBpartnerLocationId());

		final PricingSystemId pricingSystemId = CoalesceUtil.coalesceSuppliers(
				flatrateTerm::getPricingSystemId,
				() -> bPartnerDAO.retrievePricingSystemIdOrNull(flatrateTerm.getBillPartnerLocationAndCaptureId().getBpartnerId(), SOTrx.SALES));

		final ProductId productId = Check.assumeNotNull(flatrateTerm.getProductId(), "C_FlatrateTerm_ID={} needs to have an M_Product", entry.getId().getFlatrateTermId().getRepoId());
		
		final ExecutionContext context = new ExecutionContext(flatrateTerm, countryId, pricingSystemId, productId);

		final ImmutableMap<AttributeSetInstanceId, Quantity> priceRelevantASIs = createPriceRelevantASIs(entry, context);
		for (final AttributeSetInstanceId asiId : priceRelevantASIs.keySet())
		{
			createCandidateRecord(entry, asiId, priceRelevantASIs.get(asiId), context);

		}
		return ImmutableList.of();
	}

	/**
	 * Iterates the {@link AttributeSetInstanceId}s of the given entry's details and checks which ASI-IDs have product-prices.
	 *
	 * @return {@link AttributeSetInstanceId}
	 */
	private ImmutableMap<AttributeSetInstanceId, Quantity> createPriceRelevantASIs(
			@NonNull final FlatrateDataEntry entry,
			@NonNull final ExecutionContext context)
	{
		final IPriceListBL priceListBL = Services.get(IPriceListBL.class);

		final I_M_PriceList_Version priceListVersion = Check.assumeNotNull(priceListBL.getCurrentPriceListVersionOrNull(
				context.getPricingSystemId(),
				context.getCountryId(),
				entry.getEndDate(),
				SOTrx.SALES,
				null), "Missing M_PriceList_Version for C_Flatrate_DataEntry_ID={}", entry.getId().getRepoId());
	
		// Make sure that between our various departments, we don't have multiple equal ASIs
		// Also sum up the details' quantites, grouped per attribute-keys
		final Map<AttributesKey, AttributeSetInstanceId> dataEntryKey2AsiIds = new HashMap<>();
		final Map<AttributesKey, BigDecimal> dataEntryKey2Qtys = new HashMap<>();
		for (final FlatrateDataEntryDetail detail : entry.getDetails())
		{
			final AttributesKey dataEntryKey = AttributesKeys.createAttributesKeyFromASIAllAttributes(detail.getAsiId()).orElse(AttributesKey.NONE);
			dataEntryKey2AsiIds.put(dataEntryKey, detail.getAsiId());

			final BigDecimal qty = Quantitys.toBigDecimalOrZero(detail.getQuantity());
			dataEntryKey2Qtys.compute(dataEntryKey, (key, oldValue) -> oldValue == null ? qty : oldValue.add(qty));
		}

		// Now find a product-price for each detail's attribute-key and re-aggregate the quantities, this time grouped by the product-prices' attribute-keys
		final Map<AttributesKey, AttributeSetInstanceId> ppKey2AsiIds = new HashMap<>();
		final Map<AttributesKey, BigDecimal> ppKey2Qtys = new HashMap<>();

		for (final Map.Entry<AttributesKey, AttributeSetInstanceId> dataEntryKey2AsiId : dataEntryKey2AsiIds.entrySet())
		{
			final AttributeSetInstanceId asiId = dataEntryKey2AsiId.getValue();

			final I_M_AttributeSetInstance attributeSetInstanceRecord = attributeDAO.getAttributeSetInstanceById(asiId);
			final I_M_ProductPrice productPriceRecord = ProductPrices.newQuery(priceListVersion)
					.setProductId(context.getProductId())
					.onlyValidPrices(true)
					.matching(ImmutableList.of())
					.strictlyMatchingAttributes(attributeSetInstanceRecord)
					.firstMatching();
			if (productPriceRecord == null)
			{
				continue;
			}

			final AttributeSetInstanceId ppAsiId = AttributeSetInstanceId.ofRepoId(productPriceRecord.getM_AttributeSetInstance_ID());
			final AttributesKey ppKey = AttributesKeys.createAttributesKeyFromASIAllAttributes(ppAsiId).orElse(AttributesKey.NONE);
			if (!ppKey2AsiIds.containsKey(ppKey))
			{
				final AttributeSetInstanceId attributeSetInstanceId = attributeDAO.copyASI(ppAsiId);
				ppKey2AsiIds.put(ppKey, ppAsiId);
			}
			final AttributesKey dataEntryKey = dataEntryKey2AsiId.getKey();
			ppKey2Qtys.compute(ppKey, (key, oldValue) -> oldValue == null ? dataEntryKey2Qtys.get(dataEntryKey) : oldValue.add(dataEntryKey2Qtys.get(dataEntryKey)));
		}

		// finally create out result which consists of copies of the product-price ASI-IDs and the aggregated quantites from the entry's details.
		final ImmutableMap.Builder<AttributeSetInstanceId, Quantity> result = ImmutableMap.builder();
		for (final AttributesKey ppKey : ppKey2AsiIds.keySet())
		{
			final BigDecimal qty = ppKey2Qtys.get(ppKey);
			result.put(ppKey2AsiIds.get(ppKey), Quantitys.create(qty, entry.getUomId()));
		}

		return result.build();
	}

	private I_C_Invoice_Candidate createCandidateRecord(
			@NonNull final FlatrateDataEntry entry,
			@NonNull final AttributeSetInstanceId asiId,
			@NonNull final Quantity quantity,
			@NonNull final ExecutionContext context)
	{
		final I_C_Invoice_Candidate newCand = InterfaceWrapperHelper.newInstance(I_C_Invoice_Candidate.class);

		newCand.setAD_Org_ID(context.getFlatrateTerm().getOrgId().getRepoId());
		newCand.setM_PricingSystem_ID(context.getPricingSystemId().getRepoId());

		newCand.setM_Product_ID(context.getProductId().getRepoId());
		//newCand.setM_AttributeSetInstance_ID(asiId.getRepoId());
		newCand.setQtyOrdered(quantity.toBigDecimal());
		newCand.setQtyToInvoice(BigDecimal.ZERO); // to be computed

		// to be computed?
		//newCand.setPriceActual(priceActual);
		//newCand.setPrice_UOM_ID(term.getC_UOM_ID()); // 07090 when we set PiceActual, we shall also set PriceUOM.
		//newCand.setPriceEntered(priceActual); // cg : task 04917 -- same as price actual

		newCand.setDiscount(BigDecimal.ZERO);
		
		InterfaceWrapperHelper.saveRecord(newCand);
		return newCand;
	}

	// private I_C_Invoice_Candidate createCand(
	// 		final Properties ctx,
	// 		final I_C_Flatrate_Term term,
	// 		final FlatrateDataEntry dataEntry,
	// 		final int productId,
	// 		final BigDecimal priceActual)
	// {
	// 	final I_C_Invoice_Candidate newCand = InterfaceWrapperHelper.newInstance(I_C_Invoice_Candidate.class);
	// 	final FlatrateTerm flatrateTerm = flatrateTermRepo.getById(dataEntry.getId().getFlatrateTermId());
	//
	//
	//
	// 	final I_C_Flatrate_Conditions fc = term.getC_Flatrate_Conditions();
	//
	//
	//
	//
	//
	// 	newCand.setInvoiceRule(fc.getInvoiceRule());
	// 	newCand.setC_Currency_ID(term.getC_Currency_ID());
	//
	// 	InvoiceCandidateLocationAdapterFactory
	// 			.billLocationAdapter(newCand)
	// 			.setFrom(ContractLocationHelper.extractBillLocation(term));
	//
	// 	newCand.setDateOrdered(dataEntry.getC_Period().getEndDate());
	//
	// 	newCand.setAD_Table_ID(adTableDAO.retrieveTableId(I_C_Flatrate_DataEntry.Table_Name));
	// 	newCand.setRecord_ID(dataEntry.getC_Flatrate_DataEntry_ID());
	//
	// 	// 07442 activity and tax
	// 	final ActivityId activityId = findActivityIdOrNull(term, productId);
	//
	// 	newCand.setC_Activity_ID(ActivityId.toRepoId(activityId));
	// 	newCand.setIsTaxIncluded(term.isTaxIncluded());
	//
	// 	final TaxCategoryId taxCategoryId = TaxCategoryId.ofRepoIdOrNull(term.getC_TaxCategory_ID());
	//
	// 	final BPartnerLocationAndCaptureId shipToLocationId = CoalesceUtil.coalesceSuppliers(
	// 			() -> ContractLocationHelper.extractDropshipLocationId(term),
	// 			() -> ContractLocationHelper.extractBillToLocationId(term));
	//
	// 	final Timestamp shipDate;
	// 	if (dataEntry.getDate_Reported() != null)
	// 	{
	// 		shipDate = dataEntry.getDate_Reported();
	// 	}
	// 	else
	// 	{
	// 		shipDate = dataEntry.getC_Period().getEndDate();
	// 	}
	// 	final TaxId taxId = Services.get(ITaxBL.class).getTaxNotNull(
	// 			term,
	// 			taxCategoryId,
	// 			productId,
	// 			shipDate,// shipDate
	// 			orgId,
	// 			null,
	// 			shipToLocationId,
	// 			SOTrx.SALES);
	//
	// 	newCand.setC_Tax_ID(taxId.getRepoId());
	//
	// 	setILCandHandler(ctx, newCand);
	//
	// 	save(newCand);
	//
	// 	return newCand;
	// }

	@Value
	private static class ExecutionContext
	{
		FlatrateTerm flatrateTerm;
		CountryId countryId;
		PricingSystemId pricingSystemId;
		ProductId productId;
	}
}
