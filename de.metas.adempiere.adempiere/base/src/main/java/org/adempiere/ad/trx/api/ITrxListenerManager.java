package org.adempiere.ad.trx.api;

import java.util.function.Supplier;

import com.google.common.base.MoreObjects;
import com.google.common.base.MoreObjects.ToStringHelper;

import lombok.Getter;
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

	public class RegisterListenerRequest
	{
		@Getter
		private final TrxEventTiming timing;
		@Getter
		private boolean registerWeakly = false;
		@Getter
		private boolean invokeMethodJustOnce = true;
		@Getter
		private EventHandlingMethod handlingMethod;
		@Getter
		private Supplier<String> additionalToStringInfo = null;

		private boolean active = true;

		private final ITrxListenerManager parent;

		private RegisterListenerRequest(
				@NonNull final ITrxListenerManager parent,
				@NonNull final TrxEventTiming timing)
		{
			this.parent = parent;
			this.timing = timing;
		}

		/**
		 * The default is {@code false}; call this method with {@code true} if know what you do.
		 * Might introduce hard to track unit test problems.
		 */
		public RegisterListenerRequest registerWeakly(final boolean registerWeakly)
		{
			this.registerWeakly = registerWeakly;
			return this;
		}

		/**
		 * The default is {@code true}; call this method with {@code false} if you want the method to be invoked e.g. on *every* commit.
		 */
		public RegisterListenerRequest invokeMethodJustOnce(final boolean invokeMethodJustOnce)
		{
			this.invokeMethodJustOnce = invokeMethodJustOnce;
			return this;
		}

		public RegisterListenerRequest additionalToStringInfo(@NonNull final Supplier<String> additionalToStringInfo)
		{
			this.additionalToStringInfo = additionalToStringInfo;
			return this;
		}

		/**
		 * Sets the given handling method (can be lambda) and registers the listener.
		 */
		public void registerHandlingMethod(@NonNull final EventHandlingMethod handlingMethod)
		{
			this.handlingMethod = handlingMethod;
			parent.registerListener(this);
		}

		@Override
		public String toString()
		{
			final ToStringHelper toStringHelper = MoreObjects.toStringHelper(this)
					.add("timing", timing)
					.add("active", active)
					.add("invokeMethodJustOnce", invokeMethodJustOnce)
					.add("registerWeakly", registerWeakly);
			if (additionalToStringInfo != null)
			{
				toStringHelper.add("additionalToStringInfo", additionalToStringInfo.get());
			}
			return toStringHelper.toString();
		}

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
	}

	default RegisterListenerRequest newEventListener(@NonNull final TrxEventTiming timing)
	{
		return new RegisterListenerRequest(this, timing);
	}

	/**
	 * This method shall only be called by the framework. Instead, call {@link #newEventListener()}
	 * and be sure to call {@link RegisterListenerRequest#registerHandlingMethod(EventHandlingMethod)} at the end.
	 *
	 * @param listener
	 */
	void registerListener(RegisterListenerRequest listener);

	boolean canRegisterOnTiming(TrxEventTiming timing);

	/**
	 * This method shall only be called by the framework.
	 */
	void fireBeforeCommit(ITrx trx);

	/**
	 * This method shall only be called by the framework.
	 */
	void fireAfterCommit(ITrx trx);

	/**
	 * This method shall only be called by the framework.
	 */
	void fireAfterRollback(ITrx trx);

	/**
	 * This method shall only be called by the framework.
	 */
	void fireAfterClose(ITrx trx);
}
