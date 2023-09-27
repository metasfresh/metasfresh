package de.metas.shippingnotification;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.shippingnotification.model.I_M_Shipping_NotificationLine;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.Value;

import javax.annotation.Nullable;

@Value
public class ShippingNotificationLineId implements RepoIdAware
{
	int repoId;

	@JsonCreator
	public static ShippingNotificationLineId ofRepoId(final int repoId)
	{
		return new ShippingNotificationLineId(repoId);
	}

	@Nullable
	public static ShippingNotificationLineId ofRepoIdOrNull(final int repoId)
	{
		return repoId > 0 ? ofRepoId(repoId) : null;
	}

	private ShippingNotificationLineId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, I_M_Shipping_NotificationLine.COLUMNNAME_M_Shipping_NotificationLine_ID);
	}

	@Override
	@JsonValue
	public int getRepoId()
	{
		return repoId;
	}

	public static int toRepoId(@Nullable final ShippingNotificationLineId id)
	{
		return id != null ? id.getRepoId() : -1;
	}

}
