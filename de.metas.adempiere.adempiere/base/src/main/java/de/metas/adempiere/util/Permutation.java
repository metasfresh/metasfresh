package de.metas.adempiere.util;

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