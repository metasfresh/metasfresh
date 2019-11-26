package de.metas.materialtracking.model.validator;

/*
 * #%L
 * de.metas.materialtracking
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

import java.util.ArrayList;
import java.util.List;

import org.adempiere.ad.modelvalidator.annotations.Init;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_InOut;

import de.metas.inout.IInOutBL;
import de.metas.inout.IInOutDAO;
import de.metas.inout.model.I_M_InOutLine;
import de.metas.materialtracking.IMaterialTrackingAttributeBL;
import de.metas.materialtracking.IMaterialTrackingBL;
import de.metas.materialtracking.model.I_M_Material_Tracking;
import de.metas.materialtracking.spi.impl.listeners.InOutLineMaterialTrackingListener;
import de.metas.util.Services;

@Interceptor(I_M_InOut.class)
public class M_InOut extends MaterialTrackableDocumentByASIInterceptor<I_M_InOut, I_M_InOutLine>
{
	@Init
	public void init()
	{
		final IMaterialTrackingBL materialTrackingBL = Services.get(IMaterialTrackingBL.class);

		materialTrackingBL.addModelTrackingListener(
				InOutLineMaterialTrackingListener.LISTENER_TableName,
				InOutLineMaterialTrackingListener.instance);
	}

	@Override
	protected final boolean isEligibleForMaterialTracking(final I_M_InOut receipt)
	{
		// Shipments are not eligible
		if (receipt.isSOTrx())
		{
			return false;
		}

		// reversals are not eligible either, because there counterpart is also unlinked
		if (Services.get(IInOutBL.class).isReversal(receipt))
		{
			return false;
		}

		return true;
	}

	/**
	 * Returns all active inout lines, including packaging lines.<br>
	 * We do include packaging lines, because we need them to have a <code>M_Material_Trackinf_Ref</code>. That's because we need to pass on the inout lines M_Material_Tracking_Id to its invoice
	 * candidate. and we want to do so in a uniform way.<br>
	 * Also note that the qtys from HUs with different M_Material_Trackings will never be aggregated into one packaging line.
	 */
	@Override
	protected List<I_M_InOutLine> retrieveDocumentLines(final I_M_InOut document)
	{
		final IInOutDAO inoutDAO = Services.get(IInOutDAO.class);
		final List<I_M_InOutLine> documentLines = new ArrayList<I_M_InOutLine>();
		for (final I_M_InOutLine iol : inoutDAO.retrieveLines(document, I_M_InOutLine.class))
		{
			documentLines.add(iol);
		}
		return documentLines;
	}

	@Override
	protected I_M_Material_Tracking getMaterialTrackingFromDocumentLineASI(final I_M_InOutLine documentLine)
	{
		final de.metas.materialtracking.model.I_M_InOutLine iolExt = InterfaceWrapperHelper.create(documentLine, de.metas.materialtracking.model.I_M_InOutLine.class);
		if (iolExt.getM_Material_Tracking_ID() > 0)
		{
			return iolExt.getM_Material_Tracking();
		}

		// fall-back in case the M_Material_Tracking_ID is not (yet) set
		final IMaterialTrackingAttributeBL materialTrackingAttributeBL = Services.get(IMaterialTrackingAttributeBL.class);

		final AttributeSetInstanceId asiId = AttributeSetInstanceId.ofRepoIdOrNone(iolExt.getM_AttributeSetInstance_ID());
		final I_M_Material_Tracking materialTracking = materialTrackingAttributeBL.getMaterialTrackingOrNull(asiId);
		return materialTracking;
	}

	@Override
	protected AttributeSetInstanceId getM_AttributeSetInstance(final I_M_InOutLine documentLine)
	{
		// shall not be called because we implement "getMaterialTrackingFromDocumentLineASI"
		throw new IllegalStateException("shall not be called");
	}
}
