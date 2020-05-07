package de.metas.migration.scanner.impl;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Comparator;
import java.util.Iterator;
import java.util.TreeSet;

import org.junit.Test;

import com.google.common.collect.ImmutableList;

import de.metas.migration.IScript;
import de.metas.migration.impl.LocalScript;

/*
 * #%L
 * de.metas.migration.base
 * %%
 * Copyright (C) 2015 metas GmbH
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

public class GloballyOrderedScannerDecoratorTests
{
	/**
	 * See {@link GloballyOrderedScannerDecorator#extractSequenceNumber(String)} for the tested behavior.
	 */
	@Test
	public void testExtractSequenceNumber()
	{
		// return prefix before first '_'
		assertThat(GloballyOrderedScannerDecorator.extractSequenceNumber("1234_sys_test.sql"), is("1234"));

		// no '_' => full name
		assertThat(GloballyOrderedScannerDecorator.extractSequenceNumber("1234-sys-test.sql"), is("1234-sys-test.sql"));

		// empty => empty
		assertThat(GloballyOrderedScannerDecorator.extractSequenceNumber(""), is(""));

		// empty prefix (just '_') => full name
		assertThat(GloballyOrderedScannerDecorator.extractSequenceNumber("_sys_test.sql"), is("_sys_test.sql"));

		assertThat(GloballyOrderedScannerDecorator.extractSequenceNumber("5441640_sys_09848_add_table_AD_JAXRS_Endpoint.sql"), is("5441640"));
		assertThat(GloballyOrderedScannerDecorator.extractSequenceNumber("5442050_sys_09628_add_AD_JavaClasses_and_JaxRs_Endpoints.sql"), is("5442050"));
	}

	/**
	 * Verified that the files are returned in the order of their sequence number
	 */
	@Test
	public void testSupplier()
	{
		final InputStream is1 = new ByteArrayInputStream("somecommand".getBytes(StandardCharsets.UTF_8));
		final IScript script1 = new LocalScript("10-test", "10-de.metas.adempiere", "5442050_sys_09628_add_AD_JavaClasses_and_JaxRs_Endpoints.sql", is1);

		final InputStream is2 = new ByteArrayInputStream("somecommand".getBytes(StandardCharsets.UTF_8));
		final IScript script2 = new LocalScript("20-test", "20-de.metas.jax.rs", "5441640_sys_09848_add_table_AD_JAXRS_Endpoint.sql", is2);


		final GloballyOrderedScannerDecorator testee = new GloballyOrderedScannerDecorator(PlainListScriptScanner.fromScripts(ImmutableList.<IScript>of(script1, script2)));
		final Iterator<IScript> iterator = testee.lexiographicallyOrderedScriptsSupplier.get();
		assertThat(iterator.hasNext(), is(true));
		assertThat(iterator.next(), is(script2));
		assertThat(iterator.hasNext(), is(true));
		assertThat(iterator.next(), is(script1));
	}

	/**
	 * Just to visualize {@link TreeSet}'s behavior: if two different objects compare to 0, then they can't both be added.
	 * Note: we don't use TreeSet anymore, but this test is still nice, in case we'll reconsider it :-).
	 */
	@Test
	public void testTreeSet()
	{
		final TreeSet<String> set = new TreeSet<String>(new Comparator<String>()
		{
			@Override
			public int compare(final String o1, final String o2)
			{
				return o1.substring(0, 1).compareTo(o2.substring(0, 1));
			}
		});

		set.add("String1");
		set.add("String2");

		assertThat(set.size(), is(1));
	}
}
