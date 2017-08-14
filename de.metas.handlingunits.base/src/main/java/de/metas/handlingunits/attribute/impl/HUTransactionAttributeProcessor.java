package de.metas.handlingunits.attribute.impl;

/*
 * #%L
 * de.metas.handlingunits.base
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
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import de.metas.logging.LogManager;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.lang.IReference;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.util.TimeUtil;

import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.attribute.IHUTransactionAttributeProcessor;
import de.metas.handlingunits.attribute.exceptions.InvalidAttributeValueException;
import de.metas.handlingunits.attribute.spi.IHUTrxAttributeProcessor;
import de.metas.handlingunits.attribute.spi.impl.HUTrxAttributeProcessor_ASI;
import de.metas.handlingunits.attribute.spi.impl.HUTrxAttributeProcessor_HU;
import de.metas.handlingunits.hutransaction.IHUTransactionAttribute;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Trx_Attribute;
import de.metas.handlingunits.model.I_M_HU_Trx_Hdr;
import de.metas.handlingunits.model.I_M_HU_Trx_Line;
import de.metas.handlingunits.model.X_M_HU_Trx_Attribute;

public class HUTransactionAttributeProcessor implements IHUTransactionAttributeProcessor
{
	private static final String DYNATTR_ReferencedObject = HUTransactionAttributeProcessor.class.getName() + "#ReferencedObject";

	private static final transient Logger logger = LogManager.getLogger(HUTransactionAttributeProcessor.class);

	private final IHUContext huContext;

	private IReference<I_M_HU_Trx_Hdr> _trxHdrRef;

	private final Map<String, IHUTrxAttributeProcessor> tableName2trxAttributeProcessors = new HashMap<String, IHUTrxAttributeProcessor>();

	//
	// Config: shall we also save M_HU_Trx_Attribute?
	private static final String SYSCONFIG_saveTrxAttributes = "de.metas.handlingunits.attribute.IHUTransactionAttributeProcessor.SaveTrxAttributes";
	private static final boolean DEFAULT_saveTrxAttributes = true;
	private final boolean saveTrxAttributes;

	public HUTransactionAttributeProcessor(final IHUContext huContext)
	{
		super();

		this.huContext = huContext;

		//
		// Register default processors
		// note: if there are more processors from additions packages, we shall add a register methods to be called from the main model validator.
		tableName2trxAttributeProcessors.put(I_M_HU.Table_Name, new HUTrxAttributeProcessor_HU());
		tableName2trxAttributeProcessors.put(I_M_AttributeSetInstance.Table_Name, new HUTrxAttributeProcessor_ASI());

		//
		// Config: shall we also save M_HU_Trx_Attribute?
		saveTrxAttributes = Services.get(ISysConfigBL.class).getBooleanValue(SYSCONFIG_saveTrxAttributes, DEFAULT_saveTrxAttributes);
	}

	/**
	 * Gets the actual {@link IHUTrxAttributeProcessor} implementation to be used when processing given <code>referencedMode</code>.
	 *
	 * @param referencedModel referenced model (e.g. HU, ASI etc)
	 * @return actual processor to be used; never return null
	 * @throws AdempiereException if there is no processor which can process our referenced model
	 */
	private IHUTrxAttributeProcessor getHUTrxAttributeProcessor(final Object referencedModel)
	{
		final String tableName = InterfaceWrapperHelper.getModelTableName(referencedModel);
		final IHUTrxAttributeProcessor trxAttributeProcessor = tableName2trxAttributeProcessors.get(tableName);
		if (trxAttributeProcessor == null)
		{
			throw new AdempiereException("No Trx Processor was found."
					+ "\n referencedModel=" + referencedModel
					+ "\n tableName=" + tableName);
		}

		return trxAttributeProcessor;
	}

	@Override
	public void setM_HU_Trx_Hdr(final IReference<I_M_HU_Trx_Hdr> trxHdrRef)
	{
		Check.assumeNotNull(trxHdrRef, "trxHdrRef not null");
		_trxHdrRef = trxHdrRef;
	}

	private final I_M_HU_Trx_Hdr getM_HU_Trx_Hdr()
	{
		Check.assumeNotNull(_trxHdrRef, "trxHdrRef not null");

		final I_M_HU_Trx_Hdr trxHdr = _trxHdrRef.getValue();
		Check.assumeNotNull(trxHdr, "trxHdr not null");

		return trxHdr;
	}

	@Override
	public List<I_M_HU_Trx_Attribute> createAndProcess(final List<IHUTransactionAttribute> attributeTrxs)
	{
		if (attributeTrxs.isEmpty())
		{
			return Collections.emptyList();
		}

		final List<I_M_HU_Trx_Attribute> huTrxAttributes = new ArrayList<I_M_HU_Trx_Attribute>(attributeTrxs.size());
		for (final IHUTransactionAttribute attributeTrx : attributeTrxs)
		{
			final I_M_HU_Trx_Attribute huTrxAttribute = createTrxAttribute(attributeTrx);

			process(huTrxAttribute);

			huTrxAttributes.add(huTrxAttribute);
		}

		return huTrxAttributes;
	}

	/**
	 * Creates the actual attribute transaction ({@link I_M_HU_Trx_Attribute}) based on given candidate.
	 *
	 * NOTE: this method is not saving the attribute
	 *
	 * @param attributeTrx
	 * @return attribute transaction candidate
	 */
	private I_M_HU_Trx_Attribute createTrxAttribute(final IHUTransactionAttribute attributeTrx)
	{
		final I_M_HU_Trx_Hdr trxHdr = getM_HU_Trx_Hdr();
		final I_M_HU_Trx_Attribute huTrxAttribute = InterfaceWrapperHelper.newInstance(I_M_HU_Trx_Attribute.class, trxHdr);

		// Parent link
		huTrxAttribute.setAD_Org_ID(trxHdr.getAD_Org_ID()); // just to be sure
		huTrxAttribute.setM_HU_Trx_Hdr(trxHdr);
		huTrxAttribute.setM_HU_Trx_Line(null); // we don't have a linked line

		// Operation
		huTrxAttribute.setOperation(attributeTrx.getOperation());

		// Attribute & PI Attribute
		huTrxAttribute.setM_Attribute(attributeTrx.getM_Attribute());
		huTrxAttribute.setM_HU_PI_Attribute(attributeTrx.getM_HU_PI_Attribute());
		huTrxAttribute.setM_HU_Attribute(attributeTrx.getM_HU_Attribute());

		// Value
		huTrxAttribute.setValue(attributeTrx.getValueString());
		huTrxAttribute.setValueNumber(attributeTrx.getValueNumber());
		huTrxAttribute.setValueDate(TimeUtil.asTimestamp(attributeTrx.getValueDate()));

		// Value Initial
		huTrxAttribute.setValueInitial(attributeTrx.getValueStringInitial());
		huTrxAttribute.setValueNumberInitial(attributeTrx.getValueNumberInitial());
		huTrxAttribute.setValueDateInitial(TimeUtil.asTimestamp(attributeTrx.getValueDateInitial()));

		// Reference (e.g. HU, ASI etc)
		setReferencedObject(huTrxAttribute, attributeTrx.getReferencedObject());

		// Status (just to make sure)
		huTrxAttribute.setIsActive(true);
		huTrxAttribute.setProcessed(false);

		// save(huTrxAttribute); // don't save it
		return huTrxAttribute;
	}

	private void setReferencedObject(final I_M_HU_Trx_Attribute huTrxAttribute, final Object referencedObject)
	{
		if (referencedObject == null)
		{
			return;
		}

		if (InterfaceWrapperHelper.isInstanceOf(referencedObject, I_M_HU.class))
		{
			final I_M_HU hu = InterfaceWrapperHelper.create(referencedObject, I_M_HU.class);
			huTrxAttribute.setM_HU(hu);
		}

		verifyReferenceObjectTrxName(huTrxAttribute, referencedObject);

		// TODO: we need to store the referenced object to M_HU_Trx_Attribute AD_Table_ID/Record_ID
		InterfaceWrapperHelper.setDynAttribute(huTrxAttribute, DYNATTR_ReferencedObject, referencedObject);
	}

	private Object getReferencedObject(final I_M_HU_Trx_Attribute huTrxAttribute)
	{
		final I_M_HU hu = huTrxAttribute.getM_HU();
		if (hu != null)
		{
			return hu;
		}

		final Object referencedObject = InterfaceWrapperHelper.getDynAttribute(huTrxAttribute, DYNATTR_ReferencedObject);
		Check.assumeNotNull(referencedObject, "referencedObject shall be set for {}", huTrxAttribute);
		verifyReferenceObjectTrxName(huTrxAttribute, referencedObject);

		return referencedObject;
	}

	private void verifyReferenceObjectTrxName(final I_M_HU_Trx_Attribute huTrxAttribute, final Object referencedObject)
	{
		// final String refObjTrxName = InterfaceWrapperHelper.getTrxName(referencedObject);
		// final String huTrxAttrTrxName = InterfaceWrapperHelper.getTrxName(huTrxAttribute);

		// Check.errorUnless(Objects.equals(refObjTrxName, huTrxAttrTrxName),
		// "The two objects have different trxNames: 'referencedObject'=>{}, 'huTrxAttribute'=>{}",
		// refObjTrxName, huTrxAttrTrxName);
	}

	public void process(final I_M_HU_Trx_Attribute huTrxAttribute)
	{
		//
		// Check if it was already processed
		if (huTrxAttribute.isProcessed())
		{
			return;
		}

		//
		// Actually process it
		process0(huTrxAttribute);

		//
		// Flag it as Processed and save it
		huTrxAttribute.setProcessed(true);
		save(huTrxAttribute);
	}

	private final void save(final I_M_HU_Trx_Attribute huTrxAttribute)
	{
		if (!saveTrxAttributes)
		{
			return; // don't save it
		}

		InterfaceWrapperHelper.save(huTrxAttribute);
	}

	/**
	 * Process given {@link I_M_HU_Trx_Attribute} by calling the actual processor.
	 *
	 * @param huTrxAttribute
	 */
	private void process0(final I_M_HU_Trx_Attribute huTrxAttribute)
	{
		final Object referencedModel = getReferencedObject(huTrxAttribute);
		final IHUTrxAttributeProcessor trxAttributeProcessor = getHUTrxAttributeProcessor(referencedModel);

		final String operation = huTrxAttribute.getOperation();
		if (X_M_HU_Trx_Attribute.OPERATION_Save.equals(operation))
		{
			trxAttributeProcessor.processSave(huContext, huTrxAttribute, referencedModel);
		}
		else if (X_M_HU_Trx_Attribute.OPERATION_Drop.equals(operation))
		{
			trxAttributeProcessor.processDrop(huContext, huTrxAttribute, referencedModel);
		}
		else
		{
			throw new InvalidAttributeValueException("Invalid operation on trx attribute (" + operation + "): " + huTrxAttribute);
		}
	}

	@Override
	public void reverseTrxAttributes(final I_M_HU_Trx_Line reversalTrxLine, final I_M_HU_Trx_Line trxLine)
	{
		// TODO implement trx line attributes reversal
		final AdempiereException ex = new AdempiereException("attribute transactions reversal not implemented");
		logger.warn(ex.getLocalizedMessage() + ". Skip it for now", ex);
	}
}
