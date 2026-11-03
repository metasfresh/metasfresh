package de.metas.frontend_testing.masterdata.sysconfig;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import de.metas.organization.ClientAndOrgId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;

/**
 * The state of one {@code AD_SysConfig} row (one name at one {@code AD_Client_ID} / {@code AD_Org_ID}), read without falling back
 * to other rows; used to report a previous state and to restore exactly that row (see {@link RestoreSysconfigsCommand}).
 */
@Value
@Builder
@Jacksonized
public class JsonSysconfig
{
	@NonNull String name;

	/**
	 * The row's raw {@code Value}, including the no-value marker {@code "-"}; {@code null} = the row did not exist, so restoring deletes it
	 * and reads fall back to the next less specific row again.
	 */
	@Nullable @JsonInclude(JsonInclude.Include.ALWAYS) String value;

	int clientId;
	int orgId;

	public static JsonSysconfig of(@NonNull final String name, @Nullable final String value, @NonNull final ClientAndOrgId clientAndOrgId)
	{
		return builder()
				.name(name)
				.value(value)
				.clientId(clientAndOrgId.getClientId().getRepoId())
				.orgId(clientAndOrgId.getOrgId().getRepoId())
				.build();
	}

	@JsonIgnore
	public ClientAndOrgId getClientAndOrgId()
	{
		return ClientAndOrgId.ofClientAndOrg(clientId, orgId);
	}
}
