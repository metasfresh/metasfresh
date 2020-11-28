package de.metas.printing.model;

/*
 * #%L
 * de.metas.printing.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public interface I_AD_User extends org.compiere.model.I_AD_User
{
	/**
	 * @task https://github.com/metasfresh/metasfresh/issues/1081
	 */
	//@formatter:off
	public static final String COLUMNNAME_C_Printing_Queue_Recipient_ID = "C_Printing_Queue_Recipient_ID";

	public void setC_Printing_Queue_Recipient_ID(int C_Printing_Queue_Recipient_ID);
	public int getC_Printing_Queue_Recipient_ID();

	public void setC_Printing_Queue_Recipient(I_AD_User C_Printing_Queue_Recipient);
	public I_AD_User getC_Printing_Queue_Recipient();
	//@formatter:on
}
