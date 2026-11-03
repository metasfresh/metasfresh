/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2026 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

package org.adempiere.test;

import lombok.experimental.UtilityClass;

import java.lang.ref.WeakReference;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Helps tests about objects which are referenced weakly, by running the GC in a bounded loop.
 */
@UtilityClass
public class GarbageCollectorTestHelper
{
	private static final int MAX_GC_ATTEMPTS = 20;

	/**
	 * Runs the GC until the given reference is cleared, but at most {@value #MAX_GC_ATTEMPTS} times.
	 * <p>
	 * Note: a reference which stays set after this method can mean that its referent is still strongly reachable, or that the GC did not run.
	 * Call {@link #assertGarbageCollectorClearsUnreferencedObject(Supplier)} first to rule out the latter.
	 */
	public static void runGarbageCollectorUntilCleared(final WeakReference<?> ref)
	{
		for (int i = 0; i < MAX_GC_ATTEMPTS && ref.get() != null; i++)
		{
			System.gc();
		}
	}

	/**
	 * Asserts that, in this JVM, the GC clears a weak reference to an object which is not strongly reachable.
	 *
	 * @param objectFactory creates an object comparable to the one the test is about
	 */
	public static void assertGarbageCollectorClearsUnreferencedObject(final Supplier<?> objectFactory)
	{
		final WeakReference<?> ref = new WeakReference<>(objectFactory.get());
		runGarbageCollectorUntilCleared(ref);
		assertThat(ref.get()).as("GC shall clear the weak reference to an object which is not strongly reachable").isNull();
	}
}
