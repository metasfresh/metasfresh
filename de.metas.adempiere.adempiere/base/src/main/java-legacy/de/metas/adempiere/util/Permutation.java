package de.metas.adempiere.util;

/*
 * #%L
 * ADempiere ERP - Base
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


import java.util.HashSet;

/**
 * Permutation Utility.
 * 
 * @author metas GmbH, Mark Ostermann
 * 
 */
public class Permutation {
	static int maxIndex;
	static char[] feld;
	static String s;
	static HashSet<String> result;

	public Permutation() {
		result = new HashSet<String>();
	}

	static void result(String[] a) {
		//StringBuffer sb = new StringBuffer();
		for (int i = 0; i <= maxIndex; i++) {
			//sb.append(a[i] + " ");
			//result.add(sb.toString().trim());
			result.add(a[i]);
		}
		//result.add(sb.toString().trim());
	} // result

	public HashSet<String> getResult() {
		return result;
	}

	public void setMaxIndex(int index) {
		Permutation.maxIndex = index;
	}

	static void swap(String[] a, int i, int j) {
		String ablage = a[i];
		a[i] = a[j];
		a[j] = ablage;
	} // swap

	public void permute(String[] a, int endIndex) {
		if (endIndex == 0)
			result(a);
		else {
			permute(a, endIndex - 1);
			for (int i = 0; i <= endIndex - 1; i++) {
				swap(a, i, endIndex);
				permute(a, endIndex - 1);
				swap(a, i, endIndex);
			} // for i
		}
	} // permute
} // End of Permutation