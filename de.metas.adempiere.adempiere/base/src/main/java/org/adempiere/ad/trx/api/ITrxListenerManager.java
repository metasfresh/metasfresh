package org.adempiere.ad.trx.api;

import java.util.function.Supplier;

import org.adempiere.ad.trx.api.ITrxListenerManager.RegisterListenerRequest.RegisterListenerRequestBuilder;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

/**
 * Transactions Listeners Mananger.<br>
 * Use {@link ITrxManager#getTrxListenerManager(String)} or {@link ITrxManager#getTrxListenerManagerOrAutoCommit(String)} to get your instance.
 *
 * @author tsa
 *
 */
public interface ITrxListenerManager
{

	public enum TrxEventTiming
	{
		NONE(0),
		/**
		 * Method called before a transaction will be committed.
		 * If an exception is thrown by this method, it will be propagated and the execution/transaction will fail.
		 */
		BEFORE_COMMIT(10),

		/**
		 * Method called <b>each time</b> after a transaction was successfully committed.
		 * If an exception is thrown from this method, the exception will be JUST logged but it will not fail or stop the execution.
		 *
		 * <b>The transaction the handler method is invoked with is already closed</b>
		 */
		AFTER_COMMIT(20),

		/**
		 * Method called after a transaction was <b>successfully</b> rollback.
		 */
		AFTER_ROLLBACK(30),

		/**
		 * Method called after a transaction was closed (successfully or not).
		 */
		AFTER_CLOSE(40);

		private final int seqNo;

		private TrxEventTiming(final int seqNo)
		{
			this.seqNo = seqNo;
		}

		/**
		 * <li>Every timing can registered within "none", i.e. outside of any listener method.
		 * <li>{@link #UNSPECIFIED} can only be registered if the current timing it runs within is {@link #NONE}
		 * <li>otherwise, a listener can be registered within another listener's method, if its timing is after that other listener method's timing.<br>
		 * e.g. within a beforeComlete() (=> otherTiming) method you can register a listener for "afterRollBack" (=> this timing)
		 */
		public boolean canBeRegisteredWithinOtherTiming(@NonNull final TrxEventTiming otherTiming)
		{
			if (otherTiming == NONE)
			{
				return true;
			}
			else
			{
				return seqNo > otherTiming.seqNo;
			}
		}
	}

	@FunctionalInterface
	public interface EventHandlingMethod
	{
		void onTransactionEvent(ITrx trx);
	}

	@Data
	public class RegisterListenerRequest
	{

		private final TrxEventTiming timing;

		private final boolean registerWeakly;

		private final boolean invokeMethodJustOnce;

		private final EventHandlingMethod handlingMethod;

		private final ITrxListenerManager parent;

		private Supplier<String> toStringSupplier;

		private boolean active = true;

		/**
		 * Deactivate this listener, so that it won't be invoked any further.
		 *
		 * Method can be called when this listener shall be ignored from now on.<br>
		 * Useful for example if the after-commit code shall be invoked only <b>once</b>, even if there are multiple commits.
		 */
		public void deactivate()
		{
			active = false;
		}

		/**
		 *
		 * @return <code>true</code>, unless {@link #deactivate()} has been called at least once. from there on, it always returns <code>false</code>.
		 */
		public boolean isActive()
		{
			return active;
		}

		@Builder(buildMethodName = "register")
		private RegisterListenerRequest(
				@NonNull final TrxEventTiming timing,
				@NonNull final EventHandlingMethod handlingMethod,
				@NonNull final ITrxListenerManager parent,
				Boolean registerWeakly,
				Boolean invokeMethodJustOnce,
				Supplier<String> toStringSupplier)
		{
			this.parent = parent;
			this.timing = timing;
			this.registerWeakly = registerWeakly != null ? registerWeakly : true;
			this.invokeMethodJustOnce = invokeMethodJustOnce != null ? invokeMethodJustOnce : true;
			this.handlingMethod = handlingMethod;

			this.toStringSupplier = toStringSupplier != null ? toStringSupplier : () -> toString();
			parent.registerListener(this);
		}

		@Override
		public String toString()
		{
			return toStringSupplier.get();
		}

	}

	default RegisterListenerRequestBuilder newEventListener()
	{
		return RegisterListenerRequest.builder().parent(this);
	}

	void registerListener(RegisterListenerRequest listener);

	void fireBeforeCommit(ITrx trx);

	void fireAfterCommit(ITrx trx);

	void fireAfterRollback(ITrx trx);

	void fireAfterClose(ITrx trx);
}
