package de.metas.contracts.model;

import java.io.Serial;
import java.sql.ResultSet;
import java.util.Properties;

import de.metas.contracts.model.X_C_SubscriptionProgress;

public class MSubscriptionProgress extends X_C_SubscriptionProgress
{
	@Serial
	private static final long serialVersionUID = -770469915391063757L;

	public MSubscriptionProgress(Properties ctx, int C_SubscriptionProgress_ID, String trxName)
	{
		super(ctx, C_SubscriptionProgress_ID, trxName);
	}

	public MSubscriptionProgress(Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}

	@Override
	public String toString()
	{
		final StringBuffer sb = new StringBuffer("MSubscriptionProgress[") //
				.append(get_ID()) //
				.append(", EventDate=").append(getEventDate()) //
				.append(", EventType=").append(getEventType()) //
				.append(", SeqNo=").append(getSeqNo());

		sb.append(", Qty=").append(getQty());
		
		sb.append(", C_Flatrate_Term_ID=").append(getC_Flatrate_Term_ID());
		sb.append(", Status=").append(getStatus());
		sb.append("]");

		return sb.toString();
	}
}
