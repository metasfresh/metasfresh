package de.metas.location.impl;

import java.util.List;
import java.util.Properties;

import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.dao.ISqlQueryFilter;
import org.compiere.model.I_C_Postal;

import com.google.common.collect.ImmutableList;

import de.metas.util.Check;
import lombok.NonNull;
import lombok.ToString;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2019 metas GmbH
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

@ToString
public class PostalQueryFilter implements IQueryFilter<I_C_Postal>, ISqlQueryFilter
{
	public static PostalQueryFilter of(final String postal)
	{
		return new PostalQueryFilter(postal);
	}

	private static final String SQL = "LPAD(" + I_C_Postal.COLUMNNAME_Postal + ", 20, '0')=LPAD(?, 20, '0')";

	private final String postal;

	private PostalQueryFilter(@NonNull final String postal)
	{
		Check.assumeNotEmpty(postal, "postal is not empty");
		this.postal = postal.trim();
	}

	@Override
	public boolean accept(I_C_Postal model)
	{
		return postal.equals(model.getPostal());
	}

	@Override
	public String getSql()
	{
		return SQL;
	}

	@Override
	public List<Object> getSqlParams(final Properties ctx_NOTUSED)
	{
		return ImmutableList.of(postal);
	}
}
