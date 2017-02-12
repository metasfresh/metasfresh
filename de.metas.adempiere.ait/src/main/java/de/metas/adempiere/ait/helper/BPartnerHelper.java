package de.metas.adempiere.ait.helper;

/*
 * #%L
 * de.metas.adempiere.ait
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


import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.compiere.model.I_C_Location;
import org.compiere.model.MBPartner;
import org.compiere.model.Query;
import org.compiere.util.Trx;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.Logger;

import de.metas.adempiere.model.I_C_BP_Group;
import de.metas.adempiere.model.I_C_BPartner_Location;
import de.metas.interfaces.I_C_BPartner;
import de.metas.logging.LogManager;
import de.metas.logging.LogManager;

public class BPartnerHelper
{
	protected static final Logger logger = LogManager.getLogger(BPartnerHelper.class);

	private final IHelper parent;

	public BPartnerHelper(IHelper parent)
	{
		this.parent = parent;
	}

	/**
	 * Retrieves/creates a C_BPartner by value
	 * 
	 * @param bpValue
	 * @return
	 * 
	 * @deprecated Some modules require "simple" bpValues such as 8-digit values to work (currently the only known example is the ESR module). If integration tests create BPartners and give them
	 *             explicit values -- instead of letting AD_Sequence do that job -- this might either lead to errors on such modules or to undesired collisions with existing, "real" BPartners. For
	 *             this reason, AIT developers are advised not to assign explicit BPartner Values at all, unless they know what they do.
	 */
	@Deprecated
	public I_C_BPartner getC_BPartner(final String bpValue)
	{
		return getC_BPartner(bpValue, null);
	}

	public I_C_BPartner getC_BPartnerByName(final String bpName)
	{
		return getC_BPartner(null, bpName);
	}

	public <T extends org.compiere.model.I_C_BPartner> T getC_BPartnerByName(final String bpName, final Class<T> clazz)
	{
		return InterfaceWrapperHelper.create(getC_BPartner(null, bpName), clazz);
	}

	public I_C_BPartner getC_BPartner(final TestConfig testConfig)
	{
		return getC_BPartner(testConfig.getC_BPartner_Value(), testConfig.getC_BPartner_Name());
	}

	public <T extends org.compiere.model.I_C_BPartner> T getC_BPartner(final TestConfig testConfig, final Class<T> clazz)
	{
		return InterfaceWrapperHelper.create(getC_BPartner(testConfig.getC_BPartner_Value(), testConfig.getC_BPartner_Name()), clazz);
	}

	private I_C_BPartner getC_BPartner(final String bpValue, final String bpName)
	{

		final Properties ctx = parent.getCtx();

		final String bpValueFinal;
		if (!Check.isEmpty(bpValue, true))
		{
			bpValueFinal = parent.parseItemName(bpValue);
		}
		else
		{
			bpValueFinal = null;
		}

		final String bpNameFinal;
		final String whereClause;

		final Object[] queryParam = new Object[1];

		if (Check.isEmpty(bpName, true))
		{
			Assert.assertNotNull("bpValue shall not be null if bpName is also null", bpValue);

			bpNameFinal = parent.parseItemName(bpValue); //
			whereClause = I_C_BPartner.COLUMNNAME_Value + "=?";
			queryParam[0] = parent.parseItemName(bpValue);

			logger.warn("Creating a BPartner without explicit name has been deprecated!\n"
					+ new AdempiereException());
		}
		else
		{
			bpNameFinal = parent.parseItemName(bpName);

			whereClause = I_C_BPartner.COLUMNNAME_Name + "=?";
			queryParam[0] = parent.parseItemName(bpNameFinal);
		}

		MBPartner bpPO = new Query(ctx, I_C_BPartner.Table_Name, whereClause, Trx.TRXNAME_None)
				.setParameters(queryParam)
				.setClient_ID()
				.firstOnly();

		if (bpPO == null)
		{
			bpPO = new MBPartner(ctx);
			if (bpValueFinal != null)
			{
				bpPO.setValue(bpValueFinal);
			}
			bpPO.setName(bpNameFinal);
			bpPO.setC_BP_Group_ID(getC_BP_Group(IHelper.DEFAULT_BPGroupValue).getC_BP_Group_ID());
			bpPO.setDescription(parent.getGeneratedBy());

			InterfaceWrapperHelper.save(bpPO);
			logger.info("Create BP: " + bpPO);
		}

		final I_C_BPartner bp = InterfaceWrapperHelper.create(bpPO, I_C_BPartner.class);

		if (bpPO.getLocations(false).length == 0)
		{
			createBPLocation(bp, "Main");
			bpPO.getLocations(true); // force cache reset/reload locations
		}

		return bp;
	}

	public I_C_BPartner_Location createBPLocation(I_C_BPartner bp, String locName)
	{
		return createBPLocation(bp, locName, true);
	}

	public I_C_BPartner_Location createBPLocation(I_C_BPartner bp, String locName, boolean save)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(bp);
		final String trxName = InterfaceWrapperHelper.getTrxName(bp);

		I_C_BPartner_Location bpl = InterfaceWrapperHelper.create(ctx, I_C_BPartner_Location.class, trxName);
		bpl.setAD_Org_ID(bp.getAD_Org_ID());
		bpl.setC_BPartner_ID(bp.getC_BPartner_ID());
		bpl.setC_Location_ID(createLocation(ctx, trxName).getC_Location_ID());
		bpl.setName(locName);

		if (save)
		{
			InterfaceWrapperHelper.save(bpl);
			logger.info("Create BP Location for " + bp + ": " + bpl);
		}
		return bpl;
	}

	/**
	 * Creates a dummy C_Location record
	 * 
	 * @param ctx
	 * @param trxName
	 * @return
	 */
	public I_C_Location createLocation(Properties ctx, String trxName)
	{
		I_C_Location loc = InterfaceWrapperHelper.create(ctx, I_C_Location.class, trxName);
		InterfaceWrapperHelper.save(loc);
		return loc;
	}

	private I_C_BP_Group getC_BP_Group(String value)
	{
		final Properties ctx = parent.getCtx();

		final String valueFinal = parent.parseItemName(value);
		I_C_BP_Group bpg = new Query(ctx, I_C_BP_Group.Table_Name, I_C_BP_Group.COLUMNNAME_Value + "=?", null)
				.setParameters(valueFinal)
				.setClient_ID()
				.firstOnly(I_C_BP_Group.class);
		if (bpg == null)
		{
			bpg = InterfaceWrapperHelper.create(ctx, I_C_BP_Group.class, null);
			bpg.setValue(valueFinal);
			bpg.setName(valueFinal);
			bpg.setDescription(parent.getGeneratedBy());
			InterfaceWrapperHelper.save(bpg);
		}
		return bpg;
	}
}
