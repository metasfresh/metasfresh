package de.metas.product.asidata;

import de.metas.bpartner.BPartnerId;
import de.metas.material.event.commons.AttributesKey;
import de.metas.product.ProductId;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.keys.AttributesKeys;
import org.compiere.model.I_M_Product_ASI_Data;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Repository for {@link ProductASIData}, backed by {@code M_Product_ASI_Data} table.
 * <p>
 * Resolves the best matching record by product, optional BPartner, and ASI content subset matching.
 * Among all matching records, the one with the lowest {@code SeqNo} wins.
 */
@Service
@RequiredArgsConstructor
public class ProductASIDataRepository
{
	@NonNull private final IQueryBL queryBL;

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
		final ICompositeQueryFilter<I_M_Product_ASI_Data> bpartnerFilter = queryBL
				.createCompositeQueryFilter(I_M_Product_ASI_Data.class)
				.setJoinOr()
				.addEqualsFilter(I_M_Product_ASI_Data.COLUMNNAME_C_BPartner_ID, null);
		if (bPartnerId != null)
		{
			bpartnerFilter.addEqualsFilter(I_M_Product_ASI_Data.COLUMNNAME_C_BPartner_ID, bPartnerId);
		}

		final List<I_M_Product_ASI_Data> candidates = queryBL
				.createQueryBuilder(I_M_Product_ASI_Data.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_Product_ASI_Data.COLUMNNAME_M_Product_ID, productId)
				.filter(bpartnerFilter)
				.orderBy().addColumn(I_M_Product_ASI_Data.COLUMNNAME_SeqNo).endOrderBy()
				.create()
				.list();

		if (candidates.isEmpty())
		{
			return null;
		}

		final AttributesKey lineKey = lineAsiId != null && lineAsiId.isRegular()
				? AttributesKeys.createAttributesKeyFromASIAllAttributes(lineAsiId).orElse(AttributesKey.NONE)
				: AttributesKey.NONE;

		// candidates are already ordered by SeqNo from the DB query → findFirst is the lowest SeqNo match
		return candidates.stream()
				.filter(candidate -> matchesASI(candidate, lineKey))
				.findFirst()
				.map(ProductASIDataRepository::toProductASIData)
				.orElse(null);
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
			// Candidate references an ASI record, but that ASI has no (storage-relevant) attributes → treat as wildcard.
			// Consistent with the SQL-side behaviour of IsASIAttributesKeySubset (UnnestAttributesKey('-1002') returns 0 rows → NOT EXISTS is vacuously true).
			return true;
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
