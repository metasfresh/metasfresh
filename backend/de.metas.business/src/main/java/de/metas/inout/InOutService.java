/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2024 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

package de.metas.inout;

import com.google.common.annotations.VisibleForTesting;
import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.Adempiere;
import org.compiere.model.I_M_InOut;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InOutService
{
    @NonNull private final IDocumentBL documentBL = Services.get(IDocumentBL.class);
    @NonNull private final InOutRepository inOutRepository;

    @VisibleForTesting
    public static InOutService newInstanceForUnitTesting()
    {
        Adempiere.assertUnitTestMode();
        return new InOutService(InOutRepository.newInstanceForUnitTesting());
    }

    @NonNull
    public InOut getById (@NonNull final InOutId inOutId)
    {
        return inOutRepository.getById(inOutId);
    }

    @NonNull
    public I_M_InOut getRecordById (@NonNull final InOutId inOutId)
    {
        return InterfaceWrapperHelper.load(inOutId, I_M_InOut.class);
    }

    public I_M_InOut save(final InOut inOut)
    {
        return inOutRepository.save(inOut);
    }

    public void completeIt(final I_M_InOut inOutRecord)
    {
        documentBL.processEx(inOutRecord, IDocument.ACTION_Complete);
    }
}
