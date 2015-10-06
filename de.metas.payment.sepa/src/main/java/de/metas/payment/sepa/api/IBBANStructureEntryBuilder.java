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


import de.metas.builder.ILineBuilder;
import de.metas.payment.sepa.wrapper.BBANStructureEntry;
import de.metas.payment.sepa.wrapper.BBANStructureEntry.BBANCodeEntryType;
import de.metas.payment.sepa.wrapper.BBANStructureEntry.EntryCharacterType;

/**
 * @author cg
 *
 */
public interface IBBANStructureEntryBuilder extends ILineBuilder
{
	/**
	 * Creates the {@link BBANStructureEntry}.
	 * 
	 * @return
	 */
	BBANStructureEntry	 create();


	public IBBANStructureEntryBuilder setCodeType(BBANCodeEntryType codeType);

	public IBBANStructureEntryBuilder setCharacterType(EntryCharacterType characterType);

	public IBBANStructureEntryBuilder setLength(int length);

	public IBBANStructureEntryBuilder setSeqNo(String seqNo);

}
