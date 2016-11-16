package org.compiere.report.email.service.impl;

import org.adempiere.util.api.IParams;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.MBPartner;
import org.compiere.model.MUser;
import org.compiere.report.email.service.IEmailParameters;
import org.compiere.util.Env;
import org.slf4j.Logger;

import de.metas.letters.model.MADBoilerPlate;
import de.metas.logging.LogManager;
import de.metas.process.ProcessInfo;

/**
 * Returns generic email parameters for a process that contains a
 * {@link I_C_BPartner#COLUMNNAME_C_BPartner_ID} as a process parameter.
 * 
 * @author ts
 * 
 */
public final class BPartnerEmailParams implements IEmailParameters
{

	private static final Logger logger = LogManager.getLogger(BPartnerEmailParams.class);

	private final String attachmentPrefix;
	private final static MADBoilerPlate DEFAULT_TEXT_PRESET = null;
	private final String exportFilePrefix;
	private final MUser from;
	private final static String MESSAGE = null;
	private final String subject;
	private final String title;
	private final String to;

	public BPartnerEmailParams(final ProcessInfo pi, final IParams params)
	{
		to = updateTo(params);

		attachmentPrefix = pi.getTitle();
		title = pi.getTitle();
		subject = pi.getTitle();
		exportFilePrefix = pi.getTitle();

		from = MUser.get(Env.getCtx(), Env.getAD_User_ID(Env.getCtx()));
	}

	private String updateTo(final IParams params)
	{
		String toTmp = "";

		if (!params.hasParameter(I_C_BPartner.COLUMNNAME_C_BPartner_ID))
		{
			throw new IllegalArgumentException("Process instance doesn't contain a business partner as parameter");
		}

		// find the C_BPartner_ID parameter
		final int bpartnerId = params.getParameterAsInt(I_C_BPartner.COLUMNNAME_C_BPartner_ID);
		if (bpartnerId <= 0)
		{
			logger.info("Process parameter " + I_C_BPartner.COLUMNNAME_C_BPartner_ID + " didn't contain a value");
		}
		else
		{
			final int userId = MBPartner.getDefaultContactId(bpartnerId);
			if (userId > 0)
			{
				final MUser contanct = MUser.get(Env.getCtx(), userId);

				if (contanct.getEMail() == null || "".equals(contanct.getEMail()))
				{
					logger.info("Default contact " + contanct + " doesn't have an email address");
				}
				else
				{
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
	public String getAttachmentPrefix(final String defaultValue)
	{
		return attachmentPrefix;
	}

	/**
	 * @return <code>null</code>
	 */
	@Override
	public MADBoilerPlate getDefaultTextPreset()
	{
		return DEFAULT_TEXT_PRESET;
	}

	/**
	 * @return the title of the process
	 */
	@Override
	public String getExportFilePrefix()
	{
		return exportFilePrefix;
	}

	@Override
	public MUser getFrom()
	{
		return from;
	}

	/**
	 * @return <code>null</code>
	 */
	@Override
	public String getMessage()
	{
		return MESSAGE;
	}

	/**
	 * @return the title of the process
	 */
	@Override
	public String getSubject()
	{
		return subject;
	}

	/**
	 * @return the title of the process
	 */
	@Override
	public String getTitle()
	{
		return title;
	}

	@Override
	public String getTo()
	{
		return to;
	}

}
