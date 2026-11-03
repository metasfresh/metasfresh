package de.metas.cucumber.stepdefs.match_po;

import de.metas.order.MatchPOId;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.modelvalidator.IModelInterceptorRegistry;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_CostDetail;
import org.compiere.model.ModelValidator;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

/**
 * Runs a test callback inside the transaction that deletes a given M_MatchPO, at the earliest point of the race window:
 * {@code MMatchPO.beforeDelete()} has already read the M_MatchPO's cost details and is about to delete the first of them
 * ({@code CostingService.voidAndDeleteForDocument}), so a cost detail committed by someone else from now on is not cleaned up.
 * <p>
 * Registered once (there is no API to unregister an interceptor) and does nothing unless armed via {@link #runWhileArmed(MatchPOId, Consumer, Runnable)}.
 * Not a Spring component, so it is never picked up outside of the steps that use it.
 */
@Interceptor(I_M_CostDetail.class)
final class M_CostDetail_BeforeDeleteHook
{
	private static final M_CostDetail_BeforeDeleteHook INSTANCE = new M_CostDetail_BeforeDeleteHook();
	private static boolean registered = false; // guarded by the class monitor, see registerOnce()

	private final AtomicReference<ArmedHook> armedHook = new AtomicReference<>();

	/**
	 * Runs {@code action}; when it deletes the first M_CostDetail of the given M_MatchPO, {@code hook} is called (once) with the deleting transaction's name.
	 *
	 * @throws AdempiereException if {@code action} completed without deleting a cost detail of that M_MatchPO, i.e. the hook was never called
	 */
	static void runWhileArmed(
			@NonNull final MatchPOId matchPOId,
			@NonNull final Consumer<String> hook,
			@NonNull final Runnable action)
	{
		registerOnce();

		final ArmedHook armed = new ArmedHook(matchPOId, hook, new AtomicBoolean(false));
		INSTANCE.armedHook.set(armed);
		try
		{
			action.run();
		}
		finally
		{
			INSTANCE.armedHook.set(null);
		}

		if (!armed.getFired().get())
		{
			throw new AdempiereException("No M_CostDetail of the M_MatchPO was deleted, so the hook was never called")
					.appendParametersToMessage()
					.setParameter("matchPOId", matchPOId);
		}
	}

	private static synchronized void registerOnce()
	{
		if (!registered)
		{
			Services.get(IModelInterceptorRegistry.class).addModelInterceptor(INSTANCE);
			registered = true;
		}
	}

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_DELETE)
	public void beforeDelete(@NonNull final I_M_CostDetail costDetail)
	{
		final ArmedHook armed = armedHook.get();
		if (armed == null || armed.getMatchPOId().getRepoId() != costDetail.getM_MatchPO_ID())
		{
			return;
		}
		if (!armedHook.compareAndSet(armed, null)) // one shot
		{
			return;
		}

		armed.getFired().set(true);
		armed.getHook().accept(InterfaceWrapperHelper.getTrxName(costDetail));
	}

	@Value
	private static class ArmedHook
	{
		@NonNull MatchPOId matchPOId;
		@NonNull Consumer<String> hook;
		@NonNull AtomicBoolean fired;
	}
}
