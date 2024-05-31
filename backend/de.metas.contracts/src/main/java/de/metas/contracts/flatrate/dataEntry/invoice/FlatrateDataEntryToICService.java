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
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.common.util.CoalesceUtil;
import de.metas.contracts.FlatrateTerm;
import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.FlatrateTermRepo;
import de.metas.contracts.flatrate.dataEntry.FlatrateDataEntry;
import de.metas.contracts.flatrate.dataEntry.FlatrateDataEntryDetail;
import de.metas.contracts.flatrate.dataEntry.FlatrateDataEntryId;
import de.metas.contracts.flatrate.dataEntry.FlatrateDataEntryRepo;
import de.metas.contracts.model.I_C_Flatrate_DataEntry;
import de.metas.invoicecandidate.location.adapter.InvoiceCandidateLocationAdapterFactory;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
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
import org.adempiere.mm.attributes.api.AttributesKeys;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.I_M_PriceList_Version;
import org.compiere.model.I_M_ProductPrice;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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

	/*package */ List<I_C_Invoice_Candidate> createICsFor(@NonNull final I_C_Flatrate_DataEntry entryRecord)
	{
		final FlatrateTermId flatrateTermId = FlatrateTermId.ofRepoId(entryRecord.getC_Flatrate_Term_ID());
		final FlatrateDataEntryId flatrateDataEntryId = FlatrateDataEntryId.ofRepoId(flatrateTermId, entryRecord.getC_Flatrate_DataEntry_ID());

		final ExecutionContext context = createExecutionContext(flatrateTermId);

		final FlatrateDataEntry entry = flatrateDataEntryRepo.getById(flatrateDataEntryId);

		final ImmutableList.Builder<I_C_Invoice_Candidate> result = ImmutableList.builder();
		final ImmutableMap<AttributeSetInstanceId, Quantity> priceRelevantASIs = createPriceRelevantASIs(entry, context);
		for (final AttributeSetInstanceId asiId : priceRelevantASIs.keySet())
		{
			final I_C_Invoice_Candidate newRecord = createCandidateRecord(entry, asiId, priceRelevantASIs.get(asiId), context);
						
			result.add(newRecord);
		}
		return result.build();
	}

	private ExecutionContext createExecutionContext(@NonNull final FlatrateTermId flatrateTermId)
	{
		final FlatrateTerm flatrateTerm = flatrateTermRepo.getById(flatrateTermId);
		final CountryId countryId = bPartnerDAO.getCountryId(flatrateTerm.getBillPartnerLocationAndCaptureId().getBpartnerLocationId());

		final PricingSystemId pricingSystemId = CoalesceUtil.coalesceSuppliers(
				flatrateTerm::getPricingSystemId,
				() -> bPartnerDAO.retrievePricingSystemIdOrNull(flatrateTerm.getBillPartnerLocationAndCaptureId().getBpartnerId(), SOTrx.SALES));

		final ProductId productId = Check.assumeNotNull(flatrateTerm.getProductId(), "C_FlatrateTerm_ID={} needs to have an M_Product", flatrateTermId.getRepoId());

		return new ExecutionContext(flatrateTerm, countryId, pricingSystemId, productId);
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
		final I_M_PriceList_Version priceListVersion = Check.assumeNotNull(priceListBL.getCurrentPriceListVersionOrNull(
				context.getPricingSystemId(),
				context.getCountryId(),
				entry.getEndDate(),
				SOTrx.SALES,
				null), "Missing M_PriceList_Version for C_Flatrate_DataEntry_ID={}", entry.getId().getRepoId());

		// needed later thwn we create the IC
		context.setPriceListVersionId(PriceListVersionId.ofRepoId(priceListVersion.getM_PriceList_Version_ID()));
		
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
		
		newCand.setQtyToInvoice(BigDecimal.ZERO); // to be computed
		newCand.setC_Tax_ID(Tax.C_TAX_ID_NO_TAX_FOUND); // to be computed
		
		// pricing-data to be computed by the ILCandHandler

		newCand.setInvoiceRule(flatrateTerm.getInvoiceRule().getCode());

		newCand.setAD_Table_ID(tableDAO.retrieveTableId(I_C_Flatrate_DataEntry.Table_Name));
		newCand.setRecord_ID(FlatrateDataEntryId.toRepoId(entry.getId()));

		InvoiceCandidateLocationAdapterFactory
				.billLocationAdapter(newCand)
				.setFrom(flatrateTerm.getBillLocation());

		newCand.setDateOrdered(TimeUtil.asTimestamp(entry.getEndDate()));
		
		return newCand;
	}

	@Data
	private static class ExecutionContext
	{
		final FlatrateTerm flatrateTerm;
		final CountryId countryId;
		final PricingSystemId pricingSystemId;
		final ProductId productId;

		PriceListVersionId priceListVersionId;
	}

}
