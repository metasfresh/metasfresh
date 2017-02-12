/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
package org.compiere.model;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Properties;
import java.util.zip.Deflater;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import javax.activation.DataSource;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.service.IAttachmentDAO;
import org.adempiere.util.LegacyAdapters;
import org.adempiere.util.Services;
import org.compiere.util.Util;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *	Attachment Model.
 *	One Attachment can have multiple entries
 *	
 *  @author Jorg Janke
 *  @version $Id: MAttachment.java,v 1.4 2006/07/30 00:58:37 jjanke Exp $
 */
public class MAttachment extends X_AD_Attachment
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1948066627503677516L;


	/**
	 * 	Get Attachment
	 *	@param ctx context
	 *	@param AD_Table_ID table
	 *	@param Record_ID record
	 *	@return attachment or null
	 * @deprecated Please use {@link IAttachmentDAO#retrieveAttachment(Properties, int, int)}
	 */
	@Deprecated
	public static MAttachment get (Properties ctx, int AD_Table_ID, int Record_ID)
	{
		final I_AD_Attachment attachment = Services.get(IAttachmentDAO.class).retrieveAttachment(ctx, AD_Table_ID, Record_ID, ITrx.TRXNAME_None);
		if (attachment == null)
		{
			return null;
		}
		
		return LegacyAdapters.convertToPO(attachment);
	}	//	get
	
//	/**	Static Logger	*/
//	private static Logger	s_log	= CLogMgt.getLogger(MAttachment.class);

	
	/**************************************************************************
	 * 	Standard Constructor
	 *	@param ctx context
	 *	@param AD_Attachment_ID id
	 *	@param trxName transaction
	 */
	public MAttachment(Properties ctx, int AD_Attachment_ID, String trxName)
	{
		super (ctx, AD_Attachment_ID, trxName);
		initAttachmentStoreDetails(ctx, trxName);

	}	//	MAttachment

	/**
	 * 	New Constructor
	 *	@param ctx context
	 *	@param AD_Table_ID table
	 *	@param Record_ID record
	 *	@param trxName transaction
	 */
	public MAttachment(Properties ctx, int AD_Table_ID, int Record_ID, String trxName)
	{
		this (ctx, 0, trxName);
		setAD_Table_ID (AD_Table_ID);
		setRecord_ID (Record_ID);
		initAttachmentStoreDetails(ctx, trxName);
	}	//	MAttachment

	/**
	 * 	Load Constructor
	 *	@param ctx context
	 *	@param rs result set
	 *	@param trxName transaction
	 */
	public MAttachment(Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
		initAttachmentStoreDetails(ctx, trxName);
	}	//	MAttachment
	
	/** Indicator for no data   */
	public static final String 	NONE = ".";
	/** Indicator for zip data  */
	public static final String 	ZIP = "zip";
	/** Indicator for xml data (store on file system) */
	public static final String 	XML = "xml";

	/**	List of Entry Data		*/
	private ArrayList<MAttachmentEntry> m_items = null;
	
	/** is this client using the file system for attachments */
	private boolean isStoreAttachmentsOnFileSystem = false;
	
	/** attachment (root) path - if file system is used */
	private String m_attachmentPathRoot = "";
	
	/** string replaces the attachment root in stored xml file
	 * to allow the changing of the attachment root. */
	private final String ATTACHMENT_FOLDER_PLACEHOLDER = "%ATTACHMENT_FOLDER%";
	
	/**
	 * Get the isStoreAttachmentsOnFileSystem and attachmentPath for the client.
	 * @param ctx
	 * @param trxName
	 */
	private void initAttachmentStoreDetails(Properties ctx, String trxName){
		final MClient client = new MClient(ctx, this.getAD_Client_ID(), trxName);
		isStoreAttachmentsOnFileSystem = client.isStoreAttachmentsOnFileSystem();
		if(isStoreAttachmentsOnFileSystem){
			if(File.separatorChar == '\\'){
				m_attachmentPathRoot = client.getWindowsAttachmentPath();
			} else {
				m_attachmentPathRoot = client.getUnixAttachmentPath();
			}
			if("".equals(m_attachmentPathRoot)){
				log.error("no attachmentPath defined");
			} else if (!m_attachmentPathRoot.endsWith(File.separator)){
				log.warn("attachment path doesn't end with " + File.separator);
				m_attachmentPathRoot = m_attachmentPathRoot + File.separator;
				log.debug(m_attachmentPathRoot);
			}
		}
	}
	
	/**
	 * 	Set Client Org
	 *	@param AD_Client_ID client
	 *	@param AD_Org_ID org
	 */
	@Override
	public void setClientOrg(int AD_Client_ID, int AD_Org_ID) 
	{
		super.setClientOrg(AD_Client_ID, AD_Org_ID);
	}	//	setClientOrg

	/**
	 * 	Add to Text Msg
	 *	@param added text
	 */
	public void addTextMsg (String added)
	{
		String oldTextMsg = getTextMsg();
		if (oldTextMsg == null)
			setTextMsg (added);
		else if (added != null)
			setTextMsg (oldTextMsg + added);
	}	//	addTextMsg
	
	/**
	 * 	Get Text Msg
	 *	@return trimmed message
	 */
	@Override
	public String getTextMsg ()
	{
		String msg = super.getTextMsg ();
		if (msg == null)
			return null;
		return msg.trim();
	}	//	setTextMsg
	
	/**
	 * 	String Representation
	 *	@return info
	 */
	@Override
	public String toString()
	{
		StringBuffer sb = new StringBuffer("MAttachment[");
		sb.append(getAD_Attachment_ID()).append(",Title=").append(getTitle())
			.append(",Entries=").append(getEntryCount());
		for (int i = 0; i < getEntryCount(); i++)
		{
			if (i == 0)
				sb.append(":");
			else
				sb.append(",");
			sb.append(getEntryName(i));
		}
		sb.append("]");
		return sb.toString();
	}	//	toString

	/**
	 * 	Add new Data Entry
	 *	@param file file
	 *	@return true if added
	 */
	public boolean addEntry (File file)
	{
		if (file == null)
		{
			log.warn("No File");
			return false;
		}
		if (!file.exists() || file.isDirectory())
		{
			log.warn("not added - " + file
				+ ", Exists=" + file.exists() + ", Directory=" + file.isDirectory());
			return false;
		}
		log.debug("addEntry - " + file);
		//
		String name = file.getName();
		byte[] data = null;
		try
		{
			FileInputStream fis = new FileInputStream (file);
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024*8];   //  8kB
			int length = -1;
			while ((length = fis.read(buffer)) != -1)
				os.write(buffer, 0, length);
			fis.close();
			data = os.toByteArray();
			os.close();
		}
		catch (IOException ioe)
		{
			log.error("(file)", ioe);
		}
		return addEntry (name, data);
	}	//	addEntry

	/**
	 * 	Add new Data Entry
	 *	@param name name
	 *	@param data data
	 *	@return true if added
	 */
	public boolean addEntry (String name, byte[] data)
	{
		if (name == null || data == null)
			return false;
		return addEntry (new MAttachmentEntry (name, data));	//	random index
	}	//	addEntry
	
	/**
	 * 	Add Entry
	 * 	@param item attachment entry
	 * 	@return true if added
	 */
	public boolean addEntry (MAttachmentEntry item)
	{
		if (item == null)
			return false;
		if (m_items == null)
			loadLOBData();
		boolean retValue = m_items.add(item);
		if(log.isDebugEnabled())
		{
			log.debug(item.toStringX());
		}
		addTextMsg(" ");	//	otherwise not saved
		return retValue;
	}	//	addEntry
	
	public boolean addEntry (final DataSource dataSource)
	{
		if (dataSource == null)
		{
			log.warn("Skip adding DataSource entry because it's null");
			return false;
		}

		try
		{
			final String name = dataSource.getName();
			final byte[] data = Util.readBytes(dataSource.getInputStream());
			return addEntry(new MAttachmentEntry(name, data));
		}
		catch (Exception ex)
		{
			log.warn("Failed adding DataSource {}", dataSource, ex);
			return false;
		}
	}


	/**
	 * 	Get Attachment Entry
	 * 	@param index index of the item
	 * 	@return Entry or null
	 */
	public MAttachmentEntry getEntry (int index)
	{
		if (m_items == null)
			loadLOBData();
		if (index < 0 || index >= m_items.size())
			return null;
		return m_items.get(index);
	}	//	getEntry
	
	/**
	 * 	Get Attachment Entries as array
	 * 	@return array; never return null
	 */
	public MAttachmentEntry[] getEntries ()
	{
		if (m_items == null)
			loadLOBData();
		MAttachmentEntry[] retValue = new MAttachmentEntry[m_items.size()];
		m_items.toArray (retValue);
		return retValue;
	}	//	getEntries
	
	/**
	 * Delete Entry
	 * 
	 * @param index
	 *            index
	 * @return true if deleted
	 */
	public boolean deleteEntry(int index) {
		if (index >= 0 && index < m_items.size()) {
			if(isStoreAttachmentsOnFileSystem){
				//remove files
				final MAttachmentEntry entry = m_items.get(index);
				final File file = entry.getFile();
				log.debug("delete: " + file.getAbsolutePath());
				if(file !=null && file.exists()){
					if(!file.delete()){
						log.warn("unable to delete " + file.getAbsolutePath());
					}
				}
			}
			m_items.remove(index);
			log.info("Index=" + index + " - NewSize=" + m_items.size());
			return true;
		}
		log.warn("Not deleted Index=" + index + " - Size=" + m_items.size());
		return false;
	} // deleteEntry
	
	/**
	 * 	Get Entry Count
	 *	@return number of entries
	 */
	public int getEntryCount()
	{
		if (m_items == null)
			loadLOBData();
		return m_items.size();
	}	//	getEntryCount
	
	
	/**
	 * Get Entry Name
	 * 
	 * @param index
	 *            index
	 * @return name or null
	 */
	public String getEntryName(int index) {
		MAttachmentEntry item = getEntry(index);
		if (item != null){
			//strip path
			String name = item.getName();
			if(name!=null && isStoreAttachmentsOnFileSystem){
				name = name.substring(name.lastIndexOf(File.separator)+1);
			}
			return name;
		}
		return null;
	} // getEntryName

	/**
	 * 	Dump Entry Names
	 */
	public void dumpEntryNames()
	{
		if (m_items == null)
			loadLOBData();
		if (m_items == null || m_items.size() == 0)
		{
			System.out.println("- no entries -");
			return;
		}
		System.out.println("- entries: " + m_items.size());
		for (int i = 0; i < m_items.size(); i++)
			System.out.println("  - " + getEntryName(i));		  
	}	//	dumpEntryNames

	/**
	 * 	Get Entry Data
	 * 	@param index index
	 * 	@return data or null
	 */
	public byte[] getEntryData (int index)
	{
		MAttachmentEntry item = getEntry(index);
		if (item != null)
			return item.getData();
		return null;
	}	//	getEntryData
	
	/**
	 * 	Get Entry File with name
	 * 	@param index index
	 *	@param fileName optional file name
	 *	@return file
	 */	
	public File getEntryFile (int index, String fileName)
	{
		MAttachmentEntry item = getEntry(index);
		if (item != null)
			return item.getFile(fileName);
		return null;
	}	//	getEntryFile

	/**
	 * 	Get Entry File with name
	 * 	@param index index
	 *	@param file file
	 *	@return file
	 */	
	public File getEntryFile (int index, File file)
	{
		MAttachmentEntry item = getEntry(index);
		if (item != null)
			return item.getFile(file);
		return null;
	}	//	getEntryFile

	
	/**
	 * 	Save Entry Data in Zip File format
	 *	@return true if saved
	 */
	private boolean saveLOBData()
	{
		if(isStoreAttachmentsOnFileSystem){
			return saveLOBDataToFileSystem();
		}
		return saveLOBDataToDB();
	}
	
	/**
	 * 	Save Entry Data in Zip File format into the database.
	 *	@return true if saved
	 */
	private boolean saveLOBDataToDB()
	{
		if (m_items == null || m_items.size() == 0)
		{
			setBinaryData(null);
			return true;
		}
		ByteArrayOutputStream out = new ByteArrayOutputStream(); 
		ZipOutputStream zip = new ZipOutputStream(out);
		zip.setMethod(ZipOutputStream.DEFLATED);
		zip.setLevel(Deflater.BEST_COMPRESSION);
		zip.setComment("metasfresh");
		//
		try
		{
			for (int i = 0; i < m_items.size(); i++)
			{
				MAttachmentEntry item = getEntry(i);
				ZipEntry entry = new ZipEntry(item.getName());
				entry.setTime(System.currentTimeMillis());
				entry.setMethod(ZipEntry.DEFLATED);
				zip.putNextEntry(entry);
				byte[] data = item.getData();
				zip.write (data, 0, data.length);
				zip.closeEntry();
				log.debug(entry.getName() + " - "
					+ entry.getCompressedSize() + " (" + entry.getSize() + ") "
					+ (entry.getCompressedSize()*100/entry.getSize())+ "%");
			}
		//	zip.finish();
			zip.close();
			byte[] zipData = out.toByteArray();
			log.debug("Length=" +  zipData.length);
			setBinaryData(zipData);
			return true;
		}
		catch (Exception e)
		{
			log.error("saveLOBData", e);
		}
		setBinaryData(null);
		return false;
	}	//	saveLOBData
	
	/**
	 * 	Save Entry Data to the file system.
	 *	@return true if saved
	 */
	private boolean saveLOBDataToFileSystem()
	{
		if("".equals(m_attachmentPathRoot)){
			log.error("no attachmentPath defined");
			return false;
		}
		if (m_items == null || m_items.size() == 0) {
			setBinaryData(null);
			return true;
		}
		final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			final DocumentBuilder builder = factory.newDocumentBuilder();
			final Document document = builder.newDocument();
			final Element root = document.createElement("attachments");
			document.appendChild(root);
		//	document.setXmlStandalone(true);
			// create xml entries
			for (int i = 0; i < m_items.size(); i++) {
				log.debug(m_items.get(i).toString());
				File entryFile = m_items.get(i).getFile();
				final String path = entryFile.getAbsolutePath();
				// if local file - copy to central attachment folder
				log.debug(path + " - " + m_attachmentPathRoot);
				if (!path.startsWith(m_attachmentPathRoot)) {
					log.debug("move file: " + path);
					FileChannel in = null;
					FileChannel out = null;
					try {
						//create destination folder
						final File destFolder = new File(m_attachmentPathRoot + File.separator + getAttachmentPathSnippet());
						if(!destFolder.exists()){
							if(!destFolder.mkdirs()){
								log.warn("unable to create folder: " + destFolder.getPath());
							}
						}
						final File destFile = new File(m_attachmentPathRoot + File.separator
								+ getAttachmentPathSnippet() + File.separator + entryFile.getName());
						in = new FileInputStream(entryFile).getChannel();
						out = new FileOutputStream(destFile).getChannel();
						in.transferTo(0, in.size(), out);
						in.close();
						out.close();
						if(entryFile.exists()){
							if(!entryFile.delete()){
								entryFile.deleteOnExit();
							}
						}
						entryFile = destFile;

					} catch (IOException e) {
						e.printStackTrace();
						log.error("unable to copy file " + entryFile.getAbsolutePath() + " to "
								+ m_attachmentPathRoot + File.separator + 
								getAttachmentPathSnippet() + File.separator + entryFile.getName());
					} finally {
						if (in != null && in.isOpen()) {
							in.close();
						}
						if (out != null && out.isOpen()) {
							out.close();
						}
					}
				}
				final Element entry = document.createElement("entry");
				//entry.setAttribute("name", m_items.get(i).getName());
				entry.setAttribute("name", getEntryName(i));
				String filePathToStore = entryFile.getAbsolutePath();
				filePathToStore = filePathToStore.replaceFirst(m_attachmentPathRoot.replaceAll("\\\\","\\\\\\\\"), ATTACHMENT_FOLDER_PLACEHOLDER);
				log.debug(filePathToStore);
				entry.setAttribute("file", filePathToStore);
				root.appendChild(entry);
			}

			final Source source = new DOMSource(document);
			final ByteArrayOutputStream bos = new ByteArrayOutputStream();
			final Result result = new StreamResult(bos);
			final Transformer xformer = TransformerFactory.newInstance().newTransformer();
			xformer.transform(source, result);
			final byte[] xmlData = bos.toByteArray();
			log.debug(bos.toString());
			setBinaryData(xmlData);
			return true;
		} catch (Exception e) {
			log.error("saveLOBData", e);
		}
		setBinaryData(null);
		return false;

	}
	
	/**
	 * 	Load Data into local m_data
	 *	@return true if success
	 */
	private boolean loadLOBData ()
	{
		if(isStoreAttachmentsOnFileSystem){
			return loadLOBDataFromFileSystem();
		}
		return loadLOBDataFromDB();
	}
	
	/**
	 * 	Load Data from database
	 *	@return true if success
	 */
	private boolean loadLOBDataFromDB ()
	{
		//	Reset
		m_items = new ArrayList<MAttachmentEntry>();
		//
		byte[] data = getBinaryData();
		if (data == null)
			return true;
		log.debug("ZipSize=" + data.length);
		if (data.length == 0)
			return true;
			
		//	Old Format - single file
		if (!ZIP.equals(getTitle()))
		{
			m_items.add (new MAttachmentEntry(getTitle(), data, 1));
			return true;
		}

		try
		{
			ByteArrayInputStream in = new ByteArrayInputStream(data);
			ZipInputStream zip = new ZipInputStream (in);
			ZipEntry entry = zip.getNextEntry();
			while (entry != null)
			{
				String name = entry.getName();
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				byte[] buffer = new byte[2048];
				int length = zip.read(buffer);
				while (length != -1)
				{
					out.write(buffer, 0, length);
					length = zip.read(buffer);
				}
				//
				byte[] dataEntry = out.toByteArray();
				log.debug(name 
					+ " - size=" + dataEntry.length + " - zip="
					+ entry.getCompressedSize() + "(" + entry.getSize() + ") "
					+ (entry.getCompressedSize()*100/entry.getSize())+ "%");
				//
				m_items.add (new MAttachmentEntry (name, dataEntry, m_items.size()+1));
				entry = zip.getNextEntry();
			}
		}
		catch (Exception e)
		{
			log.error("loadLOBData", e);
			m_items = null;
			return false;
		}
		return true;
	}	//	loadLOBData
	
	/**
	 * 	Load Data from file system
	 *	@return true if success
	 */
	private boolean loadLOBDataFromFileSystem(){
		if("".equals(m_attachmentPathRoot)){
			log.error("no attachmentPath defined");
			return false;
		}
		// Reset
		m_items = new ArrayList<MAttachmentEntry>();
		//
		byte[] data = getBinaryData();
		if (data == null)
			return true;
		log.debug("TextFileSize=" + data.length);
		if (data.length == 0)
			return true;

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

		try {
			final DocumentBuilder builder = factory.newDocumentBuilder();
			final Document document = builder.parse(new ByteArrayInputStream(data));
			final NodeList entries = document.getElementsByTagName("entry");
			for (int i = 0; i < entries.getLength(); i++) {
				final Node entryNode = entries.item(i);
				final NamedNodeMap attributes = entryNode.getAttributes();
				final Node fileNode = attributes.getNamedItem("file");
				final Node nameNode = attributes.getNamedItem("name");
				if(fileNode==null || nameNode==null){
					log.error("no filename for entry " + i);
					m_items = null;
					return false;
				}
				log.debug("name: " + nameNode.getNodeValue());
				String filePath = fileNode.getNodeValue();
				log.debug("filePath: " + filePath);
				if(filePath!=null){
					filePath = filePath.replaceFirst(ATTACHMENT_FOLDER_PLACEHOLDER, m_attachmentPathRoot.replaceAll("\\\\","\\\\\\\\"));
					//just to be shure...
					String replaceSeparator = File.separator;
					if(!replaceSeparator.equals("/")){
						replaceSeparator = "\\\\";
					}
					filePath = filePath.replaceAll("/", replaceSeparator);
					filePath = filePath.replaceAll("\\\\", replaceSeparator);
				}
				log.debug("filePath: " + filePath);
				final File file = new File(filePath);
				if (file.exists()) {
					// read files into byte[]
					final byte[] dataEntry = new byte[(int) file.length()];
					try {
						final FileInputStream fileInputStream = new FileInputStream(file);
						fileInputStream.read(dataEntry);
						fileInputStream.close();
					} catch (FileNotFoundException e) {
						log.error("File Not Found.");
						e.printStackTrace();
					} catch (IOException e1) {
						log.error("Error Reading The File.");
						e1.printStackTrace();
					}
					final MAttachmentEntry entry = new MAttachmentEntry(filePath,
							dataEntry, m_items.size() + 1);
					m_items.add(entry);
				} else {
					log.error("file not found: " + file.getAbsolutePath());
				}
			}

		} catch (SAXException sxe) {
			// Error generated during parsing)
			Exception x = sxe;
			if (sxe.getException() != null)
				x = sxe.getException();
			x.printStackTrace();
			log.error(x.getMessage());

		} catch (ParserConfigurationException pce) {
			// Parser with specified options can't be built
			pce.printStackTrace();
			log.error(pce.getMessage());

		} catch (IOException ioe) {
			// I/O error
			ioe.printStackTrace();
			log.error(ioe.getMessage());
		}

		return true;

	}
	
	/**
	 * Returns a path snippet, containing client, org, table and record id.
	 * @return String
	 */
	private String getAttachmentPathSnippet(){
		return this.getAD_Client_ID() + File.separator + 
		this.getAD_Org_ID() + File.separator + 
		this.getAD_Table_ID() + File.separator + this.getRecord_ID();
	}
	
	/**
	 * 	Before Save
	 *	@param newRecord new
	 *	@return true if can be saved
	 */
	@Override
	protected boolean beforeSave (boolean newRecord)
	{
		if(isStoreAttachmentsOnFileSystem){
			if (getTitle() == null || !getTitle().equals(XML)) {
				setTitle (XML);
			}
		} else {
			if (getTitle() == null || !getTitle().equals(ZIP)) {
				setTitle (ZIP);
			}
		}
		return saveLOBData();		//	save in BinaryData
	}	//	beforeSave

	/**
	 * 	Executed before Delete operation.
	 *	@return true if record can be deleted
	 */
	@Override
	protected boolean beforeDelete ()
	{
		if(isStoreAttachmentsOnFileSystem){
		//delete all attachment files and folder
		for(int i=0; i<m_items.size(); i++) {
			final MAttachmentEntry entry = m_items.get(i);
			final File file = entry.getFile();
			if(file !=null && file.exists()){
				if(!file.delete()){
					log.warn("unable to delete " + file.getAbsolutePath());
				}
			}
		}
		final File folder = new File(m_attachmentPathRoot + getAttachmentPathSnippet());
		if(folder.exists()){
			if(!folder.delete()){
				log.warn("unable to delete " + folder.getAbsolutePath());
			}
		}
		}
		return true;
	} 	//	beforeDelete

	/**
	 * Update existing entry
	 * @param i
	 * @param file
	 * @return true if success, false otherwise
	 */
	public boolean updateEntry(int i, File file) 
	{
		if (file == null)
		{
			log.warn("No File");
			return false;
		}
		if (!file.exists() || file.isDirectory())
		{
			log.warn("not added - " + file
				+ ", Exists=" + file.exists() + ", Directory=" + file.isDirectory());
			return false;
		}
		log.debug("updateEntry - " + file);
		//
		String name = file.getName();
		byte[] data = null;
		try
		{
			FileInputStream fis = new FileInputStream (file);
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024*8];   //  8kB
			int length = -1;
			while ((length = fis.read(buffer)) != -1)
				os.write(buffer, 0, length);
			fis.close();
			data = os.toByteArray();
			os.close();
		}
		catch (IOException ioe)
		{
			log.error("(file)", ioe);
		}
		return updateEntry (i, data);
		
	}
	
	/**
	 * Update existing entry
	 * @param i
	 * @param data
	 * @return true if success, false otherwise
	 */
	public boolean updateEntry(int i, byte[] data)
	{
		MAttachmentEntry entry = getEntry(i);
		if (entry == null) return false;
		entry.setData(data);
		return true;
	}

}	//	MAttachment
