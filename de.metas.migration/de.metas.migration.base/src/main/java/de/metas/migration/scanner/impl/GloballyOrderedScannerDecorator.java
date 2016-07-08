package de.metas.migration.scanner.impl;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Comparator;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.commons.lang.builder.CompareToBuilder;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;

import de.metas.migration.IScript;
import de.metas.migration.scanner.IScriptScanner;

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

/**
 * Returns all the scripts found by the internal scanner.<br>
 * They are ordered by the sequence number each of our migration scripts starts with.<br>
 * More specific: files are ordered by the prefix until the '_' char. <br>
 * If there is no '_' in the filename or the prefix is empty (i.e. filename starting with '_'), then the whole file name is compared
 * against.
 *
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class GloballyOrderedScannerDecorator extends AbstractScriptDecoratorAdapter
{
	@VisibleForTesting
	/* package */final Supplier<Iterator<IScript>> lexiographicallyOrderedScriptsSupplier = Suppliers.memoize(new Supplier<Iterator<IScript>>()
	{
		@Override
		public Iterator<IScript> get()
		{
			final SortedSet<IScript> lexiagraphicallySortedScripts = new TreeSet<IScript>(new Comparator<IScript>()
			{
				@Override
				public int compare(final IScript o1, final IScript o2)
				{
					final String o1SeqNo = extractSequenceNumber(o1.getFileName());
					final String o2SeqNo = extractSequenceNumber(o2.getFileName());

					return new CompareToBuilder()
							.append(o1SeqNo, o2SeqNo)
							.append(o1.getModuleName(), o2.getModuleName())
							.append(o1.getFileName(), o2.getFileName())

							// make damn sure that two different scripts are both added
							.append(System.identityHashCode(o1), System.identityHashCode(o2))
							.toComparison();
				}
			});

			while (getInternalScanner().hasNext())
			{
				final IScript next = getInternalScanner().next();
				lexiagraphicallySortedScripts.add(next);
			}

			//dumpTofile(lexiagraphicallySortedScripts);

			return lexiagraphicallySortedScripts.iterator();
		}
	});

	/**
	 * Examples:
	 * <ul>
	 * <li>"1234_sys_test.sql" => "1234"
	 * <li>"1234-sys-test.sql" => "1234-sys-test.sql"
	 * <li>"" => ""
	 * <li>"_sys_test.sql" => "_sys_test.sql"
	 * </ul>
	 *
	 * @param fileName
	 * @return
	 */
	@VisibleForTesting
	/* package */static String extractSequenceNumber(final String fileName)
	{
		final int indexOf = fileName.indexOf("_");
		if (indexOf <= 0)
		{
			// no '_' or empty prefix => return full name
			return fileName;
		}
		return fileName.substring(0, indexOf);
	}

	public GloballyOrderedScannerDecorator(final IScriptScanner scanner)
	{
		super(scanner);
	}

	@Override
	public boolean hasNext()
	{

		return lexiographicallyOrderedScriptsSupplier.get().hasNext();
	}

	@Override
	public IScript next()
	{
		return lexiographicallyOrderedScriptsSupplier.get().next();
	}

	@Override
	public void remove()
	{
		lexiographicallyOrderedScriptsSupplier.get().remove();
	}

	private void dumpTofile(SortedSet<IScript> lexiagraphicallySortedScripts)
	{
		FileWriter writer;
		try
		{
			writer = new FileWriter(GloballyOrderedScannerDecorator.class.getName() + "_sorted_scripts.txt");
			for (final IScript script : lexiagraphicallySortedScripts)
			{
				writer.write(script.toString()+"\n");
			}
			writer.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public String toString()
	{
		return "GloballyOrderedScannerDecorator [getInternalScanner()=" + getInternalScanner() + "]";
	}
}
