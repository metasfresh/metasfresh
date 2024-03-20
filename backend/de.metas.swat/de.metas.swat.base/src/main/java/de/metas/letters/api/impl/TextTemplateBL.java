package de.metas.letters.api.impl;

import de.metas.bpartner.service.IBPartnerBL;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.cache.annotation.CacheCtx;
import de.metas.document.location.IDocumentLocationBL;
import de.metas.letter.BoilerPlateId;
import de.metas.letters.api.ITextTemplateBL;
import de.metas.letters.model.I_AD_BoilerPlate;
import de.metas.letters.model.I_C_Letter;
import de.metas.letters.model.I_T_Letter_Spool;
import de.metas.letters.model.Letter;
import de.metas.letters.model.LetterDocumentLocationAdapter;
import de.metas.letters.model.MADBoilerPlate;
import de.metas.letters.model.MADBoilerPlate.BoilerPlateContext;
import de.metas.letters.spi.ILetterProducer;
import de.metas.logging.LogManager;
import de.metas.organization.OrgId;
import de.metas.process.AdProcessId;
import de.metas.process.IADPInstanceDAO;
import de.metas.process.PInstanceId;
import de.metas.process.ProcessInfo;
import de.metas.report.client.ReportsClient;
import de.metas.report.server.OutputType;
import de.metas.report.server.ReportResult;
import de.metas.security.permissions.Access;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.FillMandatoryException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.util.proxy.Cached;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.slf4j.Logger;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;

public final class TextTemplateBL implements ITextTemplateBL
{
	private static final TextTemplateBL instance = new TextTemplateBL();
	private final transient Logger logger = LogManager.getLogger(getClass());

	private static final AdProcessId AD_Process_ID_T_Letter_Spool_Print = AdProcessId.ofRepoId(540278); // TODO: HARDCODED

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
		return Services.get(IQueryBL.class)
				.createQueryBuilderOutOfTrx(I_AD_BoilerPlate.class)
				.addOnlyActiveRecordsFilter()
				.addOnlyContextClient()
				.orderBy(I_AD_BoilerPlate.COLUMNNAME_Name)
				.create()
				.setRequiredAccess(Access.READ)
				.list(I_AD_BoilerPlate.class);
	}

	@Override
	public String parseText(Properties ctx, String text,
			boolean isEmbeded,
			final BoilerPlateContext attributes,
			String trxName)
	{
		final String textParsed = MADBoilerPlate.parseText(ctx,
				text,
				true, // isEmbeded
				attributes,
				ITrx.TRXNAME_None); // trxName
		return textParsed;
	}

	@Override
	public void setLetterBodyParsed(I_C_Letter letter, BoilerPlateContext attributes)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(letter);
		final String trxName = InterfaceWrapperHelper.getTrxName(letter);

		final String letterBodyParsed = parseText(
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
		final IDocumentLocationBL documentLocationBL = SpringContextHolder.instance.getBean(IDocumentLocationBL.class);
		documentLocationBL.updateRenderedAddressAndCapturedLocation(new LetterDocumentLocationAdapter(letter));

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
	public byte[] createPDF(final Letter request)
	{
		final Properties ctx = Env.getCtx();
		final ProcessInfo jasperProcessInfo = ProcessInfo.builder()
				.setCtx(ctx)
				.setAD_Process_ID(getJasperProcessId(request).orElse(AD_Process_ID_T_Letter_Spool_Print))
				// .setRecord(recordRef) // no record
				.setReportLanguage(request.getAdLanguage())
				.setJRDesiredOutputType(OutputType.PDF)
				.build();
		Services.get(IADPInstanceDAO.class).saveProcessInfoOnly(jasperProcessInfo);

		createLetterSpoolRecord(jasperProcessInfo.getPinstanceId(), request, jasperProcessInfo.getAD_Client_ID());

		final ReportsClient reportsClient = ReportsClient.get();
		final ReportResult report = reportsClient.report(jasperProcessInfo);

		return report.getReportContent();
	}

	@NonNull
	public static Optional<AdProcessId> getJasperProcessId(@NonNull final Letter request)
	{
		final BoilerPlateId boilerPlateId = request.getBoilerPlateId();
		if (boilerPlateId == null)
		{
			return Optional.empty();
		}

		return getJasperProcessId(boilerPlateId);
	}

	@NonNull
	public static Optional<AdProcessId> getJasperProcessId(@NonNull final BoilerPlateId boilerPlateId)
	{
		final I_AD_BoilerPlate textTemplate = loadOutOfTrx(boilerPlateId.getRepoId(), I_AD_BoilerPlate.class);
		if (textTemplate == null)
		{
			return Optional.empty();
		}

		AdProcessId jasperProcessId = AdProcessId.ofRepoIdOrNull(textTemplate.getJasperProcess_ID());
		if (jasperProcessId == null)
		{
			return Optional.empty();
		}

		return Optional.of(jasperProcessId);
	}

	private static void createLetterSpoolRecord(final PInstanceId pinstanceId, final Letter request, final int adClientId)
	{
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
		DB.executeUpdateAndThrowExceptionOnFail(sql,
												new Object[] {
						adClientId,
						request.getAdOrgId(), // NOTE: using the same Org as in C_Letter is very important for reports to know from where to take the Org Header
						pinstanceId,
						10, // seqNo
						request.getSubject(),
						request.getBody(),
						request.getBpartnerId() == null ? null : request.getBpartnerId().getRepoId(),
						request.getBpartnerLocationId(),
						request.getAddress(),
						request.getUserId() == null ? null : request.getUserId().getRepoId(),
				},
												ITrx.TRXNAME_None);
	}

	@Override
	public void setAD_BoilerPlate(I_C_Letter letter, I_AD_BoilerPlate textTemplate)
	{
		letter.setAD_BoilerPlate(textTemplate);
		if (textTemplate != null)
		{
			letter.setLetterSubject(textTemplate.getSubject());
			letter.setLetterBody(textTemplate.getTextSnippet());
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
		final ClientId adClientId = ClientId.ofRepoId(textTemplate.getAD_Client_ID());
		final OrgId adOrgId = OrgId.ofRepoId(textTemplate.getAD_Org_ID());
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

			final BoilerPlateContext attributes = producer.createAttributes(item);
			setLetterBodyParsed(letter, attributes);

			// 04238 : We need to flag the letter for enqueue.
			letter.setIsMembershipBadgeToPrint(true);

			// mo73_05916 : Add record and table ID
			letter.setAD_Table_ID(InterfaceWrapperHelper.getModelTableId(item));
			letter.setRecord_ID(InterfaceWrapperHelper.getId(item));

			InterfaceWrapperHelper.save(letter);
			return letter;
		}
		finally
		{
			DB.restoreConstraints();
		}
	}

	@Override
	public I_AD_BoilerPlate getById(int boilerPlateId)
	{
		return loadOutOfTrx(boilerPlateId, I_AD_BoilerPlate.class);
	}
}
