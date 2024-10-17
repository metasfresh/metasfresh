package de.metas.pos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.order.OrderId;
import de.metas.pos.repository.model.I_C_POS_Order;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.Value;
import org.adempiere.util.lang.impl.TableRecordReference;

import javax.annotation.Nullable;
import java.util.Objects;

@Value
public class POSOrderId implements RepoIdAware
{
	int repoId;

	private POSOrderId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "C_POS_Order_ID");
	}

	@JsonCreator
	public static POSOrderId ofRepoId(final int repoId)
	{
		return new POSOrderId(repoId);
	}

	@Nullable
	public static POSOrderId ofRepoIdOrNull(final int repoId)
	{
		return repoId > 0 ? new POSOrderId(repoId) : null;
	}

	public static int toRepoId(@Nullable final POSOrderId id)
	{
		return id != null ? id.getRepoId() : -1;
	}

	@Override
	@JsonValue
	public int getRepoId()
	{
		return repoId;
	}

	public static boolean equals(@Nullable final POSOrderId id1, @Nullable final POSOrderId id2)
	{
		return Objects.equals(id1, id2);
	}

	public TableRecordReference toRecordRef() {return TableRecordReference.of(I_C_POS_Order.Table_Name, repoId);}
}
