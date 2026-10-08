package de.metas.handlingunits.grai;

import de.metas.handlingunits.HuPackingInstructionsId;
import de.metas.handlingunits.model.I_M_HU_PI_GRAI;
import de.metas.i18n.AdMessageKey;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
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
	 * Returns the {@link HuPackingInstructionsId} of the TU packing instruction configured for the given GRAI
	 * (matched by company-prefix and asset-type).
	 *
	 * @throws AdempiereException keyed on {@code de.metas.handlingunits.picking.GRAINoMatchingTUType}
	 *                            when no active GRAI-to-TU mapping exists for the given GRAI.
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

	/**
	 * Deletes every {@code M_HU_PI_GRAI} mapping for the given (company-prefix, asset-type) pair.
	 * <p>
	 * Intentionally NOT active-only: the unique index on (CompanyPrefix, AssetType) is global, so an
	 * inactive stale row would still block re-creating a mapping for the same pinned pair.
	 *
	 * @return the number of rows deleted.
	 */
	public int deleteMapping(@NonNull final String companyPrefix, @NonNull final String assetType)
	{
		return queryBL.createQueryBuilder(I_M_HU_PI_GRAI.class)
				.addEqualsFilter(I_M_HU_PI_GRAI.COLUMNNAME_GRAI_CompanyPrefix, companyPrefix)
				.addEqualsFilter(I_M_HU_PI_GRAI.COLUMNNAME_GRAI_AssetType, assetType)
				.create()
				.delete();
	}

	/**
	 * Creates an active {@code M_HU_PI_GRAI} row mapping the given GRAI's (company-prefix, asset-type)
	 * pair to the given TU packing instruction.
	 */
	public void createMapping(@NonNull final HuPackingInstructionsId tuPackingInstructionsId, @NonNull final GRAI grai)
	{
		final I_M_HU_PI_GRAI record = InterfaceWrapperHelper.newInstance(I_M_HU_PI_GRAI.class);
		record.setM_HU_PI_ID(tuPackingInstructionsId.getRepoId());
		record.setGRAI_CompanyPrefix(grai.getCompanyPrefix());
		record.setGRAI_AssetType(grai.getAssetType());
		record.setIsActive(true);
		InterfaceWrapperHelper.saveRecord(record);
	}
}
