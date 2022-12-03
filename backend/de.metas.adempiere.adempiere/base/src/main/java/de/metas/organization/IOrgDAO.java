package de.metas.organization;

import de.metas.user.UserGroupId;
import de.metas.util.ISingletonService;
import lombok.NonNull;
import org.adempiere.service.ClientId;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_AD_Org;
import org.compiere.util.Env;

import javax.annotation.Nullable;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;

public interface IOrgDAO extends ISingletonService
{
	void save(I_AD_Org orgRecord);

	ClientId getClientIdByOrgId(OrgId orgId);

	Optional<OrgId> retrieveOrgIdBy(OrgQuery orgQuery);

	@Deprecated
	I_AD_Org retrieveOrg(Properties ctx, int adOrgId);

	default I_AD_Org getById(@NonNull final OrgId orgId)
	{
		return retrieveOrg(Env.getCtx(), orgId.getRepoId());
	}

	default I_AD_Org getById(final int adOrgId)
	{
		return retrieveOrg(Env.getCtx(), adOrgId);
	}

	default String retrieveOrgName(final int adOrgId)
	{
		return retrieveOrgName(OrgId.ofRepoIdOrNull(adOrgId));
	}

	default String retrieveOrgName(@Nullable final OrgId adOrgId)
	{
		if (adOrgId == null)
		{
			return "?";
		}
		else if (adOrgId.isAny())
		{
			return "*";
		}
		else
		{
			final I_AD_Org orgRecord = getById(adOrgId);
			return orgRecord != null ? orgRecord.getName() : "<" + adOrgId.getRepoId() + ">";
		}
	}

	default String retrieveOrgValue(final int adOrgId)
	{
		return retrieveOrgValue(OrgId.ofRepoIdOrNull(adOrgId));
	}

	@NonNull
	default String retrieveOrgValue(@Nullable final OrgId adOrgId)
	{
		if (adOrgId == null)
		{
			return "?";
		}
		else if (adOrgId.isAny())
		{
			//return "*";
			return "0"; // "*" is the name of the "any" org, but it's org-value is 0
		}
		else
		{
			final I_AD_Org orgRecord = getById(adOrgId);
			return orgRecord != null ? orgRecord.getValue() : "<" + adOrgId.getRepoId() + ">";
		}
	}

	List<I_AD_Org> getByIds(Set<OrgId> orgIds);

	List<I_AD_Org> getAllActiveOrgs();

	OrgInfo createOrUpdateOrgInfo(OrgInfoUpdateRequest request);

	OrgInfo getOrgInfoById(OrgId adOrgId);

	OrgInfo getOrgInfoByIdInTrx(OrgId adOrgId);

	WarehouseId getOrgWarehouseId(OrgId orgId);

	WarehouseId getOrgPOWarehouseId(OrgId orgId);

	WarehouseId getOrgDropshipWarehouseId(OrgId orgId);

	/**
	 * Search for the organization when the value is known
	 *
	 * @return AD_Org Object if the organization was found, null otherwise.
	 */
	I_AD_Org retrieveOrganizationByValue(Properties ctx, String value);

	List<I_AD_Org> retrieveClientOrgs(Properties ctx, int adClientId);

	default List<I_AD_Org> retrieveClientOrgs(final int adClientId)
	{
		return retrieveClientOrgs(Env.getCtx(), adClientId);
	}

	/** @return organization's time zone or system time zone; never returns null */
	ZoneId getTimeZone(OrgId orgId);

	/**
	 * @return true if the given org falls under the european One-Stop-Shop (OSS) regulations
	 */
	boolean isEUOneStopShop(@NonNull OrgId orgId);

	UserGroupId getSupplierApprovalExpirationNotifyUserGroupID(OrgId ofRepoId);

	UserGroupId getPartnerCreatedFromAnotherOrgNotifyUserGroupID(OrgId orgId);

	String getOrgName(@NonNull OrgId orgId);

	boolean isAutoInvoiceFlatrateTerm(OrgId orgId);
}
