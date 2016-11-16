package de.metas.letters.api.impl;

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


import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.bpartner.service.IBPartnerBL;
import org.adempiere.bpartner.service.IBPartnerDAO;
import org.adempiere.exceptions.FillMandatoryException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.proxy.Cached;
import org.compiere.model.I_AD_PInstance;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.Query;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.slf4j.Logger;

import de.metas.adempiere.report.jasper.OutputType;
import de.metas.adempiere.report.jasper.client.JRClient;
import de.metas.adempiere.util.CacheCtx;
import de.metas.document.IDocumentLocationBL;
import de.metas.document.model.IDocumentLocation;
import de.metas.letters.api.ITextTemplateBL;
import de.metas.letters.model.I_AD_BoilerPlate;
import de.metas.letters.model.I_C_Letter;
import de.metas.letters.model.I_T_Letter_Spool;
import de.metas.letters.model.LetterDocumentLocationAdapter;
import de.metas.letters.model.MADBoilerPlate;
import de.metas.letters.spi.ILetterProducer;
import de.metas.logging.LogManager;
import de.metas.process.IADPInstanceDAO;

public final class TextTemplateBL implements ITextTemplateBL
{
	private static final TextTemplateBL instance = new TextTemplateBL();
	private final transient Logger logger = LogManager.getLogger(getClass());

	private static final int AD_Process_ID_T_Letter_Spool_Print = 540278; // TODO: HARDCODED

	private final IBPartnerDAO bPartnerDAO = Services.get(IBPartnerDAO.class);
	
	public static TextTemplateBL get()
	{
		return instance;
	}

	private TextTemplateBL()
	{
	}

	@Override
	public List<I_AD_BoilerPlate> getAvailableLetterTemplates()
	{
		final Properties ctx = Env.getCtx();
		return getAvailableLetterTemplates(ctx);
	}

	@Cached(cacheName = I_AD_BoilerPlate.Table_Name)
	/* package */ List<I_AD_BoilerPlate> getAvailableLetterTemplates(@CacheCtx Properties ctx)
	{
		final String whereClause = null;

		final List<I_AD_BoilerPlate> list = new Query(ctx, I_AD_BoilerPlate.Table_Name, whereClause, ITrx.TRXNAME_None)
				.setClient_ID()
				.setApplyAccessFilterRW(false)
				.setOrderBy(I_AD_BoilerPlate.COLUMNNAME_Name)
				.list(I_AD_BoilerPlate.class);

		return list;
	}

	@Override
	public String parseText(Properties ctx, String text,
			boolean isEmbeded,
			Map<String, Object> attrs,
			String trxName)
	{
		final String textParsed = MADBoilerPlate.parseText(ctx,
				text,
				true, // isEmbeded
				attrs,
				ITrx.TRXNAME_None); // trxName
		return textParsed;
	}

	@Override
	public void setLetterBodyParsed(I_C_Letter letter, Map<String, Object> attributes)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(letter);
		final String trxName = InterfaceWrapperHelper.getTrxName(letter);

		final String letterBodyParsed = Services.get(ITextTemplateBL.class).parseText(
				ctx,
				letter.getLetterBody(),
				true, // isEmbeded
				attributes,
				trxName); // trxName

		letter.setLetterBodyParsed(letterBodyParsed);
	}

	@Override
	public void setLocationContactAndOrg(I_C_Letter letter)
	{
		//
		// Check/Set BP Location
		I_C_BPartner_Location bpl = letter.getC_BPartner_Location();
		if (bpl == null)
		{
			final Properties ctx = InterfaceWrapperHelper.getCtx(letter);
			final String trxName = InterfaceWrapperHelper.getTrxName(letter);
			
			bpl = bPartnerDAO.retrieveShipToLocation(ctx, letter.getC_BPartner_ID(), trxName);
			letter.setC_BPartner_Location(bpl);
		}
		if (bpl == null)
		{
			throw new FillMandatoryException(I_C_Letter.COLUMNNAME_C_BPartner_Location_ID);
		}

		//
		// Update BPartnerAddress string
		final IDocumentLocation docLocation = new LetterDocumentLocationAdapter(letter);
		Services.get(IDocumentLocationBL.class).setBPartnerAddress(docLocation);

		//
		// Check/Set BP Contact
		if (letter.getC_BP_Contact_ID() <= 0)
		{
			final I_AD_User bpContact = Services.get(IBPartnerBL.class).retrieveUserForLoc(bpl);
			letter.setC_BP_Contact(bpContact);
		}

		//
		// Set Letter's AD_Org_ID from BP Location
		// see http://dewiki908/mediawiki/index.php/02617:_Textvorlagen_und_Schreiben_%282012032710000082%29#User_Stories , 120 Data on Letter
		letter.setAD_Org_ID(bpl.getAD_Org_ID());
	}

	@Override
	public byte[] createPDF(I_C_Letter letter)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(letter);
		// final int clientId = Env.getAD_Client_ID(ctx);

		final int jasperProcessId = getJasperProcess_ID(letter);
		final I_AD_PInstance pinstance = Services.get(IADPInstanceDAO.class).createAD_PInstance(ctx, jasperProcessId, 0, 0);

		createLetterSpoolRecord(pinstance.getAD_PInstance_ID(), letter);

		final JRClient jrClient = JRClient.get();
		final byte[] pdf = jrClient.report(pinstance.getAD_Process_ID(),
				pinstance.getAD_PInstance_ID(),
				Env.getLanguage(ctx),
				OutputType.PDF);

		return pdf;
	}

	private int getJasperProcess_ID(I_C_Letter letter)
	{
		final I_AD_BoilerPlate textTemplate = letter.getAD_BoilerPlate();
		if (textTemplate == null)
		{
			return AD_Process_ID_T_Letter_Spool_Print;
		}

		int jasperProcessId = textTemplate.getJasperProcess_ID();
		if (jasperProcessId <= 0)
		{
			jasperProcessId = AD_Process_ID_T_Letter_Spool_Print;
		}

		return jasperProcessId;
	}

	public void createLetterSpoolRecord(int adPInstanceId, I_C_Letter letter)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(letter);
		final int clientId = Env.getAD_Client_ID(ctx);

		final String sql = "INSERT INTO " + I_T_Letter_Spool.Table_Name + "("
				+ " " + I_T_Letter_Spool.COLUMNNAME_AD_Client_ID
				+ "," + I_T_Letter_Spool.COLUMNNAME_AD_Org_ID
				+ "," + I_T_Letter_Spool.COLUMNNAME_AD_PInstance_ID
				+ "," + I_T_Letter_Spool.COLUMNNAME_SeqNo
				+ "," + I_T_Letter_Spool.COLUMNNAME_LetterSubject
				+ "," + I_T_Letter_Spool.COLUMNNAME_LetterBody
				+ "," + I_T_Letter_Spool.COLUMNNAME_C_BPartner_ID
				+ "," + I_T_Letter_Spool.COLUMNNAME_C_BPartner_Location_ID
				+ "," + I_T_Letter_Spool.COLUMNNAME_BPartnerAddress
				+ "," + I_T_Letter_Spool.COLUMNNAME_C_BP_Contact_ID
				//
				+ "," + I_T_Letter_Spool.COLUMNNAME_Created
				+ "," + I_T_Letter_Spool.COLUMNNAME_CreatedBy
				+ "," + I_T_Letter_Spool.COLUMNNAME_Updated
				+ "," + I_T_Letter_Spool.COLUMNNAME_UpdatedBy
				+ "," + I_T_Letter_Spool.COLUMNNAME_IsActive
				+ ") VALUES ("
				+ "?,?,?,?,?,?,?,?,?,?"
				+ ",getdate(),0,getdate(),0,'Y'"
				+ ")";
		DB.executeUpdateEx(sql,
				new Object[] {
						clientId,
						letter.getAD_Org_ID(), // NOTE: using the same Org as in C_Letter is very important for reports to know from where to take the Org Header
						adPInstanceId,
						10, // seqNo
						letter.getLetterSubject(),
						letter.getLetterBodyParsed(),
						letter.getC_BPartner_ID(),
						letter.getC_BPartner_Location_ID(),
						letter.getBPartnerAddress(),
						letter.getC_BP_Contact_ID(),
				},
				ITrx.TRXNAME_None);
	}

	@Override
	public boolean isEmpty(I_C_Letter letter)
	{
		if (letter == null)
		{
			return true;
		}

		if (Check.isEmpty(letter.getLetterSubject()) && Check.isEmpty(letter.getLetterBody()))
		{
			return true;
		}

		return false;
	}

	@Override
	public void setAD_BoilerPlate(I_C_Letter letter, I_AD_BoilerPlate textTemplate)
	{
		letter.setAD_BoilerPlate(textTemplate);
		if (textTemplate != null)
		{
			letter.setLetterSubject(textTemplate.getSubject());
			letter.setLetterBody(textTemplate.getTextSnippext());
		}
	}

	@Override
	public boolean canUpdate(final I_AD_BoilerPlate textTemplate)
	{
		if (textTemplate == null)
		{
			return false;
		}

		final Properties ctx = InterfaceWrapperHelper.getCtx(textTemplate);
		final int adClientId = textTemplate.getAD_Client_ID();
		final int adOrgId = textTemplate.getAD_Org_ID();
		final int tableId = InterfaceWrapperHelper.getTableId(I_AD_BoilerPlate.class);
		final int recordId = textTemplate.getAD_BoilerPlate_ID();
		final boolean createError = false;

		final boolean rw = Env.getUserRolePermissions(ctx).canUpdate(adClientId, adOrgId, tableId, recordId, createError);
		return rw;
	}

	@Override
	public <T> void createLetters(Iterator<T> source, ILetterProducer<T> producer)
	{
		while (source.hasNext())
		{
			final T item = source.next();
			createLetter(item, producer);
		}
	}

	@Override
	public <T> I_C_Letter createLetter(final T item, final ILetterProducer<T> producer)
	{
		DB.saveConstraints();
		DB.getConstraints().addAllowedTrxNamePrefix("POSave");
		try
		{
			final Properties ctx = InterfaceWrapperHelper.getCtx(item);

			// NOTE: we are working out of trx because we want to produce the letters one by one and build a huge transaction
			final String trxName = ITrx.TRXNAME_None;

			final I_C_Letter letter = InterfaceWrapperHelper.create(ctx, I_C_Letter.class, trxName);
			if (!producer.createLetter(letter, item))
			{
				return null;
			}

			setLocationContactAndOrg(letter);

			final int boilerPlateID = producer.getBoilerplateID(item);

			if (boilerPlateID <= 0)
			{
				logger.warn("No default text template for org {}", letter.getAD_Org_ID());
				return null;
			}

			final I_AD_BoilerPlate textTemplate = InterfaceWrapperHelper.create(ctx, boilerPlateID, I_AD_BoilerPlate.class, trxName);

			setAD_BoilerPlate(letter, textTemplate);

			final Map<String, Object> attributes = producer.createAttributes(item);
			setLetterBodyParsed(letter, attributes);

			// 04238 : We need to flag the letter for enqueue.
			letter.setIsMembershipBadgeToPrint(true);

			InterfaceWrapperHelper.save(letter);
			return letter;
		}
		finally
		{
			DB.restoreConstraints();
		}
	}

}
