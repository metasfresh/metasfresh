package org.eevolution.process;

import org.compiere.model.MDocType;
import org.compiere.model.MGLCategory;
import org.compiere.model.MSequence;
import org.compiere.util.Env;

import de.metas.process.SvrProcess;


/**
 *	AddLiberoRecords
 *	
 *	@author Tim Heath
 *	@version $Id: AddLiberoRecords.java,v 1 xp_prg Exp $
 */
public class CreateDocType extends SvrProcess
{
	/**					*/
        
	@SuppressWarnings("unused")
	private int AD_Client_ID = 0 ;
	private String trxname = null;
        
	/**
	 *  Prepare - e.g., get Parameters.
	 */
	@Override
	protected void prepare()
	{
		System.out.println("In AddLiberoRecords prepare");
		log.debug("In AddLiberoRecords prepare");
		AD_Client_ID = Integer.parseInt(Env.getContext(Env.getCtx(), "#AD_Client_ID"));
	}	//	prepare

        
     @Override
	protected String doIt() throws Exception                
	{
		System.out.println("In AddLiberoRecords doIt");
		log.debug("In AddLiberoRecords doIt");

			//MClient c = (MClient)po;
	        //Properties m_ctx = Env.getCtx();
	        trxname = get_TrxName();
			//Manufacturing Document
			int GL_Manufacturing = createGLCategory("Manufactuing", MGLCategory.CATEGORYTYPE_Document, false);
			int GL_Distribution = createGLCategory("Distribution", MGLCategory.CATEGORYTYPE_Document, false);
			//Payroll GLCategory created in 140_FB1935325HRProcess.sql
			//int GL_Payroll = createGLCategory("Payroll", MGLCategory.CATEGORYTYPE_Document, false);
			
			createDocType("Manufacturing Order", "Manufacturing Order", 
				MDocType.DOCBASETYPE_ManufacturingOrder, null,
				0, 0, 80000, GL_Manufacturing);
			createDocType("Manufacturing Cost Collector","Cost Collector", 
				MDocType.DOCBASETYPE_ManufacturingCostCollector, null, 
				0, 0, 81000, GL_Manufacturing);
			createDocType("Maintenance Order","Maintenance Order",
				MDocType.DOCBASETYPE_MaintenanceOrder, null,
				0, 0, 86000, GL_Manufacturing);
			createDocType("Quality Order","Quality Order",
					MDocType.DOCBASETYPE_QualityOrder, null,
				0, 0, 87000, GL_Manufacturing);
			createDocType("Distribution Order","Distribution Orde", 
				MDocType.DOCBASETYPE_DistributionOrder, null,
				0, 0, 88000, GL_Distribution);
/*			//Payroll DocType created in 140_FB1935325HRProcess.sql
			createDocType("Payroll","Payroll", 
				MDocType.DOCBASETYPE_Payroll, null,
				0, 0, 90000, GL_Payroll);
*/
			return "ok";
	
		}
            

	/**
	 *  Create GL Category
	 *  @param Name name
	 *  @param CategoryType category type MGLCategory.CATEGORYTYPE_*
	 *  @param isDefault is default value
	 *  @return GL_Category_ID
	 */
	private int createGLCategory (String Name, String CategoryType, boolean isDefault)
	{
		MGLCategory cat = new MGLCategory (Env.getCtx() , 0, trxname);
		cat.setName(Name);
		cat.setCategoryType(CategoryType);
		cat.setIsDefault(isDefault);
		if (!cat.save())
		{
			log.error("GL Category NOT created - " + Name);
			return 0;
		}
		//
		return cat.getGL_Category_ID();
	}   //  createGLCategory
	
	/**
	 *  Create Document Types with Sequence
	 *  @param Name name
	 *  @param PrintName print name
	 *  @param DocBaseType document base type
	 *  @param DocSubType sales order sub type
	 *  @param C_DocTypeShipment_ID shipment doc
	 *  @param C_DocTypeInvoice_ID invoice doc
	 *  @param StartNo start doc no
	 *  @param GL_Category_ID gl category
	 *  @return C_DocType_ID doc type or 0 for error
	 */
	private int createDocType (String Name, String PrintName,
		String DocBaseType, String DocSubType,
		int C_DocTypeShipment_ID, int C_DocTypeInvoice_ID,
		int StartNo, int GL_Category_ID)
	{
	        log.debug("In createDocType");
	        log.debug("docBaseType: " + DocBaseType);
	        log.debug("GL_Category_ID: " + GL_Category_ID);
		MSequence sequence = null;
		if (StartNo != 0)
		{
			sequence = new MSequence(Env.getCtx(), getAD_Client_ID(), Name, StartNo, trxname);
			if (!sequence.save())
			{
				log.error("Sequence NOT created - " + Name);
				return 0;
			}
		}
		
		//MDocType dt = new MDocType (Env.getCtx(), DocBaseType, Name, trxname);
		MDocType dt = new MDocType (Env.getCtx(),0 , trxname);
		dt.setAD_Org_ID(0);
		dt.set_CustomColumn("DocBaseType", (Object) DocBaseType);	
		dt.setName (Name);
		dt.setPrintName (Name);
		if (DocSubType != null)
			dt.setDocSubType(DocSubType);
		if (C_DocTypeShipment_ID != 0)
			dt.setC_DocTypeShipment_ID(C_DocTypeShipment_ID);
		if (C_DocTypeInvoice_ID != 0)
			dt.setC_DocTypeInvoice_ID(C_DocTypeInvoice_ID);
		if (GL_Category_ID != 0)
			dt.setGL_Category_ID(GL_Category_ID);
		if (sequence == null)
			dt.setIsDocNoControlled(false);
		else
		{
			dt.setIsDocNoControlled(true);
			dt.setDocNoSequence_ID(sequence.getAD_Sequence_ID());
		}
		dt.setIsSOTrx(false);
		if (!dt.save())
		{
			log.error("DocType NOT created - " + Name);
			return 0;
		}
		//
		return dt.getC_DocType_ID();
	}   //  createDocType

}
