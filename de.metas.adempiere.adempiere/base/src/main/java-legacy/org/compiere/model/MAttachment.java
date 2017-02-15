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
import java.io.IOException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;
import java.util.zip.Deflater;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import javax.activation.DataSource;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.IAttachmentDAO;
import org.adempiere.util.Check;
import org.adempiere.util.LegacyAdapters;
import org.adempiere.util.Services;
import org.compiere.util.Util;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.google.common.base.Throwables;
import com.google.common.collect.ImmutableList;

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

	private static final String INDEX_Filename = ".index";

	/**	List of Entry Data		*/
	private ArrayList<MAttachmentEntry> m_items = null;
	private final AtomicInteger _nextEntryId = new AtomicInteger(1);
	
	/** is this client using the file system for attachments */
	private boolean isStoreAttachmentsOnFileSystem = false;
	
	/** attachment (root) path - if file system is used */
	private String m_attachmentPathRoot = "";
	
	/** string replaces the attachment root in stored xml file
	 * to allow the changing of the attachment root. */
	private static final String ATTACHMENT_FOLDER_PLACEHOLDER = "%ATTACHMENT_FOLDER%";
	
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
				log.warn("no attachmentPath defined");
			} else if (!m_attachmentPathRoot.endsWith(File.separator)){
				log.debug("attachment path doesn't end with " + File.separator);
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
		try(FileInputStream fis = new FileInputStream (file))
		{
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
			log.warn("(file)", ioe);
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
		
		if(INDEX_Filename.equalsIgnoreCase(name))
		{
			throw new IllegalArgumentException("Invalid filename: " + name);
		}
		
		if (m_items == null)
			loadLOBData();
		
		final MAttachmentEntry item = createAttachmentEntry(name, data);
		boolean retValue = m_items.add(item);
		if(log.isDebugEnabled())
		{
			log.debug(item.toStringX());
		}
		addTextMsg(" ");	//	otherwise not saved
		markColumnChanged(COLUMNNAME_BinaryData);
		return retValue;
	}	//	addEntry
	
	private MAttachmentEntry createAttachmentEntry(final String name, byte[] data)
	{
		return new MAttachmentEntry(name, data, _nextEntryId.getAndIncrement());
	}
	
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
			return addEntry(name, data);
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
	
	public List<MAttachmentEntry> getEntriesAsList()
	{
		if (m_items == null)
			loadLOBData();
		final List<MAttachmentEntry> items = m_items;
		return items == null ? ImmutableList.of() : ImmutableList.copyOf(m_items);
		
	}
	
	public MAttachmentEntry getEntryById(final int id)
	{
		if (m_items == null)
			loadLOBData();
		return m_items.stream()
				.filter(entry -> id == entry.getId())
				.findFirst()
				.orElse(null);
		
	}
	
	/**
	 * Delete Entry
	 * 
	 * @param index
	 *            index
	 * @return true if deleted
	 */
	public boolean deleteEntry(final int index)
	{
		if (m_items == null)
			loadLOBData();
		
		if (index >= 0 && index < m_items.size())
		{
			if(isStoreAttachmentsOnFileSystem)
			{
				//remove files
				final MAttachmentEntry entry = m_items.get(index);
				final File file = entry.getFile();
				if(file !=null && file.exists())
				{
					if(!file.delete())
					{
						log.warn("unable to delete " + file.getAbsolutePath());
					}
				}
			}
			m_items.remove(index);
			markColumnChanged(COLUMNNAME_BinaryData); // to make sure it will be saved
			return true;
		}
		else
		{
			throw new AdempiereException("Entry not found (index=" + index + ")");
		}
	} // deleteEntry
	
	public void deleteEntryById(final int id)
	{
		if (m_items == null)
			loadLOBData();
		
		for (int index = 0, size = m_items.size(); index < size; index++)
		{
			final MAttachmentEntry entry = m_items.get(index);
			if (entry.getId() == id)
			{
				deleteEntry(index);
				return;
			}
		}
		
		throw new AdempiereException("Entry not found for ID=" + id);
	}
	
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
	public File saveEntryToFile (int index, String fileName)
	{
		MAttachmentEntry item = getEntry(index);
		if (item != null)
			return item.saveToFile(fileName);
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
		{
			item.saveToFile(file);
			return file;
		}
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
			final Properties descriptor = new Properties();
			
			for (int i = 0; i < m_items.size(); i++)
			{
				MAttachmentEntry item = getEntry(i);
				final String entryName = item.getName();
				ZipEntry entry = new ZipEntry(entryName);
				entry.setTime(System.currentTimeMillis());
				entry.setMethod(ZipEntry.DEFLATED);
				zip.putNextEntry(entry);
				byte[] data = item.getData();
				zip.write (data, 0, data.length);
				zip.closeEntry();
				
				descriptor.setProperty(entryName, String.valueOf(item.getId()));
			}
			
			// Write descriptor
			{
				final ByteArrayOutputStream baos = new ByteArrayOutputStream();
				descriptor.store(baos, "");
				
				final ZipEntry entry = new ZipEntry(INDEX_Filename);
				entry.setTime(System.currentTimeMillis());
				entry.setMethod(ZipEntry.DEFLATED);
				zip.putNextEntry(entry);
				byte[] data = baos.toByteArray();
				zip.write (data, 0, data.length);
				zip.closeEntry();
			}
			
		//	zip.finish();
			zip.close();
			byte[] zipData = out.toByteArray();
			setBinaryData(zipData);
			return true;
		}
		catch (Exception e)
		{
			log.warn("saveLOBData", e);
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
			log.warn("no attachmentPath defined");
			return false;
		}
		if (m_items == null || m_items.size() == 0)
		{
			setBinaryData(null);
			return true;
		}
		
		final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try
		{
			final DocumentBuilder builder = factory.newDocumentBuilder();
			final Document document = builder.newDocument();
			final Element root = document.createElement("attachments");
			document.appendChild(root);
		//	document.setXmlStandalone(true);
			
			// create xml entries
			for (int i = 0; i < m_items.size(); i++)
			{
				final MAttachmentEntry attachmentEntry = m_items.get(i);
				final File storageFile = getStorageFile(attachmentEntry);
				attachmentEntry.saveToFile(storageFile);
				
				final Element entry = document.createElement("entry");
				
				entry.setAttribute("id", String.valueOf(attachmentEntry.getId()));
				
				//entry.setAttribute("name", m_items.get(i).getName());
				entry.setAttribute("name", getEntryName(i));
				
				String filePathToStore = storageFile.getAbsolutePath();
				filePathToStore = filePathToStore.replaceFirst(m_attachmentPathRoot.replaceAll("\\\\","\\\\\\\\"), ATTACHMENT_FOLDER_PLACEHOLDER);
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
			log.warn("saveLOBData", e);
		}
		setBinaryData(null);
		return false;

	}
	
	private File getStorageFile(final MAttachmentEntry entry)
	{
		final File entryFile = entry.getFile();
		
		//create destination folder
		final File destFolder = new File(m_attachmentPathRoot + File.separator + getAttachmentPathSnippet());
		if(!destFolder.exists()){
			if(!destFolder.mkdirs()){
				log.warn("unable to create folder: " + destFolder.getPath());
			}
		}
		final File destFile = new File(m_attachmentPathRoot + File.separator
				+ getAttachmentPathSnippet() + File.separator + entryFile.getName());
		
		return destFile;
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
		{
			_nextEntryId.set(1);
			return true;
		}
			
		//	Old Format - single file
		if (!ZIP.equals(getTitle()))
		{
			_nextEntryId.set(1);
			m_items.add (createAttachmentEntry(getTitle(), data));
			return true;
		}

		final Properties descriptor = new Properties();
		final List<Supplier<MAttachmentEntry>> entrySuppliers = new ArrayList<>();
		try
		{
			ByteArrayInputStream in = new ByteArrayInputStream(data);
			ZipInputStream zip = new ZipInputStream (in);
			ZipEntry entry = zip.getNextEntry();
			while (entry != null)
			{
				final String name = entry.getName();
				
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				final byte[] buffer = new byte[2048];
				int length = zip.read(buffer);
				while (length != -1)
				{
					out.write(buffer, 0, length);
					length = zip.read(buffer);
				}
				final byte[] dataEntry = out.toByteArray();

				
				if(INDEX_Filename.equals(name))
				{
					descriptor.load(new ByteArrayInputStream(dataEntry));
				}
				else
				{
					entrySuppliers.add(() -> {
						String idStr = descriptor.getProperty(name);
						int id = 0;
						if(!Check.isEmpty(idStr, true))
						{
							id = Integer.valueOf(idStr);
						}
						//
						if(id <= 0)
						{
							return createAttachmentEntry(name, dataEntry);
						}
						else
						{
							return new MAttachmentEntry(name, dataEntry, id);
						}
					});
				}

				//
				entry = zip.getNextEntry();
			}
			
			//
			// Find out last id
			final int lastId = descriptor.values()
					.stream()
					.mapToInt(idObj -> Integer.valueOf(idObj.toString()))
					.max()
					.orElse(0);
			
			//
			//
			_nextEntryId.set(lastId + 1);
			//m_items = new ArrayList<>();
			entrySuppliers.stream()
					.map(supplier -> supplier.get())
					.forEach(m_items::add);
		}
		catch (Exception e)
		{
			log.warn("loadLOBData", e);
			m_items = null;
			return false;
		}
		return true;
	}	//	loadLOBData
	
	/**
	 * 	Load Data from file system
	 *	@return true if success
	 */
	private boolean loadLOBDataFromFileSystem()
	{
		// reset
		m_items = new ArrayList<>();
		
		if("".equals(m_attachmentPathRoot)){
			log.warn("no attachmentPath defined");
			return false;
		}
		//
		byte[] data = getBinaryData();
		if (data == null)
			return true;
		log.debug("TextFileSize=" + data.length);
		if (data.length == 0)
			return true;

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

		final List<Supplier<MAttachmentEntry>> entrySuppliers = new ArrayList<>();
		int lastId = 0;
		try
		{
			final DocumentBuilder builder = factory.newDocumentBuilder();
			final Document document = builder.parse(new ByteArrayInputStream(data));
			final NodeList entries = document.getElementsByTagName("entry");
			for (int i = 0; i < entries.getLength(); i++)
			{
				final Node entryNode = entries.item(i);
				final NamedNodeMap attributes = entryNode.getAttributes();
				final Node idNode = attributes.getNamedItem("id");
				final Node nameNode = attributes.getNamedItem("name");
				final Node fileNode = attributes.getNamedItem("file");
				if(fileNode==null || nameNode==null)
				{
					log.warn("no filename for entry " + i);
					continue;
				}
				
				//
				int id = 0;
				if(idNode != null)
				{
					final String idStr = idNode.getNodeValue();
					if(!Check.isEmpty(idStr, true))
					{
						try
						{
							id = Integer.parseInt(idStr);
						}
						catch (Exception e)
						{
						}
					}
				}
				if(id > 0)
				{
					lastId = Math.max(lastId, id);
				}
				
				//
				String filePath = fileNode.getNodeValue();
				if(filePath != null)
				{
					filePath = filePath.replaceFirst(ATTACHMENT_FOLDER_PLACEHOLDER, m_attachmentPathRoot.replaceAll("\\\\","\\\\\\\\"));
					//just to be sure...
					String replaceSeparator = File.separator;
					if(!replaceSeparator.equals("/")){
						replaceSeparator = "\\\\";
					}
					filePath = filePath.replaceAll("/", replaceSeparator);
					filePath = filePath.replaceAll("\\\\", replaceSeparator);
				}
				final File file = new File(filePath).getAbsoluteFile();
				if (!file.exists())
				{
					log.warn("file not found: {}", file);
					continue;
				}
				
				// read files into byte[]
				final byte[] dataEntry = new byte[(int) file.length()];
				try
				{
					final FileInputStream fileInputStream = new FileInputStream(file);
					fileInputStream.read(dataEntry);
					fileInputStream.close();
				}
				catch (Exception e)
				{
					log.warn("Error Reading The File.", e);
				}
				

				if(id > 0)
				{
					final MAttachmentEntry entry = new MAttachmentEntry(file.getAbsolutePath(), dataEntry, id);
					entrySuppliers.add(() -> entry);
				}
				else
				{
					entrySuppliers.add(() -> createAttachmentEntry(file.getAbsolutePath(), dataEntry));
				}
			
			} // each entry
			
			
			//
			//
			_nextEntryId.set(lastId + 1);
			entrySuppliers.stream()
					.map(supplier -> supplier.get())
					.forEach(m_items::add);
			
			entrySuppliers.forEach(supplier -> supplier.get());

			return true;
		}
		catch (Exception ex)
		{
			// Error generated during parsing)
			log.warn("", Throwables.getRootCause(ex));
			return false;
		}
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
		//
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
			log.warn("(file)", ioe);
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
