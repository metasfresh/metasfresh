package org.compiere.report.email.service.impl;

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
import java.util.List;

import org.compiere.model.I_AD_PInstance_Para;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.MBPartner;
import org.compiere.model.MUser;
import org.compiere.process.ProcessInfo;
import org.compiere.report.email.service.IEmailParameters;
import org.slf4j.Logger;
import de.metas.logging.LogManager;
import org.compiere.util.Env;

import de.metas.letters.model.MADBoilerPlate;

/**
 * Returns generic email parameters for a process that contains a
 * {@link I_C_BPartner#COLUMNNAME_C_BPartner_ID} as a process parameter.
 * 
 * @author ts
 * 
 */
public final class BPartnerEmailParams implements IEmailParameters {

	private static final Logger logger = LogManager.getLogger(BPartnerEmailParams.class);

	private final String attachmentPrefix;
	private final static MADBoilerPlate DEFAULT_TEXT_PRESET = null;
	private final String exportFilePrefix;
	private final MUser from;
	private final static String MESSAGE = null;
	private final String subject;
	private final String title;
	private final String to;

	public BPartnerEmailParams(final ProcessInfo pi, final List<I_AD_PInstance_Para> params)
	{
		to = updateTo(params);

		attachmentPrefix = pi.getTitle();
		title = pi.getTitle();
		subject = pi.getTitle();
		exportFilePrefix = pi.getTitle();

		from = MUser.get(Env.getCtx(), Env.getAD_User_ID(Env.getCtx()));
	}

	private String updateTo(final List<I_AD_PInstance_Para> params)
	{
		int bPartnerId = -1;
		String toTmp = "";

		// find the C_BPartner_ID parameter
		for (I_AD_PInstance_Para param : params) {
			if (I_C_BPartner.COLUMNNAME_C_BPartner_ID.equals(param
					.getParameterName())) {

				BigDecimal paramVal = param.getP_Number();
				if (paramVal != null) {
					bPartnerId = paramVal.intValue();
					break;
				}
			}
		}
		if (bPartnerId == -1) {
			throw new IllegalArgumentException(
					"Process instance doesn't contain a business partner as parameter");
		}
		if (bPartnerId == 0) {
			logger.info("Process parameter "
					+ I_C_BPartner.COLUMNNAME_C_BPartner_ID
					+ " didn't contain a value");
		} else {

			final int userId = MBPartner.getDefaultContactId(bPartnerId);
			if (userId > 0) {
				final MUser contanct = MUser.get(Env.getCtx(), userId);

				if (contanct.getEMail() == null
						|| "".equals(contanct.getEMail())) {

					logger.info("Default contact " + contanct
							+ " doesn't have an email address");
				} else {
					toTmp = contanct.getEMail();
				}
			}
		}
		return toTmp;
	}

	/**
	 * @param defaultValue
	 *            ignored
	 * @return the title of the process
	 */
	@Override
	public String getAttachmentPrefix(final String defaultValue) {
		return attachmentPrefix;
	}

	/**
	 * @return <code>null</code>
	 */
	@Override
	public MADBoilerPlate getDefaultTextPreset() {
		return DEFAULT_TEXT_PRESET;
	}

	/**
	 * @return the title of the process
	 */
	@Override
	public String getExportFilePrefix() {
		return exportFilePrefix;
	}

	@Override
	public MUser getFrom() {
		return from;
	}

	/**
	 * @return <code>null</code>
	 */
	@Override
	public String getMessage() {
		return MESSAGE;
	}

	/**
	 * @return the title of the process
	 */
	@Override
	public String getSubject() {
		return subject;
	}

	/**
	 * @return the title of the process
	 */
	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public String getTo() {
		return to;
	}

}
