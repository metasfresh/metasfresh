package de.metas.document.refid.api;

/*
 * #%L
 * de.metas.document.refid
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


import java.util.List;
import java.util.Properties;

import org.compiere.model.PO;

import de.metas.document.refid.model.I_C_ReferenceNo;
import de.metas.document.refid.model.I_C_ReferenceNo_Doc;
import de.metas.document.refid.model.I_C_ReferenceNo_Type;
import de.metas.util.ISingletonService;

public interface IReferenceNoBL extends ISingletonService
{

	/**
	 * Creates/Updates {@link I_C_ReferenceNo} for given <code>po</code>. If already exists, just a link is created ({@link I_C_ReferenceNo_Doc}).
	 * 
	 * @param po
	 * @param instance
	 */
	public void linkReferenceNo(PO po, IReferenceNoGeneratorInstance instance);

	/**
	 * 
	 * @param po
	 */
	public void unlinkReferenceNo(PO po, IReferenceNoGeneratorInstance instance);

	List<IReferenceNoGeneratorInstance> getReferenceNoGeneratorInstances(Properties ctx);

	IReferenceNoGeneratorInstance getReferenceNoGeneratorInstance(Properties ctx, I_C_ReferenceNo_Type type);

	/**
	 * Link <code>fromModel</code> to same reference numbers as <code>toModel</code> is linked.
	 * 
	 * For linking, use the context and trxName of <code>toModel</code>.
	 * 
	 * @param fromModel
	 * @param toModel
	 */
	void linkOnSameReferenceNo(Object fromModel, Object toModel);

}
