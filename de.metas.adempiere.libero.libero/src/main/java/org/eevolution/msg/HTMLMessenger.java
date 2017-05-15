/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
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
 * Copyright (C) 2003-2007 e-Evolution,SC. All Rights Reserved.               *
 * Contributor(s): Victor Perez www.e-evolution.com                           *
 *****************************************************************************/

package org.eevolution.msg;

/*
 * #%L
 * de.metas.adempiere.libero.libero
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
import java.text.MessageFormat;
import java.text.SimpleDateFormat;

import org.compiere.model.MAttribute;
import org.compiere.model.MAttributeInstance;
import org.compiere.model.MAttributeSet;
import org.compiere.model.MAttributeSetInstance;
import org.compiere.model.MAttributeValue;
import org.compiere.model.MLocator;
import org.compiere.model.MProduct;
import org.compiere.model.MProject;
import org.compiere.model.MStorage;
import org.compiere.model.MWarehouse;
import org.compiere.util.Env;
import org.eevolution.model.MPPOrder;
import org.eevolution.model.reasoner.StorageReasoner;
import org.eevolution.model.wrapper.BOMLineWrapper;
import org.eevolution.model.wrapper.BOMWrapper;

import de.metas.i18n.Msg;

/**
 * @author Gunther Hoppe, tranSIT GmbH Ilmenau/Germany
 * @version 1.0, October 14th 2005
 */
@SuppressWarnings("all") // tsa: to many warnings in a code that we don't use. Suppress all to reduce noise.
public class HTMLMessenger {

	final protected String PRODUCT_TOOLTIP = 	
		"<html><H1 align=\"CENTER\">"+Msg.translate(Env.getCtx(), "M_Product_ID")+"</H1>"+
		"<table cellpadding=\"5\" cellspacing=\"5\">"+
		"<tr><td><b>"+Msg.translate(Env.getCtx(), "Description")+":</b></td><td>{}</td></tr>"+
		"</table></html>";

	final protected String LENGTHTRANSFORM_INFO_PATTERN = 	
		"<html>" +
		"<table cellpadding=\"5\" cellspacing=\"5\">"+
		"<tr><td><b>{}</b></td></tr>"+
		"<tr><td>{}</td></tr>"+
		"<tr><td>{}</td></tr>"+
		"</table></html>";	
	
	final protected String PP_ORDER_INFO_PATTERN = 	
			"<html><H1 align=\"CENTER\">"+Msg.translate(Env.getCtx(), "PP_Order_ID")+"</H1>"+
			"<table cellpadding=\"5\" cellspacing=\"5\">"+
			"<tr><td><b>"+Msg.translate(Env.getCtx(), "DocumentNo")+":</b></td><td>{}</td></tr>"+
			"<tr><td><b>"+Msg.translate(Env.getCtx(), "DateStartSchedule")+":</b></td><td>{}</td></tr>"+
			"<tr><td><b>"+Msg.translate(Env.getCtx(), "DateFinishSchedule")+":</b></td><td>{}</td></tr>"+
			"<tr><td><b>"+Msg.translate(Env.getCtx(), "C_Project_ID")+":</b></td><td>{}</td></tr>"+
			"<tr><td><b>"+Msg.translate(Env.getCtx(), "M_Product_ID")+":</b></td><td>{}</td></tr>"+
			"<tr><td><b>"+Msg.translate(Env.getCtx(), "QtyOrdered")+":</b></td><td>{}</td></tr>"+
			"<tr><td><b>"+Msg.translate(Env.getCtx(), "QtyDelivered")+":</b></td><td>{}</td></tr>"+
			"</table></html>";
	
	final protected String PP_ORDER_HEADER_INFO_PATTERN = 	
		"<html><H1 align=\"LEFT\">{}</H1>"+
		"<table cellpadding=\"5\" cellspacing=\"5\">"+
		"<tr>"+
		"<td><b>"+Msg.translate(Env.getCtx(), "DocumentNo")+"</b></td>"+
		"<td><b>"+Msg.translate(Env.getCtx(), "DateStartSchedule")+"</b></td>"+
		"<td><b>"+Msg.translate(Env.getCtx(), "DateFinishSchedule")+"</b></td>"+
		"<td><b>"+Msg.translate(Env.getCtx(), "C_Project_ID")+"</b></td>"+
		"<td><b>"+Msg.translate(Env.getCtx(), "M_Product_ID")+"</b></td>"+
		"<td><b>"+Msg.translate(Env.getCtx(), "QtyOrdered")+"</b></td>"+
		"<td><b>"+Msg.translate(Env.getCtx(), "QtyDelivered")+"</b></td>"+
		"<tr>"+
		"</table></html>";
	
	final protected String PP_ORDER_LINE_INFO_PATTERN = 	
		"<html>"+
		"<table cellpadding=\"5\" cellspacing=\"5\">"+
		"<tr>"+
		"<td>{}</td>"+
		"<td>{}</td>"+
		"<td>{}</td>"+
		"<td>{}</td>"+
		"<td>{}</td>"+
		"<td>{}</td>"+
		"<td>{}</td>"+
		"</tr>"+
		"</table></html>";
	
	final protected String BOM_INFO_PATTERN = 	
		"<html><H1 align=\"CENTER\">"+Msg.translate(Env.getCtx(), "PP_Product_BOM_ID")+"</H1>"+
		"<table cellpadding=\"5\" cellspacing=\"5\">"+
		"<tr><td><b>"+Msg.translate(Env.getCtx(), "DocumentNo")+":</b></td><td>{}</td></tr>"+
		"<tr><td><b>"+Msg.translate(Env.getCtx(), "PP_Product_BOM_ID")+":</b></td><td>{}</td></tr>"+
		"<tr><td><b>"+Msg.translate(Env.getCtx(), "ValidFrom")+":</b></td><td>{} - {}</td></tr>"+
		"<tr><td><b>"+Msg.translate(Env.getCtx(), "Value")+":</b></td><td>{}</td></tr>"+
		"<tr><td><b>"+Msg.translate(Env.getCtx(), "M_Product_ID")+":</b></td><td>{}</td></tr>"+
		"<tr><td></td><td>{}</td></tr>"+
		"</table>"+
		"<p>{}</p>"+
		"</html>";
	
	final protected String BOM_HEADER_INFO_PATTERN =
		"<table align=\"CENTER\" cellpadding=\"5\" cellspacing=\"5\"><tr>"
		+"<td><b>"+Msg.translate(Env.getCtx(), "Line")+"</b></td>"
		+"<td><b>"+Msg.translate(Env.getCtx(), "Qty")+"</b></td>"
		+"<td><b>"+Msg.translate(Env.getCtx(), "M_Product_ID")+"</b></td>"
		+"<td><b>"+Msg.translate(Env.getCtx(), "M_AttributeSetInstance_ID")+"</b></td>"
		+"</tr>";
	
	final protected String BOM_LINE_INFO_PATTERN =
		"<tr>"
		+"<td align=RIGHT>{}</td>"
		+"<td align=RIGHT>{}</td>"
		+"<td>{}</td>"
		+"<td>{}</td>"
		+"</tr>";

	final protected String BOMLINE_INFO_PATTERN = 	
		"<html><H1 align=\"CENTER\">"+Msg.translate(Env.getCtx(), "Line")+":&nbsp;{}</H1>"+
		"<table cellpadding=\"5\" cellspacing=\"5\">"+
		"<tr><td><b>"+Msg.translate(Env.getCtx(), "ComponentType")+":</b></td><td>{}</td></tr>"+
		"<tr><td><b>"+Msg.translate(Env.getCtx(), "ValidFrom")+":</b></td><td>{} - {}</td></tr>"+
		"<tr><td><b>"+Msg.translate(Env.getCtx(), "Qty")+":</b></td><td>{}</td></tr>"+
		"<tr><td><b>"+Msg.translate(Env.getCtx(), "M_Product_ID")+":</b></td><td>{}</td></tr>"+
		"<tr><td></td><td>{}</td></tr>" +
		"</table>"+
		"<p>{}</p>"+
		"</html>";

	final protected String STORAGE_HEADER_INFO_PATTERN =
		"<table align=\"CENTER\" cellpadding=\"5\" cellspacing=\"5\"><tr>"
		+"<td><b>"+Msg.translate(Env.getCtx(), "M_Locator_ID")+"</b></td>"
		+"<td><b>"+Msg.translate(Env.getCtx(), "M_Warehouse_ID")+"</b></td>"
		+"<td><b>"+Msg.translate(Env.getCtx(), "QtyOnHand")+"</b></td>"
		+"<td><b>"+Msg.translate(Env.getCtx(), "QtyReserved")+"</b></td>"
		+"<td><b>"+Msg.translate(Env.getCtx(), "QtyOrdered")+"</b></td>"
		+"<td><b>"+Msg.translate(Env.getCtx(), "QtyAvailable")+"</b></td>"
		+"</tr>";

	final protected String STORAGE_LINE_INFO_PATTERN =
		"<tr>"
		+"<td>{}</td>"
		+"<td>{}</td>"
		+"<td align=RIGHT>{}</td>"
		+"<td align=RIGHT>{}</td>"
		+"<td align=RIGHT>{}</td>"
		+"<td align=RIGHT>{}</td>"
		+"</tr>";

	final protected String STORAGE_SUM_LINE_INFO_PATTERN =
		"<tr>"
		+"<td></td>"
		+"<td></td>"
		+"<td align=RIGHT><hr size=\"1\" noshade=\"NOSHADE\">{}</td>"
		+"<td align=RIGHT><hr size=\"1\" noshade=\"NOSHADE\">{}</td>"
		+"<td align=RIGHT><hr size=\"1\" noshade=\"NOSHADE\">{}</td>"
		+"<td align=RIGHT><hr size=\"1\" noshade=\"NOSHADE\">{}</td>"
		+"</tr>";

	final protected String STORAGE_NOINVENTORY_INFO_PATTERN =
		"<tr>"
		+"<td align=\"CENTER\" colspan=\"6\">"+Msg.translate(Env.getCtx(), Msg.getMsg(Env.getCtx(), "NoQtyAvailable"))+"</td>"
		+"</tr>";
	
	final protected String STORAGE_FOOTER_INFO_PATTERN = "</table>";

	final protected String ATTRIBUTE_INFO_PATTERN = "{}&nbsp;=&nbsp;<i>{}</i>";
	
	public String getProductInfo(MProduct p) {

    	
    	Object[] obj = new Object[] {
    			p.getDescription() == null ? "" : p.getDescription()
    	};
    	
		return MessageFormat.format(PRODUCT_TOOLTIP, obj);	    	
	}

	public String getLengthTransformInfo(MProduct p, BigDecimal srcLength, BigDecimal tgtLength, BigDecimal pieces) {

    	BigDecimal scrapLength = srcLength.subtract(tgtLength.multiply(pieces));
    	
    	Object[] obj = new Object[] {
    			p.getName()+" ("+p.getValue()+")",
    			"1 x "+srcLength.setScale(2, BigDecimal.ROUND_HALF_DOWN)+" &#8594; "+pieces+" x "+tgtLength.setScale(2, BigDecimal.ROUND_HALF_DOWN),
    			Msg.translate(Env.getCtx(), "Scrap")+": 1 x "+scrapLength.setScale(2, BigDecimal.ROUND_HALF_DOWN),
    	};
    	
		return MessageFormat.format(LENGTHTRANSFORM_INFO_PATTERN, obj);	    	
	}

	public String getMfcOrderInfo(MPPOrder o) {

		MProject pj = new MProject(Env.getCtx(), o.getC_Project_ID(), null);
		MProduct pd = new MProduct(Env.getCtx(), o.getM_Product_ID(), null);
    	
    	Object[] obj = new Object[] {
    			o.getDocumentNo(),
    			o.getDateStartSchedule(),
    			o.getDateFinishSchedule(),
    			(pj.getName() == null ? "-" : pj.getName())+(pj.getValue() == null ? "" : " ("+pj.getValue()+")"),
    			pd.getName()+" ("+pd.getValue()+")",
    			o.getQtyOrdered(),
    			o.getQtyDelivered()
    	};
		return MessageFormat.format(PP_ORDER_INFO_PATTERN, obj);	    	
	}

	public String getBOMLinesInfo(BOMLineWrapper[] lines) {

    	MProduct p = null;
    	MAttributeSetInstance asi = null;
    	
    	StringBuffer sb = new StringBuffer(BOM_HEADER_INFO_PATTERN);
    	for(int i = 0; i < lines.length; i++) {
    		
        	p = new MProduct(Env.getCtx(), lines[i].getM_Product_ID(), MProduct.Table_Name);
        	asi = new MAttributeSetInstance(Env.getCtx(), lines[i].getM_AttributeSetInstance_ID(), MAttributeSetInstance.Table_Name);
        	
        	Object[] obj = new Object[] {
        			new Integer(lines[i].getPo()),
                	lines[i].getQtyBOM(),
                	p.getName(),
                	getAttributeSetInstanceInfo(asi, true)
        	};
        	
        	sb.append(MessageFormat.format(BOM_LINE_INFO_PATTERN, obj));
    	}

    	return sb.toString();
	}
	
	public String getBOMLineInfo(BOMLineWrapper mpbl) {

		SimpleDateFormat df = Env.getLanguage(Env.getCtx()).getDateFormat();

    	MProduct p = new MProduct(Env.getCtx(), mpbl.getM_Product_ID(), MProduct.Table_Name);
    	MAttributeSetInstance asi = new MAttributeSetInstance(Env.getCtx(), mpbl.getM_AttributeSetInstance_ID(), MAttributeSetInstance.Table_Name);
    	
    	Object[] obj = new Object[] {
    			new Integer(mpbl.getPo()),
            	mpbl.getComponentType(),
            	mpbl.getValidFrom() == null ? "" : df.format(mpbl.getValidFrom()),
                mpbl.getValidTo() == null ? "" : df.format(mpbl.getValidTo()),
            	mpbl.getQtyBOM(),
            	p.getName(),
            	getAttributeSetInstanceInfo(asi, false),
            	getStorageInfo(p, asi)
    	};

    	return MessageFormat.format(BOMLINE_INFO_PATTERN, obj);
	}
	
	public String getBOMInfo(BOMWrapper pb) {

		SimpleDateFormat df = Env.getLanguage(Env.getCtx()).getDateFormat();

		MProduct p = new MProduct(Env.getCtx(), pb.getM_Product_ID(), MProduct.Table_Name);
    	MAttributeSetInstance asi = new MAttributeSetInstance(Env.getCtx(), pb.getM_AttributeSetInstance_ID(), "M_AttributeSetInstance");

    	Object[] obj = new Object[] {
            	pb.getDocumentNo(),
            	pb.getName(),
            	pb.getValidFrom() == null ? "" : df.format(pb.getValidFrom()),
            	pb.getValidTo() == null ? "" : df.format(pb.getValidTo()),
            	pb.getValue(),
            	p.getName(),
            	getAttributeSetInstanceInfo(asi, false),
            	getBOMLinesInfo(pb.getLines())
    	};
    	
		return MessageFormat.format(BOM_INFO_PATTERN, obj);		
	}

	public String getAttributeSetInstanceInfo(MAttributeSetInstance asi, boolean singleRow) {
		
		MAttributeSet as = new MAttributeSet(Env.getCtx(), asi.getM_AttributeSet_ID(), null);
		
		StorageReasoner mr = new StorageReasoner();
		int[] ids = mr.getAttributeIDs(asi);
		
		MAttributeInstance ai = null;
		MAttribute a = null;
		MAttributeValue av = null;
		
		StringBuffer sb = new StringBuffer();
		String value = null;
		Object[] obj = null;
		for(int i = 0; i < ids.length; i++) {
			
			ai = new MAttributeInstance(Env.getCtx(), ids[i], asi.get_ID(), (String)null, null);
			ai.load(null);
			a = new MAttribute(Env.getCtx(), ai.getM_Attribute_ID(), null);
			av = new MAttributeValue(Env.getCtx(), ai.getM_AttributeValue_ID(), null);
			
			// Switchs value to referenced value of M_AttributeValue, if no value is directly available,
			// e.g. the list validation type 'L'
			if(ai.getValue() == null) {
			
				value = av.getValue();
			}
			// Takes the value of the M_AttributeInstance itself
			else {
				
				// Round number values to a scale of 2, if type is 'N'
				if(MAttribute.ATTRIBUTEVALUETYPE_Number.equals(a.getAttributeValueType())) {
					
					BigDecimal number = ai.getValueNumber();
					value = number.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
				}
				else {
					
					value = ai.getValue();
				}
			}
			
	    	obj = new Object[] {
	    			a.getName(),
	    			value
	    	};

	    	sb.append(MessageFormat.format(ATTRIBUTE_INFO_PATTERN, obj));
	    	
	    	if(singleRow) {
	    		
	    		sb.append("&nbsp;");
	    	}
	    	else {
	    		
	    		sb.append("<br>");
	    	}
		}
		
		return sb.toString();
	}

	public String getStorageInfo(MProduct p, MAttributeSetInstance asi) {
		
		StorageReasoner mr = new StorageReasoner();
		int[] ids = mr.getPOIDs(MLocator.Table_Name, null, null);

		MWarehouse warehouse = null;
		MStorage storage = null;
		MLocator locator = null;
		StringBuffer sb = new StringBuffer(STORAGE_HEADER_INFO_PATTERN);
		Object[] obj = null;
		
		BigDecimal sumQtyOnHand = BigDecimal.ZERO;
		BigDecimal sumQtyReserved = BigDecimal.ZERO;
		BigDecimal sumQtyOrdered = BigDecimal.ZERO;
		
		int count = 0;
		for(int i = 0; i < ids.length; i++) {
			
			storage = MStorage.get(Env.getCtx(), ids[i], p.get_ID(), asi.get_ID(), null);
			if(storage == null) {
				
				continue;
			}
			count++;
			
			warehouse = new MWarehouse(Env.getCtx(), storage.getM_Warehouse_ID(), null);
			locator = new MLocator(Env.getCtx(), storage.getM_Locator_ID(), null);
		
			sumQtyOnHand = sumQtyOnHand.add(storage.getQtyOnHand());
			sumQtyReserved = sumQtyReserved.add(storage.getQtyReserved());
			sumQtyOrdered = sumQtyOrdered.add(storage.getQtyOrdered());
			
			// the quantities of specific locator
	    	obj = new Object[] {
					locator.getX()+" - "+locator.getY()+" - "+locator.getZ(),
					warehouse.getName(),
					storage.getQtyOnHand(),
					storage.getQtyReserved(),
					storage.getQtyOrdered(),
					storage.getQtyOnHand().subtract(storage.getQtyReserved())
	    	};
	    	
			sb.append(MessageFormat.format(STORAGE_LINE_INFO_PATTERN, obj));
		}

		// the sum of the single quantities, if there is more than one line
		if(count > 1) {

			obj = new Object[] {
					sumQtyOnHand,
					sumQtyReserved,
					sumQtyOrdered,
					sumQtyOnHand.subtract(sumQtyReserved)
	    	};
	    	
			sb.append(MessageFormat.format(STORAGE_SUM_LINE_INFO_PATTERN, obj));
		}

		double available = sumQtyOnHand.subtract(sumQtyReserved).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		if(count == 0 || (available <= 0.00d)) {
			
			sb.append(MessageFormat.format(STORAGE_NOINVENTORY_INFO_PATTERN, obj));
		}
		
		sb.append(STORAGE_FOOTER_INFO_PATTERN);
		
		return sb.toString();
	}
}
