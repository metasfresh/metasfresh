package de.metas.pos.withdrawal;

import com.google.common.collect.ImmutableList;
import de.metas.costing.ChargeId;
import de.metas.organization.ClientAndOrgId;
import de.metas.organization.OrgId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.compiere.model.I_C_Charge;
import org.springframework.stereotype.Repository;

@Repository
public class POSCashWithdrawalCategoryRepository
{
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);

	/**
	 * @return the active charges of the given charge type, visible to the given client and org, ordered by name
	 */
	@NonNull
	public ImmutableList<POSCashWithdrawalCategory> getByChargeTypeId(final int chargeTypeRepoId, @NonNull final ClientAndOrgId clientAndOrgId)
	{
		return queryBL.createQueryBuilder(I_C_Charge.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Charge.COLUMNNAME_C_ChargeType_ID, chargeTypeRepoId)
				.addEqualsFilter(I_C_Charge.COLUMNNAME_AD_Client_ID, clientAndOrgId.getClientId())
				.addInArrayFilter(I_C_Charge.COLUMNNAME_AD_Org_ID, clientAndOrgId.getOrgId(), OrgId.ANY)
				.orderBy(I_C_Charge.COLUMNNAME_Name)
				.orderBy(I_C_Charge.COLUMNNAME_C_Charge_ID)
				.create()
				.stream()
				.map(POSCashWithdrawalCategoryRepository::fromRecord)
				.collect(ImmutableList.toImmutableList());
	}

	private static POSCashWithdrawalCategory fromRecord(@NonNull final I_C_Charge record)
	{
		return POSCashWithdrawalCategory.builder()
				.chargeId(ChargeId.ofRepoId(record.getC_Charge_ID()))
				.name(record.getName())
				.build();
	}
}
