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

import java.util.List;
import java.util.Properties;

import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.ModelValidator;

import de.metas.i18n.IMsgBL;
import de.metas.materialtracking.IMaterialTrackingDAO;
import de.metas.materialtracking.IMaterialTrackingQuery;
import de.metas.materialtracking.IMaterialTrackingQuery.OnMoreThanOneFound;
import de.metas.materialtracking.model.I_M_Material_Tracking;
import de.metas.order.IOrderDAO;

@Interceptor(I_C_Order.class)
public class C_Order extends MaterialTrackableDocumentByASIInterceptor<I_C_Order, I_C_OrderLine>
{
	private static final String MSG_M_Material_Tracking_Set_In_Orderline = "M_Material_Tracking_Set_In_Orderline";

	@Override
	protected final boolean isEligibleForMaterialTracking(final I_C_Order order)
	{
		// Sales orders are not eligible
		if (order.isSOTrx())
		{
			return false;
		}

		return true;
	}

	@Override
	protected List<I_C_OrderLine> retrieveDocumentLines(final I_C_Order document)
	{
		// note: we don't have C_Order reversals to check for
		final IOrderDAO orderDAO = Services.get(IOrderDAO.class);
		final List<I_C_OrderLine> documentLines = orderDAO.retrieveOrderLines(document, I_C_OrderLine.class);
		return documentLines;
	}

	@Override
	protected I_M_AttributeSetInstance getM_AttributeSetInstance(final I_C_OrderLine documentLine)
	{
		return documentLine.getM_AttributeSetInstance();
	}

	/**
	 * @param document
	 *
	 *            In case the Bestellung has a partner and product of one line belonging to a complete material tracking and that material tracking is not set into ASI, show an error message.
	 */
	@DocValidate(
			timings = { ModelValidator.TIMING_BEFORE_COMPLETE })
	public void verifyMaterialTracking(final I_C_Order document)
	{
		final IMaterialTrackingDAO materialTrackingDao = Services.get(IMaterialTrackingDAO.class);
		final Properties ctx = InterfaceWrapperHelper.getCtx(document);
		if (document.isSOTrx())
		{
			// nothing to do
			return;
		}

		final I_C_BPartner partner = document.getC_BPartner();

		final List<I_C_OrderLine> documentLines = retrieveDocumentLines(document);
		for (final I_C_OrderLine line : documentLines)
		{
			String msg;

			if (getMaterialTrackingFromDocumentLineASI(line) != null)
			{
				continue; // a tracking is set, nothing to do
			}

			final IMaterialTrackingQuery queryVO = materialTrackingDao.createMaterialTrackingQuery();
			queryVO.setCompleteFlatrateTerm(true);
			queryVO.setProcessed(false);
			queryVO.setC_BPartner_ID(partner.getC_BPartner_ID());
			queryVO.setM_Product_ID(line.getM_Product_ID());

			// there can be many trackings for the given product and partner (different parcels), which is not a problem for this verification
			queryVO.setOnMoreThanOneFound(OnMoreThanOneFound.ReturnFirst);

			final I_M_Material_Tracking materialTracking = materialTrackingDao.retrieveMaterialTracking(ctx, queryVO);
			if (materialTracking == null)
			{
				continue; // there is no tracking that could be selected, nothing to do
			}

			msg = Services.get(IMsgBL.class).getMsg(ctx, MSG_M_Material_Tracking_Set_In_Orderline, new Object[] { document.getDocumentNo(), line.getLine() });

			throw new AdempiereException(msg);
		}
	}
}
