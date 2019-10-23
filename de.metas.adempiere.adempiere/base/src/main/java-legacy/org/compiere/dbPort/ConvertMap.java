package org.compiere.dbPort;

import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import de.metas.util.Check;

public class ConvertMap
{
	private static final Map<String, String> dataTypes = new HashMap<String, String>();
	private static final Map<Pattern, String> pattern2replacement = new IdentityHashMap<Pattern, String>();

	public boolean isEmpty()
	{
		return pattern2replacement.isEmpty();
	}

	public void addPattern(final String matchPatternStr, final String replacementStr)
	{
		Check.assumeNotNull(matchPatternStr, "matchPatternStr not null");
		final Pattern matchPattern = Pattern.compile(matchPatternStr, Convert.REGEX_FLAGS);
		pattern2replacement.put(matchPattern, replacementStr);
	}

	public void addDataType(final String dataType, final String dataTypeTo)
	{
		Check.assumeNotNull(dataType, "dataType not null");
		Check.assumeNotNull(dataTypeTo, "dataTypeTo not null");
		
		dataTypes.put(dataType.toUpperCase(), dataTypeTo);

		final String matchPatternStr = "\\b" + dataType + "\\b";
		addPattern(matchPatternStr, dataTypeTo);
	}
	
	public Set<Map.Entry<Pattern, String>> getPattern2ReplacementEntries()
	{
		return pattern2replacement.entrySet();
	}
	
	public String getDataTypeReplacement(final String dataTypeUC)
	{
		if (dataTypeUC == null)
		{
			return dataTypeUC;
		}
		
		return dataTypes.get(dataTypeUC);
	}
}
