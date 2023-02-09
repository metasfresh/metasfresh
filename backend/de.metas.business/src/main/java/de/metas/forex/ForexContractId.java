package de.metas.forex;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.compiere.model.I_C_ForeignExchangeContract;

import javax.annotation.Nullable;
import java.util.Objects;

@EqualsAndHashCode
@ToString
public class ForexContractId implements RepoIdAware
{
	@JsonCreator
	public static ForexContractId ofRepoId(final int repoId) {return new ForexContractId(repoId);}

	public static ForexContractId ofRepoIdOrNull(final int repoId) {return repoId > 0 ? new ForexContractId(repoId) : null;}

	int repoId;

	private ForexContractId(final int repoId) {this.repoId = Check.assumeGreaterThanZero(repoId, I_C_ForeignExchangeContract.COLUMNNAME_C_ForeignExchangeContract_ID);}

	@JsonValue
	@Override
	public int getRepoId() {return repoId;}

	public static int toRepoId(@Nullable final ForexContractId id) {return id != null ? id.getRepoId() : -1;}

	public static boolean equals(@Nullable final ForexContractId id1, @Nullable final ForexContractId id2) {return Objects.equals(id1, id2);}
}
