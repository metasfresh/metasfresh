package de.metas.i18n;

import com.google.common.base.Splitter;
import de.metas.util.Check;
import lombok.NonNull;
import lombok.ToString;

import javax.annotation.Nullable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

@ToString
public final class AdMessagesTreeLoader
{
	private final IMsgBL msgBL;
	private final String adMessagePrefix;
	private final Predicate<String> filterAdMessagesBy;
	private final boolean removePrefix;

	private static final Splitter SPLIT_BY_DOT = Splitter.on('.');

	@lombok.Builder(builderClassName = "Builder")
	private AdMessagesTreeLoader(
			@NonNull final IMsgBL msgBL,
			@NonNull final String adMessagePrefix,
			@Nullable final Predicate<String> filterAdMessagesBy,
			final boolean removePrefix)
	{
		this.msgBL = msgBL;

		this.adMessagePrefix = adMessagePrefix;
		this.filterAdMessagesBy = filterAdMessagesBy;
		this.removePrefix = removePrefix;
	}

	public static class Builder
	{
		public Builder filterAdMessageStartingWithIgnoringCase(@Nullable final String filterString)
		{
			return filterAdMessagesBy(toAdMessageStartingWithIgnoreCaseFilter(filterString));
		}

		@Nullable
		private static Predicate<String> toAdMessageStartingWithIgnoreCaseFilter(@Nullable final String filterString)
		{
			if (filterString == null || Check.isBlank(filterString))
			{
				return null;
			}
			else
			{
				final String filterStringUC = filterString.trim().toUpperCase();
				return adMessageKey -> adMessageKey.trim().toUpperCase().startsWith(filterStringUC);
			}
		}

		public AdMessagesTree load(@NonNull final String adLanguage) {return build().load(adLanguage);}
	}

	public AdMessagesTree load(@NonNull final String adLanguage)
	{
		final LinkedHashMap<String, Object> tree = new LinkedHashMap<>();
		//tree.put("_language", adLanguage);
		loadInto(tree, adLanguage, adMessageKey -> adMessageKey);
		return AdMessagesTree.builder()
				.adLanguage(adLanguage)
				.map(tree)
				.build();
	}

	private void loadInto(
			@NonNull Map<String, Object> tree,
			@NonNull final String adLanguage,
			@NonNull final UnaryOperator<String> keyMapper)
	{
		msgBL.getMsgMap(adLanguage, adMessagePrefix, true /* removePrefix */)
				.forEach((adMessageKey, msgText) -> {
					if (filterAdMessagesBy == null || filterAdMessagesBy.test(adMessageKey))
					{
						addMessageToTree(tree, keyMapper.apply(adMessageKey), msgText);
					}
				});
	}

	private static void addMessageToTree(final Map<String, Object> tree, final String key, final String value)
	{
		final List<String> keyParts = SPLIT_BY_DOT.splitToList(key);

		Map<String, Object> currentNode = tree;
		for (int i = 0; i < keyParts.size() - 1; i++)
		{
			final String keyPart = keyParts.get(i);
			final Object currentNodeObj = currentNode.get(keyPart);
			if (currentNodeObj == null)
			{
				final Map<String, Object> parentNode = currentNode;
				currentNode = new LinkedHashMap<>();
				parentNode.put(keyPart, currentNode);
			}
			else if (currentNodeObj instanceof Map)
			{
				//noinspection unchecked
				currentNode = (Map<String, Object>)currentNodeObj;
			}
			else
			{
				// discarding the old value, shall not happen
				final Map<String, Object> parentNode = currentNode;
				currentNode = new LinkedHashMap<>();
				parentNode.put(keyPart, currentNode);
			}
		}

		final String keyPart = keyParts.get(keyParts.size() - 1);
		currentNode.put(keyPart, value);
	}
}
