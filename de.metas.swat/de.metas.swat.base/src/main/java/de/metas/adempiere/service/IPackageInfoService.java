package de.metas.adempiere.service;

/*
 * #%L
 * de.metas.swat.base
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
import java.util.Properties;

import org.adempiere.util.ISingletonService;
import org.compiere.model.MPackage;

import de.metas.inout.model.I_M_InOut;

/**
 * Interface for creation of extended package info for a package and inOut.
 * 
 * @author ts
 * @see "<a href='http://dewiki908/mediawiki/index.php/Transportverpackung_%282009_0022_G61%29'>(2009_0022_G61)</>"
 */
public interface IPackageInfoService extends ISingletonService
{

	/**
	 * create label
	 * @param ctx
	 * @param inOut
	 * @param serviceCode
	 * @param pack
	 * @param trxName
	 * @return
	 */
	void createLabel(
			Properties ctx,
			I_M_InOut inOut,
			String serviceCode,
			MPackage pack,
			String trxName);
	
	/**
	 * print label
	 * @param ctx
	 * @param inOut
	 * @param pack
	 * @param M_Shipper_ID
	 * @param trxName
	 * @return
	 */
	boolean printLabel(
			Properties ctx,
			I_M_InOut inOut,
			MPackage pack,
			BigDecimal M_Shipper_ID,
			String trxName);

	void discardLabel(
			Properties ctx,
			I_M_InOut inOut,
			MPackage pack,
			BigDecimal M_Shipper_ID,
			String trxName);

}
