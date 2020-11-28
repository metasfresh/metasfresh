/**
 * 
 */
package de.metas.payment.sepa.api.impl;

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


import de.metas.payment.sepa.api.IBBANStructureBuilder;
import de.metas.payment.sepa.api.IBBANStructureEntryBuilder;
import de.metas.payment.sepa.wrapper.BBANStructure;
import de.metas.payment.sepa.wrapper.BBANStructureEntry;
import de.metas.payment.sepa.wrapper.BBANStructureEntry.BBANCodeEntryType;
import de.metas.payment.sepa.wrapper.BBANStructureEntry.EntryCharacterType;

/**
 * @author cg
 *
 */
public class BBANStructureEntryBuilder implements IBBANStructureEntryBuilder
{
	private BBANCodeEntryType codeType;
	private EntryCharacterType characterType;
	private int length;
	private String seqNo;
	private IBBANStructureBuilder parent;
	
	private BBANStructureEntry entry;
	
	public BBANStructureEntryBuilder(BBANStructureBuilder parent)
	{
		this.parent = parent;
		entry = new BBANStructureEntry();
	}
	
	@Override
	public BBANStructureEntry create()
	{
		entry.setCharacterType(characterType);
		entry.setCodeType(codeType);
		entry.setLength(length);
		entry.setSeqNo(seqNo);
		
		return entry;
	}
	
	@Override
	public IBBANStructureEntryBuilder setCodeType(BBANCodeEntryType codeType)
	{
		this.codeType = codeType;
		return this;
	}

	@Override
	public IBBANStructureEntryBuilder setCharacterType(EntryCharacterType characterType)
	{
		this.characterType = characterType;
		return this;
	}

	@Override
	public IBBANStructureEntryBuilder setLength(int length)
	{
		this.length = length;
		return this;
	}

	@Override
	public IBBANStructureEntryBuilder setSeqNo(String seqNo)
	{
		this.seqNo = seqNo;
		return this;
	}

	public void create(BBANStructure _BBANStructure)
	{
		_BBANStructure.addEntry(entry);
	}

	public final IBBANStructureBuilder getParent()
	{
		return parent;
	}

}
