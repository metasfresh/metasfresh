/**
 * 
 */
package de.metas.impexp.bpartner;

import java.util.ArrayList;
import java.util.List;

import de.metas.vertical.pharma.model.I_I_Pharma_BPartner;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-pharma
 * %%
 * Copyright (C) 2019 metas GmbH
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
final public class IFABPartnerContext
{
		private I_I_Pharma_BPartner previousImportRecord = null;
		private List<I_I_Pharma_BPartner> previousImportRecordsForSameBP = new ArrayList<>();

		public I_I_Pharma_BPartner getPreviousImportRecord()
		{
			return previousImportRecord;
		}

		public void setPreviousImportRecord(@NonNull final I_I_Pharma_BPartner previousImportRecord)
		{
			this.previousImportRecord = previousImportRecord;
		}

		public int getPreviousC_BPartner_ID()
		{
			return previousImportRecord == null ? -1 : previousImportRecord.getC_BPartner_ID();
		}

		public String getPreviousBPValue()
		{
			return previousImportRecord == null ? null : previousImportRecord.getb00adrnr();
		}

		public List<I_I_Pharma_BPartner> getPreviousImportRecordsForSameBP()
		{
			return previousImportRecordsForSameBP;
		}

		public void clearPreviousRecordsForSameBP()
		{
			previousImportRecordsForSameBP = new ArrayList<>();
		}

		public void collectImportRecordForSameBP(@NonNull final I_I_Pharma_BPartner importRecord)
		{
			previousImportRecordsForSameBP.add(importRecord);
		}
	
}
