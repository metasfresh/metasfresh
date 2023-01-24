package de.metas.forex;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.compiere.model.I_C_ForeignExchangeContract_Alloc;

import javax.annotation.Nullable;
import java.util.Objects;

@EqualsAndHashCode
@ToString
public class ForexContractAllocationId implements RepoIdAware
{
	@JsonCreator
	public static ForexContractAllocationId ofRepoId(final int repoId) {return new ForexContractAllocationId(repoId);}

	public static ForexContractAllocationId ofRepoIdOrNull(final int repoId) {return repoId > 0 ? new ForexContractAllocationId(repoId) : null;}

	int repoId;

	private ForexContractAllocationId(final int repoId) {this.repoId = Check.assumeGreaterThanZero(repoId, I_C_ForeignExchangeContract_Alloc.COLUMNNAME_C_ForeignExchangeContract_Alloc_ID);}

	@JsonValue
	@Override
	public int getRepoId() {return repoId;}

	public static int toRepoId(@Nullable final ForexContractAllocationId id) {return id != null ? id.getRepoId() : -1;}

	public static boolean equals(@Nullable final ForexContractAllocationId id1, @Nullable final ForexContractAllocationId id2) {return Objects.equals(id1, id2);}
}
