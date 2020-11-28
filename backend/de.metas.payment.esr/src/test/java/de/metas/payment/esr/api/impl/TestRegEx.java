package de.metas.payment.esr.api.impl;

/*
 * #%L
 * de.metas.payment.esr
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


import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Ignore;

@Ignore
public class TestRegEx
{

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		final Pattern esrReferenceNumberPattern = Pattern.compile("[0-9]{6}([0-9]*)[0-9]{1}");

		final String referenceNo = "0000001234561";

		final Matcher m = esrReferenceNumberPattern.matcher(referenceNo);
//		System.out.println("matches: " + m.matches());

		if(m.find())
		{
			System.out.println("groups: " + m.groupCount());
			for (int i = 0; i <= m.groupCount(); i++)
			{
				System.out.println("group " + i + ": " + m.group(i));
			}
		}
	}

}
