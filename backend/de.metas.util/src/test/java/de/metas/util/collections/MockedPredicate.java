package de.metas.util.collections;

import org.junit.jupiter.api.Disabled;

import java.util.function.Predicate;

@Disabled
public class MockedPredicate<T> implements Predicate<T>
{

	private final boolean retValue;

	public MockedPredicate(final boolean retValue)
	{
		super();
		this.retValue = retValue;
	}

	@Override
	public String toString()
	{
		return "MockedPredicate[retValue=" + retValue + "]";
	}

	@Override
	public boolean test(T value)
	{
		return retValue;
	}
}
