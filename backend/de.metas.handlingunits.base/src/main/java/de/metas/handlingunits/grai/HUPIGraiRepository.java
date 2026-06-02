package de.metas.handlingunits.grai;

import de.metas.handlingunits.HuPackingInstructionsId;
import de.metas.handlingunits.model.I_M_HU_PI_GRAI;
import de.metas.i18n.AdMessageKey;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.springframework.stereotype.Repository;

/**
 * Repository Tables: M_HU_PI_GRAI
 * Repository Cluster: HUPIGraiRepository
 */
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
		final I_M_HU_PI_GRAI record = queryBL.createQueryBuilder(I_M_HU_PI_GRAI.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_HU_PI_GRAI.COLUMNNAME_GRAI_CompanyPrefix, grai.getCompanyPrefix())
				.addEqualsFilter(I_M_HU_PI_GRAI.COLUMNNAME_GRAI_AssetType, grai.getAssetType())
				.create()
				.firstOnly(I_M_HU_PI_GRAI.class); // global unique index on (CompanyPrefix, AssetType) → throws DBException on >1

		if (record == null)
		{
			throw new AdempiereException(MSG_NO_MATCHING_TU_TYPE, grai.toCanonicalString());
		}

		return HuPackingInstructionsId.ofRepoId(record.getM_HU_PI_ID());
	}
}
