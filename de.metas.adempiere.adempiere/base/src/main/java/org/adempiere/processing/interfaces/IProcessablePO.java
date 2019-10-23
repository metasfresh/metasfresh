package  org.adempiere.processing.interfaces;

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


public interface IProcessablePO
{
	public static final String COLUMNNAME_IsError = "IsError";

	public void setIsError(boolean IsError);

	public boolean isError();

	public static final String COLUMNNAME_Processed = "Processed";

	public void setProcessed(boolean Processed);

	public boolean isProcessed();

	public static final String COLUMNNAME_AD_Issue_ID = "AD_Issue_ID";

	public void setAD_Issue_ID(int AD_Issue_ID);

	public int getAD_Issue_ID();
	
	
}
