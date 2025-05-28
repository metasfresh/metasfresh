package de.metas.frontend_testing.expectations.assertions;

import lombok.Getter;
import lombok.NonNull;
import org.adempiere.util.lang.IAutoCloseable;

public final class AutoCloseableSoftAssertions implements IAutoCloseable
{
	@NonNull private final ThreadLocal<SoftAssertions> softAssertionsHolder;
	private boolean opened = false;
	private boolean closed = false;

	private SoftAssertions previousSoftAssertions;
	@Getter private SoftAssertions softAssertions;

	private AutoCloseableSoftAssertions(@NonNull final ThreadLocal<SoftAssertions> softAssertionsHolder)
	{
		this.softAssertionsHolder = softAssertionsHolder;
	}

	static AutoCloseableSoftAssertions createAndOpen(@NonNull final ThreadLocal<SoftAssertions> softAssertionsHolder)
	{
		final AutoCloseableSoftAssertions closeable = new AutoCloseableSoftAssertions(softAssertionsHolder);
		closeable.open();
		return closeable;
	}

	private void open()
	{
		if (opened)
		{
			throw new IllegalStateException("Already opened");
		}
		opened = true;

		softAssertions = new SoftAssertions();
		previousSoftAssertions = softAssertionsHolder.get();
		softAssertionsHolder.set(softAssertions);
	}

	@Override
	public void close()
	{
		if (closed)
		{
			return;
		}
		closed = true;

		final SoftAssertions softAssertions = this.softAssertions;
		softAssertionsHolder.set(previousSoftAssertions);
		this.softAssertions = null;

		if (previousSoftAssertions != null)
		{
			previousSoftAssertions.collectFailures(softAssertions);
		}
		else
		{
			softAssertions.assertAll();
		}
	}
}
