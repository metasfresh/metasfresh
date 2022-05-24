package de.metas.i18n;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Value
@Builder
public class AdMessagesTree
{
	@NonNull String adLanguage;
	@NonNull Map<String, Object> map;

	public static AdMessagesTree merge(@NonNull List<AdMessagesTree> trees)
	{
		if (trees.isEmpty())
		{
			throw new AdempiereException("No trees");
		}
		else if (trees.size() == 1)
		{
			return trees.get(0);
		}
		else
		{
			String resultingLanguage = null;
			final LinkedHashMap<String, Object> resultingMap = new LinkedHashMap<>();

			for (final AdMessagesTree tree : trees)
			{
				if (resultingLanguage == null)
				{
					resultingLanguage = tree.getAdLanguage();
				}
				else if (!resultingLanguage.equals(tree.getAdLanguage()))
				{
					throw new AdempiereException("Cannot merge tress if not same language: " + trees);
				}

				deepCopyInto(resultingMap, tree.getMap());
			}

			return builder()
					.adLanguage(resultingLanguage)
					.map(resultingMap)
					.build();
		}
	}

	private static void deepCopyInto(final Map<String, Object> target, final Map<String, Object> source)
	{
		for (Map.Entry<String, Object> entry : source.entrySet())
		{
			final String key = entry.getKey();
			final Object valueOld = target.get(key);
			final Object valueNew = combineValue(valueOld, entry.getValue());
			target.put(key, valueNew);
		}
	}

	@Nullable
	private static Object combineValue(@Nullable final Object target, @Nullable final Object source)
	{
		if (source instanceof Map)
		{
			//noinspection unchecked
			final Map<String, Object> sourceMap = (Map<String, Object>)source;

			final LinkedHashMap<String, Object> result = new LinkedHashMap<>();

			if (target instanceof Map)
			{
				//noinspection unchecked
				final Map<String, Object> targetMap = (Map<String, Object>)target;

				deepCopyInto(result, targetMap);
				deepCopyInto(result, sourceMap);
				return result;
			}
			else
			{
				deepCopyInto(result, sourceMap);
				return result;
			}
		}
		else
		{
			return source;
		}
	}
}
