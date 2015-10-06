package org.eevolution.mrp.api.impl;

/*
 * #%L
 * de.metas.adempiere.libero.libero
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


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;

import org.adempiere.ad.service.IADMessageDAO;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.api.IMsgBL;
import org.compiere.model.I_AD_Message;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Warehouse;
import org.compiere.model.I_S_Resource;
import org.compiere.util.CLogger;
import org.compiere.util.Env;
import org.eevolution.api.IProductPlanningBL;
import org.eevolution.exceptions.LiberoException;
import org.eevolution.model.I_AD_Note;
import org.eevolution.model.I_PP_MRP;
import org.eevolution.model.I_PP_Order;
import org.eevolution.model.I_PP_Product_Planning;
import org.eevolution.mrp.api.IMRPContext;
import org.eevolution.mrp.api.IMRPDAO;
import org.eevolution.mrp.api.IMRPExecutor;
import org.eevolution.mrp.api.IMRPNoteBuilder;
import org.eevolution.mrp.api.IMRPNotesCollector;

/* package */class MRPNoteBuilder implements IMRPNoteBuilder
{
	public static final MRPNoteBuilder cast(final IMRPNoteBuilder builder)
	{
		return (MRPNoteBuilder)builder;
	}

	// Services
	private static final transient CLogger logger = CLogger.getCLogger(MRPNoteBuilder.class);
	private final transient IMsgBL messagesBL = Services.get(IMsgBL.class);
	private final transient IADMessageDAO adMessageDAO = Services.get(IADMessageDAO.class);
	private final transient IMRPDAO mrpDAO = Services.get(IMRPDAO.class);
	private final transient IMsgBL msgBL = Services.get(IMsgBL.class);
	private final transient IProductPlanningBL productPlanningBL = Services.get(IProductPlanningBL.class);

	//
	// Parameters: context
	private IMRPContext _mrpContext;
	private final IMRPNotesCollector _mrpNotesCollector;
	// private IMRPExecutor _mrpExecutor;
	// Parameters: MRP Code
	private String _mrpCode;
	// Parameters: MRP segment
	private I_AD_Org _org;
	private I_M_Warehouse _warehouse;
	private I_S_Resource _plant;
	private I_M_Product _product;
	// Others
	private final Map<String, Object> _parameters = new LinkedHashMap<>();
	private final List<I_PP_MRP> _mrps = new ArrayList<>();
	private Exception _exception;
	private String _comment;
	private boolean _duplicate = false;

	public MRPNoteBuilder(final IMRPNotesCollector mrpNotesCollector)
	{
		super();
		Check.assumeNotNull(mrpNotesCollector, "mrpNotesCollector not null");
		_mrpNotesCollector = mrpNotesCollector;
	}

	@Override
	public void collect()
	{
		_mrpNotesCollector.collectNote(this);
	}

	@Override
	public I_AD_Note createMRPNote()
	{
		final Properties ctx = getCtx();
		final I_AD_Org org = getAD_Org_ToUse();
		final int AD_Org_ID = org == null ? 0 : org.getAD_Org_ID();
		final I_M_Warehouse warehouse = getM_Warehouse_ToUse();
		final I_S_Resource plant = getPlant_ToUse();
		final I_M_Product product = getM_Product_ToUse();
		final int productPlanningId = getPP_Product_Planning_ID_ToUse();
		final int panner_AD_User_ID = getPlanner_AD_Use_ID_ToUse();

		//
		// Note's TextMsg
		final I_AD_Message adMessageToUse = getMRPCode_AD_Message_ToUse();
		final StringBuilder noteTextMsg = new StringBuilder();
		noteTextMsg.append(adMessageToUse.getValue())
				.append(" - ")
				.append(messagesBL.getMsg(ctx, adMessageToUse.getValue()));

		//
		// Note's Reference text
		final String noteReference;
		if (product != null)
		{
			noteReference = messagesBL.translate(ctx, "M_Product_ID") + ": " + product.getValue() + " " + product.getName();
		}
		else
		{
			noteReference = "";
		}

		//
		// Append DocumentNos to note's TextMsg
		final Set<String> documentNos = getDocumentNos_ToUse();
		if (documentNos != null && !documentNos.isEmpty())
		{
			final String documentNoPropertyName = messagesBL.translate(ctx, I_PP_Order.COLUMNNAME_DocumentNo);
			for (final String documentNo : documentNos)
			{
				noteTextMsg.append("\n" + documentNoPropertyName + ":" + documentNo);
			}
		}

		//
		// Append additional parameters to TextMsg
		final String parametersStr = getParametersAsString();
		if (!Check.isEmpty(parametersStr, true))
		{
			noteTextMsg.append("\n").append(parametersStr);
		}

		//
		// Apppend Comment (if any) to note's TextMsg
		final String comment = getComment_ToUse();
		if (!Check.isEmpty(comment, true))
		{
			noteTextMsg.append("\n").append(comment);
		}

		//
		// Append Exception (if any)
		final Exception exception = getException_ToUse();
		if (exception != null)
		{
			String exceptionStr = exception.getLocalizedMessage();
			if (exceptionStr == null || exceptionStr.length() <= 5)
			{
				// comment to small, better use whole exception string
				exceptionStr = exception.toString();
			}

			noteTextMsg.append("\nException: ").append(exceptionStr);

			// NOTE: since the exception is not logged anywhere, we are printing here to console
			// FIXME: create an AD_Issue and link it to create AD_Note.
			logger.log(Level.WARNING, exception.getLocalizedMessage(), exception);
		}

		//
		// Create AD_Note and return it
		{
			final I_PP_MRP mrp = getPP_MRP_ToUse();
			final int mrpId = mrp == null ? 0 : mrp.getPP_MRP_ID();

			final I_AD_Note note = InterfaceWrapperHelper.create(ctx, I_AD_Note.class, ITrx.TRXNAME_ThreadInherited);
			note.setAD_Org_ID(AD_Org_ID);
			note.setAD_Message_ID(adMessageToUse.getAD_Message_ID());
			note.setAD_User_ID(panner_AD_User_ID);

			// NOTE: we always shall set the AD_Table_ID=PP_MRP because else the MRP cleanup won't delete this note (see org.eevolution.mrp.api.impl.MRPNoteDAO.deleteMRPNotes(IMRPContext))
			note.setAD_Table_ID(InterfaceWrapperHelper.getTableId(I_PP_MRP.class));

			if (mrpId > 0)
			{
				note.setRecord_ID(mrpId);
			}
			note.setM_Warehouse(warehouse);
			note.setPP_Plant(plant);
			note.setM_Product(product);
			note.setPP_Product_Planning_ID(productPlanningId);
			note.setReference(noteReference);
			note.setTextMsg(noteTextMsg.toString());
			InterfaceWrapperHelper.save(note);
			return note;
		}
	}

	@Override
	public IMRPNoteBuilder setMRPContext(final IMRPContext mrpContext)
	{
		_mrpContext = mrpContext;
		return this;
	}

	@Override
	public IMRPContext getMRPContext()
	{
		// null is allowed
		return _mrpContext;
	}

	private final Properties getCtx()
	{
		final IMRPContext mrpContext = getMRPContext();
		if (mrpContext != null)
		{
			return mrpContext.getCtx();
		}

		return Env.getCtx();
	}

	@Override
	public IMRPNoteBuilder setMRPCode(final String mrpCode)
	{
		_mrpCode = mrpCode;
		return this;
	}

	/* package */String getMRPCode_ToUse()
	{
		Check.assumeNotEmpty(_mrpCode, "mrpCode not empty");
		return _mrpCode;
	}

	private I_AD_Message getMRPCode_AD_Message_ToUse()
	{
		final Properties ctx = getCtx();
		final String mrpCode = getMRPCode_ToUse();
		I_AD_Message adMessage = adMessageDAO.retrieveByValue(ctx, mrpCode);
		if (adMessage == null)
		{
			// If MRP code not found, use MRP-999 - unknown error
			adMessage = adMessageDAO.retrieveByValue(ctx, IMRPExecutor.MRP_ERROR_999_Unknown);
		}
		Check.assumeNotNull(adMessage, LiberoException.class, "adMessage not null (MRPCode: {0})", mrpCode);
		return adMessage;
	}

	private I_PP_Product_Planning getProductPlanning_ToUse()
	{
		final IMRPContext mrpContext = getMRPContext();
		if (mrpContext != null)
		{
			return mrpContext.getProductPlanning();
		}

		return null;
	}

	private final int getPP_Product_Planning_ID_ToUse()
	{
		final I_PP_Product_Planning productPlanning = getProductPlanning_ToUse();
		if (productPlanning != null)
		{
			return productPlanningBL.getBase_Product_Planning_ID(productPlanning);
		}

		return -1;
	}

	private int getPlanner_AD_Use_ID_ToUse()
	{
		final I_PP_Product_Planning productPlanning = getProductPlanning_ToUse();
		if (productPlanning == null)
		{
			return 0;
		}
		final int plannerId = productPlanning.getPlanner_ID();
		return plannerId < 0 ? 0 : plannerId;
	}

	@Override
	public IMRPNoteBuilder setAD_Org(final I_AD_Org org)
	{
		_org = org;
		return this;
	}

	private I_AD_Org getAD_Org_ToUse()
	{
		if (_org != null)
		{
			return _org;
		}

		final IMRPContext mrpContext = getMRPContext();
		if (mrpContext != null)
		{
			return mrpContext.getAD_Org();
		}

		return null;
	}

	@Override
	public IMRPNoteBuilder setM_Product(final I_M_Product product)
	{
		_product = product;
		return this;
	}

	private I_M_Product getM_Product_ToUse()
	{
		if (_product != null)
		{
			return _product;
		}

		final IMRPContext mrpContext = getMRPContext();
		if (mrpContext != null)
		{
			return mrpContext.getM_Product();
		}
		return null;
	}

	@Override
	public IMRPNoteBuilder setM_Warehouse(final I_M_Warehouse warehouse)
	{
		_warehouse = warehouse;
		return this;
	}

	private I_M_Warehouse getM_Warehouse_ToUse()
	{
		if (_warehouse != null)
		{
			return _warehouse;
		}

		final IMRPContext mrpContext = getMRPContext();
		if (mrpContext != null)
		{
			return mrpContext.getM_Warehouse();
		}

		return null;
	}

	@Override
	public IMRPNoteBuilder setPlant(final I_S_Resource plant)
	{
		_plant = plant;
		return this;
	}

	private I_S_Resource getPlant_ToUse()
	{
		if (_plant != null)
		{
			return _plant;
		}

		final IMRPContext mrpContext = getMRPContext();
		if (mrpContext != null)
		{
			return mrpContext.getPlant();
		}

		return null;
	}

	@Override
	public IMRPNoteBuilder addMRPRecord(final I_PP_MRP mrp)
	{
		if (mrp == null)
		{
			return this;
		}

		_mrps.add(mrp);

		return this;
	}

	@Override
	public IMRPNoteBuilder addMRPRecords(final List<I_PP_MRP> mrps)
	{
		if (mrps == null || mrps.isEmpty())
		{
			return this;
		}

		for (final I_PP_MRP mrp : mrps)
		{
			addMRPRecord(mrp);
		}

		return this;
	}

	@Override
	public IMRPNoteBuilder addParameter(final String parameterName, final Object parameterValue)
	{
		Check.assumeNotEmpty(parameterName, "parameterName not empty");
		_parameters.put(parameterName, parameterValue);
		return this;
	}

	/* package */String getParametersAsString()
	{
		if (_parameters == null || _parameters.isEmpty())
		{
			return "";
		}

		final StringBuilder parametersStr = new StringBuilder();
		final Properties ctx = getCtx();
		for (final Map.Entry<String, Object> param : _parameters.entrySet())
		{
			final String parameterName = param.getKey();
			String parameterDisplayName = messagesBL.translate(ctx, parameterName);
			if (Check.isEmpty(parameterDisplayName, true))
			{
				parameterDisplayName = parameterName;
			}
			final Object parameterValue = param.getValue();
			final String parameterValueStr = toParameterValueString(parameterValue);

			//
			if (parametersStr.length() > 0)
			{
				parametersStr.append("\n");
			}
			parametersStr.append(parameterDisplayName).append(": ").append(parameterValueStr);
		}

		return parametersStr.toString();
	}

	private String toParameterValueString(final Object parameterValue)
	{
		return parameterValue == null ? "" : parameterValue.toString();
	}

	@Override
	public IMRPNoteBuilder setException(final Exception exception)
	{
		_exception = exception;
		return this;
	}

	/* package */Exception getException_ToUse()
	{
		return _exception;
	}

	private Collection<I_PP_MRP> getPP_MRPs_ToUse()
	{
		final Map<Integer, I_PP_MRP> mrps = new LinkedHashMap<>();

		//
		// Get MRP records that were explicitelly set
		for (final I_PP_MRP mrp : _mrps)
		{
			mrps.put(mrp.getPP_MRP_ID(), mrp);
		}
		// If we have MRP records explicitelly assigned, there is no point checking the MRP Context
		// because developer specified them and the ones from MRP context could be not relevant
		if (!mrps.isEmpty())
		{
			return mrps.values();
		}

		//
		// Get MRP records from context
		final IMRPContext mrpContext = getMRPContext();
		if (mrpContext != null)
		{
			final I_PP_MRP mrp = mrpContext.getPP_MRP();
			if (mrp != null)
			{
				mrps.put(mrp.getPP_MRP_ID(), mrp);
			}
		}

		return mrps.values();
	}

	/**
	 * Facade for {@link #getPP_MRPs_ToUse()}. It will return the first {@link I_PP_MRP} record from there or <code>null</code> if there are no MRP records.
	 *
	 * @return first MRP record or <code>null</code>
	 */
	/* package */I_PP_MRP getPP_MRP_ToUse()
	{
		final Collection<I_PP_MRP> mrps = getPP_MRPs_ToUse();
		if (mrps == null || mrps.isEmpty())
		{
			return null;
		}

		final I_PP_MRP mrp = mrps.iterator().next();
		return mrp;
	}

	private Set<String> getDocumentNos_ToUse()
	{
		final Set<String> documentNos = new HashSet<String>();
		final Collection<I_PP_MRP> mrps = getPP_MRPs_ToUse();

		for (final I_PP_MRP mrp : mrps)
		{
			if (mrp == null)
			{
				// shall not happen
				continue;
			}
			final int ppMRPId = mrp.getPP_MRP_ID();
			final String documentNo = ppMRPId > 0 ? mrpDAO.getDocumentNo(ppMRPId) : null;
			documentNos.add(documentNo);
		}

		return documentNos;
	}

	@Override
	public IMRPNoteBuilder setComment(final String comment)
	{
		_comment = comment;
		return this;
	}

	/* package */String getComment_ToUse()
	{
		if (Check.isEmpty(_comment, true))
		{
			return "";
		}

		String commentTrl = msgBL.parseTranslation(getCtx(), _comment);
		if (Check.isEmpty(commentTrl, true))
		{
			commentTrl = _comment;
		}
		return commentTrl;
	}

	@Override
	public IMRPNoteBuilder setQtyPlan(final BigDecimal qtyPlan)
	{
		addParameter("QtyPlan", qtyPlan);
		return this;
	}

	@Override
	public boolean isDuplicate()
	{
		return _duplicate;
	}

	@Override
	public void setDuplicate()
	{
		_duplicate = true;
	}
}
