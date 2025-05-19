package de.metas.contracts.flatrate.exceptions;

import java.io.Serial;
import java.sql.Timestamp;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.util.Env;

import de.metas.contracts.model.I_C_Flatrate_Conditions;
import de.metas.contracts.model.I_C_SubscriptionProgress;
import de.metas.i18n.Msg;

/**
 * Indicates that there is more than one {@link I_C_SubscriptionChange} entry that could be applied in the given
 * situation (which is an error).
 * 
 * @author ts
 * 
 */
public class SubscriptionChangeException extends AdempiereException
{

	public static final String MSG_NO_CHANGE_ALLOWED = "subscription.noChangeConditions_3P";
	public static final String MSG_ILLEGAL_DATE = "subscription.illegalDateForSPs_1P";

	/**
	 * 
	 */
	@Serial
	private static final long serialVersionUID = -4403978513373371855L;

	public SubscriptionChangeException(
			final Timestamp date, 
			final I_C_SubscriptionProgress sp1,
			final I_C_SubscriptionProgress sp2)
	{
		super(buildMsgIllegalDate(date, sp1, sp2));
	}

	/**
	 * Use this constructor is there is no {@link I_C_SubscriptionChange} entry that could be applied in the given
	 * situation.
	 * 
	 * @param oldSubscriptionId
	 * @param newSubscriptionId
	 * @param daysRunning
	 */
	public SubscriptionChangeException(int oldSubscriptionId, int newSubscriptionId, final Timestamp date)
	{
		super(buildMsgNoChangeconditions(oldSubscriptionId, newSubscriptionId, date));
	}

	public SubscriptionChangeException(int oldSubscriptionId, String newStatus, final Timestamp date)
	{
		super(buildMsgNoChangeconditions(oldSubscriptionId, newStatus, date));
	}

	private static String buildMsgIllegalDate(final Timestamp date, final I_C_SubscriptionProgress sp1,
			final I_C_SubscriptionProgress sp2)
	{

		return Msg.getMsg(Env.getCtx(), MSG_ILLEGAL_DATE, new Object[] { date });
	}

	private static String buildMsgNoChangeconditions(
			final int oldSubscriptionId,
			final int newSubscriptionId,
			final Timestamp date)
	{
		final I_C_Flatrate_Conditions oldS = InterfaceWrapperHelper.create(Env.getCtx(), oldSubscriptionId, I_C_Flatrate_Conditions.class, null);

		final I_C_Flatrate_Conditions newS = InterfaceWrapperHelper.create(Env.getCtx(), newSubscriptionId, I_C_Flatrate_Conditions.class, null);

		return Msg.getMsg(Env.getCtx(), MSG_NO_CHANGE_ALLOWED,
				new Object[] { oldS.getName(), newS.getName(), date });
	}

	private static String buildMsgNoChangeconditions(
			final int oldSubscriptionId,
			final String newStatus,
			final Timestamp date)
	{
		final I_C_Flatrate_Conditions oldS = InterfaceWrapperHelper.create(Env.getCtx(), oldSubscriptionId, I_C_Flatrate_Conditions.class, null);

		return Msg.getMsg(Env.getCtx(), MSG_NO_CHANGE_ALLOWED, 
				new Object[] { oldS.getName(), newStatus, date });
	}

}
