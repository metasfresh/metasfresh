/**
 *
 */
package de.metas.acct.impexp;

import java.util.ArrayList;
import java.util.List;

import org.compiere.model.I_I_ElementValue;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2018 metas GmbH
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
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public final class AccountImportContext
{
	private I_I_ElementValue previousImportRecord = null;
	private List<I_I_ElementValue> previousImportRecordsForSameAccount = new ArrayList<>();

	public I_I_ElementValue getPreviousImportRecord()
	{
		return previousImportRecord;
	}

	public void setPreviousImportRecord(final I_I_ElementValue previousImportRecord)
	{
		this.previousImportRecord = previousImportRecord;
	}

	public String getPreviousElementName()
	{
		return previousImportRecord == null ? null : previousImportRecord.getElementName();
	}

	public List<I_I_ElementValue> getPreviousImportRecordsForSameDiscountSchema()
	{
		return previousImportRecordsForSameAccount;
	}

	public void clearPreviousRecordsForSameElement()
	{
		previousImportRecordsForSameAccount = new ArrayList<>();
	}

	public void collectImportRecordForSameElement(final I_I_ElementValue importRecord)
	{
		previousImportRecordsForSameAccount.add(importRecord);
	}
}
