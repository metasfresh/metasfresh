package de.metas.handlingunits.grai;

import com.google.common.annotations.VisibleForTesting;
import lombok.NonNull;
import lombok.ToString;

import java.util.concurrent.atomic.AtomicInteger;

@ToString
public class DummyGRAIProvider
{
	@NonNull private final DummyGRAITemplate template;
	@NonNull private final AtomicInteger nextCounter;

	public DummyGRAIProvider(@NonNull final DummyGRAITemplate template, final int startCounter)
	{
		this.template = template;
		this.nextCounter = new AtomicInteger(startCounter);
	}

	@NonNull
	public GRAI nextGRAI()
	{
		final int counter = nextCounter.getAndIncrement();
		return template.buildGRAI(counter);
	}

	@VisibleForTesting
	public int getNextCounter() {return nextCounter.get();}
}
