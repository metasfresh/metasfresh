package de.metas.handlingunits.grai;

import de.metas.handlingunits.HuPackingInstructionsId;
import de.metas.handlingunits.model.I_M_HU_PI_GRAI;
import de.metas.i18n.AdMessageKey;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;
import java.util.List;

@Repository
public class HUPIGraiRepository
{
	private static final AdMessageKey MSG_NO_MATCHING_TU_TYPE = AdMessageKey.of("de.metas.handlingunits.picking.GRAINoMatchingTUType");

	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	/**
	 * Resolves a parsed GRAI to its TU type ({@code M_HU_PI}) via the {@code M_HU_PI_GRAI} table.
	 *
	 * <p>Queries {@code M_HU_PI_GRAI WHERE GRAI_CompanyPrefix = grai.companyPrefix AND GRAI_AssetType = grai.assetType}.
	 * The global unique index on {@code (GRAI_CompanyPrefix, GRAI_AssetType)} guarantees at most one match.
	 *
	 * @throws AdempiereException keyed on {@code de.metas.handlingunits.picking.GRAINoMatchingTUType} when no row matches.
	 */
	@NonNull
	public HuPackingInstructionsId resolveHuPackingInstructionsId(@NonNull final GRAI grai)
	{
		final String[] parts = grai.toCanonicalString().split("\\.");
		if (parts.length != 3)
		{
			throw new AdempiereException("Invalid canonical GRAI format: " + grai.toCanonicalString());
		}
		final String companyPrefix = parts[0];
		final String assetType = parts[1];

		final List<I_M_HU_PI_GRAI> records = queryBL.createQueryBuilder(I_M_HU_PI_GRAI.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_HU_PI_GRAI.COLUMNNAME_GRAI_CompanyPrefix, companyPrefix)
				.addEqualsFilter(I_M_HU_PI_GRAI.COLUMNNAME_GRAI_AssetType, assetType)
				.create()
				.list();

		if (records.isEmpty())
		{
			throw new AdempiereException(MSG_NO_MATCHING_TU_TYPE, grai.toCanonicalString());
		}

		// The global unique index guarantees at most one row; take the first defensively.
		final I_M_HU_PI_GRAI record = records.get(0);
		return HuPackingInstructionsId.ofRepoId(record.getM_HU_PI_ID());
	}
}
