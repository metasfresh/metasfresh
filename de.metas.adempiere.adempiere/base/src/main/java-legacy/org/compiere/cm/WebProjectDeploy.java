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
package org.compiere.cm;

import java.util.ArrayList;
import java.util.HashMap;

import org.compiere.model.MCStage;
import org.compiere.model.MContainer;
import org.compiere.model.MMedia;
import org.compiere.model.MMediaServer;
import org.compiere.model.MTree;
import org.compiere.model.MTreeNode;
import org.compiere.model.MTree_NodeCMC;
import org.compiere.model.MTree_NodeCMS;
import org.compiere.model.MWebProject;
import org.compiere.util.AdempiereUserError;

import de.metas.process.ProcessInfoParameter;
import de.metas.process.SvrProcess;

/**
 * 	Deploy Web Project
 *	
 *  @author Jorg Janke
 *  @version $Id: WebProjectDeploy.java,v 1.10 2006/09/04 21:21:31 comdivision Exp $
 */
public class WebProjectDeploy extends SvrProcess
{
	/**	WebProject					*/
	private int		p_CM_WebProject_ID = 0;
	
	/** Project						*/
	private MWebProject 				m_project = null;
	/**	Stage Hash Map				*/
	private HashMap<Integer, MCStage> 	m_map = new HashMap<Integer, MCStage>();
	/** List of IDs					*/
	private ArrayList <Integer>			m_idList = new ArrayList<Integer>();
	
	
	/**
	 *  Prepare - e.g., get Parameters.
	 */
	@Override
	protected void prepare()
	{
		ProcessInfoParameter[] para = getParametersAsArray();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();
			if (para[i].getParameter() == null)
				;
			else if (name.equals("CM_WebProject_ID"))
				p_CM_WebProject_ID = para[i].getParameterAsInt();
			else
				log.error("Unknown Parameter: " + name);
		}
	}	//	prepare

	/**
	 * 	Process
	 *	@return info
	 *	@throws Exception
	 */
	@Override
	protected String doIt ()
		throws Exception
	{
		org.compiere.cm.CacheHandler thisHandler = new org.compiere.cm.CacheHandler(org.compiere.cm.CacheHandler.convertJNPURLToCacheURL(getCtx().getProperty("java.naming.provider.url")), log, getCtx(), get_TrxName());
		
		log.info("CM_WebProject_ID=" + p_CM_WebProject_ID);
		m_project = new MWebProject(getCtx(), p_CM_WebProject_ID, get_TrxName());
		if (m_project.get_ID() != p_CM_WebProject_ID)
			throw new AdempiereUserError("@NotFound@ @CM_WebProject_ID@ " + p_CM_WebProject_ID);
		
		//	Deploy Media
		MMedia[] media = MMedia.getMedia(m_project);
		MMediaServer[] mserver = MMediaServer.getMediaServer(m_project);
		for (int i = 0; i < mserver.length; i++)
			mserver[i].deploy(media);
		
		//	Stage
		MCStage[] stages = MCStage.getStages(m_project);
		for (int i = 0; i < stages.length; i++)
			m_map.put(new Integer(stages[i].getCM_CStage_ID()), stages[i]);
		
		//	Copy Stage Tree
		final MTree treeS = MTree.builder()
				.setCtx(getCtx())
				.setTrxName(getTrxName())
				.setAD_Tree_ID(m_project.getAD_TreeCMS_ID())
				.setEditable(false)
				.setClientTree(false)
				.build();
		MTreeNode root = treeS.getRoot();
		copyStage(root, "/");
		
		//	Delete Inactive Containers
		MContainer[] containers = MContainer.getContainers(m_project);
		for (int i = 0; i < containers.length; i++)
		{
			MContainer container = containers[i];
			if (!m_idList.contains(new Integer(container.getCM_Container_ID())))
			{
				String name = container.getName();
				if (container.delete(true))
					log.debug("Deleted: " + name);
				else	//	e.g. was referenced
				{
					log.warn("Failed Delete: " + name);
					addLog(0,null,null, "@Error@ @Delete@: " + name);
				}
			}
			// Remove Container from cache
			thisHandler.cleanContainer(container.get_ID());
		}	//	Delete Inactive

		//	Sync Stage & Container Tree
		MTree_NodeCMS nodesCMS[] = MTree_NodeCMS.getTree(getCtx(), m_project.getAD_TreeCMS_ID(), get_TrxName());
		MTree_NodeCMC nodesCMC[] = MTree_NodeCMC.getTree(getCtx(), m_project.getAD_TreeCMC_ID(), get_TrxName());
		for (int s = 0; s < nodesCMS.length; s++)
		{
			MTree_NodeCMS nodeCMS = nodesCMS[s];
			int Node_ID = nodeCMS.getNode_ID();
			for (int c = 0; c < nodesCMC.length; c++)
			{
				MTree_NodeCMC nodeCMC = nodesCMC[c];
				if (nodeCMC.getNode_ID() == Node_ID)
				{
					//if (nodeCMS.getParent_ID()!=0) 
						nodeCMC.setParent_ID(nodeCMS.getParent_ID());
					nodeCMC.setSeqNo(nodeCMS.getSeqNo());
					nodeCMC.save();
					break;
				}
			}
		}	//	for all stage nodes
		// Clean ContainerTree Cache
		thisHandler.cleanContainerTree (p_CM_WebProject_ID);
	
		return "@Copied@ @CM_Container_ID@ #" + m_idList.size();
	}	//	doIt
	
	
	/**
	 * 	Copy Stage
	 *	@param node node 
	 *	@param path path
	 */
	private void copyStage (MTreeNode node, String path)
	{
		org.compiere.cm.CacheHandler thisHandler = new org.compiere.cm.CacheHandler(org.compiere.cm.CacheHandler.convertJNPURLToCacheURL(getCtx().getProperty("java.naming.provider.url")), log, getCtx(), get_TrxName());
		Integer ID = new Integer(node.getNode_ID());
		MCStage stage = m_map.get(ID);
		//	
		int size = node.getChildCount();
		for (int i = 0; i < size; i++)
		{
			MTreeNode child = (MTreeNode)node.getChildAt(i);
			ID = new Integer(child.getNode_ID());
			stage = m_map.get(ID);
			if (stage == null)
			{
				log.warn("Not Found ID=" + ID);
				continue;
			}
			if (!stage.isActive())
				continue;
			//
			if (stage != null && stage.isModified())
			{
				MContainer cc = MContainer.copy (m_project, stage, path);
				if (cc != null)
				{
					addLog (0, null, null, "@Copied@: " + cc.getName());
					m_idList.add(ID);
				}
				// Remove Container from cache
				thisHandler.cleanContainer(cc.get_ID());
				// Reset Modified flag...
				stage.setIsModified(false);
				stage.save(stage.get_TrxName());
			}
			if (child.isSummary())
				copyStage (child, path + stage.getRelativeURL() + "/");

		}
	}	//	copyStage
	
}	//	WebProjectDeploy
