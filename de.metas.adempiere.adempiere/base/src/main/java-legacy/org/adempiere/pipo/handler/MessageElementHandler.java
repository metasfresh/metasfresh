/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 Adempiere, Inc. All Rights Reserved.                *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 *
 * Copyright (C) 2005 Robert Klein. robeklein@hotmail.com
 * Contributor(s):	Low Heng Sin hengsin@avantz.com
 * 					Teo Sarca, teo.sarca@gmail.com
 *****************************************************************************/
package org.adempiere.pipo.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.xml.transform.sax.TransformerHandler;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.pipo.AbstractElementHandler;
import org.adempiere.pipo.Element;
import org.adempiere.pipo.PackOut;
import org.adempiere.pipo.exception.POSaveFailedException;
import org.compiere.model.I_AD_Message;
import org.compiere.model.Query;
import org.compiere.model.X_AD_Message;
import org.compiere.model.X_AD_Package_Exp_Detail;
import org.compiere.util.Env;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

public class MessageElementHandler extends AbstractElementHandler {

	private List<Integer> messages = new ArrayList<Integer>();
	
	public void startElement(Properties ctx, Element element) throws SAXException {
		String elementValue = element.getElementValue();
		Attributes atts = element.attributes;
		log.info(elementValue+" "+atts.getValue("Value"));
		String entitytype = atts.getValue("EntityType");
		if (isProcessElement(ctx, entitytype)) {
			String value = atts.getValue("Value");
			int id = get_IDWithColumn(ctx, "AD_Message", "value", value);

			I_AD_Message m_Message = InterfaceWrapperHelper.create(ctx, id, I_AD_Message.class, getTrxName(ctx));
			int AD_Backup_ID  = -1;
			String Object_Status = null;
			if (id <= 0 && atts.getValue("AD_Message_ID") != null && Integer.parseInt(atts.getValue("AD_Message_ID")) <= PackOut.MAX_OFFICIAL_ID)
				m_Message.setAD_Message_ID(Integer.parseInt(atts.getValue("AD_Message_ID")));
			if (id > 0){		
				AD_Backup_ID = copyRecord(ctx, "AD_Message",m_Message);
				Object_Status = "Update";			
			}
			else{
				Object_Status = "New";
				AD_Backup_ID =0;
			}    	    
			m_Message.setMsgText(getStringValue(atts, "MsgText"));
			m_Message.setMsgTip(getStringValue(atts, "MsgTip"));
			m_Message.setEntityType(atts.getValue("EntityType"));
			m_Message.setIsActive(atts.getValue("isActive") != null ? Boolean.valueOf(atts.getValue("isActive")).booleanValue():true);
			m_Message.setValue(value);
			m_Message.setMsgType(atts.getValue("MsgType"));
			
			try
			{
				InterfaceWrapperHelper.save(m_Message, getTrxName(ctx));
				record_log (ctx, 1, m_Message.getValue(),"Message", m_Message.getAD_Message_ID(),AD_Backup_ID, Object_Status,"AD_Message",get_IDWithColumn(ctx, "AD_Table", "TableName", "AD_Message"));           		        		
			}
			catch (Exception e)
			{
				record_log (ctx, 0, m_Message.getValue(),"Message", m_Message.getAD_Message_ID(),AD_Backup_ID, Object_Status,"AD_Message",get_IDWithColumn(ctx, "AD_Table", "TableName", "AD_Message"));
				throw new POSaveFailedException("Failed to save message.", e);
				
			}
		} else {
			element.skip = true;
		}

	}

	public void endElement(Properties ctx, Element element) throws SAXException {
	}

	public void create(Properties ctx, TransformerHandler document) throws SAXException
	{
		for (X_AD_Message message : getMessages(ctx))
		{
			if (messages.contains(message.getAD_Message_ID()))
				continue;
			messages.add(message.getAD_Message_ID());
			//
			AttributesImpl atts = new AttributesImpl();
			createMessageBinding(atts, message);	
			document.startElement("","","message",atts);
			document.endElement("","","message");
		}
	}
	
	private List<X_AD_Message> getMessages(Properties ctx)
	{
		int AD_Message_ID = Env.getContextAsInt(ctx, X_AD_Package_Exp_Detail.COLUMNNAME_AD_Message_ID);
		int AD_EntityType_ID = Env.getContextAsInt(ctx, X_AD_Package_Exp_Detail.COLUMNNAME_AD_EntityType_ID);
		String whereClause;
		Object[] params;
		if (AD_Message_ID > 0)
		{
			whereClause = X_AD_Message.COLUMNNAME_AD_Message_ID+"=?";
			params = new Object[]{AD_Message_ID};
		}
		else if (AD_EntityType_ID > 0)
		{
			whereClause = " EXISTS (SELECT 1 FROM AD_EntityType et"
				+" WHERE et.AD_EntityType_ID=? AND et.EntityType=AD_Message.EntityType)";
			params = new Object[]{AD_EntityType_ID};
		}
		else
		{
			throw new IllegalArgumentException("AD_Message_ID and AD_EntityType_ID not found");
		}
		
		List<X_AD_Message> list = new Query(ctx, X_AD_Message.Table_Name, whereClause, null)
		.setParameters(params)
		.setOrderBy(X_AD_Message.COLUMNNAME_AD_Message_ID)
		.list();
		return list;
	}

	private AttributesImpl createMessageBinding( AttributesImpl atts, X_AD_Message m_Message) 
	{
		atts.clear();
		if (m_Message.getAD_Message_ID() <= PackOut.MAX_OFFICIAL_ID)
	        atts.addAttribute("","","AD_Message_ID","CDATA",Integer.toString(m_Message.getAD_Message_ID()));
		//FIXME:  may not need this I guess
		//atts.addAttribute("","","AccessLevel","CDATA",(m_Message.getAccessLevel () != null ? m_Message.getAccessLevel ():""));
		atts.addAttribute("","","MsgText","CDATA",(m_Message.getMsgText() != null ? m_Message.getMsgText():""));
		atts.addAttribute("","","MsgType","CDATA",(m_Message.getMsgType() != null ? m_Message.getMsgType ():""));
		atts.addAttribute("","","MsgTip","CDATA",(m_Message.getMsgTip() != null ? m_Message.getMsgTip ():""));
		atts.addAttribute("","","Value","CDATA",(m_Message.getValue() != null ? m_Message.getValue ():""));
		atts.addAttribute("","","EntityType","CDATA",(m_Message.getEntityType () != null ? m_Message.getEntityType ():""));
		atts.addAttribute("","","isActive","CDATA",(m_Message.isActive()== true ? "true":"false"));
		return atts;
	}
}
