/**
 * 
 */
package de.metas.payment.sepa.api;

/*
 * #%L
 * de.metas.payment.sepa
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


import de.metas.builder.IBuilder;
import de.metas.payment.sepa.api.impl.BBANStructureEntryBuilder;
import de.metas.payment.sepa.wrapper.BBANStructure;

/**
 * @author cg
 *
 */
public interface IBBANStructureBuilder extends IBuilder
{
	/**
	 * Creates the {@link BBANStructure}.
	 * 
	 * @return
	 */
	BBANStructure create();
	
	/**
	 * Convenience method to create a new line builder using the {@link IBBANStructureEntryBuilder} implementation. Module specific subclasses can override this method.
	 * 
	 * @return
	 */
	IBBANStructureEntryBuilder addBBANStructureEntry();

	/**
	 * Creates a new BBANStructure Entry builder to add another entry to the BBAN structure that is currently build
	 * 
	 * @param implClazz the BBANStructure Entry  builder implementation class to be used. If unsure which class to pick, then use {@link #addBBANStructureEntry()}.
	 *            <p>
	 *            <b>IMPORTANT: when using an class that is included in another class, then this included class needs to be <code>static</code></b>
	 * @return
	 */
	<T extends BBANStructureEntryBuilder> T addBBANStructureEntry(Class<T> implClazz);
}
