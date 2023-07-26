/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved. *
 * This program is free software; you can redistribute it and/or modify it *
 * under the terms version 2 of the GNU General Public License as published *
 * by the Free Software Foundation. This program is distributed in the hope *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. *
 * See the GNU General Public License for more details. *
 * You should have received a copy of the GNU General Public License along *
 * with this program; if not, write to the Free Software Foundation, Inc., *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA. *
 * For the text or an alternative of this public license, you may reach us *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA *
 * or via info@compiere.org or http://www.compiere.org/license.html *
 *****************************************************************************/
package org.compiere.model;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.location.CountryId;
import de.metas.location.ICountryDAO;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.FillMandatoryException;
import org.adempiere.model.InterfaceWrapperHelper;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

/**
 * Partner Location Model
 *
 * @author Jorg Janke
 * @version $Id: MBPartnerLocation.java,v 1.3 2006/07/30 00:51:03 jjanke Exp $
 */
public class MBPartnerLocation extends X_C_BPartner_Location
{

	private static final IQueryBL queryBL = Services.get(IQueryBL.class);
	private static final IBPartnerDAO bpartnerDAO = Services.get(IBPartnerDAO.class);

	public MBPartnerLocation(final Properties ctx, final int C_BPartner_Location_ID, final String trxName)
	{
		super(ctx, C_BPartner_Location_ID, trxName);
		if (is_new())
		{
			setName(".");
			//
			setIsShipTo(true);
			setIsRemitTo(true);
			setIsPayFrom(true);
			setIsBillTo(true);
		}
	}

	@Deprecated
	public MBPartnerLocation(final I_C_BPartner bp)
	{
		this(InterfaceWrapperHelper.getCtx(bp),
				0,
				InterfaceWrapperHelper.getTrxName(bp));
		setClientOrg(InterfaceWrapperHelper.getPO(bp));
		// may (still) be 0
		set_ValueNoCheck("C_BPartner_ID", bp.getC_BPartner_ID());
	}

	public MBPartnerLocation(final Properties ctx, final ResultSet rs, final String trxName)
	{
		super(ctx, rs, trxName);
	}

	@Override
	public String toString()
	{
		return "MBPartnerLocation[ID=" + get_ID()
				+ ",C_Location_ID=" + getC_Location_ID()
				+ ",Name=" + getName()
				+ "]";
	}

	@Override
	protected boolean beforeSave(final boolean newRecord)
	{
		if (getC_Location_ID() <= 0)
		{
			throw new FillMandatoryException(COLUMNNAME_C_Location_ID);
		}

		// Set New Name only if new record
		if (newRecord)
		{
			final int cBPartnerId = getC_BPartner_ID();
			setName(MakeUniqueNameCommand.builder()
					.name(getName())
					.address(getC_Location())
					.companyName(bpartnerDAO.getBPartnerNameById(BPartnerId.ofRepoId(cBPartnerId)))
					.existingNames(getOtherLocationNames(cBPartnerId, getC_BPartner_Location_ID()))
					.maxLength(getPOInfo().getFieldLength(I_C_BPartner_Location.COLUMNNAME_Name))
					.build()
					.execute());
		}

		return true;
	}

	private static List<String> getOtherLocationNames(
			final int bpartnerId,
			final int bpartnerLocationIdToExclude)
	{
		return queryBL
				.createQueryBuilder(I_C_BPartner_Location.class)
				.addEqualsFilter(I_C_BPartner_Location.COLUMNNAME_C_BPartner_ID, bpartnerId)
				.addNotEqualsFilter(I_C_BPartner_Location.COLUMNNAME_C_BPartner_Location_ID, bpartnerLocationIdToExclude)
				.create()
				.listDistinct(I_C_BPartner_Location.COLUMNNAME_Name, String.class);
	}

	@VisibleForTesting
	final static class MakeUniqueNameCommand
	{
		private final ICountryDAO countriesRepo = Services.get(ICountryDAO.class);

		private final String nameInitial;
		private final String companyName;
		private final I_C_Location address;
		private final List<String> existingNames;
		private final int maxLength;

		@Builder
		private MakeUniqueNameCommand(
				@Nullable final String name,
				@Nullable final String companyName,
				@NonNull final I_C_Location address,
				@Nullable final List<String> existingNames,
				final int maxLength)
		{
			this.companyName = companyName;
			this.address = address;
			this.existingNames = existingNames != null ? existingNames : ImmutableList.of();
			this.maxLength = maxLength > 0 ? maxLength : Integer.MAX_VALUE;

			if (Check.isEmpty(name, true) || ".".equals(name))
			{
				this.nameInitial = null;
			}
			else
			{
				this.nameInitial = name.trim();
			}
		}

		public String execute()
		{
			final String name = !Check.isEmpty(this.nameInitial, true)
					? this.nameInitial.trim()
					: buildDefaultName();

			return truncateAndMakeUnique(name);
		}

		private String buildDefaultName()
		{
			String defaultName = "";

			//
			// City
			defaultName = appendToName(defaultName, address.getCity());

			//
			// Address1
			defaultName = appendToName(defaultName, address.getAddress1());

			// Company Name
			defaultName = appendToName(defaultName, companyName);
			if (isValidUniqueName(defaultName))
			{
				return defaultName;
			}

			//
			// Address2
			{
				defaultName = appendToName(defaultName, address.getAddress2());
				if (isValidUniqueName(defaultName))
				{
					return defaultName;
				}
			}

			//
			// Address3
			{
				defaultName = appendToName(defaultName, address.getAddress3());
				if (isValidUniqueName(defaultName))
				{
					return defaultName;
				}
			}

			//
			// Address4
			{
				defaultName = appendToName(defaultName, address.getAddress4());
				if (isValidUniqueName(defaultName))
				{
					return defaultName;
				}
			}

			//
			// Country
			if (defaultName.isEmpty())
			{
				final CountryId countryId = CountryId.ofRepoId(address.getC_Country_ID());
				final String countryName = countriesRepo.getCountryNameById(countryId).getDefaultValue();
				defaultName = appendToName(defaultName, countryName);
			}

			return defaultName;
		}

		private static String appendToName(final String name, final String namePartToAppend)
		{
			if (name == null || name.isEmpty())
			{
				return namePartToAppend != null ? namePartToAppend.trim() : "";
			}
			else if (Check.isEmpty(namePartToAppend, true))
			{
				return name.trim();
			}
			else
			{
				return name.trim() + " " + namePartToAppend.trim();
			}
		}

		private boolean isValidUniqueName(final String name)
		{
			return !Check.isEmpty(name, true)
					&& !existingNames.contains(name);
		}

		private String truncateAndMakeUnique(@NonNull final String name)
		{
			Check.assumeNotEmpty(name, "name is not empty");

			int i = 2;
			String nameUnique = StringUtils.trunc(name, maxLength);
			while (existingNames.contains(nameUnique))
			{
				final String suffix = " (" + i + ")";
				nameUnique = StringUtils.trunc(name, maxLength - suffix.length()) + suffix;
				i++;
			}

			return nameUnique;
		}

	}
}
