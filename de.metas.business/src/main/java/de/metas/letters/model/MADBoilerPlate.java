package de.metas.letters.model;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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


import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.activation.DataSource;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLEditorKit;

import org.adempiere.ad.validationRule.IValidationRule;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.api.IMsgBL;
import org.compiere.model.GridTab;
import org.compiere.model.I_AD_Process;
import org.compiere.model.I_R_Request;
import org.compiere.model.Lookup;
import org.compiere.model.MAsset;
import org.compiere.model.MAttachment;
import org.compiere.model.MBPartner;
import org.compiere.model.MCampaign;
import org.compiere.model.MInOut;
import org.compiere.model.MInvoice;
import org.compiere.model.MLookupFactory;
import org.compiere.model.MOrder;
import org.compiere.model.MOrderLine;
import org.compiere.model.MPayment;
import org.compiere.model.MProduct;
import org.compiere.model.MProject;
import org.compiere.model.MRMA;
import org.compiere.model.MRequest;
import org.compiere.model.MUser;
import org.compiere.model.PO;
import org.compiere.model.Query;
import org.compiere.print.ReportEngine;
import org.compiere.process.ProcessInfo;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.util.CCache;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;
import org.slf4j.Logger;
import org.slf4j.Logger;

import de.metas.email.EMail;
import de.metas.email.EMailAttachment;
import de.metas.email.EMailSentStatus;
import de.metas.logging.LogManager;
import de.metas.logging.LogManager;
import de.metas.process.ProcessCtl;

public final class MADBoilerPlate extends X_AD_BoilerPlate
{

	private static final long serialVersionUID = 5825759144157759944L;
	private static final Logger log = LogManager.getLogger(MADBoilerPlate.class);

	public static final String FunctionSeparator = "/";
	public static final String NamePattern = "[a-zA-Z0-9-_+]+";
	public static final String NamePatternWithFunctions = "[a-zA-Z0-9-_+" + FunctionSeparator + "]+";
	public static final String TagBegin = "@";
	public static final String TagEnd = "@";
	public static final Pattern NameTagPattern = Pattern.compile(TagBegin + "([ ]*" + NamePatternWithFunctions + "[ ]*)" + TagEnd, Pattern.CASE_INSENSITIVE);

	public static final String VAR_WindowNo = "WindowNo";
	public static final String VAR_SalesRep = "SalesRep";
	public static final String VAR_SalesRep_ID = "SalesRep_ID";
	public static final String VAR_EMail = "EMail";
	public static final String VAR_AD_User_ID = "AD_User_ID";
	public static final String VAR_AD_User = "AD_User";
	public static final String VAR_C_BPartner_ID = "C_BPartner_ID";
	public static final String VAR_AD_Language = "AD_Language";
	/** User Persistent Object */
	public static final String VAR_UserPO = "__UserPO";
	public static final String FUNCTION_stripWhitespaces = "stripWhitespaces";
	public static final String FUNCTION_trim = "trim";
	public static final String FUNCTION_urlEncode = "urlEncode";
	public static final String FUNCTION_upperCase = "upperCase";
	public static final String FUNCTION_lowerCase = "lowerCase";

	private static CCache<Integer, MADBoilerPlate> s_cache = new CCache<Integer, MADBoilerPlate>(Table_Name, 20);

	public static MADBoilerPlate get(Properties ctx, int AD_BoilerPlate_ID)
	{
		if (AD_BoilerPlate_ID <= 0)
			return null;
		MADBoilerPlate bp = s_cache.get(AD_BoilerPlate_ID);
		if (bp != null)
			return bp;
		bp = new MADBoilerPlate(ctx, AD_BoilerPlate_ID, null);
		if (bp.getAD_BoilerPlate_ID() != AD_BoilerPlate_ID)
		{
			bp = null;
		}
		else
		{
			s_cache.put(AD_BoilerPlate_ID, bp);
		}
		return bp;
	}

	public static ReportEngine getReportEngine(String html, Map<String, Object> attrs)
	{
		final Properties ctx = Env.getCtx();
		String text = parseText(ctx, html, false, attrs, null);
		text = text.replace("</", " </"); // we need to leave at least one space before closing tag, else jasper will not apply the effect of that tag
		//
		// Get Process
		final I_AD_Process process = new Query(Env.getCtx(), I_AD_Process.Table_Name, I_AD_Process.COLUMNNAME_Classname + "=?", null)
				.setParameters(new Object[] {
						"de.metas.letters.report.AD_BoilerPlate_Report",
						// AD_BoilerPlate_Report.class.getCanonicalName()
				})
				.firstOnly();
		
		final ProcessInfo pi = ProcessInfo.builder()
				.setFromAD_Process(process)
				.addParameter(ProcessInfoParameter.of(X_T_BoilerPlate_Spool.COLUMNNAME_MsgText, text))
				.build();
		
		pi.setAD_User_ID(Env.getAD_User_ID(ctx));
		pi.setAD_Client_ID(Env.getAD_Client_ID(ctx));
		
		ProcessCtl.builder().setProcessInfo(pi).executeSync();
		final ReportEngine re = ReportEngine.get(ctx, pi);
		return re;
	}

	public static File getPDF(String fileNamePrefix, String html, Map<String, Object> attrs)
	{
		File file = null;
		if (!Check.isEmpty(fileNamePrefix))
		{
			try
			{
				file = File.createTempFile(fileNamePrefix.trim(), ".pdf");
			}
			catch (IOException e)
			{
				log.error(e.getLocalizedMessage(), e);
				file = null;
			}
		}
		//
		final ReportEngine re = getReportEngine(html, attrs);
		return re.getPDF(file);
	}

	public static void sendEMail(IEMailEditor editor)
	{
		sendEMail(editor, true);
	}

	public static void sendEMail(IEMailEditor editor, boolean withRequest)
	{
		final Object baseObject = editor.getBaseObject();
		final Map<String, Object> variables = createEditorContext(baseObject);
		final MUser from = (MUser)variables.get(MADBoilerPlate.VAR_SalesRep);
		final String toEmail = (String)variables.get(MADBoilerPlate.VAR_EMail);
		if (Check.isEmpty(toEmail, true))
		{
			throw new AdempiereException("@NotFound@ @EMail@");
		}
		final int AD_Table_ID = editor.getAD_Table_ID();
		final int Record_ID = editor.getRecord_ID();
		//
		final EMail email = editor.sendEMail(from, toEmail, "", variables);
		if (withRequest)
			createRequest(email, AD_Table_ID, Record_ID, variables);
	}

	private static void createRequest(EMail email,
			int parent_table_id, int parent_record_id,
			Map<String, Object> variables)
	{
		if (email == null)
		{
			return;
		}
		
		final EMailSentStatus emailSentStatus = email.getLastSentStatus();
		if (!emailSentStatus.isSentOK())
		{
			return;
		}

		final MRequestTypeService rtService = new MRequestTypeService(Env.getCtx());
		final Integer SalesRep_ID = (Integer)variables.get(MADBoilerPlate.VAR_SalesRep_ID);
		final MRequest request = new MRequest(Env.getCtx(),
				SalesRep_ID == null ? 0 : SalesRep_ID.intValue(),
				rtService.getDefault(I_R_RequestType.COLUMNNAME_IsDefaultForEMail),
				email.getSubject(),
				false, // isSelfService,
				null // trxName
		);
		String message = getPlainText(email.getMessageHTML());
		if (Check.isEmpty(message, true))
			message = email.getMessageCRLF();
		request.setResult(message);
		updateRequestDetails(request, parent_table_id, parent_record_id, variables);
		request.saveEx();
		//
		// Attach email attachments to this request
		final MAttachment requestAttachment = request.createAttachment();
		for (final EMailAttachment emailAttachment : email.getAttachments())
		{
			try
			{
				final DataSource dataSource = emailAttachment.createDataSource();
				requestAttachment.addEntry(dataSource);
			}
			catch (Exception e)
			{
				log.warn("Failed adding {} to {}", emailAttachment, requestAttachment);
			}
		}
		if (requestAttachment.getEntryCount() > 0)
		{
			requestAttachment.saveEx();
		}
	}

	public static void createRequest(File pdf,
			int parent_table_id, int parent_record_id,
			Map<String, Object> variables)
	{
		final MRequestTypeService rtService = new MRequestTypeService(Env.getCtx());
		final Integer SalesRep_ID = (Integer)variables.get(MADBoilerPlate.VAR_SalesRep_ID);
		final MRequest request = new MRequest(Env.getCtx(),
				SalesRep_ID == null ? 0 : SalesRep_ID.intValue(),
				rtService.getDefault(I_R_RequestType.COLUMNNAME_IsDefaultForLetter),
				Services.get(IMsgBL.class).translate(Env.getCtx(), "de.metas.letter.RequestLetterSubject"),
				false, // isSelfService,
				null // trxName
		);
		MADBoilerPlate.updateRequestDetails(request, parent_table_id, parent_record_id, variables);
		request.saveEx();
		// Attach printed letter
		final MAttachment requestAttachment = request.createAttachment();
		requestAttachment.addEntry(pdf);
		requestAttachment.saveEx();
	}

	private static void updateRequestDetails(I_R_Request rq,
			int parent_table_id, int parent_record_id,
			Map<String, Object> attributes)
	{
		final Integer C_BPartner_ID = (Integer)attributes.get(MADBoilerPlate.VAR_C_BPartner_ID);
		if (C_BPartner_ID != null && C_BPartner_ID > 0)
			rq.setC_BPartner_ID(C_BPartner_ID);
		final Integer AD_User_ID = (Integer)attributes.get(MADBoilerPlate.VAR_AD_User_ID);
		if (AD_User_ID != null && AD_User_ID > 0)
			rq.setAD_User_ID(AD_User_ID);

		rq.setAD_Table_ID(parent_table_id);
		rq.setRecord_ID(parent_record_id);
		if (parent_record_id <= 0)
			return;
		//
		if (parent_table_id == MBPartner.Table_ID)
			rq.setC_BPartner_ID(parent_record_id);
		else if (parent_table_id == MUser.Table_ID)
			rq.setAD_User_ID(parent_record_id);
		//
		else if (parent_table_id == MProject.Table_ID)
			rq.setC_Project_ID(parent_record_id);
		else if (parent_table_id == MAsset.Table_ID)
			rq.setA_Asset_ID(parent_record_id);
		//
		else if (parent_table_id == MOrder.Table_ID)
			rq.setC_Order_ID(parent_record_id);
		else if (parent_table_id == MInvoice.Table_ID)
			rq.setC_Invoice_ID(parent_record_id);
		//
		else if (parent_table_id == MProduct.Table_ID)
			rq.setM_Product_ID(parent_record_id);
		else if (parent_table_id == MPayment.Table_ID)
			rq.setC_Payment_ID(parent_record_id);
		//
		else if (parent_table_id == MInOut.Table_ID)
			rq.setM_InOut_ID(parent_record_id);
		else if (parent_table_id == MRMA.Table_ID)
			rq.setM_RMA_ID(parent_record_id);
		//
		else if (parent_table_id == MCampaign.Table_ID)
			rq.setC_Campaign_ID(parent_record_id);
		//
		else if (parent_table_id == InterfaceWrapperHelper.getTableId(I_R_Request.class))
			rq.setR_RequestRelated_ID(parent_record_id);
		// FR [2842165] - Order Ref link from SO line creating new request
		else if (parent_table_id == MOrderLine.Table_ID)
		{
			MOrderLine oLine = new MOrderLine(Env.getCtx(), parent_record_id, null);
			if (oLine != null)
			{
				rq.setC_Order_ID(oLine.getC_Order_ID());
			}
		}
	}

	public MADBoilerPlate(Properties ctx, int AD_BoilerPlate_ID, String trxName)
	{
		super(ctx, AD_BoilerPlate_ID, trxName);
	}

	public MADBoilerPlate(Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}

	public static Query getByNameQuery(Properties ctx, String name, String trxName)
	{
		final String whereClause = COLUMNNAME_Name + "=? AND AD_Client_ID=?";
		return new Query(ctx, Table_Name, whereClause, trxName)
				.setParameters(new Object[] { name, Env.getAD_Client_ID(ctx) });
	}

	public static int getIdByName(Properties ctx, String name, String trxName)
	{
		return getByNameQuery(ctx, name, trxName).firstIdOnly();
	}

	public static MADBoilerPlate getByName(Properties ctx, String name, String trxName)
	{
		return getByNameQuery(ctx, name, trxName).firstOnly();
	}

	public static KeyNamePair[] getDependsOn(Properties ctx, int AD_BoilerPlate_ID, String trxName)
	{
		String sql = "SELECT " + COLUMNNAME_AD_BoilerPlate_ID + "," + COLUMNNAME_Name
				+ " FROM " + Table_Name
				+ " WHERE EXISTS (SELECT 1 FROM " + X_AD_BoilerPlate_Ref.Table_Name + " r"
				+ " WHERE r." + X_AD_BoilerPlate_Ref.COLUMNNAME_Ref_BoilerPlate_ID + "=" + Table_Name + "." + COLUMNNAME_AD_BoilerPlate_ID
				+ " AND r." + X_AD_BoilerPlate_Ref.COLUMNNAME_AD_BoilerPlate_ID + "=?)";
		return DB.getKeyNamePairs(trxName, sql, false, AD_BoilerPlate_ID);
	}

	public KeyNamePair toKeyNamePair()
	{
		return new KeyNamePair(getAD_BoilerPlate_ID(), getName());
	}

	public static KeyNamePair[] getAllSnippetsKeyNamePair()
	{
		List<MADBoilerPlate> allSnippets = getAll(Env.getCtx());
		KeyNamePair[] result = new KeyNamePair[allSnippets.size()];
		int i = 0;
		for (MADBoilerPlate bp : allSnippets)
		{
			s_cache.put(bp.getAD_BoilerPlate_ID(), bp);
			result[i] = bp.toKeyNamePair();
		}
		return result;
	}

	public static Lookup getAllLookup(final int windowNo)
	{
		final Lookup lookup;
		try
		{
			final Properties ctx = Env.getCtx();

			lookup = MLookupFactory.get(ctx,
					windowNo,
					0, // Column_ID
					DisplayType.TableDir,
					MADBoilerPlate.COLUMNNAME_AD_BoilerPlate_ID,
					0, // AD_Reference_Value_ID,
					false, // IsParent,
					IValidationRule.AD_Val_Rule_ID_Null); // ValidationCode
		}
		catch (final Exception e)
		{
			throw new AdempiereException(e);
		}
		return lookup;
	}

	/**
	 * 
	 * @return all snippets, ordered by name
	 */
	public static SortedMap<String, String> getAllSnippetsMap()
	{
		SortedMap<String, String> result = new TreeMap<String, String>();

		for (MADBoilerPlate boilerPlate : getAll(Env.getCtx()))
		{
			if (boilerPlate.getTextSnippext() != null)
			{
				result.put(boilerPlate.getName(), boilerPlate.getTextSnippext());
			}
			else
			{
				log.warn("boilerPlate entry '" + boilerPlate.getName() + "' has null value as snippet");
				continue;
			}
		}
		return result;
	}

	public static List<MADBoilerPlate> getAll(Properties ctx)
	{
		return new Query(ctx, Table_Name, "AD_Client_ID IN (0,?)", null)
				.setParameters(new Object[] { Env.getAD_Client_ID(ctx) })
				.setOrderBy(COLUMNNAME_Name + ", " + COLUMNNAME_AD_Client_ID + " DESC")
				.list();
	}

	private void checkCycles(int AD_BoilerPlate_ID, Collection<KeyNamePair> trace)
	{
		if (trace == null)
		{
			trace = new ArrayList<KeyNamePair>();
		}
		if (AD_BoilerPlate_ID <= 0)
		{
			trace.add(this.toKeyNamePair());
			AD_BoilerPlate_ID = getAD_BoilerPlate_ID();
		}
		for (KeyNamePair dependOn : MADBoilerPlate.getDependsOn(getCtx(), AD_BoilerPlate_ID, get_TrxName()))
		{

			if (trace.contains(dependOn))
			{
				throw new AdempiereException("@de.metas.letters.AD_BoilerPlate.CycleDetectedError@ - " + dependOn);
			}
			Collection<KeyNamePair> trace2 = new ArrayList<KeyNamePair>(trace);
			trace2.add(dependOn);
			checkCycles(dependOn.getKey(), trace2);
		}
	}

	public String getTextSnippetPlain()
	{
		return getPlainText(getTextSnippext());
	}

	public static String getPlainText(String html)
	{
		if (Check.isEmpty(html, true))
			return html;
		//
		final String brMarker = "[br]" + System.currentTimeMillis();
		html = html.replace("<br>", brMarker);
		HTMLEditorKit editorKit = new HTMLEditorKit();
		Reader r = new StringReader(html);
		Document doc = editorKit.createDefaultDocument();
		String text = html;
		try
		{
			editorKit.read(r, doc, 0);
			text = doc.getText(0, doc.getEndPosition().getOffset()).trim();
		}
		catch (Exception e)
		{
			throw new AdempiereException(e);
		}
		text = text.replace(brMarker, "\r\n");
		return text;
	}

	public String getTextSnippext(String AD_Language)
	{
		if (AD_Language == null)
			AD_Language = Env.getAD_Language(getCtx());
		return get_Translation(COLUMNNAME_TextSnippext, AD_Language);
	}

	/**
	 * Get Parsed Text
	 * 
	 * @param ctx
	 * @param text
	 * @param AD_Language
	 * @param isEmbeded will this text be embeded (i.e. shoud we strip html, head, body tags?
	 * @param attrs variables map. If null, no variable replacement will be made
	 * @param trxName
	 * @return
	 */
	public static String parseText(Properties ctx, String text,
			boolean isEmbeded,
			Map<String, Object> attrs,
			String trxName)
	{
		if (text == null)
			return null;
		//
		String textFixed = text;
		if (isEmbeded)
		{
			textFixed = textFixed.replace("<html>", "")
					.replace("</html>", "")
					.replace("<head>", "")
					.replace("</head>", "")
					.replace("<body>", "")
					.replace("</body>", "")
					.trim();
		}
		final Matcher m = NameTagPattern.matcher(textFixed);
		final StringBuffer sb = new StringBuffer();
		while (m.find())
		{
			final String[] functions = getTagNameAndFunctions(m);
			final String refName = functions[0];
			functions[0] = null;

			final MADBoilerPlateVar var = MADBoilerPlateVar.get(ctx, refName);

			String replacement;
			if (attrs != null && attrs.containsKey(refName))
			{
				final Object attrValue = attrs.get(refName);
				replacement = attrValue == null ? "" : attrValue.toString();
			}
			else if (var != null)
			{
				try
				{
					replacement = var.evaluate(attrs);
				}
				catch (Exception e)
				{
					log.warn(e.getLocalizedMessage(), e);
					replacement = m.group();
				}
			}
			else
			{
				final MADBoilerPlate ref = getByName(ctx, refName, trxName);
				if (ref == null)
				{
					throw new AdempiereException("@NotFound@ @AD_BoilerPlate_ID@ (@Name@:" + refName + ")");
				}
				replacement = ref.getTextSnippetParsed(true, attrs);
			}
			if (replacement == null) // metas: c.ghita@metas.ro
				replacement = "";
			replacement = applyTagFunctions(replacement, functions);
			m.appendReplacement(sb, replacement);
		}
		m.appendTail(sb);
		return sb.toString();

	}

	private static String applyTagFunctions(String text, String[] functions)
	{
		if (text == null)
			text = "";
		for (String function : functions)
		{
			if (Check.isEmpty(function, true))
				continue;
			text = applyTagFunction(text, function.trim());
		}
		return text;
	}

	private static String applyTagFunction(String text, String function)
	{
		if (FUNCTION_stripWhitespaces.equalsIgnoreCase(function))
		{
			return text.replaceAll("\\s", "");
		}
		else if (FUNCTION_trim.equalsIgnoreCase(function))
		{
			return text.trim();
		}
		else if (FUNCTION_urlEncode.equalsIgnoreCase(function))
		{
			try
			{
				return URLEncoder.encode(text, "UTF-8");
			}
			catch (UnsupportedEncodingException e)
			{
				throw new AdempiereException(e);
			}
		}
		else if (FUNCTION_upperCase.equalsIgnoreCase(function))
		{
			return text.toUpperCase();
		}
		else if (FUNCTION_lowerCase.equalsIgnoreCase(function))
		{
			return text.toLowerCase();
		}
		else
		{
			throw new AdempiereException("@NotSupported@ @Function@ " + function);
		}
	}

	/**
	 * 
	 * @param AD_Language
	 * @param isEmbeded
	 * @param attrs
	 * @return
	 * @see #getTextSnippetParsed(String, boolean, Map) and consider isEmbeded = true
	 */
	public String getTextSnippetParsed(Map<String, Object> attrs)
	{
		return getTextSnippetParsed(false, attrs);
	}

	/**
	 * Get Parsed Text
	 * 
	 * @param AD_Language
	 * @param attrs variables map. If null, no variable repacement will be made
	 * @param isEmbeded will this text be embeded (i.e. shoud we strip html, head, body tags?
	 * @return parsed text
	 */
	public String getTextSnippetParsed(boolean isEmbeded, Map<String, Object> attrs)
	{
		final String AD_Language = getAD_Language(Env.getCtx(), attrs);
		final String text = getTextSnippext(AD_Language);
		return parseText(getCtx(), text, isEmbeded, attrs, get_TrxName());
	}

	public String getTagString()
	{
		return createTag(getName());
	}

	public static String createTag(String name)
	{
		if (Check.isEmpty(name, true))
			return "";
		return TagBegin + name.trim() + TagEnd;
	}

	@Override
	protected boolean beforeSave(boolean newRecord)
	{
		final String name = getName();
		if (MADBoilerPlateVar.get(getCtx(), name) != null)
		{
			throw new AdempiereException("@Invalid@ - " + name);
		}
		if (!name.matches("^" + NamePattern + "$"))
		{
			throw new AdempiereException("@Invalid@ - " + name);
		}
		return true;
	}

	@Override
	protected boolean afterSave(boolean newRecord, boolean success)
	{
		if (!success)
			return false;

		rebuildReferences();
		//
		return true;
	}

	public Collection<String> parseNeededReferences()
	{
		String text = getTextSnippetPlain();
		Matcher m = NameTagPattern.matcher(text);
		Collection<String> neededReferences = new TreeSet<String>();
		while (m.find())
		{
			String refName = getTagName(m);
			if (MADBoilerPlateVar.exists(getCtx(), refName))
			{
				continue;
			}
			neededReferences.add(refName);
		}
		return neededReferences;
	}

	protected static String getTagString(Matcher m)
	{
		String refName;
		if (m.groupCount() >= 1)
			refName = m.group(1);
		else
			refName = m.group();
		refName = refName.trim();
		return refName;
	}

	protected static String getTagName(Matcher m)
	{
		String[] parts = getTagString(m).split(FunctionSeparator);
		return parts != null && parts.length > 0 ? parts[0] : "";
	}

	protected static String[] getTagNameAndFunctions(Matcher m)
	{
		String[] parts = getTagString(m).split(FunctionSeparator);
		return parts;
	}

	public void rebuildReferences()
	{
		DB.executeUpdateEx("DELETE FROM " + X_AD_BoilerPlate_Ref.Table_Name
				+ " WHERE " + X_AD_BoilerPlate_Ref.COLUMNNAME_AD_BoilerPlate_ID + "=?",
				new Object[] { getAD_BoilerPlate_ID() },
				get_TrxName());
		for (String refName : parseNeededReferences())
		{
			MADBoilerPlateRef ref = new MADBoilerPlateRef(this, refName);
			ref.saveEx();
		}
		checkCycles(-1, null);
	}

	/**
	 * Create Context
	 * 
	 * @param object parent object - GridTab or PO
	 * @return
	 */
	public static Map<String, Object> createEditorContext(Object object)
	{
		final Properties ctx = Env.getCtx();
		final HashMap<String, Object> attrs = new HashMap<String, Object>();

		if (object instanceof GridTab)
			attrs.put(VAR_WindowNo, ((GridTab)object).getWindowNo());
		else
			attrs.put(VAR_WindowNo, 0);

		MUser salesRep = MUser.get(Env.getCtx(), Env.getAD_User_ID(Env.getCtx()));
		if (salesRep != null)
		{
			attrs.put(VAR_SalesRep, salesRep);
			attrs.put(VAR_SalesRep_ID, salesRep.getAD_User_ID());
		}

		int C_BPartner_ID = -1;
		MUser user = null;
		int AD_User_ID = getValueAsInt(object, "AD_User_ID");
		String email = null;
		//
		if (AD_User_ID > 0)
		{
			user = MUser.get(ctx, AD_User_ID);
			attrs.put(VAR_AD_User_ID, user.getAD_User_ID());
			attrs.put(VAR_AD_User, user);
			if (user.isEMailValid())
			{
				email = user.getEMail();
				attrs.put(VAR_EMail, email);
			}
			C_BPartner_ID = user.getC_BPartner_ID();
		}
		if (C_BPartner_ID <= 0)
		{
			C_BPartner_ID = getValueAsInt(object, "C_BPartner_ID");
		}
		if (C_BPartner_ID > 0)
		{
			attrs.put(VAR_C_BPartner_ID, C_BPartner_ID);
			MBPartner bp = MBPartner.get(ctx, C_BPartner_ID);
			if (email == null)
			{
				final MUser contact = getDefaultContactOrFirstWithValidEMail(bp);
				if (contact != null)
				{
					attrs.put(VAR_AD_User_ID, contact.getAD_User_ID());
					attrs.put(VAR_AD_User, contact);
					if (contact.isEMailValid())
					{
						email = contact.getEMail();
						attrs.put(VAR_EMail, email);
					}
				}
			}
		}
		
		//
		// Language
		String AD_Language = Env.getAD_Language(ctx);
		if (C_BPartner_ID > 0)
		{
			MBPartner bp = MBPartner.get(ctx, C_BPartner_ID);
			if (bp != null)
			{
				AD_Language = bp.getAD_Language();
			}
		}
		attrs.put(VAR_AD_Language, AD_Language);
		
		//
		//
		// attrs.put(VAR_Phone, null);
		// attrs.put(VAR_Phone2, null);
		// attrs.put(VAR_Fax, null);
		// if (user != null)
		// {
		// attrs.put(VAR_Phone, user.getPhone());
		// attrs.put(VAR_Phone2, user.getPhone2());
		// attrs.put(VAR_Fax, user.getFax());
		// }
		// //
		// attrs.put(MADBoilerPlate.VAR_BPValue, null);
		// attrs.put(MADBoilerPlate.VAR_BPName, null);
		// if (C_BPartner_ID > 0)
		// {
		// MBPartner bp = MBPartner.get(ctx, C_BPartner_ID);
		// if (bp != null)
		// {
		// attrs.put(MADBoilerPlate.VAR_BPValue, bp.getValue());
		// attrs.put(MADBoilerPlate.VAR_BPName, bp.get_Translation(MBPartner.COLUMNNAME_Name, AD_Language));
		// }
		// }
		//
		return attrs;
	}
	
	private static MUser getDefaultContactOrFirstWithValidEMail(final MBPartner bpartner)
	{
		MUser firstContact = null;
		MUser firstValidContact = null;
		for (final MUser contact : bpartner.getContacts(false))
		{
			if(contact.isDefaultContact())
			{
				return contact;
			}
			
			if(firstContact == null)
			{
				firstContact = contact;
			}
			
			if (contact.isEMailValid())
			{
				if(firstValidContact == null)
				{
					firstValidContact = contact;
				}
			}
		}
		
		if(firstValidContact != null)
		{
			return firstValidContact;
		}
		
		return firstContact;
	}

	private static int getValueAsInt(Object o, String columnName)
	{
		Object value = null;
		if (o instanceof GridTab)
			value = ((GridTab)o).getValue(columnName);
		else if (InterfaceWrapperHelper.getPO(o) instanceof PO)
			value = InterfaceWrapperHelper.getPO(o).get_Value(columnName);
		if (value == null)
			return -1;
		return (Integer)value;
	}

	/**
	 * Get Language from attributes
	 * 
	 * @param attributes
	 * @return
	 */
	public static String getAD_Language(Properties ctx, Map<String, Object> attributes)
	{
		if (attributes != null)
		{
			Object o = attributes.get(VAR_AD_Language);
			if (o != null)
				return o.toString();
		}
		return Env.getAD_Language(ctx);
	}

	public static int getWindowNo(Map<String, Object> attributes)
	{
		Object o = attributes.get(MADBoilerPlate.VAR_WindowNo);
		if (o instanceof Number)
			return ((Number)o).intValue();
		return 0;
	}

	@Override
	public String toString()
	{
		StringBuffer result = new StringBuffer();
		result.append(super.toString());
		result.append(' ');
		result.append(getName());
		result.append(" (");
		result.append(getTextSnippext().substring(0, 20));
		result.append("...)");
		return result.toString();
	}

	/**
	 * Create record into T_BoilerPlate_Spool table
	 * 
	 * @param ctx
	 * @param AD_Client_ID
	 * @param AD_PInstance_ID
	 * @param text
	 * @param trxName
	 */
	public static void createSpoolRecord(Properties ctx, int AD_Client_ID, int AD_PInstance_ID, String text, String trxName)
	{
		final String sql = "INSERT INTO " + X_T_BoilerPlate_Spool.Table_Name + "("
				+ " " + X_T_BoilerPlate_Spool.COLUMNNAME_AD_Client_ID
				+ "," + X_T_BoilerPlate_Spool.COLUMNNAME_AD_Org_ID
				+ "," + X_T_BoilerPlate_Spool.COLUMNNAME_AD_PInstance_ID
				+ "," + X_T_BoilerPlate_Spool.COLUMNNAME_SeqNo
				+ "," + X_T_BoilerPlate_Spool.COLUMNNAME_MsgText
				+ ") VALUES (?,?,?,?,?)";
		DB.executeUpdateEx(sql,
				new Object[] { AD_Client_ID, 0, AD_PInstance_ID, 10, text },
				trxName);
	}
}
