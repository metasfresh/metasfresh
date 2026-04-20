package de.metas.product.asidata;

import de.metas.bpartner.BPartnerId;
import de.metas.material.event.commons.AttributesKey;
import de.metas.product.ProductId;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.keys.AttributesKeys;
import org.compiere.model.I_M_Product_ASI_Data;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.Comparator;
import java.util.List;

import de.metas.util.Services;

/**
 * Repository for {@link ProductASIData}, backed by {@code M_Product_ASI_Data} table.
 * <p>
 * Resolves the best matching record by product, optional BPartner, and ASI content subset matching.
 * Among all matching records, the one with the lowest {@code SeqNo} wins.
 */
@Service
public class ProductASIDataRepository
{
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);

	/**
	 * Find the best matching {@link ProductASIData} for the given product, BPartner, and line ASI.
	 * <p>
	 * Matching logic:
	 * <ol>
	 *   <li>Filter by product (mandatory)</li>
	 *   <li>Filter by BPartner: record's BPartner is NULL (wildcard) or matches</li>
	 *   <li>Filter by ASI subset: record's ASI is NULL (wildcard) or all its attributes are contained in the line's ASI</li>
	 *   <li>Among matches, return the one with the lowest SeqNo</li>
	 * </ol>
	 *
	 * @param productId the product to look up
	 * @param bPartnerId optional business partner (buyer)
	 * @param lineAsiId ASI from the shipment/invoice line
	 * @return best matching record, or null if none found
	 */
	@Nullable
	public ProductASIData retrieveBestMatch(
			@NonNull final ProductId productId,
			@Nullable final BPartnerId bPartnerId,
			@Nullable final AttributeSetInstanceId lineAsiId)
	{
		final List<I_M_Product_ASI_Data> candidates = queryBL
				.createQueryBuilder(I_M_Product_ASI_Data.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_Product_ASI_Data.COLUMNNAME_M_Product_ID, productId)
				.create()
				.list();

		if (candidates.isEmpty())
		{
			return null;
		}

		final AttributesKey lineKey = lineAsiId != null && lineAsiId.isRegular()
				? AttributesKeys.createAttributesKeyFromASIAllAttributes(lineAsiId).orElse(AttributesKey.NONE)
				: AttributesKey.NONE;

		return candidates.stream()
				.filter(candidate -> matchesBPartner(candidate, bPartnerId))
				.filter(candidate -> matchesASI(candidate, lineKey))
				.min(Comparator.comparingInt(I_M_Product_ASI_Data::getSeqNo))
				.map(ProductASIDataRepository::toProductASIData)
				.orElse(null);
	}

	private static boolean matchesBPartner(@NonNull final I_M_Product_ASI_Data candidate, @Nullable final BPartnerId bPartnerId)
	{
		final int candidateBPartnerId = candidate.getC_BPartner_ID();
		if (candidateBPartnerId <= 0)
		{
			return true; // wildcard — matches any BPartner
		}
		return bPartnerId != null && candidateBPartnerId == bPartnerId.getRepoId();
	}

	private static boolean matchesASI(@NonNull final I_M_Product_ASI_Data candidate, @NonNull final AttributesKey lineKey)
	{
		final int candidateAsiId = candidate.getM_AttributeSetInstance_ID();
		if (candidateAsiId <= 0)
		{
			return true; // wildcard — matches any ASI
		}

		if (lineKey.isNone())
		{
			return false; // line has no ASI, but candidate requires one
		}

		final AttributesKey candidateKey = AttributesKeys
				.createAttributesKeyFromASIAllAttributes(AttributeSetInstanceId.ofRepoId(candidateAsiId))
				.orElse(AttributesKey.NONE);

		if (candidateKey.isNone())
		{
			return true; // candidate ASI has no attributes → wildcard
		}

		// Check if the line's ASI is a superset of (or equal to) the candidate's ASI
		return lineKey.contains(candidateKey);
	}

	@NonNull
	private static ProductASIData toProductASIData(@NonNull final I_M_Product_ASI_Data record)
	{
		return ProductASIData.builder()
				.gtin(record.getGTIN())
				.eanCU(record.getEAN_CU())
				.upc(record.getUPC())
				.productNo(record.getProductNo())
				.productName(record.getProductName())
				.productDescription(record.getProductDescription())
				.seqNo(record.getSeqNo())
				.build();
	}
}
